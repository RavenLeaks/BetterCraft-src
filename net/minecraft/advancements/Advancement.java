/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.event.HoverEvent;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ public class Advancement
/*     */ {
/*     */   private final Advancement field_192076_a;
/*     */   private final DisplayInfo field_192077_b;
/*     */   private final AdvancementRewards field_192078_c;
/*     */   private final ResourceLocation field_192079_d;
/*     */   private final Map<String, Criterion> field_192080_e;
/*     */   private final String[][] field_192081_f;
/*  31 */   private final Set<Advancement> field_192082_g = Sets.newLinkedHashSet();
/*     */   
/*     */   private final ITextComponent field_193125_h;
/*     */   
/*     */   public Advancement(ResourceLocation p_i47472_1_, @Nullable Advancement p_i47472_2_, @Nullable DisplayInfo p_i47472_3_, AdvancementRewards p_i47472_4_, Map<String, Criterion> p_i47472_5_, String[][] p_i47472_6_) {
/*  36 */     this.field_192079_d = p_i47472_1_;
/*  37 */     this.field_192077_b = p_i47472_3_;
/*  38 */     this.field_192080_e = (Map<String, Criterion>)ImmutableMap.copyOf(p_i47472_5_);
/*  39 */     this.field_192076_a = p_i47472_2_;
/*  40 */     this.field_192078_c = p_i47472_4_;
/*  41 */     this.field_192081_f = p_i47472_6_;
/*     */     
/*  43 */     if (p_i47472_2_ != null)
/*     */     {
/*  45 */       p_i47472_2_.func_192071_a(this);
/*     */     }
/*     */     
/*  48 */     if (p_i47472_3_ == null) {
/*     */       
/*  50 */       this.field_193125_h = (ITextComponent)new TextComponentString(p_i47472_1_.toString());
/*     */     }
/*     */     else {
/*     */       
/*  54 */       this.field_193125_h = (ITextComponent)new TextComponentString("[");
/*  55 */       this.field_193125_h.getStyle().setColor(p_i47472_3_.func_192291_d().func_193229_c());
/*  56 */       ITextComponent itextcomponent = p_i47472_3_.func_192297_a().createCopy();
/*  57 */       TextComponentString textComponentString = new TextComponentString("");
/*  58 */       ITextComponent itextcomponent2 = itextcomponent.createCopy();
/*  59 */       itextcomponent2.getStyle().setColor(p_i47472_3_.func_192291_d().func_193229_c());
/*  60 */       textComponentString.appendSibling(itextcomponent2);
/*  61 */       textComponentString.appendText("\n");
/*  62 */       textComponentString.appendSibling(p_i47472_3_.func_193222_b());
/*  63 */       itextcomponent.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)textComponentString));
/*  64 */       this.field_193125_h.appendSibling(itextcomponent);
/*  65 */       this.field_193125_h.appendText("]");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Builder func_192075_a() {
/*  71 */     return new Builder((this.field_192076_a == null) ? null : this.field_192076_a.func_192067_g(), this.field_192077_b, this.field_192078_c, this.field_192080_e, this.field_192081_f);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Advancement func_192070_b() {
/*  77 */     return this.field_192076_a;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public DisplayInfo func_192068_c() {
/*  83 */     return this.field_192077_b;
/*     */   }
/*     */ 
/*     */   
/*     */   public AdvancementRewards func_192072_d() {
/*  88 */     return this.field_192078_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  93 */     return "SimpleAdvancement{id=" + func_192067_g() + ", parent=" + ((this.field_192076_a == null) ? "null" : (String)this.field_192076_a.func_192067_g()) + ", display=" + this.field_192077_b + ", rewards=" + this.field_192078_c + ", criteria=" + this.field_192080_e + ", requirements=" + Arrays.deepToString((Object[])this.field_192081_f) + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<Advancement> func_192069_e() {
/*  98 */     return this.field_192082_g;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Criterion> func_192073_f() {
/* 103 */     return this.field_192080_e;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_193124_g() {
/* 108 */     return this.field_192081_f.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192071_a(Advancement p_192071_1_) {
/* 113 */     this.field_192082_g.add(p_192071_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192067_g() {
/* 118 */     return this.field_192079_d;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 123 */     if (this == p_equals_1_)
/*     */     {
/* 125 */       return true;
/*     */     }
/* 127 */     if (!(p_equals_1_ instanceof Advancement))
/*     */     {
/* 129 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 133 */     Advancement advancement = (Advancement)p_equals_1_;
/* 134 */     return this.field_192079_d.equals(advancement.field_192079_d);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 140 */     return this.field_192079_d.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String[][] func_192074_h() {
/* 145 */     return this.field_192081_f;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent func_193123_j() {
/* 150 */     return this.field_193125_h;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private final ResourceLocation field_192061_a;
/*     */     private Advancement field_192062_b;
/*     */     private final DisplayInfo field_192063_c;
/*     */     private final AdvancementRewards field_192064_d;
/*     */     private final Map<String, Criterion> field_192065_e;
/*     */     private final String[][] field_192066_f;
/*     */     
/*     */     Builder(@Nullable ResourceLocation p_i47414_1_, @Nullable DisplayInfo p_i47414_2_, AdvancementRewards p_i47414_3_, Map<String, Criterion> p_i47414_4_, String[][] p_i47414_5_) {
/* 164 */       this.field_192061_a = p_i47414_1_;
/* 165 */       this.field_192063_c = p_i47414_2_;
/* 166 */       this.field_192064_d = p_i47414_3_;
/* 167 */       this.field_192065_e = p_i47414_4_;
/* 168 */       this.field_192066_f = p_i47414_5_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192058_a(Function<ResourceLocation, Advancement> p_192058_1_) {
/* 173 */       if (this.field_192061_a == null)
/*     */       {
/* 175 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 179 */       this.field_192062_b = p_192058_1_.apply(this.field_192061_a);
/* 180 */       return (this.field_192062_b != null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Advancement func_192056_a(ResourceLocation p_192056_1_) {
/* 186 */       return new Advancement(p_192056_1_, this.field_192062_b, this.field_192063_c, this.field_192064_d, this.field_192065_e, this.field_192066_f);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192057_a(PacketBuffer p_192057_1_) {
/* 191 */       if (this.field_192061_a == null) {
/*     */         
/* 193 */         p_192057_1_.writeBoolean(false);
/*     */       }
/*     */       else {
/*     */         
/* 197 */         p_192057_1_.writeBoolean(true);
/* 198 */         p_192057_1_.func_192572_a(this.field_192061_a);
/*     */       } 
/*     */       
/* 201 */       if (this.field_192063_c == null) {
/*     */         
/* 203 */         p_192057_1_.writeBoolean(false);
/*     */       }
/*     */       else {
/*     */         
/* 207 */         p_192057_1_.writeBoolean(true);
/* 208 */         this.field_192063_c.func_192290_a(p_192057_1_);
/*     */       } 
/*     */       
/* 211 */       Criterion.func_192141_a(this.field_192065_e, p_192057_1_);
/* 212 */       p_192057_1_.writeVarIntToBuffer(this.field_192066_f.length); byte b; int i;
/*     */       String[][] arrayOfString;
/* 214 */       for (i = (arrayOfString = this.field_192066_f).length, b = 0; b < i; ) { String[] astring = arrayOfString[b];
/*     */         
/* 216 */         p_192057_1_.writeVarIntToBuffer(astring.length); byte b1; int j;
/*     */         String[] arrayOfString1;
/* 218 */         for (j = (arrayOfString1 = astring).length, b1 = 0; b1 < j; ) { String s = arrayOfString1[b1];
/*     */           
/* 220 */           p_192057_1_.writeString(s);
/*     */           b1++; }
/*     */         
/*     */         b++; }
/*     */     
/*     */     }
/*     */     public String toString() {
/* 227 */       return "Task Advancement{parentId=" + this.field_192061_a + ", display=" + this.field_192063_c + ", rewards=" + this.field_192064_d + ", criteria=" + this.field_192065_e + ", requirements=" + Arrays.deepToString((Object[])this.field_192066_f) + '}';
/*     */     }
/*     */ 
/*     */     
/*     */     public static Builder func_192059_a(JsonObject p_192059_0_, JsonDeserializationContext p_192059_1_) {
/* 232 */       ResourceLocation resourcelocation = p_192059_0_.has("parent") ? new ResourceLocation(JsonUtils.getString(p_192059_0_, "parent")) : null;
/* 233 */       DisplayInfo displayinfo = p_192059_0_.has("display") ? DisplayInfo.func_192294_a(JsonUtils.getJsonObject(p_192059_0_, "display"), p_192059_1_) : null;
/* 234 */       AdvancementRewards advancementrewards = (AdvancementRewards)JsonUtils.deserializeClass(p_192059_0_, "rewards", AdvancementRewards.field_192114_a, p_192059_1_, AdvancementRewards.class);
/* 235 */       Map<String, Criterion> map = Criterion.func_192144_b(JsonUtils.getJsonObject(p_192059_0_, "criteria"), p_192059_1_);
/*     */       
/* 237 */       if (map.isEmpty())
/*     */       {
/* 239 */         throw new JsonSyntaxException("Advancement criteria cannot be empty");
/*     */       }
/*     */ 
/*     */       
/* 243 */       JsonArray jsonarray = JsonUtils.getJsonArray(p_192059_0_, "requirements", new JsonArray());
/* 244 */       String[][] astring = new String[jsonarray.size()][];
/*     */       
/* 246 */       for (int i = 0; i < jsonarray.size(); i++) {
/*     */         
/* 248 */         JsonArray jsonarray1 = JsonUtils.getJsonArray(jsonarray.get(i), "requirements[" + i + "]");
/* 249 */         astring[i] = new String[jsonarray1.size()];
/*     */         
/* 251 */         for (int k = 0; k < jsonarray1.size(); k++)
/*     */         {
/* 253 */           astring[i][k] = JsonUtils.getString(jsonarray1.get(k), "requirements[" + i + "][" + k + "]");
/*     */         }
/*     */       } 
/*     */       
/* 257 */       if (astring.length == 0) {
/*     */         
/* 259 */         astring = new String[map.size()][];
/* 260 */         int k = 0;
/*     */         
/* 262 */         for (String s2 : map.keySet()) {
/*     */           
/* 264 */           (new String[1])[0] = s2; astring[k++] = new String[1];
/*     */         } 
/*     */       }  byte b; int j;
/*     */       String[][] arrayOfString1;
/* 268 */       for (j = (arrayOfString1 = astring).length, b = 0; b < j; ) { String[] astring1 = arrayOfString1[b];
/*     */         
/* 270 */         if (astring1.length == 0 && map.isEmpty())
/*     */         {
/* 272 */           throw new JsonSyntaxException("Requirement entry cannot be empty"); }  byte b1;
/*     */         int k;
/*     */         String[] arrayOfString2;
/* 275 */         for (k = (arrayOfString2 = astring1).length, b1 = 0; b1 < k; ) { String s = arrayOfString2[b1];
/*     */           
/* 277 */           if (!map.containsKey(s))
/*     */           {
/* 279 */             throw new JsonSyntaxException("Unknown required criterion '" + s + "'"); } 
/*     */           b1++; }
/*     */         
/*     */         b++; }
/*     */       
/* 284 */       for (String s1 : map.keySet()) {
/*     */         
/* 286 */         boolean flag = false; byte b1; int k;
/*     */         String[][] arrayOfString;
/* 288 */         for (k = (arrayOfString = astring).length, b1 = 0; b1 < k; ) { String[] astring2 = arrayOfString[b1];
/*     */           
/* 290 */           if (ArrayUtils.contains((Object[])astring2, s1)) {
/*     */             
/* 292 */             flag = true;
/*     */             break;
/*     */           } 
/*     */           b1++; }
/*     */         
/* 297 */         if (!flag)
/*     */         {
/* 299 */           throw new JsonSyntaxException("Criterion '" + s1 + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required.");
/*     */         }
/*     */       } 
/*     */       
/* 303 */       return new Builder(resourcelocation, displayinfo, advancementrewards, map, astring);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static Builder func_192060_b(PacketBuffer p_192060_0_) throws IOException {
/* 309 */       ResourceLocation resourcelocation = p_192060_0_.readBoolean() ? p_192060_0_.func_192575_l() : null;
/* 310 */       DisplayInfo displayinfo = p_192060_0_.readBoolean() ? DisplayInfo.func_192295_b(p_192060_0_) : null;
/* 311 */       Map<String, Criterion> map = Criterion.func_192142_c(p_192060_0_);
/* 312 */       String[][] astring = new String[p_192060_0_.readVarIntFromBuffer()][];
/*     */       
/* 314 */       for (int i = 0; i < astring.length; i++) {
/*     */         
/* 316 */         astring[i] = new String[p_192060_0_.readVarIntFromBuffer()];
/*     */         
/* 318 */         for (int j = 0; j < (astring[i]).length; j++)
/*     */         {
/* 320 */           astring[i][j] = p_192060_0_.readStringFromBuffer(32767);
/*     */         }
/*     */       } 
/*     */       
/* 324 */       return new Builder(resourcelocation, displayinfo, AdvancementRewards.field_192114_a, map, astring);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\Advancement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */