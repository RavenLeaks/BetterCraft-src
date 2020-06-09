/*     */ package net.minecraft.network.play.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class CPacketPlayerBlockPlacement
/*     */   implements Packet<INetHandlerPlayServer> {
/*  13 */   private static final BlockPos field_179726_a = new BlockPos(-1, -1, -1);
/*     */   
/*     */   private BlockPos position;
/*     */   
/*     */   private int placedBlockDirection;
/*     */   
/*     */   private ItemStack stack;
/*     */   private float facingX;
/*     */   private float facingY;
/*     */   private float facingZ;
/*     */   
/*     */   public CPacketPlayerBlockPlacement() {}
/*     */   
/*     */   public CPacketPlayerBlockPlacement(ItemStack stackIn) {
/*  27 */     this(field_179726_a, 255, stackIn, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public CPacketPlayerBlockPlacement(BlockPos positionIn, int placedBlockDirectionIn, ItemStack stackIn, float facingXIn, float facingYIn, float facingZIn) {
/*  32 */     this.position = positionIn;
/*  33 */     this.placedBlockDirection = placedBlockDirectionIn;
/*  34 */     this.stack = (stackIn != null) ? stackIn.copy() : null;
/*  35 */     this.facingX = facingXIn;
/*  36 */     this.facingY = facingYIn;
/*  37 */     this.facingZ = facingZIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  45 */     this.position = buf.readBlockPos();
/*  46 */     this.placedBlockDirection = buf.readUnsignedByte();
/*  47 */     this.stack = buf.readItemStackFromBuffer();
/*  48 */     this.facingX = buf.readUnsignedByte() / 16.0F;
/*  49 */     this.facingY = buf.readUnsignedByte() / 16.0F;
/*  50 */     this.facingZ = buf.readUnsignedByte() / 16.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  58 */     buf.writeBlockPos(this.position);
/*  59 */     buf.writeByte(this.placedBlockDirection);
/*  60 */     buf.writeItemStackToBuffer(this.stack);
/*  61 */     buf.writeByte((int)(this.facingX * 16.0F));
/*  62 */     buf.writeByte((int)(this.facingY * 16.0F));
/*  63 */     buf.writeByte((int)(this.facingZ * 16.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayServer handler) {
/*  71 */     handler.processPlayerBlockPlacement(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/*  76 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPlacedBlockDirection() {
/*  81 */     return this.placedBlockDirection;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getStack() {
/*  86 */     return this.stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPlacedBlockOffsetX() {
/*  94 */     return this.facingX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPlacedBlockOffsetY() {
/* 102 */     return this.facingY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPlacedBlockOffsetZ() {
/* 110 */     return this.facingZ;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketPlayerBlockPlacement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */