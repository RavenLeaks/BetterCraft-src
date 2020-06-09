/*     */ package net.minecraft.client.util;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ 
/*     */ 
/*     */ public class JsonBlendingMode
/*     */ {
/*     */   private static JsonBlendingMode lastApplied;
/*     */   private final int srcColorFactor;
/*     */   private final int srcAlphaFactor;
/*     */   private final int destColorFactor;
/*     */   private final int destAlphaFactor;
/*     */   private final int blendFunction;
/*     */   private final boolean separateBlend;
/*     */   private final boolean opaque;
/*     */   
/*     */   private JsonBlendingMode(boolean p_i45084_1_, boolean p_i45084_2_, int p_i45084_3_, int p_i45084_4_, int p_i45084_5_, int p_i45084_6_, int p_i45084_7_) {
/*  21 */     this.separateBlend = p_i45084_1_;
/*  22 */     this.srcColorFactor = p_i45084_3_;
/*  23 */     this.destColorFactor = p_i45084_4_;
/*  24 */     this.srcAlphaFactor = p_i45084_5_;
/*  25 */     this.destAlphaFactor = p_i45084_6_;
/*  26 */     this.opaque = p_i45084_2_;
/*  27 */     this.blendFunction = p_i45084_7_;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonBlendingMode() {
/*  32 */     this(false, true, 1, 0, 1, 0, 32774);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonBlendingMode(int p_i45085_1_, int p_i45085_2_, int p_i45085_3_) {
/*  37 */     this(false, false, p_i45085_1_, p_i45085_2_, p_i45085_1_, p_i45085_2_, p_i45085_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonBlendingMode(int p_i45086_1_, int p_i45086_2_, int p_i45086_3_, int p_i45086_4_, int p_i45086_5_) {
/*  42 */     this(true, false, p_i45086_1_, p_i45086_2_, p_i45086_3_, p_i45086_4_, p_i45086_5_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void apply() {
/*  47 */     if (!equals(lastApplied)) {
/*     */       
/*  49 */       if (lastApplied == null || this.opaque != lastApplied.isOpaque()) {
/*     */         
/*  51 */         lastApplied = this;
/*     */         
/*  53 */         if (this.opaque) {
/*     */           
/*  55 */           GlStateManager.disableBlend();
/*     */           
/*     */           return;
/*     */         } 
/*  59 */         GlStateManager.enableBlend();
/*     */       } 
/*     */       
/*  62 */       GlStateManager.glBlendEquation(this.blendFunction);
/*     */       
/*  64 */       if (this.separateBlend) {
/*     */         
/*  66 */         GlStateManager.tryBlendFuncSeparate(this.srcColorFactor, this.destColorFactor, this.srcAlphaFactor, this.destAlphaFactor);
/*     */       }
/*     */       else {
/*     */         
/*  70 */         GlStateManager.blendFunc(this.srcColorFactor, this.destColorFactor);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  77 */     if (this == p_equals_1_)
/*     */     {
/*  79 */       return true;
/*     */     }
/*  81 */     if (!(p_equals_1_ instanceof JsonBlendingMode))
/*     */     {
/*  83 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  87 */     JsonBlendingMode jsonblendingmode = (JsonBlendingMode)p_equals_1_;
/*     */     
/*  89 */     if (this.blendFunction != jsonblendingmode.blendFunction)
/*     */     {
/*  91 */       return false;
/*     */     }
/*  93 */     if (this.destAlphaFactor != jsonblendingmode.destAlphaFactor)
/*     */     {
/*  95 */       return false;
/*     */     }
/*  97 */     if (this.destColorFactor != jsonblendingmode.destColorFactor)
/*     */     {
/*  99 */       return false;
/*     */     }
/* 101 */     if (this.opaque != jsonblendingmode.opaque)
/*     */     {
/* 103 */       return false;
/*     */     }
/* 105 */     if (this.separateBlend != jsonblendingmode.separateBlend)
/*     */     {
/* 107 */       return false;
/*     */     }
/* 109 */     if (this.srcAlphaFactor != jsonblendingmode.srcAlphaFactor)
/*     */     {
/* 111 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 115 */     return (this.srcColorFactor == jsonblendingmode.srcColorFactor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     int i = this.srcColorFactor;
/* 123 */     i = 31 * i + this.srcAlphaFactor;
/* 124 */     i = 31 * i + this.destColorFactor;
/* 125 */     i = 31 * i + this.destAlphaFactor;
/* 126 */     i = 31 * i + this.blendFunction;
/* 127 */     i = 31 * i + (this.separateBlend ? 1 : 0);
/* 128 */     i = 31 * i + (this.opaque ? 1 : 0);
/* 129 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpaque() {
/* 134 */     return this.opaque;
/*     */   }
/*     */ 
/*     */   
/*     */   public static JsonBlendingMode parseBlendNode(JsonObject json) {
/* 139 */     if (json == null)
/*     */     {
/* 141 */       return new JsonBlendingMode();
/*     */     }
/*     */ 
/*     */     
/* 145 */     int i = 32774;
/* 146 */     int j = 1;
/* 147 */     int k = 0;
/* 148 */     int l = 1;
/* 149 */     int i1 = 0;
/* 150 */     boolean flag = true;
/* 151 */     boolean flag1 = false;
/*     */     
/* 153 */     if (JsonUtils.isString(json, "func")) {
/*     */       
/* 155 */       i = stringToBlendFunction(json.get("func").getAsString());
/*     */       
/* 157 */       if (i != 32774)
/*     */       {
/* 159 */         flag = false;
/*     */       }
/*     */     } 
/*     */     
/* 163 */     if (JsonUtils.isString(json, "srcrgb")) {
/*     */       
/* 165 */       j = stringToBlendFactor(json.get("srcrgb").getAsString());
/*     */       
/* 167 */       if (j != 1)
/*     */       {
/* 169 */         flag = false;
/*     */       }
/*     */     } 
/*     */     
/* 173 */     if (JsonUtils.isString(json, "dstrgb")) {
/*     */       
/* 175 */       k = stringToBlendFactor(json.get("dstrgb").getAsString());
/*     */       
/* 177 */       if (k != 0)
/*     */       {
/* 179 */         flag = false;
/*     */       }
/*     */     } 
/*     */     
/* 183 */     if (JsonUtils.isString(json, "srcalpha")) {
/*     */       
/* 185 */       l = stringToBlendFactor(json.get("srcalpha").getAsString());
/*     */       
/* 187 */       if (l != 1)
/*     */       {
/* 189 */         flag = false;
/*     */       }
/*     */       
/* 192 */       flag1 = true;
/*     */     } 
/*     */     
/* 195 */     if (JsonUtils.isString(json, "dstalpha")) {
/*     */       
/* 197 */       i1 = stringToBlendFactor(json.get("dstalpha").getAsString());
/*     */       
/* 199 */       if (i1 != 0)
/*     */       {
/* 201 */         flag = false;
/*     */       }
/*     */       
/* 204 */       flag1 = true;
/*     */     } 
/*     */     
/* 207 */     if (flag)
/*     */     {
/* 209 */       return new JsonBlendingMode();
/*     */     }
/*     */ 
/*     */     
/* 213 */     return flag1 ? new JsonBlendingMode(j, k, l, i1, i) : new JsonBlendingMode(j, k, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int stringToBlendFunction(String p_148108_0_) {
/* 220 */     String s = p_148108_0_.trim().toLowerCase(Locale.ROOT);
/*     */     
/* 222 */     if ("add".equals(s))
/*     */     {
/* 224 */       return 32774;
/*     */     }
/* 226 */     if ("subtract".equals(s))
/*     */     {
/* 228 */       return 32778;
/*     */     }
/* 230 */     if ("reversesubtract".equals(s))
/*     */     {
/* 232 */       return 32779;
/*     */     }
/* 234 */     if ("reverse_subtract".equals(s))
/*     */     {
/* 236 */       return 32779;
/*     */     }
/* 238 */     if ("min".equals(s))
/*     */     {
/* 240 */       return 32775;
/*     */     }
/*     */ 
/*     */     
/* 244 */     return "max".equals(s) ? 32776 : 32774;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int stringToBlendFactor(String p_148107_0_) {
/* 250 */     String s = p_148107_0_.trim().toLowerCase(Locale.ROOT);
/* 251 */     s = s.replaceAll("_", "");
/* 252 */     s = s.replaceAll("one", "1");
/* 253 */     s = s.replaceAll("zero", "0");
/* 254 */     s = s.replaceAll("minus", "-");
/*     */     
/* 256 */     if ("0".equals(s))
/*     */     {
/* 258 */       return 0;
/*     */     }
/* 260 */     if ("1".equals(s))
/*     */     {
/* 262 */       return 1;
/*     */     }
/* 264 */     if ("srccolor".equals(s))
/*     */     {
/* 266 */       return 768;
/*     */     }
/* 268 */     if ("1-srccolor".equals(s))
/*     */     {
/* 270 */       return 769;
/*     */     }
/* 272 */     if ("dstcolor".equals(s))
/*     */     {
/* 274 */       return 774;
/*     */     }
/* 276 */     if ("1-dstcolor".equals(s))
/*     */     {
/* 278 */       return 775;
/*     */     }
/* 280 */     if ("srcalpha".equals(s))
/*     */     {
/* 282 */       return 770;
/*     */     }
/* 284 */     if ("1-srcalpha".equals(s))
/*     */     {
/* 286 */       return 771;
/*     */     }
/* 288 */     if ("dstalpha".equals(s))
/*     */     {
/* 290 */       return 772;
/*     */     }
/*     */ 
/*     */     
/* 294 */     return "1-dstalpha".equals(s) ? 773 : -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\clien\\util\JsonBlendingMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */