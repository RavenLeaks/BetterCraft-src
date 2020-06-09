/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class ElderGuardianSplit
/*    */   implements IFixableData
/*    */ {
/*    */   public int getFixVersion() {
/* 10 */     return 700;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 15 */     if ("Guardian".equals(compound.getString("id"))) {
/*    */       
/* 17 */       if (compound.getBoolean("Elder"))
/*    */       {
/* 19 */         compound.setString("id", "ElderGuardian");
/*    */       }
/*    */       
/* 22 */       compound.removeTag("Elder");
/*    */     } 
/*    */     
/* 25 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\ElderGuardianSplit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */