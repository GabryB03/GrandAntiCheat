package it.gabrielebologna.grandanticheat.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@SuppressWarnings("rawtypes")
public class UtilReflection {

	   private static final String serverVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
	   public static final Class EntityPlayer = getNMSClass("EntityPlayer");
	   public static final Class Entity = getNMSClass("Entity");
	   public static final Class CraftPlayer = getCBClass("entity.CraftPlayer");
	   @SuppressWarnings("unused")
	private static final Class CraftEntity = getCBClass("entity.CraftEntity");
	   public static final Class CraftWorld = getCBClass("CraftWorld");
	   public static final Class World = getNMSClass("World");
	   private static final Method getBlocks;
	   private static final Method getBlocks1_12;

	   static {
	      getBlocks = getMethod(World, "a", getNMSClass("AxisAlignedBB"));
	      getBlocks1_12 = getMethod(World, "getCubes", getNMSClass("Entity"), getNMSClass("AxisAlignedBB"));
	   }

	   @SuppressWarnings("unchecked")
	public static Method getMethod(Class object, String method, @Nullable Class... args) {
	      try {
	         Method methodObject = object.getMethod(method, args);
	         methodObject.setAccessible(true);
	         return methodObject;
	      } catch (NoSuchMethodException var4) {
	         var4.printStackTrace();
	         return null;
	      }
	   }

	   public static Object getInvokedMethod(Method method, Object object, Object... args) {
	      try {
	         method.setAccessible(true);
	         return method.invoke(object, args);
	      } catch (Exception var4) {
	         var4.printStackTrace();
	         return null;
	      }
	   }

	   public static Field getField(Class object, String field) {
	      try {
	         Field fieldObject = object.getField(field);
	         fieldObject.setAccessible(true);
	         return fieldObject;
	      } catch (NoSuchFieldException var3) {
	         var3.printStackTrace();
	         return null;
	      }
	   }

	   public static Object getInvokedField(Field field, Object object) {
	      try {
	         field.setAccessible(true);
	         return field.get(object);
	      } catch (IllegalAccessException var3) {
	         var3.printStackTrace();
	         return null;
	      }
	   }

	   public static Class getNMSClass(String string) {
	      return getClass("net.minecraft.server." + serverVersion + "." + string);
	   }

	   public static boolean isBukkitVerison(String version) {
	      return serverVersion.contains(version);
	   }

	   public static boolean isNewVersion() {
	      return isBukkitVerison("1_9") || isBukkitVerison("1_1");
	   }

	   public static Class getCBClass(String string) {
	      return getClass("org.bukkit.craftbukkit." + serverVersion + "." + string);
	   }

	   public static Class getClass(String string) {
	      try {
	         return Class.forName(string);
	      } catch (ClassNotFoundException var2) {
	         var2.printStackTrace();
	         return null;
	      }
	   }

	   public static Collection getCollidingBlocks(Player player, Object axisAlignedBB) {
	      Object world = getInvokedMethod(getMethod(CraftWorld, "getHandle"), player.getWorld());
	      return (Collection)(isNewVersion() ? getInvokedMethod(getBlocks1_12, world, null, axisAlignedBB) : getInvokedMethod(getBlocks, world, axisAlignedBB));
	   }

	   public static Object getBoundingBox(Player player) {
	      return isBukkitVerison("1_7") ? getInvokedField(getField(Entity, "boundingBox"), getEntityPlayer(player)) : getInvokedMethod(getMethod(EntityPlayer, "getBoundingBox"), getEntityPlayer(player));
	   }

	   public static Object expandBoundingBox(Object box, double x, double y, double z) {
	      return getInvokedMethod(getMethod(box.getClass(), "grow", Double.TYPE, Double.TYPE, Double.TYPE), box, x, y, z);
	   }

	   @SuppressWarnings("unchecked")
	public static Object modifyBoundingBox(Object box, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
	      double newminX = (Double)getInvokedField(getField(box.getClass(), "a"), box) + minX;
	      double newminY = (Double)getInvokedField(getField(box.getClass(), "b"), box) + minY;
	      double newminZ = (Double)getInvokedField(getField(box.getClass(), "c"), box) + minZ;
	      double newmaxX = (Double)getInvokedField(getField(box.getClass(), "d"), box) + maxX;
	      double newmaxY = (Double)getInvokedField(getField(box.getClass(), "e"), box) + maxY;
	      double newmaxZ = (Double)getInvokedField(getField(box.getClass(), "f"), box) + maxZ;

	      try {
	         return getNMSClass("AxisAlignedBB").getConstructor(Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE).newInstance(newminX, newminY, newminZ, newmaxX, newmaxY, newmaxZ);
	      } catch (Exception var26) {
	         var26.printStackTrace();
	         return null;
	      }
	   }

	   public static Object getEntityPlayer(Player player) {
	      return getInvokedMethod(getMethod(CraftPlayer, "getHandle"), player);
	   }

	    public static double getMotionY(Player player) {
	        double motionY = 0;
	        try {
	            motionY = (double) UtilReflection.getEntityPlayer(player).getClass().getField("motY").get(UtilReflection.getEntityPlayer(player));
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        } catch (NoSuchFieldException e) {
	            e.printStackTrace();
	        }

	        return motionY;
	    }
}
