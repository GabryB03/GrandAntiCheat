package it.gabrielebologna.grandanticheat.violations;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;

public class FlagManager
{
	@SuppressWarnings("deprecation")
	public void flagPlayer(Player player, Check check, String info, String otherInfo, boolean instantBan)
	{
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))
		{
			return;
		}
		
		if (!GrandAntiCheat.isDebugMode() && player.isOp())
		{
			return;
		}
		
		if (!info.endsWith(" (SPOOFED)"))
		{
			if (!playerData.canBeChecked())
			{
				return;
			}
		}
		
		if (GrandAntiCheat.isDebugMode())
		{
			if (check.isEnabled() && check.canDoFlag())
			{
				if (check.canCustomFlagMessage())
				{
					GrandAntiCheat.message(player, check.getCustomFlagMessage());
				}
				else
				{
					GrandAntiCheat.message(player, GrandAntiCheat.getTheConfig().getConfigurationSection("flags").getString("player-flag-message").replace("&", "§").replace("{PLAYER}", player.getName()).replace("{CHECK}", check.getName()).replace("{INFO}", info).replace("{OTHER_INFO}", otherInfo));
				}

				for (Player p: Bukkit.getOnlinePlayers())
				{
					if (p.isOp() || p.hasPermission("*") || p.hasPermission("grandanticheat.*") || p.hasPermission("gac.*"))
					{
						GrandAntiCheat.message(p, GrandAntiCheat.getTheConfig().getConfigurationSection("flags").getString("staff-flag-message").replace("&", "§").replace("{PLAYER}", player.getName()).replace("{CHECK}", check.getName()).replace("{INFO}", info).replace("{OTHER_INFO}", otherInfo));
					}
				}
				
				for (String s: check.getFlagCommands())
				{
					ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
					Bukkit.dispatchCommand(console, s.replace("/", ""));
				}
			}
			
			if (check.isEnabled() && check.canUseVLSystem())
			{
				GrandAntiCheat.getVLManager().addVL(player, check.getVLAdd(), check);
				GrandAntiCheat.getVLManager().addVL(player, check.getVLAdd());
				
				if (check.canVLReset())
				{
					Bukkit.getScheduler().runTaskLater(GrandAntiCheat.getPlugin(), new BukkitRunnable()
					{			
						@Override
						public void run()
						{
							if (check.canVLResetComplete())
							{
								GrandAntiCheat.getVLManager().resetVL(player, check);
								GrandAntiCheat.getVLManager().resetVL(player);
								
								if (check.canSendVLResetMessage())
								{
									GrandAntiCheat.message(player, check.getVLResetMessage().replace("&", "§").replace("{CHECK}", check.getName()));
								}
							}
							else
							{
								GrandAntiCheat.getVLManager().subVL(player, check.getVLResetFlags(), check);
								GrandAntiCheat.getVLManager().subVL(player, check.getVLResetFlags());
								
								if (check.canSendVLResetMessage() && GrandAntiCheat.getVLManager().getVL(player, check) <= 0)
								{
									GrandAntiCheat.message(player, check.getVLResetMessage().replace("&", "§").replace("{CHECK}", check.getName()));
								}
							}
						}
					}, check.getVLResetTime());
				}					
			}
		}
		else
		{	
			if (playerData.getFlagVerbose().flag(4, 2000L) || instantBan)
			{
				if (!GrandAntiCheat.getBanwaveManager().isStarted())
				{
					GrandAntiCheat.getBanwaveManager().setStarted(true);
				}
				
				GrandAntiCheat.getBanwaveManager().addBanned(player);
			}
		}
	}
}