/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.nzxter.bettercraft.utils.ColorUtils;
/*     */ import me.nzxter.bettercraft.utils.RenderUtils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class GuiButton
/*     */   extends Gui {
/*  15 */   protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
/*     */ 
/*     */   
/*     */   protected int width;
/*     */ 
/*     */   
/*     */   protected int height;
/*     */ 
/*     */   
/*     */   public int xPosition;
/*     */ 
/*     */   
/*     */   public int yPosition;
/*     */   
/*     */   public String displayString;
/*     */   
/*     */   public int id;
/*     */   
/*     */   public boolean enabled;
/*     */   
/*     */   public boolean visible;
/*     */   
/*     */   protected boolean hovered;
/*     */ 
/*     */   
/*     */   public GuiButton(int buttonId, int x, int y, String buttonText) {
/*  41 */     this(buttonId, x, y, 200, 20, buttonText);
/*     */   }
/*     */   
/*     */   public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
/*  45 */     this.width = 200;
/*  46 */     this.height = 20;
/*  47 */     this.enabled = true;
/*  48 */     this.visible = true;
/*  49 */     this.id = buttonId;
/*  50 */     this.xPosition = x;
/*  51 */     this.yPosition = y;
/*  52 */     this.width = widthIn;
/*  53 */     this.height = heightIn;
/*  54 */     this.displayString = buttonText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getHoverState(boolean mouseOver) {
/*  62 */     int i = 1;
/*     */     
/*  64 */     if (!this.enabled) {
/*  65 */       i = 0;
/*  66 */     } else if (mouseOver) {
/*  67 */       i = 2;
/*     */     } 
/*     */     
/*  70 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191745_a(Minecraft mc, int mouseX, int mouseY, float p_191745_4_) {
/*  75 */     if (this.visible) {
/*  76 */       FontRenderer fontrenderer = mc.fontRendererObj;
/*  77 */       int fade = 200;
/*  78 */       if (!this.hovered) {
/*  79 */         if (fade != 100) {
/*  80 */           fade += 10;
/*     */         }
/*     */       } else {
/*  83 */         if (fade <= 40) {
/*     */           return;
/*     */         }
/*  86 */         if (fade != 70) {
/*  87 */           fade -= 100;
/*     */         }
/*     */       } 
/*  90 */       Color color = new Color(50, 45, 80, fade);
/*  91 */       Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 
/*  92 */           color.darker().darker().getRGB());
/*  93 */       RenderUtils.drawLine(this.xPosition, this.yPosition, (this.xPosition + this.width), this.yPosition, 
/*  94 */           ColorUtils.rainbowColor(200000000L, 1.0F), 1.0F);
/*  95 */       RenderUtils.drawLine(this.xPosition, this.yPosition, this.xPosition, (this.yPosition + this.height), 
/*  96 */           ColorUtils.rainbowColor(200000000L, 1.0F), 1.0F);
/*  97 */       RenderUtils.drawLine((this.xPosition + this.width), (this.yPosition + this.height), (this.xPosition + this.width), 
/*  98 */           this.yPosition, ColorUtils.rainbowColor(200000000L, 1.0F), 1.0F);
/*  99 */       RenderUtils.drawLine(this.xPosition, (this.yPosition + this.height), (this.xPosition + this.width), (
/* 100 */           this.yPosition + this.height), ColorUtils.rainbowColor(200000000L, 1.0F), 1.0F);
/* 101 */       boolean bl = this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && 
/* 102 */         mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/* 103 */       if (this.enabled) {
/* 104 */         drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, 
/* 105 */             this.yPosition + (this.height - 8) / 2, 14737632);
/*     */       } else {
/* 107 */         drawCenteredString(fontrenderer, "§7" + this.displayString, 
/* 108 */             this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 14737632);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 133 */     return (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && 
/* 134 */       mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMouseOver() {
/* 141 */     return this.hovered;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawButtonForegroundLayer(int mouseX, int mouseY) {}
/*     */   
/*     */   public void playPressSound(SoundHandler soundHandlerIn) {
/* 148 */     soundHandlerIn.playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*     */   }
/*     */   
/*     */   public int getButtonWidth() {
/* 152 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(int width) {
/* 156 */     this.width = width;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */