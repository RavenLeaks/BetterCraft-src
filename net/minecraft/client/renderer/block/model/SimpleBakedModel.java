/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ public class SimpleBakedModel
/*     */   implements IBakedModel {
/*     */   protected final List<BakedQuad> generalQuads;
/*     */   protected final Map<EnumFacing, List<BakedQuad>> faceQuads;
/*     */   protected final boolean ambientOcclusion;
/*     */   protected final boolean gui3d;
/*     */   protected final TextureAtlasSprite texture;
/*     */   protected final ItemCameraTransforms cameraTransforms;
/*     */   protected final ItemOverrideList itemOverrideList;
/*     */   
/*     */   public SimpleBakedModel(List<BakedQuad> generalQuadsIn, Map<EnumFacing, List<BakedQuad>> faceQuadsIn, boolean ambientOcclusionIn, boolean gui3dIn, TextureAtlasSprite textureIn, ItemCameraTransforms cameraTransformsIn, ItemOverrideList itemOverrideListIn) {
/*  26 */     this.generalQuads = generalQuadsIn;
/*  27 */     this.faceQuads = faceQuadsIn;
/*  28 */     this.ambientOcclusion = ambientOcclusionIn;
/*  29 */     this.gui3d = gui3dIn;
/*  30 */     this.texture = textureIn;
/*  31 */     this.cameraTransforms = cameraTransformsIn;
/*  32 */     this.itemOverrideList = itemOverrideListIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
/*  37 */     return (side == null) ? this.generalQuads : this.faceQuads.get(side);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAmbientOcclusion() {
/*  42 */     return this.ambientOcclusion;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGui3d() {
/*  47 */     return this.gui3d;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBuiltInRenderer() {
/*  52 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getParticleTexture() {
/*  57 */     return this.texture;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemCameraTransforms getItemCameraTransforms() {
/*  62 */     return this.cameraTransforms;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemOverrideList getOverrides() {
/*  67 */     return this.itemOverrideList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     public Builder(ModelBlock model, ItemOverrideList overrides) {
/*  82 */       this(model.isAmbientOcclusion(), model.isGui3d(), model.getAllTransforms(), overrides);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder(IBlockState state, IBakedModel model, TextureAtlasSprite texture, BlockPos pos) {
/*  87 */       this(model.isAmbientOcclusion(), model.isGui3d(), model.getItemCameraTransforms(), model.getOverrides());
/*  88 */       this.builderTexture = model.getParticleTexture();
/*  89 */       long i = MathHelper.getPositionRandom((Vec3i)pos); byte b; int j;
/*     */       EnumFacing[] arrayOfEnumFacing;
/*  91 */       for (j = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/*  93 */         addFaceQuads(state, model, texture, enumfacing, i);
/*     */         b++; }
/*     */       
/*  96 */       addGeneralQuads(state, model, texture, i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 101 */     private final List<BakedQuad> builderGeneralQuads = Lists.newArrayList();
/* 102 */     private final Map<EnumFacing, List<BakedQuad>> builderFaceQuads = Maps.newEnumMap(EnumFacing.class); private final ItemOverrideList builderItemOverrideList; private final boolean builderAmbientOcclusion; private Builder(boolean ambientOcclusion, boolean gui3d, ItemCameraTransforms transforms, ItemOverrideList overrides) { byte b; int i;
/*     */       EnumFacing[] arrayOfEnumFacing;
/* 104 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/* 106 */         this.builderFaceQuads.put(enumfacing, Lists.newArrayList());
/*     */         b++; }
/*     */       
/* 109 */       this.builderItemOverrideList = overrides;
/* 110 */       this.builderAmbientOcclusion = ambientOcclusion;
/* 111 */       this.builderGui3d = gui3d;
/* 112 */       this.builderCameraTransforms = transforms; }
/*     */     
/*     */     private TextureAtlasSprite builderTexture; private final boolean builderGui3d; private final ItemCameraTransforms builderCameraTransforms;
/*     */     
/*     */     private void addFaceQuads(IBlockState p_188644_1_, IBakedModel p_188644_2_, TextureAtlasSprite p_188644_3_, EnumFacing p_188644_4_, long p_188644_5_) {
/* 117 */       for (BakedQuad bakedquad : p_188644_2_.getQuads(p_188644_1_, p_188644_4_, p_188644_5_))
/*     */       {
/* 119 */         addFaceQuad(p_188644_4_, new BakedQuadRetextured(bakedquad, p_188644_3_));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void addGeneralQuads(IBlockState p_188645_1_, IBakedModel p_188645_2_, TextureAtlasSprite p_188645_3_, long p_188645_4_) {
/* 125 */       for (BakedQuad bakedquad : p_188645_2_.getQuads(p_188645_1_, null, p_188645_4_))
/*     */       {
/* 127 */         addGeneralQuad(new BakedQuadRetextured(bakedquad, p_188645_3_));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder addFaceQuad(EnumFacing facing, BakedQuad quad) {
/* 133 */       ((List<BakedQuad>)this.builderFaceQuads.get(facing)).add(quad);
/* 134 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder addGeneralQuad(BakedQuad quad) {
/* 139 */       this.builderGeneralQuads.add(quad);
/* 140 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder setTexture(TextureAtlasSprite texture) {
/* 145 */       this.builderTexture = texture;
/* 146 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public IBakedModel makeBakedModel() {
/* 151 */       if (this.builderTexture == null)
/*     */       {
/* 153 */         throw new RuntimeException("Missing particle!");
/*     */       }
/*     */ 
/*     */       
/* 157 */       return new SimpleBakedModel(this.builderGeneralQuads, this.builderFaceQuads, this.builderAmbientOcclusion, this.builderGui3d, this.builderTexture, this.builderCameraTransforms, this.builderItemOverrideList);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\SimpleBakedModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */