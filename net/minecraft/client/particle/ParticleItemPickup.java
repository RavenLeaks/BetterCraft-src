/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ import optifine.Config;
/*    */ import shadersmod.client.Shaders;
/*    */ 
/*    */ public class ParticleItemPickup
/*    */   extends Particle {
/*    */   private final Entity item;
/*    */   private final Entity target;
/*    */   private int age;
/*    */   private final int maxAge;
/*    */   private final float yOffset;
/* 20 */   private final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
/*    */ 
/*    */   
/*    */   public ParticleItemPickup(World worldIn, Entity p_i1233_2_, Entity p_i1233_3_, float p_i1233_4_) {
/* 24 */     super(worldIn, p_i1233_2_.posX, p_i1233_2_.posY, p_i1233_2_.posZ, p_i1233_2_.motionX, p_i1233_2_.motionY, p_i1233_2_.motionZ);
/* 25 */     this.item = p_i1233_2_;
/* 26 */     this.target = p_i1233_3_;
/* 27 */     this.maxAge = 3;
/* 28 */     this.yOffset = p_i1233_4_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 36 */     int i = 0;
/*    */     
/* 38 */     if (Config.isShaders()) {
/*    */       
/* 40 */       i = Shaders.activeProgram;
/* 41 */       Shaders.nextEntity(this.item);
/*    */     } 
/*    */     
/* 44 */     float f = (this.age + partialTicks) / this.maxAge;
/* 45 */     f *= f;
/* 46 */     double d0 = this.item.posX;
/* 47 */     double d1 = this.item.posY;
/* 48 */     double d2 = this.item.posZ;
/* 49 */     double d3 = this.target.lastTickPosX + (this.target.posX - this.target.lastTickPosX) * partialTicks;
/* 50 */     double d4 = this.target.lastTickPosY + (this.target.posY - this.target.lastTickPosY) * partialTicks + this.yOffset;
/* 51 */     double d5 = this.target.lastTickPosZ + (this.target.posZ - this.target.lastTickPosZ) * partialTicks;
/* 52 */     double d6 = d0 + (d3 - d0) * f;
/* 53 */     double d7 = d1 + (d4 - d1) * f;
/* 54 */     double d8 = d2 + (d5 - d2) * f;
/* 55 */     int j = getBrightnessForRender(partialTicks);
/* 56 */     int k = j % 65536;
/* 57 */     int l = j / 65536;
/* 58 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k, l);
/* 59 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 60 */     d6 -= interpPosX;
/* 61 */     d7 -= interpPosY;
/* 62 */     d8 -= interpPosZ;
/* 63 */     GlStateManager.enableLighting();
/* 64 */     this.renderManager.doRenderEntity(this.item, d6, d7, d8, this.item.rotationYaw, partialTicks, false);
/*    */     
/* 66 */     if (Config.isShaders())
/*    */     {
/* 68 */       Shaders.useProgram(i);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 74 */     this.age++;
/*    */     
/* 76 */     if (this.age == this.maxAge)
/*    */     {
/* 78 */       setExpired();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFXLayer() {
/* 88 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleItemPickup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */