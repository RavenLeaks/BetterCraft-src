/*     */ package optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class NaturalTextures
/*     */ {
/*  14 */   private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];
/*     */ 
/*     */   
/*     */   public static void update() {
/*  18 */     propertiesByIndex = new NaturalProperties[0];
/*     */     
/*  20 */     if (Config.isNaturalTextures()) {
/*     */       
/*  22 */       String s = "optifine/natural.properties";
/*     */ 
/*     */       
/*     */       try {
/*  26 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */         
/*  28 */         if (!Config.hasResource(resourcelocation)) {
/*     */           
/*  30 */           Config.dbg("NaturalTextures: configuration \"" + s + "\" not found");
/*     */           
/*     */           return;
/*     */         } 
/*  34 */         boolean flag = Config.isFromDefaultResourcePack(resourcelocation);
/*  35 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*  36 */         ArrayList<NaturalProperties> arraylist = new ArrayList(256);
/*  37 */         String s1 = Config.readInputStream(inputstream);
/*  38 */         inputstream.close();
/*  39 */         String[] astring = Config.tokenize(s1, "\n\r");
/*     */         
/*  41 */         if (flag) {
/*     */           
/*  43 */           Config.dbg("Natural Textures: Parsing default configuration \"" + s + "\"");
/*  44 */           Config.dbg("Natural Textures: Valid only for textures from default resource pack");
/*     */         }
/*     */         else {
/*     */           
/*  48 */           Config.dbg("Natural Textures: Parsing configuration \"" + s + "\"");
/*     */         } 
/*     */         
/*  51 */         TextureMap texturemap = TextureUtils.getTextureMapBlocks();
/*     */         
/*  53 */         for (int i = 0; i < astring.length; i++) {
/*     */           
/*  55 */           String s2 = astring[i].trim();
/*     */           
/*  57 */           if (!s2.startsWith("#")) {
/*     */             
/*  59 */             String[] astring1 = Config.tokenize(s2, "=");
/*     */             
/*  61 */             if (astring1.length != 2) {
/*     */               
/*  63 */               Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + s2);
/*     */             }
/*     */             else {
/*     */               
/*  67 */               String s3 = astring1[0].trim();
/*  68 */               String s4 = astring1[1].trim();
/*  69 */               TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe("minecraft:blocks/" + s3);
/*     */               
/*  71 */               if (textureatlassprite == null) {
/*     */                 
/*  73 */                 Config.warn("Natural Textures: Texture not found: \"" + s + "\" line: " + s2);
/*     */               }
/*     */               else {
/*     */                 
/*  77 */                 int j = textureatlassprite.getIndexInMap();
/*     */                 
/*  79 */                 if (j < 0) {
/*     */                   
/*  81 */                   Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + s2);
/*     */                 }
/*     */                 else {
/*     */                   
/*  85 */                   if (flag && !Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/" + s3 + ".png"))) {
/*     */                     return;
/*     */                   }
/*     */ 
/*     */                   
/*  90 */                   NaturalProperties naturalproperties = new NaturalProperties(s4);
/*     */                   
/*  92 */                   if (naturalproperties.isValid()) {
/*     */                     
/*  94 */                     while (arraylist.size() <= j)
/*     */                     {
/*  96 */                       arraylist.add(null);
/*     */                     }
/*     */                     
/*  99 */                     arraylist.set(j, naturalproperties);
/* 100 */                     Config.dbg("NaturalTextures: " + s3 + " = " + s4);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 108 */         propertiesByIndex = arraylist.<NaturalProperties>toArray(new NaturalProperties[arraylist.size()]);
/*     */       }
/* 110 */       catch (FileNotFoundException var17) {
/*     */         
/* 112 */         Config.warn("NaturalTextures: configuration \"" + s + "\" not found");
/*     */         
/*     */         return;
/* 115 */       } catch (Exception exception) {
/*     */         
/* 117 */         exception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static BakedQuad getNaturalTexture(BlockPos p_getNaturalTexture_0_, BakedQuad p_getNaturalTexture_1_) {
/* 124 */     TextureAtlasSprite textureatlassprite = p_getNaturalTexture_1_.getSprite();
/*     */     
/* 126 */     if (textureatlassprite == null)
/*     */     {
/* 128 */       return p_getNaturalTexture_1_;
/*     */     }
/*     */ 
/*     */     
/* 132 */     NaturalProperties naturalproperties = getNaturalProperties(textureatlassprite);
/*     */     
/* 134 */     if (naturalproperties == null)
/*     */     {
/* 136 */       return p_getNaturalTexture_1_;
/*     */     }
/*     */ 
/*     */     
/* 140 */     int i = ConnectedTextures.getSide(p_getNaturalTexture_1_.getFace());
/* 141 */     int j = Config.getRandom(p_getNaturalTexture_0_, i);
/* 142 */     int k = 0;
/* 143 */     boolean flag = false;
/*     */     
/* 145 */     if (naturalproperties.rotation > 1)
/*     */     {
/* 147 */       k = j & 0x3;
/*     */     }
/*     */     
/* 150 */     if (naturalproperties.rotation == 2)
/*     */     {
/* 152 */       k = k / 2 * 2;
/*     */     }
/*     */     
/* 155 */     if (naturalproperties.flip)
/*     */     {
/* 157 */       flag = ((j & 0x4) != 0);
/*     */     }
/*     */     
/* 160 */     return naturalproperties.getQuad(p_getNaturalTexture_1_, k, flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NaturalProperties getNaturalProperties(TextureAtlasSprite p_getNaturalProperties_0_) {
/* 167 */     if (!(p_getNaturalProperties_0_ instanceof TextureAtlasSprite))
/*     */     {
/* 169 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 173 */     int i = p_getNaturalProperties_0_.getIndexInMap();
/*     */     
/* 175 */     if (i >= 0 && i < propertiesByIndex.length) {
/*     */       
/* 177 */       NaturalProperties naturalproperties = propertiesByIndex[i];
/* 178 */       return naturalproperties;
/*     */     } 
/*     */ 
/*     */     
/* 182 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\NaturalTextures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */