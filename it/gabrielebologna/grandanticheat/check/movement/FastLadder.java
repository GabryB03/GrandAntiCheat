package it.gabrielebologna.grandanticheat.check.movement;

import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;
import it.gabrielebologna.grandanticheat.utils.UtilMath;
import it.gabrielebologna.grandanticheat.utils.UtilPlayer;

public class FastLadder extends Check
{
	public Map<Player, Integer> count;

	public FastLadder()
	{
		super("FastLadder");
		count = new WeakHashMap<>();
	}
	
    @EventHandler
    public void checkFastLadder(PlayerMoveEvent e)
    {
        Player player = e.getPlayer();
        PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
        
        if (!playerData.canBeChecked())
        {
        	return;
        }

        if (e.isCancelled() || (e.getFrom().getY() == e.getTo().getY()) || player.getAllowFlight() || !UtilPlayer.isOnClimbable(player, 1) || !UtilPlayer.isOnClimbable(player, 0))
        {
        	return;
        }

        int Count = count.getOrDefault(player, 0);
        double OffsetY = UtilMath.offset(UtilMath.getVerticalVector(e.getFrom().toVector()), UtilMath.getVerticalVector(e.getTo().toVector()));
        double Limit = 0.13;

        double updown = e.getTo().getY() - e.getFrom().getY();
        
        if (updown <= 0)
        {
        	return;
        }
        
        if (UtilPlayer.isOnClimbable(player, 1))
        {
        	Limit = 0.34D;
        }

        if (OffsetY > Limit)
        {
            Count++;
        }
        else
        {
            Count = Count > -2 ? Count - 1 : 0;
        }

        long percent = Math.round((OffsetY - Limit) * 120);

        if (Count > 2)
        {
            Count = 0;
            GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Fast Ladder", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", speed: " + OffsetY + ", max: " + Limit + ", percent: " + percent + "%", true);
        }
        
        count.put(player, Count);
    }
}