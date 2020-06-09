/*    */ package optifine;
/*    */ 
/*    */ import net.minecraft.enchantment.Enchantment;
/*    */ 
/*    */ public class ParserEnchantmentId
/*    */   implements IParserInt
/*    */ {
/*    */   public int parse(String p_parse_1_, int p_parse_2_) {
/*  9 */     Enchantment enchantment = Enchantment.getEnchantmentByLocation(p_parse_1_);
/* 10 */     return (enchantment == null) ? p_parse_2_ : Enchantment.getEnchantmentID(enchantment);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ParserEnchantmentId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */