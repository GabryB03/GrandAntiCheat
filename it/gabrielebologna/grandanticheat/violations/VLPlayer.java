package it.gabrielebologna.grandanticheat.violations;

import org.bukkit.entity.Player;

import it.gabrielebologna.grandanticheat.check.Check;

public class VLPlayer
{
	private Player player;
	private Check check;
	private int violations;
	
	public VLPlayer(Player player, Check check)
	{
		this.player = player;
		this.check = check;
		this.violations = 0;
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public Check getCheck()
	{
		return check;
	}

	public void setCheck(Check check)
	{
		this.check = check;
	}

	public int getViolations()
	{
		return violations;
	}

	public void setViolations(int violations)
	{
		this.violations = violations;
	}
}