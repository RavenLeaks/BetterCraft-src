/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityZombieVillager extends EntityZombie {
/*  34 */   private static final DataParameter<Boolean> CONVERTING = EntityDataManager.createKey(EntityZombieVillager.class, DataSerializers.BOOLEAN);
/*  35 */   private static final DataParameter<Integer> field_190739_c = EntityDataManager.createKey(EntityZombieVillager.class, DataSerializers.VARINT);
/*     */ 
/*     */   
/*     */   private int conversionTime;
/*     */ 
/*     */   
/*     */   private UUID field_191992_by;
/*     */ 
/*     */   
/*     */   public EntityZombieVillager(World p_i47277_1_) {
/*  45 */     super(p_i47277_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  50 */     super.entityInit();
/*  51 */     this.dataManager.register(CONVERTING, Boolean.valueOf(false));
/*  52 */     this.dataManager.register(field_190739_c, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190733_a(int p_190733_1_) {
/*  57 */     this.dataManager.set(field_190739_c, Integer.valueOf(p_190733_1_));
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_190736_dl() {
/*  62 */     return Math.max(((Integer)this.dataManager.get(field_190739_c)).intValue() % 6, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_190737_b(DataFixer p_190737_0_) {
/*  67 */     EntityLiving.registerFixesMob(p_190737_0_, EntityZombieVillager.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  75 */     super.writeEntityToNBT(compound);
/*  76 */     compound.setInteger("Profession", func_190736_dl());
/*  77 */     compound.setInteger("ConversionTime", isConverting() ? this.conversionTime : -1);
/*     */     
/*  79 */     if (this.field_191992_by != null)
/*     */     {
/*  81 */       compound.setUniqueId("ConversionPlayer", this.field_191992_by);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/*  90 */     super.readEntityFromNBT(compound);
/*  91 */     func_190733_a(compound.getInteger("Profession"));
/*     */     
/*  93 */     if (compound.hasKey("ConversionTime", 99) && compound.getInteger("ConversionTime") > -1)
/*     */     {
/*  95 */       func_191991_a(compound.hasUniqueId("ConversionPlayer") ? compound.getUniqueId("ConversionPlayer") : null, compound.getInteger("ConversionTime"));
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
/* 107 */     func_190733_a(this.world.rand.nextInt(6));
/* 108 */     return super.onInitialSpawn(difficulty, livingdata);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 116 */     if (!this.world.isRemote && isConverting()) {
/*     */       
/* 118 */       int i = func_190735_dq();
/* 119 */       this.conversionTime -= i;
/*     */       
/* 121 */       if (this.conversionTime <= 0)
/*     */       {
/* 123 */         func_190738_dp();
/*     */       }
/*     */     } 
/*     */     
/* 127 */     super.onUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 132 */     ItemStack itemstack = player.getHeldItem(hand);
/*     */     
/* 134 */     if (itemstack.getItem() == Items.GOLDEN_APPLE && itemstack.getMetadata() == 0 && isPotionActive(MobEffects.WEAKNESS)) {
/*     */       
/* 136 */       if (!player.capabilities.isCreativeMode)
/*     */       {
/* 138 */         itemstack.func_190918_g(1);
/*     */       }
/*     */       
/* 141 */       if (!this.world.isRemote)
/*     */       {
/* 143 */         func_191991_a(player.getUniqueID(), this.rand.nextInt(2401) + 3600);
/*     */       }
/*     */       
/* 146 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDespawn() {
/* 159 */     return !isConverting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConverting() {
/* 167 */     return ((Boolean)getDataManager().get(CONVERTING)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_191991_a(@Nullable UUID p_191991_1_, int p_191991_2_) {
/* 172 */     this.field_191992_by = p_191991_1_;
/* 173 */     this.conversionTime = p_191991_2_;
/* 174 */     getDataManager().set(CONVERTING, Boolean.valueOf(true));
/* 175 */     removePotionEffect(MobEffects.WEAKNESS);
/* 176 */     addPotionEffect(new PotionEffect(MobEffects.STRENGTH, p_191991_2_, Math.min(this.world.getDifficulty().getDifficultyId() - 1, 0)));
/* 177 */     this.world.setEntityState((Entity)this, (byte)16);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 182 */     if (id == 16) {
/*     */       
/* 184 */       if (!isSilent())
/*     */       {
/* 186 */         this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 191 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_190738_dp() {
/* 197 */     EntityVillager entityvillager = new EntityVillager(this.world);
/* 198 */     entityvillager.copyLocationAndAnglesFrom((Entity)this);
/* 199 */     entityvillager.setProfession(func_190736_dl());
/* 200 */     entityvillager.func_190672_a(this.world.getDifficultyForLocation(new BlockPos((Entity)entityvillager)), null, false);
/* 201 */     entityvillager.setLookingForHome();
/*     */     
/* 203 */     if (isChild())
/*     */     {
/* 205 */       entityvillager.setGrowingAge(-24000);
/*     */     }
/*     */     
/* 208 */     this.world.removeEntity((Entity)this);
/* 209 */     entityvillager.setNoAI(isAIDisabled());
/*     */     
/* 211 */     if (hasCustomName()) {
/*     */       
/* 213 */       entityvillager.setCustomNameTag(getCustomNameTag());
/* 214 */       entityvillager.setAlwaysRenderNameTag(getAlwaysRenderNameTag());
/*     */     } 
/*     */     
/* 217 */     this.world.spawnEntityInWorld((Entity)entityvillager);
/*     */     
/* 219 */     if (this.field_191992_by != null) {
/*     */       
/* 221 */       EntityPlayer entityplayer = this.world.getPlayerEntityByUUID(this.field_191992_by);
/*     */       
/* 223 */       if (entityplayer instanceof EntityPlayerMP)
/*     */       {
/* 225 */         CriteriaTriggers.field_192137_q.func_192183_a((EntityPlayerMP)entityplayer, this, entityvillager);
/*     */       }
/*     */     } 
/*     */     
/* 229 */     entityvillager.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
/* 230 */     this.world.playEvent(null, 1027, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int func_190735_dq() {
/* 235 */     int i = 1;
/*     */     
/* 237 */     if (this.rand.nextFloat() < 0.01F) {
/*     */       
/* 239 */       int j = 0;
/* 240 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */       
/* 242 */       for (int k = (int)this.posX - 4; k < (int)this.posX + 4 && j < 14; k++) {
/*     */         
/* 244 */         for (int l = (int)this.posY - 4; l < (int)this.posY + 4 && j < 14; l++) {
/*     */           
/* 246 */           for (int i1 = (int)this.posZ - 4; i1 < (int)this.posZ + 4 && j < 14; i1++) {
/*     */             
/* 248 */             Block block = this.world.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(k, l, i1)).getBlock();
/*     */             
/* 250 */             if (block == Blocks.IRON_BARS || block == Blocks.BED) {
/*     */               
/* 252 */               if (this.rand.nextFloat() < 0.3F)
/*     */               {
/* 254 */                 i++;
/*     */               }
/*     */               
/* 257 */               j++;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 264 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundPitch() {
/* 272 */     return isChild() ? ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 2.0F) : ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getAmbientSound() {
/* 277 */     return SoundEvents.ENTITY_ZOMBIE_VILLAGER_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 282 */     return SoundEvents.ENTITY_ZOMBIE_VILLAGER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getDeathSound() {
/* 287 */     return SoundEvents.ENTITY_ZOMBIE_VILLAGER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent func_190731_di() {
/* 292 */     return SoundEvents.ENTITY_ZOMBIE_VILLAGER_STEP;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 298 */     return LootTableList.field_191183_as;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack func_190732_dj() {
/* 303 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityZombieVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */