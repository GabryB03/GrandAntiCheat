package it.gabrielebologna.grandanticheat.utils.hitbox;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import java.util.Iterator;
import java.util.Random;

@SuppressWarnings("rawtypes")
public enum EnumDirectionLimit implements Predicate, Iterable {
   HORIZONTAL,
   VERTICAL;

   public EnumDirection[] a() {
      switch(SyntheticClass_1.c[this.ordinal()]) {
      case 1:
         return new EnumDirection[]{EnumDirection.NORTH, EnumDirection.EAST, EnumDirection.SOUTH, EnumDirection.WEST};
      case 2:
         return new EnumDirection[]{EnumDirection.UP, EnumDirection.DOWN};
      default:
         throw new Error("Someone's been tampering with the universe!");
      }
   }

   public EnumDirection a(Random random) {
      EnumDirection[] aenumdirection = this.a();
      return aenumdirection[random.nextInt(aenumdirection.length)];
   }

   public boolean a(EnumDirection enumdirection) {
      return enumdirection != null && enumdirection.k().d() == this;
   }

   public Iterator iterator() {
      return Iterators.forArray(this.a());
   }

   public boolean apply(EnumDirection object) {
      return this.a(object);
   }

@Override
public boolean apply(Object arg0) {
	// TODO Auto-generated method stub
	return false;
}
}
