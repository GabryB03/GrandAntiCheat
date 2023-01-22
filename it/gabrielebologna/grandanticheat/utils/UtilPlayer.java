package it.gabrielebologna.grandanticheat.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UtilPlayer
{
	   public static final double PLAYER_WIDTH = 0.6D;
	   public static final double PLAYER_HEIGTH = 1.8D;
	   public static final double PLAYER_HEIGTH_SNEAKING = 1.6D;
	   public static boolean isOnGround1(Player player) {
		      return isOnGround(player, 0.25D);
		   }

		   public static boolean isOnGround(Player player, double yExpanded) {
		      Object box = UtilReflection.modifyBoundingBox(UtilReflection.getBoundingBox(player), 0.0D, -yExpanded, 0.0D, 0.0D, 0.0D, 0.0D);
		      return UtilReflection.getCollidingBlocks(player, box).size() > 0;
		   }

		   public static boolean inUnderBlock(Player player) {
		      Object box = UtilReflection.modifyBoundingBox(UtilReflection.getBoundingBox(player), 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D);
		      double minX = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "a"), box);
		      double minY = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "b"), box);
		      double minZ = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "c"), box);
		      double maxX = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "d"), box);
		      double maxY = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "e"), box);
		      double maxZ = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "f"), box);

		      for(double x = minX; x < maxX; ++x) {
		         for(double y = minY; y < maxY; ++y) {
		            for(double z = minZ; z < maxZ; ++z) {
		               Block block = (new Location(player.getWorld(), x, y, z)).getBlock();
		               if (block.getType().isSolid()) {
		                  return true;
		               }
		            }
		         }
		      }

		      return false;
		   }

		   public static boolean inUnderBlock(Player player, double y2) {
		      Object box = UtilReflection.modifyBoundingBox(UtilReflection.getBoundingBox(player), 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D);
		      double minX = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "a"), box);
		      double minY = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "b"), box);
		      double minZ = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "c"), box);
		      double maxX = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "d"), box);
		      double maxY = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "e"), box);
		      double maxZ = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "f"), box);

		      for(double x = minX; x < maxX; ++x) {
		         for(double y = minY; y < maxY; ++y) {
		            for(double z = minZ; z < maxZ; ++z) {
		               Block block = (new Location(player.getWorld(), x, y + y2, z)).getBlock();
		               if (block.getType().isSolid()) {
		                  return true;
		               }
		            }
		         }
		      }

		      return false;
		   }

		   public static boolean isOnIce(Player player) {
		      Object box = UtilReflection.modifyBoundingBox(UtilReflection.getBoundingBox(player), 0.0D, -0.5D, 0.0D, 0.0D, 0.0D, 0.0D);
		      double minX = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "a"), box);
		      double minY = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "b"), box);
		      double minZ = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "c"), box);
		      double maxX = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "d"), box);
		      double maxY = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "e"), box);
		      double maxZ = (Double)UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "f"), box);

		      for(double x = minX; x < maxX; ++x) {
		         for(double y = minY; y < maxY; ++y) {
		            for(double z = minZ; z < maxZ; ++z) {
		               for(double i = -1.0D; i <= 1.0D; i += player.getLocation().getY() - (double)player.getLocation().getBlockY() + 0.1D) {
		                  Block block = (new Location(player.getWorld(), x, y - i - 0.1D, z)).getBlock();
		                  if (block.getType().equals(Material.ICE) || block.getType().equals(Material.PACKED_ICE)) {
		                     return true;
		                  }
		               }
		            }
		         }
		      }

		      return false;
		   }


	   public static boolean isColliding(Location location, MaterialCheck material) {
	      double d = 0.3D;
	      return material.checkMaterial(location) || material.checkMaterial(location.clone().add(d, 0.0D, 0.0D)) || material.checkMaterial(location.clone().add(-d, 0.0D, 0.0D)) || material.checkMaterial(location.clone().add(d, 0.0D, d)) || material.checkMaterial(location.clone().add(-d, 0.0D, d)) || material.checkMaterial(location.clone().add(d, 0.0D, -d)) || material.checkMaterial(location.clone().add(-d, 0.0D, -d)) || material.checkMaterial(location.clone().add(0.0D, 0.0D, -d)) || material.checkMaterial(location.clone().add(0.0D, 0.0D, d));
	   }

	   public static boolean shouldNotFlag(Location loc) {
	      return isMaterialGlideable(loc.getBlock().getType()) || isMaterialGlideable(loc.clone().add(0.3D, 0.0D, 0.0D).getBlock().getType()) || isMaterialGlideable(loc.clone().add(0.3D, 0.0D, 0.3D).getBlock().getType()) || isMaterialGlideable(loc.clone().add(0.3D, 0.0D, -0.3D).getBlock().getType()) || isMaterialGlideable(loc.clone().add(-0.3D, 0.0D, 0.0D).getBlock().getType()) || isMaterialGlideable(loc.clone().add(-0.3D, 0.0D, -0.3D).getBlock().getType()) || isMaterialGlideable(loc.clone().add(0.0D, 0.0D, -0.3D).getBlock().getType()) || isMaterialGlideable(loc.clone().add(0.0D, 0.0D, 0.3D).getBlock().getType()) || isOnGround(loc.clone().add(0.0D, 0.0D, 0.0D));
	   }

	   public static boolean isOnGround(Location loc) {
	      return loc.getBlock().getLocation().clone().add(0.0D, -1.0D, 0.0D).getBlock().getType().isSolid() || loc.clone().add(0.3D, -1.0D, 0.0D).getBlock().getType().isSolid() || loc.clone().add(0.3D, -1.0D, 0.3D).getBlock().getType().isSolid() || loc.clone().add(0.3D, -1.0D, -0.3D).getBlock().getType().isSolid() || loc.clone().add(-0.3D, -1.0D, 0.0D).getBlock().getType().isSolid() || loc.clone().add(-0.3D, -1.0D, -0.3D).getBlock().getType().isSolid() || loc.clone().add(0.0D, -1.0D, -0.3D).getBlock().getType().isSolid() || loc.clone().add(0.0D, -1.0D, 0.3D).getBlock().getType().isSolid();
	   }

	   public static boolean onLiquid(Location loc) {
	      return loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.WATER || loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.LAVA || loc.getBlock().getRelative(BlockFace.DOWN).isLiquid();
	   }

	   public static boolean onSlime(PlayerMoveEvent e) {
	      Location l = e.getPlayer().getLocation();
	      Block sB = l.getBlock().getRelative(BlockFace.DOWN);
	      Block bl1 = sB.getLocation().add(0.0D, 1.0D, 0.0D).getBlock();
	      Block bl = sB.getLocation().add(0.0D, -1.0D, 0.0D).getBlock();
	      Block bls = sB.getLocation().add(0.0D, e.getTo().getY() - e.getFrom().getY(), 0.0D).getBlock();
	      Block blt = sB.getLocation().add(0.0D, Math.abs(e.getTo().getY() - e.getFrom().getY()), 0.0D).getBlock();
	      return bl1.getType() == Material.SLIME_BLOCK || bl.getType() == Material.SLIME_BLOCK || bls.getType() == Material.SLIME_BLOCK || blt.getType() == Material.SLIME_BLOCK;
	   }
	   
	   public static boolean c(Distance distance, Material m) {
		      return isMaterialSorround(distance, m);
		   }

		   public static boolean is(Distance distance) {
		      if (c(distance, Material.ACACIA_STAIRS)) {
		         return true;
		      } else if (c(distance, Material.BIRCH_WOOD_STAIRS)) {
		         return true;
		      } else if (c(distance, Material.BRICK_STAIRS)) {
		         return true;
		      } else if (c(distance, Material.COBBLESTONE_STAIRS)) {
		         return true;
		      } else if (c(distance, Material.DARK_OAK_STAIRS)) {
		         return true;
		      } else if (c(distance, Material.JUNGLE_WOOD_STAIRS)) {
		         return true;
		      } else if (c(distance, Material.NETHER_BRICK_STAIRS)) {
		         return true;
		      } else if (c(distance, Material.RED_SANDSTONE_STAIRS)) {
		         return true;
		      } else if (c(distance, Material.SANDSTONE_STAIRS)) {
		         return true;
		      } else if (c(distance, Material.SMOOTH_STAIRS)) {
		         return true;
		      } else if (c(distance, Material.SPRUCE_WOOD_STAIRS)) {
		         return true;
		      } else if (c(distance, Material.WOOD_STAIRS)) {
		         return true;
		      } else if (c(distance, Material.QUARTZ_STAIRS)) {
		         return true;
		      } else if (c(distance, Material.STEP)) {
		         return true;
		      } else if (c(distance, Material.WOOD_STEP)) {
		         return true;
		      } else {
		         return c(distance, Material.SKULL);
		      }
		   }

		   @SuppressWarnings("deprecation")
		public static boolean isMaterialGlideable(Material mat) {
		      if (mat.getId() == 66 || mat.getId() == 107)
		      {
		    	  return true;
		      }
		      return false;
		   }


	   public static boolean isLiquidSorruding(Distance d) {
	      int raduis = 1;
	      Block middle = d.getTo().getBlock();

	      for(int x = raduis; x >= -raduis; --x) {
	         for(int y = raduis; y >= -raduis; --y) {
	            for(int z = raduis; z >= -raduis; --z) {
	               if (middle.getRelative(x, y, z).getType() == Material.WATER || middle.getRelative(x, y, z).getType() == Material.STATIONARY_WATER || middle.getRelative(x, y, z).getType() == Material.LAVA || middle.getRelative(x, y, z).getType() == Material.STATIONARY_LAVA) {
	                  return true;
	               }
	            }
	         }
	      }

	      return false;
	   }

	   public static boolean isMaterialSorround(Distance d, Material m) {
	      int raduis = 2;
	      Block middle = d.getTo().getBlock();

	      for(int x = raduis; x >= -raduis; --x) {
	         for(int y = raduis; y >= -raduis; --y) {
	            for(int z = raduis; z >= -raduis; --z) {
	               if (middle.getRelative(x, y, z).getType() == m) {
	                  return true;
	               }
	            }
	         }
	      }

	      return false;
	   }

	   public static boolean isMaterialSorround(Distance d, Material m, int rad) {
	      int raduis = rad;
	      Block middle = d.getTo().getBlock();

	      for(int x = rad; x >= -raduis; --x) {
	         for(int y = raduis; y >= -raduis; --y) {
	            for(int z = raduis; z >= -raduis; --z) {
	               if (middle.getRelative(x, y, z).getType() == m) {
	                  return true;
	               }
	            }
	         }
	      }

	      return false;
	   }

	   public static boolean isMaterialSorroundLight(Distance d, Material m, Material m2) {
	      int raduis = 2;
	      Block middle = d.getTo().getBlock();

	      for(int x = raduis; x >= -raduis; --x) {
	         for(int y = raduis; y >= -raduis; --y) {
	            for(int z = raduis; z >= -raduis; --z) {
	               if (middle.getRelative(x, y, z).getType() == m || middle.getRelative(x, y, z).getType() == m2) {
	                  return true;
	               }
	            }
	         }
	      }

	      return false;
	   }

	
    public static void clear(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.setSprinting(false);
        player.setFoodLevel(20);
        player.setSaturation(3.0f);
        player.setExhaustion(0.0f);
        player.setMaxHealth(20.0);
        player.setHealth(player.getMaxHealth());
        player.setFireTicks(0);
        player.setFallDistance(0.0f);
        player.setLevel(0);
        player.setExp(0.0f);
        player.setWalkSpeed(0.2f);
        player.setFlySpeed(0.1f);
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.updateInventory();
        for (final PotionEffect potion : player.getActivePotionEffects()) {
            player.removePotionEffect(potion.getType());
        }
    }

    public static Location getEyeLocation(Player player) {
        final Location eye = player.getLocation();
        eye.setY(eye.getY() + player.getEyeHeight());
        return eye;
    }

    public static boolean isInWater(Player player) {
        final Material m = player.getLocation().getBlock().getType();
        return m == Material.STATIONARY_WATER || m == Material.WATER;
    }

    public static boolean isOnClimbable(Player player, int blocks) {
        if (blocks == 0) {
            for (Block block : UtilBlock.getSurrounding(player.getLocation().getBlock(), false)) {
                if (block.getType() == Material.LADDER || block.getType() == Material.VINE) {
                    return true;
                }
            }
        } else {
            for (Block block : UtilBlock.getSurrounding(player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock(),
                    false)) {
                if (block.getType() == Material.LADDER || block.getType() == Material.VINE) {
                    return true;
                }
            }
        }
        return player.getLocation().getBlock().getType() == Material.LADDER
                || player.getLocation().getBlock().getType() == Material.VINE;
    }

    public static boolean isInAir(Player player) {
        for (Block block : UtilBlock.getSurrounding(player.getLocation().getBlock(), false)) {
            if (block.getType() == Material.AIR) {
                return true;
            }
        }
        return player.getLocation().getBlock().getType() == Material.AIR;
    }

    public static boolean isPartiallyStuck(Player player) {
        if (player.getLocation().clone().getBlock() == null) {
            return false;
        }
        Block block = player.getLocation().clone().getBlock();
        if (UtilCheat.isSlab(block) || UtilCheat.isStair(block)) {
            return false;
        }
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()
                || player.getLocation().getBlock().getRelative(BlockFace.UP).getType().isSolid()) {
            return true;
        }
        if (player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock().getRelative(BlockFace.DOWN).getType()
                .isSolid()
                || player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock().getRelative(BlockFace.UP).getType()
                .isSolid()) {
            return true;
        }
        return block.getType().isSolid();
    }

    public static boolean isFullyStuck(Player player) {
        Block block1 = player.getLocation().clone().getBlock();
        Block block2 = player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock();
        if (block1.getType().isSolid() && block2.getType().isSolid()) {
            return true;
        }
        return block1.getRelative(BlockFace.DOWN).getType().isSolid()
                || block1.getLocation().getBlock().getRelative(BlockFace.UP).getType().isSolid()
                && block2.getRelative(BlockFace.DOWN).getType().isSolid()
                || block2.getLocation().getBlock().getRelative(BlockFace.UP).getType().isSolid();
    }

    public static boolean isOnGround(Player player) {
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
            return true;
        }
        Location a = player.getLocation().clone();
        a.setY(a.getY() - 0.5);
        if (a.getBlock().getType() != Material.AIR) {
            return true;
        }
        a = player.getLocation().clone();
        a.setY(a.getY() + 0.5);
        return a.getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR
                || UtilCheat.isBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN),
                new Material[]{Material.FENCE, Material.FENCE_GATE, Material.COBBLE_WALL, Material.LADDER});
    }

    public static List<Entity> getNearbyRidables(Location loc, double distance) {
        final List<Entity> entities = new ArrayList<>();
        for (final Entity entity : new ArrayList<>(loc.getWorld().getEntities())) {
            if (!entity.getType().equals(EntityType.HORSE)
                    && !entity.getType().equals(EntityType.BOAT)) {
                continue;
            }
            Bukkit.getServer()
                    .broadcastMessage(new StringBuilder(String.valueOf(entity.getLocation().distance(loc))).toString());
            if (entity.getLocation().distance(loc) > distance) {
                continue;
            }
            entities.add(entity);
        }
        return entities;
    }
	
	   public static boolean hasGround(Player player) {
		      return !player.isFlying() && player.getLocation().subtract(0.0D, 0.1D, 0.0D).getBlock().getType().isSolid() && !player.getLocation().subtract(0.0D, 0.1D, 0.0D).getBlock().getType().toString().contains("SIGN") && !player.getLocation().subtract(0.0D, 0.1D, 0.0D).getBlock().getType().toString().contains("PANE");
		   }

		   public static boolean hasGroundDoorEx(Player player) {
		      return !player.isFlying() && player.getLocation().subtract(0.0D, 0.1D, 0.0D).getBlock().getType().isSolid() && !player.getLocation().subtract(0.0D, 0.1D, 0.0D).getBlock().getType().toString().contains("SIGN") && !player.getLocation().subtract(0.0D, 0.1D, 0.0D).getBlock().getType().toString().contains("PANE") && !player.getLocation().subtract(0.0D, 0.1D, 0.0D).getBlock().getType().toString().contains("DOOR");
		   }

		   public static boolean hasGroundv2(Player player) {
		      Location loc = player.getLocation();
		      double expand = 0.3D;

		      for(double x = -expand; x <= expand; x += expand) {
		         for(double z = -expand; z <= expand; z += expand) {
		            if (loc.clone().add(z, -0.1D, x).getBlock().getType().isSolid() && !loc.clone().add(z, -0.1D, x).getBlock().getType().toString().contains("SIGN")) {
		               return true;
		            }
		         }
		      }

		      return false;
		   }

		   public static int getDistanceToGround(Player player) {
		      Location loc = player.getLocation().clone();
		      double y = (double)loc.getBlockY();
		      int distance = 0;

		      for(double i = y; i >= 0.0D; --i) {
		         loc.setY(i);
		         if (loc.getBlock().getType().isSolid()) {
		            break;
		         }

		         ++distance;
		      }

		      return distance;
		   }

		   public static boolean isNearGround(Player player, double offset) {
		      double expand = 0.3D;

		      for(double x = -expand; x <= expand; x += expand) {
		         for(double z = -expand; z <= expand; z += expand) {
		            if (player.getLocation().clone().add(z, offset, x).getBlock().getType() != Material.AIR) {
		               return true;
		            }
		         }
		      }

		      return false;
		   }

		   public static boolean blockNearHead(Player player) {
		      double expand = 0.3D;

		      for(double x = -expand; x <= expand; x += expand) {
		         for(double z = -expand; z <= expand; z += expand) {
		            if (player.getLocation().clone().add(z, 2.0D, x).getBlock().getType() != Material.AIR) {
		               return true;
		            }
		         }
		      }

		      return false;
		   }

		   public static boolean isHalfBlockNearHead(Player player) {
		      double expand = 0.3D;

		      for(double x = -expand; x <= expand; x += expand) {
		         for(double z = -expand; z <= expand; z += expand) {
		            if (player.getLocation().clone().add(z, 1.5001D, x).getBlock().getType() != Material.AIR) {
		               return true;
		            }
		         }
		      }

		      return false;
		   }

		   public static boolean isOnFence(Player player) {
		      double expand = 0.3D;

		      for(double x = -expand; x <= expand; x += expand) {
		         for(double z = -expand; z <= expand; z += expand) {
		            if (player.getLocation().clone().add(z, -0.5001D, x).getBlock().getType().toString().contains("FENCE") || player.getLocation().clone().add(z, -0.5001D, x).getBlock().getType().toString().contains("WALL")) {
		               return true;
		            }
		         }
		      }

		      return false;
		   }

		   public static boolean isInWeb(Player player) {
		      double expand = 0.3D;

		      for(double x = -expand; x <= expand; x += expand) {
		         for(double z = -expand; z <= expand; z += expand) {
		            if (player.getLocation().clone().add(z, 0.0D, x).getBlock().getType() == Material.WEB) {
		               return true;
		            }
		         }
		      }

		      return false;
		   }

		   public static boolean isInWebOffset(Player player) {
		      double expand = 0.3D;

		      for(double x = -expand; x <= expand; x += expand) {
		         for(double z = -expand; z <= expand; z += expand) {
		            if (player.getLocation().clone().add(z, 1.5001D, x).getBlock().getType() == Material.WEB) {
		               return true;
		            }
		         }
		      }

		      return false;
		   }

		   public static boolean isOnSlab(Player player) {
		      Location loc = player.getLocation();
		      double expand = 0.3D;

		      for(double x = -expand; x <= expand; x += expand) {
		         for(double z = -expand; z <= expand; z += expand) {
		            if (loc.clone().add(z, -0.4999D, x).getBlock().getType().toString().contains("SLAB")) {
		               return true;
		            }
		         }
		      }

		      return false;
		   }

		   public static boolean isOnStairs(Player player) {
		      Location loc = player.getLocation();
		      double expand = 0.3D;

		      for(double x = -expand; x <= expand; x += expand) {
		         for(double z = -expand; z <= expand; z += expand) {
		            if (loc.clone().add(z, -0.1D, x).getBlock().getType().toString().contains("STAIR")) {
		               return true;
		            }
		         }
		      }

		      return false;
		   }

		   public static boolean isOnLilyCarpet(Player player) {
		      Location loc = player.getLocation();
		      double expand = 0.3D;

		      for(double x = -expand; x <= expand; x += expand) {
		         for(double z = -expand; z <= expand; z += expand) {
		            if (loc.clone().add(z, 0.0D, x).getBlock().getType().toString().contains("LILY") || loc.clone().add(z, -0.001D, x).getBlock().getType().toString().contains("CARPET")) {
		               return true;
		            }
		         }
		      }

		      return false;
		   }

		   public static boolean hasKbBypassOld(Player player) {
		      if (player.getLocation().add(0.0D, 2.0D, 0.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(1.0D, 0.0D, 0.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(0.0D, 0.0D, 1.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(-1.0D, 0.0D, 0.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(0.0D, 0.0D, -1.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(1.0D, 1.0D, 0.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(0.0D, 1.0D, 1.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(-1.0D, 1.0D, 0.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(0.0D, 1.0D, -1.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(0.0D, 2.0D, 0.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(1.0D, 2.0D, 0.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(0.0D, 2.0D, 1.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(0.0D, 2.0D, -1.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(-1.0D, 2.0D, 0.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(1.0D, 2.0D, 1.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else if (player.getLocation().add(-1.0D, 2.0D, -1.0D).getBlock().getType() != Material.AIR) {
		         return true;
		      } else {
		         return player.getVehicle() != null;
		      }
		   }

		   public static boolean isNearClimbable(Player player) {
		      double expand = 0.3D;

		      for(double x = -expand; x <= expand; x += expand) {
		         for(double z = -expand; z <= expand; z += expand) {
		            if (player.getLocation().clone().add(z, 0.0D, x).getBlock().getType() == Material.LADDER || player.getLocation().clone().add(z, 0.0D, x).getBlock().getType() == Material.VINE) {
		               return true;
		            }
		         }
		      }

		      return false;
		   }

		   public static boolean isNearLiquid(Player player) {
		      double expand = 0.3D;

		      for(double x = -expand; x <= expand; x += expand) {
		         for(double z = -expand; z <= expand; z += expand) {
		            if (player.getLocation().clone().add(z, 0.0D, x).getBlock().isLiquid()) {
		               return true;
		            }
		         }
		      }

		      return false;
		   }

		   public static boolean isNearLiquidOffset(Player player) {
		      double expand = 0.3D;

		      for(double x = -expand; x <= expand; x += expand) {
		         for(double z = -expand; z <= expand; z += expand) {
		            Material loc = player.getLocation().clone().add(x, -0.5001D, z).getBlock().getType();
		            if (loc != Material.WATER && loc != Material.STATIONARY_WATER && loc != Material.LAVA && loc != Material.STATIONARY_LAVA) {
		               return false;
		            }
		         }
		      }

		      return true;
		   }

		   public static int getPotionEffectLevel(PotionEffectType pet, Player player) {
		      @SuppressWarnings("rawtypes")
			Iterator var2 = player.getActivePotionEffects().iterator();

		      PotionEffect pe;
		      do {
		         if (!var2.hasNext()) {
		            return 0;
		         }

		         pe = (PotionEffect)var2.next();
		      } while(!pe.getType().getName().equals(pet.getName()));

		      return pe.getAmplifier() + 1;
		   }

}