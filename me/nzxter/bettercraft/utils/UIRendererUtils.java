/*    */ package me.nzxter.bettercraft.utils;
/*    */ 
/*    */ import me.nzxter.bettercraft.mods.gui.ui.ArmorStatus;
/*    */ import me.nzxter.bettercraft.mods.gui.ui.CPS;
/*    */ import me.nzxter.bettercraft.mods.gui.ui.Keystrokes;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UIRendererUtils
/*    */   extends Gui
/*    */ {
/* 21 */   private static Minecraft mc = Minecraft.getMinecraft();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void draw() {
/* 27 */     drawString(mc.fontRendererObj, String.valueOf("Network Settings"), 1, 1, ColorUtils.rainbowEffect(0L, 1.0F).getRGB());
/* 28 */     drawString(mc.fontRendererObj, String.valueOf("» Remote Adresse: " + mc.getConnection().getNetworkManager().getRemoteAddress().toString()), 5, 10, 16777215);
/* 29 */     drawString(mc.fontRendererObj, String.valueOf("» Server Brand: " + mc.player.getServerBrand().replaceAll("git:", "").replaceAll("Bootstrap", "").replaceAll("SNAPSHOT", "").replaceAll(":", "").replaceAll("BungeeCord ", "").replaceAll("Waterfall ", "")).replaceAll("Firefall ", "").replaceAll("SkillCord ", "").replaceAll("MelonBungee ", "").replaceAll("unkown", "").replaceAll("<- Wer das liest ist dumm", ""), 5, 20, 16777215);
/* 30 */     drawString(mc.fontRendererObj, String.valueOf("» Ticks per second: " + (Math.round(TimeHelperUtils.lastTps * 10.0D) / 10.0D)), 5, 30, 16777215);
/* 31 */     drawString(mc.fontRendererObj, String.valueOf("» Lag Meter: " + TimeHelperUtils.getFormattedLag()).replaceAll("Lag Meter: 0", "Lag Meter: No Lag"), 5, 40, 16777215);
/*    */     
/* 33 */     drawString(mc.fontRendererObj, String.valueOf("World Settings"), 1, 60, ColorUtils.rainbowEffect(0L, 1.0F).getRGB());
/* 34 */     drawString(mc.fontRendererObj, String.valueOf("» Spawn: " + mc.world.getSpawnPoint().toString().replaceAll("BlockPos", "")), 5, 70, 16777215);
/* 35 */     drawString(mc.fontRendererObj, String.valueOf("» Biom: " + mc.world.getBiome(mc.player.getPosition()).getBiomeName()), 5, 80, 16777215);
/*    */     
/* 37 */     drawString(mc.fontRendererObj, String.valueOf("» Entitys: " + mc.world.loadedEntityList.size()), 5, 100, 16777215);
/*    */     
/* 39 */     drawString(mc.fontRendererObj, String.valueOf("Player Settings"), 1, 120, ColorUtils.rainbowEffect(0L, 1.0F).getRGB());
/* 40 */     drawString(mc.fontRendererObj, String.valueOf("» Ram: " + Long.valueOf(TimeHelperUtils.bytesToMb(Runtime.getRuntime().freeMemory())) + "MB/" + Long.valueOf(TimeHelperUtils.bytesToMb(Runtime.getRuntime().maxMemory())) + "MB"), 5, 130, 16777215);
/* 41 */     drawString(mc.fontRendererObj, String.valueOf("» Name: " + Minecraft.getSession().getUsername()), 5, 140, 16777215);
/*    */     
/*    */     try {
/* 44 */       drawString(mc.fontRendererObj, String.valueOf(String.valueOf("» Block: ")) + mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock().getLocalizedName().replaceAll("tile.air.name", "Air").replaceAll("tile.skull.skeleton.name", "Skull"), 5, 90, 16777215);
/* 45 */     } catch (NullPointerException e) {
/* 46 */       drawString(mc.fontRendererObj, String.valueOf("» Block: Entity"), 5, 90, 16777215);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 51 */     ArmorStatus.render();
/* 52 */     CPS.render();
/* 53 */     Keystrokes.render();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\UIRendererUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */