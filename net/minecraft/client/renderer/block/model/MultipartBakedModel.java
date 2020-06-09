/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ public class MultipartBakedModel
/*    */   implements IBakedModel
/*    */ {
/*    */   private final Map<Predicate<IBlockState>, IBakedModel> selectors;
/*    */   protected final boolean ambientOcclusion;
/*    */   protected final boolean gui3D;
/*    */   protected final TextureAtlasSprite particleTexture;
/*    */   protected final ItemCameraTransforms cameraTransforms;
/*    */   protected final ItemOverrideList overrides;
/*    */   
/*    */   public MultipartBakedModel(Map<Predicate<IBlockState>, IBakedModel> selectorsIn) {
/* 25 */     this.selectors = selectorsIn;
/* 26 */     IBakedModel ibakedmodel = selectorsIn.values().iterator().next();
/* 27 */     this.ambientOcclusion = ibakedmodel.isAmbientOcclusion();
/* 28 */     this.gui3D = ibakedmodel.isGui3d();
/* 29 */     this.particleTexture = ibakedmodel.getParticleTexture();
/* 30 */     this.cameraTransforms = ibakedmodel.getItemCameraTransforms();
/* 31 */     this.overrides = ibakedmodel.getOverrides();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
/* 36 */     List<BakedQuad> list = Lists.newArrayList();
/*    */     
/* 38 */     if (state != null)
/*    */     {
/* 40 */       for (Map.Entry<Predicate<IBlockState>, IBakedModel> entry : this.selectors.entrySet()) {
/*    */         
/* 42 */         if (((Predicate)entry.getKey()).apply(state))
/*    */         {
/* 44 */           list.addAll(((IBakedModel)entry.getValue()).getQuads(state, side, rand++));
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 49 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAmbientOcclusion() {
/* 54 */     return this.ambientOcclusion;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isGui3d() {
/* 59 */     return this.gui3D;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBuiltInRenderer() {
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public TextureAtlasSprite getParticleTexture() {
/* 69 */     return this.particleTexture;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemCameraTransforms getItemCameraTransforms() {
/* 74 */     return this.cameraTransforms;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemOverrideList getOverrides() {
/* 79 */     return this.overrides;
/*    */   }
/*    */   
/*    */   public static class Builder
/*    */   {
/* 84 */     private final Map<Predicate<IBlockState>, IBakedModel> builderSelectors = Maps.newLinkedHashMap();
/*    */ 
/*    */     
/*    */     public void putModel(Predicate<IBlockState> predicate, IBakedModel model) {
/* 88 */       this.builderSelectors.put(predicate, model);
/*    */     }
/*    */ 
/*    */     
/*    */     public IBakedModel makeMultipartModel() {
/* 93 */       return new MultipartBakedModel(this.builderSelectors);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\MultipartBakedModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */