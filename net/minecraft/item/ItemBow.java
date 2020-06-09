/*     */ package net.minecraft.item;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBow
/*     */   extends Item {
/*     */   public ItemBow() {
/*  24 */     this.maxStackSize = 1;
/*  25 */     setMaxDamage(384);
/*  26 */     setCreativeTab(CreativeTabs.COMBAT);
/*  27 */     addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
/*     */         {
/*     */           public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
/*     */           {
/*  31 */             if (entityIn == null)
/*     */             {
/*  33 */               return 0.0F;
/*     */             }
/*     */ 
/*     */             
/*  37 */             return (entityIn.getActiveItemStack().getItem() != Items.BOW) ? 0.0F : ((stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F);
/*     */           }
/*     */         });
/*     */     
/*  41 */     addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
/*     */         {
/*     */           public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
/*     */           {
/*  45 */             return (entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack) ? 1.0F : 0.0F;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private ItemStack findAmmo(EntityPlayer player) {
/*  52 */     if (isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
/*     */     {
/*  54 */       return player.getHeldItem(EnumHand.OFF_HAND);
/*     */     }
/*  56 */     if (isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
/*     */     {
/*  58 */       return player.getHeldItem(EnumHand.MAIN_HAND);
/*     */     }
/*     */ 
/*     */     
/*  62 */     for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
/*     */       
/*  64 */       ItemStack itemstack = player.inventory.getStackInSlot(i);
/*     */       
/*  66 */       if (isArrow(itemstack))
/*     */       {
/*  68 */         return itemstack;
/*     */       }
/*     */     } 
/*     */     
/*  72 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isArrow(ItemStack stack) {
/*  78 */     return stack.getItem() instanceof ItemArrow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
/*  86 */     if (entityLiving instanceof EntityPlayer) {
/*     */       
/*  88 */       EntityPlayer entityplayer = (EntityPlayer)entityLiving;
/*  89 */       boolean flag = !(!entityplayer.capabilities.isCreativeMode && EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) <= 0);
/*  90 */       ItemStack itemstack = findAmmo(entityplayer);
/*     */       
/*  92 */       if (!itemstack.func_190926_b() || flag) {
/*     */         
/*  94 */         if (itemstack.func_190926_b())
/*     */         {
/*  96 */           itemstack = new ItemStack(Items.ARROW);
/*     */         }
/*     */         
/*  99 */         int i = getMaxItemUseDuration(stack) - timeLeft;
/* 100 */         float f = getArrowVelocity(i);
/*     */         
/* 102 */         if (f >= 0.1D) {
/*     */           
/* 104 */           boolean flag1 = (flag && itemstack.getItem() == Items.ARROW);
/*     */           
/* 106 */           if (!worldIn.isRemote) {
/*     */             
/* 108 */             ItemArrow itemarrow = (itemstack.getItem() instanceof ItemArrow) ? (ItemArrow)itemstack.getItem() : (ItemArrow)Items.ARROW;
/* 109 */             EntityArrow entityarrow = itemarrow.createArrow(worldIn, itemstack, (EntityLivingBase)entityplayer);
/* 110 */             entityarrow.setAim((Entity)entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);
/*     */             
/* 112 */             if (f == 1.0F)
/*     */             {
/* 114 */               entityarrow.setIsCritical(true);
/*     */             }
/*     */             
/* 117 */             int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
/*     */             
/* 119 */             if (j > 0)
/*     */             {
/* 121 */               entityarrow.setDamage(entityarrow.getDamage() + j * 0.5D + 0.5D);
/*     */             }
/*     */             
/* 124 */             int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
/*     */             
/* 126 */             if (k > 0)
/*     */             {
/* 128 */               entityarrow.setKnockbackStrength(k);
/*     */             }
/*     */             
/* 131 */             if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
/*     */             {
/* 133 */               entityarrow.setFire(100);
/*     */             }
/*     */             
/* 136 */             stack.damageItem(1, (EntityLivingBase)entityplayer);
/*     */             
/* 138 */             if (flag1 || (entityplayer.capabilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)))
/*     */             {
/* 140 */               entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
/*     */             }
/*     */             
/* 143 */             worldIn.spawnEntityInWorld((Entity)entityarrow);
/*     */           } 
/*     */           
/* 146 */           worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
/*     */           
/* 148 */           if (!flag1 && !entityplayer.capabilities.isCreativeMode) {
/*     */             
/* 150 */             itemstack.func_190918_g(1);
/*     */             
/* 152 */             if (itemstack.func_190926_b())
/*     */             {
/* 154 */               entityplayer.inventory.deleteStack(itemstack);
/*     */             }
/*     */           } 
/*     */           
/* 158 */           entityplayer.addStat(StatList.getObjectUseStats(this));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getArrowVelocity(int charge) {
/* 169 */     float f = charge / 20.0F;
/* 170 */     f = (f * f + f * 2.0F) / 3.0F;
/*     */     
/* 172 */     if (f > 1.0F)
/*     */     {
/* 174 */       f = 1.0F;
/*     */     }
/*     */     
/* 177 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxItemUseDuration(ItemStack stack) {
/* 185 */     return 72000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction getItemUseAction(ItemStack stack) {
/* 193 */     return EnumAction.BOW;
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 198 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/* 199 */     boolean flag = !findAmmo(worldIn).func_190926_b();
/*     */     
/* 201 */     if (!worldIn.capabilities.isCreativeMode && !flag)
/*     */     {
/* 203 */       return flag ? new ActionResult(EnumActionResult.PASS, itemstack) : new ActionResult(EnumActionResult.FAIL, itemstack);
/*     */     }
/*     */ 
/*     */     
/* 207 */     worldIn.setActiveHand(playerIn);
/* 208 */     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemEnchantability() {
/* 217 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemBow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */