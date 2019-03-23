package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemSnowball extends Item {
   public ItemSnowball(Item.Properties p_i48466_1_) {
      super(p_i48466_1_);
   }

   public ActionResult<ItemStack> onItemRightClick(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.getHeldItem(p_77659_3_);
      if (!p_77659_2_.capabilities.isCreativeMode) {
         itemstack.shrink(1);
      }

      p_77659_1_.playSound((EntityPlayer)null, p_77659_2_.posX, p_77659_2_.posY, p_77659_2_.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
      if (!p_77659_1_.isRemote) {
         EntitySnowball entitysnowball = new EntitySnowball(p_77659_1_, p_77659_2_);
         entitysnowball.shoot(p_77659_2_, p_77659_2_.rotationPitch, p_77659_2_.rotationYaw, 0.0F, 1.5F, 1.0F);
         p_77659_1_.spawnEntity(entitysnowball);
      }

      p_77659_2_.func_71029_a(StatList.ITEM_USED.func_199076_b(this));
      return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
   }
}
