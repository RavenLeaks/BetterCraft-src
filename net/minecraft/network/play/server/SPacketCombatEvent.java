/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.CombatTracker;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ 
/*     */ 
/*     */ public class SPacketCombatEvent
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   public Event eventType;
/*     */   public int playerId;
/*     */   public int entityId;
/*     */   public int duration;
/*     */   public ITextComponent deathMessage;
/*     */   
/*     */   public SPacketCombatEvent() {}
/*     */   
/*     */   public SPacketCombatEvent(CombatTracker tracker, Event eventIn) {
/*  26 */     this(tracker, eventIn, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SPacketCombatEvent(CombatTracker tracker, Event eventIn, boolean p_i46932_3_) {
/*  32 */     this.eventType = eventIn;
/*  33 */     EntityLivingBase entitylivingbase = tracker.getBestAttacker();
/*     */     
/*  35 */     switch (eventIn) {
/*     */       
/*     */       case null:
/*  38 */         this.duration = tracker.getCombatDuration();
/*  39 */         this.entityId = (entitylivingbase == null) ? -1 : entitylivingbase.getEntityId();
/*     */         break;
/*     */       
/*     */       case ENTITY_DIED:
/*  43 */         this.playerId = tracker.getFighter().getEntityId();
/*  44 */         this.entityId = (entitylivingbase == null) ? -1 : entitylivingbase.getEntityId();
/*     */         
/*  46 */         if (p_i46932_3_) {
/*     */           
/*  48 */           this.deathMessage = tracker.getDeathMessage();
/*     */           
/*     */           break;
/*     */         } 
/*  52 */         this.deathMessage = (ITextComponent)new TextComponentString("");
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  62 */     this.eventType = (Event)buf.readEnumValue(Event.class);
/*     */     
/*  64 */     if (this.eventType == Event.END_COMBAT) {
/*     */       
/*  66 */       this.duration = buf.readVarIntFromBuffer();
/*  67 */       this.entityId = buf.readInt();
/*     */     }
/*  69 */     else if (this.eventType == Event.ENTITY_DIED) {
/*     */       
/*  71 */       this.playerId = buf.readVarIntFromBuffer();
/*  72 */       this.entityId = buf.readInt();
/*  73 */       this.deathMessage = buf.readTextComponent();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  82 */     buf.writeEnumValue(this.eventType);
/*     */     
/*  84 */     if (this.eventType == Event.END_COMBAT) {
/*     */       
/*  86 */       buf.writeVarIntToBuffer(this.duration);
/*  87 */       buf.writeInt(this.entityId);
/*     */     }
/*  89 */     else if (this.eventType == Event.ENTITY_DIED) {
/*     */       
/*  91 */       buf.writeVarIntToBuffer(this.playerId);
/*  92 */       buf.writeInt(this.entityId);
/*  93 */       buf.writeTextComponent(this.deathMessage);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 102 */     handler.handleCombatEvent(this);
/*     */   }
/*     */   
/*     */   public enum Event
/*     */   {
/* 107 */     ENTER_COMBAT,
/* 108 */     END_COMBAT,
/* 109 */     ENTITY_DIED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketCombatEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */