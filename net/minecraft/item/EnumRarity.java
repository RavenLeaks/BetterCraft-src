/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.util.text.TextFormatting;
/*    */ 
/*    */ public enum EnumRarity
/*    */ {
/*  7 */   COMMON(TextFormatting.WHITE, "Common"),
/*  8 */   UNCOMMON(TextFormatting.YELLOW, "Uncommon"),
/*  9 */   RARE(TextFormatting.AQUA, "Rare"),
/* 10 */   EPIC(TextFormatting.LIGHT_PURPLE, "Epic");
/*    */ 
/*    */ 
/*    */   
/*    */   public final TextFormatting rarityColor;
/*    */ 
/*    */ 
/*    */   
/*    */   public final String rarityName;
/*    */ 
/*    */ 
/*    */   
/*    */   EnumRarity(TextFormatting color, String name) {
/* 23 */     this.rarityColor = color;
/* 24 */     this.rarityName = name;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\EnumRarity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */