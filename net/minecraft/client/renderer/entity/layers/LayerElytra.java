/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelElytra;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import optifine.Config;
/*    */ import optifine.CustomItems;
/*    */ 
/*    */ public class LayerElytra implements LayerRenderer<EntityLivingBase> {
/* 19 */   private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
/*    */   
/*    */   protected final RenderLivingBase<?> renderPlayer;
/*    */   
/* 23 */   private final ModelElytra modelElytra = new ModelElytra();
/*    */ 
/*    */   
/*    */   public LayerElytra(RenderLivingBase<?> p_i47185_1_) {
/* 27 */     this.renderPlayer = p_i47185_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 32 */     ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
/*    */     
/* 34 */     if (itemstack.getItem() == Items.ELYTRA) {
/*    */       
/* 36 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 37 */       GlStateManager.enableBlend();
/* 38 */       GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*    */       
/* 40 */       if (entitylivingbaseIn instanceof AbstractClientPlayer) {
/*    */         
/* 42 */         AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entitylivingbaseIn;
/*    */         
/* 44 */         if (abstractclientplayer.isPlayerInfoSet() && abstractclientplayer.getLocationElytra() != null)
/*    */         {
/* 46 */           this.renderPlayer.bindTexture(abstractclientplayer.getLocationElytra());
/*    */         }
/* 48 */         else if (abstractclientplayer.hasElytraCape() && abstractclientplayer.hasPlayerInfo() && abstractclientplayer.getLocationCape() != null && abstractclientplayer.isWearing(EnumPlayerModelParts.CAPE))
/*    */         {
/* 50 */           this.renderPlayer.bindTexture(abstractclientplayer.getLocationCape());
/*    */         }
/*    */         else
/*    */         {
/* 54 */           ResourceLocation resourcelocation1 = TEXTURE_ELYTRA;
/*    */           
/* 56 */           if (Config.isCustomItems())
/*    */           {
/* 58 */             resourcelocation1 = CustomItems.getCustomElytraTexture(itemstack, resourcelocation1);
/*    */           }
/*    */           
/* 61 */           this.renderPlayer.bindTexture(resourcelocation1);
/*    */         }
/*    */       
/*    */       } else {
/*    */         
/* 66 */         ResourceLocation resourcelocation = TEXTURE_ELYTRA;
/*    */         
/* 68 */         if (Config.isCustomItems())
/*    */         {
/* 70 */           resourcelocation = CustomItems.getCustomElytraTexture(itemstack, resourcelocation);
/*    */         }
/*    */         
/* 73 */         this.renderPlayer.bindTexture(resourcelocation);
/*    */       } 
/*    */       
/* 76 */       GlStateManager.pushMatrix();
/* 77 */       GlStateManager.translate(0.0F, 0.0F, 0.125F);
/* 78 */       this.modelElytra.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, (Entity)entitylivingbaseIn);
/* 79 */       this.modelElytra.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*    */       
/* 81 */       if (itemstack.isItemEnchanted())
/*    */       {
/* 83 */         LayerArmorBase.renderEnchantedGlint(this.renderPlayer, entitylivingbaseIn, (ModelBase)this.modelElytra, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
/*    */       }
/*    */       
/* 86 */       GlStateManager.disableBlend();
/* 87 */       GlStateManager.popMatrix();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 93 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerElytra.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */