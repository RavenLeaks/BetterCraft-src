/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.util.vector.Matrix4f;
/*     */ 
/*     */ public class ShaderUniform
/*     */ {
/*  13 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private int uniformLocation;
/*     */   private final int uniformCount;
/*     */   private final int uniformType;
/*     */   private final IntBuffer uniformIntBuffer;
/*     */   private final FloatBuffer uniformFloatBuffer;
/*     */   private final String shaderName;
/*     */   private boolean dirty;
/*     */   private final ShaderManager shaderManager;
/*     */   
/*     */   public ShaderUniform(String name, int type, int count, ShaderManager manager) {
/*  25 */     this.shaderName = name;
/*  26 */     this.uniformCount = count;
/*  27 */     this.uniformType = type;
/*  28 */     this.shaderManager = manager;
/*     */     
/*  30 */     if (type <= 3) {
/*     */       
/*  32 */       this.uniformIntBuffer = BufferUtils.createIntBuffer(count);
/*  33 */       this.uniformFloatBuffer = null;
/*     */     }
/*     */     else {
/*     */       
/*  37 */       this.uniformIntBuffer = null;
/*  38 */       this.uniformFloatBuffer = BufferUtils.createFloatBuffer(count);
/*     */     } 
/*     */     
/*  41 */     this.uniformLocation = -1;
/*  42 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   private void markDirty() {
/*  47 */     this.dirty = true;
/*     */     
/*  49 */     if (this.shaderManager != null)
/*     */     {
/*  51 */       this.shaderManager.markDirty();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static int parseType(String typeName) {
/*  57 */     int i = -1;
/*     */     
/*  59 */     if ("int".equals(typeName)) {
/*     */       
/*  61 */       i = 0;
/*     */     }
/*  63 */     else if ("float".equals(typeName)) {
/*     */       
/*  65 */       i = 4;
/*     */     }
/*  67 */     else if (typeName.startsWith("matrix")) {
/*     */       
/*  69 */       if (typeName.endsWith("2x2")) {
/*     */         
/*  71 */         i = 8;
/*     */       }
/*  73 */       else if (typeName.endsWith("3x3")) {
/*     */         
/*  75 */         i = 9;
/*     */       }
/*  77 */       else if (typeName.endsWith("4x4")) {
/*     */         
/*  79 */         i = 10;
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUniformLocation(int uniformLocationIn) {
/*  88 */     this.uniformLocation = uniformLocationIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getShaderName() {
/*  93 */     return this.shaderName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(float p_148090_1_) {
/*  98 */     this.uniformFloatBuffer.position(0);
/*  99 */     this.uniformFloatBuffer.put(0, p_148090_1_);
/* 100 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(float p_148087_1_, float p_148087_2_) {
/* 105 */     this.uniformFloatBuffer.position(0);
/* 106 */     this.uniformFloatBuffer.put(0, p_148087_1_);
/* 107 */     this.uniformFloatBuffer.put(1, p_148087_2_);
/* 108 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(float p_148095_1_, float p_148095_2_, float p_148095_3_) {
/* 113 */     this.uniformFloatBuffer.position(0);
/* 114 */     this.uniformFloatBuffer.put(0, p_148095_1_);
/* 115 */     this.uniformFloatBuffer.put(1, p_148095_2_);
/* 116 */     this.uniformFloatBuffer.put(2, p_148095_3_);
/* 117 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(float p_148081_1_, float p_148081_2_, float p_148081_3_, float p_148081_4_) {
/* 122 */     this.uniformFloatBuffer.position(0);
/* 123 */     this.uniformFloatBuffer.put(p_148081_1_);
/* 124 */     this.uniformFloatBuffer.put(p_148081_2_);
/* 125 */     this.uniformFloatBuffer.put(p_148081_3_);
/* 126 */     this.uniformFloatBuffer.put(p_148081_4_);
/* 127 */     this.uniformFloatBuffer.flip();
/* 128 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSafe(float p_148092_1_, float p_148092_2_, float p_148092_3_, float p_148092_4_) {
/* 133 */     this.uniformFloatBuffer.position(0);
/*     */     
/* 135 */     if (this.uniformType >= 4)
/*     */     {
/* 137 */       this.uniformFloatBuffer.put(0, p_148092_1_);
/*     */     }
/*     */     
/* 140 */     if (this.uniformType >= 5)
/*     */     {
/* 142 */       this.uniformFloatBuffer.put(1, p_148092_2_);
/*     */     }
/*     */     
/* 145 */     if (this.uniformType >= 6)
/*     */     {
/* 147 */       this.uniformFloatBuffer.put(2, p_148092_3_);
/*     */     }
/*     */     
/* 150 */     if (this.uniformType >= 7)
/*     */     {
/* 152 */       this.uniformFloatBuffer.put(3, p_148092_4_);
/*     */     }
/*     */     
/* 155 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int p_148083_1_, int p_148083_2_, int p_148083_3_, int p_148083_4_) {
/* 160 */     this.uniformIntBuffer.position(0);
/*     */     
/* 162 */     if (this.uniformType >= 0)
/*     */     {
/* 164 */       this.uniformIntBuffer.put(0, p_148083_1_);
/*     */     }
/*     */     
/* 167 */     if (this.uniformType >= 1)
/*     */     {
/* 169 */       this.uniformIntBuffer.put(1, p_148083_2_);
/*     */     }
/*     */     
/* 172 */     if (this.uniformType >= 2)
/*     */     {
/* 174 */       this.uniformIntBuffer.put(2, p_148083_3_);
/*     */     }
/*     */     
/* 177 */     if (this.uniformType >= 3)
/*     */     {
/* 179 */       this.uniformIntBuffer.put(3, p_148083_4_);
/*     */     }
/*     */     
/* 182 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(float[] p_148097_1_) {
/* 187 */     if (p_148097_1_.length < this.uniformCount) {
/*     */       
/* 189 */       LOGGER.warn("Uniform.set called with a too-small value array (expected {}, got {}). Ignoring.", Integer.valueOf(this.uniformCount), Integer.valueOf(p_148097_1_.length));
/*     */     }
/*     */     else {
/*     */       
/* 193 */       this.uniformFloatBuffer.position(0);
/* 194 */       this.uniformFloatBuffer.put(p_148097_1_);
/* 195 */       this.uniformFloatBuffer.position(0);
/* 196 */       markDirty();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
/* 202 */     this.uniformFloatBuffer.position(0);
/* 203 */     this.uniformFloatBuffer.put(0, m00);
/* 204 */     this.uniformFloatBuffer.put(1, m01);
/* 205 */     this.uniformFloatBuffer.put(2, m02);
/* 206 */     this.uniformFloatBuffer.put(3, m03);
/* 207 */     this.uniformFloatBuffer.put(4, m10);
/* 208 */     this.uniformFloatBuffer.put(5, m11);
/* 209 */     this.uniformFloatBuffer.put(6, m12);
/* 210 */     this.uniformFloatBuffer.put(7, m13);
/* 211 */     this.uniformFloatBuffer.put(8, m20);
/* 212 */     this.uniformFloatBuffer.put(9, m21);
/* 213 */     this.uniformFloatBuffer.put(10, m22);
/* 214 */     this.uniformFloatBuffer.put(11, m23);
/* 215 */     this.uniformFloatBuffer.put(12, m30);
/* 216 */     this.uniformFloatBuffer.put(13, m31);
/* 217 */     this.uniformFloatBuffer.put(14, m32);
/* 218 */     this.uniformFloatBuffer.put(15, m33);
/* 219 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(Matrix4f matrix) {
/* 224 */     set(matrix.m00, matrix.m01, matrix.m02, matrix.m03, matrix.m10, matrix.m11, matrix.m12, matrix.m13, matrix.m20, matrix.m21, matrix.m22, matrix.m23, matrix.m30, matrix.m31, matrix.m32, matrix.m33);
/*     */   }
/*     */ 
/*     */   
/*     */   public void upload() {
/* 229 */     if (!this.dirty);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     this.dirty = false;
/*     */     
/* 236 */     if (this.uniformType <= 3) {
/*     */       
/* 238 */       uploadInt();
/*     */     }
/* 240 */     else if (this.uniformType <= 7) {
/*     */       
/* 242 */       uploadFloat();
/*     */     }
/*     */     else {
/*     */       
/* 246 */       if (this.uniformType > 10) {
/*     */         
/* 248 */         LOGGER.warn("Uniform.upload called, but type value ({}) is not a valid type. Ignoring.", Integer.valueOf(this.uniformType));
/*     */         
/*     */         return;
/*     */       } 
/* 252 */       uploadFloatMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void uploadInt() {
/* 258 */     switch (this.uniformType) {
/*     */       
/*     */       case 0:
/* 261 */         OpenGlHelper.glUniform1(this.uniformLocation, this.uniformIntBuffer);
/*     */         return;
/*     */       
/*     */       case 1:
/* 265 */         OpenGlHelper.glUniform2(this.uniformLocation, this.uniformIntBuffer);
/*     */         return;
/*     */       
/*     */       case 2:
/* 269 */         OpenGlHelper.glUniform3(this.uniformLocation, this.uniformIntBuffer);
/*     */         return;
/*     */       
/*     */       case 3:
/* 273 */         OpenGlHelper.glUniform4(this.uniformLocation, this.uniformIntBuffer);
/*     */         return;
/*     */     } 
/*     */     
/* 277 */     LOGGER.warn("Uniform.upload called, but count value ({}) is  not in the range of 1 to 4. Ignoring.", Integer.valueOf(this.uniformCount));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void uploadFloat() {
/* 283 */     switch (this.uniformType) {
/*     */       
/*     */       case 4:
/* 286 */         OpenGlHelper.glUniform1(this.uniformLocation, this.uniformFloatBuffer);
/*     */         return;
/*     */       
/*     */       case 5:
/* 290 */         OpenGlHelper.glUniform2(this.uniformLocation, this.uniformFloatBuffer);
/*     */         return;
/*     */       
/*     */       case 6:
/* 294 */         OpenGlHelper.glUniform3(this.uniformLocation, this.uniformFloatBuffer);
/*     */         return;
/*     */       
/*     */       case 7:
/* 298 */         OpenGlHelper.glUniform4(this.uniformLocation, this.uniformFloatBuffer);
/*     */         return;
/*     */     } 
/*     */     
/* 302 */     LOGGER.warn("Uniform.upload called, but count value ({}) is not in the range of 1 to 4. Ignoring.", Integer.valueOf(this.uniformCount));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void uploadFloatMatrix() {
/* 308 */     switch (this.uniformType) {
/*     */       
/*     */       case 8:
/* 311 */         OpenGlHelper.glUniformMatrix2(this.uniformLocation, true, this.uniformFloatBuffer);
/*     */         break;
/*     */       
/*     */       case 9:
/* 315 */         OpenGlHelper.glUniformMatrix3(this.uniformLocation, true, this.uniformFloatBuffer);
/*     */         break;
/*     */       
/*     */       case 10:
/* 319 */         OpenGlHelper.glUniformMatrix4(this.uniformLocation, true, this.uniformFloatBuffer);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\shader\ShaderUniform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */