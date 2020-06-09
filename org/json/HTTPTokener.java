/*    */ package org.json;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HTTPTokener
/*    */   extends JSONTokener
/*    */ {
/*    */   public HTTPTokener(String string) {
/* 40 */     super(string);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String nextToken() throws JSONException {
/* 52 */     StringBuilder sb = new StringBuilder();
/*    */     while (true) {
/* 54 */       char c = next();
/* 55 */       if (!Character.isWhitespace(c)) {
/* 56 */         if (c == '"' || c == '\'') {
/* 57 */           char q = c;
/*    */           while (true) {
/* 59 */             c = next();
/* 60 */             if (c < ' ') {
/* 61 */               throw syntaxError("Unterminated string.");
/*    */             }
/* 63 */             if (c == q) {
/* 64 */               return sb.toString();
/*    */             }
/* 66 */             sb.append(c);
/*    */           }  break;
/*    */         } 
/*    */         while (true) {
/* 70 */           if (c == '\000' || Character.isWhitespace(c)) {
/* 71 */             return sb.toString();
/*    */           }
/* 73 */           sb.append(c);
/* 74 */           c = next();
/*    */         } 
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\json\HTTPTokener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */