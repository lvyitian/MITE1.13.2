package net.minecraft.item.crafting;

import javax.annotation.Nullable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BannerAddPatternRecipe extends IRecipeHidden implements ITimedRecipe{
   public BannerAddPatternRecipe(ResourceLocation p_i48172_1_) {
      super(p_i48172_1_);
   }

   @Override
   public int getCraftingTime(IInventory inventory) {
      ItemStack result = this.getCraftingResult(inventory);
      NBTTagCompound nbttagcompound1 = result.getOrCreateChildTag("BlockEntityTag");
      if (nbttagcompound1.hasKey("Patterns",9)){
         return nbttagcompound1.getTagList("Patterns",10).size()*1000;
      }else return 1500;
   }

   public boolean matches(IInventory p_77569_1_, World p_77569_2_) {
      if (!(p_77569_1_ instanceof InventoryCrafting)) {
         return false;
      } else {
         boolean flag = false;

         for(int i = 0; i < p_77569_1_.getSizeInventory(); ++i) {
            ItemStack itemstack = p_77569_1_.getStackInSlot(i);
            if (itemstack.getItem() instanceof ItemBanner) {
               if (flag) {
                  return false;
               }

               if (TileEntityBanner.getPatterns(itemstack) >= 6) {
                  return false;
               }

               flag = true;
            }
         }

         return flag && this.func_201838_c(p_77569_1_) != null;
      }
   }

   public ItemStack getCraftingResult(IInventory p_77572_1_) {
      ItemStack itemstack = ItemStack.EMPTY;

      for(int i = 0; i < p_77572_1_.getSizeInventory(); ++i) {
         ItemStack itemstack1 = p_77572_1_.getStackInSlot(i);
         if (!itemstack1.isEmpty() && itemstack1.getItem() instanceof ItemBanner) {
            itemstack = itemstack1.copy();
            itemstack.setCount(1);
            break;
         }
      }

      BannerPattern bannerpattern = this.func_201838_c(p_77572_1_);
      if (bannerpattern != null) {
         EnumDyeColor enumdyecolor = EnumDyeColor.WHITE;

         for(int j = 0; j < p_77572_1_.getSizeInventory(); ++j) {
            Item item = p_77572_1_.getStackInSlot(j).getItem();
            if (item instanceof ItemDye) {
               enumdyecolor = ((ItemDye)item).getDyeColor();
               break;
            }
         }

         NBTTagCompound nbttagcompound1 = itemstack.getOrCreateChildTag("BlockEntityTag");
         NBTTagList nbttaglist;
         if (nbttagcompound1.hasKey("Patterns", 9)) {
            nbttaglist = nbttagcompound1.getTagList("Patterns", 10);
         } else {
            nbttaglist = new NBTTagList();
            nbttagcompound1.setTag("Patterns", nbttaglist);
         }

         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.setString("Pattern", bannerpattern.getHashname());
         nbttagcompound.setInteger("Color", enumdyecolor.getId());
         nbttaglist.add(nbttagcompound);
      }

      return itemstack;
   }

   @Nullable
   private BannerPattern func_201838_c(IInventory p_201838_1_) {
      for(BannerPattern bannerpattern : BannerPattern.values()) {
         if (bannerpattern.hasPattern()) {
            boolean flag = true;
            if (bannerpattern.hasPatternItem()) {
               boolean flag1 = false;
               boolean flag2 = false;

               for(int i = 0; i < p_201838_1_.getSizeInventory() && flag; ++i) {
                  ItemStack itemstack = p_201838_1_.getStackInSlot(i);
                  if (!itemstack.isEmpty() && !(itemstack.getItem() instanceof ItemBanner)) {
                     if (itemstack.getItem() instanceof ItemDye) {
                        if (flag2) {
                           flag = false;
                           break;
                        }

                        flag2 = true;
                     } else {
                        if (flag1 || !itemstack.isItemEqual(bannerpattern.getPatternItem())) {
                           flag = false;
                           break;
                        }

                        flag1 = true;
                     }
                  }
               }

               if (!flag1 || !flag2) {
                  flag = false;
               }
            } else if (p_201838_1_.getSizeInventory() == bannerpattern.getPatterns().length * bannerpattern.getPatterns()[0].length()) {
               EnumDyeColor enumdyecolor1 = null;

               for(int j = 0; j < p_201838_1_.getSizeInventory() && flag; ++j) {
                  int k = j / 3;
                  int l = j % 3;
                  ItemStack itemstack1 = p_201838_1_.getStackInSlot(j);
                  Item item = itemstack1.getItem();
                  if (!itemstack1.isEmpty() && !(item instanceof ItemBanner)) {
                     if (!(item instanceof ItemDye)) {
                        flag = false;
                        break;
                     }

                     EnumDyeColor enumdyecolor = ((ItemDye)item).getDyeColor();
                     if (enumdyecolor1 != null && enumdyecolor1 != enumdyecolor) {
                        flag = false;
                        break;
                     }

                     if (bannerpattern.getPatterns()[k].charAt(l) == ' ') {
                        flag = false;
                        break;
                     }

                     enumdyecolor1 = enumdyecolor;
                  } else if (bannerpattern.getPatterns()[k].charAt(l) != ' ') {
                     flag = false;
                     break;
                  }
               }
            } else {
               flag = false;
            }

            if (flag) {
               return bannerpattern;
            }
         }
      }

      return null;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean canFit(int p_194133_1_, int p_194133_2_) {
      return p_194133_1_ >= 3 && p_194133_2_ >= 3;
   }

   public IRecipeSerializer<?> getSerializer() {
      return RecipeSerializers.CRAFTING_SPECIAL_BANNERADDPATTERN;
   }
}
