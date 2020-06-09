/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityEnderChest
/*     */   extends TileEntity
/*     */   implements ITickable
/*     */ {
/*     */   public float lidAngle;
/*     */   public float prevLidAngle;
/*     */   public int numPlayersUsing;
/*     */   private int ticksSinceSync;
/*     */   
/*     */   public void update() {
/*  23 */     if (++this.ticksSinceSync % 20 * 4 == 0)
/*     */     {
/*  25 */       this.world.addBlockEvent(this.pos, Blocks.ENDER_CHEST, 1, this.numPlayersUsing);
/*     */     }
/*     */     
/*  28 */     this.prevLidAngle = this.lidAngle;
/*  29 */     int i = this.pos.getX();
/*  30 */     int j = this.pos.getY();
/*  31 */     int k = this.pos.getZ();
/*  32 */     float f = 0.1F;
/*     */     
/*  34 */     if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
/*     */       
/*  36 */       double d0 = i + 0.5D;
/*  37 */       double d1 = k + 0.5D;
/*  38 */       this.world.playSound(null, d0, j + 0.5D, d1, SoundEvents.BLOCK_ENDERCHEST_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
/*     */     } 
/*     */     
/*  41 */     if ((this.numPlayersUsing == 0 && this.lidAngle > 0.0F) || (this.numPlayersUsing > 0 && this.lidAngle < 1.0F)) {
/*     */       
/*  43 */       float f2 = this.lidAngle;
/*     */       
/*  45 */       if (this.numPlayersUsing > 0) {
/*     */         
/*  47 */         this.lidAngle += 0.1F;
/*     */       }
/*     */       else {
/*     */         
/*  51 */         this.lidAngle -= 0.1F;
/*     */       } 
/*     */       
/*  54 */       if (this.lidAngle > 1.0F)
/*     */       {
/*  56 */         this.lidAngle = 1.0F;
/*     */       }
/*     */       
/*  59 */       float f1 = 0.5F;
/*     */       
/*  61 */       if (this.lidAngle < 0.5F && f2 >= 0.5F) {
/*     */         
/*  63 */         double d3 = i + 0.5D;
/*  64 */         double d2 = k + 0.5D;
/*  65 */         this.world.playSound(null, d3, j + 0.5D, d2, SoundEvents.BLOCK_ENDERCHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
/*     */       } 
/*     */       
/*  68 */       if (this.lidAngle < 0.0F)
/*     */       {
/*  70 */         this.lidAngle = 0.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/*  77 */     if (id == 1) {
/*     */       
/*  79 */       this.numPlayersUsing = type;
/*  80 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  84 */     return super.receiveClientEvent(id, type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {
/*  93 */     updateContainingBlockInfo();
/*  94 */     super.invalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void openChest() {
/*  99 */     this.numPlayersUsing++;
/* 100 */     this.world.addBlockEvent(this.pos, Blocks.ENDER_CHEST, 1, this.numPlayersUsing);
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeChest() {
/* 105 */     this.numPlayersUsing--;
/* 106 */     this.world.addBlockEvent(this.pos, Blocks.ENDER_CHEST, 1, this.numPlayersUsing);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeUsed(EntityPlayer player) {
/* 111 */     if (this.world.getTileEntity(this.pos) != this)
/*     */     {
/* 113 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 117 */     return (player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityEnderChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */