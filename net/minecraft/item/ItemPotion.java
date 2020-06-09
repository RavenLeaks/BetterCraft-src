/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.PotionTypes;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.potion.PotionType;
/*     */ import net.minecraft.potion.PotionUtils;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemPotion
/*     */   extends Item {
/*     */   public ItemPotion() {
/*  28 */     setMaxStackSize(1);
/*  29 */     setCreativeTab(CreativeTabs.BREWING);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_190903_i() {
/*  34 */     return PotionUtils.addPotionToItemStack(super.func_190903_i(), PotionTypes.WATER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
/*  43 */     EntityPlayer entityplayer = (entityLiving instanceof EntityPlayer) ? (EntityPlayer)entityLiving : null;
/*     */     
/*  45 */     if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
/*     */     {
/*  47 */       stack.func_190918_g(1);
/*     */     }
/*     */     
/*  50 */     if (entityplayer instanceof EntityPlayerMP)
/*     */     {
/*  52 */       CriteriaTriggers.field_193138_y.func_193148_a((EntityPlayerMP)entityplayer, stack);
/*     */     }
/*     */     
/*  55 */     if (!worldIn.isRemote)
/*     */     {
/*  57 */       for (PotionEffect potioneffect : PotionUtils.getEffectsFromStack(stack)) {
/*     */         
/*  59 */         if (potioneffect.getPotion().isInstant()) {
/*     */           
/*  61 */           potioneffect.getPotion().affectEntity((Entity)entityplayer, (Entity)entityplayer, entityLiving, potioneffect.getAmplifier(), 1.0D);
/*     */           
/*     */           continue;
/*     */         } 
/*  65 */         entityLiving.addPotionEffect(new PotionEffect(potioneffect));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  70 */     if (entityplayer != null)
/*     */     {
/*  72 */       entityplayer.addStat(StatList.getObjectUseStats(this));
/*     */     }
/*     */     
/*  75 */     if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
/*     */       
/*  77 */       if (stack.func_190926_b())
/*     */       {
/*  79 */         return new ItemStack(Items.GLASS_BOTTLE);
/*     */       }
/*     */       
/*  82 */       if (entityplayer != null)
/*     */       {
/*  84 */         entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
/*     */       }
/*     */     } 
/*     */     
/*  88 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxItemUseDuration(ItemStack stack) {
/*  96 */     return 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction getItemUseAction(ItemStack stack) {
/* 104 */     return EnumAction.DRINK;
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 109 */     worldIn.setActiveHand(playerIn);
/* 110 */     return new ActionResult(EnumActionResult.SUCCESS, worldIn.getHeldItem(playerIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/* 115 */     return I18n.translateToLocal(PotionUtils.getPotionFromItem(stack).getNamePrefixed("potion.effect."));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
/* 123 */     PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasEffect(ItemStack stack) {
/* 128 */     return !(!super.hasEffect(stack) && PotionUtils.getEffectsFromStack(stack).isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/* 136 */     if (func_194125_a(itemIn))
/*     */     {
/* 138 */       for (PotionType potiontype : PotionType.REGISTRY) {
/*     */         
/* 140 */         if (potiontype != PotionTypes.EMPTY)
/*     */         {
/* 142 */           tab.add(PotionUtils.addPotionToItemStack(new ItemStack(this), potiontype));
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemPotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */