/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.BlockRedstoneDiode;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ public abstract class EntityHanging extends Entity {
/*  24 */   private static final Predicate<Entity> IS_HANGING_ENTITY = new Predicate<Entity>()
/*     */     {
/*     */       public boolean apply(@Nullable Entity p_apply_1_)
/*     */       {
/*  28 */         return p_apply_1_ instanceof EntityHanging;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private int tickCounter1;
/*     */   
/*     */   protected BlockPos hangingPosition;
/*     */   @Nullable
/*     */   public EnumFacing facingDirection;
/*     */   
/*     */   public EntityHanging(World worldIn) {
/*  40 */     super(worldIn);
/*  41 */     setSize(0.5F, 0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityHanging(World worldIn, BlockPos hangingPositionIn) {
/*  46 */     this(worldIn);
/*  47 */     this.hangingPosition = hangingPositionIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateFacingWithBoundingBox(EnumFacing facingDirectionIn) {
/*  59 */     Validate.notNull(facingDirectionIn);
/*  60 */     Validate.isTrue(facingDirectionIn.getAxis().isHorizontal());
/*  61 */     this.facingDirection = facingDirectionIn;
/*  62 */     this.rotationYaw = (this.facingDirection.getHorizontalIndex() * 90);
/*  63 */     this.prevRotationYaw = this.rotationYaw;
/*  64 */     updateBoundingBox();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateBoundingBox() {
/*  72 */     if (this.facingDirection != null) {
/*     */       
/*  74 */       double d0 = this.hangingPosition.getX() + 0.5D;
/*  75 */       double d1 = this.hangingPosition.getY() + 0.5D;
/*  76 */       double d2 = this.hangingPosition.getZ() + 0.5D;
/*  77 */       double d3 = 0.46875D;
/*  78 */       double d4 = offs(getWidthPixels());
/*  79 */       double d5 = offs(getHeightPixels());
/*  80 */       d0 -= this.facingDirection.getFrontOffsetX() * 0.46875D;
/*  81 */       d2 -= this.facingDirection.getFrontOffsetZ() * 0.46875D;
/*  82 */       d1 += d5;
/*  83 */       EnumFacing enumfacing = this.facingDirection.rotateYCCW();
/*  84 */       d0 += d4 * enumfacing.getFrontOffsetX();
/*  85 */       d2 += d4 * enumfacing.getFrontOffsetZ();
/*  86 */       this.posX = d0;
/*  87 */       this.posY = d1;
/*  88 */       this.posZ = d2;
/*  89 */       double d6 = getWidthPixels();
/*  90 */       double d7 = getHeightPixels();
/*  91 */       double d8 = getWidthPixels();
/*     */       
/*  93 */       if (this.facingDirection.getAxis() == EnumFacing.Axis.Z) {
/*     */         
/*  95 */         d8 = 1.0D;
/*     */       }
/*     */       else {
/*     */         
/*  99 */         d6 = 1.0D;
/*     */       } 
/*     */       
/* 102 */       d6 /= 32.0D;
/* 103 */       d7 /= 32.0D;
/* 104 */       d8 /= 32.0D;
/* 105 */       setEntityBoundingBox(new AxisAlignedBB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private double offs(int p_190202_1_) {
/* 111 */     return (p_190202_1_ % 32 == 0) ? 0.5D : 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 119 */     this.prevPosX = this.posX;
/* 120 */     this.prevPosY = this.posY;
/* 121 */     this.prevPosZ = this.posZ;
/*     */     
/* 123 */     if (this.tickCounter1++ == 100 && !this.world.isRemote) {
/*     */       
/* 125 */       this.tickCounter1 = 0;
/*     */       
/* 127 */       if (!this.isDead && !onValidSurface()) {
/*     */         
/* 129 */         setDead();
/* 130 */         onBroken((Entity)null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onValidSurface() {
/* 140 */     if (!this.world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty())
/*     */     {
/* 142 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 146 */     int i = Math.max(1, getWidthPixels() / 16);
/* 147 */     int j = Math.max(1, getHeightPixels() / 16);
/* 148 */     BlockPos blockpos = this.hangingPosition.offset(this.facingDirection.getOpposite());
/* 149 */     EnumFacing enumfacing = this.facingDirection.rotateYCCW();
/* 150 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 152 */     for (int k = 0; k < i; k++) {
/*     */       
/* 154 */       for (int l = 0; l < j; l++) {
/*     */         
/* 156 */         int i1 = (i - 1) / -2;
/* 157 */         int j1 = (j - 1) / -2;
/* 158 */         blockpos$mutableblockpos.setPos((Vec3i)blockpos).move(enumfacing, k + i1).move(EnumFacing.UP, l + j1);
/* 159 */         IBlockState iblockstate = this.world.getBlockState((BlockPos)blockpos$mutableblockpos);
/*     */         
/* 161 */         if (!iblockstate.getMaterial().isSolid() && !BlockRedstoneDiode.isDiode(iblockstate))
/*     */         {
/* 163 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 168 */     return this.world.getEntitiesInAABBexcluding(this, getEntityBoundingBox(), IS_HANGING_ENTITY).isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 177 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hitByEntity(Entity entityIn) {
/* 185 */     return (entityIn instanceof EntityPlayer) ? attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entityIn), 0.0F) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumFacing getHorizontalFacing() {
/* 193 */     return this.facingDirection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 201 */     if (isEntityInvulnerable(source))
/*     */     {
/* 203 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 207 */     if (!this.isDead && !this.world.isRemote) {
/*     */       
/* 209 */       setDead();
/* 210 */       setBeenAttacked();
/* 211 */       onBroken(source.getEntity());
/*     */     } 
/*     */     
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntity(MoverType x, double p_70091_2_, double p_70091_4_, double p_70091_6_) {
/* 223 */     if (!this.world.isRemote && !this.isDead && p_70091_2_ * p_70091_2_ + p_70091_4_ * p_70091_4_ + p_70091_6_ * p_70091_6_ > 0.0D) {
/*     */       
/* 225 */       setDead();
/* 226 */       onBroken((Entity)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addVelocity(double x, double y, double z) {
/* 235 */     if (!this.world.isRemote && !this.isDead && x * x + y * y + z * z > 0.0D) {
/*     */       
/* 237 */       setDead();
/* 238 */       onBroken((Entity)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 247 */     compound.setByte("Facing", (byte)this.facingDirection.getHorizontalIndex());
/* 248 */     BlockPos blockpos = getHangingPosition();
/* 249 */     compound.setInteger("TileX", blockpos.getX());
/* 250 */     compound.setInteger("TileY", blockpos.getY());
/* 251 */     compound.setInteger("TileZ", blockpos.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 259 */     this.hangingPosition = new BlockPos(compound.getInteger("TileX"), compound.getInteger("TileY"), compound.getInteger("TileZ"));
/* 260 */     updateFacingWithBoundingBox(EnumFacing.getHorizontal(compound.getByte("Facing")));
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityItem entityDropItem(ItemStack stack, float offsetY) {
/* 279 */     EntityItem entityitem = new EntityItem(this.world, this.posX + (this.facingDirection.getFrontOffsetX() * 0.15F), this.posY + offsetY, this.posZ + (this.facingDirection.getFrontOffsetZ() * 0.15F), stack);
/* 280 */     entityitem.setDefaultPickupDelay();
/* 281 */     this.world.spawnEntityInWorld((Entity)entityitem);
/* 282 */     return entityitem;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldSetPosAfterLoading() {
/* 287 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(double x, double y, double z) {
/* 295 */     this.hangingPosition = new BlockPos(x, y, z);
/* 296 */     updateBoundingBox();
/* 297 */     this.isAirBorne = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getHangingPosition() {
/* 302 */     return this.hangingPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRotatedYaw(Rotation transformRotation) {
/* 312 */     if (this.facingDirection != null && this.facingDirection.getAxis() != EnumFacing.Axis.Y)
/*     */     {
/* 314 */       switch (transformRotation) {
/*     */         
/*     */         case null:
/* 317 */           this.facingDirection = this.facingDirection.getOpposite();
/*     */           break;
/*     */         
/*     */         case COUNTERCLOCKWISE_90:
/* 321 */           this.facingDirection = this.facingDirection.rotateYCCW();
/*     */           break;
/*     */         
/*     */         case CLOCKWISE_90:
/* 325 */           this.facingDirection = this.facingDirection.rotateY();
/*     */           break;
/*     */       } 
/*     */     }
/* 329 */     float f = MathHelper.wrapDegrees(this.rotationYaw);
/*     */     
/* 331 */     switch (transformRotation) {
/*     */       
/*     */       case null:
/* 334 */         return f + 180.0F;
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 337 */         return f + 90.0F;
/*     */       
/*     */       case CLOCKWISE_90:
/* 340 */         return f + 270.0F;
/*     */     } 
/*     */     
/* 343 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMirroredYaw(Mirror transformMirror) {
/* 352 */     return getRotatedYaw(transformMirror.toRotation(this.facingDirection));
/*     */   }
/*     */   
/*     */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {}
/*     */   
/*     */   public abstract int getWidthPixels();
/*     */   
/*     */   public abstract int getHeightPixels();
/*     */   
/*     */   public abstract void onBroken(Entity paramEntity);
/*     */   
/*     */   public abstract void playPlaceSound();
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\EntityHanging.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */