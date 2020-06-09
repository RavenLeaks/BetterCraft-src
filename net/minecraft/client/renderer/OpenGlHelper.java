/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.net.URI;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.util.Locale;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.util.Util;
/*      */ import optifine.Config;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.Sys;
/*      */ import org.lwjgl.opengl.ARBFramebufferObject;
/*      */ import org.lwjgl.opengl.ARBMultitexture;
/*      */ import org.lwjgl.opengl.ARBShaderObjects;
/*      */ import org.lwjgl.opengl.ARBVertexBufferObject;
/*      */ import org.lwjgl.opengl.ARBVertexShader;
/*      */ import org.lwjgl.opengl.ContextCapabilities;
/*      */ import org.lwjgl.opengl.EXTBlendFuncSeparate;
/*      */ import org.lwjgl.opengl.EXTFramebufferObject;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL13;
/*      */ import org.lwjgl.opengl.GL14;
/*      */ import org.lwjgl.opengl.GL15;
/*      */ import org.lwjgl.opengl.GL20;
/*      */ import org.lwjgl.opengl.GL30;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import oshi.SystemInfo;
/*      */ import oshi.hardware.Processor;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class OpenGlHelper
/*      */ {
/*   40 */   private static final Logger LOGGER = LogManager.getLogger();
/*      */   
/*      */   public static boolean nvidia;
/*      */   
/*      */   public static boolean ati;
/*      */   
/*      */   public static int GL_FRAMEBUFFER;
/*      */   
/*      */   public static int GL_RENDERBUFFER;
/*      */   
/*      */   public static int GL_COLOR_ATTACHMENT0;
/*      */   
/*      */   public static int GL_DEPTH_ATTACHMENT;
/*      */   
/*      */   public static int GL_FRAMEBUFFER_COMPLETE;
/*      */   
/*      */   public static int GL_FB_INCOMPLETE_ATTACHMENT;
/*      */   
/*      */   public static int GL_FB_INCOMPLETE_MISS_ATTACH;
/*      */   
/*      */   public static int GL_FB_INCOMPLETE_DRAW_BUFFER;
/*      */   public static int GL_FB_INCOMPLETE_READ_BUFFER;
/*      */   private static FboMode framebufferType;
/*      */   public static boolean framebufferSupported;
/*      */   private static boolean shadersAvailable;
/*      */   private static boolean arbShaders;
/*      */   public static int GL_LINK_STATUS;
/*      */   public static int GL_COMPILE_STATUS;
/*      */   public static int GL_VERTEX_SHADER;
/*      */   public static int GL_FRAGMENT_SHADER;
/*      */   private static boolean arbMultitexture;
/*      */   public static int defaultTexUnit;
/*      */   public static int lightmapTexUnit;
/*      */   public static int GL_TEXTURE2;
/*      */   private static boolean arbTextureEnvCombine;
/*      */   public static int GL_COMBINE;
/*      */   public static int GL_INTERPOLATE;
/*      */   public static int GL_PRIMARY_COLOR;
/*      */   public static int GL_CONSTANT;
/*      */   public static int GL_PREVIOUS;
/*      */   public static int GL_COMBINE_RGB;
/*      */   public static int GL_SOURCE0_RGB;
/*      */   public static int GL_SOURCE1_RGB;
/*      */   public static int GL_SOURCE2_RGB;
/*      */   public static int GL_OPERAND0_RGB;
/*      */   public static int GL_OPERAND1_RGB;
/*      */   public static int GL_OPERAND2_RGB;
/*      */   public static int GL_COMBINE_ALPHA;
/*      */   public static int GL_SOURCE0_ALPHA;
/*      */   public static int GL_SOURCE1_ALPHA;
/*      */   public static int GL_SOURCE2_ALPHA;
/*      */   public static int GL_OPERAND0_ALPHA;
/*      */   public static int GL_OPERAND1_ALPHA;
/*      */   public static int GL_OPERAND2_ALPHA;
/*      */   private static boolean openGL14;
/*      */   public static boolean extBlendFuncSeparate;
/*      */   public static boolean openGL21;
/*      */   public static boolean shadersSupported;
/*   98 */   private static String logText = "";
/*      */   private static String cpu;
/*      */   public static boolean vboSupported;
/*      */   public static boolean vboSupportedAti;
/*      */   private static boolean arbVbo;
/*      */   public static int GL_ARRAY_BUFFER;
/*      */   public static int GL_STATIC_DRAW;
/*  105 */   public static float lastBrightnessX = 0.0F;
/*  106 */   public static float lastBrightnessY = 0.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void initializeTextures() {
/*  113 */     Config.initDisplay();
/*  114 */     ContextCapabilities contextcapabilities = GLContext.getCapabilities();
/*  115 */     arbMultitexture = (contextcapabilities.GL_ARB_multitexture && !contextcapabilities.OpenGL13);
/*  116 */     arbTextureEnvCombine = (contextcapabilities.GL_ARB_texture_env_combine && !contextcapabilities.OpenGL13);
/*      */     
/*  118 */     if (arbMultitexture) {
/*      */       
/*  120 */       logText = String.valueOf(logText) + "Using ARB_multitexture.\n";
/*  121 */       defaultTexUnit = 33984;
/*  122 */       lightmapTexUnit = 33985;
/*  123 */       GL_TEXTURE2 = 33986;
/*      */     }
/*      */     else {
/*      */       
/*  127 */       logText = String.valueOf(logText) + "Using GL 1.3 multitexturing.\n";
/*  128 */       defaultTexUnit = 33984;
/*  129 */       lightmapTexUnit = 33985;
/*  130 */       GL_TEXTURE2 = 33986;
/*      */     } 
/*      */     
/*  133 */     if (arbTextureEnvCombine) {
/*      */       
/*  135 */       logText = String.valueOf(logText) + "Using ARB_texture_env_combine.\n";
/*  136 */       GL_COMBINE = 34160;
/*  137 */       GL_INTERPOLATE = 34165;
/*  138 */       GL_PRIMARY_COLOR = 34167;
/*  139 */       GL_CONSTANT = 34166;
/*  140 */       GL_PREVIOUS = 34168;
/*  141 */       GL_COMBINE_RGB = 34161;
/*  142 */       GL_SOURCE0_RGB = 34176;
/*  143 */       GL_SOURCE1_RGB = 34177;
/*  144 */       GL_SOURCE2_RGB = 34178;
/*  145 */       GL_OPERAND0_RGB = 34192;
/*  146 */       GL_OPERAND1_RGB = 34193;
/*  147 */       GL_OPERAND2_RGB = 34194;
/*  148 */       GL_COMBINE_ALPHA = 34162;
/*  149 */       GL_SOURCE0_ALPHA = 34184;
/*  150 */       GL_SOURCE1_ALPHA = 34185;
/*  151 */       GL_SOURCE2_ALPHA = 34186;
/*  152 */       GL_OPERAND0_ALPHA = 34200;
/*  153 */       GL_OPERAND1_ALPHA = 34201;
/*  154 */       GL_OPERAND2_ALPHA = 34202;
/*      */     }
/*      */     else {
/*      */       
/*  158 */       logText = String.valueOf(logText) + "Using GL 1.3 texture combiners.\n";
/*  159 */       GL_COMBINE = 34160;
/*  160 */       GL_INTERPOLATE = 34165;
/*  161 */       GL_PRIMARY_COLOR = 34167;
/*  162 */       GL_CONSTANT = 34166;
/*  163 */       GL_PREVIOUS = 34168;
/*  164 */       GL_COMBINE_RGB = 34161;
/*  165 */       GL_SOURCE0_RGB = 34176;
/*  166 */       GL_SOURCE1_RGB = 34177;
/*  167 */       GL_SOURCE2_RGB = 34178;
/*  168 */       GL_OPERAND0_RGB = 34192;
/*  169 */       GL_OPERAND1_RGB = 34193;
/*  170 */       GL_OPERAND2_RGB = 34194;
/*  171 */       GL_COMBINE_ALPHA = 34162;
/*  172 */       GL_SOURCE0_ALPHA = 34184;
/*  173 */       GL_SOURCE1_ALPHA = 34185;
/*  174 */       GL_SOURCE2_ALPHA = 34186;
/*  175 */       GL_OPERAND0_ALPHA = 34200;
/*  176 */       GL_OPERAND1_ALPHA = 34201;
/*  177 */       GL_OPERAND2_ALPHA = 34202;
/*      */     } 
/*      */     
/*  180 */     extBlendFuncSeparate = (contextcapabilities.GL_EXT_blend_func_separate && !contextcapabilities.OpenGL14);
/*  181 */     openGL14 = !(!contextcapabilities.OpenGL14 && !contextcapabilities.GL_EXT_blend_func_separate);
/*  182 */     framebufferSupported = (openGL14 && (contextcapabilities.GL_ARB_framebuffer_object || contextcapabilities.GL_EXT_framebuffer_object || contextcapabilities.OpenGL30));
/*      */     
/*  184 */     if (framebufferSupported) {
/*      */       
/*  186 */       logText = String.valueOf(logText) + "Using framebuffer objects because ";
/*      */       
/*  188 */       if (contextcapabilities.OpenGL30)
/*      */       {
/*  190 */         logText = String.valueOf(logText) + "OpenGL 3.0 is supported and separate blending is supported.\n";
/*  191 */         framebufferType = FboMode.BASE;
/*  192 */         GL_FRAMEBUFFER = 36160;
/*  193 */         GL_RENDERBUFFER = 36161;
/*  194 */         GL_COLOR_ATTACHMENT0 = 36064;
/*  195 */         GL_DEPTH_ATTACHMENT = 36096;
/*  196 */         GL_FRAMEBUFFER_COMPLETE = 36053;
/*  197 */         GL_FB_INCOMPLETE_ATTACHMENT = 36054;
/*  198 */         GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
/*  199 */         GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
/*  200 */         GL_FB_INCOMPLETE_READ_BUFFER = 36060;
/*      */       }
/*  202 */       else if (contextcapabilities.GL_ARB_framebuffer_object)
/*      */       {
/*  204 */         logText = String.valueOf(logText) + "ARB_framebuffer_object is supported and separate blending is supported.\n";
/*  205 */         framebufferType = FboMode.ARB;
/*  206 */         GL_FRAMEBUFFER = 36160;
/*  207 */         GL_RENDERBUFFER = 36161;
/*  208 */         GL_COLOR_ATTACHMENT0 = 36064;
/*  209 */         GL_DEPTH_ATTACHMENT = 36096;
/*  210 */         GL_FRAMEBUFFER_COMPLETE = 36053;
/*  211 */         GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
/*  212 */         GL_FB_INCOMPLETE_ATTACHMENT = 36054;
/*  213 */         GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
/*  214 */         GL_FB_INCOMPLETE_READ_BUFFER = 36060;
/*      */       }
/*  216 */       else if (contextcapabilities.GL_EXT_framebuffer_object)
/*      */       {
/*  218 */         logText = String.valueOf(logText) + "EXT_framebuffer_object is supported.\n";
/*  219 */         framebufferType = FboMode.EXT;
/*  220 */         GL_FRAMEBUFFER = 36160;
/*  221 */         GL_RENDERBUFFER = 36161;
/*  222 */         GL_COLOR_ATTACHMENT0 = 36064;
/*  223 */         GL_DEPTH_ATTACHMENT = 36096;
/*  224 */         GL_FRAMEBUFFER_COMPLETE = 36053;
/*  225 */         GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
/*  226 */         GL_FB_INCOMPLETE_ATTACHMENT = 36054;
/*  227 */         GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
/*  228 */         GL_FB_INCOMPLETE_READ_BUFFER = 36060;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  233 */       logText = String.valueOf(logText) + "Not using framebuffer objects because ";
/*  234 */       logText = String.valueOf(logText) + "OpenGL 1.4 is " + (contextcapabilities.OpenGL14 ? "" : "not ") + "supported, ";
/*  235 */       logText = String.valueOf(logText) + "EXT_blend_func_separate is " + (contextcapabilities.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ";
/*  236 */       logText = String.valueOf(logText) + "OpenGL 3.0 is " + (contextcapabilities.OpenGL30 ? "" : "not ") + "supported, ";
/*  237 */       logText = String.valueOf(logText) + "ARB_framebuffer_object is " + (contextcapabilities.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ";
/*  238 */       logText = String.valueOf(logText) + "EXT_framebuffer_object is " + (contextcapabilities.GL_EXT_framebuffer_object ? "" : "not ") + "supported.\n";
/*      */     } 
/*      */     
/*  241 */     openGL21 = contextcapabilities.OpenGL21;
/*  242 */     shadersAvailable = !(!openGL21 && (!contextcapabilities.GL_ARB_vertex_shader || !contextcapabilities.GL_ARB_fragment_shader || !contextcapabilities.GL_ARB_shader_objects));
/*  243 */     logText = String.valueOf(logText) + "Shaders are " + (shadersAvailable ? "" : "not ") + "available because ";
/*      */     
/*  245 */     if (shadersAvailable) {
/*      */       
/*  247 */       if (contextcapabilities.OpenGL21)
/*      */       {
/*  249 */         logText = String.valueOf(logText) + "OpenGL 2.1 is supported.\n";
/*  250 */         arbShaders = false;
/*  251 */         GL_LINK_STATUS = 35714;
/*  252 */         GL_COMPILE_STATUS = 35713;
/*  253 */         GL_VERTEX_SHADER = 35633;
/*  254 */         GL_FRAGMENT_SHADER = 35632;
/*      */       }
/*      */       else
/*      */       {
/*  258 */         logText = String.valueOf(logText) + "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n";
/*  259 */         arbShaders = true;
/*  260 */         GL_LINK_STATUS = 35714;
/*  261 */         GL_COMPILE_STATUS = 35713;
/*  262 */         GL_VERTEX_SHADER = 35633;
/*  263 */         GL_FRAGMENT_SHADER = 35632;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  268 */       logText = String.valueOf(logText) + "OpenGL 2.1 is " + (contextcapabilities.OpenGL21 ? "" : "not ") + "supported, ";
/*  269 */       logText = String.valueOf(logText) + "ARB_shader_objects is " + (contextcapabilities.GL_ARB_shader_objects ? "" : "not ") + "supported, ";
/*  270 */       logText = String.valueOf(logText) + "ARB_vertex_shader is " + (contextcapabilities.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ";
/*  271 */       logText = String.valueOf(logText) + "ARB_fragment_shader is " + (contextcapabilities.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n";
/*      */     } 
/*      */     
/*  274 */     shadersSupported = (framebufferSupported && shadersAvailable);
/*  275 */     String s = GL11.glGetString(7936).toLowerCase(Locale.ROOT);
/*  276 */     nvidia = s.contains("nvidia");
/*  277 */     arbVbo = (!contextcapabilities.OpenGL15 && contextcapabilities.GL_ARB_vertex_buffer_object);
/*  278 */     vboSupported = !(!contextcapabilities.OpenGL15 && !arbVbo);
/*  279 */     logText = String.valueOf(logText) + "VBOs are " + (vboSupported ? "" : "not ") + "available because ";
/*      */     
/*  281 */     if (vboSupported)
/*      */     {
/*  283 */       if (arbVbo) {
/*      */         
/*  285 */         logText = String.valueOf(logText) + "ARB_vertex_buffer_object is supported.\n";
/*  286 */         GL_STATIC_DRAW = 35044;
/*  287 */         GL_ARRAY_BUFFER = 34962;
/*      */       }
/*      */       else {
/*      */         
/*  291 */         logText = String.valueOf(logText) + "OpenGL 1.5 is supported.\n";
/*  292 */         GL_STATIC_DRAW = 35044;
/*  293 */         GL_ARRAY_BUFFER = 34962;
/*      */       } 
/*      */     }
/*      */     
/*  297 */     ati = s.contains("ati");
/*      */     
/*  299 */     if (ati)
/*      */     {
/*  301 */       if (vboSupported) {
/*      */         
/*  303 */         vboSupportedAti = true;
/*      */       }
/*      */       else {
/*      */         
/*  307 */         GameSettings.Options.RENDER_DISTANCE.setValueMax(16.0F);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  313 */       Processor[] aprocessor = (new SystemInfo()).getHardware().getProcessors();
/*  314 */       cpu = String.format("%dx %s", new Object[] { Integer.valueOf(aprocessor.length), aprocessor[0] }).replaceAll("\\s+", " ");
/*      */     }
/*  316 */     catch (Throwable throwable) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean areShadersSupported() {
/*  324 */     return shadersSupported;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getLogText() {
/*  329 */     return logText;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetProgrami(int program, int pname) {
/*  334 */     return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(program, pname) : GL20.glGetProgrami(program, pname);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glAttachShader(int program, int shaderIn) {
/*  339 */     if (arbShaders) {
/*      */       
/*  341 */       ARBShaderObjects.glAttachObjectARB(program, shaderIn);
/*      */     }
/*      */     else {
/*      */       
/*  345 */       GL20.glAttachShader(program, shaderIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDeleteShader(int shaderIn) {
/*  351 */     if (arbShaders) {
/*      */       
/*  353 */       ARBShaderObjects.glDeleteObjectARB(shaderIn);
/*      */     }
/*      */     else {
/*      */       
/*  357 */       GL20.glDeleteShader(shaderIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int glCreateShader(int type) {
/*  366 */     return arbShaders ? ARBShaderObjects.glCreateShaderObjectARB(type) : GL20.glCreateShader(type);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glShaderSource(int shaderIn, ByteBuffer string) {
/*  371 */     if (arbShaders) {
/*      */       
/*  373 */       ARBShaderObjects.glShaderSourceARB(shaderIn, string);
/*      */     }
/*      */     else {
/*      */       
/*  377 */       GL20.glShaderSource(shaderIn, string);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glCompileShader(int shaderIn) {
/*  383 */     if (arbShaders) {
/*      */       
/*  385 */       ARBShaderObjects.glCompileShaderARB(shaderIn);
/*      */     }
/*      */     else {
/*      */       
/*  389 */       GL20.glCompileShader(shaderIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetShaderi(int shaderIn, int pname) {
/*  395 */     return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(shaderIn, pname) : GL20.glGetShaderi(shaderIn, pname);
/*      */   }
/*      */ 
/*      */   
/*      */   public static String glGetShaderInfoLog(int shaderIn, int maxLength) {
/*  400 */     return arbShaders ? ARBShaderObjects.glGetInfoLogARB(shaderIn, maxLength) : GL20.glGetShaderInfoLog(shaderIn, maxLength);
/*      */   }
/*      */ 
/*      */   
/*      */   public static String glGetProgramInfoLog(int program, int maxLength) {
/*  405 */     return arbShaders ? ARBShaderObjects.glGetInfoLogARB(program, maxLength) : GL20.glGetProgramInfoLog(program, maxLength);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUseProgram(int program) {
/*  410 */     if (arbShaders) {
/*      */       
/*  412 */       ARBShaderObjects.glUseProgramObjectARB(program);
/*      */     }
/*      */     else {
/*      */       
/*  416 */       GL20.glUseProgram(program);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glCreateProgram() {
/*  422 */     return arbShaders ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDeleteProgram(int program) {
/*  427 */     if (arbShaders) {
/*      */       
/*  429 */       ARBShaderObjects.glDeleteObjectARB(program);
/*      */     }
/*      */     else {
/*      */       
/*  433 */       GL20.glDeleteProgram(program);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glLinkProgram(int program) {
/*  439 */     if (arbShaders) {
/*      */       
/*  441 */       ARBShaderObjects.glLinkProgramARB(program);
/*      */     }
/*      */     else {
/*      */       
/*  445 */       GL20.glLinkProgram(program);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetUniformLocation(int programObj, CharSequence name) {
/*  451 */     return arbShaders ? ARBShaderObjects.glGetUniformLocationARB(programObj, name) : GL20.glGetUniformLocation(programObj, name);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform1(int location, IntBuffer values) {
/*  456 */     if (arbShaders) {
/*      */       
/*  458 */       ARBShaderObjects.glUniform1ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  462 */       GL20.glUniform1(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform1i(int location, int v0) {
/*  468 */     if (arbShaders) {
/*      */       
/*  470 */       ARBShaderObjects.glUniform1iARB(location, v0);
/*      */     }
/*      */     else {
/*      */       
/*  474 */       GL20.glUniform1i(location, v0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform1(int location, FloatBuffer values) {
/*  480 */     if (arbShaders) {
/*      */       
/*  482 */       ARBShaderObjects.glUniform1ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  486 */       GL20.glUniform1(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform2(int location, IntBuffer values) {
/*  492 */     if (arbShaders) {
/*      */       
/*  494 */       ARBShaderObjects.glUniform2ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  498 */       GL20.glUniform2(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform2(int location, FloatBuffer values) {
/*  504 */     if (arbShaders) {
/*      */       
/*  506 */       ARBShaderObjects.glUniform2ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  510 */       GL20.glUniform2(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform3(int location, IntBuffer values) {
/*  516 */     if (arbShaders) {
/*      */       
/*  518 */       ARBShaderObjects.glUniform3ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  522 */       GL20.glUniform3(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform3(int location, FloatBuffer values) {
/*  528 */     if (arbShaders) {
/*      */       
/*  530 */       ARBShaderObjects.glUniform3ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  534 */       GL20.glUniform3(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform4(int location, IntBuffer values) {
/*  540 */     if (arbShaders) {
/*      */       
/*  542 */       ARBShaderObjects.glUniform4ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  546 */       GL20.glUniform4(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform4(int location, FloatBuffer values) {
/*  552 */     if (arbShaders) {
/*      */       
/*  554 */       ARBShaderObjects.glUniform4ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  558 */       GL20.glUniform4(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniformMatrix2(int location, boolean transpose, FloatBuffer matrices) {
/*  564 */     if (arbShaders) {
/*      */       
/*  566 */       ARBShaderObjects.glUniformMatrix2ARB(location, transpose, matrices);
/*      */     }
/*      */     else {
/*      */       
/*  570 */       GL20.glUniformMatrix2(location, transpose, matrices);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniformMatrix3(int location, boolean transpose, FloatBuffer matrices) {
/*  576 */     if (arbShaders) {
/*      */       
/*  578 */       ARBShaderObjects.glUniformMatrix3ARB(location, transpose, matrices);
/*      */     }
/*      */     else {
/*      */       
/*  582 */       GL20.glUniformMatrix3(location, transpose, matrices);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniformMatrix4(int location, boolean transpose, FloatBuffer matrices) {
/*  588 */     if (arbShaders) {
/*      */       
/*  590 */       ARBShaderObjects.glUniformMatrix4ARB(location, transpose, matrices);
/*      */     }
/*      */     else {
/*      */       
/*  594 */       GL20.glUniformMatrix4(location, transpose, matrices);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetAttribLocation(int program, CharSequence name) {
/*  600 */     return arbShaders ? ARBVertexShader.glGetAttribLocationARB(program, name) : GL20.glGetAttribLocation(program, name);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGenBuffers() {
/*  605 */     return arbVbo ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glBindBuffer(int target, int buffer) {
/*  610 */     if (arbVbo) {
/*      */       
/*  612 */       ARBVertexBufferObject.glBindBufferARB(target, buffer);
/*      */     }
/*      */     else {
/*      */       
/*  616 */       GL15.glBindBuffer(target, buffer);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glBufferData(int target, ByteBuffer data, int usage) {
/*  622 */     if (arbVbo) {
/*      */       
/*  624 */       ARBVertexBufferObject.glBufferDataARB(target, data, usage);
/*      */     }
/*      */     else {
/*      */       
/*  628 */       GL15.glBufferData(target, data, usage);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDeleteBuffers(int buffer) {
/*  634 */     if (arbVbo) {
/*      */       
/*  636 */       ARBVertexBufferObject.glDeleteBuffersARB(buffer);
/*      */     }
/*      */     else {
/*      */       
/*  640 */       GL15.glDeleteBuffers(buffer);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean useVbo() {
/*  646 */     if (Config.isMultiTexture())
/*      */     {
/*  648 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  652 */     return (vboSupported && (Minecraft.getMinecraft()).gameSettings.useVbo);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glBindFramebuffer(int target, int framebufferIn) {
/*  658 */     if (framebufferSupported)
/*      */     {
/*  660 */       switch (framebufferType) {
/*      */         
/*      */         case BASE:
/*  663 */           GL30.glBindFramebuffer(target, framebufferIn);
/*      */           break;
/*      */         
/*      */         case null:
/*  667 */           ARBFramebufferObject.glBindFramebuffer(target, framebufferIn);
/*      */           break;
/*      */         
/*      */         case EXT:
/*  671 */           EXTFramebufferObject.glBindFramebufferEXT(target, framebufferIn);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static void glBindRenderbuffer(int target, int renderbuffer) {
/*  678 */     if (framebufferSupported)
/*      */     {
/*  680 */       switch (framebufferType) {
/*      */         
/*      */         case BASE:
/*  683 */           GL30.glBindRenderbuffer(target, renderbuffer);
/*      */           break;
/*      */         
/*      */         case null:
/*  687 */           ARBFramebufferObject.glBindRenderbuffer(target, renderbuffer);
/*      */           break;
/*      */         
/*      */         case EXT:
/*  691 */           EXTFramebufferObject.glBindRenderbufferEXT(target, renderbuffer);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static void glDeleteRenderbuffers(int renderbuffer) {
/*  698 */     if (framebufferSupported)
/*      */     {
/*  700 */       switch (framebufferType) {
/*      */         
/*      */         case BASE:
/*  703 */           GL30.glDeleteRenderbuffers(renderbuffer);
/*      */           break;
/*      */         
/*      */         case null:
/*  707 */           ARBFramebufferObject.glDeleteRenderbuffers(renderbuffer);
/*      */           break;
/*      */         
/*      */         case EXT:
/*  711 */           EXTFramebufferObject.glDeleteRenderbuffersEXT(renderbuffer);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static void glDeleteFramebuffers(int framebufferIn) {
/*  718 */     if (framebufferSupported)
/*      */     {
/*  720 */       switch (framebufferType) {
/*      */         
/*      */         case BASE:
/*  723 */           GL30.glDeleteFramebuffers(framebufferIn);
/*      */           break;
/*      */         
/*      */         case null:
/*  727 */           ARBFramebufferObject.glDeleteFramebuffers(framebufferIn);
/*      */           break;
/*      */         
/*      */         case EXT:
/*  731 */           EXTFramebufferObject.glDeleteFramebuffersEXT(framebufferIn);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int glGenFramebuffers() {
/*  741 */     if (!framebufferSupported)
/*      */     {
/*  743 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  747 */     switch (framebufferType) {
/*      */       
/*      */       case BASE:
/*  750 */         return GL30.glGenFramebuffers();
/*      */       
/*      */       case null:
/*  753 */         return ARBFramebufferObject.glGenFramebuffers();
/*      */       
/*      */       case EXT:
/*  756 */         return EXTFramebufferObject.glGenFramebuffersEXT();
/*      */     } 
/*      */     
/*  759 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int glGenRenderbuffers() {
/*  766 */     if (!framebufferSupported)
/*      */     {
/*  768 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  772 */     switch (framebufferType) {
/*      */       
/*      */       case BASE:
/*  775 */         return GL30.glGenRenderbuffers();
/*      */       
/*      */       case null:
/*  778 */         return ARBFramebufferObject.glGenRenderbuffers();
/*      */       
/*      */       case EXT:
/*  781 */         return EXTFramebufferObject.glGenRenderbuffersEXT();
/*      */     } 
/*      */     
/*  784 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glRenderbufferStorage(int target, int internalFormat, int width, int height) {
/*  791 */     if (framebufferSupported)
/*      */     {
/*  793 */       switch (framebufferType) {
/*      */         
/*      */         case BASE:
/*  796 */           GL30.glRenderbufferStorage(target, internalFormat, width, height);
/*      */           break;
/*      */         
/*      */         case null:
/*  800 */           ARBFramebufferObject.glRenderbufferStorage(target, internalFormat, width, height);
/*      */           break;
/*      */         
/*      */         case EXT:
/*  804 */           EXTFramebufferObject.glRenderbufferStorageEXT(target, internalFormat, width, height);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static void glFramebufferRenderbuffer(int target, int attachment, int renderBufferTarget, int renderBuffer) {
/*  811 */     if (framebufferSupported)
/*      */     {
/*  813 */       switch (framebufferType) {
/*      */         
/*      */         case BASE:
/*  816 */           GL30.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
/*      */           break;
/*      */         
/*      */         case null:
/*  820 */           ARBFramebufferObject.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
/*      */           break;
/*      */         
/*      */         case EXT:
/*  824 */           EXTFramebufferObject.glFramebufferRenderbufferEXT(target, attachment, renderBufferTarget, renderBuffer);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static int glCheckFramebufferStatus(int target) {
/*  831 */     if (!framebufferSupported)
/*      */     {
/*  833 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  837 */     switch (framebufferType) {
/*      */       
/*      */       case BASE:
/*  840 */         return GL30.glCheckFramebufferStatus(target);
/*      */       
/*      */       case null:
/*  843 */         return ARBFramebufferObject.glCheckFramebufferStatus(target);
/*      */       
/*      */       case EXT:
/*  846 */         return EXTFramebufferObject.glCheckFramebufferStatusEXT(target);
/*      */     } 
/*      */     
/*  849 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
/*  856 */     if (framebufferSupported)
/*      */     {
/*  858 */       switch (framebufferType) {
/*      */         
/*      */         case BASE:
/*  861 */           GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
/*      */           break;
/*      */         
/*      */         case null:
/*  865 */           ARBFramebufferObject.glFramebufferTexture2D(target, attachment, textarget, texture, level);
/*      */           break;
/*      */         
/*      */         case EXT:
/*  869 */           EXTFramebufferObject.glFramebufferTexture2DEXT(target, attachment, textarget, texture, level);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setActiveTexture(int texture) {
/*  879 */     if (arbMultitexture) {
/*      */       
/*  881 */       ARBMultitexture.glActiveTextureARB(texture);
/*      */     }
/*      */     else {
/*      */       
/*  885 */       GL13.glActiveTexture(texture);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setClientActiveTexture(int texture) {
/*  894 */     if (arbMultitexture) {
/*      */       
/*  896 */       ARBMultitexture.glClientActiveTextureARB(texture);
/*      */     }
/*      */     else {
/*      */       
/*  900 */       GL13.glClientActiveTexture(texture);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setLightmapTextureCoords(int target, float p_77475_1_, float t) {
/*  909 */     if (arbMultitexture) {
/*      */       
/*  911 */       ARBMultitexture.glMultiTexCoord2fARB(target, p_77475_1_, t);
/*      */     }
/*      */     else {
/*      */       
/*  915 */       GL13.glMultiTexCoord2f(target, p_77475_1_, t);
/*      */     } 
/*      */     
/*  918 */     if (target == lightmapTexUnit) {
/*      */       
/*  920 */       lastBrightnessX = p_77475_1_;
/*  921 */       lastBrightnessY = t;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glBlendFunc(int sFactorRGB, int dFactorRGB, int sfactorAlpha, int dfactorAlpha) {
/*  927 */     if (openGL14) {
/*      */       
/*  929 */       if (extBlendFuncSeparate)
/*      */       {
/*  931 */         EXTBlendFuncSeparate.glBlendFuncSeparateEXT(sFactorRGB, dFactorRGB, sfactorAlpha, dfactorAlpha);
/*      */       }
/*      */       else
/*      */       {
/*  935 */         GL14.glBlendFuncSeparate(sFactorRGB, dFactorRGB, sfactorAlpha, dfactorAlpha);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  940 */       GL11.glBlendFunc(sFactorRGB, dFactorRGB);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFramebufferEnabled() {
/*  946 */     if (Config.isFastRender())
/*      */     {
/*  948 */       return false;
/*      */     }
/*  950 */     if (Config.isAntialiasing())
/*      */     {
/*  952 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  956 */     return (framebufferSupported && (Minecraft.getMinecraft()).gameSettings.fboEnable);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getCpu() {
/*  962 */     return (cpu == null) ? "<unknown>" : cpu;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderDirections(int p_188785_0_) {
/*  967 */     GlStateManager.disableTexture2D();
/*  968 */     GlStateManager.depthMask(false);
/*  969 */     Tessellator tessellator = Tessellator.getInstance();
/*  970 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  971 */     GL11.glLineWidth(4.0F);
/*  972 */     bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
/*  973 */     bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/*  974 */     bufferbuilder.pos(p_188785_0_, 0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/*  975 */     bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/*  976 */     bufferbuilder.pos(0.0D, p_188785_0_, 0.0D).color(0, 0, 0, 255).endVertex();
/*  977 */     bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/*  978 */     bufferbuilder.pos(0.0D, 0.0D, p_188785_0_).color(0, 0, 0, 255).endVertex();
/*  979 */     tessellator.draw();
/*  980 */     GL11.glLineWidth(2.0F);
/*  981 */     bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
/*  982 */     bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(255, 0, 0, 255).endVertex();
/*  983 */     bufferbuilder.pos(p_188785_0_, 0.0D, 0.0D).color(255, 0, 0, 255).endVertex();
/*  984 */     bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(0, 255, 0, 255).endVertex();
/*  985 */     bufferbuilder.pos(0.0D, p_188785_0_, 0.0D).color(0, 255, 0, 255).endVertex();
/*  986 */     bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(127, 127, 255, 255).endVertex();
/*  987 */     bufferbuilder.pos(0.0D, 0.0D, p_188785_0_).color(127, 127, 255, 255).endVertex();
/*  988 */     tessellator.draw();
/*  989 */     GL11.glLineWidth(1.0F);
/*  990 */     GlStateManager.depthMask(true);
/*  991 */     GlStateManager.enableTexture2D();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void openFile(File fileIn) {
/*  996 */     String s = fileIn.getAbsolutePath();
/*      */     
/*  998 */     if (Util.getOSType() == Util.EnumOS.OSX) {
/*      */ 
/*      */       
/*      */       try {
/* 1002 */         LOGGER.info(s);
/* 1003 */         Runtime.getRuntime().exec(new String[] { "/usr/bin/open", s });
/*      */         
/*      */         return;
/* 1006 */       } catch (IOException ioexception1) {
/*      */         
/* 1008 */         LOGGER.error("Couldn't open file", ioexception1);
/*      */       }
/*      */     
/* 1011 */     } else if (Util.getOSType() == Util.EnumOS.WINDOWS) {
/*      */       
/* 1013 */       String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { s });
/*      */ 
/*      */       
/*      */       try {
/* 1017 */         Runtime.getRuntime().exec(s1);
/*      */         
/*      */         return;
/* 1020 */       } catch (IOException ioexception) {
/*      */         
/* 1022 */         LOGGER.error("Couldn't open file", ioexception);
/*      */       } 
/*      */     } 
/*      */     
/* 1026 */     boolean flag = false;
/*      */ 
/*      */     
/*      */     try {
/* 1030 */       Class<?> oclass = Class.forName("java.awt.Desktop");
/* 1031 */       Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 1032 */       oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { fileIn.toURI() });
/*      */     }
/* 1034 */     catch (Throwable throwable1) {
/*      */       
/* 1036 */       LOGGER.error("Couldn't open link", throwable1);
/* 1037 */       flag = true;
/*      */     } 
/*      */     
/* 1040 */     if (flag) {
/*      */       
/* 1042 */       LOGGER.info("Opening via system class!");
/* 1043 */       Sys.openURL("file://" + s);
/*      */     } 
/*      */   }
/*      */   
/*      */   enum FboMode
/*      */   {
/* 1049 */     BASE,
/* 1050 */     ARB,
/* 1051 */     EXT;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\OpenGlHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */