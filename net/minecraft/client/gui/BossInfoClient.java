/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.network.play.server.SPacketUpdateBossInfo;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.BossInfo;
/*    */ 
/*    */ public class BossInfoClient
/*    */   extends BossInfo
/*    */ {
/*    */   protected float rawPercent;
/*    */   protected long percentSetTime;
/*    */   
/*    */   public BossInfoClient(SPacketUpdateBossInfo packetIn) {
/* 15 */     super(packetIn.getUniqueId(), packetIn.getName(), packetIn.getColor(), packetIn.getOverlay());
/* 16 */     this.rawPercent = packetIn.getPercent();
/* 17 */     this.percent = packetIn.getPercent();
/* 18 */     this.percentSetTime = Minecraft.getSystemTime();
/* 19 */     setDarkenSky(packetIn.shouldDarkenSky());
/* 20 */     setPlayEndBossMusic(packetIn.shouldPlayEndBossMusic());
/* 21 */     setCreateFog(packetIn.shouldCreateFog());
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPercent(float percentIn) {
/* 26 */     this.percent = getPercent();
/* 27 */     this.rawPercent = percentIn;
/* 28 */     this.percentSetTime = Minecraft.getSystemTime();
/*    */   }
/*    */ 
/*    */   
/*    */   public float getPercent() {
/* 33 */     long i = Minecraft.getSystemTime() - this.percentSetTime;
/* 34 */     float f = MathHelper.clamp((float)i / 100.0F, 0.0F, 1.0F);
/* 35 */     return this.percent + (this.rawPercent - this.percent) * f;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateFromPacket(SPacketUpdateBossInfo packetIn) {
/* 40 */     switch (packetIn.getOperation()) {
/*    */       
/*    */       case UPDATE_NAME:
/* 43 */         setName(packetIn.getName());
/*    */         break;
/*    */       
/*    */       case UPDATE_PCT:
/* 47 */         setPercent(packetIn.getPercent());
/*    */         break;
/*    */       
/*    */       case UPDATE_STYLE:
/* 51 */         setColor(packetIn.getColor());
/* 52 */         setOverlay(packetIn.getOverlay());
/*    */         break;
/*    */       
/*    */       case UPDATE_PROPERTIES:
/* 56 */         setDarkenSky(packetIn.shouldDarkenSky());
/* 57 */         setPlayEndBossMusic(packetIn.shouldPlayEndBossMusic());
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\BossInfoClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */