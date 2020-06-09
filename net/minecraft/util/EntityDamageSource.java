/*    */ package net.minecraft.util;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ import net.minecraft.util.text.translation.I18n;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityDamageSource
/*    */   extends DamageSource
/*    */ {
/*    */   @Nullable
/*    */   protected Entity damageSourceEntity;
/*    */   private boolean isThornsDamage;
/*    */   
/*    */   public EntityDamageSource(String damageTypeIn, @Nullable Entity damageSourceEntityIn) {
/* 25 */     super(damageTypeIn);
/* 26 */     this.damageSourceEntity = damageSourceEntityIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityDamageSource setIsThornsDamage() {
/* 34 */     this.isThornsDamage = true;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getIsThornsDamage() {
/* 40 */     return this.isThornsDamage;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Entity getEntity() {
/* 46 */     return this.damageSourceEntity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
/* 54 */     ItemStack itemstack = (this.damageSourceEntity instanceof EntityLivingBase) ? ((EntityLivingBase)this.damageSourceEntity).getHeldItemMainhand() : ItemStack.field_190927_a;
/* 55 */     String s = "death.attack." + this.damageType;
/* 56 */     String s1 = String.valueOf(s) + ".item";
/* 57 */     return (!itemstack.func_190926_b() && itemstack.hasDisplayName() && I18n.canTranslate(s1)) ? (ITextComponent)new TextComponentTranslation(s1, new Object[] { entityLivingBaseIn.getDisplayName(), this.damageSourceEntity.getDisplayName(), itemstack.getTextComponent() }) : (ITextComponent)new TextComponentTranslation(s, new Object[] { entityLivingBaseIn.getDisplayName(), this.damageSourceEntity.getDisplayName() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDifficultyScaled() {
/* 65 */     return (this.damageSourceEntity != null && this.damageSourceEntity instanceof EntityLivingBase && !(this.damageSourceEntity instanceof net.minecraft.entity.player.EntityPlayer));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Vec3d getDamageLocation() {
/* 75 */     return new Vec3d(this.damageSourceEntity.posX, this.damageSourceEntity.posY, this.damageSourceEntity.posZ);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\EntityDamageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */