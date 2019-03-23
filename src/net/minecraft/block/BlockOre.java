package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockOre extends Block {
   public BlockOre(Block.Properties p_i48357_1_) {
      super(p_i48357_1_);
   }

   public IItemProvider getItemDropped(IBlockState p_199769_1_, World p_199769_2_, BlockPos p_199769_3_, int p_199769_4_) {
      if (this == Blocks.COAL_ORE) {
         return Items.COAL;
      } else if (this == Blocks.DIAMOND_ORE) {
         return Items.DIAMOND;
      } else if (this == Blocks.LAPIS_ORE) {
         return Items.LAPIS_LAZULI;
      } else if (this == Blocks.EMERALD_ORE) {
         return Items.EMERALD;
      } else {
         return (IItemProvider)(this == Blocks.NETHER_QUARTZ_ORE ? Items.QUARTZ : this);
      }
   }

   public int quantityDropped(IBlockState p_196264_1_, Random p_196264_2_) {
      return this == Blocks.LAPIS_ORE ? 4 + p_196264_2_.nextInt(5) : 1;
   }

   public int getItemsToDropCount(IBlockState p_196251_1_, int p_196251_2_, World p_196251_3_, BlockPos p_196251_4_, Random p_196251_5_) {
      if (p_196251_2_ > 0 && this != this.getItemDropped(this.getStateContainer().getValidStates().iterator().next(), p_196251_3_, p_196251_4_, p_196251_2_)) {
         int i = p_196251_5_.nextInt(p_196251_2_ + 2) - 1;
         if (i < 0) {
            i = 0;
         }

         return this.quantityDropped(p_196251_1_, p_196251_5_) * (i + 1);
      } else {
         return this.quantityDropped(p_196251_1_, p_196251_5_);
      }
   }

   public void dropBlockAsItemWithChance(IBlockState p_196255_1_, World p_196255_2_, BlockPos p_196255_3_, float p_196255_4_, int p_196255_5_) {
      super.dropBlockAsItemWithChance(p_196255_1_, p_196255_2_, p_196255_3_, p_196255_4_, p_196255_5_);
      if (this.getItemDropped(p_196255_1_, p_196255_2_, p_196255_3_, p_196255_5_) != this) {
         int i = 0;
         if (this == Blocks.COAL_ORE) {
            i = MathHelper.nextInt(p_196255_2_.rand, 0, 2);
         } else if (this == Blocks.DIAMOND_ORE) {
            i = MathHelper.nextInt(p_196255_2_.rand, 3, 7);
         } else if (this == Blocks.EMERALD_ORE) {
            i = MathHelper.nextInt(p_196255_2_.rand, 3, 7);
         } else if (this == Blocks.LAPIS_ORE) {
            i = MathHelper.nextInt(p_196255_2_.rand, 2, 5);
         } else if (this == Blocks.NETHER_QUARTZ_ORE) {
            i = MathHelper.nextInt(p_196255_2_.rand, 2, 5);
         }

         this.dropXpOnBlockBreak(p_196255_2_, p_196255_3_, i);
      }

   }

   public ItemStack getItem(IBlockReader p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(this);
   }
}