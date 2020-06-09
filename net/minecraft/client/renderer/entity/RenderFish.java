/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class RenderFish extends Render<EntityFishHook> {
/*  19 */   private static final ResourceLocation FISH_PARTICLES = new ResourceLocation("textures/particle/particles.png");
/*     */ 
/*     */   
/*     */   public RenderFish(RenderManager renderManagerIn) {
/*  23 */     super(renderManagerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityFishHook entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  31 */     EntityPlayer entityplayer = entity.func_190619_l();
/*     */     
/*  33 */     if (entityplayer != null && !this.renderOutlines) {
/*     */       double d4, d5, d6, d7;
/*  35 */       GlStateManager.pushMatrix();
/*  36 */       GlStateManager.translate((float)x, (float)y, (float)z);
/*  37 */       GlStateManager.enableRescaleNormal();
/*  38 */       GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*  39 */       bindEntityTexture(entity);
/*  40 */       Tessellator tessellator = Tessellator.getInstance();
/*  41 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  42 */       int i = 1;
/*  43 */       int j = 2;
/*  44 */       float f = 0.0625F;
/*  45 */       float f1 = 0.125F;
/*  46 */       float f2 = 0.125F;
/*  47 */       float f3 = 0.1875F;
/*  48 */       float f4 = 1.0F;
/*  49 */       float f5 = 0.5F;
/*  50 */       float f6 = 0.5F;
/*  51 */       GlStateManager.rotate(180.0F - RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/*  52 */       GlStateManager.rotate(((this.renderManager.options.thirdPersonView == 2) ? -1 : true) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/*     */       
/*  54 */       if (this.renderOutlines) {
/*     */         
/*  56 */         GlStateManager.enableColorMaterial();
/*  57 */         GlStateManager.enableOutlineMode(getTeamColor(entity));
/*     */       } 
/*     */       
/*  60 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/*  61 */       bufferbuilder.pos(-0.5D, -0.5D, 0.0D).tex(0.0625D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  62 */       bufferbuilder.pos(0.5D, -0.5D, 0.0D).tex(0.125D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  63 */       bufferbuilder.pos(0.5D, 0.5D, 0.0D).tex(0.125D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  64 */       bufferbuilder.pos(-0.5D, 0.5D, 0.0D).tex(0.0625D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  65 */       tessellator.draw();
/*     */       
/*  67 */       if (this.renderOutlines) {
/*     */         
/*  69 */         GlStateManager.disableOutlineMode();
/*  70 */         GlStateManager.disableColorMaterial();
/*     */       } 
/*     */       
/*  73 */       GlStateManager.disableRescaleNormal();
/*  74 */       GlStateManager.popMatrix();
/*  75 */       int k = (entityplayer.getPrimaryHand() == EnumHandSide.RIGHT) ? 1 : -1;
/*  76 */       ItemStack itemstack = entityplayer.getHeldItemMainhand();
/*     */       
/*  78 */       if (itemstack.getItem() != Items.FISHING_ROD)
/*     */       {
/*  80 */         k = -k;
/*     */       }
/*     */       
/*  83 */       float f7 = entityplayer.getSwingProgress(partialTicks);
/*  84 */       float f8 = MathHelper.sin(MathHelper.sqrt(f7) * 3.1415927F);
/*  85 */       float f9 = (entityplayer.prevRenderYawOffset + (entityplayer.renderYawOffset - entityplayer.prevRenderYawOffset) * partialTicks) * 0.017453292F;
/*  86 */       double d0 = MathHelper.sin(f9);
/*  87 */       double d1 = MathHelper.cos(f9);
/*  88 */       double d2 = k * 0.35D;
/*  89 */       double d3 = 0.8D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  95 */       if ((this.renderManager.options == null || this.renderManager.options.thirdPersonView <= 0) && entityplayer == (Minecraft.getMinecraft()).player) {
/*     */         
/*  97 */         float f10 = this.renderManager.options.fovSetting;
/*  98 */         f10 /= 100.0F;
/*  99 */         Vec3d vec3d = new Vec3d(k * -0.36D * f10, -0.045D * f10, 0.4D);
/* 100 */         vec3d = vec3d.rotatePitch(-(entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * partialTicks) * 0.017453292F);
/* 101 */         vec3d = vec3d.rotateYaw(-(entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * partialTicks) * 0.017453292F);
/* 102 */         vec3d = vec3d.rotateYaw(f8 * 0.5F);
/* 103 */         vec3d = vec3d.rotatePitch(-f8 * 0.7F);
/* 104 */         d4 = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * partialTicks + vec3d.xCoord;
/* 105 */         d5 = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * partialTicks + vec3d.yCoord;
/* 106 */         d6 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * partialTicks + vec3d.zCoord;
/* 107 */         d7 = entityplayer.getEyeHeight();
/*     */       }
/*     */       else {
/*     */         
/* 111 */         d4 = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * partialTicks - d1 * d2 - d0 * 0.8D;
/* 112 */         d5 = entityplayer.prevPosY + entityplayer.getEyeHeight() + (entityplayer.posY - entityplayer.prevPosY) * partialTicks - 0.45D;
/* 113 */         d6 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * partialTicks - d0 * d2 + d1 * 0.8D;
/* 114 */         d7 = entityplayer.isSneaking() ? -0.1875D : 0.0D;
/*     */       } 
/*     */       
/* 117 */       double d13 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/* 118 */       double d8 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + 0.25D;
/* 119 */       double d9 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/* 120 */       double d10 = (float)(d4 - d13);
/* 121 */       double d11 = (float)(d5 - d8) + d7;
/* 122 */       double d12 = (float)(d6 - d9);
/* 123 */       GlStateManager.disableTexture2D();
/* 124 */       GlStateManager.disableLighting();
/* 125 */       bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
/* 126 */       int l = 16;
/*     */       
/* 128 */       for (int i1 = 0; i1 <= 16; i1++) {
/*     */         
/* 130 */         float f11 = i1 / 16.0F;
/* 131 */         bufferbuilder.pos(x + d10 * f11, y + d11 * (f11 * f11 + f11) * 0.5D + 0.25D, z + d12 * f11).color(0, 0, 0, 255).endVertex();
/*     */       } 
/*     */       
/* 134 */       tessellator.draw();
/* 135 */       GlStateManager.enableLighting();
/* 136 */       GlStateManager.enableTexture2D();
/* 137 */       super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityFishHook entity) {
/* 146 */     return FISH_PARTICLES;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderFish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */