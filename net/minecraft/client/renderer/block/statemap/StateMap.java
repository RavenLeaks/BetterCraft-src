/*    */ package net.minecraft.client.renderer.block.statemap;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class StateMap
/*    */   extends StateMapperBase
/*    */ {
/*    */   private final IProperty<?> name;
/*    */   private final String suffix;
/*    */   private final List<IProperty<?>> ignored;
/*    */   
/*    */   private StateMap(@Nullable IProperty<?> name, @Nullable String suffix, List<IProperty<?>> ignored) {
/* 23 */     this.name = name;
/* 24 */     this.suffix = suffix;
/* 25 */     this.ignored = ignored;
/*    */   }
/*    */   
/*    */   protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
/*    */     String s;
/* 30 */     Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap((Map)state.getProperties());
/*    */ 
/*    */     
/* 33 */     if (this.name == null) {
/*    */       
/* 35 */       s = ((ResourceLocation)Block.REGISTRY.getNameForObject(state.getBlock())).toString();
/*    */     }
/*    */     else {
/*    */       
/* 39 */       s = removeName(this.name, map);
/*    */     } 
/*    */     
/* 42 */     if (this.suffix != null)
/*    */     {
/* 44 */       s = String.valueOf(s) + this.suffix;
/*    */     }
/*    */     
/* 47 */     for (IProperty<?> iproperty : this.ignored)
/*    */     {
/* 49 */       map.remove(iproperty);
/*    */     }
/*    */     
/* 52 */     return new ModelResourceLocation(s, getPropertyString(map));
/*    */   }
/*    */ 
/*    */   
/*    */   private <T extends Comparable<T>> String removeName(IProperty<T> p_187490_1_, Map<IProperty<?>, Comparable<?>> p_187490_2_) {
/* 57 */     return p_187490_1_.getName(p_187490_2_.remove(this.name));
/*    */   }
/*    */   
/*    */   public static class Builder
/*    */   {
/*    */     private IProperty<?> name;
/*    */     private String suffix;
/* 64 */     private final List<IProperty<?>> ignored = Lists.newArrayList();
/*    */ 
/*    */     
/*    */     public Builder withName(IProperty<?> builderPropertyIn) {
/* 68 */       this.name = builderPropertyIn;
/* 69 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public Builder withSuffix(String builderSuffixIn) {
/* 74 */       this.suffix = builderSuffixIn;
/* 75 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public Builder ignore(IProperty... p_178442_1_) {
/* 80 */       Collections.addAll(this.ignored, (IProperty<?>[])p_178442_1_);
/* 81 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public StateMap build() {
/* 86 */       return new StateMap(this.name, this.suffix, this.ignored, null);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\statemap\StateMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */