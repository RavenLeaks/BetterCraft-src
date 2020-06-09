/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.tileentity.BannerPattern;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class BannerTextures
/*    */ {
/* 18 */   public static final Cache BANNER_DESIGNS = new Cache("B", new ResourceLocation("textures/entity/banner_base.png"), "textures/entity/banner/");
/*    */ 
/*    */   
/* 21 */   public static final Cache SHIELD_DESIGNS = new Cache("S", new ResourceLocation("textures/entity/shield_base.png"), "textures/entity/shield/");
/* 22 */   public static final ResourceLocation SHIELD_BASE_TEXTURE = new ResourceLocation("textures/entity/shield_base_nopattern.png");
/* 23 */   public static final ResourceLocation BANNER_BASE_TEXTURE = new ResourceLocation("textures/entity/banner/base.png");
/*    */   
/*    */   public static class Cache
/*    */   {
/* 27 */     private final Map<String, BannerTextures.CacheEntry> cacheMap = Maps.newLinkedHashMap();
/*    */     
/*    */     private final ResourceLocation cacheResourceLocation;
/*    */     private final String cacheResourceBase;
/*    */     private final String cacheId;
/*    */     
/*    */     public Cache(String id, ResourceLocation baseResource, String resourcePath) {
/* 34 */       this.cacheId = id;
/* 35 */       this.cacheResourceLocation = baseResource;
/* 36 */       this.cacheResourceBase = resourcePath;
/*    */     }
/*    */ 
/*    */     
/*    */     @Nullable
/*    */     public ResourceLocation getResourceLocation(String id, List<BannerPattern> patternList, List<EnumDyeColor> colorList) {
/* 42 */       if (id.isEmpty())
/*    */       {
/* 44 */         return null;
/*    */       }
/*    */ 
/*    */       
/* 48 */       id = String.valueOf(this.cacheId) + id;
/* 49 */       BannerTextures.CacheEntry bannertextures$cacheentry = this.cacheMap.get(id);
/*    */       
/* 51 */       if (bannertextures$cacheentry == null) {
/*    */         
/* 53 */         if (this.cacheMap.size() >= 256 && !freeCacheSlot())
/*    */         {
/* 55 */           return BannerTextures.BANNER_BASE_TEXTURE;
/*    */         }
/*    */         
/* 58 */         List<String> list = Lists.newArrayList();
/*    */         
/* 60 */         for (BannerPattern bannerpattern : patternList)
/*    */         {
/* 62 */           list.add(String.valueOf(this.cacheResourceBase) + bannerpattern.func_190997_a() + ".png");
/*    */         }
/*    */         
/* 65 */         bannertextures$cacheentry = new BannerTextures.CacheEntry(null);
/* 66 */         bannertextures$cacheentry.textureLocation = new ResourceLocation(id);
/* 67 */         Minecraft.getMinecraft().getTextureManager().loadTexture(bannertextures$cacheentry.textureLocation, (ITextureObject)new LayeredColorMaskTexture(this.cacheResourceLocation, list, colorList));
/* 68 */         this.cacheMap.put(id, bannertextures$cacheentry);
/*    */       } 
/*    */       
/* 71 */       bannertextures$cacheentry.lastUseMillis = System.currentTimeMillis();
/* 72 */       return bannertextures$cacheentry.textureLocation;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     private boolean freeCacheSlot() {
/* 78 */       long i = System.currentTimeMillis();
/* 79 */       Iterator<String> iterator = this.cacheMap.keySet().iterator();
/*    */       
/* 81 */       while (iterator.hasNext()) {
/*    */         
/* 83 */         String s = iterator.next();
/* 84 */         BannerTextures.CacheEntry bannertextures$cacheentry = this.cacheMap.get(s);
/*    */         
/* 86 */         if (i - bannertextures$cacheentry.lastUseMillis > 5000L) {
/*    */           
/* 88 */           Minecraft.getMinecraft().getTextureManager().deleteTexture(bannertextures$cacheentry.textureLocation);
/* 89 */           iterator.remove();
/* 90 */           return true;
/*    */         } 
/*    */       } 
/*    */       
/* 94 */       return (this.cacheMap.size() < 256);
/*    */     }
/*    */   }
/*    */   
/*    */   static class CacheEntry {
/*    */     public long lastUseMillis;
/*    */     public ResourceLocation textureLocation;
/*    */     
/*    */     private CacheEntry() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\BannerTextures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */