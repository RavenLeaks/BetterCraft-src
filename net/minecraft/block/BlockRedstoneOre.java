/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneOre
/*     */   extends Block
/*     */ {
/*     */   private final boolean isOn;
/*     */   
/*     */   public BlockRedstoneOre(boolean isOn) {
/*  24 */     super(Material.ROCK);
/*     */     
/*  26 */     if (isOn)
/*     */     {
/*  28 */       setTickRandomly(true);
/*     */     }
/*     */     
/*  31 */     this.isOn = isOn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  39 */     return 30;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/*  44 */     activate(worldIn, pos);
/*  45 */     super.onBlockClicked(worldIn, pos, playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
/*  53 */     activate(worldIn, pos);
/*  54 */     super.onEntityWalk(worldIn, pos, entityIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/*  59 */     activate(worldIn, pos);
/*  60 */     return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY);
/*     */   }
/*     */ 
/*     */   
/*     */   private void activate(World worldIn, BlockPos pos) {
/*  65 */     spawnParticles(worldIn, pos);
/*     */     
/*  67 */     if (this == Blocks.REDSTONE_ORE)
/*     */     {
/*  69 */       worldIn.setBlockState(pos, Blocks.LIT_REDSTONE_ORE.getDefaultState());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  75 */     if (this == Blocks.LIT_REDSTONE_ORE)
/*     */     {
/*  77 */       worldIn.setBlockState(pos, Blocks.REDSTONE_ORE.getDefaultState());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  86 */     return Items.REDSTONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDroppedWithBonus(int fortune, Random random) {
/*  94 */     return quantityDropped(random) + random.nextInt(fortune + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 102 */     return 4 + random.nextInt(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 110 */     super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     
/* 112 */     if (getItemDropped(state, worldIn.rand, fortune) != Item.getItemFromBlock(this)) {
/*     */       
/* 114 */       int i = 1 + worldIn.rand.nextInt(5);
/* 115 */       dropXpOnBlockBreak(worldIn, pos, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/* 121 */     if (this.isOn)
/*     */     {
/* 123 */       spawnParticles(worldIn, pos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void spawnParticles(World worldIn, BlockPos pos) {
/* 129 */     Random random = worldIn.rand;
/* 130 */     double d0 = 0.0625D;
/*     */     
/* 132 */     for (int i = 0; i < 6; i++) {
/*     */       
/* 134 */       double d1 = (pos.getX() + random.nextFloat());
/* 135 */       double d2 = (pos.getY() + random.nextFloat());
/* 136 */       double d3 = (pos.getZ() + random.nextFloat());
/*     */       
/* 138 */       if (i == 0 && !worldIn.getBlockState(pos.up()).isOpaqueCube())
/*     */       {
/* 140 */         d2 = pos.getY() + 0.0625D + 1.0D;
/*     */       }
/*     */       
/* 143 */       if (i == 1 && !worldIn.getBlockState(pos.down()).isOpaqueCube())
/*     */       {
/* 145 */         d2 = pos.getY() - 0.0625D;
/*     */       }
/*     */       
/* 148 */       if (i == 2 && !worldIn.getBlockState(pos.south()).isOpaqueCube())
/*     */       {
/* 150 */         d3 = pos.getZ() + 0.0625D + 1.0D;
/*     */       }
/*     */       
/* 153 */       if (i == 3 && !worldIn.getBlockState(pos.north()).isOpaqueCube())
/*     */       {
/* 155 */         d3 = pos.getZ() - 0.0625D;
/*     */       }
/*     */       
/* 158 */       if (i == 4 && !worldIn.getBlockState(pos.east()).isOpaqueCube())
/*     */       {
/* 160 */         d1 = pos.getX() + 0.0625D + 1.0D;
/*     */       }
/*     */       
/* 163 */       if (i == 5 && !worldIn.getBlockState(pos.west()).isOpaqueCube())
/*     */       {
/* 165 */         d1 = pos.getX() - 0.0625D;
/*     */       }
/*     */       
/* 168 */       if (d1 < pos.getX() || d1 > (pos.getX() + 1) || d2 < 0.0D || d2 > (pos.getY() + 1) || d3 < pos.getZ() || d3 > (pos.getZ() + 1))
/*     */       {
/* 170 */         worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack getSilkTouchDrop(IBlockState state) {
/* 177 */     return new ItemStack(Blocks.REDSTONE_ORE);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 182 */     return new ItemStack(Item.getItemFromBlock(Blocks.REDSTONE_ORE), 1, damageDropped(state));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockRedstoneOre.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */