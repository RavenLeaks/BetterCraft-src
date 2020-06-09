/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.BlockWorldState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockMaterialMatcher;
/*     */ import net.minecraft.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.block.state.pattern.BlockStateMatcher;
/*     */ import net.minecraft.block.state.pattern.FactoryBlockPattern;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.monster.EntitySnowman;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPumpkin
/*     */   extends BlockHorizontal {
/*     */   private BlockPattern snowmanBasePattern;
/*     */   private BlockPattern snowmanPattern;
/*     */   
/*  35 */   private static final Predicate<IBlockState> IS_PUMPKIN = new Predicate<IBlockState>()
/*     */     {
/*     */       public boolean apply(@Nullable IBlockState p_apply_1_)
/*     */       {
/*  39 */         return (p_apply_1_ != null && (p_apply_1_.getBlock() == Blocks.PUMPKIN || p_apply_1_.getBlock() == Blocks.LIT_PUMPKIN));
/*     */       }
/*     */     };
/*     */   private BlockPattern golemBasePattern; private BlockPattern golemPattern;
/*     */   
/*     */   protected BlockPumpkin() {
/*  45 */     super(Material.GOURD, MapColor.ADOBE);
/*  46 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  47 */     setTickRandomly(true);
/*  48 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  56 */     super.onBlockAdded(worldIn, pos, state);
/*  57 */     trySpawnGolem(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDispenserPlace(World worldIn, BlockPos pos) {
/*  62 */     return !(getSnowmanBasePattern().match(worldIn, pos) == null && getGolemBasePattern().match(worldIn, pos) == null);
/*     */   }
/*     */ 
/*     */   
/*     */   private void trySpawnGolem(World worldIn, BlockPos pos) {
/*  67 */     BlockPattern.PatternHelper blockpattern$patternhelper = getSnowmanPattern().match(worldIn, pos);
/*     */     
/*  69 */     if (blockpattern$patternhelper != null) {
/*     */       
/*  71 */       for (int i = 0; i < getSnowmanPattern().getThumbLength(); i++) {
/*     */         
/*  73 */         BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(0, i, 0);
/*  74 */         worldIn.setBlockState(blockworldstate.getPos(), Blocks.AIR.getDefaultState(), 2);
/*     */       } 
/*     */       
/*  77 */       EntitySnowman entitysnowman = new EntitySnowman(worldIn);
/*  78 */       BlockPos blockpos1 = blockpattern$patternhelper.translateOffset(0, 2, 0).getPos();
/*  79 */       entitysnowman.setLocationAndAngles(blockpos1.getX() + 0.5D, blockpos1.getY() + 0.05D, blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
/*  80 */       worldIn.spawnEntityInWorld((Entity)entitysnowman);
/*     */       
/*  82 */       for (EntityPlayerMP entityplayermp : worldIn.getEntitiesWithinAABB(EntityPlayerMP.class, entitysnowman.getEntityBoundingBox().expandXyz(5.0D)))
/*     */       {
/*  84 */         CriteriaTriggers.field_192133_m.func_192229_a(entityplayermp, (Entity)entitysnowman);
/*     */       }
/*     */       
/*  87 */       for (int l = 0; l < 120; l++)
/*     */       {
/*  89 */         worldIn.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, blockpos1.getX() + worldIn.rand.nextDouble(), blockpos1.getY() + worldIn.rand.nextDouble() * 2.5D, blockpos1.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */       
/*  92 */       for (int i1 = 0; i1 < getSnowmanPattern().getThumbLength(); i1++)
/*     */       {
/*  94 */         BlockWorldState blockworldstate2 = blockpattern$patternhelper.translateOffset(0, i1, 0);
/*  95 */         worldIn.notifyNeighborsRespectDebug(blockworldstate2.getPos(), Blocks.AIR, false);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 100 */       blockpattern$patternhelper = getGolemPattern().match(worldIn, pos);
/*     */       
/* 102 */       if (blockpattern$patternhelper != null) {
/*     */         
/* 104 */         for (int j = 0; j < getGolemPattern().getPalmLength(); j++) {
/*     */           
/* 106 */           for (int k = 0; k < getGolemPattern().getThumbLength(); k++)
/*     */           {
/* 108 */             worldIn.setBlockState(blockpattern$patternhelper.translateOffset(j, k, 0).getPos(), Blocks.AIR.getDefaultState(), 2);
/*     */           }
/*     */         } 
/*     */         
/* 112 */         BlockPos blockpos = blockpattern$patternhelper.translateOffset(1, 2, 0).getPos();
/* 113 */         EntityIronGolem entityirongolem = new EntityIronGolem(worldIn);
/* 114 */         entityirongolem.setPlayerCreated(true);
/* 115 */         entityirongolem.setLocationAndAngles(blockpos.getX() + 0.5D, blockpos.getY() + 0.05D, blockpos.getZ() + 0.5D, 0.0F, 0.0F);
/* 116 */         worldIn.spawnEntityInWorld((Entity)entityirongolem);
/*     */         
/* 118 */         for (EntityPlayerMP entityplayermp1 : worldIn.getEntitiesWithinAABB(EntityPlayerMP.class, entityirongolem.getEntityBoundingBox().expandXyz(5.0D)))
/*     */         {
/* 120 */           CriteriaTriggers.field_192133_m.func_192229_a(entityplayermp1, (Entity)entityirongolem);
/*     */         }
/*     */         
/* 123 */         for (int j1 = 0; j1 < 120; j1++)
/*     */         {
/* 125 */           worldIn.spawnParticle(EnumParticleTypes.SNOWBALL, blockpos.getX() + worldIn.rand.nextDouble(), blockpos.getY() + worldIn.rand.nextDouble() * 3.9D, blockpos.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */         
/* 128 */         for (int k1 = 0; k1 < getGolemPattern().getPalmLength(); k1++) {
/*     */           
/* 130 */           for (int l1 = 0; l1 < getGolemPattern().getThumbLength(); l1++) {
/*     */             
/* 132 */             BlockWorldState blockworldstate1 = blockpattern$patternhelper.translateOffset(k1, l1, 0);
/* 133 */             worldIn.notifyNeighborsRespectDebug(blockworldstate1.getPos(), Blocks.AIR, false);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 142 */     return ((worldIn.getBlockState(pos).getBlock()).blockMaterial.isReplaceable() && worldIn.getBlockState(pos.down()).isFullyOpaque());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 151 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 160 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 169 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 177 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 185 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 190 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockPattern getSnowmanBasePattern() {
/* 195 */     if (this.snowmanBasePattern == null)
/*     */     {
/* 197 */       this.snowmanBasePattern = FactoryBlockPattern.start().aisle(new String[] { " ", "#", "#" }).where('#', BlockWorldState.hasState((Predicate)BlockStateMatcher.forBlock(Blocks.SNOW))).build();
/*     */     }
/*     */     
/* 200 */     return this.snowmanBasePattern;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockPattern getSnowmanPattern() {
/* 205 */     if (this.snowmanPattern == null)
/*     */     {
/* 207 */       this.snowmanPattern = FactoryBlockPattern.start().aisle(new String[] { "^", "#", "#" }).where('^', BlockWorldState.hasState(IS_PUMPKIN)).where('#', BlockWorldState.hasState((Predicate)BlockStateMatcher.forBlock(Blocks.SNOW))).build();
/*     */     }
/*     */     
/* 210 */     return this.snowmanPattern;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockPattern getGolemBasePattern() {
/* 215 */     if (this.golemBasePattern == null)
/*     */     {
/* 217 */       this.golemBasePattern = FactoryBlockPattern.start().aisle(new String[] { "~ ~", "###", "~#~" }).where('#', BlockWorldState.hasState((Predicate)BlockStateMatcher.forBlock(Blocks.IRON_BLOCK))).where('~', BlockWorldState.hasState((Predicate)BlockMaterialMatcher.forMaterial(Material.AIR))).build();
/*     */     }
/*     */     
/* 220 */     return this.golemBasePattern;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockPattern getGolemPattern() {
/* 225 */     if (this.golemPattern == null)
/*     */     {
/* 227 */       this.golemPattern = FactoryBlockPattern.start().aisle(new String[] { "~^~", "###", "~#~" }).where('^', BlockWorldState.hasState(IS_PUMPKIN)).where('#', BlockWorldState.hasState((Predicate)BlockStateMatcher.forBlock(Blocks.IRON_BLOCK))).where('~', BlockWorldState.hasState((Predicate)BlockMaterialMatcher.forMaterial(Material.AIR))).build();
/*     */     }
/*     */     
/* 230 */     return this.golemPattern;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockPumpkin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */