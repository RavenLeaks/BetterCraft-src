/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.projectile.EntityArrow;
/*    */ import net.minecraft.entity.projectile.EntityTippedArrow;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemArrow
/*    */   extends Item
/*    */ {
/*    */   public ItemArrow() {
/* 13 */     setCreativeTab(CreativeTabs.COMBAT);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
/* 18 */     EntityTippedArrow entitytippedarrow = new EntityTippedArrow(worldIn, shooter);
/* 19 */     entitytippedarrow.setPotionEffect(stack);
/* 20 */     return (EntityArrow)entitytippedarrow;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */