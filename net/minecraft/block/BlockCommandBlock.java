/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.CommandBlockBaseLogic;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.GameRules;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class BlockCommandBlock
/*     */   extends BlockContainer {
/*  33 */   private static final Logger field_193388_c = LogManager.getLogger();
/*  34 */   public static final PropertyDirection FACING = BlockDirectional.FACING;
/*  35 */   public static final PropertyBool CONDITIONAL = PropertyBool.create("conditional");
/*     */ 
/*     */   
/*     */   public BlockCommandBlock(MapColor color) {
/*  39 */     super(Material.IRON, color);
/*  40 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)CONDITIONAL, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  48 */     TileEntityCommandBlock tileentitycommandblock = new TileEntityCommandBlock();
/*  49 */     tileentitycommandblock.setAuto((this == Blocks.CHAIN_COMMAND_BLOCK));
/*  50 */     return (TileEntity)tileentitycommandblock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/*  60 */     if (!worldIn.isRemote) {
/*     */       
/*  62 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  64 */       if (tileentity instanceof TileEntityCommandBlock) {
/*     */         
/*  66 */         TileEntityCommandBlock tileentitycommandblock = (TileEntityCommandBlock)tileentity;
/*  67 */         boolean flag = worldIn.isBlockPowered(pos);
/*  68 */         boolean flag1 = tileentitycommandblock.isPowered();
/*  69 */         tileentitycommandblock.setPowered(flag);
/*     */         
/*  71 */         if (!flag1 && !tileentitycommandblock.isAuto() && tileentitycommandblock.getMode() != TileEntityCommandBlock.Mode.SEQUENCE)
/*     */         {
/*  73 */           if (flag) {
/*     */             
/*  75 */             tileentitycommandblock.setConditionMet();
/*  76 */             worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  85 */     if (!worldIn.isRemote) {
/*     */       
/*  87 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  89 */       if (tileentity instanceof TileEntityCommandBlock) {
/*     */         
/*  91 */         TileEntityCommandBlock tileentitycommandblock = (TileEntityCommandBlock)tileentity;
/*  92 */         CommandBlockBaseLogic commandblockbaselogic = tileentitycommandblock.getCommandBlockLogic();
/*  93 */         boolean flag = !StringUtils.isNullOrEmpty(commandblockbaselogic.getCommand());
/*  94 */         TileEntityCommandBlock.Mode tileentitycommandblock$mode = tileentitycommandblock.getMode();
/*  95 */         boolean flag1 = tileentitycommandblock.isConditionMet();
/*     */         
/*  97 */         if (tileentitycommandblock$mode == TileEntityCommandBlock.Mode.AUTO) {
/*     */           
/*  99 */           tileentitycommandblock.setConditionMet();
/*     */           
/* 101 */           if (flag1) {
/*     */             
/* 103 */             func_193387_a(state, worldIn, pos, commandblockbaselogic, flag);
/*     */           }
/* 105 */           else if (tileentitycommandblock.isConditional()) {
/*     */             
/* 107 */             commandblockbaselogic.setSuccessCount(0);
/*     */           } 
/*     */           
/* 110 */           if (tileentitycommandblock.isPowered() || tileentitycommandblock.isAuto())
/*     */           {
/* 112 */             worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */           }
/*     */         }
/* 115 */         else if (tileentitycommandblock$mode == TileEntityCommandBlock.Mode.REDSTONE) {
/*     */           
/* 117 */           if (flag1) {
/*     */             
/* 119 */             func_193387_a(state, worldIn, pos, commandblockbaselogic, flag);
/*     */           }
/* 121 */           else if (tileentitycommandblock.isConditional()) {
/*     */             
/* 123 */             commandblockbaselogic.setSuccessCount(0);
/*     */           } 
/*     */         } 
/*     */         
/* 127 */         worldIn.updateComparatorOutputLevel(pos, this);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_193387_a(IBlockState p_193387_1_, World p_193387_2_, BlockPos p_193387_3_, CommandBlockBaseLogic p_193387_4_, boolean p_193387_5_) {
/* 134 */     if (p_193387_5_) {
/*     */       
/* 136 */       p_193387_4_.trigger(p_193387_2_);
/*     */     }
/*     */     else {
/*     */       
/* 140 */       p_193387_4_.setSuccessCount(0);
/*     */     } 
/*     */     
/* 143 */     func_193386_c(p_193387_2_, p_193387_3_, (EnumFacing)p_193387_1_.getValue((IProperty)FACING));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/* 151 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 156 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 158 */     if (tileentity instanceof TileEntityCommandBlock && playerIn.canUseCommandBlock()) {
/*     */       
/* 160 */       playerIn.displayGuiCommandBlock((TileEntityCommandBlock)tileentity);
/* 161 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 165 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride(IBlockState state) {
/* 171 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
/* 176 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 177 */     return (tileentity instanceof TileEntityCommandBlock) ? ((TileEntityCommandBlock)tileentity).getCommandBlockLogic().getSuccessCount() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 185 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 187 */     if (tileentity instanceof TileEntityCommandBlock) {
/*     */       
/* 189 */       TileEntityCommandBlock tileentitycommandblock = (TileEntityCommandBlock)tileentity;
/* 190 */       CommandBlockBaseLogic commandblockbaselogic = tileentitycommandblock.getCommandBlockLogic();
/*     */       
/* 192 */       if (stack.hasDisplayName())
/*     */       {
/* 194 */         commandblockbaselogic.setName(stack.getDisplayName());
/*     */       }
/*     */       
/* 197 */       if (!worldIn.isRemote) {
/*     */         
/* 199 */         NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */         
/* 201 */         if (nbttagcompound == null || !nbttagcompound.hasKey("BlockEntityTag", 10)) {
/*     */           
/* 203 */           commandblockbaselogic.setTrackOutput(worldIn.getGameRules().getBoolean("sendCommandFeedback"));
/* 204 */           tileentitycommandblock.setAuto((this == Blocks.CHAIN_COMMAND_BLOCK));
/*     */         } 
/*     */         
/* 207 */         if (tileentitycommandblock.getMode() == TileEntityCommandBlock.Mode.SEQUENCE) {
/*     */           
/* 209 */           boolean flag = worldIn.isBlockPowered(pos);
/* 210 */           tileentitycommandblock.setPowered(flag);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 221 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/* 230 */     return EnumBlockRenderType.MODEL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 238 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getFront(meta & 0x7)).withProperty((IProperty)CONDITIONAL, Boolean.valueOf(((meta & 0x8) != 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 246 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex() | (((Boolean)state.getValue((IProperty)CONDITIONAL)).booleanValue() ? 8 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 255 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 264 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 269 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)CONDITIONAL });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 278 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.func_190914_a(pos, placer)).withProperty((IProperty)CONDITIONAL, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void func_193386_c(World p_193386_0_, BlockPos p_193386_1_, EnumFacing p_193386_2_) {
/* 283 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_193386_1_);
/* 284 */     GameRules gamerules = p_193386_0_.getGameRules();
/*     */     
/*     */     int i;
/*     */     
/* 288 */     for (i = gamerules.getInt("maxCommandChainLength"); i-- > 0; p_193386_2_ = (EnumFacing)iblockstate.getValue((IProperty)FACING)) {
/*     */       
/* 290 */       blockpos$mutableblockpos.move(p_193386_2_);
/* 291 */       IBlockState iblockstate = p_193386_0_.getBlockState((BlockPos)blockpos$mutableblockpos);
/* 292 */       Block block = iblockstate.getBlock();
/*     */       
/* 294 */       if (block != Blocks.CHAIN_COMMAND_BLOCK) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 299 */       TileEntity tileentity = p_193386_0_.getTileEntity((BlockPos)blockpos$mutableblockpos);
/*     */       
/* 301 */       if (!(tileentity instanceof TileEntityCommandBlock)) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 306 */       TileEntityCommandBlock tileentitycommandblock = (TileEntityCommandBlock)tileentity;
/*     */       
/* 308 */       if (tileentitycommandblock.getMode() != TileEntityCommandBlock.Mode.SEQUENCE) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 313 */       if (tileentitycommandblock.isPowered() || tileentitycommandblock.isAuto()) {
/*     */         
/* 315 */         CommandBlockBaseLogic commandblockbaselogic = tileentitycommandblock.getCommandBlockLogic();
/*     */         
/* 317 */         if (tileentitycommandblock.setConditionMet()) {
/*     */           
/* 319 */           if (!commandblockbaselogic.trigger(p_193386_0_)) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/* 324 */           p_193386_0_.updateComparatorOutputLevel((BlockPos)blockpos$mutableblockpos, block);
/*     */         }
/* 326 */         else if (tileentitycommandblock.isConditional()) {
/*     */           
/* 328 */           commandblockbaselogic.setSuccessCount(0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 333 */     if (i <= 0) {
/*     */       
/* 335 */       int j = Math.max(gamerules.getInt("maxCommandChainLength"), 0);
/* 336 */       field_193388_c.warn("Commandblock chain tried to execure more than " + j + " steps!");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockCommandBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */