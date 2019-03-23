package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelSquid extends ModelBase {
   private final ModelRenderer squidBody;
   private final ModelRenderer[] squidTentacles = new ModelRenderer[8];

   public ModelSquid() {
      int i = -16;
      this.squidBody = new ModelRenderer(this, 0, 0);
      this.squidBody.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
      this.squidBody.rotationPointY += 8.0F;

      for(int j = 0; j < this.squidTentacles.length; ++j) {
         this.squidTentacles[j] = new ModelRenderer(this, 48, 0);
         double d0 = (double)j * Math.PI * 2.0D / (double)this.squidTentacles.length;
         float f = (float)Math.cos(d0) * 5.0F;
         float f1 = (float)Math.sin(d0) * 5.0F;
         this.squidTentacles[j].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
         this.squidTentacles[j].rotationPointX = f;
         this.squidTentacles[j].rotationPointZ = f1;
         this.squidTentacles[j].rotationPointY = 15.0F;
         d0 = (double)j * Math.PI * -2.0D / (double)this.squidTentacles.length + (Math.PI / 2D);
         this.squidTentacles[j].rotateAngleY = (float)d0;
      }

   }

   public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      for(ModelRenderer modelrenderer : this.squidTentacles) {
         modelrenderer.rotateAngleX = p_78087_3_;
      }

   }

   public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.squidBody.render(p_78088_7_);

      for(ModelRenderer modelrenderer : this.squidTentacles) {
         modelrenderer.render(p_78088_7_);
      }

   }
}