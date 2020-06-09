/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class HorseSaddle
/*    */   implements IFixableData {
/*    */   public int getFixVersion() {
/* 10 */     return 110;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 15 */     if ("EntityHorse".equals(compound.getString("id")) && !compound.hasKey("SaddleItem", 10) && compound.getBoolean("Saddle")) {
/*    */       
/* 17 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 18 */       nbttagcompound.setString("id", "minecraft:saddle");
/* 19 */       nbttagcompound.setByte("Count", (byte)1);
/* 20 */       nbttagcompound.setShort("Damage", (short)0);
/* 21 */       compound.setTag("SaddleItem", (NBTBase)nbttagcompound);
/* 22 */       compound.removeTag("Saddle");
/*    */     } 
/*    */     
/* 25 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\HorseSaddle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */