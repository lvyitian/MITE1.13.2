package net.minecraft.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum EnumHandSide {
   LEFT(new TextComponentTranslation("options.mainHand.left")),
   RIGHT(new TextComponentTranslation("options.mainHand.right"));

   private final ITextComponent handName;

   private EnumHandSide(ITextComponent p_i46806_3_) {
      this.handName = p_i46806_3_;
   }

   @OnlyIn(Dist.CLIENT)
   public EnumHandSide opposite() {
      return this == LEFT ? RIGHT : LEFT;
   }

   public String toString() {
      return this.handName.getString();
   }
}
