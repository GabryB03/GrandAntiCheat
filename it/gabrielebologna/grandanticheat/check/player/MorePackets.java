package it.gabrielebologna.grandanticheat.check.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;
import it.gabrielebologna.grandanticheat.update.UpdateEvent;
import it.gabrielebologna.grandanticheat.utils.UtilMath;

public class MorePackets extends Check
{
	private PacketType[] playerPackets = new PacketType[] {PacketType.Play.Client.FLYING, PacketType.Play.Server.POSITION};
	
	public MorePackets()
	{
		super("MorePackets");
		
		Check chick = this;
		
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(GrandAntiCheat.getPlugin(), playerPackets)
        {
            public void onPacketReceiving(final PacketEvent event)
            {   	
            	if (!chick.isToggled("timer"))
            	{
            		return;
            	}
            	
            	Player player = event.getPlayer();
            	PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(event.getPlayer());
            	
                if (!playerData.canBeChecked())
                {
                	return;
                }
                
                long time = System.currentTimeMillis();
                long lastTime = playerData.getLastTime() != 0L ? playerData.getLastTime() : time - 50L;
                playerData.setLastTime(time);;
                long rate = time - lastTime;
                playerData.setBalance(playerData.getBalance() + 50.0D);
                playerData.setBalance(playerData.getBalance() - rate);
                
                if (playerData.getBalance() >= 10.25D + (double)UtilMath.pingFormula((long)(playerData.getPing() + 2)))
                {
                	if (playerData.getVerbose5() > 10)
                	{
                		GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type G - Timer", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", balance: " + playerData.getBalance(), true);
                		playerData.setBalance(0.0D);
                	}
                }
                else
                {
                	playerData.setVerbose5(0);
                }
            }
            
            public void onPacketSending(final PacketEvent event)
            {
            	if (!chick.isToggled("timer"))
            	{
            		return;
            	}
            	
            	PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(event.getPlayer());
            	
                if (!playerData.canBeChecked())
                {
                	return;
                }
                
                playerData.setBalance(playerData.getBalance() - 50.0D);
            }
        });
	}

	@Override
	public void setup()
	{
		if (GrandAntiCheat.isDebugMode())
		{
			this.getSettings().addDefault("flying-packets", true);
			this.getSettings().addDefault("max-flying-packets", 26);
			this.getSettings().addDefault("position-packets", true);
			this.getSettings().addDefault("max-position-packets", 21);
			this.getSettings().addDefault("position-look-packets", true);
			this.getSettings().addDefault("max-position-look-packets", 21);
			this.getSettings().addDefault("look-packets", true);
			this.getSettings().addDefault("max-look-packets", 21);
			this.getSettings().addDefault("moves", true);
			this.getSettings().addDefault("max-moves", 21);
			this.getSettings().addDefault("swings", true);
			this.getSettings().addDefault("max-swings", 23);
			this.getSettings().addDefault("timer", true);
			this.getSettings().addDefault("inventory-clicks", true);
			this.getSettings().addDefault("max-inventory-clicks", 16);
			this.getSettings().addDefault("drops", true);
			this.getSettings().addDefault("max-drops", 6);
		}
		
		super.setup();
	}
	
	@EventHandler
	public void onUpdate(UpdateEvent event)
	{
		if (!this.isEnabled())
		{
			return;
		}
		
		for (Player player: Bukkit.getOnlinePlayers())
		{
			PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
					
			if (playerData.getPing() >= 600)
			{
				if (playerData.getFlyingPackets() >= 7 && this.isToggled("flying-packets"))
				{
					GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Flying packets (SPOOFED)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", flying packets: " + playerData.getFlyingPackets(), true);
				}
				
				if (playerData.getPositionPackets() >= 6 && this.isToggled("position-packets"))
				{
					GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type B - Position packets (SPOOFED)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", position packets: " + playerData.getPositionPackets(), true);
				}
				
				if (playerData.getPositionLookPackets() >= 6 && this.isToggled("position-look-packets"))
				{
					GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type C - Position look packets (SPOOFED)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", position look packets: " + playerData.getPositionLookPackets(), true);
				}
				
				if (playerData.getLookPackets() >= 6 && this.isToggled("look-packets"))
				{
					GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type D - Look packets (SPOOFED)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", look packets: " + playerData.getLookPackets(), true);
				}
			}
			else if (playerData.getPing() >= 200)
			{
				if (playerData.getFlyingPackets() >= 16 && this.isToggled("flying-packets"))
				{
					GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Flying packets (SPOOFED)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", flying packets: " + playerData.getFlyingPackets(), true);
				}
				
				if (playerData.getPositionPackets() >= 14 && this.isToggled("position-packets"))
				{
					GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type B - Position packets (SPOOFED)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", position packets: " + playerData.getPositionPackets(), true);
				}
				
				if (playerData.getPositionLookPackets() >= 14 && this.isToggled("position-look-packets"))
				{
					GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type C - Position look packets (SPOOFED)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", position look packets: " + playerData.getPositionLookPackets(), true);
				}
				
				if (playerData.getLookPackets() >= 14 && this.isToggled("look-packets"))
				{
					GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type D - Look packets (SPOOFED)", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", look packets: " + playerData.getLookPackets(), true);
				}
			}
			
			if (playerData.getFlyingPackets() > 32 && this.isToggled("flying-packets"))
			{
				GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Flying packets", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", flying packets: " + playerData.getFlyingPackets(), true);
			}
			
			if (playerData.getPositionPackets() > 25 && this.isToggled("position-packets"))
			{
				GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type B - Position packets", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", position packets: " + playerData.getPositionPackets(), true);
			}
			
			if (playerData.getPositionLookPackets() > 25 && this.isToggled("position-look-packets"))
			{
				GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type C - Position look packets", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", position look packets: " + playerData.getPositionLookPackets(), true);
			}
			
			if (playerData.getLookPackets() > 25 && this.isToggled("look-packets"))
			{
				GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type D - Look packets", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", look packets: " + playerData.getLookPackets(), true);
			}
			
			if (playerData.getMoves() > 39 && this.isToggled("moves"))
			{
				GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type E - Movements", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", movements (1): " + playerData.getMoves(), true);
			}
			
			if (playerData.getMoves2() > 39 && this.isToggled("moves"))
			{
				GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type E - Movements", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", movements (2): " + playerData.getMoves2(), true);
			}
			
			if (playerData.getArmSwings() > 23 && this.isToggled("swings"))
			{
				GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type F - Arm swings", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", arm swings: " + playerData.getArmSwings(), true);
			}
			
			if (playerData.getInventoryClicks() > 16 && this.isToggled("inventory-clicks"))
			{
				GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type G - Inventory clicks", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", inventory clicks: " + playerData.getInventoryClicks(), true);
			}
			
			if (playerData.getDrops() > 6 && this.isToggled("drops"))
			{
				GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type H - Drops", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", drops: " + playerData.getDrops(), true);
			}
		}
	}
}