/*    */ package me.nzxter.bettercraft.mods.esp;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.nzxter.bettercraft.utils.ColorUtils;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ESP
/*    */ {
/*    */   public static void onRender() {
/* 22 */     for (Entity entity : (Minecraft.getMinecraft()).world.loadedEntityList) {
/*    */       
/* 24 */       if (entity instanceof net.minecraft.entity.player.EntityPlayer && entity != (Minecraft.getMinecraft()).player) {
/* 25 */         Color color = ColorUtils.rainbowEffect(0L, 1.0F);
/* 26 */         drawESP(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), entity);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private static double getDiff(double lastI, double i, float ticks, double ownI) {
/* 32 */     return lastI + (i - lastI) * ticks - ownI;
/*    */   }
/*    */   
/*    */   public static void drawESP(float red, float green, float blue, float alpha, Entity entity) {
/* 36 */     Minecraft.getMinecraft().getRenderManager(); double xPos = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (Minecraft.getMinecraft()).timer.field_194147_b - RenderManager.renderPosX;
/* 37 */     Minecraft.getMinecraft().getRenderManager(); double yPos = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (Minecraft.getMinecraft()).timer.field_194147_b - RenderManager.renderPosY;
/* 38 */     Minecraft.getMinecraft().getRenderManager(); double zPos = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (Minecraft.getMinecraft()).timer.field_194147_b - RenderManager.renderPosZ;
/* 39 */     red = (float)(red - entity.hurtResistantTime / 30.0D);
/* 40 */     green = (float)(green - entity.hurtResistantTime / 30.0D);
/* 41 */     blue = (float)(blue - entity.hurtResistantTime / 30.0D);
/*    */     
/* 43 */     drawOutlinedEntityESP(xPos, yPos, zPos, (entity.width / 2.0F), entity.height, red, green, blue, alpha);
/*    */   }
/*    */   
/*    */   public static void drawOutlinedEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
/* 47 */     GL11.glPushMatrix();
/* 48 */     GL11.glEnable(3042);
/* 49 */     GL11.glBlendFunc(770, 771);
/* 50 */     GL11.glDisable(3553);
/* 51 */     GL11.glEnable(2848);
/* 52 */     GL11.glDisable(2929);
/* 53 */     GL11.glDepthMask(false);
/* 54 */     GL11.glLineWidth(1.0F);
/* 55 */     GL11.glColor4f(red, green, blue, alpha);
/* 56 */     drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
/* 57 */     GL11.glDisable(2848);
/* 58 */     GL11.glEnable(3553);
/* 59 */     GL11.glEnable(2929);
/* 60 */     GL11.glDepthMask(true);
/* 61 */     GL11.glDisable(3042);
/* 62 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
/* 67 */     Tessellator tessellator = Tessellator.getInstance();
/* 68 */     BufferBuilder worldRenderer = tessellator.getBuffer();
/* 69 */     worldRenderer.begin(3, DefaultVertexFormats.POSITION);
/* 70 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 71 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 72 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 73 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 74 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 75 */     tessellator.draw();
/* 76 */     worldRenderer.begin(3, DefaultVertexFormats.POSITION);
/* 77 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 78 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 79 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 80 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 81 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 82 */     tessellator.draw();
/* 83 */     worldRenderer.begin(1, DefaultVertexFormats.POSITION);
/* 84 */     worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
/* 85 */     worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
/* 86 */     worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
/* 87 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
/* 88 */     worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
/* 89 */     worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
/* 90 */     worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
/* 91 */     worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
/* 92 */     tessellator.draw();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\esp\ESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */