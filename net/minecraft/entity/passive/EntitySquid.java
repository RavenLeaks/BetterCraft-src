/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntitySquid
/*     */   extends EntityWaterMob
/*     */ {
/*     */   public float squidPitch;
/*     */   public float prevSquidPitch;
/*     */   public float squidYaw;
/*     */   public float prevSquidYaw;
/*     */   public float squidRotation;
/*     */   public float prevSquidRotation;
/*     */   public float tentacleAngle;
/*     */   public float lastTentacleAngle;
/*     */   private float randomMotionSpeed;
/*     */   private float rotationVelocity;
/*     */   private float rotateSpeed;
/*     */   private float randomMotionVecX;
/*     */   private float randomMotionVecY;
/*     */   private float randomMotionVecZ;
/*     */   
/*     */   public EntitySquid(World worldIn) {
/*  49 */     super(worldIn);
/*  50 */     setSize(0.8F, 0.8F);
/*  51 */     this.rand.setSeed((1 + getEntityId()));
/*  52 */     this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesSquid(DataFixer fixer) {
/*  57 */     EntityLiving.registerFixesMob(fixer, EntitySquid.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  62 */     this.tasks.addTask(0, new AIMoveRandom(this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  67 */     super.applyEntityAttributes();
/*  68 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/*  73 */     return this.height * 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  78 */     return SoundEvents.ENTITY_SQUID_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  83 */     return SoundEvents.ENTITY_SQUID_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  88 */     return SoundEvents.ENTITY_SQUID_DEATH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/*  96 */     return 0.4F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 111 */     return LootTableList.ENTITIES_SQUID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 120 */     super.onLivingUpdate();
/* 121 */     this.prevSquidPitch = this.squidPitch;
/* 122 */     this.prevSquidYaw = this.squidYaw;
/* 123 */     this.prevSquidRotation = this.squidRotation;
/* 124 */     this.lastTentacleAngle = this.tentacleAngle;
/* 125 */     this.squidRotation += this.rotationVelocity;
/*     */     
/* 127 */     if (this.squidRotation > 6.283185307179586D)
/*     */     {
/* 129 */       if (this.world.isRemote) {
/*     */         
/* 131 */         this.squidRotation = 6.2831855F;
/*     */       }
/*     */       else {
/*     */         
/* 135 */         this.squidRotation = (float)(this.squidRotation - 6.283185307179586D);
/*     */         
/* 137 */         if (this.rand.nextInt(10) == 0)
/*     */         {
/* 139 */           this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
/*     */         }
/*     */         
/* 142 */         this.world.setEntityState((Entity)this, (byte)19);
/*     */       } 
/*     */     }
/*     */     
/* 146 */     if (this.inWater) {
/*     */       
/* 148 */       if (this.squidRotation < 3.1415927F) {
/*     */         
/* 150 */         float f = this.squidRotation / 3.1415927F;
/* 151 */         this.tentacleAngle = MathHelper.sin(f * f * 3.1415927F) * 3.1415927F * 0.25F;
/*     */         
/* 153 */         if (f > 0.75D)
/*     */         {
/* 155 */           this.randomMotionSpeed = 1.0F;
/* 156 */           this.rotateSpeed = 1.0F;
/*     */         }
/*     */         else
/*     */         {
/* 160 */           this.rotateSpeed *= 0.8F;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 165 */         this.tentacleAngle = 0.0F;
/* 166 */         this.randomMotionSpeed *= 0.9F;
/* 167 */         this.rotateSpeed *= 0.99F;
/*     */       } 
/*     */       
/* 170 */       if (!this.world.isRemote) {
/*     */         
/* 172 */         this.motionX = (this.randomMotionVecX * this.randomMotionSpeed);
/* 173 */         this.motionY = (this.randomMotionVecY * this.randomMotionSpeed);
/* 174 */         this.motionZ = (this.randomMotionVecZ * this.randomMotionSpeed);
/*     */       } 
/*     */       
/* 177 */       float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 178 */       this.renderYawOffset += (-((float)MathHelper.atan2(this.motionX, this.motionZ)) * 57.295776F - this.renderYawOffset) * 0.1F;
/* 179 */       this.rotationYaw = this.renderYawOffset;
/* 180 */       this.squidYaw = (float)(this.squidYaw + Math.PI * this.rotateSpeed * 1.5D);
/* 181 */       this.squidPitch += (-((float)MathHelper.atan2(f1, this.motionY)) * 57.295776F - this.squidPitch) * 0.1F;
/*     */     }
/*     */     else {
/*     */       
/* 185 */       this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * 3.1415927F * 0.25F;
/*     */       
/* 187 */       if (!this.world.isRemote) {
/*     */         
/* 189 */         this.motionX = 0.0D;
/* 190 */         this.motionZ = 0.0D;
/*     */         
/* 192 */         if (isPotionActive(MobEffects.LEVITATION)) {
/*     */           
/* 194 */           this.motionY += 0.05D * (getActivePotionEffect(MobEffects.LEVITATION).getAmplifier() + 1) - this.motionY;
/*     */         }
/* 196 */         else if (!hasNoGravity()) {
/*     */           
/* 198 */           this.motionY -= 0.08D;
/*     */         } 
/*     */         
/* 201 */         this.motionY *= 0.9800000190734863D;
/*     */       } 
/*     */       
/* 204 */       this.squidPitch = (float)(this.squidPitch + (-90.0F - this.squidPitch) * 0.02D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
/* 210 */     moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 218 */     return (this.posY > 45.0D && this.posY < this.world.getSeaLevel() && super.getCanSpawnHere());
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 223 */     if (id == 19) {
/*     */       
/* 225 */       this.squidRotation = 0.0F;
/*     */     }
/*     */     else {
/*     */       
/* 229 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMovementVector(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn) {
/* 235 */     this.randomMotionVecX = randomMotionVecXIn;
/* 236 */     this.randomMotionVecY = randomMotionVecYIn;
/* 237 */     this.randomMotionVecZ = randomMotionVecZIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMovementVector() {
/* 242 */     return !(this.randomMotionVecX == 0.0F && this.randomMotionVecY == 0.0F && this.randomMotionVecZ == 0.0F);
/*     */   }
/*     */   
/*     */   static class AIMoveRandom
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntitySquid squid;
/*     */     
/*     */     public AIMoveRandom(EntitySquid p_i45859_1_) {
/* 251 */       this.squid = p_i45859_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 256 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 261 */       int i = this.squid.getAge();
/*     */       
/* 263 */       if (i > 100) {
/*     */         
/* 265 */         this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
/*     */       }
/* 267 */       else if (this.squid.getRNG().nextInt(50) == 0 || !this.squid.inWater || !this.squid.hasMovementVector()) {
/*     */         
/* 269 */         float f = this.squid.getRNG().nextFloat() * 6.2831855F;
/* 270 */         float f1 = MathHelper.cos(f) * 0.2F;
/* 271 */         float f2 = -0.1F + this.squid.getRNG().nextFloat() * 0.2F;
/* 272 */         float f3 = MathHelper.sin(f) * 0.2F;
/* 273 */         this.squid.setMovementVector(f1, f2, f3);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntitySquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */