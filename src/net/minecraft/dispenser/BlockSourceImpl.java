package net.minecraft.dispenser;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSourceImpl implements IBlockSource {
   private final World world;
   private final BlockPos pos;

   public BlockSourceImpl(World p_i46023_1_, BlockPos p_i46023_2_) {
      this.world = p_i46023_1_;
      this.pos = p_i46023_2_;
   }

   public World getWorld() {
      return this.world;
   }

   public double getX() {
      return (double)this.pos.getX() + 0.5D;
   }

   public double getY() {
      return (double)this.pos.getY() + 0.5D;
   }

   public double getZ() {
      return (double)this.pos.getZ() + 0.5D;
   }

   public BlockPos getBlockPos() {
      return this.pos;
   }

   public IBlockState getBlockState() {
      return this.world.getBlockState(this.pos);
   }

   public <T extends TileEntity> T getBlockTileEntity() {
      return (T)this.world.getTileEntity(this.pos);
   }
}
