/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.PotionTypes;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionUtils;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCauldron
/*     */   extends Block
/*     */ {
/*  37 */   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 3);
/*  38 */   protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
/*  39 */   protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
/*  40 */   protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
/*  41 */   protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/*  42 */   protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);
/*     */ 
/*     */   
/*     */   public BlockCauldron() {
/*  46 */     super(Material.IRON, MapColor.STONE);
/*  47 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)LEVEL, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
/*  52 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
/*  53 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
/*  54 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
/*  55 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
/*  56 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  61 */     return FULL_BLOCK_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/*  82 */     int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*  83 */     float f = pos.getY() + (6.0F + (3 * i)) / 16.0F;
/*     */     
/*  85 */     if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && (entityIn.getEntityBoundingBox()).minY <= f) {
/*     */       
/*  87 */       entityIn.extinguish();
/*  88 */       setWaterLevel(worldIn, pos, state, i - 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/*  94 */     ItemStack itemstack = playerIn.getHeldItem(hand);
/*     */     
/*  96 */     if (itemstack.func_190926_b())
/*     */     {
/*  98 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 102 */     int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/* 103 */     Item item = itemstack.getItem();
/*     */     
/* 105 */     if (item == Items.WATER_BUCKET) {
/*     */       
/* 107 */       if (i < 3 && !worldIn.isRemote) {
/*     */         
/* 109 */         if (!playerIn.capabilities.isCreativeMode)
/*     */         {
/* 111 */           playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
/*     */         }
/*     */         
/* 114 */         playerIn.addStat(StatList.CAULDRON_FILLED);
/* 115 */         setWaterLevel(worldIn, pos, state, 3);
/* 116 */         worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
/*     */       } 
/*     */       
/* 119 */       return true;
/*     */     } 
/* 121 */     if (item == Items.BUCKET) {
/*     */       
/* 123 */       if (i == 3 && !worldIn.isRemote) {
/*     */         
/* 125 */         if (!playerIn.capabilities.isCreativeMode) {
/*     */           
/* 127 */           itemstack.func_190918_g(1);
/*     */           
/* 129 */           if (itemstack.func_190926_b()) {
/*     */             
/* 131 */             playerIn.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));
/*     */           }
/* 133 */           else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET))) {
/*     */             
/* 135 */             playerIn.dropItem(new ItemStack(Items.WATER_BUCKET), false);
/*     */           } 
/*     */         } 
/*     */         
/* 139 */         playerIn.addStat(StatList.CAULDRON_USED);
/* 140 */         setWaterLevel(worldIn, pos, state, 0);
/* 141 */         worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
/*     */       } 
/*     */       
/* 144 */       return true;
/*     */     } 
/* 146 */     if (item == Items.GLASS_BOTTLE) {
/*     */       
/* 148 */       if (i > 0 && !worldIn.isRemote) {
/*     */         
/* 150 */         if (!playerIn.capabilities.isCreativeMode) {
/*     */           
/* 152 */           ItemStack itemstack3 = PotionUtils.addPotionToItemStack(new ItemStack((Item)Items.POTIONITEM), PotionTypes.WATER);
/* 153 */           playerIn.addStat(StatList.CAULDRON_USED);
/* 154 */           itemstack.func_190918_g(1);
/*     */           
/* 156 */           if (itemstack.func_190926_b()) {
/*     */             
/* 158 */             playerIn.setHeldItem(hand, itemstack3);
/*     */           }
/* 160 */           else if (!playerIn.inventory.addItemStackToInventory(itemstack3)) {
/*     */             
/* 162 */             playerIn.dropItem(itemstack3, false);
/*     */           }
/* 164 */           else if (playerIn instanceof EntityPlayerMP) {
/*     */             
/* 166 */             ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
/*     */           } 
/*     */         } 
/*     */         
/* 170 */         worldIn.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
/* 171 */         setWaterLevel(worldIn, pos, state, i - 1);
/*     */       } 
/*     */       
/* 174 */       return true;
/*     */     } 
/* 176 */     if (item == Items.POTIONITEM && PotionUtils.getPotionFromItem(itemstack) == PotionTypes.WATER) {
/*     */       
/* 178 */       if (i < 3 && !worldIn.isRemote) {
/*     */         
/* 180 */         if (!playerIn.capabilities.isCreativeMode) {
/*     */           
/* 182 */           ItemStack itemstack2 = new ItemStack(Items.GLASS_BOTTLE);
/* 183 */           playerIn.addStat(StatList.CAULDRON_USED);
/* 184 */           playerIn.setHeldItem(hand, itemstack2);
/*     */           
/* 186 */           if (playerIn instanceof EntityPlayerMP)
/*     */           {
/* 188 */             ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
/*     */           }
/*     */         } 
/*     */         
/* 192 */         worldIn.playSound(null, pos, SoundEvents.field_191241_J, SoundCategory.BLOCKS, 1.0F, 1.0F);
/* 193 */         setWaterLevel(worldIn, pos, state, i + 1);
/*     */       } 
/*     */       
/* 196 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 200 */     if (i > 0 && item instanceof ItemArmor) {
/*     */       
/* 202 */       ItemArmor itemarmor = (ItemArmor)item;
/*     */       
/* 204 */       if (itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && itemarmor.hasColor(itemstack) && !worldIn.isRemote) {
/*     */         
/* 206 */         itemarmor.removeColor(itemstack);
/* 207 */         setWaterLevel(worldIn, pos, state, i - 1);
/* 208 */         playerIn.addStat(StatList.ARMOR_CLEANED);
/* 209 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     if (i > 0 && item instanceof net.minecraft.item.ItemBanner) {
/*     */       
/* 215 */       if (TileEntityBanner.getPatterns(itemstack) > 0 && !worldIn.isRemote) {
/*     */         
/* 217 */         ItemStack itemstack1 = itemstack.copy();
/* 218 */         itemstack1.func_190920_e(1);
/* 219 */         TileEntityBanner.removeBannerData(itemstack1);
/* 220 */         playerIn.addStat(StatList.BANNER_CLEANED);
/*     */         
/* 222 */         if (!playerIn.capabilities.isCreativeMode) {
/*     */           
/* 224 */           itemstack.func_190918_g(1);
/* 225 */           setWaterLevel(worldIn, pos, state, i - 1);
/*     */         } 
/*     */         
/* 228 */         if (itemstack.func_190926_b()) {
/*     */           
/* 230 */           playerIn.setHeldItem(hand, itemstack1);
/*     */         }
/* 232 */         else if (!playerIn.inventory.addItemStackToInventory(itemstack1)) {
/*     */           
/* 234 */           playerIn.dropItem(itemstack1, false);
/*     */         }
/* 236 */         else if (playerIn instanceof EntityPlayerMP) {
/*     */           
/* 238 */           ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
/*     */         } 
/*     */       } 
/*     */       
/* 242 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 246 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWaterLevel(World worldIn, BlockPos pos, IBlockState state, int level) {
/* 254 */     worldIn.setBlockState(pos, state.withProperty((IProperty)LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 3))), 2);
/* 255 */     worldIn.updateComparatorOutputLevel(pos, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillWithRain(World worldIn, BlockPos pos) {
/* 263 */     if (worldIn.rand.nextInt(20) == 1) {
/*     */       
/* 265 */       float f = worldIn.getBiome(pos).getFloatTemperature(pos);
/*     */       
/* 267 */       if (worldIn.getBiomeProvider().getTemperatureAtHeight(f, pos.getY()) >= 0.15F) {
/*     */         
/* 269 */         IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */         
/* 271 */         if (((Integer)iblockstate.getValue((IProperty)LEVEL)).intValue() < 3)
/*     */         {
/* 273 */           worldIn.setBlockState(pos, iblockstate.cycleProperty((IProperty)LEVEL), 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 284 */     return Items.CAULDRON;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 289 */     return new ItemStack(Items.CAULDRON);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride(IBlockState state) {
/* 294 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
/* 299 */     return ((Integer)blockState.getValue((IProperty)LEVEL)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 307 */     return getDefaultState().withProperty((IProperty)LEVEL, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 315 */     return ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 320 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)LEVEL });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/* 325 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 330 */     if (p_193383_4_ == EnumFacing.UP)
/*     */     {
/* 332 */       return BlockFaceShape.BOWL;
/*     */     }
/*     */ 
/*     */     
/* 336 */     return (p_193383_4_ == EnumFacing.DOWN) ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockCauldron.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */