package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotFurnaceFuel extends Slot {
   public SlotFurnaceFuel(IInventory p_i45795_1_, int p_i45795_2_, int p_i45795_3_, int p_i45795_4_) {
      super(p_i45795_1_, p_i45795_2_, p_i45795_3_, p_i45795_4_);
   }

   public boolean isItemValid(ItemStack other) {
      return TileEntityFurnace.isItemFuel(other) || isBucket(other);
   }

   public int getItemStackLimit(ItemStack p_178170_1_) {
      return isBucket(p_178170_1_) ? 1 : super.getItemStackLimit(p_178170_1_);
   }

   public static boolean isBucket(ItemStack p_178173_0_) {
      return p_178173_0_.getItem() == Items.BUCKET;
   }
}
