/*     */ package net.minecraft.potion;
/*     */ 
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class PotionEffect
/*     */   implements Comparable<PotionEffect> {
/*  11 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final Potion potion;
/*     */ 
/*     */   
/*     */   private int duration;
/*     */ 
/*     */   
/*     */   private int amplifier;
/*     */   
/*     */   private boolean isSplashPotion;
/*     */   
/*     */   private boolean isAmbient;
/*     */   
/*     */   private boolean isPotionDurationMax;
/*     */   
/*     */   private boolean showParticles;
/*     */ 
/*     */   
/*     */   public PotionEffect(Potion potionIn) {
/*  32 */     this(potionIn, 0, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public PotionEffect(Potion potionIn, int durationIn) {
/*  37 */     this(potionIn, durationIn, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public PotionEffect(Potion potionIn, int durationIn, int amplifierIn) {
/*  42 */     this(potionIn, durationIn, amplifierIn, false, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public PotionEffect(Potion potionIn, int durationIn, int amplifierIn, boolean ambientIn, boolean showParticlesIn) {
/*  47 */     this.potion = potionIn;
/*  48 */     this.duration = durationIn;
/*  49 */     this.amplifier = amplifierIn;
/*  50 */     this.isAmbient = ambientIn;
/*  51 */     this.showParticles = showParticlesIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public PotionEffect(PotionEffect other) {
/*  56 */     this.potion = other.potion;
/*  57 */     this.duration = other.duration;
/*  58 */     this.amplifier = other.amplifier;
/*  59 */     this.isAmbient = other.isAmbient;
/*  60 */     this.showParticles = other.showParticles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void combine(PotionEffect other) {
/*  69 */     if (this.potion != other.potion)
/*     */     {
/*  71 */       LOGGER.warn("This method should only be called for matching effects!");
/*     */     }
/*     */     
/*  74 */     if (other.amplifier > this.amplifier) {
/*     */       
/*  76 */       this.amplifier = other.amplifier;
/*  77 */       this.duration = other.duration;
/*     */     }
/*  79 */     else if (other.amplifier == this.amplifier && this.duration < other.duration) {
/*     */       
/*  81 */       this.duration = other.duration;
/*     */     }
/*  83 */     else if (!other.isAmbient && this.isAmbient) {
/*     */       
/*  85 */       this.isAmbient = other.isAmbient;
/*     */     } 
/*     */     
/*  88 */     this.showParticles = other.showParticles;
/*     */   }
/*     */ 
/*     */   
/*     */   public Potion getPotion() {
/*  93 */     return this.potion;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDuration() {
/*  98 */     return this.duration;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAmplifier() {
/* 103 */     return this.amplifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsAmbient() {
/* 111 */     return this.isAmbient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesShowParticles() {
/* 119 */     return this.showParticles;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onUpdate(EntityLivingBase entityIn) {
/* 124 */     if (this.duration > 0) {
/*     */       
/* 126 */       if (this.potion.isReady(this.duration, this.amplifier))
/*     */       {
/* 128 */         performEffect(entityIn);
/*     */       }
/*     */       
/* 131 */       deincrementDuration();
/*     */     } 
/*     */     
/* 134 */     return (this.duration > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private int deincrementDuration() {
/* 139 */     return --this.duration;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performEffect(EntityLivingBase entityIn) {
/* 144 */     if (this.duration > 0)
/*     */     {
/* 146 */       this.potion.performEffect(entityIn, this.amplifier);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEffectName() {
/* 152 */     return this.potion.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     String s;
/* 159 */     if (this.amplifier > 0) {
/*     */       
/* 161 */       s = String.valueOf(getEffectName()) + " x " + (this.amplifier + 1) + ", Duration: " + this.duration;
/*     */     }
/*     */     else {
/*     */       
/* 165 */       s = String.valueOf(getEffectName()) + ", Duration: " + this.duration;
/*     */     } 
/*     */     
/* 168 */     if (this.isSplashPotion)
/*     */     {
/* 170 */       s = String.valueOf(s) + ", Splash: true";
/*     */     }
/*     */     
/* 173 */     if (!this.showParticles)
/*     */     {
/* 175 */       s = String.valueOf(s) + ", Particles: false";
/*     */     }
/*     */     
/* 178 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 183 */     if (this == p_equals_1_)
/*     */     {
/* 185 */       return true;
/*     */     }
/* 187 */     if (!(p_equals_1_ instanceof PotionEffect))
/*     */     {
/* 189 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 193 */     PotionEffect potioneffect = (PotionEffect)p_equals_1_;
/* 194 */     return (this.duration == potioneffect.duration && this.amplifier == potioneffect.amplifier && this.isSplashPotion == potioneffect.isSplashPotion && this.isAmbient == potioneffect.isAmbient && this.potion.equals(potioneffect.potion));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 200 */     int i = this.potion.hashCode();
/* 201 */     i = 31 * i + this.duration;
/* 202 */     i = 31 * i + this.amplifier;
/* 203 */     i = 31 * i + (this.isSplashPotion ? 1 : 0);
/* 204 */     i = 31 * i + (this.isAmbient ? 1 : 0);
/* 205 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeCustomPotionEffectToNBT(NBTTagCompound nbt) {
/* 213 */     nbt.setByte("Id", (byte)Potion.getIdFromPotion(getPotion()));
/* 214 */     nbt.setByte("Amplifier", (byte)getAmplifier());
/* 215 */     nbt.setInteger("Duration", getDuration());
/* 216 */     nbt.setBoolean("Ambient", getIsAmbient());
/* 217 */     nbt.setBoolean("ShowParticles", doesShowParticles());
/* 218 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PotionEffect readCustomPotionEffectFromNBT(NBTTagCompound nbt) {
/* 226 */     int i = nbt.getByte("Id");
/* 227 */     Potion potion = Potion.getPotionById(i);
/*     */     
/* 229 */     if (potion == null)
/*     */     {
/* 231 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 235 */     int j = nbt.getByte("Amplifier");
/* 236 */     int k = nbt.getInteger("Duration");
/* 237 */     boolean flag = nbt.getBoolean("Ambient");
/* 238 */     boolean flag1 = true;
/*     */     
/* 240 */     if (nbt.hasKey("ShowParticles", 1))
/*     */     {
/* 242 */       flag1 = nbt.getBoolean("ShowParticles");
/*     */     }
/*     */     
/* 245 */     return new PotionEffect(potion, k, (j < 0) ? 0 : j, flag, flag1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPotionDurationMax(boolean maxDuration) {
/* 254 */     this.isPotionDurationMax = maxDuration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsPotionDurationMax() {
/* 262 */     return this.isPotionDurationMax;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(PotionEffect p_compareTo_1_) {
/* 267 */     int i = 32147;
/* 268 */     return ((getDuration() <= 32147 || p_compareTo_1_.getDuration() <= 32147) && (!getIsAmbient() || !p_compareTo_1_.getIsAmbient())) ? ComparisonChain.start().compare(Boolean.valueOf(getIsAmbient()), Boolean.valueOf(p_compareTo_1_.getIsAmbient())).compare(getDuration(), p_compareTo_1_.getDuration()).compare(getPotion().getLiquidColor(), p_compareTo_1_.getPotion().getLiquidColor()).result() : ComparisonChain.start().compare(Boolean.valueOf(getIsAmbient()), Boolean.valueOf(p_compareTo_1_.getIsAmbient())).compare(getPotion().getLiquidColor(), p_compareTo_1_.getPotion().getLiquidColor()).result();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\potion\PotionEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */