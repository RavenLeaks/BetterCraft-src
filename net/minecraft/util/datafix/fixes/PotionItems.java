/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.datafix.IFixableData;
/*     */ 
/*     */ public class PotionItems implements IFixableData {
/*   8 */   private static final String[] POTION_IDS = new String[128];
/*     */ 
/*     */   
/*     */   public int getFixVersion() {
/*  12 */     return 102;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/*  17 */     if ("minecraft:potion".equals(compound.getString("id"))) {
/*     */       
/*  19 */       NBTTagCompound nbttagcompound = compound.getCompoundTag("tag");
/*  20 */       short short1 = compound.getShort("Damage");
/*     */       
/*  22 */       if (!nbttagcompound.hasKey("Potion", 8)) {
/*     */         
/*  24 */         String s = POTION_IDS[short1 & 0x7F];
/*  25 */         nbttagcompound.setString("Potion", (s == null) ? "minecraft:water" : s);
/*  26 */         compound.setTag("tag", (NBTBase)nbttagcompound);
/*     */         
/*  28 */         if ((short1 & 0x4000) == 16384)
/*     */         {
/*  30 */           compound.setString("id", "minecraft:splash_potion");
/*     */         }
/*     */       } 
/*     */       
/*  34 */       if (short1 != 0)
/*     */       {
/*  36 */         compound.setShort("Damage", (short)0);
/*     */       }
/*     */     } 
/*     */     
/*  40 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/*  45 */     POTION_IDS[0] = "minecraft:water";
/*  46 */     POTION_IDS[1] = "minecraft:regeneration";
/*  47 */     POTION_IDS[2] = "minecraft:swiftness";
/*  48 */     POTION_IDS[3] = "minecraft:fire_resistance";
/*  49 */     POTION_IDS[4] = "minecraft:poison";
/*  50 */     POTION_IDS[5] = "minecraft:healing";
/*  51 */     POTION_IDS[6] = "minecraft:night_vision";
/*  52 */     POTION_IDS[7] = null;
/*  53 */     POTION_IDS[8] = "minecraft:weakness";
/*  54 */     POTION_IDS[9] = "minecraft:strength";
/*  55 */     POTION_IDS[10] = "minecraft:slowness";
/*  56 */     POTION_IDS[11] = "minecraft:leaping";
/*  57 */     POTION_IDS[12] = "minecraft:harming";
/*  58 */     POTION_IDS[13] = "minecraft:water_breathing";
/*  59 */     POTION_IDS[14] = "minecraft:invisibility";
/*  60 */     POTION_IDS[15] = null;
/*  61 */     POTION_IDS[16] = "minecraft:awkward";
/*  62 */     POTION_IDS[17] = "minecraft:regeneration";
/*  63 */     POTION_IDS[18] = "minecraft:swiftness";
/*  64 */     POTION_IDS[19] = "minecraft:fire_resistance";
/*  65 */     POTION_IDS[20] = "minecraft:poison";
/*  66 */     POTION_IDS[21] = "minecraft:healing";
/*  67 */     POTION_IDS[22] = "minecraft:night_vision";
/*  68 */     POTION_IDS[23] = null;
/*  69 */     POTION_IDS[24] = "minecraft:weakness";
/*  70 */     POTION_IDS[25] = "minecraft:strength";
/*  71 */     POTION_IDS[26] = "minecraft:slowness";
/*  72 */     POTION_IDS[27] = "minecraft:leaping";
/*  73 */     POTION_IDS[28] = "minecraft:harming";
/*  74 */     POTION_IDS[29] = "minecraft:water_breathing";
/*  75 */     POTION_IDS[30] = "minecraft:invisibility";
/*  76 */     POTION_IDS[31] = null;
/*  77 */     POTION_IDS[32] = "minecraft:thick";
/*  78 */     POTION_IDS[33] = "minecraft:strong_regeneration";
/*  79 */     POTION_IDS[34] = "minecraft:strong_swiftness";
/*  80 */     POTION_IDS[35] = "minecraft:fire_resistance";
/*  81 */     POTION_IDS[36] = "minecraft:strong_poison";
/*  82 */     POTION_IDS[37] = "minecraft:strong_healing";
/*  83 */     POTION_IDS[38] = "minecraft:night_vision";
/*  84 */     POTION_IDS[39] = null;
/*  85 */     POTION_IDS[40] = "minecraft:weakness";
/*  86 */     POTION_IDS[41] = "minecraft:strong_strength";
/*  87 */     POTION_IDS[42] = "minecraft:slowness";
/*  88 */     POTION_IDS[43] = "minecraft:strong_leaping";
/*  89 */     POTION_IDS[44] = "minecraft:strong_harming";
/*  90 */     POTION_IDS[45] = "minecraft:water_breathing";
/*  91 */     POTION_IDS[46] = "minecraft:invisibility";
/*  92 */     POTION_IDS[47] = null;
/*  93 */     POTION_IDS[48] = null;
/*  94 */     POTION_IDS[49] = "minecraft:strong_regeneration";
/*  95 */     POTION_IDS[50] = "minecraft:strong_swiftness";
/*  96 */     POTION_IDS[51] = "minecraft:fire_resistance";
/*  97 */     POTION_IDS[52] = "minecraft:strong_poison";
/*  98 */     POTION_IDS[53] = "minecraft:strong_healing";
/*  99 */     POTION_IDS[54] = "minecraft:night_vision";
/* 100 */     POTION_IDS[55] = null;
/* 101 */     POTION_IDS[56] = "minecraft:weakness";
/* 102 */     POTION_IDS[57] = "minecraft:strong_strength";
/* 103 */     POTION_IDS[58] = "minecraft:slowness";
/* 104 */     POTION_IDS[59] = "minecraft:strong_leaping";
/* 105 */     POTION_IDS[60] = "minecraft:strong_harming";
/* 106 */     POTION_IDS[61] = "minecraft:water_breathing";
/* 107 */     POTION_IDS[62] = "minecraft:invisibility";
/* 108 */     POTION_IDS[63] = null;
/* 109 */     POTION_IDS[64] = "minecraft:mundane";
/* 110 */     POTION_IDS[65] = "minecraft:long_regeneration";
/* 111 */     POTION_IDS[66] = "minecraft:long_swiftness";
/* 112 */     POTION_IDS[67] = "minecraft:long_fire_resistance";
/* 113 */     POTION_IDS[68] = "minecraft:long_poison";
/* 114 */     POTION_IDS[69] = "minecraft:healing";
/* 115 */     POTION_IDS[70] = "minecraft:long_night_vision";
/* 116 */     POTION_IDS[71] = null;
/* 117 */     POTION_IDS[72] = "minecraft:long_weakness";
/* 118 */     POTION_IDS[73] = "minecraft:long_strength";
/* 119 */     POTION_IDS[74] = "minecraft:long_slowness";
/* 120 */     POTION_IDS[75] = "minecraft:long_leaping";
/* 121 */     POTION_IDS[76] = "minecraft:harming";
/* 122 */     POTION_IDS[77] = "minecraft:long_water_breathing";
/* 123 */     POTION_IDS[78] = "minecraft:long_invisibility";
/* 124 */     POTION_IDS[79] = null;
/* 125 */     POTION_IDS[80] = "minecraft:awkward";
/* 126 */     POTION_IDS[81] = "minecraft:long_regeneration";
/* 127 */     POTION_IDS[82] = "minecraft:long_swiftness";
/* 128 */     POTION_IDS[83] = "minecraft:long_fire_resistance";
/* 129 */     POTION_IDS[84] = "minecraft:long_poison";
/* 130 */     POTION_IDS[85] = "minecraft:healing";
/* 131 */     POTION_IDS[86] = "minecraft:long_night_vision";
/* 132 */     POTION_IDS[87] = null;
/* 133 */     POTION_IDS[88] = "minecraft:long_weakness";
/* 134 */     POTION_IDS[89] = "minecraft:long_strength";
/* 135 */     POTION_IDS[90] = "minecraft:long_slowness";
/* 136 */     POTION_IDS[91] = "minecraft:long_leaping";
/* 137 */     POTION_IDS[92] = "minecraft:harming";
/* 138 */     POTION_IDS[93] = "minecraft:long_water_breathing";
/* 139 */     POTION_IDS[94] = "minecraft:long_invisibility";
/* 140 */     POTION_IDS[95] = null;
/* 141 */     POTION_IDS[96] = "minecraft:thick";
/* 142 */     POTION_IDS[97] = "minecraft:regeneration";
/* 143 */     POTION_IDS[98] = "minecraft:swiftness";
/* 144 */     POTION_IDS[99] = "minecraft:long_fire_resistance";
/* 145 */     POTION_IDS[100] = "minecraft:poison";
/* 146 */     POTION_IDS[101] = "minecraft:strong_healing";
/* 147 */     POTION_IDS[102] = "minecraft:long_night_vision";
/* 148 */     POTION_IDS[103] = null;
/* 149 */     POTION_IDS[104] = "minecraft:long_weakness";
/* 150 */     POTION_IDS[105] = "minecraft:strength";
/* 151 */     POTION_IDS[106] = "minecraft:long_slowness";
/* 152 */     POTION_IDS[107] = "minecraft:leaping";
/* 153 */     POTION_IDS[108] = "minecraft:strong_harming";
/* 154 */     POTION_IDS[109] = "minecraft:long_water_breathing";
/* 155 */     POTION_IDS[110] = "minecraft:long_invisibility";
/* 156 */     POTION_IDS[111] = null;
/* 157 */     POTION_IDS[112] = null;
/* 158 */     POTION_IDS[113] = "minecraft:regeneration";
/* 159 */     POTION_IDS[114] = "minecraft:swiftness";
/* 160 */     POTION_IDS[115] = "minecraft:long_fire_resistance";
/* 161 */     POTION_IDS[116] = "minecraft:poison";
/* 162 */     POTION_IDS[117] = "minecraft:strong_healing";
/* 163 */     POTION_IDS[118] = "minecraft:long_night_vision";
/* 164 */     POTION_IDS[119] = null;
/* 165 */     POTION_IDS[120] = "minecraft:long_weakness";
/* 166 */     POTION_IDS[121] = "minecraft:strength";
/* 167 */     POTION_IDS[122] = "minecraft:long_slowness";
/* 168 */     POTION_IDS[123] = "minecraft:leaping";
/* 169 */     POTION_IDS[124] = "minecraft:strong_harming";
/* 170 */     POTION_IDS[125] = "minecraft:long_water_breathing";
/* 171 */     POTION_IDS[126] = "minecraft:long_invisibility";
/* 172 */     POTION_IDS[127] = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\PotionItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */