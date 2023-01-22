package it.gabrielebologna.grandanticheat.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockPosition {
   public static final BlockPosition ZERO = new BlockPosition(0, 0, 0);
   private int x;
   private int y;
   private int z;

   public BlockPosition(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getZ() {
      return this.z;
   }

   public Block getBlock(World world) {
      return UtilBlock.getBlockAsync(new Location(world, (double)this.x, (double)this.y, (double)this.z));
   }
}
