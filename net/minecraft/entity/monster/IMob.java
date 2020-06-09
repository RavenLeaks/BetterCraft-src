/*    */ package net.minecraft.entity.monster;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.IAnimals;
/*    */ 
/*    */ public interface IMob
/*    */   extends IAnimals {
/* 10 */   public static final Predicate<Entity> MOB_SELECTOR = new Predicate<Entity>()
/*    */     {
/*    */       public boolean apply(@Nullable Entity p_apply_1_)
/*    */       {
/* 14 */         return p_apply_1_ instanceof IMob;
/*    */       }
/*    */     };
/* 17 */   public static final Predicate<Entity> VISIBLE_MOB_SELECTOR = new Predicate<Entity>()
/*    */     {
/*    */       public boolean apply(@Nullable Entity p_apply_1_)
/*    */       {
/* 21 */         return (p_apply_1_ instanceof IMob && !p_apply_1_.isInvisible());
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\IMob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */