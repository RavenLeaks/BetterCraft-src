/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockOre
/*     */   extends Block
/*     */ {
/*     */   public BlockOre() {
/*  21 */     this(Material.ROCK.getMaterialMapColor());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockOre(MapColor color) {
/*  26 */     super(Material.ROCK, color);
/*  27 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  35 */     if (this == Blocks.COAL_ORE)
/*     */     {
/*  37 */       return Items.COAL;
/*     */     }
/*  39 */     if (this == Blocks.DIAMOND_ORE)
/*     */     {
/*  41 */       return Items.DIAMOND;
/*     */     }
/*  43 */     if (this == Blocks.LAPIS_ORE)
/*     */     {
/*  45 */       return Items.DYE;
/*     */     }
/*  47 */     if (this == Blocks.EMERALD_ORE)
/*     */     {
/*  49 */       return Items.EMERALD;
/*     */     }
/*     */ 
/*     */     
/*  53 */     return (this == Blocks.QUARTZ_ORE) ? Items.QUARTZ : Item.getItemFromBlock(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/*  62 */     return (this == Blocks.LAPIS_ORE) ? (4 + random.nextInt(5)) : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDroppedWithBonus(int fortune, Random random) {
/*  70 */     if (fortune > 0 && Item.getItemFromBlock(this) != getItemDropped((IBlockState)getBlockState().getValidStates().iterator().next(), random, fortune)) {
/*     */       
/*  72 */       int i = random.nextInt(fortune + 2) - 1;
/*     */       
/*  74 */       if (i < 0)
/*     */       {
/*  76 */         i = 0;
/*     */       }
/*     */       
/*  79 */       return quantityDropped(random) * (i + 1);
/*     */     } 
/*     */ 
/*     */     
/*  83 */     return quantityDropped(random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/*  92 */     super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     
/*  94 */     if (getItemDropped(state, worldIn.rand, fortune) != Item.getItemFromBlock(this)) {
/*     */       
/*  96 */       int i = 0;
/*     */       
/*  98 */       if (this == Blocks.COAL_ORE) {
/*     */         
/* 100 */         i = MathHelper.getInt(worldIn.rand, 0, 2);
/*     */       }
/* 102 */       else if (this == Blocks.DIAMOND_ORE) {
/*     */         
/* 104 */         i = MathHelper.getInt(worldIn.rand, 3, 7);
/*     */       }
/* 106 */       else if (this == Blocks.EMERALD_ORE) {
/*     */         
/* 108 */         i = MathHelper.getInt(worldIn.rand, 3, 7);
/*     */       }
/* 110 */       else if (this == Blocks.LAPIS_ORE) {
/*     */         
/* 112 */         i = MathHelper.getInt(worldIn.rand, 2, 5);
/*     */       }
/* 114 */       else if (this == Blocks.QUARTZ_ORE) {
/*     */         
/* 116 */         i = MathHelper.getInt(worldIn.rand, 2, 5);
/*     */       } 
/*     */       
/* 119 */       dropXpOnBlockBreak(worldIn, pos, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 125 */     return new ItemStack(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 134 */     return (this == Blocks.LAPIS_ORE) ? EnumDyeColor.BLUE.getDyeDamage() : 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockOre.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */