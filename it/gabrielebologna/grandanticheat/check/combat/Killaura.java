package it.gabrielebologna.grandanticheat.check.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.comphenix.packetwrapper.WrapperPlayClientEntityAction;
import com.comphenix.packetwrapper.WrapperPlayClientUseEntity;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerAction;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;
import it.gabrielebologna.grandanticheat.update.UpdateEvent;
import it.gabrielebologna.grandanticheat.utils.Distance;
import it.gabrielebologna.grandanticheat.utils.UtilCheat;
import it.gabrielebologna.grandanticheat.utils.UtilMath;
import it.gabrielebologna.grandanticheat.utils.UtilTime;
 
public class Killaura extends Check
{
    private PacketType[] playerPackets = {PacketType.Play.Client.POSITION, PacketType.Play.Client.LOOK, PacketType.Play.Client.FLYING, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.USE_ENTITY, PacketType.Play.Client.ENTITY_ACTION, PacketType.Play.Client.BLOCK_DIG, PacketType.Play.Client.BLOCK_PLACE, PacketType.Play.Client.ARM_ANIMATION};
   
    public Killaura()
    {
        super("Killaura");
        Check chick = this;
       
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(GrandAntiCheat.getPlugin(), playerPackets)
        {
            public void onPacketReceiving(final PacketEvent event)
            {
                if (!chick.isEnabled())
                {
                    return;
                }
               
                final PacketContainer packet = event.getPacket();
                final Player player = event.getPlayer();
                PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
                
        		if (!playerData.canBeChecked())
        		{
        			return;
        		}
        		
        		if (chick.isToggled("wall"))
        		{
        			if (packet.getType().equals(PacketType.Play.Client.USE_ENTITY))
        			{
        				WrapperPlayClientUseEntity wrapperPlayClientUseEntity = new WrapperPlayClientUseEntity(packet);   
        				
        				if (!wrapperPlayClientUseEntity.getType().equals(EntityUseAction.ATTACK))
        				{
        					return;
        				}
        				
    					LivingEntity targetEntity = (LivingEntity) wrapperPlayClientUseEntity.getTarget(event);
    					Player p = player;
    					Distance distance = new Distance(p.getLocation(), targetEntity.getLocation());
    					double x = distance.getXDifference();
    					double z = distance.getZDifference();
    					
    			        if (x != 0.0D && z != 0.0D)
    			        {
    			             if (distance.getYDifference() >= 0.6D)
    			             {
    			            	 
    			             }
    			             else
    			             {
    			                Location l = null;
    			                
    			                if (x <= 0.5D && z >= 1.0D)
    			                {
    			                   if (p.getLocation().getZ() > targetEntity.getLocation().getZ())
    			                   {
    			                      l = p.getLocation().clone().add(0.0D, 0.0D, -1.0D);
    			                   }
    			                   else
    			                   {
    			                      l = p.getLocation().clone().add(0.0D, 0.0D, 1.0D);
    			                   }
    			                }
    			                else if (z <= 0.5D && x >= 1.0D)
    			                {
    			                   if (p.getLocation().getX() > targetEntity.getLocation().getX())
    			                   {
    			                      l = p.getLocation().clone().add(-1.0D, 0.0D, 0.0D);
    			                   }
    			                   else
    			                   {
    			                      l = p.getLocation().clone().add(-1.0D, 0.0D, 0.0D);
    			                   }
    			                }

    			                boolean failed = false;
    			                
    			                if (l != null)
    			                {
    			                   failed = l.getBlock().getType().isSolid() && l.clone().add(0.0D, 1.0D, 0.0D).getBlock().getType().isSolid();
    			                }

    			                if (failed)
    			                {
    			                	GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type R - Wall", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
    			                }
    			             }
    			          }
    			          else
    			          {
    			        	  
    			          }
        			}
        		}
        		
        		if (chick.isToggled("auto-block"))
        		{
        			if (packet.getType().equals(PacketType.Play.Client.USE_ENTITY))
        			{
        				WrapperPlayClientUseEntity wrapperPlayClientUseEntity = new WrapperPlayClientUseEntity(packet);
        				
        				if (wrapperPlayClientUseEntity.getType().equals(EntityUseAction.ATTACK))
        				{
        					playerData.setTicks8(0);
        				}
        			}
        			
        			if (packet.getType().equals(PacketType.Play.Client.FLYING) || packet.getType().equals(PacketType.Play.Client.POSITION_LOOK) || packet.getType().equals(PacketType.Play.Client.POSITION) || packet.getType().equals(PacketType.Play.Client.LOOK))
        			{
        				playerData.setTicks8(playerData.getTicks8() + 1);
        			}
        			
        			playerData.setWasWasSprinting(playerData.isWasSprinting());
        			
        			if (packet.getType().equals(PacketType.Play.Client.USE_ENTITY))
        			{
        				playerData.setTicks7(0);
        				
        			}
        			else if (packet.getType().equals(PacketType.Play.Client.ENTITY_ACTION))
        			{
        				WrapperPlayClientEntityAction wrapperPlayClientEntityAction = new WrapperPlayClientEntityAction(packet);
        				
        				if (wrapperPlayClientEntityAction.getAction().equals(PlayerAction.START_SPRINTING))
        				{
        					playerData.setWasSprinting(true);
        				}
        				else if (wrapperPlayClientEntityAction.getAction().equals(PlayerAction.STOP_SPRINTING))
        				{
        					playerData.setWasSprinting(false);
        				}
        			}
        			else if (packet.getType().equals(PacketType.Play.Client.FLYING) || packet.getType().equals(PacketType.Play.Client.LOOK))
        			{
        				playerData.setTicks7(playerData.getTicks7() + 1);
        			}
        			
        			playerData.setLastPacket(packet);
        		}
 
                if (packet.getType().equals(PacketType.Play.Client.FLYING) || packet.getType().equals(PacketType.Play.Client.POSITION) || packet.getType().equals(PacketType.Play.Client.POSITION_LOOK) || packet.getType().equals(PacketType.Play.Client.LOOK))
                {
                    playerData.setrHitTicks(playerData.getrHitTicks() + 1);
                    playerData.setTicks1(playerData.getTicks1() + 1);
                }
                
                if (packet.getType().equals(PacketType.Play.Client.USE_ENTITY))
                {
                	WrapperPlayClientUseEntity wrapperPlayClientUseEntity = new WrapperPlayClientUseEntity(packet);
                	
                	if (wrapperPlayClientUseEntity.getType().equals(EntityUseAction.ATTACK))
                	{
                		playerData.setTicks1(0);
                	}
                }
                
                if (packet.getType().equals(PacketType.Play.Client.FLYING) || packet.getType().equals(PacketType.Play.Client.POSITION))
                {
                	playerData.setTotalPacketsT(playerData.getTotalPacketsT() + 1);
                }
                
                if (packet.getType().equals(PacketType.Play.Client.LOOK) || packet.getType().equals(PacketType.Play.Client.POSITION_LOOK))
                {
                	playerData.setLookPacketsT(playerData.getLookPacketsT() + 1);
                	playerData.setTotalPacketsT(playerData.getTotalPacketsT() + 1);
                	
                    if (chick.isToggled("bad-aim") && (System.currentTimeMillis() - playerData.getLastEntityUse()) < 350L)
                    {
                    	try
                    	{
                            float pitch = packet.getFloat().read(1);
                            float lastPitch = playerData.getLastPitch();
                            playerData.setLastPitch(pitch);
                            float pitchDiff = Math.abs(pitch - lastPitch);
                            float lastPitchDiff = playerData.getLastPitchDiff();
                            playerData.setLastPitchDiff(pitchDiff);
                            float pitchOffset = Math.abs(pitchDiff - lastPitchDiff);
                            float lastPitchOffset = playerData.getLastPitchOffset();
                            playerData.setLastPitchOffset(pitchOffset);;
                            double pitchGcd = (double)((float) UtilMath.gcd((long)pitchDiff, (long)lastPitchDiff) % pitchOffset);
                            
                            if (!String.valueOf(pitchDiff).equalsIgnoreCase("NaN"))
                            {
                                boolean isZoom = (double)(UtilMath.gcd((long)pitchOffset, (long)lastPitchOffset) % 180L) < 0.1D;
                                
                                if ((double)pitchOffset > 1.0E-4D && (double)pitchOffset < 0.1D && pitchGcd > 0.0D && pitchGcd < 0.1D && !isZoom && pitchGcd > 0.07D)
                                {
                                	GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type M - Bad aim (1)", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing() + ", pitch offset: " + pitchOffset + ", pitch gcd: " + pitchGcd, true);
                                }
                            }
                    	}
                    	catch (Exception ex)
                    	{
                    		
                    	}
                    }
                    
                    if (chick.isToggled("bad-aim") && (System.currentTimeMillis() - playerData.getLastEntityUse()) < 350L)
                    {
                    	try
                    	{
                            float yaw = packet.getFloat().read(0);
                            float lastYaw = playerData.getLastYaw();
                            playerData.setLastYaw(yaw);
                            float yawDiff = Math.abs(yaw - lastYaw);
                            float lastYawDiff = playerData.getLastYawDiff();
                            playerData.setLastYawDiff(yawDiff);
                            float yawOffset = Math.abs(yawDiff - lastYawDiff);
                            float lastYawOffset = playerData.getLastYawOffset();
                            playerData.setLastYawOffset(yawOffset);
                            double yawGcd = (double)((float)UtilMath.gcd((long)yawDiff, (long)lastYawDiff) % yawOffset);
                            
                            if (!String.valueOf(yawDiff).equalsIgnoreCase("NaN"))
                            {
                                boolean isZoom = (double)(UtilMath.gcd((long)yawOffset, (long)lastYawOffset) % 360L) < 0.1D;
                                
                                if ((double)yawOffset > 1.0E-4D && (double)yawOffset < 0.06D && yawGcd > 0.0D && yawGcd < 0.1D && !isZoom)
                                {
                                	GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type M - Bad aim (2)", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing() + ", yaw offset: " + yawOffset + ", yaw gcd: " + yawGcd, true);
                                }
                            }
                    	}
                    	catch (Exception ex)
                    	{
                    		
                    	}
                    }
                }
               
                if (packet.getType().equals(PacketType.Play.Client.USE_ENTITY))
                {
                    playerData.setrHitTicks(0);
                    WrapperPlayClientUseEntity wrapperPlayClientUseEntity = new WrapperPlayClientUseEntity(packet);
                   
                    if (!wrapperPlayClientUseEntity.getType().equals(EntityUseAction.ATTACK))
                    {
                        return;
                    }
                   
                    if (!playerData.canBeChecked())
                    {
                        return;                    
                    }
                   
                    LivingEntity packetEntity = (LivingEntity) wrapperPlayClientUseEntity.getTarget(event);
                    playerData.setPostAttacked(true);
                    playerData.setPostTime(System.currentTimeMillis());
                    
                    if (chick.isToggled("fast-rotations"))
                    {
                		List<Double> fastTimings = (List<Double>) playerData.getYawTimings();
                		
                		if (fastTimings != null)
                		{
                    		Iterator<Double> it = fastTimings.iterator();

                    		while (it.hasNext())
                    		{
                    			double val = it.next();
                    			
                    			if (System.currentTimeMillis() - val > 250)
                    			{
                    				it.remove();
                    			}
                    		}

                    		if (fastTimings.size() > 4)
                    		{
                    			GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type K - Fast rotations", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing() + ", size: " + fastTimings.size(), true);
                    		}
                		}
                    }
                    
                    if (chick.isToggled("invalid-diff"))
                    {
                    	double diff = (double) playerData.getTotalPacketsT() / ((double) playerData.getLookPacketsT() + 0.5D);
                    	
                    	if (diff > 0.6D && diff <= 2.0D && (diff < 0.8D || diff >= 0.87D))
                    	{
                    		if (diff == playerData.getLastDiff())
                    		{
                    			playerData.setVerbose17(playerData.getVerbose17() + 1);
                    			
                    			if (playerData.getVerbose17() > 4)
                        		{
                    				GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type O - Invalid diff", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing() + ", diff: " + diff, false);
                        		}
                    		}
                    		else
                    		{
                    			playerData.setVerbose17(0);
                    		}
                    	}
                    	
                    	playerData.setLastDiff(diff);
                    	playerData.setLookPacketsT(0);
                    	playerData.setTotalPacketsT(0);
                    }
                    
                    if (chick.isToggled("hit-miss-ratio"))
                    {
                        int Count = 0;
                        long Time = System.currentTimeMillis();
                        
                        if (playerData.getAuraTicksTime() > 0)
                        {
                            Count = playerData.getAuraTicksCount();
                            Time = playerData.getAuraTicksTime();
                        }
                        
                        double OffsetXZ = UtilCheat.getAimbotoffset(player.getLocation(), player.getEyeHeight(), packetEntity);
                        double LimitOffset = 200.0;
                        
                        if (player.getVelocity().length() > 0.08)
                        {
                            LimitOffset += 200.0;
                        }
                        
                        int Ping = playerData.getPing();
                        
                        if (Ping >= 100 && Ping < 200)
                        {
                            LimitOffset += 50.0;
                        }
                        else if (Ping >= 200 && Ping < 250)
                        {
                            LimitOffset += 75.0;
                        }
                        else if (Ping >= 250 && Ping < 300)
                        {
                            LimitOffset += 150.0;
                        }
                        else if (Ping >= 300 && Ping < 350)
                        {
                            LimitOffset += 300.0;
                        }
                        else if (Ping >= 350 && Ping < 400)
                        {
                            LimitOffset += 400.0;
                        }
                        else if (Ping > 400)
                        {
                        	return;
                        }
                        
                        if (OffsetXZ > LimitOffset * 4.0)
                        {
                            Count += 12;
                        }
                        else if (OffsetXZ > LimitOffset * 3.0)
                        {
                            Count += 10;
                        }
                        else if (OffsetXZ > LimitOffset * 2.0)
                        {
                            Count += 8;
                        }
                        else if (OffsetXZ > LimitOffset)
                        {
                            Count += 4;
                        }
                        
                        if (playerData.getAuraTicksTime() > 0 && UtilTime.elapsed(Time, 60000L))
                        {
                            Count = 0;
                            Time = UtilTime.nowlong();
                        }
                        
                        if (Count >= 16)
                        {
                        	GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type G - Hit miss ratio", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing() + ", offset: " + OffsetXZ + ", limit: " + LimitOffset, true);
                            Count = 0;
                        }
                        
                        playerData.setAuraTicksCount(Count);
                        playerData.setAuraTicksTime(Time);
                    }
                    
                    if (chick.isToggled("perfect-rotations"))
                    {
                    	LivingEntity livingEntity = null;
                    	
                    	for (Entity entity: player.getWorld().getEntities())
                    	{
                    		if (entity instanceof LivingEntity)
                    		{
                    			if (((LivingEntity) entity).getEntityId() == wrapperPlayClientUseEntity.getTargetID())
                        		{
                        			livingEntity = (LivingEntity) entity;
                        			break;
                        		}
                    		}
                    	}
                    	
                    	float[] rotations = UtilCheat.getRotations(player, livingEntity);
                    	
                    	if (player.getLocation().getYaw() == rotations[0] && player.getLocation().getPitch() == rotations[1])
                    	{
                    		GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type J - Perfect rotations", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing() + ", yaw: " + rotations[0] + ", pitch: " + rotations[1], false);
                    	}
                    }
                    
                    if (chick.isToggled("aimbot"))        	
                    {
                        Location from = null;
                        Location to = player.getLocation();
                        
                        if (playerData.getLastLocation() != null)
                        {
                            from = playerData.getLastLocation();
                        }
                        
                        playerData.setLastLocation(player.getLocation());
                        double Count = 0;
                        long Time = System.currentTimeMillis();
                        double LastDifference = -111111.0;
                        
                        if (playerData.getDifferences().size() > 0)
                        {
                            LastDifference = playerData.getDifferences().get(playerData.getDifferences().size() - 1);
                        }
                        
                        if (playerData.getAimbotTicksTime() > 0)
                        {
                            Count = playerData.getAimbotTicksCount();
                            Time = playerData.getAimbotTicksTime();
                        }
                        
                        if (from == null || (to.getX() == from.getX() && to.getZ() == from.getZ()))
                        {
                        	
                        }
                        else
                        {
                            double Difference = Math.abs(to.getYaw() - from.getYaw());
                            
                            if (Difference != 0.0)
                            {
                                if (Difference > 2.4)
                                {
                                    double diff = Math.abs(LastDifference - Difference);
                                    
                                    if (packetEntity.getVelocity().length() < 0.1)
                                    {
                                        if (diff < 1.4)
                                        {
                                            Count += 1;
                                        }
                                        else
                                        {
                                            Count = 0;
                                        }
                                    }
                                    else
                                    {
                                        if (diff < 1.8)
                                        {
                                            Count += 1;
                                        }
                                        else
                                        {
                                            Count = 0;
                                        }
                                    }
                                }
                                
                                playerData.getDifferences().add(Difference);
                                
                                if (playerData.getAimbotTicksTime() > 0 && UtilTime.elapsed(Time, 5000L))
                                {
                                    Count = 0;
                                    Time = UtilTime.nowlong();
                                }
                                
                                if (Count > 5)
                                {
                                	GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type H - Aimbot", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing() + ", diff: " + Difference, true);
                                    Count = 0;
                                }
                                
                                playerData.setAimbotTicksCount((int) Math.round(Count));
                                playerData.setAimbotTicksTime(Time);
                            }
                        }              
                    }
                    
                    if (chick.isToggled("bad-angle"))
                    {
                        double dir = UtilMath.getDirection(player.getLocation(), packetEntity.getLocation());
                        double dist = UtilMath.getDistanceBetweenAngles360((double)player.getLocation().getYaw(), dir);
                        double range = player.getLocation().distance(packetEntity.getLocation());
                        double max = 32.0D;
                        max += (double) UtilMath.pingFormula((long)(playerData.getPing() + 2));
                        
                        if (dist > max && range > 1.0D)
                        {
                        	playerData.setVerbose4(playerData.getVerbose4() + 1);
                        	
                        	if (playerData.getVerbose4() > 6) // 4
                            {
                        		GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type L - Bad angle", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing() + ", dist: " + dist + ", max: " + max + ", range: " + range, false);
                            }
                        }
                        else
                        {
                            playerData.setVerbose4(0);
                        }
                    }
                }
                else
                {
                    playerData.setLastFlying(System.currentTimeMillis());
                    
                    if (playerData.isPostAttacked() && chick.isToggled("timings"))
                    {
                        playerData.setPostAttacked(false);
                        
                        if (System.currentTimeMillis() - playerData.getPostTime() > 50)
                        {
                            playerData.setPostPingCheck(playerData.getPostPingCheck() + 1);
                            
                            if (playerData.getPostPingCheck() > 1)
                            {      	
                                GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type E - Unusual timing pattern", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing(), true);
                            }
                        }
                        else
                        {
                            playerData.setPostPingCheck(0);
                        }
                    }
                }
            }
        });
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
        
        if (this.isToggled("bad-rotations") && (System.currentTimeMillis() - playerData.getLastEntityUse()) < 350L)
        {
        	if (event.getTo().getPitch() - event.getFrom().getPitch() > 0.1 && player.getLocation().getPitch() == Math.round(player.getLocation().getPitch()))
        	{
        		GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type T - Bad rotations (1)", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing(), true);
        	}
        	
        	if (event.getTo().getYaw() - event.getFrom().getYaw() > 0.1 && player.getLocation().getYaw() == Math.round(player.getLocation().getYaw()))
        	{
        		GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type T - Bad rotations (2)", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing(), true);
        	}
        	
        	float pitch = event.getTo().getPitch() - event.getFrom().getPitch();
        	
            if ((Math.round(pitch) == pitch || pitch % 1 == 0) && pitch != 0)
            {
            	playerData.getVerbose66().flag(1, 1000L);
            	
                if (playerData.getVerbose66().getVerbose() > 3)
                {
                	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type T - Bad rotations (3)", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing(), true);
                }
            }
            
            pitch = Math.abs(event.getTo().getYaw() - event.getFrom().getYaw()) % 360.0F;
            
            if (pitch < 0.08F && pitch > 0.0F)
            {
            	playerData.setVerbose67(playerData.getVerbose67() + 1);
            	
                if (playerData.getVerbose67() > 2)
                {
                	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type T - Bad rotations (4)", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing(), true);
                }
            }
            else
            {
            	playerData.setVerbose67(0);
            }
            
            double yawDiff = event.getTo().getYaw() - event.getFrom().getYaw();
            playerData.getDiffs().add((long) yawDiff);
            
            if (playerData.getDiffs().size() == 5)
            {
                double deviation = UtilMath.getStandardDeviation(playerData.getDiffs().stream().mapToLong(l -> l).toArray());

                if ((deviation > 100.0D) && yawDiff > 2.0D)
                {
                	playerData.setVerbose68(playerData.getVerbose68() + 1);
                	
                    if (playerData.getVerbose68() > 2)
                    {
                    	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type T - Bad rotations (5)", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing(), true);
                    }
                }
                else
                {
                	playerData.setVerbose68(0);
                }

                playerData.getDiffs().clear();
            }
            
            float diff = Math.abs(event.getTo().getYaw() - event.getFrom().getYaw()) % 360F;
            float lastDiff = playerData.getDiff1();
            playerData.setDiff1(diff);

            double gcd = UtilMath.getGcd((long) (diff * Math.pow(2, 24)), (long) (lastDiff * Math.pow(2, 24)));
            double val = gcd / Math.pow(2, 24);

            if (val > 0.0D && val < 0.0015D)
            {
                if (playerData.getVerbose69() > 4)
                {
                	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type T - Bad rotations (6)", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing(), true);
                }
            }
            else
            {
            	playerData.setVerbose69(0);
            }
            
            diff = Math.abs(event.getTo().getPitch() - event.getFrom().getPitch()) % 180F;
            lastDiff = playerData.getDiff2();
            playerData.setDiff2(diff);

            gcd = UtilMath.getGcd((long) (diff * Math.pow(2, 24)), (long) (lastDiff * Math.pow(2, 24)));
            val = gcd / Math.pow(2, 24);

            if (val > 0.0D && val < 0.08D)
            {
                playerData.getVerbose70().flag(1, 1000L);
                
                if (playerData.getVerbose70().getVerbose() > 4)
                {
                	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type T - Bad rotations (7)", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing(), true);
                }
            }
            else
            {
            	playerData.getVerbose70().setVerbose(0);
            }
        }
        
        if (this.isToggled("heuristics") && (System.currentTimeMillis() - playerData.getLastEntityUse()) < 350L)
        {
        	Distance distance = new Distance(event);
            float yawDiff = (float) UtilMath.round((double)Math.abs(UtilMath.clamp180(distance.getFrom().getYaw() - distance.getTo().getYaw())), 3);
            
            if (yawDiff > 0.0F)
            {
            	int vb = 0;
            	
            	if (playerData.getSamples().containsKey(yawDiff))
            	{
            		vb = playerData.getSamples().get(yawDiff);
            		playerData.getSamples().put(yawDiff, vb + 1);
            		playerData.setPatternVerbose(playerData.getPatternVerbose() + 1);
            	}
            	else
            	{
            		playerData.getSamples().put(yawDiff, 1);
            		
            		if (System.currentTimeMillis() - playerData.getPatternMS() >= 5500L)
            		{
            			vb = playerData.getPatternVerbose();
            			int samples = playerData.getSamples().size();
            			playerData.setPatternVerbose(0);
            			playerData.getSamples().clear();
            			playerData.resetPatternMS();
            			int samplesNeeded = 55;
            			
                		if (vb == 0 && samples > samplesNeeded)
                		{
                			GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type S - Heuristics", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing(), true);
                		}
            		}
            	}
            }
        }
 
        if (this.isToggled("invalid-dist") && (System.currentTimeMillis() - playerData.getLastEntityUse()) < 350L)
        {
            final double yawTo = event.getTo().getYaw() % 1.0f;
            final double yawFrom = event.getFrom().getYaw() % 1.0f;
            final double diff = Math.abs(yawTo - yawFrom);
            final double invalidDist = Math.abs(diff / playerData.getLastRDiff());
            
            if (playerData.getrHitTicks() < 3)
            {
                if (invalidDist != 1.0 && invalidDist != 0.0 && invalidDist != Double.NaN && invalidDist != Double.NEGATIVE_INFINITY && invalidDist != Double.POSITIVE_INFINITY)
                {
                    playerData.setInvalidrotation(playerData.getInvalidrotation() + 1);
                }
                else
                {
                    playerData.setInvalidrotation(0);
                }
                
                if (playerData.getInvalidrotation() > 100.0)
                {
                    playerData.setIrverbose(playerData.getIrverbose() + 1);
                    
                    if (playerData.getIrverbose() > 18) // 15
                    {
                        GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type C - Invalid distance", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing(), false);
                    }
                }
            }
            else if (playerData.getrHitTicks() > 20)
            {
                playerData.setInvalidrotation(0);
            }
            
            playerData.setLastRDiff(diff);
        }
 
        if (this.isToggled("silent-rotation") && (System.currentTimeMillis() - playerData.getLastEntityUse()) < 350L)
        {
            double xDiff = event.getTo().getX() - event.getFrom().getX();
            double zDiff = event.getTo().getZ() - event.getFrom().getZ();
            float yaw = UtilMath.wrapAngleTo180_float(event.getFrom().getYaw() % 360);
            boolean known = false;
            known = UtilMath.isKnownDirection(xDiff, zDiff, yaw, Math.sqrt(xDiff * xDiff + zDiff * zDiff));    
            double speed = Math.abs(event.getTo().getYaw() - event.getFrom().getYaw());
           
            if (!known && speed > 0 && speed < 15)
            {
                if (!UtilMath.isKnownWithSpeedDirection(xDiff, zDiff, yaw, speed, (int) (event.getTo().getYaw() - event.getFrom().getYaw())))
                {
                	playerData.setVerbose1(playerData.getVerbose1() + 1);
                }
                else
                {
                	playerData.setVerbose1(playerData.getVerbose1() - 2);
                }
            }
            else
            {
                playerData.setVerbose1(0);
            }
           
            if (playerData.getVerbose1() < 0)
            {
            	playerData.setVerbose1(0);
            }
     
            if (playerData.isWasGround() != playerData.isClientOnGround())
            {
            	playerData.setVerbose1(playerData.getVerbose1() / 1.2);
            }
     
            if (playerData.getVerbose1() > (playerData.isClientOnGround() ? 2 : 4))
            {
                long lV = playerData.getVerbose2();
                playerData.setVerbose2(System.currentTimeMillis());
               
                if (System.currentTimeMillis() - lV > 2500)
                {
                    playerData.setVerbose1(playerData.getVerbose1() - 4);
                }
                else
                {
                    playerData.setVerbose1(0);
                    GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type B - Silent rotation", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing() + ", speed: " + speed, false);
                }
            }
           
            playerData.setWasGround(playerData.isClientOnGround());
        }
 
        if (this.isToggled("fast-rotations"))
        {
        	double yawDist = Math.abs(event.getTo().getYaw() - event.getFrom().getYaw());
            double pitchDist = Math.abs(event.getTo().getPitch() - event.getFrom().getPitch());

            if ((yawDist % 1 == 0 && yawDist > 0) || (pitchDist % 1 == 0 && pitchDist > 0))
            {
            	playerData.setVerbose3(playerData.getVerbose3() + 1);
            	
                if (playerData.getVerbose3() > 5)
                {
                	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type K - Fast rotations", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing() + ", yaw dist: " + yawDist + ", pitch dist: " + pitchDist, true);
                }
            }
            else
            {
                playerData.setVerbose3(0);
            }
            
            float diff = Math.abs(event.getTo().getYaw() - event.getFrom().getYaw());

            float zoom = event.getFrom().getYaw() * 0.8f;
            float dist = Math.abs(zoom - diff);

            int length = (diff + "").length();

            if (playerData.getTicks1() < 7)
            {
                if (length > 5 && diff > 0 && diff < 12 && dist > 0.1 && dist < 63)
                {
                	playerData.setVerbose27(playerData.getVerbose27() + 1);
                	
                    if (playerData.getVerbose27() > 37)
                    {
                    	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type K - Fast rotations", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing() + ", length: " + length + ", diff: " + diff + ", dist: " + dist, true);
                    }
                }
                else
                {
                    playerData.setVerbose27(0);
                }
            }
        }
        
        if (this.isToggled("fast-rotations"))
        {
    		double yawDiff = Math.abs(Math.abs(event.getTo().getYaw()) - Math.abs(event.getFrom().getYaw()));

    		if (yawDiff > 30)
    		{
        		List<Double> fastTimings = (List<Double>) playerData.getYawTimings();
        		
        		if (fastTimings == null)
        		{
        			fastTimings = new ArrayList<>();
        		}
        		
        		fastTimings.add((double) System.currentTimeMillis());
        		Iterator<Double> it = fastTimings.iterator();

        		while (it.hasNext())
        		{
        			double val = it.next();
        			
        			if (System.currentTimeMillis() - val > 250)
        			{
        				it.remove();
        			}
        		}
    		}
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
    		
    		if (playerData.getEntityUses() > 14 && this.isToggled("fast-attacking"))
    		{
    			GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type F - Fast attacking", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing() + ", uses: " + playerData.getEntityUses(), false);
    		}
    	}
    }
    
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event)
    {
        if (!this.isToggled("multiple-entities"))
        {
        	return;
        }
        
        if (!(event.getDamager() instanceof Player))
        {
        	return;
        }
        
        Player player = (Player) event.getDamager();  
        PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
        
		if (!playerData.canBeChecked())
		{
			return;
		}
        
        LivingEntity damagedEntity = (LivingEntity) event.getEntity();
        
        if (playerData.getLastEntityAttacked() != null && playerData.getLastEntityAttacked() != damagedEntity)
        {
        	long time = playerData.getLastEntityAttack();
        	
        	if (System.currentTimeMillis() - time < 6L)
        	{
        		GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type I - Multiple entities", "TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", ping: " + playerData.getPing(), true);
        	}
        }
    }
    
	@Override
    public void setup()
    {
        if (GrandAntiCheat.isDebugMode())
        {
        	this.getSettings().addDefault("entity-in-sight", true);
            this.getSettings().addDefault("silent-rotation", true);
            this.getSettings().addDefault("invalid-dist", true);
            this.getSettings().addDefault("invalid-hit-difference", true);
            this.getSettings().addDefault("timings", true);
            this.getSettings().addDefault("fast-attacking", true);
            this.getSettings().addDefault("hit-miss-ratio", true);
            this.getSettings().addDefault("aimbot", true);
            this.getSettings().addDefault("multiple-entities", true);
            this.getSettings().addDefault("perfect-rotations", true);
            this.getSettings().addDefault("fast-rotations", true);
            this.getSettings().addDefault("bad-angle", true);
            this.getSettings().addDefault("bad-aim", true);
            this.getSettings().addDefault("invalid-diff", true);
            this.getSettings().addDefault("auto-block", true);
            this.getSettings().addDefault("raycast", true);
            this.getSettings().addDefault("wall", true);
            this.getSettings().addDefault("heuristics", true);
            this.getSettings().addDefault("bad-rotations", true);
        }
    }
}