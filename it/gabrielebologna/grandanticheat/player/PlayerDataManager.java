package it.gabrielebologna.grandanticheat.player;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;

public class PlayerDataManager
{
	private ArrayList<PlayerData> playerDatas;
	
	public PlayerDataManager()
	{
		playerDatas = new ArrayList<PlayerData>();
	}
	
	public ArrayList<PlayerData> getPlayerDatas()
	{
		return playerDatas;
	}
	
	@SuppressWarnings("deprecation")
	public void addPlayerData(Player player)
	{
		PlayerData playerData = new PlayerData(player);
		playerData.setJoinedNow(true);
		playerDatas.add(playerData);
		everySecondAction(player, playerData);
		
		Bukkit.getScheduler().runTaskLater(GrandAntiCheat.getPlugin(), new BukkitRunnable()
		{	
			@Override
			public void run()
			{
				playerData.setJoinedNow(false);
			}
		}, 100L);
	}
	
	public void removePlayerData(Player player)
	{
		for (PlayerData playerData: playerDatas)
		{
			if (playerData.getPlayer().equals(player))
			{
				playerDatas.remove(playerData);
				return;
			}
		}
	}
	
	public PlayerData getPlayerData(Player player)
	{
		for (PlayerData playerData: playerDatas)
		{
			if (playerData.getPlayer().equals(player))
			{
				return playerData;
			}
		}
		
		addPlayerData(player);
		
		for (PlayerData playerData: playerDatas)
		{
			if (playerData.getPlayer().equals(player))
			{
				return playerData;
			}
		}
		
		return null;
	}
	
	public boolean isPlayerPresent(Player player)
	{
		for (PlayerData playerData: playerDatas)
		{
			if (playerData.getPlayer().equals(player))
			{
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void everySecondAction(Player player, PlayerData playerData)
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(GrandAntiCheat.getPlugin(), new BukkitRunnable()
		{	
			@Override
			public void run()
			{
				playerData.setDrops(0);
			}
		}, 0L, 10L);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(GrandAntiCheat.getPlugin(), new BukkitRunnable()
		{	
			@Override
			public void run()
			{
				playerData.setFlyingPackets(0);
				playerData.setArmSwings(0);
				playerData.setEntityUses(0);
				playerData.setEntityAttacks(0);
				playerData.setPositionPackets(0);
				playerData.setPositionLookPackets(0);			
				playerData.setLeftClicks(0);
				playerData.setRightClicks(0);
				playerData.setMoves(0);
				playerData.setLookPackets(0);
				playerData.setPayloadPackets(0);
				playerData.setPlayerPackets(0);						
				playerData.setTeleports(0);
				playerData.setInventoryClicks(0);
				playerData.setMoves2(0);
			}
		}, 0L, 20L);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(GrandAntiCheat.getPlugin(), new BukkitRunnable()
		{	
			@Override
			public void run()
			{
				playerData.setFlyingPackets1(0);
				playerData.setArmSwings1(0);
				playerData.setEntityUses1(0);
				playerData.setEntityAttacks1(0);
				playerData.setPositionPackets1(0);
				playerData.setPositionLookPackets1(0);
				playerData.setLeftClicks1(0);
				playerData.setRightClicks1(0);
				playerData.setMoves1(0);
				playerData.setLookPackets1(0);
				playerData.setPayloadPackets1(0);
				playerData.setPlayerPackets1(0);
				playerData.setTeleports1(0);
				playerData.setScaffoldBlocksPlaced(0);
				playerData.setInventoryClicks1(0);
			}
		}, 0L, 40L);
	}
}