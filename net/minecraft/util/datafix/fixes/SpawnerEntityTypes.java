/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class SpawnerEntityTypes
/*    */   implements IFixableData {
/*    */   public int getFixVersion() {
/* 11 */     return 107;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 16 */     if (!"MobSpawner".equals(compound.getString("id")))
/*    */     {
/* 18 */       return compound;
/*    */     }
/*    */ 
/*    */     
/* 22 */     if (compound.hasKey("EntityId", 8)) {
/*    */       
/* 24 */       String s = compound.getString("EntityId");
/* 25 */       NBTTagCompound nbttagcompound = compound.getCompoundTag("SpawnData");
/* 26 */       nbttagcompound.setString("id", s.isEmpty() ? "Pig" : s);
/* 27 */       compound.setTag("SpawnData", (NBTBase)nbttagcompound);
/* 28 */       compound.removeTag("EntityId");
/*    */     } 
/*    */     
/* 31 */     if (compound.hasKey("SpawnPotentials", 9)) {
/*    */       
/* 33 */       NBTTagList nbttaglist = compound.getTagList("SpawnPotentials", 10);
/*    */       
/* 35 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*    */         
/* 37 */         NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/*    */         
/* 39 */         if (nbttagcompound1.hasKey("Type", 8)) {
/*    */           
/* 41 */           NBTTagCompound nbttagcompound2 = nbttagcompound1.getCompoundTag("Properties");
/* 42 */           nbttagcompound2.setString("id", nbttagcompound1.getString("Type"));
/* 43 */           nbttagcompound1.setTag("Entity", (NBTBase)nbttagcompound2);
/* 44 */           nbttagcompound1.removeTag("Type");
/* 45 */           nbttagcompound1.removeTag("Properties");
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 50 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\SpawnerEntityTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */