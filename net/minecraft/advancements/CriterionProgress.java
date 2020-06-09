/*    */ package net.minecraft.advancements;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonNull;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import com.google.gson.JsonSyntaxException;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ 
/*    */ public class CriterionProgress
/*    */ {
/* 14 */   private static final SimpleDateFormat field_192155_a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
/*    */   
/*    */   private final AdvancementProgress field_192156_b;
/*    */   private Date field_192157_c;
/*    */   
/*    */   public CriterionProgress(AdvancementProgress p_i47469_1_) {
/* 20 */     this.field_192156_b = p_i47469_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_192151_a() {
/* 25 */     return (this.field_192157_c != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_192153_b() {
/* 30 */     this.field_192157_c = new Date();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_192154_c() {
/* 35 */     this.field_192157_c = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Date func_193140_d() {
/* 40 */     return this.field_192157_c;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 45 */     return "CriterionProgress{obtained=" + ((this.field_192157_c == null) ? "false" : (String)this.field_192157_c) + '}';
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_192150_a(PacketBuffer p_192150_1_) {
/* 50 */     p_192150_1_.writeBoolean((this.field_192157_c != null));
/*    */     
/* 52 */     if (this.field_192157_c != null)
/*    */     {
/* 54 */       p_192150_1_.func_192574_a(this.field_192157_c);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement func_192148_e() {
/* 60 */     return (this.field_192157_c != null) ? (JsonElement)new JsonPrimitive(field_192155_a.format(this.field_192157_c)) : (JsonElement)JsonNull.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public static CriterionProgress func_192149_a(PacketBuffer p_192149_0_, AdvancementProgress p_192149_1_) {
/* 65 */     CriterionProgress criterionprogress = new CriterionProgress(p_192149_1_);
/*    */     
/* 67 */     if (p_192149_0_.readBoolean())
/*    */     {
/* 69 */       criterionprogress.field_192157_c = p_192149_0_.func_192573_m();
/*    */     }
/*    */     
/* 72 */     return criterionprogress;
/*    */   }
/*    */ 
/*    */   
/*    */   public static CriterionProgress func_192152_a(AdvancementProgress p_192152_0_, String p_192152_1_) {
/* 77 */     CriterionProgress criterionprogress = new CriterionProgress(p_192152_0_);
/*    */ 
/*    */     
/*    */     try {
/* 81 */       criterionprogress.field_192157_c = field_192155_a.parse(p_192152_1_);
/* 82 */       return criterionprogress;
/*    */     }
/* 84 */     catch (ParseException parseexception) {
/*    */       
/* 86 */       throw new JsonSyntaxException("Invalid datetime: " + p_192152_1_, parseexception);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\CriterionProgress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */