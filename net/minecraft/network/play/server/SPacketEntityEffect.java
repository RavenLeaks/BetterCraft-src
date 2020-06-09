/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ 
/*     */ 
/*     */ public class SPacketEntityEffect
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int entityId;
/*     */   private byte effectId;
/*     */   private byte amplifier;
/*     */   private int duration;
/*     */   private byte flags;
/*     */   
/*     */   public SPacketEntityEffect() {}
/*     */   
/*     */   public SPacketEntityEffect(int entityIdIn, PotionEffect effect) {
/*  24 */     this.entityId = entityIdIn;
/*  25 */     this.effectId = (byte)(Potion.getIdFromPotion(effect.getPotion()) & 0xFF);
/*  26 */     this.amplifier = (byte)(effect.getAmplifier() & 0xFF);
/*     */     
/*  28 */     if (effect.getDuration() > 32767) {
/*     */       
/*  30 */       this.duration = 32767;
/*     */     }
/*     */     else {
/*     */       
/*  34 */       this.duration = effect.getDuration();
/*     */     } 
/*     */     
/*  37 */     this.flags = 0;
/*     */     
/*  39 */     if (effect.getIsAmbient())
/*     */     {
/*  41 */       this.flags = (byte)(this.flags | 0x1);
/*     */     }
/*     */     
/*  44 */     if (effect.doesShowParticles())
/*     */     {
/*  46 */       this.flags = (byte)(this.flags | 0x2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  55 */     this.entityId = buf.readVarIntFromBuffer();
/*  56 */     this.effectId = buf.readByte();
/*  57 */     this.amplifier = buf.readByte();
/*  58 */     this.duration = buf.readVarIntFromBuffer();
/*  59 */     this.flags = buf.readByte();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  67 */     buf.writeVarIntToBuffer(this.entityId);
/*  68 */     buf.writeByte(this.effectId);
/*  69 */     buf.writeByte(this.amplifier);
/*  70 */     buf.writeVarIntToBuffer(this.duration);
/*  71 */     buf.writeByte(this.flags);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMaxDuration() {
/*  76 */     return (this.duration == 32767);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  84 */     handler.handleEntityEffect(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityId() {
/*  89 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getEffectId() {
/*  94 */     return this.effectId;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getAmplifier() {
/*  99 */     return this.amplifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDuration() {
/* 104 */     return this.duration;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesShowParticles() {
/* 109 */     return ((this.flags & 0x2) == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getIsAmbient() {
/* 114 */     return ((this.flags & 0x1) == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketEntityEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */