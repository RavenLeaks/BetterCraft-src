/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class ItemPickaxe
/*    */   extends ItemTool {
/* 12 */   private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet((Object[])new Block[] { Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, (Block)Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, (Block)Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE });
/*    */ 
/*    */   
/*    */   protected ItemPickaxe(Item.ToolMaterial material) {
/* 16 */     super(1.0F, -2.8F, material, EFFECTIVE_ON);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canHarvestBlock(IBlockState blockIn) {
/* 24 */     Block block = blockIn.getBlock();
/*    */     
/* 26 */     if (block == Blocks.OBSIDIAN)
/*    */     {
/* 28 */       return (this.toolMaterial.getHarvestLevel() == 3);
/*    */     }
/* 30 */     if (block != Blocks.DIAMOND_BLOCK && block != Blocks.DIAMOND_ORE) {
/*    */       
/* 32 */       if (block != Blocks.EMERALD_ORE && block != Blocks.EMERALD_BLOCK) {
/*    */         
/* 34 */         if (block != Blocks.GOLD_BLOCK && block != Blocks.GOLD_ORE) {
/*    */           
/* 36 */           if (block != Blocks.IRON_BLOCK && block != Blocks.IRON_ORE) {
/*    */             
/* 38 */             if (block != Blocks.LAPIS_BLOCK && block != Blocks.LAPIS_ORE) {
/*    */               
/* 40 */               if (block != Blocks.REDSTONE_ORE && block != Blocks.LIT_REDSTONE_ORE) {
/*    */                 
/* 42 */                 Material material = blockIn.getMaterial();
/*    */                 
/* 44 */                 if (material == Material.ROCK)
/*    */                 {
/* 46 */                   return true;
/*    */                 }
/* 48 */                 if (material == Material.IRON)
/*    */                 {
/* 50 */                   return true;
/*    */                 }
/*    */ 
/*    */                 
/* 54 */                 return (material == Material.ANVIL);
/*    */               } 
/*    */ 
/*    */ 
/*    */               
/* 59 */               return (this.toolMaterial.getHarvestLevel() >= 2);
/*    */             } 
/*    */ 
/*    */ 
/*    */             
/* 64 */             return (this.toolMaterial.getHarvestLevel() >= 1);
/*    */           } 
/*    */ 
/*    */ 
/*    */           
/* 69 */           return (this.toolMaterial.getHarvestLevel() >= 1);
/*    */         } 
/*    */ 
/*    */ 
/*    */         
/* 74 */         return (this.toolMaterial.getHarvestLevel() >= 2);
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 79 */       return (this.toolMaterial.getHarvestLevel() >= 2);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 84 */     return (this.toolMaterial.getHarvestLevel() >= 2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getStrVsBlock(ItemStack stack, IBlockState state) {
/* 90 */     Material material = state.getMaterial();
/* 91 */     return (material != Material.IRON && material != Material.ANVIL && material != Material.ROCK) ? super.getStrVsBlock(stack, state) : this.efficiencyOnProperMaterial;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemPickaxe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */