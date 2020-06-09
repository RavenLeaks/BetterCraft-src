/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class RidingToPassengers
/*    */   implements IFixableData {
/*    */   public int getFixVersion() {
/* 11 */     return 135;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 16 */     while (compound.hasKey("Riding", 10)) {
/*    */       
/* 18 */       NBTTagCompound nbttagcompound = extractVehicle(compound);
/* 19 */       addPassengerToVehicle(compound, nbttagcompound);
/* 20 */       compound = nbttagcompound;
/*    */     } 
/*    */     
/* 23 */     return compound;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addPassengerToVehicle(NBTTagCompound p_188219_1_, NBTTagCompound p_188219_2_) {
/* 28 */     NBTTagList nbttaglist = new NBTTagList();
/* 29 */     nbttaglist.appendTag((NBTBase)p_188219_1_);
/* 30 */     p_188219_2_.setTag("Passengers", (NBTBase)nbttaglist);
/*    */   }
/*    */ 
/*    */   
/*    */   protected NBTTagCompound extractVehicle(NBTTagCompound p_188220_1_) {
/* 35 */     NBTTagCompound nbttagcompound = p_188220_1_.getCompoundTag("Riding");
/* 36 */     p_188220_1_.removeTag("Riding");
/* 37 */     return nbttagcompound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\RidingToPassengers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */