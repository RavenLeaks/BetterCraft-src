/*     */ package net.minecraft.util;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ 
/*     */ 
/*     */ public class FoodStats
/*     */ {
/*  12 */   private int foodLevel = 20;
/*     */ 
/*     */   
/*  15 */   private float foodSaturationLevel = 5.0F;
/*     */ 
/*     */   
/*     */   private float foodExhaustionLevel;
/*     */   
/*     */   private int foodTimer;
/*     */   
/*  22 */   private int prevFoodLevel = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStats(int foodLevelIn, float foodSaturationModifier) {
/*  29 */     this.foodLevel = Math.min(foodLevelIn + this.foodLevel, 20);
/*  30 */     this.foodSaturationLevel = Math.min(this.foodSaturationLevel + foodLevelIn * foodSaturationModifier * 2.0F, this.foodLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addStats(ItemFood foodItem, ItemStack stack) {
/*  35 */     addStats(foodItem.getHealAmount(stack), foodItem.getSaturationModifier(stack));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate(EntityPlayer player) {
/*  43 */     EnumDifficulty enumdifficulty = player.world.getDifficulty();
/*  44 */     this.prevFoodLevel = this.foodLevel;
/*     */     
/*  46 */     if (this.foodExhaustionLevel > 4.0F) {
/*     */       
/*  48 */       this.foodExhaustionLevel -= 4.0F;
/*     */       
/*  50 */       if (this.foodSaturationLevel > 0.0F) {
/*     */         
/*  52 */         this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
/*     */       }
/*  54 */       else if (enumdifficulty != EnumDifficulty.PEACEFUL) {
/*     */         
/*  56 */         this.foodLevel = Math.max(this.foodLevel - 1, 0);
/*     */       } 
/*     */     } 
/*     */     
/*  60 */     boolean flag = player.world.getGameRules().getBoolean("naturalRegeneration");
/*     */     
/*  62 */     if (flag && this.foodSaturationLevel > 0.0F && player.shouldHeal() && this.foodLevel >= 20) {
/*     */       
/*  64 */       this.foodTimer++;
/*     */       
/*  66 */       if (this.foodTimer >= 10)
/*     */       {
/*  68 */         float f = Math.min(this.foodSaturationLevel, 6.0F);
/*  69 */         player.heal(f / 6.0F);
/*  70 */         addExhaustion(f);
/*  71 */         this.foodTimer = 0;
/*     */       }
/*     */     
/*  74 */     } else if (flag && this.foodLevel >= 18 && player.shouldHeal()) {
/*     */       
/*  76 */       this.foodTimer++;
/*     */       
/*  78 */       if (this.foodTimer >= 80)
/*     */       {
/*  80 */         player.heal(1.0F);
/*  81 */         addExhaustion(6.0F);
/*  82 */         this.foodTimer = 0;
/*     */       }
/*     */     
/*  85 */     } else if (this.foodLevel <= 0) {
/*     */       
/*  87 */       this.foodTimer++;
/*     */       
/*  89 */       if (this.foodTimer >= 80)
/*     */       {
/*  91 */         if (player.getHealth() > 10.0F || enumdifficulty == EnumDifficulty.HARD || (player.getHealth() > 1.0F && enumdifficulty == EnumDifficulty.NORMAL))
/*     */         {
/*  93 */           player.attackEntityFrom(DamageSource.starve, 1.0F);
/*     */         }
/*     */         
/*  96 */         this.foodTimer = 0;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 101 */       this.foodTimer = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readNBT(NBTTagCompound compound) {
/* 110 */     if (compound.hasKey("foodLevel", 99)) {
/*     */       
/* 112 */       this.foodLevel = compound.getInteger("foodLevel");
/* 113 */       this.foodTimer = compound.getInteger("foodTickTimer");
/* 114 */       this.foodSaturationLevel = compound.getFloat("foodSaturationLevel");
/* 115 */       this.foodExhaustionLevel = compound.getFloat("foodExhaustionLevel");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNBT(NBTTagCompound compound) {
/* 124 */     compound.setInteger("foodLevel", this.foodLevel);
/* 125 */     compound.setInteger("foodTickTimer", this.foodTimer);
/* 126 */     compound.setFloat("foodSaturationLevel", this.foodSaturationLevel);
/* 127 */     compound.setFloat("foodExhaustionLevel", this.foodExhaustionLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFoodLevel() {
/* 135 */     return this.foodLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needFood() {
/* 143 */     return (this.foodLevel < 20);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addExhaustion(float exhaustion) {
/* 151 */     this.foodExhaustionLevel = Math.min(this.foodExhaustionLevel + exhaustion, 40.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSaturationLevel() {
/* 159 */     return this.foodSaturationLevel;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFoodLevel(int foodLevelIn) {
/* 164 */     this.foodLevel = foodLevelIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFoodSaturationLevel(float foodSaturationLevelIn) {
/* 169 */     this.foodSaturationLevel = foodSaturationLevelIn;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\FoodStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */