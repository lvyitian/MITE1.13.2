package net.minecraft.entity.ai;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAIFindEntityNearest extends EntityAIBase {
   private static final Logger LOGGER = LogManager.getLogger();
   private final EntityLiving mob;
   private final Predicate<EntityLivingBase> predicate;
   private final EntityAINearestAttackableTarget.Sorter sorter;
   private EntityLivingBase target;
   private final Class<? extends EntityLivingBase> classToCheck;

   public EntityAIFindEntityNearest(EntityLiving p_i45884_1_, Class<? extends EntityLivingBase> p_i45884_2_) {
      this.mob = p_i45884_1_;
      this.classToCheck = p_i45884_2_;
      if (p_i45884_1_ instanceof EntityCreature) {
         LOGGER.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
      }

      this.predicate = (p_210292_1_) -> {
         double d0 = this.getFollowRange();
         if (p_210292_1_.isSneaking()) {
            d0 *= (double)0.8F;
         }

         if (p_210292_1_.isInvisible()) {
            return false;
         } else {
            return !((double) p_210292_1_.getDistance(this.mob) > d0) && EntityAITarget.isSuitableTarget(this.mob,
                    p_210292_1_, false, true);
         }
      };
      this.sorter = new EntityAINearestAttackableTarget.Sorter(p_i45884_1_);
   }

   public boolean shouldExecute() {
      double d0 = this.getFollowRange();
      List<EntityLivingBase> list = this.mob.world.getEntitiesWithinAABB(this.classToCheck, this.mob.getEntityBoundingBox().grow(d0, 4.0D, d0), this.predicate);
      Collections.sort(list, this.sorter);
      if (list.isEmpty()) {
         return false;
      } else {
         this.target = list.get(0);
         return true;
      }
   }

   public boolean shouldContinueExecuting() {
      EntityLivingBase entitylivingbase = this.mob.getAttackTarget();
      if (entitylivingbase == null) {
         return false;
      } else if (!entitylivingbase.isEntityAlive()) {
         return false;
      } else {
         double d0 = this.getFollowRange();
         if (this.mob.getDistanceSq(entitylivingbase) > d0 * d0) {
            return false;
         } else {
            return !(entitylivingbase instanceof EntityPlayerMP) || !((EntityPlayerMP)entitylivingbase).interactionManager.isCreative();
         }
      }
   }

   public void startExecuting() {
      this.mob.setAttackTarget(this.target);
      super.startExecuting();
   }

   public void resetTask() {
      this.mob.setAttackTarget(null);
      super.startExecuting();
   }

   protected double getFollowRange() {
      IAttributeInstance iattributeinstance = this.mob.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
      return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
   }
}
