package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BushConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.MinableConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.TallGrassConfig;
import net.minecraft.world.gen.feature.structure.DesertPyramidConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.StrongholdConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.DungeonRoomConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.LakeChanceConfig;
import net.minecraft.world.gen.surfacebuilders.CompositeSurfaceBuilder;

public final class DesertHillsBiome extends Biome {
   public DesertHillsBiome() {
      super((new Biome.BiomeBuilder()).surfaceBuilder(new CompositeSurfaceBuilder(DEFAULT_SURFACE_BUILDER, SAND_SAND_GRAVEL_SURFACE)).precipitation(Biome.RainType.NONE).category(Biome.Category.DESERT).depth(0.45F).scale(0.3F).temperature(2.0F).downfall(0.0F).waterColor(4159204).waterFogColor(329011).parent(
              null));
      this.addStructure(Feature.DESERT_PYRAMID, new DesertPyramidConfig());
      this.addStructure(Feature.MINESHAFT, new MineshaftConfig(0.004D, MineshaftStructure.Type.NORMAL));
      this.addStructure(Feature.STRONGHOLD, new StrongholdConfig());
      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(CAVE_WORLD_CARVER, new ProbabilityConfig(0.14285715F)));
      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(CANYON_WORLD_CARVER, new ProbabilityConfig(0.02F)));
      this.addStructureFeatures();
      this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(Feature.LAKES, new LakesConfig(Blocks.LAVA), LAVA_LAKE, new LakeChanceConfig(80)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, createCompositeFeature(Feature.DUNGEONS, IFeatureConfig.NO_FEATURE_CONFIG, DUNGEON_ROOM, new DungeonRoomConfig(8)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE,
              new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIRT.getDefaultState(), 33), COUNT_RANGE,
              new CountRangeConfig(10, 32, 0, 128)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE,
              new MinableConfig(MinableConfig.IS_ROCK, Blocks.GRAVEL.getDefaultState(), 33), COUNT_RANGE,
              new CountRangeConfig(8, 24, 0, 128)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE,
              new MinableConfig(MinableConfig.IS_ROCK, Blocks.GRANITE.getDefaultState(), 33), COUNT_RANGE,
              new CountRangeConfig(10, 0, 0, 80)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE,
              new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIORITE.getDefaultState(), 33), COUNT_RANGE,
              new CountRangeConfig(10, 0, 0, 80)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE,
              new MinableConfig(MinableConfig.IS_ROCK, Blocks.ANDESITE.getDefaultState(), 33), COUNT_RANGE,
              new CountRangeConfig(10, 0, 0, 80)));

      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE,
              new MinableConfig(MinableConfig.IS_ROCK, Blocks.COAL_ORE.getDefaultState(), 16), COUNT_RANGE,
              new CountRangeConfig(15, 16, 0, 96)));

      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE,
              new MinableConfig(MinableConfig.IS_ROCK, Blocks.COPPER_ORE.getDefaultState(), 6), RANDOM_COUNT_WITH_RANGE,
              new CountRangeConfig(20, 0, 0, 128)));

      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE,
              new MinableConfig(MinableConfig.IS_ROCK, Blocks.SILVER_ORE.getDefaultState(), 6), RANDOM_COUNT_WITH_RANGE,
              new CountRangeConfig(18, 0, 0, 96)));

      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE,
              new MinableConfig(MinableConfig.IS_ROCK, Blocks.IRON_ORE.getDefaultState(), 6), COUNT_RANGE,
              new CountRangeConfig(10, 0, 0, 64)));

      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE,
              new MinableConfig(MinableConfig.IS_ROCK, Blocks.GOLD_ORE.getDefaultState(), 4), COUNT_RANGE,
              new CountRangeConfig(9, 0, 0, 48)));

      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE,
              new MinableConfig(MinableConfig.IS_ROCK, Blocks.REDSTONE_ORE.getDefaultState(), 5), RANDOM_COUNT_WITH_RANGE,
              new CountRangeConfig(8, 0, 0, 24)));

      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE,
              new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIAMOND_ORE.getDefaultState(), 6), RANDOM_COUNT_WITH_RANGE,
              new CountRangeConfig(2, 0, 0, 32)));

      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE,
              new MinableConfig(MinableConfig.IS_ROCK, Blocks.LAPIS_ORE.getDefaultState(), 3), DEPTH_AVERAGE,
              new DepthAverageConfig(1, 16, 16)));

      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE,
              new MinableConfig(MinableConfig.IS_ROCK, Blocks.MITHRIL_ORE.getDefaultState(), 3), RANDOM_COUNT_WITH_RANGE,
              new CountRangeConfig(2, 0, 0, 32)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.SAND, 7, 2, Lists.newArrayList(
              Blocks.DIRT, Blocks.GRASS_BLOCK)), TOP_SOLID, new FrequencyConfig(3)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.CLAY, 4, 1, Lists.newArrayList(
              Blocks.DIRT, Blocks.CLAY)), TOP_SOLID, new FrequencyConfig(1)));
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFlowerFeature(Feature.DEFAULT_FLOWERS, SURFACE_PLUS_32, new FrequencyConfig(2)));
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.TALL_GRASS, new TallGrassConfig(Blocks.GRASS.getDefaultState()), TWICE_SURFACE, new FrequencyConfig(1)));
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.DEAD_BUSH, IFeatureConfig.NO_FEATURE_CONFIG, TWICE_SURFACE, new FrequencyConfig(2)));
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM), TWICE_SURFACE_WITH_CHANCE, new ChanceConfig(4)));
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM), TWICE_SURFACE_WITH_CHANCE, new ChanceConfig(8)));
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.REED, IFeatureConfig.NO_FEATURE_CONFIG, TWICE_SURFACE, new FrequencyConfig(60)));
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.PUMPKIN, IFeatureConfig.NO_FEATURE_CONFIG, TWICE_SURFACE_WITH_CHANCE, new ChanceConfig(32)));
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.CACTUS, IFeatureConfig.NO_FEATURE_CONFIG, TWICE_SURFACE, new FrequencyConfig(10)));
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.WATER), HEIGHT_BIASED_RANGE, new CountRangeConfig(50, 8, 8, 256)));
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.LIQUIDS, new LiquidsConfig(Fluids.LAVA), HEIGHT_VERY_BIASED_RANGE, new CountRangeConfig(20, 8, 16, 256)));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(Feature.DESERT_WELLS, IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_WITH_CHANCE, new ChanceConfig(1000)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, createCompositeFeature(Feature.FOSSILS, IFeatureConfig.NO_FEATURE_CONFIG, WITH_CHANCE, new ChanceConfig(64)));
      this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, createCompositeFeature(Feature.ICE_AND_SNOW, IFeatureConfig.NO_FEATURE_CONFIG, PASSTHROUGH, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addSpawn(EnumCreatureType.CREATURE, new Biome.SpawnListEntry(EntityType.RABBIT, 4, 2, 3));
      this.addSpawn(EnumCreatureType.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 19, 4, 4));
      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 1, 1, 1));
      this.addSpawn(EnumCreatureType.MONSTER, new Biome.SpawnListEntry(EntityType.HUSK, 80, 4, 4));
   }
}
