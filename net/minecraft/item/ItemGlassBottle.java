/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityAreaEffectCloud;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.init.PotionTypes;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.potion.PotionUtils;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class ItemGlassBottle
/*    */   extends Item
/*    */ {
/*    */   public ItemGlassBottle() {
/* 28 */     setCreativeTab(CreativeTabs.BREWING);
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 33 */     List<EntityAreaEffectCloud> list = itemStackIn.getEntitiesWithinAABB(EntityAreaEffectCloud.class, worldIn.getEntityBoundingBox().expandXyz(2.0D), new Predicate<EntityAreaEffectCloud>()
/*    */         {
/*    */           public boolean apply(@Nullable EntityAreaEffectCloud p_apply_1_)
/*    */           {
/* 37 */             return (p_apply_1_ != null && p_apply_1_.isEntityAlive() && p_apply_1_.getOwner() instanceof net.minecraft.entity.boss.EntityDragon);
/*    */           }
/*    */         });
/* 40 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/*    */     
/* 42 */     if (!list.isEmpty()) {
/*    */       
/* 44 */       EntityAreaEffectCloud entityareaeffectcloud = list.get(0);
/* 45 */       entityareaeffectcloud.setRadius(entityareaeffectcloud.getRadius() - 0.5F);
/* 46 */       itemStackIn.playSound(null, worldIn.posX, worldIn.posY, worldIn.posZ, SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
/* 47 */       return new ActionResult(EnumActionResult.SUCCESS, turnBottleIntoItem(itemstack, worldIn, new ItemStack(Items.DRAGON_BREATH)));
/*    */     } 
/*    */ 
/*    */     
/* 51 */     RayTraceResult raytraceresult = rayTrace(itemStackIn, worldIn, true);
/*    */     
/* 53 */     if (raytraceresult == null)
/*    */     {
/* 55 */       return new ActionResult(EnumActionResult.PASS, itemstack);
/*    */     }
/*    */ 
/*    */     
/* 59 */     if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
/*    */       
/* 61 */       BlockPos blockpos = raytraceresult.getBlockPos();
/*    */       
/* 63 */       if (!itemStackIn.isBlockModifiable(worldIn, blockpos) || !worldIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack))
/*    */       {
/* 65 */         return new ActionResult(EnumActionResult.PASS, itemstack);
/*    */       }
/*    */       
/* 68 */       if (itemStackIn.getBlockState(blockpos).getMaterial() == Material.WATER) {
/*    */         
/* 70 */         itemStackIn.playSound(worldIn, worldIn.posX, worldIn.posY, worldIn.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
/* 71 */         return new ActionResult(EnumActionResult.SUCCESS, turnBottleIntoItem(itemstack, worldIn, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER)));
/*    */       } 
/*    */     } 
/*    */     
/* 75 */     return new ActionResult(EnumActionResult.PASS, itemstack);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ItemStack turnBottleIntoItem(ItemStack p_185061_1_, EntityPlayer player, ItemStack stack) {
/* 82 */     p_185061_1_.func_190918_g(1);
/* 83 */     player.addStat(StatList.getObjectUseStats(this));
/*    */     
/* 85 */     if (p_185061_1_.func_190926_b())
/*    */     {
/* 87 */       return stack;
/*    */     }
/*    */ 
/*    */     
/* 91 */     if (!player.inventory.addItemStackToInventory(stack))
/*    */     {
/* 93 */       player.dropItem(stack, false);
/*    */     }
/*    */     
/* 96 */     return p_185061_1_;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemGlassBottle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */