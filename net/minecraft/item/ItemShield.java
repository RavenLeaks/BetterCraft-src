/*    */ package net.minecraft.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.BlockDispenser;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.tileentity.TileEntityBanner;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.text.translation.I18n;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemShield
/*    */   extends Item
/*    */ {
/*    */   public ItemShield() {
/* 23 */     this.maxStackSize = 1;
/* 24 */     setCreativeTab(CreativeTabs.COMBAT);
/* 25 */     setMaxDamage(336);
/* 26 */     addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter()
/*    */         {
/*    */           public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
/*    */           {
/* 30 */             return (entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack) ? 1.0F : 0.0F;
/*    */           }
/*    */         });
/* 33 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getItemStackDisplayName(ItemStack stack) {
/* 38 */     if (stack.getSubCompound("BlockEntityTag") != null) {
/*    */       
/* 40 */       EnumDyeColor enumdyecolor = TileEntityBanner.func_190616_d(stack);
/* 41 */       return I18n.translateToLocal("item.shield." + enumdyecolor.getUnlocalizedName() + ".name");
/*    */     } 
/*    */ 
/*    */     
/* 45 */     return I18n.translateToLocal("item.shield.name");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
/* 54 */     ItemBanner.appendHoverTextFromTileEntityTag(stack, tooltip);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumAction getItemUseAction(ItemStack stack) {
/* 62 */     return EnumAction.BLOCK;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxItemUseDuration(ItemStack stack) {
/* 70 */     return 72000;
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 75 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/* 76 */     worldIn.setActiveHand(playerIn);
/* 77 */     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/* 85 */     return (repair.getItem() == Item.getItemFromBlock(Blocks.PLANKS)) ? true : super.getIsRepairable(toRepair, repair);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemShield.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */