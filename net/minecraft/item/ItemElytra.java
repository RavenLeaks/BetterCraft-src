/*    */ package net.minecraft.item;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.BlockDispenser;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemElytra
/*    */   extends Item
/*    */ {
/*    */   public ItemElytra() {
/* 21 */     this.maxStackSize = 1;
/* 22 */     setMaxDamage(432);
/* 23 */     setCreativeTab(CreativeTabs.TRANSPORTATION);
/* 24 */     addPropertyOverride(new ResourceLocation("broken"), new IItemPropertyGetter()
/*    */         {
/*    */           public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
/*    */           {
/* 28 */             return ItemElytra.isBroken(stack) ? 0.0F : 1.0F;
/*    */           }
/*    */         });
/* 31 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isBroken(ItemStack stack) {
/* 36 */     return (stack.getItemDamage() < stack.getMaxDamage() - 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/* 44 */     return (repair.getItem() == Items.LEATHER);
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 49 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/* 50 */     EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemstack);
/* 51 */     ItemStack itemstack1 = worldIn.getItemStackFromSlot(entityequipmentslot);
/*    */     
/* 53 */     if (itemstack1.func_190926_b()) {
/*    */       
/* 55 */       worldIn.setItemStackToSlot(entityequipmentslot, itemstack.copy());
/* 56 */       itemstack.func_190920_e(0);
/* 57 */       return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*    */     } 
/*    */ 
/*    */     
/* 61 */     return new ActionResult(EnumActionResult.FAIL, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemElytra.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */