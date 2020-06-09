/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityAIBeg
/*     */   extends EntityAIBase {
/*     */   private final EntityWolf theWolf;
/*     */   private EntityPlayer thePlayer;
/*     */   private final World worldObject;
/*     */   private final float minPlayerDistance;
/*     */   private int timeoutCounter;
/*     */   
/*     */   public EntityAIBeg(EntityWolf wolf, float minDistance) {
/*  20 */     this.theWolf = wolf;
/*  21 */     this.worldObject = wolf.world;
/*  22 */     this.minPlayerDistance = minDistance;
/*  23 */     setMutexBits(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  31 */     this.thePlayer = this.worldObject.getClosestPlayerToEntity((Entity)this.theWolf, this.minPlayerDistance);
/*  32 */     return (this.thePlayer == null) ? false : hasPlayerGotBoneInHand(this.thePlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  40 */     if (!this.thePlayer.isEntityAlive())
/*     */     {
/*  42 */       return false;
/*     */     }
/*  44 */     if (this.theWolf.getDistanceSqToEntity((Entity)this.thePlayer) > (this.minPlayerDistance * this.minPlayerDistance))
/*     */     {
/*  46 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  50 */     return (this.timeoutCounter > 0 && hasPlayerGotBoneInHand(this.thePlayer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  59 */     this.theWolf.setBegging(true);
/*  60 */     this.timeoutCounter = 40 + this.theWolf.getRNG().nextInt(40);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  68 */     this.theWolf.setBegging(false);
/*  69 */     this.thePlayer = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  77 */     this.theWolf.getLookHelper().setLookPosition(this.thePlayer.posX, this.thePlayer.posY + this.thePlayer.getEyeHeight(), this.thePlayer.posZ, 10.0F, this.theWolf.getVerticalFaceSpeed());
/*  78 */     this.timeoutCounter--;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasPlayerGotBoneInHand(EntityPlayer player) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumHand[] arrayOfEnumHand;
/*  86 */     for (i = (arrayOfEnumHand = EnumHand.values()).length, b = 0; b < i; ) { EnumHand enumhand = arrayOfEnumHand[b];
/*     */       
/*  88 */       ItemStack itemstack = player.getHeldItem(enumhand);
/*     */       
/*  90 */       if (this.theWolf.isTamed() && itemstack.getItem() == Items.BONE)
/*     */       {
/*  92 */         return true;
/*     */       }
/*     */       
/*  95 */       if (this.theWolf.isBreedingItem(itemstack))
/*     */       {
/*  97 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/* 101 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIBeg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */