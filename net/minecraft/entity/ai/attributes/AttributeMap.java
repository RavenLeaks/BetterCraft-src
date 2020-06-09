/*    */ package net.minecraft.entity.ai.attributes;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.LowerStringMap;
/*    */ 
/*    */ public class AttributeMap
/*    */   extends AbstractAttributeMap {
/* 11 */   private final Set<IAttributeInstance> attributeInstanceSet = Sets.newHashSet();
/* 12 */   protected final Map<String, IAttributeInstance> descriptionToAttributeInstanceMap = (Map<String, IAttributeInstance>)new LowerStringMap();
/*    */ 
/*    */   
/*    */   public ModifiableAttributeInstance getAttributeInstance(IAttribute attribute) {
/* 16 */     return (ModifiableAttributeInstance)super.getAttributeInstance(attribute);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModifiableAttributeInstance getAttributeInstanceByName(String attributeName) {
/* 21 */     IAttributeInstance iattributeinstance = super.getAttributeInstanceByName(attributeName);
/*    */     
/* 23 */     if (iattributeinstance == null)
/*    */     {
/* 25 */       iattributeinstance = this.descriptionToAttributeInstanceMap.get(attributeName);
/*    */     }
/*    */     
/* 28 */     return (ModifiableAttributeInstance)iattributeinstance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IAttributeInstance registerAttribute(IAttribute attribute) {
/* 36 */     IAttributeInstance iattributeinstance = super.registerAttribute(attribute);
/*    */     
/* 38 */     if (attribute instanceof RangedAttribute && ((RangedAttribute)attribute).getDescription() != null)
/*    */     {
/* 40 */       this.descriptionToAttributeInstanceMap.put(((RangedAttribute)attribute).getDescription(), iattributeinstance);
/*    */     }
/*    */     
/* 43 */     return iattributeinstance;
/*    */   }
/*    */ 
/*    */   
/*    */   protected IAttributeInstance createInstance(IAttribute attribute) {
/* 48 */     return new ModifiableAttributeInstance(this, attribute);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onAttributeModified(IAttributeInstance instance) {
/* 53 */     if (instance.getAttribute().getShouldWatch())
/*    */     {
/* 55 */       this.attributeInstanceSet.add(instance);
/*    */     }
/*    */     
/* 58 */     for (IAttribute iattribute : this.descendantsByParent.get(instance.getAttribute())) {
/*    */       
/* 60 */       ModifiableAttributeInstance modifiableattributeinstance = getAttributeInstance(iattribute);
/*    */       
/* 62 */       if (modifiableattributeinstance != null)
/*    */       {
/* 64 */         modifiableattributeinstance.flagForUpdate();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<IAttributeInstance> getAttributeInstanceSet() {
/* 71 */     return this.attributeInstanceSet;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<IAttributeInstance> getWatchedAttributes() {
/* 76 */     Set<IAttributeInstance> set = Sets.newHashSet();
/*    */     
/* 78 */     for (IAttributeInstance iattributeinstance : getAllAttributes()) {
/*    */       
/* 80 */       if (iattributeinstance.getAttribute().getShouldWatch())
/*    */       {
/* 82 */         set.add(iattributeinstance);
/*    */       }
/*    */     } 
/*    */     
/* 86 */     return set;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\attributes\AttributeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */