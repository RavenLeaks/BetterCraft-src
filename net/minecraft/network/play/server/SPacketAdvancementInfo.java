/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.AdvancementProgress;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPacketAdvancementInfo
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private boolean field_192605_a;
/*     */   private Map<ResourceLocation, Advancement.Builder> field_192606_b;
/*     */   private Set<ResourceLocation> field_192607_c;
/*     */   private Map<ResourceLocation, AdvancementProgress> field_192608_d;
/*     */   
/*     */   public SPacketAdvancementInfo() {}
/*     */   
/*     */   public SPacketAdvancementInfo(boolean p_i47519_1_, Collection<Advancement> p_i47519_2_, Set<ResourceLocation> p_i47519_3_, Map<ResourceLocation, AdvancementProgress> p_i47519_4_) {
/*  30 */     this.field_192605_a = p_i47519_1_;
/*  31 */     this.field_192606_b = Maps.newHashMap();
/*     */     
/*  33 */     for (Advancement advancement : p_i47519_2_)
/*     */     {
/*  35 */       this.field_192606_b.put(advancement.func_192067_g(), advancement.func_192075_a());
/*     */     }
/*     */     
/*  38 */     this.field_192607_c = p_i47519_3_;
/*  39 */     this.field_192608_d = Maps.newHashMap(p_i47519_4_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  47 */     handler.func_191981_a(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  55 */     this.field_192605_a = buf.readBoolean();
/*  56 */     this.field_192606_b = Maps.newHashMap();
/*  57 */     this.field_192607_c = Sets.newLinkedHashSet();
/*  58 */     this.field_192608_d = Maps.newHashMap();
/*  59 */     int i = buf.readVarIntFromBuffer();
/*     */     
/*  61 */     for (int j = 0; j < i; j++) {
/*     */       
/*  63 */       ResourceLocation resourcelocation = buf.func_192575_l();
/*  64 */       Advancement.Builder advancement$builder = Advancement.Builder.func_192060_b(buf);
/*  65 */       this.field_192606_b.put(resourcelocation, advancement$builder);
/*     */     } 
/*     */     
/*  68 */     i = buf.readVarIntFromBuffer();
/*     */     
/*  70 */     for (int k = 0; k < i; k++) {
/*     */       
/*  72 */       ResourceLocation resourcelocation1 = buf.func_192575_l();
/*  73 */       this.field_192607_c.add(resourcelocation1);
/*     */     } 
/*     */     
/*  76 */     i = buf.readVarIntFromBuffer();
/*     */     
/*  78 */     for (int l = 0; l < i; l++) {
/*     */       
/*  80 */       ResourceLocation resourcelocation2 = buf.func_192575_l();
/*  81 */       this.field_192608_d.put(resourcelocation2, AdvancementProgress.func_192100_b(buf));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  90 */     buf.writeBoolean(this.field_192605_a);
/*  91 */     buf.writeVarIntToBuffer(this.field_192606_b.size());
/*     */     
/*  93 */     for (Map.Entry<ResourceLocation, Advancement.Builder> entry : this.field_192606_b.entrySet()) {
/*     */       
/*  95 */       ResourceLocation resourcelocation = entry.getKey();
/*  96 */       Advancement.Builder advancement$builder = entry.getValue();
/*  97 */       buf.func_192572_a(resourcelocation);
/*  98 */       advancement$builder.func_192057_a(buf);
/*     */     } 
/*     */     
/* 101 */     buf.writeVarIntToBuffer(this.field_192607_c.size());
/*     */     
/* 103 */     for (ResourceLocation resourcelocation1 : this.field_192607_c)
/*     */     {
/* 105 */       buf.func_192572_a(resourcelocation1);
/*     */     }
/*     */     
/* 108 */     buf.writeVarIntToBuffer(this.field_192608_d.size());
/*     */     
/* 110 */     for (Map.Entry<ResourceLocation, AdvancementProgress> entry1 : this.field_192608_d.entrySet()) {
/*     */       
/* 112 */       buf.func_192572_a(entry1.getKey());
/* 113 */       ((AdvancementProgress)entry1.getValue()).func_192104_a(buf);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ResourceLocation, Advancement.Builder> func_192603_a() {
/* 119 */     return this.field_192606_b;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<ResourceLocation> func_192600_b() {
/* 124 */     return this.field_192607_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ResourceLocation, AdvancementProgress> func_192604_c() {
/* 129 */     return this.field_192608_d;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192602_d() {
/* 134 */     return this.field_192605_a;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketAdvancementInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */