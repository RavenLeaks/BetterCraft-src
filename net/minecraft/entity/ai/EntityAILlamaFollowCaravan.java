/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.EntityLlama;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class EntityAILlamaFollowCaravan
/*     */   extends EntityAIBase {
/*     */   public EntityLlama field_190859_a;
/*     */   private double field_190860_b;
/*     */   private int field_190861_c;
/*     */   
/*     */   public EntityAILlamaFollowCaravan(EntityLlama p_i47305_1_, double p_i47305_2_) {
/*  15 */     this.field_190859_a = p_i47305_1_;
/*  16 */     this.field_190860_b = p_i47305_2_;
/*  17 */     setMutexBits(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  25 */     if (!this.field_190859_a.getLeashed() && !this.field_190859_a.func_190718_dR()) {
/*     */       
/*  27 */       List<EntityLlama> list = this.field_190859_a.world.getEntitiesWithinAABB(this.field_190859_a.getClass(), this.field_190859_a.getEntityBoundingBox().expand(9.0D, 4.0D, 9.0D));
/*  28 */       EntityLlama entityllama = null;
/*  29 */       double d0 = Double.MAX_VALUE;
/*     */       
/*  31 */       for (EntityLlama entityllama1 : list) {
/*     */         
/*  33 */         if (entityllama1.func_190718_dR() && !entityllama1.func_190712_dQ()) {
/*     */           
/*  35 */           double d1 = this.field_190859_a.getDistanceSqToEntity((Entity)entityllama1);
/*     */           
/*  37 */           if (d1 <= d0) {
/*     */             
/*  39 */             d0 = d1;
/*  40 */             entityllama = entityllama1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  45 */       if (entityllama == null)
/*     */       {
/*  47 */         for (EntityLlama entityllama2 : list) {
/*     */           
/*  49 */           if (entityllama2.getLeashed() && !entityllama2.func_190712_dQ()) {
/*     */             
/*  51 */             double d2 = this.field_190859_a.getDistanceSqToEntity((Entity)entityllama2);
/*     */             
/*  53 */             if (d2 <= d0) {
/*     */               
/*  55 */               d0 = d2;
/*  56 */               entityllama = entityllama2;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/*  62 */       if (entityllama == null)
/*     */       {
/*  64 */         return false;
/*     */       }
/*  66 */       if (d0 < 4.0D)
/*     */       {
/*  68 */         return false;
/*     */       }
/*  70 */       if (!entityllama.getLeashed() && !func_190858_a(entityllama, 1))
/*     */       {
/*  72 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  76 */       this.field_190859_a.func_190715_a(entityllama);
/*  77 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  91 */     if (this.field_190859_a.func_190718_dR() && this.field_190859_a.func_190716_dS().isEntityAlive() && func_190858_a(this.field_190859_a, 0)) {
/*     */       
/*  93 */       double d0 = this.field_190859_a.getDistanceSqToEntity((Entity)this.field_190859_a.func_190716_dS());
/*     */       
/*  95 */       if (d0 > 676.0D) {
/*     */         
/*  97 */         if (this.field_190860_b <= 3.0D) {
/*     */           
/*  99 */           this.field_190860_b *= 1.2D;
/* 100 */           this.field_190861_c = 40;
/* 101 */           return true;
/*     */         } 
/*     */         
/* 104 */         if (this.field_190861_c == 0)
/*     */         {
/* 106 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 110 */       if (this.field_190861_c > 0)
/*     */       {
/* 112 */         this.field_190861_c--;
/*     */       }
/*     */       
/* 115 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 128 */     this.field_190859_a.func_190709_dP();
/* 129 */     this.field_190860_b = 2.1D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/* 137 */     if (this.field_190859_a.func_190718_dR()) {
/*     */       
/* 139 */       EntityLlama entityllama = this.field_190859_a.func_190716_dS();
/* 140 */       double d0 = this.field_190859_a.getDistanceToEntity((Entity)entityllama);
/* 141 */       float f = 2.0F;
/* 142 */       Vec3d vec3d = (new Vec3d(entityllama.posX - this.field_190859_a.posX, entityllama.posY - this.field_190859_a.posY, entityllama.posZ - this.field_190859_a.posZ)).normalize().scale(Math.max(d0 - 2.0D, 0.0D));
/* 143 */       this.field_190859_a.getNavigator().tryMoveToXYZ(this.field_190859_a.posX + vec3d.xCoord, this.field_190859_a.posY + vec3d.yCoord, this.field_190859_a.posZ + vec3d.zCoord, this.field_190860_b);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_190858_a(EntityLlama p_190858_1_, int p_190858_2_) {
/* 149 */     if (p_190858_2_ > 8)
/*     */     {
/* 151 */       return false;
/*     */     }
/* 153 */     if (p_190858_1_.func_190718_dR()) {
/*     */       
/* 155 */       if (p_190858_1_.func_190716_dS().getLeashed())
/*     */       {
/* 157 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 161 */       EntityLlama entityllama = p_190858_1_.func_190716_dS();
/* 162 */       p_190858_2_++;
/* 163 */       return func_190858_a(entityllama, p_190858_2_);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 168 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAILlamaFollowCaravan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */