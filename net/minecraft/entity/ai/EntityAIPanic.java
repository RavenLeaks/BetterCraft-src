/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityAIPanic
/*     */   extends EntityAIBase {
/*     */   protected final EntityCreature theEntityCreature;
/*     */   protected double speed;
/*     */   protected double randPosX;
/*     */   protected double randPosY;
/*     */   protected double randPosZ;
/*     */   
/*     */   public EntityAIPanic(EntityCreature creature, double speedIn) {
/*  22 */     this.theEntityCreature = creature;
/*  23 */     this.speed = speedIn;
/*  24 */     setMutexBits(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  32 */     if (this.theEntityCreature.getAITarget() == null && !this.theEntityCreature.isBurning())
/*     */     {
/*  34 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  38 */     if (this.theEntityCreature.isBurning()) {
/*     */       
/*  40 */       BlockPos blockpos = getRandPos(this.theEntityCreature.world, (Entity)this.theEntityCreature, 5, 4);
/*     */       
/*  42 */       if (blockpos != null) {
/*     */         
/*  44 */         this.randPosX = blockpos.getX();
/*  45 */         this.randPosY = blockpos.getY();
/*  46 */         this.randPosZ = blockpos.getZ();
/*  47 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  51 */     return func_190863_f();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_190863_f() {
/*  57 */     Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);
/*     */     
/*  59 */     if (vec3d == null)
/*     */     {
/*  61 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  65 */     this.randPosX = vec3d.xCoord;
/*  66 */     this.randPosY = vec3d.yCoord;
/*  67 */     this.randPosZ = vec3d.zCoord;
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  77 */     this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  85 */     return !this.theEntityCreature.getNavigator().noPath();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private BlockPos getRandPos(World worldIn, Entity entityIn, int horizontalRange, int verticalRange) {
/*  91 */     BlockPos blockpos = new BlockPos(entityIn);
/*  92 */     int i = blockpos.getX();
/*  93 */     int j = blockpos.getY();
/*  94 */     int k = blockpos.getZ();
/*  95 */     float f = (horizontalRange * horizontalRange * verticalRange * 2);
/*  96 */     BlockPos blockpos1 = null;
/*  97 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/*  99 */     for (int l = i - horizontalRange; l <= i + horizontalRange; l++) {
/*     */       
/* 101 */       for (int i1 = j - verticalRange; i1 <= j + verticalRange; i1++) {
/*     */         
/* 103 */         for (int j1 = k - horizontalRange; j1 <= k + horizontalRange; j1++) {
/*     */           
/* 105 */           blockpos$mutableblockpos.setPos(l, i1, j1);
/* 106 */           IBlockState iblockstate = worldIn.getBlockState((BlockPos)blockpos$mutableblockpos);
/*     */           
/* 108 */           if (iblockstate.getMaterial() == Material.WATER) {
/*     */             
/* 110 */             float f1 = ((l - i) * (l - i) + (i1 - j) * (i1 - j) + (j1 - k) * (j1 - k));
/*     */             
/* 112 */             if (f1 < f) {
/*     */               
/* 114 */               f = f1;
/* 115 */               blockpos1 = new BlockPos((Vec3i)blockpos$mutableblockpos);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     return blockpos1;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIPanic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */