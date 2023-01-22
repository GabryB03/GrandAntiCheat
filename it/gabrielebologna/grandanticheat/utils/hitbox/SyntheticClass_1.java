package it.gabrielebologna.grandanticheat.utils.hitbox;

class SyntheticClass_1 {
   static final int[] a;
   static final int[] b;
   static final int[] c = new int[EnumDirectionLimit.values().length];

   static {
      try {
         c[EnumDirectionLimit.HORIZONTAL.ordinal()] = 1;
      } catch (NoSuchFieldError var11) {
      }

      try {
         c[EnumDirectionLimit.VERTICAL.ordinal()] = 2;
      } catch (NoSuchFieldError var10) {
      }

      b = new int[EnumDirection.values().length];

      try {
         b[EnumDirection.NORTH.ordinal()] = 1;
      } catch (NoSuchFieldError var9) {
      }

      try {
         b[EnumDirection.EAST.ordinal()] = 2;
      } catch (NoSuchFieldError var8) {
      }

      try {
         b[EnumDirection.SOUTH.ordinal()] = 3;
      } catch (NoSuchFieldError var7) {
      }

      try {
         b[EnumDirection.WEST.ordinal()] = 4;
      } catch (NoSuchFieldError var6) {
      }

      try {
         b[EnumDirection.UP.ordinal()] = 5;
      } catch (NoSuchFieldError var5) {
      }

      try {
         b[EnumDirection.DOWN.ordinal()] = 6;
      } catch (NoSuchFieldError var4) {
      }

      a = new int[EnumAxis.values().length];

      try {
         a[EnumAxis.X.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         a[EnumAxis.Y.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         a[EnumAxis.Z.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }

   }
}
