package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BannerTextures {
   public static final BannerTextures.Cache BANNER_DESIGNS = new BannerTextures.Cache("banner_", new ResourceLocation("textures/entity/banner_base.png"), "textures/entity/banner/");
   public static final BannerTextures.Cache SHIELD_DESIGNS = new BannerTextures.Cache("shield_", new ResourceLocation("textures/entity/shield_base.png"), "textures/entity/shield/");
   public static final ResourceLocation SHIELD_BASE_TEXTURE = new ResourceLocation("textures/entity/shield_base_nopattern.png");
   public static final ResourceLocation BANNER_BASE_TEXTURE = new ResourceLocation("textures/entity/banner/base.png");

   @OnlyIn(Dist.CLIENT)
   public static class Cache {
      private final Map<String, BannerTextures.CacheEntry> cacheMap = Maps.newLinkedHashMap();
      private final ResourceLocation cacheResourceLocation;
      private final String cacheResourceBase;
      private final String cacheId;

      public Cache(String p_i46998_1_, ResourceLocation p_i46998_2_, String p_i46998_3_) {
         this.cacheId = p_i46998_1_;
         this.cacheResourceLocation = p_i46998_2_;
         this.cacheResourceBase = p_i46998_3_;
      }

      @Nullable
      public ResourceLocation getResourceLocation(String p_187478_1_, List<BannerPattern> p_187478_2_, List<EnumDyeColor> p_187478_3_) {
         if (p_187478_1_.isEmpty()) {
            return null;
         } else if (!p_187478_2_.isEmpty() && !p_187478_3_.isEmpty()) {
            p_187478_1_ = this.cacheId + p_187478_1_;
            BannerTextures.CacheEntry bannertextures$cacheentry = this.cacheMap.get(p_187478_1_);
            if (bannertextures$cacheentry == null) {
               if (this.cacheMap.size() >= 256 && !this.freeCacheSlot()) {
                  return BannerTextures.BANNER_BASE_TEXTURE;
               }

               List<String> list = Lists.newArrayList();

               for(BannerPattern bannerpattern : p_187478_2_) {
                  list.add(this.cacheResourceBase + bannerpattern.getFileName() + ".png");
               }

               bannertextures$cacheentry = new BannerTextures.CacheEntry();
               bannertextures$cacheentry.textureLocation = new ResourceLocation(p_187478_1_);
               Minecraft.getInstance().getTextureManager().loadTexture(bannertextures$cacheentry.textureLocation, new LayeredColorMaskTexture(this.cacheResourceLocation, list, p_187478_3_));
               this.cacheMap.put(p_187478_1_, bannertextures$cacheentry);
            }

            bannertextures$cacheentry.lastUseMillis = Util.milliTime();
            return bannertextures$cacheentry.textureLocation;
         } else {
            return MissingTextureSprite.getLocation();
         }
      }

      private boolean freeCacheSlot() {
         long i = Util.milliTime();
         Iterator<String> iterator = this.cacheMap.keySet().iterator();

         while(iterator.hasNext()) {
            String s = iterator.next();
            BannerTextures.CacheEntry bannertextures$cacheentry = this.cacheMap.get(s);
            if (i - bannertextures$cacheentry.lastUseMillis > 5000L) {
               Minecraft.getInstance().getTextureManager().deleteTexture(bannertextures$cacheentry.textureLocation);
               iterator.remove();
               return true;
            }
         }

         return this.cacheMap.size() < 256;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class CacheEntry {
      public long lastUseMillis;
      public ResourceLocation textureLocation;

      private CacheEntry() {
      }
   }
}
