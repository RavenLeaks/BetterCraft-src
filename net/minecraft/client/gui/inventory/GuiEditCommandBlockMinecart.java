/*     */ package net.minecraft.client.gui.inventory;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*     */ import net.minecraft.tileentity.CommandBlockBaseLogic;
/*     */ import net.minecraft.util.ITabCompleter;
/*     */ import net.minecraft.util.TabCompleter;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiEditCommandBlockMinecart
/*     */   extends GuiScreen implements ITabCompleter {
/*     */   private GuiTextField commandField;
/*     */   private GuiTextField previousEdit;
/*     */   private final CommandBlockBaseLogic commandBlockLogic;
/*     */   private GuiButton doneButton;
/*     */   private GuiButton cancelButton;
/*     */   private GuiButton outputButton;
/*     */   private boolean trackOutput;
/*     */   private TabCompleter tabCompleter;
/*     */   
/*     */   public GuiEditCommandBlockMinecart(CommandBlockBaseLogic p_i46595_1_) {
/*  32 */     this.commandBlockLogic = p_i46595_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  40 */     this.commandField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  49 */     Keyboard.enableRepeatEvents(true);
/*  50 */     this.buttonList.clear();
/*  51 */     this.doneButton = addButton(new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0])));
/*  52 */     this.cancelButton = addButton(new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  53 */     this.outputButton = addButton(new GuiButton(4, this.width / 2 + 150 - 20, 150, 20, 20, "O"));
/*  54 */     this.commandField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
/*  55 */     this.commandField.setMaxStringLength(32500);
/*  56 */     this.commandField.setFocused(true);
/*  57 */     this.commandField.setText(this.commandBlockLogic.getCommand());
/*  58 */     this.previousEdit = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 150, 150, 276, 20);
/*  59 */     this.previousEdit.setMaxStringLength(32500);
/*  60 */     this.previousEdit.setEnabled(false);
/*  61 */     this.previousEdit.setText("-");
/*  62 */     this.trackOutput = this.commandBlockLogic.shouldTrackOutput();
/*  63 */     updateCommandOutput();
/*  64 */     this.doneButton.enabled = !this.commandField.getText().trim().isEmpty();
/*  65 */     this.tabCompleter = new TabCompleter(this.commandField, true)
/*     */       {
/*     */         @Nullable
/*     */         public BlockPos getTargetBlockPos()
/*     */         {
/*  70 */           return GuiEditCommandBlockMinecart.this.commandBlockLogic.getPosition();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  80 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  88 */     if (button.enabled)
/*     */     {
/*  90 */       if (button.id == 1) {
/*     */         
/*  92 */         this.commandBlockLogic.setTrackOutput(this.trackOutput);
/*  93 */         this.mc.displayGuiScreen(null);
/*     */       }
/*  95 */       else if (button.id == 0) {
/*     */         
/*  97 */         PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/*  98 */         packetbuffer.writeByte(this.commandBlockLogic.getCommandBlockType());
/*  99 */         this.commandBlockLogic.fillInInfo((ByteBuf)packetbuffer);
/* 100 */         packetbuffer.writeString(this.commandField.getText());
/* 101 */         packetbuffer.writeBoolean(this.commandBlockLogic.shouldTrackOutput());
/* 102 */         this.mc.getConnection().sendPacket((Packet)new CPacketCustomPayload("MC|AdvCmd", packetbuffer));
/*     */         
/* 104 */         if (!this.commandBlockLogic.shouldTrackOutput())
/*     */         {
/* 106 */           this.commandBlockLogic.setLastOutput(null);
/*     */         }
/*     */         
/* 109 */         this.mc.displayGuiScreen(null);
/*     */       }
/* 111 */       else if (button.id == 4) {
/*     */         
/* 113 */         this.commandBlockLogic.setTrackOutput(!this.commandBlockLogic.shouldTrackOutput());
/* 114 */         updateCommandOutput();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 125 */     this.tabCompleter.resetRequested();
/*     */     
/* 127 */     if (keyCode == 15) {
/*     */       
/* 129 */       this.tabCompleter.complete();
/*     */     }
/*     */     else {
/*     */       
/* 133 */       this.tabCompleter.resetDidComplete();
/*     */     } 
/*     */     
/* 136 */     this.commandField.textboxKeyTyped(typedChar, keyCode);
/* 137 */     this.previousEdit.textboxKeyTyped(typedChar, keyCode);
/* 138 */     this.doneButton.enabled = !this.commandField.getText().trim().isEmpty();
/*     */     
/* 140 */     if (keyCode != 28 && keyCode != 156) {
/*     */       
/* 142 */       if (keyCode == 1)
/*     */       {
/* 144 */         actionPerformed(this.cancelButton);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 149 */       actionPerformed(this.doneButton);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 158 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 159 */     this.commandField.mouseClicked(mouseX, mouseY, mouseButton);
/* 160 */     this.previousEdit.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 168 */     drawDefaultBackground();
/* 169 */     drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), this.width / 2, 20, 16777215);
/* 170 */     drawString(this.fontRendererObj, I18n.format("advMode.command", new Object[0]), this.width / 2 - 150, 40, 10526880);
/* 171 */     this.commandField.drawTextBox();
/* 172 */     int i = 75;
/* 173 */     int j = 0;
/* 174 */     drawString(this.fontRendererObj, I18n.format("advMode.nearestPlayer", new Object[0]), this.width / 2 - 140, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 175 */     drawString(this.fontRendererObj, I18n.format("advMode.randomPlayer", new Object[0]), this.width / 2 - 140, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 176 */     drawString(this.fontRendererObj, I18n.format("advMode.allPlayers", new Object[0]), this.width / 2 - 140, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 177 */     drawString(this.fontRendererObj, I18n.format("advMode.allEntities", new Object[0]), this.width / 2 - 140, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 178 */     drawString(this.fontRendererObj, I18n.format("advMode.self", new Object[0]), this.width / 2 - 140, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/*     */     
/* 180 */     if (!this.previousEdit.getText().isEmpty()) {
/*     */       
/* 182 */       i = i + j * this.fontRendererObj.FONT_HEIGHT + 20;
/* 183 */       drawString(this.fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), this.width / 2 - 150, i, 10526880);
/* 184 */       this.previousEdit.drawTextBox();
/*     */     } 
/*     */     
/* 187 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateCommandOutput() {
/* 192 */     if (this.commandBlockLogic.shouldTrackOutput()) {
/*     */       
/* 194 */       this.outputButton.displayString = "O";
/*     */       
/* 196 */       if (this.commandBlockLogic.getLastOutput() != null)
/*     */       {
/* 198 */         this.previousEdit.setText(this.commandBlockLogic.getLastOutput().getUnformattedText());
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 203 */       this.outputButton.displayString = "X";
/* 204 */       this.previousEdit.setText("-");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCompletions(String... newCompletions) {
/* 213 */     this.tabCompleter.setCompletions(newCompletions);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\inventory\GuiEditCommandBlockMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */