/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public class TileEntityComparator
/*    */   extends TileEntity
/*    */ {
/*    */   private int outputSignal;
/*    */   
/*    */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 11 */     super.writeToNBT(compound);
/* 12 */     compound.setInteger("OutputSignal", this.outputSignal);
/* 13 */     return compound;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 18 */     super.readFromNBT(compound);
/* 19 */     this.outputSignal = compound.getInteger("OutputSignal");
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOutputSignal() {
/* 24 */     return this.outputSignal;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setOutputSignal(int outputSignalIn) {
/* 29 */     this.outputSignal = outputSignalIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */