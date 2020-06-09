/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityComparator;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneComparator
/*     */   extends BlockRedstoneDiode implements ITileEntityProvider {
/*  38 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  39 */   public static final PropertyEnum<Mode> MODE = PropertyEnum.create("mode", Mode.class);
/*     */ 
/*     */   
/*     */   public BlockRedstoneComparator(boolean powered) {
/*  43 */     super(powered);
/*  44 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)MODE, Mode.COMPARE));
/*  45 */     this.isBlockContainer = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  53 */     return I18n.translateToLocal("item.comparator.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  61 */     return Items.COMPARATOR;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/*  66 */     return new ItemStack(Items.COMPARATOR);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getDelay(IBlockState state) {
/*  71 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBlockState getPoweredState(IBlockState unpoweredState) {
/*  76 */     Boolean obool = (Boolean)unpoweredState.getValue((IProperty)POWERED);
/*  77 */     Mode blockredstonecomparator$mode = (Mode)unpoweredState.getValue((IProperty)MODE);
/*  78 */     EnumFacing enumfacing = (EnumFacing)unpoweredState.getValue((IProperty)FACING);
/*  79 */     return Blocks.POWERED_COMPARATOR.getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)POWERED, obool).withProperty((IProperty)MODE, blockredstonecomparator$mode);
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBlockState getUnpoweredState(IBlockState poweredState) {
/*  84 */     Boolean obool = (Boolean)poweredState.getValue((IProperty)POWERED);
/*  85 */     Mode blockredstonecomparator$mode = (Mode)poweredState.getValue((IProperty)MODE);
/*  86 */     EnumFacing enumfacing = (EnumFacing)poweredState.getValue((IProperty)FACING);
/*  87 */     return Blocks.UNPOWERED_COMPARATOR.getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)POWERED, obool).withProperty((IProperty)MODE, blockredstonecomparator$mode);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isPowered(IBlockState state) {
/*  92 */     return !(!this.isRepeaterPowered && !((Boolean)state.getValue((IProperty)POWERED)).booleanValue());
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getActiveSignal(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/*  97 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*  98 */     return (tileentity instanceof TileEntityComparator) ? ((TileEntityComparator)tileentity).getOutputSignal() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private int calculateOutput(World worldIn, BlockPos pos, IBlockState state) {
/* 103 */     return (state.getValue((IProperty)MODE) == Mode.SUBTRACT) ? Math.max(calculateInputStrength(worldIn, pos, state) - getPowerOnSides((IBlockAccess)worldIn, pos, state), 0) : calculateInputStrength(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
/* 108 */     int i = calculateInputStrength(worldIn, pos, state);
/*     */     
/* 110 */     if (i >= 15)
/*     */     {
/* 112 */       return true;
/*     */     }
/* 114 */     if (i == 0)
/*     */     {
/* 116 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 120 */     int j = getPowerOnSides((IBlockAccess)worldIn, pos, state);
/*     */     
/* 122 */     if (j == 0)
/*     */     {
/* 124 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 128 */     return (i >= j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state) {
/* 135 */     int i = super.calculateInputStrength(worldIn, pos, state);
/* 136 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 137 */     BlockPos blockpos = pos.offset(enumfacing);
/* 138 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */     
/* 140 */     if (iblockstate.hasComparatorInputOverride()) {
/*     */       
/* 142 */       i = iblockstate.getComparatorInputOverride(worldIn, blockpos);
/*     */     }
/* 144 */     else if (i < 15 && iblockstate.isNormalCube()) {
/*     */       
/* 146 */       blockpos = blockpos.offset(enumfacing);
/* 147 */       iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 149 */       if (iblockstate.hasComparatorInputOverride()) {
/*     */         
/* 151 */         i = iblockstate.getComparatorInputOverride(worldIn, blockpos);
/*     */       }
/* 153 */       else if (iblockstate.getMaterial() == Material.AIR) {
/*     */         
/* 155 */         EntityItemFrame entityitemframe = findItemFrame(worldIn, enumfacing, blockpos);
/*     */         
/* 157 */         if (entityitemframe != null)
/*     */         {
/* 159 */           i = entityitemframe.getAnalogOutput();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private EntityItemFrame findItemFrame(World worldIn, final EnumFacing facing, BlockPos pos) {
/* 170 */     List<EntityItemFrame> list = worldIn.getEntitiesWithinAABB(EntityItemFrame.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), (pos.getX() + 1), (pos.getY() + 1), (pos.getZ() + 1)), new Predicate<Entity>()
/*     */         {
/*     */           public boolean apply(@Nullable Entity p_apply_1_)
/*     */           {
/* 174 */             return (p_apply_1_ != null && p_apply_1_.getHorizontalFacing() == facing);
/*     */           }
/*     */         });
/* 177 */     return (list.size() == 1) ? list.get(0) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 182 */     if (!playerIn.capabilities.allowEdit)
/*     */     {
/* 184 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 188 */     state = state.cycleProperty((IProperty)MODE);
/* 189 */     float f = (state.getValue((IProperty)MODE) == Mode.SUBTRACT) ? 0.55F : 0.5F;
/* 190 */     worldIn.playSound(playerIn, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3F, f);
/* 191 */     worldIn.setBlockState(pos, state, 2);
/* 192 */     onStateChange(worldIn, pos, state);
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
/* 199 */     if (!worldIn.isBlockTickPending(pos, this)) {
/*     */       
/* 201 */       int i = calculateOutput(worldIn, pos, state);
/* 202 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/* 203 */       int j = (tileentity instanceof TileEntityComparator) ? ((TileEntityComparator)tileentity).getOutputSignal() : 0;
/*     */       
/* 205 */       if (i != j || isPowered(state) != shouldBePowered(worldIn, pos, state))
/*     */       {
/* 207 */         if (isFacingTowardsRepeater(worldIn, pos, state)) {
/*     */           
/* 209 */           worldIn.updateBlockTick(pos, this, 2, -1);
/*     */         }
/*     */         else {
/*     */           
/* 213 */           worldIn.updateBlockTick(pos, this, 2, 0);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onStateChange(World worldIn, BlockPos pos, IBlockState state) {
/* 221 */     int i = calculateOutput(worldIn, pos, state);
/* 222 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 223 */     int j = 0;
/*     */     
/* 225 */     if (tileentity instanceof TileEntityComparator) {
/*     */       
/* 227 */       TileEntityComparator tileentitycomparator = (TileEntityComparator)tileentity;
/* 228 */       j = tileentitycomparator.getOutputSignal();
/* 229 */       tileentitycomparator.setOutputSignal(i);
/*     */     } 
/*     */     
/* 232 */     if (j != i || state.getValue((IProperty)MODE) == Mode.COMPARE) {
/*     */       
/* 234 */       boolean flag1 = shouldBePowered(worldIn, pos, state);
/* 235 */       boolean flag = isPowered(state);
/*     */       
/* 237 */       if (flag && !flag1) {
/*     */         
/* 239 */         worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(false)), 2);
/*     */       }
/* 241 */       else if (!flag && flag1) {
/*     */         
/* 243 */         worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)), 2);
/*     */       } 
/*     */       
/* 246 */       notifyNeighbors(worldIn, pos, state);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 252 */     if (this.isRepeaterPowered)
/*     */     {
/* 254 */       worldIn.setBlockState(pos, getUnpoweredState(state).withProperty((IProperty)POWERED, Boolean.valueOf(true)), 4);
/*     */     }
/*     */     
/* 257 */     onStateChange(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 265 */     super.onBlockAdded(worldIn, pos, state);
/* 266 */     worldIn.setTileEntity(pos, createNewTileEntity(worldIn, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 274 */     super.breakBlock(worldIn, pos, state);
/* 275 */     worldIn.removeTileEntity(pos);
/* 276 */     notifyNeighbors(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
/* 287 */     super.eventReceived(state, worldIn, pos, id, param);
/* 288 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 289 */     return (tileentity == null) ? false : tileentity.receiveClientEvent(id, param);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 297 */     return (TileEntity)new TileEntityComparator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 305 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0))).withProperty((IProperty)MODE, ((meta & 0x4) > 0) ? Mode.SUBTRACT : Mode.COMPARE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 313 */     int i = 0;
/* 314 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 316 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 318 */       i |= 0x8;
/*     */     }
/*     */     
/* 321 */     if (state.getValue((IProperty)MODE) == Mode.SUBTRACT)
/*     */     {
/* 323 */       i |= 0x4;
/*     */     }
/*     */     
/* 326 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 335 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 344 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 349 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)MODE, (IProperty)POWERED });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 358 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite()).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)MODE, Mode.COMPARE);
/*     */   }
/*     */   
/*     */   public enum Mode
/*     */     implements IStringSerializable {
/* 363 */     COMPARE("compare"),
/* 364 */     SUBTRACT("subtract");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     Mode(String name) {
/* 370 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 375 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 380 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockRedstoneComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */