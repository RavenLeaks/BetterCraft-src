/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.pathfinding.NodeProcessor;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.pathfinding.PathNodeType;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityMoveHelper
/*     */ {
/*     */   protected final EntityLiving entity;
/*     */   protected double posX;
/*     */   protected double posY;
/*     */   protected double posZ;
/*     */   protected double speed;
/*     */   protected float moveForward;
/*     */   protected float moveStrafe;
/*  22 */   public Action action = Action.WAIT;
/*     */ 
/*     */   
/*     */   public EntityMoveHelper(EntityLiving entitylivingIn) {
/*  26 */     this.entity = entitylivingIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUpdating() {
/*  31 */     return (this.action == Action.MOVE_TO);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSpeed() {
/*  36 */     return this.speed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMoveTo(double x, double y, double z, double speedIn) {
/*  44 */     this.posX = x;
/*  45 */     this.posY = y;
/*  46 */     this.posZ = z;
/*  47 */     this.speed = speedIn;
/*  48 */     this.action = Action.MOVE_TO;
/*     */   }
/*     */ 
/*     */   
/*     */   public void strafe(float forward, float strafe) {
/*  53 */     this.action = Action.STRAFE;
/*  54 */     this.moveForward = forward;
/*  55 */     this.moveStrafe = strafe;
/*  56 */     this.speed = 0.25D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(EntityMoveHelper that) {
/*  61 */     this.action = that.action;
/*  62 */     this.posX = that.posX;
/*  63 */     this.posY = that.posY;
/*  64 */     this.posZ = that.posZ;
/*  65 */     this.speed = Math.max(that.speed, 1.0D);
/*  66 */     this.moveForward = that.moveForward;
/*  67 */     this.moveStrafe = that.moveStrafe;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateMoveHelper() {
/*  72 */     if (this.action == Action.STRAFE) {
/*     */       
/*  74 */       float f = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
/*  75 */       float f1 = (float)this.speed * f;
/*  76 */       float f2 = this.moveForward;
/*  77 */       float f3 = this.moveStrafe;
/*  78 */       float f4 = MathHelper.sqrt(f2 * f2 + f3 * f3);
/*     */       
/*  80 */       if (f4 < 1.0F)
/*     */       {
/*  82 */         f4 = 1.0F;
/*     */       }
/*     */       
/*  85 */       f4 = f1 / f4;
/*  86 */       f2 *= f4;
/*  87 */       f3 *= f4;
/*  88 */       float f5 = MathHelper.sin(this.entity.rotationYaw * 0.017453292F);
/*  89 */       float f6 = MathHelper.cos(this.entity.rotationYaw * 0.017453292F);
/*  90 */       float f7 = f2 * f6 - f3 * f5;
/*  91 */       float f8 = f3 * f6 + f2 * f5;
/*  92 */       PathNavigate pathnavigate = this.entity.getNavigator();
/*     */       
/*  94 */       if (pathnavigate != null) {
/*     */         
/*  96 */         NodeProcessor nodeprocessor = pathnavigate.getNodeProcessor();
/*     */         
/*  98 */         if (nodeprocessor != null && nodeprocessor.getPathNodeType((IBlockAccess)this.entity.world, MathHelper.floor(this.entity.posX + f7), MathHelper.floor(this.entity.posY), MathHelper.floor(this.entity.posZ + f8)) != PathNodeType.WALKABLE) {
/*     */           
/* 100 */           this.moveForward = 1.0F;
/* 101 */           this.moveStrafe = 0.0F;
/* 102 */           f1 = f;
/*     */         } 
/*     */       } 
/*     */       
/* 106 */       this.entity.setAIMoveSpeed(f1);
/* 107 */       this.entity.func_191989_p(this.moveForward);
/* 108 */       this.entity.setMoveStrafing(this.moveStrafe);
/* 109 */       this.action = Action.WAIT;
/*     */     }
/* 111 */     else if (this.action == Action.MOVE_TO) {
/*     */       
/* 113 */       this.action = Action.WAIT;
/* 114 */       double d0 = this.posX - this.entity.posX;
/* 115 */       double d1 = this.posZ - this.entity.posZ;
/* 116 */       double d2 = this.posY - this.entity.posY;
/* 117 */       double d3 = d0 * d0 + d2 * d2 + d1 * d1;
/*     */       
/* 119 */       if (d3 < 2.500000277905201E-7D) {
/*     */         
/* 121 */         this.entity.func_191989_p(0.0F);
/*     */         
/*     */         return;
/*     */       } 
/* 125 */       float f9 = (float)(MathHelper.atan2(d1, d0) * 57.29577951308232D) - 90.0F;
/* 126 */       this.entity.rotationYaw = limitAngle(this.entity.rotationYaw, f9, 90.0F);
/* 127 */       this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
/*     */       
/* 129 */       if (d2 > this.entity.stepHeight && d0 * d0 + d1 * d1 < Math.max(1.0F, this.entity.width))
/*     */       {
/* 131 */         this.entity.getJumpHelper().setJumping();
/* 132 */         this.action = Action.JUMPING;
/*     */       }
/*     */     
/* 135 */     } else if (this.action == Action.JUMPING) {
/*     */       
/* 137 */       this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
/*     */       
/* 139 */       if (this.entity.onGround)
/*     */       {
/* 141 */         this.action = Action.WAIT;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 146 */       this.entity.func_191989_p(0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float limitAngle(float p_75639_1_, float p_75639_2_, float p_75639_3_) {
/* 155 */     float f = MathHelper.wrapDegrees(p_75639_2_ - p_75639_1_);
/*     */     
/* 157 */     if (f > p_75639_3_)
/*     */     {
/* 159 */       f = p_75639_3_;
/*     */     }
/*     */     
/* 162 */     if (f < -p_75639_3_)
/*     */     {
/* 164 */       f = -p_75639_3_;
/*     */     }
/*     */     
/* 167 */     float f1 = p_75639_1_ + f;
/*     */     
/* 169 */     if (f1 < 0.0F) {
/*     */       
/* 171 */       f1 += 360.0F;
/*     */     }
/* 173 */     else if (f1 > 360.0F) {
/*     */       
/* 175 */       f1 -= 360.0F;
/*     */     } 
/*     */     
/* 178 */     return f1;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/* 183 */     return this.posX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/* 188 */     return this.posY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 193 */     return this.posZ;
/*     */   }
/*     */   
/*     */   public enum Action
/*     */   {
/* 198 */     WAIT,
/* 199 */     MOVE_TO,
/* 200 */     STRAFE,
/* 201 */     JUMPING;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityMoveHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */