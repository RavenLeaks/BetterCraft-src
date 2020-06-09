/*     */ package net.minecraft.world.storage.loot;
/*     */ 
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.common.io.Resources;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.storage.loot.conditions.LootCondition;
/*     */ import net.minecraft.world.storage.loot.conditions.LootConditionManager;
/*     */ import net.minecraft.world.storage.loot.functions.LootFunction;
/*     */ import net.minecraft.world.storage.loot.functions.LootFunctionManager;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class LootTableManager
/*     */ {
/*  27 */   private static final Logger LOGGER = LogManager.getLogger();
/*  28 */   private static final Gson GSON_INSTANCE = (new GsonBuilder()).registerTypeAdapter(RandomValueRange.class, new RandomValueRange.Serializer()).registerTypeAdapter(LootPool.class, new LootPool.Serializer()).registerTypeAdapter(LootTable.class, new LootTable.Serializer()).registerTypeHierarchyAdapter(LootEntry.class, new LootEntry.Serializer()).registerTypeHierarchyAdapter(LootFunction.class, new LootFunctionManager.Serializer()).registerTypeHierarchyAdapter(LootCondition.class, new LootConditionManager.Serializer()).registerTypeHierarchyAdapter(LootContext.EntityTarget.class, new LootContext.EntityTarget.Serializer()).create();
/*  29 */   private final LoadingCache<ResourceLocation, LootTable> registeredLootTables = CacheBuilder.newBuilder().build(new Loader(null));
/*     */   
/*     */   private final File baseFolder;
/*     */   
/*     */   public LootTableManager(@Nullable File folder) {
/*  34 */     this.baseFolder = folder;
/*  35 */     reloadLootTables();
/*     */   }
/*     */ 
/*     */   
/*     */   public LootTable getLootTableFromLocation(ResourceLocation ressources) {
/*  40 */     return (LootTable)this.registeredLootTables.getUnchecked(ressources);
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadLootTables() {
/*  45 */     this.registeredLootTables.invalidateAll();
/*     */     
/*  47 */     for (ResourceLocation resourcelocation : LootTableList.getAll())
/*     */     {
/*  49 */       getLootTableFromLocation(resourcelocation);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class Loader
/*     */     extends CacheLoader<ResourceLocation, LootTable>
/*     */   {
/*     */     private Loader() {}
/*     */ 
/*     */     
/*     */     public LootTable load(ResourceLocation p_load_1_) throws Exception {
/*  61 */       if (p_load_1_.getResourcePath().contains(".")) {
/*     */         
/*  63 */         LootTableManager.LOGGER.debug("Invalid loot table name '{}' (can't contain periods)", p_load_1_);
/*  64 */         return LootTable.EMPTY_LOOT_TABLE;
/*     */       } 
/*     */ 
/*     */       
/*  68 */       LootTable loottable = loadLootTable(p_load_1_);
/*     */       
/*  70 */       if (loottable == null)
/*     */       {
/*  72 */         loottable = loadBuiltinLootTable(p_load_1_);
/*     */       }
/*     */       
/*  75 */       if (loottable == null) {
/*     */         
/*  77 */         loottable = LootTable.EMPTY_LOOT_TABLE;
/*  78 */         LootTableManager.LOGGER.warn("Couldn't find resource table {}", p_load_1_);
/*     */       } 
/*     */       
/*  81 */       return loottable;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private LootTable loadLootTable(ResourceLocation resource) {
/*  88 */       if (LootTableManager.this.baseFolder == null)
/*     */       {
/*  90 */         return null;
/*     */       }
/*     */ 
/*     */       
/*  94 */       File file1 = new File(new File(LootTableManager.this.baseFolder, resource.getResourceDomain()), String.valueOf(resource.getResourcePath()) + ".json");
/*     */       
/*  96 */       if (file1.exists()) {
/*     */         
/*  98 */         if (file1.isFile()) {
/*     */           String s;
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 104 */             s = Files.toString(file1, StandardCharsets.UTF_8);
/*     */           }
/* 106 */           catch (IOException ioexception) {
/*     */             
/* 108 */             LootTableManager.LOGGER.warn("Couldn't load loot table {} from {}", resource, file1, ioexception);
/* 109 */             return LootTable.EMPTY_LOOT_TABLE;
/*     */           } 
/*     */ 
/*     */           
/*     */           try {
/* 114 */             return (LootTable)JsonUtils.gsonDeserialize(LootTableManager.GSON_INSTANCE, s, LootTable.class);
/*     */           }
/* 116 */           catch (IllegalArgumentException|JsonParseException jsonparseexception) {
/*     */             
/* 118 */             LootTableManager.LOGGER.error("Couldn't load loot table {} from {}", resource, file1, jsonparseexception);
/* 119 */             return LootTable.EMPTY_LOOT_TABLE;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 124 */         LootTableManager.LOGGER.warn("Expected to find loot table {} at {} but it was a folder.", resource, file1);
/* 125 */         return LootTable.EMPTY_LOOT_TABLE;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 130 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private LootTable loadBuiltinLootTable(ResourceLocation resource) {
/* 138 */       URL url = LootTableManager.class.getResource("/assets/" + resource.getResourceDomain() + "/loot_tables/" + resource.getResourcePath() + ".json");
/*     */       
/* 140 */       if (url != null) {
/*     */         String s;
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 146 */           s = Resources.toString(url, StandardCharsets.UTF_8);
/*     */         }
/* 148 */         catch (IOException ioexception) {
/*     */           
/* 150 */           LootTableManager.LOGGER.warn("Couldn't load loot table {} from {}", resource, url, ioexception);
/* 151 */           return LootTable.EMPTY_LOOT_TABLE;
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 156 */           return (LootTable)JsonUtils.gsonDeserialize(LootTableManager.GSON_INSTANCE, s, LootTable.class);
/*     */         }
/* 158 */         catch (JsonParseException jsonparseexception) {
/*     */           
/* 160 */           LootTableManager.LOGGER.error("Couldn't load loot table {} from {}", resource, url, jsonparseexception);
/* 161 */           return LootTable.EMPTY_LOOT_TABLE;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 166 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\LootTableManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */