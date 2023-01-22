package it.gabrielebologna.grandanticheat.check.movement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.packetwrapper.WrapperPlayServerEntityVelocity;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;
import it.gabrielebologna.grandanticheat.update.UpdateEvent;
import it.gabrielebologna.grandanticheat.utils.UtilCheat;
import it.gabrielebologna.grandanticheat.utils.UtilMath;
import it.gabrielebologna.grandanticheat.utils.UtilPlayer;

public class Fly extends Check
{
	private PacketType[] packetTypes = new PacketType[] {PacketType.Play.Client.FLYING, PacketType.Play.Client.LOOK, PacketType.Play.Client.POSITION, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Server.ENTITY_VELOCITY};
	public static Map<UUID, Long> flyTicksA = new HashMap<UUID, Long>();
	
	@SuppressWarnings("deprecation")
	public Fly()
	{
		super("Fly");
		
		Check chick = this;
		
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(GrandAntiCheat.getPlugin(), packetTypes)
        {
            public void onPacketReceiving(final PacketEvent event)
            {
            	Player player = event.getPlayer();
            	PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
            	
            	if (!playerData.canBeChecked())
            	{
            		return;
            	}
            	
            	if ((System.currentTimeMillis() - playerData.getLastVelocity()) < 600L)
            	{
            		return;
            	}
            	
            	double distY = playerData.getClientDeltaY();
            	
            	if (!UtilPlayer.isNearGround(player, -0.5001D) && !UtilPlayer.hasGround(player) && !UtilPlayer.hasGroundv2(player))
            	{
            		playerData.setVerbose25(playerData.getVerbose25() + 1);
            		
            		if (player.getFallDistance() > 0.4F && distY > -0.05D && playerData.getVerbose25() > 2)
            		{
            			GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type B - Velocity", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
            		}
            	}
            	else
            	{
            		playerData.setVerbose25(0);
            	}
            }
            
            public void onPacketSending(final PacketEvent event)
            {
            	Player player = event.getPlayer();
            	PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
            	PacketContainer packet = event.getPacket();
            	
            	if (!playerData.canBeChecked())
            	{
            		return;
            	}
            	
            	WrapperPlayServerEntityVelocity wrapperPlayServerEntityVelocity = new WrapperPlayServerEntityVelocity(packet);
            	
            	if (wrapperPlayServerEntityVelocity.getEntity(player.getWorld()) == player)
            	{
            		playerData.setLastVelocity(System.currentTimeMillis());
            	}
            }
        });

