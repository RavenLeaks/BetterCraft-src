/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ public class SPacketSoundEffect
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   public SoundEvent sound;
/*     */   public SoundCategory category;
/*     */   public int posX;
/*     */   public int posY;
/*     */   public int posZ;
/*     */   public float soundVolume;
/*     */   public float soundPitch;
/*     */   
/*     */   public SPacketSoundEffect() {}
/*     */   
/*     */   public SPacketSoundEffect(SoundEvent soundIn, SoundCategory categoryIn, double xIn, double yIn, double zIn, float volumeIn, float pitchIn) {
/*  27 */     Validate.notNull(soundIn, "sound", new Object[0]);
/*  28 */     this.sound = soundIn;
/*  29 */     this.category = categoryIn;
/*  30 */     this.posX = (int)(xIn * 8.0D);
/*  31 */     this.posY = (int)(yIn * 8.0D);
/*  32 */     this.posZ = (int)(zIn * 8.0D);
/*  33 */     this.soundVolume = volumeIn;
/*  34 */     this.soundPitch = pitchIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  42 */     this.sound = (SoundEvent)SoundEvent.REGISTRY.getObjectById(buf.readVarIntFromBuffer());
/*  43 */     this.category = (SoundCategory)buf.readEnumValue(SoundCategory.class);
/*  44 */     this.posX = buf.readInt();
/*  45 */     this.posY = buf.readInt();
/*  46 */     this.posZ = buf.readInt();
/*  47 */     this.soundVolume = buf.readFloat();
/*  48 */     this.soundPitch = buf.readFloat();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  56 */     buf.writeVarIntToBuffer(SoundEvent.REGISTRY.getIDForObject(this.sound));
/*  57 */     buf.writeEnumValue((Enum)this.category);
/*  58 */     buf.writeInt(this.posX);
/*  59 */     buf.writeInt(this.posY);
/*  60 */     buf.writeInt(this.posZ);
/*  61 */     buf.writeFloat(this.soundVolume);
/*  62 */     buf.writeFloat(this.soundPitch);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getSound() {
/*  67 */     return this.sound;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundCategory getCategory() {
/*  72 */     return this.category;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/*  77 */     return (this.posX / 8.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/*  82 */     return (this.posY / 8.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/*  87 */     return (this.posZ / 8.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getVolume() {
/*  92 */     return this.soundVolume;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPitch() {
/*  97 */     return this.soundPitch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 105 */     handler.handleSoundEffect(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketSoundEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */