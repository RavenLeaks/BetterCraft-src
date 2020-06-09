/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.common.base.Optional;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.ICriterionInstance;
/*     */ import net.minecraft.advancements.ICriterionTrigger;
/*     */ import net.minecraft.advancements.PlayerAdvancements;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class EnterBlockTrigger
/*     */   implements ICriterionTrigger<EnterBlockTrigger.Instance> {
/*  28 */   private static final ResourceLocation field_192196_a = new ResourceLocation("enter_block");
/*  29 */   private final Map<PlayerAdvancements, Listeners> field_192197_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  33 */     return field_192196_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  38 */     Listeners enterblocktrigger$listeners = this.field_192197_b.get(p_192165_1_);
/*     */     
/*  40 */     if (enterblocktrigger$listeners == null) {
/*     */       
/*  42 */       enterblocktrigger$listeners = new Listeners(p_192165_1_);
/*  43 */       this.field_192197_b.put(p_192165_1_, enterblocktrigger$listeners);
/*     */     } 
/*     */     
/*  46 */     enterblocktrigger$listeners.func_192472_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  51 */     Listeners enterblocktrigger$listeners = this.field_192197_b.get(p_192164_1_);
/*     */     
/*  53 */     if (enterblocktrigger$listeners != null) {
/*     */       
/*  55 */       enterblocktrigger$listeners.func_192469_b(p_192164_2_);
/*     */       
/*  57 */       if (enterblocktrigger$listeners.func_192470_a())
/*     */       {
/*  59 */         this.field_192197_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  66 */     this.field_192197_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  71 */     Block block = null;
/*     */     
/*  73 */     if (p_192166_1_.has("block")) {
/*     */       
/*  75 */       ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(p_192166_1_, "block"));
/*     */       
/*  77 */       if (!Block.REGISTRY.containsKey(resourcelocation))
/*     */       {
/*  79 */         throw new JsonSyntaxException("Unknown block type '" + resourcelocation + "'");
/*     */       }
/*     */       
/*  82 */       block = (Block)Block.REGISTRY.getObject(resourcelocation);
/*     */     } 
/*     */     
/*  85 */     Map<IProperty<?>, Object> map = null;
/*     */     
/*  87 */     if (p_192166_1_.has("state")) {
/*     */       
/*  89 */       if (block == null)
/*     */       {
/*  91 */         throw new JsonSyntaxException("Can't define block state without a specific block type");
/*     */       }
/*     */       
/*  94 */       BlockStateContainer blockstatecontainer = block.getBlockState();
/*     */       
/*  96 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)JsonUtils.getJsonObject(p_192166_1_, "state").entrySet()) {
/*     */         
/*  98 */         IProperty<?> iproperty = blockstatecontainer.getProperty(entry.getKey());
/*     */         
/* 100 */         if (iproperty == null)
/*     */         {
/* 102 */           throw new JsonSyntaxException("Unknown block state property '" + (String)entry.getKey() + "' for block '" + Block.REGISTRY.getNameForObject(block) + "'");
/*     */         }
/*     */         
/* 105 */         String s = JsonUtils.getString(entry.getValue(), entry.getKey());
/* 106 */         Optional<?> optional = iproperty.parseValue(s);
/*     */         
/* 108 */         if (!optional.isPresent())
/*     */         {
/* 110 */           throw new JsonSyntaxException("Invalid block state value '" + s + "' for property '" + (String)entry.getKey() + "' on block '" + Block.REGISTRY.getNameForObject(block) + "'");
/*     */         }
/*     */         
/* 113 */         if (map == null)
/*     */         {
/* 115 */           map = Maps.newHashMap();
/*     */         }
/*     */         
/* 118 */         map.put(iproperty, optional.get());
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     return new Instance(block, map);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192193_a(EntityPlayerMP p_192193_1_, IBlockState p_192193_2_) {
/* 127 */     Listeners enterblocktrigger$listeners = this.field_192197_b.get(p_192193_1_.func_192039_O());
/*     */     
/* 129 */     if (enterblocktrigger$listeners != null)
/*     */     {
/* 131 */       enterblocktrigger$listeners.func_192471_a(p_192193_2_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final Block field_192261_a;
/*     */     private final Map<IProperty<?>, Object> field_192262_b;
/*     */     
/*     */     public Instance(@Nullable Block p_i47451_1_, @Nullable Map<IProperty<?>, Object> p_i47451_2_) {
/* 142 */       super(EnterBlockTrigger.field_192196_a);
/* 143 */       this.field_192261_a = p_i47451_1_;
/* 144 */       this.field_192262_b = p_i47451_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192260_a(IBlockState p_192260_1_) {
/* 149 */       if (this.field_192261_a != null && p_192260_1_.getBlock() != this.field_192261_a)
/*     */       {
/* 151 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 155 */       if (this.field_192262_b != null)
/*     */       {
/* 157 */         for (Map.Entry<IProperty<?>, Object> entry : this.field_192262_b.entrySet()) {
/*     */           
/* 159 */           if (p_192260_1_.getValue(entry.getKey()) != entry.getValue())
/*     */           {
/* 161 */             return false;
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 166 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_192473_a;
/* 174 */     private final Set<ICriterionTrigger.Listener<EnterBlockTrigger.Instance>> field_192474_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47452_1_) {
/* 178 */       this.field_192473_a = p_i47452_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192470_a() {
/* 183 */       return this.field_192474_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192472_a(ICriterionTrigger.Listener<EnterBlockTrigger.Instance> p_192472_1_) {
/* 188 */       this.field_192474_b.add(p_192472_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192469_b(ICriterionTrigger.Listener<EnterBlockTrigger.Instance> p_192469_1_) {
/* 193 */       this.field_192474_b.remove(p_192469_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192471_a(IBlockState p_192471_1_) {
/* 198 */       List<ICriterionTrigger.Listener<EnterBlockTrigger.Instance>> list = null;
/*     */       
/* 200 */       for (ICriterionTrigger.Listener<EnterBlockTrigger.Instance> listener : this.field_192474_b) {
/*     */         
/* 202 */         if (((EnterBlockTrigger.Instance)listener.func_192158_a()).func_192260_a(p_192471_1_)) {
/*     */           
/* 204 */           if (list == null)
/*     */           {
/* 206 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 209 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 213 */       if (list != null)
/*     */       {
/* 215 */         for (ICriterionTrigger.Listener<EnterBlockTrigger.Instance> listener1 : list)
/*     */         {
/* 217 */           listener1.func_192159_a(this.field_192473_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\EnterBlockTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */