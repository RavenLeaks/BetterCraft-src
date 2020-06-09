/*    */ package net.minecraft.client.shader;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.util.JsonException;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class ShaderLinkHelper
/*    */ {
/* 11 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/*    */   private static ShaderLinkHelper staticShaderLinkHelper;
/*    */   
/*    */   public static void setNewStaticShaderLinkHelper() {
/* 16 */     staticShaderLinkHelper = new ShaderLinkHelper();
/*    */   }
/*    */ 
/*    */   
/*    */   public static ShaderLinkHelper getStaticShaderLinkHelper() {
/* 21 */     return staticShaderLinkHelper;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteShader(ShaderManager manager) {
/* 26 */     manager.getFragmentShaderLoader().deleteShader(manager);
/* 27 */     manager.getVertexShaderLoader().deleteShader(manager);
/* 28 */     OpenGlHelper.glDeleteProgram(manager.getProgram());
/*    */   }
/*    */ 
/*    */   
/*    */   public int createProgram() throws JsonException {
/* 33 */     int i = OpenGlHelper.glCreateProgram();
/*    */     
/* 35 */     if (i <= 0)
/*    */     {
/* 37 */       throw new JsonException("Could not create shader program (returned program ID " + i + ")");
/*    */     }
/*    */ 
/*    */     
/* 41 */     return i;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void linkProgram(ShaderManager manager) throws IOException {
/* 47 */     manager.getFragmentShaderLoader().attachShader(manager);
/* 48 */     manager.getVertexShaderLoader().attachShader(manager);
/* 49 */     OpenGlHelper.glLinkProgram(manager.getProgram());
/* 50 */     int i = OpenGlHelper.glGetProgrami(manager.getProgram(), OpenGlHelper.GL_LINK_STATUS);
/*    */     
/* 52 */     if (i == 0) {
/*    */       
/* 54 */       LOGGER.warn("Error encountered when linking program containing VS {} and FS {}. Log output:", manager.getVertexShaderLoader().getShaderFilename(), manager.getFragmentShaderLoader().getShaderFilename());
/* 55 */       LOGGER.warn(OpenGlHelper.glGetProgramInfoLog(manager.getProgram(), 32768));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\shader\ShaderLinkHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */