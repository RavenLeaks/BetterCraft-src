/*     */ package net.minecraft.network.play.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayServer;
/*     */ 
/*     */ 
/*     */ public class CPacketRecipeInfo
/*     */   implements Packet<INetHandlerPlayServer>
/*     */ {
/*     */   private Purpose field_194157_a;
/*     */   private IRecipe field_193649_d;
/*     */   private boolean field_192631_e;
/*     */   private boolean field_192632_f;
/*     */   
/*     */   public CPacketRecipeInfo() {}
/*     */   
/*     */   public CPacketRecipeInfo(IRecipe p_i47518_1_) {
/*  23 */     this.field_194157_a = Purpose.SHOWN;
/*  24 */     this.field_193649_d = p_i47518_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public CPacketRecipeInfo(boolean p_i47424_1_, boolean p_i47424_2_) {
/*  29 */     this.field_194157_a = Purpose.SETTINGS;
/*  30 */     this.field_192631_e = p_i47424_1_;
/*  31 */     this.field_192632_f = p_i47424_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  39 */     this.field_194157_a = (Purpose)buf.readEnumValue(Purpose.class);
/*     */     
/*  41 */     if (this.field_194157_a == Purpose.SHOWN) {
/*     */       
/*  43 */       this.field_193649_d = CraftingManager.func_193374_a(buf.readInt());
/*     */     }
/*  45 */     else if (this.field_194157_a == Purpose.SETTINGS) {
/*     */       
/*  47 */       this.field_192631_e = buf.readBoolean();
/*  48 */       this.field_192632_f = buf.readBoolean();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  57 */     buf.writeEnumValue(this.field_194157_a);
/*     */     
/*  59 */     if (this.field_194157_a == Purpose.SHOWN) {
/*     */       
/*  61 */       buf.writeInt(CraftingManager.func_193375_a(this.field_193649_d));
/*     */     }
/*  63 */     else if (this.field_194157_a == Purpose.SETTINGS) {
/*     */       
/*  65 */       buf.writeBoolean(this.field_192631_e);
/*  66 */       buf.writeBoolean(this.field_192632_f);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayServer handler) {
/*  75 */     handler.func_191984_a(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Purpose func_194156_a() {
/*  80 */     return this.field_194157_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public IRecipe func_193648_b() {
/*  85 */     return this.field_193649_d;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192624_c() {
/*  90 */     return this.field_192631_e;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192625_d() {
/*  95 */     return this.field_192632_f;
/*     */   }
/*     */   
/*     */   public enum Purpose
/*     */   {
/* 100 */     SHOWN,
/* 101 */     SETTINGS;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketRecipeInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */