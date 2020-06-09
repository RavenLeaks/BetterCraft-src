/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemMonsterPlacer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityAgeable
/*     */   extends EntityCreature {
/*  18 */   public static final DataParameter<Boolean> BABY = EntityDataManager.createKey(EntityAgeable.class, DataSerializers.BOOLEAN);
/*     */   protected int growingAge;
/*     */   protected int forcedAge;
/*     */   protected int forcedAgeTimer;
/*  22 */   private float ageWidth = -1.0F;
/*     */   
/*     */   private float ageHeight;
/*     */   
/*     */   public EntityAgeable(World worldIn) {
/*  27 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public abstract EntityAgeable createChild(EntityAgeable paramEntityAgeable);
/*     */   
/*     */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/*  35 */     ItemStack itemstack = player.getHeldItem(hand);
/*     */     
/*  37 */     if (itemstack.getItem() == Items.SPAWN_EGG) {
/*     */       
/*  39 */       if (!this.world.isRemote) {
/*     */         
/*  41 */         Class<? extends Entity> oclass = (Class<? extends Entity>)EntityList.field_191308_b.getObject(ItemMonsterPlacer.func_190908_h(itemstack));
/*     */         
/*  43 */         if (oclass != null && getClass() == oclass) {
/*     */           
/*  45 */           EntityAgeable entityageable = createChild(this);
/*     */           
/*  47 */           if (entityageable != null) {
/*     */             
/*  49 */             entityageable.setGrowingAge(-24000);
/*  50 */             entityageable.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
/*  51 */             this.world.spawnEntityInWorld(entityageable);
/*     */             
/*  53 */             if (itemstack.hasDisplayName())
/*     */             {
/*  55 */               entityageable.setCustomNameTag(itemstack.getDisplayName());
/*     */             }
/*     */             
/*  58 */             if (!player.capabilities.isCreativeMode)
/*     */             {
/*  60 */               itemstack.func_190918_g(1);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  66 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_190669_a(ItemStack p_190669_1_, Class<? extends Entity> p_190669_2_) {
/*  76 */     if (p_190669_1_.getItem() != Items.SPAWN_EGG)
/*     */     {
/*  78 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  82 */     Class<? extends Entity> oclass = (Class<? extends Entity>)EntityList.field_191308_b.getObject(ItemMonsterPlacer.func_190908_h(p_190669_1_));
/*  83 */     return (oclass != null && p_190669_2_ == oclass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  89 */     super.entityInit();
/*  90 */     this.dataManager.register(BABY, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGrowingAge() {
/* 100 */     if (this.world.isRemote)
/*     */     {
/* 102 */       return ((Boolean)this.dataManager.get(BABY)).booleanValue() ? -1 : 1;
/*     */     }
/*     */ 
/*     */     
/* 106 */     return this.growingAge;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void ageUp(int p_175501_1_, boolean p_175501_2_) {
/* 112 */     int i = getGrowingAge();
/* 113 */     int j = i;
/* 114 */     i += p_175501_1_ * 20;
/*     */     
/* 116 */     if (i > 0) {
/*     */       
/* 118 */       i = 0;
/*     */       
/* 120 */       if (j < 0)
/*     */       {
/* 122 */         onGrowingAdult();
/*     */       }
/*     */     } 
/*     */     
/* 126 */     int k = i - j;
/* 127 */     setGrowingAge(i);
/*     */     
/* 129 */     if (p_175501_2_) {
/*     */       
/* 131 */       this.forcedAge += k;
/*     */       
/* 133 */       if (this.forcedAgeTimer == 0)
/*     */       {
/* 135 */         this.forcedAgeTimer = 40;
/*     */       }
/*     */     } 
/*     */     
/* 139 */     if (getGrowingAge() == 0)
/*     */     {
/* 141 */       setGrowingAge(this.forcedAge);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addGrowth(int growth) {
/* 151 */     ageUp(growth, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGrowingAge(int age) {
/* 160 */     this.dataManager.set(BABY, Boolean.valueOf((age < 0)));
/* 161 */     this.growingAge = age;
/* 162 */     setScaleForAge(isChild());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 170 */     super.writeEntityToNBT(compound);
/* 171 */     compound.setInteger("Age", getGrowingAge());
/* 172 */     compound.setInteger("ForcedAge", this.forcedAge);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 180 */     super.readEntityFromNBT(compound);
/* 181 */     setGrowingAge(compound.getInteger("Age"));
/* 182 */     this.forcedAge = compound.getInteger("ForcedAge");
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 187 */     if (BABY.equals(key))
/*     */     {
/* 189 */       setScaleForAge(isChild());
/*     */     }
/*     */     
/* 192 */     super.notifyDataManagerChange(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 201 */     super.onLivingUpdate();
/*     */     
/* 203 */     if (this.world.isRemote) {
/*     */       
/* 205 */       if (this.forcedAgeTimer > 0)
/*     */       {
/* 207 */         if (this.forcedAgeTimer % 4 == 0)
/*     */         {
/* 209 */           this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */         
/* 212 */         this.forcedAgeTimer--;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 217 */       int i = getGrowingAge();
/*     */       
/* 219 */       if (i < 0) {
/*     */         
/* 221 */         i++;
/* 222 */         setGrowingAge(i);
/*     */         
/* 224 */         if (i == 0)
/*     */         {
/* 226 */           onGrowingAdult();
/*     */         }
/*     */       }
/* 229 */       else if (i > 0) {
/*     */         
/* 231 */         i--;
/* 232 */         setGrowingAge(i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onGrowingAdult() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChild() {
/* 250 */     return (getGrowingAge() < 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScaleForAge(boolean child) {
/* 258 */     setScale(child ? 0.5F : 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setSize(float width, float height) {
/* 266 */     boolean flag = (this.ageWidth > 0.0F);
/* 267 */     this.ageWidth = width;
/* 268 */     this.ageHeight = height;
/*     */     
/* 270 */     if (!flag)
/*     */     {
/* 272 */       setScale(1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void setScale(float scale) {
/* 278 */     super.setSize(this.ageWidth * scale, this.ageHeight * scale);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\EntityAgeable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */