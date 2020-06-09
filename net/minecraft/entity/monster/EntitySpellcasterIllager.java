/*     */ package net.minecraft.entity.monster;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntitySpellcasterIllager extends AbstractIllager {
/*  16 */   private static final DataParameter<Byte> field_193088_c = EntityDataManager.createKey(EntitySpellcasterIllager.class, DataSerializers.BYTE);
/*     */   protected int field_193087_b;
/*  18 */   private SpellType field_193089_bx = SpellType.NONE;
/*     */ 
/*     */   
/*     */   public EntitySpellcasterIllager(World p_i47506_1_) {
/*  22 */     super(p_i47506_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  27 */     super.entityInit();
/*  28 */     this.dataManager.register(field_193088_c, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/*  36 */     super.readEntityFromNBT(compound);
/*  37 */     this.field_193087_b = compound.getInteger("SpellTicks");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  45 */     super.writeEntityToNBT(compound);
/*  46 */     compound.setInteger("SpellTicks", this.field_193087_b);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractIllager.IllagerArmPose func_193077_p() {
/*  51 */     return func_193082_dl() ? AbstractIllager.IllagerArmPose.SPELLCASTING : AbstractIllager.IllagerArmPose.CROSSED;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193082_dl() {
/*  56 */     if (this.world.isRemote)
/*     */     {
/*  58 */       return (((Byte)this.dataManager.get(field_193088_c)).byteValue() > 0);
/*     */     }
/*     */ 
/*     */     
/*  62 */     return (this.field_193087_b > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_193081_a(SpellType p_193081_1_) {
/*  68 */     this.field_193089_bx = p_193081_1_;
/*  69 */     this.dataManager.set(field_193088_c, Byte.valueOf((byte)p_193081_1_.field_193345_g));
/*     */   }
/*     */ 
/*     */   
/*     */   protected SpellType func_193083_dm() {
/*  74 */     return !this.world.isRemote ? this.field_193089_bx : SpellType.func_193337_a(((Byte)this.dataManager.get(field_193088_c)).byteValue());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/*  79 */     super.updateAITasks();
/*     */     
/*  81 */     if (this.field_193087_b > 0)
/*     */     {
/*  83 */       this.field_193087_b--;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  92 */     super.onUpdate();
/*     */     
/*  94 */     if (this.world.isRemote && func_193082_dl()) {
/*     */       
/*  96 */       SpellType entityspellcasterillager$spelltype = func_193083_dm();
/*  97 */       double d0 = entityspellcasterillager$spelltype.field_193346_h[0];
/*  98 */       double d1 = entityspellcasterillager$spelltype.field_193346_h[1];
/*  99 */       double d2 = entityspellcasterillager$spelltype.field_193346_h[2];
/* 100 */       float f = this.renderYawOffset * 0.017453292F + MathHelper.cos(this.ticksExisted * 0.6662F) * 0.25F;
/* 101 */       float f1 = MathHelper.cos(f);
/* 102 */       float f2 = MathHelper.sin(f);
/* 103 */       this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + f1 * 0.6D, this.posY + 1.8D, this.posZ + f2 * 0.6D, d0, d1, d2, new int[0]);
/* 104 */       this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX - f1 * 0.6D, this.posY + 1.8D, this.posZ - f2 * 0.6D, d0, d1, d2, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int func_193085_dn() {
/* 110 */     return this.field_193087_b;
/*     */   }
/*     */   
/*     */   protected abstract SoundEvent func_193086_dk();
/*     */   
/*     */   public class AICastingApell
/*     */     extends EntityAIBase
/*     */   {
/*     */     public AICastingApell() {
/* 119 */       setMutexBits(3);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 124 */       return (EntitySpellcasterIllager.this.func_193085_dn() > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 129 */       super.startExecuting();
/* 130 */       EntitySpellcasterIllager.this.navigator.clearPathEntity();
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 135 */       super.resetTask();
/* 136 */       EntitySpellcasterIllager.this.func_193081_a(EntitySpellcasterIllager.SpellType.NONE);
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 141 */       if (EntitySpellcasterIllager.this.getAttackTarget() != null)
/*     */       {
/* 143 */         EntitySpellcasterIllager.this.getLookHelper().setLookPositionWithEntity((Entity)EntitySpellcasterIllager.this.getAttackTarget(), EntitySpellcasterIllager.this.getHorizontalFaceSpeed(), EntitySpellcasterIllager.this.getVerticalFaceSpeed());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public abstract class AIUseSpell
/*     */     extends EntityAIBase
/*     */   {
/*     */     protected int field_193321_c;
/*     */     protected int field_193322_d;
/*     */     
/*     */     public boolean shouldExecute() {
/* 155 */       if (EntitySpellcasterIllager.this.getAttackTarget() == null)
/*     */       {
/* 157 */         return false;
/*     */       }
/* 159 */       if (EntitySpellcasterIllager.this.func_193082_dl())
/*     */       {
/* 161 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 165 */       return (EntitySpellcasterIllager.this.ticksExisted >= this.field_193322_d);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 171 */       return (EntitySpellcasterIllager.this.getAttackTarget() != null && this.field_193321_c > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 176 */       this.field_193321_c = func_190867_m();
/* 177 */       EntitySpellcasterIllager.this.field_193087_b = func_190869_f();
/* 178 */       this.field_193322_d = EntitySpellcasterIllager.this.ticksExisted + func_190872_i();
/* 179 */       SoundEvent soundevent = func_190871_k();
/*     */       
/* 181 */       if (soundevent != null)
/*     */       {
/* 183 */         EntitySpellcasterIllager.this.playSound(soundevent, 1.0F, 1.0F);
/*     */       }
/*     */       
/* 186 */       EntitySpellcasterIllager.this.func_193081_a(func_193320_l());
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 191 */       this.field_193321_c--;
/*     */       
/* 193 */       if (this.field_193321_c == 0) {
/*     */         
/* 195 */         func_190868_j();
/* 196 */         EntitySpellcasterIllager.this.playSound(EntitySpellcasterIllager.this.func_193086_dk(), 1.0F, 1.0F);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected abstract void func_190868_j();
/*     */     
/*     */     protected int func_190867_m() {
/* 204 */       return 20;
/*     */     }
/*     */     
/*     */     protected abstract int func_190869_f();
/*     */     
/*     */     protected abstract int func_190872_i();
/*     */     
/*     */     @Nullable
/*     */     protected abstract SoundEvent func_190871_k();
/*     */     
/*     */     protected abstract EntitySpellcasterIllager.SpellType func_193320_l();
/*     */   }
/*     */   
/*     */   public enum SpellType
/*     */   {
/* 219 */     NONE(0, 0.0D, 0.0D, 0.0D),
/* 220 */     SUMMON_VEX(1, 0.7D, 0.7D, 0.8D),
/* 221 */     FANGS(2, 0.4D, 0.3D, 0.35D),
/* 222 */     WOLOLO(3, 0.7D, 0.5D, 0.2D),
/* 223 */     DISAPPEAR(4, 0.3D, 0.3D, 0.8D),
/* 224 */     BLINDNESS(5, 0.1D, 0.1D, 0.2D);
/*     */     
/*     */     private final int field_193345_g;
/*     */     
/*     */     private final double[] field_193346_h;
/*     */     
/*     */     SpellType(int p_i47561_3_, double p_i47561_4_, double p_i47561_6_, double p_i47561_8_) {
/* 231 */       this.field_193345_g = p_i47561_3_;
/* 232 */       this.field_193346_h = new double[] { p_i47561_4_, p_i47561_6_, p_i47561_8_ };
/*     */     } public static SpellType func_193337_a(int p_193337_0_) {
/*     */       byte b;
/*     */       int i;
/*     */       SpellType[] arrayOfSpellType;
/* 237 */       for (i = (arrayOfSpellType = values()).length, b = 0; b < i; ) { SpellType entityspellcasterillager$spelltype = arrayOfSpellType[b];
/*     */         
/* 239 */         if (p_193337_0_ == entityspellcasterillager$spelltype.field_193345_g)
/*     */         {
/* 241 */           return entityspellcasterillager$spelltype;
/*     */         }
/*     */         b++; }
/*     */       
/* 245 */       return NONE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntitySpellcasterIllager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */