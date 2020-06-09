/*     */ package net.minecraft.network.play.client;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayServer;
/*     */ 
/*     */ 
/*     */ public class CPacketRecipePlacement
/*     */   implements Packet<INetHandlerPlayServer>
/*     */ {
/*     */   private int field_192616_a;
/*     */   private short field_192617_b;
/*     */   private List<ItemMove> field_192618_c;
/*     */   private List<ItemMove> field_192619_d;
/*     */   
/*     */   public CPacketRecipePlacement() {}
/*     */   
/*     */   public CPacketRecipePlacement(int p_i47425_1_, List<ItemMove> p_i47425_2_, List<ItemMove> p_i47425_3_, short p_i47425_4_) {
/*  24 */     this.field_192616_a = p_i47425_1_;
/*  25 */     this.field_192617_b = p_i47425_4_;
/*  26 */     this.field_192618_c = p_i47425_2_;
/*  27 */     this.field_192619_d = p_i47425_3_;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_192613_a() {
/*  32 */     return this.field_192616_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public short func_192614_b() {
/*  37 */     return this.field_192617_b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  45 */     this.field_192616_a = buf.readByte();
/*  46 */     this.field_192617_b = buf.readShort();
/*  47 */     this.field_192618_c = func_192611_c(buf);
/*  48 */     this.field_192619_d = func_192611_c(buf);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<ItemMove> func_192611_c(PacketBuffer p_192611_1_) throws IOException {
/*  53 */     int i = p_192611_1_.readShort();
/*  54 */     List<ItemMove> list = Lists.newArrayListWithCapacity(i);
/*     */     
/*  56 */     for (int j = 0; j < i; j++) {
/*     */       
/*  58 */       ItemStack itemstack = p_192611_1_.readItemStackFromBuffer();
/*  59 */       byte b0 = p_192611_1_.readByte();
/*  60 */       byte b1 = p_192611_1_.readByte();
/*  61 */       list.add(new ItemMove(itemstack, b0, b1));
/*     */     } 
/*     */     
/*  64 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  72 */     buf.writeByte(this.field_192616_a);
/*  73 */     buf.writeShort(this.field_192617_b);
/*  74 */     func_192612_a(buf, this.field_192618_c);
/*  75 */     func_192612_a(buf, this.field_192619_d);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_192612_a(PacketBuffer p_192612_1_, List<ItemMove> p_192612_2_) {
/*  80 */     p_192612_1_.writeShort(p_192612_2_.size());
/*     */     
/*  82 */     for (ItemMove cpacketrecipeplacement$itemmove : p_192612_2_) {
/*     */       
/*  84 */       p_192612_1_.writeItemStackToBuffer(cpacketrecipeplacement$itemmove.field_192673_a);
/*  85 */       p_192612_1_.writeByte(cpacketrecipeplacement$itemmove.field_192674_b);
/*  86 */       p_192612_1_.writeByte(cpacketrecipeplacement$itemmove.field_192675_c);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayServer handler) {
/*  95 */     handler.func_191985_a(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemMove> func_192615_c() {
/* 100 */     return this.field_192619_d;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemMove> func_192610_d() {
/* 105 */     return this.field_192618_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ItemMove
/*     */   {
/*     */     public ItemStack field_192673_a;
/*     */     public int field_192674_b;
/*     */     public int field_192675_c;
/*     */     
/*     */     public ItemMove(ItemStack p_i47401_1_, int p_i47401_2_, int p_i47401_3_) {
/* 116 */       this.field_192673_a = p_i47401_1_.copy();
/* 117 */       this.field_192674_b = p_i47401_2_;
/* 118 */       this.field_192675_c = p_i47401_3_;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketRecipePlacement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */