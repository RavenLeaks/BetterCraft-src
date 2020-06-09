/*    */ package net.minecraft.client.renderer.block.statemap;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.UnmodifiableIterator;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*    */ 
/*    */ public abstract class StateMapperBase
/*    */   implements IStateMapper
/*    */ {
/* 14 */   protected Map<IBlockState, ModelResourceLocation> mapStateModelLocations = Maps.newLinkedHashMap();
/*    */ 
/*    */   
/*    */   public String getPropertyString(Map<IProperty<?>, Comparable<?>> values) {
/* 18 */     StringBuilder stringbuilder = new StringBuilder();
/*    */     
/* 20 */     for (Map.Entry<IProperty<?>, Comparable<?>> entry : values.entrySet()) {
/*    */       
/* 22 */       if (stringbuilder.length() != 0)
/*    */       {
/* 24 */         stringbuilder.append(",");
/*    */       }
/*    */       
/* 27 */       IProperty<?> iproperty = entry.getKey();
/* 28 */       stringbuilder.append(iproperty.getName());
/* 29 */       stringbuilder.append("=");
/* 30 */       stringbuilder.append(getPropertyName(iproperty, entry.getValue()));
/*    */     } 
/*    */     
/* 33 */     if (stringbuilder.length() == 0)
/*    */     {
/* 35 */       stringbuilder.append("normal");
/*    */     }
/*    */     
/* 38 */     return stringbuilder.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   private <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> value) {
/* 43 */     return property.getName(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
/* 48 */     UnmodifiableIterator unmodifiableiterator = blockIn.getBlockState().getValidStates().iterator();
/*    */     
/* 50 */     while (unmodifiableiterator.hasNext()) {
/*    */       
/* 52 */       IBlockState iblockstate = (IBlockState)unmodifiableiterator.next();
/* 53 */       this.mapStateModelLocations.put(iblockstate, getModelResourceLocation(iblockstate));
/*    */     } 
/*    */     
/* 56 */     return this.mapStateModelLocations;
/*    */   }
/*    */   
/*    */   protected abstract ModelResourceLocation getModelResourceLocation(IBlockState paramIBlockState);
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\statemap\StateMapperBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */