/*     */ package net.minecraft.client.gui.recipebook;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiButtonToggle;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.Language;
/*     */ import net.minecraft.client.resources.LanguageManager;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.client.util.RecipeBookClient;
/*     */ import net.minecraft.client.util.RecipeItemHelper;
/*     */ import net.minecraft.client.util.SearchTreeManager;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.item.crafting.Ingredient;
/*     */ import net.minecraft.item.crafting.ShapedRecipes;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketRecipeInfo;
/*     */ import net.minecraft.stats.RecipeBook;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiRecipeBook
/*     */   extends Gui implements IRecipeUpdateListener {
/*  40 */   protected static final ResourceLocation field_191894_a = new ResourceLocation("textures/gui/recipe_book.png");
/*     */   private int field_191903_n;
/*     */   private int field_191904_o;
/*     */   private int field_191905_p;
/*  44 */   private final GhostRecipe field_191915_z = new GhostRecipe();
/*  45 */   private final List<GuiButtonRecipeTab> field_193018_j = Lists.newArrayList((Object[])new GuiButtonRecipeTab[] { new GuiButtonRecipeTab(0, CreativeTabs.SEARCH), new GuiButtonRecipeTab(0, CreativeTabs.TOOLS), new GuiButtonRecipeTab(0, CreativeTabs.BUILDING_BLOCKS), new GuiButtonRecipeTab(0, CreativeTabs.MISC), new GuiButtonRecipeTab(0, CreativeTabs.REDSTONE) });
/*     */   private GuiButtonRecipeTab field_191913_x;
/*     */   private GuiButtonToggle field_193960_m;
/*     */   private InventoryCrafting field_193961_o;
/*     */   private Minecraft field_191888_F;
/*     */   private GuiTextField field_193962_q;
/*  51 */   private String field_193963_r = "";
/*     */   private RecipeBook field_193964_s;
/*  53 */   private final RecipeBookPage field_193022_s = new RecipeBookPage();
/*  54 */   private RecipeItemHelper field_193965_u = new RecipeItemHelper();
/*     */   
/*     */   private int field_193966_v;
/*     */   
/*     */   public void func_194303_a(int p_194303_1_, int p_194303_2_, Minecraft p_194303_3_, boolean p_194303_4_, InventoryCrafting p_194303_5_) {
/*  59 */     this.field_191888_F = p_194303_3_;
/*  60 */     this.field_191904_o = p_194303_1_;
/*  61 */     this.field_191905_p = p_194303_2_;
/*  62 */     this.field_193961_o = p_194303_5_;
/*  63 */     this.field_193964_s = p_194303_3_.player.func_192035_E();
/*  64 */     this.field_193966_v = p_194303_3_.player.inventory.func_194015_p();
/*  65 */     this.field_191913_x = this.field_193018_j.get(0);
/*  66 */     this.field_191913_x.func_191753_b(true);
/*     */     
/*  68 */     if (func_191878_b())
/*     */     {
/*  70 */       func_193014_a(p_194303_4_, p_194303_5_);
/*     */     }
/*     */     
/*  73 */     Keyboard.enableRepeatEvents(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193014_a(boolean p_193014_1_, InventoryCrafting p_193014_2_) {
/*  78 */     this.field_191903_n = p_193014_1_ ? 0 : 86;
/*  79 */     int i = (this.field_191904_o - 147) / 2 - this.field_191903_n;
/*  80 */     int j = (this.field_191905_p - 166) / 2;
/*  81 */     this.field_193965_u.func_194119_a();
/*  82 */     this.field_191888_F.player.inventory.func_194016_a(this.field_193965_u, false);
/*  83 */     p_193014_2_.func_194018_a(this.field_193965_u);
/*  84 */     this.field_193962_q = new GuiTextField(0, this.field_191888_F.fontRendererObj, i + 25, j + 14, 80, this.field_191888_F.fontRendererObj.FONT_HEIGHT + 5);
/*  85 */     this.field_193962_q.setMaxStringLength(50);
/*  86 */     this.field_193962_q.setEnableBackgroundDrawing(false);
/*  87 */     this.field_193962_q.setVisible(true);
/*  88 */     this.field_193962_q.setTextColor(16777215);
/*  89 */     this.field_193022_s.func_194194_a(this.field_191888_F, i, j);
/*  90 */     this.field_193022_s.func_193732_a(this);
/*  91 */     this.field_193960_m = new GuiButtonToggle(0, i + 110, j + 12, 26, 16, this.field_193964_s.func_192815_c());
/*  92 */     this.field_193960_m.func_191751_a(152, 41, 28, 18, field_191894_a);
/*  93 */     func_193003_g(false);
/*  94 */     func_193949_f();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191871_c() {
/*  99 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_193011_a(boolean p_193011_1_, int p_193011_2_, int p_193011_3_) {
/*     */     int i;
/* 106 */     if (func_191878_b() && !p_193011_1_) {
/*     */       
/* 108 */       i = 177 + (p_193011_2_ - p_193011_3_ - 200) / 2;
/*     */     }
/*     */     else {
/*     */       
/* 112 */       i = (p_193011_2_ - p_193011_3_) / 2;
/*     */     } 
/*     */     
/* 115 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191866_a() {
/* 120 */     func_193006_a(!func_191878_b());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191878_b() {
/* 125 */     return this.field_193964_s.func_192812_b();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_193006_a(boolean p_193006_1_) {
/* 130 */     this.field_193964_s.func_192813_a(p_193006_1_);
/*     */     
/* 132 */     if (!p_193006_1_)
/*     */     {
/* 134 */       this.field_193022_s.func_194200_c();
/*     */     }
/*     */     
/* 137 */     func_193956_j();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191874_a(@Nullable Slot p_191874_1_) {
/* 142 */     if (p_191874_1_ != null && p_191874_1_.slotNumber <= 9) {
/*     */       
/* 144 */       this.field_191915_z.func_192682_a();
/*     */       
/* 146 */       if (func_191878_b())
/*     */       {
/* 148 */         func_193942_g();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_193003_g(boolean p_193003_1_) {
/* 155 */     List<RecipeList> list = (List<RecipeList>)RecipeBookClient.field_194086_e.get(this.field_191913_x.func_191764_e());
/* 156 */     list.forEach(p_193944_1_ -> p_193944_1_.func_194210_a(this.field_193965_u, this.field_193961_o.getWidth(), this.field_193961_o.getHeight(), this.field_193964_s));
/*     */ 
/*     */ 
/*     */     
/* 160 */     List<RecipeList> list1 = Lists.newArrayList(list);
/* 161 */     list1.removeIf(p_193952_0_ -> !p_193952_0_.func_194209_a());
/*     */ 
/*     */ 
/*     */     
/* 165 */     list1.removeIf(p_193953_0_ -> !p_193953_0_.func_194212_c());
/*     */ 
/*     */ 
/*     */     
/* 169 */     String s = this.field_193962_q.getText();
/*     */     
/* 171 */     if (!s.isEmpty()) {
/*     */       
/* 173 */       ObjectLinkedOpenHashSet objectLinkedOpenHashSet = new ObjectLinkedOpenHashSet(this.field_191888_F.func_193987_a(SearchTreeManager.field_194012_b).func_194038_a(s.toLowerCase(Locale.ROOT)));
/* 174 */       list1.removeIf(p_193947_1_ -> !paramObjectSet.contains(p_193947_1_));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     if (this.field_193964_s.func_192815_c())
/*     */     {
/* 182 */       list1.removeIf(p_193958_0_ -> !p_193958_0_.func_192708_c());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     this.field_193022_s.func_194192_a(list1, p_193003_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_193949_f() {
/* 193 */     int i = (this.field_191904_o - 147) / 2 - this.field_191903_n - 30;
/* 194 */     int j = (this.field_191905_p - 166) / 2 + 3;
/* 195 */     int k = 27;
/* 196 */     int l = 0;
/*     */     
/* 198 */     for (GuiButtonRecipeTab guibuttonrecipetab : this.field_193018_j) {
/*     */       
/* 200 */       CreativeTabs creativetabs = guibuttonrecipetab.func_191764_e();
/*     */       
/* 202 */       if (creativetabs == CreativeTabs.SEARCH) {
/*     */         
/* 204 */         guibuttonrecipetab.visible = true;
/* 205 */         guibuttonrecipetab.func_191752_c(i, j + 27 * l++); continue;
/*     */       } 
/* 207 */       if (guibuttonrecipetab.func_193919_e()) {
/*     */         
/* 209 */         guibuttonrecipetab.func_191752_c(i, j + 27 * l++);
/* 210 */         guibuttonrecipetab.func_193918_a(this.field_191888_F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193957_d() {
/* 217 */     if (func_191878_b())
/*     */     {
/* 219 */       if (this.field_193966_v != this.field_191888_F.player.inventory.func_194015_p()) {
/*     */         
/* 221 */         func_193942_g();
/* 222 */         this.field_193966_v = this.field_191888_F.player.inventory.func_194015_p();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_193942_g() {
/* 229 */     this.field_193965_u.func_194119_a();
/* 230 */     this.field_191888_F.player.inventory.func_194016_a(this.field_193965_u, false);
/* 231 */     this.field_193961_o.func_194018_a(this.field_193965_u);
/* 232 */     func_193003_g(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191861_a(int p_191861_1_, int p_191861_2_, float p_191861_3_) {
/* 237 */     if (func_191878_b()) {
/*     */       
/* 239 */       RenderHelper.enableGUIStandardItemLighting();
/* 240 */       GlStateManager.disableLighting();
/* 241 */       GlStateManager.pushMatrix();
/* 242 */       GlStateManager.translate(0.0F, 0.0F, 100.0F);
/* 243 */       this.field_191888_F.getTextureManager().bindTexture(field_191894_a);
/* 244 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 245 */       int i = (this.field_191904_o - 147) / 2 - this.field_191903_n;
/* 246 */       int j = (this.field_191905_p - 166) / 2;
/* 247 */       drawTexturedModalRect(i, j, 1, 1, 147, 166);
/* 248 */       this.field_193962_q.drawTextBox();
/* 249 */       RenderHelper.disableStandardItemLighting();
/*     */       
/* 251 */       for (GuiButtonRecipeTab guibuttonrecipetab : this.field_193018_j)
/*     */       {
/* 253 */         guibuttonrecipetab.func_191745_a(this.field_191888_F, p_191861_1_, p_191861_2_, p_191861_3_);
/*     */       }
/*     */       
/* 256 */       this.field_193960_m.func_191745_a(this.field_191888_F, p_191861_1_, p_191861_2_, p_191861_3_);
/* 257 */       this.field_193022_s.func_194191_a(i, j, p_191861_1_, p_191861_2_, p_191861_3_);
/* 258 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191876_c(int p_191876_1_, int p_191876_2_, int p_191876_3_, int p_191876_4_) {
/* 264 */     if (func_191878_b()) {
/*     */       
/* 266 */       this.field_193022_s.func_193721_a(p_191876_3_, p_191876_4_);
/*     */       
/* 268 */       if (this.field_193960_m.isMouseOver()) {
/*     */         
/* 270 */         String s1 = I18n.format(this.field_193960_m.func_191754_c() ? "gui.recipebook.toggleRecipes.craftable" : "gui.recipebook.toggleRecipes.all", new Object[0]);
/*     */         
/* 272 */         if (this.field_191888_F.currentScreen != null)
/*     */         {
/* 274 */           this.field_191888_F.currentScreen.drawCreativeTabHoveringText(s1, p_191876_3_, p_191876_4_);
/*     */         }
/*     */       } 
/*     */       
/* 278 */       func_193015_d(p_191876_1_, p_191876_2_, p_191876_3_, p_191876_4_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_193015_d(int p_193015_1_, int p_193015_2_, int p_193015_3_, int p_193015_4_) {
/* 284 */     ItemStack itemstack = null;
/*     */     
/* 286 */     for (int i = 0; i < this.field_191915_z.func_192684_b(); i++) {
/*     */       
/* 288 */       GhostRecipe.GhostIngredient ghostrecipe$ghostingredient = this.field_191915_z.func_192681_a(i);
/* 289 */       int j = ghostrecipe$ghostingredient.func_193713_b() + p_193015_1_;
/* 290 */       int k = ghostrecipe$ghostingredient.func_193712_c() + p_193015_2_;
/*     */       
/* 292 */       if (p_193015_3_ >= j && p_193015_4_ >= k && p_193015_3_ < j + 16 && p_193015_4_ < k + 16)
/*     */       {
/* 294 */         itemstack = ghostrecipe$ghostingredient.func_194184_c();
/*     */       }
/*     */     } 
/*     */     
/* 298 */     if (itemstack != null && this.field_191888_F.currentScreen != null)
/*     */     {
/* 300 */       this.field_191888_F.currentScreen.drawHoveringText(this.field_191888_F.currentScreen.func_191927_a(itemstack), p_193015_3_, p_193015_4_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191864_a(int p_191864_1_, int p_191864_2_, boolean p_191864_3_, float p_191864_4_) {
/* 306 */     this.field_191915_z.func_194188_a(this.field_191888_F, p_191864_1_, p_191864_2_, p_191864_3_, p_191864_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191862_a(int p_191862_1_, int p_191862_2_, int p_191862_3_) {
/* 311 */     if (func_191878_b() && !this.field_191888_F.player.isSpectator()) {
/*     */       
/* 313 */       if (this.field_193022_s.func_194196_a(p_191862_1_, p_191862_2_, p_191862_3_, (this.field_191904_o - 147) / 2 - this.field_191903_n, (this.field_191905_p - 166) / 2, 147, 166)) {
/*     */         
/* 315 */         IRecipe irecipe = this.field_193022_s.func_194193_a();
/* 316 */         RecipeList recipelist = this.field_193022_s.func_194199_b();
/*     */         
/* 318 */         if (irecipe != null && recipelist != null) {
/*     */           
/* 320 */           if (!recipelist.func_194213_a(irecipe) && this.field_191915_z.func_192686_c() == irecipe)
/*     */           {
/* 322 */             return false;
/*     */           }
/*     */           
/* 325 */           this.field_191915_z.func_192682_a();
/* 326 */           this.field_191888_F.playerController.func_194338_a(this.field_191888_F.player.openContainer.windowId, irecipe, GuiScreen.isShiftKeyDown(), (EntityPlayer)this.field_191888_F.player);
/*     */           
/* 328 */           if (!func_191880_f() && p_191862_3_ == 0)
/*     */           {
/* 330 */             func_193006_a(false);
/*     */           }
/*     */         } 
/*     */         
/* 334 */         return true;
/*     */       } 
/* 336 */       if (p_191862_3_ != 0)
/*     */       {
/* 338 */         return false;
/*     */       }
/* 340 */       if (this.field_193962_q.mouseClicked(p_191862_1_, p_191862_2_, p_191862_3_))
/*     */       {
/* 342 */         return true;
/*     */       }
/* 344 */       if (this.field_193960_m.mousePressed(this.field_191888_F, p_191862_1_, p_191862_2_)) {
/*     */         
/* 346 */         boolean flag = !this.field_193964_s.func_192815_c();
/* 347 */         this.field_193964_s.func_192810_b(flag);
/* 348 */         this.field_193960_m.func_191753_b(flag);
/* 349 */         this.field_193960_m.playPressSound(this.field_191888_F.getSoundHandler());
/* 350 */         func_193956_j();
/* 351 */         func_193003_g(false);
/* 352 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 356 */       for (GuiButtonRecipeTab guibuttonrecipetab : this.field_193018_j) {
/*     */         
/* 358 */         if (guibuttonrecipetab.mousePressed(this.field_191888_F, p_191862_1_, p_191862_2_)) {
/*     */           
/* 360 */           if (this.field_191913_x != guibuttonrecipetab) {
/*     */             
/* 362 */             guibuttonrecipetab.playPressSound(this.field_191888_F.getSoundHandler());
/* 363 */             this.field_191913_x.func_191753_b(false);
/* 364 */             this.field_191913_x = guibuttonrecipetab;
/* 365 */             this.field_191913_x.func_191753_b(true);
/* 366 */             func_193003_g(true);
/*     */           } 
/*     */           
/* 369 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 373 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 378 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_193955_c(int p_193955_1_, int p_193955_2_, int p_193955_3_, int p_193955_4_, int p_193955_5_, int p_193955_6_) {
/* 384 */     if (!func_191878_b())
/*     */     {
/* 386 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 390 */     boolean flag = !(p_193955_1_ >= p_193955_3_ && p_193955_2_ >= p_193955_4_ && p_193955_1_ < p_193955_3_ + p_193955_5_ && p_193955_2_ < p_193955_4_ + p_193955_6_);
/* 391 */     boolean flag1 = (p_193955_3_ - 147 < p_193955_1_ && p_193955_1_ < p_193955_3_ && p_193955_4_ < p_193955_2_ && p_193955_2_ < p_193955_4_ + p_193955_6_);
/* 392 */     return (flag && !flag1 && !this.field_191913_x.mousePressed(this.field_191888_F, p_193955_1_, p_193955_2_));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_191859_a(char p_191859_1_, int p_191859_2_) {
/* 398 */     if (func_191878_b() && !this.field_191888_F.player.isSpectator()) {
/*     */       
/* 400 */       if (p_191859_2_ == 1 && !func_191880_f()) {
/*     */         
/* 402 */         func_193006_a(false);
/* 403 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 407 */       if (GameSettings.isKeyDown(this.field_191888_F.gameSettings.keyBindChat) && !this.field_193962_q.isFocused()) {
/*     */         
/* 409 */         this.field_193962_q.setFocused(true);
/*     */       }
/* 411 */       else if (this.field_193962_q.textboxKeyTyped(p_191859_1_, p_191859_2_)) {
/*     */         
/* 413 */         String s1 = this.field_193962_q.getText().toLowerCase(Locale.ROOT);
/* 414 */         func_193716_a(s1);
/*     */         
/* 416 */         if (!s1.equals(this.field_193963_r)) {
/*     */           
/* 418 */           func_193003_g(false);
/* 419 */           this.field_193963_r = s1;
/*     */         } 
/*     */         
/* 422 */         return true;
/*     */       } 
/*     */       
/* 425 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 430 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_193716_a(String p_193716_1_) {
/* 436 */     if ("excitedze".equals(p_193716_1_)) {
/*     */       
/* 438 */       LanguageManager languagemanager = this.field_191888_F.getLanguageManager();
/* 439 */       Language language = languagemanager.func_191960_a("en_pt");
/*     */       
/* 441 */       if (languagemanager.getCurrentLanguage().compareTo(language) == 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 446 */       languagemanager.setCurrentLanguage(language);
/* 447 */       this.field_191888_F.gameSettings.language = language.getLanguageCode();
/* 448 */       this.field_191888_F.refreshResources();
/* 449 */       this.field_191888_F.fontRendererObj.setUnicodeFlag(!(!this.field_191888_F.getLanguageManager().isCurrentLocaleUnicode() && !this.field_191888_F.gameSettings.forceUnicodeFont));
/* 450 */       this.field_191888_F.fontRendererObj.setBidiFlag(languagemanager.isCurrentLanguageBidirectional());
/* 451 */       this.field_191888_F.gameSettings.saveOptions();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_191880_f() {
/* 457 */     return (this.field_191903_n == 86);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193948_e() {
/* 462 */     func_193949_f();
/*     */     
/* 464 */     if (func_191878_b())
/*     */     {
/* 466 */       func_193003_g(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193001_a(List<IRecipe> p_193001_1_) {
/* 472 */     for (IRecipe irecipe : p_193001_1_)
/*     */     {
/* 474 */       this.field_191888_F.player.func_193103_a(irecipe);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193951_a(IRecipe p_193951_1_, List<Slot> p_193951_2_) {
/* 480 */     ItemStack itemstack = p_193951_1_.getRecipeOutput();
/* 481 */     this.field_191915_z.func_192685_a(p_193951_1_);
/* 482 */     this.field_191915_z.func_194187_a(Ingredient.func_193369_a(new ItemStack[] { itemstack }, ), ((Slot)p_193951_2_.get(0)).xDisplayPosition, ((Slot)p_193951_2_.get(0)).yDisplayPosition);
/* 483 */     int i = this.field_193961_o.getWidth();
/* 484 */     int j = this.field_193961_o.getHeight();
/* 485 */     int k = (p_193951_1_ instanceof ShapedRecipes) ? ((ShapedRecipes)p_193951_1_).func_192403_f() : i;
/* 486 */     int l = 1;
/* 487 */     Iterator<Ingredient> iterator = p_193951_1_.func_192400_c().iterator();
/*     */     
/* 489 */     for (int i1 = 0; i1 < j; i1++) {
/*     */       
/* 491 */       for (int j1 = 0; j1 < k; j1++) {
/*     */         
/* 493 */         if (!iterator.hasNext()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 498 */         Ingredient ingredient = iterator.next();
/*     */         
/* 500 */         if (ingredient != Ingredient.field_193370_a) {
/*     */           
/* 502 */           Slot slot = p_193951_2_.get(l);
/* 503 */           this.field_191915_z.func_194187_a(ingredient, slot.xDisplayPosition, slot.yDisplayPosition);
/*     */         } 
/*     */         
/* 506 */         l++;
/*     */       } 
/*     */       
/* 509 */       if (k < i)
/*     */       {
/* 511 */         l += i - k;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_193956_j() {
/* 518 */     if (this.field_191888_F.getConnection() != null)
/*     */     {
/* 520 */       this.field_191888_F.getConnection().sendPacket((Packet)new CPacketRecipeInfo(func_191878_b(), this.field_193964_s.func_192815_c()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\recipebook\GuiRecipeBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */