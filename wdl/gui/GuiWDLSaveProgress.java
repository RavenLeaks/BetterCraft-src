/*     */ package wdl.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import wdl.WorldBackup;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiWDLSaveProgress
/*     */   extends GuiTurningCameraBase
/*     */   implements WorldBackup.IBackupProgressMonitor
/*     */ {
/*     */   private final String title;
/*  19 */   private volatile String majorTaskMessage = "";
/*  20 */   private volatile String minorTaskMessage = "";
/*     */ 
/*     */   
/*     */   private volatile int majorTaskNumber;
/*     */   
/*     */   private final int majorTaskCount;
/*     */   
/*     */   private volatile int minorTaskProgress;
/*     */   
/*     */   private volatile int minorTaskMaximum;
/*     */   
/*     */   private volatile boolean doneWorking = false;
/*     */ 
/*     */   
/*     */   public GuiWDLSaveProgress(String title, int taskCount) {
/*  35 */     this.title = title;
/*  36 */     this.majorTaskCount = taskCount;
/*  37 */     this.majorTaskNumber = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startMajorTask(String message, int minorTaskMaximum) {
/*  44 */     this.majorTaskMessage = message;
/*  45 */     this.majorTaskNumber++;
/*     */     
/*  47 */     this.minorTaskMessage = "";
/*  48 */     this.minorTaskProgress = 0;
/*  49 */     this.minorTaskMaximum = minorTaskMaximum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinorTaskProgress(String message, int progress) {
/*  61 */     this.minorTaskMessage = message;
/*  62 */     this.minorTaskProgress = progress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinorTaskProgress(int progress) {
/*  69 */     this.minorTaskProgress = progress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDoneWorking() {
/*  76 */     this.doneWorking = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  85 */     if (this.doneWorking) {
/*  86 */       this.mc.displayGuiScreen(null);
/*     */     } else {
/*  88 */       Utils.drawBorder(32, 32, 0, 0, this.height, this.width);
/*     */       
/*  90 */       String majorTaskInfo = this.majorTaskMessage;
/*  91 */       if (this.majorTaskCount > 1) {
/*  92 */         majorTaskInfo = I18n.format(
/*  93 */             "wdl.gui.saveProgress.progressInfo", new Object[] { this.majorTaskMessage, 
/*  94 */               Integer.valueOf(this.majorTaskNumber), Integer.valueOf(this.majorTaskCount) });
/*     */       }
/*  96 */       String minorTaskInfo = this.minorTaskMessage;
/*  97 */       if (this.minorTaskMaximum > 1) {
/*  98 */         majorTaskInfo = I18n.format(
/*  99 */             "wdl.gui.saveProgress.progressInfo", new Object[] { this.minorTaskMessage, 
/* 100 */               Integer.valueOf(this.minorTaskProgress), Integer.valueOf(this.minorTaskMaximum) });
/*     */       }
/*     */       
/* 103 */       drawCenteredString(this.fontRendererObj, this.title, 
/* 104 */           this.width / 2, 8, 16777215);
/*     */       
/* 106 */       drawCenteredString(this.fontRendererObj, 
/* 107 */           majorTaskInfo, this.width / 2, 100, 16777215);
/*     */       
/* 109 */       if (this.minorTaskMaximum > 0) {
/* 110 */         drawProgressBar(110, 84, 89, 
/* 111 */             this.majorTaskNumber * this.minorTaskMaximum + this.minorTaskProgress, (
/* 112 */             this.majorTaskCount + 1) * this.minorTaskMaximum);
/*     */       } else {
/* 114 */         drawProgressBar(110, 84, 89, this.majorTaskNumber, 
/* 115 */             this.majorTaskCount);
/*     */       } 
/*     */       
/* 118 */       drawCenteredString(this.fontRendererObj, minorTaskInfo, 
/* 119 */           this.width / 2, 130, 16777215);
/* 120 */       drawProgressBar(140, 64, 69, this.minorTaskProgress, this.minorTaskMaximum);
/*     */       
/* 122 */       super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     } 
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawProgressBar(int y, int emptyV, int filledV, int progress, int maximum) {
/* 146 */     if (maximum == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 150 */     this.mc.getTextureManager().bindTexture(Gui.ICONS);
/*     */     
/* 152 */     int fullWidth = 182;
/* 153 */     int currentWidth = progress * 182 / maximum;
/* 154 */     int height = 5;
/*     */     
/* 156 */     int x = this.width / 2 - 91;
/* 157 */     int u = 0;
/*     */     
/* 159 */     drawTexturedModalRect(x, y, 0, emptyV, 182, 5);
/* 160 */     drawTexturedModalRect(x, y, 0, filledV, currentWidth, 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumberOfFiles(int num) {
/* 172 */     this.minorTaskMaximum = num;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNextFile(String name) {
/* 177 */     this.minorTaskProgress++;
/* 178 */     this.minorTaskMessage = I18n.format("wdl.saveProgress.backingUp.file", new Object[] { name });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLSaveProgress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */