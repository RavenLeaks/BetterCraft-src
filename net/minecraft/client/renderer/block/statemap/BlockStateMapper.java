/*    */ package net.minecraft.client.renderer.block.statemap;
/*    */ 
/*    */ import com.google.common.base.MoreObjects;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class BlockStateMapper
/*    */ {
/* 16 */   private final Map<Block, IStateMapper> blockStateMap = Maps.newIdentityHashMap();
/* 17 */   private final Set<Block> setBuiltInBlocks = Sets.newIdentityHashSet();
/*    */ 
/*    */   
/*    */   public void registerBlockStateMapper(Block blockIn, IStateMapper stateMapper) {
/* 21 */     this.blockStateMap.put(blockIn, stateMapper);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerBuiltInBlocks(Block... blockIn) {
/* 26 */     Collections.addAll(this.setBuiltInBlocks, blockIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<IBlockState, ModelResourceLocation> putAllStateModelLocations() {
/* 31 */     Map<IBlockState, ModelResourceLocation> map = Maps.newIdentityHashMap();
/*    */     
/* 33 */     for (Block block : Block.REGISTRY)
/*    */     {
/* 35 */       map.putAll(getVariants(block));
/*    */     }
/*    */     
/* 38 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<ResourceLocation> getBlockstateLocations(Block blockIn) {
/* 43 */     if (this.setBuiltInBlocks.contains(blockIn))
/*    */     {
/* 45 */       return Collections.emptySet();
/*    */     }
/*    */ 
/*    */     
/* 49 */     IStateMapper istatemapper = this.blockStateMap.get(blockIn);
/*    */     
/* 51 */     if (istatemapper == null)
/*    */     {
/* 53 */       return Collections.singleton((ResourceLocation)Block.REGISTRY.getNameForObject(blockIn));
/*    */     }
/*    */ 
/*    */     
/* 57 */     Set<ResourceLocation> set = Sets.newHashSet();
/*    */     
/* 59 */     for (ModelResourceLocation modelresourcelocation : istatemapper.putStateModelLocations(blockIn).values())
/*    */     {
/* 61 */       set.add(new ResourceLocation(modelresourcelocation.getResourceDomain(), modelresourcelocation.getResourcePath()));
/*    */     }
/*    */     
/* 64 */     return set;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<IBlockState, ModelResourceLocation> getVariants(Block blockIn) {
/* 71 */     return this.setBuiltInBlocks.contains(blockIn) ? Collections.<IBlockState, ModelResourceLocation>emptyMap() : ((IStateMapper)MoreObjects.firstNonNull(this.blockStateMap.get(blockIn), new DefaultStateMapper())).putStateModelLocations(blockIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\statemap\BlockStateMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */