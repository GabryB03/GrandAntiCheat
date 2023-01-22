package it.gabrielebologna.grandanticheat.check.movement;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;
import it.gabrielebologna.grandanticheat.utils.UtilCheat;
import it.gabrielebologna.grandanticheat.utils.UtilPlayer;

public class NoSlow extends Check
{
	private PacketType[] packetTypes = new PacketType[] {PacketType.Play.Client.FLYING, PacketType.Play.Client.LOOK, PacketType.Play.Client.POSITION, PacketType.Play.Client.POSITION_LOOK};
	public static Map<UUID, Map.Entry<Integer, Long>> speedTicks;
	
	public NoSlow()
	{
		super("NoSlow");
		
		Check chick = this;
		
		speedTicks = new HashMap<UUID, Map.Entry<Integer,Long>>();
		
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
            	
            	if (chick.isToggled("blocking"))
            	{
            		if ((System.currentTimeMillis() - playerData.getLastIce()) < 600L)
                	{
                		return;
                	}
                	
                    double dist = playerData.getClientSpeedXZ();
                    double lastDist = playerData.getLastClientSpeedXZ();
                    double prediction = (lastDist + 0.08D) * 0.699999988079071D;
                    
                    if (dist > prediction && player.isBlocking())
                    {         	
                    	if (dist > 0.32)
                    	{
                    		GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type A - Blocking", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", dist: " + dist + ", prediction: " + prediction, true);
                    	}
                    }
            	}
            	
            	if (chick.isToggled("sneaking"))
            	{
            		if ((System.currentTimeMillis() - playerData.getLastIce()) < 600L)
                	{
                		return;
                	}
                	
                    double dist = playerData.getClientSpeedXZ();
                    double lastDist = playerData.getLastClientSpeedXZ();
                    double prediction = (lastDist + 0.08D) * 0.699999988079071D;
                    
                    if (dist > prediction && player.isSneaking())
                    {         	
                    	if (dist > 0.32)
                    	{	
                    		GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type B - Sneaking", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", dist: " + dist + ", prediction: " + prediction, true);
                    	}
                    }
            	}
            	
            	if (chick.isToggled("web"))
            	{
            		if ((System.currentTimeMillis() - playerData.getLastIce()) < 600L)
                	{
                		return;
                	}
                	
                    double dist = playerData.getClientSpeedXZ();
                    double lastDist = playerData.getLastClientSpeedXZ();
                    double prediction = (lastDist + 0.08D) * 0.699999988079071D;
                    
                    if (dist > prediction && (UtilCheat.isInWeb(player) || UtilPlayer.isInWebOffset(player)))
                    {         	
                    	if (dist > 0.26)
                    	{
                    		GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type D - Web", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", dist: " + dist + ", prediction: " + prediction, false);
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
			this.getSettings().addDefault("blocking", true);
			this.getSettings().addDefault("sneaking", true);
			this.getSettings().addDefault("bow", true);
			this.getSettings().addDefault("web", true);
			this.getSettings().addDefault("interact", true);
		}
	}
	
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        speedTicks.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void BowShoot(final EntityShootBowEvent event)
    {
    	if (!this.isToggled("bow"))
    	{
    		return;
    	}
    	
        if (!(event.getEntity() instanceof Player))
        {
        	return;
        }
        
        final Player player = (Player) event.getEntity();
        PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
        
        if (player.isInsideVehicle() || !player.isSprinting() || !playerData.canBeChecked())
        {
        	 return;
        }
        
        GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type C - Bow", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
    }
}