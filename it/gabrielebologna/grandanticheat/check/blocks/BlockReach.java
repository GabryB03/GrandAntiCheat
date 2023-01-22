package it.gabrielebologna.grandanticheat.check.blocks;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;
import it.gabrielebologna.grandanticheat.utils.UtilMath;

public class BlockReach extends Check
{	
	public BlockReach()
	{
		super("BlockReach");
	}
	
	@Override
	public void setup()
	{
		if (GrandAntiCheat.isDebugMode())
		{
			this.getSettings().addDefault("dig-reach", true);
			this.getSettings().addDefault("click-reach", true);
			this.getSettings().addDefault("break-reach", true);	
		}
	}
	
	@EventHandler
	public void onBlockInteract(PlayerInteractEvent event)
	{
    	if (GrandAntiCheat.isDebugMode())
    	{
    		if (!this.isToggled("click-reach"))
    		{
    			return;
    		}
    	}
				
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			Block block = event.getClickedBlock();
			Player player = event.getPlayer();
			Location useLoc = player.getLocation();
			Location blockLoc = block.getLocation();
			PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
			
            final double distanceLimit = player.getGameMode() == GameMode.CREATIVE ? 5.6D : 5.2D;
            final Location loc = player.getLocation(useLoc);
            loc.setY(loc.getY() + player.getEyeHeight());
            final double distance = UtilMath.distance(loc, blockLoc) - distanceLimit;
            useLoc.setWorld(null);
            
            if (distance > 0.8D)
            {
            	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type B - Click reach", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", distance: " + distance, true);
            }
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
    	if (GrandAntiCheat.isDebugMode())
    	{
    		if (!this.isToggled("break-reach"))
    		{
    			return;
    		}
    	}
		
		Block block = event.getBlock();
		Player player = event.getPlayer();
		Location useLoc = player.getLocation();
		Location blockLoc = block.getLocation();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
        final double distanceLimit = player.getGameMode() == GameMode.CREATIVE ? 5.6D : 5.2D;
        final Location loc = player.getLocation(useLoc);
        loc.setY(loc.getY() + player.getEyeHeight());
        final double distance = UtilMath.distance(loc, blockLoc) - distanceLimit;
        useLoc.setWorld(null);
        
        if (distance > 0.7D)
        {
        	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type C - Break reach", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", distance: " + distance, true);
        }
	}
}