/*    */ package net.minecraft.util.math;
/*    */ 
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagFloat;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Rotations
/*    */ {
/*    */   protected final float x;
/*    */   protected final float y;
/*    */   protected final float z;
/*    */   
/*    */   public Rotations(float x, float y, float z) {
/* 19 */     this.x = (!Float.isInfinite(x) && !Float.isNaN(x)) ? (x % 360.0F) : 0.0F;
/* 20 */     this.y = (!Float.isInfinite(y) && !Float.isNaN(y)) ? (y % 360.0F) : 0.0F;
/* 21 */     this.z = (!Float.isInfinite(z) && !Float.isNaN(z)) ? (z % 360.0F) : 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public Rotations(NBTTagList nbt) {
/* 26 */     this(nbt.getFloatAt(0), nbt.getFloatAt(1), nbt.getFloatAt(2));
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagList writeToNBT() {
/* 31 */     NBTTagList nbttaglist = new NBTTagList();
/* 32 */     nbttaglist.appendTag((NBTBase)new NBTTagFloat(this.x));
/* 33 */     nbttaglist.appendTag((NBTBase)new NBTTagFloat(this.y));
/* 34 */     nbttaglist.appendTag((NBTBase)new NBTTagFloat(this.z));
/* 35 */     return nbttaglist;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 40 */     if (!(p_equals_1_ instanceof Rotations))
/*    */     {
/* 42 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 46 */     Rotations rotations = (Rotations)p_equals_1_;
/* 47 */     return (this.x == rotations.x && this.y == rotations.y && this.z == rotations.z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getX() {
/* 56 */     return this.x;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getY() {
/* 64 */     return this.y;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getZ() {
/* 72 */     return this.z;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\math\Rotations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */