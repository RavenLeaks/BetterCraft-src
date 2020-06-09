/*     */ package net.minecraft.item;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.block.BlockStandingSign;
/*     */ import net.minecraft.block.BlockWallSign;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.BannerPattern;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBanner extends ItemBlock {
/*     */   public ItemBanner() {
/*  33 */     super(Blocks.STANDING_BANNER);
/*  34 */     this.maxStackSize = 16;
/*  35 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*  36 */     setHasSubtypes(true);
/*  37 */     setMaxDamage(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/*  45 */     IBlockState iblockstate = playerIn.getBlockState(worldIn);
/*  46 */     boolean flag = iblockstate.getBlock().isReplaceable((IBlockAccess)playerIn, worldIn);
/*     */     
/*  48 */     if (hand != EnumFacing.DOWN && (iblockstate.getMaterial().isSolid() || flag) && (!flag || hand == EnumFacing.UP)) {
/*     */       
/*  50 */       worldIn = worldIn.offset(hand);
/*  51 */       ItemStack itemstack = stack.getHeldItem(pos);
/*     */       
/*  53 */       if (stack.canPlayerEdit(worldIn, hand, itemstack) && Blocks.STANDING_BANNER.canPlaceBlockAt(playerIn, worldIn)) {
/*     */         
/*  55 */         if (playerIn.isRemote)
/*     */         {
/*  57 */           return EnumActionResult.SUCCESS;
/*     */         }
/*     */ 
/*     */         
/*  61 */         worldIn = flag ? worldIn.down() : worldIn;
/*     */         
/*  63 */         if (hand == EnumFacing.UP) {
/*     */           
/*  65 */           int i = MathHelper.floor(((stack.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 0xF;
/*  66 */           playerIn.setBlockState(worldIn, Blocks.STANDING_BANNER.getDefaultState().withProperty((IProperty)BlockStandingSign.ROTATION, Integer.valueOf(i)), 3);
/*     */         }
/*     */         else {
/*     */           
/*  70 */           playerIn.setBlockState(worldIn, Blocks.WALL_BANNER.getDefaultState().withProperty((IProperty)BlockWallSign.FACING, (Comparable)hand), 3);
/*     */         } 
/*     */         
/*  73 */         TileEntity tileentity = playerIn.getTileEntity(worldIn);
/*     */         
/*  75 */         if (tileentity instanceof TileEntityBanner)
/*     */         {
/*  77 */           ((TileEntityBanner)tileentity).setItemValues(itemstack, false);
/*     */         }
/*     */         
/*  80 */         if (stack instanceof EntityPlayerMP)
/*     */         {
/*  82 */           CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)stack, worldIn, itemstack);
/*     */         }
/*     */         
/*  85 */         itemstack.func_190918_g(1);
/*  86 */         return EnumActionResult.SUCCESS;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  91 */       return EnumActionResult.FAIL;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  96 */     return EnumActionResult.FAIL;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/* 102 */     String s = "item.banner.";
/* 103 */     EnumDyeColor enumdyecolor = getBaseColor(stack);
/* 104 */     s = String.valueOf(s) + enumdyecolor.getUnlocalizedName() + ".name";
/* 105 */     return I18n.translateToLocal(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void appendHoverTextFromTileEntityTag(ItemStack stack, List<String> p_185054_1_) {
/* 110 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag");
/*     */     
/* 112 */     if (nbttagcompound != null && nbttagcompound.hasKey("Patterns")) {
/*     */       
/* 114 */       NBTTagList nbttaglist = nbttagcompound.getTagList("Patterns", 10);
/*     */       
/* 116 */       for (int i = 0; i < nbttaglist.tagCount() && i < 6; i++) {
/*     */         
/* 118 */         NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 119 */         EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(nbttagcompound1.getInteger("Color"));
/* 120 */         BannerPattern bannerpattern = BannerPattern.func_190994_a(nbttagcompound1.getString("Pattern"));
/*     */         
/* 122 */         if (bannerpattern != null)
/*     */         {
/* 124 */           p_185054_1_.add(I18n.translateToLocal("item.banner." + bannerpattern.func_190997_a() + "." + enumdyecolor.getUnlocalizedName()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
/* 135 */     appendHoverTextFromTileEntityTag(stack, tooltip);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/* 143 */     if (func_194125_a(itemIn)) {
/*     */       byte b; int i; EnumDyeColor[] arrayOfEnumDyeColor;
/* 145 */       for (i = (arrayOfEnumDyeColor = EnumDyeColor.values()).length, b = 0; b < i; ) { EnumDyeColor enumdyecolor = arrayOfEnumDyeColor[b];
/*     */         
/* 147 */         tab.add(func_190910_a(enumdyecolor, (NBTTagList)null));
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ItemStack func_190910_a(EnumDyeColor p_190910_0_, @Nullable NBTTagList p_190910_1_) {
/* 154 */     ItemStack itemstack = new ItemStack(Items.BANNER, 1, p_190910_0_.getDyeDamage());
/*     */     
/* 156 */     if (p_190910_1_ != null && !p_190910_1_.hasNoTags())
/*     */     {
/* 158 */       itemstack.func_190925_c("BlockEntityTag").setTag("Patterns", (NBTBase)p_190910_1_.copy());
/*     */     }
/*     */     
/* 161 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreativeTabs getCreativeTab() {
/* 169 */     return CreativeTabs.DECORATIONS;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumDyeColor getBaseColor(ItemStack stack) {
/* 174 */     return EnumDyeColor.byDyeDamage(stack.getMetadata() & 0xF);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */