/*     */ package net.minecraft.block.state;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ImmutableTable;
/*     */ import com.google.common.collect.Iterables;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.util.ResourceLocation;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BlockStateBase
/*     */   implements IBlockState
/*     */ {
/*     */   public BlockStateBase() {
/*  38 */     this.blockId = -1;
/*  39 */     this.blockStateId = -1;
/*  40 */     this.metadata = -1;
/*  41 */     this.blockLocation = null;
/*     */   } private static final Joiner COMMA_JOINER = Joiner.on(','); private static final Function<Map.Entry<IProperty<?>, Comparable<?>>, String> MAP_ENTRY_TO_STRING = new Function<Map.Entry<IProperty<?>, Comparable<?>>, String>() { @Nullable public String apply(@Nullable Map.Entry<IProperty<?>, Comparable<?>> p_apply_1_) { if (p_apply_1_ == null)
/*     */           return "<NULL>";  IProperty<?> iproperty = p_apply_1_.getKey();
/*     */         return String.valueOf(iproperty.getName()) + "=" + getPropertyName(iproperty, p_apply_1_.getValue()); } private <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> entry) { return property.getName(entry); } }
/*  45 */   ; private int blockId; public int getBlockId() { if (this.blockId < 0)
/*     */     {
/*  47 */       this.blockId = Block.getIdFromBlock(getBlock());
/*     */     }
/*     */     
/*  50 */     return this.blockId; }
/*     */   
/*     */   private int blockStateId; private int metadata; private ResourceLocation blockLocation;
/*     */   
/*     */   public int getBlockStateId() {
/*  55 */     if (this.blockStateId < 0)
/*     */     {
/*  57 */       this.blockStateId = Block.getStateId(this);
/*     */     }
/*     */     
/*  60 */     return this.blockStateId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMetadata() {
/*  65 */     if (this.metadata < 0)
/*     */     {
/*  67 */       this.metadata = getBlock().getMetaFromState(this);
/*     */     }
/*     */     
/*  70 */     return this.metadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getBlockLocation() {
/*  75 */     if (this.blockLocation == null)
/*     */     {
/*  77 */       this.blockLocation = (ResourceLocation)Block.REGISTRY.getNameForObject(getBlock());
/*     */     }
/*     */     
/*  80 */     return this.blockLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableTable<IProperty<?>, Comparable<?>, IBlockState> getPropertyValueTable() {
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> property) {
/*  90 */     return withProperty(property, cyclePropertyValue(property.getAllowedValues(), getValue(property)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static <T> T cyclePropertyValue(Collection<T> values, T currentValue) {
/*  95 */     Iterator<T> iterator = values.iterator();
/*     */     
/*  97 */     while (iterator.hasNext()) {
/*     */       
/*  99 */       if (iterator.next().equals(currentValue)) {
/*     */         
/* 101 */         if (iterator.hasNext())
/*     */         {
/* 103 */           return iterator.next();
/*     */         }
/*     */         
/* 106 */         return values.iterator().next();
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     return iterator.next();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 115 */     StringBuilder stringbuilder = new StringBuilder();
/* 116 */     stringbuilder.append(Block.REGISTRY.getNameForObject(getBlock()));
/*     */     
/* 118 */     if (!getProperties().isEmpty()) {
/*     */       
/* 120 */       stringbuilder.append("[");
/* 121 */       COMMA_JOINER.appendTo(stringbuilder, Iterables.transform((Iterable)getProperties().entrySet(), MAP_ENTRY_TO_STRING));
/* 122 */       stringbuilder.append("]");
/*     */     } 
/*     */     
/* 125 */     return stringbuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\state\BlockStateBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */