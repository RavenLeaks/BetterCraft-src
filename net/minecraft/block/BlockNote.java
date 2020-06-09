/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityNote;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockNote
/*     */   extends BlockContainer {
/*  24 */   private static final List<SoundEvent> INSTRUMENTS = Lists.newArrayList((Object[])new SoundEvent[] { SoundEvents.BLOCK_NOTE_HARP, SoundEvents.BLOCK_NOTE_BASEDRUM, SoundEvents.BLOCK_NOTE_SNARE, SoundEvents.BLOCK_NOTE_HAT, SoundEvents.BLOCK_NOTE_BASS, SoundEvents.field_193809_ey, SoundEvents.field_193807_ew, SoundEvents.field_193810_ez, SoundEvents.field_193808_ex, SoundEvents.field_193785_eE });
/*     */ 
/*     */   
/*     */   public BlockNote() {
/*  28 */     super(Material.WOOD);
/*  29 */     setCreativeTab(CreativeTabs.REDSTONE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/*  39 */     boolean flag = worldIn.isBlockPowered(pos);
/*  40 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  42 */     if (tileentity instanceof TileEntityNote) {
/*     */       
/*  44 */       TileEntityNote tileentitynote = (TileEntityNote)tileentity;
/*     */       
/*  46 */       if (tileentitynote.previousRedstoneState != flag) {
/*     */         
/*  48 */         if (flag)
/*     */         {
/*  50 */           tileentitynote.triggerNote(worldIn, pos);
/*     */         }
/*     */         
/*  53 */         tileentitynote.previousRedstoneState = flag;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/*  60 */     if (worldIn.isRemote)
/*     */     {
/*  62 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  66 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  68 */     if (tileentity instanceof TileEntityNote) {
/*     */       
/*  70 */       TileEntityNote tileentitynote = (TileEntityNote)tileentity;
/*  71 */       tileentitynote.changePitch();
/*  72 */       tileentitynote.triggerNote(worldIn, pos);
/*  73 */       playerIn.addStat(StatList.NOTEBLOCK_TUNED);
/*     */     } 
/*     */     
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/*  82 */     if (!worldIn.isRemote) {
/*     */       
/*  84 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  86 */       if (tileentity instanceof TileEntityNote) {
/*     */         
/*  88 */         ((TileEntityNote)tileentity).triggerNote(worldIn, pos);
/*  89 */         playerIn.addStat(StatList.NOTEBLOCK_PLAYED);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  99 */     return (TileEntity)new TileEntityNote();
/*     */   }
/*     */ 
/*     */   
/*     */   private SoundEvent getInstrument(int p_185576_1_) {
/* 104 */     if (p_185576_1_ < 0 || p_185576_1_ >= INSTRUMENTS.size())
/*     */     {
/* 106 */       p_185576_1_ = 0;
/*     */     }
/*     */     
/* 109 */     return INSTRUMENTS.get(p_185576_1_);
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
/* 120 */     float f = (float)Math.pow(2.0D, (param - 12) / 12.0D);
/* 121 */     worldIn.playSound(null, pos, getInstrument(id), SoundCategory.RECORDS, 3.0F, f);
/* 122 */     worldIn.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5D, pos.getY() + 1.2D, pos.getZ() + 0.5D, param / 24.0D, 0.0D, 0.0D, new int[0]);
/* 123 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/* 132 */     return EnumBlockRenderType.MODEL;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockNote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */