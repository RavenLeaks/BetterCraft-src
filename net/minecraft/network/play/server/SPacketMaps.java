/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import net.minecraft.world.storage.MapDecoration;
/*     */ 
/*     */ 
/*     */ public class SPacketMaps
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int mapId;
/*     */   private byte mapScale;
/*     */   private boolean trackingPosition;
/*     */   private MapDecoration[] icons;
/*     */   private int minX;
/*     */   private int minZ;
/*     */   private int columns;
/*     */   private int rows;
/*     */   private byte[] mapDataBytes;
/*     */   
/*     */   public SPacketMaps() {}
/*     */   
/*     */   public SPacketMaps(int mapIdIn, byte mapScaleIn, boolean trackingPositionIn, Collection<MapDecoration> iconsIn, byte[] p_i46937_5_, int minXIn, int minZIn, int columnsIn, int rowsIn) {
/*  29 */     this.mapId = mapIdIn;
/*  30 */     this.mapScale = mapScaleIn;
/*  31 */     this.trackingPosition = trackingPositionIn;
/*  32 */     this.icons = iconsIn.<MapDecoration>toArray(new MapDecoration[iconsIn.size()]);
/*  33 */     this.minX = minXIn;
/*  34 */     this.minZ = minZIn;
/*  35 */     this.columns = columnsIn;
/*  36 */     this.rows = rowsIn;
/*  37 */     this.mapDataBytes = new byte[columnsIn * rowsIn];
/*     */     
/*  39 */     for (int i = 0; i < columnsIn; i++) {
/*     */       
/*  41 */       for (int j = 0; j < rowsIn; j++)
/*     */       {
/*  43 */         this.mapDataBytes[i + j * columnsIn] = p_i46937_5_[minXIn + i + (minZIn + j) * 128];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  53 */     this.mapId = buf.readVarIntFromBuffer();
/*  54 */     this.mapScale = buf.readByte();
/*  55 */     this.trackingPosition = buf.readBoolean();
/*  56 */     this.icons = new MapDecoration[buf.readVarIntFromBuffer()];
/*     */     
/*  58 */     for (int i = 0; i < this.icons.length; i++) {
/*     */       
/*  60 */       short short1 = (short)buf.readByte();
/*  61 */       this.icons[i] = new MapDecoration(MapDecoration.Type.func_191159_a((byte)(short1 >> 4 & 0xF)), buf.readByte(), buf.readByte(), (byte)(short1 & 0xF));
/*     */     } 
/*     */     
/*  64 */     this.columns = buf.readUnsignedByte();
/*     */     
/*  66 */     if (this.columns > 0) {
/*     */       
/*  68 */       this.rows = buf.readUnsignedByte();
/*  69 */       this.minX = buf.readUnsignedByte();
/*  70 */       this.minZ = buf.readUnsignedByte();
/*  71 */       this.mapDataBytes = buf.readByteArray();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  80 */     buf.writeVarIntToBuffer(this.mapId);
/*  81 */     buf.writeByte(this.mapScale);
/*  82 */     buf.writeBoolean(this.trackingPosition);
/*  83 */     buf.writeVarIntToBuffer(this.icons.length); byte b; int i;
/*     */     MapDecoration[] arrayOfMapDecoration;
/*  85 */     for (i = (arrayOfMapDecoration = this.icons).length, b = 0; b < i; ) { MapDecoration mapdecoration = arrayOfMapDecoration[b];
/*     */       
/*  87 */       buf.writeByte((mapdecoration.getType() & 0xF) << 4 | mapdecoration.getRotation() & 0xF);
/*  88 */       buf.writeByte(mapdecoration.getX());
/*  89 */       buf.writeByte(mapdecoration.getY());
/*     */       b++; }
/*     */     
/*  92 */     buf.writeByte(this.columns);
/*     */     
/*  94 */     if (this.columns > 0) {
/*     */       
/*  96 */       buf.writeByte(this.rows);
/*  97 */       buf.writeByte(this.minX);
/*  98 */       buf.writeByte(this.minZ);
/*  99 */       buf.writeByteArray(this.mapDataBytes);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 108 */     handler.handleMaps(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMapId() {
/* 113 */     return this.mapId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMapdataTo(MapData mapdataIn) {
/* 121 */     mapdataIn.scale = this.mapScale;
/* 122 */     mapdataIn.trackingPosition = this.trackingPosition;
/* 123 */     mapdataIn.mapDecorations.clear();
/*     */     
/* 125 */     for (int i = 0; i < this.icons.length; i++) {
/*     */       
/* 127 */       MapDecoration mapdecoration = this.icons[i];
/* 128 */       mapdataIn.mapDecorations.put("icon-" + i, mapdecoration);
/*     */     } 
/*     */     
/* 131 */     for (int j = 0; j < this.columns; j++) {
/*     */       
/* 133 */       for (int k = 0; k < this.rows; k++)
/*     */       {
/* 135 */         mapdataIn.colors[this.minX + j + (this.minZ + k) * 128] = this.mapDataBytes[j + k * this.columns];
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */