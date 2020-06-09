/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.datafix.IFixableData;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ 
/*     */ public class SignStrictJSON
/*     */   implements IFixableData {
/*  20 */   public static final Gson GSON_INSTANCE = (new GsonBuilder()).registerTypeAdapter(ITextComponent.class, new JsonDeserializer<ITextComponent>()
/*     */       {
/*     */         public ITextComponent deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
/*     */         {
/*  24 */           if (p_deserialize_1_.isJsonPrimitive())
/*     */           {
/*  26 */             return (ITextComponent)new TextComponentString(p_deserialize_1_.getAsString());
/*     */           }
/*  28 */           if (p_deserialize_1_.isJsonArray()) {
/*     */             
/*  30 */             JsonArray jsonarray = p_deserialize_1_.getAsJsonArray();
/*  31 */             ITextComponent itextcomponent = null;
/*     */             
/*  33 */             for (JsonElement jsonelement : jsonarray) {
/*     */               
/*  35 */               ITextComponent itextcomponent1 = deserialize(jsonelement, jsonelement.getClass(), p_deserialize_3_);
/*     */               
/*  37 */               if (itextcomponent == null) {
/*     */                 
/*  39 */                 itextcomponent = itextcomponent1;
/*     */                 
/*     */                 continue;
/*     */               } 
/*  43 */               itextcomponent.appendSibling(itextcomponent1);
/*     */             } 
/*     */ 
/*     */             
/*  47 */             return itextcomponent;
/*     */           } 
/*     */ 
/*     */           
/*  51 */           throw new JsonParseException("Don't know how to turn " + p_deserialize_1_ + " into a Component");
/*     */         }
/*  54 */       }).create();
/*     */ 
/*     */   
/*     */   public int getFixVersion() {
/*  58 */     return 101;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/*  63 */     if ("Sign".equals(compound.getString("id"))) {
/*     */       
/*  65 */       updateLine(compound, "Text1");
/*  66 */       updateLine(compound, "Text2");
/*  67 */       updateLine(compound, "Text3");
/*  68 */       updateLine(compound, "Text4");
/*     */     } 
/*     */     
/*  71 */     return compound;
/*     */   }
/*     */   
/*     */   private void updateLine(NBTTagCompound compound, String key) {
/*     */     TextComponentString textComponentString;
/*  76 */     String s = compound.getString(key);
/*  77 */     ITextComponent itextcomponent = null;
/*     */     
/*  79 */     if (!"null".equals(s) && !StringUtils.isNullOrEmpty(s)) {
/*     */       
/*  81 */       if ((s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') || (s.charAt(0) == '{' && s.charAt(s.length() - 1) == '}')) {
/*     */         TextComponentString textComponentString1;
/*     */         ITextComponent iTextComponent;
/*     */         try {
/*  85 */           itextcomponent = (ITextComponent)JsonUtils.gsonDeserialize(GSON_INSTANCE, s, ITextComponent.class, true);
/*     */           
/*  87 */           if (itextcomponent == null)
/*     */           {
/*  89 */             textComponentString1 = new TextComponentString("");
/*     */           }
/*     */         }
/*  92 */         catch (JsonParseException jsonParseException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  97 */         if (textComponentString1 == null) {
/*     */           
/*     */           try {
/*     */             
/* 101 */             iTextComponent = ITextComponent.Serializer.jsonToComponent(s);
/*     */           }
/* 103 */           catch (JsonParseException jsonParseException) {}
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 109 */         if (iTextComponent == null) {
/*     */           
/*     */           try {
/*     */             
/* 113 */             iTextComponent = ITextComponent.Serializer.fromJsonLenient(s);
/*     */           }
/* 115 */           catch (JsonParseException jsonParseException) {}
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 121 */         if (iTextComponent == null)
/*     */         {
/* 123 */           textComponentString = new TextComponentString(s);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 128 */         textComponentString = new TextComponentString(s);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 133 */       textComponentString = new TextComponentString("");
/*     */     } 
/*     */     
/* 136 */     compound.setString(key, ITextComponent.Serializer.componentToJson((ITextComponent)textComponentString));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\SignStrictJSON.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */