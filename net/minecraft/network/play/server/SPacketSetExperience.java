/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketSetExperience
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private float experienceBar;
/*    */   private int totalExperience;
/*    */   private int level;
/*    */   
/*    */   public SPacketSetExperience() {}
/*    */   
/*    */   public SPacketSetExperience(float experienceBarIn, int totalExperienceIn, int levelIn) {
/* 20 */     this.experienceBar = experienceBarIn;
/* 21 */     this.totalExperience = totalExperienceIn;
/* 22 */     this.level = levelIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 30 */     this.experienceBar = buf.readFloat();
/* 31 */     this.level = buf.readVarIntFromBuffer();
/* 32 */     this.totalExperience = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 40 */     buf.writeFloat(this.experienceBar);
/* 41 */     buf.writeVarIntToBuffer(this.level);
/* 42 */     buf.writeVarIntToBuffer(this.totalExperience);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 50 */     handler.handleSetExperience(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getExperienceBar() {
/* 55 */     return this.experienceBar;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTotalExperience() {
/* 60 */     return this.totalExperience;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLevel() {
/* 65 */     return this.level;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketSetExperience.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */