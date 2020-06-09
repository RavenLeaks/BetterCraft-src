/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemFireworkCharge
/*     */   extends Item
/*     */ {
/*     */   public static NBTBase getExplosionTag(ItemStack stack, String key) {
/*  15 */     if (stack.hasTagCompound()) {
/*     */       
/*  17 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Explosion");
/*     */       
/*  19 */       if (nbttagcompound != null)
/*     */       {
/*  21 */         return nbttagcompound.getTag(key);
/*     */       }
/*     */     } 
/*     */     
/*  25 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
/*  33 */     if (stack.hasTagCompound()) {
/*     */       
/*  35 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Explosion");
/*     */       
/*  37 */       if (nbttagcompound != null)
/*     */       {
/*  39 */         addExplosionInfo(nbttagcompound, tooltip);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addExplosionInfo(NBTTagCompound nbt, List<String> tooltip) {
/*  46 */     byte b0 = nbt.getByte("Type");
/*     */     
/*  48 */     if (b0 >= 0 && b0 <= 4) {
/*     */       
/*  50 */       tooltip.add(I18n.translateToLocal("item.fireworksCharge.type." + b0).trim());
/*     */     }
/*     */     else {
/*     */       
/*  54 */       tooltip.add(I18n.translateToLocal("item.fireworksCharge.type").trim());
/*     */     } 
/*     */     
/*  57 */     int[] aint = nbt.getIntArray("Colors");
/*     */     
/*  59 */     if (aint.length > 0) {
/*     */       
/*  61 */       boolean flag = true;
/*  62 */       String s = ""; byte b;
/*     */       int i, arrayOfInt[];
/*  64 */       for (i = (arrayOfInt = aint).length, b = 0; b < i; ) { int k = arrayOfInt[b];
/*     */         
/*  66 */         if (!flag)
/*     */         {
/*  68 */           s = String.valueOf(s) + ", ";
/*     */         }
/*     */         
/*  71 */         flag = false;
/*  72 */         boolean flag1 = false;
/*     */         
/*  74 */         for (int j = 0; j < ItemDye.DYE_COLORS.length; j++) {
/*     */           
/*  76 */           if (k == ItemDye.DYE_COLORS[j]) {
/*     */             
/*  78 */             flag1 = true;
/*  79 */             s = String.valueOf(s) + I18n.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(j).getUnlocalizedName());
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*  84 */         if (!flag1)
/*     */         {
/*  86 */           s = String.valueOf(s) + I18n.translateToLocal("item.fireworksCharge.customColor");
/*     */         }
/*     */         b++; }
/*     */       
/*  90 */       tooltip.add(s);
/*     */     } 
/*     */     
/*  93 */     int[] aint1 = nbt.getIntArray("FadeColors");
/*     */     
/*  95 */     if (aint1.length > 0) {
/*     */       
/*  97 */       boolean flag2 = true;
/*  98 */       String s1 = String.valueOf(I18n.translateToLocal("item.fireworksCharge.fadeTo")) + " "; byte b;
/*     */       int i, arrayOfInt[];
/* 100 */       for (i = (arrayOfInt = aint1).length, b = 0; b < i; ) { int l = arrayOfInt[b];
/*     */         
/* 102 */         if (!flag2)
/*     */         {
/* 104 */           s1 = String.valueOf(s1) + ", ";
/*     */         }
/*     */         
/* 107 */         flag2 = false;
/* 108 */         boolean flag5 = false;
/*     */         
/* 110 */         for (int k = 0; k < 16; k++) {
/*     */           
/* 112 */           if (l == ItemDye.DYE_COLORS[k]) {
/*     */             
/* 114 */             flag5 = true;
/* 115 */             s1 = String.valueOf(s1) + I18n.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(k).getUnlocalizedName());
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 120 */         if (!flag5)
/*     */         {
/* 122 */           s1 = String.valueOf(s1) + I18n.translateToLocal("item.fireworksCharge.customColor");
/*     */         }
/*     */         b++; }
/*     */       
/* 126 */       tooltip.add(s1);
/*     */     } 
/*     */     
/* 129 */     boolean flag3 = nbt.getBoolean("Trail");
/*     */     
/* 131 */     if (flag3)
/*     */     {
/* 133 */       tooltip.add(I18n.translateToLocal("item.fireworksCharge.trail"));
/*     */     }
/*     */     
/* 136 */     boolean flag4 = nbt.getBoolean("Flicker");
/*     */     
/* 138 */     if (flag4)
/*     */     {
/* 140 */       tooltip.add(I18n.translateToLocal("item.fireworksCharge.flicker"));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemFireworkCharge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */