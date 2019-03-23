package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class EntityAIWatchClosestWithoutMoving extends EntityAIWatchClosest {
   public EntityAIWatchClosestWithoutMoving(EntityLiving p_i1629_1_, Class<? extends Entity> p_i1629_2_, float p_i1629_3_, float p_i1629_4_) {
      super(p_i1629_1_, p_i1629_2_, p_i1629_3_, p_i1629_4_);
      this.setMutexBits(3);
   }
}
