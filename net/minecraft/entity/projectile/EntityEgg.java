/*    */ package net.minecraft.entity.projectile;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityChicken;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityEgg
/*    */   extends EntityThrowable {
/*    */   public EntityEgg(World worldIn) {
/* 17 */     super(worldIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityEgg(World worldIn, EntityLivingBase throwerIn) {
/* 22 */     super(worldIn, throwerIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityEgg(World worldIn, double x, double y, double z) {
/* 27 */     super(worldIn, x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerFixesEgg(DataFixer fixer) {
/* 32 */     EntityThrowable.registerFixesThrowable(fixer, "ThrownEgg");
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleStatusUpdate(byte id) {
/* 37 */     if (id == 3) {
/*    */       
/* 39 */       double d0 = 0.08D;
/*    */       
/* 41 */       for (int i = 0; i < 8; i++) {
/*    */         
/* 43 */         this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, (this.rand.nextFloat() - 0.5D) * 0.08D, (this.rand.nextFloat() - 0.5D) * 0.08D, (this.rand.nextFloat() - 0.5D) * 0.08D, new int[] { Item.getIdFromItem(Items.EGG) });
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onImpact(RayTraceResult result) {
/* 53 */     if (result.entityHit != null)
/*    */     {
/* 55 */       result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, (Entity)getThrower()), 0.0F);
/*    */     }
/*    */     
/* 58 */     if (!this.world.isRemote) {
/*    */       
/* 60 */       if (this.rand.nextInt(8) == 0) {
/*    */         
/* 62 */         int i = 1;
/*    */         
/* 64 */         if (this.rand.nextInt(32) == 0)
/*    */         {
/* 66 */           i = 4;
/*    */         }
/*    */         
/* 69 */         for (int j = 0; j < i; j++) {
/*    */           
/* 71 */           EntityChicken entitychicken = new EntityChicken(this.world);
/* 72 */           entitychicken.setGrowingAge(-24000);
/* 73 */           entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
/* 74 */           this.world.spawnEntityInWorld((Entity)entitychicken);
/*    */         } 
/*    */       } 
/*    */       
/* 78 */       this.world.setEntityState(this, (byte)3);
/* 79 */       setDead();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntityEgg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */