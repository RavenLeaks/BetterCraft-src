/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ 
/*    */ public class DisconnectedRealmsScreen
/*    */   extends RealmsScreen
/*    */ {
/*    */   private final String title;
/*    */   private final ITextComponent reason;
/*    */   private List<String> lines;
/*    */   private final RealmsScreen parent;
/*    */   private int textHeight;
/*    */   
/*    */   public DisconnectedRealmsScreen(RealmsScreen parentIn, String unlocalizedTitle, ITextComponent reasonIn) {
/* 16 */     this.parent = parentIn;
/* 17 */     this.title = getLocalizedString(unlocalizedTitle);
/* 18 */     this.reason = reasonIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 23 */     Realms.setConnectedToRealms(false);
/* 24 */     Realms.clearResourcePack();
/* 25 */     buttonsClear();
/* 26 */     this.lines = fontSplit(this.reason.getFormattedText(), width() - 50);
/* 27 */     this.textHeight = this.lines.size() * fontLineHeight();
/* 28 */     buttonsAdd(newButton(0, width() / 2 - 100, height() / 2 + this.textHeight / 2 + fontLineHeight(), getLocalizedString("gui.back")));
/*    */   }
/*    */ 
/*    */   
/*    */   public void keyPressed(char p_keyPressed_1_, int p_keyPressed_2_) {
/* 33 */     if (p_keyPressed_2_ == 1)
/*    */     {
/* 35 */       Realms.setScreen(this.parent);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void buttonClicked(RealmsButton p_buttonClicked_1_) {
/* 41 */     if (p_buttonClicked_1_.id() == 0)
/*    */     {
/* 43 */       Realms.setScreen(this.parent);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
/* 49 */     renderBackground();
/* 50 */     drawCenteredString(this.title, width() / 2, height() / 2 - this.textHeight / 2 - fontLineHeight() * 2, 11184810);
/* 51 */     int i = height() / 2 - this.textHeight / 2;
/*    */     
/* 53 */     if (this.lines != null)
/*    */     {
/* 55 */       for (String s : this.lines) {
/*    */         
/* 57 */         drawCenteredString(s, width() / 2, i, 16777215);
/* 58 */         i += fontLineHeight();
/*    */       } 
/*    */     }
/*    */     
/* 62 */     super.render(p_render_1_, p_render_2_, p_render_3_);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\realms\DisconnectedRealmsScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */