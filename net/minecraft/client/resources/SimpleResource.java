/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.resources.data.IMetadataSection;
/*     */ import net.minecraft.client.resources.data.MetadataSerializer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class SimpleResource
/*     */   implements IResource
/*     */ {
/*  21 */   private final Map<String, IMetadataSection> mapMetadataSections = Maps.newHashMap();
/*     */   
/*     */   private final String resourcePackName;
/*     */   private final ResourceLocation srResourceLocation;
/*     */   private final InputStream resourceInputStream;
/*     */   private final InputStream mcmetaInputStream;
/*     */   private final MetadataSerializer srMetadataSerializer;
/*     */   private boolean mcmetaJsonChecked;
/*     */   private JsonObject mcmetaJson;
/*     */   
/*     */   public SimpleResource(String resourcePackNameIn, ResourceLocation srResourceLocationIn, InputStream resourceInputStreamIn, InputStream mcmetaInputStreamIn, MetadataSerializer srMetadataSerializerIn) {
/*  32 */     this.resourcePackName = resourcePackNameIn;
/*  33 */     this.srResourceLocation = srResourceLocationIn;
/*  34 */     this.resourceInputStream = resourceInputStreamIn;
/*  35 */     this.mcmetaInputStream = mcmetaInputStreamIn;
/*  36 */     this.srMetadataSerializer = srMetadataSerializerIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getResourceLocation() {
/*  41 */     return this.srResourceLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() {
/*  46 */     return this.resourceInputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMetadata() {
/*  51 */     return (this.mcmetaInputStream != null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends IMetadataSection> T getMetadata(String sectionName) {
/*  57 */     if (!hasMetadata())
/*     */     {
/*  59 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  63 */     if (this.mcmetaJson == null && !this.mcmetaJsonChecked) {
/*     */       
/*  65 */       this.mcmetaJsonChecked = true;
/*  66 */       BufferedReader bufferedreader = null;
/*     */ 
/*     */       
/*     */       try {
/*  70 */         bufferedreader = new BufferedReader(new InputStreamReader(this.mcmetaInputStream, StandardCharsets.UTF_8));
/*  71 */         this.mcmetaJson = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
/*     */       }
/*     */       finally {
/*     */         
/*  75 */         IOUtils.closeQuietly(bufferedreader);
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     IMetadataSection iMetadataSection = this.mapMetadataSections.get(sectionName);
/*     */     
/*  81 */     if (iMetadataSection == null)
/*     */     {
/*  83 */       iMetadataSection = this.srMetadataSerializer.parseMetadataSection(sectionName, this.mcmetaJson);
/*     */     }
/*     */     
/*  86 */     return (T)iMetadataSection;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getResourcePackName() {
/*  92 */     return this.resourcePackName;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  97 */     if (this == p_equals_1_)
/*     */     {
/*  99 */       return true;
/*     */     }
/* 101 */     if (!(p_equals_1_ instanceof SimpleResource))
/*     */     {
/* 103 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 107 */     SimpleResource simpleresource = (SimpleResource)p_equals_1_;
/*     */     
/* 109 */     if (this.srResourceLocation != null) {
/*     */       
/* 111 */       if (!this.srResourceLocation.equals(simpleresource.srResourceLocation))
/*     */       {
/* 113 */         return false;
/*     */       }
/*     */     }
/* 116 */     else if (simpleresource.srResourceLocation != null) {
/*     */       
/* 118 */       return false;
/*     */     } 
/*     */     
/* 121 */     if (this.resourcePackName != null) {
/*     */       
/* 123 */       if (!this.resourcePackName.equals(simpleresource.resourcePackName))
/*     */       {
/* 125 */         return false;
/*     */       }
/*     */     }
/* 128 */     else if (simpleresource.resourcePackName != null) {
/*     */       
/* 130 */       return false;
/*     */     } 
/*     */     
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 139 */     int i = (this.resourcePackName != null) ? this.resourcePackName.hashCode() : 0;
/* 140 */     i = 31 * i + ((this.srResourceLocation != null) ? this.srResourceLocation.hashCode() : 0);
/* 141 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 146 */     this.resourceInputStream.close();
/*     */     
/* 148 */     if (this.mcmetaInputStream != null)
/*     */     {
/* 150 */       this.mcmetaInputStream.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\SimpleResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */