/*     */ package net.minecraft.item;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemFishingRod
/*     */   extends Item {
/*     */   public ItemFishingRod() {
/*  22 */     setMaxDamage(64);
/*  23 */     setMaxStackSize(1);
/*  24 */     setCreativeTab(CreativeTabs.TOOLS);
/*  25 */     addPropertyOverride(new ResourceLocation("cast"), new IItemPropertyGetter()
/*     */         {
/*     */           public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
/*     */           {
/*  29 */             if (entityIn == null)
/*     */             {
/*  31 */               return 0.0F;
/*     */             }
/*     */ 
/*     */             
/*  35 */             boolean flag = (entityIn.getHeldItemMainhand() == stack);
/*  36 */             boolean flag1 = (entityIn.getHeldItemOffhand() == stack);
/*     */             
/*  38 */             if (entityIn.getHeldItemMainhand().getItem() instanceof ItemFishingRod)
/*     */             {
/*  40 */               flag1 = false;
/*     */             }
/*     */             
/*  43 */             return ((flag || flag1) && entityIn instanceof EntityPlayer && ((EntityPlayer)entityIn).fishEntity != null) ? 1.0F : 0.0F;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull3D() {
/*  54 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldRotateAroundWhenRendering() {
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/*  68 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/*     */     
/*  70 */     if (worldIn.fishEntity != null) {
/*     */       
/*  72 */       int i = worldIn.fishEntity.handleHookRetraction();
/*  73 */       itemstack.damageItem(i, (EntityLivingBase)worldIn);
/*  74 */       worldIn.swingArm(playerIn);
/*  75 */       itemStackIn.playSound(null, worldIn.posX, worldIn.posY, worldIn.posZ, SoundEvents.field_193780_J, SoundCategory.NEUTRAL, 1.0F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*     */     }
/*     */     else {
/*     */       
/*  79 */       itemStackIn.playSound(null, worldIn.posX, worldIn.posY, worldIn.posZ, SoundEvents.ENTITY_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*     */       
/*  81 */       if (!itemStackIn.isRemote) {
/*     */         
/*  83 */         EntityFishHook entityfishhook = new EntityFishHook(itemStackIn, worldIn);
/*  84 */         int j = EnchantmentHelper.func_191528_c(itemstack);
/*     */         
/*  86 */         if (j > 0)
/*     */         {
/*  88 */           entityfishhook.func_191516_a(j);
/*     */         }
/*     */         
/*  91 */         int k = EnchantmentHelper.func_191529_b(itemstack);
/*     */         
/*  93 */         if (k > 0)
/*     */         {
/*  95 */           entityfishhook.func_191517_b(k);
/*     */         }
/*     */         
/*  98 */         itemStackIn.spawnEntityInWorld((Entity)entityfishhook);
/*     */       } 
/*     */       
/* 101 */       worldIn.swingArm(playerIn);
/* 102 */       worldIn.addStat(StatList.getObjectUseStats(this));
/*     */     } 
/*     */     
/* 105 */     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemEnchantability() {
/* 113 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemFishingRod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */