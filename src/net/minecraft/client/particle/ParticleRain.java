package net.minecraft.client.particle;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParticleRain extends Particle {
   protected ParticleRain(World p_i1235_1_, double p_i1235_2_, double p_i1235_4_, double p_i1235_6_) {
      super(p_i1235_1_, p_i1235_2_, p_i1235_4_, p_i1235_6_, 0.0D, 0.0D, 0.0D);
      this.motionX *= (double)0.3F;
      this.motionY = Math.random() * (double)0.2F + (double)0.1F;
      this.motionZ *= (double)0.3F;
      this.particleRed = 1.0F;
      this.particleGreen = 1.0F;
      this.particleBlue = 1.0F;
      this.setParticleTextureIndex(19 + this.rand.nextInt(4));
      this.setSize(0.01F, 0.01F);
      this.particleGravity = 0.06F;
      this.maxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
   }

   public void tick() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.motionY -= (double)this.particleGravity;
      this.move(this.motionX, this.motionY, this.motionZ);
      this.motionX *= (double)0.98F;
      this.motionY *= (double)0.98F;
      this.motionZ *= (double)0.98F;
      if (this.maxAge-- <= 0) {
         this.setExpired();
      }

      if (this.onGround) {
         if (Math.random() < 0.5D) {
            this.setExpired();
         }

         this.motionX *= (double)0.7F;
         this.motionZ *= (double)0.7F;
      }

      BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
      IBlockState iblockstate = this.world.getBlockState(blockpos);
      Material material = iblockstate.getMaterial();
      IFluidState ifluidstate = this.world.getFluidState(blockpos);
      if (!ifluidstate.isEmpty() || material.isSolid()) {
         double d0;
         if (ifluidstate.getHeight() > 0.0F) {
            d0 = (double)ifluidstate.getHeight();
         } else {
            d0 = iblockstate.getCollisionShape(this.world, blockpos).func_197760_b(EnumFacing.Axis.Y, this.posX - Math.floor(this.posX), this.posZ - Math.floor(this.posZ));
         }

         double d1 = (double)MathHelper.floor(this.posY) + d0;
         if (this.posY < d1) {
            this.setExpired();
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle makeParticle(BasicParticleType p_199234_1_, World p_199234_2_, double p_199234_3_, double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
         return new ParticleRain(p_199234_2_, p_199234_3_, p_199234_5_, p_199234_7_);
      }
   }
}
