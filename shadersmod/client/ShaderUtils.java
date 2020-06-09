/*    */ package shadersmod.client;
/*    */ 
/*    */ import optifine.Config;
/*    */ 
/*    */ 
/*    */ public class ShaderUtils
/*    */ {
/*    */   public static ShaderOption getShaderOption(String name, ShaderOption[] opts) {
/*  9 */     if (opts == null)
/*    */     {
/* 11 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 15 */     for (int i = 0; i < opts.length; i++) {
/*    */       
/* 17 */       ShaderOption shaderoption = opts[i];
/*    */       
/* 19 */       if (shaderoption.getName().equals(name))
/*    */       {
/* 21 */         return shaderoption;
/*    */       }
/*    */     } 
/*    */     
/* 25 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ShaderProfile detectProfile(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
/* 31 */     if (profs == null)
/*    */     {
/* 33 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 37 */     for (int i = 0; i < profs.length; i++) {
/*    */       
/* 39 */       ShaderProfile shaderprofile = profs[i];
/*    */       
/* 41 */       if (matchProfile(shaderprofile, opts, def))
/*    */       {
/* 43 */         return shaderprofile;
/*    */       }
/*    */     } 
/*    */     
/* 47 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean matchProfile(ShaderProfile prof, ShaderOption[] opts, boolean def) {
/* 53 */     if (prof == null)
/*    */     {
/* 55 */       return false;
/*    */     }
/* 57 */     if (opts == null)
/*    */     {
/* 59 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 63 */     String[] astring = prof.getOptions();
/*    */     
/* 65 */     for (int i = 0; i < astring.length; i++) {
/*    */       
/* 67 */       String s = astring[i];
/* 68 */       ShaderOption shaderoption = getShaderOption(s, opts);
/*    */       
/* 70 */       if (shaderoption != null) {
/*    */         
/* 72 */         String s1 = def ? shaderoption.getValueDefault() : shaderoption.getValue();
/* 73 */         String s2 = prof.getValue(s);
/*    */         
/* 75 */         if (!Config.equals(s1, s2))
/*    */         {
/* 77 */           return false;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 82 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */