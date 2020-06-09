/*    */ package net.minecraft.util;
/*    */ 
/*    */ import io.netty.util.ResourceLeakDetector;
/*    */ 
/*    */ 
/*    */ public class ChatAllowedCharacters
/*    */ {
/*  8 */   public static final ResourceLeakDetector.Level NETTY_LEAK_DETECTION = ResourceLeakDetector.Level.DISABLED;
/*  9 */   public static final char[] ILLEGAL_STRUCTURE_CHARACTERS = new char[] { '.', '\n', '\r', '\t', '\f', '`', '?', '*', '\\', '<', '>', '|', '"' };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 14 */   public static final char[] ILLEGAL_FILE_CHARACTERS = new char[] { '/', '\n', '\r', '\t', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':' };
/*    */ 
/*    */   
/*    */   public static boolean isAllowedCharacter(char character) {
/* 18 */     return (character != '§' && character >= ' ' && character != '');
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String filterAllowedCharacters(String input) {
/* 26 */     StringBuilder stringbuilder = new StringBuilder(); byte b; int i;
/*    */     char[] arrayOfChar;
/* 28 */     for (i = (arrayOfChar = input.toCharArray()).length, b = 0; b < i; ) { char c0 = arrayOfChar[b];
/*    */       
/* 30 */       if (isAllowedCharacter(c0))
/*    */       {
/* 32 */         stringbuilder.append(c0);
/*    */       }
/*    */       b++; }
/*    */     
/* 36 */     return stringbuilder.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 41 */     ResourceLeakDetector.setLevel(NETTY_LEAK_DETECTION);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\ChatAllowedCharacters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */