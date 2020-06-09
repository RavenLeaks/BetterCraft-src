/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTripWire extends Block {
/*  29 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  30 */   public static final PropertyBool ATTACHED = PropertyBool.create("attached");
/*  31 */   public static final PropertyBool DISARMED = PropertyBool.create("disarmed");
/*  32 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  33 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  34 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  35 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*  36 */   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0625D, 0.0D, 1.0D, 0.15625D, 1.0D);
/*  37 */   protected static final AxisAlignedBB TRIP_WRITE_ATTACHED_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
/*     */ 
/*     */   
/*     */   public BlockTripWire() {
/*  41 */     super(Material.CIRCUITS);
/*  42 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)ATTACHED, Boolean.valueOf(false)).withProperty((IProperty)DISARMED, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)));
/*  43 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  48 */     return !((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue() ? TRIP_WRITE_ATTACHED_AABB : AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  57 */     return state.withProperty((IProperty)NORTH, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.NORTH))).withProperty((IProperty)EAST, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.EAST))).withProperty((IProperty)SOUTH, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.SOUTH))).withProperty((IProperty)WEST, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.WEST)));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  63 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/*  81 */     return BlockRenderLayer.TRANSLUCENT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  89 */     return Items.STRING;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/*  94 */     return new ItemStack(Items.STRING);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 102 */     worldIn.setBlockState(pos, state, 3);
/* 103 */     notifyHook(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 111 */     notifyHook(worldIn, pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 116 */     if (!worldIn.isRemote)
/*     */     {
/* 118 */       if (!player.getHeldItemMainhand().func_190926_b() && player.getHeldItemMainhand().getItem() == Items.SHEARS)
/*     */       {
/* 120 */         worldIn.setBlockState(pos, state.withProperty((IProperty)DISARMED, Boolean.valueOf(true)), 4); }  } 
/*     */   }
/*     */   
/*     */   private void notifyHook(World worldIn, BlockPos pos, IBlockState state) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 127 */     for (i = (arrayOfEnumFacing = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.WEST }, ).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/* 129 */       for (int j = 1; j < 42; j++) {
/*     */         
/* 131 */         BlockPos blockpos = pos.offset(enumfacing, j);
/* 132 */         IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */         
/* 134 */         if (iblockstate.getBlock() == Blocks.TRIPWIRE_HOOK) {
/*     */           
/* 136 */           if (iblockstate.getValue((IProperty)BlockTripWireHook.FACING) == enumfacing.getOpposite())
/*     */           {
/* 138 */             Blocks.TRIPWIRE_HOOK.calculateState(worldIn, blockpos, iblockstate, false, true, j, state);
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 144 */         if (iblockstate.getBlock() != Blocks.TRIPWIRE) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 157 */     if (!worldIn.isRemote)
/*     */     {
/* 159 */       if (!((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */       {
/* 161 */         updateState(worldIn, pos);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 175 */     if (!worldIn.isRemote)
/*     */     {
/* 177 */       if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)POWERED)).booleanValue())
/*     */       {
/* 179 */         updateState(worldIn, pos);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateState(World worldIn, BlockPos pos) {
/* 186 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 187 */     boolean flag = ((Boolean)iblockstate.getValue((IProperty)POWERED)).booleanValue();
/* 188 */     boolean flag1 = false;
/* 189 */     List<? extends Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(null, iblockstate.getBoundingBox((IBlockAccess)worldIn, pos).offset(pos));
/*     */     
/* 191 */     if (!list.isEmpty())
/*     */     {
/* 193 */       for (Entity entity : list) {
/*     */         
/* 195 */         if (!entity.doesEntityNotTriggerPressurePlate()) {
/*     */           
/* 197 */           flag1 = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 203 */     if (flag1 != flag) {
/*     */       
/* 205 */       iblockstate = iblockstate.withProperty((IProperty)POWERED, Boolean.valueOf(flag1));
/* 206 */       worldIn.setBlockState(pos, iblockstate, 3);
/* 207 */       notifyHook(worldIn, pos, iblockstate);
/*     */     } 
/*     */     
/* 210 */     if (flag1)
/*     */     {
/* 212 */       worldIn.scheduleUpdate(new BlockPos((Vec3i)pos), this, tickRate(worldIn));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isConnectedTo(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing direction) {
/* 218 */     BlockPos blockpos = pos.offset(direction);
/* 219 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 220 */     Block block = iblockstate.getBlock();
/*     */     
/* 222 */     if (block == Blocks.TRIPWIRE_HOOK) {
/*     */       
/* 224 */       EnumFacing enumfacing = direction.getOpposite();
/* 225 */       return (iblockstate.getValue((IProperty)BlockTripWireHook.FACING) == enumfacing);
/*     */     } 
/*     */ 
/*     */     
/* 229 */     return (block == Blocks.TRIPWIRE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 238 */     return getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x1) > 0))).withProperty((IProperty)ATTACHED, Boolean.valueOf(((meta & 0x4) > 0))).withProperty((IProperty)DISARMED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 246 */     int i = 0;
/*     */     
/* 248 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 250 */       i |= 0x1;
/*     */     }
/*     */     
/* 253 */     if (((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue())
/*     */     {
/* 255 */       i |= 0x4;
/*     */     }
/*     */     
/* 258 */     if (((Boolean)state.getValue((IProperty)DISARMED)).booleanValue())
/*     */     {
/* 260 */       i |= 0x8;
/*     */     }
/*     */     
/* 263 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 272 */     switch (rot) {
/*     */       
/*     */       case null:
/* 275 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)SOUTH)).withProperty((IProperty)EAST, state.getValue((IProperty)WEST)).withProperty((IProperty)SOUTH, state.getValue((IProperty)NORTH)).withProperty((IProperty)WEST, state.getValue((IProperty)EAST));
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 278 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)EAST)).withProperty((IProperty)EAST, state.getValue((IProperty)SOUTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)WEST)).withProperty((IProperty)WEST, state.getValue((IProperty)NORTH));
/*     */       
/*     */       case CLOCKWISE_90:
/* 281 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)WEST)).withProperty((IProperty)EAST, state.getValue((IProperty)NORTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)EAST)).withProperty((IProperty)WEST, state.getValue((IProperty)SOUTH));
/*     */     } 
/*     */     
/* 284 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 294 */     switch (mirrorIn) {
/*     */       
/*     */       case LEFT_RIGHT:
/* 297 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)SOUTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)NORTH));
/*     */       
/*     */       case null:
/* 300 */         return state.withProperty((IProperty)EAST, state.getValue((IProperty)WEST)).withProperty((IProperty)WEST, state.getValue((IProperty)EAST));
/*     */     } 
/*     */     
/* 303 */     return super.withMirror(state, mirrorIn);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 309 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)POWERED, (IProperty)ATTACHED, (IProperty)DISARMED, (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 314 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockTripWire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */