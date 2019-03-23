package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;

public class EntityAIFollowGolem extends EntityAIBase {
   private final EntityVillager villager;
   private EntityIronGolem ironGolem;
   private int takeGolemRoseTick;
   private boolean tookGolemRose;

   public EntityAIFollowGolem(EntityVillager p_i1656_1_) {
      this.villager = p_i1656_1_;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      if (this.villager.getGrowingAge() >= 0) {
         return false;
      } else if (!this.villager.world.isDaytime()) {
         return false;
      } else {
         List<EntityIronGolem> list = this.villager.world.getEntitiesWithinAABB(EntityIronGolem.class, this.villager.getEntityBoundingBox().grow(6.0D, 2.0D, 6.0D));
         if (list.isEmpty()) {
            return false;
         } else {
            for(EntityIronGolem entityirongolem : list) {
               if (entityirongolem.getHoldRoseTick() > 0) {
                  this.ironGolem = entityirongolem;
                  break;
               }
            }

            return this.ironGolem != null;
         }
      }
   }

   public boolean shouldContinueExecuting() {
      return this.ironGolem.getHoldRoseTick() > 0;
   }

   public void startExecuting() {
      this.takeGolemRoseTick = this.villager.getRNG().nextInt(320);
      this.tookGolemRose = false;
      this.ironGolem.getNavigator().clearPath();
   }

   public void resetTask() {
      this.ironGolem = null;
      this.villager.getNavigator().clearPath();
   }

   public void updateTask() {
      this.villager.getLookHelper().setLookPositionWithEntity(this.ironGolem, 30.0F, 30.0F);
      if (this.ironGolem.getHoldRoseTick() == this.takeGolemRoseTick) {
         this.villager.getNavigator().tryMoveToEntityLiving(this.ironGolem, 0.5D);
         this.tookGolemRose = true;
      }

      if (this.tookGolemRose && this.villager.getDistanceSq(this.ironGolem) < 4.0D) {
         this.ironGolem.setHoldingRose(false);
         this.villager.getNavigator().clearPath();
      }

   }
}