/*     */ package net.minecraft.advancements;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ 
/*     */ public class AdvancementProgress implements Comparable<AdvancementProgress> {
/*     */   private final Map<String, CriterionProgress> field_192110_a;
/*     */   
/*     */   public AdvancementProgress() {
/*  26 */     this.field_192110_a = Maps.newHashMap();
/*  27 */     this.field_192111_b = new String[0][];
/*     */   }
/*     */   private String[][] field_192111_b;
/*     */   public void func_192099_a(Map<String, Criterion> p_192099_1_, String[][] p_192099_2_) {
/*  31 */     Set<String> set = p_192099_1_.keySet();
/*  32 */     Iterator<Map.Entry<String, CriterionProgress>> iterator = this.field_192110_a.entrySet().iterator();
/*     */     
/*  34 */     while (iterator.hasNext()) {
/*     */       
/*  36 */       Map.Entry<String, CriterionProgress> entry = iterator.next();
/*     */       
/*  38 */       if (!set.contains(entry.getKey()))
/*     */       {
/*  40 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */     
/*  44 */     for (String s : set) {
/*     */       
/*  46 */       if (!this.field_192110_a.containsKey(s))
/*     */       {
/*  48 */         this.field_192110_a.put(s, new CriterionProgress(this));
/*     */       }
/*     */     } 
/*     */     
/*  52 */     this.field_192111_b = p_192099_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192105_a() {
/*  57 */     if (this.field_192111_b.length == 0)
/*     */     {
/*  59 */       return false; } 
/*     */     byte b;
/*     */     int i;
/*     */     String[][] arrayOfString;
/*  63 */     for (i = (arrayOfString = this.field_192111_b).length, b = 0; b < i; ) { String[] astring = arrayOfString[b];
/*     */       
/*  65 */       boolean flag = false; byte b1; int j;
/*     */       String[] arrayOfString1;
/*  67 */       for (j = (arrayOfString1 = astring).length, b1 = 0; b1 < j; ) { String s = arrayOfString1[b1];
/*     */         
/*  69 */         CriterionProgress criterionprogress = func_192106_c(s);
/*     */         
/*  71 */         if (criterionprogress != null && criterionprogress.func_192151_a()) {
/*     */           
/*  73 */           flag = true;
/*     */           break;
/*     */         } 
/*     */         b1++; }
/*     */       
/*  78 */       if (!flag)
/*     */       {
/*  80 */         return false;
/*     */       }
/*     */       b++; }
/*     */     
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_192108_b() {
/*  90 */     for (CriterionProgress criterionprogress : this.field_192110_a.values()) {
/*     */       
/*  92 */       if (criterionprogress.func_192151_a())
/*     */       {
/*  94 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192109_a(String p_192109_1_) {
/* 103 */     CriterionProgress criterionprogress = this.field_192110_a.get(p_192109_1_);
/*     */     
/* 105 */     if (criterionprogress != null && !criterionprogress.func_192151_a()) {
/*     */       
/* 107 */       criterionprogress.func_192153_b();
/* 108 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_192101_b(String p_192101_1_) {
/* 118 */     CriterionProgress criterionprogress = this.field_192110_a.get(p_192101_1_);
/*     */     
/* 120 */     if (criterionprogress != null && criterionprogress.func_192151_a()) {
/*     */       
/* 122 */       criterionprogress.func_192154_c();
/* 123 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 133 */     return "AdvancementProgress{criteria=" + this.field_192110_a + ", requirements=" + Arrays.deepToString((Object[])this.field_192111_b) + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192104_a(PacketBuffer p_192104_1_) {
/* 138 */     p_192104_1_.writeVarIntToBuffer(this.field_192110_a.size());
/*     */     
/* 140 */     for (Map.Entry<String, CriterionProgress> entry : this.field_192110_a.entrySet()) {
/*     */       
/* 142 */       p_192104_1_.writeString(entry.getKey());
/* 143 */       ((CriterionProgress)entry.getValue()).func_192150_a(p_192104_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static AdvancementProgress func_192100_b(PacketBuffer p_192100_0_) {
/* 149 */     AdvancementProgress advancementprogress = new AdvancementProgress();
/* 150 */     int i = p_192100_0_.readVarIntFromBuffer();
/*     */     
/* 152 */     for (int j = 0; j < i; j++)
/*     */     {
/* 154 */       advancementprogress.field_192110_a.put(p_192100_0_.readStringFromBuffer(32767), CriterionProgress.func_192149_a(p_192100_0_, advancementprogress));
/*     */     }
/*     */     
/* 157 */     return advancementprogress;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CriterionProgress func_192106_c(String p_192106_1_) {
/* 163 */     return this.field_192110_a.get(p_192106_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_192103_c() {
/* 168 */     if (this.field_192110_a.isEmpty())
/*     */     {
/* 170 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 174 */     float f = this.field_192111_b.length;
/* 175 */     float f1 = func_194032_h();
/* 176 */     return f1 / f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String func_193126_d() {
/* 183 */     if (this.field_192110_a.isEmpty())
/*     */     {
/* 185 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 189 */     int i = this.field_192111_b.length;
/*     */     
/* 191 */     if (i <= 1)
/*     */     {
/* 193 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 197 */     int j = func_194032_h();
/* 198 */     return String.valueOf(j) + "/" + i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int func_194032_h() {
/* 205 */     int i = 0; byte b; int j;
/*     */     String[][] arrayOfString;
/* 207 */     for (j = (arrayOfString = this.field_192111_b).length, b = 0; b < j; ) { String[] astring = arrayOfString[b];
/*     */       
/* 209 */       boolean flag = false; byte b1; int k;
/*     */       String[] arrayOfString1;
/* 211 */       for (k = (arrayOfString1 = astring).length, b1 = 0; b1 < k; ) { String s = arrayOfString1[b1];
/*     */         
/* 213 */         CriterionProgress criterionprogress = func_192106_c(s);
/*     */         
/* 215 */         if (criterionprogress != null && criterionprogress.func_192151_a()) {
/*     */           
/* 217 */           flag = true;
/*     */           break;
/*     */         } 
/*     */         b1++; }
/*     */       
/* 222 */       if (flag)
/*     */       {
/* 224 */         i++;
/*     */       }
/*     */       b++; }
/*     */     
/* 228 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<String> func_192107_d() {
/* 233 */     List<String> list = Lists.newArrayList();
/*     */     
/* 235 */     for (Map.Entry<String, CriterionProgress> entry : this.field_192110_a.entrySet()) {
/*     */       
/* 237 */       if (!((CriterionProgress)entry.getValue()).func_192151_a())
/*     */       {
/* 239 */         list.add(entry.getKey());
/*     */       }
/*     */     } 
/*     */     
/* 243 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<String> func_192102_e() {
/* 248 */     List<String> list = Lists.newArrayList();
/*     */     
/* 250 */     for (Map.Entry<String, CriterionProgress> entry : this.field_192110_a.entrySet()) {
/*     */       
/* 252 */       if (((CriterionProgress)entry.getValue()).func_192151_a())
/*     */       {
/* 254 */         list.add(entry.getKey());
/*     */       }
/*     */     } 
/*     */     
/* 258 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Date func_193128_g() {
/* 264 */     Date date = null;
/*     */     
/* 266 */     for (CriterionProgress criterionprogress : this.field_192110_a.values()) {
/*     */       
/* 268 */       if (criterionprogress.func_192151_a() && (date == null || criterionprogress.func_193140_d().before(date)))
/*     */       {
/* 270 */         date = criterionprogress.func_193140_d();
/*     */       }
/*     */     } 
/*     */     
/* 274 */     return date;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(AdvancementProgress p_compareTo_1_) {
/* 279 */     Date date = func_193128_g();
/* 280 */     Date date1 = p_compareTo_1_.func_193128_g();
/*     */     
/* 282 */     if (date == null && date1 != null)
/*     */     {
/* 284 */       return 1;
/*     */     }
/* 286 */     if (date != null && date1 == null)
/*     */     {
/* 288 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 292 */     return (date == null && date1 == null) ? 0 : date.compareTo(date1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<AdvancementProgress>, JsonSerializer<AdvancementProgress>
/*     */   {
/*     */     public JsonElement serialize(AdvancementProgress p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 300 */       JsonObject jsonobject = new JsonObject();
/* 301 */       JsonObject jsonobject1 = new JsonObject();
/*     */       
/* 303 */       for (Map.Entry<String, CriterionProgress> entry : (Iterable<Map.Entry<String, CriterionProgress>>)p_serialize_1_.field_192110_a.entrySet()) {
/*     */         
/* 305 */         CriterionProgress criterionprogress = entry.getValue();
/*     */         
/* 307 */         if (criterionprogress.func_192151_a())
/*     */         {
/* 309 */           jsonobject1.add(entry.getKey(), criterionprogress.func_192148_e());
/*     */         }
/*     */       } 
/*     */       
/* 313 */       if (!jsonobject1.entrySet().isEmpty())
/*     */       {
/* 315 */         jsonobject.add("criteria", (JsonElement)jsonobject1);
/*     */       }
/*     */       
/* 318 */       jsonobject.addProperty("done", Boolean.valueOf(p_serialize_1_.func_192105_a()));
/* 319 */       return (JsonElement)jsonobject;
/*     */     }
/*     */ 
/*     */     
/*     */     public AdvancementProgress deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 324 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "advancement");
/* 325 */       JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "criteria", new JsonObject());
/* 326 */       AdvancementProgress advancementprogress = new AdvancementProgress();
/*     */       
/* 328 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject1.entrySet()) {
/*     */         
/* 330 */         String s = entry.getKey();
/* 331 */         advancementprogress.field_192110_a.put(s, CriterionProgress.func_192152_a(advancementprogress, JsonUtils.getString(entry.getValue(), s)));
/*     */       } 
/*     */       
/* 334 */       return advancementprogress;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\AdvancementProgress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */