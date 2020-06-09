/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import com.google.common.base.Optional;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityOwnable;
/*     */ import net.minecraft.entity.ai.EntityAISit;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.server.management.PreYggdrasilConverter;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityTameable
/*     */   extends EntityAnimal implements IEntityOwnable {
/*  25 */   public static final DataParameter<Byte> TAMED = EntityDataManager.createKey(EntityTameable.class, DataSerializers.BYTE);
/*  26 */   public static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(EntityTameable.class, DataSerializers.OPTIONAL_UNIQUE_ID);
/*     */   
/*     */   protected EntityAISit aiSit;
/*     */   
/*     */   public EntityTameable(World worldIn) {
/*  31 */     super(worldIn);
/*  32 */     setupTamedAI();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  37 */     super.entityInit();
/*  38 */     this.dataManager.register(TAMED, Byte.valueOf((byte)0));
/*  39 */     this.dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  47 */     super.writeEntityToNBT(compound);
/*     */     
/*  49 */     if (getOwnerId() == null) {
/*     */       
/*  51 */       compound.setString("OwnerUUID", "");
/*     */     }
/*     */     else {
/*     */       
/*  55 */       compound.setString("OwnerUUID", getOwnerId().toString());
/*     */     } 
/*     */     
/*  58 */     compound.setBoolean("Sitting", isSitting());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/*     */     String s;
/*  66 */     super.readEntityFromNBT(compound);
/*     */ 
/*     */     
/*  69 */     if (compound.hasKey("OwnerUUID", 8)) {
/*     */       
/*  71 */       s = compound.getString("OwnerUUID");
/*     */     }
/*     */     else {
/*     */       
/*  75 */       String s1 = compound.getString("Owner");
/*  76 */       s = PreYggdrasilConverter.convertMobOwnerIfNeeded(getServer(), s1);
/*     */     } 
/*     */     
/*  79 */     if (!s.isEmpty()) {
/*     */       
/*     */       try {
/*     */         
/*  83 */         setOwnerId(UUID.fromString(s));
/*  84 */         setTamed(true);
/*     */       }
/*  86 */       catch (Throwable var4) {
/*     */         
/*  88 */         setTamed(false);
/*     */       } 
/*     */     }
/*     */     
/*  92 */     if (this.aiSit != null)
/*     */     {
/*  94 */       this.aiSit.setSitting(compound.getBoolean("Sitting"));
/*     */     }
/*     */     
/*  97 */     setSitting(compound.getBoolean("Sitting"));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeLeashedTo(EntityPlayer player) {
/* 102 */     return !getLeashed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void playTameEffect(boolean play) {
/* 110 */     EnumParticleTypes enumparticletypes = EnumParticleTypes.HEART;
/*     */     
/* 112 */     if (!play)
/*     */     {
/* 114 */       enumparticletypes = EnumParticleTypes.SMOKE_NORMAL;
/*     */     }
/*     */     
/* 117 */     for (int i = 0; i < 7; i++) {
/*     */       
/* 119 */       double d0 = this.rand.nextGaussian() * 0.02D;
/* 120 */       double d1 = this.rand.nextGaussian() * 0.02D;
/* 121 */       double d2 = this.rand.nextGaussian() * 0.02D;
/* 122 */       this.world.spawnParticle(enumparticletypes, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 128 */     if (id == 7) {
/*     */       
/* 130 */       playTameEffect(true);
/*     */     }
/* 132 */     else if (id == 6) {
/*     */       
/* 134 */       playTameEffect(false);
/*     */     }
/*     */     else {
/*     */       
/* 138 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTamed() {
/* 144 */     return ((((Byte)this.dataManager.get(TAMED)).byteValue() & 0x4) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTamed(boolean tamed) {
/* 149 */     byte b0 = ((Byte)this.dataManager.get(TAMED)).byteValue();
/*     */     
/* 151 */     if (tamed) {
/*     */       
/* 153 */       this.dataManager.set(TAMED, Byte.valueOf((byte)(b0 | 0x4)));
/*     */     }
/*     */     else {
/*     */       
/* 157 */       this.dataManager.set(TAMED, Byte.valueOf((byte)(b0 & 0xFFFFFFFB)));
/*     */     } 
/*     */     
/* 160 */     setupTamedAI();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setupTamedAI() {}
/*     */ 
/*     */   
/*     */   public boolean isSitting() {
/* 169 */     return ((((Byte)this.dataManager.get(TAMED)).byteValue() & 0x1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSitting(boolean sitting) {
/* 174 */     byte b0 = ((Byte)this.dataManager.get(TAMED)).byteValue();
/*     */     
/* 176 */     if (sitting) {
/*     */       
/* 178 */       this.dataManager.set(TAMED, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     }
/*     */     else {
/*     */       
/* 182 */       this.dataManager.set(TAMED, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UUID getOwnerId() {
/* 189 */     return (UUID)((Optional)this.dataManager.get(OWNER_UNIQUE_ID)).orNull();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOwnerId(@Nullable UUID p_184754_1_) {
/* 194 */     this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(p_184754_1_));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193101_c(EntityPlayer p_193101_1_) {
/* 199 */     setTamed(true);
/* 200 */     setOwnerId(p_193101_1_.getUniqueID());
/*     */     
/* 202 */     if (p_193101_1_ instanceof EntityPlayerMP)
/*     */     {
/* 204 */       CriteriaTriggers.field_193136_w.func_193178_a((EntityPlayerMP)p_193101_1_, this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EntityLivingBase getOwner() {
/*     */     try {
/* 213 */       UUID uuid = getOwnerId();
/* 214 */       return (uuid == null) ? null : (EntityLivingBase)this.world.getPlayerEntityByUUID(uuid);
/*     */     }
/* 216 */     catch (IllegalArgumentException var2) {
/*     */       
/* 218 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwner(EntityLivingBase entityIn) {
/* 224 */     return (entityIn == getOwner());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityAISit getAISit() {
/* 232 */     return this.aiSit;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldAttackEntity(EntityLivingBase p_142018_1_, EntityLivingBase p_142018_2_) {
/* 237 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Team getTeam() {
/* 242 */     if (isTamed()) {
/*     */       
/* 244 */       EntityLivingBase entitylivingbase = getOwner();
/*     */       
/* 246 */       if (entitylivingbase != null)
/*     */       {
/* 248 */         return entitylivingbase.getTeam();
/*     */       }
/*     */     } 
/*     */     
/* 252 */     return super.getTeam();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnSameTeam(Entity entityIn) {
/* 260 */     if (isTamed()) {
/*     */       
/* 262 */       EntityLivingBase entitylivingbase = getOwner();
/*     */       
/* 264 */       if (entityIn == entitylivingbase)
/*     */       {
/* 266 */         return true;
/*     */       }
/*     */       
/* 269 */       if (entitylivingbase != null)
/*     */       {
/* 271 */         return entitylivingbase.isOnSameTeam(entityIn);
/*     */       }
/*     */     } 
/*     */     
/* 275 */     return super.isOnSameTeam(entityIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 283 */     if (!this.world.isRemote && this.world.getGameRules().getBoolean("showDeathMessages") && getOwner() instanceof EntityPlayerMP)
/*     */     {
/* 285 */       getOwner().addChatMessage(getCombatTracker().getDeathMessage());
/*     */     }
/*     */     
/* 288 */     super.onDeath(cause);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityTameable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */