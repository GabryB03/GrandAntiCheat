package it.gabrielebologna.grandanticheat.check.movement;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;
import it.gabrielebologna.grandanticheat.utils.UtilPlayer;

public class BadMotion extends Check
{
	public static Map<UUID, Long> lastHit;
	
	public BadMotion()
	{
		super("BadMotion");
		lastHit = new HashMap<>();
	}
	
    @EventHandler
    public void onLog(PlayerQuitEvent e)
    {
    	if (lastHit.containsKey(e.getPlayer().getUniqueId()))
    	{
    		lastHit.remove(e.getPlayer().getUniqueId());
    	}
    	
        lastHit.remove(e.getPlayer().getUniqueId());
    }
	
    @EventHandler(ignoreCancelled = true)
    public void onHit(EntityDamageByEntityEvent e)
    {
        if (e.getEntity() instanceof Player)
        {
            Player player = (Player) e.getEntity();
            
            if (lastHit.containsKey(player.getUniqueId()))
            {
            	lastHit.remove(player.getUniqueId());
            }
            
            lastHit.put(player.getUniqueId(), System.currentTimeMillis());
        }
    }
	
	@EventHandler
	public void onMotionA(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
		
		if (lastHit.containsKey(player.getUniqueId()))
		{
			if (System.currentTimeMillis() - lastHit.get(player.getUniqueId()) < 1500L)
			{
				return;
			}
		}
		
		if (UtilPlayer.isInWeb(player) || UtilPlayer.isInWebOffset(player))
		{
			return;
		}
		
		if (event.getPlayer().getVelocity().getY() < 0)
		{
			return;
		}

        double YDist = Math.abs(event.getTo().getY() - event.getFrom().getY());
        double last = playerData.getLastYDi();
        playerData.setLastYDi(YDist);

        double distance = Math.abs(YDist - last);
        
        if (distance == 0.004677816280590719 || distance == 0.004677816280588054 || distance == 0.004677816280588942 || distance == 0.004677816280587166 || distance == 0.004677816280576508 || distance == 0.0D || distance == 2.5001525898460386E-5 || distance == 0.004702817806474968 || distance == 0.007360353995920832 || distance == 0.003469252354221908 || distance == 1.224871516285475E-4)
        {
        	return;
        }
        
        if (distance < 0.01 && YDist > 0 && !UtilPlayer.isOnClimbable(player, 0) && !UtilPlayer.isInWeb(player) && !UtilPlayer.isInWebOffset(player) &&!UtilPlayer.isNearLiquid(player) && !UtilPlayer.onLiquid(player.getLocation()) && !player.isFlying())
        {
        	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Motion Y (1)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", distance: " + distance, false);
        }
	}
	
	@EventHandler
	public void onMotionB(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
		
		if (UtilPlayer.isOnClimbable(player, 0) || UtilPlayer.isOnClimbable(player, 1))
		{
			return;
		}
		
		if (lastHit.containsKey(player.getUniqueId()))
		{
			if (System.currentTimeMillis() - lastHit.get(player.getUniqueId()) < 1500L)
			{
				return;
			}
		}

        double motionX = Math.abs(event.getTo().getX() - event.getFrom().getX());
        double motionZ = Math.abs(event.getTo().getZ() - event.getFrom().getZ());

        double lastX = playerData.getLastX();
        playerData.setLastX(motionX);
        double lastZ = playerData.getLastZ();
        playerData.setLastZ(motionZ);

        double f = event.getTo().getYaw() * 0.017453292F;

        double predictionX = lastX - (Math.sin(f) * 0.2f);
        double predictionZ = lastZ + (Math.sin(f) * 0.2f);

        double motion = Math.sqrt((motionX * motionX) + (motionZ * motionZ)) * 0.91f;
        double preMotion = Math.sqrt((predictionX * predictionX) + (predictionZ * predictionZ)) * 0.91f;

        double diff = Math.abs(motion - preMotion);
        double lastdiff = playerData.getLastXZ();
        playerData.setLastXZ(diff);
        
        if (playerData.getClientAirTicks() > 1 && !playerData.isClientOnGround() && motion > 0 && diff < 0.15 && diff == lastdiff && !(diff == 0.029497635240993615))
        {
        	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type B - Motion XZ", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", diff: " + diff, true);
        }
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
	public void onMotionC(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
		
		if (lastHit.containsKey(player.getUniqueId()))
		{
			if (System.currentTimeMillis() - lastHit.get(player.getUniqueId()) < 1500L)
			{
				return;
			}
		}
		
        if (UtilPlayer.isInWeb(player) || UtilPlayer.isInWebOffset(player) || playerData.isOnSlime() || playerData.isOnIce() || player.isFlying() || UtilPlayer.isOnClimbable(player, 0) || UtilPlayer.onLiquid(player.getLocation()) || UtilPlayer.isNearLiquid(player) || UtilPlayer.isNearLiquidOffset(player) || UtilPlayer.isOnClimbable(player, 1) || player.getActivePotionEffects().contains(PotionEffectType.JUMP))
        {
            playerData.setVerbose59(0);
            playerData.setAdded(0);
            playerData.setCancel(false);
        }
        
        if (playerData.isClientOnGround())
        {
            playerData.setVerbose59(0);
            playerData.setAdded(0);
            playerData.setCancel(false);
            return;
        }
        else
        {
            if (event.getFrom().getY() <= event.getTo().getY() && !playerData.isCancel())
            {
                double motionY = Math.abs(event.getTo().getY() - event.getFrom().getY());
                playerData.setAdded(playerData.getAdded() + motionY);
                
                if (playerData.getAdded() > 7.5)
                {
                	playerData.setVerbose59(playerData.getVerbose59() + 1);
                	
                    if (playerData.getVerbose59() > 3)
                    {
                    	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Motion Y (2)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", motion y: " + motionY + ", added: " + playerData.getAdded(), false);
                    }
                }
                else
                {
                	playerData.setVerbose59(playerData.getVerbose59() > 0 ? playerData.getVerbose59() - 1 : 0);
                }
            }
            else
            {
            	playerData.setVerbose59(0);
            }
        }
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
	public void onMotionD(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
			
        double YDist = Math.abs(event.getTo().getY() - event.getFrom().getY());
        double last = playerData.getDist1();
        playerData.setDist1(YDist);

        double distance = Math.abs(YDist - last);

        if (playerData.isClientOnGround() || distance == 0.004677816280590719 || distance == 0.015555072702206019 || distance == 0.004677816280588942 || distance == 0.004677816280587166 || distance == 0.014676931814392447 || distance == 0.004677816280588054)
        {
            playerData.setVerbose60(0);
            playerData.setCancel1(false);
            return;
        }
        
        if (playerData.isOnSlime() || playerData.isOnIce() || player.isFlying() || UtilPlayer.isOnClimbable(player, 0) || UtilPlayer.onLiquid(player.getLocation()) || UtilPlayer.isNearLiquid(player) || UtilPlayer.isNearLiquidOffset(player) || UtilPlayer.isOnClimbable(player, 1) || player.getActivePotionEffects().contains(PotionEffectType.JUMP))
        {
        	playerData.setCancel1(true);
        }

        if (!playerData.isClientOnGround() && distance < 0.03 && distance > 0 && !playerData.isCancel1())
        {
        	playerData.setVerbose60(playerData.getVerbose60() + 1);
        	
            if (playerData.getVerbose60() > 2)
            {
            	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Motion Y (3)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", distance: " + distance + ", last: " + last, false);
            }
        }
        else
        {
        	playerData.setVerbose60(playerData.getVerbose60() > 0 ? playerData.getVerbose60() - 1 : 0);
        }
	}
}