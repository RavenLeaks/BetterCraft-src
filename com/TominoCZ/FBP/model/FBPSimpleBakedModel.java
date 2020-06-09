/*     */ package com.TominoCZ.FBP.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.block.model.ItemOverrideList;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FBPSimpleBakedModel
/*     */   implements IBakedModel
/*     */ {
/*  21 */   private final List<BakedQuad>[] quads = (List<BakedQuad>[])new List[7];
/*     */   
/*     */   private final IBakedModel parent;
/*     */   private TextureAtlasSprite particle;
/*     */   
/*     */   public FBPSimpleBakedModel() {
/*  27 */     this(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public FBPSimpleBakedModel(IBakedModel parent) {
/*  32 */     this.parent = parent;
/*  33 */     for (int i = 0; i < this.quads.length; i++)
/*     */     {
/*  35 */       this.quads[i] = new ArrayList<>();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParticle(TextureAtlasSprite particle) {
/*  41 */     this.particle = particle;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addQuad(EnumFacing side, BakedQuad quad) {
/*  46 */     this.quads[(side == null) ? 6 : side.ordinal()].add(quad);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
/*  52 */     return this.quads[(side == null) ? 6 : side.ordinal()];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAmbientOcclusion() {
/*  58 */     return (this.parent != null) ? this.parent.isAmbientOcclusion() : true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGui3d() {
/*  64 */     return (this.parent != null) ? this.parent.isGui3d() : true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBuiltInRenderer() {
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getParticleTexture() {
/*  76 */     if (this.particle != null)
/*     */     {
/*  78 */       return this.particle;
/*     */     }
/*     */     
/*  81 */     return (this.parent != null) ? this.parent.getParticleTexture() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemOverrideList getOverrides() {
/*  88 */     return ItemOverrideList.NONE;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemCameraTransforms getItemCameraTransforms() {
/* 107 */     return this.parent.getItemCameraTransforms();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\model\FBPSimpleBakedModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */