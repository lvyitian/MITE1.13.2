package net.minecraft.client.tutorial;

import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.util.MovementInput;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MovementStep implements ITutorialStep {
   private static final ITextComponent MOVE_TITLE = new TextComponentTranslation("tutorial.move.title", Tutorial.createKeybindComponent("forward"), Tutorial.createKeybindComponent("left"), Tutorial.createKeybindComponent("back"), Tutorial.createKeybindComponent("right"));
   private static final ITextComponent MOVE_DESCRIPTION = new TextComponentTranslation("tutorial.move.description", Tutorial.createKeybindComponent("jump"));
   private static final ITextComponent LOOK_TITLE = new TextComponentTranslation("tutorial.look.title");
   private static final ITextComponent LOOK_DESCRIPTION = new TextComponentTranslation("tutorial.look.description");
   private final Tutorial tutorial;
   private TutorialToast moveToast;
   private TutorialToast lookToast;
   private int timeWaiting;
   private int timeMoved;
   private int timeLooked;
   private boolean moved;
   private boolean turned;
   private int moveCompleted = -1;
   private int lookCompleted = -1;

   public MovementStep(Tutorial p_i47581_1_) {
      this.tutorial = p_i47581_1_;
   }

   public void tick() {
      ++this.timeWaiting;
      if (this.moved) {
         ++this.timeMoved;
         this.moved = false;
      }

      if (this.turned) {
         ++this.timeLooked;
         this.turned = false;
      }

      if (this.moveCompleted == -1 && this.timeMoved > 40) {
         if (this.moveToast != null) {
            this.moveToast.hide();
            this.moveToast = null;
         }

         this.moveCompleted = this.timeWaiting;
      }

      if (this.lookCompleted == -1 && this.timeLooked > 40) {
         if (this.lookToast != null) {
            this.lookToast.hide();
            this.lookToast = null;
         }

         this.lookCompleted = this.timeWaiting;
      }

      if (this.moveCompleted != -1 && this.lookCompleted != -1) {
         if (this.tutorial.getGameType() == GameType.SURVIVAL) {
            this.tutorial.setStep(TutorialSteps.OPEN_INVENTORY);
         } else {
            this.tutorial.setStep(TutorialSteps.NONE);
         }
      }

      if (this.moveToast != null) {
         this.moveToast.setProgress((float)this.timeMoved / 40.0F);
      }

      if (this.lookToast != null) {
         this.lookToast.setProgress((float)this.timeLooked / 40.0F);
      }

      if (this.timeWaiting >= 100) {
         if (this.moveCompleted == -1 && this.moveToast == null) {
            this.moveToast = new TutorialToast(TutorialToast.Icons.MOVEMENT_KEYS, MOVE_TITLE, MOVE_DESCRIPTION, true);
            this.tutorial.getMinecraft().getToastGui().add(this.moveToast);
         } else if (this.moveCompleted != -1 && this.timeWaiting - this.moveCompleted >= 20 && this.lookCompleted == -1 && this.lookToast == null) {
            this.lookToast = new TutorialToast(TutorialToast.Icons.MOUSE, LOOK_TITLE, LOOK_DESCRIPTION, true);
            this.tutorial.getMinecraft().getToastGui().add(this.lookToast);
         }
      }

   }

   public void onStop() {
      if (this.moveToast != null) {
         this.moveToast.hide();
         this.moveToast = null;
      }

      if (this.lookToast != null) {
         this.lookToast.hide();
         this.lookToast = null;
      }

   }

   public void handleMovement(MovementInput p_193247_1_) {
      if (p_193247_1_.forwardKeyDown || p_193247_1_.backKeyDown || p_193247_1_.leftKeyDown || p_193247_1_.rightKeyDown || p_193247_1_.jump) {
         this.moved = true;
      }

   }

   public void onMouseMove(double p_195870_1_, double p_195870_3_) {
      if (Math.abs(p_195870_1_) > 0.01D || Math.abs(p_195870_3_) > 0.01D) {
         this.turned = true;
      }

   }
}
