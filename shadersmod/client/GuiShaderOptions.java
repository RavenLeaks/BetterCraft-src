/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import optifine.Config;
/*     */ import optifine.GuiScreenOF;
/*     */ import optifine.Lang;
/*     */ import optifine.StrUtils;
/*     */ 
/*     */ public class GuiShaderOptions
/*     */   extends GuiScreenOF
/*     */ {
/*     */   private GuiScreen prevScreen;
/*     */   protected String title;
/*     */   private GameSettings settings;
/*     */   private int lastMouseX;
/*     */   private int lastMouseY;
/*     */   private long mouseStillTime;
/*     */   private String screenName;
/*     */   private String screenText;
/*     */   private boolean changed;
/*     */   public static final String OPTION_PROFILE = "<profile>";
/*     */   public static final String OPTION_EMPTY = "<empty>";
/*     */   public static final String OPTION_REST = "*";
/*     */   
/*     */   public GuiShaderOptions(GuiScreen guiscreen, GameSettings gamesettings) {
/*  34 */     this.lastMouseX = 0;
/*  35 */     this.lastMouseY = 0;
/*  36 */     this.mouseStillTime = 0L;
/*  37 */     this.screenName = null;
/*  38 */     this.screenText = null;
/*  39 */     this.changed = false;
/*  40 */     this.title = "Shader Options";
/*  41 */     this.prevScreen = guiscreen;
/*  42 */     this.settings = gamesettings;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiShaderOptions(GuiScreen guiscreen, GameSettings gamesettings, String screenName) {
/*  47 */     this(guiscreen, gamesettings);
/*  48 */     this.screenName = screenName;
/*     */     
/*  50 */     if (screenName != null)
/*     */     {
/*  52 */       this.screenText = Shaders.translate("screen." + screenName, screenName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  62 */     this.title = I18n.format("of.options.shaderOptionsTitle", new Object[0]);
/*  63 */     int i = 100;
/*  64 */     int j = 0;
/*  65 */     int k = 30;
/*  66 */     int l = 20;
/*  67 */     int i1 = this.width - 130;
/*  68 */     int j1 = 120;
/*  69 */     int k1 = 20;
/*  70 */     int l1 = Shaders.getShaderPackColumns(this.screenName, 2);
/*  71 */     ShaderOption[] ashaderoption = Shaders.getShaderPackOptions(this.screenName);
/*     */     
/*  73 */     if (ashaderoption != null) {
/*     */       
/*  75 */       int i2 = MathHelper.ceil(ashaderoption.length / 9.0D);
/*     */       
/*  77 */       if (l1 < i2)
/*     */       {
/*  79 */         l1 = i2;
/*     */       }
/*     */       
/*  82 */       for (int j2 = 0; j2 < ashaderoption.length; j2++) {
/*     */         
/*  84 */         ShaderOption shaderoption = ashaderoption[j2];
/*     */         
/*  86 */         if (shaderoption != null && shaderoption.isVisible()) {
/*     */           GuiButtonShaderOption guibuttonshaderoption;
/*  88 */           int k2 = j2 % l1;
/*  89 */           int l2 = j2 / l1;
/*  90 */           int i3 = Math.min(this.width / l1, 200);
/*  91 */           j = (this.width - i3 * l1) / 2;
/*  92 */           int j3 = k2 * i3 + 5 + j;
/*  93 */           int k3 = k + l2 * l;
/*  94 */           int l3 = i3 - 10;
/*  95 */           String s = getButtonText(shaderoption, l3);
/*     */ 
/*     */           
/*  98 */           if (Shaders.isShaderPackOptionSlider(shaderoption.getName())) {
/*     */             
/* 100 */             guibuttonshaderoption = new GuiSliderShaderOption(i + j2, j3, k3, l3, k1, shaderoption, s);
/*     */           }
/*     */           else {
/*     */             
/* 104 */             guibuttonshaderoption = new GuiButtonShaderOption(i + j2, j3, k3, l3, k1, shaderoption, s);
/*     */           } 
/*     */           
/* 107 */           guibuttonshaderoption.enabled = shaderoption.isEnabled();
/* 108 */           this.buttonList.add(guibuttonshaderoption);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     this.buttonList.add(new GuiButton(201, this.width / 2 - j1 - 20, this.height / 6 + 168 + 11, j1, k1, I18n.format("controls.reset", new Object[0])));
/* 114 */     this.buttonList.add(new GuiButton(200, this.width / 2 + 20, this.height / 6 + 168 + 11, j1, k1, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getButtonText(ShaderOption so, int btnWidth) {
/* 119 */     String s = so.getNameText();
/*     */     
/* 121 */     if (so instanceof ShaderOptionScreen) {
/*     */       
/* 123 */       ShaderOptionScreen shaderoptionscreen = (ShaderOptionScreen)so;
/* 124 */       return String.valueOf(s) + "...";
/*     */     } 
/*     */ 
/*     */     
/* 128 */     FontRenderer fontrenderer = (Config.getMinecraft()).fontRendererObj;
/*     */     
/* 130 */     for (int i = fontrenderer.getStringWidth(": " + Lang.getOff()) + 5; fontrenderer.getStringWidth(s) + i >= btnWidth && s.length() > 0; s = s.substring(0, s.length() - 1));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     String s1 = so.isChanged() ? so.getValueColor(so.getValue()) : "";
/* 136 */     String s2 = so.getValueText(so.getValue());
/* 137 */     return String.valueOf(s) + ": " + s1 + s2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton guibutton) {
/* 146 */     if (guibutton.enabled) {
/*     */       
/* 148 */       if (guibutton.id < 200 && guibutton instanceof GuiButtonShaderOption) {
/*     */         
/* 150 */         GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)guibutton;
/* 151 */         ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();
/*     */         
/* 153 */         if (shaderoption instanceof ShaderOptionScreen) {
/*     */           
/* 155 */           String s = shaderoption.getName();
/* 156 */           GuiShaderOptions guishaderoptions = new GuiShaderOptions((GuiScreen)this, this.settings, s);
/* 157 */           this.mc.displayGuiScreen((GuiScreen)guishaderoptions);
/*     */           
/*     */           return;
/*     */         } 
/* 161 */         if (isShiftKeyDown()) {
/*     */           
/* 163 */           shaderoption.resetValue();
/*     */         }
/*     */         else {
/*     */           
/* 167 */           shaderoption.nextValue();
/*     */         } 
/*     */         
/* 170 */         updateAllButtons();
/* 171 */         this.changed = true;
/*     */       } 
/*     */       
/* 174 */       if (guibutton.id == 201) {
/*     */         
/* 176 */         ShaderOption[] ashaderoption = Shaders.getChangedOptions(Shaders.getShaderPackOptions());
/*     */         
/* 178 */         for (int i = 0; i < ashaderoption.length; i++) {
/*     */           
/* 180 */           ShaderOption shaderoption1 = ashaderoption[i];
/* 181 */           shaderoption1.resetValue();
/* 182 */           this.changed = true;
/*     */         } 
/*     */         
/* 185 */         updateAllButtons();
/*     */       } 
/*     */       
/* 188 */       if (guibutton.id == 200) {
/*     */         
/* 190 */         if (this.changed) {
/*     */           
/* 192 */           Shaders.saveShaderPackOptions();
/* 193 */           this.changed = false;
/* 194 */           Shaders.uninit();
/*     */         } 
/*     */         
/* 197 */         this.mc.displayGuiScreen(this.prevScreen);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformedRightClick(GuiButton btn) {
/* 204 */     if (btn instanceof GuiButtonShaderOption) {
/*     */       
/* 206 */       GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)btn;
/* 207 */       ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();
/*     */       
/* 209 */       if (isShiftKeyDown()) {
/*     */         
/* 211 */         shaderoption.resetValue();
/*     */       }
/*     */       else {
/*     */         
/* 215 */         shaderoption.prevValue();
/*     */       } 
/*     */       
/* 218 */       updateAllButtons();
/* 219 */       this.changed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 228 */     super.onGuiClosed();
/*     */     
/* 230 */     if (this.changed) {
/*     */       
/* 232 */       Shaders.saveShaderPackOptions();
/* 233 */       this.changed = false;
/* 234 */       Shaders.uninit();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateAllButtons() {
/* 240 */     for (GuiButton guibutton : this.buttonList) {
/*     */       
/* 242 */       if (guibutton instanceof GuiButtonShaderOption) {
/*     */         
/* 244 */         GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)guibutton;
/* 245 */         ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();
/*     */         
/* 247 */         if (shaderoption instanceof ShaderOptionProfile) {
/*     */           
/* 249 */           ShaderOptionProfile shaderoptionprofile = (ShaderOptionProfile)shaderoption;
/* 250 */           shaderoptionprofile.updateProfile();
/*     */         } 
/*     */         
/* 253 */         guibuttonshaderoption.displayString = getButtonText(shaderoption, guibuttonshaderoption.getButtonWidth());
/* 254 */         guibuttonshaderoption.valueChanged();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int x, int y, float f) {
/* 264 */     drawDefaultBackground();
/*     */     
/* 266 */     if (this.screenText != null) {
/*     */       
/* 268 */       drawCenteredString(this.fontRendererObj, this.screenText, this.width / 2, 15, 16777215);
/*     */     }
/*     */     else {
/*     */       
/* 272 */       drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
/*     */     } 
/*     */     
/* 275 */     super.drawScreen(x, y, f);
/*     */     
/* 277 */     if (Math.abs(x - this.lastMouseX) <= 5 && Math.abs(y - this.lastMouseY) <= 5) {
/*     */       
/* 279 */       drawTooltips(x, y, this.buttonList);
/*     */     }
/*     */     else {
/*     */       
/* 283 */       this.lastMouseX = x;
/* 284 */       this.lastMouseY = y;
/* 285 */       this.mouseStillTime = System.currentTimeMillis();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawTooltips(int x, int y, List buttons) {
/* 291 */     int i = 700;
/*     */     
/* 293 */     if (System.currentTimeMillis() >= this.mouseStillTime + i) {
/*     */       
/* 295 */       int j = this.width / 2 - 150;
/* 296 */       int k = this.height / 6 - 7;
/*     */       
/* 298 */       if (y <= k + 98)
/*     */       {
/* 300 */         k += 105;
/*     */       }
/*     */       
/* 303 */       int l = j + 150 + 150;
/* 304 */       int i1 = k + 84 + 10;
/* 305 */       GuiButton guibutton = getSelectedButton(buttons, x, y);
/*     */       
/* 307 */       if (guibutton instanceof GuiButtonShaderOption) {
/*     */         
/* 309 */         GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)guibutton;
/* 310 */         ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();
/* 311 */         String[] astring = makeTooltipLines(shaderoption, l - j);
/*     */         
/* 313 */         if (astring == null) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 318 */         drawGradientRect(j, k, l, i1, -536870912, -536870912);
/*     */         
/* 320 */         for (int j1 = 0; j1 < astring.length; j1++) {
/*     */           
/* 322 */           String s = astring[j1];
/* 323 */           int k1 = 14540253;
/*     */           
/* 325 */           if (s.endsWith("!"))
/*     */           {
/* 327 */             k1 = 16719904;
/*     */           }
/*     */           
/* 330 */           this.fontRendererObj.drawStringWithShadow(s, (j + 5), (k + 5 + j1 * 11), k1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] makeTooltipLines(ShaderOption so, int width) {
/* 338 */     if (so instanceof ShaderOptionProfile)
/*     */     {
/* 340 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 344 */     String s = so.getNameText();
/* 345 */     String s1 = Config.normalize(so.getDescriptionText()).trim();
/* 346 */     String[] astring = splitDescription(s1);
/* 347 */     String s2 = null;
/*     */     
/* 349 */     if (!s.equals(so.getName()) && this.settings.advancedItemTooltips)
/*     */     {
/* 351 */       s2 = "§8" + Lang.get("of.general.id") + ": " + so.getName();
/*     */     }
/*     */     
/* 354 */     String s3 = null;
/*     */     
/* 356 */     if (so.getPaths() != null && this.settings.advancedItemTooltips)
/*     */     {
/* 358 */       s3 = "§8" + Lang.get("of.general.from") + ": " + Config.arrayToString((Object[])so.getPaths());
/*     */     }
/*     */     
/* 361 */     String s4 = null;
/*     */     
/* 363 */     if (so.getValueDefault() != null && this.settings.advancedItemTooltips) {
/*     */       
/* 365 */       String s5 = so.isEnabled() ? so.getValueText(so.getValueDefault()) : Lang.get("of.general.ambiguous");
/* 366 */       s4 = "§8" + Lang.getDefault() + ": " + s5;
/*     */     } 
/*     */     
/* 369 */     List<String> list = new ArrayList<>();
/* 370 */     list.add(s);
/* 371 */     list.addAll(Arrays.asList(astring));
/*     */     
/* 373 */     if (s2 != null)
/*     */     {
/* 375 */       list.add(s2);
/*     */     }
/*     */     
/* 378 */     if (s3 != null)
/*     */     {
/* 380 */       list.add(s3);
/*     */     }
/*     */     
/* 383 */     if (s4 != null)
/*     */     {
/* 385 */       list.add(s4);
/*     */     }
/*     */     
/* 388 */     String[] astring1 = makeTooltipLines(width, list);
/* 389 */     return astring1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] splitDescription(String desc) {
/* 395 */     if (desc.length() <= 0)
/*     */     {
/* 397 */       return new String[0];
/*     */     }
/*     */ 
/*     */     
/* 401 */     desc = StrUtils.removePrefix(desc, "//");
/* 402 */     String[] astring = desc.split("\\. ");
/*     */     
/* 404 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 406 */       astring[i] = "- " + astring[i].trim();
/* 407 */       astring[i] = StrUtils.removeSuffix(astring[i], ".");
/*     */     } 
/*     */     
/* 410 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] makeTooltipLines(int width, List<String> args) {
/* 416 */     FontRenderer fontrenderer = (Config.getMinecraft()).fontRendererObj;
/* 417 */     List<String> list = new ArrayList<>();
/*     */     
/* 419 */     for (int i = 0; i < args.size(); i++) {
/*     */       
/* 421 */       String s = args.get(i);
/*     */       
/* 423 */       if (s != null && s.length() > 0)
/*     */       {
/* 425 */         for (String s1 : fontrenderer.listFormattedStringToWidth(s, width))
/*     */         {
/* 427 */           list.add(s1);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 432 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 433 */     return astring;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\GuiShaderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */