/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ public class SPacketCustomSound implements Packet<INetHandlerPlayClient> {
/*     */   private String soundName;
/*     */   private SoundCategory category;
/*     */   private int x;
/*  15 */   private int y = Integer.MAX_VALUE;
/*     */ 
/*     */   
/*     */   private int z;
/*     */   
/*     */   private float volume;
/*     */   
/*     */   private float pitch;
/*     */ 
/*     */   
/*     */   public SPacketCustomSound(String soundNameIn, SoundCategory categoryIn, double xIn, double yIn, double zIn, float volumeIn, float pitchIn) {
/*  26 */     Validate.notNull(soundNameIn, "name", new Object[0]);
/*  27 */     this.soundName = soundNameIn;
/*  28 */     this.category = categoryIn;
/*  29 */     this.x = (int)(xIn * 8.0D);
/*  30 */     this.y = (int)(yIn * 8.0D);
/*  31 */     this.z = (int)(zIn * 8.0D);
/*  32 */     this.volume = volumeIn;
/*  33 */     this.pitch = pitchIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  41 */     this.soundName = buf.readStringFromBuffer(256);
/*  42 */     this.category = (SoundCategory)buf.readEnumValue(SoundCategory.class);
/*  43 */     this.x = buf.readInt();
/*  44 */     this.y = buf.readInt();
/*  45 */     this.z = buf.readInt();
/*  46 */     this.volume = buf.readFloat();
/*  47 */     this.pitch = buf.readFloat();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  55 */     buf.writeString(this.soundName);
/*  56 */     buf.writeEnumValue((Enum)this.category);
/*  57 */     buf.writeInt(this.x);
/*  58 */     buf.writeInt(this.y);
/*  59 */     buf.writeInt(this.z);
/*  60 */     buf.writeFloat(this.volume);
/*  61 */     buf.writeFloat(this.pitch);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSoundName() {
/*  66 */     return this.soundName;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundCategory getCategory() {
/*  71 */     return this.category;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/*  76 */     return (this.x / 8.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/*  81 */     return (this.y / 8.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/*  86 */     return (this.z / 8.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getVolume() {
/*  91 */     return this.volume;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPitch() {
/*  96 */     return this.pitch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 104 */     handler.handleCustomSound(this);
/*     */   }
/*     */   
/*     */   public SPacketCustomSound() {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketCustomSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */