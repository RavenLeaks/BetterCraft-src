/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketEntityAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiSleepMP
/*    */   extends GuiChat
/*    */ {
/*    */   public void initGui() {
/* 16 */     super.initGui();
/* 17 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 40, I18n.format("multiplayer.stopSleeping", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 26 */     if (keyCode == 1) {
/*    */       
/* 28 */       wakeFromSleep();
/*    */     }
/* 30 */     else if (keyCode != 28 && keyCode != 156) {
/*    */       
/* 32 */       super.keyTyped(typedChar, keyCode);
/*    */     }
/*    */     else {
/*    */       
/* 36 */       String s = inputField.getText().trim();
/*    */       
/* 38 */       if (!s.isEmpty())
/*    */       {
/* 40 */         this.mc.player.sendChatMessage(s);
/*    */       }
/*    */       
/* 43 */       inputField.setText("");
/* 44 */       this.mc.ingameGUI.getChatGUI().resetScroll();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 53 */     if (button.id == 1) {
/*    */       
/* 55 */       wakeFromSleep();
/*    */     }
/*    */     else {
/*    */       
/* 59 */       super.actionPerformed(button);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void wakeFromSleep() {
/* 65 */     NetHandlerPlayClient nethandlerplayclient = this.mc.player.connection;
/* 66 */     nethandlerplayclient.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SLEEPING));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiSleepMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */