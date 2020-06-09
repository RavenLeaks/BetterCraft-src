/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityZombieHorse
/*     */   extends AbstractHorse {
/*     */   public EntityZombieHorse(World p_i47293_1_) {
/*  22 */     super(p_i47293_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_190693_b(DataFixer p_190693_0_) {
/*  27 */     AbstractHorse.func_190683_c(p_190693_0_, EntityZombieHorse.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  32 */     super.applyEntityAttributes();
/*  33 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
/*  34 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
/*  35 */     getEntityAttribute(JUMP_STRENGTH).setBaseValue(getModifiedJumpStrength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/*  43 */     return EnumCreatureAttribute.UNDEAD;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  48 */     super.getAmbientSound();
/*  49 */     return SoundEvents.ENTITY_ZOMBIE_HORSE_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  54 */     super.getDeathSound();
/*  55 */     return SoundEvents.ENTITY_ZOMBIE_HORSE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  60 */     super.getHurtSound(p_184601_1_);
/*  61 */     return SoundEvents.ENTITY_ZOMBIE_HORSE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/*  67 */     return LootTableList.ENTITIES_ZOMBIE_HORSE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/*  72 */     ItemStack itemstack = player.getHeldItem(hand);
/*  73 */     boolean flag = !itemstack.func_190926_b();
/*     */     
/*  75 */     if (flag && itemstack.getItem() == Items.SPAWN_EGG)
/*     */     {
/*  77 */       return super.processInteract(player, hand);
/*     */     }
/*  79 */     if (!isTame())
/*     */     {
/*  81 */       return false;
/*     */     }
/*  83 */     if (isChild())
/*     */     {
/*  85 */       return super.processInteract(player, hand);
/*     */     }
/*  87 */     if (player.isSneaking()) {
/*     */       
/*  89 */       openGUI(player);
/*  90 */       return true;
/*     */     } 
/*  92 */     if (isBeingRidden())
/*     */     {
/*  94 */       return super.processInteract(player, hand);
/*     */     }
/*     */ 
/*     */     
/*  98 */     if (flag) {
/*     */       
/* 100 */       if (!isHorseSaddled() && itemstack.getItem() == Items.SADDLE) {
/*     */         
/* 102 */         openGUI(player);
/* 103 */         return true;
/*     */       } 
/*     */       
/* 106 */       if (itemstack.interactWithEntity(player, (EntityLivingBase)this, hand))
/*     */       {
/* 108 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 112 */     mountTo(player);
/* 113 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityZombieHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */