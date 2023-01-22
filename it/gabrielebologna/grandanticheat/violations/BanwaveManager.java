package it.gabrielebologna.grandanticheat.violations;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;

public class BanwaveManager
{
	private ArrayList<Player> banned;
	private boolean started;
	
	public BanwaveManager()
	{
		banned = new ArrayList<Player>();
	}

	public ArrayList<Player> getBanned()
	{
		return banned;
	}

	public void setBanned(ArrayList<Player> banned)
	{
		this.banned = banned;
	}
	
	public boolean isBanned(Player player)
	{
		return banned.contains(player);
	}
	
	public void addBanned(Player player)
	{
		if (!isBanned(player))
		{
			banned.add(player);
		}
	}
	
	public void removeBanned(Player player)
	{
		if (isBanned(player))
		{
			banned.remove(player);
		}
	}
	
	public boolean isStarted()
	{
		return started;
	}

	public void setStarted(boolean started)
	{
		this.started = started;
		startBanwaves();
	}

	@SuppressWarnings("deprecation")
	public void startBanwaves()
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(GrandAntiCheat.getPlugin(), new BukkitRunnable()
		{			
			@Override
			public void run()
			{
				if (isStarted())
				{
					if (!banned.isEmpty())
					{
						try
						{
							for (Player player: banned)
							{
								ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
								String command = GrandAntiCheat.getTheConfig().getString("ban-command").replace("{PLAYER}", player.getName()).replace("%player%", player.getName()).replace("/", "");				
								Bukkit.dispatchCommand(console, command);
								
								try
								{
									banned.remove(player);
								}
								catch (Exception ex)
								{
									
								}
							}
						}
						catch (Exception ex)
						{
							
						}
					}
				}
			}
		}, 0L, 120L);
	}
}