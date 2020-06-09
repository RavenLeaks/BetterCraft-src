/*     */ package net.minecraft.item;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*     */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*     */ import net.minecraft.dispenser.IBlockSource;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemArmor extends Item {
/*  34 */   private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };
/*  35 */   private static final UUID[] ARMOR_MODIFIERS = new UUID[] { UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150") };
/*  36 */   public static final String[] EMPTY_SLOT_NAMES = new String[] { "minecraft:items/empty_armor_slot_boots", "minecraft:items/empty_armor_slot_leggings", "minecraft:items/empty_armor_slot_chestplate", "minecraft:items/empty_armor_slot_helmet" };
/*  37 */   public static final IBehaviorDispenseItem DISPENSER_BEHAVIOR = (IBehaviorDispenseItem)new BehaviorDefaultDispenseItem()
/*     */     {
/*     */       protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */       {
/*  41 */         ItemStack itemstack = ItemArmor.dispenseArmor(source, stack);
/*  42 */         return itemstack.func_190926_b() ? super.dispenseStack(source, stack) : itemstack;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final EntityEquipmentSlot armorType;
/*     */ 
/*     */   
/*     */   public final int damageReduceAmount;
/*     */ 
/*     */   
/*     */   public final float toughness;
/*     */ 
/*     */   
/*     */   public final int renderIndex;
/*     */ 
/*     */   
/*     */   private final ArmorMaterial material;
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack dispenseArmor(IBlockSource blockSource, ItemStack stack) {
/*  66 */     BlockPos blockpos = blockSource.getBlockPos().offset((EnumFacing)blockSource.getBlockState().getValue((IProperty)BlockDispenser.FACING));
/*  67 */     List<EntityLivingBase> list = blockSource.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(blockpos), Predicates.and(EntitySelectors.NOT_SPECTATING, (Predicate)new EntitySelectors.ArmoredMob(stack)));
/*     */     
/*  69 */     if (list.isEmpty())
/*     */     {
/*  71 */       return ItemStack.field_190927_a;
/*     */     }
/*     */ 
/*     */     
/*  75 */     EntityLivingBase entitylivingbase = list.get(0);
/*  76 */     EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(stack);
/*  77 */     ItemStack itemstack = stack.splitStack(1);
/*  78 */     entitylivingbase.setItemStackToSlot(entityequipmentslot, itemstack);
/*     */     
/*  80 */     if (entitylivingbase instanceof EntityLiving)
/*     */     {
/*  82 */       ((EntityLiving)entitylivingbase).setDropChance(entityequipmentslot, 2.0F);
/*     */     }
/*     */     
/*  85 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemArmor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
/*  91 */     this.material = materialIn;
/*  92 */     this.armorType = equipmentSlotIn;
/*  93 */     this.renderIndex = renderIndexIn;
/*  94 */     this.damageReduceAmount = materialIn.getDamageReductionAmount(equipmentSlotIn);
/*  95 */     setMaxDamage(materialIn.getDurability(equipmentSlotIn));
/*  96 */     this.toughness = materialIn.getToughness();
/*  97 */     this.maxStackSize = 1;
/*  98 */     setCreativeTab(CreativeTabs.COMBAT);
/*  99 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, DISPENSER_BEHAVIOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityEquipmentSlot getEquipmentSlot() {
/* 107 */     return this.armorType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemEnchantability() {
/* 115 */     return this.material.getEnchantability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArmorMaterial getArmorMaterial() {
/* 123 */     return this.material;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasColor(ItemStack stack) {
/* 131 */     if (this.material != ArmorMaterial.LEATHER)
/*     */     {
/* 133 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 137 */     NBTTagCompound nbttagcompound = stack.getTagCompound();
/* 138 */     return (nbttagcompound != null && nbttagcompound.hasKey("display", 10)) ? nbttagcompound.getCompoundTag("display").hasKey("color", 3) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColor(ItemStack stack) {
/* 147 */     if (this.material != ArmorMaterial.LEATHER)
/*     */     {
/* 149 */       return 16777215;
/*     */     }
/*     */ 
/*     */     
/* 153 */     NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */     
/* 155 */     if (nbttagcompound != null) {
/*     */       
/* 157 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */       
/* 159 */       if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
/*     */       {
/* 161 */         return nbttagcompound1.getInteger("color");
/*     */       }
/*     */     } 
/*     */     
/* 165 */     return 10511680;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeColor(ItemStack stack) {
/* 174 */     if (this.material == ArmorMaterial.LEATHER) {
/*     */       
/* 176 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */       
/* 178 */       if (nbttagcompound != null) {
/*     */         
/* 180 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */         
/* 182 */         if (nbttagcompound1.hasKey("color"))
/*     */         {
/* 184 */           nbttagcompound1.removeTag("color");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(ItemStack stack, int color) {
/* 195 */     if (this.material != ArmorMaterial.LEATHER)
/*     */     {
/* 197 */       throw new UnsupportedOperationException("Can't dye non-leather!");
/*     */     }
/*     */ 
/*     */     
/* 201 */     NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */     
/* 203 */     if (nbttagcompound == null) {
/*     */       
/* 205 */       nbttagcompound = new NBTTagCompound();
/* 206 */       stack.setTagCompound(nbttagcompound);
/*     */     } 
/*     */     
/* 209 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */     
/* 211 */     if (!nbttagcompound.hasKey("display", 10))
/*     */     {
/* 213 */       nbttagcompound.setTag("display", (NBTBase)nbttagcompound1);
/*     */     }
/*     */     
/* 216 */     nbttagcompound1.setInteger("color", color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/* 225 */     return (this.material.getRepairItem() == repair.getItem()) ? true : super.getIsRepairable(toRepair, repair);
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 230 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/* 231 */     EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemstack);
/* 232 */     ItemStack itemstack1 = worldIn.getItemStackFromSlot(entityequipmentslot);
/*     */     
/* 234 */     if (itemstack1.func_190926_b()) {
/*     */       
/* 236 */       worldIn.setItemStackToSlot(entityequipmentslot, itemstack.copy());
/* 237 */       itemstack.func_190920_e(0);
/* 238 */       return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*     */     } 
/*     */ 
/*     */     
/* 242 */     return new ActionResult(EnumActionResult.FAIL, itemstack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
/* 248 */     Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
/*     */     
/* 250 */     if (equipmentSlot == this.armorType) {
/*     */       
/* 252 */       multimap.put(SharedMonsterAttributes.ARMOR.getAttributeUnlocalizedName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", this.damageReduceAmount, 0));
/* 253 */       multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getAttributeUnlocalizedName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor toughness", this.toughness, 0));
/*     */     } 
/*     */     
/* 256 */     return multimap;
/*     */   }
/*     */   
/*     */   public enum ArmorMaterial
/*     */   {
/* 261 */     LEATHER("leather", 5, (String)new int[] { 1, 2, 3, 1 }, 15, (int[])SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F),
/* 262 */     CHAIN("chainmail", 15, (String)new int[] { 1, 4, 5, 2 }, 12, (int[])SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F),
/* 263 */     IRON("iron", 15, (String)new int[] { 2, 5, 6, 2 }, 9, (int[])SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F),
/* 264 */     GOLD("gold", 7, (String)new int[] { 1, 3, 5, 2 }, 25, (int[])SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F),
/* 265 */     DIAMOND("diamond", 33, (String)new int[] { 3, 6, 8, 3 }, 10, (int[])SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private final int maxDamageFactor;
/*     */     private final int[] damageReductionAmountArray;
/*     */     private final int enchantability;
/*     */     private final SoundEvent soundEvent;
/*     */     private final float toughness;
/*     */     
/*     */     ArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountArrayIn, int enchantabilityIn, SoundEvent soundEventIn, float toughnessIn) {
/* 276 */       this.name = nameIn;
/* 277 */       this.maxDamageFactor = maxDamageFactorIn;
/* 278 */       this.damageReductionAmountArray = damageReductionAmountArrayIn;
/* 279 */       this.enchantability = enchantabilityIn;
/* 280 */       this.soundEvent = soundEventIn;
/* 281 */       this.toughness = toughnessIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getDurability(EntityEquipmentSlot armorType) {
/* 286 */       return ItemArmor.MAX_DAMAGE_ARRAY[armorType.getIndex()] * this.maxDamageFactor;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getDamageReductionAmount(EntityEquipmentSlot armorType) {
/* 291 */       return this.damageReductionAmountArray[armorType.getIndex()];
/*     */     }
/*     */ 
/*     */     
/*     */     public int getEnchantability() {
/* 296 */       return this.enchantability;
/*     */     }
/*     */ 
/*     */     
/*     */     public SoundEvent getSoundEvent() {
/* 301 */       return this.soundEvent;
/*     */     }
/*     */ 
/*     */     
/*     */     public Item getRepairItem() {
/* 306 */       if (this == LEATHER)
/*     */       {
/* 308 */         return Items.LEATHER;
/*     */       }
/* 310 */       if (this == CHAIN)
/*     */       {
/* 312 */         return Items.IRON_INGOT;
/*     */       }
/* 314 */       if (this == GOLD)
/*     */       {
/* 316 */         return Items.GOLD_INGOT;
/*     */       }
/* 318 */       if (this == IRON)
/*     */       {
/* 320 */         return Items.IRON_INGOT;
/*     */       }
/*     */ 
/*     */       
/* 324 */       return (this == DIAMOND) ? Items.DIAMOND : null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/* 330 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getToughness() {
/* 335 */       return this.toughness;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */