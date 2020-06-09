/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EntityAIMoveToBlock
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final EntityCreature theEntity;
/*     */   private final double movementSpeed;
/*     */   protected int runDelay;
/*     */   private int timeoutCounter;
/*     */   private int maxStayTicks;
/*  18 */   protected BlockPos destinationBlock = BlockPos.ORIGIN;
/*     */   
/*     */   private boolean isAboveDestination;
/*     */   private final int searchLength;
/*     */   
/*     */   public EntityAIMoveToBlock(EntityCreature creature, double speedIn, int length) {
/*  24 */     this.theEntity = creature;
/*  25 */     this.movementSpeed = speedIn;
/*  26 */     this.searchLength = length;
/*  27 */     setMutexBits(5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  35 */     if (this.runDelay > 0) {
/*     */       
/*  37 */       this.runDelay--;
/*  38 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  42 */     this.runDelay = 200 + this.theEntity.getRNG().nextInt(200);
/*  43 */     return searchForDestination();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  52 */     return (this.timeoutCounter >= -this.maxStayTicks && this.timeoutCounter <= 1200 && shouldMoveTo(this.theEntity.world, this.destinationBlock));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  60 */     this.theEntity.getNavigator().tryMoveToXYZ(this.destinationBlock.getX() + 0.5D, (this.destinationBlock.getY() + 1), this.destinationBlock.getZ() + 0.5D, this.movementSpeed);
/*  61 */     this.timeoutCounter = 0;
/*  62 */     this.maxStayTicks = this.theEntity.getRNG().nextInt(this.theEntity.getRNG().nextInt(1200) + 1200) + 1200;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  70 */     if (this.theEntity.getDistanceSqToCenter(this.destinationBlock.up()) > 1.0D) {
/*     */       
/*  72 */       this.isAboveDestination = false;
/*  73 */       this.timeoutCounter++;
/*     */       
/*  75 */       if (this.timeoutCounter % 40 == 0)
/*     */       {
/*  77 */         this.theEntity.getNavigator().tryMoveToXYZ(this.destinationBlock.getX() + 0.5D, (this.destinationBlock.getY() + 1), this.destinationBlock.getZ() + 0.5D, this.movementSpeed);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  82 */       this.isAboveDestination = true;
/*  83 */       this.timeoutCounter--;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean getIsAboveDestination() {
/*  89 */     return this.isAboveDestination;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean searchForDestination() {
/*  99 */     int i = this.searchLength;
/* 100 */     int j = 1;
/* 101 */     BlockPos blockpos = new BlockPos((Entity)this.theEntity);
/*     */     
/* 103 */     for (int k = 0; k <= 1; k = (k > 0) ? -k : (1 - k)) {
/*     */       
/* 105 */       for (int l = 0; l < i; l++) {
/*     */         
/* 107 */         for (int i1 = 0; i1 <= l; i1 = (i1 > 0) ? -i1 : (1 - i1)) {
/*     */           
/* 109 */           for (int j1 = (i1 < l && i1 > -l) ? l : 0; j1 <= l; j1 = (j1 > 0) ? -j1 : (1 - j1)) {
/*     */             
/* 111 */             BlockPos blockpos1 = blockpos.add(i1, k - 1, j1);
/*     */             
/* 113 */             if (this.theEntity.isWithinHomeDistanceFromPosition(blockpos1) && shouldMoveTo(this.theEntity.world, blockpos1)) {
/*     */               
/* 115 */               this.destinationBlock = blockpos1;
/* 116 */               return true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract boolean shouldMoveTo(World paramWorld, BlockPos paramBlockPos);
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIMoveToBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */