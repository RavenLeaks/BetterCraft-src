/*     */ package net.minecraft.entity.ai.attributes;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModifiableAttributeInstance
/*     */   implements IAttributeInstance
/*     */ {
/*     */   private final AbstractAttributeMap attributeMap;
/*     */   private final IAttribute genericAttribute;
/*  19 */   private final Map<Integer, Set<AttributeModifier>> mapByOperation = Maps.newHashMap();
/*  20 */   private final Map<String, Set<AttributeModifier>> mapByName = Maps.newHashMap();
/*  21 */   private final Map<UUID, AttributeModifier> mapByUUID = Maps.newHashMap();
/*     */   
/*     */   private double baseValue;
/*     */   private boolean needsUpdate = true;
/*     */   private double cachedValue;
/*     */   
/*     */   public ModifiableAttributeInstance(AbstractAttributeMap attributeMapIn, IAttribute genericAttributeIn) {
/*  28 */     this.attributeMap = attributeMapIn;
/*  29 */     this.genericAttribute = genericAttributeIn;
/*  30 */     this.baseValue = genericAttributeIn.getDefaultValue();
/*     */     
/*  32 */     for (int i = 0; i < 3; i++)
/*     */     {
/*  34 */       this.mapByOperation.put(Integer.valueOf(i), Sets.newHashSet());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IAttribute getAttribute() {
/*  43 */     return this.genericAttribute;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getBaseValue() {
/*  48 */     return this.baseValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBaseValue(double baseValue) {
/*  53 */     if (baseValue != getBaseValue()) {
/*     */       
/*  55 */       this.baseValue = baseValue;
/*  56 */       flagForUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<AttributeModifier> getModifiersByOperation(int operation) {
/*  62 */     return this.mapByOperation.get(Integer.valueOf(operation));
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<AttributeModifier> getModifiers() {
/*  67 */     Set<AttributeModifier> set = Sets.newHashSet();
/*     */     
/*  69 */     for (int i = 0; i < 3; i++)
/*     */     {
/*  71 */       set.addAll(getModifiersByOperation(i));
/*     */     }
/*     */     
/*  74 */     return set;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AttributeModifier getModifier(UUID uuid) {
/*  84 */     return this.mapByUUID.get(uuid);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasModifier(AttributeModifier modifier) {
/*  89 */     return (this.mapByUUID.get(modifier.getID()) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyModifier(AttributeModifier modifier) {
/*  94 */     if (getModifier(modifier.getID()) != null)
/*     */     {
/*  96 */       throw new IllegalArgumentException("Modifier is already applied on this attribute!");
/*     */     }
/*     */ 
/*     */     
/* 100 */     Set<AttributeModifier> set = this.mapByName.get(modifier.getName());
/*     */     
/* 102 */     if (set == null) {
/*     */       
/* 104 */       set = Sets.newHashSet();
/* 105 */       this.mapByName.put(modifier.getName(), set);
/*     */     } 
/*     */     
/* 108 */     ((Set<AttributeModifier>)this.mapByOperation.get(Integer.valueOf(modifier.getOperation()))).add(modifier);
/* 109 */     set.add(modifier);
/* 110 */     this.mapByUUID.put(modifier.getID(), modifier);
/* 111 */     flagForUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void flagForUpdate() {
/* 117 */     this.needsUpdate = true;
/* 118 */     this.attributeMap.onAttributeModified(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeModifier(AttributeModifier modifier) {
/* 123 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 125 */       Set<AttributeModifier> set = this.mapByOperation.get(Integer.valueOf(i));
/* 126 */       set.remove(modifier);
/*     */     } 
/*     */     
/* 129 */     Set<AttributeModifier> set1 = this.mapByName.get(modifier.getName());
/*     */     
/* 131 */     if (set1 != null) {
/*     */       
/* 133 */       set1.remove(modifier);
/*     */       
/* 135 */       if (set1.isEmpty())
/*     */       {
/* 137 */         this.mapByName.remove(modifier.getName());
/*     */       }
/*     */     } 
/*     */     
/* 141 */     this.mapByUUID.remove(modifier.getID());
/* 142 */     flagForUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeModifier(UUID p_188479_1_) {
/* 147 */     AttributeModifier attributemodifier = getModifier(p_188479_1_);
/*     */     
/* 149 */     if (attributemodifier != null)
/*     */     {
/* 151 */       removeModifier(attributemodifier);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAllModifiers() {
/* 157 */     Collection<AttributeModifier> collection = getModifiers();
/*     */     
/* 159 */     if (collection != null)
/*     */     {
/* 161 */       for (AttributeModifier attributemodifier : Lists.newArrayList(collection))
/*     */       {
/* 163 */         removeModifier(attributemodifier);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double getAttributeValue() {
/* 170 */     if (this.needsUpdate) {
/*     */       
/* 172 */       this.cachedValue = computeValue();
/* 173 */       this.needsUpdate = false;
/*     */     } 
/*     */     
/* 176 */     return this.cachedValue;
/*     */   }
/*     */ 
/*     */   
/*     */   private double computeValue() {
/* 181 */     double d0 = getBaseValue();
/*     */     
/* 183 */     for (AttributeModifier attributemodifier : getAppliedModifiers(0))
/*     */     {
/* 185 */       d0 += attributemodifier.getAmount();
/*     */     }
/*     */     
/* 188 */     double d1 = d0;
/*     */     
/* 190 */     for (AttributeModifier attributemodifier1 : getAppliedModifiers(1))
/*     */     {
/* 192 */       d1 += d0 * attributemodifier1.getAmount();
/*     */     }
/*     */     
/* 195 */     for (AttributeModifier attributemodifier2 : getAppliedModifiers(2))
/*     */     {
/* 197 */       d1 *= 1.0D + attributemodifier2.getAmount();
/*     */     }
/*     */     
/* 200 */     return this.genericAttribute.clampValue(d1);
/*     */   }
/*     */ 
/*     */   
/*     */   private Collection<AttributeModifier> getAppliedModifiers(int operation) {
/* 205 */     Set<AttributeModifier> set = Sets.newHashSet(getModifiersByOperation(operation));
/*     */     
/* 207 */     for (IAttribute iattribute = this.genericAttribute.getParent(); iattribute != null; iattribute = iattribute.getParent()) {
/*     */       
/* 209 */       IAttributeInstance iattributeinstance = this.attributeMap.getAttributeInstance(iattribute);
/*     */       
/* 211 */       if (iattributeinstance != null)
/*     */       {
/* 213 */         set.addAll(iattributeinstance.getModifiersByOperation(operation));
/*     */       }
/*     */     } 
/*     */     
/* 217 */     return set;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\attributes\ModifiableAttributeInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */