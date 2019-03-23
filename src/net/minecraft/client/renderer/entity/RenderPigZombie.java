package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.model.ModelZombie;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderPigZombie extends RenderBiped<EntityPigZombie> {
   private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("textures/entity/zombie_pigman.png");

   public RenderPigZombie(RenderManager p_i46148_1_) {
      super(p_i46148_1_, new ModelZombie(), 0.5F);
      this.addLayer(new LayerBipedArmor(this) {
         protected void initArmor() {
            this.modelLeggings = new ModelZombie(0.5F, true);
            this.modelArmor = new ModelZombie(1.0F, true);
         }
      });
   }

   protected ResourceLocation getEntityTexture(EntityPigZombie p_110775_1_) {
      return ZOMBIE_PIGMAN_TEXTURE;
   }
}