/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyBool;
/*    */ import net.minecraft.block.state.BlockStateContainer;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockMycelium
/*    */   extends Block {
/* 20 */   public static final PropertyBool SNOWY = PropertyBool.create("snowy");
/*    */ 
/*    */   
/*    */   protected BlockMycelium() {
/* 24 */     super(Material.GRASS, MapColor.PURPLE);
/* 25 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)SNOWY, Boolean.valueOf(false)));
/* 26 */     setTickRandomly(true);
/* 27 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 36 */     Block block = worldIn.getBlockState(pos.up()).getBlock();
/* 37 */     return state.withProperty((IProperty)SNOWY, Boolean.valueOf(!(block != Blocks.SNOW && block != Blocks.SNOW_LAYER)));
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 42 */     if (!worldIn.isRemote)
/*    */     {
/* 44 */       if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity() > 2) {
/*    */         
/* 46 */         worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
/*    */ 
/*    */       
/*    */       }
/* 50 */       else if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
/*    */         
/* 52 */         for (int i = 0; i < 4; i++) {
/*    */           
/* 54 */           BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
/* 55 */           IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 56 */           IBlockState iblockstate1 = worldIn.getBlockState(blockpos.up());
/*    */           
/* 58 */           if (iblockstate.getBlock() == Blocks.DIRT && iblockstate.getValue((IProperty)BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate1.getLightOpacity() <= 2)
/*    */           {
/* 60 */             worldIn.setBlockState(blockpos, getDefaultState());
/*    */           }
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/* 70 */     super.randomDisplayTick(stateIn, worldIn, pos, rand);
/*    */     
/* 72 */     if (rand.nextInt(10) == 0)
/*    */     {
/* 74 */       worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, (pos.getX() + rand.nextFloat()), (pos.getY() + 1.1F), (pos.getZ() + rand.nextFloat()), 0.0D, 0.0D, 0.0D, new int[0]);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 83 */     return Blocks.DIRT.getItemDropped(Blocks.DIRT.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 91 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockStateContainer createBlockState() {
/* 96 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)SNOWY });
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockMycelium.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */