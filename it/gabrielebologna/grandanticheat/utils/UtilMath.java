package it.gabrielebologna.grandanticheat.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.EntityPlayer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.List;
import java.util.Random;

public class UtilMath
{
    public static double average(List<Double> values) {
        double avg = 0.0D;
        for (Double value : values)
            avg += value;
        return avg / values.size();
    }

    public static double addAll(List<Double> values) {
        double total = 0.0D;
        for (Double value : values)
            total += value;
        return total;
    }

    public static double getStandardDeviation(long numberArray[]) {
        double sum = 0.0, deviation = 0.0;
        int length = numberArray.length;
        for (double num : numberArray)
            sum += num;
        double mean = sum / length;
        for (double num : numberArray)
            deviation += Math.pow(num - mean, 2);

        return Math.sqrt(deviation / length);
    }

    public static double getStandardDeviation(double numberArray[]) {
        double sum = 0.0, deviation = 0.0;
        int length = numberArray.length;
        for (double num : numberArray)
            sum += num;
        double mean = sum / length;
        for (double num : numberArray)
            deviation += Math.pow(num - mean, 2);

        return Math.sqrt(deviation / length);
    }
	
    public static boolean isRoughlyEqual1(double d1, double d2, double seperator) {
        return Math.abs(d1-d2) < seperator;
    }

    public static double getMagnitude(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    public static boolean isUnderBlock(EntityPlayer player) {
        return player.world.c(player.getBoundingBox().grow(0.5, 0, 0.5).a(0.0, 1, 0.0));
    }

    public static double getLCM(double a, double b){
        return a * b;
    }

    public static long gcd1(long a, long b) {
        return b <= 0x4000 ? a : gcd(b, a % b);
    }

    public static long getGcd(long current, long previous) {
        return (double) previous <= 16384.0D ? current : getGcd(previous, (long) Math.abs(current - previous));
    }

    public static float clamp_float(float num, float min, float max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static double preciseRound(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
    public static float getDelta(float one, float two) {
        return Math.abs(one - two);
    }

    public static boolean isValueVrySmall(double value) {
        return String.valueOf(value).contains("E");
    }

    public static boolean looked(Location from, Location to) {
        return (from.getYaw() != 0 && to.getYaw() != 0) || (from.getPitch() != 0 && from.getPitch() != 0);
    }
    public static double normalize(double val, double min, double max) {
        if (max < min) return 0;
        return (val - min) / (max - min);
    }

    public static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    public static double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(0, RoundingMode.UP);
        return bd.doubleValue();
    }

    public static boolean nextToWall(Player p) {
        Location loc = p.getLocation();
        Location xp = new Location(loc.getWorld(), loc.getX() + 0.5, loc.getY(), loc.getZ());
        Location xn = new Location(loc.getWorld(), loc.getX() - 0.5, loc.getY(), loc.getZ());
        Location zp = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + 0.5);
        Location zn = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - 0.5);
        return xp.getBlock().getType().isSolid() || xn.getBlock().getType().isSolid() || zp.getBlock().getType().isSolid() || zn.getBlock().getType().isSolid();
    }

    public static double getHorizontalDistance(Vector to, Vector from) {
        double x = Math.abs(Math.abs(to.getX()) - Math.abs(from.getX()));
        double z = Math.abs(Math.abs(to.getZ()) - Math.abs(from.getZ()));

        return Math.sqrt(x * x + z * z);
    }

    public static double getHorizontalDistanceSpeed(Location to, Location from, Player p) {
        double x = Math.abs(to.getX()) - Math.abs(from.getX());
        double z = Math.abs(to.getZ()) - Math.abs(from.getZ());
        return Math.sqrt(x * x + z * z);
    }
	
    public static boolean roughlyEquals(final double a, final double b, final double range)
    {
        return offset(a, b) <= range;
    }

    public static double offset(final double a, final double b)
    {
        return a > b ? (a - b) : (b - a);
    }

    public static boolean inRange(final int min, final int max, final int number)
    {
        return number >= min && number <= max;
    }

    public static boolean inRange(final double min, final double max, final double number)
    {
        return number >= min && number <= max;
    }
	
    public static final double distance(final Location location1, final Location location2)
    {
        return distance(location1.getX(), location1.getY(), location1.getZ(), location2.getX(), location2.getY(), location2.getZ());
    }
	
    public static final double distance(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2)
    {
        final double dx = Math.abs(x1 - x2);
        final double dy = Math.abs(y1 - y2);
        final double dz = Math.abs(z1 - z2);
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
	
    public static boolean playerMoved(Vector from, Vector to)
    {
        return from.distance(to) > 0;
    }

    public static boolean playerLooked(Location from, Location to)
    {
        return (from.getYaw() - to.getYaw() != 0) || (from.getPitch() - to.getPitch() != 0);
    }
    
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static Random random;

    static
    {
        UtilMath.random = new Random();
    }
    
    public static float getYawDifference(float current, float target)
    {
        float rot = 0;
        return rot + ((rot = (target + 180.0f - current) % 360.0f) > 0.0f ? -180.0f : 180.0f);
    }

    public static double round(double value, int places)
    {
        if (places < 0)
        {
            throw new IllegalArgumentException();
        }
        
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double getFraction(final double value)
    {
        return value % 1.0;
    }
    
    public static Vector getHV(Vector V)
    {
        V.setY(0);
        return V;
    }

    public static double trim(int degree, double d)
    {
        String format = "#.#";
        
        for (int i = 1; i < degree; ++i)
        {
            format = String.valueOf(format) + "#";
        }
        
        DecimalFormat twoDForm = new DecimalFormat(format);
        return Double.valueOf(twoDForm.format(d).replaceAll(",", "."));
    }

    public static int r(int i)
    {
        return UtilMath.random.nextInt(i);
    }

    public static double abs(double a)
    {
        return (a <= 0.0) ? (0.0 - a) : a;
    }

    public static String ArrayToString(String[] list)
    {
        String string = "";
        
        for (final String key : list)
        {
            string = String.valueOf(string) + key + ",";
        }
        
        if (string.length() != 0)
        {
            return string.substring(0, string.length() - 1);
        }
        
        return null;
    }

    public static String ArrayToString(List<String> list)
    {
        String string = "";
        
        for (final String key : list)
        {
            string = String.valueOf(string) + key + ",";
        }
        
        if (string.length() != 0)
        {
            return string.substring(0, string.length() - 1);
        }
        
        return null;
    }

    public static String[] StringToArray(String string, String split)
    {
        return string.split(split);
    }

    public static double offset2d(Entity a, Entity b)
    {
        return offset2d(a.getLocation().toVector(), b.getLocation().toVector());
    }

    public static double offset2d(Location a, Location b)
    {
        return offset2d(a.toVector(), b.toVector());
    }

    public static String decrypt(String strEncrypted)
    {
        String strData = "";

        try
        {
            byte[] decoded = Base64.getDecoder().decode(strEncrypted);
            strData = (new String(decoded, StandardCharsets.UTF_8) + "\n");
        }
        catch (Exception e)
        {
        	
        }
        
        return strData;
    }

    public static double offset2d(Vector a, Vector b)
    {
        a.setY(0);
        b.setY(0);
        return a.subtract(b).length();
    }

    public static double offset(Entity a, Entity b)
    {
        return offset(a.getLocation().toVector(), b.getLocation().toVector());
    }

    public static double offset(Location a, Location b)
    {
        return offset(a.toVector(), b.toVector());
    }

    public static double offset(final Vector a, final Vector b)
    {
        return a.subtract(b).length();
    }

    public static Vector getHorizontalVector(final Vector v)
    {
        v.setY(0);
        return v;
    }

    public static Vector getVerticalVector(final Vector v)
    {
        v.setX(0);
        v.setZ(0);
        return v;
    }

    public static String serializeLocation(Location location)
    {
        final int X = (int) location.getX();
        final int Y = (int) location.getY();
        final int Z = (int) location.getZ();
        final int P = (int) location.getPitch();
        final int Yaw = (int) location.getYaw();
        return new String(String.valueOf(location.getWorld().getName()) + "," + X + "," + Y + "," + Z + "," + P + "," + Yaw);
    }

    public static Location deserializeLocation(String string)
    {
        String[] parts = string.split(",");
        World world = Bukkit.getServer().getWorld(parts[0]);
        Double LX = Double.parseDouble(parts[1]);
        Double LY = Double.parseDouble(parts[2]);
        Double LZ = Double.parseDouble(parts[3]);
        Float P = Float.parseFloat(parts[4]);
        Float Y = Float.parseFloat(parts[5]);
        Location result = new Location(world, LX, LY, LZ);
        result.setPitch(P);
        result.setYaw(Y);
        return result;
    }

    public static long averageLong(List<Long> list)
    {
        long add = 0L;
        
        for (final Long listlist : list)
        {
            add += listlist;
        }
        
        return add / list.size();
    }

    public static double averageDouble(List<Double> list)
    {
        Double add = 0.0;
        
        for (Double listlist : list)
        {
            add += listlist;
        }
        
        return add / list.size();
    }
    
	public static boolean isKnownWithSpeedDirection(double xDiff, double zDiff, float yaw, double speed, int yawSpeed)
	{
        if (yawSpeed > 0)
        {
            for (int i = 0; i < yawSpeed; i++)
                if (isDirection(xDiff, zDiff, wrapAngleTo180_float(yaw + i), speed))
                    return true;
        }
        else
            for (int i = 0; i > yawSpeed; i--)
                if (isDirection(xDiff, zDiff, wrapAngleTo180_float(yaw + i), speed))
                    return true;
        return false;
    }

    public static boolean isKnownDirection(double xDiff, double zDiff, float yaw, double speed)
    {
    	if (isDirection(xDiff, zDiff, wrapAngleTo180_float(yaw), speed))
    		return true;
    	return false;
    }
    
    public static boolean isDirection(double xDiff, double zDiff, float yaw, double speed)
    {
        return (Math.abs(getMotion(wrapAngleTo180_float(yaw))[0] * speed - xDiff) < 0.07 && Math.abs(getMotion(wrapAngleTo180_float(yaw))[1] * speed - zDiff) < 0.07) || (Math.abs(getMotion(wrapAngleTo180_float(yaw + 45.0f))[0] * speed - xDiff) < 0.07 && Math.abs(getMotion(wrapAngleTo180_float(yaw + 45.0f))[1] * speed - zDiff) < 0.07) || (Math.abs(getMotion(wrapAngleTo180_float(yaw - 45.0f))[0] * speed - xDiff) < 0.07 && Math.abs(getMotion(wrapAngleTo180_float(yaw - 45.0f))[1] * speed - zDiff) < 0.07) || (Math.abs(getMotion(wrapAngleTo180_float(yaw + 90.0f))[0] * speed - xDiff) < 0.05 && Math.abs(getMotion(wrapAngleTo180_float(yaw + 90.0f))[1] * speed - zDiff) < 0.05) || (Math.abs(getMotion(wrapAngleTo180_float(yaw - 90.0f))[0] * speed - xDiff) < 0.05 && Math.abs(getMotion(wrapAngleTo180_float(yaw - 90.0f))[1] * speed - zDiff) < 0.05) || (Math.abs(getMotion(wrapAngleTo180_float(yaw - 90.0f - 45.0f))[0] * speed - xDiff) < 0.05 && Math.abs(getMotion(wrapAngleTo180_float(yaw - 90.0f - 45.0f))[1] * speed - zDiff) < 0.05) || (Math.abs(getMotion(wrapAngleTo180_float(yaw + 180.0f))[0] * speed - xDiff) < 0.05 && Math.abs(getMotion(wrapAngleTo180_float(yaw + 180.0f))[1] * speed - zDiff) < 0.05) || (Math.abs(getMotion(wrapAngleTo180_float(yaw + 90.0f + 45.0f))[0] * speed - xDiff) < 0.05 && Math.abs(getMotion(wrapAngleTo180_float(yaw + 90.0f + 45.0f))[1] * speed - zDiff) < 0.05);
    }

	public static double[] getMotion(float yaw)
	{
		return new double[] { -Math.sin(Math.toRadians(yaw)), Math.cos(Math.toRadians(yaw)) };
	}

	public static float wrapAngleTo180_float(float value)
	{
		value = value % 360.0F;

		if (value >= 180.0F)
		{
			value -= 360.0F;
		}

		if (value < -180.0F)
		{
			value += 360.0F;
		}

		return value;
	}
	
    public static Vector getRotation(Location one, Location two)
    {
        double dx = two.getX() - one.getX();
        double dy = two.getY() - one.getY();
        double dz = two.getZ() - one.getZ();
        double distanceXZ = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Math.atan2(dz, dx) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(dy, distanceXZ) * 180.0D / 3.141592653589793D);
        return new Vector(yaw, pitch, 0.0F);
    }

    public static double clamp180(double theta)
    {
        theta %= 360.0D;
        
        if (theta >= 180.0D)
        {
            theta -= 360.0D;
        }
        
        if (theta < -180.0D)
        {
            theta += 360.0D;
        }
        
        return theta;
    }

    public static double getVerticalDistance(Location one, Location two)
    {
        double toReturn = 0.0D;
        double ySqr = (two.getY() - one.getY()) * (two.getY() - one.getY());
        double sqrt = Math.sqrt(ySqr);
        toReturn = Math.abs(sqrt);
        return toReturn;
    }

    public static double getHorizontalDistance(Location one, Location two)
    {
        double toReturn = 0.0D;
        double xSqr = (two.getX() - one.getX()) * (two.getX() - one.getX());
        double zSqr = (two.getZ() - one.getZ()) * (two.getZ() - one.getZ());
        double sqrt = Math.sqrt(xSqr + zSqr);
        toReturn = Math.abs(sqrt);
        return toReturn;
    }
       private static final float[] SIN_TABLE = new float[65536];

       public static double gcd(double a, double b) {
          return b == 0.0D ? a : gcd(b, a % b);
       }

       public static long gcd(long a, long b) {
          while(b > 0L) {
             long temp = b;
             b = a % b;
             a = temp;
          }

          return a;
       }

       public static boolean isBetween(double a, double b, double c) {
          return a >= b && a <= c;
       }

       public static boolean isRoughlyEqual(double d1, double d2, double seperator) {
          return Math.abs(d1 - d2) < seperator;
       }

       public static float sqrt_float(float value) {
          return (float)Math.sqrt((double)value);
       }

       public static float sqrt_double(double value) {
          return (float)Math.sqrt(value);
       }

       public static double getDistance3D(Location a, Location b) {
          double xSqr = (b.getX() - a.getX()) * (b.getX() - a.getX());
          double ySqr = (b.getY() - a.getY()) * (b.getY() - a.getY());
          double zSqr = (b.getZ() - a.getZ()) * (b.getZ() - a.getZ());
          double sqrt = Math.sqrt(xSqr + ySqr + zSqr);
          return Math.abs(sqrt);
       }

       public static float sin(float p_76126_0_) {
          return SIN_TABLE[(int)(p_76126_0_ * 10430.378F) & '\uffff'];
       }

       public static float cos(float value) {
          return SIN_TABLE[(int)(value * 10430.378F + 16384.0F) & '\uffff'];
       }

       public static float getDistanceXZToEntity(Entity entity1, Entity entityIn) {
          org.bukkit.Location p = entity1.getLocation();
          org.bukkit.Location e = entityIn.getLocation();
          float f = (float)(p.getX() - e.getX());
          float f1 = (float)(p.getZ() - e.getZ());
          return Math.abs(sqrt_float(f * f + f1 * f1));
       }

       public static int pingFormula(long ping) {
          return (int)Math.ceil((double)ping / 100.0D);
       }

       public static boolean moved(Location in, Location lastIn) {
         try
         {
        	 return Math.abs(in.getX() - lastIn.getX()) > 0.1D || Math.abs(in.getY() - lastIn.getY()) > 0.1D || Math.abs(in.getZ() - lastIn.getZ()) > 0.1D;
         }
         catch (Exception ex)
         {
        	 return false;
         }
       }
       
       public static double getDirection(Location from, Location to) {
    	      if (from != null && to != null) {
    	         double difX = to.getX() - from.getX();
    	         double difZ = to.getZ() - from.getZ();
    	         return (double)((float)(Math.atan2(difZ, difX) * 180.0D / 3.141592653589793D - 90.0D));
    	      } else {
    	         return 0.0D;
    	      }
    	   }

    	   public static float fixRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
    	      float var4 = wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
    	      if (var4 > p_70663_3_) {
    	         var4 = p_70663_3_;
    	      }

    	      if (var4 < -p_70663_3_) {
    	         var4 = -p_70663_3_;
    	      }

    	      return p_70663_1_ + var4;
    	   }

    	   public static double getDistanceBetweenAngles360(double angle1, double angle2) {
    	      double distance = Math.abs(angle1 % 360.0D - angle2 % 360.0D);
    	      distance = Math.min(360.0D - distance, distance);
    	      return Math.abs(distance);
    	   }

}