package net.minecraft.entity.monster;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Particles;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityWitch extends EntityMob implements IRangedAttackMob {
   private static final UUID MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
   private static final AttributeModifier MODIFIER = (new AttributeModifier(MODIFIER_UUID, "Drinking speed penalty", -0.25D, 0)).setSaved(false);
   private static final DataParameter<Boolean> IS_DRINKING = EntityDataManager.createKey(EntityWitch.class, DataSerializers.BOOLEAN);
   private int potionUseTimer;

   public EntityWitch(World p_i1744_1_) {
      super(EntityType.WITCH, p_i1744_1_);
      this.setSize(0.6F, 1.95F);
   }

   protected void initEntityAI() {
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, new EntityAIAttackRanged(this, 1.0D, 60, 10.0F));
      this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1.0D));
      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(3, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
   }

   protected void registerData() {
      super.registerData();
      this.getDataManager().register(IS_DRINKING, false);
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_WITCH_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
      return SoundEvents.ENTITY_WITCH_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_WITCH_DEATH;
   }

   public void setDrinkingPotion(boolean p_82197_1_) {
      this.getDataManager().set(IS_DRINKING, p_82197_1_);
   }

   public boolean isDrinkingPotion() {
      return this.getDataManager().get(IS_DRINKING);
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(26.0D);
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
   }

   public void livingTick() {
      if (!this.world.isRemote) {
         if (this.isDrinkingPotion()) {
            if (this.potionUseTimer-- <= 0) {
               this.setDrinkingPotion(false);
               ItemStack itemstack = this.getHeldItemMainhand();
               this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
               if (itemstack.getItem() == Items.POTION) {
                  List<PotionEffect> list = PotionUtils.getEffectsFromStack(itemstack);
                  if (list != null) {
                     for(PotionEffect potioneffect : list) {
                        this.addPotionEffect(new PotionEffect(potioneffect));
                     }
                  }
               }

               this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(MODIFIER);
            }
         } else {
            PotionType potiontype = null;
            if (this.rand.nextFloat() < 0.15F && this.areEyesInFluid(FluidTags.WATER) && !this.isPotionActive(MobEffects.WATER_BREATHING)) {
               potiontype = PotionTypes.WATER_BREATHING;
            } else if (this.rand.nextFloat() < 0.15F && (this.isBurning() || this.getLastDamageSource() != null && this.getLastDamageSource().isFireDamage()) && !this.isPotionActive(MobEffects.FIRE_RESISTANCE)) {
               potiontype = PotionTypes.FIRE_RESISTANCE;
            } else if (this.rand.nextFloat() < 0.05F && this.getHealth() < this.getMaxHealth()) {
               potiontype = PotionTypes.HEALING;
            } else if (this.rand.nextFloat() < 0.5F && this.getAttackTarget() != null && !this.isPotionActive(MobEffects.SPEED) && this.getAttackTarget().getDistanceSq(this) > 121.0D) {
               potiontype = PotionTypes.SWIFTNESS;
            }

            if (potiontype != null) {
               this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), potiontype));
               this.potionUseTimer = this.getHeldItemMainhand().getUseDuration();
               this.setDrinkingPotion(true);
               this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_DRINK, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
               IAttributeInstance iattributeinstance = this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
               iattributeinstance.removeModifier(MODIFIER);
               iattributeinstance.applyModifier(MODIFIER);
            }
         }

         if (this.rand.nextFloat() < 7.5E-4F) {
            this.world.setEntityState(this, (byte)15);
         }
      }

      super.livingTick();
   }

   @OnlyIn(Dist.CLIENT)
   public void handleStatusUpdate(byte p_70103_1_) {
      if (p_70103_1_ == 15) {
         for(int i = 0; i < this.rand.nextInt(35) + 10; ++i) {
            this.world.spawnParticle(Particles.WITCH, this.posX + this.rand.nextGaussian() * (double)0.13F, this.getEntityBoundingBox().maxY + 0.5D + this.rand.nextGaussian() * (double)0.13F, this.posZ + this.rand.nextGaussian() * (double)0.13F, 0.0D, 0.0D, 0.0D);
         }
      } else {
         super.handleStatusUpdate(p_70103_1_);
      }

   }

   protected float applyPotionDamageCalculations(DamageSource p_70672_1_, float p_70672_2_) {
      p_70672_2_ = super.applyPotionDamageCalculations(p_70672_1_, p_70672_2_);
      if (p_70672_1_.getTrueSource() == this) {
         p_70672_2_ = 0.0F;
      }

      if (p_70672_1_.isMagicDamage()) {
         p_70672_2_ = (float)((double)p_70672_2_ * 0.15D);
      }

      return p_70672_2_;
   }

   @Nullable
   protected ResourceLocation getLootTable() {
      return LootTableList.ENTITIES_WITCH;
   }

   public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_) {
      if (!this.isDrinkingPotion()) {
         double d0 = p_82196_1_.posY + (double)p_82196_1_.getEyeHeight() - (double)1.1F;
         double d1 = p_82196_1_.posX + p_82196_1_.motionX - this.posX;
         double d2 = d0 - this.posY;
         double d3 = p_82196_1_.posZ + p_82196_1_.motionZ - this.posZ;
         float f = MathHelper.sqrt(d1 * d1 + d3 * d3);
         PotionType potiontype = PotionTypes.HARMING;
         if (f >= 8.0F && !p_82196_1_.isPotionActive(MobEffects.SLOWNESS)) {
            potiontype = PotionTypes.SLOWNESS;
         } else if (p_82196_1_.getHealth() >= 8.0F && !p_82196_1_.isPotionActive(MobEffects.POISON)) {
            potiontype = PotionTypes.POISON;
         } else if (f <= 3.0F && !p_82196_1_.isPotionActive(MobEffects.WEAKNESS) && this.rand.nextFloat() < 0.25F) {
            potiontype = PotionTypes.WEAKNESS;
         }

         EntityPotion entitypotion = new EntityPotion(this.world, this, PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), potiontype));
         entitypotion.rotationPitch -= -20.0F;
         entitypotion.shoot(d1, d2 + (double)(f * 0.2F), d3, 0.75F, 8.0F);
         this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
         this.world.spawnEntity(entitypotion);
      }
   }

   public float getEyeHeight() {
      return 1.62F;
   }

   public void setSwingingArms(boolean p_184724_1_) {
   }
}
