/*    */ package shadersmod.client;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ public class GuiButtonEnumShaderOption
/*    */   extends GuiButton {
/*  8 */   private EnumShaderOption enumShaderOption = null;
/*    */ 
/*    */   
/*    */   public GuiButtonEnumShaderOption(EnumShaderOption enumShaderOption, int x, int y, int widthIn, int heightIn) {
/* 12 */     super(enumShaderOption.ordinal(), x, y, widthIn, heightIn, getButtonText(enumShaderOption));
/* 13 */     this.enumShaderOption = enumShaderOption;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumShaderOption getEnumShaderOption() {
/* 18 */     return this.enumShaderOption;
/*    */   }
/*    */ 
/*    */   
/*    */   private static String getButtonText(EnumShaderOption eso) {
/* 23 */     String s = String.valueOf(I18n.format(eso.getResourceKey(), new Object[0])) + ": ";
/*    */     
/* 25 */     switch (eso) {
/*    */       
/*    */       case null:
/* 28 */         return String.valueOf(s) + GuiShaders.toStringAa(Shaders.configAntialiasingLevel);
/*    */       
/*    */       case NORMAL_MAP:
/* 31 */         return String.valueOf(s) + GuiShaders.toStringOnOff(Shaders.configNormalMap);
/*    */       
/*    */       case SPECULAR_MAP:
/* 34 */         return String.valueOf(s) + GuiShaders.toStringOnOff(Shaders.configSpecularMap);
/*    */       
/*    */       case RENDER_RES_MUL:
/* 37 */         return String.valueOf(s) + GuiShaders.toStringQuality(Shaders.configRenderResMul);
/*    */       
/*    */       case SHADOW_RES_MUL:
/* 40 */         return String.valueOf(s) + GuiShaders.toStringQuality(Shaders.configShadowResMul);
/*    */       
/*    */       case HAND_DEPTH_MUL:
/* 43 */         return String.valueOf(s) + GuiShaders.toStringHandDepth(Shaders.configHandDepthMul);
/*    */       
/*    */       case CLOUD_SHADOW:
/* 46 */         return String.valueOf(s) + GuiShaders.toStringOnOff(Shaders.configCloudShadow);
/*    */       
/*    */       case OLD_HAND_LIGHT:
/* 49 */         return String.valueOf(s) + Shaders.configOldHandLight.getUserValue();
/*    */       
/*    */       case OLD_LIGHTING:
/* 52 */         return String.valueOf(s) + Shaders.configOldLighting.getUserValue();
/*    */       
/*    */       case SHADOW_CLIP_FRUSTRUM:
/* 55 */         return String.valueOf(s) + GuiShaders.toStringOnOff(Shaders.configShadowClipFrustrum);
/*    */       
/*    */       case TWEAK_BLOCK_DAMAGE:
/* 58 */         return String.valueOf(s) + GuiShaders.toStringOnOff(Shaders.configTweakBlockDamage);
/*    */     } 
/*    */     
/* 61 */     return String.valueOf(s) + Shaders.getEnumShaderOption(eso);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateButtonText() {
/* 67 */     this.displayString = getButtonText(this.enumShaderOption);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\GuiButtonEnumShaderOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */