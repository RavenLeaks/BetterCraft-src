/*    */ package net.minecraft.util;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ import net.minecraft.util.text.translation.I18n;
/*    */ 
/*    */ public class EntityDamageSourceIndirect
/*    */   extends EntityDamageSource
/*    */ {
/*    */   private final Entity indirectEntity;
/*    */   
/*    */   public EntityDamageSourceIndirect(String damageTypeIn, Entity source, @Nullable Entity indirectEntityIn) {
/* 17 */     super(damageTypeIn, source);
/* 18 */     this.indirectEntity = indirectEntityIn;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Entity getSourceOfDamage() {
/* 24 */     return this.damageSourceEntity;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Entity getEntity() {
/* 30 */     return this.indirectEntity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
/* 38 */     ITextComponent itextcomponent = (this.indirectEntity == null) ? this.damageSourceEntity.getDisplayName() : this.indirectEntity.getDisplayName();
/* 39 */     ItemStack itemstack = (this.indirectEntity instanceof EntityLivingBase) ? ((EntityLivingBase)this.indirectEntity).getHeldItemMainhand() : ItemStack.field_190927_a;
/* 40 */     String s = "death.attack." + this.damageType;
/* 41 */     String s1 = String.valueOf(s) + ".item";
/* 42 */     return (!itemstack.func_190926_b() && itemstack.hasDisplayName() && I18n.canTranslate(s1)) ? (ITextComponent)new TextComponentTranslation(s1, new Object[] { entityLivingBaseIn.getDisplayName(), itextcomponent, itemstack.getTextComponent() }) : (ITextComponent)new TextComponentTranslation(s, new Object[] { entityLivingBaseIn.getDisplayName(), itextcomponent });
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\EntityDamageSourceIndirect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */