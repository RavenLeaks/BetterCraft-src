/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ import net.minecraft.world.GameType;
/*    */ import net.minecraft.world.WorldType;
/*    */ 
/*    */ 
/*    */ public class SPacketRespawn
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int dimensionID;
/*    */   private EnumDifficulty difficulty;
/*    */   private GameType gameType;
/*    */   private WorldType worldType;
/*    */   
/*    */   public SPacketRespawn() {}
/*    */   
/*    */   public SPacketRespawn(int dimensionIdIn, EnumDifficulty difficultyIn, WorldType worldTypeIn, GameType gameModeIn) {
/* 24 */     this.dimensionID = dimensionIdIn;
/* 25 */     this.difficulty = difficultyIn;
/* 26 */     this.gameType = gameModeIn;
/* 27 */     this.worldType = worldTypeIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 35 */     handler.handleRespawn(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 43 */     this.dimensionID = buf.readInt();
/* 44 */     this.difficulty = EnumDifficulty.getDifficultyEnum(buf.readUnsignedByte());
/* 45 */     this.gameType = GameType.getByID(buf.readUnsignedByte());
/* 46 */     this.worldType = WorldType.parseWorldType(buf.readStringFromBuffer(16));
/*    */     
/* 48 */     if (this.worldType == null)
/*    */     {
/* 50 */       this.worldType = WorldType.DEFAULT;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 59 */     buf.writeInt(this.dimensionID);
/* 60 */     buf.writeByte(this.difficulty.getDifficultyId());
/* 61 */     buf.writeByte(this.gameType.getID());
/* 62 */     buf.writeString(this.worldType.getWorldTypeName());
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDimensionID() {
/* 67 */     return this.dimensionID;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumDifficulty getDifficulty() {
/* 72 */     return this.difficulty;
/*    */   }
/*    */ 
/*    */   
/*    */   public GameType getGameType() {
/* 77 */     return this.gameType;
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldType getWorldType() {
/* 82 */     return this.worldType;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketRespawn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */