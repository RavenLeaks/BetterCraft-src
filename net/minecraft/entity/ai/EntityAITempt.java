/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAITempt
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final EntityCreature temptedEntity;
/*     */   private final double speed;
/*     */   private double targetX;
/*     */   private double targetY;
/*     */   private double targetZ;
/*     */   private double pitch;
/*     */   private double yaw;
/*     */   private EntityPlayer temptingPlayer;
/*     */   private int delayTemptCounter;
/*     */   private boolean isRunning;
/*     */   private final Set<Item> temptItem;
/*     */   private final boolean scaredByPlayerMovement;
/*     */   
/*     */   public EntityAITempt(EntityCreature temptedEntityIn, double speedIn, Item temptItemIn, boolean scaredByPlayerMovementIn) {
/*  52 */     this(temptedEntityIn, speedIn, scaredByPlayerMovementIn, Sets.newHashSet((Object[])new Item[] { temptItemIn }));
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityAITempt(EntityCreature temptedEntityIn, double speedIn, boolean scaredByPlayerMovementIn, Set<Item> temptItemIn) {
/*  57 */     this.temptedEntity = temptedEntityIn;
/*  58 */     this.speed = speedIn;
/*  59 */     this.temptItem = temptItemIn;
/*  60 */     this.scaredByPlayerMovement = scaredByPlayerMovementIn;
/*  61 */     setMutexBits(3);
/*     */     
/*  63 */     if (!(temptedEntityIn.getNavigator() instanceof net.minecraft.pathfinding.PathNavigateGround))
/*     */     {
/*  65 */       throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  74 */     if (this.delayTemptCounter > 0) {
/*     */       
/*  76 */       this.delayTemptCounter--;
/*  77 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  81 */     this.temptingPlayer = this.temptedEntity.world.getClosestPlayerToEntity((Entity)this.temptedEntity, 10.0D);
/*     */     
/*  83 */     if (this.temptingPlayer == null)
/*     */     {
/*  85 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  89 */     return !(!isTempting(this.temptingPlayer.getHeldItemMainhand()) && !isTempting(this.temptingPlayer.getHeldItemOffhand()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isTempting(ItemStack stack) {
/*  96 */     return this.temptItem.contains(stack.getItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/* 104 */     if (this.scaredByPlayerMovement) {
/*     */       
/* 106 */       if (this.temptedEntity.getDistanceSqToEntity((Entity)this.temptingPlayer) < 36.0D) {
/*     */         
/* 108 */         if (this.temptingPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002D)
/*     */         {
/* 110 */           return false;
/*     */         }
/*     */         
/* 113 */         if (Math.abs(this.temptingPlayer.rotationPitch - this.pitch) > 5.0D || Math.abs(this.temptingPlayer.rotationYaw - this.yaw) > 5.0D)
/*     */         {
/* 115 */           return false;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 120 */         this.targetX = this.temptingPlayer.posX;
/* 121 */         this.targetY = this.temptingPlayer.posY;
/* 122 */         this.targetZ = this.temptingPlayer.posZ;
/*     */       } 
/*     */       
/* 125 */       this.pitch = this.temptingPlayer.rotationPitch;
/* 126 */       this.yaw = this.temptingPlayer.rotationYaw;
/*     */     } 
/*     */     
/* 129 */     return shouldExecute();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 137 */     this.targetX = this.temptingPlayer.posX;
/* 138 */     this.targetY = this.temptingPlayer.posY;
/* 139 */     this.targetZ = this.temptingPlayer.posZ;
/* 140 */     this.isRunning = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 148 */     this.temptingPlayer = null;
/* 149 */     this.temptedEntity.getNavigator().clearPathEntity();
/* 150 */     this.delayTemptCounter = 100;
/* 151 */     this.isRunning = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/* 159 */     this.temptedEntity.getLookHelper().setLookPositionWithEntity((Entity)this.temptingPlayer, (this.temptedEntity.getHorizontalFaceSpeed() + 20), this.temptedEntity.getVerticalFaceSpeed());
/*     */     
/* 161 */     if (this.temptedEntity.getDistanceSqToEntity((Entity)this.temptingPlayer) < 6.25D) {
/*     */       
/* 163 */       this.temptedEntity.getNavigator().clearPathEntity();
/*     */     }
/*     */     else {
/*     */       
/* 167 */       this.temptedEntity.getNavigator().tryMoveToEntityLiving((Entity)this.temptingPlayer, this.speed);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRunning() {
/* 176 */     return this.isRunning;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAITempt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */