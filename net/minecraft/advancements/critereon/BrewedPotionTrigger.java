/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.ICriterionInstance;
/*     */ import net.minecraft.advancements.ICriterionTrigger;
/*     */ import net.minecraft.advancements.PlayerAdvancements;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.potion.PotionType;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class BrewedPotionTrigger implements ICriterionTrigger<BrewedPotionTrigger.Instance> {
/*  22 */   private static final ResourceLocation field_192176_a = new ResourceLocation("brewed_potion");
/*  23 */   private final Map<PlayerAdvancements, Listeners> field_192177_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  27 */     return field_192176_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  32 */     Listeners brewedpotiontrigger$listeners = this.field_192177_b.get(p_192165_1_);
/*     */     
/*  34 */     if (brewedpotiontrigger$listeners == null) {
/*     */       
/*  36 */       brewedpotiontrigger$listeners = new Listeners(p_192165_1_);
/*  37 */       this.field_192177_b.put(p_192165_1_, brewedpotiontrigger$listeners);
/*     */     } 
/*     */     
/*  40 */     brewedpotiontrigger$listeners.func_192349_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  45 */     Listeners brewedpotiontrigger$listeners = this.field_192177_b.get(p_192164_1_);
/*     */     
/*  47 */     if (brewedpotiontrigger$listeners != null) {
/*     */       
/*  49 */       brewedpotiontrigger$listeners.func_192346_b(p_192164_2_);
/*     */       
/*  51 */       if (brewedpotiontrigger$listeners.func_192347_a())
/*     */       {
/*  53 */         this.field_192177_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  60 */     this.field_192177_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  65 */     PotionType potiontype = null;
/*     */     
/*  67 */     if (p_192166_1_.has("potion")) {
/*     */       
/*  69 */       ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(p_192166_1_, "potion"));
/*     */       
/*  71 */       if (!PotionType.REGISTRY.containsKey(resourcelocation))
/*     */       {
/*  73 */         throw new JsonSyntaxException("Unknown potion '" + resourcelocation + "'");
/*     */       }
/*     */       
/*  76 */       potiontype = (PotionType)PotionType.REGISTRY.getObject(resourcelocation);
/*     */     } 
/*     */     
/*  79 */     return new Instance(potiontype);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192173_a(EntityPlayerMP p_192173_1_, PotionType p_192173_2_) {
/*  84 */     Listeners brewedpotiontrigger$listeners = this.field_192177_b.get(p_192173_1_.func_192039_O());
/*     */     
/*  86 */     if (brewedpotiontrigger$listeners != null)
/*     */     {
/*  88 */       brewedpotiontrigger$listeners.func_192348_a(p_192173_2_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final PotionType field_192251_a;
/*     */     
/*     */     public Instance(@Nullable PotionType p_i47398_1_) {
/*  98 */       super(BrewedPotionTrigger.field_192176_a);
/*  99 */       this.field_192251_a = p_i47398_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192250_a(PotionType p_192250_1_) {
/* 104 */       return !(this.field_192251_a != null && this.field_192251_a != p_192250_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_192350_a;
/* 111 */     private final Set<ICriterionTrigger.Listener<BrewedPotionTrigger.Instance>> field_192351_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47399_1_) {
/* 115 */       this.field_192350_a = p_i47399_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192347_a() {
/* 120 */       return this.field_192351_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192349_a(ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> p_192349_1_) {
/* 125 */       this.field_192351_b.add(p_192349_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192346_b(ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> p_192346_1_) {
/* 130 */       this.field_192351_b.remove(p_192346_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192348_a(PotionType p_192348_1_) {
/* 135 */       List<ICriterionTrigger.Listener<BrewedPotionTrigger.Instance>> list = null;
/*     */       
/* 137 */       for (ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> listener : this.field_192351_b) {
/*     */         
/* 139 */         if (((BrewedPotionTrigger.Instance)listener.func_192158_a()).func_192250_a(p_192348_1_)) {
/*     */           
/* 141 */           if (list == null)
/*     */           {
/* 143 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 146 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 150 */       if (list != null)
/*     */       {
/* 152 */         for (ICriterionTrigger.Listener<BrewedPotionTrigger.Instance> listener1 : list)
/*     */         {
/* 154 */           listener1.func_192159_a(this.field_192350_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\BrewedPotionTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */