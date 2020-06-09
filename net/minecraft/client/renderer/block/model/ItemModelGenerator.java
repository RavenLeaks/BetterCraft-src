/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ public class ItemModelGenerator
/*     */ {
/*  16 */   public static final List<String> LAYERS = Lists.newArrayList((Object[])new String[] { "layer0", "layer1", "layer2", "layer3", "layer4" });
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ModelBlock makeItemModel(TextureMap textureMapIn, ModelBlock blockModel) {
/*  21 */     Map<String, String> map = Maps.newHashMap();
/*  22 */     List<BlockPart> list = Lists.newArrayList();
/*     */     
/*  24 */     for (int i = 0; i < LAYERS.size(); i++) {
/*     */       
/*  26 */       String s = LAYERS.get(i);
/*     */       
/*  28 */       if (!blockModel.isTexturePresent(s)) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/*  33 */       String s1 = blockModel.resolveTextureName(s);
/*  34 */       map.put(s, s1);
/*  35 */       TextureAtlasSprite textureatlassprite = textureMapIn.getAtlasSprite((new ResourceLocation(s1)).toString());
/*  36 */       list.addAll(getBlockParts(i, s, textureatlassprite));
/*     */     } 
/*     */     
/*  39 */     if (list.isEmpty())
/*     */     {
/*  41 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  45 */     map.put("particle", blockModel.isTexturePresent("particle") ? blockModel.resolveTextureName("particle") : map.get("layer0"));
/*  46 */     return new ModelBlock(null, list, map, false, false, blockModel.getAllTransforms(), blockModel.getOverrides());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<BlockPart> getBlockParts(int tintIndex, String p_178394_2_, TextureAtlasSprite p_178394_3_) {
/*  52 */     Map<EnumFacing, BlockPartFace> map = Maps.newHashMap();
/*  53 */     map.put(EnumFacing.SOUTH, new BlockPartFace(null, tintIndex, p_178394_2_, new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0)));
/*  54 */     map.put(EnumFacing.NORTH, new BlockPartFace(null, tintIndex, p_178394_2_, new BlockFaceUV(new float[] { 16.0F, 0.0F, 0.0F, 16.0F }, 0)));
/*  55 */     List<BlockPart> list = Lists.newArrayList();
/*  56 */     list.add(new BlockPart(new Vector3f(0.0F, 0.0F, 7.5F), new Vector3f(16.0F, 16.0F, 8.5F), map, null, true));
/*  57 */     list.addAll(getBlockParts(p_178394_3_, p_178394_2_, tintIndex));
/*  58 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<BlockPart> getBlockParts(TextureAtlasSprite p_178397_1_, String p_178397_2_, int p_178397_3_) {
/*  63 */     float f = p_178397_1_.getIconWidth();
/*  64 */     float f1 = p_178397_1_.getIconHeight();
/*  65 */     List<BlockPart> list = Lists.newArrayList();
/*     */     
/*  67 */     for (Span itemmodelgenerator$span : getSpans(p_178397_1_)) {
/*     */       
/*  69 */       float f2 = 0.0F;
/*  70 */       float f3 = 0.0F;
/*  71 */       float f4 = 0.0F;
/*  72 */       float f5 = 0.0F;
/*  73 */       float f6 = 0.0F;
/*  74 */       float f7 = 0.0F;
/*  75 */       float f8 = 0.0F;
/*  76 */       float f9 = 0.0F;
/*  77 */       float f10 = 0.0F;
/*  78 */       float f11 = 0.0F;
/*  79 */       float f12 = itemmodelgenerator$span.getMin();
/*  80 */       float f13 = itemmodelgenerator$span.getMax();
/*  81 */       float f14 = itemmodelgenerator$span.getAnchor();
/*  82 */       SpanFacing itemmodelgenerator$spanfacing = itemmodelgenerator$span.getFacing();
/*     */       
/*  84 */       switch (itemmodelgenerator$spanfacing) {
/*     */         
/*     */         case UP:
/*  87 */           f6 = f12;
/*  88 */           f2 = f12;
/*  89 */           f4 = f7 = f13 + 1.0F;
/*  90 */           f8 = f14;
/*  91 */           f3 = f14;
/*  92 */           f9 = f14;
/*  93 */           f5 = f14;
/*  94 */           f10 = 16.0F / f;
/*  95 */           f11 = 16.0F / (f1 - 1.0F);
/*     */           break;
/*     */         
/*     */         case null:
/*  99 */           f9 = f14;
/* 100 */           f8 = f14;
/* 101 */           f6 = f12;
/* 102 */           f2 = f12;
/* 103 */           f4 = f7 = f13 + 1.0F;
/* 104 */           f3 = f14 + 1.0F;
/* 105 */           f5 = f14 + 1.0F;
/* 106 */           f10 = 16.0F / f;
/* 107 */           f11 = 16.0F / (f1 - 1.0F);
/*     */           break;
/*     */         
/*     */         case LEFT:
/* 111 */           f6 = f14;
/* 112 */           f2 = f14;
/* 113 */           f7 = f14;
/* 114 */           f4 = f14;
/* 115 */           f9 = f12;
/* 116 */           f3 = f12;
/* 117 */           f5 = f8 = f13 + 1.0F;
/* 118 */           f10 = 16.0F / (f - 1.0F);
/* 119 */           f11 = 16.0F / f1;
/*     */           break;
/*     */         
/*     */         case RIGHT:
/* 123 */           f7 = f14;
/* 124 */           f6 = f14;
/* 125 */           f2 = f14 + 1.0F;
/* 126 */           f4 = f14 + 1.0F;
/* 127 */           f9 = f12;
/* 128 */           f3 = f12;
/* 129 */           f5 = f8 = f13 + 1.0F;
/* 130 */           f10 = 16.0F / (f - 1.0F);
/* 131 */           f11 = 16.0F / f1;
/*     */           break;
/*     */       } 
/* 134 */       float f15 = 16.0F / f;
/* 135 */       float f16 = 16.0F / f1;
/* 136 */       f2 *= f15;
/* 137 */       f4 *= f15;
/* 138 */       f3 *= f16;
/* 139 */       f5 *= f16;
/* 140 */       f3 = 16.0F - f3;
/* 141 */       f5 = 16.0F - f5;
/* 142 */       f6 *= f10;
/* 143 */       f7 *= f10;
/* 144 */       f8 *= f11;
/* 145 */       f9 *= f11;
/* 146 */       Map<EnumFacing, BlockPartFace> map = Maps.newHashMap();
/* 147 */       map.put(itemmodelgenerator$spanfacing.getFacing(), new BlockPartFace(null, p_178397_3_, p_178397_2_, new BlockFaceUV(new float[] { f6, f8, f7, f9 }, 0)));
/*     */       
/* 149 */       switch (itemmodelgenerator$spanfacing) {
/*     */         
/*     */         case UP:
/* 152 */           list.add(new BlockPart(new Vector3f(f2, f3, 7.5F), new Vector3f(f4, f3, 8.5F), map, null, true));
/*     */ 
/*     */         
/*     */         case null:
/* 156 */           list.add(new BlockPart(new Vector3f(f2, f5, 7.5F), new Vector3f(f4, f5, 8.5F), map, null, true));
/*     */ 
/*     */         
/*     */         case LEFT:
/* 160 */           list.add(new BlockPart(new Vector3f(f2, f3, 7.5F), new Vector3f(f2, f5, 8.5F), map, null, true));
/*     */ 
/*     */         
/*     */         case RIGHT:
/* 164 */           list.add(new BlockPart(new Vector3f(f4, f3, 7.5F), new Vector3f(f4, f5, 8.5F), map, null, true));
/*     */       } 
/*     */     
/*     */     } 
/* 168 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Span> getSpans(TextureAtlasSprite p_178393_1_) {
/* 173 */     int i = p_178393_1_.getIconWidth();
/* 174 */     int j = p_178393_1_.getIconHeight();
/* 175 */     List<Span> list = Lists.newArrayList();
/*     */     
/* 177 */     for (int k = 0; k < p_178393_1_.getFrameCount(); k++) {
/*     */       
/* 179 */       int[] aint = p_178393_1_.getFrameTextureData(k)[0];
/*     */       
/* 181 */       for (int l = 0; l < j; l++) {
/*     */         
/* 183 */         for (int i1 = 0; i1 < i; i1++) {
/*     */           
/* 185 */           boolean flag = !isTransparent(aint, i1, l, i, j);
/* 186 */           checkTransition(SpanFacing.UP, list, aint, i1, l, i, j, flag);
/* 187 */           checkTransition(SpanFacing.DOWN, list, aint, i1, l, i, j, flag);
/* 188 */           checkTransition(SpanFacing.LEFT, list, aint, i1, l, i, j, flag);
/* 189 */           checkTransition(SpanFacing.RIGHT, list, aint, i1, l, i, j, flag);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkTransition(SpanFacing p_178396_1_, List<Span> p_178396_2_, int[] p_178396_3_, int p_178396_4_, int p_178396_5_, int p_178396_6_, int p_178396_7_, boolean p_178396_8_) {
/* 199 */     boolean flag = (isTransparent(p_178396_3_, p_178396_4_ + p_178396_1_.getXOffset(), p_178396_5_ + p_178396_1_.getYOffset(), p_178396_6_, p_178396_7_) && p_178396_8_);
/*     */     
/* 201 */     if (flag)
/*     */     {
/* 203 */       createOrExpandSpan(p_178396_2_, p_178396_1_, p_178396_4_, p_178396_5_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void createOrExpandSpan(List<Span> p_178395_1_, SpanFacing p_178395_2_, int p_178395_3_, int p_178395_4_) {
/* 209 */     Span itemmodelgenerator$span = null;
/*     */     
/* 211 */     for (Span itemmodelgenerator$span1 : p_178395_1_) {
/*     */       
/* 213 */       if (itemmodelgenerator$span1.getFacing() == p_178395_2_) {
/*     */         
/* 215 */         int i = p_178395_2_.isHorizontal() ? p_178395_4_ : p_178395_3_;
/*     */         
/* 217 */         if (itemmodelgenerator$span1.getAnchor() == i) {
/*     */           
/* 219 */           itemmodelgenerator$span = itemmodelgenerator$span1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 225 */     int j = p_178395_2_.isHorizontal() ? p_178395_4_ : p_178395_3_;
/* 226 */     int k = p_178395_2_.isHorizontal() ? p_178395_3_ : p_178395_4_;
/*     */     
/* 228 */     if (itemmodelgenerator$span == null) {
/*     */       
/* 230 */       p_178395_1_.add(new Span(p_178395_2_, k, j));
/*     */     }
/*     */     else {
/*     */       
/* 234 */       itemmodelgenerator$span.expand(k);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isTransparent(int[] p_178391_1_, int p_178391_2_, int p_178391_3_, int p_178391_4_, int p_178391_5_) {
/* 240 */     if (p_178391_2_ >= 0 && p_178391_3_ >= 0 && p_178391_2_ < p_178391_4_ && p_178391_3_ < p_178391_5_)
/*     */     {
/* 242 */       return ((p_178391_1_[p_178391_3_ * p_178391_4_ + p_178391_2_] >> 24 & 0xFF) == 0);
/*     */     }
/*     */ 
/*     */     
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   static class Span
/*     */   {
/*     */     private final ItemModelGenerator.SpanFacing spanFacing;
/*     */     
/*     */     private int min;
/*     */     private int max;
/*     */     private final int anchor;
/*     */     
/*     */     public Span(ItemModelGenerator.SpanFacing spanFacingIn, int p_i46216_2_, int p_i46216_3_) {
/* 259 */       this.spanFacing = spanFacingIn;
/* 260 */       this.min = p_i46216_2_;
/* 261 */       this.max = p_i46216_2_;
/* 262 */       this.anchor = p_i46216_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void expand(int p_178382_1_) {
/* 267 */       if (p_178382_1_ < this.min) {
/*     */         
/* 269 */         this.min = p_178382_1_;
/*     */       }
/* 271 */       else if (p_178382_1_ > this.max) {
/*     */         
/* 273 */         this.max = p_178382_1_;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemModelGenerator.SpanFacing getFacing() {
/* 279 */       return this.spanFacing;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMin() {
/* 284 */       return this.min;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMax() {
/* 289 */       return this.max;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getAnchor() {
/* 294 */       return this.anchor;
/*     */     }
/*     */   }
/*     */   
/*     */   enum SpanFacing
/*     */   {
/* 300 */     UP((String)EnumFacing.UP, 0, -1),
/* 301 */     DOWN((String)EnumFacing.DOWN, 0, 1),
/* 302 */     LEFT((String)EnumFacing.EAST, -1, 0),
/* 303 */     RIGHT((String)EnumFacing.WEST, 1, 0);
/*     */     
/*     */     private final EnumFacing facing;
/*     */     
/*     */     private final int xOffset;
/*     */     private final int yOffset;
/*     */     
/*     */     SpanFacing(EnumFacing facing, int p_i46215_4_, int p_i46215_5_) {
/* 311 */       this.facing = facing;
/* 312 */       this.xOffset = p_i46215_4_;
/* 313 */       this.yOffset = p_i46215_5_;
/*     */     }
/*     */ 
/*     */     
/*     */     public EnumFacing getFacing() {
/* 318 */       return this.facing;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getXOffset() {
/* 323 */       return this.xOffset;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getYOffset() {
/* 328 */       return this.yOffset;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean isHorizontal() {
/* 333 */       return !(this != DOWN && this != UP);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\ItemModelGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */