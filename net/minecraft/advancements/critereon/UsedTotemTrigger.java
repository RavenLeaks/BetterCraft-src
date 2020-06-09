/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.advancements.ICriterionInstance;
/*     */ import net.minecraft.advancements.ICriterionTrigger;
/*     */ import net.minecraft.advancements.PlayerAdvancements;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class UsedTotemTrigger implements ICriterionTrigger<UsedTotemTrigger.Instance> {
/*  19 */   private static final ResourceLocation field_193188_a = new ResourceLocation("used_totem");
/*  20 */   private final Map<PlayerAdvancements, Listeners> field_193189_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  24 */     return field_193188_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  29 */     Listeners usedtotemtrigger$listeners = this.field_193189_b.get(p_192165_1_);
/*     */     
/*  31 */     if (usedtotemtrigger$listeners == null) {
/*     */       
/*  33 */       usedtotemtrigger$listeners = new Listeners(p_192165_1_);
/*  34 */       this.field_193189_b.put(p_192165_1_, usedtotemtrigger$listeners);
/*     */     } 
/*     */     
/*  37 */     usedtotemtrigger$listeners.func_193508_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  42 */     Listeners usedtotemtrigger$listeners = this.field_193189_b.get(p_192164_1_);
/*     */     
/*  44 */     if (usedtotemtrigger$listeners != null) {
/*     */       
/*  46 */       usedtotemtrigger$listeners.func_193506_b(p_192164_2_);
/*     */       
/*  48 */       if (usedtotemtrigger$listeners.func_193507_a())
/*     */       {
/*  50 */         this.field_193189_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  57 */     this.field_193189_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  62 */     ItemPredicate itempredicate = ItemPredicate.func_192492_a(p_192166_1_.get("item"));
/*  63 */     return new Instance(itempredicate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193187_a(EntityPlayerMP p_193187_1_, ItemStack p_193187_2_) {
/*  68 */     Listeners usedtotemtrigger$listeners = this.field_193189_b.get(p_193187_1_.func_192039_O());
/*     */     
/*  70 */     if (usedtotemtrigger$listeners != null)
/*     */     {
/*  72 */       usedtotemtrigger$listeners.func_193509_a(p_193187_2_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final ItemPredicate field_193219_a;
/*     */     
/*     */     public Instance(ItemPredicate p_i47564_1_) {
/*  82 */       super(UsedTotemTrigger.field_193188_a);
/*  83 */       this.field_193219_a = p_i47564_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193218_a(ItemStack p_193218_1_) {
/*  88 */       return this.field_193219_a.func_192493_a(p_193218_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_193510_a;
/*  95 */     private final Set<ICriterionTrigger.Listener<UsedTotemTrigger.Instance>> field_193511_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47565_1_) {
/*  99 */       this.field_193510_a = p_i47565_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193507_a() {
/* 104 */       return this.field_193511_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193508_a(ICriterionTrigger.Listener<UsedTotemTrigger.Instance> p_193508_1_) {
/* 109 */       this.field_193511_b.add(p_193508_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193506_b(ICriterionTrigger.Listener<UsedTotemTrigger.Instance> p_193506_1_) {
/* 114 */       this.field_193511_b.remove(p_193506_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193509_a(ItemStack p_193509_1_) {
/* 119 */       List<ICriterionTrigger.Listener<UsedTotemTrigger.Instance>> list = null;
/*     */       
/* 121 */       for (ICriterionTrigger.Listener<UsedTotemTrigger.Instance> listener : this.field_193511_b) {
/*     */         
/* 123 */         if (((UsedTotemTrigger.Instance)listener.func_192158_a()).func_193218_a(p_193509_1_)) {
/*     */           
/* 125 */           if (list == null)
/*     */           {
/* 127 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 130 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 134 */       if (list != null)
/*     */       {
/* 136 */         for (ICriterionTrigger.Listener<UsedTotemTrigger.Instance> listener1 : list)
/*     */         {
/* 138 */           listener1.func_192159_a(this.field_193510_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\UsedTotemTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */