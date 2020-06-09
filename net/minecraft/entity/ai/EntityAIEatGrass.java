/*     */ package net.minecraft.entity.ai;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockTallGrass;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockStateMatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityAIEatGrass extends EntityAIBase {
/*  16 */   private static final Predicate<IBlockState> IS_TALL_GRASS = (Predicate<IBlockState>)BlockStateMatcher.forBlock((Block)Blocks.TALLGRASS).where((IProperty)BlockTallGrass.TYPE, Predicates.equalTo(BlockTallGrass.EnumType.GRASS));
/*     */ 
/*     */   
/*     */   private final EntityLiving grassEaterEntity;
/*     */ 
/*     */   
/*     */   private final World entityWorld;
/*     */ 
/*     */   
/*     */   int eatingGrassTimer;
/*     */ 
/*     */   
/*     */   public EntityAIEatGrass(EntityLiving grassEaterEntityIn) {
/*  29 */     this.grassEaterEntity = grassEaterEntityIn;
/*  30 */     this.entityWorld = grassEaterEntityIn.world;
/*  31 */     setMutexBits(7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  39 */     if (this.grassEaterEntity.getRNG().nextInt(this.grassEaterEntity.isChild() ? 50 : 1000) != 0)
/*     */     {
/*  41 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  45 */     BlockPos blockpos = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);
/*     */     
/*  47 */     if (IS_TALL_GRASS.apply(this.entityWorld.getBlockState(blockpos)))
/*     */     {
/*  49 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  53 */     return (this.entityWorld.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  63 */     this.eatingGrassTimer = 40;
/*  64 */     this.entityWorld.setEntityState((Entity)this.grassEaterEntity, (byte)10);
/*  65 */     this.grassEaterEntity.getNavigator().clearPathEntity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  73 */     this.eatingGrassTimer = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  81 */     return (this.eatingGrassTimer > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEatingGrassTimer() {
/*  89 */     return this.eatingGrassTimer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  97 */     this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
/*     */     
/*  99 */     if (this.eatingGrassTimer == 4) {
/*     */       
/* 101 */       BlockPos blockpos = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);
/*     */       
/* 103 */       if (IS_TALL_GRASS.apply(this.entityWorld.getBlockState(blockpos))) {
/*     */         
/* 105 */         if (this.entityWorld.getGameRules().getBoolean("mobGriefing"))
/*     */         {
/* 107 */           this.entityWorld.destroyBlock(blockpos, false);
/*     */         }
/*     */         
/* 110 */         this.grassEaterEntity.eatGrassBonus();
/*     */       }
/*     */       else {
/*     */         
/* 114 */         BlockPos blockpos1 = blockpos.down();
/*     */         
/* 116 */         if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.GRASS) {
/*     */           
/* 118 */           if (this.entityWorld.getGameRules().getBoolean("mobGriefing")) {
/*     */             
/* 120 */             this.entityWorld.playEvent(2001, blockpos1, Block.getIdFromBlock((Block)Blocks.GRASS));
/* 121 */             this.entityWorld.setBlockState(blockpos1, Blocks.DIRT.getDefaultState(), 2);
/*     */           } 
/*     */           
/* 124 */           this.grassEaterEntity.eatGrassBonus();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIEatGrass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */