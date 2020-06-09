/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class BuiltInModel
/*    */   implements IBakedModel
/*    */ {
/*    */   private final ItemCameraTransforms cameraTransforms;
/*    */   private final ItemOverrideList overrideList;
/*    */   
/*    */   public BuiltInModel(ItemCameraTransforms p_i46537_1_, ItemOverrideList p_i46537_2_) {
/* 17 */     this.cameraTransforms = p_i46537_1_;
/* 18 */     this.overrideList = p_i46537_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
/* 23 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAmbientOcclusion() {
/* 28 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isGui3d() {
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBuiltInRenderer() {
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public TextureAtlasSprite getParticleTexture() {
/* 43 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemCameraTransforms getItemCameraTransforms() {
/* 48 */     return this.cameraTransforms;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemOverrideList getOverrides() {
/* 53 */     return this.overrideList;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\BuiltInModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */