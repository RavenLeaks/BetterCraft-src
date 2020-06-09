/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*     */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*     */ import net.minecraft.dispenser.IBlockSource;
/*     */ import net.minecraft.dispenser.IPosition;
/*     */ import net.minecraft.dispenser.PositionImpl;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.registry.RegistryDefaulted;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDispenser
/*     */   extends BlockContainer {
/*  37 */   public static final PropertyDirection FACING = BlockDirectional.FACING;
/*  38 */   public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");
/*  39 */   public static final RegistryDefaulted<Item, IBehaviorDispenseItem> DISPENSE_BEHAVIOR_REGISTRY = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
/*  40 */   protected Random rand = new Random();
/*     */ 
/*     */   
/*     */   protected BlockDispenser() {
/*  44 */     super(Material.ROCK);
/*  45 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)TRIGGERED, Boolean.valueOf(false)));
/*  46 */     setCreativeTab(CreativeTabs.REDSTONE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  54 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  62 */     super.onBlockAdded(worldIn, pos, state);
/*  63 */     setDefaultDirection(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setDefaultDirection(World worldIn, BlockPos pos, IBlockState state) {
/*  68 */     if (!worldIn.isRemote) {
/*     */       
/*  70 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*  71 */       boolean flag = worldIn.getBlockState(pos.north()).isFullBlock();
/*  72 */       boolean flag1 = worldIn.getBlockState(pos.south()).isFullBlock();
/*     */       
/*  74 */       if (enumfacing == EnumFacing.NORTH && flag && !flag1) {
/*     */         
/*  76 */         enumfacing = EnumFacing.SOUTH;
/*     */       }
/*  78 */       else if (enumfacing == EnumFacing.SOUTH && flag1 && !flag) {
/*     */         
/*  80 */         enumfacing = EnumFacing.NORTH;
/*     */       }
/*     */       else {
/*     */         
/*  84 */         boolean flag2 = worldIn.getBlockState(pos.west()).isFullBlock();
/*  85 */         boolean flag3 = worldIn.getBlockState(pos.east()).isFullBlock();
/*     */         
/*  87 */         if (enumfacing == EnumFacing.WEST && flag2 && !flag3) {
/*     */           
/*  89 */           enumfacing = EnumFacing.EAST;
/*     */         }
/*  91 */         else if (enumfacing == EnumFacing.EAST && flag3 && !flag2) {
/*     */           
/*  93 */           enumfacing = EnumFacing.WEST;
/*     */         } 
/*     */       } 
/*     */       
/*  97 */       worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)TRIGGERED, Boolean.valueOf(false)), 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 103 */     if (worldIn.isRemote)
/*     */     {
/* 105 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 109 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 111 */     if (tileentity instanceof TileEntityDispenser) {
/*     */       
/* 113 */       playerIn.displayGUIChest((IInventory)tileentity);
/*     */       
/* 115 */       if (tileentity instanceof net.minecraft.tileentity.TileEntityDropper) {
/*     */         
/* 117 */         playerIn.addStat(StatList.DROPPER_INSPECTED);
/*     */       }
/*     */       else {
/*     */         
/* 121 */         playerIn.addStat(StatList.DISPENSER_INSPECTED);
/*     */       } 
/*     */     } 
/*     */     
/* 125 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dispense(World worldIn, BlockPos pos) {
/* 131 */     BlockSourceImpl blocksourceimpl = new BlockSourceImpl(worldIn, pos);
/* 132 */     TileEntityDispenser tileentitydispenser = blocksourceimpl.<TileEntityDispenser>getBlockTileEntity();
/*     */     
/* 134 */     if (tileentitydispenser != null) {
/*     */       
/* 136 */       int i = tileentitydispenser.getDispenseSlot();
/*     */       
/* 138 */       if (i < 0) {
/*     */         
/* 140 */         worldIn.playEvent(1001, pos, 0);
/*     */       }
/*     */       else {
/*     */         
/* 144 */         ItemStack itemstack = tileentitydispenser.getStackInSlot(i);
/* 145 */         IBehaviorDispenseItem ibehaviordispenseitem = getBehavior(itemstack);
/*     */         
/* 147 */         if (ibehaviordispenseitem != IBehaviorDispenseItem.DEFAULT_BEHAVIOR)
/*     */         {
/* 149 */           tileentitydispenser.setInventorySlotContents(i, ibehaviordispenseitem.dispense(blocksourceimpl, itemstack));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBehaviorDispenseItem getBehavior(ItemStack stack) {
/* 157 */     return (IBehaviorDispenseItem)DISPENSE_BEHAVIOR_REGISTRY.getObject(stack.getItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 167 */     boolean flag = !(!worldIn.isBlockPowered(pos) && !worldIn.isBlockPowered(pos.up()));
/* 168 */     boolean flag1 = ((Boolean)state.getValue((IProperty)TRIGGERED)).booleanValue();
/*     */     
/* 170 */     if (flag && !flag1) {
/*     */       
/* 172 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/* 173 */       worldIn.setBlockState(pos, state.withProperty((IProperty)TRIGGERED, Boolean.valueOf(true)), 4);
/*     */     }
/* 175 */     else if (!flag && flag1) {
/*     */       
/* 177 */       worldIn.setBlockState(pos, state.withProperty((IProperty)TRIGGERED, Boolean.valueOf(false)), 4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 183 */     if (!worldIn.isRemote)
/*     */     {
/* 185 */       dispense(worldIn, pos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 194 */     return (TileEntity)new TileEntityDispenser();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 203 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.func_190914_a(pos, placer)).withProperty((IProperty)TRIGGERED, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 211 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)EnumFacing.func_190914_a(pos, placer)), 2);
/*     */     
/* 213 */     if (stack.hasDisplayName()) {
/*     */       
/* 215 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 217 */       if (tileentity instanceof TileEntityDispenser)
/*     */       {
/* 219 */         ((TileEntityDispenser)tileentity).func_190575_a(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 229 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 231 */     if (tileentity instanceof TileEntityDispenser) {
/*     */       
/* 233 */       InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/* 234 */       worldIn.updateComparatorOutputLevel(pos, this);
/*     */     } 
/*     */     
/* 237 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IPosition getDispensePosition(IBlockSource coords) {
/* 245 */     EnumFacing enumfacing = (EnumFacing)coords.getBlockState().getValue((IProperty)FACING);
/* 246 */     double d0 = coords.getX() + 0.7D * enumfacing.getFrontOffsetX();
/* 247 */     double d1 = coords.getY() + 0.7D * enumfacing.getFrontOffsetY();
/* 248 */     double d2 = coords.getZ() + 0.7D * enumfacing.getFrontOffsetZ();
/* 249 */     return (IPosition)new PositionImpl(d0, d1, d2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride(IBlockState state) {
/* 254 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
/* 259 */     return Container.calcRedstone(worldIn.getTileEntity(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/* 268 */     return EnumBlockRenderType.MODEL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 276 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getFront(meta & 0x7)).withProperty((IProperty)TRIGGERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 284 */     int i = 0;
/* 285 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 287 */     if (((Boolean)state.getValue((IProperty)TRIGGERED)).booleanValue())
/*     */     {
/* 289 */       i |= 0x8;
/*     */     }
/*     */     
/* 292 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 301 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 310 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 315 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)TRIGGERED });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */