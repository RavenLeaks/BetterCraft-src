/*     */ package me.nzxter.bettercraft.mods.shader;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DrawShader
/*     */ {
/*   7 */   public static ShaderUtils Universes = new ShaderUtils(BackgroundShader1.Universes);
/*   8 */   public static ShaderUtils Minecraft = new ShaderUtils(BackgroundShader1.Minecraft);
/*   9 */   public static ShaderUtils Mario = new ShaderUtils(BackgroundShader1.Mario);
/*  10 */   public static ShaderUtils Nordlicht = new ShaderUtils(BackgroundShader1.Nordlicht);
/*  11 */   public static ShaderUtils Button = new ShaderUtils(BackgroundShader1.Button);
/*  12 */   public static ShaderUtils Loading = new ShaderUtils(BackgroundShader1.Loading);
/*  13 */   public static ShaderUtils LoadingScreenSnow = new ShaderUtils(BackgroundShader1.LoadingScreenSnow);
/*  14 */   public static ShaderUtils LoadingScreennoSnow = new ShaderUtils(BackgroundShader1.LoadingScreennoSnow);
/*  15 */   public static ShaderUtils Smoke = new ShaderUtils(BackgroundShader1.Smoke);
/*  16 */   public static ShaderUtils MainMenu = new ShaderUtils(BackgroundShader1.MainMenu);
/*  17 */   public static ShaderUtils Options = new ShaderUtils(BackgroundShader1.Options);
/*  18 */   public static ShaderUtils AddServer = new ShaderUtils(BackgroundShader1.AddServer);
/*  19 */   public static ShaderUtils CreateWorld = new ShaderUtils(BackgroundShader1.CreateWorld);
/*  20 */   public static ShaderUtils Mori0 = new ShaderUtils(BackgroundShader1.Mori0);
/*  21 */   public static ShaderUtils City = new ShaderUtils(BackgroundShader1.City);
/*  22 */   public static ShaderUtils Rings = new ShaderUtils(BackgroundShader1.Rings);
/*  23 */   public static ShaderUtils SnowCircle = new ShaderUtils(BackgroundShader1.SnowCircle);
/*  24 */   public static ShaderUtils Galaxy = new ShaderUtils(BackgroundShader1.Galaxy);
/*  25 */   public static ShaderUtils RedNight = new ShaderUtils(BackgroundShader1.RedNight);
/*  26 */   public static ShaderUtils Ball = new ShaderUtils(BackgroundShader1.Ball);
/*  27 */   public static ShaderUtils Thunder = new ShaderUtils(BackgroundShader1.Thunder);
/*  28 */   public static ShaderUtils Wassersaeule = new ShaderUtils(BackgroundShader1.Wassersaeule);
/*  29 */   public static ShaderUtils Green = new ShaderUtils(BackgroundShader1.Green);
/*  30 */   public static ShaderUtils Acora = new ShaderUtils(BackgroundShader1.Acora);
/*  31 */   public static ShaderUtils RainbowColor = new ShaderUtils(BackgroundShader1.RainbowColor);
/*     */   
/*  33 */   public static ShaderUtils EaZy1 = new ShaderUtils(BackgroundShader2.EaZy1);
/*  34 */   public static ShaderUtils EaZy2 = new ShaderUtils(BackgroundShader2.EaZy2);
/*  35 */   public static ShaderUtils staticGod = new ShaderUtils(BackgroundShader2.staticGod);
/*  36 */   public static ShaderUtils Julian = new ShaderUtils(BackgroundShader2.Julian);
/*  37 */   public static ShaderUtils Sandbox = new ShaderUtils(BackgroundShader2.Sandbox);
/*  38 */   public static ShaderUtils Flames = new ShaderUtils(BackgroundShader2.Flames);
/*  39 */   public static ShaderUtils Mauer = new ShaderUtils(BackgroundShader2.Mauer);
/*  40 */   public static ShaderUtils Boxes = new ShaderUtils(BackgroundShader2.Boxes);
/*  41 */   public static ShaderUtils Nordlich = new ShaderUtils(BackgroundShader2.Nordlich);
/*  42 */   public static ShaderUtils Face = new ShaderUtils(BackgroundShader2.Face);
/*  43 */   public static ShaderUtils Smiley = new ShaderUtils(BackgroundShader2.Smiley);
/*  44 */   public static ShaderUtils Balls = new ShaderUtils(BackgroundShader2.Balls);
/*  45 */   public static ShaderUtils Rainy = new ShaderUtils(BackgroundShader2.Rainy);
/*  46 */   public static ShaderUtils ThunderClouds = new ShaderUtils(BackgroundShader2.ThunderClouds);
/*  47 */   public static ShaderUtils Crosshair = new ShaderUtils(BackgroundShader2.Crosshair);
/*  48 */   public static ShaderUtils Schall = new ShaderUtils(BackgroundShader2.Schall);
/*  49 */   public static ShaderUtils LSD = new ShaderUtils(BackgroundShader2.LSD);
/*     */   
/*  51 */   public static ShaderUtils Custom = null;
/*     */   
/*  53 */   public static int current_shader = 0;
/*     */   
/*     */   public static void switchShader() {
/*  56 */     current_shader = (current_shader != 41) ? ++current_shader : 0;
/*     */   }
/*     */   
/*     */   public static void doShaderStuff() {
/*  60 */     if (current_shader == 0) {
/*  61 */       renderShader(Universes, false);
/*     */     }
/*  63 */     if (current_shader == 1) {
/*  64 */       renderShader(Minecraft, false);
/*     */     }
/*  66 */     if (current_shader == 2) {
/*  67 */       renderShader(Mario, false);
/*     */     }
/*  69 */     if (current_shader == 3) {
/*  70 */       renderShader(Nordlicht, false);
/*     */     }
/*  72 */     if (current_shader == 4) {
/*  73 */       renderShader(Button, false);
/*     */     }
/*  75 */     if (current_shader == 5) {
/*  76 */       renderShader(Loading, false);
/*     */     }
/*  78 */     if (current_shader == 6) {
/*  79 */       renderShader(LoadingScreenSnow, false);
/*     */     }
/*  81 */     if (current_shader == 7) {
/*  82 */       renderShader(LoadingScreennoSnow, false);
/*     */     }
/*  84 */     if (current_shader == 8) {
/*  85 */       renderShader(Smoke, false);
/*     */     }
/*  87 */     if (current_shader == 9) {
/*  88 */       renderShader(MainMenu, false);
/*     */     }
/*  90 */     if (current_shader == 10) {
/*  91 */       renderShader(Options, false);
/*     */     }
/*  93 */     if (current_shader == 11) {
/*  94 */       renderShader(AddServer, false);
/*     */     }
/*  96 */     if (current_shader == 12) {
/*  97 */       renderShader(CreateWorld, false);
/*     */     }
/*  99 */     if (current_shader == 13) {
/* 100 */       renderShader(Mori0, false);
/*     */     }
/* 102 */     if (current_shader == 14) {
/* 103 */       renderShader(City, false);
/*     */     }
/* 105 */     if (current_shader == 15) {
/* 106 */       renderShader(Rings, false);
/*     */     }
/* 108 */     if (current_shader == 16) {
/* 109 */       renderShader(SnowCircle, false);
/*     */     }
/* 111 */     if (current_shader == 17) {
/* 112 */       renderShader(Galaxy, false);
/*     */     }
/* 114 */     if (current_shader == 18) {
/* 115 */       renderShader(RedNight, false);
/*     */     }
/* 117 */     if (current_shader == 19) {
/* 118 */       renderShader(Ball, false);
/*     */     }
/* 120 */     if (current_shader == 20) {
/* 121 */       renderShader(Thunder, false);
/*     */     }
/* 123 */     if (current_shader == 21) {
/* 124 */       renderShader(Wassersaeule, false);
/*     */     }
/* 126 */     if (current_shader == 22) {
/* 127 */       renderShader(Green, false);
/*     */     }
/* 129 */     if (current_shader == 23) {
/* 130 */       renderShader(Acora, false);
/*     */     }
/* 132 */     if (current_shader == 24) {
/* 133 */       renderShader(RainbowColor, false);
/*     */     }
/* 135 */     if (current_shader == 25) {
/* 136 */       renderShader(EaZy1, false);
/*     */     }
/* 138 */     if (current_shader == 26) {
/* 139 */       renderShader(EaZy2, false);
/*     */     }
/* 141 */     if (current_shader == 27) {
/* 142 */       renderShader(staticGod, false);
/*     */     }
/* 144 */     if (current_shader == 28) {
/* 145 */       renderShader(Julian, false);
/*     */     }
/* 147 */     if (current_shader == 29) {
/* 148 */       renderShader(Sandbox, false);
/*     */     }
/* 150 */     if (current_shader == 30) {
/* 151 */       renderShader(Flames, false);
/*     */     }
/* 153 */     if (current_shader == 31) {
/* 154 */       renderShader(Mauer, false);
/*     */     }
/* 156 */     if (current_shader == 32) {
/* 157 */       renderShader(Boxes, false);
/*     */     }
/* 159 */     if (current_shader == 33) {
/* 160 */       renderShader(Nordlich, false);
/*     */     }
/* 162 */     if (current_shader == 34) {
/* 163 */       renderShader(Face, false);
/*     */     }
/* 165 */     if (current_shader == 35) {
/* 166 */       renderShader(Smiley, false);
/*     */     }
/* 168 */     if (current_shader == 36) {
/* 169 */       renderShader(Balls, false);
/*     */     }
/* 171 */     if (current_shader == 37) {
/* 172 */       renderShader(Rainy, false);
/*     */     }
/* 174 */     if (current_shader == 38) {
/* 175 */       renderShader(ThunderClouds, false);
/*     */     }
/* 177 */     if (current_shader == 39) {
/* 178 */       renderShader(Crosshair, false);
/*     */     }
/* 180 */     if (current_shader == 40) {
/* 181 */       renderShader(Schall, false);
/*     */     }
/* 183 */     if (current_shader == 41) {
/* 184 */       renderShader(LSD, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void renderShader(ShaderUtils shader, boolean DefaultUniforms) {
/*     */     try {
/* 190 */       if (Custom != null) {
/* 191 */         shader = Custom;
/*     */       }
/* 193 */       shader.renderFirst();
/* 194 */       shader.addDefaultUniforms(DefaultUniforms);
/* 195 */       shader.renderSecond();
/*     */     }
/* 197 */     catch (Exception e) {
/* 198 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\shader\DrawShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */