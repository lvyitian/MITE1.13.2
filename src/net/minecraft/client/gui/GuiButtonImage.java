package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class GuiButtonImage extends GuiButton {
   private final ResourceLocation resourceLocation;
   private final int xTexStart;
   private final int yTexStart;
   private final int yDiffText;

   public GuiButtonImage(int buttonID, int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffText, ResourceLocation resourceLocation) {
      super(buttonID, x, y, width, height, "");
      this.xTexStart = xTexStart;
      this.yTexStart = yTexStart;
      this.yDiffText = yDiffText;
      this.resourceLocation = resourceLocation;
   }

   public void setPosition(int p_191746_1_, int p_191746_2_) {
      this.x = p_191746_1_;
      this.y = p_191746_2_;
   }

   public void render(int p_194828_1_, int p_194828_2_, float p_194828_3_) {
      if (this.visible) {
         this.hovered = p_194828_1_ >= this.x && p_194828_2_ >= this.y && p_194828_1_ < this.x + this.width && p_194828_2_ < this.y + this.height;
         Minecraft minecraft = Minecraft.getInstance();
         minecraft.getTextureManager().bindTexture(this.resourceLocation);
         GlStateManager.disableDepthTest();
         int i = this.yTexStart;
         if (this.hovered) {
            i += this.yDiffText;
         }

         this.drawTexturedModalRect(this.x, this.y, this.xTexStart, i, this.width, this.height);
         GlStateManager.enableDepthTest();
      }
   }
}
