package it.gabrielebologna.grandanticheat.utils;

import java.lang.reflect.Constructor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UtilPacket {

    public static void sendKeepAlive(Player player) {
        try {
            Constructor<?> keepAliveConstructor = getNMSClass("PacketPlayOutKeepAlive").getConstructor(int.class);
            Object packet = keepAliveConstructor.newInstance(-1);
            sendPacket(packet, player);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void sendTransaction(Player player) {
        try {
            Constructor<?> transactionConstructor = getNMSClass("PacketPlayOutTransaction").getConstructor(int.class, short.class, boolean.class);
            Object packet = transactionConstructor.newInstance((int) 0, (short) 0, false);
            sendPacket(packet, player);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    /*
    Packet Methods
     */

    public static void sendPacket(Object packet, Player player) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String name) {
        final String packageName = Bukkit.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getServerVersion(){
        final String packageName = Bukkit.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);
        return version;
    }

}