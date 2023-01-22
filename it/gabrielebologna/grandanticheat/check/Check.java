package it.gabrielebologna.grandanticheat.check;

import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;

public class Check implements Listener
{
	private String name;
	
	public Check(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setup()
	{
		
	}
	
	public boolean isEnabled()
	{
		return !GrandAntiCheat.isDebugMode() ? true : GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getBoolean("enabled");
	}
	
	public boolean canDoFlag()
	{
		return !GrandAntiCheat.isDebugMode() ? true : GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getBoolean("do-flag");
	}
	
	public boolean canCustomFlagMessage()
	{
		return !GrandAntiCheat.isDebugMode() ? true : GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getBoolean("custom-flag-message");
	}
	
	public String getCustomFlagMessage()
	{
		return !GrandAntiCheat.isDebugMode() ? "" : GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getString("custom-flag-msg").replace("&", "§");
	}
	
	public boolean canSetback()
	{
		return !GrandAntiCheat.isDebugMode() ? true : GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getBoolean("setback");
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getFlagCommands()
	{
		return !GrandAntiCheat.isDebugMode() ? null : (ArrayList<String>) GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getList("flag-commands");
	}
	
	public boolean canUseVLSystem()
	{
		return GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getBoolean("vl-system");
	}
	
	public int getVLAdd()
	{
		return GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getInt("vl-add");
	}
	
	public boolean canVLReset()
	{
		return GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getBoolean("vl-reset");
	}
	
	public long getVLResetTime()
	{
		return GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getLong("vl-reset-time");
	}
	
	public boolean canSendVLResetMessage()
	{
		return GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getBoolean("vl-reset-send-message");
	}
	
	public String getVLResetMessage()
	{
		return GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getString("vl-reset-message").replace("&", "§");
	}
	
	public boolean canVLResetComplete()
	{
		return GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getBoolean("vl-reset-complete");
	}
	
	public int getVLResetFlags()
	{
		return GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getInt("vl-reset-flags");
	}
	
	public ConfigurationSection getSettings()
	{
		return GrandAntiCheat.isDebugMode() ? GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getConfigurationSection("settings") : null;
	}
	
	public boolean isToggled(String thing)
	{
		return true;
		//return GrandAntiCheat.isDebugMode() ? GrandAntiCheat.getTheConfig().getConfigurationSection("checks").getConfigurationSection(this.getName().toLowerCase()).getBoolean(thing) : true;
	}
}