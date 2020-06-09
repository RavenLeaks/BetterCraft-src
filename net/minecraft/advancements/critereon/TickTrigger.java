/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.advancements.ICriterionInstance;
/*     */ import net.minecraft.advancements.ICriterionTrigger;
/*     */ import net.minecraft.advancements.PlayerAdvancements;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class TickTrigger implements ICriterionTrigger<TickTrigger.Instance> {
/*  17 */   public static final ResourceLocation field_193183_a = new ResourceLocation("tick");
/*  18 */   private final Map<PlayerAdvancements, Listeners> field_193184_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  22 */     return field_193183_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  27 */     Listeners ticktrigger$listeners = this.field_193184_b.get(p_192165_1_);
/*     */     
/*  29 */     if (ticktrigger$listeners == null) {
/*     */       
/*  31 */       ticktrigger$listeners = new Listeners(p_192165_1_);
/*  32 */       this.field_193184_b.put(p_192165_1_, ticktrigger$listeners);
/*     */     } 
/*     */     
/*  35 */     ticktrigger$listeners.func_193502_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  40 */     Listeners ticktrigger$listeners = this.field_193184_b.get(p_192164_1_);
/*     */     
/*  42 */     if (ticktrigger$listeners != null) {
/*     */       
/*  44 */       ticktrigger$listeners.func_193500_b(p_192164_2_);
/*     */       
/*  46 */       if (ticktrigger$listeners.func_193501_a())
/*     */       {
/*  48 */         this.field_193184_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  55 */     this.field_193184_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  60 */     return new Instance();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193182_a(EntityPlayerMP p_193182_1_) {
/*  65 */     Listeners ticktrigger$listeners = this.field_193184_b.get(p_193182_1_.func_192039_O());
/*     */     
/*  67 */     if (ticktrigger$listeners != null)
/*     */     {
/*  69 */       ticktrigger$listeners.func_193503_b();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     public Instance() {
/*  77 */       super(TickTrigger.field_193183_a);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_193504_a;
/*  84 */     private final Set<ICriterionTrigger.Listener<TickTrigger.Instance>> field_193505_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47496_1_) {
/*  88 */       this.field_193504_a = p_i47496_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193501_a() {
/*  93 */       return this.field_193505_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193502_a(ICriterionTrigger.Listener<TickTrigger.Instance> p_193502_1_) {
/*  98 */       this.field_193505_b.add(p_193502_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193500_b(ICriterionTrigger.Listener<TickTrigger.Instance> p_193500_1_) {
/* 103 */       this.field_193505_b.remove(p_193500_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193503_b() {
/* 108 */       for (ICriterionTrigger.Listener<TickTrigger.Instance> listener : (Iterable<ICriterionTrigger.Listener<TickTrigger.Instance>>)Lists.newArrayList(this.field_193505_b))
/*     */       {
/* 110 */         listener.func_192159_a(this.field_193504_a);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\TickTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */