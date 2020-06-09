/*     */ package net.minecraft.block;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockContainer
/*     */   extends Block
/*     */   implements ITileEntityProvider {
/*     */   protected BlockContainer(Material materialIn) {
/*  25 */     this(materialIn, materialIn.getMaterialMapColor());
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockContainer(Material materialIn, MapColor color) {
/*  30 */     super(materialIn, color);
/*  31 */     this.isBlockContainer = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isInvalidNeighbor(World worldIn, BlockPos pos, EnumFacing facing) {
/*  36 */     return (worldIn.getBlockState(pos.offset(facing)).getMaterial() == Material.CACTUS);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasInvalidNeighbor(World worldIn, BlockPos pos) {
/*  41 */     return !(!isInvalidNeighbor(worldIn, pos, EnumFacing.NORTH) && !isInvalidNeighbor(worldIn, pos, EnumFacing.SOUTH) && !isInvalidNeighbor(worldIn, pos, EnumFacing.WEST) && !isInvalidNeighbor(worldIn, pos, EnumFacing.EAST));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/*  50 */     return EnumBlockRenderType.INVISIBLE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  58 */     super.breakBlock(worldIn, pos, state);
/*  59 */     worldIn.removeTileEntity(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
/*  64 */     if (te instanceof IWorldNameable && ((IWorldNameable)te).hasCustomName()) {
/*     */       
/*  66 */       player.addStat(StatList.getBlockStats(this));
/*  67 */       player.addExhaustion(0.005F);
/*     */       
/*  69 */       if (worldIn.isRemote) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  74 */       int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
/*  75 */       Item item = getItemDropped(state, worldIn.rand, i);
/*     */       
/*  77 */       if (item == Items.field_190931_a) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  82 */       ItemStack itemstack = new ItemStack(item, quantityDropped(worldIn.rand));
/*  83 */       itemstack.setStackDisplayName(((IWorldNameable)te).getName());
/*  84 */       spawnAsEntity(worldIn, pos, itemstack);
/*     */     }
/*     */     else {
/*     */       
/*  88 */       super.harvestBlock(worldIn, player, pos, state, null, stack);
/*     */     } 
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
/* 100 */     super.eventReceived(state, worldIn, pos, id, param);
/* 101 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 102 */     return (tileentity == null) ? false : tileentity.receiveClientEvent(id, param);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */