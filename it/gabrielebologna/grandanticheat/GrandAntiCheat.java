package it.gabrielebologna.grandanticheat;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.check.CheckManager;
import it.gabrielebologna.grandanticheat.check.blocks.BlockReach;
import it.gabrielebologna.grandanticheat.check.blocks.Scaffold;
import it.gabrielebologna.grandanticheat.check.chat.Spammer;
import it.gabrielebologna.grandanticheat.check.combat.FastBow;
import it.gabrielebologna.grandanticheat.check.combat.Killaura;
import it.gabrielebologna.grandanticheat.check.combat.Reach;
import it.gabrielebologna.grandanticheat.check.exploit.Disabler;
import it.gabrielebologna.grandanticheat.check.exploit.Exploits;
import it.gabrielebologna.grandanticheat.check.exploit.PingSpoof;
import it.gabrielebologna.grandanticheat.check.exploit.PluginFinder;
import it.gabrielebologna.grandanticheat.check.exploit.SkinDerp;
import it.gabrielebologna.grandanticheat.check.movement.BadMotion;
import it.gabrielebologna.grandanticheat.check.movement.FastLadder;
import it.gabrielebologna.grandanticheat.check.movement.Fly;
import it.gabrielebologna.grandanticheat.check.movement.Jesus;
import it.gabrielebologna.grandanticheat.check.movement.NoFall;
import it.gabrielebologna.grandanticheat.check.movement.NoSlow;
import it.gabrielebologna.grandanticheat.check.movement.Speed;
import it.gabrielebologna.grandanticheat.check.movement.Step;
import it.gabrielebologna.grandanticheat.check.player.ClientDisabler;
import it.gabrielebologna.grandanticheat.check.player.InvalidPacket;
import it.gabrielebologna.grandanticheat.check.player.Inventory;
import it.gabrielebologna.grandanticheat.check.player.MorePackets;
import it.gabrielebologna.grandanticheat.check.player.PlayerSwing;
import it.gabrielebologna.grandanticheat.check.render.HealthTags;
import it.gabrielebologna.grandanticheat.lag.LagManager;
import it.gabrielebologna.grandanticheat.player.PlayerDataListener;
import it.gabrielebologna.grandanticheat.player.PlayerDataManager;
import it.gabrielebologna.grandanticheat.update.Updater;
import it.gabrielebologna.grandanticheat.violations.BanwaveManager;
import it.gabrielebologna.grandanticheat.violations.FlagManager;
import it.gabrielebologna.grandanticheat.violations.VLManager;

public class GrandAntiCheat extends JavaPlugin
{
	private static Plugin thePlugin;
	private static FileConfiguration theConfig;
	private static String prefix = "§9GAC> §7";
	private static PlayerDataManager playerDataManager;
	private static PlayerDataListener playerDataListener;
	private static Updater updater;
	private static FlagManager flagManager;
	private static VLManager vlManager;
	private static LagManager lagManager;
	private static CheckManager checkManager;
	private static BanwaveManager banwaveManager;
	private static boolean debugMode;
	
	@Override
	public void onEnable()
	{
		if (Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null)
		{
			if (!Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib").isEnabled())
			{
				setEnabled(false);
				return;
			}
		}
		else
		{
			setEnabled(false);
			return;
		}
		
		setDebugMode(true);
		
		try
		{
			thePlugin = this;
			theConfig = this.getConfig();
			checkManager = new CheckManager();
			banwaveManager = new BanwaveManager();
			
			File configFile = new File("config.yml");
			
			if (!configFile.exists())
			{
				if (isDebugMode())
				{
					ConfigurationSection general = getConfig().createSection("general");
					general.addDefault("prefix", "&9GrandAntiCheat> &7");
					ConfigurationSection flags = getConfig().createSection("flags");
					flags.addDefault("send-to-staff", true);
					flags.addDefault("send-to-player", false);
					flags.addDefault("player-flag-message", "&7You have been detected using &c{CHECK} &8(&a{INFO}&8)&7 -> &e{OTHER_INFO}&7.");
					flags.addDefault("staff-flag-message", "&7The player &a{PLAYER} been detected using &c{CHECK} &8(&a{INFO}&8)&7 -> &e{OTHER_INFO}&7.");
					ConfigurationSection checks = getConfig().createSection("checks");
					addChecks();
					
					for (Check check: getCheckManager().getChecks())
					{
						ConfigurationSection theCheck = checks.createSection(check.getName().toLowerCase());
						theCheck.addDefault("enabled", true);
						theCheck.addDefault("do-flag", true);
						theCheck.addDefault("custom-flag-message", false);
						theCheck.addDefault("cutom-flag-msg", "&7You have been detected using &c{CHECK} &8(&a{INFO}&8)&7 -> &e{OTHER_INFO}&7.");
						theCheck.addDefault("setback", true);
						theCheck.addDefault("flag-commands", new ArrayList<String>());
						theCheck.addDefault("vl-system", true);
						theCheck.addDefault("vl-add", 1);
						theCheck.addDefault("vl-reset", true);
						theCheck.addDefault("vl-reset-time", 3000L);
						theCheck.addDefault("vl-reset-send-message", false);
						theCheck.addDefault("vl-reset-message", "&7Your violations for check &c{CHECK} &7have been reset succesfully.");
						theCheck.addDefault("vl-reset-complete", true);
						theCheck.addDefault("vl-reset-flags", 1);
						theCheck.createSection("settings");
						check.setup();
					}
				}
				else
				{
					getConfig().addDefault("ban-command", "ban {PLAYER} Cheating");
					addChecks();
					
					for (Check check: getCheckManager().getChecks())
					{
						check.setup();
					}
				}
				
				getConfig().options().copyDefaults(true);
				saveConfig();
				reloadConfig();
			}
			else
			{
				reloadConfig();
				addChecks();
			}
			
			prefix = GrandAntiCheat.isDebugMode() ? this.getConfig().getConfigurationSection("general").getString("prefix").replace("&", "§") : "§9GAC> §7";
			playerDataManager = new PlayerDataManager();
			playerDataListener = new PlayerDataListener();
			updater = new Updater();
			flagManager = new FlagManager();
			vlManager = new VLManager();
			lagManager = new LagManager();
			lagManager.run();
			addListener(playerDataListener);
			
			print("Succesfully initialized the plugin.");
		}
		catch (Exception ex)
		{
			print("Failed to initialize the plugin due to an exception:");
			ex.printStackTrace();
		}
	}
	
	@Override
	public void onDisable()
	{
		try
		{	
			print("Succesfully disabled the plugin.");
		}
		catch (Exception ex)
		{
			print("Failed to disable the plugin due to an exception:");
			ex.printStackTrace();
		}
	}
	
	public void addChecks()
	{
		addCheck(new MorePackets());
		addCheck(new Killaura());
		addCheck(new InvalidPacket());
		addCheck(new Speed());
		addCheck(new PingSpoof());
		addCheck(new Reach());
		addCheck(new Fly());
		addCheck(new NoFall());
		addCheck(new BlockReach());
		addCheck(new Scaffold());
		addCheck(new NoSlow());
		addCheck(new Jesus());
		addCheck(new Step());
		addCheck(new HealthTags());
		addCheck(new Exploits());
		addCheck(new ClientDisabler());
		addCheck(new Inventory());
		addCheck(new FastBow());
		addCheck(new FastLadder());
		addCheck(new PluginFinder());
		addCheck(new SkinDerp());
		addCheck(new Spammer());
		addCheck(new Disabler());
		addCheck(new PlayerSwing());
		addCheck(new BadMotion());
	}
	
	public static void debugCheck(String debug)
	{
		for (Player p: Bukkit.getOnlinePlayers())
		{
			if (p.isOp() || p.hasPermission("*") || p.hasPermission("grandanticheat.*") || p.hasPermission("gac.*"))
			{
				p.sendMessage("§9GAC> §7" + debug);
			}
		}
	}
	
	public static void print(String line)
	{
		System.out.println("[GAC] " + line);
	}
	
	public static void addListener(Listener listener)
	{
		Bukkit.getServer().getPluginManager().registerEvents(listener, getPlugin());
	}
	
	public static Plugin getPlugin()
	{
		return thePlugin;
	}
	
	public static FileConfiguration getTheConfig()
	{
		return theConfig;
	}
	
	public static String getPrefix()
	{
		return prefix;
	}
	
	public static void message(Player player, String msg)
	{
		player.sendMessage(getPrefix() + msg);
	}
	
	public static PlayerDataManager getPlayerDataManager()
	{
		return playerDataManager;
	}
	
	public static PlayerDataListener getPlayerDataListener()
	{
		return playerDataListener;
	}
	
	public static Updater getUpdater()
	{
		return updater;
	}
	
	public static void addCheck(Check check)
	{
		getCheckManager().addCheck(check);
		addListener(check);
	}
	
	public static FlagManager getFlagManager()
	{
		return flagManager;
	}
	
	public static VLManager getVLManager()
	{
		return vlManager;
	}
	
	public static LagManager getLagManager()
	{
		return lagManager;
	}
	
	public static CheckManager getCheckManager()
	{
		return checkManager;
	}
	
	public static BanwaveManager getBanwaveManager()
	{
		return banwaveManager;
	}

	public static boolean isDebugMode()
	{
		return debugMode;
	}

	public static void setDebugMode(boolean debugMode)
	{
		GrandAntiCheat.debugMode = debugMode;
	}
}