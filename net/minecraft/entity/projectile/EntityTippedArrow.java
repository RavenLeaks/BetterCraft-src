/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.PotionTypes;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.potion.PotionType;
/*     */ import net.minecraft.potion.PotionUtils;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityTippedArrow extends EntityArrow {
/*  25 */   private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntityTippedArrow.class, DataSerializers.VARINT);
/*  26 */   private PotionType potion = PotionTypes.EMPTY;
/*  27 */   private final Set<PotionEffect> customPotionEffects = Sets.newHashSet();
/*     */   
/*     */   private boolean field_191509_at;
/*     */   
/*     */   public EntityTippedArrow(World worldIn) {
/*  32 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityTippedArrow(World worldIn, double x, double y, double z) {
/*  37 */     super(worldIn, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityTippedArrow(World worldIn, EntityLivingBase shooter) {
/*  42 */     super(worldIn, shooter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPotionEffect(ItemStack stack) {
/*  47 */     if (stack.getItem() == Items.TIPPED_ARROW) {
/*     */       
/*  49 */       this.potion = PotionUtils.getPotionFromItem(stack);
/*  50 */       Collection<PotionEffect> collection = PotionUtils.getFullEffectsFromItem(stack);
/*     */       
/*  52 */       if (!collection.isEmpty())
/*     */       {
/*  54 */         for (PotionEffect potioneffect : collection)
/*     */         {
/*  56 */           this.customPotionEffects.add(new PotionEffect(potioneffect));
/*     */         }
/*     */       }
/*     */       
/*  60 */       int i = func_191508_b(stack);
/*     */       
/*  62 */       if (i == -1)
/*     */       {
/*  64 */         func_190548_o();
/*     */       }
/*     */       else
/*     */       {
/*  68 */         func_191507_d(i);
/*     */       }
/*     */     
/*  71 */     } else if (stack.getItem() == Items.ARROW) {
/*     */       
/*  73 */       this.potion = PotionTypes.EMPTY;
/*  74 */       this.customPotionEffects.clear();
/*  75 */       this.dataManager.set(COLOR, Integer.valueOf(-1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_191508_b(ItemStack p_191508_0_) {
/*  81 */     NBTTagCompound nbttagcompound = p_191508_0_.getTagCompound();
/*  82 */     return (nbttagcompound != null && nbttagcompound.hasKey("CustomPotionColor", 99)) ? nbttagcompound.getInteger("CustomPotionColor") : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190548_o() {
/*  87 */     this.field_191509_at = false;
/*  88 */     this.dataManager.set(COLOR, Integer.valueOf(PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.customPotionEffects))));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEffect(PotionEffect effect) {
/*  93 */     this.customPotionEffects.add(effect);
/*  94 */     getDataManager().set(COLOR, Integer.valueOf(PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.customPotionEffects))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  99 */     super.entityInit();
/* 100 */     this.dataManager.register(COLOR, Integer.valueOf(-1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 108 */     super.onUpdate();
/*     */     
/* 110 */     if (this.world.isRemote) {
/*     */       
/* 112 */       if (this.inGround)
/*     */       {
/* 114 */         if (this.timeInGround % 5 == 0)
/*     */         {
/* 116 */           spawnPotionParticles(1);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 121 */         spawnPotionParticles(2);
/*     */       }
/*     */     
/* 124 */     } else if (this.inGround && this.timeInGround != 0 && !this.customPotionEffects.isEmpty() && this.timeInGround >= 600) {
/*     */       
/* 126 */       this.world.setEntityState(this, (byte)0);
/* 127 */       this.potion = PotionTypes.EMPTY;
/* 128 */       this.customPotionEffects.clear();
/* 129 */       this.dataManager.set(COLOR, Integer.valueOf(-1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void spawnPotionParticles(int particleCount) {
/* 135 */     int i = getColor();
/*     */     
/* 137 */     if (i != -1 && particleCount > 0) {
/*     */       
/* 139 */       double d0 = (i >> 16 & 0xFF) / 255.0D;
/* 140 */       double d1 = (i >> 8 & 0xFF) / 255.0D;
/* 141 */       double d2 = (i >> 0 & 0xFF) / 255.0D;
/*     */       
/* 143 */       for (int j = 0; j < particleCount; j++)
/*     */       {
/* 145 */         this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, d0, d1, d2, new int[0]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor() {
/* 152 */     return ((Integer)this.dataManager.get(COLOR)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_191507_d(int p_191507_1_) {
/* 157 */     this.field_191509_at = true;
/* 158 */     this.dataManager.set(COLOR, Integer.valueOf(p_191507_1_));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesTippedArrow(DataFixer fixer) {
/* 163 */     EntityArrow.registerFixesArrow(fixer, "TippedArrow");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 171 */     super.writeEntityToNBT(compound);
/*     */     
/* 173 */     if (this.potion != PotionTypes.EMPTY && this.potion != null)
/*     */     {
/* 175 */       compound.setString("Potion", ((ResourceLocation)PotionType.REGISTRY.getNameForObject(this.potion)).toString());
/*     */     }
/*     */     
/* 178 */     if (this.field_191509_at)
/*     */     {
/* 180 */       compound.setInteger("Color", getColor());
/*     */     }
/*     */     
/* 183 */     if (!this.customPotionEffects.isEmpty()) {
/*     */       
/* 185 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/* 187 */       for (PotionEffect potioneffect : this.customPotionEffects)
/*     */       {
/* 189 */         nbttaglist.appendTag((NBTBase)potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
/*     */       }
/*     */       
/* 192 */       compound.setTag("CustomPotionEffects", (NBTBase)nbttaglist);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 201 */     super.readEntityFromNBT(compound);
/*     */     
/* 203 */     if (compound.hasKey("Potion", 8))
/*     */     {
/* 205 */       this.potion = PotionUtils.getPotionTypeFromNBT(compound);
/*     */     }
/*     */     
/* 208 */     for (PotionEffect potioneffect : PotionUtils.getFullEffectsFromTag(compound))
/*     */     {
/* 210 */       addEffect(potioneffect);
/*     */     }
/*     */     
/* 213 */     if (compound.hasKey("Color", 99)) {
/*     */       
/* 215 */       func_191507_d(compound.getInteger("Color"));
/*     */     }
/*     */     else {
/*     */       
/* 219 */       func_190548_o();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void arrowHit(EntityLivingBase living) {
/* 225 */     super.arrowHit(living);
/*     */     
/* 227 */     for (PotionEffect potioneffect : this.potion.getEffects())
/*     */     {
/* 229 */       living.addPotionEffect(new PotionEffect(potioneffect.getPotion(), Math.max(potioneffect.getDuration() / 8, 1), potioneffect.getAmplifier(), potioneffect.getIsAmbient(), potioneffect.doesShowParticles()));
/*     */     }
/*     */     
/* 232 */     if (!this.customPotionEffects.isEmpty())
/*     */     {
/* 234 */       for (PotionEffect potioneffect1 : this.customPotionEffects)
/*     */       {
/* 236 */         living.addPotionEffect(potioneffect1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack getArrowStack() {
/* 243 */     if (this.customPotionEffects.isEmpty() && this.potion == PotionTypes.EMPTY)
/*     */     {
/* 245 */       return new ItemStack(Items.ARROW);
/*     */     }
/*     */ 
/*     */     
/* 249 */     ItemStack itemstack = new ItemStack(Items.TIPPED_ARROW);
/* 250 */     PotionUtils.addPotionToItemStack(itemstack, this.potion);
/* 251 */     PotionUtils.appendEffects(itemstack, this.customPotionEffects);
/*     */     
/* 253 */     if (this.field_191509_at) {
/*     */       
/* 255 */       NBTTagCompound nbttagcompound = itemstack.getTagCompound();
/*     */       
/* 257 */       if (nbttagcompound == null) {
/*     */         
/* 259 */         nbttagcompound = new NBTTagCompound();
/* 260 */         itemstack.setTagCompound(nbttagcompound);
/*     */       } 
/*     */       
/* 263 */       nbttagcompound.setInteger("CustomPotionColor", getColor());
/*     */     } 
/*     */     
/* 266 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 272 */     if (id == 0) {
/*     */       
/* 274 */       int i = getColor();
/*     */       
/* 276 */       if (i != -1)
/*     */       {
/* 278 */         double d0 = (i >> 16 & 0xFF) / 255.0D;
/* 279 */         double d1 = (i >> 8 & 0xFF) / 255.0D;
/* 280 */         double d2 = (i >> 0 & 0xFF) / 255.0D;
/*     */         
/* 282 */         for (int j = 0; j < 20; j++)
/*     */         {
/* 284 */           this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, d0, d1, d2, new int[0]);
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 290 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntityTippedArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */