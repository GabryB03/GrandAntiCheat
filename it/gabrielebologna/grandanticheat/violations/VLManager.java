package it.gabrielebologna.grandanticheat.violations;

import org.bukkit.entity.Player;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;

public class VLManager
{
	public void addVL(Player player, int vl)
	{
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		playerData.setViolations(playerData.getViolations() + vl);
	}
	
	public void setVL(Player player, int vl)
	{
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		playerData.setViolations(vl);
	}
	
	public void subVL(Player player, int vl)
	{
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		playerData.setViolations(playerData.getViolations() - vl);
	}
	
	public void resetVL(Player player)
	{
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		playerData.setViolations(0);
	}
	
	public int getVL(Player player)
	{
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		return playerData.getViolations();
	}
	
	public void addVL(Player player, int vl, Check check)
	{
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		VLPlayer vlPlayer = null;
		
		for (VLPlayer vlPlayer2: playerData.getPlayerViolations())
		{
			if (vlPlayer2.getPlayer().equals(player) && vlPlayer2.getCheck().equals(check))
			{
				vlPlayer = vlPlayer2;
			}
		}
		
		if (vlPlayer == null)
		{
			vlPlayer = new VLPlayer(player, check);
			vlPlayer.setViolations(vl);
		}
		else
		{
			vlPlayer.setViolations(vlPlayer.getViolations() + vl);
			playerData.getPlayerViolations().remove(vlPlayer);
		}
		
		playerData.getPlayerViolations().add(vlPlayer);
	}
	
	public void setVL(Player player, int vl, Check check)
	{
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		VLPlayer vlPlayer = null;
		
		for (VLPlayer vlPlayer2: playerData.getPlayerViolations())
		{
			if (vlPlayer2.getPlayer().equals(player) && vlPlayer2.getCheck().equals(check))
			{
				vlPlayer = vlPlayer2;
			}
		}
		
		if (vlPlayer == null)
		{
			vlPlayer = new VLPlayer(player, check);
			vlPlayer.setViolations(vl);
		}
		else
		{
			vlPlayer.setViolations(vl);
			playerData.getPlayerViolations().remove(vlPlayer);
		}
		
		playerData.getPlayerViolations().add(vlPlayer);
	}
	
	public void subVL(Player player, int vl, Check check)
	{
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		VLPlayer vlPlayer = null;
		
		for (VLPlayer vlPlayer2: playerData.getPlayerViolations())
		{
			if (vlPlayer2.getPlayer().equals(player) && vlPlayer2.getCheck().equals(check))
			{
				vlPlayer = vlPlayer2;
			}
		}
		
		if (vlPlayer == null)
		{
			vlPlayer = new VLPlayer(player, check);
			vlPlayer.setViolations(-vl);
		}
		else
		{
			vlPlayer.setViolations(vlPlayer.getViolations() - vl);
			playerData.getPlayerViolations().remove(vlPlayer);
		}
		
		playerData.getPlayerViolations().add(vlPlayer);
	}
	
	public void resetVL(Player player, Check check)
	{
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		VLPlayer vlPlayer = null;
		
		for (VLPlayer vlPlayer2: playerData.getPlayerViolations())
		{
			if (vlPlayer2.getPlayer().equals(player) && vlPlayer2.getCheck().equals(check))
			{
				vlPlayer = vlPlayer2;
			}
		}
		
		if (vlPlayer == null)
		{
			vlPlayer = new VLPlayer(player, check);
			vlPlayer.setViolations(0);
		}
		else
		{
			vlPlayer.setViolations(0);
			playerData.getPlayerViolations().remove(vlPlayer);
		}
		
		playerData.getPlayerViolations().add(vlPlayer);
	}
	
	public int getVL(Player player, Check check)
	{
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		VLPlayer vlPlayer = null;
		
		for (VLPlayer vlPlayer2: playerData.getPlayerViolations())
		{
			if (vlPlayer2.getPlayer().equals(player) && vlPlayer2.getCheck().equals(check))
			{
				vlPlayer = vlPlayer2;
			}
		}
		
		if (vlPlayer == null)
		{
			vlPlayer = new VLPlayer(player, check);
			vlPlayer.setViolations(0);
			playerData.getPlayerViolations().add(vlPlayer);
		}
		
		return vlPlayer.getViolations();	
	}
}