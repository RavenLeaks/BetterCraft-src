/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.resources.data.MetadataSerializer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SimpleReloadableResourceManager
/*     */   implements IReloadableResourceManager {
/*  22 */   private static final Logger LOGGER = LogManager.getLogger();
/*  23 */   private static final Joiner JOINER_RESOURCE_PACKS = Joiner.on(", ");
/*  24 */   private final Map<String, FallbackResourceManager> domainResourceManagers = Maps.newHashMap();
/*  25 */   private final List<IResourceManagerReloadListener> reloadListeners = Lists.newArrayList();
/*  26 */   private final Set<String> setResourceDomains = Sets.newLinkedHashSet();
/*     */   
/*     */   private final MetadataSerializer rmMetadataSerializer;
/*     */   
/*     */   public SimpleReloadableResourceManager(MetadataSerializer rmMetadataSerializerIn) {
/*  31 */     this.rmMetadataSerializer = rmMetadataSerializerIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadResourcePack(IResourcePack resourcePack) {
/*  36 */     for (String s : resourcePack.getResourceDomains()) {
/*     */       
/*  38 */       this.setResourceDomains.add(s);
/*  39 */       FallbackResourceManager fallbackresourcemanager = this.domainResourceManagers.get(s);
/*     */       
/*  41 */       if (fallbackresourcemanager == null) {
/*     */         
/*  43 */         fallbackresourcemanager = new FallbackResourceManager(this.rmMetadataSerializer);
/*  44 */         this.domainResourceManagers.put(s, fallbackresourcemanager);
/*     */       } 
/*     */       
/*  47 */       fallbackresourcemanager.addResourcePack(resourcePack);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getResourceDomains() {
/*  53 */     return this.setResourceDomains;
/*     */   }
/*     */ 
/*     */   
/*     */   public IResource getResource(ResourceLocation location) throws IOException {
/*  58 */     IResourceManager iresourcemanager = this.domainResourceManagers.get(location.getResourceDomain());
/*     */     
/*  60 */     if (iresourcemanager != null)
/*     */     {
/*  62 */       return iresourcemanager.getResource(location);
/*     */     }
/*     */ 
/*     */     
/*  66 */     throw new FileNotFoundException(location.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IResource> getAllResources(ResourceLocation location) throws IOException {
/*  72 */     IResourceManager iresourcemanager = this.domainResourceManagers.get(location.getResourceDomain());
/*     */     
/*  74 */     if (iresourcemanager != null)
/*     */     {
/*  76 */       return iresourcemanager.getAllResources(location);
/*     */     }
/*     */ 
/*     */     
/*  80 */     throw new FileNotFoundException(location.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void clearResources() {
/*  86 */     this.domainResourceManagers.clear();
/*  87 */     this.setResourceDomains.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadResources(List<IResourcePack> resourcesPacksList) {
/*  92 */     clearResources();
/*  93 */     LOGGER.info("Reloading ResourceManager: {}", JOINER_RESOURCE_PACKS.join(Iterables.transform(resourcesPacksList, new Function<IResourcePack, String>()
/*     */             {
/*     */               public String apply(@Nullable IResourcePack p_apply_1_)
/*     */               {
/*  97 */                 return (p_apply_1_ == null) ? "<NULL>" : p_apply_1_.getPackName();
/*     */               }
/*     */             })));
/*     */     
/* 101 */     for (IResourcePack iresourcepack : resourcesPacksList)
/*     */     {
/* 103 */       reloadResourcePack(iresourcepack);
/*     */     }
/*     */     
/* 106 */     notifyReloadListeners();
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerReloadListener(IResourceManagerReloadListener reloadListener) {
/* 111 */     this.reloadListeners.add(reloadListener);
/* 112 */     reloadListener.onResourceManagerReload(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void notifyReloadListeners() {
/* 117 */     for (IResourceManagerReloadListener iresourcemanagerreloadlistener : this.reloadListeners)
/*     */     {
/* 119 */       iresourcemanagerreloadlistener.onResourceManagerReload(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\SimpleReloadableResourceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */