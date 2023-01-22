package it.gabrielebologna.grandanticheat.check.movement;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;
import it.gabrielebologna.grandanticheat.utils.UtilBlock;
import it.gabrielebologna.grandanticheat.utils.UtilCheat;
import it.gabrielebologna.grandanticheat.utils.UtilMath;
import it.gabrielebologna.grandanticheat.utils.UtilPlayer;

public class Step extends Check
{
	public Step()
	{
		super("Step");
	}
	
	@Override
	public void setup()
	{
		if (GrandAntiCheat.isDebugMode())
		{
			this.getSettings().addDefault("height", true);
			this.getSettings().addDefault("block-speed", true);
			this.getSettings().addDefault("y-speed", true);
		}
	}
	
    public boolean isOnGround(Player player)
    {
        if (UtilPlayer.isOnClimbable(player, 0))
        {
            return false;
        }
        
        if (player.getVehicle() != null)
        {
            return false;
        }
        
        Material type = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
        
        if ((type != Material.AIR) && (type.isBlock()) && (type.isSolid()) && (type != Material.LADDER) && (type != Material.VINE))
        {
            return true;
        }
        
        Location a = player.getLocation().clone();
        a.setY(a.getY() - 0.5D);
        type = a.getBlock().getType();
        
        if ((type != Material.AIR) && (type.isBlock()) && (type.isSolid()) && (type != Material.LADDER) && (type != Material.VINE))
        {
            return true;
        }
        
        a = player.getLocation().clone();
        a.setY(a.getY() + 0.5D);
        type = a.getBlock().getRelative(BlockFace.DOWN).getType();
        
        if ((type != Material.AIR) && (type.isBlock()) && (type.isSolid()) && (type != Material.LADDER) && (type != Material.VINE))
        {
            return true;
        }
        
        return UtilCheat.isBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN), new Material[]{Material.FENCE, Material.FENCE_GATE, Material.COBBLE_WALL, Material.LADDER});
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

        if (!isOnGround(player) || player.getAllowFlight() || player.hasPotionEffect(PotionEffectType.JUMP) || UtilPlayer.isOnClimbable(player, 0) || UtilCheat.slabsNear(player.getLocation()) || player.getLocation().getBlock().getType().equals(Material.WATER) || player.getLocation().getBlock().getType().equals(Material.STATIONARY_WATER))
        {
        	return;
        }

        double yDist = event.getTo().getY() - event.getFrom().getY();
        
        if (yDist < 0)
        {
        	return;
        }
        
        double YSpeed = UtilMath.offset(UtilMath.getVerticalVector(event.getFrom().toVector()), UtilMath.getVerticalVector(event.getTo().toVector()));
        
        if (yDist > 0.95)
        {
            GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Height", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", height: " + Math.round(yDist) + ", y dist: " + yDist, true);
            return;
        }
        
        if (((YSpeed == 0.25D || (YSpeed >= 0.58D && YSpeed < 0.581D)) && yDist > 0.0D || (YSpeed > 0.2457D && YSpeed < 0.24582D) || (YSpeed > 0.329 && YSpeed < 0.33)) && !player.getLocation().clone().subtract(0.0D, 0.1, 0.0D).getBlock().getType().equals(Material.SNOW))
        {
            GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type B - Block speed", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", y-speed: " + YSpeed + ", block: " + player.getLocation().clone().subtract(0.0D, 0.1D, 0.0D).getBlock().getType().toString(), true);
            return;
        }
        
        ArrayList<Block> blocks = UtilBlock.getBlocksAroundCenter(player.getLocation(), 1);
        
        for (Block block : blocks)
        {
            if (block.getType().isSolid())
            {
                if ((YSpeed >= 0.321 && YSpeed < 0.322))
                {
                    GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type C - Y Speed", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", y-speed: " + YSpeed, true);
                    return;
                }
            }
        }
    }
}