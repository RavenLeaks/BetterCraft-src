/*    */ package net.minecraft.entity.player;
/*    */ 
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ 
/*    */ public enum EnumPlayerModelParts
/*    */ {
/*  8 */   CAPE(0, "cape"),
/*  9 */   JACKET(1, "jacket"),
/* 10 */   LEFT_SLEEVE(2, "left_sleeve"),
/* 11 */   RIGHT_SLEEVE(3, "right_sleeve"),
/* 12 */   LEFT_PANTS_LEG(4, "left_pants_leg"),
/* 13 */   RIGHT_PANTS_LEG(5, "right_pants_leg"),
/* 14 */   HAT(6, "hat");
/*    */   
/*    */   private final int partId;
/*    */   
/*    */   private final int partMask;
/*    */   private final String partName;
/*    */   private final ITextComponent name;
/*    */   
/*    */   EnumPlayerModelParts(int partIdIn, String partNameIn) {
/* 23 */     this.partId = partIdIn;
/* 24 */     this.partMask = 1 << partIdIn;
/* 25 */     this.partName = partNameIn;
/* 26 */     this.name = (ITextComponent)new TextComponentTranslation("options.modelPart." + partNameIn, new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPartMask() {
/* 31 */     return this.partMask;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPartId() {
/* 36 */     return this.partId;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPartName() {
/* 41 */     return this.partName;
/*    */   }
/*    */ 
/*    */   
/*    */   public ITextComponent getName() {
/* 46 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\player\EnumPlayerModelParts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */