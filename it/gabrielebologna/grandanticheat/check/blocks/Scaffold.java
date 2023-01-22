package it.gabrielebologna.grandanticheat.check.blocks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;
import it.gabrielebologna.grandanticheat.utils.UtilMath;

public class Scaffold extends Check
{
	private final int SIZE = 20;
	
	public Scaffold()
	{
		super("Scaffold");
		Check chick = this;
		
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(GrandAntiCheat.getPlugin(), PacketType.Play.Client.HELD_ITEM_SLOT)
        {
            public void onPacketReceiving(final PacketEvent event)
            {     	
            	if (!chick.isToggled("slot"))
            	{
            		return;
            	}
            	
            	Player player = event.getPlayer();
            	PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
 	
                if (!playerData.canBeChecked())
                {
                	return;
                }
                
                double diff = (System.nanoTime() - playerData.getLastHeld());
                
                if (String.valueOf(diff).length() <= 9 && !String.valueOf(diff).contains("E"))
                {
                    if (playerData.getVerbose44().flag(1, 600))
                    {
                        GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type G - Slot", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
                    }
                }
                
                playerData.setLastHeld(System.nanoTime());
            }
        });
	}
	
	@Override
	public void setup()
	{
    	if (GrandAntiCheat.isDebugMode())
    	{
    		this.getSettings().addDefault("position", true);
    		this.getSettings().addDefault("angle", true);
    		this.getSettings().addDefault("sprint", true);
    		this.getSettings().addDefault("fast-place", true);
    		this.getSettings().addDefault("fast-rotations", true);
    		this.getSettings().addDefault("bad-interact", true);	
    		this.getSettings().addDefault("slot", true);
    		this.getSettings().addDefault("bad-place", true);
    	}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event)
	{
    	if (GrandAntiCheat.isDebugMode())
    	{
    		if (!this.isToggled("bad-place"))
    		{
    			return;
    		}
    	}
    	
    	Player player = event.getPlayer();
    	PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
    	
    	if (!playerData.canBeChecked())
    	{
    		return;
    	}
    	
        double x = Math.abs(event.getBlockPlaced().getX() - player.getLocation().getX());
        double y = (player.getLocation().getY() - event.getBlockPlaced().getY());
        double z = Math.abs(event.getBlockPlaced().getZ() - player.getLocation().getZ());
        double dist = Math.sqrt((x * x) + (z * z));

        if (dist > 0.79 && y == 1.0)
        {
            if (playerData.getVerbose45().flag(2, 500L))
            {
            	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type H - Bad place", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
            }
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
		
    	if (GrandAntiCheat.isDebugMode())
    	{
    		if (!this.isToggled("fast-rotations"))
    		{
    			return;
    		}
    	}
		
		List<Double> avgPitches = (List<Double>) playerData.getAverageScaffoldPitches();
		
		if (avgPitches == null)
		{
			avgPitches = new ArrayList<>();
		}

		avgPitches.add(0, (double) player.getLocation().getPitch());

		for (int i = SIZE; i < avgPitches.size(); i++)
		{
			avgPitches.remove(i);
		}
		
		playerData.setAverageScaffoldPitches(avgPitches);
	}
	
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
		
		if (this.isToggled("position"))
		{
	        final double xOffset = UtilMath.offset(event.getPlayer().getLocation().getX(), event.getBlockAgainst().getX());
	        final double zOffset = UtilMath.offset(event.getPlayer().getLocation().getZ(), event.getBlockAgainst().getZ());

	        boolean flag = false;
	        
	        switch (event.getBlock().getFace(event.getBlockAgainst()))
	        {
	            case EAST:
	                flag = xOffset <= 0;
	                break;
	            case WEST:
	                flag = xOffset <= 1;
	                break;
	            case NORTH:
	                flag = zOffset <= 1;
	                break;
	            case SOUTH:
	                flag = zOffset <= 0;
	                break;
	            default:
	                flag = false;
	                break;
	        }
	        
	        if (flag)
	        {
	        	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Position", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", x offset: " + xOffset + ", z offset: " + zOffset, true);
	        }
		}
		
		if (this.isToggled("angle"))
		{
	        final BlockFace placedFace = event.getBlock().getFace(event.getBlockAgainst());
	        final Vector placedVector = new Vector(placedFace.getModX(), placedFace.getModY(), placedFace.getModZ());

	        if (player.getLocation().getDirection().angle(placedVector) > Math.toRadians(90.0D))
	        {
	        	GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type B - Angle", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", angle: " + player.getLocation().getDirection().angle(placedVector), true);
	        }
		}
		
		if (this.isToggled("sprint"))
		{
	        if ((System.currentTimeMillis() - playerData.getLastSprint()) < 400L)
	        {
	        	playerData.setVerbose30(playerData.getVerbose30() + 1);
	        	
	        	if (playerData.getVerbose30() >= 20)
	        	{
	        		GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type C - Sprint", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
	        	}
	        }
	        else if (playerData.getVerbose30() > 0)
	        {
	        	playerData.setVerbose30(playerData.getVerbose30() - 1);
	        }
		}
		
		if (this.isToggled("fast-place"))
		{
			Block placed = event.getBlockPlaced();

			if (player.getLocation().subtract(0, 1, 0).getBlock().equals(placed))
			{
				int blocksPlaced = playerData.getScaffoldBlocksPlaced();
				playerData.setScaffoldBlocksPlaced(playerData.getScaffoldBlocksPlaced() + 1);

				if (blocksPlaced > 7)
				{	
					GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type D - Fast place", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
				}							
			}
		}
		
		if (this.isToggled("fast-rotations"))
		{
			List<Double> avgPitches = (List<Double>) playerData.getAverageScaffoldPitches();
			
			if (avgPitches != null)
			{
				if (avgPitches.size() > SIZE)
				{
					if (!event.getBlock().getRelative(BlockFace.DOWN).getType().isSolid())
					{
						double total = 0;
						
						for (double d : avgPitches)
						{
							total += d;
						}
						
						total /= avgPitches.size();

						double diff = Math.abs((player.getLocation().getPitch() - total));

						if (diff < 30)
						{
							return;
						}

						GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type E - Fast rotations", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", diff: " + diff, true);
					}
				}
			}
		}
	}
}