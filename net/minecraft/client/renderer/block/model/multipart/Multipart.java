/*     */ package net.minecraft.client.renderer.block.model.multipart;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.client.renderer.block.model.VariantList;
/*     */ 
/*     */ 
/*     */ public class Multipart
/*     */ {
/*     */   private final List<Selector> selectors;
/*     */   private BlockStateContainer stateContainer;
/*     */   
/*     */   public Multipart(List<Selector> selectorsIn) {
/*  23 */     this.selectors = selectorsIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Selector> getSelectors() {
/*  28 */     return this.selectors;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<VariantList> getVariants() {
/*  33 */     Set<VariantList> set = Sets.newHashSet();
/*     */     
/*  35 */     for (Selector selector : this.selectors)
/*     */     {
/*  37 */       set.add(selector.getVariantList());
/*     */     }
/*     */     
/*  40 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStateContainer(BlockStateContainer stateContainerIn) {
/*  45 */     this.stateContainer = stateContainerIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockStateContainer getStateContainer() {
/*  50 */     return this.stateContainer;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  55 */     if (this == p_equals_1_)
/*     */     {
/*  57 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  61 */     if (p_equals_1_ instanceof Multipart) {
/*     */       
/*  63 */       Multipart multipart = (Multipart)p_equals_1_;
/*     */       
/*  65 */       if (this.selectors.equals(multipart.selectors)) {
/*     */         
/*  67 */         if (this.stateContainer == null)
/*     */         {
/*  69 */           return (multipart.stateContainer == null);
/*     */         }
/*     */         
/*  72 */         return this.stateContainer.equals(multipart.stateContainer);
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  82 */     return 31 * this.selectors.hashCode() + ((this.stateContainer == null) ? 0 : this.stateContainer.hashCode());
/*     */   }
/*     */   
/*     */   public static class Deserializer
/*     */     implements JsonDeserializer<Multipart>
/*     */   {
/*     */     public Multipart deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*  89 */       return new Multipart(getSelectors(p_deserialize_3_, p_deserialize_1_.getAsJsonArray()));
/*     */     }
/*     */ 
/*     */     
/*     */     private List<Selector> getSelectors(JsonDeserializationContext context, JsonArray elements) {
/*  94 */       List<Selector> list = Lists.newArrayList();
/*     */       
/*  96 */       for (JsonElement jsonelement : elements)
/*     */       {
/*  98 */         list.add((Selector)context.deserialize(jsonelement, Selector.class));
/*     */       }
/*     */       
/* 101 */       return list;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\multipart\Multipart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */