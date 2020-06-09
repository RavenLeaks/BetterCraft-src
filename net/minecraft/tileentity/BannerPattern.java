/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public enum BannerPattern {
/*  11 */   BASE("base", "b"),
/*  12 */   SQUARE_BOTTOM_LEFT("square_bottom_left", "bl", "   ", "   ", "#  "),
/*  13 */   SQUARE_BOTTOM_RIGHT("square_bottom_right", "br", "   ", "   ", "  #"),
/*  14 */   SQUARE_TOP_LEFT("square_top_left", "tl", "#  ", "   ", "   "),
/*  15 */   SQUARE_TOP_RIGHT("square_top_right", "tr", "  #", "   ", "   "),
/*  16 */   STRIPE_BOTTOM("stripe_bottom", "bs", "   ", "   ", "###"),
/*  17 */   STRIPE_TOP("stripe_top", "ts", "###", "   ", "   "),
/*  18 */   STRIPE_LEFT("stripe_left", "ls", "#  ", "#  ", "#  "),
/*  19 */   STRIPE_RIGHT("stripe_right", "rs", "  #", "  #", "  #"),
/*  20 */   STRIPE_CENTER("stripe_center", "cs", " # ", " # ", " # "),
/*  21 */   STRIPE_MIDDLE("stripe_middle", "ms", "   ", "###", "   "),
/*  22 */   STRIPE_DOWNRIGHT("stripe_downright", "drs", "#  ", " # ", "  #"),
/*  23 */   STRIPE_DOWNLEFT("stripe_downleft", "dls", "  #", " # ", "#  "),
/*  24 */   STRIPE_SMALL("small_stripes", "ss", "# #", "# #", "   "),
/*  25 */   CROSS("cross", "cr", "# #", " # ", "# #"),
/*  26 */   STRAIGHT_CROSS("straight_cross", "sc", " # ", "###", " # "),
/*  27 */   TRIANGLE_BOTTOM("triangle_bottom", "bt", "   ", " # ", "# #"),
/*  28 */   TRIANGLE_TOP("triangle_top", "tt", "# #", " # ", "   "),
/*  29 */   TRIANGLES_BOTTOM("triangles_bottom", "bts", "   ", "# #", " # "),
/*  30 */   TRIANGLES_TOP("triangles_top", "tts", " # ", "# #", "   "),
/*  31 */   DIAGONAL_LEFT("diagonal_left", "ld", "## ", "#  ", "   "),
/*  32 */   DIAGONAL_RIGHT("diagonal_up_right", "rd", "   ", "  #", " ##"),
/*  33 */   DIAGONAL_LEFT_MIRROR("diagonal_up_left", "lud", "   ", "#  ", "## "),
/*  34 */   DIAGONAL_RIGHT_MIRROR("diagonal_right", "rud", " ##", "  #", "   "),
/*  35 */   CIRCLE_MIDDLE("circle", "mc", "   ", " # ", "   "),
/*  36 */   RHOMBUS_MIDDLE("rhombus", "mr", " # ", "# #", " # "),
/*  37 */   HALF_VERTICAL("half_vertical", "vh", "## ", "## ", "## "),
/*  38 */   HALF_HORIZONTAL("half_horizontal", "hh", "###", "###", "   "),
/*  39 */   HALF_VERTICAL_MIRROR("half_vertical_right", "vhr", " ##", " ##", " ##"),
/*  40 */   HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "hhb", "   ", "###", "###"),
/*  41 */   BORDER("border", "bo", "###", "# #", "###"),
/*  42 */   CURLY_BORDER("curly_border", "cbo", new ItemStack(Blocks.VINE)),
/*  43 */   CREEPER("creeper", "cre", new ItemStack(Items.SKULL, 1, 4)),
/*  44 */   GRADIENT("gradient", "gra", "# #", " # ", " # "),
/*  45 */   GRADIENT_UP("gradient_up", "gru", " # ", " # ", "# #"),
/*  46 */   BRICKS("bricks", "bri", new ItemStack(Blocks.BRICK_BLOCK)),
/*  47 */   SKULL("skull", "sku", new ItemStack(Items.SKULL, 1, 1)),
/*  48 */   FLOWER("flower", "flo", new ItemStack((Block)Blocks.RED_FLOWER, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta())),
/*  49 */   MOJANG("mojang", "moj", new ItemStack(Items.GOLDEN_APPLE, 1, 1));
/*     */   
/*     */   private final String field_191014_N;
/*     */   
/*     */   private final String field_191015_O;
/*     */   private final String[] field_191016_P;
/*     */   private ItemStack field_191017_Q;
/*     */   
/*     */   BannerPattern(String p_i47245_3_, String p_i47245_4_) {
/*  58 */     this.field_191016_P = new String[3];
/*  59 */     this.field_191017_Q = ItemStack.field_190927_a;
/*  60 */     this.field_191014_N = p_i47245_3_;
/*  61 */     this.field_191015_O = p_i47245_4_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   BannerPattern(String p_i47246_3_, String p_i47246_4_, ItemStack p_i47246_5_) {
/*  67 */     this.field_191017_Q = p_i47246_5_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   BannerPattern(String p_i47247_3_, String p_i47247_4_, String p_i47247_5_, String p_i47247_6_, String p_i47247_7_) {
/*  73 */     this.field_191016_P[0] = p_i47247_5_;
/*  74 */     this.field_191016_P[1] = p_i47247_6_;
/*  75 */     this.field_191016_P[2] = p_i47247_7_;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_190997_a() {
/*  80 */     return this.field_191014_N;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_190993_b() {
/*  85 */     return this.field_191015_O;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] func_190996_c() {
/*  90 */     return this.field_191016_P;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191000_d() {
/*  95 */     return !(this.field_191017_Q.func_190926_b() && this.field_191016_P[0] == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190999_e() {
/* 100 */     return !this.field_191017_Q.func_190926_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_190998_f() {
/* 105 */     return this.field_191017_Q;
/*     */   } @Nullable
/*     */   public static BannerPattern func_190994_a(String p_190994_0_) {
/*     */     byte b;
/*     */     int i;
/*     */     BannerPattern[] arrayOfBannerPattern;
/* 111 */     for (i = (arrayOfBannerPattern = values()).length, b = 0; b < i; ) { BannerPattern bannerpattern = arrayOfBannerPattern[b];
/*     */       
/* 113 */       if (bannerpattern.field_191015_O.equals(p_190994_0_))
/*     */       {
/* 115 */         return bannerpattern;
/*     */       }
/*     */       b++; }
/*     */     
/* 119 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\BannerPattern.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */