/*    */ package optifine;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ public class PlayerItemRenderer
/*    */ {
/*  8 */   private int attachTo = 0;
/*  9 */   private ModelRenderer modelRenderer = null;
/*    */ 
/*    */   
/*    */   public PlayerItemRenderer(int p_i74_1_, ModelRenderer p_i74_2_) {
/* 13 */     this.attachTo = p_i74_1_;
/* 14 */     this.modelRenderer = p_i74_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer() {
/* 19 */     return this.modelRenderer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(ModelBiped p_render_1_, float p_render_2_) {
/* 24 */     ModelRenderer modelrenderer = PlayerItemModel.getAttachModel(p_render_1_, this.attachTo);
/*    */     
/* 26 */     if (modelrenderer != null)
/*    */     {
/* 28 */       modelrenderer.postRender(p_render_2_);
/*    */     }
/*    */     
/* 31 */     this.modelRenderer.render(p_render_2_);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\PlayerItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */