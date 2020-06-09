/*    */ package net.minecraft.entity.ai.attributes;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public abstract class BaseAttribute
/*    */   implements IAttribute
/*    */ {
/*    */   private final IAttribute parent;
/*    */   private final String unlocalizedName;
/*    */   private final double defaultValue;
/*    */   private boolean shouldWatch;
/*    */   
/*    */   protected BaseAttribute(@Nullable IAttribute parentIn, String unlocalizedNameIn, double defaultValueIn) {
/* 14 */     this.parent = parentIn;
/* 15 */     this.unlocalizedName = unlocalizedNameIn;
/* 16 */     this.defaultValue = defaultValueIn;
/*    */     
/* 18 */     if (unlocalizedNameIn == null)
/*    */     {
/* 20 */       throw new IllegalArgumentException("Name cannot be null!");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAttributeUnlocalizedName() {
/* 26 */     return this.unlocalizedName;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDefaultValue() {
/* 31 */     return this.defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getShouldWatch() {
/* 36 */     return this.shouldWatch;
/*    */   }
/*    */ 
/*    */   
/*    */   public BaseAttribute setShouldWatch(boolean shouldWatchIn) {
/* 41 */     this.shouldWatch = shouldWatchIn;
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public IAttribute getParent() {
/* 48 */     return this.parent;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 53 */     return this.unlocalizedName.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 58 */     return (p_equals_1_ instanceof IAttribute && this.unlocalizedName.equals(((IAttribute)p_equals_1_).getAttributeUnlocalizedName()));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\attributes\BaseAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */