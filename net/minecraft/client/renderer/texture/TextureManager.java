/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import optifine.Config;
/*     */ import optifine.CustomGuis;
/*     */ import optifine.RandomMobs;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import shadersmod.client.ShadersTex;
/*     */ 
/*     */ 
/*     */ public class TextureManager
/*     */   implements ITickable, IResourceManagerReloadListener
/*     */ {
/*  27 */   private static final Logger LOGGER = LogManager.getLogger();
/*  28 */   public static final ResourceLocation field_194008_a = new ResourceLocation("");
/*  29 */   private final Map<ResourceLocation, ITextureObject> mapTextureObjects = Maps.newHashMap();
/*  30 */   private final List<ITickable> listTickables = Lists.newArrayList();
/*  31 */   private final Map<String, Integer> mapTextureCounters = Maps.newHashMap();
/*     */   
/*     */   private final IResourceManager theResourceManager;
/*     */   
/*     */   public TextureManager(IResourceManager resourceManager) {
/*  36 */     this.theResourceManager = resourceManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindTexture(ResourceLocation resource) {
/*  41 */     if (Config.isRandomMobs())
/*     */     {
/*  43 */       resource = RandomMobs.getTextureLocation(resource);
/*     */     }
/*     */     
/*  46 */     if (Config.isCustomGuis())
/*     */     {
/*  48 */       resource = CustomGuis.getTextureLocation(resource);
/*     */     }
/*     */     
/*  51 */     ITextureObject itextureobject = this.mapTextureObjects.get(resource);
/*     */     
/*  53 */     if (itextureobject == null) {
/*     */       
/*  55 */       itextureobject = new SimpleTexture(resource);
/*  56 */       loadTexture(resource, itextureobject);
/*     */     } 
/*     */     
/*  59 */     if (Config.isShaders()) {
/*     */       
/*  61 */       ShadersTex.bindTexture(itextureobject);
/*     */     }
/*     */     else {
/*     */       
/*  65 */       TextureUtil.bindTexture(itextureobject.getGlTextureId());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean loadTickableTexture(ResourceLocation textureLocation, ITickableTextureObject textureObj) {
/*  71 */     if (loadTexture(textureLocation, textureObj)) {
/*     */       
/*  73 */       this.listTickables.add(textureObj);
/*  74 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean loadTexture(ResourceLocation textureLocation, ITextureObject textureObj) {
/*  84 */     boolean flag = true;
/*     */ 
/*     */     
/*     */     try {
/*  88 */       textureObj.loadTexture(this.theResourceManager);
/*     */     }
/*  90 */     catch (IOException ioexception) {
/*     */       
/*  92 */       if (textureLocation != field_194008_a)
/*     */       {
/*  94 */         LOGGER.warn("Failed to load texture: {}", textureLocation, ioexception);
/*     */       }
/*     */       
/*  97 */       textureObj = TextureUtil.MISSING_TEXTURE;
/*  98 */       this.mapTextureObjects.put(textureLocation, textureObj);
/*  99 */       flag = false;
/*     */     }
/* 101 */     catch (Throwable throwable) {
/*     */       
/* 103 */       final ITextureObject textureObjf = textureObj;
/* 104 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Registering texture");
/* 105 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Resource location being registered");
/* 106 */       crashreportcategory.addCrashSection("Resource location", textureLocation);
/* 107 */       crashreportcategory.setDetail("Texture object class", new ICrashReportDetail<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 111 */               return textureObjf.getClass().getName();
/*     */             }
/*     */           });
/* 114 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */     
/* 117 */     this.mapTextureObjects.put(textureLocation, textureObj);
/* 118 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextureObject getTexture(ResourceLocation textureLocation) {
/* 123 */     return this.mapTextureObjects.get(textureLocation);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getDynamicTextureLocation(String name, DynamicTexture texture) {
/* 128 */     if (name.equals("logo"))
/*     */     {
/* 130 */       texture = Config.getMojangLogoTexture(texture);
/*     */     }
/*     */     
/* 133 */     Integer integer = this.mapTextureCounters.get(name);
/*     */     
/* 135 */     if (integer == null) {
/*     */       
/* 137 */       integer = Integer.valueOf(1);
/*     */     }
/*     */     else {
/*     */       
/* 141 */       integer = Integer.valueOf(integer.intValue() + 1);
/*     */     } 
/*     */     
/* 144 */     this.mapTextureCounters.put(name, integer);
/* 145 */     ResourceLocation resourcelocation = new ResourceLocation(String.format("dynamic/%s_%d", new Object[] { name, integer }));
/* 146 */     loadTexture(resourcelocation, texture);
/* 147 */     return resourcelocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 152 */     for (ITickable itickable : this.listTickables)
/*     */     {
/* 154 */       itickable.tick();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteTexture(ResourceLocation textureLocation) {
/* 160 */     ITextureObject itextureobject = getTexture(textureLocation);
/*     */     
/* 162 */     if (itextureobject != null) {
/*     */       
/* 164 */       this.mapTextureObjects.remove(textureLocation);
/* 165 */       TextureUtil.deleteTexture(itextureobject.getGlTextureId());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResourceManagerReload(IResourceManager resourceManager) {
/* 171 */     Config.dbg("*** Reloading textures ***");
/* 172 */     Config.log("Resource packs: " + Config.getResourcePackNames());
/* 173 */     Iterator<ResourceLocation> iterator = this.mapTextureObjects.keySet().iterator();
/*     */     
/* 175 */     while (iterator.hasNext()) {
/*     */       
/* 177 */       ResourceLocation resourcelocation = iterator.next();
/* 178 */       String s = resourcelocation.getResourcePath();
/*     */       
/* 180 */       if (s.startsWith("mcpatcher/") || s.startsWith("optifine/")) {
/*     */         
/* 182 */         ITextureObject itextureobject = this.mapTextureObjects.get(resourcelocation);
/*     */         
/* 184 */         if (itextureobject instanceof AbstractTexture) {
/*     */           
/* 186 */           AbstractTexture abstracttexture = (AbstractTexture)itextureobject;
/* 187 */           abstracttexture.deleteGlTexture();
/*     */         } 
/*     */         
/* 190 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     Iterator<Map.Entry<ResourceLocation, ITextureObject>> iterator1 = this.mapTextureObjects.entrySet().iterator();
/*     */     
/* 196 */     while (iterator1.hasNext()) {
/*     */       
/* 198 */       Map.Entry<ResourceLocation, ITextureObject> entry = iterator1.next();
/* 199 */       ITextureObject itextureobject1 = entry.getValue();
/*     */       
/* 201 */       if (itextureobject1 == TextureUtil.MISSING_TEXTURE) {
/*     */         
/* 203 */         iterator1.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 207 */       loadTexture(entry.getKey(), itextureobject1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reloadBannerTextures() {
/* 214 */     for (Map.Entry<ResourceLocation, ITextureObject> entry : this.mapTextureObjects.entrySet()) {
/*     */       
/* 216 */       ResourceLocation resourcelocation = entry.getKey();
/* 217 */       ITextureObject itextureobject = entry.getValue();
/*     */       
/* 219 */       if (itextureobject instanceof LayeredColorMaskTexture)
/*     */       {
/* 221 */         loadTexture(resourcelocation, itextureobject);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\texture\TextureManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */