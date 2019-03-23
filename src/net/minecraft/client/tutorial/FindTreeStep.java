package net.minecraft.client.tutorial;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FindTreeStep implements ITutorialStep {
   private static final Set<Block> TREE_BLOCKS = Sets.newHashSet(Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG, Blocks.OAK_WOOD, Blocks.SPRUCE_WOOD, Blocks.BIRCH_WOOD, Blocks.JUNGLE_WOOD, Blocks.ACACIA_WOOD, Blocks.DARK_OAK_WOOD, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES);
   private static final ITextComponent TITLE = new TextComponentTranslation("tutorial.find_tree.title");
   private static final ITextComponent DESCRIPTION = new TextComponentTranslation("tutorial.find_tree.description");
   private final Tutorial tutorial;
   private TutorialToast toast;
   private int timeWaiting;

   public FindTreeStep(Tutorial p_i47582_1_) {
      this.tutorial = p_i47582_1_;
   }

   public void tick() {
      ++this.timeWaiting;
      if (this.tutorial.getGameType() != GameType.SURVIVAL) {
         this.tutorial.setStep(TutorialSteps.NONE);
      } else {
         if (this.timeWaiting == 1) {
            EntityPlayerSP entityplayersp = this.tutorial.getMinecraft().player;
            if (entityplayersp != null) {
               for(Block block : TREE_BLOCKS) {
                  if (entityplayersp.inventory.hasItemStack(new ItemStack(block))) {
                     this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
                     return;
                  }
               }

               if (hasPunchedTreesPreviously(entityplayersp)) {
                  this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
                  return;
               }
            }
         }

         if (this.timeWaiting >= 6000 && this.toast == null) {
            this.toast = new TutorialToast(TutorialToast.Icons.TREE, TITLE, DESCRIPTION, false);
            this.tutorial.getMinecraft().getToastGui().add(this.toast);
         }

      }
   }

   public void onStop() {
      if (this.toast != null) {
         this.toast.hide();
         this.toast = null;
      }

   }

   public void onMouseHover(WorldClient p_193246_1_, RayTraceResult p_193246_2_) {
      if (p_193246_2_.type == RayTraceResult.Type.BLOCK && p_193246_2_.getBlockPos() != null) {
         IBlockState iblockstate = p_193246_1_.getBlockState(p_193246_2_.getBlockPos());
         if (TREE_BLOCKS.contains(iblockstate.getBlock())) {
            this.tutorial.setStep(TutorialSteps.PUNCH_TREE);
         }
      }

   }

   public void handleSetSlot(ItemStack p_193252_1_) {
      for(Block block : TREE_BLOCKS) {
         if (p_193252_1_.getItem() == block.asItem()) {
            this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
            return;
         }
      }

   }

   public static boolean hasPunchedTreesPreviously(EntityPlayerSP p_194070_0_) {
      for(Block block : TREE_BLOCKS) {
         if (p_194070_0_.getStatFileWriter().func_77444_a(StatList.BLOCK_MINED.func_199076_b(block)) > 0) {
            return true;
         }
      }

      return false;
   }
}
