package it.gabrielebologna.grandanticheat.check.movement;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;
import it.gabrielebologna.grandanticheat.update.UpdateEvent;
import it.gabrielebologna.grandanticheat.utils.UtilPlayer;
import it.gabrielebologna.grandanticheat.utils.UtilTime;

public class NoFall extends Check
{
    public static Map<UUID, Map.Entry<Long, Integer>> NoFallTicks;
    public static Map<UUID, Double> FallDistance;
    public static ArrayList<Player> cancel;
	
	public NoFall()
	{
		super("NoFall");
		
        NoFallTicks = new HashMap<>();
        FallDistance = new HashMap<>();
        cancel = new ArrayList<>();
	}
	
	@Override
	public void setup()
	{
		if (GrandAntiCheat.isDebugMode())
		{
			this.getSettings().addDefault("fast-fall", true);
			this.getSettings().addDefault("instant-fall", true);
			this.getSettings().addDefault("no-damage", true);
		}
	}
	
    @EventHandler
    public void onDeath(PlayerDeathEvent e)
    {
        cancel.add(e.getEntity());
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent e)
    {
        FallDistance.remove(e.getPlayer().getUniqueId());
        
        if (FallDistance.containsKey(e.getPlayer().getUniqueId()))
        {
            FallDistance.containsKey(e.getPlayer().getUniqueId());
        }
        
        cancel.remove(e.getPlayer());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e)
    {
        if (e.getCause() == TeleportCause.ENDER_PEARL)
        {
            cancel.add(e.getPlayer());
        }
    }
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		if (!this.isToggled("fast-falling"))
		{
			return;
		}
		
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
		
		if ((System.currentTimeMillis() - playerData.getLastPlace()) < 1000L)
		{
			return;
		}
		
        if (player.getAllowFlight() || player.getGameMode().equals(GameMode.CREATIVE) || player.getVehicle() != null || cancel.remove(player) || UtilPlayer.isOnClimbable(player, 0) || UtilPlayer.isInWater(player))
        {
        	return;
        }
        
        Damageable dplayer = event.getPlayer();

        if (dplayer.getHealth() <= 0.0D)
        {
        	return;
        }

        double Falling = 0.0D;
        
        if ((!UtilPlayer.isOnGround(player)) && (event.getFrom().getY() > event.getTo().getY()))
        {
            if (FallDistance.containsKey(player.getUniqueId()))
            {
                Falling = FallDistance.get(player.getUniqueId());
            }
            
            Falling += event.getFrom().getY() - event.getTo().getY();
        }
        
        FallDistance.put(player.getUniqueId(), Falling);
        
        if (Falling < 3.0D)
        {
        	return;
        }
        
        long Time = System.currentTimeMillis();
        int Count = 0;
        
        if (NoFallTicks.containsKey(player.getUniqueId()))
        {
            Time = NoFallTicks.get(player.getUniqueId()).getKey();
            Count = NoFallTicks.get(player.getUniqueId()).getValue();
        }
        
        if ((player.isOnGround()) || (player.getFallDistance() == 0.0F))
        {
            player.damage(5);
            Count += 2;
        }
        else
        {
            Count--;
        }
        
        if (NoFallTicks.containsKey(player.getUniqueId()) && UtilTime.elapsed(Time, 10000L))
        {
            Count = 0;
            Time = System.currentTimeMillis();
        }
        
        if (Count > 0)
        {
        	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Fast falling", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", fall distance: " + player.getFallDistance(), true);
            Count = 0;
            FallDistance.put(player.getUniqueId(), 0.0D);
        }
        
        NoFallTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<>(Time, Count));
	}
	
	@EventHandler
	public void onUpdate(UpdateEvent event)
	{	
		for (Player player: Bukkit.getOnlinePlayers())
		{
			PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
			
			if (!playerData.canBeChecked())
			{
				continue;
			}
			
			if ((System.currentTimeMillis() - playerData.getLastPlace()) < 1000L)
			{
				return;
			}
			
			if (playerData.isClientOnGround() && playerData.getInitialDistanceFromGround() != 0.0D && this.isToggled("instant-fall"))
			{
				int expected = (int) playerData.getInitialDistanceFromGround();
				
				if (playerData.getLastClientAirTicks() < expected)
				{
					GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type B - Instant fall", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", air ticks: " + playerData.getLastClientAirTicks() + ", expected: " + expected, true);
				}
			}
			
			if (playerData.isInitialFall() && this.isToggled("no-damage") && !(playerData.getInitialDistanceFromGround() < 6.0D) && player.getFallDistance() == 0.0D && playerData.getClientGroundTicks() > 0L)
			{
				playerData.setInitialFall(false);
				
				if (!(playerData.getInitialDistanceFromGround() < 6.0D) && (player.getHealth() == playerData.getInitialFallHealth() || player.getHealth() == (playerData.getInitialFallHealth() - 0.5D) || player.getHealth() == (playerData.getInitialFallHealth() - 1.0D)))
				{
					GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type C - No damage", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
				}
			}
		}
	}
}