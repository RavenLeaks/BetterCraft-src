/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class ZombieSplit
/*    */   implements IFixableData
/*    */ {
/*    */   public int getFixVersion() {
/* 10 */     return 702;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 15 */     if ("Zombie".equals(compound.getString("id"))) {
/*    */       
/* 17 */       int i = compound.getInteger("ZombieType");
/*    */       
/* 19 */       switch (i) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/*    */         case 1:
/*    */         case 2:
/*    */         case 3:
/*    */         case 4:
/*    */         case 5:
/* 30 */           compound.setString("id", "ZombieVillager");
/* 31 */           compound.setInteger("Profession", i - 1);
/*    */           break;
/*    */         
/*    */         case 6:
/* 35 */           compound.setString("id", "Husk");
/*    */           break;
/*    */       } 
/* 38 */       compound.removeTag("ZombieType");
/*    */     } 
/*    */     
/* 41 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\ZombieSplit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */