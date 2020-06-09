/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.entity.model.anim.ModelUpdater;
/*     */ import optifine.Config;
/*     */ import optifine.ModelSprite;
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
/*     */ public class ModelRenderer
/*     */ {
/*     */   public float textureWidth;
/*     */   public float textureHeight;
/*     */   private int textureOffsetX;
/*     */   private int textureOffsetY;
/*     */   public float rotationPointX;
/*     */   public float rotationPointY;
/*     */   public float rotationPointZ;
/*     */   public float rotateAngleX;
/*     */   public float rotateAngleY;
/*     */   public float rotateAngleZ;
/*     */   private boolean compiled;
/*     */   private int displayList;
/*     */   public boolean mirror;
/*     */   public boolean showModel;
/*     */   public boolean isHidden;
/*     */   public List<ModelBox> cubeList;
/*     */   public List<ModelRenderer> childModels;
/*     */   public final String boxName;
/*     */   private final ModelBase baseModel;
/*     */   public float offsetX;
/*     */   public float offsetY;
/*     */   public float offsetZ;
/*     */   public List spriteList;
/*     */   public boolean mirrorV;
/*     */   public float scaleX;
/*     */   public float scaleY;
/*     */   public float scaleZ;
/*     */   private float savedScale;
/*     */   private ResourceLocation textureLocation;
/*     */   private String id;
/*     */   private ModelUpdater modelUpdater;
/*     */   private RenderGlobal renderGlobal;
/*     */   
/*     */   public ModelRenderer(ModelBase model, String boxNameIn) {
/*  64 */     this.spriteList = new ArrayList();
/*  65 */     this.mirrorV = false;
/*  66 */     this.scaleX = 1.0F;
/*  67 */     this.scaleY = 1.0F;
/*  68 */     this.scaleZ = 1.0F;
/*  69 */     this.textureLocation = null;
/*  70 */     this.id = null;
/*  71 */     this.renderGlobal = Config.getRenderGlobal();
/*  72 */     this.textureWidth = 64.0F;
/*  73 */     this.textureHeight = 32.0F;
/*  74 */     this.showModel = true;
/*  75 */     this.cubeList = Lists.newArrayList();
/*  76 */     this.baseModel = model;
/*  77 */     model.boxList.add(this);
/*  78 */     this.boxName = boxNameIn;
/*  79 */     setTextureSize(model.textureWidth, model.textureHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer(ModelBase model) {
/*  84 */     this(model, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer(ModelBase model, int texOffX, int texOffY) {
/*  89 */     this(model);
/*  90 */     setTextureOffset(texOffX, texOffY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(ModelRenderer renderer) {
/*  98 */     if (this.childModels == null)
/*     */     {
/* 100 */       this.childModels = Lists.newArrayList();
/*     */     }
/*     */     
/* 103 */     this.childModels.add(renderer);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer setTextureOffset(int x, int y) {
/* 108 */     this.textureOffsetX = x;
/* 109 */     this.textureOffsetY = y;
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer addBox(String partName, float offX, float offY, float offZ, int width, int height, int depth) {
/* 115 */     partName = String.valueOf(this.boxName) + "." + partName;
/* 116 */     TextureOffset textureoffset = this.baseModel.getTextureOffset(partName);
/* 117 */     setTextureOffset(textureoffset.textureOffsetX, textureoffset.textureOffsetY);
/* 118 */     this.cubeList.add((new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, 0.0F)).setBoxName(partName));
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer addBox(float offX, float offY, float offZ, int width, int height, int depth) {
/* 124 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, 0.0F));
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer addBox(float offX, float offY, float offZ, int width, int height, int depth, boolean mirrored) {
/* 130 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, 0.0F, mirrored));
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBox(float offX, float offY, float offZ, int width, int height, int depth, float scaleFactor) {
/* 139 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, scaleFactor));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRotationPoint(float rotationPointXIn, float rotationPointYIn, float rotationPointZIn) {
/* 144 */     this.rotationPointX = rotationPointXIn;
/* 145 */     this.rotationPointY = rotationPointYIn;
/* 146 */     this.rotationPointZ = rotationPointZIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float scale) {
/* 151 */     if (!this.isHidden && this.showModel) {
/*     */       
/* 153 */       if (!this.compiled)
/*     */       {
/* 155 */         compileDisplayList(scale);
/*     */       }
/*     */       
/* 158 */       int i = 0;
/*     */       
/* 160 */       if (this.textureLocation != null && !this.renderGlobal.renderOverlayDamaged) {
/*     */         
/* 162 */         if (this.renderGlobal.renderOverlayEyes) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 167 */         i = GlStateManager.getBoundTexture();
/* 168 */         Config.getTextureManager().bindTexture(this.textureLocation);
/*     */       } 
/*     */       
/* 171 */       if (this.modelUpdater != null)
/*     */       {
/* 173 */         this.modelUpdater.update();
/*     */       }
/*     */       
/* 176 */       boolean flag = !(this.scaleX == 1.0F && this.scaleY == 1.0F && this.scaleZ == 1.0F);
/* 177 */       GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
/*     */       
/* 179 */       if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
/*     */         
/* 181 */         if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F)
/*     */         {
/* 183 */           if (flag)
/*     */           {
/* 185 */             GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */           }
/*     */           
/* 188 */           GlStateManager.callList(this.displayList);
/*     */           
/* 190 */           if (this.childModels != null)
/*     */           {
/* 192 */             for (int l = 0; l < this.childModels.size(); l++)
/*     */             {
/* 194 */               ((ModelRenderer)this.childModels.get(l)).render(scale);
/*     */             }
/*     */           }
/*     */           
/* 198 */           if (flag)
/*     */           {
/* 200 */             GlStateManager.scale(1.0F / this.scaleX, 1.0F / this.scaleY, 1.0F / this.scaleZ);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 205 */           GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */           
/* 207 */           if (flag)
/*     */           {
/* 209 */             GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */           }
/*     */           
/* 212 */           GlStateManager.callList(this.displayList);
/*     */           
/* 214 */           if (this.childModels != null)
/*     */           {
/* 216 */             for (int k = 0; k < this.childModels.size(); k++)
/*     */             {
/* 218 */               ((ModelRenderer)this.childModels.get(k)).render(scale);
/*     */             }
/*     */           }
/*     */           
/* 222 */           if (flag)
/*     */           {
/* 224 */             GlStateManager.scale(1.0F / this.scaleX, 1.0F / this.scaleY, 1.0F / this.scaleZ);
/*     */           }
/*     */           
/* 227 */           GlStateManager.translate(-this.rotationPointX * scale, -this.rotationPointY * scale, -this.rotationPointZ * scale);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 232 */         GlStateManager.pushMatrix();
/* 233 */         GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */         
/* 235 */         if (this.rotateAngleZ != 0.0F)
/*     */         {
/* 237 */           GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */         }
/*     */         
/* 240 */         if (this.rotateAngleY != 0.0F)
/*     */         {
/* 242 */           GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 245 */         if (this.rotateAngleX != 0.0F)
/*     */         {
/* 247 */           GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */         
/* 250 */         if (flag)
/*     */         {
/* 252 */           GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */         }
/*     */         
/* 255 */         GlStateManager.callList(this.displayList);
/*     */         
/* 257 */         if (this.childModels != null)
/*     */         {
/* 259 */           for (int j = 0; j < this.childModels.size(); j++)
/*     */           {
/* 261 */             ((ModelRenderer)this.childModels.get(j)).render(scale);
/*     */           }
/*     */         }
/*     */         
/* 265 */         GlStateManager.popMatrix();
/*     */       } 
/*     */       
/* 268 */       GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
/*     */       
/* 270 */       if (i != 0)
/*     */       {
/* 272 */         GlStateManager.bindTexture(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderWithRotation(float scale) {
/* 279 */     if (!this.isHidden && this.showModel) {
/*     */       
/* 281 */       if (!this.compiled)
/*     */       {
/* 283 */         compileDisplayList(scale);
/*     */       }
/*     */       
/* 286 */       int i = 0;
/*     */       
/* 288 */       if (this.textureLocation != null && !this.renderGlobal.renderOverlayDamaged) {
/*     */         
/* 290 */         if (this.renderGlobal.renderOverlayEyes) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 295 */         i = GlStateManager.getBoundTexture();
/* 296 */         Config.getTextureManager().bindTexture(this.textureLocation);
/*     */       } 
/*     */       
/* 299 */       if (this.modelUpdater != null)
/*     */       {
/* 301 */         this.modelUpdater.update();
/*     */       }
/*     */       
/* 304 */       boolean flag = !(this.scaleX == 1.0F && this.scaleY == 1.0F && this.scaleZ == 1.0F);
/* 305 */       GlStateManager.pushMatrix();
/* 306 */       GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */       
/* 308 */       if (this.rotateAngleY != 0.0F)
/*     */       {
/* 310 */         GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */       }
/*     */       
/* 313 */       if (this.rotateAngleX != 0.0F)
/*     */       {
/* 315 */         GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */       }
/*     */       
/* 318 */       if (this.rotateAngleZ != 0.0F)
/*     */       {
/* 320 */         GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */       }
/*     */       
/* 323 */       if (flag)
/*     */       {
/* 325 */         GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */       }
/*     */       
/* 328 */       GlStateManager.callList(this.displayList);
/*     */       
/* 330 */       if (this.childModels != null)
/*     */       {
/* 332 */         for (int j = 0; j < this.childModels.size(); j++)
/*     */         {
/* 334 */           ((ModelRenderer)this.childModels.get(j)).render(scale);
/*     */         }
/*     */       }
/*     */       
/* 338 */       GlStateManager.popMatrix();
/*     */       
/* 340 */       if (i != 0)
/*     */       {
/* 342 */         GlStateManager.bindTexture(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postRender(float scale) {
/* 352 */     if (!this.isHidden && this.showModel) {
/*     */       
/* 354 */       if (!this.compiled)
/*     */       {
/* 356 */         compileDisplayList(scale);
/*     */       }
/*     */       
/* 359 */       if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
/*     */         
/* 361 */         if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F)
/*     */         {
/* 363 */           GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 368 */         GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */         
/* 370 */         if (this.rotateAngleZ != 0.0F)
/*     */         {
/* 372 */           GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */         }
/*     */         
/* 375 */         if (this.rotateAngleY != 0.0F)
/*     */         {
/* 377 */           GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 380 */         if (this.rotateAngleX != 0.0F)
/*     */         {
/* 382 */           GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void compileDisplayList(float scale) {
/* 393 */     if (this.displayList == 0) {
/*     */       
/* 395 */       this.savedScale = scale;
/* 396 */       this.displayList = GLAllocation.generateDisplayLists(1);
/*     */     } 
/*     */     
/* 399 */     GlStateManager.glNewList(this.displayList, 4864);
/* 400 */     BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
/*     */     
/* 402 */     for (int i = 0; i < this.cubeList.size(); i++)
/*     */     {
/* 404 */       ((ModelBox)this.cubeList.get(i)).render(bufferbuilder, scale);
/*     */     }
/*     */     
/* 407 */     for (int j = 0; j < this.spriteList.size(); j++) {
/*     */       
/* 409 */       ModelSprite modelsprite = this.spriteList.get(j);
/* 410 */       modelsprite.render(Tessellator.getInstance(), scale);
/*     */     } 
/*     */     
/* 413 */     GlStateManager.glEndList();
/* 414 */     this.compiled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelRenderer setTextureSize(int textureWidthIn, int textureHeightIn) {
/* 422 */     this.textureWidth = textureWidthIn;
/* 423 */     this.textureHeight = textureHeightIn;
/* 424 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSprite(float p_addSprite_1_, float p_addSprite_2_, float p_addSprite_3_, int p_addSprite_4_, int p_addSprite_5_, int p_addSprite_6_, float p_addSprite_7_) {
/* 429 */     this.spriteList.add(new ModelSprite(this, this.textureOffsetX, this.textureOffsetY, p_addSprite_1_, p_addSprite_2_, p_addSprite_3_, p_addSprite_4_, p_addSprite_5_, p_addSprite_6_, p_addSprite_7_));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getCompiled() {
/* 434 */     return this.compiled;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDisplayList() {
/* 439 */     return this.displayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetDisplayList() {
/* 444 */     if (this.compiled) {
/*     */       
/* 446 */       this.compiled = false;
/* 447 */       compileDisplayList(this.savedScale);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation() {
/* 453 */     return this.textureLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureLocation(ResourceLocation p_setTextureLocation_1_) {
/* 458 */     this.textureLocation = p_setTextureLocation_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 463 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(String p_setId_1_) {
/* 468 */     this.id = p_setId_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBox(int[][] p_addBox_1_, float p_addBox_2_, float p_addBox_3_, float p_addBox_4_, float p_addBox_5_, float p_addBox_6_, float p_addBox_7_, float p_addBox_8_) {
/* 473 */     this.cubeList.add(new ModelBox(this, p_addBox_1_, p_addBox_2_, p_addBox_3_, p_addBox_4_, p_addBox_5_, p_addBox_6_, p_addBox_7_, p_addBox_8_, this.mirror));
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer getChild(String p_getChild_1_) {
/* 478 */     if (p_getChild_1_ == null)
/*     */     {
/* 480 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 484 */     if (this.childModels != null)
/*     */     {
/* 486 */       for (int i = 0; i < this.childModels.size(); i++) {
/*     */         
/* 488 */         ModelRenderer modelrenderer = this.childModels.get(i);
/*     */         
/* 490 */         if (p_getChild_1_.equals(modelrenderer.getId()))
/*     */         {
/* 492 */           return modelrenderer;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 497 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelRenderer getChildDeep(String p_getChildDeep_1_) {
/* 503 */     if (p_getChildDeep_1_ == null)
/*     */     {
/* 505 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 509 */     ModelRenderer modelrenderer = getChild(p_getChildDeep_1_);
/*     */     
/* 511 */     if (modelrenderer != null)
/*     */     {
/* 513 */       return modelrenderer;
/*     */     }
/*     */ 
/*     */     
/* 517 */     if (this.childModels != null)
/*     */     {
/* 519 */       for (int i = 0; i < this.childModels.size(); i++) {
/*     */         
/* 521 */         ModelRenderer modelrenderer1 = this.childModels.get(i);
/* 522 */         ModelRenderer modelrenderer2 = modelrenderer1.getChildDeep(p_getChildDeep_1_);
/*     */         
/* 524 */         if (modelrenderer2 != null)
/*     */         {
/* 526 */           return modelrenderer2;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 531 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModelUpdater(ModelUpdater p_setModelUpdater_1_) {
/* 538 */     this.modelUpdater = p_setModelUpdater_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 543 */     StringBuffer stringbuffer = new StringBuffer();
/* 544 */     stringbuffer.append("id: " + this.id + ", boxes: " + ((this.cubeList != null) ? (String)Integer.valueOf(this.cubeList.size()) : null) + ", submodels: " + ((this.childModels != null) ? (String)Integer.valueOf(this.childModels.size()) : null));
/* 545 */     return stringbuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */