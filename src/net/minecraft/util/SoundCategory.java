package net.minecraft.util;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SoundCategory {
   MASTER("master"),
   MUSIC("music"),
   RECORDS("record"),
   WEATHER("weather"),
   BLOCKS("block"),
   HOSTILE("hostile"),
   NEUTRAL("neutral"),
   PLAYERS("player"),
   AMBIENT("ambient"),
   VOICE("voice");

   private static final Map<String, SoundCategory> SOUND_CATEGORIES = Arrays.stream(values()).collect(Collectors.toMap(SoundCategory::getName, Function.identity()));
   private final String name;

   SoundCategory(String p_i46833_3_) {
      this.name = p_i46833_3_;
   }

   public String getName() {
      return this.name;
   }
}
