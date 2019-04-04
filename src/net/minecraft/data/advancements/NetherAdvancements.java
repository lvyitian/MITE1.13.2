package net.minecraft.data.advancements;

import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.BrewedPotionTrigger;
import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.advancements.criterion.ConstructBeaconTrigger;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.advancements.criterion.DistancePredicate;
import net.minecraft.advancements.criterion.EffectsChangedTrigger;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.KilledTrigger;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.advancements.criterion.NetherTravelTrigger;
import net.minecraft.advancements.criterion.PositionTrigger;
import net.minecraft.advancements.criterion.SummonedEntityTrigger;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.dimension.DimensionType;

public class NetherAdvancements implements Consumer<Consumer<Advancement>> {
   public void accept(Consumer<Advancement> p_accept_1_) {
      Advancement advancement = Advancement.Builder.builder().withDisplay(Blocks.RED_NETHER_BRICKS, new TextComponentTranslation("advancements.nether.root.title"), new TextComponentTranslation("advancements.nether.root.description"), new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/nether.png"), FrameType.TASK, false, false, false).withCriterion("entered_nether", ChangeDimensionTrigger.Instance.func_203911_a(DimensionType.NETHER)).register(p_accept_1_, "nether/root");
      Advancement advancement1 = Advancement.Builder.builder().withParent(advancement).withDisplay(Items.FIRE_CHARGE, new TextComponentTranslation("advancements.nether.return_to_sender.title"), new TextComponentTranslation("advancements.nether.return_to_sender.description"),
              null, FrameType.CHALLENGE, true, true, false).withRewards(AdvancementRewards.Builder.experience(50)).withCriterion("killed_ghast", KilledTrigger.Instance.func_203929_a(EntityPredicate.Builder.func_203996_a().func_203998_a(EntityType.GHAST), DamageSourcePredicate.Builder.func_203981_a().func_203978_a(true).func_203980_a(EntityPredicate.Builder.func_203996_a().func_203998_a(EntityType.FIREBALL)))).register(p_accept_1_, "nether/return_to_sender");
      Advancement advancement2 = Advancement.Builder.builder().withParent(advancement).withDisplay(Blocks.NETHER_BRICKS, new TextComponentTranslation("advancements.nether.find_fortress.title"), new TextComponentTranslation("advancements.nether.find_fortress.description"),
              null, FrameType.TASK, true, true, false).withCriterion("fortress", PositionTrigger.Instance.func_203932_a(LocationPredicate.func_204007_a("Fortress"))).register(p_accept_1_, "nether/find_fortress");
      Advancement advancement3 = Advancement.Builder.builder().withParent(advancement).withDisplay(Items.MAP, new TextComponentTranslation("advancements.nether.fast_travel.title"), new TextComponentTranslation("advancements.nether.fast_travel.description"),
              null, FrameType.CHALLENGE, true, true, false).withRewards(AdvancementRewards.Builder.experience(100)).withCriterion("travelled", NetherTravelTrigger.Instance.func_203933_a(DistancePredicate.func_203995_a(MinMaxBounds.FloatBound.func_211355_b(7000.0F)))).register(p_accept_1_, "nether/fast_travel");
      Advancement advancement4 = Advancement.Builder.builder().withParent(advancement1).withDisplay(Items.GHAST_TEAR, new TextComponentTranslation("advancements.nether.uneasy_alliance.title"), new TextComponentTranslation("advancements.nether.uneasy_alliance.description"),
              null, FrameType.CHALLENGE, true, true, false).withRewards(AdvancementRewards.Builder.experience(100)).withCriterion("killed_ghast", KilledTrigger.Instance.func_203928_a(EntityPredicate.Builder.func_203996_a().func_203998_a(EntityType.GHAST).func_203999_a(LocationPredicate.func_204008_a(DimensionType.OVERWORLD)))).register(p_accept_1_, "nether/uneasy_alliance");
      Advancement advancement5 = Advancement.Builder.builder().withParent(advancement2).withDisplay(Blocks.WITHER_SKELETON_SKULL, new TextComponentTranslation("advancements.nether.get_wither_skull.title"), new TextComponentTranslation("advancements.nether.get_wither_skull.description"),
              null, FrameType.TASK, true, true, false).withCriterion("wither_skull", InventoryChangeTrigger.Instance.func_203922_a(Blocks.WITHER_SKELETON_SKULL)).register(p_accept_1_, "nether/get_wither_skull");
      Advancement advancement6 = Advancement.Builder.builder().withParent(advancement5).withDisplay(Items.NETHER_STAR, new TextComponentTranslation("advancements.nether.summon_wither.title"), new TextComponentTranslation("advancements.nether.summon_wither.description"),
              null, FrameType.TASK, true, true, false).withCriterion("summoned", SummonedEntityTrigger.Instance.func_203937_a(EntityPredicate.Builder.func_203996_a().func_203998_a(EntityType.WITHER))).register(p_accept_1_, "nether/summon_wither");
      Advancement advancement7 = Advancement.Builder.builder().withParent(advancement2).withDisplay(Items.BLAZE_ROD, new TextComponentTranslation("advancements.nether.obtain_blaze_rod.title"), new TextComponentTranslation("advancements.nether.obtain_blaze_rod.description"),
              null, FrameType.TASK, true, true, false).withCriterion("blaze_rod", InventoryChangeTrigger.Instance.func_203922_a(Items.BLAZE_ROD)).register(p_accept_1_, "nether/obtain_blaze_rod");
      Advancement advancement8 = Advancement.Builder.builder().withParent(advancement6).withDisplay(Blocks.BEACON, new TextComponentTranslation("advancements.nether.create_beacon.title"), new TextComponentTranslation("advancements.nether.create_beacon.description"),
              null, FrameType.TASK, true, true, false).withCriterion("beacon", ConstructBeaconTrigger.Instance.func_203912_a(MinMaxBounds.IntBound.func_211340_b(1))).register(p_accept_1_, "nether/create_beacon");
      Advancement advancement9 = Advancement.Builder.builder().withParent(advancement8).withDisplay(Blocks.BEACON, new TextComponentTranslation("advancements.nether.create_full_beacon.title"), new TextComponentTranslation("advancements.nether.create_full_beacon.description"),
              null, FrameType.GOAL, true, true, false).withCriterion("beacon", ConstructBeaconTrigger.Instance.func_203912_a(MinMaxBounds.IntBound.func_211345_a(4))).register(p_accept_1_, "nether/create_full_beacon");
      Advancement advancement10 = Advancement.Builder.builder().withParent(advancement7).withDisplay(Items.POTION, new TextComponentTranslation("advancements.nether.brew_potion.title"), new TextComponentTranslation("advancements.nether.brew_potion.description"),
              null, FrameType.TASK, true, true, false).withCriterion("potion", BrewedPotionTrigger.Instance.func_203910_c()).register(p_accept_1_, "nether/brew_potion");
      Advancement advancement11 = Advancement.Builder.builder().withParent(advancement10).withDisplay(Items.MILK_BUCKET, new TextComponentTranslation("advancements.nether.all_potions.title"), new TextComponentTranslation("advancements.nether.all_potions.description"),
              null, FrameType.CHALLENGE, true, true, false).withRewards(AdvancementRewards.Builder.experience(100)).withCriterion("all_effects", EffectsChangedTrigger.Instance.func_203917_a(MobEffectsPredicate.func_204014_a().func_204015_a(MobEffects.SPEED).func_204015_a(MobEffects.SLOWNESS).func_204015_a(MobEffects.STRENGTH).func_204015_a(MobEffects.JUMP_BOOST).func_204015_a(MobEffects.REGENERATION).func_204015_a(MobEffects.FIRE_RESISTANCE).func_204015_a(MobEffects.WATER_BREATHING).func_204015_a(MobEffects.INVISIBILITY).func_204015_a(MobEffects.NIGHT_VISION).func_204015_a(MobEffects.WEAKNESS).func_204015_a(MobEffects.POISON).func_204015_a(MobEffects.SLOW_FALLING).func_204015_a(MobEffects.RESISTANCE))).register(p_accept_1_, "nether/all_potions");
      Advancement advancement12 = Advancement.Builder.builder().withParent(advancement11).withDisplay(Items.BUCKET, new TextComponentTranslation("advancements.nether.all_effects.title"), new TextComponentTranslation("advancements.nether.all_effects.description"),
              null, FrameType.CHALLENGE, true, true, true).withRewards(AdvancementRewards.Builder.experience(1000)).withCriterion("all_effects", EffectsChangedTrigger.Instance.func_203917_a(MobEffectsPredicate.func_204014_a().func_204015_a(MobEffects.SPEED).func_204015_a(MobEffects.SLOWNESS).func_204015_a(MobEffects.STRENGTH).func_204015_a(MobEffects.JUMP_BOOST).func_204015_a(MobEffects.REGENERATION).func_204015_a(MobEffects.FIRE_RESISTANCE).func_204015_a(MobEffects.WATER_BREATHING).func_204015_a(MobEffects.INVISIBILITY).func_204015_a(MobEffects.NIGHT_VISION).func_204015_a(MobEffects.WEAKNESS).func_204015_a(MobEffects.POISON).func_204015_a(MobEffects.WITHER).func_204015_a(MobEffects.HASTE).func_204015_a(MobEffects.MINING_FATIGUE).func_204015_a(MobEffects.LEVITATION).func_204015_a(MobEffects.GLOWING).func_204015_a(MobEffects.ABSORPTION).func_204015_a(MobEffects.HUNGER).func_204015_a(MobEffects.NAUSEA).func_204015_a(MobEffects.RESISTANCE).func_204015_a(MobEffects.SLOW_FALLING).func_204015_a(MobEffects.CONDUIT_POWER).func_204015_a(MobEffects.DOLPHINS_GRACE))).register(p_accept_1_, "nether/all_effects");
   }
}
