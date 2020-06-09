/*    */ package shadersmod.client;
/*    */ 
/*    */ public enum EnumShaderOption
/*    */ {
/*  5 */   ANTIALIASING("of.options.shaders.ANTIALIASING", "antialiasingLevel", "0"),
/*  6 */   NORMAL_MAP("of.options.shaders.NORMAL_MAP", "normalMapEnabled", "true"),
/*  7 */   SPECULAR_MAP("of.options.shaders.SPECULAR_MAP", "specularMapEnabled", "true"),
/*  8 */   RENDER_RES_MUL("of.options.shaders.RENDER_RES_MUL", "renderResMul", "1.0"),
/*  9 */   SHADOW_RES_MUL("of.options.shaders.SHADOW_RES_MUL", "shadowResMul", "1.0"),
/* 10 */   HAND_DEPTH_MUL("of.options.shaders.HAND_DEPTH_MUL", "handDepthMul", "0.125"),
/* 11 */   CLOUD_SHADOW("of.options.shaders.CLOUD_SHADOW", "cloudShadow", "true"),
/* 12 */   OLD_HAND_LIGHT("of.options.shaders.OLD_HAND_LIGHT", "oldHandLight", "default"),
/* 13 */   OLD_LIGHTING("of.options.shaders.OLD_LIGHTING", "oldLighting", "default"),
/* 14 */   SHADER_PACK("of.options.shaders.SHADER_PACK", "shaderPack", ""),
/* 15 */   TWEAK_BLOCK_DAMAGE("of.options.shaders.TWEAK_BLOCK_DAMAGE", "tweakBlockDamage", "false"),
/* 16 */   SHADOW_CLIP_FRUSTRUM("of.options.shaders.SHADOW_CLIP_FRUSTRUM", "shadowClipFrustrum", "true"),
/* 17 */   TEX_MIN_FIL_B("of.options.shaders.TEX_MIN_FIL_B", "TexMinFilB", "0"),
/* 18 */   TEX_MIN_FIL_N("of.options.shaders.TEX_MIN_FIL_N", "TexMinFilN", "0"),
/* 19 */   TEX_MIN_FIL_S("of.options.shaders.TEX_MIN_FIL_S", "TexMinFilS", "0"),
/* 20 */   TEX_MAG_FIL_B("of.options.shaders.TEX_MAG_FIL_B", "TexMagFilB", "0"),
/* 21 */   TEX_MAG_FIL_N("of.options.shaders.TEX_MAG_FIL_N", "TexMagFilN", "0"),
/* 22 */   TEX_MAG_FIL_S("of.options.shaders.TEX_MAG_FIL_S", "TexMagFilS", "0");
/*    */   
/* 24 */   private String resourceKey = null;
/* 25 */   private String propertyKey = null;
/* 26 */   private String valueDefault = null;
/*    */ 
/*    */   
/*    */   EnumShaderOption(String resourceKey, String propertyKey, String valueDefault) {
/* 30 */     this.resourceKey = resourceKey;
/* 31 */     this.propertyKey = propertyKey;
/* 32 */     this.valueDefault = valueDefault;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getResourceKey() {
/* 37 */     return this.resourceKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPropertyKey() {
/* 42 */     return this.propertyKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getValueDefault() {
/* 47 */     return this.valueDefault;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\EnumShaderOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */