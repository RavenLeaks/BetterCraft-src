/*    */ package net.minecraft.world;
/*    */ 
/*    */ import javax.annotation.concurrent.Immutable;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ @Immutable
/*    */ public class LockCode
/*    */ {
/*  9 */   public static final LockCode EMPTY_CODE = new LockCode("");
/*    */   
/*    */   private final String lock;
/*    */   
/*    */   public LockCode(String code) {
/* 14 */     this.lock = code;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 19 */     return !(this.lock != null && !this.lock.isEmpty());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLock() {
/* 24 */     return this.lock;
/*    */   }
/*    */ 
/*    */   
/*    */   public void toNBT(NBTTagCompound nbt) {
/* 29 */     nbt.setString("Lock", this.lock);
/*    */   }
/*    */ 
/*    */   
/*    */   public static LockCode fromNBT(NBTTagCompound nbt) {
/* 34 */     if (nbt.hasKey("Lock", 8)) {
/*    */       
/* 36 */       String s = nbt.getString("Lock");
/* 37 */       return new LockCode(s);
/*    */     } 
/*    */ 
/*    */     
/* 41 */     return EMPTY_CODE;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\LockCode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */