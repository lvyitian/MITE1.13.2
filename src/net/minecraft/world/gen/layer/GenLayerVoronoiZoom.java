package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.IContextExtended;
import net.minecraft.world.gen.area.AreaDimension;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

public enum GenLayerVoronoiZoom implements IAreaTransformer1 {
   INSTANCE;

   public int apply(IContextExtended<?> p_202712_1_, AreaDimension p_202712_2_, IArea p_202712_3_, int p_202712_4_, int p_202712_5_) {
      int i = p_202712_4_ + p_202712_2_.getStartX() - 2;
      int j = p_202712_5_ + p_202712_2_.getStartZ() - 2;
      int k = p_202712_2_.getStartX() >> 2;
      int l = p_202712_2_.getStartZ() >> 2;
      int i1 = (i >> 2) - k;
      int j1 = (j >> 2) - l;
      p_202712_1_.setPosition((long)(i1 + k << 2), (long)(j1 + l << 2));
      double d0 = ((double)p_202712_1_.random(1024) / 1024.0D - 0.5D) * 3.6D;
      double d1 = ((double)p_202712_1_.random(1024) / 1024.0D - 0.5D) * 3.6D;
      p_202712_1_.setPosition((long)(i1 + k + 1 << 2), (long)(j1 + l << 2));
      double d2 = ((double)p_202712_1_.random(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
      double d3 = ((double)p_202712_1_.random(1024) / 1024.0D - 0.5D) * 3.6D;
      p_202712_1_.setPosition((long)(i1 + k << 2), (long)(j1 + l + 1 << 2));
      double d4 = ((double)p_202712_1_.random(1024) / 1024.0D - 0.5D) * 3.6D;
      double d5 = ((double)p_202712_1_.random(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
      p_202712_1_.setPosition((long)(i1 + k + 1 << 2), (long)(j1 + l + 1 << 2));
      double d6 = ((double)p_202712_1_.random(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
      double d7 = ((double)p_202712_1_.random(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
      int k1 = i & 3;
      int l1 = j & 3;
      double d8 = ((double)l1 - d1) * ((double)l1 - d1) + ((double)k1 - d0) * ((double)k1 - d0);
      double d9 = ((double)l1 - d3) * ((double)l1 - d3) + ((double)k1 - d2) * ((double)k1 - d2);
      double d10 = ((double)l1 - d5) * ((double)l1 - d5) + ((double)k1 - d4) * ((double)k1 - d4);
      double d11 = ((double)l1 - d7) * ((double)l1 - d7) + ((double)k1 - d6) * ((double)k1 - d6);
      if (d8 < d9 && d8 < d10 && d8 < d11) {
         return p_202712_3_.getValue(i1 + 0, j1 + 0);
      } else if (d9 < d8 && d9 < d10 && d9 < d11) {
         return p_202712_3_.getValue(i1 + 1, j1 + 0) & 255;
      } else {
         return d10 < d8 && d10 < d9 && d10 < d11 ? p_202712_3_.getValue(i1 + 0, j1 + 1) : p_202712_3_.getValue(i1 + 1, j1 + 1) & 255;
      }
   }

   public AreaDimension apply(AreaDimension p_202706_1_) {
      int i = p_202706_1_.getStartX() >> 2;
      int j = p_202706_1_.getStartZ() >> 2;
      int k = (p_202706_1_.getXSize() >> 2) + 2;
      int l = (p_202706_1_.getZSize() >> 2) + 2;
      return new AreaDimension(i, j, k, l);
   }
}
