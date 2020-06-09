/*    */ package me.nzxter.bettercraft.mods.altmanager;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Alt
/*    */ {
/*    */   @SerializedName("email")
/*    */   public String email;
/*    */   @SerializedName("name")
/*    */   public String name;
/*    */   @SerializedName("password")
/*    */   public String password;
/*    */   @SerializedName("cracked")
/*    */   public boolean cracked;
/*    */   
/*    */   public Alt(String email, String password) {
/* 21 */     this.email = email;
/* 22 */     if (password == null || password.isEmpty()) {
/* 23 */       this.name = email;
/* 24 */       this.password = null;
/* 25 */       this.cracked = true;
/*    */     } else {
/* 27 */       this.name = email;
/* 28 */       this.password = password;
/* 29 */       this.cracked = false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 35 */     return "Alt [email=" + this.email + ", name=" + this.name + ", password=" + this.password + ", cracked=" + this.cracked + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\altmanager\Alt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */