/*      */ package net.minecraft.item;
/*      */ 
/*      */ import com.google.common.collect.HashMultimap;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Multimap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockDirt;
/*      */ import net.minecraft.block.BlockDoublePlant;
/*      */ import net.minecraft.block.BlockFlower;
/*      */ import net.minecraft.block.BlockPlanks;
/*      */ import net.minecraft.block.BlockPrismarine;
/*      */ import net.minecraft.block.BlockRedSandstone;
/*      */ import net.minecraft.block.BlockSand;
/*      */ import net.minecraft.block.BlockSandStone;
/*      */ import net.minecraft.block.BlockSilverfish;
/*      */ import net.minecraft.block.BlockStone;
/*      */ import net.minecraft.block.BlockStoneBrick;
/*      */ import net.minecraft.block.BlockWall;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.util.ITooltipFlag;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.item.EntityBoat;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.item.EntityPainting;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.init.MobEffects;
/*      */ import net.minecraft.init.SoundEvents;
/*      */ import net.minecraft.inventory.EntityEquipmentSlot;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.util.ActionResult;
/*      */ import net.minecraft.util.EnumActionResult;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.EnumHandSide;
/*      */ import net.minecraft.util.NonNullList;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.RayTraceResult;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.util.registry.IRegistry;
/*      */ import net.minecraft.util.registry.RegistryNamespaced;
/*      */ import net.minecraft.util.registry.RegistrySimple;
/*      */ import net.minecraft.util.text.translation.I18n;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public class Item
/*      */ {
/*   61 */   public static final RegistryNamespaced<ResourceLocation, Item> REGISTRY = new RegistryNamespaced();
/*   62 */   private static final Map<Block, Item> BLOCK_TO_ITEM = Maps.newHashMap();
/*   63 */   private static final IItemPropertyGetter DAMAGED_GETTER = new IItemPropertyGetter()
/*      */     {
/*      */       public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
/*      */       {
/*   67 */         return stack.isItemDamaged() ? 1.0F : 0.0F;
/*      */       }
/*      */     };
/*   70 */   private static final IItemPropertyGetter DAMAGE_GETTER = new IItemPropertyGetter()
/*      */     {
/*      */       public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
/*      */       {
/*   74 */         return MathHelper.clamp(stack.getItemDamage() / stack.getMaxDamage(), 0.0F, 1.0F);
/*      */       }
/*      */     };
/*   77 */   private static final IItemPropertyGetter LEFTHANDED_GETTER = new IItemPropertyGetter()
/*      */     {
/*      */       public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
/*      */       {
/*   81 */         return (entityIn != null && entityIn.getPrimaryHand() != EnumHandSide.RIGHT) ? 1.0F : 0.0F;
/*      */       }
/*      */     };
/*   84 */   private static final IItemPropertyGetter COOLDOWN_GETTER = new IItemPropertyGetter()
/*      */     {
/*      */       public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
/*      */       {
/*   88 */         return (entityIn instanceof EntityPlayer) ? ((EntityPlayer)entityIn).getCooldownTracker().getCooldown(stack.getItem(), 0.0F) : 0.0F;
/*      */       }
/*      */     };
/*   91 */   private final IRegistry<ResourceLocation, IItemPropertyGetter> properties = (IRegistry<ResourceLocation, IItemPropertyGetter>)new RegistrySimple();
/*   92 */   protected static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
/*   93 */   protected static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
/*      */   
/*      */   private CreativeTabs tabToDisplayOn;
/*      */   
/*   97 */   protected static Random itemRand = new Random();
/*      */ 
/*      */   
/*  100 */   protected int maxStackSize = 64;
/*      */ 
/*      */   
/*      */   private int maxDamage;
/*      */ 
/*      */   
/*      */   protected boolean bFull3D;
/*      */ 
/*      */   
/*      */   protected boolean hasSubtypes;
/*      */ 
/*      */   
/*      */   private Item containerItem;
/*      */ 
/*      */   
/*      */   private String unlocalizedName;
/*      */ 
/*      */   
/*      */   public static int getIdFromItem(Item itemIn) {
/*  119 */     return (itemIn == null) ? 0 : REGISTRY.getIDForObject(itemIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Item getItemById(int id) {
/*  124 */     return (Item)REGISTRY.getObjectById(id);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Item getItemFromBlock(Block blockIn) {
/*  129 */     Item item = BLOCK_TO_ITEM.get(blockIn);
/*  130 */     return (item == null) ? Items.field_190931_a : item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static Item getByNameOrId(String id) {
/*  141 */     Item item = (Item)REGISTRY.getObject(new ResourceLocation(id));
/*      */     
/*  143 */     if (item == null) {
/*      */       
/*      */       try {
/*      */         
/*  147 */         return getItemById(Integer.parseInt(id));
/*      */       }
/*  149 */       catch (NumberFormatException numberFormatException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  155 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void addPropertyOverride(ResourceLocation key, IItemPropertyGetter getter) {
/*  163 */     this.properties.putObject(key, getter);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public IItemPropertyGetter getPropertyGetter(ResourceLocation key) {
/*  169 */     return (IItemPropertyGetter)this.properties.getObject(key);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasCustomProperties() {
/*  174 */     return !this.properties.getKeys().isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean updateItemStackNBT(NBTTagCompound nbt) {
/*  182 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item() {
/*  187 */     addPropertyOverride(new ResourceLocation("lefthanded"), LEFTHANDED_GETTER);
/*  188 */     addPropertyOverride(new ResourceLocation("cooldown"), COOLDOWN_GETTER);
/*      */   }
/*      */ 
/*      */   
/*      */   public Item setMaxStackSize(int maxStackSize) {
/*  193 */     this.maxStackSize = maxStackSize;
/*  194 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/*  202 */     return EnumActionResult.PASS;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getStrVsBlock(ItemStack stack, IBlockState state) {
/*  207 */     return 1.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/*  212 */     return new ActionResult(EnumActionResult.PASS, worldIn.getHeldItem(playerIn));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
/*  221 */     return stack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemStackLimit() {
/*  229 */     return this.maxStackSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMetadata(int damage) {
/*  238 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getHasSubtypes() {
/*  243 */     return this.hasSubtypes;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Item setHasSubtypes(boolean hasSubtypes) {
/*  248 */     this.hasSubtypes = hasSubtypes;
/*  249 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxDamage() {
/*  257 */     return this.maxDamage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Item setMaxDamage(int maxDamageIn) {
/*  265 */     this.maxDamage = maxDamageIn;
/*      */     
/*  267 */     if (maxDamageIn > 0) {
/*      */       
/*  269 */       addPropertyOverride(new ResourceLocation("damaged"), DAMAGED_GETTER);
/*  270 */       addPropertyOverride(new ResourceLocation("damage"), DAMAGE_GETTER);
/*      */     } 
/*      */     
/*  273 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDamageable() {
/*  278 */     return (this.maxDamage > 0 && (!this.hasSubtypes || this.maxStackSize == 1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
/*  287 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
/*  295 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canHarvestBlock(IBlockState blockIn) {
/*  303 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
/*  311 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item setFull3D() {
/*  319 */     this.bFull3D = true;
/*  320 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFull3D() {
/*  328 */     return this.bFull3D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldRotateAroundWhenRendering() {
/*  337 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item setUnlocalizedName(String unlocalizedName) {
/*  345 */     this.unlocalizedName = unlocalizedName;
/*  346 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedNameInefficiently(ItemStack stack) {
/*  355 */     return I18n.translateToLocal(getUnlocalizedName(stack));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName() {
/*  363 */     return "item." + this.unlocalizedName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName(ItemStack stack) {
/*  372 */     return "item." + this.unlocalizedName;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item setContainerItem(Item containerItem) {
/*  377 */     this.containerItem = containerItem;
/*  378 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getShareTag() {
/*  386 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Item getContainerItem() {
/*  392 */     return this.containerItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasContainerItem() {
/*  400 */     return (this.containerItem != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMap() {
/*  423 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumAction getItemUseAction(ItemStack stack) {
/*  431 */     return EnumAction.NONE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxItemUseDuration(ItemStack stack) {
/*  439 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getItemStackDisplayName(ItemStack stack) {
/*  458 */     return I18n.translateToLocal(String.valueOf(getUnlocalizedNameInefficiently(stack)) + ".name").trim();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasEffect(ItemStack stack) {
/*  463 */     return stack.isItemEnchanted();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumRarity getRarity(ItemStack stack) {
/*  471 */     return stack.isItemEnchanted() ? EnumRarity.RARE : EnumRarity.COMMON;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemTool(ItemStack stack) {
/*  479 */     return (getItemStackLimit() == 1 && isDamageable());
/*      */   }
/*      */ 
/*      */   
/*      */   protected RayTraceResult rayTrace(World worldIn, EntityPlayer playerIn, boolean useLiquids) {
/*  484 */     float f = playerIn.rotationPitch;
/*  485 */     float f1 = playerIn.rotationYaw;
/*  486 */     double d0 = playerIn.posX;
/*  487 */     double d1 = playerIn.posY + playerIn.getEyeHeight();
/*  488 */     double d2 = playerIn.posZ;
/*  489 */     Vec3d vec3d = new Vec3d(d0, d1, d2);
/*  490 */     float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
/*  491 */     float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
/*  492 */     float f4 = -MathHelper.cos(-f * 0.017453292F);
/*  493 */     float f5 = MathHelper.sin(-f * 0.017453292F);
/*  494 */     float f6 = f3 * f4;
/*  495 */     float f7 = f2 * f4;
/*  496 */     double d3 = 5.0D;
/*  497 */     Vec3d vec3d1 = vec3d.addVector(f6 * 5.0D, f5 * 5.0D, f7 * 5.0D);
/*  498 */     return worldIn.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemEnchantability() {
/*  506 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/*  514 */     if (func_194125_a(itemIn))
/*      */     {
/*  516 */       tab.add(new ItemStack(this));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean func_194125_a(CreativeTabs p_194125_1_) {
/*  522 */     CreativeTabs creativetabs = getCreativeTab();
/*  523 */     return (creativetabs != null && (p_194125_1_ == CreativeTabs.SEARCH || p_194125_1_ == creativetabs));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public CreativeTabs getCreativeTab() {
/*  533 */     return this.tabToDisplayOn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item setCreativeTab(CreativeTabs tab) {
/*  541 */     this.tabToDisplayOn = tab;
/*  542 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canItemEditBlocks() {
/*  551 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/*  559 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
/*  564 */     return (Multimap<String, AttributeModifier>)HashMultimap.create();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void registerItems() {
/*  569 */     registerItemBlock(Blocks.AIR, new ItemAir(Blocks.AIR));
/*  570 */     registerItemBlock(Blocks.STONE, (new ItemMultiTexture(Blocks.STONE, Blocks.STONE, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  574 */               return BlockStone.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  576 */           })).setUnlocalizedName("stone"));
/*  577 */     registerItemBlock((Block)Blocks.GRASS, new ItemColored((Block)Blocks.GRASS, false));
/*  578 */     registerItemBlock(Blocks.DIRT, (new ItemMultiTexture(Blocks.DIRT, Blocks.DIRT, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  582 */               return BlockDirt.DirtType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  584 */           })).setUnlocalizedName("dirt"));
/*  585 */     registerItemBlock(Blocks.COBBLESTONE);
/*  586 */     registerItemBlock(Blocks.PLANKS, (new ItemMultiTexture(Blocks.PLANKS, Blocks.PLANKS, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  590 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  592 */           })).setUnlocalizedName("wood"));
/*  593 */     registerItemBlock(Blocks.SAPLING, (new ItemMultiTexture(Blocks.SAPLING, Blocks.SAPLING, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  597 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  599 */           })).setUnlocalizedName("sapling"));
/*  600 */     registerItemBlock(Blocks.BEDROCK);
/*  601 */     registerItemBlock((Block)Blocks.SAND, (new ItemMultiTexture((Block)Blocks.SAND, (Block)Blocks.SAND, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  605 */               return BlockSand.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  607 */           })).setUnlocalizedName("sand"));
/*  608 */     registerItemBlock(Blocks.GRAVEL);
/*  609 */     registerItemBlock(Blocks.GOLD_ORE);
/*  610 */     registerItemBlock(Blocks.IRON_ORE);
/*  611 */     registerItemBlock(Blocks.COAL_ORE);
/*  612 */     registerItemBlock(Blocks.LOG, (new ItemMultiTexture(Blocks.LOG, Blocks.LOG, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  616 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  618 */           })).setUnlocalizedName("log"));
/*  619 */     registerItemBlock(Blocks.LOG2, (new ItemMultiTexture(Blocks.LOG2, Blocks.LOG2, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  623 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata() + 4).getUnlocalizedName();
/*      */             }
/*  625 */           })).setUnlocalizedName("log"));
/*  626 */     registerItemBlock((Block)Blocks.LEAVES, (new ItemLeaves(Blocks.LEAVES)).setUnlocalizedName("leaves"));
/*  627 */     registerItemBlock((Block)Blocks.LEAVES2, (new ItemLeaves(Blocks.LEAVES2)).setUnlocalizedName("leaves"));
/*  628 */     registerItemBlock(Blocks.SPONGE, (new ItemMultiTexture(Blocks.SPONGE, Blocks.SPONGE, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  632 */               return ((p_apply_1_.getMetadata() & 0x1) == 1) ? "wet" : "dry";
/*      */             }
/*  634 */           })).setUnlocalizedName("sponge"));
/*  635 */     registerItemBlock(Blocks.GLASS);
/*  636 */     registerItemBlock(Blocks.LAPIS_ORE);
/*  637 */     registerItemBlock(Blocks.LAPIS_BLOCK);
/*  638 */     registerItemBlock(Blocks.DISPENSER);
/*  639 */     registerItemBlock(Blocks.SANDSTONE, (new ItemMultiTexture(Blocks.SANDSTONE, Blocks.SANDSTONE, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  643 */               return BlockSandStone.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  645 */           })).setUnlocalizedName("sandStone"));
/*  646 */     registerItemBlock(Blocks.NOTEBLOCK);
/*  647 */     registerItemBlock(Blocks.GOLDEN_RAIL);
/*  648 */     registerItemBlock(Blocks.DETECTOR_RAIL);
/*  649 */     registerItemBlock((Block)Blocks.STICKY_PISTON, new ItemPiston((Block)Blocks.STICKY_PISTON));
/*  650 */     registerItemBlock(Blocks.WEB);
/*  651 */     registerItemBlock((Block)Blocks.TALLGRASS, (new ItemColored((Block)Blocks.TALLGRASS, true)).setSubtypeNames(new String[] { "shrub", "grass", "fern" }));
/*  652 */     registerItemBlock((Block)Blocks.DEADBUSH);
/*  653 */     registerItemBlock((Block)Blocks.PISTON, new ItemPiston((Block)Blocks.PISTON));
/*  654 */     registerItemBlock(Blocks.WOOL, (new ItemCloth(Blocks.WOOL)).setUnlocalizedName("cloth"));
/*  655 */     registerItemBlock((Block)Blocks.YELLOW_FLOWER, (new ItemMultiTexture((Block)Blocks.YELLOW_FLOWER, (Block)Blocks.YELLOW_FLOWER, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  659 */               return BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.YELLOW, p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  661 */           })).setUnlocalizedName("flower"));
/*  662 */     registerItemBlock((Block)Blocks.RED_FLOWER, (new ItemMultiTexture((Block)Blocks.RED_FLOWER, (Block)Blocks.RED_FLOWER, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  666 */               return BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.RED, p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  668 */           })).setUnlocalizedName("rose"));
/*  669 */     registerItemBlock((Block)Blocks.BROWN_MUSHROOM);
/*  670 */     registerItemBlock((Block)Blocks.RED_MUSHROOM);
/*  671 */     registerItemBlock(Blocks.GOLD_BLOCK);
/*  672 */     registerItemBlock(Blocks.IRON_BLOCK);
/*  673 */     registerItemBlock((Block)Blocks.STONE_SLAB, (new ItemSlab((Block)Blocks.STONE_SLAB, Blocks.STONE_SLAB, Blocks.DOUBLE_STONE_SLAB)).setUnlocalizedName("stoneSlab"));
/*  674 */     registerItemBlock(Blocks.BRICK_BLOCK);
/*  675 */     registerItemBlock(Blocks.TNT);
/*  676 */     registerItemBlock(Blocks.BOOKSHELF);
/*  677 */     registerItemBlock(Blocks.MOSSY_COBBLESTONE);
/*  678 */     registerItemBlock(Blocks.OBSIDIAN);
/*  679 */     registerItemBlock(Blocks.TORCH);
/*  680 */     registerItemBlock(Blocks.END_ROD);
/*  681 */     registerItemBlock(Blocks.CHORUS_PLANT);
/*  682 */     registerItemBlock(Blocks.CHORUS_FLOWER);
/*  683 */     registerItemBlock(Blocks.PURPUR_BLOCK);
/*  684 */     registerItemBlock(Blocks.PURPUR_PILLAR);
/*  685 */     registerItemBlock(Blocks.PURPUR_STAIRS);
/*  686 */     registerItemBlock((Block)Blocks.PURPUR_SLAB, (new ItemSlab((Block)Blocks.PURPUR_SLAB, Blocks.PURPUR_SLAB, Blocks.PURPUR_DOUBLE_SLAB)).setUnlocalizedName("purpurSlab"));
/*  687 */     registerItemBlock(Blocks.MOB_SPAWNER);
/*  688 */     registerItemBlock(Blocks.OAK_STAIRS);
/*  689 */     registerItemBlock((Block)Blocks.CHEST);
/*  690 */     registerItemBlock(Blocks.DIAMOND_ORE);
/*  691 */     registerItemBlock(Blocks.DIAMOND_BLOCK);
/*  692 */     registerItemBlock(Blocks.CRAFTING_TABLE);
/*  693 */     registerItemBlock(Blocks.FARMLAND);
/*  694 */     registerItemBlock(Blocks.FURNACE);
/*  695 */     registerItemBlock(Blocks.LADDER);
/*  696 */     registerItemBlock(Blocks.RAIL);
/*  697 */     registerItemBlock(Blocks.STONE_STAIRS);
/*  698 */     registerItemBlock(Blocks.LEVER);
/*  699 */     registerItemBlock(Blocks.STONE_PRESSURE_PLATE);
/*  700 */     registerItemBlock(Blocks.WOODEN_PRESSURE_PLATE);
/*  701 */     registerItemBlock(Blocks.REDSTONE_ORE);
/*  702 */     registerItemBlock(Blocks.REDSTONE_TORCH);
/*  703 */     registerItemBlock(Blocks.STONE_BUTTON);
/*  704 */     registerItemBlock(Blocks.SNOW_LAYER, new ItemSnow(Blocks.SNOW_LAYER));
/*  705 */     registerItemBlock(Blocks.ICE);
/*  706 */     registerItemBlock(Blocks.SNOW);
/*  707 */     registerItemBlock((Block)Blocks.CACTUS);
/*  708 */     registerItemBlock(Blocks.CLAY);
/*  709 */     registerItemBlock(Blocks.JUKEBOX);
/*  710 */     registerItemBlock(Blocks.OAK_FENCE);
/*  711 */     registerItemBlock(Blocks.SPRUCE_FENCE);
/*  712 */     registerItemBlock(Blocks.BIRCH_FENCE);
/*  713 */     registerItemBlock(Blocks.JUNGLE_FENCE);
/*  714 */     registerItemBlock(Blocks.DARK_OAK_FENCE);
/*  715 */     registerItemBlock(Blocks.ACACIA_FENCE);
/*  716 */     registerItemBlock(Blocks.PUMPKIN);
/*  717 */     registerItemBlock(Blocks.NETHERRACK);
/*  718 */     registerItemBlock(Blocks.SOUL_SAND);
/*  719 */     registerItemBlock(Blocks.GLOWSTONE);
/*  720 */     registerItemBlock(Blocks.LIT_PUMPKIN);
/*  721 */     registerItemBlock(Blocks.TRAPDOOR);
/*  722 */     registerItemBlock(Blocks.MONSTER_EGG, (new ItemMultiTexture(Blocks.MONSTER_EGG, Blocks.MONSTER_EGG, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  726 */               return BlockSilverfish.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  728 */           })).setUnlocalizedName("monsterStoneEgg"));
/*  729 */     registerItemBlock(Blocks.STONEBRICK, (new ItemMultiTexture(Blocks.STONEBRICK, Blocks.STONEBRICK, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  733 */               return BlockStoneBrick.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  735 */           })).setUnlocalizedName("stonebricksmooth"));
/*  736 */     registerItemBlock(Blocks.BROWN_MUSHROOM_BLOCK);
/*  737 */     registerItemBlock(Blocks.RED_MUSHROOM_BLOCK);
/*  738 */     registerItemBlock(Blocks.IRON_BARS);
/*  739 */     registerItemBlock(Blocks.GLASS_PANE);
/*  740 */     registerItemBlock(Blocks.MELON_BLOCK);
/*  741 */     registerItemBlock(Blocks.VINE, new ItemColored(Blocks.VINE, false));
/*  742 */     registerItemBlock(Blocks.OAK_FENCE_GATE);
/*  743 */     registerItemBlock(Blocks.SPRUCE_FENCE_GATE);
/*  744 */     registerItemBlock(Blocks.BIRCH_FENCE_GATE);
/*  745 */     registerItemBlock(Blocks.JUNGLE_FENCE_GATE);
/*  746 */     registerItemBlock(Blocks.DARK_OAK_FENCE_GATE);
/*  747 */     registerItemBlock(Blocks.ACACIA_FENCE_GATE);
/*  748 */     registerItemBlock(Blocks.BRICK_STAIRS);
/*  749 */     registerItemBlock(Blocks.STONE_BRICK_STAIRS);
/*  750 */     registerItemBlock((Block)Blocks.MYCELIUM);
/*  751 */     registerItemBlock(Blocks.WATERLILY, new ItemLilyPad(Blocks.WATERLILY));
/*  752 */     registerItemBlock(Blocks.NETHER_BRICK);
/*  753 */     registerItemBlock(Blocks.NETHER_BRICK_FENCE);
/*  754 */     registerItemBlock(Blocks.NETHER_BRICK_STAIRS);
/*  755 */     registerItemBlock(Blocks.ENCHANTING_TABLE);
/*  756 */     registerItemBlock(Blocks.END_PORTAL_FRAME);
/*  757 */     registerItemBlock(Blocks.END_STONE);
/*  758 */     registerItemBlock(Blocks.END_BRICKS);
/*  759 */     registerItemBlock(Blocks.DRAGON_EGG);
/*  760 */     registerItemBlock(Blocks.REDSTONE_LAMP);
/*  761 */     registerItemBlock((Block)Blocks.WOODEN_SLAB, (new ItemSlab((Block)Blocks.WOODEN_SLAB, Blocks.WOODEN_SLAB, Blocks.DOUBLE_WOODEN_SLAB)).setUnlocalizedName("woodSlab"));
/*  762 */     registerItemBlock(Blocks.SANDSTONE_STAIRS);
/*  763 */     registerItemBlock(Blocks.EMERALD_ORE);
/*  764 */     registerItemBlock(Blocks.ENDER_CHEST);
/*  765 */     registerItemBlock((Block)Blocks.TRIPWIRE_HOOK);
/*  766 */     registerItemBlock(Blocks.EMERALD_BLOCK);
/*  767 */     registerItemBlock(Blocks.SPRUCE_STAIRS);
/*  768 */     registerItemBlock(Blocks.BIRCH_STAIRS);
/*  769 */     registerItemBlock(Blocks.JUNGLE_STAIRS);
/*  770 */     registerItemBlock(Blocks.COMMAND_BLOCK);
/*  771 */     registerItemBlock((Block)Blocks.BEACON);
/*  772 */     registerItemBlock(Blocks.COBBLESTONE_WALL, (new ItemMultiTexture(Blocks.COBBLESTONE_WALL, Blocks.COBBLESTONE_WALL, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  776 */               return BlockWall.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  778 */           })).setUnlocalizedName("cobbleWall"));
/*  779 */     registerItemBlock(Blocks.WOODEN_BUTTON);
/*  780 */     registerItemBlock(Blocks.ANVIL, (new ItemAnvilBlock(Blocks.ANVIL)).setUnlocalizedName("anvil"));
/*  781 */     registerItemBlock(Blocks.TRAPPED_CHEST);
/*  782 */     registerItemBlock(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
/*  783 */     registerItemBlock(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
/*  784 */     registerItemBlock((Block)Blocks.DAYLIGHT_DETECTOR);
/*  785 */     registerItemBlock(Blocks.REDSTONE_BLOCK);
/*  786 */     registerItemBlock(Blocks.QUARTZ_ORE);
/*  787 */     registerItemBlock((Block)Blocks.HOPPER);
/*  788 */     registerItemBlock(Blocks.QUARTZ_BLOCK, (new ItemMultiTexture(Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, new String[] { "default", "chiseled", "lines" })).setUnlocalizedName("quartzBlock"));
/*  789 */     registerItemBlock(Blocks.QUARTZ_STAIRS);
/*  790 */     registerItemBlock(Blocks.ACTIVATOR_RAIL);
/*  791 */     registerItemBlock(Blocks.DROPPER);
/*  792 */     registerItemBlock(Blocks.STAINED_HARDENED_CLAY, (new ItemCloth(Blocks.STAINED_HARDENED_CLAY)).setUnlocalizedName("clayHardenedStained"));
/*  793 */     registerItemBlock(Blocks.BARRIER);
/*  794 */     registerItemBlock(Blocks.IRON_TRAPDOOR);
/*  795 */     registerItemBlock(Blocks.HAY_BLOCK);
/*  796 */     registerItemBlock(Blocks.CARPET, (new ItemCloth(Blocks.CARPET)).setUnlocalizedName("woolCarpet"));
/*  797 */     registerItemBlock(Blocks.HARDENED_CLAY);
/*  798 */     registerItemBlock(Blocks.COAL_BLOCK);
/*  799 */     registerItemBlock(Blocks.PACKED_ICE);
/*  800 */     registerItemBlock(Blocks.ACACIA_STAIRS);
/*  801 */     registerItemBlock(Blocks.DARK_OAK_STAIRS);
/*  802 */     registerItemBlock(Blocks.SLIME_BLOCK);
/*  803 */     registerItemBlock(Blocks.GRASS_PATH);
/*  804 */     registerItemBlock((Block)Blocks.DOUBLE_PLANT, (new ItemMultiTexture((Block)Blocks.DOUBLE_PLANT, (Block)Blocks.DOUBLE_PLANT, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  808 */               return BlockDoublePlant.EnumPlantType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  810 */           })).setUnlocalizedName("doublePlant"));
/*  811 */     registerItemBlock((Block)Blocks.STAINED_GLASS, (new ItemCloth((Block)Blocks.STAINED_GLASS)).setUnlocalizedName("stainedGlass"));
/*  812 */     registerItemBlock((Block)Blocks.STAINED_GLASS_PANE, (new ItemCloth((Block)Blocks.STAINED_GLASS_PANE)).setUnlocalizedName("stainedGlassPane"));
/*  813 */     registerItemBlock(Blocks.PRISMARINE, (new ItemMultiTexture(Blocks.PRISMARINE, Blocks.PRISMARINE, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  817 */               return BlockPrismarine.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  819 */           })).setUnlocalizedName("prismarine"));
/*  820 */     registerItemBlock(Blocks.SEA_LANTERN);
/*  821 */     registerItemBlock(Blocks.RED_SANDSTONE, (new ItemMultiTexture(Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE, new ItemMultiTexture.Mapper()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  825 */               return BlockRedSandstone.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  827 */           })).setUnlocalizedName("redSandStone"));
/*  828 */     registerItemBlock(Blocks.RED_SANDSTONE_STAIRS);
/*  829 */     registerItemBlock((Block)Blocks.STONE_SLAB2, (new ItemSlab((Block)Blocks.STONE_SLAB2, Blocks.STONE_SLAB2, Blocks.DOUBLE_STONE_SLAB2)).setUnlocalizedName("stoneSlab2"));
/*  830 */     registerItemBlock(Blocks.REPEATING_COMMAND_BLOCK);
/*  831 */     registerItemBlock(Blocks.CHAIN_COMMAND_BLOCK);
/*  832 */     registerItemBlock(Blocks.MAGMA);
/*  833 */     registerItemBlock(Blocks.NETHER_WART_BLOCK);
/*  834 */     registerItemBlock(Blocks.RED_NETHER_BRICK);
/*  835 */     registerItemBlock(Blocks.BONE_BLOCK);
/*  836 */     registerItemBlock(Blocks.STRUCTURE_VOID);
/*  837 */     registerItemBlock(Blocks.field_190976_dk);
/*  838 */     registerItemBlock(Blocks.field_190977_dl, new ItemShulkerBox(Blocks.field_190977_dl));
/*  839 */     registerItemBlock(Blocks.field_190978_dm, new ItemShulkerBox(Blocks.field_190978_dm));
/*  840 */     registerItemBlock(Blocks.field_190979_dn, new ItemShulkerBox(Blocks.field_190979_dn));
/*  841 */     registerItemBlock(Blocks.field_190980_do, new ItemShulkerBox(Blocks.field_190980_do));
/*  842 */     registerItemBlock(Blocks.field_190981_dp, new ItemShulkerBox(Blocks.field_190981_dp));
/*  843 */     registerItemBlock(Blocks.field_190982_dq, new ItemShulkerBox(Blocks.field_190982_dq));
/*  844 */     registerItemBlock(Blocks.field_190983_dr, new ItemShulkerBox(Blocks.field_190983_dr));
/*  845 */     registerItemBlock(Blocks.field_190984_ds, new ItemShulkerBox(Blocks.field_190984_ds));
/*  846 */     registerItemBlock(Blocks.field_190985_dt, new ItemShulkerBox(Blocks.field_190985_dt));
/*  847 */     registerItemBlock(Blocks.field_190986_du, new ItemShulkerBox(Blocks.field_190986_du));
/*  848 */     registerItemBlock(Blocks.field_190987_dv, new ItemShulkerBox(Blocks.field_190987_dv));
/*  849 */     registerItemBlock(Blocks.field_190988_dw, new ItemShulkerBox(Blocks.field_190988_dw));
/*  850 */     registerItemBlock(Blocks.field_190989_dx, new ItemShulkerBox(Blocks.field_190989_dx));
/*  851 */     registerItemBlock(Blocks.field_190990_dy, new ItemShulkerBox(Blocks.field_190990_dy));
/*  852 */     registerItemBlock(Blocks.field_190991_dz, new ItemShulkerBox(Blocks.field_190991_dz));
/*  853 */     registerItemBlock(Blocks.field_190975_dA, new ItemShulkerBox(Blocks.field_190975_dA));
/*  854 */     registerItemBlock(Blocks.field_192427_dB);
/*  855 */     registerItemBlock(Blocks.field_192428_dC);
/*  856 */     registerItemBlock(Blocks.field_192429_dD);
/*  857 */     registerItemBlock(Blocks.field_192430_dE);
/*  858 */     registerItemBlock(Blocks.field_192431_dF);
/*  859 */     registerItemBlock(Blocks.field_192432_dG);
/*  860 */     registerItemBlock(Blocks.field_192433_dH);
/*  861 */     registerItemBlock(Blocks.field_192434_dI);
/*  862 */     registerItemBlock(Blocks.field_192435_dJ);
/*  863 */     registerItemBlock(Blocks.field_192436_dK);
/*  864 */     registerItemBlock(Blocks.field_192437_dL);
/*  865 */     registerItemBlock(Blocks.field_192438_dM);
/*  866 */     registerItemBlock(Blocks.field_192439_dN);
/*  867 */     registerItemBlock(Blocks.field_192440_dO);
/*  868 */     registerItemBlock(Blocks.field_192441_dP);
/*  869 */     registerItemBlock(Blocks.field_192442_dQ);
/*  870 */     registerItemBlock(Blocks.field_192443_dR, (new ItemCloth(Blocks.field_192443_dR)).setUnlocalizedName("concrete"));
/*  871 */     registerItemBlock(Blocks.field_192444_dS, (new ItemCloth(Blocks.field_192444_dS)).setUnlocalizedName("concrete_powder"));
/*  872 */     registerItemBlock(Blocks.STRUCTURE_BLOCK);
/*  873 */     registerItem(256, "iron_shovel", (new ItemSpade(ToolMaterial.IRON)).setUnlocalizedName("shovelIron"));
/*  874 */     registerItem(257, "iron_pickaxe", (new ItemPickaxe(ToolMaterial.IRON)).setUnlocalizedName("pickaxeIron"));
/*  875 */     registerItem(258, "iron_axe", (new ItemAxe(ToolMaterial.IRON)).setUnlocalizedName("hatchetIron"));
/*  876 */     registerItem(259, "flint_and_steel", (new ItemFlintAndSteel()).setUnlocalizedName("flintAndSteel"));
/*  877 */     registerItem(260, "apple", (new ItemFood(4, 0.3F, false)).setUnlocalizedName("apple"));
/*  878 */     registerItem(261, "bow", (new ItemBow()).setUnlocalizedName("bow"));
/*  879 */     registerItem(262, "arrow", (new ItemArrow()).setUnlocalizedName("arrow"));
/*  880 */     registerItem(263, "coal", (new ItemCoal()).setUnlocalizedName("coal"));
/*  881 */     registerItem(264, "diamond", (new Item()).setUnlocalizedName("diamond").setCreativeTab(CreativeTabs.MATERIALS));
/*  882 */     registerItem(265, "iron_ingot", (new Item()).setUnlocalizedName("ingotIron").setCreativeTab(CreativeTabs.MATERIALS));
/*  883 */     registerItem(266, "gold_ingot", (new Item()).setUnlocalizedName("ingotGold").setCreativeTab(CreativeTabs.MATERIALS));
/*  884 */     registerItem(267, "iron_sword", (new ItemSword(ToolMaterial.IRON)).setUnlocalizedName("swordIron"));
/*  885 */     registerItem(268, "wooden_sword", (new ItemSword(ToolMaterial.WOOD)).setUnlocalizedName("swordWood"));
/*  886 */     registerItem(269, "wooden_shovel", (new ItemSpade(ToolMaterial.WOOD)).setUnlocalizedName("shovelWood"));
/*  887 */     registerItem(270, "wooden_pickaxe", (new ItemPickaxe(ToolMaterial.WOOD)).setUnlocalizedName("pickaxeWood"));
/*  888 */     registerItem(271, "wooden_axe", (new ItemAxe(ToolMaterial.WOOD)).setUnlocalizedName("hatchetWood"));
/*  889 */     registerItem(272, "stone_sword", (new ItemSword(ToolMaterial.STONE)).setUnlocalizedName("swordStone"));
/*  890 */     registerItem(273, "stone_shovel", (new ItemSpade(ToolMaterial.STONE)).setUnlocalizedName("shovelStone"));
/*  891 */     registerItem(274, "stone_pickaxe", (new ItemPickaxe(ToolMaterial.STONE)).setUnlocalizedName("pickaxeStone"));
/*  892 */     registerItem(275, "stone_axe", (new ItemAxe(ToolMaterial.STONE)).setUnlocalizedName("hatchetStone"));
/*  893 */     registerItem(276, "diamond_sword", (new ItemSword(ToolMaterial.DIAMOND)).setUnlocalizedName("swordDiamond"));
/*  894 */     registerItem(277, "diamond_shovel", (new ItemSpade(ToolMaterial.DIAMOND)).setUnlocalizedName("shovelDiamond"));
/*  895 */     registerItem(278, "diamond_pickaxe", (new ItemPickaxe(ToolMaterial.DIAMOND)).setUnlocalizedName("pickaxeDiamond"));
/*  896 */     registerItem(279, "diamond_axe", (new ItemAxe(ToolMaterial.DIAMOND)).setUnlocalizedName("hatchetDiamond"));
/*  897 */     registerItem(280, "stick", (new Item()).setFull3D().setUnlocalizedName("stick").setCreativeTab(CreativeTabs.MATERIALS));
/*  898 */     registerItem(281, "bowl", (new Item()).setUnlocalizedName("bowl").setCreativeTab(CreativeTabs.MATERIALS));
/*  899 */     registerItem(282, "mushroom_stew", (new ItemSoup(6)).setUnlocalizedName("mushroomStew"));
/*  900 */     registerItem(283, "golden_sword", (new ItemSword(ToolMaterial.GOLD)).setUnlocalizedName("swordGold"));
/*  901 */     registerItem(284, "golden_shovel", (new ItemSpade(ToolMaterial.GOLD)).setUnlocalizedName("shovelGold"));
/*  902 */     registerItem(285, "golden_pickaxe", (new ItemPickaxe(ToolMaterial.GOLD)).setUnlocalizedName("pickaxeGold"));
/*  903 */     registerItem(286, "golden_axe", (new ItemAxe(ToolMaterial.GOLD)).setUnlocalizedName("hatchetGold"));
/*  904 */     registerItem(287, "string", (new ItemBlockSpecial(Blocks.TRIPWIRE)).setUnlocalizedName("string").setCreativeTab(CreativeTabs.MATERIALS));
/*  905 */     registerItem(288, "feather", (new Item()).setUnlocalizedName("feather").setCreativeTab(CreativeTabs.MATERIALS));
/*  906 */     registerItem(289, "gunpowder", (new Item()).setUnlocalizedName("sulphur").setCreativeTab(CreativeTabs.MATERIALS));
/*  907 */     registerItem(290, "wooden_hoe", (new ItemHoe(ToolMaterial.WOOD)).setUnlocalizedName("hoeWood"));
/*  908 */     registerItem(291, "stone_hoe", (new ItemHoe(ToolMaterial.STONE)).setUnlocalizedName("hoeStone"));
/*  909 */     registerItem(292, "iron_hoe", (new ItemHoe(ToolMaterial.IRON)).setUnlocalizedName("hoeIron"));
/*  910 */     registerItem(293, "diamond_hoe", (new ItemHoe(ToolMaterial.DIAMOND)).setUnlocalizedName("hoeDiamond"));
/*  911 */     registerItem(294, "golden_hoe", (new ItemHoe(ToolMaterial.GOLD)).setUnlocalizedName("hoeGold"));
/*  912 */     registerItem(295, "wheat_seeds", (new ItemSeeds(Blocks.WHEAT, Blocks.FARMLAND)).setUnlocalizedName("seeds"));
/*  913 */     registerItem(296, "wheat", (new Item()).setUnlocalizedName("wheat").setCreativeTab(CreativeTabs.MATERIALS));
/*  914 */     registerItem(297, "bread", (new ItemFood(5, 0.6F, false)).setUnlocalizedName("bread"));
/*  915 */     registerItem(298, "leather_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.HEAD)).setUnlocalizedName("helmetCloth"));
/*  916 */     registerItem(299, "leather_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.CHEST)).setUnlocalizedName("chestplateCloth"));
/*  917 */     registerItem(300, "leather_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.LEGS)).setUnlocalizedName("leggingsCloth"));
/*  918 */     registerItem(301, "leather_boots", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.FEET)).setUnlocalizedName("bootsCloth"));
/*  919 */     registerItem(302, "chainmail_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.HEAD)).setUnlocalizedName("helmetChain"));
/*  920 */     registerItem(303, "chainmail_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.CHEST)).setUnlocalizedName("chestplateChain"));
/*  921 */     registerItem(304, "chainmail_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.LEGS)).setUnlocalizedName("leggingsChain"));
/*  922 */     registerItem(305, "chainmail_boots", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.FEET)).setUnlocalizedName("bootsChain"));
/*  923 */     registerItem(306, "iron_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, EntityEquipmentSlot.HEAD)).setUnlocalizedName("helmetIron"));
/*  924 */     registerItem(307, "iron_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, EntityEquipmentSlot.CHEST)).setUnlocalizedName("chestplateIron"));
/*  925 */     registerItem(308, "iron_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, EntityEquipmentSlot.LEGS)).setUnlocalizedName("leggingsIron"));
/*  926 */     registerItem(309, "iron_boots", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, EntityEquipmentSlot.FEET)).setUnlocalizedName("bootsIron"));
/*  927 */     registerItem(310, "diamond_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, EntityEquipmentSlot.HEAD)).setUnlocalizedName("helmetDiamond"));
/*  928 */     registerItem(311, "diamond_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, EntityEquipmentSlot.CHEST)).setUnlocalizedName("chestplateDiamond"));
/*  929 */     registerItem(312, "diamond_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, EntityEquipmentSlot.LEGS)).setUnlocalizedName("leggingsDiamond"));
/*  930 */     registerItem(313, "diamond_boots", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, EntityEquipmentSlot.FEET)).setUnlocalizedName("bootsDiamond"));
/*  931 */     registerItem(314, "golden_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, EntityEquipmentSlot.HEAD)).setUnlocalizedName("helmetGold"));
/*  932 */     registerItem(315, "golden_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, EntityEquipmentSlot.CHEST)).setUnlocalizedName("chestplateGold"));
/*  933 */     registerItem(316, "golden_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, EntityEquipmentSlot.LEGS)).setUnlocalizedName("leggingsGold"));
/*  934 */     registerItem(317, "golden_boots", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, EntityEquipmentSlot.FEET)).setUnlocalizedName("bootsGold"));
/*  935 */     registerItem(318, "flint", (new Item()).setUnlocalizedName("flint").setCreativeTab(CreativeTabs.MATERIALS));
/*  936 */     registerItem(319, "porkchop", (new ItemFood(3, 0.3F, true)).setUnlocalizedName("porkchopRaw"));
/*  937 */     registerItem(320, "cooked_porkchop", (new ItemFood(8, 0.8F, true)).setUnlocalizedName("porkchopCooked"));
/*  938 */     registerItem(321, "painting", (new ItemHangingEntity((Class)EntityPainting.class)).setUnlocalizedName("painting"));
/*  939 */     registerItem(322, "golden_apple", (new ItemAppleGold(4, 1.2F, false)).setAlwaysEdible().setUnlocalizedName("appleGold"));
/*  940 */     registerItem(323, "sign", (new ItemSign()).setUnlocalizedName("sign"));
/*  941 */     registerItem(324, "wooden_door", (new ItemDoor((Block)Blocks.OAK_DOOR)).setUnlocalizedName("doorOak"));
/*  942 */     Item item = (new ItemBucket(Blocks.AIR)).setUnlocalizedName("bucket").setMaxStackSize(16);
/*  943 */     registerItem(325, "bucket", item);
/*  944 */     registerItem(326, "water_bucket", (new ItemBucket((Block)Blocks.FLOWING_WATER)).setUnlocalizedName("bucketWater").setContainerItem(item));
/*  945 */     registerItem(327, "lava_bucket", (new ItemBucket((Block)Blocks.FLOWING_LAVA)).setUnlocalizedName("bucketLava").setContainerItem(item));
/*  946 */     registerItem(328, "minecart", (new ItemMinecart(EntityMinecart.Type.RIDEABLE)).setUnlocalizedName("minecart"));
/*  947 */     registerItem(329, "saddle", (new ItemSaddle()).setUnlocalizedName("saddle"));
/*  948 */     registerItem(330, "iron_door", (new ItemDoor((Block)Blocks.IRON_DOOR)).setUnlocalizedName("doorIron"));
/*  949 */     registerItem(331, "redstone", (new ItemRedstone()).setUnlocalizedName("redstone"));
/*  950 */     registerItem(332, "snowball", (new ItemSnowball()).setUnlocalizedName("snowball"));
/*  951 */     registerItem(333, "boat", new ItemBoat(EntityBoat.Type.OAK));
/*  952 */     registerItem(334, "leather", (new Item()).setUnlocalizedName("leather").setCreativeTab(CreativeTabs.MATERIALS));
/*  953 */     registerItem(335, "milk_bucket", (new ItemBucketMilk()).setUnlocalizedName("milk").setContainerItem(item));
/*  954 */     registerItem(336, "brick", (new Item()).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.MATERIALS));
/*  955 */     registerItem(337, "clay_ball", (new Item()).setUnlocalizedName("clay").setCreativeTab(CreativeTabs.MATERIALS));
/*  956 */     registerItem(338, "reeds", (new ItemBlockSpecial((Block)Blocks.REEDS)).setUnlocalizedName("reeds").setCreativeTab(CreativeTabs.MATERIALS));
/*  957 */     registerItem(339, "paper", (new Item()).setUnlocalizedName("paper").setCreativeTab(CreativeTabs.MISC));
/*  958 */     registerItem(340, "book", (new ItemBook()).setUnlocalizedName("book").setCreativeTab(CreativeTabs.MISC));
/*  959 */     registerItem(341, "slime_ball", (new Item()).setUnlocalizedName("slimeball").setCreativeTab(CreativeTabs.MISC));
/*  960 */     registerItem(342, "chest_minecart", (new ItemMinecart(EntityMinecart.Type.CHEST)).setUnlocalizedName("minecartChest"));
/*  961 */     registerItem(343, "furnace_minecart", (new ItemMinecart(EntityMinecart.Type.FURNACE)).setUnlocalizedName("minecartFurnace"));
/*  962 */     registerItem(344, "egg", (new ItemEgg()).setUnlocalizedName("egg"));
/*  963 */     registerItem(345, "compass", (new ItemCompass()).setUnlocalizedName("compass").setCreativeTab(CreativeTabs.TOOLS));
/*  964 */     registerItem(346, "fishing_rod", (new ItemFishingRod()).setUnlocalizedName("fishingRod"));
/*  965 */     registerItem(347, "clock", (new ItemClock()).setUnlocalizedName("clock").setCreativeTab(CreativeTabs.TOOLS));
/*  966 */     registerItem(348, "glowstone_dust", (new Item()).setUnlocalizedName("yellowDust").setCreativeTab(CreativeTabs.MATERIALS));
/*  967 */     registerItem(349, "fish", (new ItemFishFood(false)).setUnlocalizedName("fish").setHasSubtypes(true));
/*  968 */     registerItem(350, "cooked_fish", (new ItemFishFood(true)).setUnlocalizedName("fish").setHasSubtypes(true));
/*  969 */     registerItem(351, "dye", (new ItemDye()).setUnlocalizedName("dyePowder"));
/*  970 */     registerItem(352, "bone", (new Item()).setUnlocalizedName("bone").setFull3D().setCreativeTab(CreativeTabs.MISC));
/*  971 */     registerItem(353, "sugar", (new Item()).setUnlocalizedName("sugar").setCreativeTab(CreativeTabs.MATERIALS));
/*  972 */     registerItem(354, "cake", (new ItemBlockSpecial(Blocks.CAKE)).setMaxStackSize(1).setUnlocalizedName("cake").setCreativeTab(CreativeTabs.FOOD));
/*  973 */     registerItem(355, "bed", (new ItemBed()).setMaxStackSize(1).setUnlocalizedName("bed"));
/*  974 */     registerItem(356, "repeater", (new ItemBlockSpecial((Block)Blocks.UNPOWERED_REPEATER)).setUnlocalizedName("diode").setCreativeTab(CreativeTabs.REDSTONE));
/*  975 */     registerItem(357, "cookie", (new ItemFood(2, 0.1F, false)).setUnlocalizedName("cookie"));
/*  976 */     registerItem(358, "filled_map", (new ItemMap()).setUnlocalizedName("map"));
/*  977 */     registerItem(359, "shears", (new ItemShears()).setUnlocalizedName("shears"));
/*  978 */     registerItem(360, "melon", (new ItemFood(2, 0.3F, false)).setUnlocalizedName("melon"));
/*  979 */     registerItem(361, "pumpkin_seeds", (new ItemSeeds(Blocks.PUMPKIN_STEM, Blocks.FARMLAND)).setUnlocalizedName("seeds_pumpkin"));
/*  980 */     registerItem(362, "melon_seeds", (new ItemSeeds(Blocks.MELON_STEM, Blocks.FARMLAND)).setUnlocalizedName("seeds_melon"));
/*  981 */     registerItem(363, "beef", (new ItemFood(3, 0.3F, true)).setUnlocalizedName("beefRaw"));
/*  982 */     registerItem(364, "cooked_beef", (new ItemFood(8, 0.8F, true)).setUnlocalizedName("beefCooked"));
/*  983 */     registerItem(365, "chicken", (new ItemFood(2, 0.3F, true)).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.3F).setUnlocalizedName("chickenRaw"));
/*  984 */     registerItem(366, "cooked_chicken", (new ItemFood(6, 0.6F, true)).setUnlocalizedName("chickenCooked"));
/*  985 */     registerItem(367, "rotten_flesh", (new ItemFood(4, 0.1F, true)).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.8F).setUnlocalizedName("rottenFlesh"));
/*  986 */     registerItem(368, "ender_pearl", (new ItemEnderPearl()).setUnlocalizedName("enderPearl"));
/*  987 */     registerItem(369, "blaze_rod", (new Item()).setUnlocalizedName("blazeRod").setCreativeTab(CreativeTabs.MATERIALS).setFull3D());
/*  988 */     registerItem(370, "ghast_tear", (new Item()).setUnlocalizedName("ghastTear").setCreativeTab(CreativeTabs.BREWING));
/*  989 */     registerItem(371, "gold_nugget", (new Item()).setUnlocalizedName("goldNugget").setCreativeTab(CreativeTabs.MATERIALS));
/*  990 */     registerItem(372, "nether_wart", (new ItemSeeds(Blocks.NETHER_WART, Blocks.SOUL_SAND)).setUnlocalizedName("netherStalkSeeds"));
/*  991 */     registerItem(373, "potion", (new ItemPotion()).setUnlocalizedName("potion"));
/*  992 */     Item item1 = (new ItemGlassBottle()).setUnlocalizedName("glassBottle");
/*  993 */     registerItem(374, "glass_bottle", item1);
/*  994 */     registerItem(375, "spider_eye", (new ItemFood(2, 0.8F, false)).setPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0), 1.0F).setUnlocalizedName("spiderEye"));
/*  995 */     registerItem(376, "fermented_spider_eye", (new Item()).setUnlocalizedName("fermentedSpiderEye").setCreativeTab(CreativeTabs.BREWING));
/*  996 */     registerItem(377, "blaze_powder", (new Item()).setUnlocalizedName("blazePowder").setCreativeTab(CreativeTabs.BREWING));
/*  997 */     registerItem(378, "magma_cream", (new Item()).setUnlocalizedName("magmaCream").setCreativeTab(CreativeTabs.BREWING));
/*  998 */     registerItem(379, "brewing_stand", (new ItemBlockSpecial(Blocks.BREWING_STAND)).setUnlocalizedName("brewingStand").setCreativeTab(CreativeTabs.BREWING));
/*  999 */     registerItem(380, "cauldron", (new ItemBlockSpecial((Block)Blocks.CAULDRON)).setUnlocalizedName("cauldron").setCreativeTab(CreativeTabs.BREWING));
/* 1000 */     registerItem(381, "ender_eye", (new ItemEnderEye()).setUnlocalizedName("eyeOfEnder"));
/* 1001 */     registerItem(382, "speckled_melon", (new Item()).setUnlocalizedName("speckledMelon").setCreativeTab(CreativeTabs.BREWING));
/* 1002 */     registerItem(383, "spawn_egg", (new ItemMonsterPlacer()).setUnlocalizedName("monsterPlacer"));
/* 1003 */     registerItem(384, "experience_bottle", (new ItemExpBottle()).setUnlocalizedName("expBottle"));
/* 1004 */     registerItem(385, "fire_charge", (new ItemFireball()).setUnlocalizedName("fireball"));
/* 1005 */     registerItem(386, "writable_book", (new ItemWritableBook()).setUnlocalizedName("writingBook").setCreativeTab(CreativeTabs.MISC));
/* 1006 */     registerItem(387, "written_book", (new ItemWrittenBook()).setUnlocalizedName("writtenBook").setMaxStackSize(16));
/* 1007 */     registerItem(388, "emerald", (new Item()).setUnlocalizedName("emerald").setCreativeTab(CreativeTabs.MATERIALS));
/* 1008 */     registerItem(389, "item_frame", (new ItemHangingEntity((Class)EntityItemFrame.class)).setUnlocalizedName("frame"));
/* 1009 */     registerItem(390, "flower_pot", (new ItemBlockSpecial(Blocks.FLOWER_POT)).setUnlocalizedName("flowerPot").setCreativeTab(CreativeTabs.DECORATIONS));
/* 1010 */     registerItem(391, "carrot", (new ItemSeedFood(3, 0.6F, Blocks.CARROTS, Blocks.FARMLAND)).setUnlocalizedName("carrots"));
/* 1011 */     registerItem(392, "potato", (new ItemSeedFood(1, 0.3F, Blocks.POTATOES, Blocks.FARMLAND)).setUnlocalizedName("potato"));
/* 1012 */     registerItem(393, "baked_potato", (new ItemFood(5, 0.6F, false)).setUnlocalizedName("potatoBaked"));
/* 1013 */     registerItem(394, "poisonous_potato", (new ItemFood(2, 0.3F, false)).setPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0), 0.6F).setUnlocalizedName("potatoPoisonous"));
/* 1014 */     registerItem(395, "map", (new ItemEmptyMap()).setUnlocalizedName("emptyMap"));
/* 1015 */     registerItem(396, "golden_carrot", (new ItemFood(6, 1.2F, false)).setUnlocalizedName("carrotGolden").setCreativeTab(CreativeTabs.BREWING));
/* 1016 */     registerItem(397, "skull", (new ItemSkull()).setUnlocalizedName("skull"));
/* 1017 */     registerItem(398, "carrot_on_a_stick", (new ItemCarrotOnAStick()).setUnlocalizedName("carrotOnAStick"));
/* 1018 */     registerItem(399, "nether_star", (new ItemSimpleFoiled()).setUnlocalizedName("netherStar").setCreativeTab(CreativeTabs.MATERIALS));
/* 1019 */     registerItem(400, "pumpkin_pie", (new ItemFood(8, 0.3F, false)).setUnlocalizedName("pumpkinPie").setCreativeTab(CreativeTabs.FOOD));
/* 1020 */     registerItem(401, "fireworks", (new ItemFirework()).setUnlocalizedName("fireworks"));
/* 1021 */     registerItem(402, "firework_charge", (new ItemFireworkCharge()).setUnlocalizedName("fireworksCharge").setCreativeTab(CreativeTabs.MISC));
/* 1022 */     registerItem(403, "enchanted_book", (new ItemEnchantedBook()).setMaxStackSize(1).setUnlocalizedName("enchantedBook"));
/* 1023 */     registerItem(404, "comparator", (new ItemBlockSpecial((Block)Blocks.UNPOWERED_COMPARATOR)).setUnlocalizedName("comparator").setCreativeTab(CreativeTabs.REDSTONE));
/* 1024 */     registerItem(405, "netherbrick", (new Item()).setUnlocalizedName("netherbrick").setCreativeTab(CreativeTabs.MATERIALS));
/* 1025 */     registerItem(406, "quartz", (new Item()).setUnlocalizedName("netherquartz").setCreativeTab(CreativeTabs.MATERIALS));
/* 1026 */     registerItem(407, "tnt_minecart", (new ItemMinecart(EntityMinecart.Type.TNT)).setUnlocalizedName("minecartTnt"));
/* 1027 */     registerItem(408, "hopper_minecart", (new ItemMinecart(EntityMinecart.Type.HOPPER)).setUnlocalizedName("minecartHopper"));
/* 1028 */     registerItem(409, "prismarine_shard", (new Item()).setUnlocalizedName("prismarineShard").setCreativeTab(CreativeTabs.MATERIALS));
/* 1029 */     registerItem(410, "prismarine_crystals", (new Item()).setUnlocalizedName("prismarineCrystals").setCreativeTab(CreativeTabs.MATERIALS));
/* 1030 */     registerItem(411, "rabbit", (new ItemFood(3, 0.3F, true)).setUnlocalizedName("rabbitRaw"));
/* 1031 */     registerItem(412, "cooked_rabbit", (new ItemFood(5, 0.6F, true)).setUnlocalizedName("rabbitCooked"));
/* 1032 */     registerItem(413, "rabbit_stew", (new ItemSoup(10)).setUnlocalizedName("rabbitStew"));
/* 1033 */     registerItem(414, "rabbit_foot", (new Item()).setUnlocalizedName("rabbitFoot").setCreativeTab(CreativeTabs.BREWING));
/* 1034 */     registerItem(415, "rabbit_hide", (new Item()).setUnlocalizedName("rabbitHide").setCreativeTab(CreativeTabs.MATERIALS));
/* 1035 */     registerItem(416, "armor_stand", (new ItemArmorStand()).setUnlocalizedName("armorStand").setMaxStackSize(16));
/* 1036 */     registerItem(417, "iron_horse_armor", (new Item()).setUnlocalizedName("horsearmormetal").setMaxStackSize(1).setCreativeTab(CreativeTabs.MISC));
/* 1037 */     registerItem(418, "golden_horse_armor", (new Item()).setUnlocalizedName("horsearmorgold").setMaxStackSize(1).setCreativeTab(CreativeTabs.MISC));
/* 1038 */     registerItem(419, "diamond_horse_armor", (new Item()).setUnlocalizedName("horsearmordiamond").setMaxStackSize(1).setCreativeTab(CreativeTabs.MISC));
/* 1039 */     registerItem(420, "lead", (new ItemLead()).setUnlocalizedName("leash"));
/* 1040 */     registerItem(421, "name_tag", (new ItemNameTag()).setUnlocalizedName("nameTag"));
/* 1041 */     registerItem(422, "command_block_minecart", (new ItemMinecart(EntityMinecart.Type.COMMAND_BLOCK)).setUnlocalizedName("minecartCommandBlock").setCreativeTab(null));
/* 1042 */     registerItem(423, "mutton", (new ItemFood(2, 0.3F, true)).setUnlocalizedName("muttonRaw"));
/* 1043 */     registerItem(424, "cooked_mutton", (new ItemFood(6, 0.8F, true)).setUnlocalizedName("muttonCooked"));
/* 1044 */     registerItem(425, "banner", (new ItemBanner()).setUnlocalizedName("banner"));
/* 1045 */     registerItem(426, "end_crystal", new ItemEndCrystal());
/* 1046 */     registerItem(427, "spruce_door", (new ItemDoor((Block)Blocks.SPRUCE_DOOR)).setUnlocalizedName("doorSpruce"));
/* 1047 */     registerItem(428, "birch_door", (new ItemDoor((Block)Blocks.BIRCH_DOOR)).setUnlocalizedName("doorBirch"));
/* 1048 */     registerItem(429, "jungle_door", (new ItemDoor((Block)Blocks.JUNGLE_DOOR)).setUnlocalizedName("doorJungle"));
/* 1049 */     registerItem(430, "acacia_door", (new ItemDoor((Block)Blocks.ACACIA_DOOR)).setUnlocalizedName("doorAcacia"));
/* 1050 */     registerItem(431, "dark_oak_door", (new ItemDoor((Block)Blocks.DARK_OAK_DOOR)).setUnlocalizedName("doorDarkOak"));
/* 1051 */     registerItem(432, "chorus_fruit", (new ItemChorusFruit(4, 0.3F)).setAlwaysEdible().setUnlocalizedName("chorusFruit").setCreativeTab(CreativeTabs.MATERIALS));
/* 1052 */     registerItem(433, "chorus_fruit_popped", (new Item()).setUnlocalizedName("chorusFruitPopped").setCreativeTab(CreativeTabs.MATERIALS));
/* 1053 */     registerItem(434, "beetroot", (new ItemFood(1, 0.6F, false)).setUnlocalizedName("beetroot"));
/* 1054 */     registerItem(435, "beetroot_seeds", (new ItemSeeds(Blocks.BEETROOTS, Blocks.FARMLAND)).setUnlocalizedName("beetroot_seeds"));
/* 1055 */     registerItem(436, "beetroot_soup", (new ItemSoup(6)).setUnlocalizedName("beetroot_soup"));
/* 1056 */     registerItem(437, "dragon_breath", (new Item()).setCreativeTab(CreativeTabs.BREWING).setUnlocalizedName("dragon_breath").setContainerItem(item1));
/* 1057 */     registerItem(438, "splash_potion", (new ItemSplashPotion()).setUnlocalizedName("splash_potion"));
/* 1058 */     registerItem(439, "spectral_arrow", (new ItemSpectralArrow()).setUnlocalizedName("spectral_arrow"));
/* 1059 */     registerItem(440, "tipped_arrow", (new ItemTippedArrow()).setUnlocalizedName("tipped_arrow"));
/* 1060 */     registerItem(441, "lingering_potion", (new ItemLingeringPotion()).setUnlocalizedName("lingering_potion"));
/* 1061 */     registerItem(442, "shield", (new ItemShield()).setUnlocalizedName("shield"));
/* 1062 */     registerItem(443, "elytra", (new ItemElytra()).setUnlocalizedName("elytra"));
/* 1063 */     registerItem(444, "spruce_boat", new ItemBoat(EntityBoat.Type.SPRUCE));
/* 1064 */     registerItem(445, "birch_boat", new ItemBoat(EntityBoat.Type.BIRCH));
/* 1065 */     registerItem(446, "jungle_boat", new ItemBoat(EntityBoat.Type.JUNGLE));
/* 1066 */     registerItem(447, "acacia_boat", new ItemBoat(EntityBoat.Type.ACACIA));
/* 1067 */     registerItem(448, "dark_oak_boat", new ItemBoat(EntityBoat.Type.DARK_OAK));
/* 1068 */     registerItem(449, "totem_of_undying", (new Item()).setUnlocalizedName("totem").setMaxStackSize(1).setCreativeTab(CreativeTabs.COMBAT));
/* 1069 */     registerItem(450, "shulker_shell", (new Item()).setUnlocalizedName("shulkerShell").setCreativeTab(CreativeTabs.MATERIALS));
/* 1070 */     registerItem(452, "iron_nugget", (new Item()).setUnlocalizedName("ironNugget").setCreativeTab(CreativeTabs.MATERIALS));
/* 1071 */     registerItem(453, "knowledge_book", (new ItemKnowledgeBook()).setUnlocalizedName("knowledgeBook"));
/* 1072 */     registerItem(2256, "record_13", (new ItemRecord("13", SoundEvents.RECORD_13)).setUnlocalizedName("record"));
/* 1073 */     registerItem(2257, "record_cat", (new ItemRecord("cat", SoundEvents.RECORD_CAT)).setUnlocalizedName("record"));
/* 1074 */     registerItem(2258, "record_blocks", (new ItemRecord("blocks", SoundEvents.RECORD_BLOCKS)).setUnlocalizedName("record"));
/* 1075 */     registerItem(2259, "record_chirp", (new ItemRecord("chirp", SoundEvents.RECORD_CHIRP)).setUnlocalizedName("record"));
/* 1076 */     registerItem(2260, "record_far", (new ItemRecord("far", SoundEvents.RECORD_FAR)).setUnlocalizedName("record"));
/* 1077 */     registerItem(2261, "record_mall", (new ItemRecord("mall", SoundEvents.RECORD_MALL)).setUnlocalizedName("record"));
/* 1078 */     registerItem(2262, "record_mellohi", (new ItemRecord("mellohi", SoundEvents.RECORD_MELLOHI)).setUnlocalizedName("record"));
/* 1079 */     registerItem(2263, "record_stal", (new ItemRecord("stal", SoundEvents.RECORD_STAL)).setUnlocalizedName("record"));
/* 1080 */     registerItem(2264, "record_strad", (new ItemRecord("strad", SoundEvents.RECORD_STRAD)).setUnlocalizedName("record"));
/* 1081 */     registerItem(2265, "record_ward", (new ItemRecord("ward", SoundEvents.RECORD_WARD)).setUnlocalizedName("record"));
/* 1082 */     registerItem(2266, "record_11", (new ItemRecord("11", SoundEvents.RECORD_11)).setUnlocalizedName("record"));
/* 1083 */     registerItem(2267, "record_wait", (new ItemRecord("wait", SoundEvents.RECORD_WAIT)).setUnlocalizedName("record"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void registerItemBlock(Block blockIn) {
/* 1091 */     registerItemBlock(blockIn, new ItemBlock(blockIn));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void registerItemBlock(Block blockIn, Item itemIn) {
/* 1099 */     registerItem(Block.getIdFromBlock(blockIn), (ResourceLocation)Block.REGISTRY.getNameForObject(blockIn), itemIn);
/* 1100 */     BLOCK_TO_ITEM.put(blockIn, itemIn);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void registerItem(int id, String textualID, Item itemIn) {
/* 1105 */     registerItem(id, new ResourceLocation(textualID), itemIn);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void registerItem(int id, ResourceLocation textualID, Item itemIn) {
/* 1110 */     REGISTRY.register(id, textualID, itemIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack func_190903_i() {
/* 1115 */     return new ItemStack(this);
/*      */   }
/*      */   
/*      */   public enum ToolMaterial
/*      */   {
/* 1120 */     WOOD(0, 59, 2.0F, 0.0F, 15),
/* 1121 */     STONE(1, 131, 4.0F, 1.0F, 5),
/* 1122 */     IRON(2, 250, 6.0F, 2.0F, 14),
/* 1123 */     DIAMOND(3, 1561, 8.0F, 3.0F, 10),
/* 1124 */     GOLD(0, 32, 12.0F, 0.0F, 22);
/*      */     
/*      */     private final int harvestLevel;
/*      */     
/*      */     private final int maxUses;
/*      */     private final float efficiencyOnProperMaterial;
/*      */     private final float damageVsEntity;
/*      */     private final int enchantability;
/*      */     
/*      */     ToolMaterial(int harvestLevel, int maxUses, float efficiency, float damageVsEntity, int enchantability) {
/* 1134 */       this.harvestLevel = harvestLevel;
/* 1135 */       this.maxUses = maxUses;
/* 1136 */       this.efficiencyOnProperMaterial = efficiency;
/* 1137 */       this.damageVsEntity = damageVsEntity;
/* 1138 */       this.enchantability = enchantability;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getMaxUses() {
/* 1143 */       return this.maxUses;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getEfficiencyOnProperMaterial() {
/* 1148 */       return this.efficiencyOnProperMaterial;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getDamageVsEntity() {
/* 1153 */       return this.damageVsEntity;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHarvestLevel() {
/* 1158 */       return this.harvestLevel;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getEnchantability() {
/* 1163 */       return this.enchantability;
/*      */     }
/*      */ 
/*      */     
/*      */     public Item getRepairItem() {
/* 1168 */       if (this == WOOD)
/*      */       {
/* 1170 */         return Item.getItemFromBlock(Blocks.PLANKS);
/*      */       }
/* 1172 */       if (this == STONE)
/*      */       {
/* 1174 */         return Item.getItemFromBlock(Blocks.COBBLESTONE);
/*      */       }
/* 1176 */       if (this == GOLD)
/*      */       {
/* 1178 */         return Items.GOLD_INGOT;
/*      */       }
/* 1180 */       if (this == IRON)
/*      */       {
/* 1182 */         return Items.IRON_INGOT;
/*      */       }
/*      */ 
/*      */       
/* 1186 */       return (this == DIAMOND) ? Items.DIAMOND : null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\Item.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */