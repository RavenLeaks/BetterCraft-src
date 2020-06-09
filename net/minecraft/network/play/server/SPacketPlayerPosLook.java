/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ 
/*     */ 
/*     */ public class SPacketPlayerPosLook
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private double x;
/*     */   private double y;
/*     */   private double z;
/*     */   private float yaw;
/*     */   private float pitch;
/*     */   private Set<EnumFlags> flags;
/*     */   private int teleportId;
/*     */   
/*     */   public SPacketPlayerPosLook() {}
/*     */   
/*     */   public SPacketPlayerPosLook(double xIn, double yIn, double zIn, float yawIn, float pitchIn, Set<EnumFlags> flagsIn, int teleportIdIn) {
/*  26 */     this.x = xIn;
/*  27 */     this.y = yIn;
/*  28 */     this.z = zIn;
/*  29 */     this.yaw = yawIn;
/*  30 */     this.pitch = pitchIn;
/*  31 */     this.flags = flagsIn;
/*  32 */     this.teleportId = teleportIdIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  40 */     this.x = buf.readDouble();
/*  41 */     this.y = buf.readDouble();
/*  42 */     this.z = buf.readDouble();
/*  43 */     this.yaw = buf.readFloat();
/*  44 */     this.pitch = buf.readFloat();
/*  45 */     this.flags = EnumFlags.unpack(buf.readUnsignedByte());
/*  46 */     this.teleportId = buf.readVarIntFromBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  54 */     buf.writeDouble(this.x);
/*  55 */     buf.writeDouble(this.y);
/*  56 */     buf.writeDouble(this.z);
/*  57 */     buf.writeFloat(this.yaw);
/*  58 */     buf.writeFloat(this.pitch);
/*  59 */     buf.writeByte(EnumFlags.pack(this.flags));
/*  60 */     buf.writeVarIntToBuffer(this.teleportId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  68 */     handler.handlePlayerPosLook(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/*  73 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/*  78 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/*  83 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getYaw() {
/*  88 */     return this.yaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPitch() {
/*  93 */     return this.pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTeleportId() {
/*  98 */     return this.teleportId;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<EnumFlags> getFlags() {
/* 103 */     return this.flags;
/*     */   }
/*     */   
/*     */   public enum EnumFlags
/*     */   {
/* 108 */     X(0),
/* 109 */     Y(1),
/* 110 */     Z(2),
/* 111 */     Y_ROT(3),
/* 112 */     X_ROT(4);
/*     */     
/*     */     private final int bit;
/*     */ 
/*     */     
/*     */     EnumFlags(int p_i46690_3_) {
/* 118 */       this.bit = p_i46690_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     private int getMask() {
/* 123 */       return 1 << this.bit;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean isSet(int p_187043_1_) {
/* 128 */       return ((p_187043_1_ & getMask()) == getMask());
/*     */     }
/*     */ 
/*     */     
/*     */     public static Set<EnumFlags> unpack(int flags) {
/* 133 */       Set<EnumFlags> set = EnumSet.noneOf(EnumFlags.class); byte b; int i;
/*     */       EnumFlags[] arrayOfEnumFlags;
/* 135 */       for (i = (arrayOfEnumFlags = values()).length, b = 0; b < i; ) { EnumFlags spacketplayerposlook$enumflags = arrayOfEnumFlags[b];
/*     */         
/* 137 */         if (spacketplayerposlook$enumflags.isSet(flags))
/*     */         {
/* 139 */           set.add(spacketplayerposlook$enumflags);
/*     */         }
/*     */         b++; }
/*     */       
/* 143 */       return set;
/*     */     }
/*     */ 
/*     */     
/*     */     public static int pack(Set<EnumFlags> flags) {
/* 148 */       int i = 0;
/*     */       
/* 150 */       for (EnumFlags spacketplayerposlook$enumflags : flags)
/*     */       {
/* 152 */         i |= spacketplayerposlook$enumflags.getMask();
/*     */       }
/*     */       
/* 155 */       return i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketPlayerPosLook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */