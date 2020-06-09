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
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class ConstructBeaconTrigger implements ICriterionTrigger<ConstructBeaconTrigger.Instance> {
/*  19 */   private static final ResourceLocation field_192181_a = new ResourceLocation("construct_beacon");
/*  20 */   private final Map<PlayerAdvancements, Listeners> field_192182_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  24 */     return field_192181_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  29 */     Listeners constructbeacontrigger$listeners = this.field_192182_b.get(p_192165_1_);
/*     */     
/*  31 */     if (constructbeacontrigger$listeners == null) {
/*     */       
/*  33 */       constructbeacontrigger$listeners = new Listeners(p_192165_1_);
/*  34 */       this.field_192182_b.put(p_192165_1_, constructbeacontrigger$listeners);
/*     */     } 
/*     */     
/*  37 */     constructbeacontrigger$listeners.func_192355_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  42 */     Listeners constructbeacontrigger$listeners = this.field_192182_b.get(p_192164_1_);
/*     */     
/*  44 */     if (constructbeacontrigger$listeners != null) {
/*     */       
/*  46 */       constructbeacontrigger$listeners.func_192353_b(p_192164_2_);
/*     */       
/*  48 */       if (constructbeacontrigger$listeners.func_192354_a())
/*     */       {
/*  50 */         this.field_192182_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  57 */     this.field_192182_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  62 */     MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(p_192166_1_.get("level"));
/*  63 */     return new Instance(minmaxbounds);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192180_a(EntityPlayerMP p_192180_1_, TileEntityBeacon p_192180_2_) {
/*  68 */     Listeners constructbeacontrigger$listeners = this.field_192182_b.get(p_192180_1_.func_192039_O());
/*     */     
/*  70 */     if (constructbeacontrigger$listeners != null)
/*     */     {
/*  72 */       constructbeacontrigger$listeners.func_192352_a(p_192180_2_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final MinMaxBounds field_192253_a;
/*     */     
/*     */     public Instance(MinMaxBounds p_i47373_1_) {
/*  82 */       super(ConstructBeaconTrigger.field_192181_a);
/*  83 */       this.field_192253_a = p_i47373_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192252_a(TileEntityBeacon p_192252_1_) {
/*  88 */       return this.field_192253_a.func_192514_a(p_192252_1_.func_191979_s());
/*     */     }
/*     */   }
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_192356_a;
/*  95 */     private final Set<ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance>> field_192357_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47374_1_) {
/*  99 */       this.field_192356_a = p_i47374_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192354_a() {
/* 104 */       return this.field_192357_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192355_a(ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> p_192355_1_) {
/* 109 */       this.field_192357_b.add(p_192355_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192353_b(ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> p_192353_1_) {
/* 114 */       this.field_192357_b.remove(p_192353_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192352_a(TileEntityBeacon p_192352_1_) {
/* 119 */       List<ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance>> list = null;
/*     */       
/* 121 */       for (ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> listener : this.field_192357_b) {
/*     */         
/* 123 */         if (((ConstructBeaconTrigger.Instance)listener.func_192158_a()).func_192252_a(p_192352_1_)) {
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
/* 136 */         for (ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> listener1 : list)
/*     */         {
/* 138 */           listener1.func_192159_a(this.field_192356_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\ConstructBeaconTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */