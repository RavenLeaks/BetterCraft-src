/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.io.Files;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonParser;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourceIndex
/*    */ {
/* 25 */   private static final Logger LOGGER = LogManager.getLogger();
/* 26 */   private final Map<String, File> resourceMap = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceIndex() {}
/*    */ 
/*    */   
/*    */   public ResourceIndex(File assetsFolder, String indexName) {
/* 34 */     File file1 = new File(assetsFolder, "objects");
/* 35 */     File file2 = new File(assetsFolder, "indexes/" + indexName + ".json");
/* 36 */     BufferedReader bufferedreader = null;
/*    */ 
/*    */     
/*    */     try {
/* 40 */       bufferedreader = Files.newReader(file2, StandardCharsets.UTF_8);
/* 41 */       JsonObject jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
/* 42 */       JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "objects", null);
/*    */       
/* 44 */       if (jsonobject1 != null)
/*    */       {
/* 46 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject1.entrySet())
/*    */         {
/* 48 */           JsonObject jsonobject2 = (JsonObject)entry.getValue();
/* 49 */           String s = entry.getKey();
/* 50 */           String[] astring = s.split("/", 2);
/* 51 */           String s1 = (astring.length == 1) ? astring[0] : (String.valueOf(astring[0]) + ":" + astring[1]);
/* 52 */           String s2 = JsonUtils.getString(jsonobject2, "hash");
/* 53 */           File file3 = new File(file1, String.valueOf(s2.substring(0, 2)) + "/" + s2);
/* 54 */           this.resourceMap.put(s1, file3);
/*    */         }
/*    */       
/*    */       }
/* 58 */     } catch (JsonParseException var20) {
/*    */       
/* 60 */       LOGGER.error("Unable to parse resource index file: {}", file2);
/*    */     }
/* 62 */     catch (FileNotFoundException var21) {
/*    */       
/* 64 */       LOGGER.error("Can't find the resource index file: {}", file2);
/*    */     }
/*    */     finally {
/*    */       
/* 68 */       IOUtils.closeQuietly(bufferedreader);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public File getFile(ResourceLocation location) {
/* 75 */     String s = location.toString();
/* 76 */     return this.resourceMap.get(s);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFileExisting(ResourceLocation location) {
/* 81 */     File file1 = getFile(location);
/* 82 */     return (file1 != null && file1.isFile());
/*    */   }
/*    */ 
/*    */   
/*    */   public File getPackMcmeta() {
/* 87 */     return this.resourceMap.get("pack.mcmeta");
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\ResourceIndex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */