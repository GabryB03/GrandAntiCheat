package it.gabrielebologna.grandanticheat.utils;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.EntityPlayer;

public class UtilNMS
{
    public static EntityPlayer getNmsPlayer(Player player)
    {
        return ((CraftPlayer) player).getHandle();
    }
}