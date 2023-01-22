package it.gabrielebologna.grandanticheat.utils.hitbox;

import org.bukkit.entity.Entity;

public class HitboxUtils {
   public static AxisAlignedBB getHitbox(Entity et) {
      try {
         Object entityHandle = et.getClass().getMethod("getHandle").invoke(et);
         Object entityBox = entityHandle.getClass().getMethod("getBoundingBox").invoke(entityHandle);
         double minXe = (Double)entityBox.getClass().getField("a").get(entityBox);
         double minYe = (Double)entityBox.getClass().getField("b").get(entityBox);
         double minZe = (Double)entityBox.getClass().getField("c").get(entityBox);
         double maxXe = (Double)entityBox.getClass().getField("d").get(entityBox);
         double maxYe = (Double)entityBox.getClass().getField("e").get(entityBox);
         double maxZe = (Double)entityBox.getClass().getField("f").get(entityBox);
         return new AxisAlignedBB(minXe, minYe, minZe, maxXe, maxYe, maxZe);
      } catch (Exception var15) {
         var15.printStackTrace();
         return null;
      }
   }

   public static double getDistBetweenHitboxes(Entity entity1, Entity entity2) {
      AxisAlignedBB box1 = getHitbox(entity1);
      AxisAlignedBB box2 = getHitbox(entity2);
      double distX = box2.d - box1.d;
      double distZ = box2.f - box1.f;
      return Math.abs(Math.sqrt(distX * distX + distZ * distZ));
   }

   public static double getYOffsetBetweenHitboxes(AxisAlignedBB box1, AxisAlignedBB box2) {
      return Math.abs(box2.e - box1.e);
   }
}
