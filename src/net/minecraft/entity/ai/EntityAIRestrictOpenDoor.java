package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIRestrictOpenDoor extends EntityAIBase {
   private final EntityCreature entity;
   private VillageDoorInfo frontDoor;

   public EntityAIRestrictOpenDoor(EntityCreature p_i1651_1_) {
      this.entity = p_i1651_1_;
      if (!(p_i1651_1_.getNavigator() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
      }
   }

   public boolean shouldExecute() {
      if (this.entity.world.isDaytime()) {
         return false;
      } else {
         BlockPos blockpos = new BlockPos(this.entity);
         Village village = this.entity.world.getVillageCollection().getNearestVillage(blockpos, 16);
         if (village == null) {
            return false;
         } else {
            this.frontDoor = village.getNearestDoor(blockpos);
            if (this.frontDoor == null) {
               return false;
            } else {
               return (double)this.frontDoor.getDistanceToInsideBlockSq(blockpos) < 2.25D;
            }
         }
      }
   }

   public boolean shouldContinueExecuting() {
      if (this.entity.world.isDaytime()) {
         return false;
      } else {
         return !this.frontDoor.getIsDetachedFromVillageFlag() && this.frontDoor.isInsideSide(new BlockPos(this.entity));
      }
   }

   public void startExecuting() {
      ((PathNavigateGround)this.entity.getNavigator()).setBreakDoors(false);
      ((PathNavigateGround)this.entity.getNavigator()).setEnterDoors(false);
   }

   public void resetTask() {
      ((PathNavigateGround)this.entity.getNavigator()).setBreakDoors(true);
      ((PathNavigateGround)this.entity.getNavigator()).setEnterDoors(true);
      this.frontDoor = null;
   }

   public void updateTask() {
      this.frontDoor.incrementDoorOpeningRestrictionCounter();
   }
}