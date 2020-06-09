/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.FileSystems;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.util.EnumTypeAdapterFactory;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.Style;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdvancementManager
/*     */ {
/*  44 */   private static final Logger field_192782_a = LogManager.getLogger();
/*  45 */   private static final Gson field_192783_b = (new GsonBuilder()).registerTypeHierarchyAdapter(Advancement.Builder.class, new JsonDeserializer<Advancement.Builder>()
/*     */       {
/*     */         public Advancement.Builder deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
/*     */         {
/*  49 */           JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "advancement");
/*  50 */           return Advancement.Builder.func_192059_a(jsonobject, p_deserialize_3_);
/*     */         }
/*  52 */       }).registerTypeAdapter(AdvancementRewards.class, new AdvancementRewards.Deserializer()).registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer()).registerTypeHierarchyAdapter(Style.class, new Style.Serializer()).registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory()).create();
/*  53 */   private static final AdvancementList field_192784_c = new AdvancementList();
/*     */   
/*     */   private final File field_192785_d;
/*     */   private boolean field_193768_e;
/*     */   
/*     */   public AdvancementManager(@Nullable File p_i47421_1_) {
/*  59 */     this.field_192785_d = p_i47421_1_;
/*  60 */     func_192779_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192779_a() {
/*  65 */     this.field_193768_e = false;
/*  66 */     field_192784_c.func_192087_a();
/*  67 */     Map<ResourceLocation, Advancement.Builder> map = func_192781_c();
/*  68 */     func_192777_a(map);
/*  69 */     field_192784_c.func_192083_a(map);
/*     */     
/*  71 */     for (Advancement advancement : field_192784_c.func_192088_b()) {
/*     */       
/*  73 */       if (advancement.func_192068_c() != null)
/*     */       {
/*  75 */         AdvancementTreeNode.func_192323_a(advancement);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193767_b() {
/*  82 */     return this.field_193768_e;
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<ResourceLocation, Advancement.Builder> func_192781_c() {
/*  87 */     if (this.field_192785_d == null)
/*     */     {
/*  89 */       return Maps.newHashMap();
/*     */     }
/*     */ 
/*     */     
/*  93 */     Map<ResourceLocation, Advancement.Builder> map = Maps.newHashMap();
/*  94 */     this.field_192785_d.mkdirs();
/*     */     
/*  96 */     for (File file1 : FileUtils.listFiles(this.field_192785_d, new String[] { "json" }, true)) {
/*     */       
/*  98 */       String s = FilenameUtils.removeExtension(this.field_192785_d.toURI().relativize(file1.toURI()).toString());
/*  99 */       String[] astring = s.split("/", 2);
/*     */       
/* 101 */       if (astring.length == 2) {
/*     */         
/* 103 */         ResourceLocation resourcelocation = new ResourceLocation(astring[0], astring[1]);
/*     */ 
/*     */         
/*     */         try {
/* 107 */           Advancement.Builder advancement$builder = (Advancement.Builder)JsonUtils.gsonDeserialize(field_192783_b, FileUtils.readFileToString(file1, StandardCharsets.UTF_8), Advancement.Builder.class);
/*     */           
/* 109 */           if (advancement$builder == null) {
/*     */             
/* 111 */             field_192782_a.error("Couldn't load custom advancement " + resourcelocation + " from " + file1 + " as it's empty or null");
/*     */             
/*     */             continue;
/*     */           } 
/* 115 */           map.put(resourcelocation, advancement$builder);
/*     */         
/*     */         }
/* 118 */         catch (IllegalArgumentException|JsonParseException jsonparseexception) {
/*     */           
/* 120 */           field_192782_a.error("Parsing error loading custom advancement " + resourcelocation, jsonparseexception);
/* 121 */           this.field_193768_e = true;
/*     */         }
/* 123 */         catch (IOException ioexception) {
/*     */           
/* 125 */           field_192782_a.error("Couldn't read custom advancement " + resourcelocation + " from " + file1, ioexception);
/* 126 */           this.field_193768_e = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 130 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_192777_a(Map<ResourceLocation, Advancement.Builder> p_192777_1_) {
/* 136 */     FileSystem filesystem = null;
/*     */ 
/*     */     
/*     */     try {
/* 140 */       URL url = AdvancementManager.class.getResource("/assets/.mcassetsroot");
/*     */       
/* 142 */       if (url != null) {
/*     */         Path path;
/* 144 */         URI uri = url.toURI();
/*     */ 
/*     */         
/* 147 */         if ("file".equals(uri.getScheme())) {
/*     */           
/* 149 */           path = Paths.get(CraftingManager.class.getResource("/assets/minecraft/advancements").toURI());
/*     */         }
/*     */         else {
/*     */           
/* 153 */           if (!"jar".equals(uri.getScheme())) {
/*     */             
/* 155 */             field_192782_a.error("Unsupported scheme " + uri + " trying to list all built-in advancements (NYI?)");
/* 156 */             this.field_193768_e = true;
/*     */             
/*     */             return;
/*     */           } 
/* 160 */           filesystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
/* 161 */           path = filesystem.getPath("/assets/minecraft/advancements", new String[0]);
/*     */         } 
/*     */         
/* 164 */         Iterator<Path> iterator = Files.walk(path, new java.nio.file.FileVisitOption[0]).iterator();
/*     */         
/* 166 */         while (iterator.hasNext()) {
/*     */           
/* 168 */           Path path1 = iterator.next();
/*     */           
/* 170 */           if ("json".equals(FilenameUtils.getExtension(path1.toString()))) {
/*     */             
/* 172 */             Path path2 = path.relativize(path1);
/* 173 */             String s = FilenameUtils.removeExtension(path2.toString()).replaceAll("\\\\", "/");
/* 174 */             ResourceLocation resourcelocation = new ResourceLocation("minecraft", s);
/*     */             
/* 176 */             if (!p_192777_1_.containsKey(resourcelocation)) {
/*     */               
/* 178 */               BufferedReader bufferedreader = null;
/*     */ 
/*     */               
/*     */               try {
/* 182 */                 bufferedreader = Files.newBufferedReader(path1);
/* 183 */                 Advancement.Builder advancement$builder = (Advancement.Builder)JsonUtils.func_193839_a(field_192783_b, bufferedreader, Advancement.Builder.class);
/* 184 */                 p_192777_1_.put(resourcelocation, advancement$builder);
/*     */               }
/* 186 */               catch (JsonParseException jsonparseexception) {
/*     */                 
/* 188 */                 field_192782_a.error("Parsing error loading built-in advancement " + resourcelocation, (Throwable)jsonparseexception);
/* 189 */                 this.field_193768_e = true;
/*     */                 continue;
/* 191 */               } catch (IOException ioexception) {
/*     */                 
/* 193 */                 field_192782_a.error("Couldn't read advancement " + resourcelocation + " from " + path1, ioexception);
/* 194 */                 this.field_193768_e = true;
/*     */                 
/*     */                 continue;
/*     */               } finally {
/* 198 */                 IOUtils.closeQuietly(bufferedreader);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 207 */       field_192782_a.error("Couldn't find .mcassetsroot");
/* 208 */       this.field_193768_e = true;
/*     */     }
/* 210 */     catch (IOException|java.net.URISyntaxException urisyntaxexception) {
/*     */       
/* 212 */       field_192782_a.error("Couldn't get a list of all built-in advancement files", urisyntaxexception);
/* 213 */       this.field_193768_e = true;
/*     */ 
/*     */       
/*     */       return;
/*     */     } finally {
/* 218 */       IOUtils.closeQuietly(filesystem);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Advancement func_192778_a(ResourceLocation p_192778_1_) {
/* 225 */     return field_192784_c.func_192084_a(p_192778_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<Advancement> func_192780_b() {
/* 230 */     return field_192784_c.func_192089_c();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\AdvancementManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */