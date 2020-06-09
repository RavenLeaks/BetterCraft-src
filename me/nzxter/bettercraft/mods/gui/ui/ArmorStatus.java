/*    */ package me.nzxter.bettercraft.mods.gui.ui;
/*    */ 
/*    */ import me.nzxter.bettercraft.utils.ColorUtils;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArmorStatus
/*    */   extends Gui
/*    */ {
/* 21 */   private static Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   public static void render() {
/* 24 */     for (int i = 0; i < (Minecraft.getMinecraft()).player.inventory.armorInventory.size(); i++) {
/* 25 */       ItemStack itemStack = (ItemStack)(Minecraft.getMinecraft()).player.inventory.armorInventory.get(i);
/* 26 */       renderItemStack(i, itemStack);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static void renderItemStack(int i, ItemStack is) {
/* 32 */     if (is == null) {
/*    */       return;
/*    */     }
/*    */     
/* 36 */     GL11.glPushMatrix();
/* 37 */     int yAdd = -16 * i + 48;
/*    */     
/* 39 */     ScaledResolution sr = new ScaledResolution(mc);
/*    */     
/* 41 */     if (is.getItem().isDamageable()) {
/* 42 */       double damage = (is.getMaxDamage() - is.getItemDamage()) / is.getMaxDamage() * 100.0D;
/* 43 */       drawString(mc.fontRendererObj, String.format("%.2f%%", new Object[] { Double.valueOf(damage) }), ScaledResolution.getScaledWidth() / 2 + 70 - 55 * i, ScaledResolution.getScaledHeight() - 86, ColorUtils.rainbowEffect(0L, 1.0F).getRGB());
/*    */     } 
/*    */ 
/*    */     
/* 47 */     mc.getRenderItem().renderItemAndEffectIntoGUI(is, ScaledResolution.getScaledWidth() / 2 + 55 - 55 * i, ScaledResolution.getScaledHeight() - 90);
/* 48 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\gu\\ui\ArmorStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */