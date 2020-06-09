/*    */ package net.minecraft.entity.item;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.projectile.EntityThrowable;
/*    */ import net.minecraft.init.PotionTypes;
/*    */ import net.minecraft.potion.PotionUtils;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityExpBottle
/*    */   extends EntityThrowable {
/*    */   public EntityExpBottle(World worldIn) {
/* 16 */     super(worldIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityExpBottle(World worldIn, EntityLivingBase throwerIn) {
/* 21 */     super(worldIn, throwerIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityExpBottle(World worldIn, double x, double y, double z) {
/* 26 */     super(worldIn, x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerFixesExpBottle(DataFixer fixer) {
/* 31 */     EntityThrowable.registerFixesThrowable(fixer, "ThrowableExpBottle");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected float getGravityVelocity() {
/* 39 */     return 0.07F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onImpact(RayTraceResult result) {
/* 47 */     if (!this.world.isRemote) {
/*    */       
/* 49 */       this.world.playEvent(2002, new BlockPos((Entity)this), PotionUtils.getPotionColor(PotionTypes.WATER));
/* 50 */       int i = 3 + this.world.rand.nextInt(5) + this.world.rand.nextInt(5);
/*    */       
/* 52 */       while (i > 0) {
/*    */         
/* 54 */         int j = EntityXPOrb.getXPSplit(i);
/* 55 */         i -= j;
/* 56 */         this.world.spawnEntityInWorld(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
/*    */       } 
/*    */       
/* 59 */       setDead();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityExpBottle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */