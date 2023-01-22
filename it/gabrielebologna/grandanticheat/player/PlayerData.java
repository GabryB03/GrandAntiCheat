package it.gabrielebologna.grandanticheat.player;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.comphenix.protocol.events.PacketContainer;

import it.gabrielebologna.grandanticheat.GrandAntiCheat;
import it.gabrielebologna.grandanticheat.utils.UtilMove;
import it.gabrielebologna.grandanticheat.utils.Verbose;
import it.gabrielebologna.grandanticheat.violations.VLPlayer;

public class PlayerData
{
	private Player player;
	private boolean clientOnGround;
	private boolean serverOnGround;
	private int clientGroundTicks;
	private int serverGroundTicks;
	private int clientAirTicks;
	private int serverAirTicks;
	private int lastClientGroundTicks;
	private int lastServerGroundTicks;
	private int lastClientAirTicks;
	private int lastServerAirTicks;
	private long lastClientGroundTime;
	private long lastServerGroundTime;
	private long lastClientAirTime;
	private long lastServerAirTime;
	private boolean isJumping;
	private double positionX;
	private double positionY;
	private double positionZ;
	private long lastFlyingPacket;
	private long lastFlyingPacket1;
	private float rotationYaw;
	private float rotationPitch;
	private double lastPositionX;
	private double lastPositionY;
	private double lastPositionZ;
	private float lastRotationYaw;
	private float lastRotationPitch;
	private double clientDeltaY;
	private double clientLastDeltaY;
	private double serverDeltaY;
	private double serverLastDeltaY;
	private double clientYAcceleration;
	private double serverYAcceleration;
	private double lastClientYAcceleration;
	private double lastServerYAcceleration;
	private boolean hasJumped;
	private boolean inAir;
	private double serverYVelocity;
	private double lastServerYVelocity;
	private float yawDelta;
	private float pitchDelta;
	private int ping;
	private boolean isLagging;
	private boolean inWater;
	private boolean inWeb;
	private boolean inLadder;
	private boolean partiallyStuck;
	private boolean completelyStuck;
	private int violations;
	private ArrayList<VLPlayer> playerViolations = new ArrayList<>();
	private boolean flaggyStuffNear;
	private double clientSpeedXZ;
	private double serverSpeedXZ;
	private float rotationOffset;
	private Entity entityInSight;
	private long lastArmSwing;
	private long lastEntityUse;
	private long lastEntityAttack;	
	private long lastPositionPacket;
	private long lastLookPacket;
	private long lastPositionLookPacket;
	private long lastLeftClick;
	private long lastRightClick;
	private long lastFallDamage;
	private long lastMove;
	private long lastArmSwing1;
	private long lastEntityUse1;
	private long lastEntityAttack1;	
	private long lastPositionPacket1;
	private long lastLookPacket1;
	private long lastPositionLookPacket1;
	private long lastLeftClick1;
	private long lastRightClick1;
	private long lastFallDamage1;
	private long lastMove1;
	private int flyingPackets;
	private int armSwings;
	private int entityUses;
	private int entityAttacks;
	private int positionPackets;
	private int lookPackets;
	private int positionLookPackets;
	private int leftClicks;
	private int rightClicks;
	private int moves;
	private boolean joinedNow;
	private int flyingPackets1;
	private int armSwings1;
	private int entityUses1;
	private int entityAttacks1;
	private int positionPackets1;
	private int lookPackets1;
	private int positionLookPackets1;
	private int leftClicks1;
	private int rightClicks1;
	private int moves1;
	private long lastPayloadPacket;
	private long lastPayloadPacket1;
	private int payloadPackets;
	private int payloadPackets1;
	private long lastPlayerPacket;
	private long lastPlayerPacket1;
	private int playerPackets;
	private int playerPackets1;
	private long lastFlying;
	private ArrayList<Double> speedOffsets = new ArrayList<Double>();
	private ArrayList<Double> flyOffsets = new ArrayList<Double>();
	private double verbose1;
	private boolean wasGround;
	private long verbose2;
	private int invalidrotation;
    private int irverbose;
    private double lastRDiff;
    private double rHitTicks;
    private long postTime;
    private boolean postAttacked;
    private int postPingCheck;
    private int auraTicksCount;
    private long auraTicksTime;
    private int aimbotTicksCount;
    private long aimbotTicksTime;
    private ArrayList<Double> differences = new ArrayList<Double>();
    private Location lastLocation;
    private LivingEntity lastEntityUsed;
    private LivingEntity lastEntityAttacked;
    private float yawDist;
    private float pitchDist;
    private float yawDiff;
    private int keepAlivePackets;
    private int keepAlivePackets1;
    private long lastKeepAlivePacket;
    private long lastKeepAlivePacket1;
    private long verbose3;
    private long verbose4;   
    private float lastPitch;
    private float lastPitchDiff;
    private float lastPitchOffset;
    private float lastYaw;
    private float lastYawDiff;
    private float lastYawOffset;
    private long lastTime;
    private double balance;
    private long verbose5;
    private long lastTeleport;
    private long lastTeleport1;
    private long teleports;
    private long teleports1;
    private long verbose6;
    private boolean lastOnGround;
    private boolean lastLastOnGround;
    private long verbose7;
    private int ticks;
    private long verbose8;
    private boolean lastWasArm;
    private long verbose9;
    private long lastLook;
    private long verbose10;
    private boolean sent;
    private long verbose11;  
    private float suspiciousYaw;
    private long verbose12; 
    private long lastPlace;
    private long verbose13; 
    private int ticks2;
    private int ticks3;  
    private long verbose14;
    private long verbose15;
    private long verbose16;
    private int lookPacketsT;
    private int totalPacketsT;
    private double lastDiff;
    private long verbose17;
    private int ticks4;
    private double avgClickSpeed;
    private double lastCps;
    private long verbose18;  
    private int flying;
    private int arm;
    private long verbose19;
    private int ticks5;
    private boolean sent1;
    private ArrayList<Float> floats = new ArrayList<Float>();
    private long verbose20;
    private double lastClientSpeedXZ;
    private double lastServerSpeedXZ;  
    private boolean lastOnGround1;
    private boolean lastLastOnGround1;
    private long verbose21; 
    private long b;
    private boolean lastOnGround2;
    private long verbose22;  
    private long b1;
    private long s1; 
    private long lastSlime;
    private long lastIce; 
    private boolean onSlime;
    private boolean onIce;
    private long lastDig;  
    private boolean sentVertical;
    private double lastVertical;
    private int ticks6;
    private long verbose23;
    private int lastPing;  
    private boolean lastOnGround3;
    private boolean lastLastOnGround3;
    private long verbose24;
    private long lastVelocity;
    private long verbose25;
    private long verbose26;   
    private double initialDistanceFromGround;
    private double distanceFromGround;  
    private double initialFallHealth;
    private boolean initialFall;
    private ArrayList<Double> serverSpeedOffsets = new ArrayList<Double>();
    private long verbose27;
    private int ticks1;   
    private int ticks7;
    private long verbose28;
    private boolean wasSprinting;
    private boolean wasWasSprinting;
    private PacketContainer lastPacket;   
    private Location currentLoc;
    private Location lastLoc;
    private long verbose29;
    private long lastSprint;
    private long verbose30;
    private int scaffoldBlocksPlaced;
    private List<Double> averageScaffoldPitches;
    private long lastInventoryClick;
    private long lastInventoryClose; 
    private int ticks8;  
    private long verbose31;
    private long verbose32;
    private boolean lastInLiquid;
    private boolean lastLastInLiquid;
    private long verbose33; 
    private int inventoryClicks;
    private int inventoryClicks1;
    private boolean isInventoryOpened;
    private long lastDrop;
    private long drops;
    private long lastInventoryOpen;  
    private long verbose34;
    private long verbose35;   
    private HashMap<Float, Integer> samples = new HashMap<Float, Integer>();
    private int patternVerbose;
    private long patternMS;
    private Location loc1;
    private Location loc2;
    private int moves2;
    private double motionX;
    private double motionY;
    private double motionZ;
    private double lastMotionX;
    private double lastMotionY;
    private double lastMotionZ;
    private long verbose36;
    private long verbose37;
    private long lastMessage;
    private ArrayList<Double> yawTimings;
    private long verbose38;
    private double lastYawDi;
    private double lastPitchDi;
    private double lastPitchDifference;
    private double lastYawDifference;
    private long last;
    private long verbose39;
    private long lastTrans;
    private long lastTrans1;
    private long lastArm;
    private long lastDig1;
    private long verbose40;
    private long lastDig2;
    private long Arm;
    private long Hit;
    private long verbose41;
    private long verbose42;
    private long lastArm1;
    private long verbose43;
    private long lastHeld;
    private Verbose verbose44 = new Verbose();
    private Verbose verbose45 = new Verbose();
    private Verbose verbose46 = new Verbose();
    private Verbose verbose47 = new Verbose();
    private Verbose verbose48 = new Verbose(); 
    private double lastPitchDifferance;
    private double lastYawDifferance;
    private int optifineTicks;
    private double lastPitch1;
    private long lastOptifine;
    private int verbose49;   
    private Verbose verbose50 = new Verbose();
    private long lastPlacer;  
    private long verbose51;
    private int sArm;
    private int sUse;  
    private Verbose verbose52 = new Verbose();  
    private long verbose53;
    private double dist;
    private Verbose verbose54 = new Verbose();
    private double lastYDist;
    private long verbose55;
    private double value;
    private long verbose56;
    private double lastFly;
    private long verbose57;
    private Verbose verbose58 = new Verbose();
    private double lastFriction;
    private double lastYDi;
    private double lastX;
    private double lastZ;
    private double lastXZ;
    private long verbose59;
    private double added;
    private boolean cancel;
    private long verbose60;
    private double dist1;
    private boolean cancel1;
    private long lastVelocityEvent;
    private long verbose61;
    private Verbose flagVerbose = new Verbose();
    private Location from;
    private int countA;
    private int spikes;
    private long ping1;
    private boolean keepAliveLag;
    private long lastTransactionPacket;
    private boolean transLag;
    private int ticks9;
    private int skippedTicks;
    private boolean lagging;
    private long lastKeepAlive;
    private boolean masterLag;
    private long lastSetOldLag;
    private long lastSetTickLag;
    private long lastPacket1;
    private boolean oldLagging;
    private long lastLagSet;
    private long lastLag;
    private long lastLagCheck;
    private long lastSkippedTicks;
    private long userCreated;
    private long verbose62;  
    private double lastDistance;
    private long lastFlag;
    private long verbose63;
    private long verbose64;
    private Location from1;
    private long verbose65;
    private double lastSpeed;
    private Verbose verbose66 = new Verbose();
    private long verbose67;
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private Deque<Long> diffs = new LinkedList();
    private long verbose68;
    private long verbose69;
    private Verbose verbose70 = new Verbose();
    private float diff1;
    private float diff2;
    private long lastWorldChange;
    private long lastReceivedTransactionPacket;
    private long lastReceivedKeepAlivePacket;
    
    public Location getFrom1()
    {
		return from1;
	}

	public void setFrom1(Location from1)
	{
		this.from1 = from1;
	}

	public long getVerbose65()
	{
		return verbose65;
	}

	public void setVerbose65(long verbose65)
	{
		this.verbose65 = verbose65;
	}

	public double getLastSpeed()
	{
		return lastSpeed;
	}

	public void setLastSpeed(double lastSpeed)
	{
		this.lastSpeed = lastSpeed;
	}

	public long getVerbose64()
    {
		return verbose64;
	}

	public void setVerbose64(long verbose64)
	{
		this.verbose64 = verbose64;
	}

	public double getLastDistance()
    {
		return lastDistance;
	}

	public void setLastDistance(double lastDistance)
	{
		this.lastDistance = lastDistance;
	}

	public long getLastFlag()
	{
		return lastFlag;
	}

	public void setLastFlag(long lastFlag)
	{
		this.lastFlag = lastFlag;
	}

	public long getVerbose63()
	{
		return verbose63;
	}

	public void setVerbose63(long verbose63)
	{
		this.verbose63 = verbose63;
	}

	public boolean isLagging1()
    {
    	return this.lagging;
    }
    
    public void setLagging1(boolean lagging)
    {
    	this.lagging = lagging;
    }
    
	public long getPing1()
	{
		return ping1;
	}

	public void setPing1(long ping1)
	{
		this.ping1 = ping1;
	}

	public boolean isKeepAliveLag()
	{
		return keepAliveLag;
	}

	public void setKeepAliveLag(boolean keepAliveLag)
	{
		this.keepAliveLag = keepAliveLag;
	}

	public long getLastTransactionPacket()
	{
		return lastTransactionPacket;
	}

	public void setLastTransactionPacket(long lastTransactionPacket)
	{
		this.lastTransactionPacket = lastTransactionPacket;
	}

	public boolean isTransLag()
	{
		return transLag;
	}

	public void setTransLag(boolean transLag)
	{
		this.transLag = transLag;
	}

	public int getTicks9()
	{
		return ticks9;
	}

	public void setTicks9(int ticks9)
	{
		this.ticks9 = ticks9;
	}

	public int getSkippedTicks()
	{
		return skippedTicks;
	}

	public void setSkippedTicks(int skippedTicks)
	{
		this.skippedTicks = skippedTicks;
	}

	public long getLastKeepAlive()
	{
		return lastKeepAlive;
	}

	public void setLastKeepAlive(long lastKeepAlive)
	{
		this.lastKeepAlive = lastKeepAlive;
	}

	public boolean isMasterLag()
	{
		return masterLag;
	}

	public void setMasterLag(boolean masterLag)
	{
		this.masterLag = masterLag;
	}

	public long getLastSetOldLag()
	{
		return lastSetOldLag;
	}

	public void setLastSetOldLag(long lastSetOldLag)
	{
		this.lastSetOldLag = lastSetOldLag;
	}

	public long getLastSetTickLag()
	{
		return lastSetTickLag;
	}

	public void setLastSetTickLag(long lastSetTickLag)
	{
		this.lastSetTickLag = lastSetTickLag;
	}

	public long getLastPacket1()
	{
		return lastPacket1;
	}

	public void setLastPacket1(long lastPacket1)
	{
		this.lastPacket1 = lastPacket1;
	}

	public boolean isOldLagging()
	{
		return oldLagging;
	}

	public void setOldLagging(boolean oldLagging)
	{
		this.oldLagging = oldLagging;
	}

	public long getLastLagSet()
	{
		return lastLagSet;
	}

	public void setLastLagSet(long lastLagSet)
	{
		this.lastLagSet = lastLagSet;
	}

	public long getLastLag()
	{
		return lastLag;
	}

	public void setLastLag(long lastLag)
	{
		this.lastLag = lastLag;
	}

	public long getLastLagCheck()
	{
		return lastLagCheck;
	}

	public void setLastLagCheck(long lastLagCheck)
	{
		this.lastLagCheck = lastLagCheck;
	}

	public long getLastSkippedTicks()
	{
		return lastSkippedTicks;
	}

	public void setLastSkippedTicks(long lastSkippedTicks)
	{
		this.lastSkippedTicks = lastSkippedTicks;
	}

	public long getUserCreated()
	{
		return userCreated;
	}

	public void setUserCreated(long userCreated)
	{
		this.userCreated = userCreated;
	}

	public void setFlagVerbose(Verbose flagVerbose)
	{
		this.flagVerbose = flagVerbose;
	}

	public PlayerData(Player player)
	{
		this.player = player;
		userCreated = System.currentTimeMillis();
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public boolean isClientOnGround()
	{
		return clientOnGround;
	}

	public void setClientOnGround(boolean clientOnGround)
	{
		this.clientOnGround = clientOnGround;
	}

	public boolean isServerOnGround()
	{
		return serverOnGround;
	}

	public void setServerOnGround(boolean serverOnGround)
	{
		this.serverOnGround = serverOnGround;
	}

	public int getClientGroundTicks()
	{
		return clientGroundTicks;
	}

	public void setClientGroundTicks(int clientGroundTicks)
	{
		this.clientGroundTicks = clientGroundTicks;
	}

	public int getServerGroundTicks()
	{
		return serverGroundTicks;
	}

	public void setServerGroundTicks(int serverGroundTicks)
	{
		this.serverGroundTicks = serverGroundTicks;
	}

	public int getClientAirTicks()
	{
		return clientAirTicks;
	}

	public void setClientAirTicks(int clientAirTicks)
	{
		this.clientAirTicks = clientAirTicks;
	}
	
	public int getServerAirTicks()
	{
		return serverAirTicks;
	}

	public void setServerAirTicks(int serverAirTicks)
	{
		this.serverAirTicks = serverAirTicks;
	}
	
	public int getLastClientGroundTicks()
	{
		return lastClientGroundTicks;
	}

	public void setLastClientGroundTicks(int lastClientGroundTicks)
	{
		this.lastClientGroundTicks = lastClientGroundTicks;
	}

	public int getLastServerGroundTicks()
	{
		return lastServerGroundTicks;
	}

	public void setLastServerGroundTicks(int lastServerGroundTicks)
	{
		this.lastServerGroundTicks = lastServerGroundTicks;
	}

	public int getLastClientAirTicks()
	{
		return lastClientAirTicks;
	}

	public void setLastClientAirTicks(int lastClientAirTicks)
	{
		this.lastClientAirTicks = lastClientAirTicks;
	}

	public int getLastServerAirTicks()
	{
		return lastServerAirTicks;
	}

	public void setLastServerAirTicks(int lastServerAirTicks)
	{
		this.lastServerAirTicks = lastServerAirTicks;
	}
	
	public long getLastClientGroundTime()
	{
		return lastClientGroundTime;
	}

	public void setLastClientGroundTime(long lastClientGroundTime)
	{
		this.lastClientGroundTime = lastClientGroundTime;
	}

	public long getLastServerGroundTime()
	{
		return lastServerGroundTime;
	}

	public void setLastServerGroundTime(long lastServerGroundTime)
	{
		this.lastServerGroundTime = lastServerGroundTime;
	}

	public long getLastClientAirTime()
	{
		return lastClientAirTime;
	}

	public void setLastClientAirTime(long lastClientAirTime)
	{
		this.lastClientAirTime = lastClientAirTime;
	}

	public long getLastServerAirTime()
	{
		return lastServerAirTime;
	}

	public void setLastServerAirTime(long lastServerAirTime)
	{
		this.lastServerAirTime = lastServerAirTime;
	}

	public boolean isJumping()
	{
		return isJumping;
	}

	public void setJumping(boolean isJumping)
	{
		this.isJumping = isJumping;
	}

	public double getPositionX()
	{
		return positionX;
	}

	public void setPositionX(double positionX)
	{
		this.positionX = positionX;
	}

	public double getPositionY()
	{
		return positionY;
	}

	public void setPositionY(double positionY)
	{
		this.positionY = positionY;
	}

	public double getPositionZ()
	{
		return positionZ;
	}

	public void setPositionZ(double positionZ)
	{
		this.positionZ = positionZ;
	}

	public long getLastFlyingPacket()
	{
		return lastFlyingPacket;
	}

	public void setLastFlyingPacket(long lastFlyingPacket)
	{
		this.lastFlyingPacket = lastFlyingPacket;
	}

	public float getRotationYaw()
	{
		return rotationYaw;
	}

	public void setRotationYaw(float rotationYaw)
	{
		this.rotationYaw = rotationYaw;
	}

	public float getRotationPitch()
	{
		return rotationPitch;
	}

	public void setRotationPitch(float rotationPitch)
	{
		this.rotationPitch = rotationPitch;
	}

	public double getLastPositionX()
	{
		return lastPositionX;
	}

	public void setLastPositionX(double lastPositionX)
	{
		this.lastPositionX = lastPositionX;
	}

	public double getLastPositionY()
	{
		return lastPositionY;
	}

	public void setLastPositionY(double lastPositionY)
	{
		this.lastPositionY = lastPositionY;
	}

	public double getLastPositionZ() 
	{
		return lastPositionZ;
	}

	public void setLastPositionZ(double lastPositionZ)
	{
		this.lastPositionZ = lastPositionZ;
	}

	public float getLastRotationYaw()
	{
		return lastRotationYaw;
	}

	public void setLastRotationYaw(float lastRotationYaw)
	{
		this.lastRotationYaw = lastRotationYaw;
	}

	public float getLastRotationPitch()
	{
		return lastRotationPitch;
	}

	public void setLastRotationPitch(float lastRotationPitch)
	{
		this.lastRotationPitch = lastRotationPitch;
	}

	public double getClientDeltaY()
	{
		return clientDeltaY;
	}

	public void setClientDeltaY(double clientDeltaY)
	{
		this.clientDeltaY = clientDeltaY;
	}

	public double getClientLastDeltaY()
	{
		return clientLastDeltaY;
	}

	public void setClientLastDeltaY(double clientLastDeltaY)
	{
		this.clientLastDeltaY = clientLastDeltaY;
	}

	public double getServerDeltaY()
	{
		return serverDeltaY;
	}

	public void setServerDeltaY(double serverDeltaY)
	{
		this.serverDeltaY = serverDeltaY;
	}

	public double getServerLastDeltaY()
	{
		return serverLastDeltaY;
	}

	public void setServerLastDeltaY(double serverLastDeltaY)
	{
		this.serverLastDeltaY = serverLastDeltaY;
	}

	public double getClientYAcceleration()
	{
		return clientYAcceleration;
	}

	public void setClientYAcceleration(double clientYAcceleration)
	{
		this.clientYAcceleration = clientYAcceleration;
	}

	public double getServerYAcceleration()
	{
		return serverYAcceleration;
	}

	public void setServerYAcceleration(double serverYAcceleration)
	{
		this.serverYAcceleration = serverYAcceleration;
	}

	public double getLastClientYAcceleration()
	{
		return lastClientYAcceleration;
	}

	public void setLastClientYAcceleration(double lastClientYAcceleration)
	{
		this.lastClientYAcceleration = lastClientYAcceleration;
	}

	public double getLastServerYAcceleration()
	{
		return lastServerYAcceleration;
	}

	public void setLastServerYAcceleration(double lastServerYAcceleration)
	{
		this.lastServerYAcceleration = lastServerYAcceleration;
	}

	public boolean isHasJumped() 
	{
		return hasJumped;
	}

	public void setHasJumped(boolean hasJumped)
	{
		this.hasJumped = hasJumped;
	}

	public boolean isInAir()
	{
		return inAir;
	}

	public void setInAir(boolean inAir)
	{
		this.inAir = inAir;
	}

	public double getServerYVelocity()
	{
		return serverYVelocity;
	}

	public void setServerYVelocity(double serverYVelocity)
	{
		this.serverYVelocity = serverYVelocity;
	}

	public double getLastServerYVelocity()
	{
		return lastServerYVelocity;
	}

	public void setLastServerYVelocity(double lastServerYVelocity)
	{
		this.lastServerYVelocity = lastServerYVelocity;
	}

	public float getYawDelta()
	{
		return yawDelta;
	}

	public void setYawDelta(float yawDelta)
	{
		this.yawDelta = yawDelta;
	}

	public float getPitchDelta()
	{
		return pitchDelta;
	}

	public void setPitchDelta(float pitchDelta)
	{
		this.pitchDelta = pitchDelta;
	}

	public int getPing()
	{
		return ping;
	}

	public void setPing(int ping)
	{
		this.ping = ping;
	}

	public boolean isLagging()
	{
		return isLagging;
	}

	public void setLagging(boolean isLagging)
	{
		this.isLagging = isLagging;
	}

	public boolean isInWater()
	{
		return inWater;
	}

	public void setInWater(boolean inWater)
	{
		this.inWater = inWater;
	}

	public boolean isInWeb()
	{
		return inWeb;
	}

	public void setInWeb(boolean inWeb)
	{
		this.inWeb = inWeb;
	}

	public boolean isInLadder()
	{
		return inLadder;
	}

	public void setInLadder(boolean inLadder)
	{
		this.inLadder = inLadder;
	}

	public boolean isPartiallyStuck()
	{
		return partiallyStuck;
	}

	public void setPartiallyStuck(boolean partiallyStuck)
	{
		this.partiallyStuck = partiallyStuck;
	}

	public boolean isCompletelyStuck()
	{
		return completelyStuck;
	}

	public void setCompletelyStuck(boolean completelyStuck)
	{
		this.completelyStuck = completelyStuck;
	}

	public int getViolations()
	{
		return violations;
	}

	public void setViolations(int violations)
	{
		this.violations = violations;
	}

	public ArrayList<VLPlayer> getPlayerViolations()
	{
		return playerViolations;
	}

	public void setPlayerViolations(ArrayList<VLPlayer> playerViolations)
	{
		this.playerViolations = playerViolations;
	}

	public boolean isFlaggyStuffNear()
	{
		return flaggyStuffNear;
	}

	public void setFlaggyStuffNear(boolean flaggyStuffNear)
	{
		this.flaggyStuffNear = flaggyStuffNear;
	}

	public double getClientSpeedXZ()
	{
		return clientSpeedXZ;
	}

	public void setClientSpeedXZ(double clientSpeedXZ)
	{
		this.clientSpeedXZ = clientSpeedXZ;
	}

	public double getServerSpeedXZ()
	{
		return serverSpeedXZ;
	}

	public void setServerSpeedXZ(double serverSpeedXZ)
	{
		this.serverSpeedXZ = serverSpeedXZ;
	}

	public float getRotationOffset()
	{
		return rotationOffset;
	}

	public void setRotationOffset(float rotationOffset)
	{
		this.rotationOffset = rotationOffset;
	}

	public Entity getEntityInSight()
	{
		return entityInSight;
	}

	public void setEntityInSight(Entity entity)
	{
		this.entityInSight = entity;
	}

	public long getLastArmSwing()
	{
		return lastArmSwing;
	}

	public void setLastArmSwing(long lastArmSwing)
	{
		this.lastArmSwing = lastArmSwing;
	}

	public long getLastEntityUse()
	{
		return lastEntityUse;
	}

	public void setLastEntityUse(long lastEntityUse)
	{
		this.lastEntityUse = lastEntityUse;
	}

	public long getLastEntityAttack()
	{
		return lastEntityAttack;
	}

	public void setLastEntityAttack(long lastEntityAttack)
	{
		this.lastEntityAttack = lastEntityAttack;
	}

	public long getLastPositionPacket()
	{
		return lastPositionPacket;
	}

	public void setLastPositionPacket(long lastPositionPacket)
	{
		this.lastPositionPacket = lastPositionPacket;
	}

	public long getLastLookPacket()
	{
		return lastLookPacket;
	}

	public void setLastLookPacket(long lastLookPacket)
	{
		this.lastLookPacket = lastLookPacket;
	}

	public long getLastPositionLookPacket() 
	{
		return lastPositionLookPacket;
	}

	public void setLastPositionLookPacket(long lastPositionLookPacket)
	{
		this.lastPositionLookPacket = lastPositionLookPacket;
	}

	public long getLastLeftClick()
	{
		return lastLeftClick;
	}

	public void setLastLeftClick(long lastLeftClick)
	{
		this.lastLeftClick = lastLeftClick;
	}

	public long getLastRightClick()
	{
		return lastRightClick;
	}

	public void setLastRightClick(long lastRightClick)
	{
		this.lastRightClick = lastRightClick;
	}

	public long getLastFallDamage()
	{
		return lastFallDamage;
	}

	public void setLastFallDamage(long lastFallDamage)
	{
		this.lastFallDamage = lastFallDamage;
	}

	public long getLastMove()
	{
		return lastMove;
	}

	public void setLastMove(long lastMove)
	{
		this.lastMove = lastMove;
	}
	
	public boolean canBeChecked()
	{
		if (/*isFlaggyStuffNear() || isPartiallyStuck() || isCompletelyStuck() || */isJoinedNow() || isMasterLag() ||  GrandAntiCheat.getLagManager().getTPS() < 17.0D || System.currentTimeMillis() - getLastSlime() < 1500L || isOnSlime() || getPing() > 335 || UtilMove.blockNearHead(player) || this.getPlayer().getGameMode().equals(GameMode.CREATIVE) || this.getPlayer().getGameMode().equals(GameMode.SPECTATOR) || this.getPlayer().isFlying() || this.getPlayer().getAllowFlight())
		{
			return false;
		}
		
		return true;
	}

	public int getFlyingPackets()
	{
		return flyingPackets;
	}

	public void setFlyingPackets(int flyingPackets)
	{
		this.flyingPackets = flyingPackets;
	}

	public int getArmSwings()
	{
		return armSwings;
	}

	public void setArmSwings(int armSwings)
	{
		this.armSwings = armSwings;
	}

	public int getEntityUses() 
	{
		return entityUses;
	}

	public void setEntityUses(int entityUses)
	{
		this.entityUses = entityUses;
	}

	public int getEntityAttacks()
	{
		return entityAttacks;
	}

	public void setEntityAttacks(int entityAttacks)
	{
		this.entityAttacks = entityAttacks;
	}

	public int getPositionPackets()
	{
		return positionPackets;
	}

	public void setPositionPackets(int positionPackets)
	{
		this.positionPackets = positionPackets;
	}

	public int getLookPackets()
	{
		return lookPackets;
	}

	public void setLookPackets(int lookPackets)
	{
		this.lookPackets = lookPackets;
	}

	public int getPositionLookPackets()
	{
		return positionLookPackets;
	}

	public void setPositionLookPackets(int positionLookPackets)
	{
		this.positionLookPackets = positionLookPackets;
	}

	public int getLeftClicks()
	{
		return leftClicks;
	}

	public void setLeftClicks(int leftClicks)
	{
		this.leftClicks = leftClicks;
	}

	public int getRightClicks()
	{
		return rightClicks;
	}

	public void setRightClicks(int rightClicks)
	{
		this.rightClicks = rightClicks;
	}

	public int getMoves()
	{
		return moves;
	}

	public void setMoves(int moves)
	{
		this.moves = moves;
	}

	public boolean isJoinedNow()
	{
		return joinedNow;
	}

	public void setJoinedNow(boolean joinedNow)
	{
		this.joinedNow = joinedNow;
	}

	public int getFlyingPackets1()
	{
		return flyingPackets1;
	}

	public void setFlyingPackets1(int flyingPackets1)
	{
		this.flyingPackets1 = flyingPackets1;
	}

	public int getArmSwings1()
	{
		return armSwings1;
	}

	public void setArmSwings1(int armSwings1)
	{
		this.armSwings1 = armSwings1;
	}

	public int getEntityUses1()
	{
		return entityUses1;
	}

	public void setEntityUses1(int entityUses1)
	{
		this.entityUses1 = entityUses1;
	}

	public int getEntityAttacks1()
	{
		return entityAttacks1;
	}

	public void setEntityAttacks1(int entityAttacks1)
	{
		this.entityAttacks1 = entityAttacks1;
	}

	public int getPositionPackets1()
	{
		return positionPackets1;
	}

	public void setPositionPackets1(int positionPackets1)
	{
		this.positionPackets1 = positionPackets1;
	}

	public int getLookPackets1()
	{
		return lookPackets1;
	}

	public void setLookPackets1(int lookPackets1)
	{
		this.lookPackets1 = lookPackets1;
	}

	public int getPositionLookPackets1()
	{
		return positionLookPackets1;
	}

	public void setPositionLookPackets1(int positionLookPackets1)
	{
		this.positionLookPackets1 = positionLookPackets1;
	}

	public int getLeftClicks1()
	{
		return leftClicks1;
	}

	public void setLeftClicks1(int leftClicks1)
	{
		this.leftClicks1 = leftClicks1;
	}

	public int getRightClicks1()
	{
		return rightClicks1;
	}

	public void setRightClicks1(int rightClicks1)
	{
		this.rightClicks1 = rightClicks1;
	}

	public int getMoves1()
	{
		return moves1;
	}

	public void setMoves1(int moves1)
	{
		this.moves1 = moves1;
	}

	public long getLastArmSwing1()
	{
		return lastArmSwing1;
	}

	public void setLastArmSwing1(long lastArmSwing1)
	{
		this.lastArmSwing1 = lastArmSwing1;
	}

	public long getLastEntityUse1()
	{
		return lastEntityUse1;
	}

	public void setLastEntityUse1(long lastEntityUse1)
	{
		this.lastEntityUse1 = lastEntityUse1;
	}

	public long getLastEntityAttack1()
	{
		return lastEntityAttack1;
	}

	public void setLastEntityAttack1(long lastEntityAttack1)
	{
		this.lastEntityAttack1 = lastEntityAttack1;
	}

	public long getLastPositionPacket1()
	{
		return lastPositionPacket1;
	}

	public void setLastPositionPacket1(long lastPositionPacket1)
	{
		this.lastPositionPacket1 = lastPositionPacket1;
	}

	public long getLastLookPacket1()
	{
		return lastLookPacket1;
	}

	public void setLastLookPacket1(long lastLookPacket1)
	{
		this.lastLookPacket1 = lastLookPacket1;
	}

	public long getLastPositionLookPacket1()
	{
		return lastPositionLookPacket1;
	}

	public void setLastPositionLookPacket1(long lastPositionLookPacket1)
	{
		this.lastPositionLookPacket1 = lastPositionLookPacket1;
	}

	public long getLastLeftClick1()
	{
		return lastLeftClick1;
	}

	public void setLastLeftClick1(long lastLeftClick1)
	{
		this.lastLeftClick1 = lastLeftClick1;
	}

	public long getLastRightClick1()
	{
		return lastRightClick1;
	}

	public void setLastRightClick1(long lastRightClick1)
	{
		this.lastRightClick1 = lastRightClick1;
	}

	public long getLastFallDamage1()
	{
		return lastFallDamage1;
	}

	public void setLastFallDamage1(long lastFallDamage1)
	{
		this.lastFallDamage1 = lastFallDamage1;
	}

	public long getLastMove1()
	{
		return lastMove1;
	}

	public void setLastMove1(long lastMove1)
	{
		this.lastMove1 = lastMove1;
	}

	public long getLastFlyingPacket1()
	{
		return lastFlyingPacket1;
	}

	public void setLastFlyingPacket1(long lastFlyingPacket1)
	{
		this.lastFlyingPacket1 = lastFlyingPacket1;
	}

	public long getLastPayloadPacket()
	{
		return lastPayloadPacket;
	}

	public void setLastPayloadPacket(long lastPayloadPacket) 
	{
		this.lastPayloadPacket = lastPayloadPacket;
	}

	public long getLastPayloadPacket1()
	{
		return lastPayloadPacket1;
	}

	public void setLastPayloadPacket1(long lastPayloadPacket1)
	{
		this.lastPayloadPacket1 = lastPayloadPacket1;
	}

	public int getPayloadPackets()
	{
		return payloadPackets;
	}

	public void setPayloadPackets(int payloadPackets)
	{
		this.payloadPackets = payloadPackets;
	}

	public int getPayloadPackets1()
	{
		return payloadPackets1;
	}

	public void setPayloadPackets1(int payloadPackets1)
	{
		this.payloadPackets1 = payloadPackets1;
	}

	public long getLastPlayerPacket()
	{
		return lastPlayerPacket;
	}

	public void setLastPlayerPacket(long lastPlayerPacket)
	{
		this.lastPlayerPacket = lastPlayerPacket;
	}

	public long getLastPlayerPacket1()
	{
		return lastPlayerPacket1;
	}

	public void setLastPlayerPacket1(long lastPlayerPacket1)
	{
		this.lastPlayerPacket1 = lastPlayerPacket1;
	}

	public int getPlayerPackets()
	{
		return playerPackets;
	}

	public void setPlayerPackets(int playerPackets)
	{
		this.playerPackets = playerPackets;
	}

	public int getPlayerPackets1()
	{
		return playerPackets1;
	}

	public void setPlayerPackets1(int playerPackets1)
	{
		this.playerPackets1 = playerPackets1;
	}

	public long getLastFlying()
	{
		return lastFlying;
	}

	public void setLastFlying(long lastFlying)
	{
		this.lastFlying = lastFlying;
	}

	public ArrayList<Double> getSpeedOffsets()
	{
		return speedOffsets;
	}

	public void setSpeedOffsets(ArrayList<Double> speedOffsets)
	{
		this.speedOffsets = speedOffsets;
	}

	public double getVerbose1()
	{
		return verbose1;
	}

	public void setVerbose1(double verbose1)
	{
		this.verbose1 = verbose1;
	}

	public boolean isWasGround()
	{
		return wasGround;
	}

	public void setWasGround(boolean wasGround)
	{
		this.wasGround = wasGround;
	}

	public long getVerbose2()
	{
		return verbose2;
	}

	public void setVerbose2(long verbose2)
	{
		this.verbose2 = verbose2;
	}

	public int getInvalidrotation()
	{
		return invalidrotation;
	}

	public void setInvalidrotation(int invalidrotation)
	{
		this.invalidrotation = invalidrotation;
	}

	public int getIrverbose()
	{
		return irverbose;
	}

	public void setIrverbose(int irverbose)
	{
		this.irverbose = irverbose;
	}

	public double getLastRDiff()
	{
		return lastRDiff;
	}

	public void setLastRDiff(double lastRDiff)
	{
		this.lastRDiff = lastRDiff;
	}

	public double getrHitTicks()
	{
		return rHitTicks;
	}

	public void setrHitTicks(double rHitTicks)
	{
		this.rHitTicks = rHitTicks;
	}

	public long getPostTime()
	{
		return postTime;
	}

	public void setPostTime(long postTime)
	{
		this.postTime = postTime;
	}

	public boolean isPostAttacked()
	{
		return postAttacked;
	}

	public void setPostAttacked(boolean postAttacked)
	{
		this.postAttacked = postAttacked;
	}

	public int getPostPingCheck()
	{
		return postPingCheck;
	}

	public void setPostPingCheck(int postPingCheck)
	{
		this.postPingCheck = postPingCheck;
	}

	public int getAuraTicksCount()
	{
		return auraTicksCount;
	}

	public void setAuraTicksCount(int auraTicksCount)
	{
		this.auraTicksCount = auraTicksCount;
	}

	public long getAuraTicksTime()
	{
		return auraTicksTime;
	}

	public void setAuraTicksTime(long auraTicksTime)
	{
		this.auraTicksTime = auraTicksTime;
	}

	public int getAimbotTicksCount() 
	{
		return aimbotTicksCount;
	}

	public void setAimbotTicksCount(int aimbotTicksCount)
	{
		this.aimbotTicksCount = aimbotTicksCount;
	}

	public long getAimbotTicksTime()
	{
		return aimbotTicksTime;
	}

	public void setAimbotTicksTime(long aimbotTicksTime)
	{
		this.aimbotTicksTime = aimbotTicksTime;
	}

	public ArrayList<Double> getDifferences()
	{
		return differences;
	}

	public void setDifferences(ArrayList<Double> differences)
	{
		this.differences = differences;
	}

	public Location getLastLocation()
	{
		return lastLocation;
	}

	public void setLastLocation(Location lastLocation)
	{
		this.lastLocation = lastLocation;
	}

	public LivingEntity getLastEntityUsed()
	{
		return lastEntityUsed;
	}

	public void setLastEntityUsed(LivingEntity lastEntityUsed)
	{
		this.lastEntityUsed = lastEntityUsed;
	}

	public LivingEntity getLastEntityAttacked()
	{
		return lastEntityAttacked;
	}

	public void setLastEntityAttacked(LivingEntity lastEntityAttacked)
	{
		this.lastEntityAttacked = lastEntityAttacked;
	}

	public float getYawDist()
	{
		return yawDist;
	}

	public void setYawDist(float yawDist)
	{
		this.yawDist = yawDist;
	}

	public float getPitchDist()
	{
		return pitchDist;
	}

	public void setPitchDist(float pitchDist)
	{
		this.pitchDist = pitchDist;
	}

	public float getYawDiff()
	{
		return yawDiff;
	}

	public void setYawDiff(float yawDiff)
	{
		this.yawDiff = yawDiff;
	}
	
	public int getKeepAlivePackets()
	{
		return keepAlivePackets;
	}

	public void setKeepAlivePackets(int keepAlivePackets)
	{
		this.keepAlivePackets = keepAlivePackets;
	}

	public int getKeepAlivePackets1()
	{
		return keepAlivePackets1;
	}

	public void setKeepAlivePackets1(int keepAlivePackets1)
	{
		this.keepAlivePackets1 = keepAlivePackets1;
	}

	public long getLastKeepAlivePacket()
	{
		return lastKeepAlivePacket;
	}

	public void setLastKeepAlivePacket(long lastKeepAlivePacket)
	{
		this.lastKeepAlivePacket = lastKeepAlivePacket;
	}

	public long getLastKeepAlivePacket1()
	{
		return lastKeepAlivePacket1;
	}

	public void setLastKeepAlivePacket1(long lastKeepAlivePacket1)
	{
		this.lastKeepAlivePacket1 = lastKeepAlivePacket1;
	}

	public long getVerbose3()
	{
		return verbose3;
	}

	public void setVerbose3(long verbose3)
	{
		this.verbose3 = verbose3;
	}

	public long getVerbose4()
	{
		return verbose4;
	}

	public void setVerbose4(long verbose4)
	{
		this.verbose4 = verbose4;
	}

	public float getLastPitch() 
	{
		return lastPitch;
	}

	public void setLastPitch(float lastPitch)
	{
		this.lastPitch = lastPitch;
	}

	public float getLastPitchDiff()
	{
		return lastPitchDiff;
	}

	public void setLastPitchDiff(float lastPitchDiff)
	{
		this.lastPitchDiff = lastPitchDiff;
	}

	public float getLastPitchOffset()
	{
		return lastPitchOffset;
	}

	public void setLastPitchOffset(float lastPitchOffset)
	{
		this.lastPitchOffset = lastPitchOffset;
	}

	public float getLastYaw()
	{
		return lastYaw;
	}

	public void setLastYaw(float lastYaw)
	{
		this.lastYaw = lastYaw;
	}

	public float getLastYawDiff()
	{
		return lastYawDiff;
	}

	public void setLastYawDiff(float lastYawDiff)
	{
		this.lastYawDiff = lastYawDiff;
	}

	public float getLastYawOffset()
	{
		return lastYawOffset;
	}

	public void setLastYawOffset(float lastYawOffset)
	{
		this.lastYawOffset = lastYawOffset;
	}

	public long getLastTime()
	{
		return lastTime;
	}

	public void setLastTime(long lastTime)
	{
		this.lastTime = lastTime;
	}

	public double getBalance()
	{
		return balance;
	}

	public void setBalance(double balance)
	{
		this.balance = balance;
	}

	public long getVerbose5()
	{
		return verbose5;
	}

	public void setVerbose5(long verbose5)
	{
		this.verbose5 = verbose5;
	}

	public long getLastTeleport()
	{
		return lastTeleport;
	}

	public void setLastTeleport(long lastTeleport)
	{
		this.lastTeleport = lastTeleport;
	}

	public long getLastTeleport1()
	{
		return lastTeleport1;
	}

	public void setLastTeleport1(long lastTeleport1)
	{
		this.lastTeleport1 = lastTeleport1;
	}

	public long getTeleports()
	{
		return teleports;
	}

	public void setTeleports(long teleports)
	{
		this.teleports = teleports;
	}

	public long getTeleports1()
	{
		return teleports1;
	}

	public void setTeleports1(long teleports1)
	{
		this.teleports1 = teleports1;
	}

	public long getVerbose6()
	{
		return verbose6;
	}

	public void setVerbose6(long verbose6)
	{
		this.verbose6 = verbose6;
	}

	public boolean isLastOnGround()
	{
		return lastOnGround;
	}

	public void setLastOnGround(boolean lastOnGround)
	{
		this.lastOnGround = lastOnGround;
	}

	public boolean isLastLastOnGround()
	{
		return lastLastOnGround;
	}

	public void setLastLastOnGround(boolean lastLastOnGround)
	{
		this.lastLastOnGround = lastLastOnGround;
	}

	public long getVerbose7()
	{
		return verbose7;
	}

	public void setVerbose7(long verbose7)
	{
		this.verbose7 = verbose7;
	}

	public int getTicks()
	{
		return ticks;
	}

	public void setTicks(int ticks)
	{
		this.ticks = ticks;
	}

	public long getVerbose8()
	{
		return verbose8;
	}

	public void setVerbose8(long verbose8)
	{
		this.verbose8 = verbose8;
	}

	public boolean isLastWasArm()
	{
		return lastWasArm;
	}

	public void setLastWasArm(boolean lastWasArm)
	{
		this.lastWasArm = lastWasArm;
	}

	public long getVerbose9()
	{
		return verbose9;
	}

	public void setVerbose9(long verbose9)
	{
		this.verbose9 = verbose9;
	}

	public long getLastLook()
	{
		return lastLook;
	}

	public void setLastLook(long lastLook)
	{
		this.lastLook = lastLook;
	}

	public long getVerbose10()
	{
		return verbose10;
	}

	public void setVerbose10(long verbose10)
	{
		this.verbose10 = verbose10;
	}

	public boolean isSent()
	{
		return sent;
	}

	public void setSent(boolean sent)
	{
		this.sent = sent;
	}

	public long getVerbose11()
	{
		return verbose11;
	}

	public void setVerbose11(long verbose11)
	{
		this.verbose11 = verbose11;
	}

	public float getSuspiciousYaw()
	{
		return suspiciousYaw;
	}

	public void setSuspiciousYaw(float suspiciousYaw)
	{
		this.suspiciousYaw = suspiciousYaw;
	}

	public long getVerbose12()
	{
		return verbose12;
	}

	public void setVerbose12(long verbose12)
	{
		this.verbose12 = verbose12;
	}

	public long getLastPlace()
	{
		return lastPlace;
	}

	public void setLastPlace(long lastPlace)
	{
		this.lastPlace = lastPlace;
	}

	public long getVerbose13()
	{
		return verbose13;
	}

	public void setVerbose13(long verbose13)
	{
		this.verbose13 = verbose13;
	}

	public int getTicks2()
	{
		return ticks2;
	}

	public void setTicks2(int ticks2)
	{
		this.ticks2 = ticks2;
	}

	public int getTicks3()
	{
		return ticks3;
	}

	public void setTicks3(int ticks3)
	{
		this.ticks3 = ticks3;
	}

	public long getVerbose14()
	{
		return verbose14;
	}

	public void setVerbose14(long verbose14)
	{
		this.verbose14 = verbose14;
	}

	public long getVerbose15()
	{
		return verbose15;
	}

	public void setVerbose15(long verbose15)
	{
		this.verbose15 = verbose15;
	}

	public long getVerbose16()
	{
		return verbose16;
	}

	public void setVerbose16(long verbose16)
	{
		this.verbose16 = verbose16;
	}

	public int getLookPacketsT()
	{
		return lookPacketsT;
	}

	public void setLookPacketsT(int lookPacketsT)
	{
		this.lookPacketsT = lookPacketsT;
	}

	public int getTotalPacketsT()
	{
		return totalPacketsT;
	}

	public void setTotalPacketsT(int totalPacketsT)
	{
		this.totalPacketsT = totalPacketsT;
	}

	public double getLastDiff()
	{
		return lastDiff;
	}

	public void setLastDiff(double lastDiff)
	{
		this.lastDiff = lastDiff;
	}

	public long getVerbose17()
	{
		return verbose17;
	}

	public void setVerbose17(long verbose17)
	{
		this.verbose17 = verbose17;
	}

	public int getTicks4()
	{
		return ticks4;
	}

	public void setTicks4(int ticks4)
	{
		this.ticks4 = ticks4;
	}

	public double getAvgClickSpeed()
	{
		return avgClickSpeed;
	}

	public void setAvgClickSpeed(double avgClickSpeed)
	{
		this.avgClickSpeed = avgClickSpeed;
	}

	public double getLastCps()
	{
		return lastCps;
	}

	public void setLastCps(double lastCps)
	{
		this.lastCps = lastCps;
	}

	public long getVerbose18()
	{
		return verbose18;
	}

	public void setVerbose18(long verbose18)
	{
		this.verbose18 = verbose18;
	}

	public int getFlying()
	{
		return flying;
	}

	public void setFlying(int flying)
	{
		this.flying = flying;
	}

	public int getArm()
	{
		return arm;
	}

	public void setArm(int arm)
	{
		this.arm = arm;
	}

	public long getVerbose19()
	{
		return verbose19;
	}

	public void setVerbose19(long verbose19)
	{
		this.verbose19 = verbose19;
	}

	public int getTicks5()
	{
		return ticks5;
	}

	public void setTicks5(int ticks5)
	{
		this.ticks5 = ticks5;
	}

	public boolean isSent1()
	{
		return sent1;
	}

	public void setSent1(boolean sent1)
	{
		this.sent1 = sent1;
	}

	public ArrayList<Float> getFloats()
	{
		return floats;
	}

	public void setFloats(ArrayList<Float> floats)
	{
		this.floats = floats;
	}

	public long getVerbose20()
	{
		return verbose20;
	}

	public void setVerbose20(long verbose20)
	{
		this.verbose20 = verbose20;
	}

	public boolean isLastOnGround1()
	{
		return lastOnGround1;
	}

	public void setLastOnGround1(boolean lastOnGround1)
	{
		this.lastOnGround1 = lastOnGround1;
	}

	public boolean isLastLastOnGround1()
	{
		return lastLastOnGround1;
	}

	public void setLastLastOnGround1(boolean lastLastOnGround1)
	{
		this.lastLastOnGround1 = lastLastOnGround1;
	}

	public long getVerbose21()
	{
		return verbose21;
	}

	public void setVerbose21(long verbose21)
	{
		this.verbose21 = verbose21;
	}

	public long getB()
	{
		return b;
	}

	public void setB(long b)
	{
		this.b = b;
	}

	public boolean isLastOnGround2()
	{
		return lastOnGround2;
	}

	public void setLastOnGround2(boolean lastOnGround2)
	{
		this.lastOnGround2 = lastOnGround2;
	}

	public long getVerbose22()
	{
		return verbose22;
	}

	public void setVerbose22(long verbose22) 
	{
		this.verbose22 = verbose22;
	}

	public long getB1()
	{
		return b1;
	}

	public void setB1(long b1)
	{
		this.b1 = b1;
	}

	public long getS1()
	{
		return s1;
	}

	public void setS1(long s1)
	{
		this.s1 = s1;
	}

	public double getLastClientSpeedXZ()
	{
		return lastClientSpeedXZ;
	}

	public void setLastClientSpeedXZ(double lastClientSpeedXZ)
	{
		this.lastClientSpeedXZ = lastClientSpeedXZ;
	}

	public double getLastServerSpeedXZ()
	{
		return lastServerSpeedXZ;
	}

	public void setLastServerSpeedXZ(double lastServerSpeedXZ)
	{
		this.lastServerSpeedXZ = lastServerSpeedXZ;
	}

	public long getLastSlime()
	{
		return lastSlime;
	}

	public void setLastSlime(long lastSlime)
	{
		this.lastSlime = lastSlime;
	}

	public long getLastIce()
	{
		return lastIce;
	}

	public void setLastIce(long lastIce)
	{
		this.lastIce = lastIce;
	}

	public boolean isOnSlime()
	{
		return onSlime;
	}

	public void setOnSlime(boolean onSlime)
	{
		this.onSlime = onSlime;
	}

	public boolean isOnIce()
	{
		return onIce;
	}

	public void setOnIce(boolean onIce)
	{
		this.onIce = onIce;
	}
	
	public long getLastDig()
	{
		return lastDig;
	}

	public void setLastDig(long lastDig) 
	{
		this.lastDig = lastDig;
	}

	public boolean isDigging()
	{
		return Math.abs(System.currentTimeMillis() - getLastDig()) < 200L;
	}

	public boolean isSentVertical()
	{
		return sentVertical;
	}

	public void setSentVertical(boolean sentVertical)
	{
		this.sentVertical = sentVertical;
	}

	public double getLastVertical()
	{
		return lastVertical;
	}

	public void setLastVertical(double lastVertical)
	{
		this.lastVertical = lastVertical;
	}

	public int getTicks6()
	{
		return ticks6;
	}

	public void setTicks6(int ticks6)
	{
		this.ticks6 = ticks6;
	}

	public long getVerbose23()
	{
		return verbose23;
	}

	public void setVerbose23(long verbose23)
	{
		this.verbose23 = verbose23;
	}

	public int getLastPing()
	{
		return lastPing;
	}

	public void setLastPing(int lastPing)
	{
		this.lastPing = lastPing;
	}

	public boolean isLastOnGround3()
	{
		return lastOnGround3;
	}

	public void setLastOnGround3(boolean lastOnGround3)
	{
		this.lastOnGround3 = lastOnGround3;
	}

	public boolean isLastLastOnGround3()
	{
		return lastLastOnGround3;
	}

	public void setLastLastOnGround3(boolean lastLastOnGround3)
	{
		this.lastLastOnGround3 = lastLastOnGround3;
	}

	public long getVerbose24()
	{
		return verbose24;
	}

	public void setVerbose24(long verbose24)
	{
		this.verbose24 = verbose24;
	}

	public long getLastVelocity()
	{
		return lastVelocity;
	}

	public void setLastVelocity(long lastVelocity)
	{
		this.lastVelocity = lastVelocity;
	}

	public long getVerbose25()
	{
		return verbose25;
	}

	public void setVerbose25(long verbose25)
	{
		this.verbose25 = verbose25;
	}

	public long getVerbose26()
	{
		return verbose26;
	}

	public void setVerbose26(long verbose26)
	{
		this.verbose26 = verbose26;
	}

	public ArrayList<Double> getFlyOffsets()
	{
		return flyOffsets;
	}

	public void setFlyOffsets(ArrayList<Double> flyOffsets)
	{
		this.flyOffsets = flyOffsets;
	}

	public double getInitialDistanceFromGround()
	{
		return initialDistanceFromGround;
	}

	public void setInitialDistanceFromGround(double initialDistanceFromGround)
	{
		this.initialDistanceFromGround = initialDistanceFromGround;
	}

	public double getDistanceFromGround()
	{
		return distanceFromGround;
	}

	public void setDistanceFromGround(double distanceFromGround)
	{
		this.distanceFromGround = distanceFromGround;
	}

	public double getInitialFallHealth()
	{
		return initialFallHealth;
	}

	public void setInitialFallHealth(double initialFallHealth)
	{
		this.initialFallHealth = initialFallHealth;
	}

	public boolean isInitialFall()
	{
		return initialFall;
	}

	public void setInitialFall(boolean initialFall)
	{
		this.initialFall = initialFall;
	}

	public ArrayList<Double> getServerSpeedOffsets()
	{
		return serverSpeedOffsets;
	}

	public void setServerSpeedOffsets(ArrayList<Double> serverSpeedOffsets)
	{
		this.serverSpeedOffsets = serverSpeedOffsets;
	}

	public long getVerbose27()
	{
		return verbose27;
	}

	public void setVerbose27(long verbose27)
	{
		this.verbose27 = verbose27;
	}

	public int getTicks1()
	{
		return ticks1;
	}

	public void setTicks1(int ticks1)
	{
		this.ticks1 = ticks1;
	}

	public int getTicks7()
	{
		return ticks7;
	}

	public void setTicks7(int ticks7)
	{
		this.ticks7 = ticks7;
	}

	public long getVerbose28()
	{
		return verbose28;
	}

	public void setVerbose28(long verbose28)
	{
		this.verbose28 = verbose28;
	}

	public boolean isWasSprinting()
	{
		return wasSprinting;
	}

	public void setWasSprinting(boolean wasSprinting)
	{
		this.wasSprinting = wasSprinting;
	}

	public boolean isWasWasSprinting()
	{
		return wasWasSprinting;
	}

	public void setWasWasSprinting(boolean wasWasSprinting)
	{
		this.wasWasSprinting = wasWasSprinting;
	}

	public PacketContainer getLastPacket()
	{
		return lastPacket;
	}

	public void setLastPacket(PacketContainer lastPacket)
	{
		this.lastPacket = lastPacket;
	}

	public Location getCurrentLoc()
	{
		return currentLoc;
	}

	public void setCurrentLoc(Location currentLoc)
	{
		this.currentLoc = currentLoc;
	}

	public Location getLastLoc()
	{
		return lastLoc;
	}

	public void setLastLoc(Location lastLoc)
	{
		this.lastLoc = lastLoc;
	}

	public long getVerbose29()
	{
		return verbose29;
	}

	public void setVerbose29(long verbose29)
	{
		this.verbose29 = verbose29;
	}

	public long getLastSprint()
	{
		return lastSprint;
	}

	public void setLastSprint(long lastSprint)
	{
		this.lastSprint = lastSprint;
	}

	public long getVerbose30()
	{
		return verbose30;
	}

	public void setVerbose30(long verbose30)
	{
		this.verbose30 = verbose30;
	}

	public int getScaffoldBlocksPlaced()
	{
		return scaffoldBlocksPlaced;
	}

	public void setScaffoldBlocksPlaced(int scaffoldBlocksPlaced)
	{
		this.scaffoldBlocksPlaced = scaffoldBlocksPlaced;
	}

	public List<Double> getAverageScaffoldPitches()
	{
		return averageScaffoldPitches;
	}

	public void setAverageScaffoldPitches(List<Double> averageScaffoldPitches)
	{
		this.averageScaffoldPitches = averageScaffoldPitches;
	}

	public long getLastInventoryClick()
	{
		return lastInventoryClick;
	}

	public void setLastInventoryClick(long lastInventoryClick)
	{
		this.lastInventoryClick = lastInventoryClick;
	}

	public long getLastInventoryClose()
	{
		return lastInventoryClose;
	}

	public void setLastInventoryClose(long lastInventoryClose)
	{
		this.lastInventoryClose = lastInventoryClose;
	}

	public int getTicks8()
	{
		return ticks8;
	}

	public void setTicks8(int ticks8)
	{
		this.ticks8 = ticks8;
	}

	public long getVerbose31()
	{
		return verbose31;
	}

	public void setVerbose31(long verbose31)
	{
		this.verbose31 = verbose31;
	}

	public long getVerbose32()
	{
		return verbose32;
	}

	public void setVerbose32(long verbose32)
	{
		this.verbose32 = verbose32;
	}

	public boolean isLastInLiquid()
	{
		return lastInLiquid;
	}

	public void setLastInLiquid(boolean lastInLiquid)
	{
		this.lastInLiquid = lastInLiquid;
	}

	public boolean isLastLastInLiquid()
	{
		return lastLastInLiquid;
	}

	public void setLastLastInLiquid(boolean lastLastInLiquid)
	{
		this.lastLastInLiquid = lastLastInLiquid;
	}

	public long getVerbose33()
	{
		return verbose33;
	}

	public void setVerbose33(long verbose33)
	{
		this.verbose33 = verbose33;
	}

	public int getInventoryClicks()
	{
		return inventoryClicks;
	}

	public void setInventoryClicks(int inventoryClicks)
	{
		this.inventoryClicks = inventoryClicks;
	}

	public int getInventoryClicks1()
	{
		return inventoryClicks1;
	}

	public void setInventoryClicks1(int inventoryClicks1)
	{
		this.inventoryClicks1 = inventoryClicks1;
	}

	public boolean isInventoryOpened()
	{
		return isInventoryOpened;
	}

	public void setInventoryOpened(boolean isInventoryOpened)
	{
		this.isInventoryOpened = isInventoryOpened;
	}

	public long getLastDrop()
	{
		return lastDrop;
	}

	public void setLastDrop(long lastDrop)
	{
		this.lastDrop = lastDrop;
	}

	public long getDrops()
	{
		return drops;
	}

	public void setDrops(long drops)
	{
		this.drops = drops;
	}

	public long getLastInventoryOpen()
	{
		return lastInventoryOpen;
	}

	public void setLastInventoryOpen(long lastInventoryOpen)
	{
		this.lastInventoryOpen = lastInventoryOpen;
	}
	
	public boolean isReallyInventoryOpened()
	{
		return this.isInventoryOpened() && !((System.currentTimeMillis() - this.getLastInventoryOpen()) < 400L);
	}

	public long getVerbose34()
	{
		return verbose34;
	}

	public void setVerbose34(long verbose34)
	{
		this.verbose34 = verbose34;
	}

	public long getVerbose35()
	{
		return verbose35;
	}

	public void setVerbose35(long verbose35)
	{
		this.verbose35 = verbose35;
	}

	public HashMap<Float, Integer> getSamples()
	{
		return samples;
	}

	public void setSamples(HashMap<Float, Integer> samples)
	{
		this.samples = samples;
	}

	public int getPatternVerbose()
	{
		return patternVerbose;
	}

	public void setPatternVerbose(int patternVerbose)
	{
		this.patternVerbose = patternVerbose;
	}

	public long getPatternMS()
	{
		return patternMS;
	}

	public void setPatternMS(long patternMS)
	{
		this.patternMS = patternMS;
	}
	
	public void resetPatternMS()
	{
		this.setPatternMS(System.currentTimeMillis());
	}

	public Location getLoc1()
	{
		return loc1;
	}

	public void setLoc1(Location loc1)
	{
		this.loc1 = loc1;
	}

	public Location getLoc2()
	{
		return loc2;
	}

	public void setLoc2(Location loc2)
	{
		this.loc2 = loc2;
	}

	public int getMoves2()
	{
		return moves2;
	}

	public void setMoves2(int moves2)
	{
		this.moves2 = moves2;
	}

	public double getMotionX()
	{
		return motionX;
	}

	public void setMotionX(double motionX)
	{
		this.motionX = motionX;
	}

	public double getMotionY()
	{
		return motionY;
	}

	public void setMotionY(double motionY)
	{
		this.motionY = motionY;
	}

	public double getMotionZ()
	{
		return motionZ;
	}

	public void setMotionZ(double motionZ)
	{
		this.motionZ = motionZ;
	}

	public double getLastMotionX()
	{
		return lastMotionX;
	}

	public void setLastMotionX(double lastMotionX)
	{
		this.lastMotionX = lastMotionX;
	}

	public double getLastMotionY() 
	{
		return lastMotionY;
	}

	public void setLastMotionY(double lastMotionY)
	{
		this.lastMotionY = lastMotionY;
	}

	public double getLastMotionZ()
	{
		return lastMotionZ;
	}

	public void setLastMotionZ(double lastMotionZ)
	{
		this.lastMotionZ = lastMotionZ;
	}

	public long getVerbose36()
	{
		return verbose36;
	}

	public void setVerbose36(long verbose36)
	{
		this.verbose36 = verbose36;
	}

	public long getVerbose37()
	{
		return verbose37;
	}

	public void setVerbose37(long verbose37)
	{
		this.verbose37 = verbose37;
	}

	public long getLastMessage()
	{
		return lastMessage;
	}

	public void setLastMessage(long lastMessage)
	{
		this.lastMessage = lastMessage;
	}

	public ArrayList<Double> getYawTimings()
	{
		return yawTimings;
	}

	public void setYawTimings(ArrayList<Double> yawTimings)
	{
		this.yawTimings = yawTimings;
	}

	public long getVerbose38()
	{
		return verbose38;
	}

	public void setVerbose38(long verbose38)
	{
		this.verbose38 = verbose38;
	}

	public double getLastYawDi()
	{
		return lastYawDi;
	}

	public void setLastYawDi(double lastYawDi)
	{
		this.lastYawDi = lastYawDi;
	}

	public double getLastPitchDi()
	{
		return lastPitchDi;
	}

	public void setLastPitchDi(double lastPitchDi)
	{
		this.lastPitchDi = lastPitchDi;
	}

	public double getLastPitchDifference()
	{
		return lastPitchDifference;
	}

	public void setLastPitchDifference(double lastPitchDifference)
	{
		this.lastPitchDifference = lastPitchDifference;
	}

	public double getLastYawDifference()
	{
		return lastYawDifference;
	}

	public void setLastYawDifference(double lastYawDifference)
	{
		this.lastYawDifference = lastYawDifference;
	}

	public long getLast()
	{
		return last;
	}

	public void setLast(long last)
	{
		this.last = last;
	}

	public long getVerbose39()
	{
		return verbose39;
	}

	public void setVerbose39(long verbose39)
	{
		this.verbose39 = verbose39;
	}

	public long getLastTrans()
	{
		return lastTrans;
	}

	public void setLastTrans(long lastTrans)
	{
		this.lastTrans = lastTrans;
	}

	public long getLastTrans1()
	{
		return lastTrans1;
	}

	public void setLastTrans1(long lastTrans1)
	{
		this.lastTrans1 = lastTrans1;
	}

	public long getLastArm()
	{
		return lastArm;
	}

	public void setLastArm(long lastArm)
	{
		this.lastArm = lastArm;
	}

	public long getLastDig1()
	{
		return lastDig1;
	}

	public void setLastDig1(long lastDig1)
	{
		this.lastDig1 = lastDig1;
	}

	public long getVerbose40()
	{
		return verbose40;
	}

	public void setVerbose40(long verbose40)
	{
		this.verbose40 = verbose40;
	}

	public long getLastDig2()
	{
		return lastDig2;
	}

	public void setLastDig2(long lastDig2)
	{
		this.lastDig2 = lastDig2;
	}

	public long getHit()
	{
		return Hit;
	}

	public void setHit(long hit)
	{
		Hit = hit;
	}

	public long getVerbose41()
	{
		return verbose41;
	}

	public void setVerbose41(long verbose41)
	{
		this.verbose41 = verbose41;
	}

	public long getVerbose42()
	{
		return verbose42;
	}

	public void setVerbose42(long verbose42)
	{
		this.verbose42 = verbose42;
	}

	public long getLastArm1()
	{
		return lastArm1;
	}

	public void setLastArm1(long lastArm1)
	{
		this.lastArm1 = lastArm1;
	}

	public long getVerbose43()
	{
		return verbose43;
	}

	public void setVerbose43(long verbose43)
	{
		this.verbose43 = verbose43;
	}
	
	public long getLArm()
	{
		return Arm;
	}

	public void setArm(long arm)
	{
		Arm = arm;
	}

	public long getLastHeld()
	{
		return lastHeld;
	}

	public void setLastHeld(long lastHeld)
	{
		this.lastHeld = lastHeld;
	}
	
	public Verbose getVerbose44()
	{
		return verbose44;
	}
	
	public Verbose getVerbose45()
	{
		return verbose45;
	}
	
	public Verbose getVerbose46()
	{
		return verbose46;
	}
	
	public Verbose getVerbose47()
	{
		return verbose47;
	}
	
	public Verbose getVerbose48()
	{
		return verbose48;
	}

	public double getLastPitchDifferance()
	{
		return lastPitchDifferance;
	}

	public void setLastPitchDifferance(double lastPitchDifferance)
	{
		this.lastPitchDifferance = lastPitchDifferance;
	}

	public double getLastYawDifferance()
	{
		return lastYawDifferance;
	}

	public void setLastYawDifferance(double lastYawDifferance)
	{
		this.lastYawDifferance = lastYawDifferance;
	}

	public int getOptifineTicks()
	{
		return optifineTicks;
	}

	public void setOptifineTicks(int optifineTicks) 
	{
		this.optifineTicks = optifineTicks;
	}

	public double getLastPitch1()
	{
		return lastPitch1;
	}

	public void setLastPitch1(double lastPitch1)
	{
		this.lastPitch1 = lastPitch1;
	}

	public long getLastOptifine()
	{
		return lastOptifine;
	}

	public void setLastOptifine(long l)
	{
		this.lastOptifine = l;
	}

	public int getVerbose49()
	{
		return verbose49;
	}

	public void setVerbose49(int verbose49)
	{
		this.verbose49 = verbose49;
	}

	public long getLastPlacer()
	{
		return lastPlacer;
	}

	public void setLastPlacer(long lastPlacer)
	{
		this.lastPlacer = lastPlacer;
	}

	public int getsArm()
	{
		return sArm;
	}

	public void setsArm(int sArm)
	{
		this.sArm = sArm;
	}

	public int getsUse()
	{
		return sUse;
	}

	public void setsUse(int sUse)
	{
		this.sUse = sUse;
	}

	public double getDist()
	{
		return dist;
	}

	public void setDist(double dist)
	{
		this.dist = dist;
	}

	public long getVerbose51()
	{
		return verbose51;
	}

	public void setVerbose51(long verbose51)
	{
		this.verbose51 = verbose51;
	}

	public long getVerbose53()
	{
		return verbose53;
	}

	public void setVerbose53(long verbose53)
	{
		this.verbose53 = verbose53;
	}
	
	public Verbose getVerbose50()
	{
		return verbose50;
	}
	
	public Verbose getVerbose52()
	{
		return verbose52;
	}
	
	public Verbose getVerbose54()
	{
		return verbose54;
	}

	public double getLastYDist()
	{
		return lastYDist;
	}

	public void setLastYDist(double lastYDist)
	{
		this.lastYDist = lastYDist;
	}

	public long getVerbose55()
	{
		return verbose55;
	}

	public void setVerbose55(long verbose55)
	{
		this.verbose55 = verbose55;
	}

	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}

	public long getVerbose56()
	{
		return verbose56;
	}

	public void setVerbose56(long verbose56)
	{
		this.verbose56 = verbose56;
	}

	public double getLastFly()
	{
		return lastFly;
	}

	public void setLastFly(double lastFly)
	{
		this.lastFly = lastFly;
	}

	public long getVerbose57()
	{
		return verbose57;
	}

	public void setVerbose57(long verbose57)
	{
		this.verbose57 = verbose57;
	}
	
	public Verbose getVerbose58()
	{
		return verbose58;
	}

	public double getLastFriction()
	{
		return lastFriction;
	}

	public void setLastFriction(double lastFriction)
	{
		this.lastFriction = lastFriction;
	}

	public double getLastYDi()
	{
		return lastYDi;
	}

	public void setLastYDi(double lastYDi)
	{
		this.lastYDi = lastYDi;
	}

	public double getLastX()
	{
		return lastX;
	}

	public void setLastX(double lastX)
	{
		this.lastX = lastX;
	}

	public double getLastZ()
	{
		return lastZ;
	}

	public void setLastZ(double lastZ)
	{
		this.lastZ = lastZ;
	}

	public double getLastXZ()
	{
		return lastXZ;
	}

	public void setLastXZ(double lastXZ)
	{
		this.lastXZ = lastXZ;
	}

	public long getVerbose59()
	{
		return verbose59;
	}

	public void setVerbose59(long verbose59)
	{
		this.verbose59 = verbose59;
	}

	public double getAdded()
	{
		return added;
	}

	public void setAdded(double added)
	{
		this.added = added;
	}

	public boolean isCancel()
	{
		return cancel;
	}

	public void setCancel(boolean cancel)
	{
		this.cancel = cancel;
	}

	public long getVerbose60()
	{
		return verbose60;
	}

	public void setVerbose60(long verbose60)
	{
		this.verbose60 = verbose60;
	}

	public double getDist1()
	{
		return dist1;
	}

	public void setDist1(double dist1)
	{
		this.dist1 = dist1;
	}

	public boolean isCancel1()
	{
		return cancel1;
	}

	public void setCancel1(boolean cancel1)
	{
		this.cancel1 = cancel1;
	}

	public long getLastVelocityEvent()
	{
		return lastVelocityEvent;
	}

	public void setLastVelocityEvent(long lastVelocityEvent)
	{
		this.lastVelocityEvent = lastVelocityEvent;
	}

	public long getVerbose61()
	{
		return verbose61;
	}

	public void setVerbose61(long verbose61)
	{
		this.verbose61 = verbose61;
	}

	public Location getFrom()
	{
		return from;
	}

	public void setFrom(Location from)
	{
		this.from = from;
	}

	public int getCountA()
	{
		return countA;
	}

	public void setCountA(int countA)
	{
		this.countA = countA;
	}

	public int getSpikes()
	{
		return spikes;
	}

	public void setSpikes(int spikes)
	{
		this.spikes = spikes;
	}
	
	public Verbose getFlagVerbose()
	{
		return this.flagVerbose;
	}

	public long getVerbose62()
	{
		return verbose62;
	}

	public void setVerbose62(long verbose62)
	{
		this.verbose62 = verbose62;
	}

	public Verbose getVerbose66()
	{
		return verbose66;
	}

	public void setVerbose66(Verbose verbose66)
	{
		this.verbose66 = verbose66;
	}

	public long getVerbose67()
	{
		return verbose67;
	}

	public void setVerbose67(long verbose67)
	{
		this.verbose67 = verbose67;
	}

	public Deque<Long> getDiffs()
	{
		return diffs;
	}

	public void setDiffs(Deque<Long> diffs)
	{
		this.diffs = diffs;
	}
	
	public long getVerbose68()
	{
		return verbose68;
	}

	public void setVerbose68(long verbose68)
	{
		this.verbose68 = verbose68;
	}

	public long getVerbose69()
	{
		return verbose69;
	}

	public void setVerbose69(long verbose69)
	{
		this.verbose69 = verbose69;
	}

	public Verbose getVerbose70()
	{
		return verbose70;
	}

	public void setVerbose70(Verbose verbose70)
	{
		this.verbose70 = verbose70;
	}

	public float getDiff1()
	{
		return diff1;
	}

	public void setDiff1(float diff1)
	{
		this.diff1 = diff1;
	}

	public float getDiff2()
	{
		return diff2;
	}

	public void setDiff2(float diff2)
	{
		this.diff2 = diff2;
	}

	public long getLastWorldChange()
	{
		return lastWorldChange;
	}

	public void setLastWorldChange(long lastWorldChange)
	{
		this.lastWorldChange = lastWorldChange;
	}

	public long getLastReceivedTransactionPacket()
	{
		return lastReceivedTransactionPacket;
	}

	public void setLastReceivedTransactionPacket(long lastReceivedTransactionPacket)
	{
		this.lastReceivedTransactionPacket = lastReceivedTransactionPacket;
	}

	public long getLastReceivedKeepAlivePacket()
	{
		return lastReceivedKeepAlivePacket;
	}

	public void setLastReceivedKeepAlivePacket(long lastReceivedKeepAlivePacket)
	{
		this.lastReceivedKeepAlivePacket = lastReceivedKeepAlivePacket;
	}
}