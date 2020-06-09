/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import optifine.Config;
/*     */ import optifine.Lang;
/*     */ import org.lwjgl.Sys;
/*     */ 
/*     */ public class GuiShaders
/*     */   extends GuiScreen
/*     */ {
/*     */   protected GuiScreen parentGui;
/*  19 */   protected String screenTitle = "Shaders";
/*  20 */   private int updateTimer = -1;
/*     */   private GuiSlotShaders shaderList;
/*     */   private boolean saved = false;
/*  23 */   private static float[] QUALITY_MULTIPLIERS = new float[] { 0.5F, 0.70710677F, 1.0F, 1.4142135F, 2.0F };
/*  24 */   private static String[] QUALITY_MULTIPLIER_NAMES = new String[] { "0.5x", "0.7x", "1x", "1.5x", "2x" };
/*  25 */   private static float[] HAND_DEPTH_VALUES = new float[] { 0.0625F, 0.125F, 0.25F };
/*  26 */   private static String[] HAND_DEPTH_NAMES = new String[] { "0.5x", "1x", "2x" };
/*     */   
/*     */   public static final int EnumOS_UNKNOWN = 0;
/*     */   public static final int EnumOS_WINDOWS = 1;
/*     */   public static final int EnumOS_OSX = 2;
/*     */   public static final int EnumOS_SOLARIS = 3;
/*     */   public static final int EnumOS_LINUX = 4;
/*     */   
/*     */   public GuiShaders(GuiScreen par1GuiScreen, GameSettings par2GameSettings) {
/*  35 */     this.parentGui = par1GuiScreen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  44 */     this.screenTitle = I18n.format("of.options.shadersTitle", new Object[0]);
/*     */     
/*  46 */     if (Shaders.shadersConfig == null)
/*     */     {
/*  48 */       Shaders.loadConfig();
/*     */     }
/*     */     
/*  51 */     int i = 120;
/*  52 */     int j = 20;
/*  53 */     int k = this.width - i - 10;
/*  54 */     int l = 30;
/*  55 */     int i1 = 20;
/*  56 */     int j1 = this.width - i - 20;
/*  57 */     this.shaderList = new GuiSlotShaders(this, j1, this.height, l, this.height - 50, 16);
/*  58 */     this.shaderList.registerScrollButtons(7, 8);
/*  59 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.ANTIALIASING, k, 0 * i1 + l, i, j));
/*  60 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.NORMAL_MAP, k, 1 * i1 + l, i, j));
/*  61 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.SPECULAR_MAP, k, 2 * i1 + l, i, j));
/*  62 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.RENDER_RES_MUL, k, 3 * i1 + l, i, j));
/*  63 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.SHADOW_RES_MUL, k, 4 * i1 + l, i, j));
/*  64 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.HAND_DEPTH_MUL, k, 5 * i1 + l, i, j));
/*  65 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_HAND_LIGHT, k, 6 * i1 + l, i, j));
/*  66 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_LIGHTING, k, 7 * i1 + l, i, j));
/*  67 */     int k1 = Math.min(150, j1 / 2 - 10);
/*  68 */     this.buttonList.add(new GuiButton(201, j1 / 4 - k1 / 2, this.height - 25, k1, j, Lang.get("of.options.shaders.shadersFolder")));
/*  69 */     this.buttonList.add(new GuiButton(202, j1 / 4 * 3 - k1 / 2, this.height - 25, k1, j, I18n.format("gui.done", new Object[0])));
/*  70 */     this.buttonList.add(new GuiButton(203, k, this.height - 25, i, j, Lang.get("of.options.shaders.shaderOptions")));
/*  71 */     updateButtons();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateButtons() {
/*  76 */     boolean flag = Config.isShaders();
/*     */     
/*  78 */     for (GuiButton guibutton : this.buttonList) {
/*     */       
/*  80 */       if (guibutton.id != 201 && guibutton.id != 202 && guibutton.id != EnumShaderOption.ANTIALIASING.ordinal())
/*     */       {
/*  82 */         guibutton.enabled = flag;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  92 */     super.handleMouseInput();
/*  93 */     this.shaderList.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 101 */     if (button.enabled)
/*     */     {
/* 103 */       if (button instanceof GuiButtonEnumShaderOption) {
/*     */         float f2, afloat2[]; String[] astring2; int k; float f1, afloat1[]; String[] astring1; int j; float f, afloat[]; String[] astring; int i;
/* 105 */         GuiButtonEnumShaderOption guibuttonenumshaderoption = (GuiButtonEnumShaderOption)button;
/*     */         
/* 107 */         switch (guibuttonenumshaderoption.getEnumShaderOption()) {
/*     */           
/*     */           case null:
/* 110 */             Shaders.nextAntialiasingLevel();
/* 111 */             Shaders.uninit();
/*     */             break;
/*     */           
/*     */           case NORMAL_MAP:
/* 115 */             Shaders.configNormalMap = !Shaders.configNormalMap;
/* 116 */             Shaders.uninit();
/* 117 */             this.mc.scheduleResourcesRefresh();
/*     */             break;
/*     */           
/*     */           case SPECULAR_MAP:
/* 121 */             Shaders.configSpecularMap = !Shaders.configSpecularMap;
/* 122 */             Shaders.uninit();
/* 123 */             this.mc.scheduleResourcesRefresh();
/*     */             break;
/*     */           
/*     */           case RENDER_RES_MUL:
/* 127 */             f2 = Shaders.configRenderResMul;
/* 128 */             afloat2 = QUALITY_MULTIPLIERS;
/* 129 */             astring2 = QUALITY_MULTIPLIER_NAMES;
/* 130 */             k = getValueIndex(f2, afloat2);
/*     */             
/* 132 */             if (isShiftKeyDown()) {
/*     */               
/* 134 */               k--;
/*     */               
/* 136 */               if (k < 0)
/*     */               {
/* 138 */                 k = afloat2.length - 1;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/* 143 */               k++;
/*     */               
/* 145 */               if (k >= afloat2.length)
/*     */               {
/* 147 */                 k = 0;
/*     */               }
/*     */             } 
/*     */             
/* 151 */             Shaders.configRenderResMul = afloat2[k];
/* 152 */             Shaders.uninit();
/* 153 */             Shaders.scheduleResize();
/*     */             break;
/*     */           
/*     */           case SHADOW_RES_MUL:
/* 157 */             f1 = Shaders.configShadowResMul;
/* 158 */             afloat1 = QUALITY_MULTIPLIERS;
/* 159 */             astring1 = QUALITY_MULTIPLIER_NAMES;
/* 160 */             j = getValueIndex(f1, afloat1);
/*     */             
/* 162 */             if (isShiftKeyDown()) {
/*     */               
/* 164 */               j--;
/*     */               
/* 166 */               if (j < 0)
/*     */               {
/* 168 */                 j = afloat1.length - 1;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/* 173 */               j++;
/*     */               
/* 175 */               if (j >= afloat1.length)
/*     */               {
/* 177 */                 j = 0;
/*     */               }
/*     */             } 
/*     */             
/* 181 */             Shaders.configShadowResMul = afloat1[j];
/* 182 */             Shaders.uninit();
/* 183 */             Shaders.scheduleResizeShadow();
/*     */             break;
/*     */           
/*     */           case HAND_DEPTH_MUL:
/* 187 */             f = Shaders.configHandDepthMul;
/* 188 */             afloat = HAND_DEPTH_VALUES;
/* 189 */             astring = HAND_DEPTH_NAMES;
/* 190 */             i = getValueIndex(f, afloat);
/*     */             
/* 192 */             if (isShiftKeyDown()) {
/*     */               
/* 194 */               i--;
/*     */               
/* 196 */               if (i < 0)
/*     */               {
/* 198 */                 i = afloat.length - 1;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/* 203 */               i++;
/*     */               
/* 205 */               if (i >= afloat.length)
/*     */               {
/* 207 */                 i = 0;
/*     */               }
/*     */             } 
/*     */             
/* 211 */             Shaders.configHandDepthMul = afloat[i];
/* 212 */             Shaders.uninit();
/*     */             break;
/*     */           
/*     */           case OLD_HAND_LIGHT:
/* 216 */             Shaders.configOldHandLight.nextValue();
/* 217 */             Shaders.uninit();
/*     */             break;
/*     */           
/*     */           case OLD_LIGHTING:
/* 221 */             Shaders.configOldLighting.nextValue();
/* 222 */             Shaders.updateBlockLightLevel();
/* 223 */             Shaders.uninit();
/* 224 */             this.mc.scheduleResourcesRefresh();
/*     */             break;
/*     */           
/*     */           case TWEAK_BLOCK_DAMAGE:
/* 228 */             Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
/*     */             break;
/*     */           
/*     */           case CLOUD_SHADOW:
/* 232 */             Shaders.configCloudShadow = !Shaders.configCloudShadow;
/*     */             break;
/*     */           
/*     */           case TEX_MIN_FIL_B:
/* 236 */             Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
/* 237 */             Shaders.configTexMinFilN = Shaders.configTexMinFilS = Shaders.configTexMinFilB;
/* 238 */             button.displayString = "Tex Min: " + Shaders.texMinFilDesc[Shaders.configTexMinFilB];
/* 239 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */           
/*     */           case TEX_MAG_FIL_N:
/* 243 */             Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
/* 244 */             button.displayString = "Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN];
/* 245 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */           
/*     */           case TEX_MAG_FIL_S:
/* 249 */             Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
/* 250 */             button.displayString = "Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS];
/* 251 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */           
/*     */           case SHADOW_CLIP_FRUSTRUM:
/* 255 */             Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
/* 256 */             button.displayString = "ShadowClipFrustrum: " + toStringOnOff(Shaders.configShadowClipFrustrum);
/* 257 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */         } 
/* 260 */         guibuttonenumshaderoption.updateButtonText();
/*     */       } else {
/*     */         String s; boolean flag;
/*     */         GuiShaderOptions guishaderoptions;
/* 264 */         switch (button.id) {
/*     */           
/*     */           case 201:
/* 267 */             switch (getOSType()) {
/*     */               
/*     */               case 1:
/* 270 */                 s = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { Shaders.shaderpacksdir.getAbsolutePath() });
/*     */ 
/*     */                 
/*     */                 try {
/* 274 */                   Runtime.getRuntime().exec(s);
/*     */                   
/*     */                   return;
/* 277 */                 } catch (IOException ioexception) {
/*     */                   
/* 279 */                   ioexception.printStackTrace();
/*     */                   break;
/*     */                 } 
/*     */ 
/*     */               
/*     */               case 2:
/*     */                 try {
/* 286 */                   Runtime.getRuntime().exec(new String[] { "/usr/bin/open", Shaders.shaderpacksdir.getAbsolutePath() });
/*     */                   
/*     */                   return;
/* 289 */                 } catch (IOException ioexception1) {
/*     */                   
/* 291 */                   ioexception1.printStackTrace();
/*     */                   break;
/*     */                 } 
/*     */             } 
/* 295 */             flag = false;
/*     */ 
/*     */             
/*     */             try {
/* 299 */               Class<?> oclass = Class.forName("java.awt.Desktop");
/* 300 */               Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 301 */               oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { (new File(this.mc.mcDataDir, Shaders.shaderpacksdirname)).toURI() });
/*     */             }
/* 303 */             catch (Throwable throwable) {
/*     */               
/* 305 */               throwable.printStackTrace();
/* 306 */               flag = true;
/*     */             } 
/*     */             
/* 309 */             if (flag) {
/*     */               
/* 311 */               Config.dbg("Opening via system class!");
/* 312 */               Sys.openURL("file://" + Shaders.shaderpacksdir.getAbsolutePath());
/*     */             } 
/*     */             return;
/*     */ 
/*     */ 
/*     */           
/*     */           case 202:
/* 319 */             Shaders.storeConfig();
/* 320 */             this.saved = true;
/* 321 */             this.mc.displayGuiScreen(this.parentGui);
/*     */             return;
/*     */           
/*     */           case 203:
/* 325 */             guishaderoptions = new GuiShaderOptions(this, Config.getGameSettings());
/* 326 */             Config.getMinecraft().displayGuiScreen((GuiScreen)guishaderoptions);
/*     */             return;
/*     */         } 
/*     */         
/* 330 */         this.shaderList.actionPerformed(button);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 341 */     super.onGuiClosed();
/*     */     
/* 343 */     if (!this.saved)
/*     */     {
/* 345 */       Shaders.storeConfig();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 354 */     drawDefaultBackground();
/* 355 */     this.shaderList.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 357 */     if (this.updateTimer <= 0) {
/*     */       
/* 359 */       this.shaderList.updateList();
/* 360 */       this.updateTimer += 20;
/*     */     } 
/*     */     
/* 363 */     drawCenteredString(this.fontRendererObj, String.valueOf(this.screenTitle) + " ", this.width / 2, 15, 16777215);
/* 364 */     String s = "OpenGL: " + Shaders.glVersionString + ", " + Shaders.glVendorString + ", " + Shaders.glRendererString;
/* 365 */     int i = this.fontRendererObj.getStringWidth(s);
/*     */     
/* 367 */     if (i < this.width - 5) {
/*     */       
/* 369 */       drawCenteredString(this.fontRendererObj, s, this.width / 2, this.height - 40, 8421504);
/*     */     }
/*     */     else {
/*     */       
/* 373 */       drawString(this.fontRendererObj, s, 5, this.height - 40, 8421504);
/*     */     } 
/*     */     
/* 376 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 384 */     super.updateScreen();
/* 385 */     this.updateTimer--;
/*     */   }
/*     */ 
/*     */   
/*     */   public Minecraft getMc() {
/* 390 */     return this.mc;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredString(String text, int x, int y, int color) {
/* 395 */     drawCenteredString(this.fontRendererObj, text, x, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringOnOff(boolean value) {
/* 400 */     String s = Lang.getOn();
/* 401 */     String s1 = Lang.getOff();
/* 402 */     return value ? s : s1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringAa(int value) {
/* 407 */     if (value == 2)
/*     */     {
/* 409 */       return "FXAA 2x";
/*     */     }
/*     */ 
/*     */     
/* 413 */     return (value == 4) ? "FXAA 4x" : Lang.getOff();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toStringValue(float val, float[] values, String[] names) {
/* 419 */     int i = getValueIndex(val, values);
/* 420 */     return names[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getValueIndex(float val, float[] values) {
/* 425 */     for (int i = 0; i < values.length; i++) {
/*     */       
/* 427 */       float f = values[i];
/*     */       
/* 429 */       if (f >= val)
/*     */       {
/* 431 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 435 */     return values.length - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringQuality(float val) {
/* 440 */     return toStringValue(val, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_NAMES);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringHandDepth(float val) {
/* 445 */     return toStringValue(val, HAND_DEPTH_VALUES, HAND_DEPTH_NAMES);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getOSType() {
/* 450 */     String s = System.getProperty("os.name").toLowerCase();
/*     */     
/* 452 */     if (s.contains("win"))
/*     */     {
/* 454 */       return 1;
/*     */     }
/* 456 */     if (s.contains("mac"))
/*     */     {
/* 458 */       return 2;
/*     */     }
/* 460 */     if (s.contains("solaris"))
/*     */     {
/* 462 */       return 3;
/*     */     }
/* 464 */     if (s.contains("sunos"))
/*     */     {
/* 466 */       return 3;
/*     */     }
/* 468 */     if (s.contains("linux"))
/*     */     {
/* 470 */       return 4;
/*     */     }
/*     */ 
/*     */     
/* 474 */     return s.contains("unix") ? 4 : 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\GuiShaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */