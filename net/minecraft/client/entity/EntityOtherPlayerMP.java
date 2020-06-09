/*     */ package net.minecraft.client.entity;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityOtherPlayerMP
/*     */   extends AbstractClientPlayer
/*     */ {
/*     */   private int otherPlayerMPPosRotationIncrements;
/*     */   private double otherPlayerMPX;
/*     */   private double otherPlayerMPY;
/*     */   private double otherPlayerMPZ;
/*     */   private double otherPlayerMPYaw;
/*     */   private double otherPlayerMPPitch;
/*     */   
/*     */   public EntityOtherPlayerMP(World worldIn, GameProfile gameProfileIn) {
/*  27 */     super(worldIn, gameProfileIn);
/*  28 */     this.stepHeight = 1.0F;
/*  29 */     this.noClip = true;
/*  30 */     this.renderOffsetY = 0.25F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/*  38 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 10.0D;
/*     */     
/*  40 */     if (Double.isNaN(d0))
/*     */     {
/*  42 */       d0 = 1.0D;
/*     */     }
/*     */     
/*  45 */     d0 = d0 * 64.0D * getRenderDistanceWeight();
/*  46 */     return (distance < d0 * d0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  54 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/*  62 */     this.otherPlayerMPX = x;
/*  63 */     this.otherPlayerMPY = y;
/*  64 */     this.otherPlayerMPZ = z;
/*  65 */     this.otherPlayerMPYaw = yaw;
/*  66 */     this.otherPlayerMPPitch = pitch;
/*  67 */     this.otherPlayerMPPosRotationIncrements = posRotationIncrements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  76 */     this.renderOffsetY = 0.0F;
/*  77 */     super.onUpdate();
/*  78 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/*  79 */     double d0 = this.posX - this.prevPosX;
/*  80 */     double d1 = this.posZ - this.prevPosZ;
/*  81 */     float f = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;
/*     */     
/*  83 */     if (f > 1.0F)
/*     */     {
/*  85 */       f = 1.0F;
/*     */     }
/*     */     
/*  88 */     this.limbSwingAmount += (f - this.limbSwingAmount) * 0.4F;
/*  89 */     this.limbSwing += this.limbSwingAmount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 100 */     if (this.otherPlayerMPPosRotationIncrements > 0) {
/*     */       
/* 102 */       double d0 = this.posX + (this.otherPlayerMPX - this.posX) / this.otherPlayerMPPosRotationIncrements;
/* 103 */       double d1 = this.posY + (this.otherPlayerMPY - this.posY) / this.otherPlayerMPPosRotationIncrements;
/* 104 */       double d2 = this.posZ + (this.otherPlayerMPZ - this.posZ) / this.otherPlayerMPPosRotationIncrements;
/*     */       
/*     */       double d3;
/* 107 */       for (d3 = this.otherPlayerMPYaw - this.rotationYaw; d3 < -180.0D; d3 += 360.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 112 */       while (d3 >= 180.0D)
/*     */       {
/* 114 */         d3 -= 360.0D;
/*     */       }
/*     */       
/* 117 */       this.rotationYaw = (float)(this.rotationYaw + d3 / this.otherPlayerMPPosRotationIncrements);
/* 118 */       this.rotationPitch = (float)(this.rotationPitch + (this.otherPlayerMPPitch - this.rotationPitch) / this.otherPlayerMPPosRotationIncrements);
/* 119 */       this.otherPlayerMPPosRotationIncrements--;
/* 120 */       setPosition(d0, d1, d2);
/* 121 */       setRotation(this.rotationYaw, this.rotationPitch);
/*     */     } 
/*     */     
/* 124 */     this.prevCameraYaw = this.cameraYaw;
/* 125 */     updateArmSwingProgress();
/* 126 */     float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 127 */     float f = (float)Math.atan(-this.motionY * 0.20000000298023224D) * 15.0F;
/*     */     
/* 129 */     if (f1 > 0.1F)
/*     */     {
/* 131 */       f1 = 0.1F;
/*     */     }
/*     */     
/* 134 */     if (!this.onGround || getHealth() <= 0.0F)
/*     */     {
/* 136 */       f1 = 0.0F;
/*     */     }
/*     */     
/* 139 */     if (this.onGround || getHealth() <= 0.0F)
/*     */     {
/* 141 */       f = 0.0F;
/*     */     }
/*     */     
/* 144 */     this.cameraYaw += (f1 - this.cameraYaw) * 0.4F;
/* 145 */     this.cameraPitch += (f - this.cameraPitch) * 0.8F;
/* 146 */     this.world.theProfiler.startSection("push");
/* 147 */     collideWithNearbyEntities();
/* 148 */     this.world.theProfiler.endSection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChatMessage(ITextComponent component) {
/* 156 */     (Minecraft.getMinecraft()).ingameGUI.getChatGUI().printChatMessage(component);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/* 173 */     return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\entity\EntityOtherPlayerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */