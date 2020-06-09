/*     */ package net.minecraft.block;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.walkers.ItemStackData;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockJukebox extends BlockContainer {
/*  29 */   public static final PropertyBool HAS_RECORD = PropertyBool.create("has_record");
/*     */ 
/*     */   
/*     */   public static void registerFixesJukebox(DataFixer fixer) {
/*  33 */     fixer.registerWalker(FixTypes.BLOCK_ENTITY, (IDataWalker)new ItemStackData(TileEntityJukebox.class, new String[] { "RecordItem" }));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockJukebox() {
/*  38 */     super(Material.WOOD, MapColor.DIRT);
/*  39 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)HAS_RECORD, Boolean.valueOf(false)));
/*  40 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/*  45 */     if (((Boolean)state.getValue((IProperty)HAS_RECORD)).booleanValue()) {
/*     */       
/*  47 */       dropRecord(worldIn, pos, state);
/*  48 */       state = state.withProperty((IProperty)HAS_RECORD, Boolean.valueOf(false));
/*  49 */       worldIn.setBlockState(pos, state, 2);
/*  50 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertRecord(World worldIn, BlockPos pos, IBlockState state, ItemStack recordStack) {
/*  60 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  62 */     if (tileentity instanceof TileEntityJukebox) {
/*     */       
/*  64 */       ((TileEntityJukebox)tileentity).setRecord(recordStack.copy());
/*  65 */       worldIn.setBlockState(pos, state.withProperty((IProperty)HAS_RECORD, Boolean.valueOf(true)), 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void dropRecord(World worldIn, BlockPos pos, IBlockState state) {
/*  71 */     if (!worldIn.isRemote) {
/*     */       
/*  73 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  75 */       if (tileentity instanceof TileEntityJukebox) {
/*     */         
/*  77 */         TileEntityJukebox blockjukebox$tileentityjukebox = (TileEntityJukebox)tileentity;
/*  78 */         ItemStack itemstack = blockjukebox$tileentityjukebox.getRecord();
/*     */         
/*  80 */         if (!itemstack.func_190926_b()) {
/*     */           
/*  82 */           worldIn.playEvent(1010, pos, 0);
/*  83 */           worldIn.playRecord(pos, null);
/*  84 */           blockjukebox$tileentityjukebox.setRecord(ItemStack.field_190927_a);
/*  85 */           float f = 0.7F;
/*  86 */           double d0 = (worldIn.rand.nextFloat() * 0.7F) + 0.15000000596046448D;
/*  87 */           double d1 = (worldIn.rand.nextFloat() * 0.7F) + 0.06000000238418579D + 0.6D;
/*  88 */           double d2 = (worldIn.rand.nextFloat() * 0.7F) + 0.15000000596046448D;
/*  89 */           ItemStack itemstack1 = itemstack.copy();
/*  90 */           EntityItem entityitem = new EntityItem(worldIn, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, itemstack1);
/*  91 */           entityitem.setDefaultPickupDelay();
/*  92 */           worldIn.spawnEntityInWorld((Entity)entityitem);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 103 */     dropRecord(worldIn, pos, state);
/* 104 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 112 */     if (!worldIn.isRemote)
/*     */     {
/* 114 */       super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 123 */     return new TileEntityJukebox();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride(IBlockState state) {
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
/* 133 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 135 */     if (tileentity instanceof TileEntityJukebox) {
/*     */       
/* 137 */       ItemStack itemstack = ((TileEntityJukebox)tileentity).getRecord();
/*     */       
/* 139 */       if (!itemstack.func_190926_b())
/*     */       {
/* 141 */         return Item.getIdFromItem(itemstack.getItem()) + 1 - Item.getIdFromItem(Items.RECORD_13);
/*     */       }
/*     */     } 
/*     */     
/* 145 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/* 154 */     return EnumBlockRenderType.MODEL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 162 */     return getDefaultState().withProperty((IProperty)HAS_RECORD, Boolean.valueOf((meta > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 170 */     return ((Boolean)state.getValue((IProperty)HAS_RECORD)).booleanValue() ? 1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 175 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)HAS_RECORD });
/*     */   }
/*     */   
/*     */   public static class TileEntityJukebox
/*     */     extends TileEntity {
/* 180 */     private ItemStack record = ItemStack.field_190927_a;
/*     */ 
/*     */     
/*     */     public void readFromNBT(NBTTagCompound compound) {
/* 184 */       super.readFromNBT(compound);
/*     */       
/* 186 */       if (compound.hasKey("RecordItem", 10)) {
/*     */         
/* 188 */         setRecord(new ItemStack(compound.getCompoundTag("RecordItem")));
/*     */       }
/* 190 */       else if (compound.getInteger("Record") > 0) {
/*     */         
/* 192 */         setRecord(new ItemStack(Item.getItemById(compound.getInteger("Record"))));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 198 */       super.writeToNBT(compound);
/*     */       
/* 200 */       if (!getRecord().func_190926_b())
/*     */       {
/* 202 */         compound.setTag("RecordItem", (NBTBase)getRecord().writeToNBT(new NBTTagCompound()));
/*     */       }
/*     */       
/* 205 */       return compound;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getRecord() {
/* 210 */       return this.record;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setRecord(ItemStack recordStack) {
/* 215 */       this.record = recordStack;
/* 216 */       markDirty();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockJukebox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */