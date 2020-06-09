/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ public class EnchantmentNameParts {
/*  9 */   private static final EnchantmentNameParts INSTANCE = new EnchantmentNameParts();
/* 10 */   private final Random rand = new Random();
/* 11 */   private final String[] namePartsArray = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale phnglui mglwnafh cthulhu rlyeh wgahnagl fhtagnbaguette".split(" ");
/*    */ 
/*    */   
/*    */   public static EnchantmentNameParts getInstance() {
/* 15 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String generateNewRandomName(FontRenderer p_148334_1_, int p_148334_2_) {
/* 23 */     int i = this.rand.nextInt(2) + 3;
/* 24 */     String s = "";
/*    */     
/* 26 */     for (int j = 0; j < i; j++) {
/*    */       
/* 28 */       if (j > 0)
/*    */       {
/* 30 */         s = String.valueOf(s) + " ";
/*    */       }
/*    */       
/* 33 */       s = String.valueOf(s) + this.namePartsArray[this.rand.nextInt(this.namePartsArray.length)];
/*    */     } 
/*    */     
/* 36 */     List<String> list = p_148334_1_.listFormattedStringToWidth(s, p_148334_2_);
/* 37 */     return StringUtils.join((list.size() >= 2) ? list.subList(0, 2) : list, " ");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reseedRandomGenerator(long seed) {
/* 45 */     this.rand.setSeed(seed);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\EnchantmentNameParts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */