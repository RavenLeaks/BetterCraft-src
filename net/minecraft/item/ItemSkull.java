/*     */ package net.minecraft.item;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockSkull;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class ItemSkull extends Item {
/*  29 */   private static final String[] SKULL_TYPES = new String[] { "skeleton", "wither", "zombie", "char", "creeper", "dragon" };
/*     */ 
/*     */   
/*     */   public ItemSkull() {
/*  33 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*  34 */     setMaxDamage(0);
/*  35 */     setHasSubtypes(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/*  43 */     if (hand == EnumFacing.DOWN)
/*     */     {
/*  45 */       return EnumActionResult.FAIL;
/*     */     }
/*     */ 
/*     */     
/*  49 */     IBlockState iblockstate = playerIn.getBlockState(worldIn);
/*  50 */     Block block = iblockstate.getBlock();
/*  51 */     boolean flag = block.isReplaceable((IBlockAccess)playerIn, worldIn);
/*     */     
/*  53 */     if (!flag) {
/*     */       
/*  55 */       if (!playerIn.getBlockState(worldIn).getMaterial().isSolid())
/*     */       {
/*  57 */         return EnumActionResult.FAIL;
/*     */       }
/*     */       
/*  60 */       worldIn = worldIn.offset(hand);
/*     */     } 
/*     */     
/*  63 */     ItemStack itemstack = stack.getHeldItem(pos);
/*     */     
/*  65 */     if (stack.canPlayerEdit(worldIn, hand, itemstack) && Blocks.SKULL.canPlaceBlockAt(playerIn, worldIn)) {
/*     */       
/*  67 */       if (playerIn.isRemote)
/*     */       {
/*  69 */         return EnumActionResult.SUCCESS;
/*     */       }
/*     */ 
/*     */       
/*  73 */       playerIn.setBlockState(worldIn, Blocks.SKULL.getDefaultState().withProperty((IProperty)BlockSkull.FACING, (Comparable)hand), 11);
/*  74 */       int i = 0;
/*     */       
/*  76 */       if (hand == EnumFacing.UP)
/*     */       {
/*  78 */         i = MathHelper.floor((stack.rotationYaw * 16.0F / 360.0F) + 0.5D) & 0xF;
/*     */       }
/*     */       
/*  81 */       TileEntity tileentity = playerIn.getTileEntity(worldIn);
/*     */       
/*  83 */       if (tileentity instanceof TileEntitySkull) {
/*     */         
/*  85 */         TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;
/*     */         
/*  87 */         if (itemstack.getMetadata() == 3) {
/*     */           
/*  89 */           GameProfile gameprofile = null;
/*     */           
/*  91 */           if (itemstack.hasTagCompound()) {
/*     */             
/*  93 */             NBTTagCompound nbttagcompound = itemstack.getTagCompound();
/*     */             
/*  95 */             if (nbttagcompound.hasKey("SkullOwner", 10)) {
/*     */               
/*  97 */               gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
/*     */             }
/*  99 */             else if (nbttagcompound.hasKey("SkullOwner", 8) && !StringUtils.isBlank(nbttagcompound.getString("SkullOwner"))) {
/*     */               
/* 101 */               gameprofile = new GameProfile(null, nbttagcompound.getString("SkullOwner"));
/*     */             } 
/*     */           } 
/*     */           
/* 105 */           tileentityskull.setPlayerProfile(gameprofile);
/*     */         }
/*     */         else {
/*     */           
/* 109 */           tileentityskull.setType(itemstack.getMetadata());
/*     */         } 
/*     */         
/* 112 */         tileentityskull.setSkullRotation(i);
/* 113 */         Blocks.SKULL.checkWitherSpawn(playerIn, worldIn, tileentityskull);
/*     */       } 
/*     */       
/* 116 */       if (stack instanceof EntityPlayerMP)
/*     */       {
/* 118 */         CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)stack, worldIn, itemstack);
/*     */       }
/*     */       
/* 121 */       itemstack.func_190918_g(1);
/* 122 */       return EnumActionResult.SUCCESS;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 127 */     return EnumActionResult.FAIL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/* 137 */     if (func_194125_a(itemIn))
/*     */     {
/* 139 */       for (int i = 0; i < SKULL_TYPES.length; i++)
/*     */       {
/* 141 */         tab.add(new ItemStack(this, 1, i));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetadata(int damage) {
/* 152 */     return damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/* 161 */     int i = stack.getMetadata();
/*     */     
/* 163 */     if (i < 0 || i >= SKULL_TYPES.length)
/*     */     {
/* 165 */       i = 0;
/*     */     }
/*     */     
/* 168 */     return String.valueOf(getUnlocalizedName()) + "." + SKULL_TYPES[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/* 173 */     if (stack.getMetadata() == 3 && stack.hasTagCompound()) {
/*     */       
/* 175 */       if (stack.getTagCompound().hasKey("SkullOwner", 8))
/*     */       {
/* 177 */         return I18n.translateToLocalFormatted("item.skull.player.name", new Object[] { stack.getTagCompound().getString("SkullOwner") });
/*     */       }
/*     */       
/* 180 */       if (stack.getTagCompound().hasKey("SkullOwner", 10)) {
/*     */         
/* 182 */         NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("SkullOwner");
/*     */         
/* 184 */         if (nbttagcompound.hasKey("Name", 8))
/*     */         {
/* 186 */           return I18n.translateToLocalFormatted("item.skull.player.name", new Object[] { nbttagcompound.getString("Name") });
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     return super.getItemStackDisplayName(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean updateItemStackNBT(NBTTagCompound nbt) {
/* 199 */     super.updateItemStackNBT(nbt);
/*     */     
/* 201 */     if (nbt.hasKey("SkullOwner", 8) && !StringUtils.isBlank(nbt.getString("SkullOwner"))) {
/*     */       
/* 203 */       GameProfile gameprofile = new GameProfile(null, nbt.getString("SkullOwner"));
/* 204 */       gameprofile = TileEntitySkull.updateGameprofile(gameprofile);
/* 205 */       nbt.setTag("SkullOwner", (NBTBase)NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
/* 206 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 210 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */