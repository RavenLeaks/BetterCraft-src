/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import com.google.common.base.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProviderEnd;
/*     */ import net.minecraft.world.end.DragonFightManager;
/*     */ 
/*     */ public class EntityEnderCrystal
/*     */   extends Entity {
/*  21 */   private static final DataParameter<Optional<BlockPos>> BEAM_TARGET = EntityDataManager.createKey(EntityEnderCrystal.class, DataSerializers.OPTIONAL_BLOCK_POS);
/*  22 */   private static final DataParameter<Boolean> SHOW_BOTTOM = EntityDataManager.createKey(EntityEnderCrystal.class, DataSerializers.BOOLEAN);
/*     */ 
/*     */   
/*     */   public int innerRotation;
/*     */ 
/*     */   
/*     */   public EntityEnderCrystal(World worldIn) {
/*  29 */     super(worldIn);
/*  30 */     this.preventEntitySpawning = true;
/*  31 */     setSize(2.0F, 2.0F);
/*  32 */     this.innerRotation = this.rand.nextInt(100000);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityEnderCrystal(World worldIn, double x, double y, double z) {
/*  37 */     this(worldIn);
/*  38 */     setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  47 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  52 */     getDataManager().register(BEAM_TARGET, Optional.absent());
/*  53 */     getDataManager().register(SHOW_BOTTOM, Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  61 */     this.prevPosX = this.posX;
/*  62 */     this.prevPosY = this.posY;
/*  63 */     this.prevPosZ = this.posZ;
/*  64 */     this.innerRotation++;
/*     */     
/*  66 */     if (!this.world.isRemote) {
/*     */       
/*  68 */       BlockPos blockpos = new BlockPos(this);
/*     */       
/*  70 */       if (this.world.provider instanceof WorldProviderEnd && this.world.getBlockState(blockpos).getBlock() != Blocks.FIRE)
/*     */       {
/*  72 */         this.world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound compound) {
/*  82 */     if (getBeamTarget() != null)
/*     */     {
/*  84 */       compound.setTag("BeamTarget", (NBTBase)NBTUtil.createPosTag(getBeamTarget()));
/*     */     }
/*     */     
/*  87 */     compound.setBoolean("ShowBottom", shouldShowBottom());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound compound) {
/*  95 */     if (compound.hasKey("BeamTarget", 10))
/*     */     {
/*  97 */       setBeamTarget(NBTUtil.getPosFromTag(compound.getCompoundTag("BeamTarget")));
/*     */     }
/*     */     
/* 100 */     if (compound.hasKey("ShowBottom", 1))
/*     */     {
/* 102 */       setShowBottom(compound.getBoolean("ShowBottom"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 111 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 119 */     if (isEntityInvulnerable(source))
/*     */     {
/* 121 */       return false;
/*     */     }
/* 123 */     if (source.getEntity() instanceof net.minecraft.entity.boss.EntityDragon)
/*     */     {
/* 125 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 129 */     if (!this.isDead && !this.world.isRemote) {
/*     */       
/* 131 */       setDead();
/*     */       
/* 133 */       if (!this.world.isRemote) {
/*     */         
/* 135 */         if (!source.isExplosion())
/*     */         {
/* 137 */           this.world.createExplosion(null, this.posX, this.posY, this.posZ, 6.0F, true);
/*     */         }
/*     */         
/* 140 */         onCrystalDestroyed(source);
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onKillCommand() {
/* 153 */     onCrystalDestroyed(DamageSource.generic);
/* 154 */     super.onKillCommand();
/*     */   }
/*     */ 
/*     */   
/*     */   private void onCrystalDestroyed(DamageSource source) {
/* 159 */     if (this.world.provider instanceof WorldProviderEnd) {
/*     */       
/* 161 */       WorldProviderEnd worldproviderend = (WorldProviderEnd)this.world.provider;
/* 162 */       DragonFightManager dragonfightmanager = worldproviderend.getDragonFightManager();
/*     */       
/* 164 */       if (dragonfightmanager != null)
/*     */       {
/* 166 */         dragonfightmanager.onCrystalDestroyed(this, source);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBeamTarget(@Nullable BlockPos beamTarget) {
/* 173 */     getDataManager().set(BEAM_TARGET, Optional.fromNullable(beamTarget));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockPos getBeamTarget() {
/* 179 */     return (BlockPos)((Optional)getDataManager().get(BEAM_TARGET)).orNull();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setShowBottom(boolean showBottom) {
/* 184 */     getDataManager().set(SHOW_BOTTOM, Boolean.valueOf(showBottom));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldShowBottom() {
/* 189 */     return ((Boolean)getDataManager().get(SHOW_BOTTOM)).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/* 197 */     return !(!super.isInRangeToRenderDist(distance) && getBeamTarget() == null);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityEnderCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */