/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAreaEffectCloud;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.PotionTypes;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.potion.PotionType;
/*     */ import net.minecraft.potion.PotionUtils;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.walkers.ItemStackData;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class EntityPotion extends EntityThrowable {
/*  37 */   private static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(EntityPotion.class, DataSerializers.OPTIONAL_ITEM_STACK);
/*  38 */   private static final Logger LOGGER = LogManager.getLogger();
/*  39 */   public static final Predicate<EntityLivingBase> field_190546_d = new Predicate<EntityLivingBase>()
/*     */     {
/*     */       public boolean apply(@Nullable EntityLivingBase p_apply_1_)
/*     */       {
/*  43 */         return EntityPotion.func_190544_c(p_apply_1_);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public EntityPotion(World worldIn) {
/*  49 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPotion(World worldIn, EntityLivingBase throwerIn, ItemStack potionDamageIn) {
/*  54 */     super(worldIn, throwerIn);
/*  55 */     setItem(potionDamageIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPotion(World worldIn, double x, double y, double z, ItemStack potionDamageIn) {
/*  60 */     super(worldIn, x, y, z);
/*     */     
/*  62 */     if (!potionDamageIn.func_190926_b())
/*     */     {
/*  64 */       setItem(potionDamageIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  70 */     getDataManager().register(ITEM, ItemStack.field_190927_a);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getPotion() {
/*  75 */     ItemStack itemstack = (ItemStack)getDataManager().get(ITEM);
/*     */     
/*  77 */     if (itemstack.getItem() != Items.SPLASH_POTION && itemstack.getItem() != Items.LINGERING_POTION) {
/*     */       
/*  79 */       if (this.world != null)
/*     */       {
/*  81 */         LOGGER.error("ThrownPotion entity {} has no item?!", Integer.valueOf(getEntityId()));
/*     */       }
/*     */       
/*  84 */       return new ItemStack((Item)Items.SPLASH_POTION);
/*     */     } 
/*     */ 
/*     */     
/*  88 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItem(ItemStack stack) {
/*  94 */     getDataManager().set(ITEM, stack);
/*  95 */     getDataManager().setDirty(ITEM);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getGravityVelocity() {
/* 103 */     return 0.05F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onImpact(RayTraceResult result) {
/* 111 */     if (!this.world.isRemote) {
/*     */       
/* 113 */       ItemStack itemstack = getPotion();
/* 114 */       PotionType potiontype = PotionUtils.getPotionFromItem(itemstack);
/* 115 */       List<PotionEffect> list = PotionUtils.getEffectsFromStack(itemstack);
/* 116 */       boolean flag = (potiontype == PotionTypes.WATER && list.isEmpty());
/*     */       
/* 118 */       if (result.typeOfHit == RayTraceResult.Type.BLOCK && flag) {
/*     */         
/* 120 */         BlockPos blockpos = result.getBlockPos().offset(result.sideHit);
/* 121 */         extinguishFires(blockpos, result.sideHit);
/*     */         
/* 123 */         for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
/*     */         {
/* 125 */           extinguishFires(blockpos.offset(enumfacing), enumfacing);
/*     */         }
/*     */       } 
/*     */       
/* 129 */       if (flag) {
/*     */         
/* 131 */         func_190545_n();
/*     */       }
/* 133 */       else if (!list.isEmpty()) {
/*     */         
/* 135 */         if (isLingering()) {
/*     */           
/* 137 */           func_190542_a(itemstack, potiontype);
/*     */         }
/*     */         else {
/*     */           
/* 141 */           func_190543_a(result, list);
/*     */         } 
/*     */       } 
/*     */       
/* 145 */       int i = potiontype.hasInstantEffect() ? 2007 : 2002;
/* 146 */       this.world.playEvent(i, new BlockPos(this), PotionUtils.func_190932_c(itemstack));
/* 147 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190545_n() {
/* 153 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D);
/* 154 */     List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb, field_190546_d);
/*     */     
/* 156 */     if (!list.isEmpty())
/*     */     {
/* 158 */       for (EntityLivingBase entitylivingbase : list) {
/*     */         
/* 160 */         double d0 = getDistanceSqToEntity((Entity)entitylivingbase);
/*     */         
/* 162 */         if (d0 < 16.0D && func_190544_c(entitylivingbase))
/*     */         {
/* 164 */           entitylivingbase.attackEntityFrom(DamageSource.drown, 1.0F);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190543_a(RayTraceResult p_190543_1_, List<PotionEffect> p_190543_2_) {
/* 172 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D);
/* 173 */     List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
/*     */     
/* 175 */     if (!list.isEmpty())
/*     */     {
/* 177 */       for (EntityLivingBase entitylivingbase : list) {
/*     */         
/* 179 */         if (entitylivingbase.canBeHitWithPotion()) {
/*     */           
/* 181 */           double d0 = getDistanceSqToEntity((Entity)entitylivingbase);
/*     */           
/* 183 */           if (d0 < 16.0D) {
/*     */             
/* 185 */             double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
/*     */             
/* 187 */             if (entitylivingbase == p_190543_1_.entityHit)
/*     */             {
/* 189 */               d1 = 1.0D;
/*     */             }
/*     */             
/* 192 */             for (PotionEffect potioneffect : p_190543_2_) {
/*     */               
/* 194 */               Potion potion = potioneffect.getPotion();
/*     */               
/* 196 */               if (potion.isInstant()) {
/*     */                 
/* 198 */                 potion.affectEntity(this, (Entity)getThrower(), entitylivingbase, potioneffect.getAmplifier(), d1);
/*     */                 
/*     */                 continue;
/*     */               } 
/* 202 */               int i = (int)(d1 * potioneffect.getDuration() + 0.5D);
/*     */               
/* 204 */               if (i > 20)
/*     */               {
/* 206 */                 entitylivingbase.addPotionEffect(new PotionEffect(potion, i, potioneffect.getAmplifier(), potioneffect.getIsAmbient(), potioneffect.doesShowParticles()));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_190542_a(ItemStack p_190542_1_, PotionType p_190542_2_) {
/* 218 */     EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
/* 219 */     entityareaeffectcloud.setOwner(getThrower());
/* 220 */     entityareaeffectcloud.setRadius(3.0F);
/* 221 */     entityareaeffectcloud.setRadiusOnUse(-0.5F);
/* 222 */     entityareaeffectcloud.setWaitTime(10);
/* 223 */     entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / entityareaeffectcloud.getDuration());
/* 224 */     entityareaeffectcloud.setPotion(p_190542_2_);
/*     */     
/* 226 */     for (PotionEffect potioneffect : PotionUtils.getFullEffectsFromItem(p_190542_1_))
/*     */     {
/* 228 */       entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
/*     */     }
/*     */     
/* 231 */     NBTTagCompound nbttagcompound = p_190542_1_.getTagCompound();
/*     */     
/* 233 */     if (nbttagcompound != null && nbttagcompound.hasKey("CustomPotionColor", 99))
/*     */     {
/* 235 */       entityareaeffectcloud.setColor(nbttagcompound.getInteger("CustomPotionColor"));
/*     */     }
/*     */     
/* 238 */     this.world.spawnEntityInWorld((Entity)entityareaeffectcloud);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isLingering() {
/* 243 */     return (getPotion().getItem() == Items.LINGERING_POTION);
/*     */   }
/*     */ 
/*     */   
/*     */   private void extinguishFires(BlockPos pos, EnumFacing p_184542_2_) {
/* 248 */     if (this.world.getBlockState(pos).getBlock() == Blocks.FIRE)
/*     */     {
/* 250 */       this.world.extinguishFire(null, pos.offset(p_184542_2_), p_184542_2_.getOpposite());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesPotion(DataFixer fixer) {
/* 256 */     EntityThrowable.registerFixesThrowable(fixer, "ThrownPotion");
/* 257 */     fixer.registerWalker(FixTypes.ENTITY, (IDataWalker)new ItemStackData(EntityPotion.class, new String[] { "Potion" }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 265 */     super.readEntityFromNBT(compound);
/* 266 */     ItemStack itemstack = new ItemStack(compound.getCompoundTag("Potion"));
/*     */     
/* 268 */     if (itemstack.func_190926_b()) {
/*     */       
/* 270 */       setDead();
/*     */     }
/*     */     else {
/*     */       
/* 274 */       setItem(itemstack);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 283 */     super.writeEntityToNBT(compound);
/* 284 */     ItemStack itemstack = getPotion();
/*     */     
/* 286 */     if (!itemstack.func_190926_b())
/*     */     {
/* 288 */       compound.setTag("Potion", (NBTBase)itemstack.writeToNBT(new NBTTagCompound()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean func_190544_c(EntityLivingBase p_190544_0_) {
/* 294 */     return !(!(p_190544_0_ instanceof net.minecraft.entity.monster.EntityEnderman) && !(p_190544_0_ instanceof net.minecraft.entity.monster.EntityBlaze));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntityPotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */