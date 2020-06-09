/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ 
/*     */ 
/*     */ public class SPacketRecipeBook
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private State field_193646_a;
/*     */   private List<IRecipe> field_192596_a;
/*     */   private List<IRecipe> field_193647_c;
/*     */   private boolean field_192598_c;
/*     */   private boolean field_192599_d;
/*     */   
/*     */   public SPacketRecipeBook() {}
/*     */   
/*     */   public SPacketRecipeBook(State p_i47597_1_, List<IRecipe> p_i47597_2_, List<IRecipe> p_i47597_3_, boolean p_i47597_4_, boolean p_i47597_5_) {
/*  26 */     this.field_193646_a = p_i47597_1_;
/*  27 */     this.field_192596_a = p_i47597_2_;
/*  28 */     this.field_193647_c = p_i47597_3_;
/*  29 */     this.field_192598_c = p_i47597_4_;
/*  30 */     this.field_192599_d = p_i47597_5_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  38 */     handler.func_191980_a(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  46 */     this.field_193646_a = (State)buf.readEnumValue(State.class);
/*  47 */     this.field_192598_c = buf.readBoolean();
/*  48 */     this.field_192599_d = buf.readBoolean();
/*  49 */     int i = buf.readVarIntFromBuffer();
/*  50 */     this.field_192596_a = Lists.newArrayList();
/*     */     
/*  52 */     for (int j = 0; j < i; j++)
/*     */     {
/*  54 */       this.field_192596_a.add(CraftingManager.func_193374_a(buf.readVarIntFromBuffer()));
/*     */     }
/*     */     
/*  57 */     if (this.field_193646_a == State.INIT) {
/*     */       
/*  59 */       i = buf.readVarIntFromBuffer();
/*  60 */       this.field_193647_c = Lists.newArrayList();
/*     */       
/*  62 */       for (int k = 0; k < i; k++)
/*     */       {
/*  64 */         this.field_193647_c.add(CraftingManager.func_193374_a(buf.readVarIntFromBuffer()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  74 */     buf.writeEnumValue(this.field_193646_a);
/*  75 */     buf.writeBoolean(this.field_192598_c);
/*  76 */     buf.writeBoolean(this.field_192599_d);
/*  77 */     buf.writeVarIntToBuffer(this.field_192596_a.size());
/*     */     
/*  79 */     for (IRecipe irecipe : this.field_192596_a)
/*     */     {
/*  81 */       buf.writeVarIntToBuffer(CraftingManager.func_193375_a(irecipe));
/*     */     }
/*     */     
/*  84 */     if (this.field_193646_a == State.INIT) {
/*     */       
/*  86 */       buf.writeVarIntToBuffer(this.field_193647_c.size());
/*     */       
/*  88 */       for (IRecipe irecipe1 : this.field_193647_c)
/*     */       {
/*  90 */         buf.writeVarIntToBuffer(CraftingManager.func_193375_a(irecipe1));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IRecipe> func_192595_a() {
/*  97 */     return this.field_192596_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IRecipe> func_193644_b() {
/* 102 */     return this.field_193647_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192593_c() {
/* 107 */     return this.field_192598_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192594_d() {
/* 112 */     return this.field_192599_d;
/*     */   }
/*     */ 
/*     */   
/*     */   public State func_194151_e() {
/* 117 */     return this.field_193646_a;
/*     */   }
/*     */   
/*     */   public enum State
/*     */   {
/* 122 */     INIT,
/* 123 */     ADD,
/* 124 */     REMOVE;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketRecipeBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */