/*    */ package net.minecraft.entity.ai.attributes;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class RangedAttribute
/*    */   extends BaseAttribute
/*    */ {
/*    */   private final double minimumValue;
/*    */   private final double maximumValue;
/*    */   private String description;
/*    */   
/*    */   public RangedAttribute(@Nullable IAttribute parentIn, String unlocalizedNameIn, double defaultValue, double minimumValueIn, double maximumValueIn) {
/* 14 */     super(parentIn, unlocalizedNameIn, defaultValue);
/* 15 */     this.minimumValue = minimumValueIn;
/* 16 */     this.maximumValue = maximumValueIn;
/*    */     
/* 18 */     if (minimumValueIn > maximumValueIn)
/*    */     {
/* 20 */       throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
/*    */     }
/* 22 */     if (defaultValue < minimumValueIn)
/*    */     {
/* 24 */       throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
/*    */     }
/* 26 */     if (defaultValue > maximumValueIn)
/*    */     {
/* 28 */       throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public RangedAttribute setDescription(String descriptionIn) {
/* 34 */     this.description = descriptionIn;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 40 */     return this.description;
/*    */   }
/*    */ 
/*    */   
/*    */   public double clampValue(double value) {
/* 45 */     value = MathHelper.clamp(value, this.minimumValue, this.maximumValue);
/* 46 */     return value;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\attributes\RangedAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */