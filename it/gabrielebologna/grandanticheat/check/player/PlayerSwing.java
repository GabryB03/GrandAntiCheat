package it.gabrielebologna.grandanticheat.check.player;

import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayClientUseEntity;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;

public class PlayerSwing extends Check
{
	private PacketType[] packetTypes = new PacketType[] {PacketType.Play.Client.USE_ENTITY, PacketType.Play.Client.BLOCK_DIG, PacketType.Play.Client.ARM_ANIMATION, PacketType.Play.Client.BLOCK_PLACE};
	
	public PlayerSwing()
	{
		super("PlayerSwing");
			
		Check chick = this;
		
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(GrandAntiCheat.getPlugin(), packetTypes)
        {
            public void onPacketReceiving(final PacketEvent event)
            {       	
            	Player player = event.getPlayer();
            	PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
            	PacketContainer packet = event.getPacket();
            	
            	if (!playerData.canBeChecked())
            	{
            		return;
            	}
            	
            	if (chick.isToggled("hit-blocking"))
            	{
            		if (packet.getType().equals(PacketType.Play.Client.USE_ENTITY))
            		{
            			double arm = System.currentTimeMillis() - playerData.getLastArm();
            			double dig = System.currentTimeMillis() - playerData.getLastDig1();
            			
            			if (arm < 6 && dig < 35)
            			{
            				playerData.setVerbose40(playerData.getVerbose40() + 1);
            				
            				if (playerData.getVerbose40() > 2)
            				{
            					GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type A - Hit blocking", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
            				}
            			}
            			else
            			{
            				playerData.setVerbose40(playerData.getVerbose40() > 0 ? playerData.getVerbose40() - 1 : 0);
            			}
            		}
            		else if (packet.getType().equals(PacketType.Play.Client.BLOCK_DIG))
            		{
            			playerData.setLastDig1(System.currentTimeMillis());
            		}      		
            		else if (packet.getType().equals(PacketType.Play.Client.ARM_ANIMATION))
            		{
            			playerData.setLastArm(System.currentTimeMillis());
            		}
            	}
            	
            	if (chick.isToggled("place-dig"))
            	{
            		if (packet.getType().equals(PacketType.Play.Client.BLOCK_PLACE))
            		{
            			double dig = System.currentTimeMillis() - playerData.getLastDig2();
            			
            			if (dig < 40)
            			{
            				playerData.setVerbose41(playerData.getVerbose41() + 1);
            				
            				if (playerData.getVerbose41() > 6)
            				{
            					GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type B - Place dig", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
            				}
            			}
            			else
            			{
            				playerData.setVerbose41(0);
            			}
            		}
            		else if (packet.getType().equals(PacketType.Play.Client.BLOCK_DIG))
            		{
            			playerData.setLastDig2(System.currentTimeMillis());
            		}
            	}
            	
            	if (chick.isToggled("entity-swing"))
            	{
            		if (packet.getType().equals(PacketType.Play.Client.USE_ENTITY))
            		{
            			WrapperPlayClientUseEntity wrapperPlayClientUseEntity = new WrapperPlayClientUseEntity(packet);
            			
            			if (wrapperPlayClientUseEntity.getType().equals(EntityUseAction.ATTACK))
            			{
            				playerData.setHit(playerData.getHit() + 1);
            				
            				if (playerData.getHit() > playerData.getLArm())
            				{
            					playerData.setVerbose42(playerData.getVerbose42() + 1);
            					
            					if (playerData.getVerbose42() > 0)
            					{
            						GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type C - Entity swing", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
            					}
            				}
            				else
            				{
            					playerData.setVerbose42(playerData.getVerbose42() > 0 ? playerData.getVerbose42() - 1 : 0);
            				}
            				
            				if (playerData.getLArm() > 7 || playerData.getHit() > 7)
            				{
            					playerData.setArm(0);
            					playerData.setHit(0);
            				}
            			}
            		}
            		else if (packet.getType().equals(PacketType.Play.Client.ARM_ANIMATION))
            		{
            			playerData.setArm(playerData.getLArm() + 1);
            		}
            	}
            	
            	if (chick.isToggled("hit-miss"))
            	{
            		if (packet.getType().equals(PacketType.Play.Client.USE_ENTITY))
            		{
            			WrapperPlayClientUseEntity wrapperPlayClientUseEntity = new WrapperPlayClientUseEntity(packet);
            			
            			if (wrapperPlayClientUseEntity.getType().equals(EntityUseAction.ATTACK))
            			{
            				double difference = System.currentTimeMillis() - playerData.getLastArm1();
            				
            				if (difference > 500)
            				{
            					playerData.setVerbose43(playerData.getVerbose43() + 1);
            					
            					if (playerData.getVerbose43() > 2)
            					{
            						GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type D - Hit miss", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
            					}
            				}
            				else
            				{
            					playerData.setVerbose43(playerData.getVerbose43() > 0 ? playerData.getVerbose43() - 1 : 0);
            				}
            			}
            		}
            		else if (packet.getType().equals(PacketType.Play.Client.ARM_ANIMATION))
            		{
            			playerData.setLastArm1(System.currentTimeMillis());
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
			this.getSettings().addDefault("hit-blocking", true);
			this.getSettings().addDefault("place-dig", true);
			this.getSettings().addDefault("entity-swing", true);
			this.getSettings().addDefault("hit-miss", true);
		}
	}
}