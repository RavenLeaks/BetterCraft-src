/*    */ package net.minecraft.entity.projectile;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntitySnowball
/*    */   extends EntityThrowable
/*    */ {
/*    */   public EntitySnowball(World worldIn) {
/* 15 */     super(worldIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySnowball(World worldIn, EntityLivingBase throwerIn) {
/* 20 */     super(worldIn, throwerIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySnowball(World worldIn, double x, double y, double z) {
/* 25 */     super(worldIn, x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerFixesSnowball(DataFixer fixer) {
/* 30 */     EntityThrowable.registerFixesThrowable(fixer, "Snowball");
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleStatusUpdate(byte id) {
/* 35 */     if (id == 3)
/*    */     {
/* 37 */       for (int i = 0; i < 8; i++)
/*    */       {
/* 39 */         this.world.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onImpact(RayTraceResult result) {
/* 49 */     if (result.entityHit != null) {
/*    */       
/* 51 */       int i = 0;
/*    */       
/* 53 */       if (result.entityHit instanceof net.minecraft.entity.monster.EntityBlaze)
/*    */       {
/* 55 */         i = 3;
/*    */       }
/*    */       
/* 58 */       result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, (Entity)getThrower()), i);
/*    */     } 
/*    */     
/* 61 */     if (!this.world.isRemote) {
/*    */       
/* 63 */       this.world.setEntityState(this, (byte)3);
/* 64 */       setDead();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntitySnowball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */