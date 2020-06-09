/*    */ package optifine;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.ImageBufferDownload;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class CapeImageBuffer
/*    */   extends ImageBufferDownload
/*    */ {
/*    */   private AbstractClientPlayer player;
/*    */   private ResourceLocation resourceLocation;
/*    */   
/*    */   public CapeImageBuffer(AbstractClientPlayer p_i21_1_, ResourceLocation p_i21_2_) {
/* 15 */     this.player = p_i21_1_;
/* 16 */     this.resourceLocation = p_i21_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public BufferedImage parseUserSkin(BufferedImage image) {
/* 21 */     return CapeUtils.parseCape(image);
/*    */   }
/*    */ 
/*    */   
/*    */   public void skinAvailable() {
/* 26 */     if (this.player != null)
/*    */     {
/* 28 */       this.player.setLocationOfCape(this.resourceLocation);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanup() {
/* 34 */     this.player = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CapeImageBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */