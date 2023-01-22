package it.gabrielebologna.grandanticheat.check;

import java.util.ArrayList;

public class CheckManager
{
	private ArrayList<Check> checks;
	
	public CheckManager()
	{
		checks = new ArrayList<>();
	}
	
	public ArrayList<Check> getChecks()
	{
		return checks;
	}
	
	public void addCheck(Check check)
	{
		checks.add(check);
	}
	
	public Check getCheckByName(String name)
	{
		for (Check check: getChecks())
		{
			if (check.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase()))
			{
				return check;
			}
		}
		
		return null;
	}
}