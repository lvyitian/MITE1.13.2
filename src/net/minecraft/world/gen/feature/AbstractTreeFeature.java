package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public abstract class AbstractTreeFeature<T extends IFeatureConfig> extends Feature<T> {
   public AbstractTreeFeature(boolean p_i45448_1_) {
      super(p_i45448_1_);
   }

   protected boolean canGrowInto(Block p_150523_1_) {
      IBlockState iblockstate = p_150523_1_.getDefaultState();
      return iblockstate.isAir() || iblockstate.isIn(BlockTags.LEAVES) || p_150523_1_ == Blocks.GRASS_BLOCK || Block.isDirt(p_150523_1_) || p_150523_1_.isIn(BlockTags.LOGS) || p_150523_1_.isIn(BlockTags.SAPLINGS) || p_150523_1_ == Blocks.VINE;
   }

   protected void setDirtAt(IWorld p_175921_1_, BlockPos p_175921_2_) {
      if (!Block.isDirt(p_175921_1_.getBlockState(p_175921_2_).getBlock())) {
         this.setBlockState(p_175921_1_, p_175921_2_, Blocks.DIRT.getDefaultState());
      }

   }

   protected void setBlockState(IWorld p_202278_1_, BlockPos p_202278_2_, IBlockState p_202278_3_) {
      this.func_208521_b(p_202278_1_, p_202278_2_, p_202278_3_);
   }

   protected final void func_208520_a(Set<BlockPos> p_208520_1_, IWorld p_208520_2_, BlockPos p_208520_3_, IBlockState p_208520_4_) {
      this.func_208521_b(p_208520_2_, p_208520_3_, p_208520_4_);
      if (BlockTags.LOGS.contains(p_208520_4_.getBlock())) {
         p_208520_1_.add(p_208520_3_.toImmutable());
      }

   }

   private void func_208521_b(IWorld p_208521_1_, BlockPos p_208521_2_, IBlockState p_208521_3_) {
      if (this.doBlockNotify) {
         p_208521_1_.setBlockState(p_208521_2_, p_208521_3_, 19);
      } else {
         p_208521_1_.setBlockState(p_208521_2_, p_208521_3_, 18);
      }

   }

   public final boolean func_212245_a(IWorld p_212245_1_, IChunkGenerator<? extends IChunkGenSettings> p_212245_2_, Random p_212245_3_, BlockPos p_212245_4_, T p_212245_5_) {
      Set<BlockPos> set = Sets.newHashSet();
      boolean flag = this.place(set, p_212245_1_, p_212245_3_, p_212245_4_);
      List<Set<BlockPos>> list = Lists.newArrayList();
      int i = 6;

      for(int j = 0; j < 6; ++j) {
         list.add(Sets.newHashSet());
      }

      try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain()) {
         if (flag && !set.isEmpty()) {
            for(BlockPos blockpos : Lists.newArrayList(set)) {
               for(EnumFacing enumfacing : EnumFacing.values()) {
                  blockpos$pooledmutableblockpos.setPos(blockpos).move(enumfacing);
                  if (!set.contains(blockpos$pooledmutableblockpos)) {
                     IBlockState iblockstate = p_212245_1_.getBlockState(blockpos$pooledmutableblockpos);
                     if (iblockstate.has(BlockStateProperties.DISTANCE_1_7)) {
                        list.get(0).add(blockpos$pooledmutableblockpos.toImmutable());
                        this.func_208521_b(p_212245_1_, blockpos$pooledmutableblockpos, iblockstate.with(BlockStateProperties.DISTANCE_1_7, Integer.valueOf(1)));
                     }
                  }
               }
            }
         }

         for(int l = 1; l < 6; ++l) {
            Set<BlockPos> set1 = list.get(l - 1);
            Set<BlockPos> set2 = list.get(l);

            for(BlockPos blockpos1 : set1) {
               for(EnumFacing enumfacing1 : EnumFacing.values()) {
                  blockpos$pooledmutableblockpos.setPos(blockpos1).move(enumfacing1);
                  if (!set1.contains(blockpos$pooledmutableblockpos) && !set2.contains(blockpos$pooledmutableblockpos)) {
                     IBlockState iblockstate1 = p_212245_1_.getBlockState(blockpos$pooledmutableblockpos);
                     if (iblockstate1.has(BlockStateProperties.DISTANCE_1_7)) {
                        int k = iblockstate1.get(BlockStateProperties.DISTANCE_1_7);
                        if (k > l + 1) {
                           IBlockState iblockstate2 = iblockstate1.with(BlockStateProperties.DISTANCE_1_7, Integer.valueOf(l + 1));
                           this.func_208521_b(p_212245_1_, blockpos$pooledmutableblockpos, iblockstate2);
                           set2.add(blockpos$pooledmutableblockpos.toImmutable());
                        }
                     }
                  }
               }
            }
         }
      }

      return flag;
   }

   protected abstract boolean place(Set<BlockPos> p_208519_1_, IWorld p_208519_2_, Random p_208519_3_, BlockPos p_208519_4_);
}
