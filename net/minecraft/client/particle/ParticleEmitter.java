/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleEmitter
/*    */   extends Particle
/*    */ {
/*    */   private final Entity attachedEntity;
/*    */   private int age;
/*    */   private final int lifetime;
/*    */   private final EnumParticleTypes particleTypes;
/*    */   
/*    */   public ParticleEmitter(World worldIn, Entity p_i46279_2_, EnumParticleTypes particleTypesIn) {
/* 17 */     this(worldIn, p_i46279_2_, particleTypesIn, 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleEmitter(World p_i47219_1_, Entity p_i47219_2_, EnumParticleTypes p_i47219_3_, int p_i47219_4_) {
/* 22 */     super(p_i47219_1_, p_i47219_2_.posX, (p_i47219_2_.getEntityBoundingBox()).minY + (p_i47219_2_.height / 2.0F), p_i47219_2_.posZ, p_i47219_2_.motionX, p_i47219_2_.motionY, p_i47219_2_.motionZ);
/* 23 */     this.attachedEntity = p_i47219_2_;
/* 24 */     this.lifetime = p_i47219_4_;
/* 25 */     this.particleTypes = p_i47219_3_;
/* 26 */     onUpdate();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 38 */     for (int i = 0; i < 16; i++) {
/*    */       
/* 40 */       double d0 = (this.rand.nextFloat() * 2.0F - 1.0F);
/* 41 */       double d1 = (this.rand.nextFloat() * 2.0F - 1.0F);
/* 42 */       double d2 = (this.rand.nextFloat() * 2.0F - 1.0F);
/*    */       
/* 44 */       if (d0 * d0 + d1 * d1 + d2 * d2 <= 1.0D) {
/*    */         
/* 46 */         double d3 = this.attachedEntity.posX + d0 * this.attachedEntity.width / 4.0D;
/* 47 */         double d4 = (this.attachedEntity.getEntityBoundingBox()).minY + (this.attachedEntity.height / 2.0F) + d1 * this.attachedEntity.height / 4.0D;
/* 48 */         double d5 = this.attachedEntity.posZ + d2 * this.attachedEntity.width / 4.0D;
/* 49 */         this.worldObj.spawnParticle(this.particleTypes, false, d3, d4, d5, d0, d1 + 0.2D, d2, new int[0]);
/*    */       } 
/*    */     } 
/*    */     
/* 53 */     this.age++;
/*    */     
/* 55 */     if (this.age >= this.lifetime)
/*    */     {
/* 57 */       setExpired();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFXLayer() {
/* 67 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleEmitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */