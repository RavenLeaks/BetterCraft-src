/*     */ package net.minecraft.client.renderer.entity.layers;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import optifine.Config;
/*     */ import optifine.CustomItems;
/*     */ import optifine.Reflector;
/*     */ import optifine.ReflectorForge;
/*     */ import shadersmod.client.Shaders;
/*     */ import shadersmod.client.ShadersRender;
/*     */ 
/*     */ public abstract class LayerArmorBase<T extends ModelBase>
/*     */   implements LayerRenderer<EntityLivingBase> {
/*  24 */   protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*     */   protected T modelLeggings;
/*     */   protected T modelArmor;
/*     */   private final RenderLivingBase<?> renderer;
/*  28 */   private float alpha = 1.0F;
/*  29 */   private float colorR = 1.0F;
/*  30 */   private float colorG = 1.0F;
/*  31 */   private float colorB = 1.0F;
/*     */   private boolean skipRenderGlint;
/*  33 */   private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public LayerArmorBase(RenderLivingBase<?> rendererIn) {
/*  37 */     this.renderer = rendererIn;
/*  38 */     initArmor();
/*     */   }
/*     */ 
/*     */   
/*     */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  43 */     renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.CHEST);
/*  44 */     renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.LEGS);
/*  45 */     renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.FEET);
/*  46 */     renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.HEAD);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCombineTextures() {
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderArmorLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn) {
/*  56 */     ItemStack itemstack = entityLivingBaseIn.getItemStackFromSlot(slotIn);
/*     */     
/*  58 */     if (itemstack.getItem() instanceof ItemArmor) {
/*     */       
/*  60 */       ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
/*     */       
/*  62 */       if (itemarmor.getEquipmentSlot() == slotIn) {
/*     */         int i; float f, f1, f2;
/*  64 */         T t = getModelFromSlot(slotIn);
/*     */         
/*  66 */         if (Reflector.ForgeHooksClient.exists())
/*     */         {
/*  68 */           t = getArmorModelHook(entityLivingBaseIn, itemstack, slotIn, t);
/*     */         }
/*     */         
/*  71 */         t.setModelAttributes(this.renderer.getMainModel());
/*  72 */         t.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
/*  73 */         setModelSlotVisible(t, slotIn);
/*  74 */         boolean flag = isLegSlot(slotIn);
/*     */         
/*  76 */         if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(itemstack, slotIn, null))
/*     */         {
/*  78 */           if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
/*     */             
/*  80 */             this.renderer.bindTexture(getArmorResource((Entity)entityLivingBaseIn, itemstack, slotIn, null));
/*     */           }
/*     */           else {
/*     */             
/*  84 */             this.renderer.bindTexture(getArmorResource(itemarmor, flag));
/*     */           } 
/*     */         }
/*     */         
/*  88 */         if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
/*     */           
/*  90 */           if (ReflectorForge.armorHasOverlay(itemarmor, itemstack)) {
/*     */             
/*  92 */             int j = itemarmor.getColor(itemstack);
/*  93 */             float f3 = (j >> 16 & 0xFF) / 255.0F;
/*  94 */             float f4 = (j >> 8 & 0xFF) / 255.0F;
/*  95 */             float f5 = (j & 0xFF) / 255.0F;
/*  96 */             GlStateManager.color(this.colorR * f3, this.colorG * f4, this.colorB * f5, this.alpha);
/*  97 */             t.render((Entity)entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*     */             
/*  99 */             if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(itemstack, slotIn, "overlay"))
/*     */             {
/* 101 */               this.renderer.bindTexture(getArmorResource((Entity)entityLivingBaseIn, itemstack, slotIn, "overlay"));
/*     */             }
/*     */           } 
/*     */           
/* 105 */           GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
/* 106 */           t.render((Entity)entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*     */           
/* 108 */           if (!this.skipRenderGlint && itemstack.hasEffect() && (!Config.isCustomItems() || !CustomItems.renderCustomArmorEffect(entityLivingBaseIn, itemstack, (ModelBase)t, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale)))
/*     */           {
/* 110 */             renderEnchantedGlint(this.renderer, entityLivingBaseIn, (ModelBase)t, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 116 */         switch (itemarmor.getArmorMaterial()) {
/*     */           
/*     */           case LEATHER:
/* 119 */             i = itemarmor.getColor(itemstack);
/* 120 */             f = (i >> 16 & 0xFF) / 255.0F;
/* 121 */             f1 = (i >> 8 & 0xFF) / 255.0F;
/* 122 */             f2 = (i & 0xFF) / 255.0F;
/* 123 */             GlStateManager.color(this.colorR * f, this.colorG * f1, this.colorB * f2, this.alpha);
/* 124 */             t.render((Entity)entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*     */             
/* 126 */             if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(itemstack, slotIn, "overlay"))
/*     */             {
/* 128 */               this.renderer.bindTexture(getArmorResource(itemarmor, flag, "overlay"));
/*     */             }
/*     */           
/*     */           case null:
/*     */           case IRON:
/*     */           case GOLD:
/*     */           case DIAMOND:
/* 135 */             GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
/* 136 */             t.render((Entity)entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*     */             break;
/*     */         } 
/* 139 */         if (!this.skipRenderGlint && itemstack.isItemEnchanted() && (!Config.isCustomItems() || !CustomItems.renderCustomArmorEffect(entityLivingBaseIn, itemstack, (ModelBase)t, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale)))
/*     */         {
/* 141 */           renderEnchantedGlint(this.renderer, entityLivingBaseIn, (ModelBase)t, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public T getModelFromSlot(EntityEquipmentSlot slotIn) {
/* 149 */     return isLegSlot(slotIn) ? this.modelLeggings : this.modelArmor;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isLegSlot(EntityEquipmentSlot slotIn) {
/* 154 */     return (slotIn == EntityEquipmentSlot.LEGS);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderEnchantedGlint(RenderLivingBase<?> p_188364_0_, EntityLivingBase p_188364_1_, ModelBase model, float p_188364_3_, float p_188364_4_, float p_188364_5_, float p_188364_6_, float p_188364_7_, float p_188364_8_, float p_188364_9_) {
/* 159 */     if (!Config.isShaders() || !Shaders.isShadowPass) {
/*     */       
/* 161 */       float f = p_188364_1_.ticksExisted + p_188364_5_;
/* 162 */       p_188364_0_.bindTexture(ENCHANTED_ITEM_GLINT_RES);
/*     */       
/* 164 */       if (Config.isShaders())
/*     */       {
/* 166 */         ShadersRender.renderEnchantedGlintBegin();
/*     */       }
/*     */       
/* 169 */       (Minecraft.getMinecraft()).entityRenderer.func_191514_d(true);
/* 170 */       GlStateManager.enableBlend();
/* 171 */       GlStateManager.depthFunc(514);
/* 172 */       GlStateManager.depthMask(false);
/* 173 */       float f1 = 0.5F;
/* 174 */       GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
/*     */       
/* 176 */       for (int i = 0; i < 2; i++) {
/*     */         
/* 178 */         GlStateManager.disableLighting();
/* 179 */         GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
/* 180 */         float f2 = 0.76F;
/* 181 */         GlStateManager.color(0.38F, 0.19F, 0.608F, 1.0F);
/* 182 */         GlStateManager.matrixMode(5890);
/* 183 */         GlStateManager.loadIdentity();
/* 184 */         float f3 = 0.33333334F;
/* 185 */         GlStateManager.scale(0.33333334F, 0.33333334F, 0.33333334F);
/* 186 */         GlStateManager.rotate(30.0F - i * 60.0F, 0.0F, 0.0F, 1.0F);
/* 187 */         GlStateManager.translate(0.0F, f * (0.001F + i * 0.003F) * 20.0F, 0.0F);
/* 188 */         GlStateManager.matrixMode(5888);
/* 189 */         model.render((Entity)p_188364_1_, p_188364_3_, p_188364_4_, p_188364_6_, p_188364_7_, p_188364_8_, p_188364_9_);
/* 190 */         GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*     */       } 
/*     */       
/* 193 */       GlStateManager.matrixMode(5890);
/* 194 */       GlStateManager.loadIdentity();
/* 195 */       GlStateManager.matrixMode(5888);
/* 196 */       GlStateManager.enableLighting();
/* 197 */       GlStateManager.depthMask(true);
/* 198 */       GlStateManager.depthFunc(515);
/* 199 */       GlStateManager.disableBlend();
/* 200 */       (Minecraft.getMinecraft()).entityRenderer.func_191514_d(false);
/*     */       
/* 202 */       if (Config.isShaders())
/*     */       {
/* 204 */         ShadersRender.renderEnchantedGlintEnd();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getArmorResource(ItemArmor armor, boolean p_177181_2_) {
/* 211 */     return getArmorResource(armor, p_177181_2_, null);
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getArmorResource(ItemArmor armor, boolean p_177178_2_, String p_177178_3_) {
/* 216 */     String s = String.format("textures/models/armor/%s_layer_%d%s.png", new Object[] { armor.getArmorMaterial().getName(), Integer.valueOf(p_177178_2_ ? 2 : 1), (p_177178_3_ == null) ? "" : String.format("_%s", new Object[] { p_177178_3_ }) });
/* 217 */     ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s);
/*     */     
/* 219 */     if (resourcelocation == null) {
/*     */       
/* 221 */       resourcelocation = new ResourceLocation(s);
/* 222 */       ARMOR_TEXTURE_RES_MAP.put(s, resourcelocation);
/*     */     } 
/*     */     
/* 225 */     return resourcelocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T getArmorModelHook(EntityLivingBase p_getArmorModelHook_1_, ItemStack p_getArmorModelHook_2_, EntityEquipmentSlot p_getArmorModelHook_3_, T p_getArmorModelHook_4_) {
/* 234 */     return p_getArmorModelHook_4_;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getArmorResource(Entity p_getArmorResource_1_, ItemStack p_getArmorResource_2_, EntityEquipmentSlot p_getArmorResource_3_, String p_getArmorResource_4_) {
/* 239 */     ItemArmor itemarmor = (ItemArmor)p_getArmorResource_2_.getItem();
/* 240 */     String s = itemarmor.getArmorMaterial().getName();
/* 241 */     String s1 = "minecraft";
/* 242 */     int i = s.indexOf(':');
/*     */     
/* 244 */     if (i != -1) {
/*     */       
/* 246 */       s1 = s.substring(0, i);
/* 247 */       s = s.substring(i + 1);
/*     */     } 
/*     */     
/* 250 */     String s2 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", new Object[] { s1, s, Integer.valueOf(isLegSlot(p_getArmorResource_3_) ? 2 : 1), (p_getArmorResource_4_ == null) ? "" : String.format("_%s", new Object[] { p_getArmorResource_4_ }) });
/* 251 */     s2 = Reflector.callString(Reflector.ForgeHooksClient_getArmorTexture, new Object[] { p_getArmorResource_1_, p_getArmorResource_2_, s2, p_getArmorResource_3_, p_getArmorResource_4_ });
/* 252 */     ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s2);
/*     */     
/* 254 */     if (resourcelocation == null) {
/*     */       
/* 256 */       resourcelocation = new ResourceLocation(s2);
/* 257 */       ARMOR_TEXTURE_RES_MAP.put(s2, resourcelocation);
/*     */     } 
/*     */     
/* 260 */     return resourcelocation;
/*     */   }
/*     */   
/*     */   protected abstract void initArmor();
/*     */   
/*     */   protected abstract void setModelSlotVisible(T paramT, EntityEquipmentSlot paramEntityEquipmentSlot);
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerArmorBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */