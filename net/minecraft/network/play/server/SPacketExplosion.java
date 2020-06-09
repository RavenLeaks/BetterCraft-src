/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ public class SPacketExplosion
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private double posX;
/*     */   private double posY;
/*     */   private double posZ;
/*     */   private float strength;
/*     */   private List<BlockPos> affectedBlockPositions;
/*     */   private float motionX;
/*     */   private float motionY;
/*     */   private float motionZ;
/*     */   
/*     */   public SPacketExplosion() {}
/*     */   
/*     */   public SPacketExplosion(double xIn, double yIn, double zIn, float strengthIn, List<BlockPos> affectedBlockPositionsIn, Vec3d motion) {
/*  29 */     this.posX = xIn;
/*  30 */     this.posY = yIn;
/*  31 */     this.posZ = zIn;
/*  32 */     this.strength = strengthIn;
/*  33 */     this.affectedBlockPositions = Lists.newArrayList(affectedBlockPositionsIn);
/*     */     
/*  35 */     if (motion != null) {
/*     */       
/*  37 */       this.motionX = (float)motion.xCoord;
/*  38 */       this.motionY = (float)motion.yCoord;
/*  39 */       this.motionZ = (float)motion.zCoord;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  48 */     this.posX = buf.readFloat();
/*  49 */     this.posY = buf.readFloat();
/*  50 */     this.posZ = buf.readFloat();
/*  51 */     this.strength = buf.readFloat();
/*  52 */     int i = buf.readInt();
/*  53 */     this.affectedBlockPositions = Lists.newArrayListWithCapacity(i);
/*  54 */     int j = (int)this.posX;
/*  55 */     int k = (int)this.posY;
/*  56 */     int l = (int)this.posZ;
/*     */     
/*  58 */     for (int i1 = 0; i1 < i; i1++) {
/*     */       
/*  60 */       int j1 = buf.readByte() + j;
/*  61 */       int k1 = buf.readByte() + k;
/*  62 */       int l1 = buf.readByte() + l;
/*  63 */       this.affectedBlockPositions.add(new BlockPos(j1, k1, l1));
/*     */     } 
/*     */     
/*  66 */     this.motionX = buf.readFloat();
/*  67 */     this.motionY = buf.readFloat();
/*  68 */     this.motionZ = buf.readFloat();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  76 */     buf.writeFloat((float)this.posX);
/*  77 */     buf.writeFloat((float)this.posY);
/*  78 */     buf.writeFloat((float)this.posZ);
/*  79 */     buf.writeFloat(this.strength);
/*  80 */     buf.writeInt(this.affectedBlockPositions.size());
/*  81 */     int i = (int)this.posX;
/*  82 */     int j = (int)this.posY;
/*  83 */     int k = (int)this.posZ;
/*     */     
/*  85 */     for (BlockPos blockpos : this.affectedBlockPositions) {
/*     */       
/*  87 */       int l = blockpos.getX() - i;
/*  88 */       int i1 = blockpos.getY() - j;
/*  89 */       int j1 = blockpos.getZ() - k;
/*  90 */       buf.writeByte(l);
/*  91 */       buf.writeByte(i1);
/*  92 */       buf.writeByte(j1);
/*     */     } 
/*     */     
/*  95 */     buf.writeFloat(this.motionX);
/*  96 */     buf.writeFloat(this.motionY);
/*  97 */     buf.writeFloat(this.motionZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 105 */     handler.handleExplosion(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMotionX() {
/* 110 */     return this.motionX;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMotionY() {
/* 115 */     return this.motionY;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMotionZ() {
/* 120 */     return this.motionZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/* 125 */     return this.posX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/* 130 */     return this.posY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 135 */     return this.posZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getStrength() {
/* 140 */     return this.strength;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockPos> getAffectedBlockPositions() {
/* 145 */     return this.affectedBlockPositions;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketExplosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */