package net.minecraft.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.criterion.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeSerializers;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class RecipeProvider implements IDataProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGenerator generator;

    public RecipeProvider(DataGenerator p_i48262_1_) {
        this.generator = p_i48262_1_;
    }

    public void act(DirectoryCache p_200398_1_) throws IOException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        this.registerRecipes((p_200410_4_) -> {
            if (!set.add(p_200410_4_.getID())) {
                throw new IllegalStateException("Duplicate recipe " + p_200410_4_.getID());
            } else {
                this.saveRecipe(p_200398_1_,
                        p_200410_4_.getRecipeJson(),
                        path.resolve("data/" + p_200410_4_.getID().getNamespace() + "/recipes/" + p_200410_4_.getID()
                                .getPath() + ".json"));
                JsonObject jsonobject = p_200410_4_.getAdvancementJson();
                if (jsonobject != null) {
                    this.saveRecipeAdvancement(p_200398_1_,
                            jsonobject,
                            path.resolve("data/" + p_200410_4_.getID()
                                    .getNamespace() + "/advancements/" + p_200410_4_.getAdvancementID()
                                    .getPath() + ".json"));
                }

            }
        });
        this.saveRecipeAdvancement(p_200398_1_,
                Advancement.Builder.builder().withCriterion("impossible", new ImpossibleTrigger.Instance()).serialize(),
                path.resolve("data/minecraft/advancements/recipes/root.json"));
    }

    public String getName() {
        return "Recipes";
    }

    private EnterBlockTrigger.Instance enteredBlock(Block p_200407_1_) {
        return new EnterBlockTrigger.Instance(p_200407_1_, null);
    }

    private InventoryChangeTrigger.Instance hasItem(MinMaxBounds.IntBound p_200408_1_, IItemProvider p_200408_2_) {
        return this.hasItem(ItemPredicate.Builder.create().func_200308_a(p_200408_2_).count(p_200408_1_).build());
    }

    private InventoryChangeTrigger.Instance hasItem(IItemProvider p_200403_1_) {
        return this.hasItem(ItemPredicate.Builder.create().func_200308_a(p_200403_1_).build());
    }

    private InventoryChangeTrigger.Instance hasItem(Tag<Item> p_200409_1_) {
        return this.hasItem(ItemPredicate.Builder.create().tag(p_200409_1_).build());
    }

    private InventoryChangeTrigger.Instance hasItem(ItemPredicate... p_200405_1_) {
        return new InventoryChangeTrigger.Instance(MinMaxBounds.IntBound.UNBOUNDED,
                MinMaxBounds.IntBound.UNBOUNDED,
                MinMaxBounds.IntBound.UNBOUNDED,
                p_200405_1_);
    }

    private void registerRecipes(Consumer<IFinishedRecipe> p_200404_1_) {
        ShapedRecipeBuilder.shapedRecipe(Blocks.ACACIA_WOOD, 3)
                .key('#', Blocks.ACACIA_LOG)
                .patternLine("##")
                .patternLine("##")
                .setGroup("bark")
                .addCriterion("has_log", this.hasItem(Blocks.ACACIA_LOG))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.ACACIA_BOAT)
                .key('#', Blocks.ACACIA_PLANKS)
                .patternLine("# #")
                .patternLine("###")
                .setGroup("boat")
                .addCriterion("in_water", this.enteredBlock(Blocks.WATER))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.ACACIA_BUTTON)
                .addIngredient(Blocks.ACACIA_PLANKS)
                .setGroup("wooden_button")
                .addCriterion("has_planks", this.hasItem(Blocks.ACACIA_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ACACIA_DOOR, 3)
                .key('#', Blocks.ACACIA_PLANKS)
                .patternLine("##")
                .patternLine("##")
                .patternLine("##")
                .setGroup("wooden_door")
                .addCriterion("has_planks", this.hasItem(Blocks.ACACIA_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ACACIA_FENCE, 3)
                .key('#', Items.STICK)
                .key('W', Blocks.ACACIA_PLANKS)
                .patternLine("W#W")
                .patternLine("W#W")
                .setGroup("wooden_fence")
                .addCriterion("has_planks", this.hasItem(Blocks.ACACIA_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ACACIA_FENCE_GATE)
                .key('#', Items.STICK)
                .key('W', Blocks.ACACIA_PLANKS)
                .patternLine("#W#")
                .patternLine("#W#")
                .setGroup("wooden_fence_gate")
                .addCriterion("has_planks", this.hasItem(Blocks.ACACIA_PLANKS))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.ACACIA_PLANKS, 4)
                .addIngredient(ItemTags.ACACIA_LOGS)
                .setGroup("planks")
                .addCriterion("has_logs", this.hasItem(ItemTags.ACACIA_LOGS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ACACIA_PRESSURE_PLATE)
                .key('#', Blocks.ACACIA_PLANKS)
                .patternLine("##")
                .setGroup("wooden_pressure_plate")
                .addCriterion("has_planks", this.hasItem(Blocks.ACACIA_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ACACIA_SLAB, 6)
                .key('#', Blocks.ACACIA_PLANKS)
                .patternLine("###")
                .setGroup("wooden_slab")
                .addCriterion("has_planks", this.hasItem(Blocks.ACACIA_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ACACIA_STAIRS, 4)
                .key('#', Blocks.ACACIA_PLANKS)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .setGroup("wooden_stairs")
                .addCriterion("has_planks", this.hasItem(Blocks.ACACIA_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ACACIA_TRAPDOOR, 2)
                .key('#', Blocks.ACACIA_PLANKS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("wooden_trapdoor")
                .addCriterion("has_planks", this.hasItem(Blocks.ACACIA_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ACTIVATOR_RAIL, 6)
                .key('#', Blocks.REDSTONE_TORCH)
                .key('S', Items.STICK)
                .key('X', Items.IRON_INGOT)
                .patternLine("XSX")
                .patternLine("X#X")
                .patternLine("XSX")
                .addCriterion("has_rail", this.hasItem(Blocks.RAIL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.ANDESITE, 2)
                .addIngredient(Blocks.DIORITE)
                .addIngredient(Blocks.COBBLESTONE)
                .addCriterion("has_stone", this.hasItem(Blocks.DIORITE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ANVIL)
                .key('I', Blocks.IRON_BLOCK)
                .key('i', Items.IRON_INGOT)
                .patternLine("III")
                .patternLine(" i ")
                .patternLine("iii")
                .addCriterion("has_iron_block", this.hasItem(Blocks.IRON_BLOCK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.ARMOR_STAND)
                .key('/', Items.STICK)
                .key('_', Blocks.STONE_SLAB)
                .patternLine("///")
                .patternLine(" / ")
                .patternLine("/_/")
                .addCriterion("has_stone_slab", this.hasItem(Blocks.STONE_SLAB))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.ARROW, 4)
                .key('#', Items.STICK)
                .key('X', Items.FLINT)
                .key('Y', Items.FEATHER)
                .patternLine("X")
                .patternLine("#")
                .patternLine("Y")
                .addCriterion("has_feather", this.hasItem(Items.FEATHER))
                .addCriterion("has_flint", this.hasItem(Items.FLINT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BEACON)
                .key('S', Items.NETHER_STAR)
                .key('G', Blocks.GLASS)
                .key('O', Blocks.OBSIDIAN)
                .patternLine("GGG")
                .patternLine("GSG")
                .patternLine("OOO")
                .addCriterion("has_nether_star", this.hasItem(Items.NETHER_STAR))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.BEETROOT_SOUP)
                .key('B', Items.BOWL)
                .key('O', Items.BEETROOT)
                .patternLine("OOO")
                .patternLine("OOO")
                .patternLine(" B ")
                .addCriterion("has_beetroot", this.hasItem(Items.BEETROOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BIRCH_WOOD, 3)
                .key('#', Blocks.BIRCH_LOG)
                .patternLine("##")
                .patternLine("##")
                .setGroup("bark")
                .addCriterion("has_log", this.hasItem(Blocks.BIRCH_LOG))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.BIRCH_BOAT)
                .key('#', Blocks.BIRCH_PLANKS)
                .patternLine("# #")
                .patternLine("###")
                .setGroup("boat")
                .addCriterion("in_water", this.enteredBlock(Blocks.WATER))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.BIRCH_BUTTON)
                .addIngredient(Blocks.BIRCH_PLANKS)
                .setGroup("wooden_button")
                .addCriterion("has_planks", this.hasItem(Blocks.BIRCH_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BIRCH_DOOR, 3)
                .key('#', Blocks.BIRCH_PLANKS)
                .patternLine("##")
                .patternLine("##")
                .patternLine("##")
                .setGroup("wooden_door")
                .addCriterion("has_planks", this.hasItem(Blocks.BIRCH_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BIRCH_FENCE, 3)
                .key('#', Items.STICK)
                .key('W', Blocks.BIRCH_PLANKS)
                .patternLine("W#W")
                .patternLine("W#W")
                .setGroup("wooden_fence")
                .addCriterion("has_planks", this.hasItem(Blocks.BIRCH_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BIRCH_FENCE_GATE)
                .key('#', Items.STICK)
                .key('W', Blocks.BIRCH_PLANKS)
                .patternLine("#W#")
                .patternLine("#W#")
                .setGroup("wooden_fence_gate")
                .addCriterion("has_planks", this.hasItem(Blocks.BIRCH_PLANKS))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.BIRCH_PLANKS, 4)
                .addIngredient(ItemTags.BIRCH_LOGS)
                .setGroup("planks")
                .addCriterion("has_log", this.hasItem(ItemTags.BIRCH_LOGS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BIRCH_PRESSURE_PLATE)
                .key('#', Blocks.BIRCH_PLANKS)
                .patternLine("##")
                .setGroup("wooden_pressure_plate")
                .addCriterion("has_planks", this.hasItem(Blocks.BIRCH_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BIRCH_SLAB, 6)
                .key('#', Blocks.BIRCH_PLANKS)
                .patternLine("###")
                .setGroup("wooden_slab")
                .addCriterion("has_planks", this.hasItem(Blocks.BIRCH_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BIRCH_STAIRS, 4)
                .key('#', Blocks.BIRCH_PLANKS)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .setGroup("wooden_stairs")
                .addCriterion("has_planks", this.hasItem(Blocks.BIRCH_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BIRCH_TRAPDOOR, 2)
                .key('#', Blocks.BIRCH_PLANKS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("wooden_trapdoor")
                .addCriterion("has_planks", this.hasItem(Blocks.BIRCH_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.BLACK_BANNER)
                .key('#', Blocks.BLACK_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_black_wool", this.hasItem(Blocks.BLACK_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.BLACK_BED)
                .key('#', Blocks.BLACK_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_black_wool", this.hasItem(Blocks.BLACK_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLACK_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.INK_SAC)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "black_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.BLACK_CARPET, 3)
                .key('#', Blocks.BLACK_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_black_wool", this.hasItem(Blocks.BLACK_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.BLACK_CONCRETE_POWDER, 8)
                .addIngredient(Items.INK_SAC)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BLACK_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.INK_SAC)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BLACK_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.BLACK_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BLACK_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.INK_SAC)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.BLACK_WOOL)
                .addIngredient(Items.INK_SAC)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLAZE_POWDER, 2)
                .addIngredient(Items.BLAZE_ROD)
                .addCriterion("has_blaze_rod", this.hasItem(Items.BLAZE_ROD))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.BLUE_BANNER)
                .key('#', Blocks.BLUE_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_blue_wool", this.hasItem(Blocks.BLUE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.BLUE_BED)
                .key('#', Blocks.BLUE_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_blue_wool", this.hasItem(Blocks.BLUE_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLUE_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.LAPIS_LAZULI)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "blue_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.BLUE_CARPET, 3)
                .key('#', Blocks.BLUE_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_blue_wool", this.hasItem(Blocks.BLUE_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.BLUE_CONCRETE_POWDER, 8)
                .addIngredient(Items.LAPIS_LAZULI)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BLUE_ICE)
                .key('#', Blocks.PACKED_ICE)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_at_least_9_packed_ice",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Blocks.PACKED_ICE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BLUE_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.LAPIS_LAZULI)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BLUE_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.BLUE_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BLUE_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.LAPIS_LAZULI)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.BLUE_WOOL)
                .addIngredient(Items.LAPIS_LAZULI)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.OAK_BOAT)
                .key('#', Blocks.OAK_PLANKS)
                .patternLine("# #")
                .patternLine("###")
                .setGroup("boat")
                .addCriterion("in_water", this.enteredBlock(Blocks.WATER))
                .build(p_200404_1_);
        Item item = Items.BONE_MEAL;
        ShapedRecipeBuilder.shapedRecipe(Blocks.BONE_BLOCK)
                .key('X', Items.BONE_MEAL)
                .patternLine("XXX")
                .patternLine("XXX")
                .patternLine("XXX")
                .addCriterion("has_at_least_9_bonemeal", this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), item))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BONE_MEAL, 3)
                .addIngredient(Items.BONE)
                .setGroup("bonemeal")
                .addCriterion("has_bone", this.hasItem(Items.BONE))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BONE_MEAL, 9)
                .addIngredient(Blocks.BONE_BLOCK)
                .setGroup("bonemeal")
                .addCriterion("has_at_least_9_bonemeal",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.BONE_MEAL))
                .addCriterion("has_bone_block", this.hasItem(Blocks.BONE_BLOCK))
                .build(p_200404_1_, "bone_meal_from_bone_block");
        ShapelessRecipeBuilder.shapelessRecipe(Items.BOOK)
                .addIngredient(Items.PAPER, 3)
                .addIngredient(Items.LEATHER)
                .addCriterion("has_paper", this.hasItem(Items.PAPER))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BOOKSHELF)
                .key('#', ItemTags.PLANKS)
                .key('X', Items.BOOK)
                .patternLine("###")
                .patternLine("XXX")
                .patternLine("###")
                .addCriterion("has_book", this.hasItem(Items.BOOK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.BOW)
                .key('#', Items.STICK)
                .key('X', Items.STRING)
                .patternLine(" #X")
                .patternLine("# X")
                .patternLine(" #X")
                .addCriterion("has_string", this.hasItem(Items.STRING))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.BOWL, 4)
                .key('#', ItemTags.PLANKS)
                .patternLine("# #")
                .patternLine(" # ")
                .addCriterion("has_brown_mushroom", this.hasItem(Blocks.BROWN_MUSHROOM))
                .addCriterion("has_red_mushroom", this.hasItem(Blocks.RED_MUSHROOM))
                .addCriterion("has_mushroom_stew", this.hasItem(Items.MUSHROOM_STEW))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.BREAD)
                .key('#', Items.WHEAT)
                .patternLine("###")
                .addCriterion("has_wheat", this.hasItem(Items.WHEAT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BREWING_STAND)
                .key('B', Items.BLAZE_ROD)
                .key('#', Blocks.COBBLESTONE)
                .patternLine(" B ")
                .patternLine("###")
                .addCriterion("has_blaze_rod", this.hasItem(Items.BLAZE_ROD))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BRICKS)
                .key('#', Items.BRICK)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_brick", this.hasItem(Items.BRICK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BRICK_SLAB, 6)
                .key('#', Blocks.BRICKS)
                .patternLine("###")
                .addCriterion("has_brick_block", this.hasItem(Blocks.BRICKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BRICK_STAIRS, 4)
                .key('#', Blocks.BRICKS)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .addCriterion("has_brick_block", this.hasItem(Blocks.BRICKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.BROWN_BANNER)
                .key('#', Blocks.BROWN_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_brown_wool", this.hasItem(Blocks.BROWN_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.BROWN_BED)
                .key('#', Blocks.BROWN_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_brown_wool", this.hasItem(Blocks.BROWN_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BROWN_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.COCOA_BEANS)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "brown_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.BROWN_CARPET, 3)
                .key('#', Blocks.BROWN_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_brown_wool", this.hasItem(Blocks.BROWN_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.BROWN_CONCRETE_POWDER, 8)
                .addIngredient(Items.COCOA_BEANS)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BROWN_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.COCOA_BEANS)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BROWN_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.BROWN_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BROWN_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.COCOA_BEANS)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.BROWN_WOOL)
                .addIngredient(Items.COCOA_BEANS)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.BUCKET)
                .key('#', Items.IRON_INGOT)
                .patternLine("# #")
                .patternLine(" # ")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CAKE)
                .key('A', Items.MILK_BUCKET)
                .key('B', Items.SUGAR)
                .key('C', Items.WHEAT)
                .key('E', Items.EGG)
                .patternLine("AAA")
                .patternLine("BEB")
                .patternLine("CCC")
                .addCriterion("has_egg", this.hasItem(Items.EGG))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.CARROT_ON_A_STICK)
                .key('#', Items.FISHING_ROD)
                .key('X', Items.CARROT)
                .patternLine("# ")
                .patternLine(" X")
                .addCriterion("has_carrot", this.hasItem(Items.CARROT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CAULDRON)
                .key('#', Items.IRON_INGOT)
                .patternLine("# #")
                .patternLine("# #")
                .patternLine("###")
                .addCriterion("has_water_bucket", this.hasItem(Items.WATER_BUCKET))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CHEST)
                .key('#', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("# #")
                .patternLine("###")
                .addCriterion("has_lots_of_items",
                        new InventoryChangeTrigger.Instance(MinMaxBounds.IntBound.func_211340_b(10),
                                MinMaxBounds.IntBound.UNBOUNDED,
                                MinMaxBounds.IntBound.UNBOUNDED,
                                new ItemPredicate[0]))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.CHEST_MINECART)
                .key('A', Blocks.CHEST)
                .key('B', Items.MINECART)
                .patternLine("A")
                .patternLine("B")
                .addCriterion("has_minecart", this.hasItem(Items.MINECART))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CHISELED_QUARTZ_BLOCK)
                .key('#', Blocks.QUARTZ_SLAB)
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_chiseled_quartz_block", this.hasItem(Blocks.CHISELED_QUARTZ_BLOCK))
                .addCriterion("has_quartz_block", this.hasItem(Blocks.QUARTZ_BLOCK))
                .addCriterion("has_quartz_pillar", this.hasItem(Blocks.QUARTZ_PILLAR))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CHISELED_STONE_BRICKS)
                .key('#', Blocks.STONE_BRICK_SLAB)
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_stone_bricks", this.hasItem(ItemTags.STONE_BRICKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CLAY)
                .key('#', Items.CLAY_BALL)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_clay_ball", this.hasItem(Items.CLAY_BALL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.CLOCK)
                .key('#', Items.GOLD_INGOT)
                .key('X', Items.REDSTONE)
                .patternLine(" # ")
                .patternLine("#X#")
                .patternLine(" # ")
                .addCriterion("has_redstone", this.hasItem(Items.REDSTONE))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.COAL, 9)
                .addIngredient(Blocks.COAL_BLOCK)
                .addCriterion("has_at_least_9_coal", this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.COAL))
                .addCriterion("has_coal_block", this.hasItem(Blocks.COAL_BLOCK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.COAL_BLOCK)
                .key('#', Items.COAL)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_at_least_9_coal", this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.COAL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.COARSE_DIRT, 4)
                .key('D', Blocks.DIRT)
                .key('G', Blocks.GRAVEL)
                .patternLine("DG")
                .patternLine("GD")
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.COBBLESTONE_SLAB, 6)
                .key('#', Blocks.COBBLESTONE)
                .patternLine("###")
                .addCriterion("has_cobblestone", this.hasItem(Blocks.COBBLESTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.COBBLESTONE_WALL, 6)
                .key('#', Blocks.COBBLESTONE)
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_cobblestone", this.hasItem(Blocks.COBBLESTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.COMPARATOR)
                .key('#', Blocks.REDSTONE_TORCH)
                .key('X', Items.QUARTZ)
                .key('I', Blocks.STONE)
                .patternLine(" # ")
                .patternLine("#X#")
                .patternLine("III")
                .addCriterion("has_quartz", this.hasItem(Items.QUARTZ))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.COMPASS)
                .key('#', Items.IRON_INGOT)
                .key('X', Items.REDSTONE)
                .patternLine(" # ")
                .patternLine("#X#")
                .patternLine(" # ")
                .addCriterion("has_redstone", this.hasItem(Items.REDSTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.COOKIE, 8)
                .key('#', Items.WHEAT)
                .key('X', Items.COCOA_BEANS)
                .patternLine("#X#")
                .addCriterion("has_cocoa", this.hasItem(Items.COCOA_BEANS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CRAFTING_TABLE)
                .key('#', ItemTags.PLANKS)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_planks", this.hasItem(ItemTags.PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CHISELED_RED_SANDSTONE)
                .key('#', Blocks.RED_SANDSTONE_SLAB)
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_red_sandstone", this.hasItem(Blocks.RED_SANDSTONE))
                .addCriterion("has_chiseled_red_sandstone", this.hasItem(Blocks.CHISELED_RED_SANDSTONE))
                .addCriterion("has_cut_red_sandstone", this.hasItem(Blocks.CUT_RED_SANDSTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CHISELED_SANDSTONE)
                .key('#', Blocks.SANDSTONE_SLAB)
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_stone_slab", this.hasItem(Blocks.SANDSTONE_SLAB))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.CYAN_BANNER)
                .key('#', Blocks.CYAN_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_cyan_wool", this.hasItem(Blocks.CYAN_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.CYAN_BED)
                .key('#', Blocks.CYAN_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_cyan_wool", this.hasItem(Blocks.CYAN_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.CYAN_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.CYAN_DYE)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "cyan_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.CYAN_CARPET, 3)
                .key('#', Blocks.CYAN_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_cyan_wool", this.hasItem(Blocks.CYAN_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.CYAN_CONCRETE_POWDER, 8)
                .addIngredient(Items.CYAN_DYE)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.CYAN_DYE, 2)
                .addIngredient(Items.LAPIS_LAZULI)
                .addIngredient(Items.CACTUS_GREEN)
                .addCriterion("has_green_dye", this.hasItem(Items.CACTUS_GREEN))
                .addCriterion("has_lapis", this.hasItem(Items.LAPIS_LAZULI))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CYAN_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.CYAN_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CYAN_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.CYAN_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CYAN_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.CYAN_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.CYAN_WOOL)
                .addIngredient(Items.CYAN_DYE)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_OAK_WOOD, 3)
                .key('#', Blocks.DARK_OAK_LOG)
                .patternLine("##")
                .patternLine("##")
                .setGroup("bark")
                .addCriterion("has_log", this.hasItem(Blocks.DARK_OAK_LOG))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.DARK_OAK_BOAT)
                .key('#', Blocks.DARK_OAK_PLANKS)
                .patternLine("# #")
                .patternLine("###")
                .setGroup("boat")
                .addCriterion("in_water", this.enteredBlock(Blocks.WATER))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.DARK_OAK_BUTTON)
                .addIngredient(Blocks.DARK_OAK_PLANKS)
                .setGroup("wooden_button")
                .addCriterion("has_planks", this.hasItem(Blocks.DARK_OAK_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_OAK_DOOR, 3)
                .key('#', Blocks.DARK_OAK_PLANKS)
                .patternLine("##")
                .patternLine("##")
                .patternLine("##")
                .setGroup("wooden_door")
                .addCriterion("has_planks", this.hasItem(Blocks.DARK_OAK_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_OAK_FENCE, 3)
                .key('#', Items.STICK)
                .key('W', Blocks.DARK_OAK_PLANKS)
                .patternLine("W#W")
                .patternLine("W#W")
                .setGroup("wooden_fence")
                .addCriterion("has_planks", this.hasItem(Blocks.DARK_OAK_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_OAK_FENCE_GATE)
                .key('#', Items.STICK)
                .key('W', Blocks.DARK_OAK_PLANKS)
                .patternLine("#W#")
                .patternLine("#W#")
                .setGroup("wooden_fence_gate")
                .addCriterion("has_planks", this.hasItem(Blocks.DARK_OAK_PLANKS))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.DARK_OAK_PLANKS, 4)
                .addIngredient(ItemTags.DARK_OAK_LOGS)
                .setGroup("planks")
                .addCriterion("has_logs", this.hasItem(ItemTags.DARK_OAK_LOGS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_OAK_PRESSURE_PLATE)
                .key('#', Blocks.DARK_OAK_PLANKS)
                .patternLine("##")
                .setGroup("wooden_pressure_plate")
                .addCriterion("has_planks", this.hasItem(Blocks.DARK_OAK_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_OAK_SLAB, 6)
                .key('#', Blocks.DARK_OAK_PLANKS)
                .patternLine("###")
                .setGroup("wooden_slab")
                .addCriterion("has_planks", this.hasItem(Blocks.DARK_OAK_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_OAK_STAIRS, 4)
                .key('#', Blocks.DARK_OAK_PLANKS)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .setGroup("wooden_stairs")
                .addCriterion("has_planks", this.hasItem(Blocks.DARK_OAK_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_OAK_TRAPDOOR, 2)
                .key('#', Blocks.DARK_OAK_PLANKS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("wooden_trapdoor")
                .addCriterion("has_planks", this.hasItem(Blocks.DARK_OAK_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_PRISMARINE)
                .key('S', Items.PRISMARINE_SHARD)
                .key('I', Items.INK_SAC)
                .patternLine("SSS")
                .patternLine("SIS")
                .patternLine("SSS")
                .addCriterion("has_prismarine_shard", this.hasItem(Items.PRISMARINE_SHARD))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_STAIRS, 4)
                .key('#', Blocks.PRISMARINE)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .addCriterion("has_prismarine", this.hasItem(Blocks.PRISMARINE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_BRICK_STAIRS, 4)
                .key('#', Blocks.PRISMARINE_BRICKS)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .addCriterion("has_prismarine_bricks", this.hasItem(Blocks.PRISMARINE_BRICKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_PRISMARINE_STAIRS, 4)
                .key('#', Blocks.DARK_PRISMARINE)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .addCriterion("has_dark_prismarine", this.hasItem(Blocks.DARK_PRISMARINE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DAYLIGHT_DETECTOR)
                .key('Q', Items.QUARTZ)
                .key('G', Blocks.GLASS)
                .key('W', Ingredient.fromTag(ItemTags.WOODEN_SLABS))
                .patternLine("GGG")
                .patternLine("QQQ")
                .patternLine("WWW")
                .addCriterion("has_quartz", this.hasItem(Items.QUARTZ))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DETECTOR_RAIL, 6)
                .key('R', Items.REDSTONE)
                .key('#', Blocks.STONE_PRESSURE_PLATE)
                .key('X', Items.IRON_INGOT)
                .patternLine("X X")
                .patternLine("X#X")
                .patternLine("XRX")
                .addCriterion("has_rail", this.hasItem(Blocks.RAIL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.DIAMOND, 9)
                .addIngredient(Blocks.DIAMOND_BLOCK)
                .addCriterion("has_at_least_9_diamond",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.DIAMOND))
                .addCriterion("has_diamond_block", this.hasItem(Blocks.DIAMOND_BLOCK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DIAMOND_BLOCK)
                .key('#', Items.DIAMOND)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_at_least_9_diamond",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.DIAMOND))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_BOOTS)
                .key('X', Items.DIAMOND)
                .patternLine("X X")
                .patternLine("X X")
                .addCriterion("has_diamond", this.hasItem(Items.DIAMOND))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_CHESTPLATE)
                .key('X', Items.DIAMOND)
                .patternLine("X X")
                .patternLine("XXX")
                .patternLine("XXX")
                .addCriterion("has_diamond", this.hasItem(Items.DIAMOND))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_HELMET)
                .key('X', Items.DIAMOND)
                .patternLine("XXX")
                .patternLine("X X")
                .addCriterion("has_diamond", this.hasItem(Items.DIAMOND))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_LEGGINGS)
                .key('X', Items.DIAMOND)
                .patternLine("XXX")
                .patternLine("X X")
                .patternLine("X X")
                .addCriterion("has_diamond", this.hasItem(Items.DIAMOND))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DIORITE, 2)
                .key('Q', Items.QUARTZ)
                .key('C', Blocks.COBBLESTONE)
                .patternLine("CQ")
                .patternLine("QC")
                .addCriterion("has_quartz", this.hasItem(Items.QUARTZ))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DISPENSER)
                .key('R', Items.REDSTONE)
                .key('#', Blocks.COBBLESTONE)
                .key('X', Items.BOW)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("#R#")
                .addCriterion("has_bow", this.hasItem(Items.BOW))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DROPPER)
                .key('R', Items.REDSTONE)
                .key('#', Blocks.COBBLESTONE)
                .patternLine("###")
                .patternLine("# #")
                .patternLine("#R#")
                .addCriterion("has_redstone", this.hasItem(Items.REDSTONE))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.EMERALD, 9)
                .addIngredient(Blocks.EMERALD_BLOCK)
                .addCriterion("has_at_least_9_emerald",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.EMERALD))
                .addCriterion("has_emerald_block", this.hasItem(Blocks.EMERALD_BLOCK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.EMERALD_BLOCK)
                .key('#', Items.EMERALD)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_at_least_9_emerald",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.EMERALD))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ENCHANTING_TABLE)
                .key('B', Items.BOOK)
                .key('#', Blocks.OBSIDIAN)
                .key('D', Items.DIAMOND)
                .patternLine(" B ")
                .patternLine("D#D")
                .patternLine("###")
                .addCriterion("has_obsidian", this.hasItem(Blocks.OBSIDIAN))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ENDER_CHEST)
                .key('#', Blocks.OBSIDIAN)
                .key('E', Items.ENDER_EYE)
                .patternLine("###")
                .patternLine("#E#")
                .patternLine("###")
                .addCriterion("has_ender_eye", this.hasItem(Items.ENDER_EYE))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.ENDER_EYE)
                .addIngredient(Items.ENDER_PEARL)
                .addIngredient(Items.BLAZE_POWDER)
                .addCriterion("has_blaze_powder", this.hasItem(Items.BLAZE_POWDER))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.END_STONE_BRICKS, 4)
                .key('#', Blocks.END_STONE)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_end_stone", this.hasItem(Blocks.END_STONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.END_CRYSTAL)
                .key('T', Items.GHAST_TEAR)
                .key('E', Items.ENDER_EYE)
                .key('G', Blocks.GLASS)
                .patternLine("GGG")
                .patternLine("GEG")
                .patternLine("GTG")
                .addCriterion("has_ender_eye", this.hasItem(Items.ENDER_EYE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.END_ROD, 4)
                .key('#', Items.POPPED_CHORUS_FRUIT)
                .key('/', Items.BLAZE_ROD)
                .patternLine("/")
                .patternLine("#")
                .addCriterion("has_chorus_fruit_popped", this.hasItem(Items.POPPED_CHORUS_FRUIT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.OAK_FENCE, 3)
                .key('#', Items.STICK)
                .key('W', Blocks.OAK_PLANKS)
                .patternLine("W#W")
                .patternLine("W#W")
                .setGroup("wooden_fence")
                .addCriterion("has_planks", this.hasItem(Blocks.OAK_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.OAK_FENCE_GATE)
                .key('#', Items.STICK)
                .key('W', Blocks.OAK_PLANKS)
                .patternLine("#W#")
                .patternLine("#W#")
                .setGroup("wooden_fence_gate")
                .addCriterion("has_planks", this.hasItem(Blocks.OAK_PLANKS))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.FERMENTED_SPIDER_EYE)
                .addIngredient(Items.SPIDER_EYE)
                .addIngredient(Blocks.BROWN_MUSHROOM)
                .addIngredient(Items.SUGAR)
                .addCriterion("has_spider_eye", this.hasItem(Items.SPIDER_EYE))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.FIRE_CHARGE, 3)
                .addIngredient(Items.GUNPOWDER)
                .addIngredient(Items.BLAZE_POWDER)
                .addIngredient(Ingredient.fromItems(Items.COAL, Items.CHARCOAL))
                .addCriterion("has_blaze_powder", this.hasItem(Items.BLAZE_POWDER))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.FISHING_ROD)
                .key('#', Items.STICK)
                .key('X', Items.STRING)
                .patternLine("  #")
                .patternLine(" #X")
                .patternLine("# X")
                .addCriterion("has_string", this.hasItem(Items.STRING))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.FLINT_AND_STEEL)
                .addIngredient(Items.IRON_INGOT)
                .addIngredient(Items.FLINT)
                .addCriterion("has_flint", this.hasItem(Items.FLINT))
                .addCriterion("has_obsidian", this.hasItem(Blocks.OBSIDIAN))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.FLOWER_POT)
                .key('#', Items.BRICK)
                .patternLine("# #")
                .patternLine(" # ")
                .addCriterion("has_brick", this.hasItem(Items.BRICK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.FURNACE)
                .key('#', Blocks.COBBLESTONE)
                .patternLine("###")
                .patternLine("# #")
                .patternLine("###")
                .addCriterion("has_cobblestone", this.hasItem(Blocks.COBBLESTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.FURNACE_MINECART)
                .key('A', Blocks.FURNACE)
                .key('B', Items.MINECART)
                .patternLine("A")
                .patternLine("B")
                .addCriterion("has_minecart", this.hasItem(Items.MINECART))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GLASS_BOTTLE, 3)
                .key('#', Blocks.GLASS)
                .patternLine("# #")
                .patternLine(" # ")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GLASS_PANE, 16)
                .key('#', Blocks.GLASS)
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GLOWSTONE)
                .key('#', Items.GLOWSTONE_DUST)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_glowstone_dust", this.hasItem(Items.GLOWSTONE_DUST))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_APPLE)
                .key('#', Items.GOLD_INGOT)
                .key('X', Items.APPLE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .addCriterion("has_gold_ingot", this.hasItem(Items.GOLD_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_AXE)
                .key('#', Items.STICK)
                .key('X', Items.GOLD_INGOT)
                .patternLine("XX")
                .patternLine("X#")
                .patternLine(" #")
                .addCriterion("has_gold_ingot", this.hasItem(Items.GOLD_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_BOOTS)
                .key('X', Items.GOLD_INGOT)
                .patternLine("X X")
                .patternLine("X X")
                .addCriterion("has_gold_ingot", this.hasItem(Items.GOLD_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_CARROT)
                .key('#', Items.GOLD_NUGGET)
                .key('X', Items.CARROT)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .addCriterion("has_gold_nugget", this.hasItem(Items.GOLD_NUGGET))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_CHESTPLATE)
                .key('X', Items.GOLD_INGOT)
                .patternLine("X X")
                .patternLine("XXX")
                .patternLine("XXX")
                .addCriterion("has_gold_ingot", this.hasItem(Items.GOLD_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_HELMET)
                .key('X', Items.GOLD_INGOT)
                .patternLine("XXX")
                .patternLine("X X")
                .addCriterion("has_gold_ingot", this.hasItem(Items.GOLD_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_HOE)
                .key('#', Items.STICK)
                .key('X', Items.GOLD_INGOT)
                .patternLine("XX")
                .patternLine(" #")
                .patternLine(" #")
                .addCriterion("has_gold_ingot", this.hasItem(Items.GOLD_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_LEGGINGS)
                .key('X', Items.GOLD_INGOT)
                .patternLine("XXX")
                .patternLine("X X")
                .patternLine("X X")
                .addCriterion("has_gold_ingot", this.hasItem(Items.GOLD_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_PICKAXE)
                .key('#', Items.STICK)
                .key('X', Items.GOLD_INGOT)
                .patternLine("XXX")
                .patternLine(" # ")
                .patternLine(" # ")
                .addCriterion("has_gold_ingot", this.hasItem(Items.GOLD_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POWERED_RAIL, 6)
                .key('R', Items.REDSTONE)
                .key('#', Items.STICK)
                .key('X', Items.GOLD_INGOT)
                .patternLine("X X")
                .patternLine("X#X")
                .patternLine("XRX")
                .addCriterion("has_rail", this.hasItem(Blocks.RAIL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_SHOVEL)
                .key('#', Items.STICK)
                .key('X', Items.GOLD_INGOT)
                .patternLine("X")
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_gold_ingot", this.hasItem(Items.GOLD_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_SWORD)
                .key('#', Items.STICK)
                .key('X', Items.GOLD_INGOT)
                .patternLine("X")
                .patternLine("X")
                .patternLine("#")
                .addCriterion("has_gold_ingot", this.hasItem(Items.GOLD_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GOLD_BLOCK)
                .key('#', Items.GOLD_INGOT)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_at_least_9_gold_ingot",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.GOLD_INGOT))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.GOLD_INGOT, 9)
                .addIngredient(Blocks.GOLD_BLOCK)
                .setGroup("gold_ingot")
                .addCriterion("has_at_least_9_gold_ingot",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.GOLD_INGOT))
                .addCriterion("has_gold_block", this.hasItem(Blocks.GOLD_BLOCK))
                .build(p_200404_1_, "gold_ingot_from_gold_block");
        ShapedRecipeBuilder.shapedRecipe(Items.GOLD_INGOT)
                .key('#', Items.GOLD_NUGGET)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .setGroup("gold_ingot")
                .addCriterion("has_at_least_9_gold_nugget",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.GOLD_NUGGET))
                .build(p_200404_1_, "gold_ingot_from_nuggets");
        ShapelessRecipeBuilder.shapelessRecipe(Items.GOLD_NUGGET, 9)
                .addIngredient(Items.GOLD_INGOT)
                .addCriterion("has_at_least_9_gold_nugget",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.GOLD_NUGGET))
                .addCriterion("has_gold_ingot", this.hasItem(Items.GOLD_INGOT))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.GRANITE)
                .addIngredient(Blocks.DIORITE)
                .addIngredient(Items.QUARTZ)
                .addCriterion("has_quartz", this.hasItem(Items.QUARTZ))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GRAY_BANNER)
                .key('#', Blocks.GRAY_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_gray_wool", this.hasItem(Blocks.GRAY_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GRAY_BED)
                .key('#', Blocks.GRAY_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_gray_wool", this.hasItem(Blocks.GRAY_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.GRAY_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.GRAY_DYE)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "gray_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.GRAY_CARPET, 3)
                .key('#', Blocks.GRAY_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_gray_wool", this.hasItem(Blocks.GRAY_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.GRAY_CONCRETE_POWDER, 8)
                .addIngredient(Items.GRAY_DYE)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.GRAY_DYE, 2)
                .addIngredient(Items.INK_SAC)
                .addIngredient(Items.BONE_MEAL)
                .addCriterion("has_bonemeal", this.hasItem(Items.BONE_MEAL))
                .addCriterion("has_black_dye", this.hasItem(Items.INK_SAC))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GRAY_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.GRAY_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GRAY_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.GRAY_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GRAY_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.GRAY_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.GRAY_WOOL)
                .addIngredient(Items.GRAY_DYE)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GREEN_BANNER)
                .key('#', Blocks.GREEN_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_green_wool", this.hasItem(Blocks.GREEN_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GREEN_BED)
                .key('#', Blocks.GREEN_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_green_wool", this.hasItem(Blocks.GREEN_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.GREEN_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.CACTUS_GREEN)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "green_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.GREEN_CARPET, 3)
                .key('#', Blocks.GREEN_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_green_wool", this.hasItem(Blocks.GREEN_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.GREEN_CONCRETE_POWDER, 8)
                .addIngredient(Items.CACTUS_GREEN)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GREEN_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.CACTUS_GREEN)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GREEN_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.GREEN_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GREEN_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.CACTUS_GREEN)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.GREEN_WOOL)
                .addIngredient(Items.CACTUS_GREEN)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.HAY_BLOCK)
                .key('#', Items.WHEAT)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_at_least_9_wheat", this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.WHEAT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .key('#', Items.IRON_INGOT)
                .patternLine("##")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.HOPPER)
                .key('C', Blocks.CHEST)
                .key('I', Items.IRON_INGOT)
                .patternLine("I I")
                .patternLine("ICI")
                .patternLine(" I ")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.HOPPER_MINECART)
                .key('A', Blocks.HOPPER)
                .key('B', Items.MINECART)
                .patternLine("A")
                .patternLine("B")
                .addCriterion("has_minecart", this.hasItem(Items.MINECART))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_AXE)
                .key('#', Items.STICK)
                .key('X', Items.IRON_INGOT)
                .patternLine("XX")
                .patternLine("X#")
                .patternLine(" #")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.IRON_BARS, 16)
                .key('#', Items.IRON_INGOT)
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.IRON_BLOCK)
                .key('#', Items.IRON_INGOT)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_at_least_9_iron_ingot",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_BOOTS)
                .key('X', Items.IRON_INGOT)
                .patternLine("X X")
                .patternLine("X X")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_CHESTPLATE)
                .key('X', Items.IRON_INGOT)
                .patternLine("X X")
                .patternLine("XXX")
                .patternLine("XXX")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.IRON_DOOR, 3)
                .key('#', Items.IRON_INGOT)
                .patternLine("##")
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_HELMET)
                .key('X', Items.IRON_INGOT)
                .patternLine("XXX")
                .patternLine("X X")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_HOE)
                .key('#', Items.STICK)
                .key('X', Items.IRON_INGOT)
                .patternLine("XX")
                .patternLine(" #")
                .patternLine(" #")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.IRON_INGOT, 9)
                .addIngredient(Blocks.IRON_BLOCK)
                .setGroup("iron_ingot")
                .addCriterion("has_at_least_9_iron_ingot",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.IRON_INGOT))
                .addCriterion("has_iron_block", this.hasItem(Blocks.IRON_BLOCK))
                .build(p_200404_1_, "iron_ingot_from_iron_block");
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_INGOT)
                .key('#', Items.IRON_NUGGET)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .setGroup("iron_ingot")
                .addCriterion("has_at_least_9_iron_nugget",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.IRON_NUGGET))
                .build(p_200404_1_, "iron_ingot_from_nuggets");
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_LEGGINGS)
                .key('X', Items.IRON_INGOT)
                .patternLine("XXX")
                .patternLine("X X")
                .patternLine("X X")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.IRON_NUGGET, 9)
                .addIngredient(Items.IRON_INGOT)
                .addCriterion("has_at_least_9_iron_nugget",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.IRON_NUGGET))
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_PICKAXE)
                .key('#', Items.STICK)
                .key('X', Items.IRON_INGOT)
                .patternLine("XXX")
                .patternLine(" # ")
                .patternLine(" # ")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_SHOVEL)
                .key('#', Items.STICK)
                .key('X', Items.IRON_INGOT)
                .patternLine("X")
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_SWORD)
                .key('#', Items.STICK)
                .key('X', Items.IRON_INGOT)
                .patternLine("X")
                .patternLine("X")
                .patternLine("#")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.IRON_TRAPDOOR)
                .key('#', Items.IRON_INGOT)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.ITEM_FRAME)
                .key('#', Items.STICK)
                .key('X', Items.LEATHER)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .addCriterion("has_leather", this.hasItem(Items.LEATHER))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.JUKEBOX)
                .key('#', ItemTags.PLANKS)
                .key('X', Items.DIAMOND)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .addCriterion("has_diamond", this.hasItem(Items.DIAMOND))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.JUNGLE_WOOD, 3)
                .key('#', Blocks.JUNGLE_LOG)
                .patternLine("##")
                .patternLine("##")
                .setGroup("bark")
                .addCriterion("has_log", this.hasItem(Blocks.JUNGLE_LOG))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.JUNGLE_BOAT)
                .key('#', Blocks.JUNGLE_PLANKS)
                .patternLine("# #")
                .patternLine("###")
                .setGroup("boat")
                .addCriterion("in_water", this.enteredBlock(Blocks.WATER))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.JUNGLE_BUTTON)
                .addIngredient(Blocks.JUNGLE_PLANKS)
                .setGroup("wooden_button")
                .addCriterion("has_planks", this.hasItem(Blocks.JUNGLE_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.JUNGLE_DOOR, 3)
                .key('#', Blocks.JUNGLE_PLANKS)
                .patternLine("##")
                .patternLine("##")
                .patternLine("##")
                .setGroup("wooden_door")
                .addCriterion("has_planks", this.hasItem(Blocks.JUNGLE_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.JUNGLE_FENCE, 3)
                .key('#', Items.STICK)
                .key('W', Blocks.JUNGLE_PLANKS)
                .patternLine("W#W")
                .patternLine("W#W")
                .setGroup("wooden_fence")
                .addCriterion("has_planks", this.hasItem(Blocks.JUNGLE_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.JUNGLE_FENCE_GATE)
                .key('#', Items.STICK)
                .key('W', Blocks.JUNGLE_PLANKS)
                .patternLine("#W#")
                .patternLine("#W#")
                .setGroup("wooden_fence_gate")
                .addCriterion("has_planks", this.hasItem(Blocks.JUNGLE_PLANKS))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.JUNGLE_PLANKS, 4)
                .addIngredient(ItemTags.JUNGLE_LOGS)
                .setGroup("planks")
                .addCriterion("has_log", this.hasItem(ItemTags.JUNGLE_LOGS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.JUNGLE_PRESSURE_PLATE)
                .key('#', Blocks.JUNGLE_PLANKS)
                .patternLine("##")
                .setGroup("wooden_pressure_plate")
                .addCriterion("has_planks", this.hasItem(Blocks.JUNGLE_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.JUNGLE_SLAB, 6)
                .key('#', Blocks.JUNGLE_PLANKS)
                .patternLine("###")
                .setGroup("wooden_slab")
                .addCriterion("has_planks", this.hasItem(Blocks.JUNGLE_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.JUNGLE_STAIRS, 4)
                .key('#', Blocks.JUNGLE_PLANKS)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .setGroup("wooden_stairs")
                .addCriterion("has_planks", this.hasItem(Blocks.JUNGLE_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.JUNGLE_TRAPDOOR, 2)
                .key('#', Blocks.JUNGLE_PLANKS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("wooden_trapdoor")
                .addCriterion("has_planks", this.hasItem(Blocks.JUNGLE_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LADDER, 3)
                .key('#', Items.STICK)
                .patternLine("# #")
                .patternLine("###")
                .patternLine("# #")
                .addCriterion("has_stick", this.hasItem(Items.STICK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LAPIS_BLOCK)
                .key('#', Items.LAPIS_LAZULI)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_at_least_9_lapis",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.LAPIS_LAZULI))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.LAPIS_LAZULI, 9)
                .addIngredient(Blocks.LAPIS_BLOCK)
                .addCriterion("has_at_least_9_lapis",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.LAPIS_LAZULI))
                .addCriterion("has_lapis_block", this.hasItem(Blocks.LAPIS_BLOCK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.LEAD, 2)
                .key('~', Items.STRING)
                .key('O', Items.SLIME_BALL)
                .patternLine("~~ ")
                .patternLine("~O ")
                .patternLine("  ~")
                .addCriterion("has_slime_ball", this.hasItem(Items.SLIME_BALL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.LEATHER)
                .key('#', Items.RABBIT_HIDE)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_rabbit_hide", this.hasItem(Items.RABBIT_HIDE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.LEATHER_BOOTS)
                .key('X', Items.LEATHER)
                .patternLine("X X")
                .patternLine("X X")
                .addCriterion("has_leather", this.hasItem(Items.LEATHER))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.LEATHER_CHESTPLATE)
                .key('X', Items.LEATHER)
                .patternLine("X X")
                .patternLine("XXX")
                .patternLine("XXX")
                .addCriterion("has_leather", this.hasItem(Items.LEATHER))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.LEATHER_HELMET)
                .key('X', Items.LEATHER)
                .patternLine("XXX")
                .patternLine("X X")
                .addCriterion("has_leather", this.hasItem(Items.LEATHER))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.LEATHER_LEGGINGS)
                .key('X', Items.LEATHER)
                .patternLine("XXX")
                .patternLine("X X")
                .patternLine("X X")
                .addCriterion("has_leather", this.hasItem(Items.LEATHER))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LEVER)
                .key('#', Blocks.COBBLESTONE)
                .key('X', Items.STICK)
                .patternLine("X")
                .patternLine("#")
                .addCriterion("has_cobblestone", this.hasItem(Blocks.COBBLESTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.LIGHT_BLUE_BANNER)
                .key('#', Blocks.LIGHT_BLUE_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_light_blue_wool", this.hasItem(Blocks.LIGHT_BLUE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.LIGHT_BLUE_BED)
                .key('#', Blocks.LIGHT_BLUE_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_light_blue_wool", this.hasItem(Blocks.LIGHT_BLUE_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_BLUE_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.LIGHT_BLUE_DYE)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "light_blue_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.LIGHT_BLUE_CARPET, 3)
                .key('#', Blocks.LIGHT_BLUE_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_light_blue_wool", this.hasItem(Blocks.LIGHT_BLUE_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.LIGHT_BLUE_CONCRETE_POWDER, 8)
                .addIngredient(Items.LIGHT_BLUE_DYE)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_BLUE_DYE)
                .addIngredient(Blocks.BLUE_ORCHID)
                .setGroup("light_blue_dye")
                .addCriterion("has_red_flower", this.hasItem(Blocks.BLUE_ORCHID))
                .build(p_200404_1_, "light_blue_dye_from_blue_orchid");
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_BLUE_DYE, 2)
                .addIngredient(Items.LAPIS_LAZULI)
                .addIngredient(Items.BONE_MEAL)
                .setGroup("light_blue_dye")
                .addCriterion("has_lapis", this.hasItem(Items.LAPIS_LAZULI))
                .addCriterion("has_bonemeal", this.hasItem(Items.BONE_MEAL))
                .build(p_200404_1_, "light_blue_dye_from_lapis_bonemeal");
        ShapedRecipeBuilder.shapedRecipe(Blocks.LIGHT_BLUE_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.LIGHT_BLUE_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.LIGHT_BLUE_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LIGHT_BLUE_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.LIGHT_BLUE_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.LIGHT_BLUE_WOOL)
                .addIngredient(Items.LIGHT_BLUE_DYE)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.LIGHT_GRAY_BANNER)
                .key('#', Blocks.LIGHT_GRAY_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_light_gray_wool", this.hasItem(Blocks.LIGHT_GRAY_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.LIGHT_GRAY_BED)
                .key('#', Blocks.LIGHT_GRAY_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_light_gray_wool", this.hasItem(Blocks.LIGHT_GRAY_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.LIGHT_GRAY_DYE)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "light_gray_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.LIGHT_GRAY_CARPET, 3)
                .key('#', Blocks.LIGHT_GRAY_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_light_gray_wool", this.hasItem(Blocks.LIGHT_GRAY_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.LIGHT_GRAY_CONCRETE_POWDER, 8)
                .addIngredient(Items.LIGHT_GRAY_DYE)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE)
                .addIngredient(Blocks.AZURE_BLUET)
                .setGroup("light_gray_dye")
                .addCriterion("has_red_flower", this.hasItem(Blocks.AZURE_BLUET))
                .build(p_200404_1_, "light_gray_dye_from_azure_bluet");
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE, 2)
                .addIngredient(Items.GRAY_DYE)
                .addIngredient(Items.BONE_MEAL)
                .setGroup("light_gray_dye")
                .addCriterion("has_gray_dye", this.hasItem(Items.GRAY_DYE))
                .addCriterion("has_bonemeal", this.hasItem(Items.BONE_MEAL))
                .build(p_200404_1_, "light_gray_dye_from_gray_bonemeal");
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE, 3)
                .addIngredient(Items.INK_SAC)
                .addIngredient(Items.BONE_MEAL, 2)
                .setGroup("light_gray_dye")
                .addCriterion("has_bonemeal", this.hasItem(Items.BONE_MEAL))
                .addCriterion("has_black_dye", this.hasItem(Items.INK_SAC))
                .build(p_200404_1_, "light_gray_dye_from_ink_bonemeal");
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE)
                .addIngredient(Blocks.OXEYE_DAISY)
                .setGroup("light_gray_dye")
                .addCriterion("has_red_flower", this.hasItem(Blocks.OXEYE_DAISY))
                .build(p_200404_1_, "light_gray_dye_from_oxeye_daisy");
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE)
                .addIngredient(Blocks.WHITE_TULIP)
                .setGroup("light_gray_dye")
                .addCriterion("has_red_flower", this.hasItem(Blocks.WHITE_TULIP))
                .build(p_200404_1_, "light_gray_dye_from_white_tulip");
        ShapedRecipeBuilder.shapedRecipe(Blocks.LIGHT_GRAY_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.LIGHT_GRAY_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.LIGHT_GRAY_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LIGHT_GRAY_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.LIGHT_GRAY_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.LIGHT_GRAY_WOOL)
                .addIngredient(Items.LIGHT_GRAY_DYE)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)
                .key('#', Items.GOLD_INGOT)
                .patternLine("##")
                .addCriterion("has_gold_ingot", this.hasItem(Items.GOLD_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.LIME_BANNER)
                .key('#', Blocks.LIME_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_lime_wool", this.hasItem(Blocks.LIME_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.LIME_BED)
                .key('#', Blocks.LIME_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_lime_wool", this.hasItem(Blocks.LIME_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIME_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.LIME_DYE)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "lime_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.LIME_CARPET, 3)
                .key('#', Blocks.LIME_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_lime_wool", this.hasItem(Blocks.LIME_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.LIME_CONCRETE_POWDER, 8)
                .addIngredient(Items.LIME_DYE)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIME_DYE, 2)
                .addIngredient(Items.CACTUS_GREEN)
                .addIngredient(Items.BONE_MEAL)
                .addCriterion("has_green_dye", this.hasItem(Items.CACTUS_GREEN))
                .addCriterion("has_bonemeal", this.hasItem(Items.BONE_MEAL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LIME_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.LIME_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LIME_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.LIME_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LIME_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.LIME_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.LIME_WOOL)
                .addIngredient(Items.LIME_DYE)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.JACK_O_LANTERN)
                .key('A', Blocks.CARVED_PUMPKIN)
                .key('B', Blocks.TORCH)
                .patternLine("A")
                .patternLine("B")
                .addCriterion("has_carved_pumpkin", this.hasItem(Blocks.CARVED_PUMPKIN))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.MAGENTA_BANNER)
                .key('#', Blocks.MAGENTA_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_magenta_wool", this.hasItem(Blocks.MAGENTA_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.MAGENTA_BED)
                .key('#', Blocks.MAGENTA_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_magenta_wool", this.hasItem(Blocks.MAGENTA_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.MAGENTA_DYE)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "magenta_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.MAGENTA_CARPET, 3)
                .key('#', Blocks.MAGENTA_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_magenta_wool", this.hasItem(Blocks.MAGENTA_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.MAGENTA_CONCRETE_POWDER, 8)
                .addIngredient(Items.MAGENTA_DYE)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE)
                .addIngredient(Blocks.ALLIUM)
                .setGroup("magenta_dye")
                .addCriterion("has_red_flower", this.hasItem(Blocks.ALLIUM))
                .build(p_200404_1_, "magenta_dye_from_allium");
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE, 4)
                .addIngredient(Items.LAPIS_LAZULI)
                .addIngredient(Items.ROSE_RED, 2)
                .addIngredient(Items.BONE_MEAL)
                .setGroup("magenta_dye")
                .addCriterion("has_lapis", this.hasItem(Items.LAPIS_LAZULI))
                .addCriterion("has_rose_red", this.hasItem(Items.ROSE_RED))
                .addCriterion("has_bonemeal", this.hasItem(Items.BONE_MEAL))
                .build(p_200404_1_, "magenta_dye_from_lapis_ink_bonemeal");
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE, 3)
                .addIngredient(Items.LAPIS_LAZULI)
                .addIngredient(Items.ROSE_RED)
                .addIngredient(Items.PINK_DYE)
                .setGroup("magenta_dye")
                .addCriterion("has_pink_dye", this.hasItem(Items.PINK_DYE))
                .addCriterion("has_lapis", this.hasItem(Items.LAPIS_LAZULI))
                .addCriterion("has_red_dye", this.hasItem(Items.ROSE_RED))
                .build(p_200404_1_, "magenta_dye_from_lapis_red_pink");
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE, 2)
                .addIngredient(Blocks.LILAC)
                .setGroup("magenta_dye")
                .addCriterion("has_double_plant", this.hasItem(Blocks.LILAC))
                .build(p_200404_1_, "magenta_dye_from_lilac");
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE, 2)
                .addIngredient(Items.PURPLE_DYE)
                .addIngredient(Items.PINK_DYE)
                .setGroup("magenta_dye")
                .addCriterion("has_pink_dye", this.hasItem(Items.PINK_DYE))
                .addCriterion("has_purple_dye", this.hasItem(Items.PURPLE_DYE))
                .build(p_200404_1_, "magenta_dye_from_purple_and_pink");
        ShapedRecipeBuilder.shapedRecipe(Blocks.MAGENTA_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.MAGENTA_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.MAGENTA_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.MAGENTA_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.MAGENTA_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.MAGENTA_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.MAGENTA_WOOL)
                .addIngredient(Items.MAGENTA_DYE)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.MAGMA_BLOCK)
                .key('#', Items.MAGMA_CREAM)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_magma_cream", this.hasItem(Items.MAGMA_CREAM))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGMA_CREAM)
                .addIngredient(Items.BLAZE_POWDER)
                .addIngredient(Items.SLIME_BALL)
                .addCriterion("has_blaze_powder", this.hasItem(Items.BLAZE_POWDER))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.MAP)
                .key('#', Items.PAPER)
                .key('X', Items.COMPASS)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .addCriterion("has_compass", this.hasItem(Items.COMPASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.MELON)
                .key('M', Items.MELON_SLICE)
                .patternLine("MMM")
                .patternLine("MMM")
                .patternLine("MMM")
                .addCriterion("has_melon", this.hasItem(Items.MELON_SLICE))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.MELON_SEEDS)
                .addIngredient(Items.MELON_SLICE)
                .addCriterion("has_melon", this.hasItem(Items.MELON_SLICE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.MINECART)
                .key('#', Items.IRON_INGOT)
                .patternLine("# #")
                .patternLine("###")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.MOSSY_COBBLESTONE)
                .addIngredient(Blocks.COBBLESTONE)
                .addIngredient(Blocks.VINE)
                .addCriterion("has_vine", this.hasItem(Blocks.VINE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.MOSSY_COBBLESTONE_WALL, 6)
                .key('#', Blocks.MOSSY_COBBLESTONE)
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_mossy_cobblestone", this.hasItem(Blocks.MOSSY_COBBLESTONE))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.MOSSY_STONE_BRICKS)
                .addIngredient(Blocks.STONE_BRICKS)
                .addIngredient(Blocks.VINE)
                .addCriterion("has_mossy_cobblestone", this.hasItem(Blocks.MOSSY_COBBLESTONE))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.MUSHROOM_STEW)
                .addIngredient(Blocks.BROWN_MUSHROOM)
                .addIngredient(Blocks.RED_MUSHROOM)
                .addIngredient(Items.BOWL)
                .addCriterion("has_mushroom_stew", this.hasItem(Items.MUSHROOM_STEW))
                .addCriterion("has_bowl", this.hasItem(Items.BOWL))
                .addCriterion("has_brown_mushroom", this.hasItem(Blocks.BROWN_MUSHROOM))
                .addCriterion("has_red_mushroom", this.hasItem(Blocks.RED_MUSHROOM))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICKS)
                .key('N', Items.NETHER_BRICK)
                .patternLine("NN")
                .patternLine("NN")
                .addCriterion("has_netherbrick", this.hasItem(Items.NETHER_BRICK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICK_FENCE, 6)
                .key('#', Blocks.NETHER_BRICKS)
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_nether_brick", this.hasItem(Blocks.NETHER_BRICKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICK_SLAB, 6)
                .key('#', Blocks.NETHER_BRICKS)
                .patternLine("###")
                .addCriterion("has_nether_brick", this.hasItem(Blocks.NETHER_BRICKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICK_STAIRS, 4)
                .key('#', Blocks.NETHER_BRICKS)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .addCriterion("has_nether_brick", this.hasItem(Blocks.NETHER_BRICKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_WART_BLOCK)
                .key('#', Items.NETHER_WART)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_nether_wart", this.hasItem(Items.NETHER_WART))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NOTE_BLOCK)
                .key('#', ItemTags.PLANKS)
                .key('X', Items.REDSTONE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .addCriterion("has_redstone", this.hasItem(Items.REDSTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.OAK_WOOD, 3)
                .key('#', Blocks.OAK_LOG)
                .patternLine("##")
                .patternLine("##")
                .setGroup("bark")
                .addCriterion("has_log", this.hasItem(Blocks.OAK_LOG))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.OAK_BUTTON)
                .addIngredient(Blocks.OAK_PLANKS)
                .setGroup("wooden_button")
                .addCriterion("has_planks", this.hasItem(Blocks.OAK_PLANKS))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.OAK_PLANKS, 4)
                .addIngredient(ItemTags.OAK_LOGS)
                .setGroup("planks")
                .addCriterion("has_log", this.hasItem(ItemTags.OAK_LOGS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.OAK_PRESSURE_PLATE)
                .key('#', Blocks.OAK_PLANKS)
                .patternLine("##")
                .setGroup("wooden_pressure_plate")
                .addCriterion("has_planks", this.hasItem(Blocks.OAK_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.OAK_SLAB, 6)
                .key('#', Blocks.OAK_PLANKS)
                .patternLine("###")
                .setGroup("wooden_slab")
                .addCriterion("has_planks", this.hasItem(Blocks.OAK_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.OAK_STAIRS, 4)
                .key('#', Blocks.OAK_PLANKS)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .setGroup("wooden_stairs")
                .addCriterion("has_planks", this.hasItem(Blocks.OAK_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.OAK_TRAPDOOR, 2)
                .key('#', Blocks.OAK_PLANKS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("wooden_trapdoor")
                .addCriterion("has_planks", this.hasItem(Blocks.OAK_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.OBSERVER)
                .key('Q', Items.QUARTZ)
                .key('R', Items.REDSTONE)
                .key('#', Blocks.COBBLESTONE)
                .patternLine("###")
                .patternLine("RRQ")
                .patternLine("###")
                .addCriterion("has_quartz", this.hasItem(Items.QUARTZ))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.ORANGE_BANNER)
                .key('#', Blocks.ORANGE_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_orange_wool", this.hasItem(Blocks.ORANGE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.ORANGE_BED)
                .key('#', Blocks.ORANGE_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_orange_wool", this.hasItem(Blocks.ORANGE_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.ORANGE_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.ORANGE_DYE)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "orange_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.ORANGE_CARPET, 3)
                .key('#', Blocks.ORANGE_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_orange_wool", this.hasItem(Blocks.ORANGE_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.ORANGE_CONCRETE_POWDER, 8)
                .addIngredient(Items.ORANGE_DYE)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.ORANGE_DYE)
                .addIngredient(Blocks.ORANGE_TULIP)
                .setGroup("orange_dye")
                .addCriterion("has_red_flower", this.hasItem(Blocks.ORANGE_TULIP))
                .build(p_200404_1_, "orange_dye_from_orange_tulip");
        ShapelessRecipeBuilder.shapelessRecipe(Items.ORANGE_DYE, 2)
                .addIngredient(Items.ROSE_RED)
                .addIngredient(Items.DANDELION_YELLOW)
                .setGroup("orange_dye")
                .addCriterion("has_red_dye", this.hasItem(Items.ROSE_RED))
                .addCriterion("has_yellow_dye", this.hasItem(Items.DANDELION_YELLOW))
                .build(p_200404_1_, "orange_dye_from_red_yellow");
        ShapedRecipeBuilder.shapedRecipe(Blocks.ORANGE_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.ORANGE_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ORANGE_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.ORANGE_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ORANGE_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.ORANGE_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.ORANGE_WOOL)
                .addIngredient(Items.ORANGE_DYE)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.PAINTING)
                .key('#', Items.STICK)
                .key('X', Ingredient.fromTag(ItemTags.WOOL))
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .addCriterion("has_wool", this.hasItem(ItemTags.WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.PAPER, 3)
                .key('#', Blocks.SUGAR_CANE)
                .patternLine("###")
                .addCriterion("has_reeds", this.hasItem(Blocks.SUGAR_CANE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_PILLAR, 2)
                .key('#', Blocks.QUARTZ_BLOCK)
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_chiseled_quartz_block", this.hasItem(Blocks.CHISELED_QUARTZ_BLOCK))
                .addCriterion("has_quartz_block", this.hasItem(Blocks.QUARTZ_BLOCK))
                .addCriterion("has_quartz_pillar", this.hasItem(Blocks.QUARTZ_PILLAR))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.PACKED_ICE)
                .addIngredient(Blocks.ICE, 9)
                .addCriterion("has_at_least_9_ice", this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Blocks.ICE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.PINK_BANNER)
                .key('#', Blocks.PINK_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_pink_wool", this.hasItem(Blocks.PINK_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.PINK_BED)
                .key('#', Blocks.PINK_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_pink_wool", this.hasItem(Blocks.PINK_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.PINK_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.PINK_DYE)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "pink_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.PINK_CARPET, 3)
                .key('#', Blocks.PINK_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_pink_wool", this.hasItem(Blocks.PINK_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.PINK_CONCRETE_POWDER, 8)
                .addIngredient(Items.PINK_DYE)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.PINK_DYE, 2)
                .addIngredient(Blocks.PEONY)
                .setGroup("pink_dye")
                .addCriterion("has_double_plant", this.hasItem(Blocks.PEONY))
                .build(p_200404_1_, "pink_dye_from_peony");
        ShapelessRecipeBuilder.shapelessRecipe(Items.PINK_DYE)
                .addIngredient(Blocks.PINK_TULIP)
                .setGroup("pink_dye")
                .addCriterion("has_red_flower", this.hasItem(Blocks.PINK_TULIP))
                .build(p_200404_1_, "pink_dye_from_pink_tulip");
        ShapelessRecipeBuilder.shapelessRecipe(Items.PINK_DYE, 2)
                .addIngredient(Items.ROSE_RED)
                .addIngredient(Items.BONE_MEAL)
                .setGroup("pink_dye")
                .addCriterion("has_bonemeal", this.hasItem(Items.BONE_MEAL))
                .addCriterion("has_red_dye", this.hasItem(Items.ROSE_RED))
                .build(p_200404_1_, "pink_dye_from_red_bonemeal");
        ShapedRecipeBuilder.shapedRecipe(Blocks.PINK_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.PINK_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PINK_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.PINK_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PINK_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.PINK_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.PINK_WOOL)
                .addIngredient(Items.PINK_DYE)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PISTON)
                .key('R', Items.REDSTONE)
                .key('#', Blocks.COBBLESTONE)
                .key('T', ItemTags.PLANKS)
                .key('X', Items.IRON_INGOT)
                .patternLine("TTT")
                .patternLine("#X#")
                .patternLine("#R#")
                .addCriterion("has_redstone", this.hasItem(Items.REDSTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_GRANITE, 4)
                .key('S', Blocks.GRANITE)
                .patternLine("SS")
                .patternLine("SS")
                .addCriterion("has_stone", this.hasItem(Blocks.GRANITE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_DIORITE, 4)
                .key('S', Blocks.DIORITE)
                .patternLine("SS")
                .patternLine("SS")
                .addCriterion("has_stone", this.hasItem(Blocks.DIORITE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_ANDESITE, 4)
                .key('S', Blocks.ANDESITE)
                .patternLine("SS")
                .patternLine("SS")
                .addCriterion("has_stone", this.hasItem(Blocks.ANDESITE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE)
                .key('S', Items.PRISMARINE_SHARD)
                .patternLine("SS")
                .patternLine("SS")
                .addCriterion("has_prismarine_shard", this.hasItem(Items.PRISMARINE_SHARD))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_BRICKS)
                .key('S', Items.PRISMARINE_SHARD)
                .patternLine("SSS")
                .patternLine("SSS")
                .patternLine("SSS")
                .addCriterion("has_prismarine_shard", this.hasItem(Items.PRISMARINE_SHARD))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_SLAB, 6)
                .key('#', Blocks.PRISMARINE)
                .patternLine("###")
                .addCriterion("has_prismarine", this.hasItem(Blocks.PRISMARINE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_BRICK_SLAB, 6)
                .key('#', Blocks.PRISMARINE_BRICKS)
                .patternLine("###")
                .addCriterion("has_prismarine_bricks", this.hasItem(Blocks.PRISMARINE_BRICKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_PRISMARINE_SLAB, 6)
                .key('#', Blocks.DARK_PRISMARINE)
                .patternLine("###")
                .addCriterion("has_dark_prismarine", this.hasItem(Blocks.DARK_PRISMARINE))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.PUMPKIN_PIE)
                .addIngredient(Blocks.PUMPKIN)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.EGG)
                .addCriterion("has_carved_pumpkin", this.hasItem(Blocks.CARVED_PUMPKIN))
                .addCriterion("has_pumpkin", this.hasItem(Blocks.PUMPKIN))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.PUMPKIN_SEEDS, 4)
                .addIngredient(Blocks.PUMPKIN)
                .addCriterion("has_pumpkin", this.hasItem(Blocks.PUMPKIN))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.PURPLE_BANNER)
                .key('#', Blocks.PURPLE_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_purple_wool", this.hasItem(Blocks.PURPLE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.PURPLE_BED)
                .key('#', Blocks.PURPLE_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_purple_wool", this.hasItem(Blocks.PURPLE_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.PURPLE_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.PURPLE_DYE)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "purple_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.PURPLE_CARPET, 3)
                .key('#', Blocks.PURPLE_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_purple_wool", this.hasItem(Blocks.PURPLE_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.PURPLE_CONCRETE_POWDER, 8)
                .addIngredient(Items.PURPLE_DYE)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.PURPLE_DYE, 2)
                .addIngredient(Items.LAPIS_LAZULI)
                .addIngredient(Items.ROSE_RED)
                .addCriterion("has_lapis", this.hasItem(Items.LAPIS_LAZULI))
                .addCriterion("has_red_dye", this.hasItem(Items.ROSE_RED))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SHULKER_BOX)
                .key('#', Blocks.CHEST)
                .key('-', Items.SHULKER_SHELL)
                .patternLine("-")
                .patternLine("#")
                .patternLine("-")
                .addCriterion("has_shulker_shell", this.hasItem(Items.SHULKER_SHELL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PURPLE_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.PURPLE_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PURPLE_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.PURPLE_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PURPLE_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.PURPLE_DYE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.PURPLE_WOOL)
                .addIngredient(Items.PURPLE_DYE)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PURPUR_BLOCK, 4)
                .key('F', Items.POPPED_CHORUS_FRUIT)
                .patternLine("FF")
                .patternLine("FF")
                .addCriterion("has_chorus_fruit_popped", this.hasItem(Items.POPPED_CHORUS_FRUIT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PURPUR_PILLAR)
                .key('#', Blocks.PURPUR_SLAB)
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_purpur_block", this.hasItem(Blocks.PURPUR_BLOCK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PURPUR_SLAB, 6)
                .key('#', Ingredient.fromItems(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR))
                .patternLine("###")
                .addCriterion("has_purpur_block", this.hasItem(Blocks.PURPUR_BLOCK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PURPUR_STAIRS, 4)
                .key('#', Ingredient.fromItems(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR))
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .addCriterion("has_purpur_block", this.hasItem(Blocks.PURPUR_BLOCK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_BLOCK)
                .key('#', Items.QUARTZ)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_quartz", this.hasItem(Items.QUARTZ))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_SLAB, 6)
                .key('#', Ingredient.fromItems(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR))
                .patternLine("###")
                .addCriterion("has_chiseled_quartz_block", this.hasItem(Blocks.CHISELED_QUARTZ_BLOCK))
                .addCriterion("has_quartz_block", this.hasItem(Blocks.QUARTZ_BLOCK))
                .addCriterion("has_quartz_pillar", this.hasItem(Blocks.QUARTZ_PILLAR))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_STAIRS, 4)
                .key('#', Ingredient.fromItems(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR))
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .addCriterion("has_chiseled_quartz_block", this.hasItem(Blocks.CHISELED_QUARTZ_BLOCK))
                .addCriterion("has_quartz_block", this.hasItem(Blocks.QUARTZ_BLOCK))
                .addCriterion("has_quartz_pillar", this.hasItem(Blocks.QUARTZ_PILLAR))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.RABBIT_STEW)
                .key('P', Items.BAKED_POTATO)
                .key('R', Items.COOKED_RABBIT)
                .key('B', Items.BOWL)
                .key('C', Items.CARROT)
                .key('M', Blocks.BROWN_MUSHROOM)
                .patternLine(" R ")
                .patternLine("CPM")
                .patternLine(" B ")
                .setGroup("rabbit_stew")
                .addCriterion("has_cooked_rabbit", this.hasItem(Items.COOKED_RABBIT))
                .build(p_200404_1_, "rabbit_stew_from_brown_mushroom");
        ShapedRecipeBuilder.shapedRecipe(Items.RABBIT_STEW)
                .key('P', Items.BAKED_POTATO)
                .key('R', Items.COOKED_RABBIT)
                .key('B', Items.BOWL)
                .key('C', Items.CARROT)
                .key('D', Blocks.RED_MUSHROOM)
                .patternLine(" R ")
                .patternLine("CPD")
                .patternLine(" B ")
                .setGroup("rabbit_stew")
                .addCriterion("has_cooked_rabbit", this.hasItem(Items.COOKED_RABBIT))
                .build(p_200404_1_, "rabbit_stew_from_red_mushroom");
        ShapedRecipeBuilder.shapedRecipe(Blocks.RAIL, 16)
                .key('#', Items.STICK)
                .key('X', Items.IRON_INGOT)
                .patternLine("X X")
                .patternLine("X#X")
                .patternLine("X X")
                .addCriterion("has_minecart", this.hasItem(Items.MINECART))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.REDSTONE, 9)
                .addIngredient(Blocks.REDSTONE_BLOCK)
                .addCriterion("has_redstone_block", this.hasItem(Blocks.REDSTONE_BLOCK))
                .addCriterion("has_at_least_9_redstone",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.REDSTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.REDSTONE_BLOCK)
                .key('#', Items.REDSTONE)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_at_least_9_redstone",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.REDSTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.REDSTONE_LAMP)
                .key('R', Items.REDSTONE)
                .key('G', Blocks.GLOWSTONE)
                .patternLine(" R ")
                .patternLine("RGR")
                .patternLine(" R ")
                .addCriterion("has_glowstone", this.hasItem(Blocks.GLOWSTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.REDSTONE_TORCH)
                .key('#', Items.STICK)
                .key('X', Items.REDSTONE)
                .patternLine("X")
                .patternLine("#")
                .addCriterion("has_redstone", this.hasItem(Items.REDSTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.RED_BANNER)
                .key('#', Blocks.RED_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_red_wool", this.hasItem(Blocks.RED_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.RED_BED)
                .key('#', Blocks.RED_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_red_wool", this.hasItem(Blocks.RED_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.RED_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.ROSE_RED)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "red_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_CARPET, 3)
                .key('#', Blocks.RED_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_red_wool", this.hasItem(Blocks.RED_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.RED_CONCRETE_POWDER, 8)
                .addIngredient(Items.ROSE_RED)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.ROSE_RED)
                .addIngredient(Items.BEETROOT)
                .setGroup("red_dye")
                .addCriterion("has_beetroot", this.hasItem(Items.BEETROOT))
                .build(p_200404_1_, "red_dye_from_beetroot");
        ShapelessRecipeBuilder.shapelessRecipe(Items.ROSE_RED)
                .addIngredient(Blocks.POPPY)
                .setGroup("red_dye")
                .addCriterion("has_red_flower", this.hasItem(Blocks.POPPY))
                .build(p_200404_1_, "red_dye_from_poppy");
        ShapelessRecipeBuilder.shapelessRecipe(Items.ROSE_RED, 2)
                .addIngredient(Blocks.ROSE_BUSH)
                .setGroup("red_dye")
                .addCriterion("has_double_plant", this.hasItem(Blocks.ROSE_BUSH))
                .build(p_200404_1_, "red_dye_from_rose_bush");
        ShapelessRecipeBuilder.shapelessRecipe(Items.ROSE_RED)
                .addIngredient(Blocks.RED_TULIP)
                .setGroup("red_dye")
                .addCriterion("has_red_flower", this.hasItem(Blocks.RED_TULIP))
                .build(p_200404_1_, "red_dye_from_tulip");
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_NETHER_BRICKS)
                .key('W', Items.NETHER_WART)
                .key('N', Items.NETHER_BRICK)
                .patternLine("NW")
                .patternLine("WN")
                .addCriterion("has_nether_wart", this.hasItem(Items.NETHER_WART))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_SANDSTONE)
                .key('#', Blocks.RED_SAND)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_sand", this.hasItem(Blocks.RED_SAND))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_SANDSTONE_SLAB, 6)
                .key('#',
                        Ingredient.fromItems(Blocks.RED_SANDSTONE,
                                Blocks.CHISELED_RED_SANDSTONE,
                                Blocks.CUT_RED_SANDSTONE))
                .patternLine("###")
                .addCriterion("has_red_sandstone", this.hasItem(Blocks.RED_SANDSTONE))
                .addCriterion("has_chiseled_red_sandstone", this.hasItem(Blocks.CHISELED_RED_SANDSTONE))
                .addCriterion("has_cut_red_sandstone", this.hasItem(Blocks.CUT_RED_SANDSTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_SANDSTONE_STAIRS, 4)
                .key('#',
                        Ingredient.fromItems(Blocks.RED_SANDSTONE,
                                Blocks.CHISELED_RED_SANDSTONE,
                                Blocks.CUT_RED_SANDSTONE))
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .addCriterion("has_red_sandstone", this.hasItem(Blocks.RED_SANDSTONE))
                .addCriterion("has_chiseled_red_sandstone", this.hasItem(Blocks.CHISELED_RED_SANDSTONE))
                .addCriterion("has_cut_red_sandstone", this.hasItem(Blocks.CUT_RED_SANDSTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.ROSE_RED)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.RED_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.ROSE_RED)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.RED_WOOL)
                .addIngredient(Items.ROSE_RED)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.REPEATER)
                .key('#', Blocks.REDSTONE_TORCH)
                .key('X', Items.REDSTONE)
                .key('I', Blocks.STONE)
                .patternLine("#X#")
                .patternLine("III")
                .addCriterion("has_redstone_torch", this.hasItem(Blocks.REDSTONE_TORCH))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SANDSTONE)
                .key('#', Blocks.SAND)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SANDSTONE_SLAB, 6)
                .key('#', Ingredient.fromItems(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE))
                .patternLine("###")
                .addCriterion("has_sandstone", this.hasItem(Blocks.SANDSTONE))
                .addCriterion("has_chiseled_sandstone", this.hasItem(Blocks.CHISELED_SANDSTONE))
                .addCriterion("has_cut_sandstone", this.hasItem(Blocks.CUT_SANDSTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SANDSTONE_STAIRS, 4)
                .key('#', Ingredient.fromItems(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE))
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .addCriterion("has_sandstone", this.hasItem(Blocks.SANDSTONE))
                .addCriterion("has_chiseled_sandstone", this.hasItem(Blocks.CHISELED_SANDSTONE))
                .addCriterion("has_cut_sandstone", this.hasItem(Blocks.CUT_SANDSTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SEA_LANTERN)
                .key('S', Items.PRISMARINE_SHARD)
                .key('C', Items.PRISMARINE_CRYSTALS)
                .patternLine("SCS")
                .patternLine("CCC")
                .patternLine("SCS")
                .addCriterion("has_prismarine_crystals", this.hasItem(Items.PRISMARINE_CRYSTALS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.SHEARS)
                .key('#', Items.IRON_INGOT)
                .patternLine(" #")
                .patternLine("# ")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.SHIELD)
                .key('W', ItemTags.PLANKS)
                .key('o', Items.IRON_INGOT)
                .patternLine("WoW")
                .patternLine("WWW")
                .patternLine(" W ")
                .addCriterion("has_iron_ingot", this.hasItem(Items.IRON_INGOT))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.SIGN, 3)
                .key('#', ItemTags.PLANKS)
                .key('X', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" X ")
                .addCriterion("has_planks", this.hasItem(ItemTags.PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SLIME_BLOCK)
                .key('#', Items.SLIME_BALL)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_at_least_9_slime_ball",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.SLIME_BALL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.SLIME_BALL, 9)
                .addIngredient(Blocks.SLIME_BLOCK)
                .addCriterion("has_at_least_9_slime_ball",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.SLIME_BALL))
                .addCriterion("has_slime", this.hasItem(Blocks.SLIME_BLOCK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CUT_RED_SANDSTONE, 4)
                .key('#', Blocks.RED_SANDSTONE)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_red_sandstone", this.hasItem(Blocks.RED_SANDSTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CUT_SANDSTONE, 4)
                .key('#', Blocks.SANDSTONE)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_sandstone", this.hasItem(Blocks.SANDSTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SNOW_BLOCK)
                .key('#', Items.SNOWBALL)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_snowball", this.hasItem(Items.SNOWBALL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SNOW, 6)
                .key('#', Blocks.SNOW_BLOCK)
                .patternLine("###")
                .addCriterion("has_snowball", this.hasItem(Items.SNOWBALL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.GLISTERING_MELON_SLICE)
                .key('#', Items.GOLD_NUGGET)
                .key('X', Items.MELON_SLICE)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .addCriterion("has_melon", this.hasItem(Items.MELON_SLICE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.SPECTRAL_ARROW, 2)
                .key('#', Items.GLOWSTONE_DUST)
                .key('X', Items.ARROW)
                .patternLine(" # ")
                .patternLine("#X#")
                .patternLine(" # ")
                .addCriterion("has_glowstone_dust", this.hasItem(Items.GLOWSTONE_DUST))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SPRUCE_WOOD, 3)
                .key('#', Blocks.SPRUCE_LOG)
                .patternLine("##")
                .patternLine("##")
                .setGroup("bark")
                .addCriterion("has_log", this.hasItem(Blocks.SPRUCE_LOG))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.SPRUCE_BOAT)
                .key('#', Blocks.SPRUCE_PLANKS)
                .patternLine("# #")
                .patternLine("###")
                .setGroup("boat")
                .addCriterion("in_water", this.enteredBlock(Blocks.WATER))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.SPRUCE_BUTTON)
                .addIngredient(Blocks.SPRUCE_PLANKS)
                .setGroup("wooden_button")
                .addCriterion("has_planks", this.hasItem(Blocks.SPRUCE_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SPRUCE_DOOR, 3)
                .key('#', Blocks.SPRUCE_PLANKS)
                .patternLine("##")
                .patternLine("##")
                .patternLine("##")
                .setGroup("wooden_door")
                .addCriterion("has_planks", this.hasItem(Blocks.SPRUCE_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SPRUCE_FENCE, 3)
                .key('#', Items.STICK)
                .key('W', Blocks.SPRUCE_PLANKS)
                .patternLine("W#W")
                .patternLine("W#W")
                .setGroup("wooden_fence")
                .addCriterion("has_planks", this.hasItem(Blocks.SPRUCE_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SPRUCE_FENCE_GATE)
                .key('#', Items.STICK)
                .key('W', Blocks.SPRUCE_PLANKS)
                .patternLine("#W#")
                .patternLine("#W#")
                .setGroup("wooden_fence_gate")
                .addCriterion("has_planks", this.hasItem(Blocks.SPRUCE_PLANKS))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.SPRUCE_PLANKS, 4)
                .addIngredient(ItemTags.SPRUCE_LOGS)
                .setGroup("planks")
                .addCriterion("has_log", this.hasItem(ItemTags.SPRUCE_LOGS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SPRUCE_PRESSURE_PLATE)
                .key('#', Blocks.SPRUCE_PLANKS)
                .patternLine("##")
                .setGroup("wooden_pressure_plate")
                .addCriterion("has_planks", this.hasItem(Blocks.SPRUCE_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SPRUCE_SLAB, 6)
                .key('#', Blocks.SPRUCE_PLANKS)
                .patternLine("###")
                .setGroup("wooden_slab")
                .addCriterion("has_planks", this.hasItem(Blocks.SPRUCE_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SPRUCE_STAIRS, 4)
                .key('#', Blocks.SPRUCE_PLANKS)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .setGroup("wooden_stairs")
                .addCriterion("has_planks", this.hasItem(Blocks.SPRUCE_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SPRUCE_TRAPDOOR, 2)
                .key('#', Blocks.SPRUCE_PLANKS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("wooden_trapdoor")
                .addCriterion("has_planks", this.hasItem(Blocks.SPRUCE_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.STICK, 4)
                .key('#', ItemTags.PLANKS)
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_planks", this.hasItem(ItemTags.PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STICKY_PISTON)
                .key('P', Blocks.PISTON)
                .key('S', Items.SLIME_BALL)
                .patternLine("S")
                .patternLine("P")
                .addCriterion("has_slime_ball", this.hasItem(Items.SLIME_BALL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_BRICKS, 4)
                .key('#', Blocks.STONE)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_stone", this.hasItem(Blocks.STONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_BRICK_SLAB, 6)
                .key('#', Ingredient.fromTag(ItemTags.STONE_BRICKS))
                .patternLine("###")
                .addCriterion("has_stone_bricks", this.hasItem(ItemTags.STONE_BRICKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_BRICK_STAIRS, 4)
                .key('#', Ingredient.fromTag(ItemTags.STONE_BRICKS))
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .addCriterion("has_stone_bricks", this.hasItem(ItemTags.STONE_BRICKS))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.STONE_BUTTON)
                .addIngredient(Blocks.STONE)
                .addCriterion("has_stone", this.hasItem(Blocks.STONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_PRESSURE_PLATE)
                .key('#', Blocks.STONE)
                .patternLine("##")
                .addCriterion("has_stone", this.hasItem(Blocks.STONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_SLAB, 6)
                .key('#', Blocks.STONE)
                .patternLine("###")
                .addCriterion("has_stone", this.hasItem(Blocks.STONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.COBBLESTONE_STAIRS, 4)
                .key('#', Blocks.COBBLESTONE)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .addCriterion("has_cobblestone", this.hasItem(Blocks.COBBLESTONE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.WHITE_WOOL)
                .key('#', Items.STRING)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_string", this.hasItem(Items.STRING))
                .build(p_200404_1_, "white_wool_from_string");
        ShapelessRecipeBuilder.shapelessRecipe(Items.SUGAR)
                .addIngredient(Blocks.SUGAR_CANE)
                .addCriterion("has_reeds", this.hasItem(Blocks.SUGAR_CANE))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.TNT)
                .key('#', Ingredient.fromItems(Blocks.SAND, Blocks.RED_SAND))
                .key('X', Items.GUNPOWDER)
                .patternLine("X#X")
                .patternLine("#X#")
                .patternLine("X#X")
                .addCriterion("has_gunpowder", this.hasItem(Items.GUNPOWDER))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.TNT_MINECART)
                .key('A', Blocks.TNT)
                .key('B', Items.MINECART)
                .patternLine("A")
                .patternLine("B")
                .addCriterion("has_minecart", this.hasItem(Items.MINECART))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.TORCH, 4)
                .key('#', Items.STICK)
                .key('X', Ingredient.fromItems(Items.COAL, Items.CHARCOAL))
                .patternLine("X")
                .patternLine("#")
                .addCriterion("has_stick", this.hasItem(Items.STICK))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.TRAPPED_CHEST)
                .addIngredient(Blocks.CHEST)
                .addIngredient(Blocks.TRIPWIRE_HOOK)
                .addCriterion("has_tripwire_hook", this.hasItem(Blocks.TRIPWIRE_HOOK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.TRIPWIRE_HOOK, 2)
                .key('#', ItemTags.PLANKS)
                .key('S', Items.STICK)
                .key('I', Items.IRON_INGOT)
                .patternLine("I")
                .patternLine("S")
                .patternLine("#")
                .addCriterion("has_string", this.hasItem(Items.STRING))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.TURTLE_HELMET)
                .key('X', Items.SCUTE)
                .patternLine("XXX")
                .patternLine("X X")
                .addCriterion("has_scute", this.hasItem(Items.SCUTE))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.WHEAT, 9)
                .addIngredient(Blocks.HAY_BLOCK)
                .addCriterion("has_at_least_9_wheat", this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.WHEAT))
                .addCriterion("has_hay_block", this.hasItem(Blocks.HAY_BLOCK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.WHITE_BANNER)
                .key('#', Blocks.WHITE_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.WHITE_BED)
                .key('#', Blocks.WHITE_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.WHITE_CARPET, 3)
                .key('#', Blocks.WHITE_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.WHITE_CONCRETE_POWDER, 8)
                .addIngredient(Items.BONE_MEAL)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.WHITE_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.BONE_MEAL)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.WHITE_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.WHITE_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.WHITE_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.BONE_MEAL)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.OAK_DOOR, 3)
                .key('#', Blocks.OAK_PLANKS)
                .patternLine("##")
                .patternLine("##")
                .patternLine("##")
                .setGroup("wooden_door")
                .addCriterion("has_planks", this.hasItem(Blocks.OAK_PLANKS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.WOODEN_SHOVEL)
                .key('#', Items.STICK)
                .key('X', ItemTags.PLANKS)
                .patternLine("X")
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_stick", this.hasItem(Items.STICK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.WOODEN_SWORD)
                .key('#', Items.STICK)
                .key('X', ItemTags.PLANKS)
                .patternLine("X")
                .patternLine("X")
                .patternLine("#")
                .addCriterion("has_stick", this.hasItem(Items.STICK))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.WRITABLE_BOOK)
                .addIngredient(Items.BOOK)
                .addIngredient(Items.INK_SAC)
                .addIngredient(Items.FEATHER)
                .addCriterion("has_book", this.hasItem(Items.BOOK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.YELLOW_BANNER)
                .key('#', Blocks.YELLOW_WOOL)
                .key('|', Items.STICK)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_yellow_wool", this.hasItem(Blocks.YELLOW_WOOL))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Items.YELLOW_BED)
                .key('#', Blocks.YELLOW_WOOL)
                .key('X', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("XXX")
                .setGroup("bed")
                .addCriterion("has_yellow_wool", this.hasItem(Blocks.YELLOW_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.YELLOW_BED)
                .addIngredient(Items.WHITE_BED)
                .addIngredient(Items.DANDELION_YELLOW)
                .setGroup("dyed_bed")
                .addCriterion("has_bed", this.hasItem(Items.WHITE_BED))
                .build(p_200404_1_, "yellow_bed_from_white_bed");
        ShapedRecipeBuilder.shapedRecipe(Blocks.YELLOW_CARPET, 3)
                .key('#', Blocks.YELLOW_WOOL)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_yellow_wool", this.hasItem(Blocks.YELLOW_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.YELLOW_CONCRETE_POWDER, 8)
                .addIngredient(Items.DANDELION_YELLOW)
                .addIngredient(Blocks.SAND, 4)
                .addIngredient(Blocks.GRAVEL, 4)
                .setGroup("concrete_powder")
                .addCriterion("has_sand", this.hasItem(Blocks.SAND))
                .addCriterion("has_gravel", this.hasItem(Blocks.GRAVEL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.DANDELION_YELLOW)
                .addIngredient(Blocks.DANDELION)
                .setGroup("yellow_dye")
                .addCriterion("has_yellow_flower", this.hasItem(Blocks.DANDELION))
                .build(p_200404_1_, "yellow_dye_from_dandelion");
        ShapelessRecipeBuilder.shapelessRecipe(Items.DANDELION_YELLOW, 2)
                .addIngredient(Blocks.SUNFLOWER)
                .setGroup("yellow_dye")
                .addCriterion("has_double_plant", this.hasItem(Blocks.SUNFLOWER))
                .build(p_200404_1_, "yellow_dye_from_sunflower");
        ShapedRecipeBuilder.shapedRecipe(Blocks.YELLOW_STAINED_GLASS, 8)
                .key('#', Blocks.GLASS)
                .key('X', Items.DANDELION_YELLOW)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.YELLOW_STAINED_GLASS_PANE, 16)
                .key('#', Blocks.YELLOW_STAINED_GLASS)
                .patternLine("###")
                .patternLine("###")
                .setGroup("stained_glass_pane")
                .addCriterion("has_glass", this.hasItem(Blocks.GLASS))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.YELLOW_TERRACOTTA, 8)
                .key('#', Blocks.TERRACOTTA)
                .key('X', Items.DANDELION_YELLOW)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_terracotta")
                .addCriterion("has_terracotta", this.hasItem(Blocks.TERRACOTTA))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.YELLOW_WOOL)
                .addIngredient(Items.DANDELION_YELLOW)
                .addIngredient(Blocks.WHITE_WOOL)
                .setGroup("wool")
                .addCriterion("has_white_wool", this.hasItem(Blocks.WHITE_WOOL))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Items.DRIED_KELP, 9)
                .addIngredient(Blocks.DRIED_KELP_BLOCK)
                .addCriterion("has_at_least_9_dried_kelp",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.DRIED_KELP))
                .addCriterion("has_dried_kelp_block", this.hasItem(Blocks.DRIED_KELP_BLOCK))
                .build(p_200404_1_);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.DRIED_KELP_BLOCK)
                .addIngredient(Items.DRIED_KELP, 9)
                .addCriterion("has_at_least_9_dried_kelp",
                        this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.DRIED_KELP))
                .addCriterion("has_dried_kelp_block", this.hasItem(Blocks.DRIED_KELP_BLOCK))
                .build(p_200404_1_);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CONDUIT)
                .key('#', Items.NAUTILUS_SHELL)
                .key('X', Items.HEART_OF_THE_SEA)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .addCriterion("has_nautilus_core", this.hasItem(Items.HEART_OF_THE_SEA))
                .addCriterion("has_nautilus_shell", this.hasItem(Items.NAUTILUS_SHELL))
                .build(p_200404_1_);
        CustomRecipeBuilder.customRecipe(RecipeSerializers.CRAFTING_SPECIAL_ARMORDYE).build(p_200404_1_, "armor_dye");
        CustomRecipeBuilder.customRecipe(RecipeSerializers.CRAFTING_SPECIAL_BANNERADDPATTERN)
                .build(p_200404_1_, "banner_add_pattern");
        CustomRecipeBuilder.customRecipe(RecipeSerializers.CRAFTING_SPECIAL_BANNERDUPLICATE)
                .build(p_200404_1_, "banner_duplicate");
        CustomRecipeBuilder.customRecipe(RecipeSerializers.CRAFTING_SPECIAL_BOOKCLONING)
                .build(p_200404_1_, "book_cloning");
        CustomRecipeBuilder.customRecipe(RecipeSerializers.CRAFTING_SPECIAL_FIREWORK_ROCKET)
                .build(p_200404_1_, "firework_rocket");
        CustomRecipeBuilder.customRecipe(RecipeSerializers.CRAFTING_SPECIAL_FIREWORK_STAR)
                .build(p_200404_1_, "firework_star");
        CustomRecipeBuilder.customRecipe(RecipeSerializers.CRAFTING_SPECIAL_FIREWORK_STAR_FADE)
                .build(p_200404_1_, "firework_star_fade");
        CustomRecipeBuilder.customRecipe(RecipeSerializers.CRAFTING_SPECIAL_MAPCLONING)
                .build(p_200404_1_, "map_cloning");
        CustomRecipeBuilder.customRecipe(RecipeSerializers.CRAFTING_SPECIAL_MAPEXTENDING)
                .build(p_200404_1_, "map_extending");
        CustomRecipeBuilder.customRecipe(RecipeSerializers.CRAFTING_SPECIAL_REPAIRITEM)
                .build(p_200404_1_, "repair_item");
        CustomRecipeBuilder.customRecipe(RecipeSerializers.CRAFTING_SPECIAL_SHIELDDECORATION)
                .build(p_200404_1_, "shield_decoration");
        CustomRecipeBuilder.customRecipe(RecipeSerializers.CRAFTING_SPECIAL_SHULKERBOXCOLORING)
                .build(p_200404_1_, "shulker_box_coloring");
        CustomRecipeBuilder.customRecipe(RecipeSerializers.CRAFTING_SPECIAL_TIPPEDARROW)
                .build(p_200404_1_, "tipped_arrow");
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Items.POTATO), Items.BAKED_POTATO, 0.35F, 200)
                .addCriterion("has_potato", this.hasItem(Items.POTATO))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Items.CLAY_BALL), Items.BRICK, 0.3F, 200)
                .addCriterion("has_clay_ball", this.hasItem(Items.CLAY_BALL))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromTag(ItemTags.LOGS), Items.CHARCOAL, 0.15F, 200)
                .addCriterion("has_log", this.hasItem(ItemTags.LOGS))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Items.CHORUS_FRUIT),
                Items.POPPED_CHORUS_FRUIT,
                0.1F,
                200).addCriterion("has_chorus_fruit", this.hasItem(Items.CHORUS_FRUIT)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.COAL_ORE.asItem()), Items.COAL, 0.1F, 200)
                .addCriterion("has_coal_ore", this.hasItem(Blocks.COAL_ORE))
                .build(p_200404_1_, "coal_from_smelting");
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Items.BEEF), Items.COOKED_BEEF, 0.35F, 200)
                .addCriterion("has_beef", this.hasItem(Items.BEEF))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Items.CHICKEN), Items.COOKED_CHICKEN, 0.35F, 200)
                .addCriterion("has_chicken", this.hasItem(Items.CHICKEN))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Items.COD), Items.COOKED_COD, 0.35F, 200)
                .addCriterion("has_cod", this.hasItem(Items.COD))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.KELP), Items.DRIED_KELP, 0.1F, 200)
                .addCriterion("has_kelp", this.hasItem(Blocks.KELP))
                .build(p_200404_1_, "dried_kelp_from_smelting");
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Items.SALMON), Items.COOKED_SALMON, 0.35F, 200)
                .addCriterion("has_salmon", this.hasItem(Items.SALMON))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Items.MUTTON), Items.COOKED_MUTTON, 0.35F, 200)
                .addCriterion("has_mutton", this.hasItem(Items.MUTTON))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Items.PORKCHOP), Items.COOKED_PORKCHOP, 0.35F, 200)
                .addCriterion("has_porkchop", this.hasItem(Items.PORKCHOP))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Items.RABBIT), Items.COOKED_RABBIT, 0.35F, 200)
                .addCriterion("has_rabbit", this.hasItem(Items.RABBIT))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.DIAMOND_ORE.asItem()), Items.DIAMOND, 1.0F, 200)
                .addCriterion("has_diamond_ore", this.hasItem(Blocks.DIAMOND_ORE))
                .build(p_200404_1_, "diamond_from_smelting");
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.LAPIS_ORE.asItem()),
                Items.LAPIS_LAZULI,
                0.2F,
                200)
                .addCriterion("has_lapis_ore", this.hasItem(Blocks.LAPIS_ORE))
                .build(p_200404_1_, "lapis_from_smelting");
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.EMERALD_ORE.asItem()), Items.EMERALD, 1.0F, 200)
                .addCriterion("has_emerald_ore", this.hasItem(Blocks.EMERALD_ORE))
                .build(p_200404_1_, "emerald_from_smelting");
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromTag(ItemTags.SAND), Blocks.GLASS.asItem(), 0.1F, 200)
                .addCriterion("has_sand", this.hasItem(ItemTags.SAND))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.GOLD_ORE.asItem()), Items.GOLD_INGOT, 1.0F, 200)
                .addCriterion("has_gold_ore", this.hasItem(Blocks.GOLD_ORE))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.SEA_PICKLE.asItem()), Items.LIME_DYE, 0.1F, 200)
                .addCriterion("has_sea_pickle", this.hasItem(Blocks.SEA_PICKLE))
                .build(p_200404_1_, "lime_dye_from_smelting");
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.CACTUS.asItem()), Items.CACTUS_GREEN, 1.0F, 200)
                .addCriterion("has_cactus", this.hasItem(Blocks.CACTUS))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Items.GOLDEN_PICKAXE,
                Items.GOLDEN_SHOVEL,
                Items.GOLDEN_AXE,
                Items.GOLDEN_HOE,
                Items.GOLDEN_SWORD,
                Items.GOLDEN_HELMET,
                Items.GOLDEN_CHESTPLATE,
                Items.GOLDEN_LEGGINGS,
                Items.GOLDEN_BOOTS,
                Items.GOLDEN_HORSE_ARMOR), Items.GOLD_NUGGET, 0.1F, 200)
                .addCriterion("has_golden_pickaxe", this.hasItem(Items.GOLDEN_PICKAXE))
                .addCriterion("has_golden_shovel", this.hasItem(Items.GOLDEN_SHOVEL))
                .addCriterion("has_golden_axe", this.hasItem(Items.GOLDEN_AXE))
                .addCriterion("has_golden_hoe", this.hasItem(Items.GOLDEN_HOE))
                .addCriterion("has_golden_sword", this.hasItem(Items.GOLDEN_SWORD))
                .addCriterion("has_golden_helmet", this.hasItem(Items.GOLDEN_HELMET))
                .addCriterion("has_golden_chestplate", this.hasItem(Items.GOLDEN_CHESTPLATE))
                .addCriterion("has_golden_leggings", this.hasItem(Items.GOLDEN_LEGGINGS))
                .addCriterion("has_golden_boots", this.hasItem(Items.GOLDEN_BOOTS))
                .addCriterion("has_golden_horse_armor", this.hasItem(Items.GOLDEN_HORSE_ARMOR))
                .build(p_200404_1_, "gold_nugget_from_smelting");
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Items.IRON_PICKAXE,
                Items.IRON_SHOVEL,
                Items.IRON_AXE,
                Items.IRON_HOE,
                Items.IRON_SWORD,
                Items.IRON_HELMET,
                Items.IRON_CHESTPLATE,
                Items.IRON_LEGGINGS,
                Items.IRON_BOOTS,
                Items.IRON_HORSE_ARMOR,
                Items.CHAINMAIL_HELMET,
                Items.CHAINMAIL_CHESTPLATE,
                Items.CHAINMAIL_LEGGINGS,
                Items.CHAINMAIL_BOOTS), Items.IRON_NUGGET, 0.1F, 200)
                .addCriterion("has_iron_pickaxe", this.hasItem(Items.IRON_PICKAXE))
                .addCriterion("has_iron_shovel", this.hasItem(Items.IRON_SHOVEL))
                .addCriterion("has_iron_axe", this.hasItem(Items.IRON_AXE))
                .addCriterion("has_iron_hoe", this.hasItem(Items.IRON_HOE))
                .addCriterion("has_iron_sword", this.hasItem(Items.IRON_SWORD))
                .addCriterion("has_iron_helmet", this.hasItem(Items.IRON_HELMET))
                .addCriterion("has_iron_chestplate", this.hasItem(Items.IRON_CHESTPLATE))
                .addCriterion("has_iron_leggings", this.hasItem(Items.IRON_LEGGINGS))
                .addCriterion("has_iron_boots", this.hasItem(Items.IRON_BOOTS))
                .addCriterion("has_iron_horse_armor", this.hasItem(Items.IRON_HORSE_ARMOR))
                .addCriterion("has_chainmail_helmet", this.hasItem(Items.CHAINMAIL_HELMET))
                .addCriterion("has_chainmail_chestplate", this.hasItem(Items.CHAINMAIL_CHESTPLATE))
                .addCriterion("has_chainmail_leggings", this.hasItem(Items.CHAINMAIL_LEGGINGS))
                .addCriterion("has_chainmail_boots", this.hasItem(Items.CHAINMAIL_BOOTS))
                .build(p_200404_1_, "iron_nugget_from_smelting");
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.IRON_ORE.asItem()), Items.IRON_INGOT, 0.7F, 200)
                .addCriterion("has_iron_ore", this.hasItem(Blocks.IRON_ORE.asItem()))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.CLAY), Blocks.TERRACOTTA.asItem(), 0.35F, 200)
                .addCriterion("has_clay_block", this.hasItem(Blocks.CLAY))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.NETHERRACK), Items.NETHER_BRICK, 0.1F, 200)
                .addCriterion("has_netherrack", this.hasItem(Blocks.NETHERRACK))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.NETHER_QUARTZ_ORE), Items.QUARTZ, 0.2F, 200)
                .addCriterion("has_nether_quartz_ore", this.hasItem(Blocks.NETHER_QUARTZ_ORE))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.REDSTONE_ORE), Items.REDSTONE, 0.7F, 200)
                .addCriterion("has_redstone_ore", this.hasItem(Blocks.REDSTONE_ORE))
                .build(p_200404_1_, "redstone_from_smelting");
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.WET_SPONGE), Blocks.SPONGE.asItem(), 0.15F, 200)
                .addCriterion("has_wet_sponge", this.hasItem(Blocks.WET_SPONGE))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.COBBLESTONE), Blocks.STONE.asItem(), 0.1F, 200)
                .addCriterion("has_cobblestone", this.hasItem(Blocks.COBBLESTONE))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.STONE_BRICKS),
                Blocks.CRACKED_STONE_BRICKS.asItem(),
                0.1F,
                200).addCriterion("has_stone_bricks", this.hasItem(Blocks.STONE_BRICKS)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.BLACK_TERRACOTTA),
                Blocks.BLACK_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200).addCriterion("has_black_terracotta", this.hasItem(Blocks.BLACK_TERRACOTTA)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.BLUE_TERRACOTTA),
                Blocks.BLUE_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200).addCriterion("has_blue_terracotta", this.hasItem(Blocks.BLUE_TERRACOTTA)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.BROWN_TERRACOTTA),
                Blocks.BROWN_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200).addCriterion("has_brown_terracotta", this.hasItem(Blocks.BROWN_TERRACOTTA)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.CYAN_TERRACOTTA),
                Blocks.CYAN_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200).addCriterion("has_cyan_terracotta", this.hasItem(Blocks.CYAN_TERRACOTTA)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.GRAY_TERRACOTTA),
                Blocks.GRAY_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200).addCriterion("has_gray_terracotta", this.hasItem(Blocks.GRAY_TERRACOTTA)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.GREEN_TERRACOTTA),
                Blocks.GREEN_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200).addCriterion("has_green_terracotta", this.hasItem(Blocks.GREEN_TERRACOTTA)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.LIGHT_BLUE_TERRACOTTA),
                Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200)
                .addCriterion("has_light_blue_terracotta", this.hasItem(Blocks.LIGHT_BLUE_TERRACOTTA))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.LIGHT_GRAY_TERRACOTTA),
                Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200)
                .addCriterion("has_light_gray_terracotta", this.hasItem(Blocks.LIGHT_GRAY_TERRACOTTA))
                .build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.LIME_TERRACOTTA),
                Blocks.LIME_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200).addCriterion("has_lime_terracotta", this.hasItem(Blocks.LIME_TERRACOTTA)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.MAGENTA_TERRACOTTA),
                Blocks.MAGENTA_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200).addCriterion("has_magenta_terracotta", this.hasItem(Blocks.MAGENTA_TERRACOTTA)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.ORANGE_TERRACOTTA),
                Blocks.ORANGE_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200).addCriterion("has_orange_terracotta", this.hasItem(Blocks.ORANGE_TERRACOTTA)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.PINK_TERRACOTTA),
                Blocks.PINK_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200).addCriterion("has_pink_terracotta", this.hasItem(Blocks.PINK_TERRACOTTA)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.PURPLE_TERRACOTTA),
                Blocks.PURPLE_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200).addCriterion("has_purple_terracotta", this.hasItem(Blocks.PURPLE_TERRACOTTA)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.RED_TERRACOTTA),
                Blocks.RED_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200).addCriterion("has_red_terracotta", this.hasItem(Blocks.RED_TERRACOTTA)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.WHITE_TERRACOTTA),
                Blocks.WHITE_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200).addCriterion("has_white_terracotta", this.hasItem(Blocks.WHITE_TERRACOTTA)).build(p_200404_1_);
        FurnaceRecipeBuilder.furnaceRecipe(Ingredient.fromItems(Blocks.YELLOW_TERRACOTTA),
                Blocks.YELLOW_GLAZED_TERRACOTTA.asItem(),
                0.1F,
                200).addCriterion("has_yellow_terracotta", this.hasItem(Blocks.YELLOW_TERRACOTTA)).build(p_200404_1_);

        //MITE Recipes Start
        {
            //MITE Tools
            {
                ShapedRecipeBuilder.shapedRecipe(Items.FLINT_AXE)
                        .key('#', Items.STICK)
                        .key('X', Items.FLINT)
                        .patternLine("XX")
                        .patternLine("X#")
                        .patternLine(" #")
                        .addCriterion("has_flint", this.hasItem(Items.FLINT))
                        .build(p_200404_1_);
                ShapedRecipeBuilder.shapedRecipe(Items.FLINT_SHOVEL)
                        .key('#', Items.STICK)
                        .key('X', Items.FLINT)
                        .patternLine("X")
                        .patternLine("#")
                        .patternLine("#")
                        .addCriterion("has_flint", this.hasItem(Items.FLINT))
                        .build(p_200404_1_);
                ShapedRecipeBuilder.shapedRecipe(Items.FLINT_HATCHET)
                        .key('S', Items.STICK)
                        .key('F', Items.FLINT)
                        .patternLine("SF")
                        .addCriterion("has_flint", this.hasItem(Items.FLINT))
                        .build(p_200404_1_);
                //AXES
                {
                    ShapedRecipeBuilder.shapedRecipe(Items.COPPER_AXE)
                            .key('M',Items.COPPER_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MM")
                            .patternLine("MS")
                            .patternLine(" S")
                            .addCriterion("has_martial",this.hasItem(Items.COPPER_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.SILVER_AXE)
                            .key('M',Items.SILVER_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MM")
                            .patternLine("MS")
                            .patternLine(" S")
                            .addCriterion("has_martial",this.hasItem(Items.SILVER_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.ANCIENT_METAL_AXE)
                            .key('M',Items.ANCIENT_METAL_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MM")
                            .patternLine("MS")
                            .patternLine(" S")
                            .addCriterion("has_martial",this.hasItem(Items.ANCIENT_METAL_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.MITHRIL_AXE)
                            .key('M',Items.MITHRIL_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MM")
                            .patternLine("MS")
                            .patternLine(" S")
                            .addCriterion("has_martial",this.hasItem(Items.MITHRIL_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.TUNGSTEN_AXE)
                            .key('M',Items.TUNGSTEN_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MM")
                            .patternLine("MS")
                            .patternLine(" S")
                            .addCriterion("has_martial",this.hasItem(Items.TUNGSTEN_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.ADAMANTIUM_AXE)
                            .key('M',Items.ADAMANTIUM_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MM")
                            .patternLine("MS")
                            .patternLine(" S")
                            .addCriterion("has_martial",this.hasItem(Items.ADAMANTIUM_INGOT))
                            .build(p_200404_1_);
                }
                //SHOVEL
                {
                    ShapedRecipeBuilder.shapedRecipe(Items.COPPER_SHOVEL)
                            .key('M',Items.COPPER_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("M")
                            .patternLine("S")
                            .patternLine("S")
                            .addCriterion("has_martial",this.hasItem(Items.COPPER_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.SILVER_SHOVEL)
                            .key('M',Items.SILVER_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("M")
                            .patternLine("S")
                            .patternLine("S")
                            .addCriterion("has_martial",this.hasItem(Items.SILVER_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.ANCIENT_METAL_SHOVEL)
                            .key('M',Items.ANCIENT_METAL_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("M")
                            .patternLine("S")
                            .patternLine("S")
                            .addCriterion("has_martial",this.hasItem(Items.ANCIENT_METAL_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.MITHRIL_SHOVEL)
                            .key('M',Items.MITHRIL_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("M")
                            .patternLine("S")
                            .patternLine("S")
                            .addCriterion("has_martial",this.hasItem(Items.MITHRIL_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.TUNGSTEN_SHOVEL)
                            .key('M',Items.TUNGSTEN_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("M")
                            .patternLine("S")
                            .patternLine("S")
                            .addCriterion("has_martial",this.hasItem(Items.TUNGSTEN_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.ADAMANTIUM_SHOVEL)
                            .key('M',Items.ADAMANTIUM_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("M")
                            .patternLine("S")
                            .patternLine("S")
                            .addCriterion("has_martial",this.hasItem(Items.ADAMANTIUM_INGOT))
                            .build(p_200404_1_);
                }
                //SWORDS
                {
                    ShapedRecipeBuilder.shapedRecipe(Items.COPPER_SWORD)
                            .key('M',Items.COPPER_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("M")
                            .patternLine("M")
                            .patternLine("S")
                            .addCriterion("has_martial",this.hasItem(Items.COPPER_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.SILVER_SWORD)
                            .key('M',Items.SILVER_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("M")
                            .patternLine("M")
                            .patternLine("S")
                            .addCriterion("has_martial",this.hasItem(Items.SILVER_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.ANCIENT_METAL_SWORD)
                            .key('M',Items.ANCIENT_METAL_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("M")
                            .patternLine("M")
                            .patternLine("S")
                            .addCriterion("has_martial",this.hasItem(Items.ANCIENT_METAL_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.MITHRIL_SWORD)
                            .key('M',Items.MITHRIL_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("M")
                            .patternLine("M")
                            .patternLine("S")
                            .addCriterion("has_martial",this.hasItem(Items.MITHRIL_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.TUNGSTEN_SWORD)
                            .key('M',Items.TUNGSTEN_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("M")
                            .patternLine("M")
                            .patternLine("S")
                            .addCriterion("has_martial",this.hasItem(Items.TUNGSTEN_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.ADAMANTIUM_SWORD)
                            .key('M',Items.ADAMANTIUM_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("M")
                            .patternLine("M")
                            .patternLine("S")
                            .addCriterion("has_martial",this.hasItem(Items.ADAMANTIUM_INGOT))
                            .build(p_200404_1_);
                }
                //PICKAXES
                {
                    ShapedRecipeBuilder.shapedRecipe(Items.COPPER_PICKAXE)
                        .key('M',Items.COPPER_INGOT)
                        .key('S',Items.STICK)
                        .patternLine("MMM")
                        .patternLine(" S ")
                        .patternLine(" S ")
                        .addCriterion("has_martial",this.hasItem(Items.COPPER_INGOT))
                        .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.SILVER_PICKAXE)
                            .key('M',Items.SILVER_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MMM")
                            .patternLine(" S ")
                            .patternLine(" S ")
                            .addCriterion("has_martial",this.hasItem(Items.SILVER_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.ANCIENT_METAL_PICKAXE)
                            .key('M',Items.ANCIENT_METAL_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MMM")
                            .patternLine(" S ")
                            .patternLine(" S ")
                            .addCriterion("has_martial",this.hasItem(Items.ANCIENT_METAL_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.MITHRIL_PICKAXE)
                            .key('M',Items.MITHRIL_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MMM")
                            .patternLine(" S ")
                            .patternLine(" S ")
                            .addCriterion("has_martial",this.hasItem(Items.MITHRIL_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.TUNGSTEN_PICKAXE)
                            .key('M',Items.TUNGSTEN_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MMM")
                            .patternLine(" S ")
                            .patternLine(" S ")
                            .addCriterion("has_martial",this.hasItem(Items.TUNGSTEN_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.ADAMANTIUM_PICKAXE)
                            .key('M',Items.ADAMANTIUM_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MMM")
                            .patternLine(" S ")
                            .patternLine(" S ")
                            .addCriterion("has_martial",this.hasItem(Items.ADAMANTIUM_INGOT))
                            .build(p_200404_1_);
                }
                //HOE
                {
                    ShapedRecipeBuilder.shapedRecipe(Items.COPPER_HOE)
                            .key('M',Items.COPPER_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MM")
                            .patternLine(" S")
                            .patternLine(" S")
                            .addCriterion("has_martial",this.hasItem(Items.COPPER_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.SILVER_HOE)
                            .key('M',Items.SILVER_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MM")
                            .patternLine(" S")
                            .patternLine(" S")
                            .addCriterion("has_martial",this.hasItem(Items.SILVER_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.ANCIENT_METAL_PICKAXE)
                            .key('M',Items.ANCIENT_METAL_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MM")
                            .patternLine(" S")
                            .patternLine(" S")
                            .addCriterion("has_martial",this.hasItem(Items.ANCIENT_METAL_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.MITHRIL_HOE)
                            .key('M',Items.MITHRIL_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MM")
                            .patternLine(" S")
                            .patternLine(" S")
                            .addCriterion("has_martial",this.hasItem(Items.MITHRIL_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.TUNGSTEN_HOE)
                            .key('M',Items.TUNGSTEN_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MM")
                            .patternLine(" S")
                            .patternLine(" S")
                            .addCriterion("has_martial",this.hasItem(Items.TUNGSTEN_INGOT))
                            .build(p_200404_1_);
                    ShapedRecipeBuilder.shapedRecipe(Items.ADAMANTIUM_HOE)
                            .key('M',Items.ADAMANTIUM_INGOT)
                            .key('S',Items.STICK)
                            .patternLine("MM")
                            .patternLine(" S")
                            .patternLine(" S")
                            .addCriterion("has_martial",this.hasItem(Items.ADAMANTIUM_INGOT))
                            .build(p_200404_1_);
                }
            }
            //MITE Foods
            {
                ShapelessRecipeBuilder.shapelessRecipe(Items.SALAD)
                        .addIngredient(Ingredient.fromItems(Blocks.DANDELION), 3)
                        .addIngredient(Ingredient.fromItems(Items.BOWL))
                        .addCriterion("has_dandelion", this.hasItem(Blocks.DANDELION))
                        .addCriterion("has_bowl", this.hasItem(Items.BOWL))
                        .build(p_200404_1_);
            }
            //MITE MARTIAL
            {
                ShapedRecipeBuilder.shapedRecipe(Items.COPPER_INGOT)
                        .key('#', Items.COPPER_NUGGET)
                        .patternLine("###")
                        .patternLine("###")
                        .patternLine("###")
                        .addCriterion("has_at_least_9_copper_nugget",
                                this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.COPPER_NUGGET))
                        .build(p_200404_1_);
                ShapedRecipeBuilder.shapedRecipe(Items.SILVER_INGOT)
                        .key('#', Items.SILVER_NUGGET)
                        .patternLine("###")
                        .patternLine("###")
                        .patternLine("###")
                        .addCriterion("has_at_least_9_silver_nugget",
                                this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.SILVER_NUGGET))
                        .build(p_200404_1_);
                ShapedRecipeBuilder.shapedRecipe(Items.ANCIENT_METAL_INGOT)
                        .key('#', Items.ANCIENT_METAL_NUGGET)
                        .patternLine("###")
                        .patternLine("###")
                        .patternLine("###")
                        .addCriterion("has_at_least_9_ancient_metal_nugget",
                                this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.ANCIENT_METAL_NUGGET))
                        .build(p_200404_1_);
                ShapedRecipeBuilder.shapedRecipe(Items.MITHRIL_INGOT)
                        .key('#', Items.MITHRIL_NUGGET)
                        .patternLine("###")
                        .patternLine("###")
                        .patternLine("###")
                        .addCriterion("has_at_least_9_mithril_nugget",
                                this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.MITHRIL_NUGGET))
                        .build(p_200404_1_);
                ShapedRecipeBuilder.shapedRecipe(Items.TUNGSTEN_INGOT)
                        .key('#', Items.TUNGSTEN_NUGGET)
                        .patternLine("###")
                        .patternLine("###")
                        .patternLine("###")
                        .addCriterion("has_at_least_9_tungsten_nugget",
                                this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.TUNGSTEN_NUGGET))
                        .build(p_200404_1_);
                ShapedRecipeBuilder.shapedRecipe(Items.ADAMANTIUM_INGOT)
                        .key('#', Items.ADAMANTIUM_NUGGET)
                        .patternLine("###")
                        .patternLine("###")
                        .patternLine("###")
                        .addCriterion("has_at_least_9_adamantium_nugget",
                                this.hasItem(MinMaxBounds.IntBound.func_211340_b(9), Items.ADAMANTIUM_NUGGET))
                        .build(p_200404_1_);

                ShapelessRecipeBuilder.shapelessRecipe(Items.COPPER_NUGGET, 9)
                        .addIngredient(Items.COPPER_INGOT)
                        .addCriterion("has_copper_ingot", this.hasItem(Items.COPPER_INGOT))
                        .build(p_200404_1_);
                ShapelessRecipeBuilder.shapelessRecipe(Items.SILVER_NUGGET, 9)
                        .addIngredient(Items.SILVER_INGOT)
                        .addCriterion("has_silver_ingot", this.hasItem(Items.SILVER_INGOT))
                        .build(p_200404_1_);
                ShapelessRecipeBuilder.shapelessRecipe(Items.ANCIENT_METAL_NUGGET, 9)
                        .addIngredient(Items.ANCIENT_METAL_INGOT)
                        .addCriterion("has_ancient_metal_ingot", this.hasItem(Items.ANCIENT_METAL_INGOT))
                        .build(p_200404_1_);
                ShapelessRecipeBuilder.shapelessRecipe(Items.MITHRIL_NUGGET, 9)
                        .addIngredient(Items.MITHRIL_INGOT)
                        .addCriterion("has_mithril_ingot", this.hasItem(Items.MITHRIL_INGOT))
                        .build(p_200404_1_);
                ShapelessRecipeBuilder.shapelessRecipe(Items.TUNGSTEN_NUGGET, 9)
                        .addIngredient(Items.TUNGSTEN_INGOT)
                        .addCriterion("has_tungsten_ingot", this.hasItem(Items.TUNGSTEN_INGOT))
                        .build(p_200404_1_);
                ShapelessRecipeBuilder.shapelessRecipe(Items.ADAMANTIUM_NUGGET, 9)
                        .addIngredient(Items.ADAMANTIUM_INGOT)
                        .addCriterion("has_adamantium_ingot", this.hasItem(Items.ADAMANTIUM_INGOT))
                        .build(p_200404_1_);
            }
        }
    }

    private void saveRecipe(DirectoryCache p_208311_1_, JsonObject p_208311_2_, Path p_208311_3_) {
        try {
            String s = GSON.toJson(p_208311_2_);
            String s1 = HASH_FUNCTION.hashUnencodedChars(s).toString();
            if (!Objects.equals(p_208311_1_.getPreviousHash(p_208311_3_), s1) || !Files.exists(p_208311_3_)) {
                Files.createDirectories(p_208311_3_.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(p_208311_3_)) {
                    bufferedwriter.write(s);
                }
            }

            p_208311_1_.func_208316_a(p_208311_3_, s1);
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't save recipe {}", p_208311_3_, ioexception);
        }

    }

    private void saveRecipeAdvancement(DirectoryCache p_208310_1_, JsonObject p_208310_2_, Path p_208310_3_) {
        try {
            String s = GSON.toJson(p_208310_2_);
            String s1 = HASH_FUNCTION.hashUnencodedChars(s).toString();
            if (!Objects.equals(p_208310_1_.getPreviousHash(p_208310_3_), s1) || !Files.exists(p_208310_3_)) {
                Files.createDirectories(p_208310_3_.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(p_208310_3_)) {
                    bufferedwriter.write(s);
                }
            }

            p_208310_1_.func_208316_a(p_208310_3_, s1);
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't save recipe advancement {}", p_208310_3_, ioexception);
        }

    }
}
