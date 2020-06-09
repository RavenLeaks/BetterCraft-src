/*     */ package net.minecraft.village;
/*     */ 
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ public class MerchantRecipe
/*     */ {
/*     */   private ItemStack itemToBuy;
/*     */   private ItemStack secondItemToBuy;
/*     */   private ItemStack itemToSell;
/*     */   private int toolUses;
/*     */   private int maxTradeUses;
/*     */   private boolean rewardsExp;
/*     */   
/*     */   public MerchantRecipe(NBTTagCompound tagCompound) {
/*  29 */     this.itemToBuy = ItemStack.field_190927_a;
/*  30 */     this.secondItemToBuy = ItemStack.field_190927_a;
/*  31 */     this.itemToSell = ItemStack.field_190927_a;
/*  32 */     readFromTags(tagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public MerchantRecipe(ItemStack buy1, ItemStack buy2, ItemStack sell) {
/*  37 */     this(buy1, buy2, sell, 0, 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public MerchantRecipe(ItemStack buy1, ItemStack buy2, ItemStack sell, int toolUsesIn, int maxTradeUsesIn) {
/*  42 */     this.itemToBuy = ItemStack.field_190927_a;
/*  43 */     this.secondItemToBuy = ItemStack.field_190927_a;
/*  44 */     this.itemToSell = ItemStack.field_190927_a;
/*  45 */     this.itemToBuy = buy1;
/*  46 */     this.secondItemToBuy = buy2;
/*  47 */     this.itemToSell = sell;
/*  48 */     this.toolUses = toolUsesIn;
/*  49 */     this.maxTradeUses = maxTradeUsesIn;
/*  50 */     this.rewardsExp = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public MerchantRecipe(ItemStack buy1, ItemStack sell) {
/*  55 */     this(buy1, ItemStack.field_190927_a, sell);
/*     */   }
/*     */ 
/*     */   
/*     */   public MerchantRecipe(ItemStack buy1, Item sellItem) {
/*  60 */     this(buy1, new ItemStack(sellItem));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemToBuy() {
/*  68 */     return this.itemToBuy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getSecondItemToBuy() {
/*  76 */     return this.secondItemToBuy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSecondItemToBuy() {
/*  84 */     return !this.secondItemToBuy.func_190926_b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemToSell() {
/*  92 */     return this.itemToSell;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getToolUses() {
/*  97 */     return this.toolUses;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTradeUses() {
/* 102 */     return this.maxTradeUses;
/*     */   }
/*     */ 
/*     */   
/*     */   public void incrementToolUses() {
/* 107 */     this.toolUses++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void increaseMaxTradeUses(int increment) {
/* 112 */     this.maxTradeUses += increment;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRecipeDisabled() {
/* 117 */     return (this.toolUses >= this.maxTradeUses);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compensateToolUses() {
/* 126 */     this.toolUses = this.maxTradeUses;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getRewardsExp() {
/* 131 */     return this.rewardsExp;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromTags(NBTTagCompound tagCompound) {
/* 136 */     NBTTagCompound nbttagcompound = tagCompound.getCompoundTag("buy");
/* 137 */     this.itemToBuy = new ItemStack(nbttagcompound);
/* 138 */     NBTTagCompound nbttagcompound1 = tagCompound.getCompoundTag("sell");
/* 139 */     this.itemToSell = new ItemStack(nbttagcompound1);
/*     */     
/* 141 */     if (tagCompound.hasKey("buyB", 10))
/*     */     {
/* 143 */       this.secondItemToBuy = new ItemStack(tagCompound.getCompoundTag("buyB"));
/*     */     }
/*     */     
/* 146 */     if (tagCompound.hasKey("uses", 99))
/*     */     {
/* 148 */       this.toolUses = tagCompound.getInteger("uses");
/*     */     }
/*     */     
/* 151 */     if (tagCompound.hasKey("maxUses", 99)) {
/*     */       
/* 153 */       this.maxTradeUses = tagCompound.getInteger("maxUses");
/*     */     }
/*     */     else {
/*     */       
/* 157 */       this.maxTradeUses = 7;
/*     */     } 
/*     */     
/* 160 */     if (tagCompound.hasKey("rewardExp", 1)) {
/*     */       
/* 162 */       this.rewardsExp = tagCompound.getBoolean("rewardExp");
/*     */     }
/*     */     else {
/*     */       
/* 166 */       this.rewardsExp = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToTags() {
/* 172 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 173 */     nbttagcompound.setTag("buy", (NBTBase)this.itemToBuy.writeToNBT(new NBTTagCompound()));
/* 174 */     nbttagcompound.setTag("sell", (NBTBase)this.itemToSell.writeToNBT(new NBTTagCompound()));
/*     */     
/* 176 */     if (!this.secondItemToBuy.func_190926_b())
/*     */     {
/* 178 */       nbttagcompound.setTag("buyB", (NBTBase)this.secondItemToBuy.writeToNBT(new NBTTagCompound()));
/*     */     }
/*     */     
/* 181 */     nbttagcompound.setInteger("uses", this.toolUses);
/* 182 */     nbttagcompound.setInteger("maxUses", this.maxTradeUses);
/* 183 */     nbttagcompound.setBoolean("rewardExp", this.rewardsExp);
/* 184 */     return nbttagcompound;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\village\MerchantRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */