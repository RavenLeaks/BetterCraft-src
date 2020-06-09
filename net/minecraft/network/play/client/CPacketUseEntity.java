/*     */ package net.minecraft.network.play.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayServer;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class CPacketUseEntity
/*     */   implements Packet<INetHandlerPlayServer>
/*     */ {
/*     */   public int entityId;
/*     */   private Action action;
/*     */   private Vec3d hitVec;
/*     */   private EnumHand hand;
/*     */   
/*     */   public CPacketUseEntity() {}
/*     */   
/*     */   public CPacketUseEntity(Entity entityIn) {
/*  26 */     this.entityId = entityIn.getEntityId();
/*  27 */     this.action = Action.ATTACK;
/*     */   }
/*     */ 
/*     */   
/*     */   public CPacketUseEntity(Entity entityIn, EnumHand handIn) {
/*  32 */     this.entityId = entityIn.getEntityId();
/*  33 */     this.action = Action.INTERACT;
/*  34 */     this.hand = handIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public CPacketUseEntity(Entity entityIn, EnumHand handIn, Vec3d hitVecIn) {
/*  39 */     this.entityId = entityIn.getEntityId();
/*  40 */     this.action = Action.INTERACT_AT;
/*  41 */     this.hand = handIn;
/*  42 */     this.hitVec = hitVecIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  50 */     this.entityId = buf.readVarIntFromBuffer();
/*  51 */     this.action = (Action)buf.readEnumValue(Action.class);
/*     */     
/*  53 */     if (this.action == Action.INTERACT_AT)
/*     */     {
/*  55 */       this.hitVec = new Vec3d(buf.readFloat(), buf.readFloat(), buf.readFloat());
/*     */     }
/*     */     
/*  58 */     if (this.action == Action.INTERACT || this.action == Action.INTERACT_AT)
/*     */     {
/*  60 */       this.hand = (EnumHand)buf.readEnumValue(EnumHand.class);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  69 */     buf.writeVarIntToBuffer(this.entityId);
/*  70 */     buf.writeEnumValue(this.action);
/*     */     
/*  72 */     if (this.action == Action.INTERACT_AT) {
/*     */       
/*  74 */       buf.writeFloat((float)this.hitVec.xCoord);
/*  75 */       buf.writeFloat((float)this.hitVec.yCoord);
/*  76 */       buf.writeFloat((float)this.hitVec.zCoord);
/*     */     } 
/*     */     
/*  79 */     if (this.action == Action.INTERACT || this.action == Action.INTERACT_AT)
/*     */     {
/*  81 */       buf.writeEnumValue((Enum)this.hand);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayServer handler) {
/*  90 */     handler.processUseEntity(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getEntityFromWorld(World worldIn) {
/*  96 */     return worldIn.getEntityByID(this.entityId);
/*     */   }
/*     */ 
/*     */   
/*     */   public Action getAction() {
/* 101 */     return this.action;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumHand getHand() {
/* 106 */     return this.hand;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d getHitVec() {
/* 111 */     return this.hitVec;
/*     */   }
/*     */   
/*     */   public enum Action
/*     */   {
/* 116 */     INTERACT,
/* 117 */     ATTACK,
/* 118 */     INTERACT_AT;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketUseEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */