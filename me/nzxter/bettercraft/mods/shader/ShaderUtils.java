/*     */ package me.nzxter.bettercraft.mods.shader;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ public class ShaderUtils
/*     */ {
/*     */   private static final String VERTEX_SHADER = "#version 130\n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}";
/*  14 */   private Minecraft mc = Minecraft.getMinecraft();
/*  15 */   private int program = GL20.glCreateProgram();
/*  16 */   private long startTime = System.currentTimeMillis();
/*  17 */   float mousemove = 0.01F;
/*     */   
/*     */   public ShaderUtils(String fragment) {
/*  20 */     initShader(fragment);
/*     */   }
/*     */   
/*     */   private void initShader(String frag) {
/*  24 */     int vertex = GL20.glCreateShader(35633);
/*  25 */     int fragment = GL20.glCreateShader(35632);
/*  26 */     GL20.glShaderSource(vertex, "#version 130\n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}");
/*  27 */     GL20.glShaderSource(fragment, frag);
/*  28 */     GL20.glValidateProgram(this.program);
/*  29 */     GL20.glCompileShader(vertex);
/*  30 */     GL20.glCompileShader(fragment);
/*  31 */     GL20.glAttachShader(this.program, vertex);
/*  32 */     GL20.glAttachShader(this.program, fragment);
/*  33 */     GL20.glLinkProgram(this.program);
/*     */   }
/*     */   
/*     */   public void renderFirst() {
/*  37 */     GL11.glClear(16640);
/*  38 */     GL20.glUseProgram(this.program);
/*     */   }
/*     */   
/*     */   public void renderSecond() {
/*  42 */     GL11.glEnable(3042);
/*  43 */     GL11.glBlendFunc(770, 771);
/*  44 */     ScaledResolution sr = new ScaledResolution(this.mc);
/*  45 */     GL11.glBegin(7);
/*  46 */     GL11.glTexCoord2d(0.0D, 1.0D);
/*  47 */     GL11.glVertex2d(0.0D, 0.0D);
/*  48 */     GL11.glTexCoord2d(0.0D, 0.0D);
/*  49 */     GL11.glVertex2d(0.0D, ScaledResolution.getScaledHeight());
/*  50 */     GL11.glTexCoord2d(1.0D, 0.0D);
/*  51 */     GL11.glVertex2d(ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
/*  52 */     GL11.glTexCoord2d(1.0D, 1.0D);
/*  53 */     GL11.glVertex2d(ScaledResolution.getScaledWidth(), 0.0D);
/*  54 */     GL11.glEnd();
/*  55 */     GL20.glUseProgram(0);
/*     */   }
/*     */   
/*     */   public void bind() {
/*  59 */     GL20.glUseProgram(this.program);
/*     */   }
/*     */   
/*     */   public int getProgram() {
/*  63 */     return this.program;
/*     */   }
/*     */   
/*     */   public ShaderUtils uniform1i(String loc, int i) {
/*  67 */     GL20.glUniform1i(GL20.glGetUniformLocation(this.program, loc), i);
/*  68 */     return this;
/*     */   }
/*     */   
/*     */   public ShaderUtils uniform2i(String loc, int i, int i1) {
/*  72 */     GL20.glUniform2i(GL20.glGetUniformLocation(this.program, loc), i, i1);
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   public ShaderUtils uniform3i(String loc, int i, int i1, int i2) {
/*  77 */     GL20.glUniform3i(GL20.glGetUniformLocation(this.program, loc), i, i1, i2);
/*  78 */     return this;
/*     */   }
/*     */   
/*     */   public ShaderUtils uniform4i(String loc, int i, int i1, int i2, int i3) {
/*  82 */     GL20.glUniform4i(GL20.glGetUniformLocation(this.program, loc), i, i1, i2, i3);
/*  83 */     return this;
/*     */   }
/*     */   
/*     */   public ShaderUtils uniform1f(String loc, float f) {
/*  87 */     GL20.glUniform1f(GL20.glGetUniformLocation(this.program, loc), f);
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   public ShaderUtils uniform2f(String loc, float f, float f1) {
/*  92 */     GL20.glUniform2f(GL20.glGetUniformLocation(this.program, loc), f, f1);
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public ShaderUtils uniform3f(String loc, float f, float f1, float f2) {
/*  97 */     GL20.glUniform3f(GL20.glGetUniformLocation(this.program, loc), f, f1, f2);
/*  98 */     return this;
/*     */   }
/*     */   
/*     */   public ShaderUtils uniform4f(String loc, float f, float f1, float f2, float f3) {
/* 102 */     GL20.glUniform4f(GL20.glGetUniformLocation(this.program, loc), f, f1, f2, f3);
/* 103 */     return this;
/*     */   }
/*     */   
/*     */   public ShaderUtils uniform1b(String loc, boolean b) {
/* 107 */     GL20.glUniform1i(GL20.glGetUniformLocation(this.program, loc), b ? 1 : 0);
/* 108 */     return this;
/*     */   }
/*     */   
/*     */   public void addDefaultUniforms(boolean detectmouse) {
/* 112 */     this.mousemove = (Mouse.getX() > 957) ? (this.mousemove -= 0.002F) : (this.mousemove += 0.002F);
/* 113 */     float n3 = Mouse.getX() / this.mc.displayWidth;
/* 114 */     float n4 = Mouse.getY() / this.mc.displayHeight;
/* 115 */     FloatBuffer floatBuffer3 = BufferUtils.createFloatBuffer(2);
/* 116 */     floatBuffer3.position(0);
/* 117 */     floatBuffer3.put(n3);
/* 118 */     floatBuffer3.put(n4);
/* 119 */     floatBuffer3.flip();
/* 120 */     GL20.glUniform2f(GL20.glGetUniformLocation(this.program, "resolution"), this.mc.displayWidth, this.mc.displayHeight);
/* 121 */     float time = (float)(System.currentTimeMillis() - this.startTime) / 1000.0F;
/* 122 */     GL20.glUniform1f(GL20.glGetUniformLocation(this.program, "time"), time);
/* 123 */     if (detectmouse) {
/* 124 */       GL20.glUniform2f(GL20.glGetUniformLocation(this.program, "mouse"), this.mousemove, 0.0F);
/*     */     } else {
/* 126 */       GL20.glUniform2(GL20.glGetUniformLocation(this.program, "mouse"), floatBuffer3);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\shader\ShaderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */