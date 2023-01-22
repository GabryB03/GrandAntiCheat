package it.gabrielebologna.grandanticheat.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketPlayerMoveEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
	private Location from;
	private Location to;
	private Player player;
	
	public PacketPlayerMoveEvent(Location from, Location to, Player player)
	{
		this.from = from;
		this.to = to;
		this.player = player;
	}

	public Location getFrom()
	{
		return from;
	}

	public void setFrom(Location from)
	{
		this.from = from;
	}

	public Location getTo()
	{
		return to;
	}

	public void setTo(Location to)
	{
		this.to = to;
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public HandlerList getHandlers()
	{
		return handlers;
	}
	
    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}