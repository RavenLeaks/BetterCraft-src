/*    */ package me.nzxter.bettercraft.mods.cosmetics.impl;
/*    */ 
/*    */ import me.nzxter.bettercraft.mods.cosmetics.CosmeticBase;
/*    */ import me.nzxter.bettercraft.mods.cosmetics.CosmeticController;
/*    */ import me.nzxter.bettercraft.mods.cosmetics.CosmeticModelBase;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class CosmeticTopHat
/*    */   extends CosmeticBase {
/*    */   private final ModelTopHat modelTopHat;
/* 18 */   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/misc/hat.png");
/*    */   
/*    */   public CosmeticTopHat(RenderPlayer renderPlayer) {
/* 21 */     super(renderPlayer);
/* 22 */     this.modelTopHat = new ModelTopHat(renderPlayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 28 */     if (CosmeticController.shouldRenderTopHat(player)) {
/* 29 */       GlStateManager.pushMatrix();
/* 30 */       this.playerRenderer.bindTexture(TEXTURE);
/*    */       
/* 32 */       if (player.isSneaking()) {
/* 33 */         GL11.glTranslated(0.0D, 0.225D, 0.0D);
/*    */       }
/*    */       
/* 36 */       float[] color = CosmeticController.getTopHatColor(player);
/* 37 */       GL11.glColor3d(color[0], color[1], color[2]);
/* 38 */       this.modelTopHat.render((Entity)player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/* 39 */       GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 40 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   private class ModelTopHat
/*    */     extends CosmeticModelBase {
/*    */     private ModelRenderer rim;
/*    */     private ModelRenderer pointy;
/*    */     
/*    */     public ModelTopHat(RenderPlayer player) {
/* 50 */       super(player);
/* 51 */       this.rim = new ModelRenderer((ModelBase)this.playerModel, 0, 0);
/* 52 */       this.rim.addBox(-5.5F, -9.0F, -5.5F, 11, 2, 11);
/* 53 */       this.pointy = new ModelRenderer((ModelBase)this.playerModel, 0, 13);
/* 54 */       this.pointy.addBox(-3.5F, -17.0F, -3.5F, 7, 8, 7);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 61 */       this.rim.rotateAngleX = this.playerModel.bipedHead.rotateAngleX;
/* 62 */       this.rim.rotateAngleY = this.playerModel.bipedHead.rotateAngleY;
/* 63 */       this.rim.rotationPointX = 0.0F;
/* 64 */       this.rim.rotationPointY = 0.0F;
/* 65 */       this.rim.render(scale);
/*    */       
/* 67 */       this.pointy.rotateAngleX = this.playerModel.bipedHead.rotateAngleX;
/* 68 */       this.pointy.rotateAngleY = this.playerModel.bipedHead.rotateAngleY;
/* 69 */       this.pointy.rotationPointX = 0.0F;
/* 70 */       this.pointy.rotationPointY = 0.0F;
/* 71 */       this.pointy.render(scale);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\cosmetics\impl\CosmeticTopHat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */