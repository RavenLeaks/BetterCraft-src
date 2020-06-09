/*    */ package net.minecraft.entity.projectile;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityLargeFireball
/*    */   extends EntityFireball {
/* 13 */   public int explosionPower = 1;
/*    */ 
/*    */   
/*    */   public EntityLargeFireball(World worldIn) {
/* 17 */     super(worldIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityLargeFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
/* 22 */     super(worldIn, x, y, z, accelX, accelY, accelZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityLargeFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
/* 27 */     super(worldIn, shooter, accelX, accelY, accelZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onImpact(RayTraceResult result) {
/* 35 */     if (!this.world.isRemote) {
/*    */       
/* 37 */       if (result.entityHit != null) {
/*    */         
/* 39 */         result.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, (Entity)this.shootingEntity), 6.0F);
/* 40 */         applyEnchantments(this.shootingEntity, result.entityHit);
/*    */       } 
/*    */       
/* 43 */       boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
/* 44 */       this.world.newExplosion(null, this.posX, this.posY, this.posZ, this.explosionPower, flag, flag);
/* 45 */       setDead();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerFixesLargeFireball(DataFixer fixer) {
/* 51 */     EntityFireball.registerFixesFireball(fixer, "Fireball");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 59 */     super.writeEntityToNBT(compound);
/* 60 */     compound.setInteger("ExplosionPower", this.explosionPower);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 68 */     super.readEntityFromNBT(compound);
/*    */     
/* 70 */     if (compound.hasKey("ExplosionPower", 99))
/*    */     {
/* 72 */       this.explosionPower = compound.getInteger("ExplosionPower");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntityLargeFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */