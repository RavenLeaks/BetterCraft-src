/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemTool
/*     */   extends Item
/*     */ {
/*     */   private final Set<Block> effectiveBlocks;
/*     */   protected float efficiencyOnProperMaterial;
/*     */   protected float damageVsEntity;
/*     */   protected float attackSpeed;
/*     */   protected Item.ToolMaterial toolMaterial;
/*     */   
/*     */   protected ItemTool(float attackDamageIn, float attackSpeedIn, Item.ToolMaterial materialIn, Set<Block> effectiveBlocksIn) {
/*  29 */     this.efficiencyOnProperMaterial = 4.0F;
/*  30 */     this.toolMaterial = materialIn;
/*  31 */     this.effectiveBlocks = effectiveBlocksIn;
/*  32 */     this.maxStackSize = 1;
/*  33 */     setMaxDamage(materialIn.getMaxUses());
/*  34 */     this.efficiencyOnProperMaterial = materialIn.getEfficiencyOnProperMaterial();
/*  35 */     this.damageVsEntity = attackDamageIn + materialIn.getDamageVsEntity();
/*  36 */     this.attackSpeed = attackSpeedIn;
/*  37 */     setCreativeTab(CreativeTabs.TOOLS);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemTool(Item.ToolMaterial materialIn, Set<Block> effectiveBlocksIn) {
/*  42 */     this(0.0F, 0.0F, materialIn, effectiveBlocksIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getStrVsBlock(ItemStack stack, IBlockState state) {
/*  47 */     return this.effectiveBlocks.contains(state.getBlock()) ? this.efficiencyOnProperMaterial : 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
/*  56 */     stack.damageItem(2, attacker);
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
/*  65 */     if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0D)
/*     */     {
/*  67 */       stack.damageItem(1, entityLiving);
/*     */     }
/*     */     
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull3D() {
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemEnchantability() {
/*  86 */     return this.toolMaterial.getEnchantability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getToolMaterialName() {
/*  94 */     return this.toolMaterial.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/* 102 */     return (this.toolMaterial.getRepairItem() == repair.getItem()) ? true : super.getIsRepairable(toRepair, repair);
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
/* 107 */     Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
/*     */     
/* 109 */     if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
/*     */       
/* 111 */       multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", this.damageVsEntity, 0));
/* 112 */       multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", this.attackSpeed, 0));
/*     */     } 
/*     */     
/* 115 */     return multimap;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */