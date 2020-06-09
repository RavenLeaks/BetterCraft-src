/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ 
/*    */ public class PackMetadataSection
/*    */   implements IMetadataSection
/*    */ {
/*    */   private final ITextComponent packDescription;
/*    */   private final int packFormat;
/*    */   
/*    */   public PackMetadataSection(ITextComponent packDescriptionIn, int packFormatIn) {
/* 12 */     this.packDescription = packDescriptionIn;
/* 13 */     this.packFormat = packFormatIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public ITextComponent getPackDescription() {
/* 18 */     return this.packDescription;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPackFormat() {
/* 23 */     return this.packFormat;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\data\PackMetadataSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */