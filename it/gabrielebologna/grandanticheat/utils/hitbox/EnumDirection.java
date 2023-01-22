package it.gabrielebologna.grandanticheat.utils.hitbox;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("unchecked")
public enum EnumDirection implements INamable {
   DOWN(0, 1, -1, "down", EnumAxisDirection.NEGATIVE, EnumAxis.Y, new BaseBlockPosition(0, -1, 0)),
   UP(1, 0, -1, "up", EnumAxisDirection.POSITIVE, EnumAxis.Y, new BaseBlockPosition(0, 1, 0)),
   NORTH(2, 3, 2, "north", EnumAxisDirection.NEGATIVE, EnumAxis.Z, new BaseBlockPosition(0, 0, -1)),
   SOUTH(3, 2, 0, "south", EnumAxisDirection.POSITIVE, EnumAxis.Z, new BaseBlockPosition(0, 0, 1)),
   WEST(4, 5, 1, "west", EnumAxisDirection.NEGATIVE, EnumAxis.X, new BaseBlockPosition(-1, 0, 0)),
   EAST(5, 4, 3, "east", EnumAxisDirection.POSITIVE, EnumAxis.X, new BaseBlockPosition(1, 0, 0));

   private final int g;
   private final int h;
   private final int i;
   private final String j;
   private final EnumAxis k;
   private final EnumAxisDirection l;
   @SuppressWarnings("unused")
private final BaseBlockPosition m;
   private static final EnumDirection[] n = new EnumDirection[6];
   private static final EnumDirection[] o = new EnumDirection[4];
   @SuppressWarnings("rawtypes")
private static final Map p = Maps.newHashMap();

   private EnumDirection(int i, int j, int k, String s, EnumAxisDirection enumdirection_enumaxisdirection, EnumAxis enumdirection_enumaxis, BaseBlockPosition baseblockposition) {
      this.g = i;
      this.i = k;
      this.h = j;
      this.j = s;
      this.k = enumdirection_enumaxis;
      this.l = enumdirection_enumaxisdirection;
      this.m = baseblockposition;
   }

   public int a() {
      return this.g;
   }

   public int b() {
      return this.i;
   }

   public EnumAxisDirection c() {
      return this.l;
   }

   public EnumDirection opposite() {
      return fromType1(this.h);
   }

   public EnumDirection e() {
      switch(SyntheticClass_1.b[this.ordinal()]) {
      case 1:
         return EAST;
      case 2:
         return SOUTH;
      case 3:
         return WEST;
      case 4:
         return NORTH;
      default:
         throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
      }
   }

   public EnumDirection f() {
      switch(SyntheticClass_1.b[this.ordinal()]) {
      case 1:
         return WEST;
      case 2:
         return NORTH;
      case 3:
         return EAST;
      case 4:
         return SOUTH;
      default:
         throw new IllegalStateException("Unable to get CCW facing of " + this);
      }
   }

   public int getAdjacentX() {
      return this.k == EnumAxis.X ? this.l.a() : 0;
   }

   public int getAdjacentY() {
      return this.k == EnumAxis.Y ? this.l.a() : 0;
   }

   public int getAdjacentZ() {
      return this.k == EnumAxis.Z ? this.l.a() : 0;
   }

   public String j() {
      return this.j;
   }

   public EnumAxis k() {
      return this.k;
   }

   public static EnumDirection fromType1(int i) {
      return n[BaseBlockPosition.a(i % n.length)];
   }

   public static EnumDirection fromType2(int i) {
      return o[BaseBlockPosition.a(i % o.length)];
   }

   public static EnumDirection fromAngle(double d0) {
      return fromType2(BaseBlockPosition.floor(d0 / 90.0D + 0.5D) & 3);
   }

   public static EnumDirection a(Random random) {
      return values()[random.nextInt(values().length)];
   }

   public String toString() {
      return this.j;
   }

   public String getName() {
      return this.j;
   }

   public static EnumDirection a(EnumAxisDirection enumdirection_enumaxisdirection, EnumAxis enumdirection_enumaxis) {
      EnumDirection[] aenumdirection = values();
      int i = aenumdirection.length;

      for(int j = 0; j < i; ++j) {
         EnumDirection enumdirection = aenumdirection[j];
         if (enumdirection.c() == enumdirection_enumaxisdirection && enumdirection.k() == enumdirection_enumaxis) {
            return enumdirection;
         }
      }

      throw new IllegalArgumentException("No such direction: " + enumdirection_enumaxisdirection + " " + enumdirection_enumaxis);
   }

   static {
      EnumDirection[] aenumdirection = values();
      int i = aenumdirection.length;

      for(int j = 0; j < i; ++j) {
         EnumDirection enumdirection = aenumdirection[j];
         n[enumdirection.g] = enumdirection;
         if (enumdirection.k().c()) {
            o[enumdirection.i] = enumdirection;
         }

         p.put(enumdirection.j().toLowerCase(), enumdirection);
      }

   }
}
