package net.minecraft.entity.ai;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAIFindEntityNearestPlayer extends EntityAIBase {
   private static final Logger LOGGER = LogManager.getLogger();
   private final EntityLiving entityLiving;
   private final Predicate<Entity> predicate;
   private final EntityAINearestAttackableTarget.Sorter sorter;
   private EntityLivingBase entityTarget;

   public EntityAIFindEntityNearestPlayer(EntityLiving p_i45882_1_) {
      this.entityLiving = p_i45882_1_;
      if (p_i45882_1_ instanceof EntityCreature) {
         LOGGER.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
      }

      this.predicate = (p_210293_1_) -> {
         if (!(p_210293_1_ instanceof EntityPlayer)) {
            return false;
         } else if (((EntityPlayer)p_210293_1_).capabilities.disableDamage) {
            return false;
         } else {
            double d0 = this.maxTargetRange();
            if (p_210293_1_.isSneaking()) {
               d0 *= (double)0.8F;
            }

            if (p_210293_1_.isInvisible()) {
               float f = ((EntityPlayer)p_210293_1_).getArmorVisibility();
               if (f < 0.1F) {
                  f = 0.1F;
               }

               d0 *= (double)(0.7F * f);
            }

            return !((double) p_210293_1_.getDistance(this.entityLiving) > d0) && EntityAITarget.isSuitableTarget(
                    this.entityLiving, (EntityLivingBase) p_210293_1_, false, true);
         }
      };
      this.sorter = new EntityAINearestAttackableTarget.Sorter(p_i45882_1_);
   }

   public boolean shouldExecute() {
      double d0 = this.maxTargetRange();
      List<EntityPlayer> list = this.entityLiving.world.getEntitiesWithinAABB(EntityPlayer.class, this.entityLiving.getEntityBoundingBox().grow(d0, 4.0D, d0), this.predicate);
      Collections.sort(list, this.sorter);
      if (list.isEmpty()) {
         return false;
      } else {
         this.entityTarget = list.get(0);
         return true;
      }
   }

   public boolean shouldContinueExecuting() {
      EntityLivingBase entitylivingbase = this.entityLiving.getAttackTarget();
      if (entitylivingbase == null) {
         return false;
      } else if (!entitylivingbase.isEntityAlive()) {
         return false;
      } else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage) {
         return false;
      } else {
         Team team = this.entityLiving.getTeam();
         Team team1 = entitylivingbase.getTeam();
         if (team != null && team1 == team) {
            return false;
         } else {
            double d0 = this.maxTargetRange();
            if (this.entityLiving.getDistanceSq(entitylivingbase) > d0 * d0) {
               return false;
            } else {
               return !(entitylivingbase instanceof EntityPlayerMP) || !((EntityPlayerMP)entitylivingbase).interactionManager.isCreative();
            }
         }
      }
   }

   public void startExecuting() {
      this.entityLiving.setAttackTarget(this.entityTarget);
      super.startExecuting();
   }

   public void resetTask() {
      this.entityLiving.setAttackTarget(null);
      super.startExecuting();
   }

   protected double maxTargetRange() {
      IAttributeInstance iattributeinstance = this.entityLiving.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
      return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
   }
}
