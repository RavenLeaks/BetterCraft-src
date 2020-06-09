/*     */ package optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CustomSky
/*     */ {
/*  16 */   private static CustomSkyLayer[][] worldSkyLayers = null;
/*     */ 
/*     */   
/*     */   public static void reset() {
/*  20 */     worldSkyLayers = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/*  25 */     reset();
/*     */     
/*  27 */     if (Config.isCustomSky())
/*     */     {
/*  29 */       worldSkyLayers = readCustomSkies();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static CustomSkyLayer[][] readCustomSkies() {
/*  35 */     CustomSkyLayer[][] acustomskylayer = new CustomSkyLayer[10][0];
/*  36 */     String s = "mcpatcher/sky/world";
/*  37 */     int i = -1;
/*     */     
/*  39 */     for (int j = 0; j < acustomskylayer.length; j++) {
/*     */       
/*  41 */       String s1 = String.valueOf(s) + j + "/sky";
/*  42 */       List<CustomSkyLayer> list = new ArrayList();
/*     */       
/*  44 */       for (int k = 1; k < 1000; k++) {
/*     */         
/*  46 */         String s2 = String.valueOf(s1) + k + ".properties";
/*     */ 
/*     */         
/*     */         try {
/*  50 */           ResourceLocation resourcelocation = new ResourceLocation(s2);
/*  51 */           InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */           
/*  53 */           if (inputstream == null) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/*  58 */           Properties properties = new Properties();
/*  59 */           properties.load(inputstream);
/*  60 */           inputstream.close();
/*  61 */           Config.dbg("CustomSky properties: " + s2);
/*  62 */           String s3 = String.valueOf(s1) + k + ".png";
/*  63 */           CustomSkyLayer customskylayer = new CustomSkyLayer(properties, s3);
/*     */           
/*  65 */           if (customskylayer.isValid(s2)) {
/*     */             
/*  67 */             ResourceLocation resourcelocation1 = new ResourceLocation(customskylayer.source);
/*  68 */             ITextureObject itextureobject = TextureUtils.getTexture(resourcelocation1);
/*     */             
/*  70 */             if (itextureobject == null)
/*     */             {
/*  72 */               Config.log("CustomSky: Texture not found: " + resourcelocation1);
/*     */             }
/*     */             else
/*     */             {
/*  76 */               customskylayer.textureId = itextureobject.getGlTextureId();
/*  77 */               list.add(customskylayer);
/*  78 */               inputstream.close();
/*     */             }
/*     */           
/*     */           } 
/*  82 */         } catch (FileNotFoundException var15) {
/*     */           
/*     */           break;
/*     */         }
/*  86 */         catch (IOException ioexception) {
/*     */           
/*  88 */           ioexception.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/*  92 */       if (list.size() > 0) {
/*     */         
/*  94 */         CustomSkyLayer[] acustomskylayer2 = list.<CustomSkyLayer>toArray(new CustomSkyLayer[list.size()]);
/*  95 */         acustomskylayer[j] = acustomskylayer2;
/*  96 */         i = j;
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     if (i < 0)
/*     */     {
/* 102 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 106 */     int l = i + 1;
/* 107 */     CustomSkyLayer[][] acustomskylayer1 = new CustomSkyLayer[l][0];
/*     */     
/* 109 */     for (int i1 = 0; i1 < acustomskylayer1.length; i1++)
/*     */     {
/* 111 */       acustomskylayer1[i1] = acustomskylayer[i1];
/*     */     }
/*     */     
/* 114 */     return acustomskylayer1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderSky(World p_renderSky_0_, TextureManager p_renderSky_1_, float p_renderSky_2_) {
/* 120 */     if (worldSkyLayers != null) {
/*     */       
/* 122 */       int i = p_renderSky_0_.provider.getDimensionType().getId();
/*     */       
/* 124 */       if (i >= 0 && i < worldSkyLayers.length) {
/*     */         
/* 126 */         CustomSkyLayer[] acustomskylayer = worldSkyLayers[i];
/*     */         
/* 128 */         if (acustomskylayer != null) {
/*     */           
/* 130 */           long j = p_renderSky_0_.getWorldTime();
/* 131 */           int k = (int)(j % 24000L);
/* 132 */           float f = p_renderSky_0_.getCelestialAngle(p_renderSky_2_);
/* 133 */           float f1 = p_renderSky_0_.getRainStrength(p_renderSky_2_);
/* 134 */           float f2 = p_renderSky_0_.getThunderStrength(p_renderSky_2_);
/*     */           
/* 136 */           if (f1 > 0.0F)
/*     */           {
/* 138 */             f2 /= f1;
/*     */           }
/*     */           
/* 141 */           for (int l = 0; l < acustomskylayer.length; l++) {
/*     */             
/* 143 */             CustomSkyLayer customskylayer = acustomskylayer[l];
/*     */             
/* 145 */             if (customskylayer.isActive(p_renderSky_0_, k))
/*     */             {
/* 147 */               customskylayer.render(k, f, f1, f2);
/*     */             }
/*     */           } 
/*     */           
/* 151 */           float f3 = 1.0F - f1;
/* 152 */           Blender.clearBlend(f3);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasSkyLayers(World p_hasSkyLayers_0_) {
/* 160 */     if (worldSkyLayers == null)
/*     */     {
/* 162 */       return false;
/*     */     }
/* 164 */     if ((Config.getGameSettings()).renderDistanceChunks < 8)
/*     */     {
/* 166 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 170 */     int i = p_hasSkyLayers_0_.provider.getDimensionType().getId();
/*     */     
/* 172 */     if (i >= 0 && i < worldSkyLayers.length) {
/*     */       
/* 174 */       CustomSkyLayer[] acustomskylayer = worldSkyLayers[i];
/*     */       
/* 176 */       if (acustomskylayer == null)
/*     */       {
/* 178 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 182 */       return (acustomskylayer.length > 0);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 187 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CustomSky.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */