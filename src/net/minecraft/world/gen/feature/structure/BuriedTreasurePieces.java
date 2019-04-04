package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class BuriedTreasurePieces {
   public static void registerBuriedTreasurePieces() {
      StructureIO.registerStructureComponent(BuriedTreasurePieces.Piece.class, "BTP");
   }

   public static class Piece extends StructurePiece {
      public Piece() {
      }

      public Piece(BlockPos p_i48882_1_) {
         super(0);
         this.boundingBox = new MutableBoundingBox(p_i48882_1_.getX(), p_i48882_1_.getY(), p_i48882_1_.getZ(), p_i48882_1_.getX(), p_i48882_1_.getY(), p_i48882_1_.getZ());
      }

      protected void writeStructureToNBT(NBTTagCompound p_143012_1_) {
      }

      protected void readStructureFromNBT(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
      }

      public boolean addComponentParts(IWorld p_74875_1_, Random p_74875_2_, MutableBoundingBox p_74875_3_, ChunkPos p_74875_4_) {
         int i = p_74875_1_.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, this.boundingBox.minX, this.boundingBox.minZ);
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(this.boundingBox.minX, i, this.boundingBox.minZ);

         while(blockpos$mutableblockpos.getY() > 0) {
            IBlockState iblockstate = p_74875_1_.getBlockState(blockpos$mutableblockpos);
            IBlockState iblockstate1 = p_74875_1_.getBlockState(blockpos$mutableblockpos.down());
            if (iblockstate1 == Blocks.SANDSTONE.getDefaultState() || iblockstate1 == Blocks.STONE.getDefaultState() || iblockstate1 == Blocks.ANDESITE.getDefaultState() || iblockstate1 == Blocks.GRANITE.getDefaultState() || iblockstate1 == Blocks.DIORITE.getDefaultState()) {
               IBlockState iblockstate2 = !iblockstate.isAir() && !this.func_204295_a(iblockstate) ? iblockstate : Blocks.SAND.getDefaultState();

               for(EnumFacing enumfacing : EnumFacing.values()) {
                  BlockPos blockpos = blockpos$mutableblockpos.offset(enumfacing);
                  IBlockState iblockstate3 = p_74875_1_.getBlockState(blockpos);
                  if (iblockstate3.isAir() || this.func_204295_a(iblockstate3)) {
                     BlockPos blockpos1 = blockpos.down();
                     IBlockState iblockstate4 = p_74875_1_.getBlockState(blockpos1);
                     if ((iblockstate4.isAir() || this.func_204295_a(iblockstate4)) && enumfacing != EnumFacing.UP) {
                        p_74875_1_.setBlockState(blockpos, iblockstate1, 3);
                     } else {
                        p_74875_1_.setBlockState(blockpos, iblockstate2, 3);
                     }
                  }
               }

               return this.generateChest(p_74875_1_, p_74875_3_, p_74875_2_, new BlockPos(this.boundingBox.minX, blockpos$mutableblockpos.getY(), this.boundingBox.minZ), LootTableList.CHESTS_BURIED_TREASURE,
                       null);
            }

            blockpos$mutableblockpos.move(0, -1, 0);
         }

         return false;
      }

      private boolean func_204295_a(IBlockState p_204295_1_) {
         return p_204295_1_ == Blocks.WATER.getDefaultState() || p_204295_1_ == Blocks.LAVA.getDefaultState();
      }
   }
}
