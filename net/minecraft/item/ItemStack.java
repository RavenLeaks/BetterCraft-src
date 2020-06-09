/*      */ package net.minecraft.item;
/*      */ 
/*      */ import com.google.common.collect.HashMultimap;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Multimap;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.util.ITooltipFlag;
/*      */ import net.minecraft.enchantment.Enchantment;
/*      */ import net.minecraft.enchantment.EnchantmentDurability;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.EnumCreatureAttribute;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Enchantments;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.EntityEquipmentSlot;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.util.ActionResult;
/*      */ import net.minecraft.util.EnumActionResult;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.datafix.DataFixer;
/*      */ import net.minecraft.util.datafix.FixTypes;
/*      */ import net.minecraft.util.datafix.IDataWalker;
/*      */ import net.minecraft.util.datafix.walkers.BlockEntityTag;
/*      */ import net.minecraft.util.datafix.walkers.EntityTag;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.TextComponentString;
/*      */ import net.minecraft.util.text.TextFormatting;
/*      */ import net.minecraft.util.text.event.HoverEvent;
/*      */ import net.minecraft.util.text.translation.I18n;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public final class ItemStack {
/*   53 */   public static final ItemStack field_190927_a = new ItemStack(null);
/*   54 */   public static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");
/*      */ 
/*      */   
/*      */   private int stackSize;
/*      */   
/*      */   public int animationsToGo;
/*      */   
/*      */   private final Item item;
/*      */   
/*      */   public NBTTagCompound stackTagCompound;
/*      */   
/*      */   private boolean field_190928_g;
/*      */   
/*      */   private int itemDamage;
/*      */   
/*      */   private EntityItemFrame itemFrame;
/*      */   
/*      */   private Block canDestroyCacheBlock;
/*      */   
/*      */   private boolean canDestroyCacheResult;
/*      */   
/*      */   private Block canPlaceOnCacheBlock;
/*      */   
/*      */   private boolean canPlaceOnCacheResult;
/*      */ 
/*      */   
/*      */   public ItemStack(Block blockIn) {
/*   81 */     this(blockIn, 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Block blockIn, int amount) {
/*   86 */     this(blockIn, amount, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Block blockIn, int amount, int meta) {
/*   91 */     this(Item.getItemFromBlock(blockIn), amount, meta);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Item itemIn) {
/*   96 */     this(itemIn, 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Item itemIn, int amount) {
/*  101 */     this(itemIn, amount, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(Item itemIn, int amount, int meta) {
/*  106 */     this.item = itemIn;
/*  107 */     this.itemDamage = meta;
/*  108 */     this.stackSize = amount;
/*      */     
/*  110 */     if (this.itemDamage < 0)
/*      */     {
/*  112 */       this.itemDamage = 0;
/*      */     }
/*      */     
/*  115 */     func_190923_F();
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_190923_F() {
/*  120 */     this.field_190928_g = func_190926_b();
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack(NBTTagCompound p_i47263_1_) {
/*  125 */     this.item = Item.getByNameOrId(p_i47263_1_.getString("id"));
/*  126 */     this.stackSize = p_i47263_1_.getByte("Count");
/*  127 */     this.itemDamage = Math.max(0, p_i47263_1_.getShort("Damage"));
/*      */     
/*  129 */     if (p_i47263_1_.hasKey("tag", 10)) {
/*      */       
/*  131 */       this.stackTagCompound = p_i47263_1_.getCompoundTag("tag");
/*      */       
/*  133 */       if (this.item != null)
/*      */       {
/*  135 */         this.item.updateItemStackNBT(p_i47263_1_);
/*      */       }
/*      */     } 
/*      */     
/*  139 */     func_190923_F();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_190926_b() {
/*  144 */     if (this == field_190927_a)
/*      */     {
/*  146 */       return true;
/*      */     }
/*  148 */     if (this.item != null && this.item != Item.getItemFromBlock(Blocks.AIR)) {
/*      */       
/*  150 */       if (this.stackSize <= 0)
/*      */       {
/*  152 */         return true;
/*      */       }
/*      */ 
/*      */       
/*  156 */       return !(this.itemDamage >= -32768 && this.itemDamage <= 65535);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  161 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registerFixes(DataFixer fixer) {
/*  167 */     fixer.registerWalker(FixTypes.ITEM_INSTANCE, (IDataWalker)new BlockEntityTag());
/*  168 */     fixer.registerWalker(FixTypes.ITEM_INSTANCE, (IDataWalker)new EntityTag());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack splitStack(int amount) {
/*  176 */     int i = Math.min(amount, this.stackSize);
/*  177 */     ItemStack itemstack = copy();
/*  178 */     itemstack.func_190920_e(i);
/*  179 */     func_190918_g(i);
/*  180 */     return itemstack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item getItem() {
/*  188 */     return this.field_190928_g ? Item.getItemFromBlock(Blocks.AIR) : this.item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  197 */     EnumActionResult enumactionresult = getItem().onItemUse(playerIn, worldIn, pos, hand, side, hitX, hitY, hitZ);
/*      */     
/*  199 */     if (enumactionresult == EnumActionResult.SUCCESS)
/*      */     {
/*  201 */       playerIn.addStat(StatList.getObjectUseStats(this.item));
/*      */     }
/*      */     
/*  204 */     return enumactionresult;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getStrVsBlock(IBlockState blockIn) {
/*  209 */     return getItem().getStrVsBlock(this, blockIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public ActionResult<ItemStack> useItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
/*  214 */     return getItem().onItemRightClick(worldIn, playerIn, hand);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack onItemUseFinish(World worldIn, EntityLivingBase entityLiving) {
/*  222 */     return getItem().onItemUseFinish(this, worldIn, entityLiving);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
/*  230 */     ResourceLocation resourcelocation = (ResourceLocation)Item.REGISTRY.getNameForObject(this.item);
/*  231 */     nbt.setString("id", (resourcelocation == null) ? "minecraft:air" : resourcelocation.toString());
/*  232 */     nbt.setByte("Count", (byte)this.stackSize);
/*  233 */     nbt.setShort("Damage", (short)this.itemDamage);
/*      */     
/*  235 */     if (this.stackTagCompound != null)
/*      */     {
/*  237 */       nbt.setTag("tag", (NBTBase)this.stackTagCompound);
/*      */     }
/*      */     
/*  240 */     return nbt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxStackSize() {
/*  248 */     return getItem().getItemStackLimit();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isStackable() {
/*  256 */     return (getMaxStackSize() > 1 && (!isItemStackDamageable() || !isItemDamaged()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemStackDamageable() {
/*  264 */     if (this.field_190928_g)
/*      */     {
/*  266 */       return false;
/*      */     }
/*  268 */     if (this.item.getMaxDamage() <= 0)
/*      */     {
/*  270 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  274 */     return !(hasTagCompound() && getTagCompound().getBoolean("Unbreakable"));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getHasSubtypes() {
/*  280 */     return getItem().getHasSubtypes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemDamaged() {
/*  288 */     return (isItemStackDamageable() && this.itemDamage > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getItemDamage() {
/*  293 */     return this.itemDamage;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMetadata() {
/*  298 */     return this.itemDamage;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setItemDamage(int meta) {
/*  303 */     this.itemDamage = meta;
/*      */     
/*  305 */     if (this.itemDamage < 0)
/*      */     {
/*  307 */       this.itemDamage = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxDamage() {
/*  316 */     return getItem().getMaxDamage();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attemptDamageItem(int amount, Random rand, @Nullable EntityPlayerMP p_96631_3_) {
/*  327 */     if (!isItemStackDamageable())
/*      */     {
/*  329 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  333 */     if (amount > 0) {
/*      */       
/*  335 */       int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, this);
/*  336 */       int j = 0;
/*      */       
/*  338 */       for (int k = 0; i > 0 && k < amount; k++) {
/*      */         
/*  340 */         if (EnchantmentDurability.negateDamage(this, i, rand))
/*      */         {
/*  342 */           j++;
/*      */         }
/*      */       } 
/*      */       
/*  346 */       amount -= j;
/*      */       
/*  348 */       if (amount <= 0)
/*      */       {
/*  350 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  354 */     if (p_96631_3_ != null && amount != 0)
/*      */     {
/*  356 */       CriteriaTriggers.field_193132_s.func_193158_a(p_96631_3_, this, this.itemDamage + amount);
/*      */     }
/*      */     
/*  359 */     this.itemDamage += amount;
/*  360 */     return (this.itemDamage > getMaxDamage());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void damageItem(int amount, EntityLivingBase entityIn) {
/*  369 */     if (!(entityIn instanceof EntityPlayer) || !((EntityPlayer)entityIn).capabilities.isCreativeMode)
/*      */     {
/*  371 */       if (isItemStackDamageable())
/*      */       {
/*  373 */         if (attemptDamageItem(amount, entityIn.getRNG(), (entityIn instanceof EntityPlayerMP) ? (EntityPlayerMP)entityIn : null)) {
/*      */           
/*  375 */           entityIn.renderBrokenItemStack(this);
/*  376 */           func_190918_g(1);
/*      */           
/*  378 */           if (entityIn instanceof EntityPlayer) {
/*      */             
/*  380 */             EntityPlayer entityplayer = (EntityPlayer)entityIn;
/*  381 */             entityplayer.addStat(StatList.getObjectBreakStats(this.item));
/*      */           } 
/*      */           
/*  384 */           this.itemDamage = 0;
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hitEntity(EntityLivingBase entityIn, EntityPlayer playerIn) {
/*  396 */     boolean flag = this.item.hitEntity(this, entityIn, (EntityLivingBase)playerIn);
/*      */     
/*  398 */     if (flag)
/*      */     {
/*  400 */       playerIn.addStat(StatList.getObjectUseStats(this.item));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockDestroyed(World worldIn, IBlockState blockIn, BlockPos pos, EntityPlayer playerIn) {
/*  409 */     boolean flag = getItem().onBlockDestroyed(this, worldIn, blockIn, pos, (EntityLivingBase)playerIn);
/*      */     
/*  411 */     if (flag)
/*      */     {
/*  413 */       playerIn.addStat(StatList.getObjectUseStats(this.item));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canHarvestBlock(IBlockState blockIn) {
/*  422 */     return getItem().canHarvestBlock(blockIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean interactWithEntity(EntityPlayer playerIn, EntityLivingBase entityIn, EnumHand hand) {
/*  427 */     return getItem().itemInteractionForEntity(this, playerIn, entityIn, hand);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack copy() {
/*  435 */     ItemStack itemstack = new ItemStack(this.item, this.stackSize, this.itemDamage);
/*  436 */     itemstack.func_190915_d(func_190921_D());
/*      */     
/*  438 */     if (this.stackTagCompound != null)
/*      */     {
/*  440 */       itemstack.stackTagCompound = this.stackTagCompound.copy();
/*      */     }
/*      */     
/*  443 */     return itemstack;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean areItemStackTagsEqual(ItemStack stackA, ItemStack stackB) {
/*  448 */     if (stackA.func_190926_b() && stackB.func_190926_b())
/*      */     {
/*  450 */       return true;
/*      */     }
/*  452 */     if (!stackA.func_190926_b() && !stackB.func_190926_b()) {
/*      */       
/*  454 */       if (stackA.stackTagCompound == null && stackB.stackTagCompound != null)
/*      */       {
/*  456 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  460 */       return !(stackA.stackTagCompound != null && !stackA.stackTagCompound.equals(stackB.stackTagCompound));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  465 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {
/*  474 */     if (stackA.func_190926_b() && stackB.func_190926_b())
/*      */     {
/*  476 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  480 */     return (!stackA.func_190926_b() && !stackB.func_190926_b()) ? stackA.isItemStackEqual(stackB) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isItemStackEqual(ItemStack other) {
/*  489 */     if (this.stackSize != other.stackSize)
/*      */     {
/*  491 */       return false;
/*      */     }
/*  493 */     if (getItem() != other.getItem())
/*      */     {
/*  495 */       return false;
/*      */     }
/*  497 */     if (this.itemDamage != other.itemDamage)
/*      */     {
/*  499 */       return false;
/*      */     }
/*  501 */     if (this.stackTagCompound == null && other.stackTagCompound != null)
/*      */     {
/*  503 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  507 */     return !(this.stackTagCompound != null && !this.stackTagCompound.equals(other.stackTagCompound));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean areItemsEqual(ItemStack stackA, ItemStack stackB) {
/*  516 */     if (stackA == stackB)
/*      */     {
/*  518 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  522 */     return (!stackA.func_190926_b() && !stackB.func_190926_b()) ? stackA.isItemEqual(stackB) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean areItemsEqualIgnoreDurability(ItemStack stackA, ItemStack stackB) {
/*  528 */     if (stackA == stackB)
/*      */     {
/*  530 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  534 */     return (!stackA.func_190926_b() && !stackB.func_190926_b()) ? stackA.isItemEqualIgnoreDurability(stackB) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemEqual(ItemStack other) {
/*  544 */     return (!other.func_190926_b() && this.item == other.item && this.itemDamage == other.itemDamage);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isItemEqualIgnoreDurability(ItemStack stack) {
/*  549 */     if (!isItemStackDamageable())
/*      */     {
/*  551 */       return isItemEqual(stack);
/*      */     }
/*      */ 
/*      */     
/*  555 */     return (!stack.func_190926_b() && this.item == stack.item);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName() {
/*  561 */     return getItem().getUnlocalizedName(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  566 */     return String.valueOf(this.stackSize) + "x" + getItem().getUnlocalizedName() + "@" + this.itemDamage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAnimation(World worldIn, Entity entityIn, int inventorySlot, boolean isCurrentItem) {
/*  575 */     if (this.animationsToGo > 0)
/*      */     {
/*  577 */       this.animationsToGo--;
/*      */     }
/*      */     
/*  580 */     if (this.item != null)
/*      */     {
/*  582 */       this.item.onUpdate(this, worldIn, entityIn, inventorySlot, isCurrentItem);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onCrafting(World worldIn, EntityPlayer playerIn, int amount) {
/*  588 */     playerIn.addStat(StatList.getCraftStats(this.item), amount);
/*  589 */     getItem().onCreated(this, worldIn, playerIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxItemUseDuration() {
/*  594 */     return getItem().getMaxItemUseDuration(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumAction getItemUseAction() {
/*  599 */     return getItem().getItemUseAction(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPlayerStoppedUsing(World worldIn, EntityLivingBase entityLiving, int timeLeft) {
/*  607 */     getItem().onPlayerStoppedUsing(this, worldIn, entityLiving, timeLeft);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasTagCompound() {
/*  615 */     return (!this.field_190928_g && this.stackTagCompound != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public NBTTagCompound getTagCompound() {
/*  625 */     return this.stackTagCompound;
/*      */   }
/*      */ 
/*      */   
/*      */   public NBTTagCompound func_190925_c(String p_190925_1_) {
/*  630 */     if (this.stackTagCompound != null && this.stackTagCompound.hasKey(p_190925_1_, 10))
/*      */     {
/*  632 */       return this.stackTagCompound.getCompoundTag(p_190925_1_);
/*      */     }
/*      */ 
/*      */     
/*  636 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  637 */     setTagInfo(p_190925_1_, (NBTBase)nbttagcompound);
/*  638 */     return nbttagcompound;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public NBTTagCompound getSubCompound(String key) {
/*  649 */     return (this.stackTagCompound != null && this.stackTagCompound.hasKey(key, 10)) ? this.stackTagCompound.getCompoundTag(key) : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_190919_e(String p_190919_1_) {
/*  654 */     if (this.stackTagCompound != null && this.stackTagCompound.hasKey(p_190919_1_, 10))
/*      */     {
/*  656 */       this.stackTagCompound.removeTag(p_190919_1_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public NBTTagList getEnchantmentTagList() {
/*  662 */     return (this.stackTagCompound != null) ? this.stackTagCompound.getTagList("ench", 10) : new NBTTagList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTagCompound(@Nullable NBTTagCompound nbt) {
/*  670 */     this.stackTagCompound = nbt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDisplayName() {
/*  678 */     NBTTagCompound nbttagcompound = getSubCompound("display");
/*      */     
/*  680 */     if (nbttagcompound != null) {
/*      */       
/*  682 */       if (nbttagcompound.hasKey("Name", 8))
/*      */       {
/*  684 */         return nbttagcompound.getString("Name");
/*      */       }
/*      */       
/*  687 */       if (nbttagcompound.hasKey("LocName", 8))
/*      */       {
/*  689 */         return I18n.translateToLocal(nbttagcompound.getString("LocName"));
/*      */       }
/*      */     } 
/*      */     
/*  693 */     return getItem().getItemStackDisplayName(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack func_190924_f(String p_190924_1_) {
/*  698 */     func_190925_c("display").setString("LocName", p_190924_1_);
/*  699 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack setStackDisplayName(String displayName) {
/*  704 */     func_190925_c("display").setString("Name", displayName);
/*  705 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearCustomName() {
/*  713 */     NBTTagCompound nbttagcompound = getSubCompound("display");
/*      */     
/*  715 */     if (nbttagcompound != null) {
/*      */       
/*  717 */       nbttagcompound.removeTag("Name");
/*      */       
/*  719 */       if (nbttagcompound.hasNoTags())
/*      */       {
/*  721 */         func_190919_e("display");
/*      */       }
/*      */     } 
/*      */     
/*  725 */     if (this.stackTagCompound != null && this.stackTagCompound.hasNoTags())
/*      */     {
/*  727 */       this.stackTagCompound = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasDisplayName() {
/*  736 */     NBTTagCompound nbttagcompound = getSubCompound("display");
/*  737 */     return (nbttagcompound != null && nbttagcompound.hasKey("Name", 8));
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> getTooltip(@Nullable EntityPlayer playerIn, ITooltipFlag advanced) {
/*  742 */     List<String> list = Lists.newArrayList();
/*  743 */     String s = getDisplayName();
/*      */     
/*  745 */     if (hasDisplayName())
/*      */     {
/*  747 */       s = TextFormatting.ITALIC + s;
/*      */     }
/*      */     
/*  750 */     s = String.valueOf(s) + TextFormatting.RESET;
/*      */     
/*  752 */     if (advanced.func_194127_a()) {
/*      */       
/*  754 */       String s1 = "";
/*      */       
/*  756 */       if (!s.isEmpty()) {
/*      */         
/*  758 */         s = String.valueOf(s) + " (";
/*  759 */         s1 = ")";
/*      */       } 
/*      */       
/*  762 */       int j = Item.getIdFromItem(this.item);
/*      */       
/*  764 */       if (getHasSubtypes())
/*      */       {
/*  766 */         s = String.valueOf(s) + String.format("#%04d/%d%s", new Object[] { Integer.valueOf(j), Integer.valueOf(this.itemDamage), s1 });
/*      */       }
/*      */       else
/*      */       {
/*  770 */         s = String.valueOf(s) + String.format("#%04d%s", new Object[] { Integer.valueOf(j), s1 });
/*      */       }
/*      */     
/*  773 */     } else if (!hasDisplayName() && this.item == Items.FILLED_MAP) {
/*      */       
/*  775 */       s = String.valueOf(s) + " #" + this.itemDamage;
/*      */     } 
/*      */     
/*  778 */     list.add(s);
/*  779 */     int i1 = 0;
/*      */     
/*  781 */     if (hasTagCompound() && this.stackTagCompound.hasKey("HideFlags", 99))
/*      */     {
/*  783 */       i1 = this.stackTagCompound.getInteger("HideFlags");
/*      */     }
/*      */     
/*  786 */     if ((i1 & 0x20) == 0)
/*      */     {
/*  788 */       getItem().addInformation(this, (playerIn == null) ? null : playerIn.world, list, advanced);
/*      */     }
/*      */     
/*  791 */     if (hasTagCompound()) {
/*      */       
/*  793 */       if ((i1 & 0x1) == 0) {
/*      */         
/*  795 */         NBTTagList nbttaglist = getEnchantmentTagList();
/*      */         
/*  797 */         for (int j = 0; j < nbttaglist.tagCount(); j++) {
/*      */           
/*  799 */           NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(j);
/*  800 */           int k = nbttagcompound.getShort("id");
/*  801 */           int l = nbttagcompound.getShort("lvl");
/*  802 */           Enchantment enchantment = Enchantment.getEnchantmentByID(k);
/*      */           
/*  804 */           if (enchantment != null)
/*      */           {
/*  806 */             list.add(enchantment.getTranslatedName(l));
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  811 */       if (this.stackTagCompound.hasKey("display", 10)) {
/*      */         
/*  813 */         NBTTagCompound nbttagcompound1 = this.stackTagCompound.getCompoundTag("display");
/*      */         
/*  815 */         if (nbttagcompound1.hasKey("color", 3))
/*      */         {
/*  817 */           if (advanced.func_194127_a()) {
/*      */             
/*  819 */             list.add(I18n.translateToLocalFormatted("item.color", new Object[] { String.format("#%06X", new Object[] { Integer.valueOf(nbttagcompound1.getInteger("color")) }) }));
/*      */           }
/*      */           else {
/*      */             
/*  823 */             list.add(TextFormatting.ITALIC + I18n.translateToLocal("item.dyed"));
/*      */           } 
/*      */         }
/*      */         
/*  827 */         if (nbttagcompound1.getTagId("Lore") == 9) {
/*      */           
/*  829 */           NBTTagList nbttaglist3 = nbttagcompound1.getTagList("Lore", 8);
/*      */           
/*  831 */           if (!nbttaglist3.hasNoTags())
/*      */           {
/*  833 */             for (int l1 = 0; l1 < nbttaglist3.tagCount(); l1++)
/*      */             {
/*  835 */               list.add(TextFormatting.DARK_PURPLE + TextFormatting.ITALIC + nbttaglist3.getStringTagAt(l1)); }  } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     byte b;
/*      */     int i;
/*      */     EntityEquipmentSlot[] arrayOfEntityEquipmentSlot;
/*  842 */     for (i = (arrayOfEntityEquipmentSlot = EntityEquipmentSlot.values()).length, b = 0; b < i; ) { EntityEquipmentSlot entityequipmentslot = arrayOfEntityEquipmentSlot[b];
/*      */       
/*  844 */       Multimap<String, AttributeModifier> multimap = getAttributeModifiers(entityequipmentslot);
/*      */       
/*  846 */       if (!multimap.isEmpty() && (i1 & 0x2) == 0) {
/*      */         
/*  848 */         list.add("");
/*  849 */         list.add(I18n.translateToLocal("item.modifiers." + entityequipmentslot.getName()));
/*      */         
/*  851 */         for (Map.Entry<String, AttributeModifier> entry : (Iterable<Map.Entry<String, AttributeModifier>>)multimap.entries()) {
/*      */           double d1;
/*  853 */           AttributeModifier attributemodifier = entry.getValue();
/*  854 */           double d0 = attributemodifier.getAmount();
/*  855 */           boolean flag = false;
/*      */           
/*  857 */           if (playerIn != null)
/*      */           {
/*  859 */             if (attributemodifier.getID() == Item.ATTACK_DAMAGE_MODIFIER) {
/*      */               
/*  861 */               d0 += playerIn.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
/*  862 */               d0 += EnchantmentHelper.getModifierForCreature(this, EnumCreatureAttribute.UNDEFINED);
/*  863 */               flag = true;
/*      */             }
/*  865 */             else if (attributemodifier.getID() == Item.ATTACK_SPEED_MODIFIER) {
/*      */               
/*  867 */               d0 += playerIn.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getBaseValue();
/*  868 */               flag = true;
/*      */             } 
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  874 */           if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2) {
/*      */             
/*  876 */             d1 = d0;
/*      */           }
/*      */           else {
/*      */             
/*  880 */             d1 = d0 * 100.0D;
/*      */           } 
/*      */           
/*  883 */           if (flag) {
/*      */             
/*  885 */             list.add(" " + I18n.translateToLocalFormatted("attribute.modifier.equals." + attributemodifier.getOperation(), new Object[] { DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + (String)entry.getKey()) })); continue;
/*      */           } 
/*  887 */           if (d0 > 0.0D) {
/*      */             
/*  889 */             list.add(TextFormatting.BLUE + " " + I18n.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier.getOperation(), new Object[] { DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + (String)entry.getKey()) })); continue;
/*      */           } 
/*  891 */           if (d0 < 0.0D) {
/*      */             
/*  893 */             d1 *= -1.0D;
/*  894 */             list.add(TextFormatting.RED + " " + I18n.translateToLocalFormatted("attribute.modifier.take." + attributemodifier.getOperation(), new Object[] { DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + (String)entry.getKey()) }));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       b++; }
/*      */     
/*  900 */     if (hasTagCompound() && getTagCompound().getBoolean("Unbreakable") && (i1 & 0x4) == 0)
/*      */     {
/*  902 */       list.add(TextFormatting.BLUE + I18n.translateToLocal("item.unbreakable"));
/*      */     }
/*      */     
/*  905 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9) && (i1 & 0x8) == 0) {
/*      */       
/*  907 */       NBTTagList nbttaglist1 = this.stackTagCompound.getTagList("CanDestroy", 8);
/*      */       
/*  909 */       if (!nbttaglist1.hasNoTags()) {
/*      */         
/*  911 */         list.add("");
/*  912 */         list.add(TextFormatting.GRAY + I18n.translateToLocal("item.canBreak"));
/*      */         
/*  914 */         for (int j1 = 0; j1 < nbttaglist1.tagCount(); j1++) {
/*      */           
/*  916 */           Block block = Block.getBlockFromName(nbttaglist1.getStringTagAt(j1));
/*      */           
/*  918 */           if (block != null) {
/*      */             
/*  920 */             list.add(TextFormatting.DARK_GRAY + block.getLocalizedName());
/*      */           }
/*      */           else {
/*      */             
/*  924 */             list.add(TextFormatting.DARK_GRAY + "missingno");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  930 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9) && (i1 & 0x10) == 0) {
/*      */       
/*  932 */       NBTTagList nbttaglist2 = this.stackTagCompound.getTagList("CanPlaceOn", 8);
/*      */       
/*  934 */       if (!nbttaglist2.hasNoTags()) {
/*      */         
/*  936 */         list.add("");
/*  937 */         list.add(TextFormatting.GRAY + I18n.translateToLocal("item.canPlace"));
/*      */         
/*  939 */         for (int k1 = 0; k1 < nbttaglist2.tagCount(); k1++) {
/*      */           
/*  941 */           Block block1 = Block.getBlockFromName(nbttaglist2.getStringTagAt(k1));
/*      */           
/*  943 */           if (block1 != null) {
/*      */             
/*  945 */             list.add(TextFormatting.DARK_GRAY + block1.getLocalizedName());
/*      */           }
/*      */           else {
/*      */             
/*  949 */             list.add(TextFormatting.DARK_GRAY + "missingno");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  955 */     if (advanced.func_194127_a()) {
/*      */       
/*  957 */       if (isItemDamaged())
/*      */       {
/*  959 */         list.add(I18n.translateToLocalFormatted("item.durability", new Object[] { Integer.valueOf(getMaxDamage() - getItemDamage()), Integer.valueOf(getMaxDamage()) }));
/*      */       }
/*      */       
/*  962 */       list.add(TextFormatting.DARK_GRAY + ((ResourceLocation)Item.REGISTRY.getNameForObject(this.item)).toString());
/*      */       
/*  964 */       if (hasTagCompound())
/*      */       {
/*  966 */         list.add(TextFormatting.DARK_GRAY + I18n.translateToLocalFormatted("item.nbt_tags", new Object[] { Integer.valueOf(getTagCompound().getKeySet().size()) }));
/*      */       }
/*      */     } 
/*      */     
/*  970 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasEffect() {
/*  975 */     return getItem().hasEffect(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumRarity getRarity() {
/*  980 */     return getItem().getRarity(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemEnchantable() {
/*  988 */     if (!getItem().isItemTool(this))
/*      */     {
/*  990 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  994 */     return !isItemEnchanted();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEnchantment(Enchantment ench, int level) {
/* 1003 */     if (this.stackTagCompound == null)
/*      */     {
/* 1005 */       setTagCompound(new NBTTagCompound());
/*      */     }
/*      */     
/* 1008 */     if (!this.stackTagCompound.hasKey("ench", 9))
/*      */     {
/* 1010 */       this.stackTagCompound.setTag("ench", (NBTBase)new NBTTagList());
/*      */     }
/*      */     
/* 1013 */     NBTTagList nbttaglist = this.stackTagCompound.getTagList("ench", 10);
/* 1014 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 1015 */     nbttagcompound.setShort("id", (short)Enchantment.getEnchantmentID(ench));
/* 1016 */     nbttagcompound.setShort("lvl", (short)(byte)level);
/* 1017 */     nbttaglist.appendTag((NBTBase)nbttagcompound);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemEnchanted() {
/* 1025 */     if (this.stackTagCompound != null && this.stackTagCompound.hasKey("ench", 9))
/*      */     {
/* 1027 */       return !this.stackTagCompound.getTagList("ench", 10).hasNoTags();
/*      */     }
/*      */ 
/*      */     
/* 1031 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTagInfo(String key, NBTBase value) {
/* 1037 */     if (this.stackTagCompound == null)
/*      */     {
/* 1039 */       setTagCompound(new NBTTagCompound());
/*      */     }
/*      */     
/* 1042 */     this.stackTagCompound.setTag(key, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canEditBlocks() {
/* 1047 */     return getItem().canItemEditBlocks();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnItemFrame() {
/* 1055 */     return (this.itemFrame != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemFrame(EntityItemFrame frame) {
/* 1063 */     this.itemFrame = frame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityItemFrame getItemFrame() {
/* 1073 */     return this.field_190928_g ? null : this.itemFrame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRepairCost() {
/* 1081 */     return (hasTagCompound() && this.stackTagCompound.hasKey("RepairCost", 3)) ? this.stackTagCompound.getInteger("RepairCost") : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRepairCost(int cost) {
/* 1089 */     if (!hasTagCompound())
/*      */     {
/* 1091 */       this.stackTagCompound = new NBTTagCompound();
/*      */     }
/*      */     
/* 1094 */     this.stackTagCompound.setInteger("RepairCost", cost);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
/*      */     Multimap<String, AttributeModifier> multimap;
/* 1101 */     if (hasTagCompound() && this.stackTagCompound.hasKey("AttributeModifiers", 9)) {
/*      */       
/* 1103 */       HashMultimap hashMultimap = HashMultimap.create();
/* 1104 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("AttributeModifiers", 10);
/*      */       
/* 1106 */       for (int i = 0; i < nbttaglist.tagCount(); i++)
/*      */       {
/* 1108 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 1109 */         AttributeModifier attributemodifier = SharedMonsterAttributes.readAttributeModifierFromNBT(nbttagcompound);
/*      */         
/* 1111 */         if (attributemodifier != null && (!nbttagcompound.hasKey("Slot", 8) || nbttagcompound.getString("Slot").equals(equipmentSlot.getName())) && attributemodifier.getID().getLeastSignificantBits() != 0L && attributemodifier.getID().getMostSignificantBits() != 0L)
/*      */         {
/* 1113 */           hashMultimap.put(nbttagcompound.getString("AttributeName"), attributemodifier);
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1119 */       multimap = getItem().getItemAttributeModifiers(equipmentSlot);
/*      */     } 
/*      */     
/* 1122 */     return multimap;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addAttributeModifier(String attributeName, AttributeModifier modifier, @Nullable EntityEquipmentSlot equipmentSlot) {
/* 1127 */     if (this.stackTagCompound == null)
/*      */     {
/* 1129 */       this.stackTagCompound = new NBTTagCompound();
/*      */     }
/*      */     
/* 1132 */     if (!this.stackTagCompound.hasKey("AttributeModifiers", 9))
/*      */     {
/* 1134 */       this.stackTagCompound.setTag("AttributeModifiers", (NBTBase)new NBTTagList());
/*      */     }
/*      */     
/* 1137 */     NBTTagList nbttaglist = this.stackTagCompound.getTagList("AttributeModifiers", 10);
/* 1138 */     NBTTagCompound nbttagcompound = SharedMonsterAttributes.writeAttributeModifierToNBT(modifier);
/* 1139 */     nbttagcompound.setString("AttributeName", attributeName);
/*      */     
/* 1141 */     if (equipmentSlot != null)
/*      */     {
/* 1143 */       nbttagcompound.setString("Slot", equipmentSlot.getName());
/*      */     }
/*      */     
/* 1146 */     nbttaglist.appendTag((NBTBase)nbttagcompound);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ITextComponent getTextComponent() {
/* 1154 */     TextComponentString textcomponentstring = new TextComponentString(getDisplayName());
/*      */     
/* 1156 */     if (hasDisplayName())
/*      */     {
/* 1158 */       textcomponentstring.getStyle().setItalic(Boolean.valueOf(true));
/*      */     }
/*      */     
/* 1161 */     ITextComponent itextcomponent = (new TextComponentString("[")).appendSibling((ITextComponent)textcomponentstring).appendText("]");
/*      */     
/* 1163 */     if (!this.field_190928_g) {
/*      */       
/* 1165 */       NBTTagCompound nbttagcompound = writeToNBT(new NBTTagCompound());
/* 1166 */       itextcomponent.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, (ITextComponent)new TextComponentString(nbttagcompound.toString())));
/* 1167 */       itextcomponent.getStyle().setColor((getRarity()).rarityColor);
/*      */     } 
/*      */     
/* 1170 */     return itextcomponent;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canDestroy(Block blockIn) {
/* 1175 */     if (blockIn == this.canDestroyCacheBlock)
/*      */     {
/* 1177 */       return this.canDestroyCacheResult;
/*      */     }
/*      */ 
/*      */     
/* 1181 */     this.canDestroyCacheBlock = blockIn;
/*      */     
/* 1183 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9)) {
/*      */       
/* 1185 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("CanDestroy", 8);
/*      */       
/* 1187 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */         
/* 1189 */         Block block = Block.getBlockFromName(nbttaglist.getStringTagAt(i));
/*      */         
/* 1191 */         if (block == blockIn) {
/*      */           
/* 1193 */           this.canDestroyCacheResult = true;
/* 1194 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1199 */     this.canDestroyCacheResult = false;
/* 1200 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canPlaceOn(Block blockIn) {
/* 1206 */     if (blockIn == this.canPlaceOnCacheBlock)
/*      */     {
/* 1208 */       return this.canPlaceOnCacheResult;
/*      */     }
/*      */ 
/*      */     
/* 1212 */     this.canPlaceOnCacheBlock = blockIn;
/*      */     
/* 1214 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9)) {
/*      */       
/* 1216 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("CanPlaceOn", 8);
/*      */       
/* 1218 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */         
/* 1220 */         Block block = Block.getBlockFromName(nbttaglist.getStringTagAt(i));
/*      */         
/* 1222 */         if (block == blockIn) {
/*      */           
/* 1224 */           this.canPlaceOnCacheResult = true;
/* 1225 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1230 */     this.canPlaceOnCacheResult = false;
/* 1231 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int func_190921_D() {
/* 1237 */     return this.animationsToGo;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_190915_d(int p_190915_1_) {
/* 1242 */     this.animationsToGo = p_190915_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public int func_190916_E() {
/* 1247 */     return this.field_190928_g ? 0 : this.stackSize;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_190920_e(int p_190920_1_) {
/* 1252 */     this.stackSize = p_190920_1_;
/* 1253 */     func_190923_F();
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_190917_f(int p_190917_1_) {
/* 1258 */     func_190920_e(this.stackSize + p_190917_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_190918_g(int p_190918_1_) {
/* 1263 */     func_190917_f(-p_190918_1_);
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */