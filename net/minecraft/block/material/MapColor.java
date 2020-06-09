/*     */ package net.minecraft.block.material;
/*     */ 
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapColor
/*     */ {
/*  10 */   public static final MapColor[] COLORS = new MapColor[64];
/*  11 */   public static final MapColor[] field_193575_b = new MapColor[16];
/*  12 */   public static final MapColor AIR = new MapColor(0, 0);
/*  13 */   public static final MapColor GRASS = new MapColor(1, 8368696);
/*  14 */   public static final MapColor SAND = new MapColor(2, 16247203);
/*  15 */   public static final MapColor CLOTH = new MapColor(3, 13092807);
/*  16 */   public static final MapColor TNT = new MapColor(4, 16711680);
/*  17 */   public static final MapColor ICE = new MapColor(5, 10526975);
/*  18 */   public static final MapColor IRON = new MapColor(6, 10987431);
/*  19 */   public static final MapColor FOLIAGE = new MapColor(7, 31744);
/*  20 */   public static final MapColor SNOW = new MapColor(8, 16777215);
/*  21 */   public static final MapColor CLAY = new MapColor(9, 10791096);
/*  22 */   public static final MapColor DIRT = new MapColor(10, 9923917);
/*  23 */   public static final MapColor STONE = new MapColor(11, 7368816);
/*  24 */   public static final MapColor WATER = new MapColor(12, 4210943);
/*  25 */   public static final MapColor WOOD = new MapColor(13, 9402184);
/*  26 */   public static final MapColor QUARTZ = new MapColor(14, 16776437);
/*  27 */   public static final MapColor ADOBE = new MapColor(15, 14188339);
/*  28 */   public static final MapColor MAGENTA = new MapColor(16, 11685080);
/*  29 */   public static final MapColor LIGHT_BLUE = new MapColor(17, 6724056);
/*  30 */   public static final MapColor YELLOW = new MapColor(18, 15066419);
/*  31 */   public static final MapColor LIME = new MapColor(19, 8375321);
/*  32 */   public static final MapColor PINK = new MapColor(20, 15892389);
/*  33 */   public static final MapColor GRAY = new MapColor(21, 5000268);
/*  34 */   public static final MapColor SILVER = new MapColor(22, 10066329);
/*  35 */   public static final MapColor CYAN = new MapColor(23, 5013401);
/*  36 */   public static final MapColor PURPLE = new MapColor(24, 8339378);
/*  37 */   public static final MapColor BLUE = new MapColor(25, 3361970);
/*  38 */   public static final MapColor BROWN = new MapColor(26, 6704179);
/*  39 */   public static final MapColor GREEN = new MapColor(27, 6717235);
/*  40 */   public static final MapColor RED = new MapColor(28, 10040115);
/*  41 */   public static final MapColor BLACK = new MapColor(29, 1644825);
/*  42 */   public static final MapColor GOLD = new MapColor(30, 16445005);
/*  43 */   public static final MapColor DIAMOND = new MapColor(31, 6085589);
/*  44 */   public static final MapColor LAPIS = new MapColor(32, 4882687);
/*  45 */   public static final MapColor EMERALD = new MapColor(33, 55610);
/*  46 */   public static final MapColor OBSIDIAN = new MapColor(34, 8476209);
/*  47 */   public static final MapColor NETHERRACK = new MapColor(35, 7340544);
/*  48 */   public static final MapColor field_193561_M = new MapColor(36, 13742497);
/*  49 */   public static final MapColor field_193562_N = new MapColor(37, 10441252);
/*  50 */   public static final MapColor field_193563_O = new MapColor(38, 9787244);
/*  51 */   public static final MapColor field_193564_P = new MapColor(39, 7367818);
/*  52 */   public static final MapColor field_193565_Q = new MapColor(40, 12223780);
/*  53 */   public static final MapColor field_193566_R = new MapColor(41, 6780213);
/*  54 */   public static final MapColor field_193567_S = new MapColor(42, 10505550);
/*  55 */   public static final MapColor field_193568_T = new MapColor(43, 3746083);
/*  56 */   public static final MapColor field_193569_U = new MapColor(44, 8874850);
/*  57 */   public static final MapColor field_193570_V = new MapColor(45, 5725276);
/*  58 */   public static final MapColor field_193571_W = new MapColor(46, 8014168);
/*  59 */   public static final MapColor field_193572_X = new MapColor(47, 4996700);
/*  60 */   public static final MapColor field_193573_Y = new MapColor(48, 4993571);
/*  61 */   public static final MapColor field_193574_Z = new MapColor(49, 5001770);
/*  62 */   public static final MapColor field_193559_aa = new MapColor(50, 9321518);
/*  63 */   public static final MapColor field_193560_ab = new MapColor(51, 2430480);
/*     */ 
/*     */   
/*     */   public int colorValue;
/*     */ 
/*     */   
/*     */   public final int colorIndex;
/*     */ 
/*     */   
/*     */   private MapColor(int index, int color) {
/*  73 */     if (index >= 0 && index <= 63) {
/*     */       
/*  75 */       this.colorIndex = index;
/*  76 */       this.colorValue = color;
/*  77 */       COLORS[index] = this;
/*     */     }
/*     */     else {
/*     */       
/*  81 */       throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMapColor(int p_151643_1_) {
/*  87 */     int i = 220;
/*     */     
/*  89 */     if (p_151643_1_ == 3)
/*     */     {
/*  91 */       i = 135;
/*     */     }
/*     */     
/*  94 */     if (p_151643_1_ == 2)
/*     */     {
/*  96 */       i = 255;
/*     */     }
/*     */     
/*  99 */     if (p_151643_1_ == 1)
/*     */     {
/* 101 */       i = 220;
/*     */     }
/*     */     
/* 104 */     if (p_151643_1_ == 0)
/*     */     {
/* 106 */       i = 180;
/*     */     }
/*     */     
/* 109 */     int j = (this.colorValue >> 16 & 0xFF) * i / 255;
/* 110 */     int k = (this.colorValue >> 8 & 0xFF) * i / 255;
/* 111 */     int l = (this.colorValue & 0xFF) * i / 255;
/* 112 */     return 0xFF000000 | j << 16 | k << 8 | l;
/*     */   }
/*     */ 
/*     */   
/*     */   public static MapColor func_193558_a(EnumDyeColor p_193558_0_) {
/* 117 */     return field_193575_b[p_193558_0_.getMetadata()];
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 122 */     field_193575_b[EnumDyeColor.WHITE.getMetadata()] = SNOW;
/* 123 */     field_193575_b[EnumDyeColor.ORANGE.getMetadata()] = ADOBE;
/* 124 */     field_193575_b[EnumDyeColor.MAGENTA.getMetadata()] = MAGENTA;
/* 125 */     field_193575_b[EnumDyeColor.LIGHT_BLUE.getMetadata()] = LIGHT_BLUE;
/* 126 */     field_193575_b[EnumDyeColor.YELLOW.getMetadata()] = YELLOW;
/* 127 */     field_193575_b[EnumDyeColor.LIME.getMetadata()] = LIME;
/* 128 */     field_193575_b[EnumDyeColor.PINK.getMetadata()] = PINK;
/* 129 */     field_193575_b[EnumDyeColor.GRAY.getMetadata()] = GRAY;
/* 130 */     field_193575_b[EnumDyeColor.SILVER.getMetadata()] = SILVER;
/* 131 */     field_193575_b[EnumDyeColor.CYAN.getMetadata()] = CYAN;
/* 132 */     field_193575_b[EnumDyeColor.PURPLE.getMetadata()] = PURPLE;
/* 133 */     field_193575_b[EnumDyeColor.BLUE.getMetadata()] = BLUE;
/* 134 */     field_193575_b[EnumDyeColor.BROWN.getMetadata()] = BROWN;
/* 135 */     field_193575_b[EnumDyeColor.GREEN.getMetadata()] = GREEN;
/* 136 */     field_193575_b[EnumDyeColor.RED.getMetadata()] = RED;
/* 137 */     field_193575_b[EnumDyeColor.BLACK.getMetadata()] = BLACK;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\material\MapColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */