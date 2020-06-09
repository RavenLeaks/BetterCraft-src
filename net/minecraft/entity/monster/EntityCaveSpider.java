/*    */ package net.minecraft.entity.monster;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.IEntityLivingData;
/*    */ import net.minecraft.entity.SharedMonsterAttributes;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.world.DifficultyInstance;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.storage.loot.LootTableList;
/*    */ 
/*    */ public class EntityCaveSpider
/*    */   extends EntitySpider
/*    */ {
/*    */   public EntityCaveSpider(World worldIn) {
/* 22 */     super(worldIn);
/* 23 */     setSize(0.7F, 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerFixesCaveSpider(DataFixer fixer) {
/* 28 */     EntityLiving.registerFixesMob(fixer, EntityCaveSpider.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void applyEntityAttributes() {
/* 33 */     super.applyEntityAttributes();
/* 34 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean attackEntityAsMob(Entity entityIn) {
/* 39 */     if (super.attackEntityAsMob(entityIn)) {
/*    */       
/* 41 */       if (entityIn instanceof EntityLivingBase) {
/*    */         
/* 43 */         int i = 0;
/*    */         
/* 45 */         if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
/*    */           
/* 47 */           i = 7;
/*    */         }
/* 49 */         else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
/*    */           
/* 51 */           i = 15;
/*    */         } 
/*    */         
/* 54 */         if (i > 0)
/*    */         {
/* 56 */           ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON, i * 20, 0));
/*    */         }
/*    */       } 
/*    */       
/* 60 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
/* 76 */     return livingdata;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getEyeHeight() {
/* 81 */     return 0.45F;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected ResourceLocation getLootTable() {
/* 87 */     return LootTableList.ENTITIES_CAVE_SPIDER;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityCaveSpider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */