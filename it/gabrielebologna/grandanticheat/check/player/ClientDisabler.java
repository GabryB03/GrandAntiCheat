package it.gabrielebologna.grandanticheat.check.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;

public class ClientDisabler extends Check implements PluginMessageListener
{
	public ClientDisabler()
	{
		super("ClientDisabler");
	    Bukkit.getServer().getMessenger().registerIncomingPluginChannel(GrandAntiCheat.getPlugin(), "WD|INIT", this);
	    Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(GrandAntiCheat.getPlugin(), "WD|CONTROL");
	    Bukkit.getServer().getMessenger().registerIncomingPluginChannel(GrandAntiCheat.getPlugin(), "WDL|INIT", this);
	    Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(GrandAntiCheat.getPlugin(), "WDL|CONTROL");
	}
	
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event)
    {
        event.getPlayer().sendMessage("§8 §8 §1 §3 §3 §7 §8 ");
        event.getPlayer().sendMessage("§1 §6 §2 §3 §9 §9 §1 §3 §9");
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] data)
    {
    	PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
        GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Bad client", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", channel: " + s, true);
    }
}