		Bukkit.getScheduler().scheduleSyncRepeatingTask(GrandAntiCheat.getPlugin(), new BukkitRunnable()
		{
			@Override
			public void run()
			{		
				if (!chick.isToggled("fly-offsets"))
				{
					return;
				}
				
				for (Player player: Bukkit.getOnlinePlayers())
				{				
					PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);	

					if (!playerData.canBeChecked())
					{
						continue;
					}		

					if (playerData.getFlyOffsets().size() <= 0)
					{
						continue;
					}
					
					ArrayList<Double> flyOffsets = playerData.getFlyOffsets();
					int flySize = flyOffsets.size();
					double temporaryTotalFly = 0.0D;
					double realTotalFly = 0.0D;
					
					for (double d: flyOffsets)
					{
						temporaryTotalFly += d;
					}
					
					realTotalFly = temporaryTotalFly / flySize;

					if ((realTotalFly < -0.42D || realTotalFly >= 0.42D) && realTotalFly != 0.41999998688697815D && realTotalFly != 0.5D)
					{
						GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type C - Invalid Fly offsets", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", fly offset: " + realTotalFly, true);
					}
					
					playerData.getFlyOffsets().clear();
				}
			}
		}, 0L, 60L);
	}
	
	@Override
	public void setup()
	{
		if (GrandAntiCheat.isDebugMode())
		{
			this.getSettings().addDefault("ground", true);
			this.getSettings().addDefault("velocity", true);
			this.getSettings().addDefault("fly-offsets", true);
			this.getSettings().addDefault("air-time", true);
			this.getSettings().addDefault("prediction", true);
			flyTicksA = new HashMap<>();
		}
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
	public void onMoveNew(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
		
		if (this.isToggled("prediction"))
		{
	        double yDist = event.getTo().getY() - event.getFrom().getY();

	        if (playerData.isClientOnGround())
	        {
	            playerData.setVerbose56(0);
	            playerData.setValue(0);
	        }
	        else
	        {
	            playerData.setValue(playerData.getValue() + yDist);
	            
	            if (playerData.getValue() >= 0 && playerData.getValue()< 0.1 && !player.getActivePotionEffects().contains(PotionEffectType.JUMP) && playerData.getClientGroundTicks() <= 0)
	            {
	                if (playerData.getVerbose56() > 4)
	                {
	                	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type E - Prediction (2)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
	                }
	            }
	            else
	            {
	                playerData.setVerbose56(playerData.getVerbose56() > 0 ? playerData.getVerbose56() - 1 : 0);
	            }
	        }
		}
		
		if (this.isToggled("prediction"))
		{
	        double X = Math.abs(event.getTo().getX() - event.getFrom().getX());
	        double Z = Math.abs(event.getTo().getZ() - event.getFrom().getZ());

	        double XZDist = Math.abs(Math.sqrt((X * X) + (Z * Z)));
	        double last = playerData.getLastFly();
	        playerData.setLastFly(XZDist);

	        double prediction = (last - 0.8F) * 0.91;

	        if (!playerData.isClientOnGround() && !player.isFlying() && playerData.getClientAirTicks() > 4)
	        {
	            if (prediction > 0.27)
	            {
	                if (playerData.getVerbose57() > 1)
	                {
	                	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type E - Prediction (3)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
	                }
	            }
	            else
	            {
	            	playerData.setVerbose57(playerData.getVerbose57() > 0 ? playerData.getVerbose57() - 1 : 0);
	            }
	        }
	        else
	        {
	        	playerData.setVerbose57(0);
	        }
		}
	}
	
    @EventHandler
    public void onLog(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        flyTicksA.remove(uuid);
    }
	
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
		
		if (this.isToggled("air-time"))
		{
            if (UtilMath.playerMoved(event.getFrom().toVector(), event.getTo().toVector()) && player.isInsideVehicle())
            {
                if (playerData.getClientAirTicks() > 30 && playerData.getClientDeltaY() >= 0)
                {
                	playerData.setVerbose34(playerData.getVerbose34() + 1);
                	
                    if (playerData.getVerbose34() > 7)
                    {
                    	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type D - Air time", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", delta y: " + playerData.getClientDeltaY(), true);
                    }
                }
                else
                {
                    playerData.setVerbose34(Math.max(0, playerData.getVerbose34() - 1));
                }
            }
		}
		
		if (this.isToggled("ground"))
		{
        	if ((System.currentTimeMillis() - playerData.getLastVelocity()) > 600L)
        	{
    	        double distY = playerData.getClientDeltaY();
    	        double lastDistY = playerData.getClientLastDeltaY();
    	        double prediction = (lastDistY - 0.08D) * 0.9800000190734863D;
    	        double velocity = player.getVelocity().getY();
    	        boolean onGround = UtilPlayer.isNearGround(player, -0.5001D) || UtilPlayer.hasGroundv2(player);
    	        boolean lastOnGround = playerData.isLastOnGround3();
    	        boolean lastLastOnGround = playerData.isLastLastOnGround3();
    	        playerData.setLastOnGround3(onGround);
    	        playerData.setLastLastOnGround3(lastOnGround);
    	        
    	        if (distY != -0.5546255304959118 && distY != 0.41999998688697815 && distY != -0.15523200451660557 && distY != -0.044509214780390494)
    	        {
        	        if (!onGround && !lastOnGround && !lastLastOnGround && Math.abs(prediction) > 0.005D && velocity < -0.075D)
        	        {
        	        	playerData.setVerbose24(playerData.getVerbose24() + 1);
        	        	
        	        	if (!UtilMath.isRoughlyEqual(distY, prediction, 0.001D) && playerData.getVerbose24() > 12)
        	        	{
        	        		GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Ground (1)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", dist y: " + distY + ", prediction: " + prediction + ", velocity: " + velocity, true);
        	        	}
        	        }
        	        else
        	        {
        	        	playerData.setVerbose24(0);
        	        }	
    	        }
        	}
		}
		
		if (this.isToggled("air-time"))
		{
	        if (event.isCancelled() || (event.getTo().getX() == event.getFrom().getX()) && (event.getTo().getZ() == event.getFrom().getZ()) || player.getAllowFlight() || player.getVehicle() != null || UtilPlayer.isInWater(player) || UtilCheat.isInWeb(player))
	        {
	        	return;
	        }

	        if (UtilCheat.blocksNear(player.getLocation()))
	        {
	            flyTicksA.remove(player.getUniqueId());
	            return;
	        }
	        
	        if (Math.abs(event.getTo().getY() - event.getFrom().getY()) > 0.06)
	        {
	            flyTicksA.remove(player.getUniqueId());
	            return;
	        }

	        long Time = System.currentTimeMillis();
	        
	        if (flyTicksA.containsKey(player.getUniqueId()))
	        {
	            Time = flyTicksA.get(player.getUniqueId());
	        }
	        
	        long MS = System.currentTimeMillis() - Time;
	        
	        if (MS > 200L)
	        {
	            GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type D - Air time", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
	            flyTicksA.remove(player.getUniqueId());
	            return;
	        }
	        
	        flyTicksA.put(player.getUniqueId(), Time);
	    }
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event)
	{	
		if (event.getReason().toLowerCase().equalsIgnoreCase("flying is not enabled on this server"))
		{
			Player player = event.getPlayer();
			PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
			
			if (!playerData.canBeChecked())
			{
				return;
			}
			
			GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Ground (2)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
			event.setCancelled(true);
		}
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
			
			if (playerData.getClientAirTicks() > 40L && player.getFallDistance() <= 2.0D && !playerData.isInitialFall() && (!(System.currentTimeMillis() - playerData.getLastPlace() < 500L)))
			{
				GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Ground (3)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
			}
		}
	}
}