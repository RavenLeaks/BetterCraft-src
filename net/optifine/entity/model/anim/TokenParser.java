/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PushbackReader;
/*    */ import java.io.Reader;
/*    */ import java.io.StringReader;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class TokenParser
/*    */ {
/*    */   public static Token[] parse(String str) throws IOException, ParseException {
/* 14 */     Reader reader = new StringReader(str);
/* 15 */     PushbackReader pushbackreader = new PushbackReader(reader);
/* 16 */     List<Token> list = new ArrayList<>();
/*    */ 
/*    */     
/*    */     while (true) {
/* 20 */       int i = pushbackreader.read();
/*    */       
/* 22 */       if (i < 0) {
/*    */         
/* 24 */         Token[] atoken = list.<Token>toArray(new Token[list.size()]);
/* 25 */         return atoken;
/*    */       } 
/*    */       
/* 28 */       char c0 = (char)i;
/*    */       
/* 30 */       if (!Character.isWhitespace(c0)) {
/*    */         
/* 32 */         EnumTokenType enumtokentype = EnumTokenType.getTypeByFirstChar(c0);
/*    */         
/* 34 */         if (enumtokentype == null)
/*    */         {
/* 36 */           throw new ParseException("Invalid character: '" + c0 + "', in: " + str);
/*    */         }
/*    */         
/* 39 */         Token token = readToken(c0, enumtokentype, pushbackreader);
/* 40 */         list.add(token);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static Token readToken(char chFirst, EnumTokenType type, PushbackReader pr) throws IOException {
/* 47 */     StringBuffer stringbuffer = new StringBuffer();
/* 48 */     stringbuffer.append(chFirst);
/*    */     
/* 50 */     while (type.getMaxLen() <= 0 || stringbuffer.length() < type.getMaxLen()) {
/*    */       
/* 52 */       int i = pr.read();
/*    */       
/* 54 */       if (i < 0) {
/*    */         break;
/*    */       }
/*    */ 
/*    */       
/* 59 */       char c0 = (char)i;
/*    */       
/* 61 */       if (!type.hasChar(c0)) {
/*    */         
/* 63 */         pr.unread(c0);
/*    */         
/*    */         break;
/*    */       } 
/* 67 */       stringbuffer.append(c0);
/*    */     } 
/*    */     
/* 70 */     return new Token(type, stringbuffer.toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\anim\TokenParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */