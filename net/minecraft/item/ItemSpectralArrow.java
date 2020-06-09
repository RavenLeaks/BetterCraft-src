/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.projectile.EntityArrow;
/*    */ import net.minecraft.entity.projectile.EntitySpectralArrow;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemSpectralArrow
/*    */   extends ItemArrow
/*    */ {
/*    */   public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
/* 12 */     return (EntityArrow)new EntitySpectralArrow(worldIn, shooter);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemSpectralArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */