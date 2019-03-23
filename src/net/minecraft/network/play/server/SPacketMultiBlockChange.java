package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SPacketMultiBlockChange implements Packet<INetHandlerPlayClient> {
   private ChunkPos chunkPos;
   private SPacketMultiBlockChange.BlockUpdateData[] changedBlocks;

   public SPacketMultiBlockChange() {
   }

   public SPacketMultiBlockChange(int p_i46959_1_, short[] p_i46959_2_, Chunk p_i46959_3_) {
      this.chunkPos = new ChunkPos(p_i46959_3_.x, p_i46959_3_.z);
      this.changedBlocks = new SPacketMultiBlockChange.BlockUpdateData[p_i46959_1_];

      for(int i = 0; i < this.changedBlocks.length; ++i) {
         this.changedBlocks[i] = new SPacketMultiBlockChange.BlockUpdateData(p_i46959_2_[i], p_i46959_3_);
      }

   }

   public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
      this.chunkPos = new ChunkPos(p_148837_1_.readInt(), p_148837_1_.readInt());
      this.changedBlocks = new SPacketMultiBlockChange.BlockUpdateData[p_148837_1_.readVarInt()];

      for(int i = 0; i < this.changedBlocks.length; ++i) {
         this.changedBlocks[i] = new SPacketMultiBlockChange.BlockUpdateData(p_148837_1_.readShort(), Block.BLOCK_STATE_IDS.getByValue(p_148837_1_.readVarInt()));
      }

   }

   public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeInt(this.chunkPos.x);
      p_148840_1_.writeInt(this.chunkPos.z);
      p_148840_1_.writeVarInt(this.changedBlocks.length);

      for(SPacketMultiBlockChange.BlockUpdateData spacketmultiblockchange$blockupdatedata : this.changedBlocks) {
         p_148840_1_.writeShort(spacketmultiblockchange$blockupdatedata.getOffset());
         p_148840_1_.writeVarInt(Block.getStateId(spacketmultiblockchange$blockupdatedata.getBlockState()));
      }

   }

   public void processPacket(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.handleMultiBlockChange(this);
   }

   @OnlyIn(Dist.CLIENT)
   public SPacketMultiBlockChange.BlockUpdateData[] getChangedBlocks() {
      return this.changedBlocks;
   }

   public class BlockUpdateData {
      private final short offset;
      private final IBlockState blockState;

      public BlockUpdateData(short p_i46544_2_, IBlockState p_i46544_3_) {
         this.offset = p_i46544_2_;
         this.blockState = p_i46544_3_;
      }

      public BlockUpdateData(short p_i46545_2_, Chunk p_i46545_3_) {
         this.offset = p_i46545_2_;
         this.blockState = p_i46545_3_.getBlockState(this.getPos());
      }

      public BlockPos getPos() {
         return new BlockPos(SPacketMultiBlockChange.this.chunkPos.getBlock(this.offset >> 12 & 15, this.offset & 255, this.offset >> 8 & 15));
      }

      public short getOffset() {
         return this.offset;
      }

      public IBlockState getBlockState() {
         return this.blockState;
      }
   }
}
