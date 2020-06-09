/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public class BlockBeacon
/*     */   extends BlockContainer {
/*     */   public BlockBeacon() {
/*  28 */     super(Material.GLASS, MapColor.DIAMOND);
/*  29 */     setHardness(3.0F);
/*  30 */     setCreativeTab(CreativeTabs.MISC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  38 */     return (TileEntity)new TileEntityBeacon();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/*  43 */     if (worldIn.isRemote)
/*     */     {
/*  45 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  49 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  51 */     if (tileentity instanceof TileEntityBeacon) {
/*     */       
/*  53 */       playerIn.displayGUIChest((IInventory)tileentity);
/*  54 */       playerIn.addStat(StatList.BEACON_INTERACTION);
/*     */     } 
/*     */     
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/*  80 */     return EnumBlockRenderType.MODEL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/*  88 */     super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
/*     */     
/*  90 */     if (stack.hasDisplayName()) {
/*     */       
/*  92 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  94 */       if (tileentity instanceof TileEntityBeacon)
/*     */       {
/*  96 */         ((TileEntityBeacon)tileentity).setName(stack.getDisplayName());
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
/* 108 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 110 */     if (tileentity instanceof TileEntityBeacon) {
/*     */       
/* 112 */       ((TileEntityBeacon)tileentity).updateBeacon();
/* 113 */       worldIn.addBlockEvent(pos, this, 1, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 119 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateColorAsync(final World worldIn, final BlockPos glassPos) {
/* 124 */     HttpUtil.DOWNLOADER_EXECUTOR.submit(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 128 */             Chunk chunk = worldIn.getChunkFromBlockCoords(glassPos);
/*     */             
/* 130 */             for (int i = glassPos.getY() - 1; i >= 0; i--) {
/*     */               
/* 132 */               final BlockPos blockpos = new BlockPos(glassPos.getX(), i, glassPos.getZ());
/*     */               
/* 134 */               if (!chunk.canSeeSky(blockpos)) {
/*     */                 break;
/*     */               }
/*     */ 
/*     */               
/* 139 */               IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */               
/* 141 */               if (iblockstate.getBlock() == Blocks.BEACON)
/*     */               {
/* 143 */                 ((WorldServer)worldIn).addScheduledTask(new Runnable()
/*     */                     {
/*     */                       public void run()
/*     */                       {
/* 147 */                         TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */                         
/* 149 */                         if (tileentity instanceof TileEntityBeacon) {
/*     */                           
/* 151 */                           ((TileEntityBeacon)tileentity).updateBeacon();
/* 152 */                           worldIn.addBlockEvent(blockpos, Blocks.BEACON, 1, 0);
/*     */                         } 
/*     */                       }
/*     */                     });
/*     */               }
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockBeacon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */