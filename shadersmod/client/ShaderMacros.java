/*     */ package shadersmod.client;
/*     */ 
/*     */ import net.minecraft.util.Util;
/*     */ import optifine.Config;
/*     */ 
/*     */ public class ShaderMacros
/*     */ {
/*   8 */   private static String PREFIX_MACRO = "MC_";
/*     */   
/*     */   public static final String MC_VERSION = "MC_VERSION";
/*     */   public static final String MC_GL_VERSION = "MC_GL_VERSION";
/*     */   public static final String MC_GLSL_VERSION = "MC_GLSL_VERSION";
/*     */   public static final String MC_OS_WINDOWS = "MC_OS_WINDOWS";
/*     */   public static final String MC_OS_MAC = "MC_OS_MAC";
/*     */   public static final String MC_OS_LINUX = "MC_OS_LINUX";
/*     */   public static final String MC_OS_OTHER = "MC_OS_OTHER";
/*     */   public static final String MC_GL_VENDOR_ATI = "MC_GL_VENDOR_ATI";
/*     */   public static final String MC_GL_VENDOR_INTEL = "MC_GL_VENDOR_INTEL";
/*     */   public static final String MC_GL_VENDOR_NVIDIA = "MC_GL_VENDOR_NVIDIA";
/*     */   public static final String MC_GL_VENDOR_XORG = "MC_GL_VENDOR_XORG";
/*     */   public static final String MC_GL_VENDOR_OTHER = "MC_GL_VENDOR_OTHER";
/*     */   public static final String MC_GL_RENDERER_RADEON = "MC_GL_RENDERER_RADEON";
/*     */   public static final String MC_GL_RENDERER_GEFORCE = "MC_GL_RENDERER_GEFORCE";
/*     */   public static final String MC_GL_RENDERER_QUADRO = "MC_GL_RENDERER_QUADRO";
/*     */   public static final String MC_GL_RENDERER_INTEL = "MC_GL_RENDERER_INTEL";
/*     */   public static final String MC_GL_RENDERER_GALLIUM = "MC_GL_RENDERER_GALLIUM";
/*     */   public static final String MC_GL_RENDERER_MESA = "MC_GL_RENDERER_MESA";
/*     */   public static final String MC_GL_RENDERER_OTHER = "MC_GL_RENDERER_OTHER";
/*     */   public static final String MC_FXAA_LEVEL = "MC_FXAA_LEVEL";
/*     */   public static final String MC_NORMAL_MAP = "MC_NORMAL_MAP";
/*     */   public static final String MC_SPECULAR_MAP = "MC_SPECULAR_MAP";
/*     */   public static final String MC_RENDER_QUALITY = "MC_RENDER_QUALITY";
/*     */   public static final String MC_SHADOW_QUALITY = "MC_SHADOW_QUALITY";
/*     */   public static final String MC_HAND_DEPTH = "MC_HAND_DEPTH";
/*     */   public static final String MC_OLD_HAND_LIGHT = "MC_OLD_HAND_LIGHT";
/*     */   public static final String MC_OLD_LIGHTING = "MC_OLD_LIGHTING";
/*     */   private static String[] extensionMacros;
/*     */   
/*     */   public static String getOs() {
/*  40 */     Util.EnumOS util$enumos = Util.getOSType();
/*     */     
/*  42 */     switch (util$enumos) {
/*     */       
/*     */       case WINDOWS:
/*  45 */         return "MC_OS_WINDOWS";
/*     */       
/*     */       case OSX:
/*  48 */         return "MC_OS_MAC";
/*     */       
/*     */       case null:
/*  51 */         return "MC_OS_LINUX";
/*     */     } 
/*     */     
/*  54 */     return "MC_OS_OTHER";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVendor() {
/*  60 */     String s = Config.openGlVendor;
/*     */     
/*  62 */     if (s == null)
/*     */     {
/*  64 */       return "MC_GL_VENDOR_OTHER";
/*     */     }
/*     */ 
/*     */     
/*  68 */     s = s.toLowerCase();
/*     */     
/*  70 */     if (s.startsWith("ati"))
/*     */     {
/*  72 */       return "MC_GL_VENDOR_ATI";
/*     */     }
/*  74 */     if (s.startsWith("intel"))
/*     */     {
/*  76 */       return "MC_GL_VENDOR_INTEL";
/*     */     }
/*  78 */     if (s.startsWith("nvidia"))
/*     */     {
/*  80 */       return "MC_GL_VENDOR_NVIDIA";
/*     */     }
/*     */ 
/*     */     
/*  84 */     return s.startsWith("x.org") ? "MC_GL_VENDOR_XORG" : "MC_GL_VENDOR_OTHER";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getRenderer() {
/*  91 */     String s = Config.openGlRenderer;
/*     */     
/*  93 */     if (s == null)
/*     */     {
/*  95 */       return "MC_GL_RENDERER_OTHER";
/*     */     }
/*     */ 
/*     */     
/*  99 */     s = s.toLowerCase();
/*     */     
/* 101 */     if (s.startsWith("amd"))
/*     */     {
/* 103 */       return "MC_GL_RENDERER_RADEON";
/*     */     }
/* 105 */     if (s.startsWith("ati"))
/*     */     {
/* 107 */       return "MC_GL_RENDERER_RADEON";
/*     */     }
/* 109 */     if (s.startsWith("radeon"))
/*     */     {
/* 111 */       return "MC_GL_RENDERER_RADEON";
/*     */     }
/* 113 */     if (s.startsWith("gallium"))
/*     */     {
/* 115 */       return "MC_GL_RENDERER_GALLIUM";
/*     */     }
/* 117 */     if (s.startsWith("intel"))
/*     */     {
/* 119 */       return "MC_GL_RENDERER_INTEL";
/*     */     }
/* 121 */     if (s.startsWith("geforce"))
/*     */     {
/* 123 */       return "MC_GL_RENDERER_GEFORCE";
/*     */     }
/* 125 */     if (s.startsWith("nvidia"))
/*     */     {
/* 127 */       return "MC_GL_RENDERER_GEFORCE";
/*     */     }
/* 129 */     if (s.startsWith("quadro"))
/*     */     {
/* 131 */       return "MC_GL_RENDERER_QUADRO";
/*     */     }
/* 133 */     if (s.startsWith("nvs"))
/*     */     {
/* 135 */       return "MC_GL_RENDERER_QUADRO";
/*     */     }
/*     */ 
/*     */     
/* 139 */     return s.startsWith("mesa") ? "MC_GL_RENDERER_MESA" : "MC_GL_RENDERER_OTHER";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPrefixMacro() {
/* 146 */     return PREFIX_MACRO;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] getExtensions() {
/* 151 */     if (extensionMacros == null) {
/*     */       
/* 153 */       String[] astring = Config.getOpenGlExtensions();
/* 154 */       String[] astring1 = new String[astring.length];
/*     */       
/* 156 */       for (int i = 0; i < astring.length; i++)
/*     */       {
/* 158 */         astring1[i] = String.valueOf(PREFIX_MACRO) + astring[i];
/*     */       }
/*     */       
/* 161 */       extensionMacros = astring1;
/*     */     } 
/*     */     
/* 164 */     return extensionMacros;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getMacroLines() {
/* 169 */     StringBuilder stringbuilder = new StringBuilder();
/* 170 */     addMacroLine(stringbuilder, "MC_VERSION", Config.getMinecraftVersionInt());
/* 171 */     addMacroLine(stringbuilder, "MC_GL_VERSION " + Config.getGlVersion().toInt());
/* 172 */     addMacroLine(stringbuilder, "MC_GLSL_VERSION " + Config.getGlslVersion().toInt());
/* 173 */     addMacroLine(stringbuilder, getOs());
/* 174 */     addMacroLine(stringbuilder, getVendor());
/* 175 */     addMacroLine(stringbuilder, getRenderer());
/*     */     
/* 177 */     if (Shaders.configAntialiasingLevel > 0)
/*     */     {
/* 179 */       addMacroLine(stringbuilder, "MC_FXAA_LEVEL", Shaders.configAntialiasingLevel);
/*     */     }
/*     */     
/* 182 */     if (Shaders.configNormalMap)
/*     */     {
/* 184 */       addMacroLine(stringbuilder, "MC_NORMAL_MAP");
/*     */     }
/*     */     
/* 187 */     if (Shaders.configSpecularMap)
/*     */     {
/* 189 */       addMacroLine(stringbuilder, "MC_SPECULAR_MAP");
/*     */     }
/*     */     
/* 192 */     addMacroLine(stringbuilder, "MC_RENDER_QUALITY", Shaders.configRenderResMul);
/* 193 */     addMacroLine(stringbuilder, "MC_SHADOW_QUALITY", Shaders.configShadowResMul);
/* 194 */     addMacroLine(stringbuilder, "MC_HAND_DEPTH", Shaders.configHandDepthMul);
/*     */     
/* 196 */     if (Shaders.isOldHandLight())
/*     */     {
/* 198 */       addMacroLine(stringbuilder, "MC_OLD_HAND_LIGHT");
/*     */     }
/*     */     
/* 201 */     if (Shaders.isOldLighting())
/*     */     {
/* 203 */       addMacroLine(stringbuilder, "MC_OLD_LIGHTING");
/*     */     }
/*     */     
/* 206 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addMacroLine(StringBuilder sb, String name, int value) {
/* 211 */     sb.append("#define ");
/* 212 */     sb.append(name);
/* 213 */     sb.append(" ");
/* 214 */     sb.append(value);
/* 215 */     sb.append("\n");
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addMacroLine(StringBuilder sb, String name, float value) {
/* 220 */     sb.append("#define ");
/* 221 */     sb.append(name);
/* 222 */     sb.append(" ");
/* 223 */     sb.append(value);
/* 224 */     sb.append("\n");
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addMacroLine(StringBuilder sb, String name) {
/* 229 */     sb.append("#define ");
/* 230 */     sb.append(name);
/* 231 */     sb.append("\n");
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderMacros.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */