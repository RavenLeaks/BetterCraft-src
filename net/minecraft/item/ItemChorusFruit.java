/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemChorusFruit
/*    */   extends ItemFood
/*    */ {
/*    */   public ItemChorusFruit(int amount, float saturation) {
/* 14 */     super(amount, saturation, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
/* 23 */     ItemStack itemstack = super.onItemUseFinish(stack, worldIn, entityLiving);
/*    */     
/* 25 */     if (!worldIn.isRemote) {
/*    */       
/* 27 */       double d0 = entityLiving.posX;
/* 28 */       double d1 = entityLiving.posY;
/* 29 */       double d2 = entityLiving.posZ;
/*    */       
/* 31 */       for (int i = 0; i < 16; i++) {
/*    */         
/* 33 */         double d3 = entityLiving.posX + (entityLiving.getRNG().nextDouble() - 0.5D) * 16.0D;
/* 34 */         double d4 = MathHelper.clamp(entityLiving.posY + (entityLiving.getRNG().nextInt(16) - 8), 0.0D, (worldIn.getActualHeight() - 1));
/* 35 */         double d5 = entityLiving.posZ + (entityLiving.getRNG().nextDouble() - 0.5D) * 16.0D;
/*    */         
/* 37 */         if (entityLiving.isRiding())
/*    */         {
/* 39 */           entityLiving.dismountRidingEntity();
/*    */         }
/*    */         
/* 42 */         if (entityLiving.attemptTeleport(d3, d4, d5)) {
/*    */           
/* 44 */           worldIn.playSound(null, d0, d1, d2, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
/* 45 */           entityLiving.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 50 */       if (entityLiving instanceof EntityPlayer)
/*    */       {
/* 52 */         ((EntityPlayer)entityLiving).getCooldownTracker().setCooldown(this, 20);
/*    */       }
/*    */     } 
/*    */     
/* 56 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemChorusFruit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */