package it.gabrielebologna.grandanticheat.check.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;

public class Spammer extends Check
{
    public Spammer()
    {
        super("Spammer");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);

        if (!playerData.canBeChecked())
        {
            return;
        }

        if (System.currentTimeMillis() - playerData.getLastMessage() < 500L)
        {
        	/*int seconds = 2;
        	long lol = System.currentTimeMillis() - playerData.getLastMessage();
        	
        	if (lol >= 1700 && lol <= 2000)
        	{
        		seconds = 0;
        	}
        	else if (lol <= 1700 && lol >= 1000)
        	{
        		seconds = 1;
        	}
        	else
        	{
				seconds = 2;     		
			}*/
        	
        	event.setCancelled(true);
        }
        else
        {
            playerData.setLastMessage(System.currentTimeMillis());
        }
    }
}