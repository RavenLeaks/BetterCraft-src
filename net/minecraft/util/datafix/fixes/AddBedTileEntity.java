/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class AddBedTileEntity implements IFixableData {
/* 11 */   private static final Logger field_193842_a = LogManager.getLogger();
/*    */ 
/*    */   
/*    */   public int getFixVersion() {
/* 15 */     return 1125;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 20 */     int i = 416;
/*    */ 
/*    */     
/*    */     try {
/* 24 */       NBTTagCompound nbttagcompound = compound.getCompoundTag("Level");
/* 25 */       int j = nbttagcompound.getInteger("xPos");
/* 26 */       int k = nbttagcompound.getInteger("zPos");
/* 27 */       NBTTagList nbttaglist = nbttagcompound.getTagList("TileEntities", 10);
/* 28 */       NBTTagList nbttaglist1 = nbttagcompound.getTagList("Sections", 10);
/*    */       
/* 30 */       for (int l = 0; l < nbttaglist1.tagCount(); l++) {
/*    */         
/* 32 */         NBTTagCompound nbttagcompound1 = nbttaglist1.getCompoundTagAt(l);
/* 33 */         int i1 = nbttagcompound1.getByte("Y");
/* 34 */         byte[] abyte = nbttagcompound1.getByteArray("Blocks");
/*    */         
/* 36 */         for (int j1 = 0; j1 < abyte.length; j1++) {
/*    */           
/* 38 */           if (416 == (abyte[j1] & 0xFF) << 4)
/*    */           {
/* 40 */             int k1 = j1 & 0xF;
/* 41 */             int l1 = j1 >> 8 & 0xF;
/* 42 */             int i2 = j1 >> 4 & 0xF;
/* 43 */             NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 44 */             nbttagcompound2.setString("id", "bed");
/* 45 */             nbttagcompound2.setInteger("x", k1 + (j << 4));
/* 46 */             nbttagcompound2.setInteger("y", l1 + (i1 << 4));
/* 47 */             nbttagcompound2.setInteger("z", i2 + (k << 4));
/* 48 */             nbttaglist.appendTag((NBTBase)nbttagcompound2);
/*    */           }
/*    */         
/*    */         } 
/*    */       } 
/* 53 */     } catch (Exception var17) {
/*    */       
/* 55 */       field_193842_a.warn("Unable to datafix Bed blocks, level format may be missing tags.");
/*    */     } 
/*    */     
/* 58 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\AddBedTileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */