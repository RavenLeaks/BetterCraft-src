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
/*     */ import net.minecraft.advancements.ICriterionInstance;
/*     */ import net.minecraft.advancements.ICriterionTrigger;
/*     */ import net.minecraft.advancements.PlayerAdvancements;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class RecipeUnlockedTrigger implements ICriterionTrigger<RecipeUnlockedTrigger.Instance> {
/*  22 */   private static final ResourceLocation field_192227_a = new ResourceLocation("recipe_unlocked");
/*  23 */   private final Map<PlayerAdvancements, Listeners> field_192228_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  27 */     return field_192227_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  32 */     Listeners recipeunlockedtrigger$listeners = this.field_192228_b.get(p_192165_1_);
/*     */     
/*  34 */     if (recipeunlockedtrigger$listeners == null) {
/*     */       
/*  36 */       recipeunlockedtrigger$listeners = new Listeners(p_192165_1_);
/*  37 */       this.field_192228_b.put(p_192165_1_, recipeunlockedtrigger$listeners);
/*     */     } 
/*     */     
/*  40 */     recipeunlockedtrigger$listeners.func_192528_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  45 */     Listeners recipeunlockedtrigger$listeners = this.field_192228_b.get(p_192164_1_);
/*     */     
/*  47 */     if (recipeunlockedtrigger$listeners != null) {
/*     */       
/*  49 */       recipeunlockedtrigger$listeners.func_192525_b(p_192164_2_);
/*     */       
/*  51 */       if (recipeunlockedtrigger$listeners.func_192527_a())
/*     */       {
/*  53 */         this.field_192228_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  60 */     this.field_192228_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  65 */     ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(p_192166_1_, "recipe"));
/*  66 */     IRecipe irecipe = CraftingManager.func_193373_a(resourcelocation);
/*     */     
/*  68 */     if (irecipe == null)
/*     */     {
/*  70 */       throw new JsonSyntaxException("Unknown recipe '" + resourcelocation + "'");
/*     */     }
/*     */ 
/*     */     
/*  74 */     return new Instance(irecipe);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_192225_a(EntityPlayerMP p_192225_1_, IRecipe p_192225_2_) {
/*  80 */     Listeners recipeunlockedtrigger$listeners = this.field_192228_b.get(p_192225_1_.func_192039_O());
/*     */     
/*  82 */     if (recipeunlockedtrigger$listeners != null)
/*     */     {
/*  84 */       recipeunlockedtrigger$listeners.func_193493_a(p_192225_2_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final IRecipe field_192282_a;
/*     */     
/*     */     public Instance(IRecipe p_i47526_1_) {
/*  94 */       super(RecipeUnlockedTrigger.field_192227_a);
/*  95 */       this.field_192282_a = p_i47526_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193215_a(IRecipe p_193215_1_) {
/* 100 */       return (this.field_192282_a == p_193215_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_192529_a;
/* 107 */     private final Set<ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance>> field_192530_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47397_1_) {
/* 111 */       this.field_192529_a = p_i47397_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192527_a() {
/* 116 */       return this.field_192530_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192528_a(ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance> p_192528_1_) {
/* 121 */       this.field_192530_b.add(p_192528_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192525_b(ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance> p_192525_1_) {
/* 126 */       this.field_192530_b.remove(p_192525_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193493_a(IRecipe p_193493_1_) {
/* 131 */       List<ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance>> list = null;
/*     */       
/* 133 */       for (ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance> listener : this.field_192530_b) {
/*     */         
/* 135 */         if (((RecipeUnlockedTrigger.Instance)listener.func_192158_a()).func_193215_a(p_193493_1_)) {
/*     */           
/* 137 */           if (list == null)
/*     */           {
/* 139 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 142 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 146 */       if (list != null)
/*     */       {
/* 148 */         for (ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance> listener1 : list)
/*     */         {
/* 150 */           listener1.func_192159_a(this.field_192529_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\RecipeUnlockedTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */