/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ItemStackHelper;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityShulkerBox;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockShulkerBox extends BlockContainer {
/*  44 */   public static final PropertyEnum<EnumFacing> field_190957_a = (PropertyEnum<EnumFacing>)PropertyDirection.create("facing");
/*     */   
/*     */   private final EnumDyeColor field_190958_b;
/*     */   
/*     */   public BlockShulkerBox(EnumDyeColor p_i47248_1_) {
/*  49 */     super(Material.ROCK, MapColor.AIR);
/*  50 */     this.field_190958_b = p_i47248_1_;
/*  51 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*  52 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)field_190957_a, (Comparable)EnumFacing.UP));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  60 */     return (TileEntity)new TileEntityShulkerBox(this.field_190958_b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean causesSuffocation(IBlockState p_176214_1_) {
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190946_v(IBlockState p_190946_1_) {
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/*  92 */     return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/*  97 */     if (worldIn.isRemote)
/*     */     {
/*  99 */       return true;
/*     */     }
/* 101 */     if (playerIn.isSpectator())
/*     */     {
/* 103 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 107 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 109 */     if (tileentity instanceof TileEntityShulkerBox) {
/*     */       boolean flag;
/* 111 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)field_190957_a);
/*     */ 
/*     */       
/* 114 */       if (((TileEntityShulkerBox)tileentity).func_190591_p() == TileEntityShulkerBox.AnimationStatus.CLOSED) {
/*     */         
/* 116 */         AxisAlignedBB axisalignedbb = FULL_BLOCK_AABB.addCoord((0.5F * enumfacing.getFrontOffsetX()), (0.5F * enumfacing.getFrontOffsetY()), (0.5F * enumfacing.getFrontOffsetZ())).func_191195_a(enumfacing.getFrontOffsetX(), enumfacing.getFrontOffsetY(), enumfacing.getFrontOffsetZ());
/* 117 */         flag = !worldIn.collidesWithAnyBlock(axisalignedbb.offset(pos.offset(enumfacing)));
/*     */       }
/*     */       else {
/*     */         
/* 121 */         flag = true;
/*     */       } 
/*     */       
/* 124 */       if (flag) {
/*     */         
/* 126 */         playerIn.addStat(StatList.field_191272_ae);
/* 127 */         playerIn.displayGUIChest((IInventory)tileentity);
/*     */       } 
/*     */       
/* 130 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 145 */     return getDefaultState().withProperty((IProperty)field_190957_a, (Comparable)facing);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 150 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)field_190957_a });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 158 */     return ((EnumFacing)state.getValue((IProperty)field_190957_a)).getIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 166 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/* 167 */     return getDefaultState().withProperty((IProperty)field_190957_a, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 172 */     if (worldIn.getTileEntity(pos) instanceof TileEntityShulkerBox) {
/*     */       
/* 174 */       TileEntityShulkerBox tileentityshulkerbox = (TileEntityShulkerBox)worldIn.getTileEntity(pos);
/* 175 */       tileentityshulkerbox.func_190579_a(player.capabilities.isCreativeMode);
/* 176 */       tileentityshulkerbox.fillWithLoot(player);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 192 */     if (stack.hasDisplayName()) {
/*     */       
/* 194 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 196 */       if (tileentity instanceof TileEntityShulkerBox)
/*     */       {
/* 198 */         ((TileEntityShulkerBox)tileentity).func_190575_a(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 208 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 210 */     if (tileentity instanceof TileEntityShulkerBox) {
/*     */       
/* 212 */       TileEntityShulkerBox tileentityshulkerbox = (TileEntityShulkerBox)tileentity;
/*     */       
/* 214 */       if (!tileentityshulkerbox.func_190590_r() && tileentityshulkerbox.func_190582_F()) {
/*     */         
/* 216 */         ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
/* 217 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 218 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 219 */         nbttagcompound.setTag("BlockEntityTag", (NBTBase)((TileEntityShulkerBox)tileentity).func_190580_f(nbttagcompound1));
/* 220 */         itemstack.setTagCompound(nbttagcompound);
/*     */         
/* 222 */         if (tileentityshulkerbox.hasCustomName()) {
/*     */           
/* 224 */           itemstack.setStackDisplayName(tileentityshulkerbox.getName());
/* 225 */           tileentityshulkerbox.func_190575_a("");
/*     */         } 
/*     */         
/* 228 */         spawnAsEntity(worldIn, pos, itemstack);
/*     */       } 
/*     */       
/* 231 */       worldIn.updateComparatorOutputLevel(pos, state.getBlock());
/*     */     } 
/*     */     
/* 234 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190948_a(ItemStack p_190948_1_, @Nullable World p_190948_2_, List<String> p_190948_3_, ITooltipFlag p_190948_4_) {
/* 239 */     super.func_190948_a(p_190948_1_, p_190948_2_, p_190948_3_, p_190948_4_);
/* 240 */     NBTTagCompound nbttagcompound = p_190948_1_.getTagCompound();
/*     */     
/* 242 */     if (nbttagcompound != null && nbttagcompound.hasKey("BlockEntityTag", 10)) {
/*     */       
/* 244 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("BlockEntityTag");
/*     */       
/* 246 */       if (nbttagcompound1.hasKey("LootTable", 8))
/*     */       {
/* 248 */         p_190948_3_.add("???????");
/*     */       }
/*     */       
/* 251 */       if (nbttagcompound1.hasKey("Items", 9)) {
/*     */         
/* 253 */         NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(27, ItemStack.field_190927_a);
/* 254 */         ItemStackHelper.func_191283_b(nbttagcompound1, nonnulllist);
/* 255 */         int i = 0;
/* 256 */         int j = 0;
/*     */         
/* 258 */         for (ItemStack itemstack : nonnulllist) {
/*     */           
/* 260 */           if (!itemstack.func_190926_b()) {
/*     */             
/* 262 */             j++;
/*     */             
/* 264 */             if (i <= 4) {
/*     */               
/* 266 */               i++;
/* 267 */               p_190948_3_.add(String.format("%s x%d", new Object[] { itemstack.getDisplayName(), Integer.valueOf(itemstack.func_190916_E()) }));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 272 */         if (j - i > 0)
/*     */         {
/* 274 */           p_190948_3_.add(String.format(TextFormatting.ITALIC + I18n.translateToLocal("container.shulkerBox.more"), new Object[] { Integer.valueOf(j - i) }));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumPushReaction getMobilityFlag(IBlockState state) {
/* 282 */     return EnumPushReaction.DESTROY;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/* 287 */     TileEntity tileentity = source.getTileEntity(pos);
/* 288 */     return (tileentity instanceof TileEntityShulkerBox) ? ((TileEntityShulkerBox)tileentity).func_190584_a(state) : FULL_BLOCK_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride(IBlockState state) {
/* 293 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
/* 298 */     return Container.calcRedstoneFromInventory((IInventory)worldIn.getTileEntity(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 303 */     ItemStack itemstack = super.getItem(worldIn, pos, state);
/* 304 */     TileEntityShulkerBox tileentityshulkerbox = (TileEntityShulkerBox)worldIn.getTileEntity(pos);
/* 305 */     NBTTagCompound nbttagcompound = tileentityshulkerbox.func_190580_f(new NBTTagCompound());
/*     */     
/* 307 */     if (!nbttagcompound.hasNoTags())
/*     */     {
/* 309 */       itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound);
/*     */     }
/*     */     
/* 312 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumDyeColor func_190955_b(Item p_190955_0_) {
/* 317 */     return func_190954_c(Block.getBlockFromItem(p_190955_0_));
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumDyeColor func_190954_c(Block p_190954_0_) {
/* 322 */     return (p_190954_0_ instanceof BlockShulkerBox) ? ((BlockShulkerBox)p_190954_0_).func_190956_e() : EnumDyeColor.PURPLE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Block func_190952_a(EnumDyeColor p_190952_0_) {
/* 327 */     switch (p_190952_0_) {
/*     */       
/*     */       case WHITE:
/* 330 */         return Blocks.field_190977_dl;
/*     */       
/*     */       case ORANGE:
/* 333 */         return Blocks.field_190978_dm;
/*     */       
/*     */       case MAGENTA:
/* 336 */         return Blocks.field_190979_dn;
/*     */       
/*     */       case LIGHT_BLUE:
/* 339 */         return Blocks.field_190980_do;
/*     */       
/*     */       case YELLOW:
/* 342 */         return Blocks.field_190981_dp;
/*     */       
/*     */       case LIME:
/* 345 */         return Blocks.field_190982_dq;
/*     */       
/*     */       case PINK:
/* 348 */         return Blocks.field_190983_dr;
/*     */       
/*     */       case GRAY:
/* 351 */         return Blocks.field_190984_ds;
/*     */       
/*     */       case SILVER:
/* 354 */         return Blocks.field_190985_dt;
/*     */       
/*     */       case CYAN:
/* 357 */         return Blocks.field_190986_du;
/*     */ 
/*     */       
/*     */       default:
/* 361 */         return Blocks.field_190987_dv;
/*     */       
/*     */       case BLUE:
/* 364 */         return Blocks.field_190988_dw;
/*     */       
/*     */       case BROWN:
/* 367 */         return Blocks.field_190989_dx;
/*     */       
/*     */       case GREEN:
/* 370 */         return Blocks.field_190990_dy;
/*     */       
/*     */       case RED:
/* 373 */         return Blocks.field_190991_dz;
/*     */       case null:
/*     */         break;
/* 376 */     }  return Blocks.field_190975_dA;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumDyeColor func_190956_e() {
/* 382 */     return this.field_190958_b;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack func_190953_b(EnumDyeColor p_190953_0_) {
/* 387 */     return new ItemStack(func_190952_a(p_190953_0_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 396 */     return state.withProperty((IProperty)field_190957_a, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)field_190957_a)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 405 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)field_190957_a)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 410 */     p_193383_2_ = getActualState(p_193383_2_, p_193383_1_, p_193383_3_);
/* 411 */     EnumFacing enumfacing = (EnumFacing)p_193383_2_.getValue((IProperty)field_190957_a);
/* 412 */     TileEntityShulkerBox.AnimationStatus tileentityshulkerbox$animationstatus = ((TileEntityShulkerBox)p_193383_1_.getTileEntity(p_193383_3_)).func_190591_p();
/* 413 */     return (tileentityshulkerbox$animationstatus != TileEntityShulkerBox.AnimationStatus.CLOSED && (tileentityshulkerbox$animationstatus != TileEntityShulkerBox.AnimationStatus.OPENED || (enumfacing != p_193383_4_.getOpposite() && enumfacing != p_193383_4_))) ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockShulkerBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */