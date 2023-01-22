package it.gabrielebologna.grandanticheat.check.render;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;

public class HealthTags extends Check
{
	public HealthTags()
	{
		super("HealthTags");
		
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(GrandAntiCheat.getPlugin(), ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_METADATA)
        {
        	public void onPacketSending(PacketEvent event)
        	{
        		try
        		{
        			Player observer = event.getPlayer();
        			StructureModifier<Entity> entityModifer = event.getPacket().getEntityModifier(observer.getWorld());
        			org.bukkit.entity.Entity entity = entityModifer.read(0);
        			
        			if (entity != null && observer != entity && entity instanceof LivingEntity && !(entity instanceof EnderDragon && entity instanceof Wither) && (entity.getPassenger() == null || entity.getPassenger() != observer))
        			{
        				event.setPacket(event.getPacket().deepClone());
        				StructureModifier<List<WrappedWatchableObject>> watcher = event.getPacket().getWatchableCollectionModifier();
        				
        				for (WrappedWatchableObject watch : watcher.read(0))
        				{
        					if (watch.getIndex() == 6)
        					{
        						if ((float) (watch.getValue()) > 0.0F)
        						{
        							watch.setValue(((float) 0.1F));
        						}
        					}
        					else if (watch.getIndex() == 7)
        					{
        						watch.setValue(((int) 0));
        					}
        				}
        			}
        		}
        		catch (Exception ex)
        		{
        			
        		}
        	}
        });
	}
	
    @EventHandler
    public void onMount(final VehicleEnterEvent event)
    {
        if (event.getEntered() instanceof Player)
        {
            Bukkit.getScheduler().scheduleSyncDelayedTask(GrandAntiCheat.getPlugin(), new Runnable()
            {
                public void run()
                {
                    if (event.getVehicle().isValid() && event.getEntered().isValid())
                    {
                    	ProtocolLibrary.getProtocolManager().updateEntity(event.getVehicle(), Arrays.asList(new Player[] { (Player) event.getEntered() }));
                    }
                }
            });
        }
    }
}