package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderList extends ChunkRenderContainer {
   public void renderChunkLayer(BlockRenderLayer p_178001_1_) {
      if (this.initialized) {
         for(RenderChunk renderchunk : this.renderChunks) {
            ListedRenderChunk listedrenderchunk = (ListedRenderChunk)renderchunk;
            GlStateManager.pushMatrix();
            this.preRenderChunk(renderchunk);
            GlStateManager.callList(listedrenderchunk.getDisplayList(p_178001_1_, listedrenderchunk.getCompiledChunk()));
            GlStateManager.popMatrix();
         }

         GlStateManager.resetColor();
         this.renderChunks.clear();
      }
   }
}
