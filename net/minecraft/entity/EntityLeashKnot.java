/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class EntityLeashKnot
/*     */   extends EntityHanging
/*     */ {
/*     */   public EntityLeashKnot(World worldIn) {
/*  20 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLeashKnot(World worldIn, BlockPos hangingPositionIn) {
/*  25 */     super(worldIn, hangingPositionIn);
/*  26 */     setPosition(hangingPositionIn.getX() + 0.5D, hangingPositionIn.getY() + 0.5D, hangingPositionIn.getZ() + 0.5D);
/*  27 */     float f = 0.125F;
/*  28 */     float f1 = 0.1875F;
/*  29 */     float f2 = 0.25F;
/*  30 */     setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.1875D, this.posY - 0.25D + 0.125D, this.posZ - 0.1875D, this.posX + 0.1875D, this.posY + 0.25D + 0.125D, this.posZ + 0.1875D));
/*  31 */     this.forceSpawn = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(double x, double y, double z) {
/*  39 */     super.setPosition(MathHelper.floor(x) + 0.5D, MathHelper.floor(y) + 0.5D, MathHelper.floor(z) + 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateBoundingBox() {
/*  47 */     this.posX = this.hangingPosition.getX() + 0.5D;
/*  48 */     this.posY = this.hangingPosition.getY() + 0.5D;
/*  49 */     this.posZ = this.hangingPosition.getZ() + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateFacingWithBoundingBox(EnumFacing facingDirectionIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidthPixels() {
/*  61 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeightPixels() {
/*  66 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/*  71 */     return -0.0625F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/*  79 */     return (distance < 1024.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBroken(@Nullable Entity brokenEntity) {
/*  87 */     playSound(SoundEvents.ENTITY_LEASHKNOT_BREAK, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean writeToNBTOptional(NBTTagCompound compound) {
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processInitialInteract(EntityPlayer player, EnumHand stack) {
/* 116 */     if (this.world.isRemote)
/*     */     {
/* 118 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 122 */     boolean flag = false;
/* 123 */     double d0 = 7.0D;
/* 124 */     List<EntityLiving> list = this.world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - 7.0D, this.posY - 7.0D, this.posZ - 7.0D, this.posX + 7.0D, this.posY + 7.0D, this.posZ + 7.0D));
/*     */     
/* 126 */     for (EntityLiving entityliving : list) {
/*     */       
/* 128 */       if (entityliving.getLeashed() && entityliving.getLeashedToEntity() == player) {
/*     */         
/* 130 */         entityliving.setLeashedToEntity(this, true);
/* 131 */         flag = true;
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     if (!flag) {
/*     */       
/* 137 */       setDead();
/*     */       
/* 139 */       if (player.capabilities.isCreativeMode)
/*     */       {
/* 141 */         for (EntityLiving entityliving1 : list) {
/*     */           
/* 143 */           if (entityliving1.getLeashed() && entityliving1.getLeashedToEntity() == this)
/*     */           {
/* 145 */             entityliving1.clearLeashed(true, false);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onValidSurface() {
/* 160 */     return this.world.getBlockState(this.hangingPosition).getBlock() instanceof net.minecraft.block.BlockFence;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EntityLeashKnot createKnot(World worldIn, BlockPos fence) {
/* 165 */     EntityLeashKnot entityleashknot = new EntityLeashKnot(worldIn, fence);
/* 166 */     worldIn.spawnEntityInWorld(entityleashknot);
/* 167 */     entityleashknot.playPlaceSound();
/* 168 */     return entityleashknot;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static EntityLeashKnot getKnotForPosition(World worldIn, BlockPos pos) {
/* 174 */     int i = pos.getX();
/* 175 */     int j = pos.getY();
/* 176 */     int k = pos.getZ();
/*     */     
/* 178 */     for (EntityLeashKnot entityleashknot : worldIn.getEntitiesWithinAABB(EntityLeashKnot.class, new AxisAlignedBB(i - 1.0D, j - 1.0D, k - 1.0D, i + 1.0D, j + 1.0D, k + 1.0D))) {
/*     */       
/* 180 */       if (entityleashknot.getHangingPosition().equals(pos))
/*     */       {
/* 182 */         return entityleashknot;
/*     */       }
/*     */     } 
/*     */     
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playPlaceSound() {
/* 191 */     playSound(SoundEvents.ENTITY_LEASHKNOT_PLACE, 1.0F, 1.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\EntityLeashKnot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */