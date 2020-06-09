/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SharedMonsterAttributes {
/*  18 */   private static final Logger LOGGER = LogManager.getLogger();
/*  19 */   public static final IAttribute MAX_HEALTH = (IAttribute)(new RangedAttribute(null, "generic.maxHealth", 20.0D, 0.0D, 1024.0D)).setDescription("Max Health").setShouldWatch(true);
/*  20 */   public static final IAttribute FOLLOW_RANGE = (IAttribute)(new RangedAttribute(null, "generic.followRange", 32.0D, 0.0D, 2048.0D)).setDescription("Follow Range");
/*  21 */   public static final IAttribute KNOCKBACK_RESISTANCE = (IAttribute)(new RangedAttribute(null, "generic.knockbackResistance", 0.0D, 0.0D, 1.0D)).setDescription("Knockback Resistance");
/*  22 */   public static final IAttribute MOVEMENT_SPEED = (IAttribute)(new RangedAttribute(null, "generic.movementSpeed", 0.699999988079071D, 0.0D, 1024.0D)).setDescription("Movement Speed").setShouldWatch(true);
/*  23 */   public static final IAttribute field_193334_e = (IAttribute)(new RangedAttribute(null, "generic.flyingSpeed", 0.4000000059604645D, 0.0D, 1024.0D)).setDescription("Flying Speed").setShouldWatch(true);
/*  24 */   public static final IAttribute ATTACK_DAMAGE = (IAttribute)new RangedAttribute(null, "generic.attackDamage", 2.0D, 0.0D, 2048.0D);
/*  25 */   public static final IAttribute ATTACK_SPEED = (IAttribute)(new RangedAttribute(null, "generic.attackSpeed", 4.0D, 0.0D, 1024.0D)).setShouldWatch(true);
/*  26 */   public static final IAttribute ARMOR = (IAttribute)(new RangedAttribute(null, "generic.armor", 0.0D, 0.0D, 30.0D)).setShouldWatch(true);
/*  27 */   public static final IAttribute ARMOR_TOUGHNESS = (IAttribute)(new RangedAttribute(null, "generic.armorToughness", 0.0D, 0.0D, 20.0D)).setShouldWatch(true);
/*  28 */   public static final IAttribute LUCK = (IAttribute)(new RangedAttribute(null, "generic.luck", 0.0D, -1024.0D, 1024.0D)).setShouldWatch(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagList writeBaseAttributeMapToNBT(AbstractAttributeMap map) {
/*  35 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  37 */     for (IAttributeInstance iattributeinstance : map.getAllAttributes())
/*     */     {
/*  39 */       nbttaglist.appendTag((NBTBase)writeAttributeInstanceToNBT(iattributeinstance));
/*     */     }
/*     */     
/*  42 */     return nbttaglist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static NBTTagCompound writeAttributeInstanceToNBT(IAttributeInstance instance) {
/*  50 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  51 */     IAttribute iattribute = instance.getAttribute();
/*  52 */     nbttagcompound.setString("Name", iattribute.getAttributeUnlocalizedName());
/*  53 */     nbttagcompound.setDouble("Base", instance.getBaseValue());
/*  54 */     Collection<AttributeModifier> collection = instance.getModifiers();
/*     */     
/*  56 */     if (collection != null && !collection.isEmpty()) {
/*     */       
/*  58 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/*  60 */       for (AttributeModifier attributemodifier : collection) {
/*     */         
/*  62 */         if (attributemodifier.isSaved())
/*     */         {
/*  64 */           nbttaglist.appendTag((NBTBase)writeAttributeModifierToNBT(attributemodifier));
/*     */         }
/*     */       } 
/*     */       
/*  68 */       nbttagcompound.setTag("Modifiers", (NBTBase)nbttaglist);
/*     */     } 
/*     */     
/*  71 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound writeAttributeModifierToNBT(AttributeModifier modifier) {
/*  79 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  80 */     nbttagcompound.setString("Name", modifier.getName());
/*  81 */     nbttagcompound.setDouble("Amount", modifier.getAmount());
/*  82 */     nbttagcompound.setInteger("Operation", modifier.getOperation());
/*  83 */     nbttagcompound.setUniqueId("UUID", modifier.getID());
/*  84 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setAttributeModifiers(AbstractAttributeMap map, NBTTagList list) {
/*  89 */     for (int i = 0; i < list.tagCount(); i++) {
/*     */       
/*  91 */       NBTTagCompound nbttagcompound = list.getCompoundTagAt(i);
/*  92 */       IAttributeInstance iattributeinstance = map.getAttributeInstanceByName(nbttagcompound.getString("Name"));
/*     */       
/*  94 */       if (iattributeinstance == null) {
/*     */         
/*  96 */         LOGGER.warn("Ignoring unknown attribute '{}'", nbttagcompound.getString("Name"));
/*     */       }
/*     */       else {
/*     */         
/* 100 */         applyModifiersToAttributeInstance(iattributeinstance, nbttagcompound);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void applyModifiersToAttributeInstance(IAttributeInstance instance, NBTTagCompound compound) {
/* 107 */     instance.setBaseValue(compound.getDouble("Base"));
/*     */     
/* 109 */     if (compound.hasKey("Modifiers", 9)) {
/*     */       
/* 111 */       NBTTagList nbttaglist = compound.getTagList("Modifiers", 10);
/*     */       
/* 113 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/* 115 */         AttributeModifier attributemodifier = readAttributeModifierFromNBT(nbttaglist.getCompoundTagAt(i));
/*     */         
/* 117 */         if (attributemodifier != null) {
/*     */           
/* 119 */           AttributeModifier attributemodifier1 = instance.getModifier(attributemodifier.getID());
/*     */           
/* 121 */           if (attributemodifier1 != null)
/*     */           {
/* 123 */             instance.removeModifier(attributemodifier1);
/*     */           }
/*     */           
/* 126 */           instance.applyModifier(attributemodifier);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static AttributeModifier readAttributeModifierFromNBT(NBTTagCompound compound) {
/* 139 */     UUID uuid = compound.getUniqueId("UUID");
/*     */ 
/*     */     
/*     */     try {
/* 143 */       return new AttributeModifier(uuid, compound.getString("Name"), compound.getDouble("Amount"), compound.getInteger("Operation"));
/*     */     }
/* 145 */     catch (Exception exception) {
/*     */       
/* 147 */       LOGGER.warn("Unable to create attribute: {}", exception.getMessage());
/* 148 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\SharedMonsterAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */