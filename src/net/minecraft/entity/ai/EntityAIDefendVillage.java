package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.village.Village;

public class EntityAIDefendVillage extends EntityAITarget {
   private final EntityIronGolem irongolem;
   private EntityLivingBase villageAgressorTarget;

   public EntityAIDefendVillage(EntityIronGolem p_i1659_1_) {
      super(p_i1659_1_, false, true);
      this.irongolem = p_i1659_1_;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      Village village = this.irongolem.getVillage();
      if (village == null) {
         return false;
      } else {
         this.villageAgressorTarget = village.findNearestVillageAggressor(this.irongolem);
         if (this.villageAgressorTarget instanceof EntityCreeper) {
            return false;
         } else if (this.isSuitableTarget(this.villageAgressorTarget, false)) {
            return true;
         } else if (this.taskOwner.getRNG().nextInt(20) == 0) {
            this.villageAgressorTarget = village.getNearestTargetPlayer(this.irongolem);
            return this.isSuitableTarget(this.villageAgressorTarget, false);
         } else {
            return false;
         }
      }
   }

   public void startExecuting() {
      this.irongolem.setAttackTarget(this.villageAgressorTarget);
      super.startExecuting();
   }
}
