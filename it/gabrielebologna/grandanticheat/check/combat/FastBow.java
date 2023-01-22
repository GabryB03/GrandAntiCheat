package it.gabrielebologna.grandanticheat.check.combat;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;

public class FastBow extends Check
{
    public static Map<Player, Long> bowPull;
    public static Map<Player, Integer> count;
	
	public FastBow()
	{
		super("FastBow");
        bowPull = new HashMap<>();
        count = new HashMap<>();
	}
	
    @EventHandler
    public void Interact(final PlayerInteractEvent e)
    {
        Player Player = e.getPlayer();
        
        if (Player.getItemInHand() != null && Player.getItemInHand().getType().equals(Material.BOW))
        {
            bowPull.put(Player, System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent e)
    {
    	try
    	{
            if (bowPull.containsKey(e.getPlayer()))
            {
            	bowPull.remove(e.getPlayer());
            }
            
            if (count.containsKey(e.getPlayer()))
            {
            	count.remove(e.getPlayer());
            }
    	}
    	catch (Exception ex)
    	{
    		
    	}
    }

    @EventHandler
    public void onShoot(final ProjectileLaunchEvent e)
    {
        if (e.getEntity() instanceof Arrow)
        {
            Arrow arrow = (Arrow) e.getEntity();
            
            if (arrow.getShooter() != null && arrow.getShooter() instanceof Player)
            {
                Player player = (Player) arrow.getShooter();
                PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
                
                if (!playerData.canBeChecked())
                {
                	return;
                }
                
                if (bowPull.containsKey(player))
                {
                    Long time = System.currentTimeMillis() - bowPull.get(player);
                    double power = arrow.getVelocity().length();
                    Long timeLimit = 300L;
                    int Count = 0;
                    
                    if (count.containsKey(player))
                    {
                        Count = count.get(player);
                    }
                    
                    if (power > 2.5 && time < timeLimit)
                    {
                        count.put(player, Count + 1);
                    }
                    else
                    {
                        count.put(player, Count > 0 ? Count - 1 : Count);
                    }
                    
                    if (Count > 1)
                    {
                        GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Instant bow", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", time: " + time, true);
                        count.remove(player);
                    }
                }
            }
        }
    }
}