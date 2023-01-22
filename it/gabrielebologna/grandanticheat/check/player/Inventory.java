package it.gabrielebologna.grandanticheat.check.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;

public class Inventory extends Check
{
	public Inventory()
	{
		super("Inventory");
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
		
		if (playerData.isReallyInventoryOpened())
		{
			GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type A - Move", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
		}
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
		
		if (playerData.isReallyInventoryOpened())
		{
			GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type B - Chat", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
		}
	}
	
	@EventHandler
	public void onMove(BlockDamageEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
		
		if (playerData.isReallyInventoryOpened())
		{
			GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type C - Dig", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
		}
	}
	
	@EventHandler
	public void onMove(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
		
		if (playerData.isReallyInventoryOpened())
		{
			GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type D - Break", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
		}
	}
	
	@EventHandler
	public void onMove(PlayerItemConsumeEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
		
		if (playerData.isReallyInventoryOpened())
		{
			GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type E - Consume", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
		}
	}
	
	@EventHandler
	public void onMove(EntityDamageByEntityEvent event)
	{
		if (event.getDamager() instanceof Player)
		{
			Player player = (Player) event.getDamager();
			PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
			
			if (!playerData.canBeChecked())
			{
				return;
			}
			
			if (playerData.isReallyInventoryOpened())
			{
				GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type F - Attack", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
		
		if (playerData.isReallyInventoryOpened())
		{
			GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type G - Interact", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
		}
	}
	
	@EventHandler
	public void onMove(PlayerToggleSprintEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
		
		if (playerData.isReallyInventoryOpened())
		{
			GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type H - Sprint", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS(), true);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (!playerData.canBeChecked())
		{
			return;
		}
		
		long time = System.currentTimeMillis();
		long lastDrop = playerData.getLastDrop();
		
		boolean canContinue = true;
		
		if (lastDrop == 0)
		{
			canContinue = false;
		}
		
		if (canContinue)
		{
			if (time - lastDrop < 40L)
			{
				GrandAntiCheat.getFlagManager().flagPlayer(player, this, "Type I - Drop", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", time: " + (time - lastDrop), true);
			}
		}
		
		playerData.setLastDrop(System.currentTimeMillis());
	}
}