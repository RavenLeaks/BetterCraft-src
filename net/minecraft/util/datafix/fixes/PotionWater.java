/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class PotionWater
/*    */   implements IFixableData {
/*    */   public int getFixVersion() {
/* 10 */     return 806;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 15 */     String s = compound.getString("id");
/*    */     
/* 17 */     if ("minecraft:potion".equals(s) || "minecraft:splash_potion".equals(s) || "minecraft:lingering_potion".equals(s) || "minecraft:tipped_arrow".equals(s)) {
/*    */       
/* 19 */       NBTTagCompound nbttagcompound = compound.getCompoundTag("tag");
/*    */       
/* 21 */       if (!nbttagcompound.hasKey("Potion", 8))
/*    */       {
/* 23 */         nbttagcompound.setString("Potion", "minecraft:water");
/*    */       }
/*    */       
/* 26 */       if (!compound.hasKey("tag", 10))
/*    */       {
/* 28 */         compound.setTag("tag", (NBTBase)nbttagcompound);
/*    */       }
/*    */     } 
/*    */     
/* 32 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\PotionWater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */