/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBed;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockBed
/*     */   extends BlockHorizontal implements ITileEntityProvider {
/*  39 */   public static final PropertyEnum<EnumPartType> PART = PropertyEnum.create("part", EnumPartType.class);
/*  40 */   public static final PropertyBool OCCUPIED = PropertyBool.create("occupied");
/*  41 */   protected static final AxisAlignedBB BED_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D);
/*     */ 
/*     */   
/*     */   public BlockBed() {
/*  45 */     super(Material.CLOTH);
/*  46 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)PART, EnumPartType.FOOT).withProperty((IProperty)OCCUPIED, Boolean.valueOf(false)));
/*  47 */     this.isBlockContainer = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
/*  55 */     if (state.getValue((IProperty)PART) == EnumPartType.FOOT) {
/*     */       
/*  57 */       TileEntity tileentity = p_180659_2_.getTileEntity(p_180659_3_);
/*     */       
/*  59 */       if (tileentity instanceof TileEntityBed) {
/*     */         
/*  61 */         EnumDyeColor enumdyecolor = ((TileEntityBed)tileentity).func_193048_a();
/*  62 */         return MapColor.func_193558_a(enumdyecolor);
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     return MapColor.CLOTH;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/*  71 */     if (worldIn.isRemote)
/*     */     {
/*  73 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  77 */     if (state.getValue((IProperty)PART) != EnumPartType.HEAD) {
/*     */       
/*  79 */       pos = pos.offset((EnumFacing)state.getValue((IProperty)FACING));
/*  80 */       state = worldIn.getBlockState(pos);
/*     */       
/*  82 */       if (state.getBlock() != this)
/*     */       {
/*  84 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  88 */     if (worldIn.provider.canRespawnHere() && worldIn.getBiome(pos) != Biomes.HELL) {
/*     */       
/*  90 */       if (((Boolean)state.getValue((IProperty)OCCUPIED)).booleanValue()) {
/*     */         
/*  92 */         EntityPlayer entityplayer = getPlayerInBed(worldIn, pos);
/*     */         
/*  94 */         if (entityplayer != null) {
/*     */           
/*  96 */           playerIn.addChatComponentMessage((ITextComponent)new TextComponentTranslation("tile.bed.occupied", new Object[0]), true);
/*  97 */           return true;
/*     */         } 
/*     */         
/* 100 */         state = state.withProperty((IProperty)OCCUPIED, Boolean.valueOf(false));
/* 101 */         worldIn.setBlockState(pos, state, 4);
/*     */       } 
/*     */       
/* 104 */       EntityPlayer.SleepResult entityplayer$sleepresult = playerIn.trySleep(pos);
/*     */       
/* 106 */       if (entityplayer$sleepresult == EntityPlayer.SleepResult.OK) {
/*     */         
/* 108 */         state = state.withProperty((IProperty)OCCUPIED, Boolean.valueOf(true));
/* 109 */         worldIn.setBlockState(pos, state, 4);
/* 110 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 114 */       if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_POSSIBLE_NOW) {
/*     */         
/* 116 */         playerIn.addChatComponentMessage((ITextComponent)new TextComponentTranslation("tile.bed.noSleep", new Object[0]), true);
/*     */       }
/* 118 */       else if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_SAFE) {
/*     */         
/* 120 */         playerIn.addChatComponentMessage((ITextComponent)new TextComponentTranslation("tile.bed.notSafe", new Object[0]), true);
/*     */       }
/* 122 */       else if (entityplayer$sleepresult == EntityPlayer.SleepResult.TOO_FAR_AWAY) {
/*     */         
/* 124 */         playerIn.addChatComponentMessage((ITextComponent)new TextComponentTranslation("tile.bed.tooFarAway", new Object[0]), true);
/*     */       } 
/*     */       
/* 127 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 132 */     worldIn.setBlockToAir(pos);
/* 133 */     BlockPos blockpos = pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite());
/*     */     
/* 135 */     if (worldIn.getBlockState(blockpos).getBlock() == this)
/*     */     {
/* 137 */       worldIn.setBlockToAir(blockpos);
/*     */     }
/*     */     
/* 140 */     worldIn.newExplosion(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 5.0F, true, true);
/* 141 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private EntityPlayer getPlayerInBed(World worldIn, BlockPos pos) {
/* 149 */     for (EntityPlayer entityplayer : worldIn.playerEntities) {
/*     */       
/* 151 */       if (entityplayer.isPlayerSleeping() && entityplayer.bedLocation.equals(pos))
/*     */       {
/* 153 */         return entityplayer;
/*     */       }
/*     */     } 
/*     */     
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 162 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 170 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
/* 178 */     super.onFallenUpon(worldIn, pos, entityIn, fallDistance * 0.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLanded(World worldIn, Entity entityIn) {
/* 187 */     if (entityIn.isSneaking()) {
/*     */       
/* 189 */       super.onLanded(worldIn, entityIn);
/*     */     }
/* 191 */     else if (entityIn.motionY < 0.0D) {
/*     */       
/* 193 */       entityIn.motionY = -entityIn.motionY * 0.6600000262260437D;
/*     */       
/* 195 */       if (!(entityIn instanceof net.minecraft.entity.EntityLivingBase))
/*     */       {
/* 197 */         entityIn.motionY *= 0.8D;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 209 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 211 */     if (state.getValue((IProperty)PART) == EnumPartType.FOOT) {
/*     */       
/* 213 */       if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() != this)
/*     */       {
/* 215 */         worldIn.setBlockToAir(pos);
/*     */       }
/*     */     }
/* 218 */     else if (worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock() != this) {
/*     */       
/* 220 */       if (!worldIn.isRemote)
/*     */       {
/* 222 */         dropBlockAsItem(worldIn, pos, state, 0);
/*     */       }
/*     */       
/* 225 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 234 */     return (state.getValue((IProperty)PART) == EnumPartType.FOOT) ? Items.field_190931_a : Items.BED;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/* 239 */     return BED_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190946_v(IBlockState p_190946_1_) {
/* 244 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static BlockPos getSafeExitLocation(World worldIn, BlockPos pos, int tries) {
/* 254 */     EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING);
/* 255 */     int i = pos.getX();
/* 256 */     int j = pos.getY();
/* 257 */     int k = pos.getZ();
/*     */     
/* 259 */     for (int l = 0; l <= 1; l++) {
/*     */       
/* 261 */       int i1 = i - enumfacing.getFrontOffsetX() * l - 1;
/* 262 */       int j1 = k - enumfacing.getFrontOffsetZ() * l - 1;
/* 263 */       int k1 = i1 + 2;
/* 264 */       int l1 = j1 + 2;
/*     */       
/* 266 */       for (int i2 = i1; i2 <= k1; i2++) {
/*     */         
/* 268 */         for (int j2 = j1; j2 <= l1; j2++) {
/*     */           
/* 270 */           BlockPos blockpos = new BlockPos(i2, j, j2);
/*     */           
/* 272 */           if (hasRoomForPlayer(worldIn, blockpos)) {
/*     */             
/* 274 */             if (tries <= 0)
/*     */             {
/* 276 */               return blockpos;
/*     */             }
/*     */             
/* 279 */             tries--;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 285 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean hasRoomForPlayer(World worldIn, BlockPos pos) {
/* 290 */     return (worldIn.getBlockState(pos.down()).isFullyOpaque() && !worldIn.getBlockState(pos).getMaterial().isSolid() && !worldIn.getBlockState(pos.up()).getMaterial().isSolid());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 298 */     if (state.getValue((IProperty)PART) == EnumPartType.HEAD) {
/*     */       
/* 300 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/* 301 */       EnumDyeColor enumdyecolor = (tileentity instanceof TileEntityBed) ? ((TileEntityBed)tileentity).func_193048_a() : EnumDyeColor.RED;
/* 302 */       spawnAsEntity(worldIn, pos, new ItemStack(Items.BED, 1, enumdyecolor.getMetadata()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumPushReaction getMobilityFlag(IBlockState state) {
/* 308 */     return EnumPushReaction.DESTROY;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 313 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/* 322 */     return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 327 */     BlockPos blockpos = pos;
/*     */     
/* 329 */     if (state.getValue((IProperty)PART) == EnumPartType.FOOT)
/*     */     {
/* 331 */       blockpos = pos.offset((EnumFacing)state.getValue((IProperty)FACING));
/*     */     }
/*     */     
/* 334 */     TileEntity tileentity = worldIn.getTileEntity(blockpos);
/* 335 */     EnumDyeColor enumdyecolor = (tileentity instanceof TileEntityBed) ? ((TileEntityBed)tileentity).func_193048_a() : EnumDyeColor.RED;
/* 336 */     return new ItemStack(Items.BED, 1, enumdyecolor.getMetadata());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 341 */     if (player.capabilities.isCreativeMode && state.getValue((IProperty)PART) == EnumPartType.FOOT) {
/*     */       
/* 343 */       BlockPos blockpos = pos.offset((EnumFacing)state.getValue((IProperty)FACING));
/*     */       
/* 345 */       if (worldIn.getBlockState(blockpos).getBlock() == this)
/*     */       {
/* 347 */         worldIn.setBlockToAir(blockpos);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
/* 354 */     if (state.getValue((IProperty)PART) == EnumPartType.HEAD && te instanceof TileEntityBed) {
/*     */       
/* 356 */       TileEntityBed tileentitybed = (TileEntityBed)te;
/* 357 */       ItemStack itemstack = tileentitybed.func_193049_f();
/* 358 */       spawnAsEntity(worldIn, pos, itemstack);
/*     */     }
/*     */     else {
/*     */       
/* 362 */       super.harvestBlock(worldIn, player, pos, state, null, stack);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 371 */     super.breakBlock(worldIn, pos, state);
/* 372 */     worldIn.removeTileEntity(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 380 */     EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
/* 381 */     return ((meta & 0x8) > 0) ? getDefaultState().withProperty((IProperty)PART, EnumPartType.HEAD).withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)OCCUPIED, Boolean.valueOf(((meta & 0x4) > 0))) : getDefaultState().withProperty((IProperty)PART, EnumPartType.FOOT).withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 390 */     if (state.getValue((IProperty)PART) == EnumPartType.FOOT) {
/*     */       
/* 392 */       IBlockState iblockstate = worldIn.getBlockState(pos.offset((EnumFacing)state.getValue((IProperty)FACING)));
/*     */       
/* 394 */       if (iblockstate.getBlock() == this)
/*     */       {
/* 396 */         state = state.withProperty((IProperty)OCCUPIED, iblockstate.getValue((IProperty)OCCUPIED));
/*     */       }
/*     */     } 
/*     */     
/* 400 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 409 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 418 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 426 */     int i = 0;
/* 427 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 429 */     if (state.getValue((IProperty)PART) == EnumPartType.HEAD) {
/*     */       
/* 431 */       i |= 0x8;
/*     */       
/* 433 */       if (((Boolean)state.getValue((IProperty)OCCUPIED)).booleanValue())
/*     */       {
/* 435 */         i |= 0x4;
/*     */       }
/*     */     } 
/*     */     
/* 439 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 444 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 449 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)PART, (IProperty)OCCUPIED });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 457 */     return (TileEntity)new TileEntityBed();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean func_193385_b(int p_193385_0_) {
/* 462 */     return ((p_193385_0_ & 0x8) != 0);
/*     */   }
/*     */   
/*     */   public enum EnumPartType
/*     */     implements IStringSerializable {
/* 467 */     HEAD("head"),
/* 468 */     FOOT("foot");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumPartType(String name) {
/* 474 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 479 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 484 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockBed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */