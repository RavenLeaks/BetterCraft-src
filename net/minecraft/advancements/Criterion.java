/*    */ package net.minecraft.advancements;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSyntaxException;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Criterion
/*    */ {
/*    */   private final ICriterionInstance field_192147_a;
/*    */   
/*    */   public Criterion(ICriterionInstance p_i47470_1_) {
/* 21 */     this.field_192147_a = p_i47470_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public Criterion() {
/* 26 */     this.field_192147_a = null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_192140_a(PacketBuffer p_192140_1_) {}
/*    */ 
/*    */   
/*    */   public static Criterion func_192145_a(JsonObject p_192145_0_, JsonDeserializationContext p_192145_1_) {
/* 35 */     ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(p_192145_0_, "trigger"));
/* 36 */     ICriterionTrigger<?> icriteriontrigger = CriteriaTriggers.func_192119_a(resourcelocation);
/*    */     
/* 38 */     if (icriteriontrigger == null)
/*    */     {
/* 40 */       throw new JsonSyntaxException("Invalid criterion trigger: " + resourcelocation);
/*    */     }
/*    */ 
/*    */     
/* 44 */     ICriterionInstance icriterioninstance = (ICriterionInstance)icriteriontrigger.func_192166_a(JsonUtils.getJsonObject(p_192145_0_, "conditions", new JsonObject()), p_192145_1_);
/* 45 */     return new Criterion(icriterioninstance);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Criterion func_192146_b(PacketBuffer p_192146_0_) {
/* 51 */     return new Criterion();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Map<String, Criterion> func_192144_b(JsonObject p_192144_0_, JsonDeserializationContext p_192144_1_) {
/* 56 */     Map<String, Criterion> map = Maps.newHashMap();
/*    */     
/* 58 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)p_192144_0_.entrySet())
/*    */     {
/* 60 */       map.put(entry.getKey(), func_192145_a(JsonUtils.getJsonObject(entry.getValue(), "criterion"), p_192144_1_));
/*    */     }
/*    */     
/* 63 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Map<String, Criterion> func_192142_c(PacketBuffer p_192142_0_) {
/* 68 */     Map<String, Criterion> map = Maps.newHashMap();
/* 69 */     int i = p_192142_0_.readVarIntFromBuffer();
/*    */     
/* 71 */     for (int j = 0; j < i; j++)
/*    */     {
/* 73 */       map.put(p_192142_0_.readStringFromBuffer(32767), func_192146_b(p_192142_0_));
/*    */     }
/*    */     
/* 76 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void func_192141_a(Map<String, Criterion> p_192141_0_, PacketBuffer p_192141_1_) {
/* 81 */     p_192141_1_.writeVarIntToBuffer(p_192141_0_.size());
/*    */     
/* 83 */     for (Map.Entry<String, Criterion> entry : p_192141_0_.entrySet()) {
/*    */       
/* 85 */       p_192141_1_.writeString(entry.getKey());
/* 86 */       ((Criterion)entry.getValue()).func_192140_a(p_192141_1_);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ICriterionInstance func_192143_a() {
/* 93 */     return this.field_192147_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\Criterion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */