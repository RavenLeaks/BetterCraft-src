/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*     */ import net.minecraft.tileentity.CommandBlockBaseLogic;
/*     */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*     */ import net.minecraft.util.ITabCompleter;
/*     */ import net.minecraft.util.TabCompleter;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiCommandBlock
/*     */   extends GuiScreen
/*     */   implements ITabCompleter
/*     */ {
/*     */   private GuiTextField commandTextField;
/*     */   private GuiTextField previousOutputTextField;
/*     */   private final TileEntityCommandBlock commandBlock;
/*     */   private GuiButton doneBtn;
/*     */   private GuiButton cancelBtn;
/*     */   private GuiButton outputBtn;
/*     */   private GuiButton modeBtn;
/*     */   private GuiButton conditionalBtn;
/*     */   private GuiButton autoExecBtn;
/*     */   private boolean trackOutput;
/*  32 */   private TileEntityCommandBlock.Mode commandBlockMode = TileEntityCommandBlock.Mode.REDSTONE;
/*     */   
/*     */   private TabCompleter tabCompleter;
/*     */   private boolean conditional;
/*     */   private boolean automatic;
/*     */   
/*     */   public GuiCommandBlock(TileEntityCommandBlock commandBlockIn) {
/*  39 */     this.commandBlock = commandBlockIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  47 */     this.commandTextField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  56 */     final CommandBlockBaseLogic commandblockbaselogic = this.commandBlock.getCommandBlockLogic();
/*  57 */     Keyboard.enableRepeatEvents(true);
/*  58 */     this.buttonList.clear();
/*  59 */     this.doneBtn = addButton(new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0])));
/*  60 */     this.cancelBtn = addButton(new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  61 */     this.outputBtn = addButton(new GuiButton(4, this.width / 2 + 150 - 20, 135, 20, 20, "O"));
/*  62 */     this.modeBtn = addButton(new GuiButton(5, this.width / 2 - 50 - 100 - 4, 165, 100, 20, I18n.format("advMode.mode.sequence", new Object[0])));
/*  63 */     this.conditionalBtn = addButton(new GuiButton(6, this.width / 2 - 50, 165, 100, 20, I18n.format("advMode.mode.unconditional", new Object[0])));
/*  64 */     this.autoExecBtn = addButton(new GuiButton(7, this.width / 2 + 50 + 4, 165, 100, 20, I18n.format("advMode.mode.redstoneTriggered", new Object[0])));
/*  65 */     this.commandTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
/*  66 */     this.commandTextField.setMaxStringLength(32500);
/*  67 */     this.commandTextField.setFocused(true);
/*  68 */     this.previousOutputTextField = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 150, 135, 276, 20);
/*  69 */     this.previousOutputTextField.setMaxStringLength(32500);
/*  70 */     this.previousOutputTextField.setEnabled(false);
/*  71 */     this.previousOutputTextField.setText("-");
/*  72 */     this.doneBtn.enabled = false;
/*  73 */     this.outputBtn.enabled = false;
/*  74 */     this.modeBtn.enabled = false;
/*  75 */     this.conditionalBtn.enabled = false;
/*  76 */     this.autoExecBtn.enabled = false;
/*  77 */     this.tabCompleter = new TabCompleter(this.commandTextField, true)
/*     */       {
/*     */         @Nullable
/*     */         public BlockPos getTargetBlockPos()
/*     */         {
/*  82 */           return commandblockbaselogic.getPosition();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateGui() {
/*  89 */     CommandBlockBaseLogic commandblockbaselogic = this.commandBlock.getCommandBlockLogic();
/*  90 */     this.commandTextField.setText(commandblockbaselogic.getCommand());
/*  91 */     this.trackOutput = commandblockbaselogic.shouldTrackOutput();
/*  92 */     this.commandBlockMode = this.commandBlock.getMode();
/*  93 */     this.conditional = this.commandBlock.isConditional();
/*  94 */     this.automatic = this.commandBlock.isAuto();
/*  95 */     updateCmdOutput();
/*  96 */     updateMode();
/*  97 */     updateConditional();
/*  98 */     updateAutoExec();
/*  99 */     this.doneBtn.enabled = true;
/* 100 */     this.outputBtn.enabled = true;
/* 101 */     this.modeBtn.enabled = true;
/* 102 */     this.conditionalBtn.enabled = true;
/* 103 */     this.autoExecBtn.enabled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 111 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 119 */     if (button.enabled) {
/*     */       
/* 121 */       CommandBlockBaseLogic commandblockbaselogic = this.commandBlock.getCommandBlockLogic();
/*     */       
/* 123 */       if (button.id == 1) {
/*     */         
/* 125 */         commandblockbaselogic.setTrackOutput(this.trackOutput);
/* 126 */         this.mc.displayGuiScreen(null);
/*     */       }
/* 128 */       else if (button.id == 0) {
/*     */         
/* 130 */         PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/* 131 */         commandblockbaselogic.fillInInfo((ByteBuf)packetbuffer);
/* 132 */         packetbuffer.writeString(this.commandTextField.getText());
/* 133 */         packetbuffer.writeBoolean(commandblockbaselogic.shouldTrackOutput());
/* 134 */         packetbuffer.writeString(this.commandBlockMode.name());
/* 135 */         packetbuffer.writeBoolean(this.conditional);
/* 136 */         packetbuffer.writeBoolean(this.automatic);
/* 137 */         this.mc.getConnection().sendPacket((Packet)new CPacketCustomPayload("MC|AutoCmd", packetbuffer));
/*     */         
/* 139 */         if (!commandblockbaselogic.shouldTrackOutput())
/*     */         {
/* 141 */           commandblockbaselogic.setLastOutput(null);
/*     */         }
/*     */         
/* 144 */         this.mc.displayGuiScreen(null);
/*     */       }
/* 146 */       else if (button.id == 4) {
/*     */         
/* 148 */         commandblockbaselogic.setTrackOutput(!commandblockbaselogic.shouldTrackOutput());
/* 149 */         updateCmdOutput();
/*     */       }
/* 151 */       else if (button.id == 5) {
/*     */         
/* 153 */         nextMode();
/* 154 */         updateMode();
/*     */       }
/* 156 */       else if (button.id == 6) {
/*     */         
/* 158 */         this.conditional = !this.conditional;
/* 159 */         updateConditional();
/*     */       }
/* 161 */       else if (button.id == 7) {
/*     */         
/* 163 */         this.automatic = !this.automatic;
/* 164 */         updateAutoExec();
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
/* 175 */     this.tabCompleter.resetRequested();
/*     */     
/* 177 */     if (keyCode == 15) {
/*     */       
/* 179 */       this.tabCompleter.complete();
/*     */     }
/*     */     else {
/*     */       
/* 183 */       this.tabCompleter.resetDidComplete();
/*     */     } 
/*     */     
/* 186 */     this.commandTextField.textboxKeyTyped(typedChar, keyCode);
/* 187 */     this.previousOutputTextField.textboxKeyTyped(typedChar, keyCode);
/*     */     
/* 189 */     if (keyCode != 28 && keyCode != 156) {
/*     */       
/* 191 */       if (keyCode == 1)
/*     */       {
/* 193 */         actionPerformed(this.cancelBtn);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 198 */       actionPerformed(this.doneBtn);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 207 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 208 */     this.commandTextField.mouseClicked(mouseX, mouseY, mouseButton);
/* 209 */     this.previousOutputTextField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 217 */     drawDefaultBackground();
/* 218 */     drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), this.width / 2, 20, 16777215);
/* 219 */     drawString(this.fontRendererObj, I18n.format("advMode.command", new Object[0]), this.width / 2 - 150, 40, 10526880);
/* 220 */     this.commandTextField.drawTextBox();
/* 221 */     int i = 75;
/* 222 */     int j = 0;
/* 223 */     drawString(this.fontRendererObj, I18n.format("advMode.nearestPlayer", new Object[0]), this.width / 2 - 140, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 224 */     drawString(this.fontRendererObj, I18n.format("advMode.randomPlayer", new Object[0]), this.width / 2 - 140, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 225 */     drawString(this.fontRendererObj, I18n.format("advMode.allPlayers", new Object[0]), this.width / 2 - 140, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 226 */     drawString(this.fontRendererObj, I18n.format("advMode.allEntities", new Object[0]), this.width / 2 - 140, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 227 */     drawString(this.fontRendererObj, I18n.format("advMode.self", new Object[0]), this.width / 2 - 140, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/*     */     
/* 229 */     if (!this.previousOutputTextField.getText().isEmpty()) {
/*     */       
/* 231 */       i = i + j * this.fontRendererObj.FONT_HEIGHT + 1;
/* 232 */       drawString(this.fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), this.width / 2 - 150, i + 4, 10526880);
/* 233 */       this.previousOutputTextField.drawTextBox();
/*     */     } 
/*     */     
/* 236 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateCmdOutput() {
/* 241 */     CommandBlockBaseLogic commandblockbaselogic = this.commandBlock.getCommandBlockLogic();
/*     */     
/* 243 */     if (commandblockbaselogic.shouldTrackOutput()) {
/*     */       
/* 245 */       this.outputBtn.displayString = "O";
/*     */       
/* 247 */       if (commandblockbaselogic.getLastOutput() != null)
/*     */       {
/* 249 */         this.previousOutputTextField.setText(commandblockbaselogic.getLastOutput().getUnformattedText());
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 254 */       this.outputBtn.displayString = "X";
/* 255 */       this.previousOutputTextField.setText("-");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateMode() {
/* 261 */     switch (this.commandBlockMode) {
/*     */       
/*     */       case SEQUENCE:
/* 264 */         this.modeBtn.displayString = I18n.format("advMode.mode.sequence", new Object[0]);
/*     */         break;
/*     */       
/*     */       case null:
/* 268 */         this.modeBtn.displayString = I18n.format("advMode.mode.auto", new Object[0]);
/*     */         break;
/*     */       
/*     */       case REDSTONE:
/* 272 */         this.modeBtn.displayString = I18n.format("advMode.mode.redstone", new Object[0]);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void nextMode() {
/* 278 */     switch (this.commandBlockMode) {
/*     */       
/*     */       case SEQUENCE:
/* 281 */         this.commandBlockMode = TileEntityCommandBlock.Mode.AUTO;
/*     */         break;
/*     */       
/*     */       case null:
/* 285 */         this.commandBlockMode = TileEntityCommandBlock.Mode.REDSTONE;
/*     */         break;
/*     */       
/*     */       case REDSTONE:
/* 289 */         this.commandBlockMode = TileEntityCommandBlock.Mode.SEQUENCE;
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateConditional() {
/* 295 */     if (this.conditional) {
/*     */       
/* 297 */       this.conditionalBtn.displayString = I18n.format("advMode.mode.conditional", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 301 */       this.conditionalBtn.displayString = I18n.format("advMode.mode.unconditional", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateAutoExec() {
/* 307 */     if (this.automatic) {
/*     */       
/* 309 */       this.autoExecBtn.displayString = I18n.format("advMode.mode.autoexec.bat", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 313 */       this.autoExecBtn.displayString = I18n.format("advMode.mode.redstoneTriggered", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCompletions(String... newCompletions) {
/* 322 */     this.tabCompleter.setCompletions(newCompletions);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiCommandBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */