/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemOverrideList
/*    */ {
/* 13 */   public static final ItemOverrideList NONE = new ItemOverrideList();
/* 14 */   private final List<ItemOverride> overrides = Lists.newArrayList();
/*    */ 
/*    */ 
/*    */   
/*    */   private ItemOverrideList() {}
/*    */ 
/*    */   
/*    */   public ItemOverrideList(List<ItemOverride> overridesIn) {
/* 22 */     for (int i = overridesIn.size() - 1; i >= 0; i--)
/*    */     {
/* 24 */       this.overrides.add(overridesIn.get(i));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ResourceLocation applyOverride(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
/* 31 */     if (!this.overrides.isEmpty())
/*    */     {
/* 33 */       for (ItemOverride itemoverride : this.overrides) {
/*    */         
/* 35 */         if (itemoverride.matchesItemStack(stack, worldIn, entityIn))
/*    */         {
/* 37 */           return itemoverride.getLocation();
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 42 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\ItemOverrideList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */