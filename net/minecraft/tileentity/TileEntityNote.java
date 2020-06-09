/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityNote
/*     */   extends TileEntity
/*     */ {
/*     */   public byte note;
/*     */   public boolean previousRedstoneState;
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/*  22 */     super.writeToNBT(compound);
/*  23 */     compound.setByte("note", this.note);
/*  24 */     compound.setBoolean("powered", this.previousRedstoneState);
/*  25 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  30 */     super.readFromNBT(compound);
/*  31 */     this.note = compound.getByte("note");
/*  32 */     this.note = (byte)MathHelper.clamp(this.note, 0, 24);
/*  33 */     this.previousRedstoneState = compound.getBoolean("powered");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void changePitch() {
/*  41 */     this.note = (byte)((this.note + 1) % 25);
/*  42 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void triggerNote(World worldIn, BlockPos posIn) {
/*  47 */     if (worldIn.getBlockState(posIn.up()).getMaterial() == Material.AIR) {
/*     */       
/*  49 */       IBlockState iblockstate = worldIn.getBlockState(posIn.down());
/*  50 */       Material material = iblockstate.getMaterial();
/*  51 */       int i = 0;
/*     */       
/*  53 */       if (material == Material.ROCK)
/*     */       {
/*  55 */         i = 1;
/*     */       }
/*     */       
/*  58 */       if (material == Material.SAND)
/*     */       {
/*  60 */         i = 2;
/*     */       }
/*     */       
/*  63 */       if (material == Material.GLASS)
/*     */       {
/*  65 */         i = 3;
/*     */       }
/*     */       
/*  68 */       if (material == Material.WOOD)
/*     */       {
/*  70 */         i = 4;
/*     */       }
/*     */       
/*  73 */       Block block = iblockstate.getBlock();
/*     */       
/*  75 */       if (block == Blocks.CLAY)
/*     */       {
/*  77 */         i = 5;
/*     */       }
/*     */       
/*  80 */       if (block == Blocks.GOLD_BLOCK)
/*     */       {
/*  82 */         i = 6;
/*     */       }
/*     */       
/*  85 */       if (block == Blocks.WOOL)
/*     */       {
/*  87 */         i = 7;
/*     */       }
/*     */       
/*  90 */       if (block == Blocks.PACKED_ICE)
/*     */       {
/*  92 */         i = 8;
/*     */       }
/*     */       
/*  95 */       if (block == Blocks.BONE_BLOCK)
/*     */       {
/*  97 */         i = 9;
/*     */       }
/*     */       
/* 100 */       worldIn.addBlockEvent(posIn, Blocks.NOTEBLOCK, i, this.note);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityNote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */