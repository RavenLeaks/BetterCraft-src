/*     */ package net.minecraft.potion;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*     */ import net.minecraft.init.PotionTypes;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import optifine.Config;
/*     */ import optifine.CustomColors;
/*     */ 
/*     */ 
/*     */ public class PotionUtils
/*     */ {
/*     */   public static List<PotionEffect> getEffectsFromStack(ItemStack stack) {
/*  27 */     return getEffectsFromTag(stack.getTagCompound());
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<PotionEffect> mergeEffects(PotionType potionIn, Collection<PotionEffect> effects) {
/*  32 */     List<PotionEffect> list = Lists.newArrayList();
/*  33 */     list.addAll(potionIn.getEffects());
/*  34 */     list.addAll(effects);
/*  35 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<PotionEffect> getEffectsFromTag(@Nullable NBTTagCompound tag) {
/*  40 */     List<PotionEffect> list = Lists.newArrayList();
/*  41 */     list.addAll(getPotionTypeFromNBT(tag).getEffects());
/*  42 */     addCustomPotionEffectToList(tag, list);
/*  43 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<PotionEffect> getFullEffectsFromItem(ItemStack itemIn) {
/*  48 */     return getFullEffectsFromTag(itemIn.getTagCompound());
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<PotionEffect> getFullEffectsFromTag(@Nullable NBTTagCompound tag) {
/*  53 */     List<PotionEffect> list = Lists.newArrayList();
/*  54 */     addCustomPotionEffectToList(tag, list);
/*  55 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addCustomPotionEffectToList(@Nullable NBTTagCompound tag, List<PotionEffect> effectList) {
/*  60 */     if (tag != null && tag.hasKey("CustomPotionEffects", 9)) {
/*     */       
/*  62 */       NBTTagList nbttaglist = tag.getTagList("CustomPotionEffects", 10);
/*     */       
/*  64 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/*  66 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  67 */         PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);
/*     */         
/*  69 */         if (potioneffect != null)
/*     */         {
/*  71 */           effectList.add(potioneffect);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_190932_c(ItemStack p_190932_0_) {
/*  79 */     NBTTagCompound nbttagcompound = p_190932_0_.getTagCompound();
/*     */     
/*  81 */     if (nbttagcompound != null && nbttagcompound.hasKey("CustomPotionColor", 99))
/*     */     {
/*  83 */       return nbttagcompound.getInteger("CustomPotionColor");
/*     */     }
/*     */ 
/*     */     
/*  87 */     return (getPotionFromItem(p_190932_0_) == PotionTypes.EMPTY) ? 16253176 : getPotionColorFromEffectList(getEffectsFromStack(p_190932_0_));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPotionColor(PotionType potionIn) {
/*  93 */     return (potionIn == PotionTypes.EMPTY) ? 16253176 : getPotionColorFromEffectList(potionIn.getEffects());
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getPotionColorFromEffectList(Collection<PotionEffect> effects) {
/*  98 */     int i = 3694022;
/*     */     
/* 100 */     if (effects.isEmpty())
/*     */     {
/* 102 */       return Config.isCustomColors() ? CustomColors.getPotionColor(null, i) : 3694022;
/*     */     }
/*     */ 
/*     */     
/* 106 */     float f = 0.0F;
/* 107 */     float f1 = 0.0F;
/* 108 */     float f2 = 0.0F;
/* 109 */     int j = 0;
/*     */     
/* 111 */     for (PotionEffect potioneffect : effects) {
/*     */       
/* 113 */       if (potioneffect.doesShowParticles()) {
/*     */         
/* 115 */         int k = potioneffect.getPotion().getLiquidColor();
/*     */         
/* 117 */         if (Config.isCustomColors())
/*     */         {
/* 119 */           k = CustomColors.getPotionColor(potioneffect.getPotion(), k);
/*     */         }
/*     */         
/* 122 */         int l = potioneffect.getAmplifier() + 1;
/* 123 */         f += (l * (k >> 16 & 0xFF)) / 255.0F;
/* 124 */         f1 += (l * (k >> 8 & 0xFF)) / 255.0F;
/* 125 */         f2 += (l * (k >> 0 & 0xFF)) / 255.0F;
/* 126 */         j += l;
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     if (j == 0)
/*     */     {
/* 132 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 136 */     f = f / j * 255.0F;
/* 137 */     f1 = f1 / j * 255.0F;
/* 138 */     f2 = f2 / j * 255.0F;
/* 139 */     return (int)f << 16 | (int)f1 << 8 | (int)f2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PotionType getPotionFromItem(ItemStack itemIn) {
/* 146 */     return getPotionTypeFromNBT(itemIn.getTagCompound());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PotionType getPotionTypeFromNBT(@Nullable NBTTagCompound tag) {
/* 154 */     return (tag == null) ? PotionTypes.EMPTY : PotionType.getPotionTypeForName(tag.getString("Potion"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack addPotionToItemStack(ItemStack itemIn, PotionType potionIn) {
/* 159 */     ResourceLocation resourcelocation = (ResourceLocation)PotionType.REGISTRY.getNameForObject(potionIn);
/*     */     
/* 161 */     if (potionIn == PotionTypes.EMPTY) {
/*     */       
/* 163 */       if (itemIn.hasTagCompound())
/*     */       {
/* 165 */         NBTTagCompound nbttagcompound = itemIn.getTagCompound();
/* 166 */         nbttagcompound.removeTag("Potion");
/*     */         
/* 168 */         if (nbttagcompound.hasNoTags())
/*     */         {
/* 170 */           itemIn.setTagCompound(null);
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 176 */       NBTTagCompound nbttagcompound1 = itemIn.hasTagCompound() ? itemIn.getTagCompound() : new NBTTagCompound();
/* 177 */       nbttagcompound1.setString("Potion", resourcelocation.toString());
/* 178 */       itemIn.setTagCompound(nbttagcompound1);
/*     */     } 
/*     */     
/* 181 */     return itemIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack appendEffects(ItemStack itemIn, Collection<PotionEffect> effects) {
/* 186 */     if (effects.isEmpty())
/*     */     {
/* 188 */       return itemIn;
/*     */     }
/*     */ 
/*     */     
/* 192 */     NBTTagCompound nbttagcompound = (NBTTagCompound)MoreObjects.firstNonNull(itemIn.getTagCompound(), new NBTTagCompound());
/* 193 */     NBTTagList nbttaglist = nbttagcompound.getTagList("CustomPotionEffects", 9);
/*     */     
/* 195 */     for (PotionEffect potioneffect : effects)
/*     */     {
/* 197 */       nbttaglist.appendTag((NBTBase)potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
/*     */     }
/*     */     
/* 200 */     nbttagcompound.setTag("CustomPotionEffects", (NBTBase)nbttaglist);
/* 201 */     itemIn.setTagCompound(nbttagcompound);
/* 202 */     return itemIn;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addPotionTooltip(ItemStack itemIn, List<String> lores, float durationFactor) {
/* 208 */     List<PotionEffect> list = getEffectsFromStack(itemIn);
/* 209 */     List<Tuple<String, AttributeModifier>> list1 = Lists.newArrayList();
/*     */     
/* 211 */     if (list.isEmpty()) {
/*     */       
/* 213 */       String s = I18n.translateToLocal("effect.none").trim();
/* 214 */       lores.add(TextFormatting.GRAY + s);
/*     */     }
/*     */     else {
/*     */       
/* 218 */       for (PotionEffect potioneffect : list) {
/*     */         
/* 220 */         String s1 = I18n.translateToLocal(potioneffect.getEffectName()).trim();
/* 221 */         Potion potion = potioneffect.getPotion();
/* 222 */         Map<IAttribute, AttributeModifier> map = potion.getAttributeModifierMap();
/*     */         
/* 224 */         if (!map.isEmpty())
/*     */         {
/* 226 */           for (Map.Entry<IAttribute, AttributeModifier> entry : map.entrySet()) {
/*     */             
/* 228 */             AttributeModifier attributemodifier = entry.getValue();
/* 229 */             AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), potion.getAttributeModifierAmount(potioneffect.getAmplifier(), attributemodifier), attributemodifier.getOperation());
/* 230 */             list1.add(new Tuple(((IAttribute)entry.getKey()).getAttributeUnlocalizedName(), attributemodifier1));
/*     */           } 
/*     */         }
/*     */         
/* 234 */         if (potioneffect.getAmplifier() > 0)
/*     */         {
/* 236 */           s1 = String.valueOf(s1) + " " + I18n.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
/*     */         }
/*     */         
/* 239 */         if (potioneffect.getDuration() > 20)
/*     */         {
/* 241 */           s1 = String.valueOf(s1) + " (" + Potion.getPotionDurationString(potioneffect, durationFactor) + ")";
/*     */         }
/*     */         
/* 244 */         if (potion.isBadEffect()) {
/*     */           
/* 246 */           lores.add(TextFormatting.RED + s1);
/*     */           
/*     */           continue;
/*     */         } 
/* 250 */         lores.add(TextFormatting.BLUE + s1);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 255 */     if (!list1.isEmpty()) {
/*     */       
/* 257 */       lores.add("");
/* 258 */       lores.add(TextFormatting.DARK_PURPLE + I18n.translateToLocal("potion.whenDrank"));
/*     */       
/* 260 */       for (Tuple<String, AttributeModifier> tuple : list1) {
/*     */         double d1;
/* 262 */         AttributeModifier attributemodifier2 = (AttributeModifier)tuple.getSecond();
/* 263 */         double d0 = attributemodifier2.getAmount();
/*     */ 
/*     */         
/* 266 */         if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2) {
/*     */           
/* 268 */           d1 = attributemodifier2.getAmount();
/*     */         }
/*     */         else {
/*     */           
/* 272 */           d1 = attributemodifier2.getAmount() * 100.0D;
/*     */         } 
/*     */         
/* 275 */         if (d0 > 0.0D) {
/*     */           
/* 277 */           lores.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier2.getOperation(), new Object[] { ItemStack.DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + (String)tuple.getFirst()) })); continue;
/*     */         } 
/* 279 */         if (d0 < 0.0D) {
/*     */           
/* 281 */           d1 *= -1.0D;
/* 282 */           lores.add(TextFormatting.RED + I18n.translateToLocalFormatted("attribute.modifier.take." + attributemodifier2.getOperation(), new Object[] { ItemStack.DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + (String)tuple.getFirst()) }));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\potion\PotionUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */