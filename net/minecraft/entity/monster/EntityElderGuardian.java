/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketChangeGameState;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityElderGuardian extends EntityGuardian {
/*     */   public EntityElderGuardian(World p_i47288_1_) {
/*  26 */     super(p_i47288_1_);
/*  27 */     setSize(this.width * 2.35F, this.height * 2.35F);
/*  28 */     enablePersistence();
/*     */     
/*  30 */     if (this.wander != null)
/*     */     {
/*  32 */       this.wander.setExecutionChance(400);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  38 */     super.applyEntityAttributes();
/*  39 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
/*  40 */     getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
/*  41 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_190768_b(DataFixer p_190768_0_) {
/*  46 */     EntityLiving.registerFixesMob(p_190768_0_, EntityElderGuardian.class);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/*  52 */     return LootTableList.ENTITIES_ELDER_GUARDIAN;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAttackDuration() {
/*  57 */     return 60;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190767_di() {
/*  62 */     this.clientSideSpikesAnimation = 1.0F;
/*  63 */     this.clientSideSpikesAnimationO = this.clientSideSpikesAnimation;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  68 */     return isInWater() ? SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT : SoundEvents.ENTITY_ELDERGUARDIAN_AMBIENTLAND;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  73 */     return isInWater() ? SoundEvents.ENTITY_ELDER_GUARDIAN_HURT : SoundEvents.ENTITY_ELDER_GUARDIAN_HURT_LAND;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  78 */     return isInWater() ? SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH : SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH_LAND;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent func_190765_dj() {
/*  83 */     return SoundEvents.field_191240_aK;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/*  88 */     super.updateAITasks();
/*  89 */     int i = 1200;
/*     */     
/*  91 */     if ((this.ticksExisted + getEntityId()) % 1200 == 0) {
/*     */       
/*  93 */       Potion potion = MobEffects.MINING_FATIGUE;
/*  94 */       List<EntityPlayerMP> list = this.world.getPlayers(EntityPlayerMP.class, new Predicate<EntityPlayerMP>()
/*     */           {
/*     */             public boolean apply(@Nullable EntityPlayerMP p_apply_1_)
/*     */             {
/*  98 */               return (EntityElderGuardian.this.getDistanceSqToEntity((Entity)p_apply_1_) < 2500.0D && p_apply_1_.interactionManager.survivalOrAdventure());
/*     */             }
/*     */           });
/* 101 */       int j = 2;
/* 102 */       int k = 6000;
/* 103 */       int l = 1200;
/*     */       
/* 105 */       for (EntityPlayerMP entityplayermp : list) {
/*     */         
/* 107 */         if (!entityplayermp.isPotionActive(potion) || entityplayermp.getActivePotionEffect(potion).getAmplifier() < 2 || entityplayermp.getActivePotionEffect(potion).getDuration() < 1200) {
/*     */           
/* 109 */           entityplayermp.connection.sendPacket((Packet)new SPacketChangeGameState(10, 0.0F));
/* 110 */           entityplayermp.addPotionEffect(new PotionEffect(potion, 6000, 2));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     if (!hasHome())
/*     */     {
/* 117 */       setHomePosAndDistance(new BlockPos((Entity)this), 16);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityElderGuardian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */