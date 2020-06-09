/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ 
/*     */ public class CombatTracker
/*     */ {
/*  18 */   private final List<CombatEntry> combatEntries = Lists.newArrayList();
/*     */   
/*     */   private final EntityLivingBase fighter;
/*     */   
/*     */   private int lastDamageTime;
/*     */   
/*     */   private int combatStartTime;
/*     */   private int combatEndTime;
/*     */   private boolean inCombat;
/*     */   private boolean takingDamage;
/*     */   private String fallSuffix;
/*     */   
/*     */   public CombatTracker(EntityLivingBase fighterIn) {
/*  31 */     this.fighter = fighterIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void calculateFallSuffix() {
/*  36 */     resetFallSuffix();
/*     */     
/*  38 */     if (this.fighter.isOnLadder()) {
/*     */       
/*  40 */       Block block = this.fighter.world.getBlockState(new BlockPos(this.fighter.posX, (this.fighter.getEntityBoundingBox()).minY, this.fighter.posZ)).getBlock();
/*     */       
/*  42 */       if (block == Blocks.LADDER)
/*     */       {
/*  44 */         this.fallSuffix = "ladder";
/*     */       }
/*  46 */       else if (block == Blocks.VINE)
/*     */       {
/*  48 */         this.fallSuffix = "vines";
/*     */       }
/*     */     
/*  51 */     } else if (this.fighter.isInWater()) {
/*     */       
/*  53 */       this.fallSuffix = "water";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trackDamage(DamageSource damageSrc, float healthIn, float damageAmount) {
/*  62 */     reset();
/*  63 */     calculateFallSuffix();
/*  64 */     CombatEntry combatentry = new CombatEntry(damageSrc, this.fighter.ticksExisted, healthIn, damageAmount, this.fallSuffix, this.fighter.fallDistance);
/*  65 */     this.combatEntries.add(combatentry);
/*  66 */     this.lastDamageTime = this.fighter.ticksExisted;
/*  67 */     this.takingDamage = true;
/*     */     
/*  69 */     if (combatentry.isLivingDamageSrc() && !this.inCombat && this.fighter.isEntityAlive()) {
/*     */       
/*  71 */       this.inCombat = true;
/*  72 */       this.combatStartTime = this.fighter.ticksExisted;
/*  73 */       this.combatEndTime = this.combatStartTime;
/*  74 */       this.fighter.sendEnterCombat();
/*     */     } 
/*     */   }
/*     */   
/*     */   public ITextComponent getDeathMessage() {
/*     */     ITextComponent itextcomponent;
/*  80 */     if (this.combatEntries.isEmpty())
/*     */     {
/*  82 */       return (ITextComponent)new TextComponentTranslation("death.attack.generic", new Object[] { this.fighter.getDisplayName() });
/*     */     }
/*     */ 
/*     */     
/*  86 */     CombatEntry combatentry = getBestCombatEntry();
/*  87 */     CombatEntry combatentry1 = this.combatEntries.get(this.combatEntries.size() - 1);
/*  88 */     ITextComponent itextcomponent1 = combatentry1.getDamageSrcDisplayName();
/*  89 */     Entity entity = combatentry1.getDamageSrc().getEntity();
/*     */ 
/*     */     
/*  92 */     if (combatentry != null && combatentry1.getDamageSrc() == DamageSource.fall) {
/*     */       
/*  94 */       ITextComponent itextcomponent2 = combatentry.getDamageSrcDisplayName();
/*     */       
/*  96 */       if (combatentry.getDamageSrc() != DamageSource.fall && combatentry.getDamageSrc() != DamageSource.outOfWorld) {
/*     */         
/*  98 */         if (itextcomponent2 != null && (itextcomponent1 == null || !itextcomponent2.equals(itextcomponent1))) {
/*     */           
/* 100 */           Entity entity1 = combatentry.getDamageSrc().getEntity();
/* 101 */           ItemStack itemstack1 = (entity1 instanceof EntityLivingBase) ? ((EntityLivingBase)entity1).getHeldItemMainhand() : ItemStack.field_190927_a;
/*     */           
/* 103 */           if (!itemstack1.func_190926_b() && itemstack1.hasDisplayName())
/*     */           {
/* 105 */             TextComponentTranslation textComponentTranslation = new TextComponentTranslation("death.fell.assist.item", new Object[] { this.fighter.getDisplayName(), itextcomponent2, itemstack1.getTextComponent() });
/*     */           }
/*     */           else
/*     */           {
/* 109 */             TextComponentTranslation textComponentTranslation = new TextComponentTranslation("death.fell.assist", new Object[] { this.fighter.getDisplayName(), itextcomponent2 });
/*     */           }
/*     */         
/* 112 */         } else if (itextcomponent1 != null) {
/*     */           
/* 114 */           ItemStack itemstack = (entity instanceof EntityLivingBase) ? ((EntityLivingBase)entity).getHeldItemMainhand() : ItemStack.field_190927_a;
/*     */           
/* 116 */           if (!itemstack.func_190926_b() && itemstack.hasDisplayName())
/*     */           {
/* 118 */             TextComponentTranslation textComponentTranslation = new TextComponentTranslation("death.fell.finish.item", new Object[] { this.fighter.getDisplayName(), itextcomponent1, itemstack.getTextComponent() });
/*     */           }
/*     */           else
/*     */           {
/* 122 */             TextComponentTranslation textComponentTranslation = new TextComponentTranslation("death.fell.finish", new Object[] { this.fighter.getDisplayName(), itextcomponent1 });
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 127 */           TextComponentTranslation textComponentTranslation = new TextComponentTranslation("death.fell.killer", new Object[] { this.fighter.getDisplayName() });
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 132 */         TextComponentTranslation textComponentTranslation = new TextComponentTranslation("death.fell.accident." + getFallSuffix(combatentry), new Object[] { this.fighter.getDisplayName() });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 137 */       itextcomponent = combatentry1.getDamageSrc().getDeathMessage(this.fighter);
/*     */     } 
/*     */     
/* 140 */     return itextcomponent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EntityLivingBase getBestAttacker() {
/* 147 */     EntityLivingBase entitylivingbase = null;
/* 148 */     EntityPlayer entityplayer = null;
/* 149 */     float f = 0.0F;
/* 150 */     float f1 = 0.0F;
/*     */     
/* 152 */     for (CombatEntry combatentry : this.combatEntries) {
/*     */       
/* 154 */       if (combatentry.getDamageSrc().getEntity() instanceof EntityPlayer && (entityplayer == null || combatentry.getDamage() > f1)) {
/*     */         
/* 156 */         f1 = combatentry.getDamage();
/* 157 */         entityplayer = (EntityPlayer)combatentry.getDamageSrc().getEntity();
/*     */       } 
/*     */       
/* 160 */       if (combatentry.getDamageSrc().getEntity() instanceof EntityLivingBase && (entitylivingbase == null || combatentry.getDamage() > f)) {
/*     */         
/* 162 */         f = combatentry.getDamage();
/* 163 */         entitylivingbase = (EntityLivingBase)combatentry.getDamageSrc().getEntity();
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     if (entityplayer != null && f1 >= f / 3.0F)
/*     */     {
/* 169 */       return (EntityLivingBase)entityplayer;
/*     */     }
/*     */ 
/*     */     
/* 173 */     return entitylivingbase;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private CombatEntry getBestCombatEntry() {
/* 180 */     CombatEntry combatentry = null;
/* 181 */     CombatEntry combatentry1 = null;
/* 182 */     float f = 0.0F;
/* 183 */     float f1 = 0.0F;
/*     */     
/* 185 */     for (int i = 0; i < this.combatEntries.size(); i++) {
/*     */       
/* 187 */       CombatEntry combatentry2 = this.combatEntries.get(i);
/* 188 */       CombatEntry combatentry3 = (i > 0) ? this.combatEntries.get(i - 1) : null;
/*     */       
/* 190 */       if ((combatentry2.getDamageSrc() == DamageSource.fall || combatentry2.getDamageSrc() == DamageSource.outOfWorld) && combatentry2.getDamageAmount() > 0.0F && (combatentry == null || combatentry2.getDamageAmount() > f1)) {
/*     */         
/* 192 */         if (i > 0) {
/*     */           
/* 194 */           combatentry = combatentry3;
/*     */         }
/*     */         else {
/*     */           
/* 198 */           combatentry = combatentry2;
/*     */         } 
/*     */         
/* 201 */         f1 = combatentry2.getDamageAmount();
/*     */       } 
/*     */       
/* 204 */       if (combatentry2.getFallSuffix() != null && (combatentry1 == null || combatentry2.getDamage() > f)) {
/*     */         
/* 206 */         combatentry1 = combatentry2;
/* 207 */         f = combatentry2.getDamage();
/*     */       } 
/*     */     } 
/*     */     
/* 211 */     if (f1 > 5.0F && combatentry != null)
/*     */     {
/* 213 */       return combatentry;
/*     */     }
/* 215 */     if (f > 5.0F && combatentry1 != null)
/*     */     {
/* 217 */       return combatentry1;
/*     */     }
/*     */ 
/*     */     
/* 221 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getFallSuffix(CombatEntry entry) {
/* 227 */     return (entry.getFallSuffix() == null) ? "generic" : entry.getFallSuffix();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCombatDuration() {
/* 232 */     return this.inCombat ? (this.fighter.ticksExisted - this.combatStartTime) : (this.combatEndTime - this.combatStartTime);
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetFallSuffix() {
/* 237 */     this.fallSuffix = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 245 */     int i = this.inCombat ? 300 : 100;
/*     */     
/* 247 */     if (this.takingDamage && (!this.fighter.isEntityAlive() || this.fighter.ticksExisted - this.lastDamageTime > i)) {
/*     */       
/* 249 */       boolean flag = this.inCombat;
/* 250 */       this.takingDamage = false;
/* 251 */       this.inCombat = false;
/* 252 */       this.combatEndTime = this.fighter.ticksExisted;
/*     */       
/* 254 */       if (flag)
/*     */       {
/* 256 */         this.fighter.sendEndCombat();
/*     */       }
/*     */       
/* 259 */       this.combatEntries.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityLivingBase getFighter() {
/* 268 */     return this.fighter;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\CombatTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */