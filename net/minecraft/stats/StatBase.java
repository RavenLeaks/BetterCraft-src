/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.scoreboard.IScoreCriteria;
/*     */ import net.minecraft.scoreboard.ScoreCriteriaStat;
/*     */ import net.minecraft.util.IJsonSerializable;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatBase
/*     */ {
/*     */   public final String statId;
/*     */   private final ITextComponent statName;
/*     */   public boolean isIndependent;
/*     */   private final IStatType formatter;
/*     */   private final IScoreCriteria objectiveCriteria;
/*     */   private Class<? extends IJsonSerializable> serializableClazz;
/*  23 */   private static final NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.US);
/*  24 */   public static IStatType simpleStatType = new IStatType()
/*     */     {
/*     */       public String format(int number)
/*     */       {
/*  28 */         return StatBase.numberFormat.format(number);
/*     */       }
/*     */     };
/*  31 */   private static final DecimalFormat decimalFormat = new DecimalFormat("########0.00");
/*  32 */   public static IStatType timeStatType = new IStatType()
/*     */     {
/*     */       public String format(int number)
/*     */       {
/*  36 */         double d0 = number / 20.0D;
/*  37 */         double d1 = d0 / 60.0D;
/*  38 */         double d2 = d1 / 60.0D;
/*  39 */         double d3 = d2 / 24.0D;
/*  40 */         double d4 = d3 / 365.0D;
/*     */         
/*  42 */         if (d4 > 0.5D)
/*     */         {
/*  44 */           return String.valueOf(StatBase.decimalFormat.format(d4)) + " y";
/*     */         }
/*  46 */         if (d3 > 0.5D)
/*     */         {
/*  48 */           return String.valueOf(StatBase.decimalFormat.format(d3)) + " d";
/*     */         }
/*  50 */         if (d2 > 0.5D)
/*     */         {
/*  52 */           return String.valueOf(StatBase.decimalFormat.format(d2)) + " h";
/*     */         }
/*     */ 
/*     */         
/*  56 */         return (d1 > 0.5D) ? (String.valueOf(StatBase.decimalFormat.format(d1)) + " m") : (String.valueOf(d0) + " s");
/*     */       }
/*     */     };
/*     */   
/*  60 */   public static IStatType distanceStatType = new IStatType()
/*     */     {
/*     */       public String format(int number)
/*     */       {
/*  64 */         double d0 = number / 100.0D;
/*  65 */         double d1 = d0 / 1000.0D;
/*     */         
/*  67 */         if (d1 > 0.5D)
/*     */         {
/*  69 */           return String.valueOf(StatBase.decimalFormat.format(d1)) + " km";
/*     */         }
/*     */ 
/*     */         
/*  73 */         return (d0 > 0.5D) ? (String.valueOf(StatBase.decimalFormat.format(d0)) + " m") : (String.valueOf(number) + " cm");
/*     */       }
/*     */     };
/*     */   
/*  77 */   public static IStatType divideByTen = new IStatType()
/*     */     {
/*     */       public String format(int number)
/*     */       {
/*  81 */         return StatBase.decimalFormat.format(number * 0.1D);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public StatBase(String statIdIn, ITextComponent statNameIn, IStatType formatterIn) {
/*  87 */     this.statId = statIdIn;
/*  88 */     this.statName = statNameIn;
/*  89 */     this.formatter = formatterIn;
/*  90 */     this.objectiveCriteria = (IScoreCriteria)new ScoreCriteriaStat(this);
/*  91 */     IScoreCriteria.INSTANCES.put(this.objectiveCriteria.getName(), this.objectiveCriteria);
/*     */   }
/*     */ 
/*     */   
/*     */   public StatBase(String statIdIn, ITextComponent statNameIn) {
/*  96 */     this(statIdIn, statNameIn, simpleStatType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatBase initIndependentStat() {
/* 105 */     this.isIndependent = true;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatBase registerStat() {
/* 114 */     if (StatList.ID_TO_STAT_MAP.containsKey(this.statId))
/*     */     {
/* 116 */       throw new RuntimeException("Duplicate stat id: \"" + ((StatBase)StatList.ID_TO_STAT_MAP.get(this.statId)).statName + "\" and \"" + this.statName + "\" at id " + this.statId);
/*     */     }
/*     */ 
/*     */     
/* 120 */     StatList.ALL_STATS.add(this);
/* 121 */     StatList.ID_TO_STAT_MAP.put(this.statId, this);
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String format(int number) {
/* 128 */     return this.formatter.format(number);
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent getStatName() {
/* 133 */     ITextComponent itextcomponent = this.statName.createCopy();
/* 134 */     itextcomponent.getStyle().setColor(TextFormatting.GRAY);
/* 135 */     return itextcomponent;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 140 */     if (this == p_equals_1_)
/*     */     {
/* 142 */       return true;
/*     */     }
/* 144 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*     */       
/* 146 */       StatBase statbase = (StatBase)p_equals_1_;
/* 147 */       return this.statId.equals(statbase.statId);
/*     */     } 
/*     */ 
/*     */     
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 157 */     return this.statId.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 162 */     return "Stat{id=" + this.statId + ", nameId=" + this.statName + ", awardLocallyOnly=" + this.isIndependent + ", formatter=" + this.formatter + ", objectiveCriteria=" + this.objectiveCriteria + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScoreCriteria getCriteria() {
/* 170 */     return this.objectiveCriteria;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends IJsonSerializable> getSerializableClazz() {
/* 175 */     return this.serializableClazz;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\stats\StatBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */