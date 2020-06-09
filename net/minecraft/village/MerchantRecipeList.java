/*     */ package net.minecraft.village;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ 
/*     */ 
/*     */ public class MerchantRecipeList
/*     */   extends ArrayList<MerchantRecipe>
/*     */ {
/*     */   public MerchantRecipeList() {}
/*     */   
/*     */   public MerchantRecipeList(NBTTagCompound compound) {
/*  20 */     readRecipiesFromTags(compound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MerchantRecipe canRecipeBeUsed(ItemStack p_77203_1_, ItemStack p_77203_2_, int p_77203_3_) {
/*  30 */     if (p_77203_3_ > 0 && p_77203_3_ < size()) {
/*     */       
/*  32 */       MerchantRecipe merchantrecipe1 = get(p_77203_3_);
/*  33 */       return (!areItemStacksExactlyEqual(p_77203_1_, merchantrecipe1.getItemToBuy()) || ((!p_77203_2_.func_190926_b() || merchantrecipe1.hasSecondItemToBuy()) && (!merchantrecipe1.hasSecondItemToBuy() || !areItemStacksExactlyEqual(p_77203_2_, merchantrecipe1.getSecondItemToBuy()))) || p_77203_1_.func_190916_E() < merchantrecipe1.getItemToBuy().func_190916_E() || (merchantrecipe1.hasSecondItemToBuy() && p_77203_2_.func_190916_E() < merchantrecipe1.getSecondItemToBuy().func_190916_E())) ? null : merchantrecipe1;
/*     */     } 
/*     */ 
/*     */     
/*  37 */     for (int i = 0; i < size(); i++) {
/*     */       
/*  39 */       MerchantRecipe merchantrecipe = get(i);
/*     */       
/*  41 */       if (areItemStacksExactlyEqual(p_77203_1_, merchantrecipe.getItemToBuy()) && p_77203_1_.func_190916_E() >= merchantrecipe.getItemToBuy().func_190916_E() && ((!merchantrecipe.hasSecondItemToBuy() && p_77203_2_.func_190926_b()) || (merchantrecipe.hasSecondItemToBuy() && areItemStacksExactlyEqual(p_77203_2_, merchantrecipe.getSecondItemToBuy()) && p_77203_2_.func_190916_E() >= merchantrecipe.getSecondItemToBuy().func_190916_E())))
/*     */       {
/*  43 */         return merchantrecipe;
/*     */       }
/*     */     } 
/*     */     
/*  47 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean areItemStacksExactlyEqual(ItemStack stack1, ItemStack stack2) {
/*  53 */     return (ItemStack.areItemsEqual(stack1, stack2) && (!stack2.hasTagCompound() || (stack1.hasTagCompound() && NBTUtil.areNBTEquals((NBTBase)stack2.getTagCompound(), (NBTBase)stack1.getTagCompound(), false))));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToBuf(PacketBuffer buffer) {
/*  58 */     buffer.writeByte((byte)(size() & 0xFF));
/*     */     
/*  60 */     for (int i = 0; i < size(); i++) {
/*     */       
/*  62 */       MerchantRecipe merchantrecipe = get(i);
/*  63 */       buffer.writeItemStackToBuffer(merchantrecipe.getItemToBuy());
/*  64 */       buffer.writeItemStackToBuffer(merchantrecipe.getItemToSell());
/*  65 */       ItemStack itemstack = merchantrecipe.getSecondItemToBuy();
/*  66 */       buffer.writeBoolean(!itemstack.func_190926_b());
/*     */       
/*  68 */       if (!itemstack.func_190926_b())
/*     */       {
/*  70 */         buffer.writeItemStackToBuffer(itemstack);
/*     */       }
/*     */       
/*  73 */       buffer.writeBoolean(merchantrecipe.isRecipeDisabled());
/*  74 */       buffer.writeInt(merchantrecipe.getToolUses());
/*  75 */       buffer.writeInt(merchantrecipe.getMaxTradeUses());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static MerchantRecipeList readFromBuf(PacketBuffer buffer) throws IOException {
/*  81 */     MerchantRecipeList merchantrecipelist = new MerchantRecipeList();
/*  82 */     int i = buffer.readByte() & 0xFF;
/*     */     
/*  84 */     for (int j = 0; j < i; j++) {
/*     */       
/*  86 */       ItemStack itemstack = buffer.readItemStackFromBuffer();
/*  87 */       ItemStack itemstack1 = buffer.readItemStackFromBuffer();
/*  88 */       ItemStack itemstack2 = ItemStack.field_190927_a;
/*     */       
/*  90 */       if (buffer.readBoolean())
/*     */       {
/*  92 */         itemstack2 = buffer.readItemStackFromBuffer();
/*     */       }
/*     */       
/*  95 */       boolean flag = buffer.readBoolean();
/*  96 */       int k = buffer.readInt();
/*  97 */       int l = buffer.readInt();
/*  98 */       MerchantRecipe merchantrecipe = new MerchantRecipe(itemstack, itemstack2, itemstack1, k, l);
/*     */       
/* 100 */       if (flag)
/*     */       {
/* 102 */         merchantrecipe.compensateToolUses();
/*     */       }
/*     */       
/* 105 */       merchantrecipelist.add(merchantrecipe);
/*     */     } 
/*     */     
/* 108 */     return merchantrecipelist;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readRecipiesFromTags(NBTTagCompound compound) {
/* 113 */     NBTTagList nbttaglist = compound.getTagList("Recipes", 10);
/*     */     
/* 115 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 117 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 118 */       add(new MerchantRecipe(nbttagcompound));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getRecipiesAsTags() {
/* 124 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 125 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 127 */     for (int i = 0; i < size(); i++) {
/*     */       
/* 129 */       MerchantRecipe merchantrecipe = get(i);
/* 130 */       nbttaglist.appendTag((NBTBase)merchantrecipe.writeToTags());
/*     */     } 
/*     */     
/* 133 */     nbttagcompound.setTag("Recipes", (NBTBase)nbttaglist);
/* 134 */     return nbttagcompound;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\village\MerchantRecipeList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */