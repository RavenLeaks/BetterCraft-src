/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ 
/*     */ public class WeightedBakedModel
/*     */   implements IBakedModel
/*     */ {
/*     */   private final int totalWeight;
/*     */   private final List<WeightedModel> models;
/*     */   private final IBakedModel baseModel;
/*     */   
/*     */   public WeightedBakedModel(List<WeightedModel> modelsIn) {
/*  21 */     this.models = modelsIn;
/*  22 */     this.totalWeight = WeightedRandom.getTotalWeight(modelsIn);
/*  23 */     this.baseModel = ((WeightedModel)modelsIn.get(0)).model;
/*     */   }
/*     */ 
/*     */   
/*     */   private IBakedModel getRandomModel(long p_188627_1_) {
/*  28 */     return ((WeightedModel)WeightedRandom.getRandomItem(this.models, Math.abs((int)p_188627_1_ >> 16) % this.totalWeight)).model;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
/*  33 */     return getRandomModel(rand).getQuads(state, side, rand);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAmbientOcclusion() {
/*  38 */     return this.baseModel.isAmbientOcclusion();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGui3d() {
/*  43 */     return this.baseModel.isGui3d();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBuiltInRenderer() {
/*  48 */     return this.baseModel.isBuiltInRenderer();
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getParticleTexture() {
/*  53 */     return this.baseModel.getParticleTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemCameraTransforms getItemCameraTransforms() {
/*  58 */     return this.baseModel.getItemCameraTransforms();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemOverrideList getOverrides() {
/*  63 */     return this.baseModel.getOverrides();
/*     */   }
/*     */   
/*     */   public static class Builder
/*     */   {
/*  68 */     private final List<WeightedBakedModel.WeightedModel> listItems = Lists.newArrayList();
/*     */ 
/*     */     
/*     */     public Builder add(IBakedModel model, int weight) {
/*  72 */       this.listItems.add(new WeightedBakedModel.WeightedModel(model, weight));
/*  73 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public WeightedBakedModel build() {
/*  78 */       Collections.sort(this.listItems);
/*  79 */       return new WeightedBakedModel(this.listItems);
/*     */     }
/*     */ 
/*     */     
/*     */     public IBakedModel first() {
/*  84 */       return ((WeightedBakedModel.WeightedModel)this.listItems.get(0)).model;
/*     */     }
/*     */   }
/*     */   
/*     */   static class WeightedModel
/*     */     extends WeightedRandom.Item
/*     */     implements Comparable<WeightedModel> {
/*     */     protected final IBakedModel model;
/*     */     
/*     */     public WeightedModel(IBakedModel modelIn, int itemWeightIn) {
/*  94 */       super(itemWeightIn);
/*  95 */       this.model = modelIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(WeightedModel p_compareTo_1_) {
/* 100 */       return ComparisonChain.start().compare(p_compareTo_1_.itemWeight, this.itemWeight).result();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 105 */       return "MyWeighedRandomItem{weight=" + this.itemWeight + ", model=" + this.model + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\WeightedBakedModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */