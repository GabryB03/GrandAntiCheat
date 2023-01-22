package it.gabrielebologna.grandanticheat.check.player;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayClientAbilities;
import com.comphenix.packetwrapper.WrapperPlayClientCustomPayload;
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
import it.gabrielebologna.grandanticheat.utils.UtilCheat;
import it.gabrielebologna.grandanticheat.utils.UtilPlayer;

public class InvalidPacket extends Check
{
	private PacketType[] playerPackets = {PacketType.Play.Client.ABILITIES, PacketType.Play.Client.CUSTOM_PAYLOAD, PacketType.Play.Client.LOOK, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.POSITION, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.FLYING, PacketType.Play.Client.USE_ENTITY, PacketType.Play.Client.ARM_ANIMATION, PacketType.Play.Client.ENTITY_ACTION, PacketType.Play.Client.BLOCK_PLACE, PacketType.Play.Client.HELD_ITEM_SLOT, PacketType.Play.Client.KEEP_ALIVE};
	
	public InvalidPacket()
	{
		super("InvalidPacket");

		Check chick = this;
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(GrandAntiCheat.getPlugin(), playerPackets)
        {
            public void onPacketReceiving(final PacketEvent event)
            {
                final PacketContainer packet = event.getPacket();
                final Player player = event.getPlayer();               
                PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
                
                if (!packet.getType().equals(PacketType.Play.Client.CUSTOM_PAYLOAD))
                {
                	if (!playerData.canBeChecked())
                    {
                    	return;
                    }
                }
                
                if (packet.getType().equals(PacketType.Play.Client.ABILITIES))
                {
                	if (chick.isToggled("abilities"))
                	{
                		WrapperPlayClientAbilities wrapperPlayClientAbilities = new WrapperPlayClientAbilities(packet);
                    	
                    	if ((wrapperPlayClientAbilities.canFly() != player.getAllowFlight() || wrapperPlayClientAbilities.isFlying() != player.isFlying()) && !player.getGameMode().equals(GameMode.CREATIVE))
                    	{
                    		wrapperPlayClientAbilities.setCanFly(false);
                    		wrapperPlayClientAbilities.setFlying(false);
                    		GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type A - Abilities", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), false);
                    	}
                	}
                }
                
                /*if (packet.getType().equals(PacketType.Play.Client.POSITION))
                {
                	WrapperPlayClientPosition wrapperPlayClientPosition = new WrapperPlayClientPosition(event.getPacket());
                	
                	if ((playerData.getDistanceFromGround() >= 2.0D && !playerData.isClientOnGround()) && wrapperPlayClientPosition.getOnGround())
                	{
                		GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type H - On ground", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
                	}
                }
                else if (packet.getType().equals(PacketType.Play.Client.POSITION_LOOK))
                {
                	WrapperPlayClientPositionLook wrapperPlayClientPositionLook = new WrapperPlayClientPositionLook(event.getPacket());
                	
                	if ((playerData.getDistanceFromGround() >= 2.0D && !playerData.isClientOnGround()) && wrapperPlayClientPositionLook.getOnGround())
                	{
                		GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type H - On ground", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
                	}
                }
                else if (packet.getType().equals(PacketType.Play.Client.FLYING))
                {
                	WrapperPlayClientFlying wrapperPlayClientFlying = new WrapperPlayClientFlying(event.getPacket());
                	
                	if ((playerData.getDistanceFromGround() >= 2.0D && !playerData.isClientOnGround()) && wrapperPlayClientFlying.getOnGround())
                	{
                		GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type H - On ground", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
                	}
                }*/
                
                if (packet.getType().equals(PacketType.Play.Client.CUSTOM_PAYLOAD))
                {
                	if (chick.isToggled("payload"))
                	{
                    	WrapperPlayClientCustomPayload wrapperPlayClientCustomPayload = new WrapperPlayClientCustomPayload(packet);
                    	String information = new String(wrapperPlayClientCustomPayload.getContents());
                    	
                    	if (information.equals("MC|Brand"))
                    	{
                    		return;
                    	}
                    	
                    	if (information.equals("MC|Sign"))
                    	{
                    		return;
                    	}
                    	
                    	if (information.equals("MC|BSign"))
                    	{
                    		return;
                    	}
                    	
                    	if (information.endsWith("vanilla") && information.length() <= 10)
                    	{
                    		return;
                    	}
                    	
                    	if (information.toLowerCase().contains("forge") || information.toLowerCase().contains("laby"))
                    	{
                    		return;
                    	}
                    	
                    	GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type B - Payload", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", payload info: " + information, true);
                	}
                }
                
                if ((packet.getType().equals(PacketType.Play.Client.POSITION) || packet.getType().equals(PacketType.Play.Client.POSITION_LOOK) || packet.getType().equals(PacketType.Play.Client.FLYING) || packet.getType().equals(PacketType.Play.Client.LOOK)))
                {
                	playerData.setTicks(playerData.getTicks() + 1);
                	playerData.setTicks2(playerData.getTicks() + 1);
                	playerData.setTicks3(playerData.getTicks() + 1);
                	playerData.setLastWasArm(false);
                	playerData.setSent(false);
                	playerData.setLastPlace(System.currentTimeMillis());
                	
                	if (chick.isToggled("position") && (packet.getType().equals(PacketType.Play.Client.POSITION) || packet.getType().equals(PacketType.Play.Client.POSITION_LOOK)))
                	{
                    	double posX = packet.getDoubles().read(0);
                    	double posY = packet.getDoubles().read(1);
                    	double posZ = packet.getDoubles().read(2);
                    	
                    	if (posY != 127.0D)
                    	{
                        	double[] strangeValues = {Double.MAX_VALUE, Double.MIN_NORMAL, Double.MIN_VALUE, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Float.MAX_EXPONENT, Float.MAX_VALUE, Float.MIN_VALUE, Float.NaN, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, Integer.MAX_VALUE, Integer.MIN_VALUE, Byte.MAX_VALUE, Byte.MIN_VALUE, Short.MAX_VALUE, Short.MIN_VALUE};
                        	
                        	for (double d: strangeValues)
                        	{
                        		if (posX == d || posY == d || posZ == d)
                        		{
                            		GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type C - Position", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", x: " + posX + ", y: " + posY + ", z: " + posZ, true);
                        		}
                        	}	
                    	}
                	}
                	
                	if ((packet.getType().equals(PacketType.Play.Client.POSITION_LOOK) || packet.getType().equals(PacketType.Play.Client.LOOK)) && chick.isToggled("look"))
                	{
                    	float pitch = packet.getFloat().read(1);
                    	
                    	if (pitch < -90.0F || pitch > 90.0F)
                    	{
                    		GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type D - Look", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", pitch: " + pitch, true);
                    	}
                    	
                    	float diff = playerData.getYawDelta() % 360.0F;
                    	
                    	if (diff > 0.1F && ((float) Math.round(diff)) == diff)
                    	{
                    		if (diff == playerData.getSuspiciousYaw())
                    		{
                    			playerData.setVerbose12(playerData.getVerbose12() + 1);
                    			
                    			if (playerData.getVerbose12() > 1)
                    			{
                    				GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type D - Look", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", yaw diff: " + diff + ", suspicious yaw: " + playerData.getSuspiciousYaw(), true);
                    			}
                    		}
                    		else
                    		{
                    			playerData.setVerbose12(0);
                    		}
                    		
                    		playerData.setSuspiciousYaw((float) Math.round(diff));
                    	}
                    	else
                    	{
                    		playerData.setSuspiciousYaw(0.0F);
                    	}
                	}
                	
                	if (packet.getType().equals(PacketType.Play.Client.POSITION_LOOK) || packet.getType().equals(PacketType.Play.Client.LOOK))
                	{
                		playerData.setLastLook(System.currentTimeMillis());
                	}
                	
                	if (chick.isToggled("liquid"))
                	{
                        boolean ground = event.getPacket().getBooleans().read(0);
                        boolean onGround = UtilPlayer.hasGroundDoorEx(player);
                        boolean inLiquid = UtilPlayer.isNearLiquid(player) || UtilPlayer.isNearLiquidOffset(player);
                        
                        if (!ground && onGround && !inLiquid && playerData.getPing() < 800 && !UtilCheat.isInWeb(player) && !UtilPlayer.isInWebOffset(player))
                        {
                        	playerData.setVerbose7(playerData.getVerbose7() + 1);
                        	
                            if (playerData.getVerbose7() > 4)
                            {
                            	GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type F - Liquid", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), false);
                            }
                        }
                        else
                        {
                            playerData.setVerbose7(0);
                        }
                	}
                }
                
                if (packet.getType().equals(PacketType.Play.Client.USE_ENTITY))
                {
                	if (chick.isToggled("attack"))
                	{
                		WrapperPlayClientUseEntity wrapperPlayClientUseEntity = new WrapperPlayClientUseEntity(packet);
                		
                		if (wrapperPlayClientUseEntity.getType().equals(EntityUseAction.ATTACK))    			
                		{
                			int ticks = playerData.getTicks();
                			playerData.setTicks(0);
                			
                			if (ticks < 1)
                			{
                				playerData.setVerbose8(playerData.getVerbose8() + 1);
                				
                				if (playerData.getVerbose8() > 2)
                				{
                					GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type G - Attack (1)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
                				}
                			}
                			else
                			{
                				playerData.setVerbose8(0);
                			}
                			
                			if (!playerData.isLastWasArm())
                			{
                				playerData.setVerbose9(playerData.getVerbose9() + 1);
                				
                				if (playerData.getVerbose9() > 1)
                				{
                					GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type G - Attack (2)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
                				}
                			}
                			else
                			{
                				playerData.setVerbose9(0);
                			}
                		}
                		
                        if (player.getLocation().getPitch() == (float) Math.round(player.getLocation().getPitch()) && System.currentTimeMillis() - playerData.getLastLook() < 500L)
                        {
                        	playerData.setVerbose10(playerData.getVerbose10() + 1);
                        	 
                            if (playerData.getVerbose10() > 2)
                            {
                            	GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type G - Attack (3)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
                            }
                        }
                        else
                        {
                            playerData.setVerbose10(0);
                        }
                	}
                }
                
                if (packet.getType().equals(PacketType.Play.Client.ARM_ANIMATION))
                {
                	playerData.setLastWasArm(true);
                	
                	if (chick.isToggled("swing"))
                	{
                		int ticks = playerData.getTicks3();
                		
                		if (ticks < 1)
                		{
                			playerData.setVerbose15(playerData.getVerbose15() + 1);
                			
                			if (playerData.getVerbose15() > 2)
                			{
                				GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type K - Swing", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
                			}
                		}
                		else
                		{
                			playerData.setVerbose15(0);
                		}
                	}
                }
                
                if (packet.getType().equals(PacketType.Play.Client.ENTITY_ACTION))                	
                {
                	if (chick.isToggled("sprint"))
                	{
                		WrapperPlayClientEntityAction wrapperPlayClientEntityAction = new WrapperPlayClientEntityAction(packet);
                    	
                    	if (wrapperPlayClientEntityAction.getAction().equals(PlayerAction.START_SPRINTING) || wrapperPlayClientEntityAction.getAction().equals(PlayerAction.STOP_SPRINTING))
                    	{
                    		if (playerData.isSent())
                    		{
                    			playerData.setVerbose11(playerData.getVerbose11() + 1);
                    			
                    			if (playerData.getVerbose11() > 2)
                    			{
                    				GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type H - Sprint", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
                    			}
                    		}
                    		else
                    		{
                    			playerData.setSent(true);
                    		}
                    	}
                	}
                }
                
                if (packet.getType().equals(PacketType.Play.Client.BLOCK_PLACE))
                {
                	if (chick.isToggled("place"))
                	{
                		double diff = System.currentTimeMillis() - playerData.getLastPlace();
                    	
                    	if (diff < 10.0D && playerData.getPing() < 800)
                    	{
                    		playerData.setVerbose13(playerData.getVerbose13() + 1);
                    		
                    		if (playerData.getVerbose13() > 2)
                    		{
                    			GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type I - Place", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
                    		}
                    	}
                    	else
                    	{
                    		playerData.setVerbose13(0);
                    	}
                	}
                }
                
                if (packet.getType().equals(PacketType.Play.Client.HELD_ITEM_SLOT))
                {
                	if (chick.isToggled("held"))
                	{
                		int ticks = playerData.getTicks2();
                		
                		if (ticks < 1)
                		{
                			playerData.setVerbose14(playerData.getVerbose14() + 1);
                			
                			if (playerData.getVerbose14() > 1)
                			{
                				GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type J - Held", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
                			}
                		}
                		else
                		{
                			playerData.setVerbose14(0);
                		}
                	}
                }
                
                if (packet.getType().equals(PacketType.Play.Client.KEEP_ALIVE))
                {
                	if (chick.isToggled("keep-alive"))
                	{
                		try
                		{
                			int key = packet.getIntegers().read(0);
                			
                			if (key == Integer.MAX_VALUE || key == Integer.MIN_VALUE)
                			{
                				GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type K - Keep alive", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
                			}
                		}
                		catch (Exception ex)
                		{
                			long key = packet.getLongs().read(0);
                			
                			if (key == Integer.MAX_VALUE || key == Integer.MIN_VALUE || key == Long.MAX_VALUE || key == Long.MIN_VALUE)
                			{
                				GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type K - Keep alive", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
                			}
                		}
                	}
                }
                
                if (chick.isToggled("place"))
                {
                	if (packet.getType().equals(PacketType.Play.Client.BLOCK_PLACE))
                	{
                        double diff = System.currentTimeMillis() - playerData.getLastPlacer();
                        
                        if (diff < 40)
                        {
                            if (playerData.getVerbose50().flag(1, 1000L))
                            {
                            	GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type I - Place", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
                            }
                        }
                	}
                	else if (packet.getType().equals(PacketType.Play.Client.POSITION_LOOK) || packet.getType().equals(PacketType.Play.Client.LOOK))
                	{
                		playerData.setLastPlacer(System.currentTimeMillis());
                	}
                }
                
                if (chick.isToggled("attack"))
                {
                	if (packet.getType().equals(PacketType.Play.Client.USE_ENTITY))
                	{
                		WrapperPlayClientUseEntity wrapperPlayClientUseEntity = new WrapperPlayClientUseEntity(packet);
                		
                		if (wrapperPlayClientUseEntity.getType().equals(EntityUseAction.ATTACK))
                		{
                			playerData.setsUse(playerData.getsUse() + 1);
                			
                			if (playerData.getsArm() < playerData.getsUse())
                			{
                				playerData.setVerbose51(playerData.getVerbose51() + 1);
                				
                				if (playerData.getVerbose51() > 3)
                				{
                					GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type G - Attack (4)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), false);
                				}
                			}
                			else
                			{
                				playerData.setVerbose51(0);
                			}
                		}
                		
                		if (playerData.getsUse() + playerData.getsArm() > 20)
                		{
                			playerData.setsUse(0);
                			playerData.setsArm(0);
                		}
                	}
                	else if (packet.getType().equals(PacketType.Play.Client.ARM_ANIMATION))
                	{
                		playerData.setsArm(playerData.getsArm() + 1);
                	}
                }
                
                if (chick.isToggled("attack"))
                {
                	if (packet.getType().equals(PacketType.Play.Client.USE_ENTITY))
                	{
                        double motionX = playerData.getMotionX();
                        double motionZ = playerData.getMotionZ();

                        double dist = Math.sqrt((motionX * motionX) + (motionZ * motionZ));

                        double last = playerData.getDist();
                        playerData.setDist(dist);

                        if (!(System.currentTimeMillis() - playerData.getLastEntityUse() > 500L))
                        {
                        	double diff = dist - (last * 0.98f);

                            if (diff > 0.006 && diff < 0.025)
                            {
                                if (playerData.getVerbose54().flag(8, 1000L))
                                {
                                	GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type G - Attack (5)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), false);
                                }
                            }
                        }
                	}
                }
            }
        });
	}
	
	@Override
	public void setup()
	{
		if (GrandAntiCheat.isDebugMode())
		{
			this.getSettings().addDefault("abilities", true);
			this.getSettings().addDefault("payload", true);
			this.getSettings().addDefault("position", true);
			this.getSettings().addDefault("look", true);
			this.getSettings().addDefault("ground", true);
			this.getSettings().addDefault("liquid", true);
			this.getSettings().addDefault("attack", true);
			this.getSettings().addDefault("sprint", true);
			this.getSettings().addDefault("place", true);
			this.getSettings().addDefault("held", true);
			this.getSettings().addDefault("swing", true);
			this.getSettings().addDefault("keep-alive", true);
		}
	}
}