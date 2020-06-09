/*    */ package net.minecraft.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.projectile.EntityArrow;
/*    */ import net.minecraft.entity.projectile.EntityTippedArrow;
/*    */ import net.minecraft.init.PotionTypes;
/*    */ import net.minecraft.potion.PotionType;
/*    */ import net.minecraft.potion.PotionUtils;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraft.util.text.translation.I18n;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemTippedArrow
/*    */   extends ItemArrow
/*    */ {
/*    */   public ItemStack func_190903_i() {
/* 21 */     return PotionUtils.addPotionToItemStack(super.func_190903_i(), PotionTypes.POISON);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
/* 26 */     EntityTippedArrow entitytippedarrow = new EntityTippedArrow(worldIn, shooter);
/* 27 */     entitytippedarrow.setPotionEffect(stack);
/* 28 */     return (EntityArrow)entitytippedarrow;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/* 36 */     if (func_194125_a(itemIn))
/*    */     {
/* 38 */       for (PotionType potiontype : PotionType.REGISTRY) {
/*    */         
/* 40 */         if (!potiontype.getEffects().isEmpty())
/*    */         {
/* 42 */           tab.add(PotionUtils.addPotionToItemStack(new ItemStack(this), potiontype));
/*    */         }
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
/* 53 */     PotionUtils.addPotionTooltip(stack, tooltip, 0.125F);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getItemStackDisplayName(ItemStack stack) {
/* 58 */     return I18n.translateToLocal(PotionUtils.getPotionFromItem(stack).getNamePrefixed("tipped_arrow.effect."));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemTippedArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */