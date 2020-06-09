/*    */ package net.minecraft.entity.projectile;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntitySmallFireball
/*    */   extends EntityFireball
/*    */ {
/*    */   public EntitySmallFireball(World worldIn) {
/* 16 */     super(worldIn);
/* 17 */     setSize(0.3125F, 0.3125F);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySmallFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
/* 22 */     super(worldIn, shooter, accelX, accelY, accelZ);
/* 23 */     setSize(0.3125F, 0.3125F);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySmallFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
/* 28 */     super(worldIn, x, y, z, accelX, accelY, accelZ);
/* 29 */     setSize(0.3125F, 0.3125F);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerFixesSmallFireball(DataFixer fixer) {
/* 34 */     EntityFireball.registerFixesFireball(fixer, "SmallFireball");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onImpact(RayTraceResult result) {
/* 42 */     if (!this.world.isRemote) {
/*    */       
/* 44 */       if (result.entityHit != null) {
/*    */         
/* 46 */         if (!result.entityHit.isImmuneToFire()) {
/*    */           
/* 48 */           boolean flag = result.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, (Entity)this.shootingEntity), 5.0F);
/*    */           
/* 50 */           if (flag)
/*    */           {
/* 52 */             applyEnchantments(this.shootingEntity, result.entityHit);
/* 53 */             result.entityHit.setFire(5);
/*    */           }
/*    */         
/*    */         } 
/*    */       } else {
/*    */         
/* 59 */         boolean flag1 = true;
/*    */         
/* 61 */         if (this.shootingEntity != null && this.shootingEntity instanceof net.minecraft.entity.EntityLiving)
/*    */         {
/* 63 */           flag1 = this.world.getGameRules().getBoolean("mobGriefing");
/*    */         }
/*    */         
/* 66 */         if (flag1) {
/*    */           
/* 68 */           BlockPos blockpos = result.getBlockPos().offset(result.sideHit);
/*    */           
/* 70 */           if (this.world.isAirBlock(blockpos))
/*    */           {
/* 72 */             this.world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
/*    */           }
/*    */         } 
/*    */       } 
/*    */       
/* 77 */       setDead();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canBeCollidedWith() {
/* 86 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 94 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntitySmallFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */