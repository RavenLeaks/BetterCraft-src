/*     */ package net.minecraft.item;
/*     */ 
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemFood
/*     */   extends Item
/*     */ {
/*     */   public final int itemUseDuration;
/*     */   private final int healAmount;
/*     */   private final float saturationModifier;
/*     */   private final boolean isWolfsFavoriteMeat;
/*     */   private boolean alwaysEdible;
/*     */   private PotionEffect potionId;
/*     */   private float potionEffectProbability;
/*     */   
/*     */   public ItemFood(int amount, float saturation, boolean isWolfFood) {
/*  44 */     this.itemUseDuration = 32;
/*  45 */     this.healAmount = amount;
/*  46 */     this.isWolfsFavoriteMeat = isWolfFood;
/*  47 */     this.saturationModifier = saturation;
/*  48 */     setCreativeTab(CreativeTabs.FOOD);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemFood(int amount, boolean isWolfFood) {
/*  53 */     this(amount, 0.6F, isWolfFood);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
/*  62 */     if (entityLiving instanceof EntityPlayer) {
/*     */       
/*  64 */       EntityPlayer entityplayer = (EntityPlayer)entityLiving;
/*  65 */       entityplayer.getFoodStats().addStats(this, stack);
/*  66 */       worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
/*  67 */       onFoodEaten(stack, worldIn, entityplayer);
/*  68 */       entityplayer.addStat(StatList.getObjectUseStats(this));
/*     */       
/*  70 */       if (entityplayer instanceof EntityPlayerMP)
/*     */       {
/*  72 */         CriteriaTriggers.field_193138_y.func_193148_a((EntityPlayerMP)entityplayer, stack);
/*     */       }
/*     */     } 
/*     */     
/*  76 */     stack.func_190918_g(1);
/*  77 */     return stack;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
/*  82 */     if (!worldIn.isRemote && this.potionId != null && worldIn.rand.nextFloat() < this.potionEffectProbability)
/*     */     {
/*  84 */       player.addPotionEffect(new PotionEffect(this.potionId));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxItemUseDuration(ItemStack stack) {
/*  93 */     return 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction getItemUseAction(ItemStack stack) {
/* 101 */     return EnumAction.EAT;
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 106 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/*     */     
/* 108 */     if (worldIn.canEat(this.alwaysEdible)) {
/*     */       
/* 110 */       worldIn.setActiveHand(playerIn);
/* 111 */       return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*     */     } 
/*     */ 
/*     */     
/* 115 */     return new ActionResult(EnumActionResult.FAIL, itemstack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHealAmount(ItemStack stack) {
/* 121 */     return this.healAmount;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSaturationModifier(ItemStack stack) {
/* 126 */     return this.saturationModifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWolfsFavoriteMeat() {
/* 134 */     return this.isWolfsFavoriteMeat;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemFood setPotionEffect(PotionEffect p_185070_1_, float p_185070_2_) {
/* 139 */     this.potionId = p_185070_1_;
/* 140 */     this.potionEffectProbability = p_185070_2_;
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemFood setAlwaysEdible() {
/* 149 */     this.alwaysEdible = true;
/* 150 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemFood.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */