/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.renderer.StitcherException;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import optifine.MathUtils;
/*     */ 
/*     */ public class Stitcher
/*     */ {
/*     */   private final int mipmapLevelStitcher;
/*  15 */   private final Set<Holder> setStitchHolders = Sets.newHashSetWithExpectedSize(256);
/*  16 */   private final List<Slot> stitchSlots = Lists.newArrayListWithCapacity(256);
/*     */   
/*     */   private int currentWidth;
/*     */   
/*     */   private int currentHeight;
/*     */   
/*     */   private final int maxWidth;
/*     */   private final int maxHeight;
/*     */   private final int maxTileDimension;
/*     */   
/*     */   public Stitcher(int maxWidthIn, int maxHeightIn, int maxTileDimensionIn, int mipmapLevelStitcherIn) {
/*  27 */     this.mipmapLevelStitcher = mipmapLevelStitcherIn;
/*  28 */     this.maxWidth = maxWidthIn;
/*  29 */     this.maxHeight = maxHeightIn;
/*  30 */     this.maxTileDimension = maxTileDimensionIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentWidth() {
/*  35 */     return this.currentWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentHeight() {
/*  40 */     return this.currentHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSprite(TextureAtlasSprite textureAtlas) {
/*  45 */     Holder stitcher$holder = new Holder(textureAtlas, this.mipmapLevelStitcher);
/*     */     
/*  47 */     if (this.maxTileDimension > 0)
/*     */     {
/*  49 */       stitcher$holder.setNewDimension(this.maxTileDimension);
/*     */     }
/*     */     
/*  52 */     this.setStitchHolders.add(stitcher$holder);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doStitch() {
/*  57 */     Holder[] astitcher$holder = this.setStitchHolders.<Holder>toArray(new Holder[this.setStitchHolders.size()]);
/*  58 */     Arrays.sort((Object[])astitcher$holder); byte b; int i;
/*     */     Holder[] arrayOfHolder1;
/*  60 */     for (i = (arrayOfHolder1 = astitcher$holder).length, b = 0; b < i; ) { Holder stitcher$holder = arrayOfHolder1[b];
/*     */       
/*  62 */       if (!allocateSlot(stitcher$holder)) {
/*     */         
/*  64 */         String s = String.format("Unable to fit: %s, size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?", new Object[] { stitcher$holder.getAtlasSprite().getIconName(), Integer.valueOf(stitcher$holder.getAtlasSprite().getIconWidth()), Integer.valueOf(stitcher$holder.getAtlasSprite().getIconHeight()), Integer.valueOf(this.currentWidth), Integer.valueOf(this.currentHeight), Integer.valueOf(this.maxWidth), Integer.valueOf(this.maxHeight) });
/*  65 */         throw new StitcherException(stitcher$holder, s);
/*     */       } 
/*     */       b++; }
/*     */     
/*  69 */     this.currentWidth = MathHelper.smallestEncompassingPowerOfTwo(this.currentWidth);
/*  70 */     this.currentHeight = MathHelper.smallestEncompassingPowerOfTwo(this.currentHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<TextureAtlasSprite> getStichSlots() {
/*  75 */     List<Slot> list = Lists.newArrayList();
/*     */     
/*  77 */     for (Slot stitcher$slot : this.stitchSlots)
/*     */     {
/*  79 */       stitcher$slot.getAllStitchSlots(list);
/*     */     }
/*     */     
/*  82 */     List<TextureAtlasSprite> list1 = Lists.newArrayList();
/*     */     
/*  84 */     for (Slot stitcher$slot1 : list) {
/*     */       
/*  86 */       Holder stitcher$holder = stitcher$slot1.getStitchHolder();
/*  87 */       TextureAtlasSprite textureatlassprite = stitcher$holder.getAtlasSprite();
/*  88 */       textureatlassprite.initSprite(this.currentWidth, this.currentHeight, stitcher$slot1.getOriginX(), stitcher$slot1.getOriginY(), stitcher$holder.isRotated());
/*  89 */       list1.add(textureatlassprite);
/*     */     } 
/*     */     
/*  92 */     return list1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getMipmapDimension(int p_147969_0_, int p_147969_1_) {
/*  97 */     return (p_147969_0_ >> p_147969_1_) + (((p_147969_0_ & (1 << p_147969_1_) - 1) == 0) ? 0 : 1) << p_147969_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean allocateSlot(Holder p_94310_1_) {
/* 105 */     TextureAtlasSprite textureatlassprite = p_94310_1_.getAtlasSprite();
/* 106 */     boolean flag = (textureatlassprite.getIconWidth() != textureatlassprite.getIconHeight());
/*     */     
/* 108 */     for (int i = 0; i < this.stitchSlots.size(); i++) {
/*     */       
/* 110 */       if (((Slot)this.stitchSlots.get(i)).addSlot(p_94310_1_))
/*     */       {
/* 112 */         return true;
/*     */       }
/*     */       
/* 115 */       if (flag) {
/*     */         
/* 117 */         p_94310_1_.rotate();
/*     */         
/* 119 */         if (((Slot)this.stitchSlots.get(i)).addSlot(p_94310_1_))
/*     */         {
/* 121 */           return true;
/*     */         }
/*     */         
/* 124 */         p_94310_1_.rotate();
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     return expandAndAllocateSlot(p_94310_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean expandAndAllocateSlot(Holder p_94311_1_) {
/*     */     Slot stitcher$slot;
/* 136 */     int i = Math.min(p_94311_1_.getWidth(), p_94311_1_.getHeight());
/* 137 */     int j = Math.max(p_94311_1_.getWidth(), p_94311_1_.getHeight());
/* 138 */     int k = MathHelper.smallestEncompassingPowerOfTwo(this.currentWidth);
/* 139 */     int l = MathHelper.smallestEncompassingPowerOfTwo(this.currentHeight);
/* 140 */     int i1 = MathHelper.smallestEncompassingPowerOfTwo(this.currentWidth + i);
/* 141 */     int j1 = MathHelper.smallestEncompassingPowerOfTwo(this.currentHeight + i);
/* 142 */     boolean flag = (i1 <= this.maxWidth);
/* 143 */     boolean flag1 = (j1 <= this.maxHeight);
/*     */     
/* 145 */     if (!flag && !flag1)
/*     */     {
/* 147 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 151 */     int k1 = MathUtils.roundDownToPowerOfTwo(this.currentHeight);
/* 152 */     boolean flag2 = (flag && i1 <= 2 * k1);
/*     */     
/* 154 */     if (this.currentWidth == 0 && this.currentHeight == 0)
/*     */     {
/* 156 */       flag2 = true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 161 */     if (flag2) {
/*     */       
/* 163 */       if (p_94311_1_.getWidth() > p_94311_1_.getHeight())
/*     */       {
/* 165 */         p_94311_1_.rotate();
/*     */       }
/*     */       
/* 168 */       if (this.currentHeight == 0)
/*     */       {
/* 170 */         this.currentHeight = p_94311_1_.getHeight();
/*     */       }
/*     */       
/* 173 */       stitcher$slot = new Slot(this.currentWidth, 0, p_94311_1_.getWidth(), this.currentHeight);
/* 174 */       this.currentWidth += p_94311_1_.getWidth();
/*     */     }
/*     */     else {
/*     */       
/* 178 */       stitcher$slot = new Slot(0, this.currentHeight, this.currentWidth, p_94311_1_.getHeight());
/* 179 */       this.currentHeight += p_94311_1_.getHeight();
/*     */     } 
/*     */     
/* 182 */     stitcher$slot.addSlot(p_94311_1_);
/* 183 */     this.stitchSlots.add(stitcher$slot);
/* 184 */     return true;
/*     */   }
/*     */   
/*     */   public static class Holder
/*     */     implements Comparable<Holder>
/*     */   {
/*     */     private final TextureAtlasSprite theTexture;
/*     */     private final int width;
/*     */     private final int height;
/*     */     private final int mipmapLevelHolder;
/*     */     private boolean rotated;
/* 195 */     private float scaleFactor = 1.0F;
/*     */ 
/*     */     
/*     */     public Holder(TextureAtlasSprite theTextureIn, int mipmapLevelHolderIn) {
/* 199 */       this.theTexture = theTextureIn;
/* 200 */       this.width = theTextureIn.getIconWidth();
/* 201 */       this.height = theTextureIn.getIconHeight();
/* 202 */       this.mipmapLevelHolder = mipmapLevelHolderIn;
/* 203 */       this.rotated = (Stitcher.getMipmapDimension(this.height, mipmapLevelHolderIn) > Stitcher.getMipmapDimension(this.width, mipmapLevelHolderIn));
/*     */     }
/*     */ 
/*     */     
/*     */     public TextureAtlasSprite getAtlasSprite() {
/* 208 */       return this.theTexture;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getWidth() {
/* 213 */       int i = this.rotated ? this.height : this.width;
/* 214 */       return Stitcher.getMipmapDimension((int)(i * this.scaleFactor), this.mipmapLevelHolder);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getHeight() {
/* 219 */       int i = this.rotated ? this.width : this.height;
/* 220 */       return Stitcher.getMipmapDimension((int)(i * this.scaleFactor), this.mipmapLevelHolder);
/*     */     }
/*     */ 
/*     */     
/*     */     public void rotate() {
/* 225 */       this.rotated = !this.rotated;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isRotated() {
/* 230 */       return this.rotated;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setNewDimension(int p_94196_1_) {
/* 235 */       if (this.width > p_94196_1_ && this.height > p_94196_1_)
/*     */       {
/* 237 */         this.scaleFactor = p_94196_1_ / Math.min(this.width, this.height);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 243 */       return "Holder{width=" + this.width + ", height=" + this.height + ", name=" + this.theTexture.getIconName() + '}';
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(Holder p_compareTo_1_) {
/*     */       int i;
/* 250 */       if (getHeight() == p_compareTo_1_.getHeight()) {
/*     */         
/* 252 */         if (getWidth() == p_compareTo_1_.getWidth()) {
/*     */           
/* 254 */           if (this.theTexture.getIconName() == null)
/*     */           {
/* 256 */             return (p_compareTo_1_.theTexture.getIconName() == null) ? 0 : -1;
/*     */           }
/*     */           
/* 259 */           return this.theTexture.getIconName().compareTo(p_compareTo_1_.theTexture.getIconName());
/*     */         } 
/*     */         
/* 262 */         i = (getWidth() < p_compareTo_1_.getWidth()) ? 1 : -1;
/*     */       }
/*     */       else {
/*     */         
/* 266 */         i = (getHeight() < p_compareTo_1_.getHeight()) ? 1 : -1;
/*     */       } 
/*     */       
/* 269 */       return i;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Slot
/*     */   {
/*     */     private final int originX;
/*     */     private final int originY;
/*     */     private final int width;
/*     */     private final int height;
/*     */     private List<Slot> subSlots;
/*     */     private Stitcher.Holder holder;
/*     */     
/*     */     public Slot(int originXIn, int originYIn, int widthIn, int heightIn) {
/* 284 */       this.originX = originXIn;
/* 285 */       this.originY = originYIn;
/* 286 */       this.width = widthIn;
/* 287 */       this.height = heightIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public Stitcher.Holder getStitchHolder() {
/* 292 */       return this.holder;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOriginX() {
/* 297 */       return this.originX;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOriginY() {
/* 302 */       return this.originY;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addSlot(Stitcher.Holder holderIn) {
/* 307 */       if (this.holder != null)
/*     */       {
/* 309 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 313 */       int i = holderIn.getWidth();
/* 314 */       int j = holderIn.getHeight();
/*     */       
/* 316 */       if (i <= this.width && j <= this.height) {
/*     */         
/* 318 */         if (i == this.width && j == this.height) {
/*     */           
/* 320 */           this.holder = holderIn;
/* 321 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 325 */         if (this.subSlots == null) {
/*     */           
/* 327 */           this.subSlots = Lists.newArrayListWithCapacity(1);
/* 328 */           this.subSlots.add(new Slot(this.originX, this.originY, i, j));
/* 329 */           int k = this.width - i;
/* 330 */           int l = this.height - j;
/*     */           
/* 332 */           if (l > 0 && k > 0) {
/*     */             
/* 334 */             int i1 = Math.max(this.height, k);
/* 335 */             int j1 = Math.max(this.width, l);
/*     */             
/* 337 */             if (i1 >= j1)
/*     */             {
/* 339 */               this.subSlots.add(new Slot(this.originX, this.originY + j, i, l));
/* 340 */               this.subSlots.add(new Slot(this.originX + i, this.originY, k, this.height));
/*     */             }
/*     */             else
/*     */             {
/* 344 */               this.subSlots.add(new Slot(this.originX + i, this.originY, k, j));
/* 345 */               this.subSlots.add(new Slot(this.originX, this.originY + j, this.width, l));
/*     */             }
/*     */           
/* 348 */           } else if (k == 0) {
/*     */             
/* 350 */             this.subSlots.add(new Slot(this.originX, this.originY + j, i, l));
/*     */           }
/* 352 */           else if (l == 0) {
/*     */             
/* 354 */             this.subSlots.add(new Slot(this.originX + i, this.originY, k, j));
/*     */           } 
/*     */         } 
/*     */         
/* 358 */         for (Slot stitcher$slot : this.subSlots) {
/*     */           
/* 360 */           if (stitcher$slot.addSlot(holderIn))
/*     */           {
/* 362 */             return true;
/*     */           }
/*     */         } 
/*     */         
/* 366 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 371 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void getAllStitchSlots(List<Slot> p_94184_1_) {
/* 378 */       if (this.holder != null) {
/*     */         
/* 380 */         p_94184_1_.add(this);
/*     */       }
/* 382 */       else if (this.subSlots != null) {
/*     */         
/* 384 */         for (Slot stitcher$slot : this.subSlots)
/*     */         {
/* 386 */           stitcher$slot.getAllStitchSlots(p_94184_1_);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 393 */       return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\texture\Stitcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */