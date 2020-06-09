/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.advancements.ICriterionInstance;
/*     */ import net.minecraft.advancements.ICriterionTrigger;
/*     */ import net.minecraft.advancements.PlayerAdvancements;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class PositionTrigger implements ICriterionTrigger<PositionTrigger.Instance> {
/*  20 */   private final Map<PlayerAdvancements, Listeners> field_192218_b = Maps.newHashMap();
/*     */   private final ResourceLocation field_192217_a;
/*     */   
/*     */   public PositionTrigger(ResourceLocation p_i47432_1_) {
/*  24 */     this.field_192217_a = p_i47432_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  29 */     return this.field_192217_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  34 */     Listeners positiontrigger$listeners = this.field_192218_b.get(p_192165_1_);
/*     */     
/*  36 */     if (positiontrigger$listeners == null) {
/*     */       
/*  38 */       positiontrigger$listeners = new Listeners(p_192165_1_);
/*  39 */       this.field_192218_b.put(p_192165_1_, positiontrigger$listeners);
/*     */     } 
/*     */     
/*  42 */     positiontrigger$listeners.func_192510_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  47 */     Listeners positiontrigger$listeners = this.field_192218_b.get(p_192164_1_);
/*     */     
/*  49 */     if (positiontrigger$listeners != null) {
/*     */       
/*  51 */       positiontrigger$listeners.func_192507_b(p_192164_2_);
/*     */       
/*  53 */       if (positiontrigger$listeners.func_192508_a())
/*     */       {
/*  55 */         this.field_192218_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  62 */     this.field_192218_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  67 */     LocationPredicate locationpredicate = LocationPredicate.func_193454_a((JsonElement)p_192166_1_);
/*  68 */     return new Instance(this.field_192217_a, locationpredicate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192215_a(EntityPlayerMP p_192215_1_) {
/*  73 */     Listeners positiontrigger$listeners = this.field_192218_b.get(p_192215_1_.func_192039_O());
/*     */     
/*  75 */     if (positiontrigger$listeners != null)
/*     */     {
/*  77 */       positiontrigger$listeners.func_193462_a(p_192215_1_.getServerWorld(), p_192215_1_.posX, p_192215_1_.posY, p_192215_1_.posZ);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final LocationPredicate field_193205_a;
/*     */     
/*     */     public Instance(ResourceLocation p_i47544_1_, LocationPredicate p_i47544_2_) {
/*  87 */       super(p_i47544_1_);
/*  88 */       this.field_193205_a = p_i47544_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193204_a(WorldServer p_193204_1_, double p_193204_2_, double p_193204_4_, double p_193204_6_) {
/*  93 */       return this.field_193205_a.func_193452_a(p_193204_1_, p_193204_2_, p_193204_4_, p_193204_6_);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_192511_a;
/* 100 */     private final Set<ICriterionTrigger.Listener<PositionTrigger.Instance>> field_192512_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47442_1_) {
/* 104 */       this.field_192511_a = p_i47442_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192508_a() {
/* 109 */       return this.field_192512_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192510_a(ICriterionTrigger.Listener<PositionTrigger.Instance> p_192510_1_) {
/* 114 */       this.field_192512_b.add(p_192510_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192507_b(ICriterionTrigger.Listener<PositionTrigger.Instance> p_192507_1_) {
/* 119 */       this.field_192512_b.remove(p_192507_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193462_a(WorldServer p_193462_1_, double p_193462_2_, double p_193462_4_, double p_193462_6_) {
/* 124 */       List<ICriterionTrigger.Listener<PositionTrigger.Instance>> list = null;
/*     */       
/* 126 */       for (ICriterionTrigger.Listener<PositionTrigger.Instance> listener : this.field_192512_b) {
/*     */         
/* 128 */         if (((PositionTrigger.Instance)listener.func_192158_a()).func_193204_a(p_193462_1_, p_193462_2_, p_193462_4_, p_193462_6_)) {
/*     */           
/* 130 */           if (list == null)
/*     */           {
/* 132 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 135 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 139 */       if (list != null)
/*     */       {
/* 141 */         for (ICriterionTrigger.Listener<PositionTrigger.Instance> listener1 : list)
/*     */         {
/* 143 */           listener1.func_192159_a(this.field_192511_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\PositionTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */