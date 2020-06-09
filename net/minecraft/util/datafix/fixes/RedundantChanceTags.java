/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class RedundantChanceTags
/*    */   implements IFixableData
/*    */ {
/*    */   public int getFixVersion() {
/* 11 */     return 113;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 16 */     if (compound.hasKey("HandDropChances", 9)) {
/*    */       
/* 18 */       NBTTagList nbttaglist = compound.getTagList("HandDropChances", 5);
/*    */       
/* 20 */       if (nbttaglist.tagCount() == 2 && nbttaglist.getFloatAt(0) == 0.0F && nbttaglist.getFloatAt(1) == 0.0F)
/*    */       {
/* 22 */         compound.removeTag("HandDropChances");
/*    */       }
/*    */     } 
/*    */     
/* 26 */     if (compound.hasKey("ArmorDropChances", 9)) {
/*    */       
/* 28 */       NBTTagList nbttaglist1 = compound.getTagList("ArmorDropChances", 5);
/*    */       
/* 30 */       if (nbttaglist1.tagCount() == 4 && nbttaglist1.getFloatAt(0) == 0.0F && nbttaglist1.getFloatAt(1) == 0.0F && nbttaglist1.getFloatAt(2) == 0.0F && nbttaglist1.getFloatAt(3) == 0.0F)
/*    */       {
/* 32 */         compound.removeTag("ArmorDropChances");
/*    */       }
/*    */     } 
/*    */     
/* 36 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\RedundantChanceTags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */