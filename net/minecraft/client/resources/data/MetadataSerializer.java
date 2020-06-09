/*    */ package net.minecraft.client.resources.data;
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import net.minecraft.util.EnumTypeAdapterFactory;
/*    */ import net.minecraft.util.registry.IRegistry;
/*    */ import net.minecraft.util.registry.RegistrySimple;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.Style;
/*    */ 
/*    */ public class MetadataSerializer {
/* 14 */   private final IRegistry<String, Registration<? extends IMetadataSection>> metadataSectionSerializerRegistry = (IRegistry<String, Registration<? extends IMetadataSection>>)new RegistrySimple();
/* 15 */   private final GsonBuilder gsonBuilder = new GsonBuilder();
/*    */ 
/*    */ 
/*    */   
/*    */   private Gson gson;
/*    */ 
/*    */ 
/*    */   
/*    */   public MetadataSerializer() {
/* 24 */     this.gsonBuilder.registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer());
/* 25 */     this.gsonBuilder.registerTypeHierarchyAdapter(Style.class, new Style.Serializer());
/* 26 */     this.gsonBuilder.registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory());
/*    */   }
/*    */ 
/*    */   
/*    */   public <T extends IMetadataSection> void registerMetadataSectionType(IMetadataSectionSerializer<T> metadataSectionSerializer, Class<T> clazz) {
/* 31 */     this.metadataSectionSerializerRegistry.putObject(metadataSectionSerializer.getSectionName(), new Registration<>(metadataSectionSerializer, clazz, null));
/* 32 */     this.gsonBuilder.registerTypeAdapter(clazz, metadataSectionSerializer);
/* 33 */     this.gson = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T extends IMetadataSection> T parseMetadataSection(String sectionName, JsonObject json) {
/* 38 */     if (sectionName == null)
/*    */     {
/* 40 */       throw new IllegalArgumentException("Metadata section name cannot be null");
/*    */     }
/* 42 */     if (!json.has(sectionName))
/*    */     {
/* 44 */       return null;
/*    */     }
/* 46 */     if (!json.get(sectionName).isJsonObject())
/*    */     {
/* 48 */       throw new IllegalArgumentException("Invalid metadata for '" + sectionName + "' - expected object, found " + json.get(sectionName));
/*    */     }
/*    */ 
/*    */     
/* 52 */     Registration<?> registration = (Registration)this.metadataSectionSerializerRegistry.getObject(sectionName);
/*    */     
/* 54 */     if (registration == null)
/*    */     {
/* 56 */       throw new IllegalArgumentException("Don't know how to handle metadata section '" + sectionName + "'");
/*    */     }
/*    */ 
/*    */     
/* 60 */     return (T)getGson().fromJson((JsonElement)json.getAsJsonObject(sectionName), registration.clazz);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Gson getGson() {
/* 70 */     if (this.gson == null)
/*    */     {
/* 72 */       this.gson = this.gsonBuilder.create();
/*    */     }
/*    */     
/* 75 */     return this.gson;
/*    */   }
/*    */ 
/*    */   
/*    */   class Registration<T extends IMetadataSection>
/*    */   {
/*    */     final IMetadataSectionSerializer<T> section;
/*    */     final Class<T> clazz;
/*    */     
/*    */     private Registration(IMetadataSectionSerializer<T> metadataSectionSerializer, Class<T> clazzToRegister) {
/* 85 */       this.section = metadataSectionSerializer;
/* 86 */       this.clazz = clazzToRegister;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\data\MetadataSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */