/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ import net.minecraft.world.ILockableContainer;
/*    */ import net.minecraft.world.LockCode;
/*    */ 
/*    */ public abstract class TileEntityLockable
/*    */   extends TileEntity implements ILockableContainer {
/* 12 */   private LockCode code = LockCode.EMPTY_CODE;
/*    */ 
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 16 */     super.readFromNBT(compound);
/* 17 */     this.code = LockCode.fromNBT(compound);
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 22 */     super.writeToNBT(compound);
/*    */     
/* 24 */     if (this.code != null)
/*    */     {
/* 26 */       this.code.toNBT(compound);
/*    */     }
/*    */     
/* 29 */     return compound;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLocked() {
/* 34 */     return (this.code != null && !this.code.isEmpty());
/*    */   }
/*    */ 
/*    */   
/*    */   public LockCode getLockCode() {
/* 39 */     return this.code;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLockCode(LockCode code) {
/* 44 */     this.code = code;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ITextComponent getDisplayName() {
/* 52 */     return hasCustomName() ? (ITextComponent)new TextComponentString(getName()) : (ITextComponent)new TextComponentTranslation(getName(), new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityLockable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */