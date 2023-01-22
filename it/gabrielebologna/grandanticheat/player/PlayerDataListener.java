package it.gabrielebologna.grandanticheat.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.comphenix.packetwrapper.WrapperPlayClientEntityAction;
import com.comphenix.packetwrapper.WrapperPlayClientTransaction;
import com.comphenix.packetwrapper.WrapperPlayClientUseEntity;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerAction;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.event.PacketPlayerMoveEvent;
import it.gabrielebologna.grandanticheat.update.UpdateEvent;
import it.gabrielebologna.grandanticheat.utils.UtilBlock;
import it.gabrielebologna.grandanticheat.utils.UtilMath;
import it.gabrielebologna.grandanticheat.utils.UtilPlayer;

public class PlayerDataListener implements Listener
{
	private PacketType[] playerPackets = {PacketType.Play.Server.KEEP_ALIVE, PacketType.Play.Server.TRANSACTION, PacketType.Play.Client.POSITION, PacketType.Play.Client.TRANSACTION, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.LOOK, PacketType.Play.Client.FLYING, PacketType.Play.Client.ARM_ANIMATION, PacketType.Play.Client.CUSTOM_PAYLOAD, PacketType.Play.Client.USE_ENTITY, PacketType.Play.Client.KEEP_ALIVE, PacketType.Play.Client.BLOCK_DIG, PacketType.Play.Client.ENTITY_ACTION, PacketType.Play.Client.WINDOW_CLICK, PacketType.Play.Client.CLOSE_WINDOW};
	
	public PlayerDataListener()
	{
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(GrandAntiCheat.getPlugin(), playerPackets)
        {
            public void onPacketSending(final PacketEvent event)
            {
                final PacketContainer packet = event.getPacket();
                final Player player = event.getPlayer();
                PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);   
                
                if (packet.getType().equals(PacketType.Play.Server.KEEP_ALIVE))
                {
                	playerData.setLastKeepAlive(System.currentTimeMillis());
                }
                else if (packet.getType().equals(PacketType.Play.Server.TRANSACTION))
                {
                	playerData.setLastTransactionPacket(System.currentTimeMillis());
                }
            }
        	
            public void onPacketReceiving(final PacketEvent event)
            {
                final PacketContainer packet = event.getPacket();
                final Player player = event.getPlayer();
                PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);   

                if (packet.getType().equals(PacketType.Play.Client.KEEP_ALIVE))
                {
                	playerData.setPing1((int) System.currentTimeMillis() - playerData.getLastKeepAlive());
                	playerData.setKeepAliveLag(playerData.getPing1() > 250);
                }
                
                if (packet.getType().equals(PacketType.Play.Client.TRANSACTION))
                {
                	WrapperPlayClientTransaction wrapperPlayClientTransaction = new WrapperPlayClientTransaction(packet);
                	
                	if (wrapperPlayClientTransaction.getWindowId() == 55)
                	{
                    	double diff = System.currentTimeMillis() - playerData.getLastTransactionPacket();
                    	playerData.setTransLag(!(diff < 150));
                	}
                }
     
                if (packet.getType().equals(PacketType.Play.Client.FLYING) || packet.getType().equals(PacketType.Play.Client.LOOK) || packet.getType().equals(PacketType.Play.Client.POSITION) || packet.getType().equals(PacketType.Play.Client.POSITION_LOOK))
                {
                	playerData.setTicks9(playerData.getTicks9() + 1);
                	
                	if (System.currentTimeMillis() - playerData.getLastLagCheck() > 100L && System.currentTimeMillis() - playerData.getUserCreated() > 1000L)
                	{
                		playerData.setSkippedTicks(playerData.getSkippedTicks() + (int) (((System.currentTimeMillis() - playerData.getLastLagCheck()) - 50) / 25));
                	}
                	else
                	{
                		playerData.setSkippedTicks(playerData.getSkippedTicks() - (playerData.getSkippedTicks() > 0 ? 1 : 0));
                	}
                	
                	double diff = (playerData.getSkippedTicks() - playerData.getLastSkippedTicks()) + 1;
                	
                	playerData.setLagging1(diff > 3);
                	playerData.setLastSkippedTicks(playerData.getSkippedTicks());
                	playerData.setLastLagCheck(System.currentTimeMillis());
                	
                	if (player.getTicksLived() % 50 == 0)
                	{
                		WrapperPlayClientTransaction wrapperPlayClientTransaction = new WrapperPlayClientTransaction();
                    	wrapperPlayClientTransaction.setAccepted(false);
                    	wrapperPlayClientTransaction.setActionNumber((short) 0);
                    	wrapperPlayClientTransaction.setWindowId((byte) 55);
                    	wrapperPlayClientTransaction.receivePacket(event.getPlayer());
                	}
                	
                	double v = System.currentTimeMillis() - playerData.getLastPacket1();
                	double value = 50;
                	value -= v;
                	
                	if (value < -80)
                	{
                		double lastLag = System.currentTimeMillis() - playerData.getLastLag();
                		
                		if (lastLag < 900)
                		{
                			playerData.setOldLagging(true);
                			playerData.setLastLagSet(System.currentTimeMillis());
                		}
                		
                		playerData.setLastLag(System.currentTimeMillis());
                	}
                	
                	if (System.currentTimeMillis() - playerData.getLastLagSet() > 1000L)
                	{
                		playerData.setOldLagging(false);
                	}
                	
                	playerData.setLastPacket1(System.currentTimeMillis());
                }
                
                if (packet.getType().equals(PacketType.Play.Client.KEEP_ALIVE) || packet.getType().equals(PacketType.Play.Client.TRANSACTION) || packet.getType().equals(PacketType.Play.Client.FLYING) || packet.getType().equals(PacketType.Play.Client.LOOK) || packet.getType().equals(PacketType.Play.Client.POSITION) || packet.getType().equals(PacketType.Play.Client.POSITION_LOOK))
                {
                	boolean canProceed = true;
                	
                	if (packet.getType().equals(PacketType.Play.Client.TRANSACTION))
                	{
                    	WrapperPlayClientTransaction wrapperPlayClientTransaction = new WrapperPlayClientTransaction(packet);
                    	
                    	if (wrapperPlayClientTransaction.getWindowId() != 55)
                    	{
                    		canProceed = false;
                    	}
                	}
                	
                	if (canProceed)
                	{
                    	if (playerData.isOldLagging())
                    	{
                    		playerData.setLastSetOldLag(System.currentTimeMillis());
                    	}
                    	
                    	if (playerData.isLagging1())
                    	{
                    		playerData.setLastSetTickLag(System.currentTimeMillis());
                    	}
                    	
                    	boolean lagging = (playerData.isLagging1() || playerData.isOldLagging()) && (playerData.getPing1() > 200 && playerData.isTransLag());
                    	playerData.setMasterLag(lagging);
                	}
                }

                if (packet.getType().equals(PacketType.Play.Client.ENTITY_ACTION))
                {
                	WrapperPlayClientEntityAction wrapperPlayClientEntityAction = new WrapperPlayClientEntityAction(packet);
                	
                	if (wrapperPlayClientEntityAction.getAction().equals(PlayerAction.START_SPRINTING))
                	{
                		playerData.setLastSprint(System.currentTimeMillis());
                	}
                }
                
                if (packet.getType().equals(PacketType.Play.Client.WINDOW_CLICK))
                {
                	playerData.setLastInventoryClick(System.currentTimeMillis());
                	playerData.setInventoryClicks(playerData.getInventoryClicks() + 1);
                	playerData.setInventoryClicks1(playerData.getInventoryClicks1() + 1);
                	
                	if (!playerData.isInventoryOpened())
                	{
                		playerData.setLastInventoryOpen(System.currentTimeMillis());
                	}
                	
                	playerData.setInventoryOpened(true);
                }
                
                if (packet.getType().equals(PacketType.Play.Client.CLOSE_WINDOW))
                {
                	playerData.setLastInventoryClose(System.currentTimeMillis());
                	playerData.setInventoryOpened(false);
                }
                
                if (packet.getType().equals(PacketType.Play.Client.POSITION_LOOK) || packet.getType().equals(PacketType.Play.Client.POSITION) || packet.getType().equals(PacketType.Play.Client.LOOK) || packet.getType().equals(PacketType.Play.Client.FLYING))
                {
                    playerData.setServerOnGround(packet.getBooleans().getValues().get(0));
                    playerData.setLastPlayerPacket1(playerData.getLastPlayerPacket());
                    playerData.setLastPlayerPacket(System.currentTimeMillis());
                    playerData.setPlayerPackets(playerData.getPlayerPackets() + 1);
                    playerData.setPlayerPackets1(playerData.getPlayerPackets1() + 1);
                    
                    if (!packet.getType().equals(PacketType.Play.Client.FLYING))
                    {
                    	playerData.setLastPositionX(playerData.getPositionX());
                    	playerData.setLastPositionY(playerData.getPositionY());
                    	playerData.setLastPositionZ(playerData.getPositionZ());
                    	playerData.setPositionX(packet.getDoubles().getValues().get(0));
                    	playerData.setPositionY(packet.getDoubles().getValues().get(1));
                    	playerData.setPositionZ(packet.getDoubles().getValues().get(2));
                    	playerData.setLastServerSpeedXZ(playerData.getServerSpeedXZ());
                    	playerData.setServerSpeedXZ(UtilMath.offset(new Vector(playerData.getLastPositionX(), playerData.getLastPositionY(), playerData.getLastPositionZ()), new Vector(playerData.getPositionX(), playerData.getPositionY(), playerData.getPositionZ())));
                    	
                    	if (playerData.getLastPositionX() != playerData.getPositionX() && playerData.getLastPositionZ() != playerData.getPositionZ())
                    	{
                    		playerData.getServerSpeedOffsets().add(playerData.getServerSpeedXZ());
                    	}
                    }
                    else if (packet.getType().equals(PacketType.Play.Client.FLYING))
                    {
                    	WrapperPlayClientTransaction wrapperPlayClientTransaction = new WrapperPlayClientTransaction();
                    	wrapperPlayClientTransaction.setAccepted(false);
                    	wrapperPlayClientTransaction.setActionNumber((short) 0);
                    	wrapperPlayClientTransaction.setWindowId((byte) 111);
                    	wrapperPlayClientTransaction.receivePacket(event.getPlayer());
                    	playerData.setLastFlyingPacket1(playerData.getLastFlyingPacket());
                    	playerData.setLastFlyingPacket(System.currentTimeMillis());      
                    	playerData.setFlyingPackets(playerData.getFlyingPackets() + 1);
                    	playerData.setFlyingPackets1(playerData.getFlyingPackets1() + 1);
                    }
                    
                    if (packet.getType().equals(PacketType.Play.Client.POSITION_LOOK) || packet.getType().equals(PacketType.Play.Client.LOOK))
                    {
                    	playerData.setLastRotationYaw(playerData.getRotationYaw());
                    	playerData.setLastRotationPitch(playerData.getRotationPitch());
                    	playerData.setRotationYaw(packet.getFloat().read(0));
                    	playerData.setRotationPitch(packet.getFloat().read(1));
                    	playerData.setYawDelta(playerData.getLastRotationYaw() - playerData.getRotationYaw());
                    	playerData.setPitchDelta(playerData.getLastRotationPitch() - playerData.getRotationPitch());
                    	playerData.setRotationOffset((float)(UtilMath.offset(new Vector(playerData.getRotationYaw(), 0, playerData.getRotationPitch()), new Vector(playerData.getRotationYaw(), 0, playerData.getRotationPitch()))));
                    }
                    
                    if (packet.getType().equals(PacketType.Play.Client.POSITION))
                    {
                    	playerData.setLastPositionPacket1(playerData.getLastPositionPacket());
                    	playerData.setLastPositionPacket(System.currentTimeMillis());
                    	playerData.setPositionPackets(playerData.getPositionPackets() + 1);
                    	playerData.setPositionPackets1(playerData.getPositionPackets1() + 1);
                    }
                    else if (packet.getType().equals(PacketType.Play.Client.POSITION_LOOK))
                    {
                    	playerData.setLastPositionLookPacket1(playerData.getLastPositionLookPacket());
                    	playerData.setLastPositionLookPacket(System.currentTimeMillis());
                    	playerData.setPositionLookPackets(playerData.getPositionLookPackets() + 1);
                    	playerData.setPositionLookPackets1(playerData.getPositionLookPackets1() + 1);
                    }
                    else if (packet.getType().equals(PacketType.Play.Client.LOOK))
                    {
                    	playerData.setLastLookPacket1(playerData.getLastLookPacket());
                    	playerData.setLastLookPacket(System.currentTimeMillis());
                    	playerData.setLookPackets(playerData.getLookPackets() + 1);
                    	playerData.setLookPackets1(playerData.getLookPackets1() + 1);
                    }
                    
                    if (packet.getType().equals(PacketType.Play.Client.POSITION) || packet.getType().equals(PacketType.Play.Client.POSITION_LOOK))
                    {
                    	playerData.setLoc2(playerData.getLoc1());
                    	playerData.setLoc1(new Location(player.getWorld(), packet.getDoubles().read(0), packet.getDoubles().read(1), packet.getDoubles().read(2), playerData.getRotationYaw(), playerData.getRotationPitch()));
                    	
                    	if (UtilMath.moved(playerData.getLoc1(), playerData.getLoc2()) && playerData.getLoc1() != playerData.getLoc2())
                    	{
                    		Bukkit.getServer().getPluginManager().callEvent(new PacketPlayerMoveEvent(playerData.getLoc1(), playerData.getLoc2(), player));
                    	}
                    }
                }
                else
                {
                	if (packet.getType().equals(PacketType.Play.Client.ARM_ANIMATION))
                	{
                		playerData.setLastArmSwing1(playerData.getLastArmSwing());
                		playerData.setLastArmSwing(System.currentTimeMillis());
                		playerData.setArmSwings(playerData.getArmSwings() + 1);
                		playerData.setArmSwings1(playerData.getArmSwings1() + 1);
                	}
                	else if (packet.getType().equals(PacketType.Play.Client.USE_ENTITY))
                	{
                		WrapperPlayClientUseEntity wrapperPlayClientUseEntity = new WrapperPlayClientUseEntity(packet);
                		
                		if (!wrapperPlayClientUseEntity.getType().equals(EntityUseAction.ATTACK))
                		{
                			return;
                		}
                		
                		playerData.setLastEntityUse1(playerData.getLastEntityUse());
                		playerData.setLastEntityUse(System.currentTimeMillis());
                		playerData.setEntityUses(playerData.getEntityUses() + 1);
                		playerData.setEntityUses1(playerData.getEntityUses1() + 1);
                		
                		for (LivingEntity livingEntity: player.getWorld().getLivingEntities())
                		{
                			if (livingEntity.getEntityId() == wrapperPlayClientUseEntity.getTargetID())
                			{
                				playerData.setLastEntityUsed(livingEntity);
                				playerData.setYawDiff(UtilMath.getYawDifference(player.getLocation().getYaw(), livingEntity.getLocation().getYaw()));
                			}
                		}
                	}
                	else if (packet.getType().equals(PacketType.Play.Client.CUSTOM_PAYLOAD))
                	{
                		playerData.setLastPayloadPacket1(playerData.getLastPayloadPacket());
                		playerData.setLastPayloadPacket(System.currentTimeMillis());
                		playerData.setPayloadPackets(playerData.getPayloadPackets() + 1);
                		playerData.setPayloadPackets1(playerData.getPayloadPackets1() + 1);
                	}
                	else if (packet.getType().equals(PacketType.Play.Client.KEEP_ALIVE))
                	{
                		playerData.setLastKeepAlivePacket1(playerData.getLastKeepAlivePacket());
                		playerData.setLastKeepAlivePacket(System.currentTimeMillis());
                		playerData.setKeepAlivePackets(playerData.getKeepAlivePackets() + 1);
                		playerData.setKeepAlivePackets1(playerData.getKeepAlivePackets1() + 1);	
                	}
                }
                
                if (packet.getType().equals(PacketType.Play.Client.BLOCK_DIG))
                {
                	playerData.setLastDig(System.currentTimeMillis());
                }
            }
        });
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event)
	{
		if (event.getDamager() instanceof Player)
		{ 
			Player player = (Player) event.getDamager();
			PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
			playerData.setLastEntityAttack1(playerData.getLastEntityAttack());
			playerData.setLastEntityAttack(System.currentTimeMillis());
			playerData.setEntityAttacks(playerData.getEntityAttacks() + 1);
			playerData.setEntityAttacks1(playerData.getEntityAttacks() + 1);
			playerData.setLastEntityAttacked((LivingEntity) event.getEntity());
		}
	}
	
	@EventHandler
	public void onPacketMove(PacketPlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		playerData.setMoves2(playerData.getMoves2() + 1);
		playerData.setLastMotionX(playerData.getMotionX());
		playerData.setLastMotionY(playerData.getMotionY());
		playerData.setLastMotionZ(playerData.getMotionZ());
		playerData.setMotionY(event.getTo().getY() - event.getFrom().getY());
		playerData.setMotionX(event.getTo().getX() - event.getFrom().getX());
		playerData.setMotionZ(event.getTo().getZ() - event.getFrom().getZ());
	}
	
	@SuppressWarnings({ "static-access" })
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();		
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		
		if (event.getAction().equals(event.getAction().LEFT_CLICK_AIR) || event.getAction().equals(event.getAction().LEFT_CLICK_BLOCK))
		{
			playerData.setLastLeftClick1(playerData.getLastLeftClick());
			playerData.setLastLeftClick(System.currentTimeMillis());
			playerData.setLeftClicks(playerData.getRightClicks() + 1);
			playerData.setLeftClicks1(playerData.getLeftClicks1() + 1);
		}
		else if (event.getAction().equals(event.getAction().RIGHT_CLICK_AIR) || event.getAction().equals(event.getAction().RIGHT_CLICK_BLOCK))
		{
			playerData.setLastRightClick1(playerData.getLastRightClick());
			playerData.setLastRightClick(System.currentTimeMillis());
			playerData.setRightClicks(playerData.getRightClicks() + 1);
			playerData.setRightClicks1(playerData.getLeftClicks1() + 1);
		}
	}
	
	/*@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event)
	{
		Player player = event.getPlayer();		
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		playerData.setLastWorldChange(System.currentTimeMillis());
	}*/
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		if (!(event.getEntity() instanceof Player))
		{
			return;
		}
		
		if (event.getCause().equals(DamageCause.FALL))
		{
			Player player = (Player) event.getEntity();			
			PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
			playerData.setLastFallDamage1(playerData.getLastFallDamage());
			playerData.setLastFallDamage(System.currentTimeMillis());
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		GrandAntiCheat.getPlayerDataManager().addPlayerData(event.getPlayer());
		GrandAntiCheat.getVLManager().resetVL(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		GrandAntiCheat.getPlayerDataManager().removePlayerData(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event)
	{
		if (event.isCancelled())
		{
			return;
		}
		
		if (event.getReason().toLowerCase().contains("flying is not enabled"))
		{
			return;
		}
		
		GrandAntiCheat.getPlayerDataManager().removePlayerData(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
        Player player = event.getPlayer();    
        PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);    
        
        double differancePitch = Math.abs(event.getTo().getPitch() - event.getFrom().getPitch());
        double lastPitch = playerData.getLastPitchDifferance();
        playerData.setLastPitchDifferance(differancePitch);

        double differanceYaw = Math.abs(event.getTo().getYaw() - event.getFrom().getYaw());
        double lastYaw = playerData.getLastYawDifferance();
        playerData.setLastYawDifferance(differanceYaw);

        double yawSmooth = Math.abs(differanceYaw-lastYaw) % 360;
        double pitchSmooth = Math.abs(differancePitch-lastPitch) % 180;

        if ((yawSmooth < 0.08 && yawSmooth > 0) || (pitchSmooth < 0.01 && pitchSmooth > 0))
        {
            playerData.setOptifineTicks(playerData.getOptifineTicks() + 1);
        }
        else
        {
            playerData.setOptifineTicks(playerData.getOptifineTicks() > 0 ? playerData.getOptifineTicks() - 1 : 0);
        }
        
        if (event.getTo() != event.getFrom() && event.getTo().getX() != event.getFrom().getX() && event.getFrom().getZ() != event.getTo().getZ())
        {
        	playerData.getSpeedOffsets().add(UtilMath.offset(UtilMath.getHV(event.getFrom().toVector()), UtilMath.getHV(event.getTo().toVector())));
        }
        
        playerData.setLastLoc(playerData.getCurrentLoc());
        playerData.setCurrentLoc(player.getLocation());
        playerData.setLastMove1(playerData.getLastMove());
        playerData.setLastMove(System.currentTimeMillis());
        playerData.setMoves(playerData.getMoves() + 1);
        playerData.setMoves1(playerData.getMoves1() + 1);
        Vector velocity = player.getVelocity();
        
        if (velocity.getY() > 0)
        {
            double jumpVelocity = (double) 0.42F;
            
            if (player.hasPotionEffect(PotionEffectType.JUMP))
            {
            	for (PotionEffect effect: player.getActivePotionEffects())
            	{
            		if (effect.getType().equals(PotionEffectType.JUMP))
            		{
                        jumpVelocity += (double) ((float) (effect.getAmplifier() + 1) * 0.1F);
                        break;
            		}
            	}
            }

            if (player.getLocation().getBlock().getType() != Material.LADDER && Double.compare(velocity.getY(), jumpVelocity) == 0)
            {
                playerData.setJumping(true);
            }
            else
            {
            	playerData.setJumping(false);
            }
        }
        else
        {
        	playerData.setJumping(false);
        }
        
        playerData.setClientLastDeltaY(playerData.getClientDeltaY());
        playerData.setClientDeltaY(event.getTo().getY() - event.getFrom().getY());
        
        if (event.getTo().getY() != event.getFrom().getY() && event.getTo().getY() != 0 && event.getFrom().getY() != 0 && player.getFallDistance() <= 2.0D)
        {
        	playerData.getFlyOffsets().add(event.getTo().getY() - event.getFrom().getY());
        }
        
        playerData.setLastClientSpeedXZ(playerData.getClientSpeedXZ());
        playerData.setClientSpeedXZ(UtilMath.getHorizontalDistance(event.getFrom(), event.getTo()));
        playerData.setYawDist(event.getTo().getYaw() - event.getFrom().getYaw());
        playerData.setPitchDist(event.getTo().getPitch() - event.getFrom().getPitch());
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		playerData.setLastTeleport1(playerData.getLastTeleport());
		playerData.setLastTeleport(System.currentTimeMillis());
		playerData.setTeleports(playerData.getTeleports() + 1);
		playerData.setTeleports1(playerData.getTeleports1() + 1);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		playerData.setDrops(playerData.getDrops() + 1);
	}
	
	@EventHandler
	public void onVelocity(PlayerVelocityEvent event)
	{
		Player player = event.getPlayer();
		PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
		playerData.setLastVelocityEvent(System.currentTimeMillis());
	}
	
	@EventHandler
	public void onUpdate(UpdateEvent event)
	{	
		for (Player player: Bukkit.getOnlinePlayers())
		{           
			PlayerData playerData = GrandAntiCheat.getPlayerDataManager().getPlayerData(player);
			
			if (!player.isFlying() && player.getLocation().subtract(0, 0.1, 0).getBlock().getType().isSolid() && !player.getAllowFlight())
			{
				if (playerData.getClientAirTicks() != 0)
				{
					playerData.setLastClientAirTime(System.currentTimeMillis());
					playerData.setLastClientAirTicks(playerData.getClientAirTicks());
				}
				
				playerData.setClientOnGround(true);
				playerData.setClientAirTicks(0);
				playerData.setClientGroundTicks(playerData.getClientGroundTicks() + 1);
			}
			else
			{
				if (playerData.getClientGroundTicks() != 0)
				{
					playerData.setLastClientGroundTime(System.currentTimeMillis());
					playerData.setLastClientGroundTicks(playerData.getClientGroundTicks());
					playerData.setInitialDistanceFromGround(UtilPlayer.getDistanceToGround(player));
					playerData.setDistanceFromGround(0.0D);
					playerData.setInitialFallHealth(player.getHealth());
					playerData.setInitialFall(true);
				}
				
				playerData.setClientOnGround(false);
				playerData.setClientGroundTicks(0);
				playerData.setClientAirTicks(playerData.getClientAirTicks() + 1);
				playerData.setDistanceFromGround(UtilPlayer.getDistanceToGround(player));
			}
			
			if (playerData.isServerOnGround())
			{
				if (playerData.getServerAirTicks() != 0)
				{
					playerData.setLastServerAirTime(System.currentTimeMillis());
					playerData.setLastServerAirTicks(playerData.getServerAirTicks());
				}
				
				playerData.setServerAirTicks(0);
				playerData.setServerGroundTicks(playerData.getServerGroundTicks() + 1);
			}
			else
			{
				if (playerData.getServerGroundTicks() != 0)
				{
					playerData.setLastServerGroundTime(System.currentTimeMillis());
					playerData.setLastServerGroundTicks(playerData.getServerGroundTicks());
				}
				
				playerData.setServerGroundTicks(0);
				playerData.setServerAirTicks(playerData.getServerAirTicks() + 1);
			}
			
			if (playerData.isHasJumped())
			{
				playerData.setHasJumped(false);
				playerData.setInAir(true);
			}
			else if (playerData.isClientOnGround())
			{
				playerData.setInAir(false);
			}
			else if (!playerData.isInAir())
			{
				playerData.setHasJumped(true);
			}
			
			playerData.setServerLastDeltaY(playerData.getServerDeltaY());
			playerData.setServerDeltaY(playerData.getLastPositionY() - playerData.getPositionY());
			playerData.setLastClientYAcceleration(playerData.getClientYAcceleration());
			playerData.setClientYAcceleration(playerData.getClientDeltaY() - playerData.getClientLastDeltaY());
			playerData.setLastServerYAcceleration(playerData.getServerYAcceleration());
			playerData.setServerYAcceleration(playerData.getServerDeltaY() - playerData.getServerLastDeltaY());
			playerData.setLastServerYVelocity(playerData.getServerYVelocity());
			
			if (playerData.isHasJumped())
			{
				playerData.setServerYVelocity(Math.min(playerData.getClientDeltaY(), 0.42));
			}
			else if (playerData.isInAir())
			{
				double thing = playerData.getServerYVelocity();
				thing -= 0.08F;
				thing *= 0.98F;
				playerData.setServerYVelocity(thing);
			}
			else
			{
				playerData.setServerYVelocity(0.0D);
			}
			
			try
			{
				Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
				int ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
				playerData.setPing(ping);
			}
			catch (Exception ex)
			{
				
			}
			
			if (playerData.getPing() > 145)
			{
				playerData.setLagging(true);
			}
			else
			{
				playerData.setLagging(false);
			}
			
			final Material m = player.getLocation().getBlock().getType();
			
			if (m == Material.WATER || m == Material.STATIONARY_WATER)
			{
				playerData.setInWater(true);
			}
			else
			{
				playerData.setInWater(false);
			}
			
            for (Block block : UtilBlock.getSurrounding(player.getLocation().getBlock(), false))
            {
                if (block.getType() == Material.LADDER || block.getType() == Material.VINE)
                {
                	playerData.setInLadder(true);
                }
                else
                {
                	playerData.setInLadder(false);
                }
            }
            
            if (UtilBlock.isFullyStuck(player))
            {
            	playerData.setCompletelyStuck(true);
            }
            else
            {
            	playerData.setCompletelyStuck(false);
            }
            
            if (UtilBlock.isPartiallyStuck(player))
            {
            	playerData.setPartiallyStuck(true);
            }
            else
            {
            	playerData.setPartiallyStuck(false);
            }
            
            if (UtilBlock.isInWeb(player))
            {
            	playerData.setInWeb(true);
            }
            else
            {
            	playerData.setInWeb(false);
            }
            
            if (UtilBlock.flaggyStuffNear(player.getLocation()))
            {
            	playerData.setFlaggyStuffNear(true);
            }
            else
            {
            	playerData.setFlaggyStuffNear(false);
            }
            
            Location origin = player.getEyeLocation();
            Vector direction = player.getLocation().getDirection();
            int range = 4;
            
            for (int i = 1; i <= range; i++)
            {
            	direction.add(new Vector(direction.getX() * i, direction.getY() * i, direction.getZ() * i));
            	
            	for (Entity entity : player.getNearbyEntities(origin.getX(), origin.getY(), origin.getZ()))
            	{
            		if (player.hasLineOfSight(entity))
            		{
            			playerData.setEntityInSight(entity);
            			break;
            		}
            	}
            }
            
            Location location1 = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1.0D, player.getLocation().getZ());
            Location location2 = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 0.5D, player.getLocation().getZ());
            
            if (player.getWorld().getBlockAt(location1).getType().equals(Material.ICE) || player.getWorld().getBlockAt(location2).getType().equals(Material.ICE) || player.getWorld().getBlockAt(location1).getType().equals(Material.PACKED_ICE) || player.getWorld().getBlockAt(location2).getType().equals(Material.PACKED_ICE))
            {
            	playerData.setOnIce(true);
            	
            	if (playerData.isOnSlime())
            	{
            		playerData.setLastSlime(System.currentTimeMillis());
            	}
            	
            	playerData.setOnSlime(false);
            }
            else if (player.getWorld().getBlockAt(location1).getType().equals(Material.SLIME_BLOCK) || player.getWorld().getBlockAt(location2).getType().equals(Material.SLIME_BLOCK))
            {
            	playerData.setOnSlime(true);
            	
            	if (playerData.isOnIce())
            	{
                	playerData.setLastIce(System.currentTimeMillis());
            	}
            	
            	playerData.setOnIce(false);
            }
            else
            {
            	if (playerData.isOnIce())
            	{
                	playerData.setLastIce(System.currentTimeMillis());
            	}
            	
            	if (playerData.isOnSlime())
            	{
            		playerData.setLastSlime(System.currentTimeMillis());
            	}
            	
            	playerData.setOnSlime(false);
            	playerData.setOnIce(false);
			}
		}
	}
}