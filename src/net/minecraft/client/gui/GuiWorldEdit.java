package net.minecraft.client.gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.FileUtils;

@OnlyIn(Dist.CLIENT)
public class GuiWorldEdit extends GuiScreen {
   private GuiButton saveButton;
   private final GuiYesNoCallback lastScreen;
   private GuiTextField nameEdit;
   private final String worldId;

   public GuiWorldEdit(GuiYesNoCallback p_i49848_1_, String p_i49848_2_) {
      this.lastScreen = p_i49848_1_;
      this.worldId = p_i49848_2_;
   }

   public void tick() {
      this.nameEdit.tick();
   }

   protected void initGui() {
      this.mc.keyboardListener.enableRepeatEvents(true);
      GuiButton guibutton = this.addButton(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 24 + 5, I18n.format("selectWorld.edit.resetIcon")) {
         public void onClick(double p_194829_1_, double p_194829_3_) {
            ISaveFormat isaveformat1 = GuiWorldEdit.this.mc.getSaveLoader();
            FileUtils.deleteQuietly(isaveformat1.getFile(GuiWorldEdit.this.worldId, "icon.png"));
            this.enabled = false;
         }
      });
      this.addButton(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 48 + 5, I18n.format("selectWorld.edit.openFolder")) {
         public void onClick(double p_194829_1_, double p_194829_3_) {
            ISaveFormat isaveformat1 = GuiWorldEdit.this.mc.getSaveLoader();
            Util.getOSType().openFile(isaveformat1.getFile(GuiWorldEdit.this.worldId, "icon.png").getParentFile());
         }
      });
      this.addButton(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 72 + 5, I18n.format("selectWorld.edit.backup")) {
         public void onClick(double p_194829_1_, double p_194829_3_) {
            ISaveFormat isaveformat1 = GuiWorldEdit.this.mc.getSaveLoader();
            GuiWorldEdit.createBackup(isaveformat1, GuiWorldEdit.this.worldId);
            GuiWorldEdit.this.lastScreen.confirmResult(false, 0);
         }
      });
      this.addButton(new GuiButton(6, this.width / 2 - 100, this.height / 4 + 96 + 5, I18n.format("selectWorld.edit.backupFolder")) {
         public void onClick(double p_194829_1_, double p_194829_3_) {
            ISaveFormat isaveformat1 = GuiWorldEdit.this.mc.getSaveLoader();
            Path path = isaveformat1.getBackupsFolder();

            try {
               Files.createDirectories(Files.exists(path) ? path.toRealPath() : path);
            } catch (IOException ioexception) {
               throw new RuntimeException(ioexception);
            }

            Util.getOSType().openFile(path.toFile());
         }
      });
      this.addButton(new GuiButton(7, this.width / 2 - 100, this.height / 4 + 120 + 5, I18n.format("selectWorld.edit.optimize")) {
         public void onClick(double p_194829_1_, double p_194829_3_) {
            GuiWorldEdit.this.mc.displayGuiScreen(new GuiConfirmBackup(GuiWorldEdit.this, (p_212103_1_) -> {
               if (p_212103_1_) {
                  GuiWorldEdit.createBackup(GuiWorldEdit.this.mc.getSaveLoader(), GuiWorldEdit.this.worldId);
               }

               GuiWorldEdit.this.mc.displayGuiScreen(new GuiOptimizeWorld(GuiWorldEdit.this.lastScreen, GuiWorldEdit.this.worldId, GuiWorldEdit.this.mc.getSaveLoader()));
            }, I18n.format("optimizeWorld.confirm.title"), I18n.format("optimizeWorld.confirm.description")));
         }
      });
      this.saveButton = this.addButton(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 144 + 5, 98, 20, I18n.format("selectWorld.edit.save")) {
         public void onClick(double p_194829_1_, double p_194829_3_) {
            GuiWorldEdit.this.saveChanges();
         }
      });
      this.addButton(new GuiButton(1, this.width / 2 + 2, this.height / 4 + 144 + 5, 98, 20, I18n.format("gui.cancel")) {
         public void onClick(double p_194829_1_, double p_194829_3_) {
            GuiWorldEdit.this.lastScreen.confirmResult(false, 0);
         }
      });
      guibutton.enabled = this.mc.getSaveLoader().getFile(this.worldId, "icon.png").isFile();
      ISaveFormat isaveformat = this.mc.getSaveLoader();
      WorldInfo worldinfo = isaveformat.getWorldInfo(this.worldId);
      String s = worldinfo == null ? "" : worldinfo.getWorldName();
      this.nameEdit = new GuiTextField(2, this.fontRenderer, this.width / 2 - 100, 53, 200, 20);
      this.nameEdit.setFocused(true);
      this.nameEdit.setText(s);
      this.eventListeners.add(this.nameEdit);
   }

   public void onResize(Minecraft p_175273_1_, int p_175273_2_, int p_175273_3_) {
      String s = this.nameEdit.getText();
      this.setWorldAndResolution(p_175273_1_, p_175273_2_, p_175273_3_);
      this.nameEdit.setText(s);
   }

   public void onGuiClosed() {
      this.mc.keyboardListener.enableRepeatEvents(false);
   }

   private void saveChanges() {
      ISaveFormat isaveformat = this.mc.getSaveLoader();
      isaveformat.renameWorld(this.worldId, this.nameEdit.getText().trim());
      this.lastScreen.confirmResult(true, 0);
   }

   public static void createBackup(ISaveFormat p_200212_0_, String p_200212_1_) {
      GuiToast guitoast = Minecraft.getInstance().getToastGui();
      long i = 0L;
      IOException ioexception = null;

      try {
         i = p_200212_0_.createBackup(p_200212_1_);
      } catch (IOException ioexception1) {
         ioexception = ioexception1;
      }

      ITextComponent itextcomponent;
      ITextComponent itextcomponent1;
      if (ioexception != null) {
         itextcomponent = new TextComponentTranslation("selectWorld.edit.backupFailed");
         itextcomponent1 = new TextComponentString(ioexception.getMessage());
      } else {
         itextcomponent = new TextComponentTranslation("selectWorld.edit.backupCreated", p_200212_1_);
         itextcomponent1 = new TextComponentTranslation("selectWorld.edit.backupSize", MathHelper.ceil((double)i / 1048576.0D));
      }

      guitoast.add(new SystemToast(SystemToast.Type.WORLD_BACKUP, itextcomponent, itextcomponent1));
   }

   public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
      if (this.nameEdit.charTyped(p_charTyped_1_, p_charTyped_2_)) {
         this.saveButton.enabled = !this.nameEdit.getText().trim().isEmpty();
         return true;
      } else {
         return false;
      }
   }

   public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
      if (this.nameEdit.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) {
         this.saveButton.enabled = !this.nameEdit.getText().trim().isEmpty();
         return true;
      } else if (p_keyPressed_1_ != 257 && p_keyPressed_1_ != 335) {
         return false;
      } else {
         this.saveChanges();
         return true;
      }
   }

   public void render(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRenderer, I18n.format("selectWorld.edit.title"), this.width / 2, 20, 16777215);
      this.drawString(this.fontRenderer, I18n.format("selectWorld.enterName"), this.width / 2 - 100, 40, 10526880);
      this.nameEdit.drawTextField(p_73863_1_, p_73863_2_, p_73863_3_);
      super.render(p_73863_1_, p_73863_2_, p_73863_3_);
   }
}
