/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.AdvancementList;
/*     */ import net.minecraft.advancements.AdvancementProgress;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.toasts.AdvancementToast;
/*     */ import net.minecraft.client.gui.toasts.IToast;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketSeenAdvancements;
/*     */ import net.minecraft.network.play.server.SPacketAdvancementInfo;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ClientAdvancementManager {
/*  21 */   private static final Logger field_192800_a = LogManager.getLogger();
/*     */   private final Minecraft field_192801_b;
/*  23 */   private final AdvancementList field_192802_c = new AdvancementList();
/*  24 */   private final Map<Advancement, AdvancementProgress> field_192803_d = Maps.newHashMap();
/*     */   
/*     */   @Nullable
/*     */   private IListener field_192804_e;
/*     */   @Nullable
/*     */   private Advancement field_194231_f;
/*     */   
/*     */   public ClientAdvancementManager(Minecraft p_i47380_1_) {
/*  32 */     this.field_192801_b = p_i47380_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192799_a(SPacketAdvancementInfo p_192799_1_) {
/*  37 */     if (p_192799_1_.func_192602_d()) {
/*     */       
/*  39 */       this.field_192802_c.func_192087_a();
/*  40 */       this.field_192803_d.clear();
/*     */     } 
/*     */     
/*  43 */     this.field_192802_c.func_192085_a(p_192799_1_.func_192600_b());
/*  44 */     this.field_192802_c.func_192083_a(p_192799_1_.func_192603_a());
/*     */     
/*  46 */     for (Map.Entry<ResourceLocation, AdvancementProgress> entry : (Iterable<Map.Entry<ResourceLocation, AdvancementProgress>>)p_192799_1_.func_192604_c().entrySet()) {
/*     */       
/*  48 */       Advancement advancement = this.field_192802_c.func_192084_a(entry.getKey());
/*     */       
/*  50 */       if (advancement != null) {
/*     */         
/*  52 */         AdvancementProgress advancementprogress = entry.getValue();
/*  53 */         advancementprogress.func_192099_a(advancement.func_192073_f(), advancement.func_192074_h());
/*  54 */         this.field_192803_d.put(advancement, advancementprogress);
/*     */         
/*  56 */         if (this.field_192804_e != null)
/*     */         {
/*  58 */           this.field_192804_e.func_191933_a(advancement, advancementprogress);
/*     */         }
/*     */         
/*  61 */         if (!p_192799_1_.func_192602_d() && advancementprogress.func_192105_a() && advancement.func_192068_c() != null && advancement.func_192068_c().func_193223_h())
/*     */         {
/*  63 */           this.field_192801_b.func_193033_an().func_192988_a((IToast)new AdvancementToast(advancement));
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/*  68 */       field_192800_a.warn("Server informed client about progress for unknown advancement " + entry.getKey());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AdvancementList func_194229_a() {
/*  75 */     return this.field_192802_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194230_a(@Nullable Advancement p_194230_1_, boolean p_194230_2_) {
/*  80 */     NetHandlerPlayClient nethandlerplayclient = this.field_192801_b.getConnection();
/*     */     
/*  82 */     if (nethandlerplayclient != null && p_194230_1_ != null && p_194230_2_)
/*     */     {
/*  84 */       nethandlerplayclient.sendPacket((Packet)CPacketSeenAdvancements.func_194163_a(p_194230_1_));
/*     */     }
/*     */     
/*  87 */     if (this.field_194231_f != p_194230_1_) {
/*     */       
/*  89 */       this.field_194231_f = p_194230_1_;
/*     */       
/*  91 */       if (this.field_192804_e != null)
/*     */       {
/*  93 */         this.field_192804_e.func_193982_e(p_194230_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192798_a(@Nullable IListener p_192798_1_) {
/* 100 */     this.field_192804_e = p_192798_1_;
/* 101 */     this.field_192802_c.func_192086_a(p_192798_1_);
/*     */     
/* 103 */     if (p_192798_1_ != null) {
/*     */       
/* 105 */       for (Map.Entry<Advancement, AdvancementProgress> entry : this.field_192803_d.entrySet())
/*     */       {
/* 107 */         p_192798_1_.func_191933_a(entry.getKey(), entry.getValue());
/*     */       }
/*     */       
/* 110 */       p_192798_1_.func_193982_e(this.field_194231_f);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface IListener extends AdvancementList.Listener {
/*     */     void func_191933_a(Advancement param1Advancement, AdvancementProgress param1AdvancementProgress);
/*     */     
/*     */     void func_193982_e(Advancement param1Advancement);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\multiplayer\ClientAdvancementManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */