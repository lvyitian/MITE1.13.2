package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.passive.EntityAnimal;

public class EntityAIFollowParent extends EntityAIBase {
   private final EntityAnimal childAnimal;
   private EntityAnimal parentAnimal;
   private final double moveSpeed;
   private int delayCounter;

   public EntityAIFollowParent(EntityAnimal p_i1626_1_, double p_i1626_2_) {
      this.childAnimal = p_i1626_1_;
      this.moveSpeed = p_i1626_2_;
   }

   public boolean shouldExecute() {
      if (this.childAnimal.getGrowingAge() >= 0) {
         return false;
      } else {
         List<EntityAnimal> list = this.childAnimal.world.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.getEntityBoundingBox().grow(8.0D, 4.0D, 8.0D));
         EntityAnimal entityanimal = null;
         double d0 = Double.MAX_VALUE;

         for(EntityAnimal entityanimal1 : list) {
            if (entityanimal1.getGrowingAge() >= 0) {
               double d1 = this.childAnimal.getDistanceSq(entityanimal1);
               if (!(d1 > d0)) {
                  d0 = d1;
                  entityanimal = entityanimal1;
               }
            }
         }

         if (entityanimal == null) {
            return false;
         } else if (d0 < 9.0D) {
            return false;
         } else {
            this.parentAnimal = entityanimal;
            return true;
         }
      }
   }

   public boolean shouldContinueExecuting() {
      if (this.childAnimal.getGrowingAge() >= 0) {
         return false;
      } else if (!this.parentAnimal.isEntityAlive()) {
         return false;
      } else {
         double d0 = this.childAnimal.getDistanceSq(this.parentAnimal);
         return !(d0 < 9.0D) && !(d0 > 256.0D);
      }
   }

   public void startExecuting() {
      this.delayCounter = 0;
   }

   public void resetTask() {
      this.parentAnimal = null;
   }

   public void updateTask() {
      if (--this.delayCounter <= 0) {
         this.delayCounter = 10;
         this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
      }
   }
}
