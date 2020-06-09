/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import me.nzxter.bettercraft.mods.cosmetics.impl.CosmeticTopHat;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerArrow;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerCape;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerElytra;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerEntityOnShoulder;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class RenderPlayer extends RenderLivingBase<AbstractClientPlayer> {
/*     */   private final boolean smallArms;
/*     */   
/*     */   public RenderPlayer(RenderManager renderManager) {
/*  34 */     this(renderManager, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderPlayer(RenderManager renderManager, boolean useSmallArms) {
/*  39 */     super(renderManager, (ModelBase)new ModelPlayer(0.0F, useSmallArms), 0.5F);
/*  40 */     this.smallArms = useSmallArms;
/*  41 */     addLayer(new LayerBipedArmor(this));
/*  42 */     addLayer(new LayerHeldItem(this));
/*  43 */     addLayer(new LayerArrow(this));
/*  44 */     addLayer(new LayerDeadmau5Head(this));
/*  45 */     addLayer(new LayerCape(this));
/*  46 */     addLayer(new LayerCustomHead((getMainModel()).bipedHead));
/*  47 */     addLayer(new LayerElytra(this));
/*  48 */     addLayer(new LayerEntityOnShoulder(renderManager));
/*     */ 
/*     */     
/*  51 */     addLayer(new CosmeticTopHat(this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelPlayer getMainModel() {
/*  57 */     return (ModelPlayer)super.getMainModel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  65 */     if (!entity.isUser() || this.renderManager.renderViewEntity == entity) {
/*     */       
/*  67 */       double d0 = y;
/*     */       
/*  69 */       if (entity.isSneaking())
/*     */       {
/*  71 */         d0 = y - 0.125D;
/*     */       }
/*     */       
/*  74 */       setModelVisibilities(entity);
/*  75 */       GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
/*  76 */       super.doRender(entity, x, d0, z, entityYaw, partialTicks);
/*  77 */       GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setModelVisibilities(AbstractClientPlayer clientPlayer) {
/*  83 */     ModelPlayer modelplayer = getMainModel();
/*     */     
/*  85 */     if (clientPlayer.isSpectator()) {
/*     */       
/*  87 */       modelplayer.setInvisible(false);
/*  88 */       modelplayer.bipedHead.showModel = true;
/*  89 */       modelplayer.bipedHeadwear.showModel = true;
/*     */     }
/*     */     else {
/*     */       
/*  93 */       ItemStack itemstack = clientPlayer.getHeldItemMainhand();
/*  94 */       ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
/*  95 */       modelplayer.setInvisible(true);
/*  96 */       modelplayer.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
/*  97 */       modelplayer.bipedBodyWear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.JACKET);
/*  98 */       modelplayer.bipedLeftLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
/*  99 */       modelplayer.bipedRightLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
/* 100 */       modelplayer.bipedLeftArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
/* 101 */       modelplayer.bipedRightArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
/* 102 */       modelplayer.isSneak = clientPlayer.isSneaking();
/* 103 */       ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
/* 104 */       ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;
/*     */       
/* 106 */       if (!itemstack.func_190926_b()) {
/*     */         
/* 108 */         modelbiped$armpose = ModelBiped.ArmPose.ITEM;
/*     */         
/* 110 */         if (clientPlayer.getItemInUseCount() > 0) {
/*     */           
/* 112 */           EnumAction enumaction = itemstack.getItemUseAction();
/*     */           
/* 114 */           if (enumaction == EnumAction.BLOCK) {
/*     */             
/* 116 */             modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
/*     */           }
/* 118 */           else if (enumaction == EnumAction.BOW) {
/*     */             
/* 120 */             modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 125 */       if (!itemstack1.func_190926_b()) {
/*     */         
/* 127 */         modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;
/*     */         
/* 129 */         if (clientPlayer.getItemInUseCount() > 0) {
/*     */           
/* 131 */           EnumAction enumaction1 = itemstack1.getItemUseAction();
/*     */           
/* 133 */           if (enumaction1 == EnumAction.BLOCK)
/*     */           {
/* 135 */             modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 140 */       if (clientPlayer.getPrimaryHand() == EnumHandSide.RIGHT) {
/*     */         
/* 142 */         modelplayer.rightArmPose = modelbiped$armpose;
/* 143 */         modelplayer.leftArmPose = modelbiped$armpose1;
/*     */       }
/*     */       else {
/*     */         
/* 147 */         modelplayer.rightArmPose = modelbiped$armpose1;
/* 148 */         modelplayer.leftArmPose = modelbiped$armpose;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation getEntityTexture(AbstractClientPlayer entity) {
/* 158 */     return entity.getLocationSkin();
/*     */   }
/*     */ 
/*     */   
/*     */   public void transformHeldFull3DItemLayer() {
/* 163 */     GlStateManager.translate(0.0F, 0.1875F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderCallback(AbstractClientPlayer entitylivingbaseIn, float partialTickTime) {
/* 171 */     float f = 0.9375F;
/* 172 */     GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderEntityName(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq) {
/* 177 */     if (distanceSq < 100.0D) {
/*     */       
/* 179 */       Scoreboard scoreboard = entityIn.getWorldScoreboard();
/* 180 */       ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);
/*     */       
/* 182 */       if (scoreobjective != null) {
/*     */         
/* 184 */         Score score = scoreboard.getOrCreateScore(entityIn.getName(), scoreobjective);
/* 185 */         renderLivingLabel(entityIn, String.valueOf(score.getScorePoints()) + " " + scoreobjective.getDisplayName(), x, y, z, 64);
/* 186 */         y += ((getFontRendererFromRenderManager()).FONT_HEIGHT * 1.15F * 0.025F);
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     super.renderEntityName(entityIn, x, y, z, name, distanceSq);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderRightArm(AbstractClientPlayer clientPlayer) {
/* 195 */     float f = 1.0F;
/* 196 */     GlStateManager.color(1.0F, 1.0F, 1.0F);
/* 197 */     float f1 = 0.0625F;
/* 198 */     ModelPlayer modelplayer = getMainModel();
/* 199 */     setModelVisibilities(clientPlayer);
/* 200 */     GlStateManager.enableBlend();
/* 201 */     modelplayer.swingProgress = 0.0F;
/* 202 */     modelplayer.isSneak = false;
/* 203 */     modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)clientPlayer);
/* 204 */     modelplayer.bipedRightArm.rotateAngleX = 0.0F;
/* 205 */     modelplayer.bipedRightArm.render(0.0625F);
/* 206 */     modelplayer.bipedRightArmwear.rotateAngleX = 0.0F;
/* 207 */     modelplayer.bipedRightArmwear.render(0.0625F);
/* 208 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderLeftArm(AbstractClientPlayer clientPlayer) {
/* 213 */     float f = 1.0F;
/* 214 */     GlStateManager.color(1.0F, 1.0F, 1.0F);
/* 215 */     float f1 = 0.0625F;
/* 216 */     ModelPlayer modelplayer = getMainModel();
/* 217 */     setModelVisibilities(clientPlayer);
/* 218 */     GlStateManager.enableBlend();
/* 219 */     modelplayer.isSneak = false;
/* 220 */     modelplayer.swingProgress = 0.0F;
/* 221 */     modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)clientPlayer);
/* 222 */     modelplayer.bipedLeftArm.rotateAngleX = 0.0F;
/* 223 */     modelplayer.bipedLeftArm.render(0.0625F);
/* 224 */     modelplayer.bipedLeftArmwear.rotateAngleX = 0.0F;
/* 225 */     modelplayer.bipedLeftArmwear.render(0.0625F);
/* 226 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderLivingAt(AbstractClientPlayer entityLivingBaseIn, double x, double y, double z) {
/* 234 */     if (entityLivingBaseIn.isEntityAlive() && entityLivingBaseIn.isPlayerSleeping()) {
/*     */       
/* 236 */       super.renderLivingAt(entityLivingBaseIn, x + entityLivingBaseIn.renderOffsetX, y + entityLivingBaseIn.renderOffsetY, z + entityLivingBaseIn.renderOffsetZ);
/*     */     }
/*     */     else {
/*     */       
/* 240 */       super.renderLivingAt(entityLivingBaseIn, x, y, z);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void rotateCorpse(AbstractClientPlayer entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 246 */     if (entityLiving.isEntityAlive() && entityLiving.isPlayerSleeping()) {
/*     */       
/* 248 */       GlStateManager.rotate(entityLiving.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
/* 249 */       GlStateManager.rotate(getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
/* 250 */       GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
/*     */     }
/* 252 */     else if (entityLiving.isElytraFlying()) {
/*     */       
/* 254 */       super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
/* 255 */       float f = entityLiving.getTicksElytraFlying() + partialTicks;
/* 256 */       float f1 = MathHelper.clamp(f * f / 100.0F, 0.0F, 1.0F);
/* 257 */       GlStateManager.rotate(f1 * (-90.0F - entityLiving.rotationPitch), 1.0F, 0.0F, 0.0F);
/* 258 */       Vec3d vec3d = entityLiving.getLook(partialTicks);
/* 259 */       double d0 = entityLiving.motionX * entityLiving.motionX + entityLiving.motionZ * entityLiving.motionZ;
/* 260 */       double d1 = vec3d.xCoord * vec3d.xCoord + vec3d.zCoord * vec3d.zCoord;
/*     */       
/* 262 */       if (d0 > 0.0D && d1 > 0.0D)
/*     */       {
/* 264 */         double d2 = (entityLiving.motionX * vec3d.xCoord + entityLiving.motionZ * vec3d.zCoord) / Math.sqrt(d0) * Math.sqrt(d1);
/* 265 */         double d3 = entityLiving.motionX * vec3d.zCoord - entityLiving.motionZ * vec3d.xCoord;
/* 266 */         GlStateManager.rotate((float)(Math.signum(d3) * Math.acos(d2)) * 180.0F / 3.1415927F, 0.0F, 1.0F, 0.0F);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 271 */       super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */