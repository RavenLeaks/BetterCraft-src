/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBlock
/*     */   extends Item {
/*     */   protected final Block block;
/*     */   
/*     */   public ItemBlock(Block block) {
/*  32 */     this.block = block;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/*  40 */     IBlockState iblockstate = playerIn.getBlockState(worldIn);
/*  41 */     Block block = iblockstate.getBlock();
/*     */     
/*  43 */     if (!block.isReplaceable((IBlockAccess)playerIn, worldIn))
/*     */     {
/*  45 */       worldIn = worldIn.offset(hand);
/*     */     }
/*     */     
/*  48 */     ItemStack itemstack = stack.getHeldItem(pos);
/*     */     
/*  50 */     if (!itemstack.func_190926_b() && stack.canPlayerEdit(worldIn, hand, itemstack) && playerIn.func_190527_a(this.block, worldIn, false, hand, null)) {
/*     */       
/*  52 */       int i = getMetadata(itemstack.getMetadata());
/*  53 */       IBlockState iblockstate1 = this.block.onBlockPlaced(playerIn, worldIn, hand, facing, hitX, hitY, i, (EntityLivingBase)stack);
/*     */       
/*  55 */       if (playerIn.setBlockState(worldIn, iblockstate1, 11)) {
/*     */         
/*  57 */         iblockstate1 = playerIn.getBlockState(worldIn);
/*     */         
/*  59 */         if (iblockstate1.getBlock() == this.block) {
/*     */           
/*  61 */           setTileEntityNBT(playerIn, stack, worldIn, itemstack);
/*  62 */           this.block.onBlockPlacedBy(playerIn, worldIn, iblockstate1, (EntityLivingBase)stack, itemstack);
/*     */           
/*  64 */           if (stack instanceof EntityPlayerMP)
/*     */           {
/*  66 */             CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)stack, worldIn, itemstack);
/*     */           }
/*     */         } 
/*     */         
/*  70 */         SoundType soundtype = this.block.getSoundType();
/*  71 */         playerIn.playSound(stack, worldIn, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
/*  72 */         itemstack.func_190918_g(1);
/*     */       } 
/*     */       
/*  75 */       return EnumActionResult.SUCCESS;
/*     */     } 
/*     */ 
/*     */     
/*  79 */     return EnumActionResult.FAIL;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setTileEntityNBT(World worldIn, @Nullable EntityPlayer player, BlockPos pos, ItemStack stackIn) {
/*  85 */     MinecraftServer minecraftserver = worldIn.getMinecraftServer();
/*     */     
/*  87 */     if (minecraftserver == null)
/*     */     {
/*  89 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  93 */     NBTTagCompound nbttagcompound = stackIn.getSubCompound("BlockEntityTag");
/*     */     
/*  95 */     if (nbttagcompound != null) {
/*     */       
/*  97 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  99 */       if (tileentity != null) {
/*     */         
/* 101 */         if (!worldIn.isRemote && tileentity.onlyOpsCanSetNbt() && (player == null || !player.canUseCommandBlock()))
/*     */         {
/* 103 */           return false;
/*     */         }
/*     */         
/* 106 */         NBTTagCompound nbttagcompound1 = tileentity.writeToNBT(new NBTTagCompound());
/* 107 */         NBTTagCompound nbttagcompound2 = nbttagcompound1.copy();
/* 108 */         nbttagcompound1.merge(nbttagcompound);
/* 109 */         nbttagcompound1.setInteger("x", pos.getX());
/* 110 */         nbttagcompound1.setInteger("y", pos.getY());
/* 111 */         nbttagcompound1.setInteger("z", pos.getZ());
/*     */         
/* 113 */         if (!nbttagcompound1.equals(nbttagcompound2)) {
/*     */           
/* 115 */           tileentity.readFromNBT(nbttagcompound1);
/* 116 */           tileentity.markDirty();
/* 117 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
/* 128 */     Block block = worldIn.getBlockState(pos).getBlock();
/*     */     
/* 130 */     if (block == Blocks.SNOW_LAYER) {
/*     */       
/* 132 */       side = EnumFacing.UP;
/*     */     }
/* 134 */     else if (!block.isReplaceable((IBlockAccess)worldIn, pos)) {
/*     */       
/* 136 */       pos = pos.offset(side);
/*     */     } 
/*     */     
/* 139 */     return worldIn.func_190527_a(this.block, pos, false, side, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/* 148 */     return this.block.getUnlocalizedName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName() {
/* 156 */     return this.block.getUnlocalizedName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreativeTabs getCreativeTab() {
/* 164 */     return this.block.getCreativeTabToDisplayOn();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/* 172 */     if (func_194125_a(itemIn))
/*     */     {
/* 174 */       this.block.getSubBlocks(itemIn, tab);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
/* 183 */     super.addInformation(stack, playerIn, tooltip, advanced);
/* 184 */     this.block.func_190948_a(stack, playerIn, tooltip, advanced);
/*     */   }
/*     */ 
/*     */   
/*     */   public Block getBlock() {
/* 189 */     return this.block;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */