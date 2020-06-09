/*     */ package me.nzxter.bettercraft.utils;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderItem;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ItemPhysicUtils
/*     */ {
/*  20 */   public static Random random = new Random();
/*  21 */   public static Minecraft mc = Minecraft.getMinecraft();
/*  22 */   public static RenderItem renderItem = mc.getRenderItem();
/*     */   public static long tick;
/*     */   public static double rotation;
/*  25 */   public static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*     */   
/*     */   public static void doRender(Entity par1Entity, double x, double y, double z, float par8, float par9) {
/*  28 */     rotation = (System.nanoTime() - tick) / 3000000.0D * 1.0D;
/*  29 */     if (!mc.inGameHasFocus) {
/*  30 */       rotation = 0.0D;
/*     */     }
/*  32 */     EntityItem item = (EntityItem)par1Entity;
/*  33 */     ItemStack itemstack = item.getEntityItem();
/*  34 */     if (itemstack.getItem() != null) {
/*  35 */       random.setSeed(187L);
/*  36 */       boolean flag = false;
/*  37 */       if (TextureMap.LOCATION_BLOCKS_TEXTURE != null) {
/*  38 */         (mc.getRenderManager()).renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  39 */         (mc.getRenderManager()).renderEngine.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
/*     */         
/*  41 */         flag = true;
/*     */       } 
/*  43 */       GlStateManager.enableRescaleNormal();
/*  44 */       GlStateManager.alphaFunc(516, 0.1F);
/*  45 */       GlStateManager.enableBlend();
/*  46 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  47 */       GlStateManager.pushMatrix();
/*     */       
/*  49 */       IBakedModel ibakedmodel = renderItem.getItemModelMesher().getItemModel(itemstack);
/*  50 */       int i = func_177077_a(item, x, y, z, par9, ibakedmodel);
/*     */       
/*  52 */       BlockPos pos = new BlockPos((Entity)item);
/*  53 */       if (item.rotationPitch > 360.0F) {
/*  54 */         item.rotationPitch = 0.0F;
/*     */       }
/*  56 */       if (item != null && !Double.isNaN(item.getAge()) && 
/*  57 */         !Double.isNaN(item.getAir()) && !Double.isNaN(item.getEntityId()) && item.getPosition() != null) {
/*  58 */         if (item.onGround) {
/*  59 */           if (item.rotationPitch != 0.0F && item.rotationPitch != 90.0F && item.rotationPitch != 180.0F && item.rotationPitch != 270.0F) {
/*  60 */             double Abstand0 = formPositiv(item.rotationPitch);
/*  61 */             double Abstand90 = formPositiv(item.rotationPitch - 90.0F);
/*  62 */             double Abstand180 = formPositiv(item.rotationPitch - 180.0F);
/*  63 */             double Abstand270 = formPositiv(item.rotationPitch - 270.0F);
/*  64 */             if (Abstand0 <= Abstand90 && Abstand0 <= Abstand180 && Abstand0 <= Abstand270) {
/*  65 */               if (item.rotationPitch < 0.0F) {
/*  66 */                 EntityItem tmp389_387 = item;
/*  67 */                 tmp389_387.rotationPitch = (float)(tmp389_387.rotationPitch + rotation);
/*     */               } else {
/*  69 */                 EntityItem tmp407_405 = item;
/*  70 */                 tmp407_405.rotationPitch = (float)(tmp407_405.rotationPitch - rotation);
/*     */               } 
/*     */             }
/*  73 */             if (Abstand90 < Abstand0 && Abstand90 <= Abstand180 && Abstand90 <= Abstand270) {
/*  74 */               if (item.rotationPitch - 90.0F < 0.0F) {
/*  75 */                 EntityItem tmp459_457 = item;
/*  76 */                 tmp459_457.rotationPitch = (float)(tmp459_457.rotationPitch + rotation);
/*     */               } else {
/*  78 */                 EntityItem tmp477_475 = item;
/*  79 */                 tmp477_475.rotationPitch = (float)(tmp477_475.rotationPitch - rotation);
/*     */               } 
/*     */             }
/*  82 */             if (Abstand180 < Abstand90 && Abstand180 < Abstand0 && Abstand180 <= Abstand270) {
/*  83 */               if (item.rotationPitch - 180.0F < 0.0F) {
/*  84 */                 EntityItem tmp529_527 = item;
/*  85 */                 tmp529_527.rotationPitch = (float)(tmp529_527.rotationPitch + rotation);
/*     */               } else {
/*  87 */                 EntityItem tmp547_545 = item;
/*  88 */                 tmp547_545.rotationPitch = (float)(tmp547_545.rotationPitch - rotation);
/*     */               } 
/*     */             }
/*  91 */             if (Abstand270 < Abstand90 && Abstand270 < Abstand180 && Abstand270 < Abstand0) {
/*  92 */               if (item.rotationPitch - 270.0F < 0.0F) {
/*  93 */                 EntityItem tmp599_597 = item;
/*  94 */                 tmp599_597.rotationPitch = (float)(tmp599_597.rotationPitch + rotation);
/*     */               } else {
/*  96 */                 EntityItem tmp617_615 = item;
/*  97 */                 tmp617_615.rotationPitch = (float)(tmp617_615.rotationPitch - rotation);
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } else {
/* 102 */           BlockPos posUp = new BlockPos((Entity)item);
/* 103 */           posUp.add(0, 1, 0);
/*     */           
/* 105 */           Material m1 = item.world.getBlockState(posUp).getBlock().getMaterial(null);
/*     */           
/* 107 */           Material m2 = item.world.getBlockState(pos).getBlock().getMaterial(null);
/*     */           
/* 109 */           boolean m3 = item.isInsideOfMaterial(Material.WATER);
/* 110 */           boolean m4 = item.isInWater();
/* 111 */           if ((m3 | ((m1 == Material.WATER) ? 1 : 0) | ((m2 == Material.WATER) ? 1 : 0) | m4) != 0) {
/* 112 */             EntityItem tmp748_746 = item;
/* 113 */             tmp748_746.rotationPitch = (float)(tmp748_746.rotationPitch + rotation / 4.0D);
/*     */           } else {
/* 115 */             EntityItem tmp770_768 = item;
/* 116 */             tmp770_768.rotationPitch = (float)(tmp770_768.rotationPitch + rotation * 2.0D);
/*     */           } 
/*     */         } 
/*     */       }
/* 120 */       GL11.glRotatef(item.rotationYaw, 0.0F, 0.2F, 0.0F);
/* 121 */       GL11.glRotatef(item.rotationPitch + 90.0F, 1.0F, 0.0F, 0.0F);
/* 122 */       for (int j = 0; j < i; j++) {
/* 123 */         if (ibakedmodel.isAmbientOcclusion()) {
/* 124 */           GlStateManager.pushMatrix();
/* 125 */           GlStateManager.scale(0.2F, 0.2F, 0.2F);
/* 126 */           renderItem.renderItem(itemstack, ibakedmodel);
/* 127 */           GlStateManager.popMatrix();
/*     */         } else {
/* 129 */           GlStateManager.scale(0.5F, 0.5F, 0.5F);
/* 130 */           GlStateManager.pushMatrix();
/* 131 */           if (j > 0 && shouldSpreadItems()) {
/* 132 */             GlStateManager.translate(0.0F, 0.0F, 0.046875F * j);
/*     */           }
/* 134 */           renderItem.renderItem(itemstack, ibakedmodel);
/* 135 */           if (!shouldSpreadItems()) {
/* 136 */             GlStateManager.translate(0.0F, 0.0F, 0.046875F);
/*     */           }
/* 138 */           GlStateManager.popMatrix();
/*     */         } 
/*     */       } 
/* 141 */       GlStateManager.popMatrix();
/* 142 */       GlStateManager.disableRescaleNormal();
/* 143 */       GlStateManager.disableBlend();
/* 144 */       (mc.getRenderManager()).renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 145 */       if (flag) {
/* 146 */         (mc.getRenderManager()).renderEngine.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int func_177077_a(EntityItem item, double x, double y, double z, float p_177077_8_, IBakedModel p_177077_9_) {
/* 152 */     ItemStack itemstack = item.getEntityItem();
/* 153 */     Item item2 = itemstack.getItem();
/* 154 */     if (item2 == null) {
/* 155 */       return 0;
/*     */     }
/* 157 */     boolean flag = p_177077_9_.isAmbientOcclusion();
/* 158 */     int i = func_177078_a(itemstack);
/* 159 */     float f1 = 0.25F;
/* 160 */     float f2 = 0.0F;
/* 161 */     GlStateManager.translate((float)x, (float)y + f2 + 0.25F, (float)z);
/* 162 */     float f3 = 0.0F;
/* 163 */     if (flag || ((mc.getRenderManager()).renderEngine != null && 
/* 164 */       mc.gameSettings.fancyGraphics)) {
/* 165 */       GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
/*     */     }
/* 167 */     if (!flag) {
/* 168 */       f3 = -0.0F * (i - 1) * 0.5F;
/* 169 */       float f4 = -0.0F * (i - 1) * 0.5F;
/* 170 */       float f5 = -0.046875F * (i - 1) * 0.5F;
/* 171 */       GlStateManager.translate(f3, f4, f5);
/*     */     } 
/* 173 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 174 */     return i;
/*     */   }
/*     */   
/*     */   public static boolean shouldSpreadItems() {
/* 178 */     return true;
/*     */   }
/*     */   
/*     */   public static double formPositiv(float rotationPitch) {
/* 182 */     if (rotationPitch > 0.0F) {
/* 183 */       return rotationPitch;
/*     */     }
/* 185 */     return -rotationPitch;
/*     */   }
/*     */   
/*     */   public static int func_177078_a(ItemStack stack) {
/* 189 */     byte b0 = 1;
/* 190 */     if (stack.animationsToGo > 48) {
/* 191 */       b0 = 5;
/* 192 */     } else if (stack.animationsToGo > 32) {
/* 193 */       b0 = 4;
/* 194 */     } else if (stack.animationsToGo > 16) {
/* 195 */       b0 = 3;
/* 196 */     } else if (stack.animationsToGo > 1) {
/* 197 */       b0 = 2;
/*     */     } 
/* 199 */     return b0;
/*     */   }
/*     */   
/*     */   public static byte getMiniBlockCount(ItemStack stack, byte original) {
/* 203 */     return original;
/*     */   }
/*     */   
/*     */   public static byte getMiniItemCount(ItemStack stack, byte original) {
/* 207 */     return original;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\ItemPhysicUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */