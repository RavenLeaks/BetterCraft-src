/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ public class FontMetadataSection
/*    */   implements IMetadataSection
/*    */ {
/*    */   private final float[] charWidths;
/*    */   private final float[] charLefts;
/*    */   private final float[] charSpacings;
/*    */   
/*    */   public FontMetadataSection(float[] charWidthsIn, float[] charLeftsIn, float[] charSpacingsIn) {
/* 11 */     this.charWidths = charWidthsIn;
/* 12 */     this.charLefts = charLeftsIn;
/* 13 */     this.charSpacings = charSpacingsIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\data\FontMetadataSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */