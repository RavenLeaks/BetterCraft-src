/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.passive.EntityTameable;
/*     */ import net.minecraft.pathfinding.PathNodeType;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityCreature extends EntityLiving {
/*  13 */   public static final UUID FLEEING_SPEED_MODIFIER_UUID = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
/*  14 */   public static final AttributeModifier FLEEING_SPEED_MODIFIER = (new AttributeModifier(FLEEING_SPEED_MODIFIER_UUID, "Fleeing speed bonus", 2.0D, 2)).setSaved(false);
/*  15 */   private BlockPos homePosition = BlockPos.ORIGIN;
/*     */ 
/*     */   
/*  18 */   private float maximumHomeDistance = -1.0F;
/*  19 */   private final float restoreWaterCost = PathNodeType.WATER.getPriority();
/*     */ 
/*     */   
/*     */   public EntityCreature(World worldIn) {
/*  23 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBlockPathWeight(BlockPos pos) {
/*  28 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/*  36 */     return (super.getCanSpawnHere() && getBlockPathWeight(new BlockPos(this.posX, (getEntityBoundingBox()).minY, this.posZ)) >= 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPath() {
/*  44 */     return !this.navigator.noPath();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWithinHomeDistanceCurrentPosition() {
/*  49 */     return isWithinHomeDistanceFromPosition(new BlockPos(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWithinHomeDistanceFromPosition(BlockPos pos) {
/*  54 */     if (this.maximumHomeDistance == -1.0F)
/*     */     {
/*  56 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  60 */     return (this.homePosition.distanceSq((Vec3i)pos) < (this.maximumHomeDistance * this.maximumHomeDistance));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHomePosAndDistance(BlockPos pos, int distance) {
/*  69 */     this.homePosition = pos;
/*  70 */     this.maximumHomeDistance = distance;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getHomePosition() {
/*  75 */     return this.homePosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaximumHomeDistance() {
/*  80 */     return this.maximumHomeDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void detachHome() {
/*  85 */     this.maximumHomeDistance = -1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasHome() {
/*  93 */     return (this.maximumHomeDistance != -1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateLeashedState() {
/* 101 */     super.updateLeashedState();
/*     */     
/* 103 */     if (getLeashed() && getLeashedToEntity() != null && (getLeashedToEntity()).world == this.world) {
/*     */       
/* 105 */       Entity entity = getLeashedToEntity();
/* 106 */       setHomePosAndDistance(new BlockPos((int)entity.posX, (int)entity.posY, (int)entity.posZ), 5);
/* 107 */       float f = getDistanceToEntity(entity);
/*     */       
/* 109 */       if (this instanceof EntityTameable && ((EntityTameable)this).isSitting()) {
/*     */         
/* 111 */         if (f > 10.0F)
/*     */         {
/* 113 */           clearLeashed(true, true);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 119 */       onLeashDistance(f);
/*     */       
/* 121 */       if (f > 10.0F) {
/*     */         
/* 123 */         clearLeashed(true, true);
/* 124 */         this.tasks.disableControlFlag(1);
/*     */       }
/* 126 */       else if (f > 6.0F) {
/*     */         
/* 128 */         double d0 = (entity.posX - this.posX) / f;
/* 129 */         double d1 = (entity.posY - this.posY) / f;
/* 130 */         double d2 = (entity.posZ - this.posZ) / f;
/* 131 */         this.motionX += d0 * Math.abs(d0) * 0.4D;
/* 132 */         this.motionY += d1 * Math.abs(d1) * 0.4D;
/* 133 */         this.motionZ += d2 * Math.abs(d2) * 0.4D;
/*     */       }
/*     */       else {
/*     */         
/* 137 */         this.tasks.enableControlFlag(1);
/* 138 */         float f1 = 2.0F;
/* 139 */         Vec3d vec3d = (new Vec3d(entity.posX - this.posX, entity.posY - this.posY, entity.posZ - this.posZ)).normalize().scale(Math.max(f - 2.0F, 0.0F));
/* 140 */         getNavigator().tryMoveToXYZ(this.posX + vec3d.xCoord, this.posY + vec3d.yCoord, this.posZ + vec3d.zCoord, func_190634_dg());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected double func_190634_dg() {
/* 147 */     return 1.0D;
/*     */   }
/*     */   
/*     */   protected void onLeashDistance(float p_142017_1_) {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\EntityCreature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */