package net.minecraft.world.gen.placement;

public class DungeonRoomConfig implements IPlacementConfig {
   public final int count;
   public final int minHeight;

   public DungeonRoomConfig(int p_i48659_1_,int minHeight) {
      this.count = p_i48659_1_;
      this.minHeight = minHeight;
   }
   public DungeonRoomConfig(int count){
      this.count = count;
      this.minHeight = 4;
   }
}
