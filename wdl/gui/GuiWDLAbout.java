/*     */ package wdl.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import wdl.WDL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiWDLAbout
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen parent;
/*     */   private static final String FORUMS_THREAD = "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2520465";
/*     */   private static final String COREMOD_GITHUB = "https://github.com/Pokechu22/WorldDownloader";
/*     */   private static final String LITEMOD_GITHUB = "https://github.com/uyjulian/LiteModWDL/";
/*     */   private TextList list;
/*     */   
/*     */   public GuiWDLAbout(GuiScreen parent) {
/*  33 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  38 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 155, 18, 150, 20, 
/*  39 */           I18n.format("wdl.gui.about.extensions", new Object[0])));
/*  40 */     this.buttonList.add(new GuiButton(1, this.width / 2 + 5, 18, 150, 20, 
/*  41 */           I18n.format("wdl.gui.about.debugInfo", new Object[0])));
/*  42 */     this.buttonList.add(new GuiButton(2, this.width / 2 - 100, 
/*  43 */           this.height - 29, I18n.format("gui.done", new Object[0])));
/*     */     
/*  45 */     String wdlVersion = "1.11a-beta1";
/*     */     
/*  47 */     String mcVersion = WDL.getMinecraftVersionInfo();
/*     */     
/*  49 */     this.list = new TextList(this.mc, this.width, this.height, 39, 32);
/*  50 */     this.list.addLine(I18n.format("wdl.gui.about.blurb", new Object[0]));
/*  51 */     this.list.addBlankLine();
/*  52 */     this.list.addLine(I18n.format("wdl.gui.about.version", new Object[] { wdlVersion, 
/*  53 */             mcVersion }));
/*  54 */     this.list.addBlankLine();
/*     */     
/*  56 */     String currentLanguage = WDL.minecraft.getLanguageManager()
/*  57 */       .getCurrentLanguage().toString();
/*  58 */     String translatorCredit = I18n.format("wdl.translatorCredit", new Object[] {
/*  59 */           currentLanguage });
/*  60 */     if (translatorCredit != null && !translatorCredit.isEmpty()) {
/*  61 */       this.list.addLine(translatorCredit);
/*  62 */       this.list.addBlankLine();
/*     */     } 
/*     */     
/*  65 */     this.list.addLinkLine(I18n.format("wdl.gui.about.forumThread", new Object[0]), "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2520465");
/*  66 */     this.list.addBlankLine();
/*  67 */     this.list.addLinkLine(I18n.format("wdl.gui.about.coremodSrc", new Object[0]), "https://github.com/Pokechu22/WorldDownloader");
/*  68 */     this.list.addBlankLine();
/*  69 */     this.list.addLinkLine(I18n.format("wdl.gui.about.litemodSrc", new Object[0]), "https://github.com/uyjulian/LiteModWDL/");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  74 */     if (button.id == 0) {
/*     */       
/*  76 */       this.mc.displayGuiScreen(new GuiWDLExtensions(this));
/*  77 */     } else if (button.id == 1) {
/*     */       
/*  79 */       setClipboardString(WDL.getDebugInfo());
/*     */       
/*  81 */       button.displayString = 
/*  82 */         I18n.format("wdl.gui.about.debugInfo.copied", new Object[0]);
/*  83 */     } else if (button.id == 2) {
/*     */       
/*  85 */       this.mc.displayGuiScreen(this.parent);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  95 */     this.list.mouseClicked(mouseX, mouseY, mouseButton);
/*  96 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 104 */     super.handleMouseInput();
/* 105 */     this.list.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 110 */     if (this.list.mouseReleased(mouseX, mouseY, state)) {
/*     */       return;
/*     */     }
/* 113 */     super.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 118 */     if (this.list == null) {
/*     */       return;
/*     */     }
/* 121 */     drawDefaultBackground();
/*     */     
/* 123 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 125 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 127 */     drawCenteredString(this.fontRendererObj, I18n.format("wdl.gui.about.title", new Object[0]), 
/* 128 */         this.width / 2, 2, 16777215);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLAbout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */