/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemHoe
/*     */   extends Item {
/*     */   private final float speed;
/*     */   protected Item.ToolMaterial theToolMaterial;
/*     */   
/*     */   public ItemHoe(Item.ToolMaterial material) {
/*  30 */     this.theToolMaterial = material;
/*  31 */     this.maxStackSize = 1;
/*  32 */     setMaxDamage(material.getMaxUses());
/*  33 */     setCreativeTab(CreativeTabs.TOOLS);
/*  34 */     this.speed = material.getDamageVsEntity() + 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/*  44 */     ItemStack itemstack = stack.getHeldItem(pos);
/*     */     
/*  46 */     if (!stack.canPlayerEdit(worldIn.offset(hand), hand, itemstack))
/*     */     {
/*  48 */       return EnumActionResult.FAIL;
/*     */     }
/*     */ 
/*     */     
/*  52 */     IBlockState iblockstate = playerIn.getBlockState(worldIn);
/*  53 */     Block block = iblockstate.getBlock();
/*     */     
/*  55 */     if (hand != EnumFacing.DOWN && playerIn.getBlockState(worldIn.up()).getMaterial() == Material.AIR) {
/*     */       
/*  57 */       if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
/*     */         
/*  59 */         setBlock(itemstack, stack, playerIn, worldIn, Blocks.FARMLAND.getDefaultState());
/*  60 */         return EnumActionResult.SUCCESS;
/*     */       } 
/*     */       
/*  63 */       if (block == Blocks.DIRT)
/*     */       {
/*  65 */         switch ((BlockDirt.DirtType)iblockstate.getValue((IProperty)BlockDirt.VARIANT)) {
/*     */           
/*     */           case DIRT:
/*  68 */             setBlock(itemstack, stack, playerIn, worldIn, Blocks.FARMLAND.getDefaultState());
/*  69 */             return EnumActionResult.SUCCESS;
/*     */           
/*     */           case null:
/*  72 */             setBlock(itemstack, stack, playerIn, worldIn, Blocks.DIRT.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.DIRT));
/*  73 */             return EnumActionResult.SUCCESS;
/*     */         } 
/*     */       
/*     */       }
/*     */     } 
/*  78 */     return EnumActionResult.PASS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
/*  88 */     stack.damageItem(1, attacker);
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, IBlockState state) {
/*  94 */     worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
/*     */     
/*  96 */     if (!worldIn.isRemote) {
/*     */       
/*  98 */       worldIn.setBlockState(pos, state, 11);
/*  99 */       stack.damageItem(1, (EntityLivingBase)player);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull3D() {
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMaterialName() {
/* 117 */     return this.theToolMaterial.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
/* 122 */     Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
/*     */     
/* 124 */     if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
/*     */       
/* 126 */       multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 0.0D, 0));
/* 127 */       multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (this.speed - 4.0F), 0));
/*     */     } 
/*     */     
/* 130 */     return multimap;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemHoe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */