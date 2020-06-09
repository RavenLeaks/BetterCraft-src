/*    */ package net.minecraft.entity.ai.attributes;
/*    */ 
/*    */ import com.google.common.collect.HashMultimap;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Multimap;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.LowerStringMap;
/*    */ 
/*    */ 
/*    */ public abstract class AbstractAttributeMap
/*    */ {
/* 14 */   protected final Map<IAttribute, IAttributeInstance> attributes = Maps.newHashMap();
/* 15 */   protected final Map<String, IAttributeInstance> attributesByName = (Map<String, IAttributeInstance>)new LowerStringMap();
/* 16 */   protected final Multimap<IAttribute, IAttribute> descendantsByParent = (Multimap<IAttribute, IAttribute>)HashMultimap.create();
/*    */ 
/*    */   
/*    */   public IAttributeInstance getAttributeInstance(IAttribute attribute) {
/* 20 */     return this.attributes.get(attribute);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public IAttributeInstance getAttributeInstanceByName(String attributeName) {
/* 26 */     return this.attributesByName.get(attributeName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IAttributeInstance registerAttribute(IAttribute attribute) {
/* 34 */     if (this.attributesByName.containsKey(attribute.getAttributeUnlocalizedName()))
/*    */     {
/* 36 */       throw new IllegalArgumentException("Attribute is already registered!");
/*    */     }
/*    */ 
/*    */     
/* 40 */     IAttributeInstance iattributeinstance = createInstance(attribute);
/* 41 */     this.attributesByName.put(attribute.getAttributeUnlocalizedName(), iattributeinstance);
/* 42 */     this.attributes.put(attribute, iattributeinstance);
/*    */     
/* 44 */     for (IAttribute iattribute = attribute.getParent(); iattribute != null; iattribute = iattribute.getParent())
/*    */     {
/* 46 */       this.descendantsByParent.put(iattribute, attribute);
/*    */     }
/*    */     
/* 49 */     return iattributeinstance;
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract IAttributeInstance createInstance(IAttribute paramIAttribute);
/*    */ 
/*    */   
/*    */   public Collection<IAttributeInstance> getAllAttributes() {
/* 57 */     return this.attributesByName.values();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onAttributeModified(IAttributeInstance instance) {}
/*    */ 
/*    */   
/*    */   public void removeAttributeModifiers(Multimap<String, AttributeModifier> modifiers) {
/* 66 */     for (Map.Entry<String, AttributeModifier> entry : (Iterable<Map.Entry<String, AttributeModifier>>)modifiers.entries()) {
/*    */       
/* 68 */       IAttributeInstance iattributeinstance = getAttributeInstanceByName(entry.getKey());
/*    */       
/* 70 */       if (iattributeinstance != null)
/*    */       {
/* 72 */         iattributeinstance.removeModifier(entry.getValue());
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyAttributeModifiers(Multimap<String, AttributeModifier> modifiers) {
/* 79 */     for (Map.Entry<String, AttributeModifier> entry : (Iterable<Map.Entry<String, AttributeModifier>>)modifiers.entries()) {
/*    */       
/* 81 */       IAttributeInstance iattributeinstance = getAttributeInstanceByName(entry.getKey());
/*    */       
/* 83 */       if (iattributeinstance != null) {
/*    */         
/* 85 */         iattributeinstance.removeModifier(entry.getValue());
/* 86 */         iattributeinstance.applyModifier(entry.getValue());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\attributes\AbstractAttributeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */