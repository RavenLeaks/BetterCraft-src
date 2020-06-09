/*     */ package wdl.gui;
/*     */ 
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import wdl.WDL;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiTurningCameraBase
/*     */   extends GuiScreen
/*     */ {
/*     */   private float yaw;
/*     */   private float yawNextTick;
/*     */   private int oldCameraMode;
/*     */   private boolean oldHideHud;
/*     */   private boolean oldShowDebug;
/*     */   private EntityPlayer.EnumChatVisibility oldChatVisibility;
/*     */   private EntityPlayerSP cam;
/*     */   private Entity oldRenderViewEntity;
/*     */   private boolean initializedCamera = false;
/*     */   private static final float ROTATION_SPEED = 1.0F;
/*     */   private static final float ROTATION_VARIANCE = 0.7F;
/*     */   
/*     */   public void initGui() {
/*  60 */     if (!this.initializedCamera) {
/*  61 */       this.cam = new EntityPlayerSP(WDL.minecraft, (World)WDL.worldClient, 
/*  62 */           WDL.thePlayer.connection, WDL.thePlayer.getStatFileWriter(), null);
/*  63 */       this.cam.setLocationAndAngles(WDL.thePlayer.posX, WDL.thePlayer.posY - 
/*  64 */           WDL.thePlayer.getYOffset(), WDL.thePlayer.posZ, 
/*  65 */           WDL.thePlayer.rotationYaw, 0.0F);
/*  66 */       this.yaw = this.yawNextTick = WDL.thePlayer.rotationYaw;
/*  67 */       this.oldCameraMode = WDL.minecraft.gameSettings.thirdPersonView;
/*  68 */       this.oldHideHud = WDL.minecraft.gameSettings.hideGUI;
/*  69 */       this.oldShowDebug = WDL.minecraft.gameSettings.showDebugInfo;
/*  70 */       this.oldChatVisibility = WDL.minecraft.gameSettings.chatVisibility;
/*  71 */       WDL.minecraft.gameSettings.thirdPersonView = 0;
/*  72 */       WDL.minecraft.gameSettings.hideGUI = true;
/*  73 */       WDL.minecraft.gameSettings.showDebugInfo = false;
/*  74 */       WDL.minecraft.gameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.HIDDEN;
/*  75 */       this.oldRenderViewEntity = WDL.minecraft.getRenderViewEntity();
/*     */       
/*  77 */       this.initializedCamera = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     WDL.minecraft.setRenderViewEntity((Entity)this.cam);
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
/*     */   public void updateScreen() {
/* 105 */     this.yaw = this.yawNextTick;
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
/* 120 */     this.yawNextTick = this.yaw + 1.0F * 
/* 121 */       (float)(1.0D + 0.699999988079071D * 
/* 122 */       Math.cos((this.yaw + 45.0F) / 45.0D * Math.PI));
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
/*     */   private double truncateDistanceIfBlockInWay(double camX, double camZ, double currentDistance) {
/* 134 */     Vec3d playerPos = WDL.thePlayer.getPositionVector().addVector(0.0D, WDL.thePlayer.getEyeHeight(), 0.0D);
/* 135 */     Vec3d offsetPos = new Vec3d(WDL.thePlayer.posX - currentDistance * camX, WDL.thePlayer.posY + WDL.thePlayer.getEyeHeight(), WDL.thePlayer.posZ + camZ);
/*     */ 
/*     */ 
/*     */     
/* 139 */     for (int i = 0; i < 9; i++) {
/*     */       
/* 141 */       float offsetX = ((i & 0x1) != 0) ? -0.1F : 0.1F;
/* 142 */       float offsetY = ((i & 0x2) != 0) ? -0.1F : 0.1F;
/* 143 */       float offsetZ = ((i & 0x4) != 0) ? -0.1F : 0.1F;
/*     */       
/* 145 */       if (i == 8) {
/* 146 */         offsetX = 0.0F;
/* 147 */         offsetY = 0.0F;
/* 148 */         offsetZ = 0.0F;
/*     */       } 
/*     */       
/* 151 */       Vec3d from = playerPos.addVector(offsetX, offsetY, offsetZ);
/* 152 */       Vec3d to = offsetPos.addVector(offsetX, offsetY, offsetZ);
/*     */       
/* 154 */       RayTraceResult pos = this.mc.world.rayTraceBlocks(from, to);
/*     */       
/* 156 */       if (pos != null) {
/* 157 */         double distance = pos.hitVec.distanceTo(playerPos);
/* 158 */         if (distance < currentDistance && distance > 0.0D) {
/* 159 */           currentDistance = distance;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     return currentDistance - 0.25D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 169 */     if (this.cam != null) {
/* 170 */       float yaw = this.yaw + (this.yawNextTick - this.yaw) * partialTicks;
/*     */       
/* 172 */       this.cam.prevRotationPitch = this.cam.rotationPitch = 0.0F;
/* 173 */       this.cam.prevRotationYaw = this.cam.rotationYaw = yaw;
/*     */       
/* 175 */       double x = Math.cos(yaw / 180.0D * Math.PI);
/* 176 */       double z = Math.sin((yaw - 90.0F) / 180.0D * Math.PI);
/*     */       
/* 178 */       double distance = truncateDistanceIfBlockInWay(x, z, 0.5D);
/* 179 */       this.cam.lastTickPosY = this.cam.prevPosY = this.cam.posY = WDL.thePlayer.posY;
/* 180 */       this.cam.lastTickPosX = this.cam.prevPosX = this.cam.posX = WDL.thePlayer.posX - 
/* 181 */         distance * x;
/* 182 */       this.cam.lastTickPosZ = this.cam.prevPosZ = this.cam.posZ = WDL.thePlayer.posZ + 
/* 183 */         distance * z;
/*     */     } 
/*     */     
/* 186 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 191 */     super.onGuiClosed();
/* 192 */     WDL.minecraft.gameSettings.thirdPersonView = this.oldCameraMode;
/* 193 */     WDL.minecraft.gameSettings.hideGUI = this.oldHideHud;
/* 194 */     WDL.minecraft.gameSettings.showDebugInfo = this.oldShowDebug;
/* 195 */     WDL.minecraft.gameSettings.chatVisibility = this.oldChatVisibility;
/* 196 */     WDL.minecraft.setRenderViewEntity(this.oldRenderViewEntity);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiTurningCameraBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */