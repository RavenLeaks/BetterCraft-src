/*      */ package net.minecraft.entity.boss;
/*      */ 
/*      */ import java.util.List;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.IEntityMultiPart;
/*      */ import net.minecraft.entity.MoverType;
/*      */ import net.minecraft.entity.MultiPartEntityPart;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.boss.dragon.phase.IPhase;
/*      */ import net.minecraft.entity.boss.dragon.phase.PhaseList;
/*      */ import net.minecraft.entity.boss.dragon.phase.PhaseManager;
/*      */ import net.minecraft.entity.item.EntityEnderCrystal;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.monster.IMob;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.SoundEvents;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.datasync.DataSerializers;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.pathfinding.Path;
/*      */ import net.minecraft.pathfinding.PathHeap;
/*      */ import net.minecraft.pathfinding.PathPoint;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EntityDamageSource;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.SoundCategory;
/*      */ import net.minecraft.util.SoundEvent;
/*      */ import net.minecraft.util.datafix.DataFixer;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProviderEnd;
/*      */ import net.minecraft.world.end.DragonFightManager;
/*      */ import net.minecraft.world.gen.feature.WorldGenEndPodium;
/*      */ import net.minecraft.world.storage.loot.LootTableList;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class EntityDragon
/*      */   extends EntityLiving implements IEntityMultiPart, IMob {
/*   53 */   private static final Logger LOGGER = LogManager.getLogger();
/*   54 */   public static final DataParameter<Integer> PHASE = EntityDataManager.createKey(EntityDragon.class, DataSerializers.VARINT);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   59 */   public double[][] ringBuffer = new double[64][3];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   64 */   public int ringBufferIndex = -1;
/*      */ 
/*      */   
/*      */   public MultiPartEntityPart[] dragonPartArray;
/*      */ 
/*      */   
/*   70 */   public MultiPartEntityPart dragonPartHead = new MultiPartEntityPart(this, "head", 6.0F, 6.0F);
/*   71 */   public MultiPartEntityPart dragonPartNeck = new MultiPartEntityPart(this, "neck", 6.0F, 6.0F);
/*      */ 
/*      */   
/*   74 */   public MultiPartEntityPart dragonPartBody = new MultiPartEntityPart(this, "body", 8.0F, 8.0F);
/*   75 */   public MultiPartEntityPart dragonPartTail1 = new MultiPartEntityPart(this, "tail", 4.0F, 4.0F);
/*   76 */   public MultiPartEntityPart dragonPartTail2 = new MultiPartEntityPart(this, "tail", 4.0F, 4.0F);
/*   77 */   public MultiPartEntityPart dragonPartTail3 = new MultiPartEntityPart(this, "tail", 4.0F, 4.0F);
/*   78 */   public MultiPartEntityPart dragonPartWing1 = new MultiPartEntityPart(this, "wing", 4.0F, 4.0F);
/*   79 */   public MultiPartEntityPart dragonPartWing2 = new MultiPartEntityPart(this, "wing", 4.0F, 4.0F);
/*      */ 
/*      */   
/*      */   public float prevAnimTime;
/*      */ 
/*      */   
/*      */   public float animTime;
/*      */ 
/*      */   
/*      */   public boolean slowed;
/*      */ 
/*      */   
/*      */   public int deathTicks;
/*      */ 
/*      */   
/*      */   public EntityEnderCrystal healingEnderCrystal;
/*      */ 
/*      */   
/*      */   private final DragonFightManager fightManager;
/*      */ 
/*      */   
/*      */   private final PhaseManager phaseManager;
/*      */ 
/*      */   
/*  103 */   private int growlTime = 200;
/*      */ 
/*      */ 
/*      */   
/*      */   private int sittingDamageReceived;
/*      */ 
/*      */ 
/*      */   
/*  111 */   private final PathPoint[] pathPoints = new PathPoint[24];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  118 */   private final int[] neighbors = new int[24];
/*  119 */   private final PathHeap pathFindQueue = new PathHeap();
/*      */ 
/*      */   
/*      */   public EntityDragon(World worldIn) {
/*  123 */     super(worldIn);
/*  124 */     this.dragonPartArray = new MultiPartEntityPart[] { this.dragonPartHead, this.dragonPartNeck, this.dragonPartBody, this.dragonPartTail1, this.dragonPartTail2, this.dragonPartTail3, this.dragonPartWing1, this.dragonPartWing2 };
/*  125 */     setHealth(getMaxHealth());
/*  126 */     setSize(16.0F, 8.0F);
/*  127 */     this.noClip = true;
/*  128 */     this.isImmuneToFire = true;
/*  129 */     this.growlTime = 100;
/*  130 */     this.ignoreFrustumCheck = true;
/*      */     
/*  132 */     if (!worldIn.isRemote && worldIn.provider instanceof WorldProviderEnd) {
/*      */       
/*  134 */       this.fightManager = ((WorldProviderEnd)worldIn.provider).getDragonFightManager();
/*      */     }
/*      */     else {
/*      */       
/*  138 */       this.fightManager = null;
/*      */     } 
/*      */     
/*  141 */     this.phaseManager = new PhaseManager(this);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  146 */     super.applyEntityAttributes();
/*  147 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  152 */     super.entityInit();
/*  153 */     getDataManager().register(PHASE, Integer.valueOf(PhaseList.HOVER.getId()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] getMovementOffsets(int p_70974_1_, float p_70974_2_) {
/*  162 */     if (getHealth() <= 0.0F)
/*      */     {
/*  164 */       p_70974_2_ = 0.0F;
/*      */     }
/*      */     
/*  167 */     p_70974_2_ = 1.0F - p_70974_2_;
/*  168 */     int i = this.ringBufferIndex - p_70974_1_ & 0x3F;
/*  169 */     int j = this.ringBufferIndex - p_70974_1_ - 1 & 0x3F;
/*  170 */     double[] adouble = new double[3];
/*  171 */     double d0 = this.ringBuffer[i][0];
/*  172 */     double d1 = MathHelper.wrapDegrees(this.ringBuffer[j][0] - d0);
/*  173 */     adouble[0] = d0 + d1 * p_70974_2_;
/*  174 */     d0 = this.ringBuffer[i][1];
/*  175 */     d1 = this.ringBuffer[j][1] - d0;
/*  176 */     adouble[1] = d0 + d1 * p_70974_2_;
/*  177 */     adouble[2] = this.ringBuffer[i][2] + (this.ringBuffer[j][2] - this.ringBuffer[i][2]) * p_70974_2_;
/*  178 */     return adouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/*  187 */     if (this.world.isRemote) {
/*      */       
/*  189 */       setHealth(getHealth());
/*      */       
/*  191 */       if (!isSilent()) {
/*      */         
/*  193 */         float f = MathHelper.cos(this.animTime * 6.2831855F);
/*  194 */         float f1 = MathHelper.cos(this.prevAnimTime * 6.2831855F);
/*      */         
/*  196 */         if (f1 <= -0.3F && f >= -0.3F)
/*      */         {
/*  198 */           this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, getSoundCategory(), 5.0F, 0.8F + this.rand.nextFloat() * 0.3F, false);
/*      */         }
/*      */         
/*  201 */         if (!this.phaseManager.getCurrentPhase().getIsStationary() && --this.growlTime < 0) {
/*      */           
/*  203 */           this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ENDERDRAGON_GROWL, getSoundCategory(), 2.5F, 0.8F + this.rand.nextFloat() * 0.3F, false);
/*  204 */           this.growlTime = 200 + this.rand.nextInt(200);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  209 */     this.prevAnimTime = this.animTime;
/*      */     
/*  211 */     if (getHealth() <= 0.0F) {
/*      */       
/*  213 */       float f12 = (this.rand.nextFloat() - 0.5F) * 8.0F;
/*  214 */       float f13 = (this.rand.nextFloat() - 0.5F) * 4.0F;
/*  215 */       float f15 = (this.rand.nextFloat() - 0.5F) * 8.0F;
/*  216 */       this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + f12, this.posY + 2.0D + f13, this.posZ + f15, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */     }
/*      */     else {
/*      */       
/*  220 */       updateDragonEnderCrystal();
/*  221 */       float f11 = 0.2F / (MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F + 1.0F);
/*  222 */       f11 *= (float)Math.pow(2.0D, this.motionY);
/*      */       
/*  224 */       if (this.phaseManager.getCurrentPhase().getIsStationary()) {
/*      */         
/*  226 */         this.animTime += 0.1F;
/*      */       }
/*  228 */       else if (this.slowed) {
/*      */         
/*  230 */         this.animTime += f11 * 0.5F;
/*      */       }
/*      */       else {
/*      */         
/*  234 */         this.animTime += f11;
/*      */       } 
/*      */       
/*  237 */       this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
/*      */       
/*  239 */       if (isAIDisabled()) {
/*      */         
/*  241 */         this.animTime = 0.5F;
/*      */       }
/*      */       else {
/*      */         
/*  245 */         if (this.ringBufferIndex < 0)
/*      */         {
/*  247 */           for (int i = 0; i < this.ringBuffer.length; i++) {
/*      */             
/*  249 */             this.ringBuffer[i][0] = this.rotationYaw;
/*  250 */             this.ringBuffer[i][1] = this.posY;
/*      */           } 
/*      */         }
/*      */         
/*  254 */         if (++this.ringBufferIndex == this.ringBuffer.length)
/*      */         {
/*  256 */           this.ringBufferIndex = 0;
/*      */         }
/*      */         
/*  259 */         this.ringBuffer[this.ringBufferIndex][0] = this.rotationYaw;
/*  260 */         this.ringBuffer[this.ringBufferIndex][1] = this.posY;
/*      */         
/*  262 */         if (this.world.isRemote) {
/*      */           
/*  264 */           if (this.newPosRotationIncrements > 0) {
/*      */             
/*  266 */             double d5 = this.posX + (this.interpTargetX - this.posX) / this.newPosRotationIncrements;
/*  267 */             double d0 = this.posY + (this.interpTargetY - this.posY) / this.newPosRotationIncrements;
/*  268 */             double d1 = this.posZ + (this.interpTargetZ - this.posZ) / this.newPosRotationIncrements;
/*  269 */             double d2 = MathHelper.wrapDegrees(this.interpTargetYaw - this.rotationYaw);
/*  270 */             this.rotationYaw = (float)(this.rotationYaw + d2 / this.newPosRotationIncrements);
/*  271 */             this.rotationPitch = (float)(this.rotationPitch + (this.interpTargetPitch - this.rotationPitch) / this.newPosRotationIncrements);
/*  272 */             this.newPosRotationIncrements--;
/*  273 */             setPosition(d5, d0, d1);
/*  274 */             setRotation(this.rotationYaw, this.rotationPitch);
/*      */           } 
/*      */           
/*  277 */           this.phaseManager.getCurrentPhase().doClientRenderEffects();
/*      */         }
/*      */         else {
/*      */           
/*  281 */           IPhase iphase = this.phaseManager.getCurrentPhase();
/*  282 */           iphase.doLocalUpdate();
/*      */           
/*  284 */           if (this.phaseManager.getCurrentPhase() != iphase) {
/*      */             
/*  286 */             iphase = this.phaseManager.getCurrentPhase();
/*  287 */             iphase.doLocalUpdate();
/*      */           } 
/*      */           
/*  290 */           Vec3d vec3d = iphase.getTargetLocation();
/*      */           
/*  292 */           if (vec3d != null) {
/*      */             
/*  294 */             double d6 = vec3d.xCoord - this.posX;
/*  295 */             double d7 = vec3d.yCoord - this.posY;
/*  296 */             double d8 = vec3d.zCoord - this.posZ;
/*  297 */             double d3 = d6 * d6 + d7 * d7 + d8 * d8;
/*  298 */             float f5 = iphase.getMaxRiseOrFall();
/*  299 */             d7 = MathHelper.clamp(d7 / MathHelper.sqrt(d6 * d6 + d8 * d8), -f5, f5);
/*  300 */             this.motionY += d7 * 0.10000000149011612D;
/*  301 */             this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
/*  302 */             double d4 = MathHelper.clamp(MathHelper.wrapDegrees(180.0D - MathHelper.atan2(d6, d8) * 57.29577951308232D - this.rotationYaw), -50.0D, 50.0D);
/*  303 */             Vec3d vec3d1 = (new Vec3d(vec3d.xCoord - this.posX, vec3d.yCoord - this.posY, vec3d.zCoord - this.posZ)).normalize();
/*  304 */             Vec3d vec3d2 = (new Vec3d(MathHelper.sin(this.rotationYaw * 0.017453292F), this.motionY, -MathHelper.cos(this.rotationYaw * 0.017453292F))).normalize();
/*  305 */             float f7 = Math.max(((float)vec3d2.dotProduct(vec3d1) + 0.5F) / 1.5F, 0.0F);
/*  306 */             this.randomYawVelocity *= 0.8F;
/*  307 */             this.randomYawVelocity = (float)(this.randomYawVelocity + d4 * iphase.getYawFactor());
/*  308 */             this.rotationYaw += this.randomYawVelocity * 0.1F;
/*  309 */             float f8 = (float)(2.0D / (d3 + 1.0D));
/*  310 */             float f9 = 0.06F;
/*  311 */             func_191958_b(0.0F, 0.0F, -1.0F, 0.06F * (f7 * f8 + 1.0F - f8));
/*      */             
/*  313 */             if (this.slowed) {
/*      */               
/*  315 */               moveEntity(MoverType.SELF, this.motionX * 0.800000011920929D, this.motionY * 0.800000011920929D, this.motionZ * 0.800000011920929D);
/*      */             }
/*      */             else {
/*      */               
/*  319 */               moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/*      */             } 
/*      */             
/*  322 */             Vec3d vec3d3 = (new Vec3d(this.motionX, this.motionY, this.motionZ)).normalize();
/*  323 */             float f10 = ((float)vec3d3.dotProduct(vec3d2) + 1.0F) / 2.0F;
/*  324 */             f10 = 0.8F + 0.15F * f10;
/*  325 */             this.motionX *= f10;
/*  326 */             this.motionZ *= f10;
/*  327 */             this.motionY *= 0.9100000262260437D;
/*      */           } 
/*      */         } 
/*      */         
/*  331 */         this.renderYawOffset = this.rotationYaw;
/*  332 */         this.dragonPartHead.width = 1.0F;
/*  333 */         this.dragonPartHead.height = 1.0F;
/*  334 */         this.dragonPartNeck.width = 3.0F;
/*  335 */         this.dragonPartNeck.height = 3.0F;
/*  336 */         this.dragonPartTail1.width = 2.0F;
/*  337 */         this.dragonPartTail1.height = 2.0F;
/*  338 */         this.dragonPartTail2.width = 2.0F;
/*  339 */         this.dragonPartTail2.height = 2.0F;
/*  340 */         this.dragonPartTail3.width = 2.0F;
/*  341 */         this.dragonPartTail3.height = 2.0F;
/*  342 */         this.dragonPartBody.height = 3.0F;
/*  343 */         this.dragonPartBody.width = 5.0F;
/*  344 */         this.dragonPartWing1.height = 2.0F;
/*  345 */         this.dragonPartWing1.width = 4.0F;
/*  346 */         this.dragonPartWing2.height = 3.0F;
/*  347 */         this.dragonPartWing2.width = 4.0F;
/*  348 */         Vec3d[] avec3d = new Vec3d[this.dragonPartArray.length];
/*      */         
/*  350 */         for (int j = 0; j < this.dragonPartArray.length; j++)
/*      */         {
/*  352 */           avec3d[j] = new Vec3d((this.dragonPartArray[j]).posX, (this.dragonPartArray[j]).posY, (this.dragonPartArray[j]).posZ);
/*      */         }
/*      */         
/*  355 */         float f14 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F * 0.017453292F;
/*  356 */         float f16 = MathHelper.cos(f14);
/*  357 */         float f2 = MathHelper.sin(f14);
/*  358 */         float f17 = this.rotationYaw * 0.017453292F;
/*  359 */         float f3 = MathHelper.sin(f17);
/*  360 */         float f18 = MathHelper.cos(f17);
/*  361 */         this.dragonPartBody.onUpdate();
/*  362 */         this.dragonPartBody.setLocationAndAngles(this.posX + (f3 * 0.5F), this.posY, this.posZ - (f18 * 0.5F), 0.0F, 0.0F);
/*  363 */         this.dragonPartWing1.onUpdate();
/*  364 */         this.dragonPartWing1.setLocationAndAngles(this.posX + (f18 * 4.5F), this.posY + 2.0D, this.posZ + (f3 * 4.5F), 0.0F, 0.0F);
/*  365 */         this.dragonPartWing2.onUpdate();
/*  366 */         this.dragonPartWing2.setLocationAndAngles(this.posX - (f18 * 4.5F), this.posY + 2.0D, this.posZ - (f3 * 4.5F), 0.0F, 0.0F);
/*      */         
/*  368 */         if (!this.world.isRemote && this.hurtTime == 0) {
/*      */           
/*  370 */           collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartWing1.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
/*  371 */           collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartWing2.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
/*  372 */           attackEntitiesInList(this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartHead.getEntityBoundingBox().expandXyz(1.0D)));
/*  373 */           attackEntitiesInList(this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartNeck.getEntityBoundingBox().expandXyz(1.0D)));
/*      */         } 
/*      */         
/*  376 */         double[] adouble = getMovementOffsets(5, 1.0F);
/*  377 */         float f19 = MathHelper.sin(this.rotationYaw * 0.017453292F - this.randomYawVelocity * 0.01F);
/*  378 */         float f4 = MathHelper.cos(this.rotationYaw * 0.017453292F - this.randomYawVelocity * 0.01F);
/*  379 */         this.dragonPartHead.onUpdate();
/*  380 */         this.dragonPartNeck.onUpdate();
/*  381 */         float f20 = getHeadYOffset(1.0F);
/*  382 */         this.dragonPartHead.setLocationAndAngles(this.posX + (f19 * 6.5F * f16), this.posY + f20 + (f2 * 6.5F), this.posZ - (f4 * 6.5F * f16), 0.0F, 0.0F);
/*  383 */         this.dragonPartNeck.setLocationAndAngles(this.posX + (f19 * 5.5F * f16), this.posY + f20 + (f2 * 5.5F), this.posZ - (f4 * 5.5F * f16), 0.0F, 0.0F);
/*      */         
/*  385 */         for (int k = 0; k < 3; k++) {
/*      */           
/*  387 */           MultiPartEntityPart multipartentitypart = null;
/*      */           
/*  389 */           if (k == 0)
/*      */           {
/*  391 */             multipartentitypart = this.dragonPartTail1;
/*      */           }
/*      */           
/*  394 */           if (k == 1)
/*      */           {
/*  396 */             multipartentitypart = this.dragonPartTail2;
/*      */           }
/*      */           
/*  399 */           if (k == 2)
/*      */           {
/*  401 */             multipartentitypart = this.dragonPartTail3;
/*      */           }
/*      */           
/*  404 */           double[] adouble1 = getMovementOffsets(12 + k * 2, 1.0F);
/*  405 */           float f21 = this.rotationYaw * 0.017453292F + simplifyAngle(adouble1[0] - adouble[0]) * 0.017453292F;
/*  406 */           float f6 = MathHelper.sin(f21);
/*  407 */           float f22 = MathHelper.cos(f21);
/*  408 */           float f23 = 1.5F;
/*  409 */           float f24 = (k + 1) * 2.0F;
/*  410 */           multipartentitypart.onUpdate();
/*  411 */           multipartentitypart.setLocationAndAngles(this.posX - ((f3 * 1.5F + f6 * f24) * f16), this.posY + adouble1[1] - adouble[1] - ((f24 + 1.5F) * f2) + 1.5D, this.posZ + ((f18 * 1.5F + f22 * f24) * f16), 0.0F, 0.0F);
/*      */         } 
/*      */         
/*  414 */         if (!this.world.isRemote) {
/*      */           
/*  416 */           this.slowed = destroyBlocksInAABB(this.dragonPartHead.getEntityBoundingBox()) | destroyBlocksInAABB(this.dragonPartNeck.getEntityBoundingBox()) | destroyBlocksInAABB(this.dragonPartBody.getEntityBoundingBox());
/*      */           
/*  418 */           if (this.fightManager != null)
/*      */           {
/*  420 */             this.fightManager.dragonUpdate(this);
/*      */           }
/*      */         } 
/*      */         
/*  424 */         for (int l = 0; l < this.dragonPartArray.length; l++) {
/*      */           
/*  426 */           (this.dragonPartArray[l]).prevPosX = (avec3d[l]).xCoord;
/*  427 */           (this.dragonPartArray[l]).prevPosY = (avec3d[l]).yCoord;
/*  428 */           (this.dragonPartArray[l]).prevPosZ = (avec3d[l]).zCoord;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private float getHeadYOffset(float p_184662_1_) {
/*      */     double d0;
/*  438 */     if (this.phaseManager.getCurrentPhase().getIsStationary()) {
/*      */       
/*  440 */       d0 = -1.0D;
/*      */     }
/*      */     else {
/*      */       
/*  444 */       double[] adouble = getMovementOffsets(5, 1.0F);
/*  445 */       double[] adouble1 = getMovementOffsets(0, 1.0F);
/*  446 */       d0 = adouble[1] - adouble1[1];
/*      */     } 
/*      */     
/*  449 */     return (float)d0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateDragonEnderCrystal() {
/*  457 */     if (this.healingEnderCrystal != null)
/*      */     {
/*  459 */       if (this.healingEnderCrystal.isDead) {
/*      */         
/*  461 */         this.healingEnderCrystal = null;
/*      */       }
/*  463 */       else if (this.ticksExisted % 10 == 0 && getHealth() < getMaxHealth()) {
/*      */         
/*  465 */         setHealth(getHealth() + 1.0F);
/*      */       } 
/*      */     }
/*      */     
/*  469 */     if (this.rand.nextInt(10) == 0) {
/*      */       
/*  471 */       List<EntityEnderCrystal> list = this.world.getEntitiesWithinAABB(EntityEnderCrystal.class, getEntityBoundingBox().expandXyz(32.0D));
/*  472 */       EntityEnderCrystal entityendercrystal = null;
/*  473 */       double d0 = Double.MAX_VALUE;
/*      */       
/*  475 */       for (EntityEnderCrystal entityendercrystal1 : list) {
/*      */         
/*  477 */         double d1 = entityendercrystal1.getDistanceSqToEntity((Entity)this);
/*      */         
/*  479 */         if (d1 < d0) {
/*      */           
/*  481 */           d0 = d1;
/*  482 */           entityendercrystal = entityendercrystal1;
/*      */         } 
/*      */       } 
/*      */       
/*  486 */       this.healingEnderCrystal = entityendercrystal;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void collideWithEntities(List<Entity> p_70970_1_) {
/*  495 */     double d0 = ((this.dragonPartBody.getEntityBoundingBox()).minX + (this.dragonPartBody.getEntityBoundingBox()).maxX) / 2.0D;
/*  496 */     double d1 = ((this.dragonPartBody.getEntityBoundingBox()).minZ + (this.dragonPartBody.getEntityBoundingBox()).maxZ) / 2.0D;
/*      */     
/*  498 */     for (Entity entity : p_70970_1_) {
/*      */       
/*  500 */       if (entity instanceof EntityLivingBase) {
/*      */         
/*  502 */         double d2 = entity.posX - d0;
/*  503 */         double d3 = entity.posZ - d1;
/*  504 */         double d4 = d2 * d2 + d3 * d3;
/*  505 */         entity.addVelocity(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
/*      */         
/*  507 */         if (!this.phaseManager.getCurrentPhase().getIsStationary() && ((EntityLivingBase)entity).getRevengeTimer() < entity.ticksExisted - 2) {
/*      */           
/*  509 */           entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 5.0F);
/*  510 */           applyEnchantments((EntityLivingBase)this, entity);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void attackEntitiesInList(List<Entity> p_70971_1_) {
/*  521 */     for (int i = 0; i < p_70971_1_.size(); i++) {
/*      */       
/*  523 */       Entity entity = p_70971_1_.get(i);
/*      */       
/*  525 */       if (entity instanceof EntityLivingBase) {
/*      */         
/*  527 */         entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 10.0F);
/*  528 */         applyEnchantments((EntityLivingBase)this, entity);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float simplifyAngle(double p_70973_1_) {
/*  538 */     return (float)MathHelper.wrapDegrees(p_70973_1_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean destroyBlocksInAABB(AxisAlignedBB p_70972_1_) {
/*  546 */     int i = MathHelper.floor(p_70972_1_.minX);
/*  547 */     int j = MathHelper.floor(p_70972_1_.minY);
/*  548 */     int k = MathHelper.floor(p_70972_1_.minZ);
/*  549 */     int l = MathHelper.floor(p_70972_1_.maxX);
/*  550 */     int i1 = MathHelper.floor(p_70972_1_.maxY);
/*  551 */     int j1 = MathHelper.floor(p_70972_1_.maxZ);
/*  552 */     boolean flag = false;
/*  553 */     boolean flag1 = false;
/*      */     
/*  555 */     for (int k1 = i; k1 <= l; k1++) {
/*      */       
/*  557 */       for (int l1 = j; l1 <= i1; l1++) {
/*      */         
/*  559 */         for (int i2 = k; i2 <= j1; i2++) {
/*      */           
/*  561 */           BlockPos blockpos = new BlockPos(k1, l1, i2);
/*  562 */           IBlockState iblockstate = this.world.getBlockState(blockpos);
/*  563 */           Block block = iblockstate.getBlock();
/*      */           
/*  565 */           if (iblockstate.getMaterial() != Material.AIR && iblockstate.getMaterial() != Material.FIRE)
/*      */           {
/*  567 */             if (!this.world.getGameRules().getBoolean("mobGriefing")) {
/*      */               
/*  569 */               flag = true;
/*      */             }
/*  571 */             else if (block != Blocks.BARRIER && block != Blocks.OBSIDIAN && block != Blocks.END_STONE && block != Blocks.BEDROCK && block != Blocks.END_PORTAL && block != Blocks.END_PORTAL_FRAME) {
/*      */               
/*  573 */               if (block != Blocks.COMMAND_BLOCK && block != Blocks.REPEATING_COMMAND_BLOCK && block != Blocks.CHAIN_COMMAND_BLOCK && block != Blocks.IRON_BARS && block != Blocks.END_GATEWAY)
/*      */               {
/*  575 */                 flag1 = !(!this.world.setBlockToAir(blockpos) && !flag1);
/*      */               }
/*      */               else
/*      */               {
/*  579 */                 flag = true;
/*      */               }
/*      */             
/*      */             } else {
/*      */               
/*  584 */               flag = true;
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  591 */     if (flag1) {
/*      */       
/*  593 */       double d0 = p_70972_1_.minX + (p_70972_1_.maxX - p_70972_1_.minX) * this.rand.nextFloat();
/*  594 */       double d1 = p_70972_1_.minY + (p_70972_1_.maxY - p_70972_1_.minY) * this.rand.nextFloat();
/*  595 */       double d2 = p_70972_1_.minZ + (p_70972_1_.maxZ - p_70972_1_.minZ) * this.rand.nextFloat();
/*  596 */       this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */     } 
/*      */     
/*  599 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean attackEntityFromPart(MultiPartEntityPart dragonPart, DamageSource source, float damage) {
/*  604 */     damage = this.phaseManager.getCurrentPhase().getAdjustedDamage(dragonPart, source, damage);
/*      */     
/*  606 */     if (dragonPart != this.dragonPartHead)
/*      */     {
/*  608 */       damage = damage / 4.0F + Math.min(damage, 1.0F);
/*      */     }
/*      */     
/*  611 */     if (damage < 0.01F)
/*      */     {
/*  613 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  617 */     if (source.getEntity() instanceof EntityPlayer || source.isExplosion()) {
/*      */       
/*  619 */       float f = getHealth();
/*  620 */       attackDragonFrom(source, damage);
/*      */       
/*  622 */       if (getHealth() <= 0.0F && !this.phaseManager.getCurrentPhase().getIsStationary()) {
/*      */         
/*  624 */         setHealth(1.0F);
/*  625 */         this.phaseManager.setPhase(PhaseList.DYING);
/*      */       } 
/*      */       
/*  628 */       if (this.phaseManager.getCurrentPhase().getIsStationary()) {
/*      */         
/*  630 */         this.sittingDamageReceived = (int)(this.sittingDamageReceived + f - getHealth());
/*      */         
/*  632 */         if (this.sittingDamageReceived > 0.25F * getMaxHealth()) {
/*      */           
/*  634 */           this.sittingDamageReceived = 0;
/*  635 */           this.phaseManager.setPhase(PhaseList.TAKEOFF);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  640 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  649 */     if (source instanceof EntityDamageSource && ((EntityDamageSource)source).getIsThornsDamage())
/*      */     {
/*  651 */       attackEntityFromPart(this.dragonPartBody, source, amount);
/*      */     }
/*      */     
/*  654 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean attackDragonFrom(DamageSource source, float amount) {
/*  662 */     return super.attackEntityFrom(source, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillCommand() {
/*  670 */     setDead();
/*      */     
/*  672 */     if (this.fightManager != null) {
/*      */       
/*  674 */       this.fightManager.dragonUpdate(this);
/*  675 */       this.fightManager.processDragonDeath(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onDeathUpdate() {
/*  684 */     if (this.fightManager != null)
/*      */     {
/*  686 */       this.fightManager.dragonUpdate(this);
/*      */     }
/*      */     
/*  689 */     this.deathTicks++;
/*      */     
/*  691 */     if (this.deathTicks >= 180 && this.deathTicks <= 200) {
/*      */       
/*  693 */       float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
/*  694 */       float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
/*  695 */       float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
/*  696 */       this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */     } 
/*      */     
/*  699 */     boolean flag = this.world.getGameRules().getBoolean("doMobLoot");
/*  700 */     int i = 500;
/*      */     
/*  702 */     if (this.fightManager != null && !this.fightManager.hasPreviouslyKilledDragon())
/*      */     {
/*  704 */       i = 12000;
/*      */     }
/*      */     
/*  707 */     if (!this.world.isRemote) {
/*      */       
/*  709 */       if (this.deathTicks > 150 && this.deathTicks % 5 == 0 && flag)
/*      */       {
/*  711 */         dropExperience(MathHelper.floor(i * 0.08F));
/*      */       }
/*      */       
/*  714 */       if (this.deathTicks == 1)
/*      */       {
/*  716 */         this.world.playBroadcastSound(1028, new BlockPos((Entity)this), 0);
/*      */       }
/*      */     } 
/*      */     
/*  720 */     moveEntity(MoverType.SELF, 0.0D, 0.10000000149011612D, 0.0D);
/*  721 */     this.rotationYaw += 20.0F;
/*  722 */     this.renderYawOffset = this.rotationYaw;
/*      */     
/*  724 */     if (this.deathTicks == 200 && !this.world.isRemote) {
/*      */       
/*  726 */       if (flag)
/*      */       {
/*  728 */         dropExperience(MathHelper.floor(i * 0.2F));
/*      */       }
/*      */       
/*  731 */       if (this.fightManager != null)
/*      */       {
/*  733 */         this.fightManager.processDragonDeath(this);
/*      */       }
/*      */       
/*  736 */       setDead();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void dropExperience(int p_184668_1_) {
/*  742 */     while (p_184668_1_ > 0) {
/*      */       
/*  744 */       int i = EntityXPOrb.getXPSplit(p_184668_1_);
/*  745 */       p_184668_1_ -= i;
/*  746 */       this.world.spawnEntityInWorld((Entity)new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, i));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int initPathPoints() {
/*  756 */     if (this.pathPoints[0] == null) {
/*      */       
/*  758 */       for (int i = 0; i < 24; i++) {
/*      */         
/*  760 */         int l, i1, j = 5;
/*      */ 
/*      */ 
/*      */         
/*  764 */         if (i < 12) {
/*      */           
/*  766 */           l = (int)(60.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.2617994F * i)));
/*  767 */           i1 = (int)(60.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.2617994F * i)));
/*      */         }
/*  769 */         else if (i < 20) {
/*      */           
/*  771 */           int lvt_3_1_ = i - 12;
/*  772 */           l = (int)(40.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.3926991F * lvt_3_1_)));
/*  773 */           i1 = (int)(40.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.3926991F * lvt_3_1_)));
/*  774 */           j += 10;
/*      */         }
/*      */         else {
/*      */           
/*  778 */           int k1 = i - 20;
/*  779 */           l = (int)(20.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.7853982F * k1)));
/*  780 */           i1 = (int)(20.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.7853982F * k1)));
/*      */         } 
/*      */         
/*  783 */         int j1 = Math.max(this.world.getSeaLevel() + 10, this.world.getTopSolidOrLiquidBlock(new BlockPos(l, 0, i1)).getY() + j);
/*  784 */         this.pathPoints[i] = new PathPoint(l, j1, i1);
/*      */       } 
/*      */       
/*  787 */       this.neighbors[0] = 6146;
/*  788 */       this.neighbors[1] = 8197;
/*  789 */       this.neighbors[2] = 8202;
/*  790 */       this.neighbors[3] = 16404;
/*  791 */       this.neighbors[4] = 32808;
/*  792 */       this.neighbors[5] = 32848;
/*  793 */       this.neighbors[6] = 65696;
/*  794 */       this.neighbors[7] = 131392;
/*  795 */       this.neighbors[8] = 131712;
/*  796 */       this.neighbors[9] = 263424;
/*  797 */       this.neighbors[10] = 526848;
/*  798 */       this.neighbors[11] = 525313;
/*  799 */       this.neighbors[12] = 1581057;
/*  800 */       this.neighbors[13] = 3166214;
/*  801 */       this.neighbors[14] = 2138120;
/*  802 */       this.neighbors[15] = 6373424;
/*  803 */       this.neighbors[16] = 4358208;
/*  804 */       this.neighbors[17] = 12910976;
/*  805 */       this.neighbors[18] = 9044480;
/*  806 */       this.neighbors[19] = 9706496;
/*  807 */       this.neighbors[20] = 15216640;
/*  808 */       this.neighbors[21] = 13688832;
/*  809 */       this.neighbors[22] = 11763712;
/*  810 */       this.neighbors[23] = 8257536;
/*      */     } 
/*      */     
/*  813 */     return getNearestPpIdx(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNearestPpIdx(double x, double y, double z) {
/*  821 */     float f = 10000.0F;
/*  822 */     int i = 0;
/*  823 */     PathPoint pathpoint = new PathPoint(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
/*  824 */     int j = 0;
/*      */     
/*  826 */     if (this.fightManager == null || this.fightManager.getNumAliveCrystals() == 0)
/*      */     {
/*  828 */       j = 12;
/*      */     }
/*      */     
/*  831 */     for (int k = j; k < 24; k++) {
/*      */       
/*  833 */       if (this.pathPoints[k] != null) {
/*      */         
/*  835 */         float f1 = this.pathPoints[k].distanceToSquared(pathpoint);
/*      */         
/*  837 */         if (f1 < f) {
/*      */           
/*  839 */           f = f1;
/*  840 */           i = k;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  845 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Path findPath(int startIdx, int finishIdx, @Nullable PathPoint andThen) {
/*  859 */     for (int i = 0; i < 24; i++) {
/*      */       
/*  861 */       PathPoint pathpoint = this.pathPoints[i];
/*  862 */       pathpoint.visited = false;
/*  863 */       pathpoint.distanceToTarget = 0.0F;
/*  864 */       pathpoint.totalPathDistance = 0.0F;
/*  865 */       pathpoint.distanceToNext = 0.0F;
/*  866 */       pathpoint.previous = null;
/*  867 */       pathpoint.index = -1;
/*      */     } 
/*      */     
/*  870 */     PathPoint pathpoint4 = this.pathPoints[startIdx];
/*  871 */     PathPoint pathpoint5 = this.pathPoints[finishIdx];
/*  872 */     pathpoint4.totalPathDistance = 0.0F;
/*  873 */     pathpoint4.distanceToNext = pathpoint4.distanceTo(pathpoint5);
/*  874 */     pathpoint4.distanceToTarget = pathpoint4.distanceToNext;
/*  875 */     this.pathFindQueue.clearPath();
/*  876 */     this.pathFindQueue.addPoint(pathpoint4);
/*  877 */     PathPoint pathpoint1 = pathpoint4;
/*  878 */     int j = 0;
/*      */     
/*  880 */     if (this.fightManager == null || this.fightManager.getNumAliveCrystals() == 0)
/*      */     {
/*  882 */       j = 12;
/*      */     }
/*      */     
/*  885 */     while (!this.pathFindQueue.isPathEmpty()) {
/*      */       
/*  887 */       PathPoint pathpoint2 = this.pathFindQueue.dequeue();
/*      */       
/*  889 */       if (pathpoint2.equals(pathpoint5)) {
/*      */         
/*  891 */         if (andThen != null) {
/*      */           
/*  893 */           andThen.previous = pathpoint5;
/*  894 */           pathpoint5 = andThen;
/*      */         } 
/*      */         
/*  897 */         return makePath(pathpoint4, pathpoint5);
/*      */       } 
/*      */       
/*  900 */       if (pathpoint2.distanceTo(pathpoint5) < pathpoint1.distanceTo(pathpoint5))
/*      */       {
/*  902 */         pathpoint1 = pathpoint2;
/*      */       }
/*      */       
/*  905 */       pathpoint2.visited = true;
/*  906 */       int k = 0;
/*      */       
/*  908 */       for (int l = 0; l < 24; l++) {
/*      */         
/*  910 */         if (this.pathPoints[l] == pathpoint2) {
/*      */           
/*  912 */           k = l;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  917 */       for (int i1 = j; i1 < 24; i1++) {
/*      */         
/*  919 */         if ((this.neighbors[k] & 1 << i1) > 0) {
/*      */           
/*  921 */           PathPoint pathpoint3 = this.pathPoints[i1];
/*      */           
/*  923 */           if (!pathpoint3.visited) {
/*      */             
/*  925 */             float f = pathpoint2.totalPathDistance + pathpoint2.distanceTo(pathpoint3);
/*      */             
/*  927 */             if (!pathpoint3.isAssigned() || f < pathpoint3.totalPathDistance) {
/*      */               
/*  929 */               pathpoint3.previous = pathpoint2;
/*  930 */               pathpoint3.totalPathDistance = f;
/*  931 */               pathpoint3.distanceToNext = pathpoint3.distanceTo(pathpoint5);
/*      */               
/*  933 */               if (pathpoint3.isAssigned()) {
/*      */                 
/*  935 */                 this.pathFindQueue.changeDistance(pathpoint3, pathpoint3.totalPathDistance + pathpoint3.distanceToNext);
/*      */               }
/*      */               else {
/*      */                 
/*  939 */                 pathpoint3.distanceToTarget = pathpoint3.totalPathDistance + pathpoint3.distanceToNext;
/*  940 */                 this.pathFindQueue.addPoint(pathpoint3);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  948 */     if (pathpoint1 == pathpoint4)
/*      */     {
/*  950 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  954 */     LOGGER.debug("Failed to find path from {} to {}", Integer.valueOf(startIdx), Integer.valueOf(finishIdx));
/*      */     
/*  956 */     if (andThen != null) {
/*      */       
/*  958 */       andThen.previous = pathpoint1;
/*  959 */       pathpoint1 = andThen;
/*      */     } 
/*      */     
/*  962 */     return makePath(pathpoint4, pathpoint1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Path makePath(PathPoint start, PathPoint finish) {
/*  972 */     int i = 1;
/*      */     
/*  974 */     for (PathPoint pathpoint = finish; pathpoint.previous != null; pathpoint = pathpoint.previous)
/*      */     {
/*  976 */       i++;
/*      */     }
/*      */     
/*  979 */     PathPoint[] apathpoint = new PathPoint[i];
/*  980 */     PathPoint pathpoint1 = finish;
/*  981 */     i--;
/*      */     
/*  983 */     for (apathpoint[i] = finish; pathpoint1.previous != null; apathpoint[i] = pathpoint1) {
/*      */       
/*  985 */       pathpoint1 = pathpoint1.previous;
/*  986 */       i--;
/*      */     } 
/*      */     
/*  989 */     return new Path(apathpoint);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void registerFixesDragon(DataFixer fixer) {
/*  994 */     EntityLiving.registerFixesMob(fixer, EntityDragon.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 1002 */     super.writeEntityToNBT(compound);
/* 1003 */     compound.setInteger("DragonPhase", this.phaseManager.getCurrentPhase().getPhaseList().getId());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 1011 */     super.readEntityFromNBT(compound);
/*      */     
/* 1013 */     if (compound.hasKey("DragonPhase"))
/*      */     {
/* 1015 */       this.phaseManager.setPhase(PhaseList.getById(compound.getInteger("DragonPhase")));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void despawnEntity() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity[] getParts() {
/* 1031 */     return (Entity[])this.dragonPartArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedWith() {
/* 1039 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public World getWorld() {
/* 1044 */     return this.world;
/*      */   }
/*      */ 
/*      */   
/*      */   public SoundCategory getSoundCategory() {
/* 1049 */     return SoundCategory.HOSTILE;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getAmbientSound() {
/* 1054 */     return SoundEvents.ENTITY_ENDERDRAGON_AMBIENT;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 1059 */     return SoundEvents.ENTITY_ENDERDRAGON_HURT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getSoundVolume() {
/* 1067 */     return 5.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected ResourceLocation getLootTable() {
/* 1073 */     return LootTableList.field_191189_ay;
/*      */   }
/*      */   
/*      */   public float getHeadPartYOffset(int p_184667_1_, double[] p_184667_2_, double[] p_184667_3_) {
/*      */     double d0;
/* 1078 */     IPhase iphase = this.phaseManager.getCurrentPhase();
/* 1079 */     PhaseList<? extends IPhase> phaselist = iphase.getPhaseList();
/*      */ 
/*      */     
/* 1082 */     if (phaselist != PhaseList.LANDING && phaselist != PhaseList.TAKEOFF) {
/*      */       
/* 1084 */       if (iphase.getIsStationary())
/*      */       {
/* 1086 */         d0 = p_184667_1_;
/*      */       }
/* 1088 */       else if (p_184667_1_ == 6)
/*      */       {
/* 1090 */         d0 = 0.0D;
/*      */       }
/*      */       else
/*      */       {
/* 1094 */         d0 = p_184667_3_[1] - p_184667_2_[1];
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1099 */       BlockPos blockpos = this.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION);
/* 1100 */       float f = Math.max(MathHelper.sqrt(getDistanceSqToCenter(blockpos)) / 4.0F, 1.0F);
/* 1101 */       d0 = (p_184667_1_ / f);
/*      */     } 
/*      */     
/* 1104 */     return (float)d0;
/*      */   }
/*      */   
/*      */   public Vec3d getHeadLookVec(float p_184665_1_) {
/*      */     Vec3d vec3d;
/* 1109 */     IPhase iphase = this.phaseManager.getCurrentPhase();
/* 1110 */     PhaseList<? extends IPhase> phaselist = iphase.getPhaseList();
/*      */ 
/*      */     
/* 1113 */     if (phaselist != PhaseList.LANDING && phaselist != PhaseList.TAKEOFF) {
/*      */       
/* 1115 */       if (iphase.getIsStationary())
/*      */       {
/* 1117 */         float f4 = this.rotationPitch;
/* 1118 */         float f5 = 1.5F;
/* 1119 */         this.rotationPitch = -45.0F;
/* 1120 */         vec3d = getLook(p_184665_1_);
/* 1121 */         this.rotationPitch = f4;
/*      */       }
/*      */       else
/*      */       {
/* 1125 */         vec3d = getLook(p_184665_1_);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1130 */       BlockPos blockpos = this.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION);
/* 1131 */       float f = Math.max(MathHelper.sqrt(getDistanceSqToCenter(blockpos)) / 4.0F, 1.0F);
/* 1132 */       float f1 = 6.0F / f;
/* 1133 */       float f2 = this.rotationPitch;
/* 1134 */       float f3 = 1.5F;
/* 1135 */       this.rotationPitch = -f1 * 1.5F * 5.0F;
/* 1136 */       vec3d = getLook(p_184665_1_);
/* 1137 */       this.rotationPitch = f2;
/*      */     } 
/*      */     
/* 1140 */     return vec3d;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCrystalDestroyed(EntityEnderCrystal crystal, BlockPos pos, DamageSource dmgSrc) {
/*      */     EntityPlayer entityplayer;
/* 1147 */     if (dmgSrc.getEntity() instanceof EntityPlayer) {
/*      */       
/* 1149 */       entityplayer = (EntityPlayer)dmgSrc.getEntity();
/*      */     }
/*      */     else {
/*      */       
/* 1153 */       entityplayer = this.world.getNearestAttackablePlayer(pos, 64.0D, 64.0D);
/*      */     } 
/*      */     
/* 1156 */     if (crystal == this.healingEnderCrystal)
/*      */     {
/* 1158 */       attackEntityFromPart(this.dragonPartHead, DamageSource.causeExplosionDamage((EntityLivingBase)entityplayer), 10.0F);
/*      */     }
/*      */     
/* 1161 */     this.phaseManager.getCurrentPhase().onCrystalDestroyed(crystal, pos, dmgSrc, entityplayer);
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 1166 */     if (PHASE.equals(key) && this.world.isRemote)
/*      */     {
/* 1168 */       this.phaseManager.setPhase(PhaseList.getById(((Integer)getDataManager().get(PHASE)).intValue()));
/*      */     }
/*      */     
/* 1171 */     super.notifyDataManagerChange(key);
/*      */   }
/*      */ 
/*      */   
/*      */   public PhaseManager getPhaseManager() {
/* 1176 */     return this.phaseManager;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public DragonFightManager getFightManager() {
/* 1182 */     return this.fightManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPotionEffect(PotionEffect potioneffectIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canBeRidden(Entity entityIn) {
/* 1194 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNonBoss() {
/* 1202 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\EntityDragon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */