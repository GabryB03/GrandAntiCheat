package it.gabrielebologna.grandanticheat.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import it.gabrielebologna.grandanticheat.player.PlayerData;

public class UtilMove
{
    public static boolean blockNearHead(Player player)
    {
    	try
    	{
        	double expand = 0.3;
            
            for (double x = -expand; x <= expand; x += expand)
            {
                for (double z = -expand; z <= expand; z += expand)
                {
                    if (player.getEyeLocation().clone().add(z, 0.2, x).getBlock().getType() != Material.AIR)
                    {
                        return true;
                    }
                }
            }
            
            if (player.getLocation().clone().add(new Vector(0.0, 1.0, 0.0)).getBlock().getType() != Material.AIR)
            {
            	return true;
            }
            
            if (player.getLocation().clone().add(new Vector(0.0, 0.5, 0.0)).getBlock().getType() != Material.AIR)
            {
            	return true;
            }
    	}
    	catch (Exception ex)
    	{
    		return false;
    	}
        
        return false;
    }
	
    public static double getBaseMovementSpeed(PlayerData playerData, double conveinentMax, boolean blockAmplifiers)
    {
    	Player player = playerData.getPlayer();
    	double effect = 0.0D;
    	
    	if (player.hasPotionEffect(PotionEffectType.SPEED))
    	{
    		effect = getPotionEffectLevel(player, PotionEffectType.SPEED);
    	}
    	
        conveinentMax += effect * (conveinentMax / 2);
        conveinentMax *= (player.getWalkSpeed() / 0.2D);

        if (player.isFlying())
        {
        	conveinentMax *= (player.getFlySpeed() / 0.2D);
        }

        if (blockAmplifiers)
        {
            if (blockNearHead(player))
            {
                conveinentMax += 0.63D * conveinentMax;
            }
            
            if (System.currentTimeMillis() - playerData.getLastIce() < 2000)
            {
                conveinentMax += 0.63D * (conveinentMax / 2);
            }
            
            if (System.currentTimeMillis() - playerData.getLastSlime() < 2000)
            {
                conveinentMax += 0.63D * (conveinentMax / 2);
            }
        }

        return conveinentMax;
    }
    
    public static float getBaseSpeed(PlayerData playerData)
    {
        Player player = playerData.getPlayer();
        return playerData.isClientOnGround() ? 0.29f : 0.36f + (getPotionEffectLevel(player, PotionEffectType.SPEED) * 0.062f) + ((player.getWalkSpeed() - 0.2f) * 1.6f);
    }
    
    public static double getHorizontalDistanceSpeed(Location to, Location from) {
        double x = Math.abs(to.getX()) - Math.abs(from.getX());
        double z = Math.abs(to.getZ()) - Math.abs(from.getZ());
        return Math.sqrt(x * x + z * z);
    }

    public static int getPotionEffectLevel(Player player, PotionEffectType pet) {
        for (PotionEffect pe : player.getActivePotionEffects()) {
            if (pe.getType().getName().equals(pet.getName())) {
                return pe.getAmplifier() + 1;
            }
        }
        return 0;
    }
}