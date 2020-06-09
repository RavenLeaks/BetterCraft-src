/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nullable;
/*     */ import me.nzxter.bettercraft.mods.gui.GuiNameHistory;
/*     */ import me.nzxter.bettercraft.utils.ColorUtils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.ITabCompleter;
/*     */ import net.minecraft.util.TabCompleter;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class GuiChat
/*     */   extends GuiScreen
/*     */   implements ITabCompleter {
/*  23 */   private static final Logger LOGGER = LogManager.getLogger();
/*  24 */   private String historyBuffer = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   private int sentHistoryCursor = -1;
/*     */ 
/*     */   
/*     */   private TabCompleter tabCompleter;
/*     */ 
/*     */   
/*     */   protected static GuiTextField inputField;
/*     */ 
/*     */   
/*  39 */   private String defaultInputFieldText = "";
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiChat() {}
/*     */ 
/*     */   
/*     */   public GuiChat(String defaultText) {
/*  47 */     this.defaultInputFieldText = defaultText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  56 */     Keyboard.enableRepeatEvents(true);
/*  57 */     this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
/*     */     
/*  59 */     inputField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 39, this.width - 4, 12);
/*  60 */     inputField.setMaxStringLength(150);
/*     */ 
/*     */     
/*  63 */     this.buttonList.add(new GuiButton(9, this.width - 105, 5, 100, 20, "Name History"));
/*     */     
/*  65 */     inputField.setEnableBackgroundDrawing(false);
/*  66 */     inputField.setFocused(true);
/*  67 */     inputField.setText(this.defaultInputFieldText);
/*  68 */     inputField.setCanLoseFocus(false);
/*  69 */     this.tabCompleter = new ChatTabCompleter(inputField);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  77 */     Keyboard.enableRepeatEvents(false);
/*  78 */     this.mc.ingameGUI.getChatGUI().resetScroll();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  84 */     switch (button.id) {
/*     */       case 9:
/*  86 */         this.mc.displayGuiScreen((GuiScreen)new GuiNameHistory());
/*     */         break;
/*     */     } 
/*     */     
/*  90 */     super.actionPerformed(button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  99 */     inputField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 108 */     this.tabCompleter.resetRequested();
/*     */     
/* 110 */     if (keyCode == 15) {
/*     */       
/* 112 */       this.tabCompleter.complete();
/*     */     }
/*     */     else {
/*     */       
/* 116 */       this.tabCompleter.resetDidComplete();
/*     */     } 
/*     */     
/* 119 */     if (keyCode == 1) {
/*     */       
/* 121 */       this.mc.displayGuiScreen(null);
/*     */     }
/* 123 */     else if (keyCode != 28 && keyCode != 156) {
/*     */       
/* 125 */       if (keyCode == 200)
/*     */       {
/* 127 */         getSentHistory(-1);
/*     */       }
/* 129 */       else if (keyCode == 208)
/*     */       {
/* 131 */         getSentHistory(1);
/*     */       }
/* 133 */       else if (keyCode == 201)
/*     */       {
/* 135 */         this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
/*     */       }
/* 137 */       else if (keyCode == 209)
/*     */       {
/* 139 */         this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
/*     */       }
/*     */       else
/*     */       {
/* 143 */         inputField.textboxKeyTyped(typedChar, keyCode);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 148 */       String s = inputField.getText().trim();
/*     */       
/* 150 */       if (!s.isEmpty())
/*     */       {
/* 152 */         sendChatMessage(s);
/*     */       }
/*     */       
/* 155 */       this.mc.displayGuiScreen(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 164 */     super.handleMouseInput();
/* 165 */     int i = Mouse.getEventDWheel();
/*     */     
/* 167 */     if (i != 0) {
/*     */       
/* 169 */       if (i > 1)
/*     */       {
/* 171 */         i = 1;
/*     */       }
/*     */       
/* 174 */       if (i < -1)
/*     */       {
/* 176 */         i = -1;
/*     */       }
/*     */       
/* 179 */       if (!isShiftKeyDown())
/*     */       {
/* 181 */         i *= 7;
/*     */       }
/*     */       
/* 184 */       this.mc.ingameGUI.getChatGUI().scroll(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 193 */     if (mouseButton == 0) {
/*     */       
/* 195 */       ITextComponent itextcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
/*     */       
/* 197 */       if (itextcomponent != null && handleComponentClick(itextcomponent)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 203 */     inputField.mouseClicked(mouseX, mouseY, mouseButton);
/* 204 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setText(String newChatText, boolean shouldOverwrite) {
/* 212 */     if (shouldOverwrite) {
/*     */       
/* 214 */       inputField.setText(newChatText);
/*     */     }
/*     */     else {
/*     */       
/* 218 */       inputField.writeText(newChatText);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSentHistory(int msgPos) {
/* 228 */     int i = this.sentHistoryCursor + msgPos;
/* 229 */     int j = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
/* 230 */     i = MathHelper.clamp(i, 0, j);
/*     */     
/* 232 */     if (i != this.sentHistoryCursor)
/*     */     {
/* 234 */       if (i == j) {
/*     */         
/* 236 */         this.sentHistoryCursor = j;
/* 237 */         inputField.setText(this.historyBuffer);
/*     */       }
/*     */       else {
/*     */         
/* 241 */         if (this.sentHistoryCursor == j)
/*     */         {
/* 243 */           this.historyBuffer = inputField.getText();
/*     */         }
/*     */         
/* 246 */         inputField.setText(this.mc.ingameGUI.getChatGUI().getSentMessages().get(i));
/* 247 */         this.sentHistoryCursor = i;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 258 */     inputField.drawTextBox();
/*     */ 
/*     */     
/* 261 */     drawString(this.fontRendererObj, String.valueOf(inputField.getText().length()) + "/256", 5, this.height - 55, ColorUtils.rainbowEffect(0L, 1.0F).getRGB());
/*     */ 
/*     */     
/* 264 */     ITextComponent itextcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
/*     */     
/* 266 */     if (itextcomponent != null && itextcomponent.getStyle().getHoverEvent() != null)
/*     */     {
/* 268 */       handleComponentHover(itextcomponent, mouseX, mouseY);
/*     */     }
/*     */     
/* 271 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 279 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCompletions(String... newCompletions) {
/* 287 */     this.tabCompleter.setCompletions(newCompletions);
/*     */   }
/*     */   
/*     */   public static class ChatTabCompleter
/*     */     extends TabCompleter {
/* 292 */     private final Minecraft clientInstance = Minecraft.getMinecraft();
/*     */ 
/*     */     
/*     */     public ChatTabCompleter(GuiTextField p_i46749_1_) {
/* 296 */       super(p_i46749_1_, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void complete() {
/* 301 */       super.complete();
/*     */       
/* 303 */       if (this.completions.size() > 1) {
/*     */         
/* 305 */         StringBuilder stringbuilder = new StringBuilder();
/*     */         
/* 307 */         for (String s : this.completions) {
/*     */           
/* 309 */           if (stringbuilder.length() > 0)
/*     */           {
/* 311 */             stringbuilder.append(", ");
/*     */           }
/*     */           
/* 314 */           stringbuilder.append(s);
/*     */         } 
/*     */         
/* 317 */         this.clientInstance.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)new TextComponentString(stringbuilder.toString()), 1);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public BlockPos getTargetBlockPos() {
/* 324 */       BlockPos blockpos = null;
/*     */       
/* 326 */       if (this.clientInstance.objectMouseOver != null && this.clientInstance.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK)
/*     */       {
/* 328 */         blockpos = this.clientInstance.objectMouseOver.getBlockPos();
/*     */       }
/*     */       
/* 331 */       return blockpos;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */