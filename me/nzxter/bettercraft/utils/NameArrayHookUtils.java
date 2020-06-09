/*    */ package me.nzxter.bettercraft.utils;
/*    */ 
/*    */ import com.google.gson.annotations.Expose;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ public class NameArrayHookUtils {
/*    */   @SerializedName("name")
/*    */   @Expose
/*    */   private String name;
/*    */   public boolean e = false;
/*    */   
/*    */   public final synchronized String getName() {
/* 13 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setE(boolean e) {
/* 17 */     this.e = e;
/*    */   }
/*    */   
/*    */   public final synchronized void setName(String name) {
/* 21 */     this.name = name;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\NameArrayHookUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */