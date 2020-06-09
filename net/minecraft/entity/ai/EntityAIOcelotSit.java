/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBed;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityAIOcelotSit extends EntityAIMoveToBlock {
/*     */   private final EntityOcelot ocelot;
/*     */   
/*     */   public EntityAIOcelotSit(EntityOcelot ocelotIn, double p_i45315_2_) {
/*  19 */     super((EntityCreature)ocelotIn, p_i45315_2_, 8);
/*  20 */     this.ocelot = ocelotIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  28 */     return (this.ocelot.isTamed() && !this.ocelot.isSitting() && super.shouldExecute());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  36 */     super.startExecuting();
/*  37 */     this.ocelot.getAISit().setSitting(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  45 */     super.resetTask();
/*  46 */     this.ocelot.setSitting(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  54 */     super.updateTask();
/*  55 */     this.ocelot.getAISit().setSitting(false);
/*     */     
/*  57 */     if (!getIsAboveDestination()) {
/*     */       
/*  59 */       this.ocelot.setSitting(false);
/*     */     }
/*  61 */     else if (!this.ocelot.isSitting()) {
/*     */       
/*  63 */       this.ocelot.setSitting(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
/*  72 */     if (!worldIn.isAirBlock(pos.up()))
/*     */     {
/*  74 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  78 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  79 */     Block block = iblockstate.getBlock();
/*     */     
/*  81 */     if (block == Blocks.CHEST) {
/*     */       
/*  83 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  85 */       if (tileentity instanceof TileEntityChest && ((TileEntityChest)tileentity).numPlayersUsing < 1)
/*     */       {
/*  87 */         return true;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  92 */       if (block == Blocks.LIT_FURNACE)
/*     */       {
/*  94 */         return true;
/*     */       }
/*     */       
/*  97 */       if (block == Blocks.BED && iblockstate.getValue((IProperty)BlockBed.PART) != BlockBed.EnumPartType.HEAD)
/*     */       {
/*  99 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIOcelotSit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */