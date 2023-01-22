package it.gabrielebologna.grandanticheat.check.combat;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayClientUseEntity;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.check.Check;
import it.gabrielebologna.grandanticheat.player.PlayerData;
import it.gabrielebologna.grandanticheat.utils.UtilMath;
import it.gabrielebologna.grandanticheat.utils.hitbox.HitboxUtils;

public class Reach extends Check
{
	public Reach()
	{
		super("Reach");
		
		Check chick = this;
		
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(GrandAntiCheat.getPlugin(), PacketType.Play.Client.USE_ENTITY)
        {
            public void onPacketReceiving(final PacketEvent event)
            {
                Player player = event.getPlayer();
                PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
                
                if (!playerData.canBeChecked())
                {
                	return;
                }
                
                WrapperPlayClientUseEntity wrapperPlayClientUseEntity = new WrapperPlayClientUseEntity(event.getPacket());
                
                if (!wrapperPlayClientUseEntity.getType().equals(EntityUseAction.ATTACK))
                {
                	return;
                }
                
                Entity entity = wrapperPlayClientUseEntity.getTarget(player.getWorld());                
                double max = 0.2D + (double)UtilMath.pingFormula((long)(playerData.getPing() + 2));
                
                try
                {
                	double reach = HitboxUtils.getDistBetweenHitboxes(player, entity);
                	double dist = (double)UtilMath.getDistanceXZToEntity(player, entity) - chick.getSettings().getDouble("max-reach");
                	
                	double maxReach = GrandAntiCheat.isDebugMode() ? chick.getSettings().getDouble("max-reach") : 3.3D;
                	
                	if (reach >= maxReach && dist > max)
                    {
                    	playerData.setVerbose16(playerData.getVerbose16() + 1);
                    	
                        if (playerData.getVerbose16() > 1)
                        {
                            GrandAntiCheat.getFlagManager().flagPlayer(player, chick, "Type A - Invalid distance", "Ping: " + playerData.getPing() + ", TPS: " + GrandAntiCheat.getLagManager().getTPS() + ", reach: " + reach + ", dist: " + dist + ", max: " + max, true);
                        }
                    }
                    else
                    {
                        playerData.setVerbose16(0);
                    }
                }
                catch (Exception ex)
                {
                	
                }
            }
        });
	}
	
	@Override
	public void setup()
	{
		if (GrandAntiCheat.isDebugMode())
		{
			this.getSettings().addDefault("max-reach", 3.3D);
		}
	}
}