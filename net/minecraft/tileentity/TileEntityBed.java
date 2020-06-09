/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import net.minecraft.block.BlockBed;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*    */ 
/*    */ public class TileEntityBed
/*    */   extends TileEntity {
/* 12 */   private EnumDyeColor field_193053_a = EnumDyeColor.RED;
/*    */ 
/*    */   
/*    */   public void func_193051_a(ItemStack p_193051_1_) {
/* 16 */     func_193052_a(EnumDyeColor.byMetadata(p_193051_1_.getMetadata()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 21 */     super.readFromNBT(compound);
/*    */     
/* 23 */     if (compound.hasKey("color"))
/*    */     {
/* 25 */       this.field_193053_a = EnumDyeColor.byMetadata(compound.getInteger("color"));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 31 */     super.writeToNBT(compound);
/* 32 */     compound.setInteger("color", this.field_193053_a.getMetadata());
/* 33 */     return compound;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound getUpdateTag() {
/* 38 */     return writeToNBT(new NBTTagCompound());
/*    */   }
/*    */ 
/*    */   
/*    */   public SPacketUpdateTileEntity getUpdatePacket() {
/* 43 */     return new SPacketUpdateTileEntity(this.pos, 11, getUpdateTag());
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumDyeColor func_193048_a() {
/* 48 */     return this.field_193053_a;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_193052_a(EnumDyeColor p_193052_1_) {
/* 53 */     this.field_193053_a = p_193052_1_;
/* 54 */     markDirty();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_193050_e() {
/* 59 */     return BlockBed.func_193385_b(getBlockMetadata());
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_193049_f() {
/* 64 */     return new ItemStack(Items.BED, 1, this.field_193053_a.getMetadata());
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityBed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */