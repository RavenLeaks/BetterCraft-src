/*     */ package net.minecraft.crash;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class CrashReportCategory
/*     */ {
/*     */   private final CrashReport crashReport;
/*     */   private final String name;
/*  13 */   private final List<Entry> children = Lists.newArrayList();
/*  14 */   private StackTraceElement[] stackTrace = new StackTraceElement[0];
/*     */ 
/*     */   
/*     */   public CrashReportCategory(CrashReport report, String name) {
/*  18 */     this.crashReport = report;
/*  19 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getCoordinateInfo(double x, double y, double z) {
/*  24 */     return String.format("%.2f,%.2f,%.2f - %s", new Object[] { Double.valueOf(x), Double.valueOf(y), Double.valueOf(z), getCoordinateInfo(new BlockPos(x, y, z)) });
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getCoordinateInfo(BlockPos pos) {
/*  29 */     return getCoordinateInfo(pos.getX(), pos.getY(), pos.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getCoordinateInfo(int x, int y, int z) {
/*  34 */     StringBuilder stringbuilder = new StringBuilder();
/*     */ 
/*     */     
/*     */     try {
/*  38 */       stringbuilder.append(String.format("World: (%d,%d,%d)", new Object[] { Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z) }));
/*     */     }
/*  40 */     catch (Throwable var16) {
/*     */       
/*  42 */       stringbuilder.append("(Error finding world loc)");
/*     */     } 
/*     */     
/*  45 */     stringbuilder.append(", ");
/*     */ 
/*     */     
/*     */     try {
/*  49 */       int i = x >> 4;
/*  50 */       int j = z >> 4;
/*  51 */       int k = x & 0xF;
/*  52 */       int l = y >> 4;
/*  53 */       int i1 = z & 0xF;
/*  54 */       int j1 = i << 4;
/*  55 */       int k1 = j << 4;
/*  56 */       int l1 = (i + 1 << 4) - 1;
/*  57 */       int i2 = (j + 1 << 4) - 1;
/*  58 */       stringbuilder.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", new Object[] { Integer.valueOf(k), Integer.valueOf(l), Integer.valueOf(i1), Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(j1), Integer.valueOf(k1), Integer.valueOf(l1), Integer.valueOf(i2) }));
/*     */     }
/*  60 */     catch (Throwable var15) {
/*     */       
/*  62 */       stringbuilder.append("(Error finding chunk loc)");
/*     */     } 
/*     */     
/*  65 */     stringbuilder.append(", ");
/*     */ 
/*     */     
/*     */     try {
/*  69 */       int k2 = x >> 9;
/*  70 */       int l2 = z >> 9;
/*  71 */       int i3 = k2 << 5;
/*  72 */       int j3 = l2 << 5;
/*  73 */       int k3 = (k2 + 1 << 5) - 1;
/*  74 */       int l3 = (l2 + 1 << 5) - 1;
/*  75 */       int i4 = k2 << 9;
/*  76 */       int j4 = l2 << 9;
/*  77 */       int k4 = (k2 + 1 << 9) - 1;
/*  78 */       int j2 = (l2 + 1 << 9) - 1;
/*  79 */       stringbuilder.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", new Object[] { Integer.valueOf(k2), Integer.valueOf(l2), Integer.valueOf(i3), Integer.valueOf(j3), Integer.valueOf(k3), Integer.valueOf(l3), Integer.valueOf(i4), Integer.valueOf(j4), Integer.valueOf(k4), Integer.valueOf(j2) }));
/*     */     }
/*  81 */     catch (Throwable var14) {
/*     */       
/*  83 */       stringbuilder.append("(Error finding world loc)");
/*     */     } 
/*     */     
/*  86 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDetail(String nameIn, ICrashReportDetail<String> detail) {
/*     */     try {
/*  93 */       addCrashSection(nameIn, detail.call());
/*     */     }
/*  95 */     catch (Throwable throwable) {
/*     */       
/*  97 */       addCrashSectionThrowable(nameIn, throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCrashSection(String sectionName, Object value) {
/* 106 */     this.children.add(new Entry(sectionName, value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCrashSectionThrowable(String sectionName, Throwable throwable) {
/* 114 */     addCrashSection(sectionName, throwable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrunedStackTrace(int size) {
/* 123 */     StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
/*     */     
/* 125 */     if (astacktraceelement.length <= 0)
/*     */     {
/* 127 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 131 */     this.stackTrace = new StackTraceElement[astacktraceelement.length - 3 - size];
/* 132 */     System.arraycopy(astacktraceelement, 3 + size, this.stackTrace, 0, this.stackTrace.length);
/* 133 */     return this.stackTrace.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean firstTwoElementsOfStackTraceMatch(StackTraceElement s1, StackTraceElement s2) {
/* 142 */     if (this.stackTrace.length != 0 && s1 != null) {
/*     */       
/* 144 */       StackTraceElement stacktraceelement = this.stackTrace[0];
/*     */       
/* 146 */       if (stacktraceelement.isNativeMethod() == s1.isNativeMethod() && stacktraceelement.getClassName().equals(s1.getClassName()) && stacktraceelement.getFileName().equals(s1.getFileName()) && stacktraceelement.getMethodName().equals(s1.getMethodName())) {
/*     */         
/* 148 */         if (((s2 != null) ? true : false) != ((this.stackTrace.length > 1) ? true : false))
/*     */         {
/* 150 */           return false;
/*     */         }
/* 152 */         if (s2 != null && !this.stackTrace[1].equals(s2))
/*     */         {
/* 154 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 158 */         this.stackTrace[0] = s1;
/* 159 */         return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 164 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trimStackTraceEntriesFromBottom(int amount) {
/* 178 */     StackTraceElement[] astacktraceelement = new StackTraceElement[this.stackTrace.length - amount];
/* 179 */     System.arraycopy(this.stackTrace, 0, astacktraceelement, 0, astacktraceelement.length);
/* 180 */     this.stackTrace = astacktraceelement;
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendToStringBuilder(StringBuilder builder) {
/* 185 */     builder.append("-- ").append(this.name).append(" --\n");
/* 186 */     builder.append("Details:");
/*     */     
/* 188 */     for (Entry crashreportcategory$entry : this.children) {
/*     */       
/* 190 */       builder.append("\n\t");
/* 191 */       builder.append(crashreportcategory$entry.getKey());
/* 192 */       builder.append(": ");
/* 193 */       builder.append(crashreportcategory$entry.getValue());
/*     */     } 
/*     */     
/* 196 */     if (this.stackTrace != null && this.stackTrace.length > 0) {
/*     */       
/* 198 */       builder.append("\nStacktrace:"); byte b; int i;
/*     */       StackTraceElement[] arrayOfStackTraceElement;
/* 200 */       for (i = (arrayOfStackTraceElement = this.stackTrace).length, b = 0; b < i; ) { StackTraceElement stacktraceelement = arrayOfStackTraceElement[b];
/*     */         
/* 202 */         builder.append("\n\tat ");
/* 203 */         builder.append(stacktraceelement);
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public StackTraceElement[] getStackTrace() {
/* 210 */     return this.stackTrace;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addBlockInfo(CrashReportCategory category, final BlockPos pos, final Block blockIn, final int blockData) {
/* 215 */     final int i = Block.getIdFromBlock(blockIn);
/* 216 */     category.setDetail("Block type", new ICrashReportDetail<String>()
/*     */         {
/*     */           
/*     */           public String call() throws Exception
/*     */           {
/*     */             try {
/* 222 */               return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(this.val$i), this.val$blockIn.getUnlocalizedName(), this.val$blockIn.getClass().getCanonicalName() });
/*     */             }
/* 224 */             catch (Throwable var2) {
/*     */               
/* 226 */               return "ID #" + i;
/*     */             } 
/*     */           }
/*     */         });
/* 230 */     category.setDetail("Block data value", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 234 */             if (blockData < 0)
/*     */             {
/* 236 */               return "Unknown? (Got " + blockData + ")";
/*     */             }
/*     */ 
/*     */             
/* 240 */             String s = String.format("%4s", new Object[] { Integer.toBinaryString(this.val$blockData) }).replace(" ", "0");
/* 241 */             return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { Integer.valueOf(this.val$blockData), s });
/*     */           }
/*     */         });
/*     */     
/* 245 */     category.setDetail("Block location", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 249 */             return CrashReportCategory.getCoordinateInfo(pos);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addBlockInfo(CrashReportCategory category, final BlockPos pos, final IBlockState state) {
/* 256 */     category.setDetail("Block", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 260 */             return state.toString();
/*     */           }
/*     */         });
/* 263 */     category.setDetail("Block location", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 267 */             return CrashReportCategory.getCoordinateInfo(pos);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   static class Entry
/*     */   {
/*     */     private final String key;
/*     */     private final String value;
/*     */     
/*     */     public Entry(String key, Object value) {
/* 279 */       this.key = key;
/*     */       
/* 281 */       if (value == null) {
/*     */         
/* 283 */         this.value = "~~NULL~~";
/*     */       }
/* 285 */       else if (value instanceof Throwable) {
/*     */         
/* 287 */         Throwable throwable = (Throwable)value;
/* 288 */         this.value = "~~ERROR~~ " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
/*     */       }
/*     */       else {
/*     */         
/* 292 */         this.value = value.toString();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String getKey() {
/* 298 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getValue() {
/* 303 */       return this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\crash\CrashReportCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */