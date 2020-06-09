/*     */ package net.minecraft.block.state.pattern;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.state.BlockWorldState;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ public class FactoryBlockPattern
/*     */ {
/*  18 */   private static final Joiner COMMA_JOIN = Joiner.on(",");
/*  19 */   private final List<String[]> depth = Lists.newArrayList();
/*  20 */   private final Map<Character, Predicate<BlockWorldState>> symbolMap = Maps.newHashMap();
/*     */   
/*     */   private int aisleHeight;
/*     */   private int rowWidth;
/*     */   
/*     */   private FactoryBlockPattern() {
/*  26 */     this.symbolMap.put(Character.valueOf(' '), Predicates.alwaysTrue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FactoryBlockPattern aisle(String... aisle) {
/*  35 */     if (!ArrayUtils.isEmpty((Object[])aisle) && !StringUtils.isEmpty(aisle[0])) {
/*     */       
/*  37 */       if (this.depth.isEmpty()) {
/*     */         
/*  39 */         this.aisleHeight = aisle.length;
/*  40 */         this.rowWidth = aisle[0].length();
/*     */       } 
/*     */       
/*  43 */       if (aisle.length != this.aisleHeight)
/*     */       {
/*  45 */         throw new IllegalArgumentException("Expected aisle with height of " + this.aisleHeight + ", but was given one with a height of " + aisle.length + ")"); } 
/*     */       byte b;
/*     */       int i;
/*     */       String[] arrayOfString;
/*  49 */       for (i = (arrayOfString = aisle).length, b = 0; b < i; ) { String s = arrayOfString[b];
/*     */         
/*  51 */         if (s.length() != this.rowWidth)
/*     */         {
/*  53 */           throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.rowWidth + ", found one with " + s.length() + ")"); }  byte b1;
/*     */         int j;
/*     */         char[] arrayOfChar;
/*  56 */         for (j = (arrayOfChar = s.toCharArray()).length, b1 = 0; b1 < j; ) { char c0 = arrayOfChar[b1];
/*     */           
/*  58 */           if (!this.symbolMap.containsKey(Character.valueOf(c0)))
/*     */           {
/*  60 */             this.symbolMap.put(Character.valueOf(c0), null); } 
/*     */           b1++; }
/*     */         
/*     */         b++; }
/*     */       
/*  65 */       this.depth.add(aisle);
/*  66 */       return this;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  71 */     throw new IllegalArgumentException("Empty pattern for aisle");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FactoryBlockPattern start() {
/*  77 */     return new FactoryBlockPattern();
/*     */   }
/*     */ 
/*     */   
/*     */   public FactoryBlockPattern where(char symbol, Predicate<BlockWorldState> blockMatcher) {
/*  82 */     this.symbolMap.put(Character.valueOf(symbol), blockMatcher);
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPattern build() {
/*  88 */     return new BlockPattern(makePredicateArray());
/*     */   }
/*     */ 
/*     */   
/*     */   private Predicate<BlockWorldState>[][][] makePredicateArray() {
/*  93 */     checkMissingPredicates();
/*  94 */     Predicate[][][] predicate = (Predicate[][][])Array.newInstance(Predicate.class, new int[] { this.depth.size(), this.aisleHeight, this.rowWidth });
/*     */     
/*  96 */     for (int i = 0; i < this.depth.size(); i++) {
/*     */       
/*  98 */       for (int j = 0; j < this.aisleHeight; j++) {
/*     */         
/* 100 */         for (int k = 0; k < this.rowWidth; k++)
/*     */         {
/* 102 */           predicate[i][j][k] = this.symbolMap.get(Character.valueOf(((String[])this.depth.get(i))[j].charAt(k)));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     return (Predicate<BlockWorldState>[][][])predicate;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkMissingPredicates() {
/* 112 */     List<Character> list = Lists.newArrayList();
/*     */     
/* 114 */     for (Map.Entry<Character, Predicate<BlockWorldState>> entry : this.symbolMap.entrySet()) {
/*     */       
/* 116 */       if (entry.getValue() == null)
/*     */       {
/* 118 */         list.add(entry.getKey());
/*     */       }
/*     */     } 
/*     */     
/* 122 */     if (!list.isEmpty())
/*     */     {
/* 124 */       throw new IllegalStateException("Predicates for character(s) " + COMMA_JOIN.join(list) + " are missing");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\state\pattern\FactoryBlockPattern.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */