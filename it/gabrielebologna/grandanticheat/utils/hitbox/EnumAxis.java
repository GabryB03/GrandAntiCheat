package it.gabrielebologna.grandanticheat.utils.hitbox;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import java.util.Map;

@SuppressWarnings({ "rawtypes", "unchecked" })
public enum EnumAxis implements Predicate, INamable {
   X("x", EnumDirectionLimit.HORIZONTAL),
   Y("y", EnumDirectionLimit.VERTICAL),
   Z("z", EnumDirectionLimit.HORIZONTAL);

   private static final Map d = Maps.newHashMap();
   private final String e;
   private final EnumDirectionLimit f;

   private EnumAxis(String s, EnumDirectionLimit enumdirection_enumdirectionlimit) {
      this.e = s;
      this.f = enumdirection_enumdirectionlimit;
   }

   public String a() {
      return this.e;
   }

   public boolean b() {
      return this.f == EnumDirectionLimit.VERTICAL;
   }

   public boolean c() {
      return this.f == EnumDirectionLimit.HORIZONTAL;
   }

   public String toString() {
      return this.e;
   }

   public boolean a(EnumDirection enumdirection) {
      return enumdirection != null && enumdirection.k() == this;
   }

   public EnumDirectionLimit d() {
      return this.f;
   }

   public String getName() {
      return this.e;
   }

   public boolean apply(EnumDirection arg0) {
      return this.a(arg0);
   }

   static {
      EnumAxis[] aenumdirection_enumaxis = values();
      int i = aenumdirection_enumaxis.length;

      for(int j = 0; j < i; ++j) {
         EnumAxis enumdirection_enumaxis = aenumdirection_enumaxis[j];
         d.put(enumdirection_enumaxis.a().toLowerCase(), enumdirection_enumaxis);
      }

   }

@Override
public boolean apply(Object arg0) {
	// TODO Auto-generated method stub
	return false;
}
}
