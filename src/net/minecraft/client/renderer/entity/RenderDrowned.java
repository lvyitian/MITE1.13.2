package net.minecraft.client.renderer.entity;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerDrownedOuter;
import net.minecraft.client.renderer.entity.model.ModelDrowned;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderDrowned extends RenderZombie {
   private static final ResourceLocation DROWNED_LOCATION = new ResourceLocation("textures/entity/zombie/drowned.png");
   private float field_208407_j;

   public RenderDrowned(RenderManager p_i48906_1_) {
      super(p_i48906_1_, new ModelDrowned(0.0F, 0.0F, 64, 64));
      this.addLayer(new LayerDrownedOuter(this));
   }

   protected LayerBipedArmor createArmorLayer() {
      return new LayerBipedArmor(this) {
         protected void initArmor() {
            this.modelLeggings = new ModelDrowned(0.5F, true);
            this.modelArmor = new ModelDrowned(1.0F, true);
         }
      };
   }

   @Nullable
   protected ResourceLocation getEntityTexture(EntityZombie p_110775_1_) {
      return DROWNED_LOCATION;
   }

   protected void applyRotations(EntityZombie p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
      float f = p_77043_1_.getSwimAnimation(p_77043_4_);
      super.applyRotations(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
      if (f > 0.0F) {
         float f1 = this.func_208406_b(p_77043_1_.rotationPitch, -10.0F - p_77043_1_.rotationPitch, f);
         if (!p_77043_1_.isSwimming()) {
            f1 = this.interpolateRotation(this.field_208407_j, 0.0F, 1.0F - f);
         }

         GlStateManager.rotatef(f1, 1.0F, 0.0F, 0.0F);
         if (p_77043_1_.isSwimming()) {
            this.field_208407_j = f1;
         }
      }

   }

   private float func_208406_b(float p_208406_1_, float p_208406_2_, float p_208406_3_) {
      return p_208406_1_ + (p_208406_2_ - p_208406_1_) * p_208406_3_;
   }
}
