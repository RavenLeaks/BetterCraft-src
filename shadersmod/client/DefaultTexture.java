/*    */ package shadersmod.client;
/*    */ 
/*    */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ 
/*    */ public class DefaultTexture
/*    */   extends AbstractTexture
/*    */ {
/*    */   public DefaultTexture() {
/* 10 */     loadTexture(null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadTexture(IResourceManager resourcemanager) {
/* 15 */     int[] aint = ShadersTex.createAIntImage(1, -1);
/* 16 */     ShadersTex.setupTexture(getMultiTexID(), aint, 1, 1, false, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\DefaultTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */