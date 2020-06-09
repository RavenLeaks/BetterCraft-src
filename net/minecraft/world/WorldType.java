/*     */ package net.minecraft.world;
/*     */ 
/*     */ 
/*     */ public class WorldType
/*     */ {
/*   6 */   public static final WorldType[] WORLD_TYPES = new WorldType[16];
/*     */ 
/*     */   
/*   9 */   public static final WorldType DEFAULT = (new WorldType(0, "default", 1)).setVersioned();
/*     */ 
/*     */   
/*  12 */   public static final WorldType FLAT = new WorldType(1, "flat");
/*     */ 
/*     */   
/*  15 */   public static final WorldType LARGE_BIOMES = new WorldType(2, "largeBiomes");
/*     */ 
/*     */   
/*  18 */   public static final WorldType AMPLIFIED = (new WorldType(3, "amplified")).setNotificationData();
/*  19 */   public static final WorldType CUSTOMIZED = new WorldType(4, "customized");
/*  20 */   public static final WorldType DEBUG_WORLD = new WorldType(5, "debug_all_block_states");
/*     */ 
/*     */   
/*  23 */   public static final WorldType DEFAULT_1_1 = (new WorldType(8, "default_1_1", 0)).setCanBeCreated(false);
/*     */ 
/*     */   
/*     */   private final int worldTypeId;
/*     */ 
/*     */   
/*     */   private final String worldType;
/*     */ 
/*     */   
/*     */   private final int generatorVersion;
/*     */ 
/*     */   
/*     */   private boolean canBeCreated;
/*     */   
/*     */   private boolean isWorldTypeVersioned;
/*     */   
/*     */   private boolean hasNotificationData;
/*     */ 
/*     */   
/*     */   private WorldType(int id, String name) {
/*  43 */     this(id, name, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private WorldType(int id, String name, int version) {
/*  48 */     this.worldType = name;
/*  49 */     this.generatorVersion = version;
/*  50 */     this.canBeCreated = true;
/*  51 */     this.worldTypeId = id;
/*  52 */     WORLD_TYPES[id] = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWorldTypeName() {
/*  57 */     return this.worldType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranslateName() {
/*  65 */     return "generator." + this.worldType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranslatedInfo() {
/*  73 */     return String.valueOf(getTranslateName()) + ".info";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGeneratorVersion() {
/*  81 */     return this.generatorVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldType getWorldTypeForGeneratorVersion(int version) {
/*  86 */     return (this == DEFAULT && version == 0) ? DEFAULT_1_1 : this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldType setCanBeCreated(boolean enable) {
/*  94 */     this.canBeCreated = enable;
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanBeCreated() {
/* 103 */     return this.canBeCreated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldType setVersioned() {
/* 111 */     this.isWorldTypeVersioned = true;
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVersioned() {
/* 120 */     return this.isWorldTypeVersioned;
/*     */   } public static WorldType parseWorldType(String type) {
/*     */     byte b;
/*     */     int i;
/*     */     WorldType[] arrayOfWorldType;
/* 125 */     for (i = (arrayOfWorldType = WORLD_TYPES).length, b = 0; b < i; ) { WorldType worldtype = arrayOfWorldType[b];
/*     */       
/* 127 */       if (worldtype != null && worldtype.worldType.equalsIgnoreCase(type))
/*     */       {
/* 129 */         return worldtype;
/*     */       }
/*     */       b++; }
/*     */     
/* 133 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWorldTypeID() {
/* 138 */     return this.worldTypeId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean showWorldInfoNotice() {
/* 147 */     return this.hasNotificationData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldType setNotificationData() {
/* 155 */     this.hasNotificationData = true;
/* 156 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\WorldType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */