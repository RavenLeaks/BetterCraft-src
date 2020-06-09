/*     */ package net.minecraft.block.material;
/*     */ 
/*     */ public class Material
/*     */ {
/*   5 */   public static final Material AIR = new MaterialTransparent(MapColor.AIR);
/*   6 */   public static final Material GRASS = new Material(MapColor.GRASS);
/*   7 */   public static final Material GROUND = new Material(MapColor.DIRT);
/*   8 */   public static final Material WOOD = (new Material(MapColor.WOOD)).setBurning();
/*   9 */   public static final Material ROCK = (new Material(MapColor.STONE)).setRequiresTool();
/*  10 */   public static final Material IRON = (new Material(MapColor.IRON)).setRequiresTool();
/*  11 */   public static final Material ANVIL = (new Material(MapColor.IRON)).setRequiresTool().setImmovableMobility();
/*  12 */   public static final Material WATER = (new MaterialLiquid(MapColor.WATER)).setNoPushMobility();
/*  13 */   public static final Material LAVA = (new MaterialLiquid(MapColor.TNT)).setNoPushMobility();
/*  14 */   public static final Material LEAVES = (new Material(MapColor.FOLIAGE)).setBurning().setTranslucent().setNoPushMobility();
/*  15 */   public static final Material PLANTS = (new MaterialLogic(MapColor.FOLIAGE)).setNoPushMobility();
/*  16 */   public static final Material VINE = (new MaterialLogic(MapColor.FOLIAGE)).setBurning().setNoPushMobility().setReplaceable();
/*  17 */   public static final Material SPONGE = new Material(MapColor.YELLOW);
/*  18 */   public static final Material CLOTH = (new Material(MapColor.CLOTH)).setBurning();
/*  19 */   public static final Material FIRE = (new MaterialTransparent(MapColor.AIR)).setNoPushMobility();
/*  20 */   public static final Material SAND = new Material(MapColor.SAND);
/*  21 */   public static final Material CIRCUITS = (new MaterialLogic(MapColor.AIR)).setNoPushMobility();
/*  22 */   public static final Material CARPET = (new MaterialLogic(MapColor.CLOTH)).setBurning();
/*  23 */   public static final Material GLASS = (new Material(MapColor.AIR)).setTranslucent().setAdventureModeExempt();
/*  24 */   public static final Material REDSTONE_LIGHT = (new Material(MapColor.AIR)).setAdventureModeExempt();
/*  25 */   public static final Material TNT = (new Material(MapColor.TNT)).setBurning().setTranslucent();
/*  26 */   public static final Material CORAL = (new Material(MapColor.FOLIAGE)).setNoPushMobility();
/*  27 */   public static final Material ICE = (new Material(MapColor.ICE)).setTranslucent().setAdventureModeExempt();
/*  28 */   public static final Material PACKED_ICE = (new Material(MapColor.ICE)).setAdventureModeExempt();
/*  29 */   public static final Material SNOW = (new MaterialLogic(MapColor.SNOW)).setReplaceable().setTranslucent().setRequiresTool().setNoPushMobility();
/*     */ 
/*     */   
/*  32 */   public static final Material CRAFTED_SNOW = (new Material(MapColor.SNOW)).setRequiresTool();
/*  33 */   public static final Material CACTUS = (new Material(MapColor.FOLIAGE)).setTranslucent().setNoPushMobility();
/*  34 */   public static final Material CLAY = new Material(MapColor.CLAY);
/*  35 */   public static final Material GOURD = (new Material(MapColor.FOLIAGE)).setNoPushMobility();
/*  36 */   public static final Material DRAGON_EGG = (new Material(MapColor.FOLIAGE)).setNoPushMobility();
/*  37 */   public static final Material PORTAL = (new MaterialPortal(MapColor.AIR)).setImmovableMobility();
/*  38 */   public static final Material CAKE = (new Material(MapColor.AIR)).setNoPushMobility();
/*  39 */   public static final Material WEB = (new Material(MapColor.CLOTH)
/*     */     {
/*     */       public boolean blocksMovement()
/*     */       {
/*  43 */         return false;
/*     */       }
/*  45 */     }).setRequiresTool().setNoPushMobility();
/*     */ 
/*     */   
/*  48 */   public static final Material PISTON = (new Material(MapColor.STONE)).setImmovableMobility();
/*  49 */   public static final Material BARRIER = (new Material(MapColor.AIR)).setRequiresTool().setImmovableMobility();
/*  50 */   public static final Material STRUCTURE_VOID = new MaterialTransparent(MapColor.AIR);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canBurn;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean replaceable;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isTranslucent;
/*     */ 
/*     */ 
/*     */   
/*     */   private final MapColor materialMapColor;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean requiresNoTool = true;
/*     */ 
/*     */ 
/*     */   
/*  76 */   private EnumPushReaction mobilityFlag = EnumPushReaction.NORMAL;
/*     */   
/*     */   private boolean isAdventureModeExempt;
/*     */   
/*     */   public Material(MapColor color) {
/*  81 */     this.materialMapColor = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLiquid() {
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSolid() {
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean blocksLight() {
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean blocksMovement() {
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Material setTranslucent() {
/* 121 */     this.isTranslucent = true;
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Material setRequiresTool() {
/* 130 */     this.requiresNoTool = false;
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Material setBurning() {
/* 139 */     this.canBurn = true;
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanBurn() {
/* 148 */     return this.canBurn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material setReplaceable() {
/* 156 */     this.replaceable = true;
/* 157 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable() {
/* 165 */     return this.replaceable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaque() {
/* 173 */     return this.isTranslucent ? false : blocksMovement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isToolNotRequired() {
/* 181 */     return this.requiresNoTool;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumPushReaction getMobilityFlag() {
/* 186 */     return this.mobilityFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Material setNoPushMobility() {
/* 194 */     this.mobilityFlag = EnumPushReaction.DESTROY;
/* 195 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Material setImmovableMobility() {
/* 203 */     this.mobilityFlag = EnumPushReaction.BLOCK;
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Material setAdventureModeExempt() {
/* 212 */     this.isAdventureModeExempt = true;
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMaterialMapColor() {
/* 221 */     return this.materialMapColor;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\material\Material.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */