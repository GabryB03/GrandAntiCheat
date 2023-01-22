package it.gabrielebologna.grandanticheat.check.movement;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;
import it.gabrielebologna.grandanticheat.update.UpdateEvent;
import it.gabrielebologna.grandanticheat.utils.UtilBlock;
import it.gabrielebologna.grandanticheat.utils.UtilCheat;
import it.gabrielebologna.grandanticheat.utils.UtilMath;
import it.gabrielebologna.grandanticheat.utils.UtilMove;
import it.gabrielebologna.grandanticheat.utils.UtilNMS;
import it.gabrielebologna.grandanticheat.utils.UtilPacket;
import it.gabrielebologna.grandanticheat.utils.UtilPlayer;
import it.gabrielebologna.grandanticheat.utils.UtilTime;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class Speed extends Check
{
    public static Map<UUID, Map.Entry<Integer, Long>> speedTicks;
    public static Map<UUID, Map.Entry<Integer, Long>> tooFastTicks;
    public static Map<UUID, Long> lastHit;
    public static Map<UUID, Double> velocity;   
    public Map<UUID, Map.Entry<Integer, Long>> speedTicks1;
    public Map<UUID, Map.Entry<Integer, Long>> tooFastTicks1;
	
	public Speed()
	{
		super("Speed");
		
        speedTicks = new HashMap<>();
        tooFastTicks = new HashMap<>();
        speedTicks1 = new HashMap<>();
        tooFastTicks1 = new HashMap<>();
        lastHit = new HashMap<>();
        velocity = new HashMap<>();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setup()
	{	
		if (GrandAntiCheat.isDebugMode())
		{
			this.getSettings().addDefault("speed-offsets", true);
			this.getSettings().addDefault("max-speed-offset", 0.40D);
			this.getSettings().addDefault("offset-calculation-time", 60L);
			this.getSettings().addDefault("hop", true);
			this.getSettings().addDefault("distance", true);
			this.getSettings().addDefault("blocks", true);
			this.getSettings().addDefault("fast-moves", true);
			this.getSettings().addDefault("high-moves", true);
			this.getSettings().addDefault("friction", true);
		}
		
		Check chick = this;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(GrandAntiCheat.getPlugin(), new BukkitRunnable()
		{
			@Override
			public void run()
			{		
				if (!chick.isToggled("speed-offsets"))
				{
					return;
				}
				
				for (Player player: Bukkit.getOnlinePlayers())
				{				
					PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);	

					if (!playerData.canBeChecked() || playerData.isOnIce() || playerData.isOnSlime())
					{
						continue;
					}		

					if (playerData.getSpeedOffsets().size() <= 0)
					{
						continue;
					}
					
					if (lastHit.containsKey(player.getUniqueId()))
					{
						if ((System.currentTimeMillis() - (lastHit.get(player.getUniqueId()))) < 1000L)
						{
							continue;
						}
					}
					
					ArrayList<Double> speedOffsets = playerData.getSpeedOffsets();
					int speedSize = speedOffsets.size();
					double temporaryTotalSpeed = 0.0D;
					double realTotalSpeed = 0.0D;
					
					for (double d: speedOffsets)
					{
						temporaryTotalSpeed += d;
					}
					
					realTotalSpeed = temporaryTotalSpeed / speedSize;
					
					if ((System.currentTimeMillis() - playerData.getLastIce()) < 700L)
					{
						if (realTotalSpeed > 0.49D)
						{
							GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type A - Invalid Speed offsets", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", speed total offset: " + realTotalSpeed + ", speed size: " + speedSize, true);
						}	
					}
					else
					{
						double maxSpeed = GrandAntiCheat.isDebugMode() ? chick.getSettings().getDouble("max-speed-offset") : 0.40D;
						
						if (realTotalSpeed > maxSpeed)
						{
							GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type A - Invalid Speed offsets", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", speed total offset: " + realTotalSpeed + ", speed size: " + speedSize, true);
						}
					}
					
					playerData.getSpeedOffsets().clear();
				}
			}
		}, 0L, GrandAntiCheat.isDebugMode() ? this.getSettings().getLong("offset-calculation-time") : 60L);
	}
	
	@EventHandler
	public void onFriction(PlayerMoveEvent event)
	{
        if (!UtilPacket.getServerVersion().equalsIgnoreCase("v1_8_R3"))
        {
            return;
        }
        
        Player player = event.getPlayer();
        PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
        
        if (!playerData.canBeChecked())
        {
        	return;
        }
        
        Location to = event.getTo(), from = event.getFrom();
        
        if (to == null || from == null)
        {
        	return;
        }

        EntityPlayer nmsPlayer = UtilNMS.getNmsPlayer(player);
        
        double blockFriction = nmsPlayer.world.getType(new BlockPosition(nmsPlayer.locX, nmsPlayer.locY, nmsPlayer.locZ)).getBlock().frictionFactor;

        double deltaX = to.getX() - from.getX();
        double deltaZ = to.getZ() - from.getZ();

        double distance = (deltaX * deltaX) + (deltaZ * deltaZ);
        double lastdistance = playerData.getLastFriction();
        playerData.setLastFriction(distance);

        double prediction = lastdistance * blockFriction;
        double equal = distance - prediction;

        if (System.currentTimeMillis() - playerData.getLastIce() < 2000 || playerData.isOnSlime())
        {
            return;
        }

        if (equal > 1.5)
        {
            if (playerData.getVerbose58().flag(3, 1000L))
            {
            	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type G - Friction", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
            }
        }
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);	
		
		if (!playerData.canBeChecked())
		{
			return;
		}		
		
		if (lastHit.containsKey(player.getUniqueId()))
		{
			if ((System.currentTimeMillis() - (lastHit.get(player.getUniqueId()))) < 1000L)
			{
				return;
			}
		}
		
		if (player.getVehicle() != null)
		{
			return;
		}
		
		if (this.isToggled("hop"))
		{
			if (!UtilPlayer.blockNearHead(player) && !UtilPlayer.isHalfBlockNearHead(player))
			{
		        double dist = playerData.getClientSpeedXZ();
		        double lastDist = playerData.getLastClientSpeedXZ();
		        double prediction = lastDist * 0.699999988079071D;
		        double diff = dist - prediction;
		        double scaledDist = diff * 100.0D;
		        double max = 11.5D;
		        max += (double) UtilPlayer.getPotionEffectLevel(PotionEffectType.SPEED, player) * max;
		        
		        if ((System.currentTimeMillis() - playerData.getLastIce()) < 1000L)
		        {
		            max = 10.6D;
		        }

		        boolean onGround = UtilPlayer.hasGround(player) || UtilPlayer.hasGroundv2(player);
		        boolean lastOnGround = playerData.isLastOnGround1();
		        boolean lastLastOnGround = playerData.isLastLastOnGround1();
		        playerData.setLastOnGround1(onGround);
		        playerData.setLastLastOnGround1(lastOnGround);
		        
		        if (scaledDist > max && onGround && lastOnGround && lastLastOnGround)
		        {
		        	playerData.setVerbose21(playerData.getVerbose21() + 1);
		        	
		            if (playerData.getVerbose21() > 3)
		            {
		            	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type B - Hop", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", dist: " + scaledDist + ", max: " + max, true);
		            }
		        }
		        else
		        {
		            playerData.setVerbose21(0);
		        }
			}
		}
		
		if (this.isToggled("distance"))
		{
			if (UtilPlayer.blockNearHead(player) || UtilPlayer.isHalfBlockNearHead(player))
			{
				playerData.setB(System.currentTimeMillis());
			}
			
			if (!((System.currentTimeMillis() - playerData.getB()) < 2000L) && !playerData.isOnIce() && !playerData.isOnSlime())
			{
		         double dist = playerData.getClientSpeedXZ();
		         double lastDist = playerData.getLastClientSpeedXZ();
		         boolean onGround = UtilPlayer.hasGround(player) || UtilPlayer.hasGroundv2(player);
		         boolean lastOnGround = playerData.isLastOnGround2();
		         playerData.setLastOnGround2(onGround);
		         double prediction = lastDist * 0.699999988079071D;
		         double diff = dist - prediction;
		         double scaledDist = diff * 100.0D;
		         double max = 9.9D;
		         max += (double) UtilPlayer.getPotionEffectLevel(PotionEffectType.SPEED, player) * max;
		         
		         if ((System.currentTimeMillis() - playerData.getLastIce()) < 1000L || (System.currentTimeMillis() - playerData.getLastSlime()) < 1000L)
		         {
		             max = 14.2D;
		         }
		         
		         if (!onGround && !lastOnGround)
		         {
		             if (scaledDist > max)
		             {
		            	 playerData.setVerbose22(playerData.getVerbose22() + 1);
		            	 
		                 if (playerData.getVerbose22() > 2)
		                 {
		                	 GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type C - Distance", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", dist: " + scaledDist + ", max: " + max, true);
		                 }
		             }
		             else
		             {
		                 playerData.setVerbose22(0);
		             }
		         }
		         else
		         {
		        	 playerData.setVerbose22(0);
		         }
			}
		}
		
		if (this.isToggled("blocks"))
		{
	         if (UtilPlayer.isOnStairs(player))
	         {
	        	 playerData.setS1(System.currentTimeMillis());
	         }

	         if (UtilPlayer.blockNearHead(player) || UtilPlayer.isHalfBlockNearHead(player))
	         {
	        	 playerData.setB1(System.currentTimeMillis());
	         }

	         if (System.currentTimeMillis() - playerData.getB1() < 2000L)
	         {
	        	 return;
	         }

	         if (System.currentTimeMillis() - playerData.getS1() < 600L)
	         {
	        	 return;
	         }

	         double dist = playerData.getClientSpeedXZ();
	         double max = 0.63D;
	         max += (double) UtilPlayer.getPotionEffectLevel(PotionEffectType.SPEED, player) * max;
	         
	         if ((System.currentTimeMillis() - playerData.getLastIce()) < 1000L)
	         {
	        	 max += 0.24D;
	         }

	         if ((System.currentTimeMillis() - playerData.getLastSlime()) < 1000L)
	         {
	        	 max += 0.24D;
	         }

	         if (dist > max)
	         {
	        	 GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type D - Blocks", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", dist: " + dist + ", max: " + max, true);
	         }
		}
		
		if (this.isToggled("fast-moves"))
		{
	        if ((event.getFrom().getX() == event.getTo().getX()) && (event.getFrom().getY() == event.getTo().getY()) && (event.getFrom().getZ() == event.getFrom().getZ()) || player.getAllowFlight() || player.getVehicle() != null || player.getVelocity().length() + 0.1 < velocity.getOrDefault(player.getUniqueId(), -1.0D) && !player.hasPotionEffect(PotionEffectType.POISON) && !player.hasPotionEffect(PotionEffectType.WITHER) && player.getFireTicks() == 0)
	        {
	        	return;
	        }

	        long lastHitDiff = lastHit.containsKey(player.getUniqueId()) ? lastHit.get(player.getUniqueId()) - System.currentTimeMillis() : 2001L;

	        int Count = 0;
	        long Time = UtilTime.nowlong();
	        
	        if (speedTicks.containsKey(player.getUniqueId()))
	        {
	            Count = speedTicks.get(player.getUniqueId()).getKey();
	            Time = speedTicks.get(player.getUniqueId()).getValue();
	        }
	        
	        int TooFastCount = 0;
	        
	        if (tooFastTicks.containsKey(player.getUniqueId()))
	        {
	            double OffsetXZ = UtilMath.offset(UtilMath.getHorizontalVector(event.getFrom().toVector()), UtilMath.getHorizontalVector(event.getTo().toVector()));
	            double LimitXZ = 0.0D;
	            
	            if ((UtilPlayer.isOnGround(player)) && (player.getVehicle() == null))
	            {
	                LimitXZ = 0.34D;
	            }
	            else
	            {
	                LimitXZ = 0.39D;
	            }
	            
	            if (lastHitDiff < 800L)
	            {
	                ++LimitXZ;
	            }
	            else if (lastHitDiff < 1600L)
	            {
	                LimitXZ += 0.4;
	            }
	            else if (lastHitDiff < 2000L)
	            {
	                LimitXZ += 0.1;
	            }
	            
	            if (UtilCheat.slabsNear(player.getLocation()))
	            {
	                LimitXZ += 0.05D;
	            }
	            
	            Location b = UtilPlayer.getEyeLocation(player);
	            b.add(0.0D, 1.0D, 0.0D);
	            
	            if ((b.getBlock().getType() != Material.AIR) && (!UtilCheat.canStandWithin(b.getBlock())))
	            {
	                LimitXZ = 0.69D;
	            }
	            
	            Location below = event.getPlayer().getLocation().clone().add(0.0D, -1.0D, 0.0D);

	            if (UtilCheat.isStair(below.getBlock()))
	            {
	                LimitXZ += 0.6;
	            }

	            if (isOnIce(player))
	            {
	                if ((b.getBlock().getType() != Material.AIR) && (!UtilCheat.canStandWithin(b.getBlock())))
	                {
	                    LimitXZ = 1.0D;
	                }
	                else
	                {
	                    LimitXZ = 0.75D;
	                }
	            }
	            
	            float speed = player.getWalkSpeed();
	            LimitXZ += (speed > 0.2F ? speed * 10.0F * 0.33F : 0.0F);
	            
	            for (PotionEffect effect : player.getActivePotionEffects())
	            {
	                if (effect.getType().equals(PotionEffectType.SPEED))
	                {
	                    if (player.isOnGround())
	                    {
	                        LimitXZ += 0.061D * (effect.getAmplifier() + 1);
	                    }
	                    else
	                    {
	                        LimitXZ += 0.031D * (effect.getAmplifier() + 1);
	                    }
	                }
	            }
	            
	            if (OffsetXZ > LimitXZ && !UtilTime.elapsed(tooFastTicks.get(player.getUniqueId()).getValue(), 150L))
	            {
	                TooFastCount = tooFastTicks.get(player.getUniqueId()).getKey() + 3;
	            }
	            else
	            {
	                TooFastCount = TooFastCount > -150 ? TooFastCount-- : -150;
	            }
	        }
	        
	        if (TooFastCount >= 11)
	        {
	            TooFastCount = 0;
	            Count++;
	        }
	        
	        if (speedTicks.containsKey(player.getUniqueId()) && UtilTime.elapsed(Time, 30000L))
	        {
	            Count = 0;
	            Time = UtilTime.nowlong();
	        }
	        
	        if (Count >= 4 && !playerData.isOnIce())
	        {
	        	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type E - Fast moves", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
	            Count = 0;
	        }
	        
	        if (!player.isOnGround())
	        {
	            velocity.put(player.getUniqueId(), player.getVelocity().length());
	        }
	        else
	        {
	            velocity.put(player.getUniqueId(), -1.0D);
	        }
	        
	        tooFastTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<>(TooFastCount, System.currentTimeMillis()));
	        speedTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<>(Count, Time));
		}
		
		if (this.isToggled("high-moves"))
		{
	        Location from = event.getFrom().clone();
	        Location to = event.getTo().clone();
	        Player p = event.getPlayer();

	        Location l = p.getLocation();
	        int x = l.getBlockX();
	        int y = l.getBlockY();
	        int z = l.getBlockZ();
	        Location blockLoc = new Location(p.getWorld(), x, y - 1, z);
	        Location loc = new Location(p.getWorld(), x, y, z);
	        Location loc2 = new Location(p.getWorld(), x, y + 1, z);
	        Location above = new Location(p.getWorld(), x, y + 2, z);
	        Location above3 = new Location(p.getWorld(), x - 1, y + 2, z - 1);
	        long lastHitDiff = Math.abs(System.currentTimeMillis() - lastHit.getOrDefault(p.getUniqueId(), 0L));

	        if ((event.getTo().getX() == event.getFrom().getX()) && (event.getTo().getZ() == event.getFrom().getZ()) && (event.getTo().getY() == event.getFrom().getY()) || lastHitDiff < 1500L || p.getNoDamageTicks() != 0 || p.getVehicle() != null || p.getGameMode().equals(GameMode.CREATIVE) || p.getAllowFlight()) return;

	        double Airmaxspeed = 0.4;
	        double maxSpeed = 0.42;
	        double newmaxspeed = 0.75;
	        
	        if (isOnIce(p))
	        {
	            newmaxspeed = 1.0;
	        }

	        double ig = 0.28;
	        double speed = UtilMath.offset(getHV(to.toVector()), getHV(from.toVector()));
	        double onGroundDiff = (to.getY() - from.getY());

	        if (p.hasPotionEffect(PotionEffectType.SPEED))
	        {
	            int level = getPotionEffectLevel(p, PotionEffectType.SPEED);
	            
	            if (level > 0)
	            {
	                newmaxspeed = (newmaxspeed * (((level * 20) * 0.011) + 1));
	                Airmaxspeed = (Airmaxspeed * (((level * 20) * 0.011) + 1));
	                maxSpeed = (maxSpeed * (((level * 20) * 0.011) + 1));
	                ig = (ig * (((level * 20) * 0.011) + 1));
	            }
	        }
	        
	        Airmaxspeed += p.getWalkSpeed() > 0.2 ? p.getWalkSpeed() * 0.8 : 0;
	        maxSpeed += p.getWalkSpeed() > 0.2 ? p.getWalkSpeed() * 0.8 : 0;

	        if (!UtilMove.blockNearHead(player))
	        {
		        if (isReallyOnGround(p) && to.getY() == from.getY())
		        {
		            if (speed >= maxSpeed && p.isOnGround() && p.getFallDistance() < 0.15 && blockLoc.getBlock().getType() != Material.ICE && blockLoc.getBlock().getType() != Material.PACKED_ICE && loc2.getBlock().getType() != Material.TRAP_DOOR && above.getBlock().getType() == Material.AIR && above3.getBlock().getType() == Material.AIR)
		            {
		            	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type F - High moves", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", speed: " + speed + ", max speed: " + maxSpeed, true);
		            }
		        }
		        
		        if (!isReallyOnGround(p) && speed >= Airmaxspeed && !isOnIce(p) && blockLoc.getBlock().getType() != Material.ICE && !blockLoc.getBlock().isLiquid() && !loc.getBlock().isLiquid() && blockLoc.getBlock().getType() != Material.PACKED_ICE && above.getBlock().getType() == Material.AIR && above3.getBlock().getType() == Material.AIR && blockLoc.getBlock().getType() != Material.AIR)
		        {
		        	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type F - High moves", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", speed: " + speed + ", max speed: " + maxSpeed, true);
		        }
		        
		        if (speed >= newmaxspeed && isOnIce(p) && p.getFallDistance() < 0.6 && loc2.getBlock().getType() != Material.TRAP_DOOR && above.getBlock().getType() == Material.AIR && loc2.getBlock().getType() == Material.AIR)
		        {
		        	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type F - High moves", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", speed: " + speed + ", max speed: " + maxSpeed, true);
		        }

		        if (speed > ig && !isAir(p) && onGroundDiff <= -0.4 && p.getFallDistance() <= 0.4 && !flaggyStuffNear(p.getLocation()) && blockLoc.getBlock().getType() != Material.ICE && event.getTo().getY() != event.getFrom().getY() && blockLoc.getBlock().getType() != Material.PACKED_ICE && loc2.getBlock().getType() != Material.TRAP_DOOR && above.getBlock().getType() == Material.AIR && above3.getBlock().getType() == Material.AIR)
		        {
		        	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type F - High moves", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", speed: " + speed + ", max speed: " + maxSpeed, true);
		        }
	        }
		}
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event)
	{	
		if (event.getReason().toLowerCase().contains("move packet") || event.getReason().toLowerCase().contains("position"))
		{
			Player player = event.getPlayer();
			PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
			
			if (!playerData.canBeChecked())
			{
				return;
			}		
			
			if (UtilMove.blockNearHead(player))
			{
				return;
			}
			
			GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type F - High moves", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
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
			
			if (lastHit.containsKey(player.getUniqueId()))
			{
				if ((System.currentTimeMillis() - (lastHit.get(player.getUniqueId()))) < 1000L)
				{
					return;
				}
			}
			
			if (player.getVehicle() != null)
			{
				return;
			}
			
			if (UtilMove.blockNearHead(player))
			{
				return;
			}
			
			if (playerData.getClientSpeedXZ() >= 0.8D)
			{
				GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type F - High moves", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", speed: " + playerData.getClientSpeedXZ(), true);
			}
		}
	}
	
    public boolean isOnIce(final Player player)
    {
        Location a = player.getLocation();
        a.setY(a.getY() - 1.0);
        
        if (a.getBlock().getType().equals(Material.ICE))
        {
            return true;
        }
        
        a.setY(a.getY() - 1.0);
        return a.getBlock().getType().equals(Material.ICE);
    }
    
    @EventHandler
    public void onLog(PlayerQuitEvent e)
    {
        speedTicks.remove(e.getPlayer().getUniqueId());
        tooFastTicks.remove(e.getPlayer().getUniqueId());
        lastHit.remove(e.getPlayer().getUniqueId());
        velocity.remove(e.getPlayer().getUniqueId());
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
    
    @SuppressWarnings("deprecation")
	public static boolean isReallyOnGround(Player p)
    {
        Location l = p.getLocation();
        int x = l.getBlockX();
        int y = l.getBlockY();
        int z = l.getBlockZ();
        Location b = new Location(p.getWorld(), x, y - 1, z);
        return p.isOnGround() && b.getBlock().getType() != Material.AIR && b.getBlock().getType() != Material.WEB && !b.getBlock().isLiquid();
    }

    public static boolean flaggyStuffNear(Location loc)
    {
        boolean nearBlocks = false;
        
        for (Block bl : UtilBlock.getSurrounding(loc.getBlock(), true))
        {
            if ((bl.getType().equals(Material.STEP)) || (bl.getType().equals(Material.DOUBLE_STEP)) || (bl.getType().equals(Material.BED)) || (bl.getType().equals(Material.WOOD_DOUBLE_STEP)) || (bl.getType().equals(Material.WOOD_STEP)))
            {
                nearBlocks = true;
                break;
            }
        }
        
        for (Block bl : UtilBlock.getSurrounding(loc.getBlock(), false))
        {
            if ((bl.getType().equals(Material.STEP)) || (bl.getType().equals(Material.DOUBLE_STEP)) || (bl.getType().equals(Material.BED)) || (bl.getType().equals(Material.WOOD_DOUBLE_STEP)) || (bl.getType().equals(Material.WOOD_STEP)))
            {
                nearBlocks = true;
                break;
            }
        }
        
        if (isBlock(loc.getBlock().getRelative(BlockFace.DOWN), new Material[]{Material.STEP, Material.BED, Material.DOUBLE_STEP, Material.WOOD_DOUBLE_STEP, Material.WOOD_STEP}))
        {
            nearBlocks = true;
        }
        
        return nearBlocks;
    }

    public static boolean isBlock(Block block, Material[] materials)
    {
        Material type = block.getType();
        Material[] arrayOfMaterial;
        int j = (arrayOfMaterial = materials).length;
        
        for (int i = 0; i < j; i++)
        {
            Material m = arrayOfMaterial[i];
            
            if (m == type)
            {
                return true;
            }
        }
        
        return false;
    }

    public static boolean isAir(final Player player)
    {
        final Block b = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        return b.getType().equals(Material.AIR) && b.getRelative(BlockFace.WEST).getType().equals(Material.AIR) && b.getRelative(BlockFace.NORTH).getType().equals(Material.AIR) && b.getRelative(BlockFace.EAST).getType().equals(Material.AIR) && b.getRelative(BlockFace.SOUTH).getType().equals(Material.AIR);
    }
    
	private int getPotionEffectLevel(Player p, PotionEffectType pet)
    {
        for (PotionEffect pe : p.getActivePotionEffects())
        {
            if (pe.getType().getName().equals(pet.getName()))
            {
                return pe.getAmplifier() + 1;
            }
        }
        
        return 0;
    }

	private Vector getHV(Vector V)
    {
        V.setY(0);
        return V;
    }
	
	@EventHandler
	public void onMoveA(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);	
		
		if (!playerData.canBeChecked() || playerData.isOnIce() || playerData.isOnSlime())
		{
			return;
		}		
		
		double distance = Math.abs(UtilMove.getHorizontalDistanceSpeed(event.getTo(), event.getFrom()));

		double maxSpeed = UtilMove.getBaseMovementSpeed(playerData, playerData.isClientOnGround() ? 0.29 : 0.36, true);
		double diff = distance - maxSpeed;
		
		if (diff >= 0 && !player.isFlying() && (System.currentTimeMillis() - playerData.getLastVelocityEvent() > 1000L))
		{
			playerData.setVerbose61(playerData.getVerbose61() + 1);
			
			if (playerData.getVerbose61() > 10)
			{
				GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type H - Acceleration", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", distance: " + distance + ", diff: " + diff + ", max speed: " + maxSpeed, false);
			}
		}
		else
		{
			playerData.setVerbose61(playerData.getVerbose61() > 0 ? playerData.getVerbose61() - 1 : 0);
		}
	}
	
	@EventHandler
	public void onMoveB(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);	
		
		if (!playerData.canBeChecked())
		{
			return;
		}		
		
        Location to = player.getLocation();
        Location from = playerData.getFrom();
        playerData.setFrom(to);
        
        if (from == null || to == null)
        {
        	return;
        }
        
        double speed = UtilMove.getHorizontalDistanceSpeed(to, from);
        
        if (speed <= 0.01)
        {
            playerData.setCountA(playerData.getCountA() + 1);
        }
        
        if (speed > 2)
        {
            playerData.setSpikes(playerData.getSpikes() + 1);
        }
        else
        {
        	playerData.setSpikes(playerData.getSpikes() > 0 ? playerData.getSpikes() - 1 : 0);
        }

        if (playerData.getSpikes() > 2 && playerData.getCountA() > 6 && speed > 0 && !player.isFlying())
        {
        	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type I - Spikes", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
            playerData.setCountA(0);
            playerData.setSpikes(0);
        }
	}
	
	@EventHandler
	public void onMoveC(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);	
		
		if (!playerData.canBeChecked())
		{
			return;
		}		
		
        double distance = Math.abs(UtilMove.getHorizontalDistanceSpeed(event.getTo(), event.getFrom()));
        double maxSpeed = UtilMove.getBaseMovementSpeed(playerData, 0.29, true);
        
        if (distance > maxSpeed && playerData.isClientOnGround())
        {
        	playerData.setVerbose62(playerData.getVerbose62() + 1);
        	
        	if (playerData.getVerbose62() > 10)
            {
            	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type J - Generic (1)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
            }
        }
        else
        {
        	playerData.setVerbose62(playerData.getVerbose62() > 0 ? playerData.getVerbose62() - 1 : 0);
        }
	}
	
	@EventHandler
	public void onMoveD(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);	
		
		if (!playerData.canBeChecked())
		{
			return;
		}		
		
        double distance = Math.abs(UtilMove.getHorizontalDistanceSpeed(event.getTo(), event.getFrom()));
        double lastDistance = playerData.getLastDistance();
        playerData.setLastDistance(distance);

        double offest = Math.pow(2.0, 24.0);

        double gcd = UtilMath.gcd((long) (distance * offest), (long) (lastDistance * offest));
        double simple = gcd / offest;

        if (System.currentTimeMillis() - playerData.getLastFlag() > 5000)
        {
            playerData.setVerbose63(0);
        }

        if (simple > 0.0056 && playerData.getClientAirTicks() > 2 && !playerData.isClientOnGround() && Math.abs(System.currentTimeMillis() - playerData.getLastVelocityEvent()) > 2000)
        {
            playerData.setLastFlag(System.currentTimeMillis());
            playerData.setVerbose63(playerData.getVerbose63() + 1);
            
            if (playerData.getVerbose63() > 35)
            {
            	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type J - Generic (2)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
            }
        }
        else
        {
        	playerData.setVerbose63(playerData.getVerbose63() > 0 ? playerData.getVerbose63() - 1 : 0);
        }
	}
}