/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemShears
/*    */   extends Item
/*    */ {
/*    */   public ItemShears() {
/* 16 */     setMaxStackSize(1);
/* 17 */     setMaxDamage(238);
/* 18 */     setCreativeTab(CreativeTabs.TOOLS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
/* 26 */     if (!worldIn.isRemote)
/*    */     {
/* 28 */       stack.damageItem(1, entityLiving);
/*    */     }
/*    */     
/* 31 */     Block block = state.getBlock();
/* 32 */     return (state.getMaterial() != Material.LEAVES && block != Blocks.WEB && block != Blocks.TALLGRASS && block != Blocks.VINE && block != Blocks.TRIPWIRE && block != Blocks.WOOL) ? super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving) : true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canHarvestBlock(IBlockState blockIn) {
/* 40 */     Block block = blockIn.getBlock();
/* 41 */     return !(block != Blocks.WEB && block != Blocks.REDSTONE_WIRE && block != Blocks.TRIPWIRE);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getStrVsBlock(ItemStack stack, IBlockState state) {
/* 46 */     Block block = state.getBlock();
/*    */     
/* 48 */     if (block != Blocks.WEB && state.getMaterial() != Material.LEAVES)
/*    */     {
/* 50 */       return (block == Blocks.WOOL) ? 5.0F : super.getStrVsBlock(stack, state);
/*    */     }
/*    */ 
/*    */     
/* 54 */     return 15.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemShears.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */