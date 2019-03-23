package net.minecraft.entity.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityBoat extends Entity {
   private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(EntityBoat.class, DataSerializers.VARINT);
   private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.createKey(EntityBoat.class, DataSerializers.VARINT);
   private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(EntityBoat.class, DataSerializers.FLOAT);
   private static final DataParameter<Integer> BOAT_TYPE = EntityDataManager.createKey(EntityBoat.class, DataSerializers.VARINT);
   private static final DataParameter<Boolean> field_199704_e = EntityDataManager.createKey(EntityBoat.class, DataSerializers.BOOLEAN);
   private static final DataParameter<Boolean> field_199705_f = EntityDataManager.createKey(EntityBoat.class, DataSerializers.BOOLEAN);
   private static final DataParameter<Integer> ROCKING_TICKS = EntityDataManager.createKey(EntityBoat.class, DataSerializers.VARINT);
   private final float[] paddlePositions = new float[2];
   private float momentum;
   private float outOfControlTicks;
   private float deltaRotation;
   private int lerpSteps;
   private double lerpX;
   private double lerpY;
   private double lerpZ;
   private double lerpYaw;
   private double lerpPitch;
   private boolean leftInputDown;
   private boolean rightInputDown;
   private boolean forwardInputDown;
   private boolean backInputDown;
   private double waterLevel;
   private float boatGlide;
   private EntityBoat.Status status;
   private EntityBoat.Status previousStatus;
   private double lastYd;
   private boolean rocking;
   private boolean field_203060_aN;
   private float rockingIntensity;
   private float rockingAngle;
   private float prevRockingAngle;

   public EntityBoat(World p_i1704_1_) {
      super(EntityType.BOAT, p_i1704_1_);
      this.preventEntitySpawning = true;
      this.setSize(1.375F, 0.5625F);
   }

   public EntityBoat(World p_i1705_1_, double p_i1705_2_, double p_i1705_4_, double p_i1705_6_) {
      this(p_i1705_1_);
      this.setPosition(p_i1705_2_, p_i1705_4_, p_i1705_6_);
      this.motionX = 0.0D;
      this.motionY = 0.0D;
      this.motionZ = 0.0D;
      this.prevPosX = p_i1705_2_;
      this.prevPosY = p_i1705_4_;
      this.prevPosZ = p_i1705_6_;
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void registerData() {
      this.dataManager.register(TIME_SINCE_HIT, 0);
      this.dataManager.register(FORWARD_DIRECTION, 1);
      this.dataManager.register(DAMAGE_TAKEN, 0.0F);
      this.dataManager.register(BOAT_TYPE, EntityBoat.Type.OAK.ordinal());
      this.dataManager.register(field_199704_e, false);
      this.dataManager.register(field_199705_f, false);
      this.dataManager.register(ROCKING_TICKS, 0);
   }

   @Nullable
   public AxisAlignedBB getCollisionBox(Entity p_70114_1_) {
      return p_70114_1_.canBePushed() ? p_70114_1_.getEntityBoundingBox() : null;
   }

   @Nullable
   public AxisAlignedBB getCollisionBoundingBox() {
      return this.getEntityBoundingBox();
   }

   public boolean canBePushed() {
      return true;
   }

   public double getMountedYOffset() {
      return -0.1D;
   }

   public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.isInvulnerableTo(p_70097_1_)) {
         return false;
      } else if (!this.world.isRemote && !this.isDead) {
         if (p_70097_1_ instanceof EntityDamageSourceIndirect && p_70097_1_.getTrueSource() != null && this.isPassenger(p_70097_1_.getTrueSource())) {
            return false;
         } else {
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + p_70097_2_ * 10.0F);
            this.markVelocityChanged();
            boolean flag = p_70097_1_.getTrueSource() instanceof EntityPlayer && ((EntityPlayer)p_70097_1_.getTrueSource()).capabilities.isCreativeMode;
            if (flag || this.getDamageTaken() > 40.0F) {
               if (!flag && this.world.getGameRules().getBoolean("doEntityDrops")) {
                  this.entityDropItem(this.getItemBoat());
               }

               this.setDead();
            }

            return true;
         }
      } else {
         return true;
      }
   }

   public void onEnterBubbleColumnWithAirAbove(boolean p_203002_1_) {
      if (!this.world.isRemote) {
         this.rocking = true;
         this.field_203060_aN = p_203002_1_;
         if (this.getRockingTicks() == 0) {
            this.setRockingTicks(60);
         }
      }

      this.world.spawnParticle(Particles.SPLASH, this.posX + (double)this.rand.nextFloat(), this.posY + 0.7D, this.posZ + (double)this.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
      if (this.rand.nextInt(20) == 0) {
         this.world.playSound(this.posX, this.posY, this.posZ, this.getSplashSound(), this.getSoundCategory(), 1.0F, 0.8F + 0.4F * this.rand.nextFloat(), false);
      }

   }

   public void applyEntityCollision(Entity p_70108_1_) {
      if (p_70108_1_ instanceof EntityBoat) {
         if (p_70108_1_.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY) {
            super.applyEntityCollision(p_70108_1_);
         }
      } else if (p_70108_1_.getEntityBoundingBox().minY <= this.getEntityBoundingBox().minY) {
         super.applyEntityCollision(p_70108_1_);
      }

   }

   public Item getItemBoat() {
      switch(this.getBoatType()) {
      case OAK:
      default:
         return Items.OAK_BOAT;
      case SPRUCE:
         return Items.SPRUCE_BOAT;
      case BIRCH:
         return Items.BIRCH_BOAT;
      case JUNGLE:
         return Items.JUNGLE_BOAT;
      case ACACIA:
         return Items.ACACIA_BOAT;
      case DARK_OAK:
         return Items.DARK_OAK_BOAT;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void performHurtAnimation() {
      this.setForwardDirection(-this.getForwardDirection());
      this.setTimeSinceHit(10);
      this.setDamageTaken(this.getDamageTaken() * 11.0F);
   }

   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   @OnlyIn(Dist.CLIENT)
   public void setPositionAndRotationDirect(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_) {
      this.lerpX = p_180426_1_;
      this.lerpY = p_180426_3_;
      this.lerpZ = p_180426_5_;
      this.lerpYaw = (double)p_180426_7_;
      this.lerpPitch = (double)p_180426_8_;
      this.lerpSteps = 10;
   }

   public EnumFacing getAdjustedHorizontalFacing() {
      return this.getHorizontalFacing().rotateY();
   }

   public void tick() {
      this.previousStatus = this.status;
      this.status = this.getBoatStatus();
      if (this.status != EntityBoat.Status.UNDER_WATER && this.status != EntityBoat.Status.UNDER_FLOWING_WATER) {
         this.outOfControlTicks = 0.0F;
      } else {
         ++this.outOfControlTicks;
      }

      if (!this.world.isRemote && this.outOfControlTicks >= 60.0F) {
         this.removePassengers();
      }

      if (this.getTimeSinceHit() > 0) {
         this.setTimeSinceHit(this.getTimeSinceHit() - 1);
      }

      if (this.getDamageTaken() > 0.0F) {
         this.setDamageTaken(this.getDamageTaken() - 1.0F);
      }

      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      super.tick();
      this.tickLerp();
      if (this.canPassengerSteer()) {
         if (this.getPassengers().isEmpty() || !(this.getPassengers().get(0) instanceof EntityPlayer)) {
            this.setPaddleState(false, false);
         }

         this.updateMotion();
         if (this.world.isRemote) {
            this.controlBoat();
            this.world.sendPacketToServer(new CPacketSteerBoat(this.getPaddleState(0), this.getPaddleState(1)));
         }

         this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      } else {
         this.motionX = 0.0D;
         this.motionY = 0.0D;
         this.motionZ = 0.0D;
      }

      this.updateRocking();

      for(int i = 0; i <= 1; ++i) {
         if (this.getPaddleState(i)) {
            if (!this.isSilent() && (double)(this.paddlePositions[i] % ((float)Math.PI * 2F)) <= (double)((float)Math.PI / 4F) && ((double)this.paddlePositions[i] + (double)((float)Math.PI / 8F)) % (double)((float)Math.PI * 2F) >= (double)((float)Math.PI / 4F)) {
               SoundEvent soundevent = this.getPaddleSound();
               if (soundevent != null) {
                  Vec3d vec3d = this.getLook(1.0F);
                  double d0 = i == 1 ? -vec3d.z : vec3d.z;
                  double d1 = i == 1 ? vec3d.x : -vec3d.x;
                  this.world.playSound((EntityPlayer)null, this.posX + d0, this.posY, this.posZ + d1, soundevent, this.getSoundCategory(), 1.0F, 0.8F + 0.4F * this.rand.nextFloat());
               }
            }

            this.paddlePositions[i] = (float)((double)this.paddlePositions[i] + (double)((float)Math.PI / 8F));
         } else {
            this.paddlePositions[i] = 0.0F;
         }
      }

      this.doBlockCollisions();
      List<Entity> list = this.world.func_175674_a(this, this.getEntityBoundingBox().grow((double)0.2F, (double)-0.01F, (double)0.2F), EntitySelectors.func_200823_a(this));
      if (!list.isEmpty()) {
         boolean flag = !this.world.isRemote && !(this.getControllingPassenger() instanceof EntityPlayer);

         for(int j = 0; j < list.size(); ++j) {
            Entity entity = list.get(j);
            if (!entity.isPassenger(this)) {
               if (flag && this.getPassengers().size() < 2 && !entity.isRiding() && entity.width < this.width && entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob) && !(entity instanceof EntityPlayer)) {
                  entity.startRiding(this);
               } else {
                  this.applyEntityCollision(entity);
               }
            }
         }
      }

   }

   private void updateRocking() {
      if (this.world.isRemote) {
         int i = this.getRockingTicks();
         if (i > 0) {
            this.rockingIntensity += 0.05F;
         } else {
            this.rockingIntensity -= 0.1F;
         }

         this.rockingIntensity = MathHelper.clamp(this.rockingIntensity, 0.0F, 1.0F);
         this.prevRockingAngle = this.rockingAngle;
         this.rockingAngle = 10.0F * (float)Math.sin((double)(0.5F * (float)this.world.getTotalWorldTime())) * this.rockingIntensity;
      } else {
         if (!this.rocking) {
            this.setRockingTicks(0);
         }

         int k = this.getRockingTicks();
         if (k > 0) {
            --k;
            this.setRockingTicks(k);
            int j = 60 - k - 1;
            if (j > 0 && k == 0) {
               this.setRockingTicks(0);
               if (this.field_203060_aN) {
                  this.motionY -= 0.7D;
                  this.removePassengers();
               } else {
                  this.motionY = this.isPassenger(EntityPlayer.class) ? 2.7D : 0.6D;
               }
            }

            this.rocking = false;
         }
      }

   }

   @Nullable
   protected SoundEvent getPaddleSound() {
      switch(this.getBoatStatus()) {
      case IN_WATER:
      case UNDER_WATER:
      case UNDER_FLOWING_WATER:
         return SoundEvents.ENTITY_BOAT_PADDLE_WATER;
      case ON_LAND:
         return SoundEvents.ENTITY_BOAT_PADDLE_LAND;
      case IN_AIR:
      default:
         return null;
      }
   }

   private void tickLerp() {
      if (this.lerpSteps > 0 && !this.canPassengerSteer()) {
         double d0 = this.posX + (this.lerpX - this.posX) / (double)this.lerpSteps;
         double d1 = this.posY + (this.lerpY - this.posY) / (double)this.lerpSteps;
         double d2 = this.posZ + (this.lerpZ - this.posZ) / (double)this.lerpSteps;
         double d3 = MathHelper.wrapDegrees(this.lerpYaw - (double)this.rotationYaw);
         this.rotationYaw = (float)((double)this.rotationYaw + d3 / (double)this.lerpSteps);
         this.rotationPitch = (float)((double)this.rotationPitch + (this.lerpPitch - (double)this.rotationPitch) / (double)this.lerpSteps);
         --this.lerpSteps;
         this.setPosition(d0, d1, d2);
         this.setRotation(this.rotationYaw, this.rotationPitch);
      }
   }

   public void setPaddleState(boolean p_184445_1_, boolean p_184445_2_) {
      this.dataManager.set(field_199704_e, p_184445_1_);
      this.dataManager.set(field_199705_f, p_184445_2_);
   }

   @OnlyIn(Dist.CLIENT)
   public float getRowingTime(int p_184448_1_, float p_184448_2_) {
      return this.getPaddleState(p_184448_1_) ? (float)MathHelper.clampedLerp((double)this.paddlePositions[p_184448_1_] - (double)((float)Math.PI / 8F), (double)this.paddlePositions[p_184448_1_], (double)p_184448_2_) : 0.0F;
   }

   private EntityBoat.Status getBoatStatus() {
      EntityBoat.Status entityboat$status = this.getUnderwaterStatus();
      if (entityboat$status != null) {
         this.waterLevel = this.getEntityBoundingBox().maxY;
         return entityboat$status;
      } else if (this.checkInWater()) {
         return EntityBoat.Status.IN_WATER;
      } else {
         float f = this.getBoatGlide();
         if (f > 0.0F) {
            this.boatGlide = f;
            return EntityBoat.Status.ON_LAND;
         } else {
            return EntityBoat.Status.IN_AIR;
         }
      }
   }

   public float getWaterLevelAbove() {
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
      int i = MathHelper.floor(axisalignedbb.minX);
      int j = MathHelper.ceil(axisalignedbb.maxX);
      int k = MathHelper.floor(axisalignedbb.maxY);
      int l = MathHelper.ceil(axisalignedbb.maxY - this.lastYd);
      int i1 = MathHelper.floor(axisalignedbb.minZ);
      int j1 = MathHelper.ceil(axisalignedbb.maxZ);

      try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain()) {
         label161:
         for(int k1 = k; k1 < l; ++k1) {
            float f = 0.0F;

            for(int l1 = i; l1 < j; ++l1) {
               for(int i2 = i1; i2 < j1; ++i2) {
                  blockpos$pooledmutableblockpos.setPos(l1, k1, i2);
                  IFluidState ifluidstate = this.world.getFluidState(blockpos$pooledmutableblockpos);
                  if (ifluidstate.isTagged(FluidTags.WATER)) {
                     f = Math.max(f, (float)k1 + ifluidstate.getHeight());
                  }

                  if (f >= 1.0F) {
                     continue label161;
                  }
               }
            }

            if (f < 1.0F) {
               float f2 = (float)blockpos$pooledmutableblockpos.getY() + f;
               return f2;
            }
         }

         float f1 = (float)(l + 1);
         return f1;
      }
   }

   public float getBoatGlide() {
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
      AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY - 0.001D, axisalignedbb.minZ, axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
      int i = MathHelper.floor(axisalignedbb1.minX) - 1;
      int j = MathHelper.ceil(axisalignedbb1.maxX) + 1;
      int k = MathHelper.floor(axisalignedbb1.minY) - 1;
      int l = MathHelper.ceil(axisalignedbb1.maxY) + 1;
      int i1 = MathHelper.floor(axisalignedbb1.minZ) - 1;
      int j1 = MathHelper.ceil(axisalignedbb1.maxZ) + 1;
      VoxelShape voxelshape = VoxelShapes.func_197881_a(axisalignedbb1);
      float f = 0.0F;
      int k1 = 0;

      try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain()) {
         for(int l1 = i; l1 < j; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
               if (j2 != 2) {
                  for(int k2 = k; k2 < l; ++k2) {
                     if (j2 <= 0 || k2 != k && k2 != l - 1) {
                        blockpos$pooledmutableblockpos.setPos(l1, k2, i2);
                        IBlockState iblockstate = this.world.getBlockState(blockpos$pooledmutableblockpos);
                        if (!(iblockstate.getBlock() instanceof BlockLilyPad) && VoxelShapes.func_197879_c(iblockstate.getCollisionShape(this.world, blockpos$pooledmutableblockpos).withOffset((double)l1, (double)k2, (double)i2), voxelshape, IBooleanFunction.AND)) {
                           f += iblockstate.getBlock().getSlipperiness();
                           ++k1;
                        }
                     }
                  }
               }
            }
         }
      }

      return f / (float)k1;
   }

   private boolean checkInWater() {
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
      int i = MathHelper.floor(axisalignedbb.minX);
      int j = MathHelper.ceil(axisalignedbb.maxX);
      int k = MathHelper.floor(axisalignedbb.minY);
      int l = MathHelper.ceil(axisalignedbb.minY + 0.001D);
      int i1 = MathHelper.floor(axisalignedbb.minZ);
      int j1 = MathHelper.ceil(axisalignedbb.maxZ);
      boolean flag = false;
      this.waterLevel = Double.MIN_VALUE;

      try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain()) {
         for(int k1 = i; k1 < j; ++k1) {
            for(int l1 = k; l1 < l; ++l1) {
               for(int i2 = i1; i2 < j1; ++i2) {
                  blockpos$pooledmutableblockpos.setPos(k1, l1, i2);
                  IFluidState ifluidstate = this.world.getFluidState(blockpos$pooledmutableblockpos);
                  if (ifluidstate.isTagged(FluidTags.WATER)) {
                     float f = (float)l1 + ifluidstate.getHeight();
                     this.waterLevel = Math.max((double)f, this.waterLevel);
                     flag |= axisalignedbb.minY < (double)f;
                  }
               }
            }
         }
      }

      return flag;
   }

   @Nullable
   private EntityBoat.Status getUnderwaterStatus() {
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
      double d0 = axisalignedbb.maxY + 0.001D;
      int i = MathHelper.floor(axisalignedbb.minX);
      int j = MathHelper.ceil(axisalignedbb.maxX);
      int k = MathHelper.floor(axisalignedbb.maxY);
      int l = MathHelper.ceil(d0);
      int i1 = MathHelper.floor(axisalignedbb.minZ);
      int j1 = MathHelper.ceil(axisalignedbb.maxZ);
      boolean flag = false;

      try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain()) {
         for(int k1 = i; k1 < j; ++k1) {
            for(int l1 = k; l1 < l; ++l1) {
               for(int i2 = i1; i2 < j1; ++i2) {
                  blockpos$pooledmutableblockpos.setPos(k1, l1, i2);
                  IFluidState ifluidstate = this.world.getFluidState(blockpos$pooledmutableblockpos);
                  if (ifluidstate.isTagged(FluidTags.WATER) && d0 < (double)((float)blockpos$pooledmutableblockpos.getY() + ifluidstate.getHeight())) {
                     if (!ifluidstate.isSource()) {
                        EntityBoat.Status entityboat$status = EntityBoat.Status.UNDER_FLOWING_WATER;
                        return entityboat$status;
                     }

                     flag = true;
                  }
               }
            }
         }
      }

      return flag ? EntityBoat.Status.UNDER_WATER : null;
   }

   private void updateMotion() {
      double d0 = (double)-0.04F;
      double d1 = this.hasNoGravity() ? 0.0D : (double)-0.04F;
      double d2 = 0.0D;
      this.momentum = 0.05F;
      if (this.previousStatus == EntityBoat.Status.IN_AIR && this.status != EntityBoat.Status.IN_AIR && this.status != EntityBoat.Status.ON_LAND) {
         this.waterLevel = this.getEntityBoundingBox().minY + (double)this.height;
         this.setPosition(this.posX, (double)(this.getWaterLevelAbove() - this.height) + 0.101D, this.posZ);
         this.motionY = 0.0D;
         this.lastYd = 0.0D;
         this.status = EntityBoat.Status.IN_WATER;
      } else {
         if (this.status == EntityBoat.Status.IN_WATER) {
            d2 = (this.waterLevel - this.getEntityBoundingBox().minY) / (double)this.height;
            this.momentum = 0.9F;
         } else if (this.status == EntityBoat.Status.UNDER_FLOWING_WATER) {
            d1 = -7.0E-4D;
            this.momentum = 0.9F;
         } else if (this.status == EntityBoat.Status.UNDER_WATER) {
            d2 = (double)0.01F;
            this.momentum = 0.45F;
         } else if (this.status == EntityBoat.Status.IN_AIR) {
            this.momentum = 0.9F;
         } else if (this.status == EntityBoat.Status.ON_LAND) {
            this.momentum = this.boatGlide;
            if (this.getControllingPassenger() instanceof EntityPlayer) {
               this.boatGlide /= 2.0F;
            }
         }

         this.motionX *= (double)this.momentum;
         this.motionZ *= (double)this.momentum;
         this.deltaRotation *= this.momentum;
         this.motionY += d1;
         if (d2 > 0.0D) {
            double d3 = 0.65D;
            this.motionY += d2 * 0.06153846016296973D;
            double d4 = 0.75D;
            this.motionY *= 0.75D;
         }
      }

   }

   private void controlBoat() {
      if (this.isBeingRidden()) {
         float f = 0.0F;
         if (this.leftInputDown) {
            this.deltaRotation += -1.0F;
         }

         if (this.rightInputDown) {
            ++this.deltaRotation;
         }

         if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown) {
            f += 0.005F;
         }

         this.rotationYaw += this.deltaRotation;
         if (this.forwardInputDown) {
            f += 0.04F;
         }

         if (this.backInputDown) {
            f -= 0.005F;
         }

         this.motionX += (double)(MathHelper.sin(-this.rotationYaw * ((float)Math.PI / 180F)) * f);
         this.motionZ += (double)(MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180F)) * f);
         this.setPaddleState(this.rightInputDown && !this.leftInputDown || this.forwardInputDown, this.leftInputDown && !this.rightInputDown || this.forwardInputDown);
      }
   }

   public void updatePassenger(Entity p_184232_1_) {
      if (this.isPassenger(p_184232_1_)) {
         float f = 0.0F;
         float f1 = (float)((this.isDead ? (double)0.01F : this.getMountedYOffset()) + p_184232_1_.getYOffset());
         if (this.getPassengers().size() > 1) {
            int i = this.getPassengers().indexOf(p_184232_1_);
            if (i == 0) {
               f = 0.2F;
            } else {
               f = -0.6F;
            }

            if (p_184232_1_ instanceof EntityAnimal) {
               f = (float)((double)f + 0.2D);
            }
         }

         Vec3d vec3d = (new Vec3d((double)f, 0.0D, 0.0D)).rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
         p_184232_1_.setPosition(this.posX + vec3d.x, this.posY + (double)f1, this.posZ + vec3d.z);
         p_184232_1_.rotationYaw += this.deltaRotation;
         p_184232_1_.setRotationYawHead(p_184232_1_.getRotationYawHead() + this.deltaRotation);
         this.applyYawToEntity(p_184232_1_);
         if (p_184232_1_ instanceof EntityAnimal && this.getPassengers().size() > 1) {
            int j = p_184232_1_.getEntityId() % 2 == 0 ? 90 : 270;
            p_184232_1_.setRenderYawOffset(((EntityAnimal)p_184232_1_).renderYawOffset + (float)j);
            p_184232_1_.setRotationYawHead(p_184232_1_.getRotationYawHead() + (float)j);
         }

      }
   }

   protected void applyYawToEntity(Entity p_184454_1_) {
      p_184454_1_.setRenderYawOffset(this.rotationYaw);
      float f = MathHelper.wrapDegrees(p_184454_1_.rotationYaw - this.rotationYaw);
      float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
      p_184454_1_.prevRotationYaw += f1 - f;
      p_184454_1_.rotationYaw += f1 - f;
      p_184454_1_.setRotationYawHead(p_184454_1_.rotationYaw);
   }

   @OnlyIn(Dist.CLIENT)
   public void applyOrientationToEntity(Entity p_184190_1_) {
      this.applyYawToEntity(p_184190_1_);
   }

   protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
      p_70014_1_.setString("Type", this.getBoatType().getName());
   }

   protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
      if (p_70037_1_.hasKey("Type", 8)) {
         this.setBoatType(EntityBoat.Type.getTypeFromString(p_70037_1_.getString("Type")));
      }

   }

   public boolean processInitialInteract(EntityPlayer p_184230_1_, EnumHand p_184230_2_) {
      if (p_184230_1_.isSneaking()) {
         return false;
      } else {
         if (!this.world.isRemote && this.outOfControlTicks < 60.0F) {
            p_184230_1_.startRiding(this);
         }

         return true;
      }
   }

   protected void updateFallState(double p_184231_1_, boolean p_184231_3_, IBlockState p_184231_4_, BlockPos p_184231_5_) {
      this.lastYd = this.motionY;
      if (!this.isRiding()) {
         if (p_184231_3_) {
            if (this.fallDistance > 3.0F) {
               if (this.status != EntityBoat.Status.ON_LAND) {
                  this.fallDistance = 0.0F;
                  return;
               }

               this.fall(this.fallDistance, 1.0F);
               if (!this.world.isRemote && !this.isDead) {
                  this.setDead();
                  if (this.world.getGameRules().getBoolean("doEntityDrops")) {
                     for(int i = 0; i < 3; ++i) {
                        this.entityDropItem(this.getBoatType().asPlank());
                     }

                     for(int j = 0; j < 2; ++j) {
                        this.entityDropItem(Items.STICK);
                     }
                  }
               }
            }

            this.fallDistance = 0.0F;
         } else if (!this.world.getFluidState((new BlockPos(this)).down()).isTagged(FluidTags.WATER) && p_184231_1_ < 0.0D) {
            this.fallDistance = (float)((double)this.fallDistance - p_184231_1_);
         }

      }
   }

   public boolean getPaddleState(int p_184457_1_) {
      return this.dataManager.<Boolean>get(p_184457_1_ == 0 ? field_199704_e : field_199705_f) && this.getControllingPassenger() != null;
   }

   public void setDamageTaken(float p_70266_1_) {
      this.dataManager.set(DAMAGE_TAKEN, p_70266_1_);
   }

   public float getDamageTaken() {
      return this.dataManager.get(DAMAGE_TAKEN);
   }

   public void setTimeSinceHit(int p_70265_1_) {
      this.dataManager.set(TIME_SINCE_HIT, p_70265_1_);
   }

   public int getTimeSinceHit() {
      return this.dataManager.get(TIME_SINCE_HIT);
   }

   private void setRockingTicks(int p_203055_1_) {
      this.dataManager.set(ROCKING_TICKS, p_203055_1_);
   }

   private int getRockingTicks() {
      return this.dataManager.get(ROCKING_TICKS);
   }

   @OnlyIn(Dist.CLIENT)
   public float func_203056_b(float p_203056_1_) {
      return this.prevRockingAngle + (this.rockingAngle - this.prevRockingAngle) * p_203056_1_;
   }

   public void setForwardDirection(int p_70269_1_) {
      this.dataManager.set(FORWARD_DIRECTION, p_70269_1_);
   }

   public int getForwardDirection() {
      return this.dataManager.get(FORWARD_DIRECTION);
   }

   public void setBoatType(EntityBoat.Type p_184458_1_) {
      this.dataManager.set(BOAT_TYPE, p_184458_1_.ordinal());
   }

   public EntityBoat.Type getBoatType() {
      return EntityBoat.Type.byId(this.dataManager.get(BOAT_TYPE));
   }

   protected boolean canFitPassenger(Entity p_184219_1_) {
      return this.getPassengers().size() < 2 && !this.areEyesInFluid(FluidTags.WATER);
   }

   @Nullable
   public Entity getControllingPassenger() {
      List<Entity> list = this.getPassengers();
      return list.isEmpty() ? null : list.get(0);
   }

   @OnlyIn(Dist.CLIENT)
   public void updateInputs(boolean p_184442_1_, boolean p_184442_2_, boolean p_184442_3_, boolean p_184442_4_) {
      this.leftInputDown = p_184442_1_;
      this.rightInputDown = p_184442_2_;
      this.forwardInputDown = p_184442_3_;
      this.backInputDown = p_184442_4_;
   }

   public static enum Status {
      IN_WATER,
      UNDER_WATER,
      UNDER_FLOWING_WATER,
      ON_LAND,
      IN_AIR;
   }

   public static enum Type {
      OAK(Blocks.OAK_PLANKS, "oak"),
      SPRUCE(Blocks.SPRUCE_PLANKS, "spruce"),
      BIRCH(Blocks.BIRCH_PLANKS, "birch"),
      JUNGLE(Blocks.JUNGLE_PLANKS, "jungle"),
      ACACIA(Blocks.ACACIA_PLANKS, "acacia"),
      DARK_OAK(Blocks.DARK_OAK_PLANKS, "dark_oak");

      private final String name;
      private final Block block;

      private Type(Block p_i48146_3_, String p_i48146_4_) {
         this.name = p_i48146_4_;
         this.block = p_i48146_3_;
      }

      public String getName() {
         return this.name;
      }

      public Block asPlank() {
         return this.block;
      }

      public String toString() {
         return this.name;
      }

      public static EntityBoat.Type byId(int p_184979_0_) {
         EntityBoat.Type[] aentityboat$type = values();
         if (p_184979_0_ < 0 || p_184979_0_ >= aentityboat$type.length) {
            p_184979_0_ = 0;
         }

         return aentityboat$type[p_184979_0_];
      }

      public static EntityBoat.Type getTypeFromString(String p_184981_0_) {
         EntityBoat.Type[] aentityboat$type = values();

         for(int i = 0; i < aentityboat$type.length; ++i) {
            if (aentityboat$type[i].getName().equals(p_184981_0_)) {
               return aentityboat$type[i];
            }
         }

         return aentityboat$type[0];
      }
   }
}
