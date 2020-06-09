/*    */ package wdl.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import wdl.WDL;
/*    */ import wdl.WorldBackup;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiWDLBackup
/*    */   extends GuiScreen
/*    */ {
/*    */   private GuiScreen parent;
/*    */   private String description;
/*    */   private WorldBackup.WorldBackupType backupType;
/*    */   
/*    */   public GuiWDLBackup(GuiScreen parent) {
/* 22 */     this.parent = parent;
/*    */     
/* 24 */     this.description = String.valueOf(I18n.format("wdl.gui.backup.description1", new Object[0])) + "\n\n" + 
/* 25 */       I18n.format("wdl.gui.backup.description2", new Object[0]) + "\n\n" + 
/* 26 */       I18n.format("wdl.gui.backup.description3", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 31 */     this.backupType = WorldBackup.WorldBackupType.match(
/* 32 */         WDL.baseProps.getProperty("Backup", "ZIP"));
/*    */     
/* 34 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, 32, 
/* 35 */           getBackupButtonText()));
/*    */     
/* 37 */     this.buttonList.add(new GuiButton(100, this.width / 2 - 100, 
/* 38 */           this.height - 29, I18n.format("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 43 */     if (!button.enabled) {
/*    */       return;
/*    */     }
/*    */     
/* 47 */     if (button.id == 0) {
/* 48 */       switch (this.backupType) { case NONE:
/* 49 */           this.backupType = WorldBackup.WorldBackupType.FOLDER; break;
/* 50 */         case null: this.backupType = WorldBackup.WorldBackupType.ZIP; break;
/* 51 */         case ZIP: this.backupType = WorldBackup.WorldBackupType.NONE;
/*    */           break; }
/*    */       
/* 54 */       button.displayString = getBackupButtonText();
/* 55 */     } else if (button.id == 100) {
/* 56 */       this.mc.displayGuiScreen(this.parent);
/*    */     } 
/*    */   }
/*    */   
/*    */   private String getBackupButtonText() {
/* 61 */     return I18n.format("wdl.gui.backup.backupMode", new Object[] {
/* 62 */           this.backupType.getDescription()
/*    */         });
/*    */   }
/*    */   
/*    */   public void onGuiClosed() {
/* 67 */     WDL.baseProps.setProperty("Backup", this.backupType.name());
/*    */     
/* 69 */     WDL.saveProps();
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 74 */     Utils.drawListBackground(23, 32, 0, 0, this.height, this.width);
/*    */     
/* 76 */     drawCenteredString(this.fontRendererObj, 
/* 77 */         I18n.format("wdl.gui.backup.title", new Object[0]), this.width / 2, 8, 
/* 78 */         16777215);
/*    */     
/* 80 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */     
/* 82 */     Utils.drawGuiInfoBox(this.description, this.width - 50, 3 * this.height / 5, this.width, 
/* 83 */         this.height, 48);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLBackup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */