/*    */ package me.nzxter.bettercraft.mods.gui.ui;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CPS
/*    */   extends Gui
/*    */ {
/* 15 */   private static Minecraft mc = Minecraft.getMinecraft();
/*    */   
/* 17 */   private static List<Long> clicks = new ArrayList<>();
/*    */   private static boolean wasPressed;
/*    */   private static long lastPressed;
/*    */   
/*    */   public static int getCPS() {
/* 22 */     long time = System.currentTimeMillis();
/* 23 */     clicks.removeIf(aLong -> (aLong.longValue() + 1000L < paramLong));
/* 24 */     return clicks.size();
/*    */   }
/*    */   
/*    */   public static void render() {
/* 28 */     boolean pressed = Mouse.isButtonDown(0);
/*    */     
/* 30 */     if (pressed != wasPressed) {
/* 31 */       lastPressed = System.currentTimeMillis();
/* 32 */       wasPressed = pressed;
/* 33 */       if (pressed)
/* 34 */         clicks.add(Long.valueOf(lastPressed)); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\gu\\ui\CPS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */