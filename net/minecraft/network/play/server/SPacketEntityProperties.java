/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ 
/*     */ public class SPacketEntityProperties implements Packet<INetHandlerPlayClient> {
/*     */   private int entityId;
/*  17 */   private final List<Snapshot> snapshots = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SPacketEntityProperties(int entityIdIn, Collection<IAttributeInstance> instances) {
/*  25 */     this.entityId = entityIdIn;
/*     */     
/*  27 */     for (IAttributeInstance iattributeinstance : instances)
/*     */     {
/*  29 */       this.snapshots.add(new Snapshot(iattributeinstance.getAttribute().getAttributeUnlocalizedName(), iattributeinstance.getBaseValue(), iattributeinstance.getModifiers()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  38 */     this.entityId = buf.readVarIntFromBuffer();
/*  39 */     int i = buf.readInt();
/*     */     
/*  41 */     for (int j = 0; j < i; j++) {
/*     */       
/*  43 */       String s = buf.readStringFromBuffer(64);
/*  44 */       double d0 = buf.readDouble();
/*  45 */       List<AttributeModifier> list = Lists.newArrayList();
/*  46 */       int k = buf.readVarIntFromBuffer();
/*     */       
/*  48 */       for (int l = 0; l < k; l++) {
/*     */         
/*  50 */         UUID uuid = buf.readUuid();
/*  51 */         list.add(new AttributeModifier(uuid, "Unknown synced attribute modifier", buf.readDouble(), buf.readByte()));
/*     */       } 
/*     */       
/*  54 */       this.snapshots.add(new Snapshot(s, d0, list));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  63 */     buf.writeVarIntToBuffer(this.entityId);
/*  64 */     buf.writeInt(this.snapshots.size());
/*     */     
/*  66 */     for (Snapshot spacketentityproperties$snapshot : this.snapshots) {
/*     */       
/*  68 */       buf.writeString(spacketentityproperties$snapshot.getName());
/*  69 */       buf.writeDouble(spacketentityproperties$snapshot.getBaseValue());
/*  70 */       buf.writeVarIntToBuffer(spacketentityproperties$snapshot.getModifiers().size());
/*     */       
/*  72 */       for (AttributeModifier attributemodifier : spacketentityproperties$snapshot.getModifiers()) {
/*     */         
/*  74 */         buf.writeUuid(attributemodifier.getID());
/*  75 */         buf.writeDouble(attributemodifier.getAmount());
/*  76 */         buf.writeByte(attributemodifier.getOperation());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  86 */     handler.handleEntityProperties(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityId() {
/*  91 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Snapshot> getSnapshots() {
/*  96 */     return this.snapshots;
/*     */   }
/*     */   
/*     */   public SPacketEntityProperties() {}
/*     */   
/*     */   public class Snapshot {
/*     */     private final String name;
/*     */     private final double baseValue;
/*     */     private final Collection<AttributeModifier> modifiers;
/*     */     
/*     */     public Snapshot(String nameIn, double baseValueIn, Collection<AttributeModifier> modifiersIn) {
/* 107 */       this.name = nameIn;
/* 108 */       this.baseValue = baseValueIn;
/* 109 */       this.modifiers = modifiersIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 114 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getBaseValue() {
/* 119 */       return this.baseValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<AttributeModifier> getModifiers() {
/* 124 */       return this.modifiers;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketEntityProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */