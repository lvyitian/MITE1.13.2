package net.minecraft.entity.ai.attributes;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.Validate;

public class AttributeModifier {
   private final double amount;
   private final int operation;
   private final Supplier<String> name;
   private final UUID id;
   private boolean isSaved = true;

   public AttributeModifier(String p_i1605_1_, double p_i1605_2_, int p_i1605_4_) {
      this(MathHelper.getRandomUUID(ThreadLocalRandom.current()), () -> {
         return p_i1605_1_;
      }, p_i1605_2_, p_i1605_4_);
   }

   public AttributeModifier(UUID p_i1606_1_, String p_i1606_2_, double p_i1606_3_, int p_i1606_5_) {
      this(p_i1606_1_, () -> {
         return p_i1606_2_;
      }, p_i1606_3_, p_i1606_5_);
   }

   public AttributeModifier(UUID p_i49578_1_, Supplier<String> p_i49578_2_, double p_i49578_3_, int p_i49578_5_) {
      this.id = p_i49578_1_;
      this.name = p_i49578_2_;
      this.amount = p_i49578_3_;
      this.operation = p_i49578_5_;
      Validate.inclusiveBetween(0L, 2L, (long)p_i49578_5_, "Invalid operation");
   }

   public UUID getID() {
      return this.id;
   }

   public String getName() {
      return this.name.get();
   }

   public int getOperation() {
      return this.operation;
   }

   public double getAmount() {
      return this.amount;
   }

   public boolean isSaved() {
      return this.isSaved;
   }

   public AttributeModifier setSaved(boolean p_111168_1_) {
      this.isSaved = p_111168_1_;
      return this;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         AttributeModifier attributemodifier = (AttributeModifier)p_equals_1_;
         if (this.id != null) {
            if (!this.id.equals(attributemodifier.id)) {
               return false;
            }
         } else if (attributemodifier.id != null) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.id != null ? this.id.hashCode() : 0;
   }

   public String toString() {
      return "AttributeModifier{amount=" + this.amount + ", operation=" + this.operation + ", name='" + (String)this.name.get() + '\'' + ", id=" + this.id + ", serialize=" + this.isSaved + '}';
   }
}
