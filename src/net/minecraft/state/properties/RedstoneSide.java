package net.minecraft.state.properties;

import net.minecraft.util.IStringSerializable;

public enum RedstoneSide implements IStringSerializable {
   UP("up"),
   SIDE("side"),
   NONE("none");

   private final String name;

   RedstoneSide(String p_i49333_3_) {
      this.name = p_i49333_3_;
   }

   public String toString() {
      return this.getName();
   }

   public String getName() {
      return this.name;
   }
}
