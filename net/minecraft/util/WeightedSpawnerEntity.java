/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public class WeightedSpawnerEntity
/*    */   extends WeightedRandom.Item {
/*    */   private final NBTTagCompound nbt;
/*    */   
/*    */   public WeightedSpawnerEntity() {
/* 11 */     super(1);
/* 12 */     this.nbt = new NBTTagCompound();
/* 13 */     this.nbt.setString("id", "minecraft:pig");
/*    */   }
/*    */ 
/*    */   
/*    */   public WeightedSpawnerEntity(NBTTagCompound nbtIn) {
/* 18 */     this(nbtIn.hasKey("Weight", 99) ? nbtIn.getInteger("Weight") : 1, nbtIn.getCompoundTag("Entity"));
/*    */   }
/*    */ 
/*    */   
/*    */   public WeightedSpawnerEntity(int itemWeightIn, NBTTagCompound nbtIn) {
/* 23 */     super(itemWeightIn);
/* 24 */     this.nbt = nbtIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound toCompoundTag() {
/* 29 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*    */     
/* 31 */     if (!this.nbt.hasKey("id", 8)) {
/*    */       
/* 33 */       this.nbt.setString("id", "minecraft:pig");
/*    */     }
/* 35 */     else if (!this.nbt.getString("id").contains(":")) {
/*    */       
/* 37 */       this.nbt.setString("id", (new ResourceLocation(this.nbt.getString("id"))).toString());
/*    */     } 
/*    */     
/* 40 */     nbttagcompound.setTag("Entity", (NBTBase)this.nbt);
/* 41 */     nbttagcompound.setInteger("Weight", this.itemWeight);
/* 42 */     return nbttagcompound;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound getNbt() {
/* 47 */     return this.nbt;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\WeightedSpawnerEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */