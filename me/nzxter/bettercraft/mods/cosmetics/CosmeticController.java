/*    */ package me.nzxter.bettercraft.mods.cosmetics;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.nzxter.bettercraft.utils.ColorUtils;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ 
/*    */ 
/*    */ public class CosmeticController
/*    */ {
/*    */   public static boolean shouldRenderTopHat(AbstractClientPlayer player) {
/* 11 */     return true;
/*    */   }
/*    */   
/*    */   public static float[] getTopHatColor(AbstractClientPlayer player) {
/* 15 */     Color rainbow = ColorUtils.rainbowEffect(1L, 1.0F);
/* 16 */     return new float[] { rainbow.getRed(), rainbow.getGreen(), rainbow.getBlue() };
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\cosmetics\CosmeticController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */