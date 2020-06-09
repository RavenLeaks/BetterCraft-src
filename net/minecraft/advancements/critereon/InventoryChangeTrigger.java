/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.advancements.ICriterionInstance;
/*     */ import net.minecraft.advancements.ICriterionTrigger;
/*     */ import net.minecraft.advancements.PlayerAdvancements;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class InventoryChangeTrigger implements ICriterionTrigger<InventoryChangeTrigger.Instance> {
/*  22 */   private static final ResourceLocation field_192209_a = new ResourceLocation("inventory_changed");
/*  23 */   private final Map<PlayerAdvancements, Listeners> field_192210_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  27 */     return field_192209_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  32 */     Listeners inventorychangetrigger$listeners = this.field_192210_b.get(p_192165_1_);
/*     */     
/*  34 */     if (inventorychangetrigger$listeners == null) {
/*     */       
/*  36 */       inventorychangetrigger$listeners = new Listeners(p_192165_1_);
/*  37 */       this.field_192210_b.put(p_192165_1_, inventorychangetrigger$listeners);
/*     */     } 
/*     */     
/*  40 */     inventorychangetrigger$listeners.func_192489_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  45 */     Listeners inventorychangetrigger$listeners = this.field_192210_b.get(p_192164_1_);
/*     */     
/*  47 */     if (inventorychangetrigger$listeners != null) {
/*     */       
/*  49 */       inventorychangetrigger$listeners.func_192487_b(p_192164_2_);
/*     */       
/*  51 */       if (inventorychangetrigger$listeners.func_192488_a())
/*     */       {
/*  53 */         this.field_192210_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  60 */     this.field_192210_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  65 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_192166_1_, "slots", new JsonObject());
/*  66 */     MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(jsonobject.get("occupied"));
/*  67 */     MinMaxBounds minmaxbounds1 = MinMaxBounds.func_192515_a(jsonobject.get("full"));
/*  68 */     MinMaxBounds minmaxbounds2 = MinMaxBounds.func_192515_a(jsonobject.get("empty"));
/*  69 */     ItemPredicate[] aitempredicate = ItemPredicate.func_192494_b(p_192166_1_.get("items"));
/*  70 */     return new Instance(minmaxbounds, minmaxbounds1, minmaxbounds2, aitempredicate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192208_a(EntityPlayerMP p_192208_1_, InventoryPlayer p_192208_2_) {
/*  75 */     Listeners inventorychangetrigger$listeners = this.field_192210_b.get(p_192208_1_.func_192039_O());
/*     */     
/*  77 */     if (inventorychangetrigger$listeners != null)
/*     */     {
/*  79 */       inventorychangetrigger$listeners.func_192486_a(p_192208_2_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final MinMaxBounds field_192266_a;
/*     */     private final MinMaxBounds field_192267_b;
/*     */     private final MinMaxBounds field_192268_c;
/*     */     private final ItemPredicate[] field_192269_d;
/*     */     
/*     */     public Instance(MinMaxBounds p_i47390_1_, MinMaxBounds p_i47390_2_, MinMaxBounds p_i47390_3_, ItemPredicate[] p_i47390_4_) {
/*  92 */       super(InventoryChangeTrigger.field_192209_a);
/*  93 */       this.field_192266_a = p_i47390_1_;
/*  94 */       this.field_192267_b = p_i47390_2_;
/*  95 */       this.field_192268_c = p_i47390_3_;
/*  96 */       this.field_192269_d = p_i47390_4_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192265_a(InventoryPlayer p_192265_1_) {
/* 101 */       int i = 0;
/* 102 */       int j = 0;
/* 103 */       int k = 0;
/* 104 */       List<ItemPredicate> list = Lists.newArrayList((Object[])this.field_192269_d);
/*     */       
/* 106 */       for (int l = 0; l < p_192265_1_.getSizeInventory(); l++) {
/*     */         
/* 108 */         ItemStack itemstack = p_192265_1_.getStackInSlot(l);
/*     */         
/* 110 */         if (itemstack.func_190926_b()) {
/*     */           
/* 112 */           j++;
/*     */         }
/*     */         else {
/*     */           
/* 116 */           k++;
/*     */           
/* 118 */           if (itemstack.func_190916_E() >= itemstack.getMaxStackSize())
/*     */           {
/* 120 */             i++;
/*     */           }
/*     */           
/* 123 */           Iterator<ItemPredicate> iterator = list.iterator();
/*     */           
/* 125 */           while (iterator.hasNext()) {
/*     */             
/* 127 */             ItemPredicate itempredicate = iterator.next();
/*     */             
/* 129 */             if (itempredicate.func_192493_a(itemstack))
/*     */             {
/* 131 */               iterator.remove();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 137 */       if (!this.field_192267_b.func_192514_a(i))
/*     */       {
/* 139 */         return false;
/*     */       }
/* 141 */       if (!this.field_192268_c.func_192514_a(j))
/*     */       {
/* 143 */         return false;
/*     */       }
/* 145 */       if (!this.field_192266_a.func_192514_a(k))
/*     */       {
/* 147 */         return false;
/*     */       }
/* 149 */       if (!list.isEmpty())
/*     */       {
/* 151 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 155 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_192490_a;
/* 163 */     private final Set<ICriterionTrigger.Listener<InventoryChangeTrigger.Instance>> field_192491_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47391_1_) {
/* 167 */       this.field_192490_a = p_i47391_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192488_a() {
/* 172 */       return this.field_192491_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192489_a(ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> p_192489_1_) {
/* 177 */       this.field_192491_b.add(p_192489_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192487_b(ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> p_192487_1_) {
/* 182 */       this.field_192491_b.remove(p_192487_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192486_a(InventoryPlayer p_192486_1_) {
/* 187 */       List<ICriterionTrigger.Listener<InventoryChangeTrigger.Instance>> list = null;
/*     */       
/* 189 */       for (ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> listener : this.field_192491_b) {
/*     */         
/* 191 */         if (((InventoryChangeTrigger.Instance)listener.func_192158_a()).func_192265_a(p_192486_1_)) {
/*     */           
/* 193 */           if (list == null)
/*     */           {
/* 195 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 198 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 202 */       if (list != null)
/*     */       {
/* 204 */         for (ICriterionTrigger.Listener<InventoryChangeTrigger.Instance> listener1 : list)
/*     */         {
/* 206 */           listener1.func_192159_a(this.field_192490_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\InventoryChangeTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */