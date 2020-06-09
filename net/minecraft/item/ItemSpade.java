/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemSpade extends ItemTool {
/* 20 */   private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet((Object[])new Block[] { Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, (Block)Blocks.GRASS, Blocks.GRAVEL, (Block)Blocks.MYCELIUM, (Block)Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.field_192444_dS });
/*    */ 
/*    */   
/*    */   public ItemSpade(Item.ToolMaterial material) {
/* 24 */     super(1.5F, -3.0F, material, EFFECTIVE_ON);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canHarvestBlock(IBlockState blockIn) {
/* 32 */     Block block = blockIn.getBlock();
/*    */     
/* 34 */     if (block == Blocks.SNOW_LAYER)
/*    */     {
/* 36 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 40 */     return (block == Blocks.SNOW);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 49 */     ItemStack itemstack = stack.getHeldItem(pos);
/*    */     
/* 51 */     if (!stack.canPlayerEdit(worldIn.offset(hand), hand, itemstack))
/*    */     {
/* 53 */       return EnumActionResult.FAIL;
/*    */     }
/*    */ 
/*    */     
/* 57 */     IBlockState iblockstate = playerIn.getBlockState(worldIn);
/* 58 */     Block block = iblockstate.getBlock();
/*    */     
/* 60 */     if (hand != EnumFacing.DOWN && playerIn.getBlockState(worldIn.up()).getMaterial() == Material.AIR && block == Blocks.GRASS) {
/*    */       
/* 62 */       IBlockState iblockstate1 = Blocks.GRASS_PATH.getDefaultState();
/* 63 */       playerIn.playSound(stack, worldIn, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
/*    */       
/* 65 */       if (!playerIn.isRemote) {
/*    */         
/* 67 */         playerIn.setBlockState(worldIn, iblockstate1, 11);
/* 68 */         itemstack.damageItem(1, (EntityLivingBase)stack);
/*    */       } 
/*    */       
/* 71 */       return EnumActionResult.SUCCESS;
/*    */     } 
/*    */ 
/*    */     
/* 75 */     return EnumActionResult.PASS;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemSpade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */