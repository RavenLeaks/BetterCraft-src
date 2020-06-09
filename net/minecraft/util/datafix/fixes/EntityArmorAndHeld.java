/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagFloat;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class EntityArmorAndHeld
/*    */   implements IFixableData {
/*    */   public int getFixVersion() {
/* 12 */     return 100;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 17 */     NBTTagList nbttaglist = compound.getTagList("Equipment", 10);
/*    */     
/* 19 */     if (!nbttaglist.hasNoTags() && !compound.hasKey("HandItems", 10)) {
/*    */       
/* 21 */       NBTTagList nbttaglist1 = new NBTTagList();
/* 22 */       nbttaglist1.appendTag(nbttaglist.get(0));
/* 23 */       nbttaglist1.appendTag((NBTBase)new NBTTagCompound());
/* 24 */       compound.setTag("HandItems", (NBTBase)nbttaglist1);
/*    */     } 
/*    */     
/* 27 */     if (nbttaglist.tagCount() > 1 && !compound.hasKey("ArmorItem", 10)) {
/*    */       
/* 29 */       NBTTagList nbttaglist3 = new NBTTagList();
/* 30 */       nbttaglist3.appendTag((NBTBase)nbttaglist.getCompoundTagAt(1));
/* 31 */       nbttaglist3.appendTag((NBTBase)nbttaglist.getCompoundTagAt(2));
/* 32 */       nbttaglist3.appendTag((NBTBase)nbttaglist.getCompoundTagAt(3));
/* 33 */       nbttaglist3.appendTag((NBTBase)nbttaglist.getCompoundTagAt(4));
/* 34 */       compound.setTag("ArmorItems", (NBTBase)nbttaglist3);
/*    */     } 
/*    */     
/* 37 */     compound.removeTag("Equipment");
/*    */     
/* 39 */     if (compound.hasKey("DropChances", 9)) {
/*    */       
/* 41 */       NBTTagList nbttaglist4 = compound.getTagList("DropChances", 5);
/*    */       
/* 43 */       if (!compound.hasKey("HandDropChances", 10)) {
/*    */         
/* 45 */         NBTTagList nbttaglist2 = new NBTTagList();
/* 46 */         nbttaglist2.appendTag((NBTBase)new NBTTagFloat(nbttaglist4.getFloatAt(0)));
/* 47 */         nbttaglist2.appendTag((NBTBase)new NBTTagFloat(0.0F));
/* 48 */         compound.setTag("HandDropChances", (NBTBase)nbttaglist2);
/*    */       } 
/*    */       
/* 51 */       if (!compound.hasKey("ArmorDropChances", 10)) {
/*    */         
/* 53 */         NBTTagList nbttaglist5 = new NBTTagList();
/* 54 */         nbttaglist5.appendTag((NBTBase)new NBTTagFloat(nbttaglist4.getFloatAt(1)));
/* 55 */         nbttaglist5.appendTag((NBTBase)new NBTTagFloat(nbttaglist4.getFloatAt(2)));
/* 56 */         nbttaglist5.appendTag((NBTBase)new NBTTagFloat(nbttaglist4.getFloatAt(3)));
/* 57 */         nbttaglist5.appendTag((NBTBase)new NBTTagFloat(nbttaglist4.getFloatAt(4)));
/* 58 */         compound.setTag("ArmorDropChances", (NBTBase)nbttaglist5);
/*    */       } 
/*    */       
/* 61 */       compound.removeTag("DropChances");
/*    */     } 
/*    */     
/* 64 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\EntityArmorAndHeld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */