package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class OceanMonumentPieces {
   public static void registerOceanMonumentPieces() {
      StructureIO.registerStructureComponent(OceanMonumentPieces.MonumentBuilding.class, "OMB");
      StructureIO.registerStructureComponent(OceanMonumentPieces.MonumentCoreRoom.class, "OMCR");
      StructureIO.registerStructureComponent(OceanMonumentPieces.DoubleXRoom.class, "OMDXR");
      StructureIO.registerStructureComponent(OceanMonumentPieces.DoubleXYRoom.class, "OMDXYR");
      StructureIO.registerStructureComponent(OceanMonumentPieces.DoubleYRoom.class, "OMDYR");
      StructureIO.registerStructureComponent(OceanMonumentPieces.DoubleYZRoom.class, "OMDYZR");
      StructureIO.registerStructureComponent(OceanMonumentPieces.DoubleZRoom.class, "OMDZR");
      StructureIO.registerStructureComponent(OceanMonumentPieces.EntryRoom.class, "OMEntry");
      StructureIO.registerStructureComponent(OceanMonumentPieces.Penthouse.class, "OMPenthouse");
      StructureIO.registerStructureComponent(OceanMonumentPieces.SimpleRoom.class, "OMSimple");
      StructureIO.registerStructureComponent(OceanMonumentPieces.SimpleTopRoom.class, "OMSimpleT");
   }

   public static class DoubleXRoom extends OceanMonumentPieces.Piece {
      public DoubleXRoom() {
      }

      public DoubleXRoom(EnumFacing p_i45597_1_, OceanMonumentPieces.RoomDefinition p_i45597_2_, Random p_i45597_3_) {
         super(1, p_i45597_1_, p_i45597_2_, 2, 1, 1);
      }

      public boolean addComponentParts(IWorld p_74875_1_, Random p_74875_2_, MutableBoundingBox p_74875_3_, ChunkPos p_74875_4_) {
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = this.roomDefinition.connections[EnumFacing.EAST.getIndex()];
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition1 = this.roomDefinition;
         if (this.roomDefinition.index / 25 > 0) {
            this.generateDefaultFloor(p_74875_1_, p_74875_3_, 8, 0, oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.DOWN.getIndex()]);
            this.generateDefaultFloor(p_74875_1_, p_74875_3_, 0, 0, oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.DOWN.getIndex()]);
         }

         if (oceanmonumentpieces$roomdefinition1.connections[EnumFacing.UP.getIndex()] == null) {
            this.generateBoxOnFillOnly(p_74875_1_, p_74875_3_, 1, 4, 1, 7, 4, 6, ROUGH_PRISMARINE);
         }

         if (oceanmonumentpieces$roomdefinition.connections[EnumFacing.UP.getIndex()] == null) {
            this.generateBoxOnFillOnly(p_74875_1_, p_74875_3_, 8, 4, 1, 14, 4, 6, ROUGH_PRISMARINE);
         }

         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, 0, 0, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 15, 3, 0, 15, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 0, 15, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 7, 14, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 15, 2, 0, 15, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 0, 15, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 7, 14, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 15, 1, 0, 15, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 0, 15, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 7, 14, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 0, 10, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 2, 0, 9, 2, 3, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 3, 0, 10, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.setBlockState(p_74875_1_, SEA_LANTERN, 6, 2, 3, p_74875_3_);
         this.setBlockState(p_74875_1_, SEA_LANTERN, 9, 2, 3, p_74875_3_);
         if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.SOUTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 3, 1, 0, 4, 2, 0);
         }

         if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.NORTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 3, 1, 7, 4, 2, 7);
         }

         if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.WEST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 0, 1, 3, 0, 2, 4);
         }

         if (oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.SOUTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 11, 1, 0, 12, 2, 0);
         }

         if (oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.NORTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 11, 1, 7, 12, 2, 7);
         }

         if (oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.EAST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 15, 1, 3, 15, 2, 4);
         }

         return true;
      }
   }

   public static class DoubleXYRoom extends OceanMonumentPieces.Piece {
      public DoubleXYRoom() {
      }

      public DoubleXYRoom(EnumFacing p_i45596_1_, OceanMonumentPieces.RoomDefinition p_i45596_2_, Random p_i45596_3_) {
         super(1, p_i45596_1_, p_i45596_2_, 2, 2, 1);
      }

      public boolean addComponentParts(IWorld p_74875_1_, Random p_74875_2_, MutableBoundingBox p_74875_3_, ChunkPos p_74875_4_) {
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = this.roomDefinition.connections[EnumFacing.EAST.getIndex()];
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition1 = this.roomDefinition;
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition2 = oceanmonumentpieces$roomdefinition1.connections[EnumFacing.UP.getIndex()];
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition3 = oceanmonumentpieces$roomdefinition.connections[EnumFacing.UP.getIndex()];
         if (this.roomDefinition.index / 25 > 0) {
            this.generateDefaultFloor(p_74875_1_, p_74875_3_, 8, 0, oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.DOWN.getIndex()]);
            this.generateDefaultFloor(p_74875_1_, p_74875_3_, 0, 0, oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.DOWN.getIndex()]);
         }

         if (oceanmonumentpieces$roomdefinition2.connections[EnumFacing.UP.getIndex()] == null) {
            this.generateBoxOnFillOnly(p_74875_1_, p_74875_3_, 1, 8, 1, 7, 8, 6, ROUGH_PRISMARINE);
         }

         if (oceanmonumentpieces$roomdefinition3.connections[EnumFacing.UP.getIndex()] == null) {
            this.generateBoxOnFillOnly(p_74875_1_, p_74875_3_, 8, 8, 1, 14, 8, 6, ROUGH_PRISMARINE);
         }

         for(int i = 1; i <= 7; ++i) {
            IBlockState iblockstate = BRICKS_PRISMARINE;
            if (i == 2 || i == 6) {
               iblockstate = ROUGH_PRISMARINE;
            }

            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, i, 0, 0, i, 7, iblockstate, iblockstate, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 15, i, 0, 15, i, 7, iblockstate, iblockstate, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, i, 0, 15, i, 0, iblockstate, iblockstate, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, i, 7, 14, i, 7, iblockstate, iblockstate, false);
         }

         this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, 1, 3, 2, 7, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 2, 4, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 5, 4, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 13, 1, 3, 13, 7, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 11, 1, 2, 12, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 11, 1, 5, 12, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 3, 5, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 10, 1, 3, 10, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 7, 2, 10, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 5, 2, 5, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 10, 5, 2, 10, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 5, 5, 5, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 10, 5, 5, 10, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 6, 6, 2, p_74875_3_);
         this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 9, 6, 2, p_74875_3_);
         this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 6, 6, 5, p_74875_3_);
         this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 9, 6, 5, p_74875_3_);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 4, 3, 6, 4, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 9, 4, 3, 10, 4, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.setBlockState(p_74875_1_, SEA_LANTERN, 5, 4, 2, p_74875_3_);
         this.setBlockState(p_74875_1_, SEA_LANTERN, 5, 4, 5, p_74875_3_);
         this.setBlockState(p_74875_1_, SEA_LANTERN, 10, 4, 2, p_74875_3_);
         this.setBlockState(p_74875_1_, SEA_LANTERN, 10, 4, 5, p_74875_3_);
         if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.SOUTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 3, 1, 0, 4, 2, 0);
         }

         if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.NORTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 3, 1, 7, 4, 2, 7);
         }

         if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.WEST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 0, 1, 3, 0, 2, 4);
         }

         if (oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.SOUTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 11, 1, 0, 12, 2, 0);
         }

         if (oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.NORTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 11, 1, 7, 12, 2, 7);
         }

         if (oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.EAST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 15, 1, 3, 15, 2, 4);
         }

         if (oceanmonumentpieces$roomdefinition2.hasOpening[EnumFacing.SOUTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 3, 5, 0, 4, 6, 0);
         }

         if (oceanmonumentpieces$roomdefinition2.hasOpening[EnumFacing.NORTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 3, 5, 7, 4, 6, 7);
         }

         if (oceanmonumentpieces$roomdefinition2.hasOpening[EnumFacing.WEST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 0, 5, 3, 0, 6, 4);
         }

         if (oceanmonumentpieces$roomdefinition3.hasOpening[EnumFacing.SOUTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 11, 5, 0, 12, 6, 0);
         }

         if (oceanmonumentpieces$roomdefinition3.hasOpening[EnumFacing.NORTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 11, 5, 7, 12, 6, 7);
         }

         if (oceanmonumentpieces$roomdefinition3.hasOpening[EnumFacing.EAST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 15, 5, 3, 15, 6, 4);
         }

         return true;
      }
   }

   public static class DoubleYRoom extends OceanMonumentPieces.Piece {
      public DoubleYRoom() {
      }

      public DoubleYRoom(EnumFacing p_i45595_1_, OceanMonumentPieces.RoomDefinition p_i45595_2_, Random p_i45595_3_) {
         super(1, p_i45595_1_, p_i45595_2_, 1, 2, 1);
      }

      public boolean addComponentParts(IWorld p_74875_1_, Random p_74875_2_, MutableBoundingBox p_74875_3_, ChunkPos p_74875_4_) {
         if (this.roomDefinition.index / 25 > 0) {
            this.generateDefaultFloor(p_74875_1_, p_74875_3_, 0, 0, this.roomDefinition.hasOpening[EnumFacing.DOWN.getIndex()]);
         }

         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = this.roomDefinition.connections[EnumFacing.UP.getIndex()];
         if (oceanmonumentpieces$roomdefinition.connections[EnumFacing.UP.getIndex()] == null) {
            this.generateBoxOnFillOnly(p_74875_1_, p_74875_3_, 1, 8, 1, 6, 8, 6, ROUGH_PRISMARINE);
         }

         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 4, 0, 0, 4, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 4, 0, 7, 4, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 4, 0, 6, 4, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 4, 7, 6, 4, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, 4, 1, 2, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 4, 2, 1, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 4, 1, 5, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 4, 2, 6, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, 4, 5, 2, 4, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 4, 5, 1, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 4, 5, 5, 4, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 4, 5, 6, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition1 = this.roomDefinition;

         for(int i = 1; i <= 5; i += 4) {
            int j = 0;
            if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.SOUTH.getIndex()]) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, i, j, 2, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, i, j, 5, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, i + 2, j, 4, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            } else {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, i, j, 7, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, i + 1, j, 7, i + 1, j, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            }

            j = 7;
            if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.NORTH.getIndex()]) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, i, j, 2, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, i, j, 5, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, i + 2, j, 4, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            } else {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, i, j, 7, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, i + 1, j, 7, i + 1, j, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            }

            int k = 0;
            if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.WEST.getIndex()]) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, k, i, 2, k, i + 2, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, k, i, 5, k, i + 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, k, i + 2, 3, k, i + 2, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            } else {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, k, i, 0, k, i + 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, k, i + 1, 0, k, i + 1, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            }

            k = 7;
            if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.EAST.getIndex()]) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, k, i, 2, k, i + 2, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, k, i, 5, k, i + 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, k, i + 2, 3, k, i + 2, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            } else {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, k, i, 0, k, i + 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, k, i + 1, 0, k, i + 1, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            }

            oceanmonumentpieces$roomdefinition1 = oceanmonumentpieces$roomdefinition;
         }

         return true;
      }
   }

   public static class DoubleYZRoom extends OceanMonumentPieces.Piece {
      public DoubleYZRoom() {
      }

      public DoubleYZRoom(EnumFacing p_i45594_1_, OceanMonumentPieces.RoomDefinition p_i45594_2_, Random p_i45594_3_) {
         super(1, p_i45594_1_, p_i45594_2_, 1, 2, 2);
      }

      public boolean addComponentParts(IWorld p_74875_1_, Random p_74875_2_, MutableBoundingBox p_74875_3_, ChunkPos p_74875_4_) {
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = this.roomDefinition.connections[EnumFacing.NORTH.getIndex()];
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition1 = this.roomDefinition;
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition2 = oceanmonumentpieces$roomdefinition.connections[EnumFacing.UP.getIndex()];
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition3 = oceanmonumentpieces$roomdefinition1.connections[EnumFacing.UP.getIndex()];
         if (this.roomDefinition.index / 25 > 0) {
            this.generateDefaultFloor(p_74875_1_, p_74875_3_, 0, 8, oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.DOWN.getIndex()]);
            this.generateDefaultFloor(p_74875_1_, p_74875_3_, 0, 0, oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.DOWN.getIndex()]);
         }

         if (oceanmonumentpieces$roomdefinition3.connections[EnumFacing.UP.getIndex()] == null) {
            this.generateBoxOnFillOnly(p_74875_1_, p_74875_3_, 1, 8, 1, 6, 8, 7, ROUGH_PRISMARINE);
         }

         if (oceanmonumentpieces$roomdefinition2.connections[EnumFacing.UP.getIndex()] == null) {
            this.generateBoxOnFillOnly(p_74875_1_, p_74875_3_, 1, 8, 8, 6, 8, 14, ROUGH_PRISMARINE);
         }

         for(int i = 1; i <= 7; ++i) {
            IBlockState iblockstate = BRICKS_PRISMARINE;
            if (i == 2 || i == 6) {
               iblockstate = ROUGH_PRISMARINE;
            }

            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, i, 0, 0, i, 15, iblockstate, iblockstate, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, i, 0, 7, i, 15, iblockstate, iblockstate, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, i, 0, 6, i, 0, iblockstate, iblockstate, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, i, 15, 6, i, 15, iblockstate, iblockstate, false);
         }

         for(int j = 1; j <= 7; ++j) {
            IBlockState iblockstate1 = DARK_PRISMARINE;
            if (j == 2 || j == 6) {
               iblockstate1 = SEA_LANTERN;
            }

            this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, j, 7, 4, j, 8, iblockstate1, iblockstate1, false);
         }

         if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.SOUTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 3, 1, 0, 4, 2, 0);
         }

         if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.EAST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 7, 1, 3, 7, 2, 4);
         }

         if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.WEST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 0, 1, 3, 0, 2, 4);
         }

         if (oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.NORTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 3, 1, 15, 4, 2, 15);
         }

         if (oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.WEST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 0, 1, 11, 0, 2, 12);
         }

         if (oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.EAST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 7, 1, 11, 7, 2, 12);
         }

         if (oceanmonumentpieces$roomdefinition3.hasOpening[EnumFacing.SOUTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 3, 5, 0, 4, 6, 0);
         }

         if (oceanmonumentpieces$roomdefinition3.hasOpening[EnumFacing.EAST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 7, 5, 3, 7, 6, 4);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 4, 2, 6, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 1, 2, 6, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 1, 5, 6, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         }

         if (oceanmonumentpieces$roomdefinition3.hasOpening[EnumFacing.WEST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 0, 5, 3, 0, 6, 4);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 4, 2, 2, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 2, 1, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 5, 1, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         }

         if (oceanmonumentpieces$roomdefinition2.hasOpening[EnumFacing.NORTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 3, 5, 15, 4, 6, 15);
         }

         if (oceanmonumentpieces$roomdefinition2.hasOpening[EnumFacing.WEST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 0, 5, 11, 0, 6, 12);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 4, 10, 2, 4, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 10, 1, 3, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 13, 1, 3, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         }

         if (oceanmonumentpieces$roomdefinition2.hasOpening[EnumFacing.EAST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 7, 5, 11, 7, 6, 12);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 4, 10, 6, 4, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 1, 10, 6, 3, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 1, 13, 6, 3, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         }

         return true;
      }
   }

   public static class DoubleZRoom extends OceanMonumentPieces.Piece {
      public DoubleZRoom() {
      }

      public DoubleZRoom(EnumFacing p_i45593_1_, OceanMonumentPieces.RoomDefinition p_i45593_2_, Random p_i45593_3_) {
         super(1, p_i45593_1_, p_i45593_2_, 1, 1, 2);
      }

      public boolean addComponentParts(IWorld p_74875_1_, Random p_74875_2_, MutableBoundingBox p_74875_3_, ChunkPos p_74875_4_) {
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = this.roomDefinition.connections[EnumFacing.NORTH.getIndex()];
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition1 = this.roomDefinition;
         if (this.roomDefinition.index / 25 > 0) {
            this.generateDefaultFloor(p_74875_1_, p_74875_3_, 0, 8, oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.DOWN.getIndex()]);
            this.generateDefaultFloor(p_74875_1_, p_74875_3_, 0, 0, oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.DOWN.getIndex()]);
         }

         if (oceanmonumentpieces$roomdefinition1.connections[EnumFacing.UP.getIndex()] == null) {
            this.generateBoxOnFillOnly(p_74875_1_, p_74875_3_, 1, 4, 1, 6, 4, 7, ROUGH_PRISMARINE);
         }

         if (oceanmonumentpieces$roomdefinition.connections[EnumFacing.UP.getIndex()] == null) {
            this.generateBoxOnFillOnly(p_74875_1_, p_74875_3_, 1, 4, 8, 6, 4, 14, ROUGH_PRISMARINE);
         }

         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, 0, 0, 3, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 3, 0, 7, 3, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 0, 7, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 15, 6, 3, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 2, 15, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 2, 0, 7, 2, 15, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 0, 7, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 15, 6, 2, 15, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 0, 1, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 1, 0, 7, 1, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 0, 7, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 15, 6, 1, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 1, 1, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 1, 1, 6, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 1, 1, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 3, 1, 6, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 13, 1, 1, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 1, 13, 6, 1, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 13, 1, 3, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 3, 13, 6, 3, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, 1, 6, 2, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 6, 5, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, 1, 9, 2, 3, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 9, 5, 3, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 2, 6, 4, 2, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 2, 9, 4, 2, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, 2, 7, 2, 2, 8, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 2, 7, 5, 2, 8, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.setBlockState(p_74875_1_, SEA_LANTERN, 2, 2, 5, p_74875_3_);
         this.setBlockState(p_74875_1_, SEA_LANTERN, 5, 2, 5, p_74875_3_);
         this.setBlockState(p_74875_1_, SEA_LANTERN, 2, 2, 10, p_74875_3_);
         this.setBlockState(p_74875_1_, SEA_LANTERN, 5, 2, 10, p_74875_3_);
         this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 2, 3, 5, p_74875_3_);
         this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 5, 3, 5, p_74875_3_);
         this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 2, 3, 10, p_74875_3_);
         this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 5, 3, 10, p_74875_3_);
         if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.SOUTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 3, 1, 0, 4, 2, 0);
         }

         if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.EAST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 7, 1, 3, 7, 2, 4);
         }

         if (oceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.WEST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 0, 1, 3, 0, 2, 4);
         }

         if (oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.NORTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 3, 1, 15, 4, 2, 15);
         }

         if (oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.WEST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 0, 1, 11, 0, 2, 12);
         }

         if (oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.EAST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 7, 1, 11, 7, 2, 12);
         }

         return true;
      }
   }

   public static class EntryRoom extends OceanMonumentPieces.Piece {
      public EntryRoom() {
      }

      public EntryRoom(EnumFacing p_i45592_1_, OceanMonumentPieces.RoomDefinition p_i45592_2_) {
         super(1, p_i45592_1_, p_i45592_2_, 1, 1, 1);
      }

      public boolean addComponentParts(IWorld p_74875_1_, Random p_74875_2_, MutableBoundingBox p_74875_3_, ChunkPos p_74875_4_) {
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, 0, 2, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 3, 0, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 1, 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 2, 0, 7, 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 1, 0, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 7, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 0, 2, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         if (this.roomDefinition.hasOpening[EnumFacing.NORTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 3, 1, 7, 4, 2, 7);
         }

         if (this.roomDefinition.hasOpening[EnumFacing.WEST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 0, 1, 3, 1, 2, 4);
         }

         if (this.roomDefinition.hasOpening[EnumFacing.EAST.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 6, 1, 3, 7, 2, 4);
         }

         return true;
      }
   }

   static class FitSimpleRoomHelper implements OceanMonumentPieces.MonumentRoomFitHelper {
      private FitSimpleRoomHelper() {
      }

      public boolean fits(OceanMonumentPieces.RoomDefinition p_175969_1_) {
         return true;
      }

      public OceanMonumentPieces.Piece create(EnumFacing p_175968_1_, OceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
         p_175968_2_.claimed = true;
         return new OceanMonumentPieces.SimpleRoom(p_175968_1_, p_175968_2_, p_175968_3_);
      }
   }

   static class FitSimpleRoomTopHelper implements OceanMonumentPieces.MonumentRoomFitHelper {
      private FitSimpleRoomTopHelper() {
      }

      public boolean fits(OceanMonumentPieces.RoomDefinition p_175969_1_) {
         return !p_175969_1_.hasOpening[EnumFacing.WEST.getIndex()] && !p_175969_1_.hasOpening[EnumFacing.EAST.getIndex()] && !p_175969_1_.hasOpening[EnumFacing.NORTH.getIndex()] && !p_175969_1_.hasOpening[EnumFacing.SOUTH.getIndex()] && !p_175969_1_.hasOpening[EnumFacing.UP.getIndex()];
      }

      public OceanMonumentPieces.Piece create(EnumFacing p_175968_1_, OceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
         p_175968_2_.claimed = true;
         return new OceanMonumentPieces.SimpleTopRoom(p_175968_1_, p_175968_2_, p_175968_3_);
      }
   }

   public static class MonumentBuilding extends OceanMonumentPieces.Piece {
      private OceanMonumentPieces.RoomDefinition sourceRoom;
      private OceanMonumentPieces.RoomDefinition coreRoom;
      private final List<OceanMonumentPieces.Piece> childPieces = Lists.newArrayList();

      public MonumentBuilding() {
      }

      public MonumentBuilding(Random p_i45599_1_, int p_i45599_2_, int p_i45599_3_, EnumFacing p_i45599_4_) {
         super(0);
         this.setCoordBaseMode(p_i45599_4_);
         EnumFacing enumfacing = this.getCoordBaseMode();
         if (enumfacing.getAxis() == EnumFacing.Axis.Z) {
            this.boundingBox = new MutableBoundingBox(p_i45599_2_, 39, p_i45599_3_, p_i45599_2_ + 58 - 1, 61, p_i45599_3_ + 58 - 1);
         } else {
            this.boundingBox = new MutableBoundingBox(p_i45599_2_, 39, p_i45599_3_, p_i45599_2_ + 58 - 1, 61, p_i45599_3_ + 58 - 1);
         }

         List<OceanMonumentPieces.RoomDefinition> list = this.generateRoomGraph(p_i45599_1_);
         this.sourceRoom.claimed = true;
         this.childPieces.add(new OceanMonumentPieces.EntryRoom(enumfacing, this.sourceRoom));
         this.childPieces.add(new OceanMonumentPieces.MonumentCoreRoom(enumfacing, this.coreRoom, p_i45599_1_));
         List<OceanMonumentPieces.MonumentRoomFitHelper> list1 = Lists.newArrayList();
         list1.add(new OceanMonumentPieces.XYDoubleRoomFitHelper());
         list1.add(new OceanMonumentPieces.YZDoubleRoomFitHelper());
         list1.add(new OceanMonumentPieces.ZDoubleRoomFitHelper());
         list1.add(new OceanMonumentPieces.XDoubleRoomFitHelper());
         list1.add(new OceanMonumentPieces.YDoubleRoomFitHelper());
         list1.add(new OceanMonumentPieces.FitSimpleRoomTopHelper());
         list1.add(new OceanMonumentPieces.FitSimpleRoomHelper());

         label47:
         for(OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition : list) {
            if (!oceanmonumentpieces$roomdefinition.claimed && !oceanmonumentpieces$roomdefinition.isSpecial()) {
               Iterator lvt_10_1_ = list1.iterator();

               OceanMonumentPieces.MonumentRoomFitHelper oceanmonumentpieces$monumentroomfithelper;
               while(true) {
                  if (!lvt_10_1_.hasNext()) {
                     continue label47;
                  }

                  oceanmonumentpieces$monumentroomfithelper = (OceanMonumentPieces.MonumentRoomFitHelper)lvt_10_1_.next();
                  if (oceanmonumentpieces$monumentroomfithelper.fits(oceanmonumentpieces$roomdefinition)) {
                     break;
                  }
               }

               this.childPieces.add(oceanmonumentpieces$monumentroomfithelper.create(enumfacing, oceanmonumentpieces$roomdefinition, p_i45599_1_));
            }
         }

         int j = this.boundingBox.minY;
         int k = this.getXWithOffset(9, 22);
         int l = this.getZWithOffset(9, 22);

         for(OceanMonumentPieces.Piece oceanmonumentpieces$piece : this.childPieces) {
            oceanmonumentpieces$piece.getBoundingBox().offset(k, j, l);
         }

         MutableBoundingBox mutableboundingbox1 = MutableBoundingBox.createProper(this.getXWithOffset(1, 1), this.getYWithOffset(1), this.getZWithOffset(1, 1), this.getXWithOffset(23, 21), this.getYWithOffset(8), this.getZWithOffset(23, 21));
         MutableBoundingBox mutableboundingbox2 = MutableBoundingBox.createProper(this.getXWithOffset(34, 1), this.getYWithOffset(1), this.getZWithOffset(34, 1), this.getXWithOffset(56, 21), this.getYWithOffset(8), this.getZWithOffset(56, 21));
         MutableBoundingBox mutableboundingbox = MutableBoundingBox.createProper(this.getXWithOffset(22, 22), this.getYWithOffset(13), this.getZWithOffset(22, 22), this.getXWithOffset(35, 35), this.getYWithOffset(17), this.getZWithOffset(35, 35));
         int i = p_i45599_1_.nextInt();
         this.childPieces.add(new OceanMonumentPieces.WingRoom(enumfacing, mutableboundingbox1, i++));
         this.childPieces.add(new OceanMonumentPieces.WingRoom(enumfacing, mutableboundingbox2, i++));
         this.childPieces.add(new OceanMonumentPieces.Penthouse(enumfacing, mutableboundingbox));
      }

      private List<OceanMonumentPieces.RoomDefinition> generateRoomGraph(Random p_175836_1_) {
         OceanMonumentPieces.RoomDefinition[] aoceanmonumentpieces$roomdefinition = new OceanMonumentPieces.RoomDefinition[75];

         for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 4; ++j) {
               int k = 0;
               int l = getRoomIndex(i, 0, j);
               aoceanmonumentpieces$roomdefinition[l] = new OceanMonumentPieces.RoomDefinition(l);
            }
         }

         for(int i2 = 0; i2 < 5; ++i2) {
            for(int l2 = 0; l2 < 4; ++l2) {
               int k3 = 1;
               int j4 = getRoomIndex(i2, 1, l2);
               aoceanmonumentpieces$roomdefinition[j4] = new OceanMonumentPieces.RoomDefinition(j4);
            }
         }

         for(int j2 = 1; j2 < 4; ++j2) {
            for(int i3 = 0; i3 < 2; ++i3) {
               int l3 = 2;
               int k4 = getRoomIndex(j2, 2, i3);
               aoceanmonumentpieces$roomdefinition[k4] = new OceanMonumentPieces.RoomDefinition(k4);
            }
         }

         this.sourceRoom = aoceanmonumentpieces$roomdefinition[GRIDROOM_SOURCE_INDEX];

         for(int k2 = 0; k2 < 5; ++k2) {
            for(int j3 = 0; j3 < 5; ++j3) {
               for(int i4 = 0; i4 < 3; ++i4) {
                  int l4 = getRoomIndex(k2, i4, j3);
                  if (aoceanmonumentpieces$roomdefinition[l4] != null) {
                     for(EnumFacing enumfacing : EnumFacing.values()) {
                        int i1 = k2 + enumfacing.getXOffset();
                        int j1 = i4 + enumfacing.getYOffset();
                        int k1 = j3 + enumfacing.getZOffset();
                        if (i1 >= 0 && i1 < 5 && k1 >= 0 && k1 < 5 && j1 >= 0 && j1 < 3) {
                           int l1 = getRoomIndex(i1, j1, k1);
                           if (aoceanmonumentpieces$roomdefinition[l1] != null) {
                              if (k1 == j3) {
                                 aoceanmonumentpieces$roomdefinition[l4].setConnection(enumfacing, aoceanmonumentpieces$roomdefinition[l1]);
                              } else {
                                 aoceanmonumentpieces$roomdefinition[l4].setConnection(enumfacing.getOpposite(), aoceanmonumentpieces$roomdefinition[l1]);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = new OceanMonumentPieces.RoomDefinition(1003);
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition1 = new OceanMonumentPieces.RoomDefinition(1001);
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition2 = new OceanMonumentPieces.RoomDefinition(1002);
         aoceanmonumentpieces$roomdefinition[GRIDROOM_TOP_CONNECT_INDEX].setConnection(EnumFacing.UP, oceanmonumentpieces$roomdefinition);
         aoceanmonumentpieces$roomdefinition[GRIDROOM_LEFTWING_CONNECT_INDEX].setConnection(EnumFacing.SOUTH, oceanmonumentpieces$roomdefinition1);
         aoceanmonumentpieces$roomdefinition[GRIDROOM_RIGHTWING_CONNECT_INDEX].setConnection(EnumFacing.SOUTH, oceanmonumentpieces$roomdefinition2);
         oceanmonumentpieces$roomdefinition.claimed = true;
         oceanmonumentpieces$roomdefinition1.claimed = true;
         oceanmonumentpieces$roomdefinition2.claimed = true;
         this.sourceRoom.isSource = true;
         this.coreRoom = aoceanmonumentpieces$roomdefinition[getRoomIndex(p_175836_1_.nextInt(4), 0, 2)];
         this.coreRoom.claimed = true;
         this.coreRoom.connections[EnumFacing.EAST.getIndex()].claimed = true;
         this.coreRoom.connections[EnumFacing.NORTH.getIndex()].claimed = true;
         this.coreRoom.connections[EnumFacing.EAST.getIndex()].connections[EnumFacing.NORTH.getIndex()].claimed = true;
         this.coreRoom.connections[EnumFacing.UP.getIndex()].claimed = true;
         this.coreRoom.connections[EnumFacing.EAST.getIndex()].connections[EnumFacing.UP.getIndex()].claimed = true;
         this.coreRoom.connections[EnumFacing.NORTH.getIndex()].connections[EnumFacing.UP.getIndex()].claimed = true;
         this.coreRoom.connections[EnumFacing.EAST.getIndex()].connections[EnumFacing.NORTH.getIndex()].connections[EnumFacing.UP.getIndex()].claimed = true;
         List<OceanMonumentPieces.RoomDefinition> list = Lists.newArrayList();

         for(OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition4 : aoceanmonumentpieces$roomdefinition) {
            if (oceanmonumentpieces$roomdefinition4 != null) {
               oceanmonumentpieces$roomdefinition4.updateOpenings();
               list.add(oceanmonumentpieces$roomdefinition4);
            }
         }

         oceanmonumentpieces$roomdefinition.updateOpenings();
         Collections.shuffle(list, p_175836_1_);
         int i5 = 1;

         for(OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition3 : list) {
            int j5 = 0;
            int k5 = 0;

            while(j5 < 2 && k5 < 5) {
               ++k5;
               int l5 = p_175836_1_.nextInt(6);
               if (oceanmonumentpieces$roomdefinition3.hasOpening[l5]) {
                  int i6 = EnumFacing.byIndex(l5).getOpposite().getIndex();
                  oceanmonumentpieces$roomdefinition3.hasOpening[l5] = false;
                  oceanmonumentpieces$roomdefinition3.connections[l5].hasOpening[i6] = false;
                  if (oceanmonumentpieces$roomdefinition3.findSource(i5++) && oceanmonumentpieces$roomdefinition3.connections[l5].findSource(i5++)) {
                     ++j5;
                  } else {
                     oceanmonumentpieces$roomdefinition3.hasOpening[l5] = true;
                     oceanmonumentpieces$roomdefinition3.connections[l5].hasOpening[i6] = true;
                  }
               }
            }
         }

         list.add(oceanmonumentpieces$roomdefinition);
         list.add(oceanmonumentpieces$roomdefinition1);
         list.add(oceanmonumentpieces$roomdefinition2);
         return list;
      }

      public boolean addComponentParts(IWorld p_74875_1_, Random p_74875_2_, MutableBoundingBox p_74875_3_, ChunkPos p_74875_4_) {
         int i = Math.max(p_74875_1_.getSeaLevel(), 64) - this.boundingBox.minY;
         this.makeOpening(p_74875_1_, p_74875_3_, 0, 0, 0, 58, i, 58);
         this.generateWing(false, 0, p_74875_1_, p_74875_2_, p_74875_3_);
         this.generateWing(true, 33, p_74875_1_, p_74875_2_, p_74875_3_);
         this.generateEntranceArchs(p_74875_1_, p_74875_2_, p_74875_3_);
         this.generateEntranceWall(p_74875_1_, p_74875_2_, p_74875_3_);
         this.generateRoofPiece(p_74875_1_, p_74875_2_, p_74875_3_);
         this.generateLowerWall(p_74875_1_, p_74875_2_, p_74875_3_);
         this.generateMiddleWall(p_74875_1_, p_74875_2_, p_74875_3_);
         this.generateUpperWall(p_74875_1_, p_74875_2_, p_74875_3_);

         for(int j = 0; j < 7; ++j) {
            int k = 0;

            while(k < 7) {
               if (k == 0 && j == 3) {
                  k = 6;
               }

               int l = j * 9;
               int i1 = k * 9;

               for(int j1 = 0; j1 < 4; ++j1) {
                  for(int k1 = 0; k1 < 4; ++k1) {
                     this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, l + j1, 0, i1 + k1, p_74875_3_);
                     this.replaceAirAndLiquidDownwards(p_74875_1_, BRICKS_PRISMARINE, l + j1, -1, i1 + k1, p_74875_3_);
                  }
               }

               if (j != 0 && j != 6) {
                  k += 6;
               } else {
                  ++k;
               }
            }
         }

         for(int l1 = 0; l1 < 5; ++l1) {
            this.makeOpening(p_74875_1_, p_74875_3_, -1 - l1, 0 + l1 * 2, -1 - l1, -1 - l1, 23, 58 + l1);
            this.makeOpening(p_74875_1_, p_74875_3_, 58 + l1, 0 + l1 * 2, -1 - l1, 58 + l1, 23, 58 + l1);
            this.makeOpening(p_74875_1_, p_74875_3_, 0 - l1, 0 + l1 * 2, -1 - l1, 57 + l1, 23, -1 - l1);
            this.makeOpening(p_74875_1_, p_74875_3_, 0 - l1, 0 + l1 * 2, 58 + l1, 57 + l1, 23, 58 + l1);
         }

         for(OceanMonumentPieces.Piece oceanmonumentpieces$piece : this.childPieces) {
            if (oceanmonumentpieces$piece.getBoundingBox().intersectsWith(p_74875_3_)) {
               oceanmonumentpieces$piece.addComponentParts(p_74875_1_, p_74875_2_, p_74875_3_, p_74875_4_);
            }
         }

         return true;
      }

      private void generateWing(boolean p_175840_1_, int p_175840_2_, IWorld p_175840_3_, Random p_175840_4_, MutableBoundingBox p_175840_5_) {
         int i = 24;
         if (this.doesChunkIntersect(p_175840_5_, p_175840_2_, 0, p_175840_2_ + 23, 20)) {
            this.fillWithBlocks(p_175840_3_, p_175840_5_, p_175840_2_ + 0, 0, 0, p_175840_2_ + 24, 0, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.makeOpening(p_175840_3_, p_175840_5_, p_175840_2_ + 0, 1, 0, p_175840_2_ + 24, 10, 20);

            for(int j = 0; j < 4; ++j) {
               this.fillWithBlocks(p_175840_3_, p_175840_5_, p_175840_2_ + j, j + 1, j, p_175840_2_ + j, j + 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_175840_3_, p_175840_5_, p_175840_2_ + j + 7, j + 5, j + 7, p_175840_2_ + j + 7, j + 5, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_175840_3_, p_175840_5_, p_175840_2_ + 17 - j, j + 5, j + 7, p_175840_2_ + 17 - j, j + 5, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_175840_3_, p_175840_5_, p_175840_2_ + 24 - j, j + 1, j, p_175840_2_ + 24 - j, j + 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_175840_3_, p_175840_5_, p_175840_2_ + j + 1, j + 1, j, p_175840_2_ + 23 - j, j + 1, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_175840_3_, p_175840_5_, p_175840_2_ + j + 8, j + 5, j + 7, p_175840_2_ + 16 - j, j + 5, j + 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            this.fillWithBlocks(p_175840_3_, p_175840_5_, p_175840_2_ + 4, 4, 4, p_175840_2_ + 6, 4, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175840_3_, p_175840_5_, p_175840_2_ + 7, 4, 4, p_175840_2_ + 17, 4, 6, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175840_3_, p_175840_5_, p_175840_2_ + 18, 4, 4, p_175840_2_ + 20, 4, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175840_3_, p_175840_5_, p_175840_2_ + 11, 8, 11, p_175840_2_ + 13, 8, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.setBlockState(p_175840_3_, DOT_DECO_DATA, p_175840_2_ + 12, 9, 12, p_175840_5_);
            this.setBlockState(p_175840_3_, DOT_DECO_DATA, p_175840_2_ + 12, 9, 15, p_175840_5_);
            this.setBlockState(p_175840_3_, DOT_DECO_DATA, p_175840_2_ + 12, 9, 18, p_175840_5_);
            int j1 = p_175840_2_ + (p_175840_1_ ? 19 : 5);
            int k = p_175840_2_ + (p_175840_1_ ? 5 : 19);

            for(int l = 20; l >= 5; l -= 3) {
               this.setBlockState(p_175840_3_, DOT_DECO_DATA, j1, 5, l, p_175840_5_);
            }

            for(int k1 = 19; k1 >= 7; k1 -= 3) {
               this.setBlockState(p_175840_3_, DOT_DECO_DATA, k, 5, k1, p_175840_5_);
            }

            for(int l1 = 0; l1 < 4; ++l1) {
               int i1 = p_175840_1_ ? p_175840_2_ + 24 - (17 - l1 * 3) : p_175840_2_ + 17 - l1 * 3;
               this.setBlockState(p_175840_3_, DOT_DECO_DATA, i1, 5, 5, p_175840_5_);
            }

            this.setBlockState(p_175840_3_, DOT_DECO_DATA, k, 5, 5, p_175840_5_);
            this.fillWithBlocks(p_175840_3_, p_175840_5_, p_175840_2_ + 11, 1, 12, p_175840_2_ + 13, 7, 12, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175840_3_, p_175840_5_, p_175840_2_ + 12, 1, 11, p_175840_2_ + 12, 7, 13, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         }

      }

      private void generateEntranceArchs(IWorld p_175839_1_, Random p_175839_2_, MutableBoundingBox p_175839_3_) {
         if (this.doesChunkIntersect(p_175839_3_, 22, 5, 35, 17)) {
            this.makeOpening(p_175839_1_, p_175839_3_, 25, 0, 0, 32, 8, 20);

            for(int i = 0; i < 4; ++i) {
               this.fillWithBlocks(p_175839_1_, p_175839_3_, 24, 2, 5 + i * 4, 24, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_175839_1_, p_175839_3_, 22, 4, 5 + i * 4, 23, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.setBlockState(p_175839_1_, BRICKS_PRISMARINE, 25, 5, 5 + i * 4, p_175839_3_);
               this.setBlockState(p_175839_1_, BRICKS_PRISMARINE, 26, 6, 5 + i * 4, p_175839_3_);
               this.setBlockState(p_175839_1_, SEA_LANTERN, 26, 5, 5 + i * 4, p_175839_3_);
               this.fillWithBlocks(p_175839_1_, p_175839_3_, 33, 2, 5 + i * 4, 33, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_175839_1_, p_175839_3_, 34, 4, 5 + i * 4, 35, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.setBlockState(p_175839_1_, BRICKS_PRISMARINE, 32, 5, 5 + i * 4, p_175839_3_);
               this.setBlockState(p_175839_1_, BRICKS_PRISMARINE, 31, 6, 5 + i * 4, p_175839_3_);
               this.setBlockState(p_175839_1_, SEA_LANTERN, 31, 5, 5 + i * 4, p_175839_3_);
               this.fillWithBlocks(p_175839_1_, p_175839_3_, 27, 6, 5 + i * 4, 30, 6, 5 + i * 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            }
         }

      }

      private void generateEntranceWall(IWorld p_175837_1_, Random p_175837_2_, MutableBoundingBox p_175837_3_) {
         if (this.doesChunkIntersect(p_175837_3_, 15, 20, 42, 21)) {
            this.fillWithBlocks(p_175837_1_, p_175837_3_, 15, 0, 21, 42, 0, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.makeOpening(p_175837_1_, p_175837_3_, 26, 1, 21, 31, 3, 21);
            this.fillWithBlocks(p_175837_1_, p_175837_3_, 21, 12, 21, 36, 12, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175837_1_, p_175837_3_, 17, 11, 21, 40, 11, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175837_1_, p_175837_3_, 16, 10, 21, 41, 10, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175837_1_, p_175837_3_, 15, 7, 21, 42, 9, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175837_1_, p_175837_3_, 16, 6, 21, 41, 6, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175837_1_, p_175837_3_, 17, 5, 21, 40, 5, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175837_1_, p_175837_3_, 21, 4, 21, 36, 4, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175837_1_, p_175837_3_, 22, 3, 21, 26, 3, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175837_1_, p_175837_3_, 31, 3, 21, 35, 3, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175837_1_, p_175837_3_, 23, 2, 21, 25, 2, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175837_1_, p_175837_3_, 32, 2, 21, 34, 2, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175837_1_, p_175837_3_, 28, 4, 20, 29, 4, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.setBlockState(p_175837_1_, BRICKS_PRISMARINE, 27, 3, 21, p_175837_3_);
            this.setBlockState(p_175837_1_, BRICKS_PRISMARINE, 30, 3, 21, p_175837_3_);
            this.setBlockState(p_175837_1_, BRICKS_PRISMARINE, 26, 2, 21, p_175837_3_);
            this.setBlockState(p_175837_1_, BRICKS_PRISMARINE, 31, 2, 21, p_175837_3_);
            this.setBlockState(p_175837_1_, BRICKS_PRISMARINE, 25, 1, 21, p_175837_3_);
            this.setBlockState(p_175837_1_, BRICKS_PRISMARINE, 32, 1, 21, p_175837_3_);

            for(int i = 0; i < 7; ++i) {
               this.setBlockState(p_175837_1_, DARK_PRISMARINE, 28 - i, 6 + i, 21, p_175837_3_);
               this.setBlockState(p_175837_1_, DARK_PRISMARINE, 29 + i, 6 + i, 21, p_175837_3_);
            }

            for(int j = 0; j < 4; ++j) {
               this.setBlockState(p_175837_1_, DARK_PRISMARINE, 28 - j, 9 + j, 21, p_175837_3_);
               this.setBlockState(p_175837_1_, DARK_PRISMARINE, 29 + j, 9 + j, 21, p_175837_3_);
            }

            this.setBlockState(p_175837_1_, DARK_PRISMARINE, 28, 12, 21, p_175837_3_);
            this.setBlockState(p_175837_1_, DARK_PRISMARINE, 29, 12, 21, p_175837_3_);

            for(int k = 0; k < 3; ++k) {
               this.setBlockState(p_175837_1_, DARK_PRISMARINE, 22 - k * 2, 8, 21, p_175837_3_);
               this.setBlockState(p_175837_1_, DARK_PRISMARINE, 22 - k * 2, 9, 21, p_175837_3_);
               this.setBlockState(p_175837_1_, DARK_PRISMARINE, 35 + k * 2, 8, 21, p_175837_3_);
               this.setBlockState(p_175837_1_, DARK_PRISMARINE, 35 + k * 2, 9, 21, p_175837_3_);
            }

            this.makeOpening(p_175837_1_, p_175837_3_, 15, 13, 21, 42, 15, 21);
            this.makeOpening(p_175837_1_, p_175837_3_, 15, 1, 21, 15, 6, 21);
            this.makeOpening(p_175837_1_, p_175837_3_, 16, 1, 21, 16, 5, 21);
            this.makeOpening(p_175837_1_, p_175837_3_, 17, 1, 21, 20, 4, 21);
            this.makeOpening(p_175837_1_, p_175837_3_, 21, 1, 21, 21, 3, 21);
            this.makeOpening(p_175837_1_, p_175837_3_, 22, 1, 21, 22, 2, 21);
            this.makeOpening(p_175837_1_, p_175837_3_, 23, 1, 21, 24, 1, 21);
            this.makeOpening(p_175837_1_, p_175837_3_, 42, 1, 21, 42, 6, 21);
            this.makeOpening(p_175837_1_, p_175837_3_, 41, 1, 21, 41, 5, 21);
            this.makeOpening(p_175837_1_, p_175837_3_, 37, 1, 21, 40, 4, 21);
            this.makeOpening(p_175837_1_, p_175837_3_, 36, 1, 21, 36, 3, 21);
            this.makeOpening(p_175837_1_, p_175837_3_, 33, 1, 21, 34, 1, 21);
            this.makeOpening(p_175837_1_, p_175837_3_, 35, 1, 21, 35, 2, 21);
         }

      }

      private void generateRoofPiece(IWorld p_175841_1_, Random p_175841_2_, MutableBoundingBox p_175841_3_) {
         if (this.doesChunkIntersect(p_175841_3_, 21, 21, 36, 36)) {
            this.fillWithBlocks(p_175841_1_, p_175841_3_, 21, 0, 22, 36, 0, 36, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.makeOpening(p_175841_1_, p_175841_3_, 21, 1, 22, 36, 23, 36);

            for(int i = 0; i < 4; ++i) {
               this.fillWithBlocks(p_175841_1_, p_175841_3_, 21 + i, 13 + i, 21 + i, 36 - i, 13 + i, 21 + i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_175841_1_, p_175841_3_, 21 + i, 13 + i, 36 - i, 36 - i, 13 + i, 36 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_175841_1_, p_175841_3_, 21 + i, 13 + i, 22 + i, 21 + i, 13 + i, 35 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_175841_1_, p_175841_3_, 36 - i, 13 + i, 22 + i, 36 - i, 13 + i, 35 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            this.fillWithBlocks(p_175841_1_, p_175841_3_, 25, 16, 25, 32, 16, 32, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175841_1_, p_175841_3_, 25, 17, 25, 25, 19, 25, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_175841_1_, p_175841_3_, 32, 17, 25, 32, 19, 25, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_175841_1_, p_175841_3_, 25, 17, 32, 25, 19, 32, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_175841_1_, p_175841_3_, 32, 17, 32, 32, 19, 32, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.setBlockState(p_175841_1_, BRICKS_PRISMARINE, 26, 20, 26, p_175841_3_);
            this.setBlockState(p_175841_1_, BRICKS_PRISMARINE, 27, 21, 27, p_175841_3_);
            this.setBlockState(p_175841_1_, SEA_LANTERN, 27, 20, 27, p_175841_3_);
            this.setBlockState(p_175841_1_, BRICKS_PRISMARINE, 26, 20, 31, p_175841_3_);
            this.setBlockState(p_175841_1_, BRICKS_PRISMARINE, 27, 21, 30, p_175841_3_);
            this.setBlockState(p_175841_1_, SEA_LANTERN, 27, 20, 30, p_175841_3_);
            this.setBlockState(p_175841_1_, BRICKS_PRISMARINE, 31, 20, 31, p_175841_3_);
            this.setBlockState(p_175841_1_, BRICKS_PRISMARINE, 30, 21, 30, p_175841_3_);
            this.setBlockState(p_175841_1_, SEA_LANTERN, 30, 20, 30, p_175841_3_);
            this.setBlockState(p_175841_1_, BRICKS_PRISMARINE, 31, 20, 26, p_175841_3_);
            this.setBlockState(p_175841_1_, BRICKS_PRISMARINE, 30, 21, 27, p_175841_3_);
            this.setBlockState(p_175841_1_, SEA_LANTERN, 30, 20, 27, p_175841_3_);
            this.fillWithBlocks(p_175841_1_, p_175841_3_, 28, 21, 27, 29, 21, 27, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175841_1_, p_175841_3_, 27, 21, 28, 27, 21, 29, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175841_1_, p_175841_3_, 28, 21, 30, 29, 21, 30, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175841_1_, p_175841_3_, 30, 21, 28, 30, 21, 29, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         }

      }

      private void generateLowerWall(IWorld p_175835_1_, Random p_175835_2_, MutableBoundingBox p_175835_3_) {
         if (this.doesChunkIntersect(p_175835_3_, 0, 21, 6, 58)) {
            this.fillWithBlocks(p_175835_1_, p_175835_3_, 0, 0, 21, 6, 0, 57, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.makeOpening(p_175835_1_, p_175835_3_, 0, 1, 21, 6, 7, 57);
            this.fillWithBlocks(p_175835_1_, p_175835_3_, 4, 4, 21, 6, 4, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);

            for(int i = 0; i < 4; ++i) {
               this.fillWithBlocks(p_175835_1_, p_175835_3_, i, i + 1, 21, i, i + 1, 57 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            for(int j = 23; j < 53; j += 3) {
               this.setBlockState(p_175835_1_, DOT_DECO_DATA, 5, 5, j, p_175835_3_);
            }

            this.setBlockState(p_175835_1_, DOT_DECO_DATA, 5, 5, 52, p_175835_3_);

            for(int k = 0; k < 4; ++k) {
               this.fillWithBlocks(p_175835_1_, p_175835_3_, k, k + 1, 21, k, k + 1, 57 - k, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            this.fillWithBlocks(p_175835_1_, p_175835_3_, 4, 1, 52, 6, 3, 52, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175835_1_, p_175835_3_, 5, 1, 51, 5, 3, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         }

         if (this.doesChunkIntersect(p_175835_3_, 51, 21, 58, 58)) {
            this.fillWithBlocks(p_175835_1_, p_175835_3_, 51, 0, 21, 57, 0, 57, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.makeOpening(p_175835_1_, p_175835_3_, 51, 1, 21, 57, 7, 57);
            this.fillWithBlocks(p_175835_1_, p_175835_3_, 51, 4, 21, 53, 4, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);

            for(int l = 0; l < 4; ++l) {
               this.fillWithBlocks(p_175835_1_, p_175835_3_, 57 - l, l + 1, 21, 57 - l, l + 1, 57 - l, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            for(int i1 = 23; i1 < 53; i1 += 3) {
               this.setBlockState(p_175835_1_, DOT_DECO_DATA, 52, 5, i1, p_175835_3_);
            }

            this.setBlockState(p_175835_1_, DOT_DECO_DATA, 52, 5, 52, p_175835_3_);
            this.fillWithBlocks(p_175835_1_, p_175835_3_, 51, 1, 52, 53, 3, 52, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175835_1_, p_175835_3_, 52, 1, 51, 52, 3, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         }

         if (this.doesChunkIntersect(p_175835_3_, 0, 51, 57, 57)) {
            this.fillWithBlocks(p_175835_1_, p_175835_3_, 7, 0, 51, 50, 0, 57, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.makeOpening(p_175835_1_, p_175835_3_, 7, 1, 51, 50, 10, 57);

            for(int j1 = 0; j1 < 4; ++j1) {
               this.fillWithBlocks(p_175835_1_, p_175835_3_, j1 + 1, j1 + 1, 57 - j1, 56 - j1, j1 + 1, 57 - j1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }
         }

      }

      private void generateMiddleWall(IWorld p_175842_1_, Random p_175842_2_, MutableBoundingBox p_175842_3_) {
         if (this.doesChunkIntersect(p_175842_3_, 7, 21, 13, 50)) {
            this.fillWithBlocks(p_175842_1_, p_175842_3_, 7, 0, 21, 13, 0, 50, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.makeOpening(p_175842_1_, p_175842_3_, 7, 1, 21, 13, 10, 50);
            this.fillWithBlocks(p_175842_1_, p_175842_3_, 11, 8, 21, 13, 8, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);

            for(int i = 0; i < 4; ++i) {
               this.fillWithBlocks(p_175842_1_, p_175842_3_, i + 7, i + 5, 21, i + 7, i + 5, 54, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            for(int j = 21; j <= 45; j += 3) {
               this.setBlockState(p_175842_1_, DOT_DECO_DATA, 12, 9, j, p_175842_3_);
            }
         }

         if (this.doesChunkIntersect(p_175842_3_, 44, 21, 50, 54)) {
            this.fillWithBlocks(p_175842_1_, p_175842_3_, 44, 0, 21, 50, 0, 50, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.makeOpening(p_175842_1_, p_175842_3_, 44, 1, 21, 50, 10, 50);
            this.fillWithBlocks(p_175842_1_, p_175842_3_, 44, 8, 21, 46, 8, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);

            for(int k = 0; k < 4; ++k) {
               this.fillWithBlocks(p_175842_1_, p_175842_3_, 50 - k, k + 5, 21, 50 - k, k + 5, 54, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            for(int l = 21; l <= 45; l += 3) {
               this.setBlockState(p_175842_1_, DOT_DECO_DATA, 45, 9, l, p_175842_3_);
            }
         }

         if (this.doesChunkIntersect(p_175842_3_, 8, 44, 49, 54)) {
            this.fillWithBlocks(p_175842_1_, p_175842_3_, 14, 0, 44, 43, 0, 50, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.makeOpening(p_175842_1_, p_175842_3_, 14, 1, 44, 43, 10, 50);

            for(int i1 = 12; i1 <= 45; i1 += 3) {
               this.setBlockState(p_175842_1_, DOT_DECO_DATA, i1, 9, 45, p_175842_3_);
               this.setBlockState(p_175842_1_, DOT_DECO_DATA, i1, 9, 52, p_175842_3_);
               if (i1 == 12 || i1 == 18 || i1 == 24 || i1 == 33 || i1 == 39 || i1 == 45) {
                  this.setBlockState(p_175842_1_, DOT_DECO_DATA, i1, 9, 47, p_175842_3_);
                  this.setBlockState(p_175842_1_, DOT_DECO_DATA, i1, 9, 50, p_175842_3_);
                  this.setBlockState(p_175842_1_, DOT_DECO_DATA, i1, 10, 45, p_175842_3_);
                  this.setBlockState(p_175842_1_, DOT_DECO_DATA, i1, 10, 46, p_175842_3_);
                  this.setBlockState(p_175842_1_, DOT_DECO_DATA, i1, 10, 51, p_175842_3_);
                  this.setBlockState(p_175842_1_, DOT_DECO_DATA, i1, 10, 52, p_175842_3_);
                  this.setBlockState(p_175842_1_, DOT_DECO_DATA, i1, 11, 47, p_175842_3_);
                  this.setBlockState(p_175842_1_, DOT_DECO_DATA, i1, 11, 50, p_175842_3_);
                  this.setBlockState(p_175842_1_, DOT_DECO_DATA, i1, 12, 48, p_175842_3_);
                  this.setBlockState(p_175842_1_, DOT_DECO_DATA, i1, 12, 49, p_175842_3_);
               }
            }

            for(int j1 = 0; j1 < 3; ++j1) {
               this.fillWithBlocks(p_175842_1_, p_175842_3_, 8 + j1, 5 + j1, 54, 49 - j1, 5 + j1, 54, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            }

            this.fillWithBlocks(p_175842_1_, p_175842_3_, 11, 8, 54, 46, 8, 54, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_175842_1_, p_175842_3_, 14, 8, 44, 43, 8, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         }

      }

      private void generateUpperWall(IWorld p_175838_1_, Random p_175838_2_, MutableBoundingBox p_175838_3_) {
         if (this.doesChunkIntersect(p_175838_3_, 14, 21, 20, 43)) {
            this.fillWithBlocks(p_175838_1_, p_175838_3_, 14, 0, 21, 20, 0, 43, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.makeOpening(p_175838_1_, p_175838_3_, 14, 1, 22, 20, 14, 43);
            this.fillWithBlocks(p_175838_1_, p_175838_3_, 18, 12, 22, 20, 12, 39, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175838_1_, p_175838_3_, 18, 12, 21, 20, 12, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);

            for(int i = 0; i < 4; ++i) {
               this.fillWithBlocks(p_175838_1_, p_175838_3_, i + 14, i + 9, 21, i + 14, i + 9, 43 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            for(int j = 23; j <= 39; j += 3) {
               this.setBlockState(p_175838_1_, DOT_DECO_DATA, 19, 13, j, p_175838_3_);
            }
         }

         if (this.doesChunkIntersect(p_175838_3_, 37, 21, 43, 43)) {
            this.fillWithBlocks(p_175838_1_, p_175838_3_, 37, 0, 21, 43, 0, 43, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.makeOpening(p_175838_1_, p_175838_3_, 37, 1, 22, 43, 14, 43);
            this.fillWithBlocks(p_175838_1_, p_175838_3_, 37, 12, 22, 39, 12, 39, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175838_1_, p_175838_3_, 37, 12, 21, 39, 12, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);

            for(int k = 0; k < 4; ++k) {
               this.fillWithBlocks(p_175838_1_, p_175838_3_, 43 - k, k + 9, 21, 43 - k, k + 9, 43 - k, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            for(int l = 23; l <= 39; l += 3) {
               this.setBlockState(p_175838_1_, DOT_DECO_DATA, 38, 13, l, p_175838_3_);
            }
         }

         if (this.doesChunkIntersect(p_175838_3_, 15, 37, 42, 43)) {
            this.fillWithBlocks(p_175838_1_, p_175838_3_, 21, 0, 37, 36, 0, 43, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.makeOpening(p_175838_1_, p_175838_3_, 21, 1, 37, 36, 14, 43);
            this.fillWithBlocks(p_175838_1_, p_175838_3_, 21, 12, 37, 36, 12, 39, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);

            for(int i1 = 0; i1 < 4; ++i1) {
               this.fillWithBlocks(p_175838_1_, p_175838_3_, 15 + i1, i1 + 9, 43 - i1, 42 - i1, i1 + 9, 43 - i1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            for(int j1 = 21; j1 <= 36; j1 += 3) {
               this.setBlockState(p_175838_1_, DOT_DECO_DATA, j1, 13, 38, p_175838_3_);
            }
         }

      }
   }

   public static class MonumentCoreRoom extends OceanMonumentPieces.Piece {
      public MonumentCoreRoom() {
      }

      public MonumentCoreRoom(EnumFacing p_i45598_1_, OceanMonumentPieces.RoomDefinition p_i45598_2_, Random p_i45598_3_) {
         super(1, p_i45598_1_, p_i45598_2_, 2, 2, 2);
      }

      public boolean addComponentParts(IWorld p_74875_1_, Random p_74875_2_, MutableBoundingBox p_74875_3_, ChunkPos p_74875_4_) {
         this.generateBoxOnFillOnly(p_74875_1_, p_74875_3_, 1, 8, 0, 14, 8, 14, ROUGH_PRISMARINE);
         int i = 7;
         IBlockState iblockstate = BRICKS_PRISMARINE;
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 7, 0, 0, 7, 15, iblockstate, iblockstate, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 15, 7, 0, 15, 7, 15, iblockstate, iblockstate, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 7, 0, 15, 7, 0, iblockstate, iblockstate, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 7, 15, 14, 7, 15, iblockstate, iblockstate, false);

         for(int k = 1; k <= 6; ++k) {
            iblockstate = BRICKS_PRISMARINE;
            if (k == 2 || k == 6) {
               iblockstate = ROUGH_PRISMARINE;
            }

            for(int j = 0; j <= 15; j += 15) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, j, k, 0, j, k, 1, iblockstate, iblockstate, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, j, k, 6, j, k, 9, iblockstate, iblockstate, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, j, k, 14, j, k, 15, iblockstate, iblockstate, false);
            }

            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, k, 0, 1, k, 0, iblockstate, iblockstate, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, k, 0, 9, k, 0, iblockstate, iblockstate, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 14, k, 0, 14, k, 0, iblockstate, iblockstate, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, k, 15, 14, k, 15, iblockstate, iblockstate, false);
         }

         this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 3, 6, 9, 6, 9, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 4, 7, 8, 5, 8, Blocks.GOLD_BLOCK.getDefaultState(), Blocks.GOLD_BLOCK.getDefaultState(), false);

         for(int l = 3; l <= 6; l += 3) {
            for(int i1 = 6; i1 <= 9; i1 += 3) {
               this.setBlockState(p_74875_1_, SEA_LANTERN, i1, l, 6, p_74875_3_);
               this.setBlockState(p_74875_1_, SEA_LANTERN, i1, l, 9, p_74875_3_);
            }
         }

         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 6, 5, 2, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 9, 5, 2, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 10, 1, 6, 10, 2, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 10, 1, 9, 10, 2, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 1, 5, 6, 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 9, 1, 5, 9, 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 1, 10, 6, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 9, 1, 10, 9, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 2, 5, 5, 6, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 2, 10, 5, 6, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 10, 2, 5, 10, 6, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 10, 2, 10, 10, 6, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 7, 1, 5, 7, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 10, 7, 1, 10, 7, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 7, 9, 5, 7, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 10, 7, 9, 10, 7, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 7, 5, 6, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 7, 10, 6, 7, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 9, 7, 5, 14, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 9, 7, 10, 14, 7, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, 1, 2, 2, 1, 3, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 2, 3, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 13, 1, 2, 13, 1, 3, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 12, 1, 2, 12, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, 1, 12, 2, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 13, 3, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 13, 1, 12, 13, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 12, 1, 13, 12, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         return true;
      }
   }

   interface MonumentRoomFitHelper {
      boolean fits(OceanMonumentPieces.RoomDefinition p_175969_1_);

      OceanMonumentPieces.Piece create(EnumFacing p_175968_1_, OceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_);
   }

   public static class Penthouse extends OceanMonumentPieces.Piece {
      public Penthouse() {
      }

      public Penthouse(EnumFacing p_i45591_1_, MutableBoundingBox p_i45591_2_) {
         super(p_i45591_1_, p_i45591_2_);
      }

      public boolean addComponentParts(IWorld p_74875_1_, Random p_74875_2_, MutableBoundingBox p_74875_3_, ChunkPos p_74875_4_) {
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, -1, 2, 11, -1, 11, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, -1, 0, 1, -1, 11, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 12, -1, 0, 13, -1, 11, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, -1, 0, 11, -1, 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, -1, 12, 11, -1, 13, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 0, 0, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 13, 0, 0, 13, 0, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 0, 12, 0, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 13, 12, 0, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);

         for(int i = 2; i <= 11; i += 3) {
            this.setBlockState(p_74875_1_, SEA_LANTERN, 0, 0, i, p_74875_3_);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 13, 0, i, p_74875_3_);
            this.setBlockState(p_74875_1_, SEA_LANTERN, i, 0, 0, p_74875_3_);
         }

         this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, 0, 3, 4, 0, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 9, 0, 3, 11, 0, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 4, 0, 9, 9, 0, 11, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 5, 0, 8, p_74875_3_);
         this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 8, 0, 8, p_74875_3_);
         this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 10, 0, 10, p_74875_3_);
         this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 3, 0, 10, p_74875_3_);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 0, 3, 3, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 10, 0, 3, 10, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 0, 10, 7, 0, 10, DARK_PRISMARINE, DARK_PRISMARINE, false);
         int l = 3;

         for(int j = 0; j < 2; ++j) {
            for(int k = 2; k <= 8; k += 3) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, l, 0, k, l, 2, k, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            l = 10;
         }

         this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 0, 10, 5, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 8, 0, 10, 8, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, -1, 7, 7, -1, 8, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.makeOpening(p_74875_1_, p_74875_3_, 6, -1, 3, 7, -1, 4);
         this.spawnElder(p_74875_1_, p_74875_3_, 6, 1, 6);
         return true;
      }
   }

   public abstract static class Piece extends StructurePiece {
      protected static final IBlockState ROUGH_PRISMARINE = Blocks.PRISMARINE.getDefaultState();
      protected static final IBlockState BRICKS_PRISMARINE = Blocks.PRISMARINE_BRICKS.getDefaultState();
      protected static final IBlockState DARK_PRISMARINE = Blocks.DARK_PRISMARINE.getDefaultState();
      protected static final IBlockState DOT_DECO_DATA = BRICKS_PRISMARINE;
      protected static final IBlockState SEA_LANTERN = Blocks.SEA_LANTERN.getDefaultState();
      protected static final IBlockState WATER = Blocks.WATER.getDefaultState();
      protected static final Set<Block> field_212180_g = ImmutableSet.<Block>builder().add(Blocks.ICE).add(Blocks.PACKED_ICE).add(Blocks.BLUE_ICE).add(WATER.getBlock()).build();
      protected static final int GRIDROOM_SOURCE_INDEX = getRoomIndex(2, 0, 0);
      protected static final int GRIDROOM_TOP_CONNECT_INDEX = getRoomIndex(2, 2, 0);
      protected static final int GRIDROOM_LEFTWING_CONNECT_INDEX = getRoomIndex(0, 1, 0);
      protected static final int GRIDROOM_RIGHTWING_CONNECT_INDEX = getRoomIndex(4, 1, 0);
      protected OceanMonumentPieces.RoomDefinition roomDefinition;

      protected static final int getRoomIndex(int p_175820_0_, int p_175820_1_, int p_175820_2_) {
         return p_175820_1_ * 25 + p_175820_2_ * 5 + p_175820_0_;
      }

      public Piece() {
         super(0);
      }

      public Piece(int p_i45588_1_) {
         super(p_i45588_1_);
      }

      public Piece(EnumFacing p_i45589_1_, MutableBoundingBox p_i45589_2_) {
         super(1);
         this.setCoordBaseMode(p_i45589_1_);
         this.boundingBox = p_i45589_2_;
      }

      protected Piece(int p_i45590_1_, EnumFacing p_i45590_2_, OceanMonumentPieces.RoomDefinition p_i45590_3_, int p_i45590_4_, int p_i45590_5_, int p_i45590_6_) {
         super(p_i45590_1_);
         this.setCoordBaseMode(p_i45590_2_);
         this.roomDefinition = p_i45590_3_;
         int i = p_i45590_3_.index;
         int j = i % 5;
         int k = i / 5 % 5;
         int l = i / 25;
         if (p_i45590_2_ != EnumFacing.NORTH && p_i45590_2_ != EnumFacing.SOUTH) {
            this.boundingBox = new MutableBoundingBox(0, 0, 0, p_i45590_6_ * 8 - 1, p_i45590_5_ * 4 - 1, p_i45590_4_ * 8 - 1);
         } else {
            this.boundingBox = new MutableBoundingBox(0, 0, 0, p_i45590_4_ * 8 - 1, p_i45590_5_ * 4 - 1, p_i45590_6_ * 8 - 1);
         }

         switch(p_i45590_2_) {
         case NORTH:
            this.boundingBox.offset(j * 8, l * 4, -(k + p_i45590_6_) * 8 + 1);
            break;
         case SOUTH:
            this.boundingBox.offset(j * 8, l * 4, k * 8);
            break;
         case WEST:
            this.boundingBox.offset(-(k + p_i45590_6_) * 8 + 1, l * 4, j * 8);
            break;
         default:
            this.boundingBox.offset(k * 8, l * 4, j * 8);
         }

      }

      protected void writeStructureToNBT(NBTTagCompound p_143012_1_) {
      }

      protected void readStructureFromNBT(NBTTagCompound p_143011_1_, TemplateManager p_143011_2_) {
      }

      protected void makeOpening(IWorld p_209179_1_, MutableBoundingBox p_209179_2_, int p_209179_3_, int p_209179_4_, int p_209179_5_, int p_209179_6_, int p_209179_7_, int p_209179_8_) {
         for(int i = p_209179_4_; i <= p_209179_7_; ++i) {
            for(int j = p_209179_3_; j <= p_209179_6_; ++j) {
               for(int k = p_209179_5_; k <= p_209179_8_; ++k) {
                  IBlockState iblockstate = this.getBlockStateFromPos(p_209179_1_, j, i, k, p_209179_2_);
                  if (!field_212180_g.contains(iblockstate.getBlock())) {
                     if (this.getYWithOffset(i) >= p_209179_1_.getSeaLevel() && iblockstate != WATER) {
                        this.setBlockState(p_209179_1_, Blocks.AIR.getDefaultState(), j, i, k, p_209179_2_);
                     } else {
                        this.setBlockState(p_209179_1_, WATER, j, i, k, p_209179_2_);
                     }
                  }
               }
            }
         }

      }

      protected void generateDefaultFloor(IWorld p_175821_1_, MutableBoundingBox p_175821_2_, int p_175821_3_, int p_175821_4_, boolean p_175821_5_) {
         if (p_175821_5_) {
            this.fillWithBlocks(p_175821_1_, p_175821_2_, p_175821_3_ + 0, 0, p_175821_4_ + 0, p_175821_3_ + 2, 0, p_175821_4_ + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175821_1_, p_175821_2_, p_175821_3_ + 5, 0, p_175821_4_ + 0, p_175821_3_ + 8 - 1, 0, p_175821_4_ + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175821_1_, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 0, p_175821_3_ + 4, 0, p_175821_4_ + 2, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175821_1_, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 5, p_175821_3_ + 4, 0, p_175821_4_ + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_175821_1_, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 2, p_175821_3_ + 4, 0, p_175821_4_ + 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_175821_1_, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 5, p_175821_3_ + 4, 0, p_175821_4_ + 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_175821_1_, p_175821_2_, p_175821_3_ + 2, 0, p_175821_4_ + 3, p_175821_3_ + 2, 0, p_175821_4_ + 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_175821_1_, p_175821_2_, p_175821_3_ + 5, 0, p_175821_4_ + 3, p_175821_3_ + 5, 0, p_175821_4_ + 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         } else {
            this.fillWithBlocks(p_175821_1_, p_175821_2_, p_175821_3_ + 0, 0, p_175821_4_ + 0, p_175821_3_ + 8 - 1, 0, p_175821_4_ + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
         }

      }

      protected void generateBoxOnFillOnly(IWorld p_175819_1_, MutableBoundingBox p_175819_2_, int p_175819_3_, int p_175819_4_, int p_175819_5_, int p_175819_6_, int p_175819_7_, int p_175819_8_, IBlockState p_175819_9_) {
         for(int i = p_175819_4_; i <= p_175819_7_; ++i) {
            for(int j = p_175819_3_; j <= p_175819_6_; ++j) {
               for(int k = p_175819_5_; k <= p_175819_8_; ++k) {
                  if (this.getBlockStateFromPos(p_175819_1_, j, i, k, p_175819_2_) == WATER) {
                     this.setBlockState(p_175819_1_, p_175819_9_, j, i, k, p_175819_2_);
                  }
               }
            }
         }

      }

      protected boolean doesChunkIntersect(MutableBoundingBox p_175818_1_, int p_175818_2_, int p_175818_3_, int p_175818_4_, int p_175818_5_) {
         int i = this.getXWithOffset(p_175818_2_, p_175818_3_);
         int j = this.getZWithOffset(p_175818_2_, p_175818_3_);
         int k = this.getXWithOffset(p_175818_4_, p_175818_5_);
         int l = this.getZWithOffset(p_175818_4_, p_175818_5_);
         return p_175818_1_.intersectsWith(Math.min(i, k), Math.min(j, l), Math.max(i, k), Math.max(j, l));
      }

      protected boolean spawnElder(IWorld p_175817_1_, MutableBoundingBox p_175817_2_, int p_175817_3_, int p_175817_4_, int p_175817_5_) {
         int i = this.getXWithOffset(p_175817_3_, p_175817_5_);
         int j = this.getYWithOffset(p_175817_4_);
         int k = this.getZWithOffset(p_175817_3_, p_175817_5_);
         if (p_175817_2_.isVecInside(new BlockPos(i, j, k))) {
            EntityElderGuardian entityelderguardian = new EntityElderGuardian(p_175817_1_.getWorld());
            entityelderguardian.heal(entityelderguardian.getMaxHealth());
            entityelderguardian.setLocationAndAngles((double)i + 0.5D, (double)j, (double)k + 0.5D, 0.0F, 0.0F);
            entityelderguardian.onInitialSpawn(p_175817_1_.getDifficultyForLocation(new BlockPos(entityelderguardian)), (IEntityLivingData)null, (NBTTagCompound)null);
            p_175817_1_.spawnEntity(entityelderguardian);
            return true;
         } else {
            return false;
         }
      }
   }

   static class RoomDefinition {
      private final int index;
      private final OceanMonumentPieces.RoomDefinition[] connections = new OceanMonumentPieces.RoomDefinition[6];
      private final boolean[] hasOpening = new boolean[6];
      private boolean claimed;
      private boolean isSource;
      private int scanIndex;

      public RoomDefinition(int p_i45584_1_) {
         this.index = p_i45584_1_;
      }

      public void setConnection(EnumFacing p_175957_1_, OceanMonumentPieces.RoomDefinition p_175957_2_) {
         this.connections[p_175957_1_.getIndex()] = p_175957_2_;
         p_175957_2_.connections[p_175957_1_.getOpposite().getIndex()] = this;
      }

      public void updateOpenings() {
         for(int i = 0; i < 6; ++i) {
            this.hasOpening[i] = this.connections[i] != null;
         }

      }

      public boolean findSource(int p_175959_1_) {
         if (this.isSource) {
            return true;
         } else {
            this.scanIndex = p_175959_1_;

            for(int i = 0; i < 6; ++i) {
               if (this.connections[i] != null && this.hasOpening[i] && this.connections[i].scanIndex != p_175959_1_ && this.connections[i].findSource(p_175959_1_)) {
                  return true;
               }
            }

            return false;
         }
      }

      public boolean isSpecial() {
         return this.index >= 75;
      }

      public int countOpenings() {
         int i = 0;

         for(int j = 0; j < 6; ++j) {
            if (this.hasOpening[j]) {
               ++i;
            }
         }

         return i;
      }
   }

   public static class SimpleRoom extends OceanMonumentPieces.Piece {
      private int mainDesign;

      public SimpleRoom() {
      }

      public SimpleRoom(EnumFacing p_i45587_1_, OceanMonumentPieces.RoomDefinition p_i45587_2_, Random p_i45587_3_) {
         super(1, p_i45587_1_, p_i45587_2_, 1, 1, 1);
         this.mainDesign = p_i45587_3_.nextInt(3);
      }

      public boolean addComponentParts(IWorld p_74875_1_, Random p_74875_2_, MutableBoundingBox p_74875_3_, ChunkPos p_74875_4_) {
         if (this.roomDefinition.index / 25 > 0) {
            this.generateDefaultFloor(p_74875_1_, p_74875_3_, 0, 0, this.roomDefinition.hasOpening[EnumFacing.DOWN.getIndex()]);
         }

         if (this.roomDefinition.connections[EnumFacing.UP.getIndex()] == null) {
            this.generateBoxOnFillOnly(p_74875_1_, p_74875_3_, 1, 4, 1, 6, 4, 6, ROUGH_PRISMARINE);
         }

         boolean flag = this.mainDesign != 0 && p_74875_2_.nextBoolean() && !this.roomDefinition.hasOpening[EnumFacing.DOWN.getIndex()] && !this.roomDefinition.hasOpening[EnumFacing.UP.getIndex()] && this.roomDefinition.countOpenings() > 1;
         if (this.mainDesign == 0) {
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 2, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, 0, 2, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 2, 2, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 0, 2, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 1, 2, 1, p_74875_3_);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 0, 7, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 3, 0, 7, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 2, 0, 7, 2, 2, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 2, 0, 6, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 6, 2, 1, p_74875_3_);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 5, 2, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, 5, 2, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 5, 0, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 7, 2, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 1, 2, 6, p_74875_3_);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 5, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 3, 5, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 2, 5, 7, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 2, 7, 6, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 6, 2, 6, p_74875_3_);
            if (this.roomDefinition.hasOpening[EnumFacing.SOUTH.getIndex()]) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 3, 0, 4, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            } else {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 3, 0, 4, 3, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 2, 0, 4, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 0, 4, 1, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            if (this.roomDefinition.hasOpening[EnumFacing.NORTH.getIndex()]) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 3, 7, 4, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            } else {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 3, 6, 4, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 2, 7, 4, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 6, 4, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            if (this.roomDefinition.hasOpening[EnumFacing.WEST.getIndex()]) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, 3, 0, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            } else {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, 3, 1, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 3, 0, 2, 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 3, 1, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            if (this.roomDefinition.hasOpening[EnumFacing.EAST.getIndex()]) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 3, 3, 7, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            } else {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 3, 3, 7, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 2, 3, 7, 2, 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 1, 3, 7, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }
         } else if (this.mainDesign == 1) {
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, 1, 2, 2, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 2, 1, 5, 2, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 5, 5, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 2, 5, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 2, 2, 2, p_74875_3_);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 2, 2, 5, p_74875_3_);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 5, 2, 5, p_74875_3_);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 5, 2, 2, p_74875_3_);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 1, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 7, 1, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 6, 0, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 1, 7, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 1, 6, 7, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 1, 0, 7, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 1, 1, 7, 3, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.setBlockState(p_74875_1_, ROUGH_PRISMARINE, 1, 2, 0, p_74875_3_);
            this.setBlockState(p_74875_1_, ROUGH_PRISMARINE, 0, 2, 1, p_74875_3_);
            this.setBlockState(p_74875_1_, ROUGH_PRISMARINE, 1, 2, 7, p_74875_3_);
            this.setBlockState(p_74875_1_, ROUGH_PRISMARINE, 0, 2, 6, p_74875_3_);
            this.setBlockState(p_74875_1_, ROUGH_PRISMARINE, 6, 2, 7, p_74875_3_);
            this.setBlockState(p_74875_1_, ROUGH_PRISMARINE, 7, 2, 6, p_74875_3_);
            this.setBlockState(p_74875_1_, ROUGH_PRISMARINE, 6, 2, 0, p_74875_3_);
            this.setBlockState(p_74875_1_, ROUGH_PRISMARINE, 7, 2, 1, p_74875_3_);
            if (!this.roomDefinition.hasOpening[EnumFacing.SOUTH.getIndex()]) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 0, 6, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 0, 6, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            if (!this.roomDefinition.hasOpening[EnumFacing.NORTH.getIndex()]) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 7, 6, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 7, 6, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 7, 6, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            if (!this.roomDefinition.hasOpening[EnumFacing.WEST.getIndex()]) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, 1, 0, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 1, 0, 2, 6, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 1, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            if (!this.roomDefinition.hasOpening[EnumFacing.EAST.getIndex()]) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 3, 1, 7, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 2, 1, 7, 2, 6, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 1, 1, 7, 1, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }
         } else if (this.mainDesign == 2) {
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 1, 0, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 0, 6, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 7, 6, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 2, 0, 7, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 0, 6, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 7, 6, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, 0, 0, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 3, 0, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 7, 6, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 3, 0, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 1, 3, 7, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 0, 4, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 7, 4, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            if (this.roomDefinition.hasOpening[EnumFacing.SOUTH.getIndex()]) {
               this.makeOpening(p_74875_1_, p_74875_3_, 3, 1, 0, 4, 2, 0);
            }

            if (this.roomDefinition.hasOpening[EnumFacing.NORTH.getIndex()]) {
               this.makeOpening(p_74875_1_, p_74875_3_, 3, 1, 7, 4, 2, 7);
            }

            if (this.roomDefinition.hasOpening[EnumFacing.WEST.getIndex()]) {
               this.makeOpening(p_74875_1_, p_74875_3_, 0, 1, 3, 0, 2, 4);
            }

            if (this.roomDefinition.hasOpening[EnumFacing.EAST.getIndex()]) {
               this.makeOpening(p_74875_1_, p_74875_3_, 7, 1, 3, 7, 2, 4);
            }
         }

         if (flag) {
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 3, 4, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 2, 3, 4, 2, 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 3, 3, 4, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         }

         return true;
      }
   }

   public static class SimpleTopRoom extends OceanMonumentPieces.Piece {
      public SimpleTopRoom() {
      }

      public SimpleTopRoom(EnumFacing p_i45586_1_, OceanMonumentPieces.RoomDefinition p_i45586_2_, Random p_i45586_3_) {
         super(1, p_i45586_1_, p_i45586_2_, 1, 1, 1);
      }

      public boolean addComponentParts(IWorld p_74875_1_, Random p_74875_2_, MutableBoundingBox p_74875_3_, ChunkPos p_74875_4_) {
         if (this.roomDefinition.index / 25 > 0) {
            this.generateDefaultFloor(p_74875_1_, p_74875_3_, 0, 0, this.roomDefinition.hasOpening[EnumFacing.DOWN.getIndex()]);
         }

         if (this.roomDefinition.connections[EnumFacing.UP.getIndex()] == null) {
            this.generateBoxOnFillOnly(p_74875_1_, p_74875_3_, 1, 4, 1, 6, 4, 6, ROUGH_PRISMARINE);
         }

         for(int i = 1; i <= 6; ++i) {
            for(int j = 1; j <= 6; ++j) {
               if (p_74875_2_.nextInt(3) != 0) {
                  int k = 2 + (p_74875_2_.nextInt(4) == 0 ? 0 : 1);
                  IBlockState iblockstate = Blocks.WET_SPONGE.getDefaultState();
                  this.fillWithBlocks(p_74875_1_, p_74875_3_, i, k, j, i, 3, j, iblockstate, iblockstate, false);
               }
            }
         }

         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 1, 0, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 0, 6, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 7, 6, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 2, 0, 7, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 0, 6, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 7, 6, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, 0, 0, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 3, 0, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 7, 6, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 3, 0, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 1, 3, 7, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 0, 4, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
         this.fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 7, 4, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
         if (this.roomDefinition.hasOpening[EnumFacing.SOUTH.getIndex()]) {
            this.makeOpening(p_74875_1_, p_74875_3_, 3, 1, 0, 4, 2, 0);
         }

         return true;
      }
   }

   public static class WingRoom extends OceanMonumentPieces.Piece {
      private int mainDesign;

      public WingRoom() {
      }

      public WingRoom(EnumFacing p_i45585_1_, MutableBoundingBox p_i45585_2_, int p_i45585_3_) {
         super(p_i45585_1_, p_i45585_2_);
         this.mainDesign = p_i45585_3_ & 1;
      }

      public boolean addComponentParts(IWorld p_74875_1_, Random p_74875_2_, MutableBoundingBox p_74875_3_, ChunkPos p_74875_4_) {
         if (this.mainDesign == 0) {
            for(int i = 0; i < 4; ++i) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, 10 - i, 3 - i, 20 - i, 12 + i, 3 - i, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            }

            this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 0, 6, 15, 0, 16, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 0, 6, 6, 3, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 16, 0, 6, 16, 3, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 1, 7, 7, 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 15, 1, 7, 15, 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 1, 6, 9, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 13, 1, 6, 15, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 8, 1, 7, 9, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 13, 1, 7, 14, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 9, 0, 5, 13, 0, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 10, 0, 7, 12, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 8, 0, 10, 8, 0, 12, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 14, 0, 10, 14, 0, 12, DARK_PRISMARINE, DARK_PRISMARINE, false);

            for(int i1 = 18; i1 >= 7; i1 -= 3) {
               this.setBlockState(p_74875_1_, SEA_LANTERN, 6, 3, i1, p_74875_3_);
               this.setBlockState(p_74875_1_, SEA_LANTERN, 16, 3, i1, p_74875_3_);
            }

            this.setBlockState(p_74875_1_, SEA_LANTERN, 10, 0, 10, p_74875_3_);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 12, 0, 10, p_74875_3_);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 10, 0, 12, p_74875_3_);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 12, 0, 12, p_74875_3_);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 8, 3, 6, p_74875_3_);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 14, 3, 6, p_74875_3_);
            this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 4, 2, 4, p_74875_3_);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 4, 1, 4, p_74875_3_);
            this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 4, 0, 4, p_74875_3_);
            this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 18, 2, 4, p_74875_3_);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 18, 1, 4, p_74875_3_);
            this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 18, 0, 4, p_74875_3_);
            this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 4, 2, 18, p_74875_3_);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 4, 1, 18, p_74875_3_);
            this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 4, 0, 18, p_74875_3_);
            this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 18, 2, 18, p_74875_3_);
            this.setBlockState(p_74875_1_, SEA_LANTERN, 18, 1, 18, p_74875_3_);
            this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 18, 0, 18, p_74875_3_);
            this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 9, 7, 20, p_74875_3_);
            this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, 13, 7, 20, p_74875_3_);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 6, 0, 21, 7, 4, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 15, 0, 21, 16, 4, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.spawnElder(p_74875_1_, p_74875_3_, 11, 2, 16);
         } else if (this.mainDesign == 1) {
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 9, 3, 18, 13, 3, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 9, 0, 18, 9, 2, 18, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 13, 0, 18, 13, 2, 18, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            int j1 = 9;
            int j = 20;
            int k = 5;

            for(int l = 0; l < 2; ++l) {
               this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, j1, 6, 20, p_74875_3_);
               this.setBlockState(p_74875_1_, SEA_LANTERN, j1, 5, 20, p_74875_3_);
               this.setBlockState(p_74875_1_, BRICKS_PRISMARINE, j1, 4, 20, p_74875_3_);
               j1 = 13;
            }

            this.fillWithBlocks(p_74875_1_, p_74875_3_, 7, 3, 7, 15, 3, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
            j1 = 10;

            for(int k1 = 0; k1 < 2; ++k1) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, j1, 0, 10, j1, 6, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, j1, 0, 12, j1, 6, 12, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.setBlockState(p_74875_1_, SEA_LANTERN, j1, 0, 10, p_74875_3_);
               this.setBlockState(p_74875_1_, SEA_LANTERN, j1, 0, 12, p_74875_3_);
               this.setBlockState(p_74875_1_, SEA_LANTERN, j1, 4, 10, p_74875_3_);
               this.setBlockState(p_74875_1_, SEA_LANTERN, j1, 4, 12, p_74875_3_);
               j1 = 12;
            }

            j1 = 8;

            for(int l1 = 0; l1 < 2; ++l1) {
               this.fillWithBlocks(p_74875_1_, p_74875_3_, j1, 0, 7, j1, 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               this.fillWithBlocks(p_74875_1_, p_74875_3_, j1, 0, 14, j1, 2, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
               j1 = 14;
            }

            this.fillWithBlocks(p_74875_1_, p_74875_3_, 8, 3, 8, 8, 3, 13, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.fillWithBlocks(p_74875_1_, p_74875_3_, 14, 3, 8, 14, 3, 13, DARK_PRISMARINE, DARK_PRISMARINE, false);
            this.spawnElder(p_74875_1_, p_74875_3_, 11, 5, 13);
         }

         return true;
      }
   }

   static class XDoubleRoomFitHelper implements OceanMonumentPieces.MonumentRoomFitHelper {
      private XDoubleRoomFitHelper() {
      }

      public boolean fits(OceanMonumentPieces.RoomDefinition p_175969_1_) {
         return p_175969_1_.hasOpening[EnumFacing.EAST.getIndex()] && !p_175969_1_.connections[EnumFacing.EAST.getIndex()].claimed;
      }

      public OceanMonumentPieces.Piece create(EnumFacing p_175968_1_, OceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
         p_175968_2_.claimed = true;
         p_175968_2_.connections[EnumFacing.EAST.getIndex()].claimed = true;
         return new OceanMonumentPieces.DoubleXRoom(p_175968_1_, p_175968_2_, p_175968_3_);
      }
   }

   static class XYDoubleRoomFitHelper implements OceanMonumentPieces.MonumentRoomFitHelper {
      private XYDoubleRoomFitHelper() {
      }

      public boolean fits(OceanMonumentPieces.RoomDefinition p_175969_1_) {
         if (p_175969_1_.hasOpening[EnumFacing.EAST.getIndex()] && !p_175969_1_.connections[EnumFacing.EAST.getIndex()].claimed && p_175969_1_.hasOpening[EnumFacing.UP.getIndex()] && !p_175969_1_.connections[EnumFacing.UP.getIndex()].claimed) {
            OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = p_175969_1_.connections[EnumFacing.EAST.getIndex()];
            return oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.UP.getIndex()] && !oceanmonumentpieces$roomdefinition.connections[EnumFacing.UP.getIndex()].claimed;
         } else {
            return false;
         }
      }

      public OceanMonumentPieces.Piece create(EnumFacing p_175968_1_, OceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
         p_175968_2_.claimed = true;
         p_175968_2_.connections[EnumFacing.EAST.getIndex()].claimed = true;
         p_175968_2_.connections[EnumFacing.UP.getIndex()].claimed = true;
         p_175968_2_.connections[EnumFacing.EAST.getIndex()].connections[EnumFacing.UP.getIndex()].claimed = true;
         return new OceanMonumentPieces.DoubleXYRoom(p_175968_1_, p_175968_2_, p_175968_3_);
      }
   }

   static class YDoubleRoomFitHelper implements OceanMonumentPieces.MonumentRoomFitHelper {
      private YDoubleRoomFitHelper() {
      }

      public boolean fits(OceanMonumentPieces.RoomDefinition p_175969_1_) {
         return p_175969_1_.hasOpening[EnumFacing.UP.getIndex()] && !p_175969_1_.connections[EnumFacing.UP.getIndex()].claimed;
      }

      public OceanMonumentPieces.Piece create(EnumFacing p_175968_1_, OceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
         p_175968_2_.claimed = true;
         p_175968_2_.connections[EnumFacing.UP.getIndex()].claimed = true;
         return new OceanMonumentPieces.DoubleYRoom(p_175968_1_, p_175968_2_, p_175968_3_);
      }
   }

   static class YZDoubleRoomFitHelper implements OceanMonumentPieces.MonumentRoomFitHelper {
      private YZDoubleRoomFitHelper() {
      }

      public boolean fits(OceanMonumentPieces.RoomDefinition p_175969_1_) {
         if (p_175969_1_.hasOpening[EnumFacing.NORTH.getIndex()] && !p_175969_1_.connections[EnumFacing.NORTH.getIndex()].claimed && p_175969_1_.hasOpening[EnumFacing.UP.getIndex()] && !p_175969_1_.connections[EnumFacing.UP.getIndex()].claimed) {
            OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = p_175969_1_.connections[EnumFacing.NORTH.getIndex()];
            return oceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.UP.getIndex()] && !oceanmonumentpieces$roomdefinition.connections[EnumFacing.UP.getIndex()].claimed;
         } else {
            return false;
         }
      }

      public OceanMonumentPieces.Piece create(EnumFacing p_175968_1_, OceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
         p_175968_2_.claimed = true;
         p_175968_2_.connections[EnumFacing.NORTH.getIndex()].claimed = true;
         p_175968_2_.connections[EnumFacing.UP.getIndex()].claimed = true;
         p_175968_2_.connections[EnumFacing.NORTH.getIndex()].connections[EnumFacing.UP.getIndex()].claimed = true;
         return new OceanMonumentPieces.DoubleYZRoom(p_175968_1_, p_175968_2_, p_175968_3_);
      }
   }

   static class ZDoubleRoomFitHelper implements OceanMonumentPieces.MonumentRoomFitHelper {
      private ZDoubleRoomFitHelper() {
      }

      public boolean fits(OceanMonumentPieces.RoomDefinition p_175969_1_) {
         return p_175969_1_.hasOpening[EnumFacing.NORTH.getIndex()] && !p_175969_1_.connections[EnumFacing.NORTH.getIndex()].claimed;
      }

      public OceanMonumentPieces.Piece create(EnumFacing p_175968_1_, OceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
         OceanMonumentPieces.RoomDefinition oceanmonumentpieces$roomdefinition = p_175968_2_;
         if (!p_175968_2_.hasOpening[EnumFacing.NORTH.getIndex()] || p_175968_2_.connections[EnumFacing.NORTH.getIndex()].claimed) {
            oceanmonumentpieces$roomdefinition = p_175968_2_.connections[EnumFacing.SOUTH.getIndex()];
         }

         oceanmonumentpieces$roomdefinition.claimed = true;
         oceanmonumentpieces$roomdefinition.connections[EnumFacing.NORTH.getIndex()].claimed = true;
         return new OceanMonumentPieces.DoubleZRoom(p_175968_1_, oceanmonumentpieces$roomdefinition, p_175968_3_);
      }
   }
}
