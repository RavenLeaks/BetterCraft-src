/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class SkeletonSplit
/*    */   implements IFixableData
/*    */ {
/*    */   public int getFixVersion() {
/* 10 */     return 701;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 15 */     String s = compound.getString("id");
/*    */     
/* 17 */     if ("Skeleton".equals(s)) {
/*    */       
/* 19 */       int i = compound.getInteger("SkeletonType");
/*    */       
/* 21 */       if (i == 1) {
/*    */         
/* 23 */         compound.setString("id", "WitherSkeleton");
/*    */       }
/* 25 */       else if (i == 2) {
/*    */         
/* 27 */         compound.setString("id", "Stray");
/*    */       } 
/*    */       
/* 30 */       compound.removeTag("SkeletonType");
/*    */     } 
/*    */     
/* 33 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\SkeletonSplit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */