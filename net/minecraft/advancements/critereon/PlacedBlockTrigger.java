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
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class PlacedBlockTrigger
/*     */   implements ICriterionTrigger<PlacedBlockTrigger.Instance> {
/*  31 */   private static final ResourceLocation field_193174_a = new ResourceLocation("placed_block");
/*  32 */   private final Map<PlayerAdvancements, Listeners> field_193175_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  36 */     return field_193174_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  41 */     Listeners placedblocktrigger$listeners = this.field_193175_b.get(p_192165_1_);
/*     */     
/*  43 */     if (placedblocktrigger$listeners == null) {
/*     */       
/*  45 */       placedblocktrigger$listeners = new Listeners(p_192165_1_);
/*  46 */       this.field_193175_b.put(p_192165_1_, placedblocktrigger$listeners);
/*     */     } 
/*     */     
/*  49 */     placedblocktrigger$listeners.func_193490_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  54 */     Listeners placedblocktrigger$listeners = this.field_193175_b.get(p_192164_1_);
/*     */     
/*  56 */     if (placedblocktrigger$listeners != null) {
/*     */       
/*  58 */       placedblocktrigger$listeners.func_193487_b(p_192164_2_);
/*     */       
/*  60 */       if (placedblocktrigger$listeners.func_193488_a())
/*     */       {
/*  62 */         this.field_193175_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  69 */     this.field_193175_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  74 */     Block block = null;
/*     */     
/*  76 */     if (p_192166_1_.has("block")) {
/*     */       
/*  78 */       ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(p_192166_1_, "block"));
/*     */       
/*  80 */       if (!Block.REGISTRY.containsKey(resourcelocation))
/*     */       {
/*  82 */         throw new JsonSyntaxException("Unknown block type '" + resourcelocation + "'");
/*     */       }
/*     */       
/*  85 */       block = (Block)Block.REGISTRY.getObject(resourcelocation);
/*     */     } 
/*     */     
/*  88 */     Map<IProperty<?>, Object> map = null;
/*     */     
/*  90 */     if (p_192166_1_.has("state")) {
/*     */       
/*  92 */       if (block == null)
/*     */       {
/*  94 */         throw new JsonSyntaxException("Can't define block state without a specific block type");
/*     */       }
/*     */       
/*  97 */       BlockStateContainer blockstatecontainer = block.getBlockState();
/*     */       
/*  99 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)JsonUtils.getJsonObject(p_192166_1_, "state").entrySet()) {
/*     */         
/* 101 */         IProperty<?> iproperty = blockstatecontainer.getProperty(entry.getKey());
/*     */         
/* 103 */         if (iproperty == null)
/*     */         {
/* 105 */           throw new JsonSyntaxException("Unknown block state property '" + (String)entry.getKey() + "' for block '" + Block.REGISTRY.getNameForObject(block) + "'");
/*     */         }
/*     */         
/* 108 */         String s = JsonUtils.getString(entry.getValue(), entry.getKey());
/* 109 */         Optional<?> optional = iproperty.parseValue(s);
/*     */         
/* 111 */         if (!optional.isPresent())
/*     */         {
/* 113 */           throw new JsonSyntaxException("Invalid block state value '" + s + "' for property '" + (String)entry.getKey() + "' on block '" + Block.REGISTRY.getNameForObject(block) + "'");
/*     */         }
/*     */         
/* 116 */         if (map == null)
/*     */         {
/* 118 */           map = Maps.newHashMap();
/*     */         }
/*     */         
/* 121 */         map.put(iproperty, optional.get());
/*     */       } 
/*     */     } 
/*     */     
/* 125 */     LocationPredicate locationpredicate = LocationPredicate.func_193454_a(p_192166_1_.get("location"));
/* 126 */     ItemPredicate itempredicate = ItemPredicate.func_192492_a(p_192166_1_.get("item"));
/* 127 */     return new Instance(block, map, locationpredicate, itempredicate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193173_a(EntityPlayerMP p_193173_1_, BlockPos p_193173_2_, ItemStack p_193173_3_) {
/* 132 */     IBlockState iblockstate = p_193173_1_.world.getBlockState(p_193173_2_);
/* 133 */     Listeners placedblocktrigger$listeners = this.field_193175_b.get(p_193173_1_.func_192039_O());
/*     */     
/* 135 */     if (placedblocktrigger$listeners != null)
/*     */     {
/* 137 */       placedblocktrigger$listeners.func_193489_a(iblockstate, p_193173_2_, p_193173_1_.getServerWorld(), p_193173_3_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final Block field_193211_a;
/*     */     private final Map<IProperty<?>, Object> field_193212_b;
/*     */     private final LocationPredicate field_193213_c;
/*     */     private final ItemPredicate field_193214_d;
/*     */     
/*     */     public Instance(@Nullable Block p_i47566_1_, @Nullable Map<IProperty<?>, Object> p_i47566_2_, LocationPredicate p_i47566_3_, ItemPredicate p_i47566_4_) {
/* 150 */       super(PlacedBlockTrigger.field_193174_a);
/* 151 */       this.field_193211_a = p_i47566_1_;
/* 152 */       this.field_193212_b = p_i47566_2_;
/* 153 */       this.field_193213_c = p_i47566_3_;
/* 154 */       this.field_193214_d = p_i47566_4_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193210_a(IBlockState p_193210_1_, BlockPos p_193210_2_, WorldServer p_193210_3_, ItemStack p_193210_4_) {
/* 159 */       if (this.field_193211_a != null && p_193210_1_.getBlock() != this.field_193211_a)
/*     */       {
/* 161 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 165 */       if (this.field_193212_b != null)
/*     */       {
/* 167 */         for (Map.Entry<IProperty<?>, Object> entry : this.field_193212_b.entrySet()) {
/*     */           
/* 169 */           if (p_193210_1_.getValue(entry.getKey()) != entry.getValue())
/*     */           {
/* 171 */             return false;
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 176 */       if (!this.field_193213_c.func_193453_a(p_193210_3_, p_193210_2_.getX(), p_193210_2_.getY(), p_193210_2_.getZ()))
/*     */       {
/* 178 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 182 */       return this.field_193214_d.func_192493_a(p_193210_4_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_193491_a;
/*     */     
/* 191 */     private final Set<ICriterionTrigger.Listener<PlacedBlockTrigger.Instance>> field_193492_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47567_1_) {
/* 195 */       this.field_193491_a = p_i47567_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193488_a() {
/* 200 */       return this.field_193492_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193490_a(ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> p_193490_1_) {
/* 205 */       this.field_193492_b.add(p_193490_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193487_b(ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> p_193487_1_) {
/* 210 */       this.field_193492_b.remove(p_193487_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193489_a(IBlockState p_193489_1_, BlockPos p_193489_2_, WorldServer p_193489_3_, ItemStack p_193489_4_) {
/* 215 */       List<ICriterionTrigger.Listener<PlacedBlockTrigger.Instance>> list = null;
/*     */       
/* 217 */       for (ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> listener : this.field_193492_b) {
/*     */         
/* 219 */         if (((PlacedBlockTrigger.Instance)listener.func_192158_a()).func_193210_a(p_193489_1_, p_193489_2_, p_193489_3_, p_193489_4_)) {
/*     */           
/* 221 */           if (list == null)
/*     */           {
/* 223 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 226 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 230 */       if (list != null)
/*     */       {
/* 232 */         for (ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> listener1 : list)
/*     */         {
/* 234 */           listener1.func_192159_a(this.field_193491_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\PlacedBlockTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */