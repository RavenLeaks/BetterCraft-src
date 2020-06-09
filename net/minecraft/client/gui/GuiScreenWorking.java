/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.util.IProgressUpdate;
/*    */ 
/*    */ public class GuiScreenWorking
/*    */   extends GuiScreen implements IProgressUpdate {
/*  7 */   private String title = "";
/*  8 */   private String stage = "";
/*    */ 
/*    */   
/*    */   private int progress;
/*    */   
/*    */   private boolean doneWorking;
/*    */ 
/*    */   
/*    */   public void displaySavingString(String message) {
/* 17 */     resetProgressAndMessage(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetProgressAndMessage(String message) {
/* 26 */     this.title = message;
/* 27 */     displayLoadingString("Working...");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void displayLoadingString(String message) {
/* 35 */     this.stage = message;
/* 36 */     setLoadingProgress(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLoadingProgress(int progress) {
/* 44 */     this.progress = progress;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDoneWorking() {
/* 49 */     this.doneWorking = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 57 */     if (this.doneWorking) {
/*    */       
/* 59 */       if (!this.mc.isConnectedToRealms())
/*    */       {
/* 61 */         this.mc.displayGuiScreen(null);
/*    */       }
/*    */     }
/*    */     else {
/*    */       
/* 66 */       drawDefaultBackground();
/* 67 */       drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 70, 16777215);
/* 68 */       drawCenteredString(this.fontRendererObj, String.valueOf(this.stage) + " " + this.progress + "%", this.width / 2, 90, 16777215);
/* 69 */       super.drawScreen(mouseX, mouseY, partialTicks);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiScreenWorking.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */