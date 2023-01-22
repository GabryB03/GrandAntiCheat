package it.gabrielebologna.grandanticheat.check.movement;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;
import it.gabrielebologna.grandanticheat.utils.UtilPlayer;

public class Jesus extends Check
{
	private PacketType[] packetTypes = new PacketType[] {PacketType.Play.Client.FLYING, PacketType.Play.Client.LOOK, PacketType.Play.Client.POSITION, PacketType.Play.Client.POSITION_LOOK};
	
	public Jesus()
	{
		super("Jesus");
		
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
            	
            	if (chick.isToggled("distance"))
            	{
                    double dist = playerData.getClientDeltaY();
                    boolean inLiquid = UtilPlayer.isNearLiquid(player) || UtilPlayer.isNearLiquidOffset(player);
                    boolean onBlock = UtilPlayer.hasGround(player) || UtilPlayer.hasGroundv2(player) || UtilPlayer.isOnFence(player);
                    
                    if (Math.abs(dist) < 0.003D && inLiquid && !onBlock)
                    {
                    	playerData.setVerbose31(playerData.getVerbose31() + 1);
                    	
                    	if (playerData.getVerbose31() > 4)
                    	{
                    		GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type A - Distance", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", dist: " + Math.abs(dist), true);
                    	}
                    }
                    else
                    {
                    	playerData.setVerbose31(0);
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
			this.getSettings().addDefault("distance", true);
			this.getSettings().addDefault("prediction", true);
		}
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
		
        double dist = playerData.getClientSpeedXZ();
        double lastDist = playerData.getLastClientSpeedXZ();
        double prediction = lastDist * 0.699999988079071D;
        double diff = dist - prediction;
        double scaledDist = diff * 100.0D;
        double max = 6.2D;
        max += (double) UtilPlayer.getPotionEffectLevel(PotionEffectType.SPEED, player) * max;
        boolean inLiquid = UtilPlayer.isNearLiquid(player) || UtilPlayer.isNearLiquidOffset(player);
        boolean lastInLiquid = playerData.isLastInLiquid();
        boolean lastLastInLiquid = playerData.isLastLastInLiquid();
        playerData.setLastInLiquid(inLiquid);
        playerData.setLastLastInLiquid(lastInLiquid);
        
        if (scaledDist > max && inLiquid && lastInLiquid && lastLastInLiquid)
        {
        	playerData.setVerbose32(playerData.getVerbose32() + 1);
        	
        	if (playerData.getVerbose32() > 4)
        	{
        		GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type B - Prediction", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
        	}
        }
        else
        {
        	playerData.setVerbose32(0);
        }
	}
}