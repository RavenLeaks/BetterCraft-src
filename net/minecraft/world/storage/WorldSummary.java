/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.GameType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldSummary
/*     */   implements Comparable<WorldSummary>
/*     */ {
/*     */   private final String fileName;
/*     */   private final String displayName;
/*     */   private final long lastTimePlayed;
/*     */   private final long sizeOnDisk;
/*     */   private final boolean requiresConversion;
/*     */   private final GameType theEnumGameType;
/*     */   private final boolean hardcore;
/*     */   private final boolean cheatsEnabled;
/*     */   private final String versionName;
/*     */   private final int versionId;
/*     */   private final boolean versionSnapshot;
/*     */   
/*     */   public WorldSummary(WorldInfo info, String fileNameIn, String displayNameIn, long sizeOnDiskIn, boolean requiresConversionIn) {
/*  28 */     this.fileName = fileNameIn;
/*  29 */     this.displayName = displayNameIn;
/*  30 */     this.lastTimePlayed = info.getLastTimePlayed();
/*  31 */     this.sizeOnDisk = sizeOnDiskIn;
/*  32 */     this.theEnumGameType = info.getGameType();
/*  33 */     this.requiresConversion = requiresConversionIn;
/*  34 */     this.hardcore = info.isHardcoreModeEnabled();
/*  35 */     this.cheatsEnabled = info.areCommandsAllowed();
/*  36 */     this.versionName = info.getVersionName();
/*  37 */     this.versionId = info.getVersionId();
/*  38 */     this.versionSnapshot = info.isVersionSnapshot();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFileName() {
/*  46 */     return this.fileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/*  54 */     return this.displayName;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getSizeOnDisk() {
/*  59 */     return this.sizeOnDisk;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresConversion() {
/*  64 */     return this.requiresConversion;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLastTimePlayed() {
/*  69 */     return this.lastTimePlayed;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(WorldSummary p_compareTo_1_) {
/*  74 */     if (this.lastTimePlayed < p_compareTo_1_.lastTimePlayed)
/*     */     {
/*  76 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*  80 */     return (this.lastTimePlayed > p_compareTo_1_.lastTimePlayed) ? -1 : this.fileName.compareTo(p_compareTo_1_.fileName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameType getEnumGameType() {
/*  89 */     return this.theEnumGameType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHardcoreModeEnabled() {
/*  94 */     return this.hardcore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCheatsEnabled() {
/* 102 */     return this.cheatsEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getVersionName() {
/* 107 */     return StringUtils.isNullOrEmpty(this.versionName) ? I18n.translateToLocal("selectWorld.versionUnknown") : this.versionName;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean markVersionInList() {
/* 112 */     return askToOpenWorld();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean askToOpenWorld() {
/* 117 */     return (this.versionId > 1343);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\WorldSummary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */