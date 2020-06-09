/*    */ package me.nzxter.bettercraft.mods.creative;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import me.nzxter.bettercraft.utils.ItemStackUtils;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.GuiTextField;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiItemCreator
/*    */   extends GuiScreen
/*    */ {
/*    */   private GuiScreen previous;
/*    */   private GuiTextField nameOrId;
/*    */   private GuiTextField name;
/*    */   
/*    */   public GuiItemCreator(GuiScreen previous) {
/* 25 */     this.previous = previous;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 30 */     Keyboard.enableRepeatEvents(true);
/* 31 */     this.buttonList.clear();
/* 32 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 5 + 100, "Give Item"));
/* 33 */     (this.nameOrId = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 5 + 30, 200, 20))
/* 34 */       .setText("diamond_sword 1 0");
/* 35 */     this.nameOrId.setMaxStringLength(2147483647);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 41 */     this.nameOrId.updateCursorCounter();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 48 */     if (button.id == 0) {
/*    */       
/* 50 */       (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketCreativeInventoryAction(36, ItemStackUtils.stringtostack(this.nameOrId.getText())));
/* 51 */       this.mc.displayGuiScreen(this.previous);
/*    */     } 
/*    */     
/* 54 */     if (button.id == 1) {
/* 55 */       this.mc.displayGuiScreen(this.previous);
/*    */     }
/*    */   }
/*    */   
/*    */   public static String withColors(String identifier, String input) {
/* 60 */     String output = input;
/* 61 */     int index = output.indexOf(identifier);
/* 62 */     while (output.indexOf(identifier) != -1) {
/* 63 */       output = output.replace(identifier, "§");
/* 64 */       index = output.indexOf(identifier);
/*    */     } 
/* 66 */     return output;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 73 */     this.nameOrId.textboxKeyTyped(typedChar, keyCode);
/*    */     
/* 75 */     super.keyTyped(typedChar, keyCode);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 80 */     this.nameOrId.mouseClicked(mouseX, mouseY, mouseButton);
/*    */     
/* 82 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 87 */     drawDefaultBackground();
/*    */     
/* 89 */     this.nameOrId.drawTextBox();
/*    */ 
/*    */     
/* 92 */     drawCenteredString(this.fontRendererObj, "Name/ID", this.width / 2, 
/* 93 */         this.nameOrId.yPosition - 15, -1);
/*    */     
/* 95 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\creative\GuiItemCreator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */