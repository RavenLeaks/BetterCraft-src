/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class HorseSplit
/*    */   implements IFixableData
/*    */ {
/*    */   public int getFixVersion() {
/* 10 */     return 703;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 15 */     if ("EntityHorse".equals(compound.getString("id"))) {
/*    */       
/* 17 */       int i = compound.getInteger("Type");
/*    */       
/* 19 */       switch (i) {
/*    */ 
/*    */         
/*    */         default:
/* 23 */           compound.setString("id", "Horse");
/*    */           break;
/*    */         
/*    */         case 1:
/* 27 */           compound.setString("id", "Donkey");
/*    */           break;
/*    */         
/*    */         case 2:
/* 31 */           compound.setString("id", "Mule");
/*    */           break;
/*    */         
/*    */         case 3:
/* 35 */           compound.setString("id", "ZombieHorse");
/*    */           break;
/*    */         
/*    */         case 4:
/* 39 */           compound.setString("id", "SkeletonHorse");
/*    */           break;
/*    */       } 
/* 42 */       compound.removeTag("Type");
/*    */     } 
/*    */     
/* 45 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\HorseSplit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */