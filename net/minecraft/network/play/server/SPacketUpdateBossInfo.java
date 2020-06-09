/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.world.BossInfo;
/*     */ 
/*     */ 
/*     */ public class SPacketUpdateBossInfo
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private UUID uniqueId;
/*     */   private Operation operation;
/*     */   private ITextComponent name;
/*     */   private float percent;
/*     */   private BossInfo.Color color;
/*     */   private BossInfo.Overlay overlay;
/*     */   private boolean darkenSky;
/*     */   private boolean playEndBossMusic;
/*     */   private boolean createFog;
/*     */   
/*     */   public SPacketUpdateBossInfo() {}
/*     */   
/*     */   public SPacketUpdateBossInfo(Operation operationIn, BossInfo data) {
/*  29 */     this.operation = operationIn;
/*  30 */     this.uniqueId = data.getUniqueId();
/*  31 */     this.name = data.getName();
/*  32 */     this.percent = data.getPercent();
/*  33 */     this.color = data.getColor();
/*  34 */     this.overlay = data.getOverlay();
/*  35 */     this.darkenSky = data.shouldDarkenSky();
/*  36 */     this.playEndBossMusic = data.shouldPlayEndBossMusic();
/*  37 */     this.createFog = data.shouldCreateFog();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  45 */     this.uniqueId = buf.readUuid();
/*  46 */     this.operation = (Operation)buf.readEnumValue(Operation.class);
/*     */     
/*  48 */     switch (this.operation) {
/*     */       
/*     */       case null:
/*  51 */         this.name = buf.readTextComponent();
/*  52 */         this.percent = buf.readFloat();
/*  53 */         this.color = (BossInfo.Color)buf.readEnumValue(BossInfo.Color.class);
/*  54 */         this.overlay = (BossInfo.Overlay)buf.readEnumValue(BossInfo.Overlay.class);
/*  55 */         setFlags(buf.readUnsignedByte());
/*     */ 
/*     */       
/*     */       default:
/*     */         return;
/*     */       
/*     */       case UPDATE_PCT:
/*  62 */         this.percent = buf.readFloat();
/*     */ 
/*     */       
/*     */       case UPDATE_NAME:
/*  66 */         this.name = buf.readTextComponent();
/*     */ 
/*     */       
/*     */       case UPDATE_STYLE:
/*  70 */         this.color = (BossInfo.Color)buf.readEnumValue(BossInfo.Color.class);
/*  71 */         this.overlay = (BossInfo.Overlay)buf.readEnumValue(BossInfo.Overlay.class);
/*     */       case UPDATE_PROPERTIES:
/*     */         break;
/*     */     } 
/*  75 */     setFlags(buf.readUnsignedByte());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFlags(int flags) {
/*  81 */     this.darkenSky = ((flags & 0x1) > 0);
/*  82 */     this.playEndBossMusic = ((flags & 0x2) > 0);
/*  83 */     this.createFog = ((flags & 0x2) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  91 */     buf.writeUuid(this.uniqueId);
/*  92 */     buf.writeEnumValue(this.operation);
/*     */     
/*  94 */     switch (this.operation) {
/*     */       
/*     */       case null:
/*  97 */         buf.writeTextComponent(this.name);
/*  98 */         buf.writeFloat(this.percent);
/*  99 */         buf.writeEnumValue((Enum)this.color);
/* 100 */         buf.writeEnumValue((Enum)this.overlay);
/* 101 */         buf.writeByte(getFlags());
/*     */ 
/*     */       
/*     */       default:
/*     */         return;
/*     */       
/*     */       case UPDATE_PCT:
/* 108 */         buf.writeFloat(this.percent);
/*     */ 
/*     */       
/*     */       case UPDATE_NAME:
/* 112 */         buf.writeTextComponent(this.name);
/*     */ 
/*     */       
/*     */       case UPDATE_STYLE:
/* 116 */         buf.writeEnumValue((Enum)this.color);
/* 117 */         buf.writeEnumValue((Enum)this.overlay);
/*     */       case UPDATE_PROPERTIES:
/*     */         break;
/*     */     } 
/* 121 */     buf.writeByte(getFlags());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getFlags() {
/* 127 */     int i = 0;
/*     */     
/* 129 */     if (this.darkenSky)
/*     */     {
/* 131 */       i |= 0x1;
/*     */     }
/*     */     
/* 134 */     if (this.playEndBossMusic)
/*     */     {
/* 136 */       i |= 0x2;
/*     */     }
/*     */     
/* 139 */     if (this.createFog)
/*     */     {
/* 141 */       i |= 0x2;
/*     */     }
/*     */     
/* 144 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 152 */     handler.handleUpdateEntityNBT(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getUniqueId() {
/* 157 */     return this.uniqueId;
/*     */   }
/*     */ 
/*     */   
/*     */   public Operation getOperation() {
/* 162 */     return this.operation;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent getName() {
/* 167 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPercent() {
/* 172 */     return this.percent;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossInfo.Color getColor() {
/* 177 */     return this.color;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossInfo.Overlay getOverlay() {
/* 182 */     return this.overlay;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDarkenSky() {
/* 187 */     return this.darkenSky;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldPlayEndBossMusic() {
/* 192 */     return this.playEndBossMusic;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCreateFog() {
/* 197 */     return this.createFog;
/*     */   }
/*     */   
/*     */   public enum Operation
/*     */   {
/* 202 */     ADD,
/* 203 */     REMOVE,
/* 204 */     UPDATE_PCT,
/* 205 */     UPDATE_NAME,
/* 206 */     UPDATE_STYLE,
/* 207 */     UPDATE_PROPERTIES;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketUpdateBossInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */