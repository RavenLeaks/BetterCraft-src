/*     */ package net.minecraft.world;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketUpdateBossInfo;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ 
/*     */ public class BossInfoServer extends BossInfo {
/*  15 */   private final Set<EntityPlayerMP> players = Sets.newHashSet();
/*     */   
/*     */   private final Set<EntityPlayerMP> readOnlyPlayers;
/*     */   private boolean visible;
/*     */   
/*     */   public BossInfoServer(ITextComponent nameIn, BossInfo.Color colorIn, BossInfo.Overlay overlayIn) {
/*  21 */     super(MathHelper.getRandomUUID(), nameIn, colorIn, overlayIn);
/*  22 */     this.readOnlyPlayers = Collections.unmodifiableSet(this.players);
/*  23 */     this.visible = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPercent(float percentIn) {
/*  28 */     if (percentIn != this.percent) {
/*     */       
/*  30 */       super.setPercent(percentIn);
/*  31 */       sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_PCT);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColor(BossInfo.Color colorIn) {
/*  37 */     if (colorIn != this.color) {
/*     */       
/*  39 */       super.setColor(colorIn);
/*  40 */       sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_STYLE);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOverlay(BossInfo.Overlay overlayIn) {
/*  46 */     if (overlayIn != this.overlay) {
/*     */       
/*  48 */       super.setOverlay(overlayIn);
/*  49 */       sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_STYLE);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BossInfo setDarkenSky(boolean darkenSkyIn) {
/*  55 */     if (darkenSkyIn != this.darkenSky) {
/*     */       
/*  57 */       super.setDarkenSky(darkenSkyIn);
/*  58 */       sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
/*     */     } 
/*     */     
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossInfo setPlayEndBossMusic(boolean playEndBossMusicIn) {
/*  66 */     if (playEndBossMusicIn != this.playEndBossMusic) {
/*     */       
/*  68 */       super.setPlayEndBossMusic(playEndBossMusicIn);
/*  69 */       sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
/*     */     } 
/*     */     
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossInfo setCreateFog(boolean createFogIn) {
/*  77 */     if (createFogIn != this.createFog) {
/*     */       
/*  79 */       super.setCreateFog(createFogIn);
/*  80 */       sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
/*     */     } 
/*     */     
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(ITextComponent nameIn) {
/*  88 */     if (!Objects.equal(nameIn, this.name)) {
/*     */       
/*  90 */       super.setName(nameIn);
/*  91 */       sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_NAME);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendUpdate(SPacketUpdateBossInfo.Operation operationIn) {
/*  97 */     if (this.visible) {
/*     */       
/*  99 */       SPacketUpdateBossInfo spacketupdatebossinfo = new SPacketUpdateBossInfo(operationIn, this);
/*     */       
/* 101 */       for (EntityPlayerMP entityplayermp : this.players)
/*     */       {
/* 103 */         entityplayermp.connection.sendPacket((Packet)spacketupdatebossinfo);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayer(EntityPlayerMP player) {
/* 113 */     if (this.players.add(player) && this.visible)
/*     */     {
/* 115 */       player.connection.sendPacket((Packet)new SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation.ADD, this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayer(EntityPlayerMP player) {
/* 124 */     if (this.players.remove(player) && this.visible)
/*     */     {
/* 126 */       player.connection.sendPacket((Packet)new SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation.REMOVE, this));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visibleIn) {
/* 132 */     if (visibleIn != this.visible) {
/*     */       
/* 134 */       this.visible = visibleIn;
/*     */       
/* 136 */       for (EntityPlayerMP entityplayermp : this.players)
/*     */       {
/* 138 */         entityplayermp.connection.sendPacket((Packet)new SPacketUpdateBossInfo(visibleIn ? SPacketUpdateBossInfo.Operation.ADD : SPacketUpdateBossInfo.Operation.REMOVE, this));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<EntityPlayerMP> getPlayers() {
/* 145 */     return this.readOnlyPlayers;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\BossInfoServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */