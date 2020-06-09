/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldServer;
/*    */ 
/*    */ 
/*    */ public class BlockMagma
/*    */   extends Block
/*    */ {
/*    */   public BlockMagma() {
/* 26 */     super(Material.ROCK);
/* 27 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/* 28 */     setLightLevel(0.2F);
/* 29 */     setTickRandomly(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
/* 37 */     return MapColor.NETHERRACK;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
/* 45 */     if (!entityIn.isImmuneToFire() && entityIn instanceof EntityLivingBase && !EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase)entityIn))
/*    */     {
/* 47 */       entityIn.attackEntityFrom(DamageSource.hotFloor, 1.0F);
/*    */     }
/*    */     
/* 50 */     super.onEntityWalk(worldIn, pos, entityIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
/* 55 */     return 15728880;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 60 */     BlockPos blockpos = pos.up();
/* 61 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*    */     
/* 63 */     if (iblockstate.getBlock() == Blocks.WATER || iblockstate.getBlock() == Blocks.FLOWING_WATER) {
/*    */       
/* 65 */       worldIn.setBlockToAir(blockpos);
/* 66 */       worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
/*    */       
/* 68 */       if (worldIn instanceof WorldServer)
/*    */       {
/* 70 */         ((WorldServer)worldIn).spawnParticle(EnumParticleTypes.SMOKE_LARGE, blockpos.getX() + 0.5D, blockpos.getY() + 0.25D, blockpos.getZ() + 0.5D, 8, 0.5D, 0.25D, 0.5D, 0.0D, new int[0]);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEntitySpawn(IBlockState state, Entity entityIn) {
/* 77 */     return entityIn.isImmuneToFire();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockMagma.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */