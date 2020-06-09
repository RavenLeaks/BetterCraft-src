/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackMelee;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityVindicator
/*     */   extends AbstractIllager {
/*  35 */   private static final Predicate<Entity> field_190644_c = new Predicate<Entity>()
/*     */     {
/*     */       public boolean apply(@Nullable Entity p_apply_1_)
/*     */       {
/*  39 */         return (p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).func_190631_cK());
/*     */       }
/*     */     };
/*     */   private boolean field_190643_b;
/*     */   
/*     */   public EntityVindicator(World p_i47279_1_) {
/*  45 */     super(p_i47279_1_);
/*  46 */     setSize(0.6F, 1.95F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_190641_b(DataFixer p_190641_0_) {
/*  51 */     EntityLiving.registerFixesMob(p_190641_0_, EntityVindicator.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  56 */     super.initEntityAI();
/*  57 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  58 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackMelee(this, 1.0D, false));
/*  59 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWander(this, 0.6D));
/*  60 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 3.0F, 1.0F));
/*  61 */     this.tasks.addTask(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0F));
/*  62 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[] { EntityVindicator.class }));
/*  63 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  64 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityVillager.class, true));
/*  65 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
/*  66 */     this.targetTasks.addTask(4, (EntityAIBase)new AIJohnnyAttack(this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  71 */     super.applyEntityAttributes();
/*  72 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3499999940395355D);
/*  73 */     getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(12.0D);
/*  74 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
/*  75 */     getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  80 */     super.entityInit();
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceLocation getLootTable() {
/*  85 */     return LootTableList.field_191186_av;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190639_o() {
/*  90 */     return func_193078_a(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190636_a(boolean p_190636_1_) {
/*  95 */     func_193079_a(1, p_190636_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 103 */     super.writeEntityToNBT(compound);
/*     */     
/* 105 */     if (this.field_190643_b)
/*     */     {
/* 107 */       compound.setBoolean("Johnny", true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractIllager.IllagerArmPose func_193077_p() {
/* 113 */     return func_190639_o() ? AbstractIllager.IllagerArmPose.ATTACKING : AbstractIllager.IllagerArmPose.CROSSED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 121 */     super.readEntityFromNBT(compound);
/*     */     
/* 123 */     if (compound.hasKey("Johnny", 99))
/*     */     {
/* 125 */       this.field_190643_b = compound.getBoolean("Johnny");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
/* 137 */     IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
/* 138 */     setEquipmentBasedOnDifficulty(difficulty);
/* 139 */     setEnchantmentBasedOnDifficulty(difficulty);
/* 140 */     return ientitylivingdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 148 */     setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 153 */     super.updateAITasks();
/* 154 */     func_190636_a((getAttackTarget() != null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnSameTeam(Entity entityIn) {
/* 162 */     if (super.isOnSameTeam(entityIn))
/*     */     {
/* 164 */       return true;
/*     */     }
/* 166 */     if (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getCreatureAttribute() == EnumCreatureAttribute.ILLAGER)
/*     */     {
/* 168 */       return (getTeam() == null && entityIn.getTeam() == null);
/*     */     }
/*     */ 
/*     */     
/* 172 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomNameTag(String name) {
/* 181 */     super.setCustomNameTag(name);
/*     */     
/* 183 */     if (!this.field_190643_b && "Johnny".equals(name))
/*     */     {
/* 185 */       this.field_190643_b = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 191 */     return SoundEvents.field_191268_hm;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 196 */     return SoundEvents.field_191269_hn;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 201 */     return SoundEvents.field_191270_ho;
/*     */   }
/*     */   
/*     */   static class AIJohnnyAttack
/*     */     extends EntityAINearestAttackableTarget<EntityLivingBase>
/*     */   {
/*     */     public AIJohnnyAttack(EntityVindicator p_i47345_1_) {
/* 208 */       super(p_i47345_1_, EntityLivingBase.class, 0, true, true, EntityVindicator.field_190644_c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 213 */       return (((EntityVindicator)this.taskOwner).field_190643_b && super.shouldExecute());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityVindicator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */