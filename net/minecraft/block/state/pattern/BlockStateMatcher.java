/*    */ package net.minecraft.block.state.pattern;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.BlockStateContainer;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ 
/*    */ public class BlockStateMatcher
/*    */   implements Predicate<IBlockState>
/*    */ {
/* 15 */   public static final Predicate<IBlockState> ANY = new Predicate<IBlockState>()
/*    */     {
/*    */       public boolean apply(@Nullable IBlockState p_apply_1_)
/*    */       {
/* 19 */         return true;
/*    */       }
/*    */     };
/*    */   
/* 23 */   private final Map<IProperty<?>, Predicate<?>> propertyPredicates = Maps.newHashMap();
/*    */   private final BlockStateContainer blockstate;
/*    */   
/*    */   private BlockStateMatcher(BlockStateContainer blockStateIn) {
/* 27 */     this.blockstate = blockStateIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public static BlockStateMatcher forBlock(Block blockIn) {
/* 32 */     return new BlockStateMatcher(blockIn.getBlockState());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean apply(@Nullable IBlockState p_apply_1_) {
/* 37 */     if (p_apply_1_ != null && p_apply_1_.getBlock().equals(this.blockstate.getBlock())) {
/*    */       
/* 39 */       if (this.propertyPredicates.isEmpty())
/*    */       {
/* 41 */         return true;
/*    */       }
/*    */ 
/*    */       
/* 45 */       for (Map.Entry<IProperty<?>, Predicate<?>> entry : this.propertyPredicates.entrySet()) {
/*    */         
/* 47 */         if (!matches(p_apply_1_, (IProperty<Comparable>)entry.getKey(), (Predicate<Comparable>)entry.getValue()))
/*    */         {
/* 49 */           return false;
/*    */         }
/*    */       } 
/*    */       
/* 53 */       return true;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected <T extends Comparable<T>> boolean matches(IBlockState blockState, IProperty<T> property, Predicate<T> predicate) {
/* 64 */     return predicate.apply(blockState.getValue(property));
/*    */   }
/*    */ 
/*    */   
/*    */   public <V extends Comparable<V>> BlockStateMatcher where(IProperty<V> property, Predicate<? extends V> is) {
/* 69 */     if (!this.blockstate.getProperties().contains(property))
/*    */     {
/* 71 */       throw new IllegalArgumentException(this.blockstate + " cannot support property " + property);
/*    */     }
/*    */ 
/*    */     
/* 75 */     this.propertyPredicates.put(property, is);
/* 76 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\state\pattern\BlockStateMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */