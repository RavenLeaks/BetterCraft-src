/*    */ package shadersmod.client;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class HFNoiseTexture
/*    */   implements ICustomTexture {
/* 10 */   private int texID = GL11.glGenTextures();
/* 11 */   private int textureUnit = 15;
/*    */ 
/*    */   
/*    */   public HFNoiseTexture(int width, int height) {
/* 15 */     byte[] abyte = genHFNoiseImage(width, height);
/* 16 */     ByteBuffer bytebuffer = BufferUtils.createByteBuffer(abyte.length);
/* 17 */     bytebuffer.put(abyte);
/* 18 */     bytebuffer.flip();
/* 19 */     GlStateManager.bindTexture(this.texID);
/* 20 */     GL11.glTexImage2D(3553, 0, 6407, width, height, 0, 6407, 5121, bytebuffer);
/* 21 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 22 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 23 */     GL11.glTexParameteri(3553, 10240, 9729);
/* 24 */     GL11.glTexParameteri(3553, 10241, 9729);
/* 25 */     GlStateManager.bindTexture(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getID() {
/* 30 */     return this.texID;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteTexture() {
/* 35 */     GlStateManager.deleteTexture(this.texID);
/* 36 */     this.texID = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   private int random(int seed) {
/* 41 */     seed ^= seed << 13;
/* 42 */     seed ^= seed >> 17;
/* 43 */     seed ^= seed << 5;
/* 44 */     return seed;
/*    */   }
/*    */ 
/*    */   
/*    */   private byte random(int x, int y, int z) {
/* 49 */     int i = (random(x) + random(y * 19)) * random(z * 23) - z;
/* 50 */     return (byte)(random(i) % 128);
/*    */   }
/*    */ 
/*    */   
/*    */   private byte[] genHFNoiseImage(int width, int height) {
/* 55 */     byte[] abyte = new byte[width * height * 3];
/* 56 */     int i = 0;
/*    */     
/* 58 */     for (int j = 0; j < height; j++) {
/*    */       
/* 60 */       for (int k = 0; k < width; k++) {
/*    */         
/* 62 */         for (int l = 1; l < 4; l++)
/*    */         {
/* 64 */           abyte[i++] = random(k, j, l);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 69 */     return abyte;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTextureId() {
/* 74 */     return this.texID;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTextureUnit() {
/* 79 */     return this.textureUnit;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\HFNoiseTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */