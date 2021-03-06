package net.minecraft.advancements;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DisplayInfo {
   private final ITextComponent title;
   private final ITextComponent description;
   private final ItemStack icon;
   private final ResourceLocation background;
   private final FrameType frame;
   private final boolean showToast;
   private final boolean announceToChat;
   private final boolean hidden;
   private float x;
   private float y;

   public DisplayInfo(ItemStack icon, ITextComponent title, ITextComponent description, @Nullable ResourceLocation background, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
      this.title = title;
      this.description = description;
      this.icon = icon;
      this.background = background;
      this.frame = frame;
      this.showToast = showToast;
      this.announceToChat = announceToChat;
      this.hidden = hidden;
   }

   public void setPosition(float p_192292_1_, float p_192292_2_) {
      this.x = p_192292_1_;
      this.y = p_192292_2_;
   }

   public ITextComponent getTitle() {
      return this.title;
   }

   public ITextComponent getDescription() {
      return this.description;
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack getIcon() {
      return this.icon;
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public ResourceLocation getBackground() {
      return this.background;
   }

   public FrameType getFrame() {
      return this.frame;
   }

   @OnlyIn(Dist.CLIENT)
   public float getX() {
      return this.x;
   }

   @OnlyIn(Dist.CLIENT)
   public float getY() {
      return this.y;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean shouldShowToast() {
      return this.showToast;
   }

   public boolean shouldAnnounceToChat() {
      return this.announceToChat;
   }

   public boolean isHidden() {
      return this.hidden;
   }

   public static DisplayInfo deserialize(JsonObject p_192294_0_, JsonDeserializationContext p_192294_1_) {
      ITextComponent itextcomponent = JsonUtils.deserializeClass(p_192294_0_, "title", p_192294_1_, ITextComponent.class);
      ITextComponent itextcomponent1 = JsonUtils.deserializeClass(p_192294_0_, "description", p_192294_1_, ITextComponent.class);
      if (itextcomponent != null && itextcomponent1 != null) {
         ItemStack itemstack = deserializeIcon(JsonUtils.getJsonObject(p_192294_0_, "icon"));
         ResourceLocation resourcelocation = p_192294_0_.has("background") ? new ResourceLocation(JsonUtils.getString(p_192294_0_, "background")) : null;
         FrameType frametype = p_192294_0_.has("frame") ? FrameType.byName(JsonUtils.getString(p_192294_0_, "frame")) : FrameType.TASK;
         boolean flag = JsonUtils.getBoolean(p_192294_0_, "show_toast", true);
         boolean flag1 = JsonUtils.getBoolean(p_192294_0_, "announce_to_chat", true);
         boolean flag2 = JsonUtils.getBoolean(p_192294_0_, "hidden", false);
         return new DisplayInfo(itemstack, itextcomponent, itextcomponent1, resourcelocation, frametype, flag, flag1, flag2);
      } else {
         throw new JsonSyntaxException("Both title and description must be set");
      }
   }

   private static ItemStack deserializeIcon(JsonObject p_193221_0_) {
      if (!p_193221_0_.has("item")) {
         throw new JsonSyntaxException("Unsupported icon type, currently only items are supported (add 'item' key)");
      } else {
         Item item = JsonUtils.getItem(p_193221_0_, "item");
         if (p_193221_0_.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
         } else {
            ItemStack itemstack = new ItemStack(item);
            if (p_193221_0_.has("nbt")) {
               try {
                  NBTTagCompound nbttagcompound = JsonToNBT.getTagFromJson(JsonUtils.getString(p_193221_0_.get("nbt"), "nbt"));
                  itemstack.setTag(nbttagcompound);
               } catch (CommandSyntaxException commandsyntaxexception) {
                  throw new JsonSyntaxException("Invalid nbt tag: " + commandsyntaxexception.getMessage());
               }
            }

            return itemstack;
         }
      }
   }

   public void write(PacketBuffer p_192290_1_) {
      p_192290_1_.writeTextComponent(this.title);
      p_192290_1_.writeTextComponent(this.description);
      p_192290_1_.writeItemStack(this.icon);
      p_192290_1_.writeEnumValue(this.frame);
      int i = 0;
      if (this.background != null) {
         i |= 1;
      }

      if (this.showToast) {
         i |= 2;
      }

      if (this.hidden) {
         i |= 4;
      }

      p_192290_1_.writeInt(i);
      if (this.background != null) {
         p_192290_1_.writeResourceLocation(this.background);
      }

      p_192290_1_.writeFloat(this.x);
      p_192290_1_.writeFloat(this.y);
   }

   public static DisplayInfo read(PacketBuffer p_192295_0_) {
      ITextComponent itextcomponent = p_192295_0_.readTextComponent();
      ITextComponent itextcomponent1 = p_192295_0_.readTextComponent();
      ItemStack itemstack = p_192295_0_.readItemStack();
      FrameType frametype = p_192295_0_.readEnumValue(FrameType.class);
      int i = p_192295_0_.readInt();
      ResourceLocation resourcelocation = (i & 1) != 0 ? p_192295_0_.readResourceLocation() : null;
      boolean flag = (i & 2) != 0;
      boolean flag1 = (i & 4) != 0;
      DisplayInfo displayinfo = new DisplayInfo(itemstack, itextcomponent, itextcomponent1, resourcelocation, frametype, flag, false, flag1);
      displayinfo.setPosition(p_192295_0_.readFloat(), p_192295_0_.readFloat());
      return displayinfo;
   }

   public JsonElement serialize() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.add("icon", this.serializeIcon());
      jsonobject.add("title", ITextComponent.Serializer.toJsonTree(this.title));
      jsonobject.add("description", ITextComponent.Serializer.toJsonTree(this.description));
      jsonobject.addProperty("frame", this.frame.getName());
      jsonobject.addProperty("show_toast", this.showToast);
      jsonobject.addProperty("announce_to_chat", this.announceToChat);
      jsonobject.addProperty("hidden", this.hidden);
      if (this.background != null) {
         jsonobject.addProperty("background", this.background.toString());
      }

      return jsonobject;
   }

   private JsonObject serializeIcon() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("item", IRegistry.field_212630_s.func_177774_c(this.icon.getItem()).toString());
      return jsonobject;
   }
}
