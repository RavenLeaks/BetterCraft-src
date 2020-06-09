/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class BannerItemColor
/*    */   implements IFixableData
/*    */ {
/*    */   public int getFixVersion() {
/* 11 */     return 804;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 16 */     if ("minecraft:banner".equals(compound.getString("id")) && compound.hasKey("tag", 10)) {
/*    */       
/* 18 */       NBTTagCompound nbttagcompound = compound.getCompoundTag("tag");
/*    */       
/* 20 */       if (nbttagcompound.hasKey("BlockEntityTag", 10)) {
/*    */         
/* 22 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("BlockEntityTag");
/*    */         
/* 24 */         if (nbttagcompound1.hasKey("Base", 99)) {
/*    */           
/* 26 */           compound.setShort("Damage", (short)(nbttagcompound1.getShort("Base") & 0xF));
/*    */           
/* 28 */           if (nbttagcompound.hasKey("display", 10)) {
/*    */             
/* 30 */             NBTTagCompound nbttagcompound2 = nbttagcompound.getCompoundTag("display");
/*    */             
/* 32 */             if (nbttagcompound2.hasKey("Lore", 9)) {
/*    */               
/* 34 */               NBTTagList nbttaglist = nbttagcompound2.getTagList("Lore", 8);
/*    */               
/* 36 */               if (nbttaglist.tagCount() == 1 && "(+NBT)".equals(nbttaglist.getStringTagAt(0)))
/*    */               {
/* 38 */                 return compound;
/*    */               }
/*    */             } 
/*    */           } 
/*    */           
/* 43 */           nbttagcompound1.removeTag("Base");
/*    */           
/* 45 */           if (nbttagcompound1.hasNoTags())
/*    */           {
/* 47 */             nbttagcompound.removeTag("BlockEntityTag");
/*    */           }
/*    */           
/* 50 */           if (nbttagcompound.hasNoTags())
/*    */           {
/* 52 */             compound.removeTag("tag");
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 58 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\BannerItemColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */