package it.gabrielebologna.grandanticheat.update;
 
import org.bukkit.Bukkit;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;

public class Updater implements Runnable
{
    private int updater;

    public Updater()
    {
        this.updater = Bukkit.getScheduler().scheduleSyncRepeatingTask(GrandAntiCheat.getPlugin(), this, 0, 1);
    }

    public void Disable()
    {
        Bukkit.getScheduler().cancelTask(this.updater);
    }

    @Override
    public void run()
    {
        UpdateType[] arrupdateType = UpdateType.values();
        int n = arrupdateType.length;
        int n2 = 0;
        
        while (n2 < n)
        {
            UpdateType updateType = arrupdateType[n2];
            
            if (updateType != null && updateType.Elapsed())
            {
                try
                {
                    UpdateEvent event = new UpdateEvent(updateType);
                    Bukkit.getPluginManager().callEvent(event);
                }
                catch (Exception ex)
                {
                	
                }
            }
            
            ++n2;
        }
    }
}