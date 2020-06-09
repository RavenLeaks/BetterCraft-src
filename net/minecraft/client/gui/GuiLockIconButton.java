/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ public class GuiLockIconButton
/*    */   extends GuiButton
/*    */ {
/*    */   private boolean locked;
/*    */   
/*    */   public GuiLockIconButton(int p_i45538_1_, int p_i45538_2_, int p_i45538_3_) {
/* 12 */     super(p_i45538_1_, p_i45538_2_, p_i45538_3_, 20, 20, "");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLocked() {
/* 17 */     return this.locked;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLocked(boolean lockedIn) {
/* 22 */     this.locked = lockedIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
/* 27 */     if (this.visible) {
/*    */       Icon guilockiconbutton$icon;
/* 29 */       p_191745_1_.getTextureManager().bindTexture(GuiButton.BUTTON_TEXTURES);
/* 30 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 31 */       boolean flag = (p_191745_2_ >= this.xPosition && p_191745_3_ >= this.yPosition && p_191745_2_ < this.xPosition + this.width && p_191745_3_ < this.yPosition + this.height);
/*    */ 
/*    */       
/* 34 */       if (this.locked) {
/*    */         
/* 36 */         if (!this.enabled)
/*    */         {
/* 38 */           guilockiconbutton$icon = Icon.LOCKED_DISABLED;
/*    */         }
/* 40 */         else if (flag)
/*    */         {
/* 42 */           guilockiconbutton$icon = Icon.LOCKED_HOVER;
/*    */         }
/*    */         else
/*    */         {
/* 46 */           guilockiconbutton$icon = Icon.LOCKED;
/*    */         }
/*    */       
/* 49 */       } else if (!this.enabled) {
/*    */         
/* 51 */         guilockiconbutton$icon = Icon.UNLOCKED_DISABLED;
/*    */       }
/* 53 */       else if (flag) {
/*    */         
/* 55 */         guilockiconbutton$icon = Icon.UNLOCKED_HOVER;
/*    */       }
/*    */       else {
/*    */         
/* 59 */         guilockiconbutton$icon = Icon.UNLOCKED;
/*    */       } 
/*    */       
/* 62 */       drawTexturedModalRect(this.xPosition, this.yPosition, guilockiconbutton$icon.getX(), guilockiconbutton$icon.getY(), this.width, this.height);
/*    */     } 
/*    */   }
/*    */   
/*    */   enum Icon
/*    */   {
/* 68 */     LOCKED(0, 146),
/* 69 */     LOCKED_HOVER(0, 166),
/* 70 */     LOCKED_DISABLED(0, 186),
/* 71 */     UNLOCKED(20, 146),
/* 72 */     UNLOCKED_HOVER(20, 166),
/* 73 */     UNLOCKED_DISABLED(20, 186);
/*    */     
/*    */     private final int x;
/*    */     
/*    */     private final int y;
/*    */     
/*    */     Icon(int p_i45537_3_, int p_i45537_4_) {
/* 80 */       this.x = p_i45537_3_;
/* 81 */       this.y = p_i45537_4_;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getX() {
/* 86 */       return this.x;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getY() {
/* 91 */       return this.y;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiLockIconButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */