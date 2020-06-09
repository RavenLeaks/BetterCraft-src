/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketAdvancementInfo;
/*     */ import net.minecraft.network.play.server.SPacketSelectAdvancementsTab;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class PlayerAdvancements {
/*  35 */   private static final Logger field_192753_a = LogManager.getLogger();
/*  36 */   private static final Gson field_192754_b = (new GsonBuilder()).registerTypeAdapter(AdvancementProgress.class, new AdvancementProgress.Serializer()).registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer()).setPrettyPrinting().create();
/*  37 */   private static final TypeToken<Map<ResourceLocation, AdvancementProgress>> field_192755_c = new TypeToken<Map<ResourceLocation, AdvancementProgress>>() {
/*     */     
/*     */     };
/*     */   private final MinecraftServer field_192756_d;
/*     */   private final File field_192757_e;
/*  42 */   private final Map<Advancement, AdvancementProgress> field_192758_f = Maps.newLinkedHashMap();
/*  43 */   private final Set<Advancement> field_192759_g = Sets.newLinkedHashSet();
/*  44 */   private final Set<Advancement> field_192760_h = Sets.newLinkedHashSet();
/*  45 */   private final Set<Advancement> field_192761_i = Sets.newLinkedHashSet();
/*     */   
/*     */   private EntityPlayerMP field_192762_j;
/*     */   @Nullable
/*     */   private Advancement field_194221_k;
/*     */   private boolean field_192763_k = true;
/*     */   
/*     */   public PlayerAdvancements(MinecraftServer p_i47422_1_, File p_i47422_2_, EntityPlayerMP p_i47422_3_) {
/*  53 */     this.field_192756_d = p_i47422_1_;
/*  54 */     this.field_192757_e = p_i47422_2_;
/*  55 */     this.field_192762_j = p_i47422_3_;
/*  56 */     func_192740_f();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192739_a(EntityPlayerMP p_192739_1_) {
/*  61 */     this.field_192762_j = p_192739_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192745_a() {
/*  66 */     for (ICriterionTrigger<?> icriteriontrigger : CriteriaTriggers.func_192120_a())
/*     */     {
/*  68 */       icriteriontrigger.func_192167_a(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193766_b() {
/*  74 */     func_192745_a();
/*  75 */     this.field_192758_f.clear();
/*  76 */     this.field_192759_g.clear();
/*  77 */     this.field_192760_h.clear();
/*  78 */     this.field_192761_i.clear();
/*  79 */     this.field_192763_k = true;
/*  80 */     this.field_194221_k = null;
/*  81 */     func_192740_f();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_192751_c() {
/*  86 */     for (Advancement advancement : this.field_192756_d.func_191949_aK().func_192780_b())
/*     */     {
/*  88 */       func_193764_b(advancement);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_192752_d() {
/*  94 */     List<Advancement> list = Lists.newArrayList();
/*     */     
/*  96 */     for (Map.Entry<Advancement, AdvancementProgress> entry : this.field_192758_f.entrySet()) {
/*     */       
/*  98 */       if (((AdvancementProgress)entry.getValue()).func_192105_a()) {
/*     */         
/* 100 */         list.add(entry.getKey());
/* 101 */         this.field_192761_i.add(entry.getKey());
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     for (Advancement advancement : list)
/*     */     {
/* 107 */       func_192742_b(advancement);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_192748_e() {
/* 113 */     for (Advancement advancement : this.field_192756_d.func_191949_aK().func_192780_b()) {
/*     */       
/* 115 */       if (advancement.func_192073_f().isEmpty()) {
/*     */         
/* 117 */         func_192750_a(advancement, "");
/* 118 */         advancement.func_192072_d().func_192113_a(this.field_192762_j);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_192740_f() {
/* 125 */     if (this.field_192757_e.isFile()) {
/*     */       
/*     */       try {
/*     */         
/* 129 */         String s = Files.toString(this.field_192757_e, StandardCharsets.UTF_8);
/* 130 */         Map<ResourceLocation, AdvancementProgress> map = (Map<ResourceLocation, AdvancementProgress>)JsonUtils.func_193840_a(field_192754_b, s, field_192755_c.getType());
/*     */         
/* 132 */         if (map == null)
/*     */         {
/* 134 */           throw new JsonParseException("Found null for advancements");
/*     */         }
/*     */         
/* 137 */         Stream<Map.Entry<ResourceLocation, AdvancementProgress>> stream = map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue));
/*     */         
/* 139 */         for (Map.Entry<ResourceLocation, AdvancementProgress> entry : (Iterable<Map.Entry<ResourceLocation, AdvancementProgress>>)stream.collect((Collector)Collectors.toList()))
/*     */         {
/* 141 */           Advancement advancement = this.field_192756_d.func_191949_aK().func_192778_a(entry.getKey());
/*     */           
/* 143 */           if (advancement == null) {
/*     */             
/* 145 */             field_192753_a.warn("Ignored advancement '" + entry.getKey() + "' in progress file " + this.field_192757_e + " - it doesn't exist anymore?");
/*     */             
/*     */             continue;
/*     */           } 
/* 149 */           func_192743_a(advancement, entry.getValue());
/*     */         }
/*     */       
/*     */       }
/* 153 */       catch (JsonParseException jsonparseexception) {
/*     */         
/* 155 */         field_192753_a.error("Couldn't parse player advancements in " + this.field_192757_e, (Throwable)jsonparseexception);
/*     */       }
/* 157 */       catch (IOException ioexception) {
/*     */         
/* 159 */         field_192753_a.error("Couldn't access player advancements in " + this.field_192757_e, ioexception);
/*     */       } 
/*     */     }
/*     */     
/* 163 */     func_192748_e();
/* 164 */     func_192752_d();
/* 165 */     func_192751_c();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192749_b() {
/* 170 */     Map<ResourceLocation, AdvancementProgress> map = Maps.newHashMap();
/*     */     
/* 172 */     for (Map.Entry<Advancement, AdvancementProgress> entry : this.field_192758_f.entrySet()) {
/*     */       
/* 174 */       AdvancementProgress advancementprogress = entry.getValue();
/*     */       
/* 176 */       if (advancementprogress.func_192108_b())
/*     */       {
/* 178 */         map.put(((Advancement)entry.getKey()).func_192067_g(), advancementprogress);
/*     */       }
/*     */     } 
/*     */     
/* 182 */     if (this.field_192757_e.getParentFile() != null)
/*     */     {
/* 184 */       this.field_192757_e.getParentFile().mkdirs();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 189 */       Files.write(field_192754_b.toJson(map), this.field_192757_e, StandardCharsets.UTF_8);
/*     */     }
/* 191 */     catch (IOException ioexception) {
/*     */       
/* 193 */       field_192753_a.error("Couldn't save player advancements to " + this.field_192757_e, ioexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192750_a(Advancement p_192750_1_, String p_192750_2_) {
/* 199 */     boolean flag = false;
/* 200 */     AdvancementProgress advancementprogress = func_192747_a(p_192750_1_);
/* 201 */     boolean flag1 = advancementprogress.func_192105_a();
/*     */     
/* 203 */     if (advancementprogress.func_192109_a(p_192750_2_)) {
/*     */       
/* 205 */       func_193765_c(p_192750_1_);
/* 206 */       this.field_192761_i.add(p_192750_1_);
/* 207 */       flag = true;
/*     */       
/* 209 */       if (!flag1 && advancementprogress.func_192105_a()) {
/*     */         
/* 211 */         p_192750_1_.func_192072_d().func_192113_a(this.field_192762_j);
/*     */         
/* 213 */         if (p_192750_1_.func_192068_c() != null && p_192750_1_.func_192068_c().func_193220_i() && this.field_192762_j.world.getGameRules().getBoolean("announceAdvancements"))
/*     */         {
/* 215 */           this.field_192756_d.getPlayerList().sendChatMsg((ITextComponent)new TextComponentTranslation("chat.type.advancement." + p_192750_1_.func_192068_c().func_192291_d().func_192307_a(), new Object[] { this.field_192762_j.getDisplayName(), p_192750_1_.func_193123_j() }));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     if (advancementprogress.func_192105_a())
/*     */     {
/* 222 */       func_192742_b(p_192750_1_);
/*     */     }
/*     */     
/* 225 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192744_b(Advancement p_192744_1_, String p_192744_2_) {
/* 230 */     boolean flag = false;
/* 231 */     AdvancementProgress advancementprogress = func_192747_a(p_192744_1_);
/*     */     
/* 233 */     if (advancementprogress.func_192101_b(p_192744_2_)) {
/*     */       
/* 235 */       func_193764_b(p_192744_1_);
/* 236 */       this.field_192761_i.add(p_192744_1_);
/* 237 */       flag = true;
/*     */     } 
/*     */     
/* 240 */     if (!advancementprogress.func_192108_b())
/*     */     {
/* 242 */       func_192742_b(p_192744_1_);
/*     */     }
/*     */     
/* 245 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_193764_b(Advancement p_193764_1_) {
/* 250 */     AdvancementProgress advancementprogress = func_192747_a(p_193764_1_);
/*     */     
/* 252 */     if (!advancementprogress.func_192105_a())
/*     */     {
/* 254 */       for (Map.Entry<String, Criterion> entry : p_193764_1_.func_192073_f().entrySet()) {
/*     */         
/* 256 */         CriterionProgress criterionprogress = advancementprogress.func_192106_c(entry.getKey());
/*     */         
/* 258 */         if (criterionprogress != null && !criterionprogress.func_192151_a()) {
/*     */           
/* 260 */           ICriterionInstance icriterioninstance = ((Criterion)entry.getValue()).func_192143_a();
/*     */           
/* 262 */           if (icriterioninstance != null) {
/*     */             
/* 264 */             ICriterionTrigger<ICriterionInstance> icriteriontrigger = CriteriaTriggers.func_192119_a(icriterioninstance.func_192244_a());
/*     */             
/* 266 */             if (icriteriontrigger != null)
/*     */             {
/* 268 */               icriteriontrigger.func_192165_a(this, new ICriterionTrigger.Listener<>(icriterioninstance, p_193764_1_, entry.getKey()));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_193765_c(Advancement p_193765_1_) {
/* 278 */     AdvancementProgress advancementprogress = func_192747_a(p_193765_1_);
/*     */     
/* 280 */     for (Map.Entry<String, Criterion> entry : p_193765_1_.func_192073_f().entrySet()) {
/*     */       
/* 282 */       CriterionProgress criterionprogress = advancementprogress.func_192106_c(entry.getKey());
/*     */       
/* 284 */       if (criterionprogress != null && (criterionprogress.func_192151_a() || advancementprogress.func_192105_a())) {
/*     */         
/* 286 */         ICriterionInstance icriterioninstance = ((Criterion)entry.getValue()).func_192143_a();
/*     */         
/* 288 */         if (icriterioninstance != null) {
/*     */           
/* 290 */           ICriterionTrigger<ICriterionInstance> icriteriontrigger = CriteriaTriggers.func_192119_a(icriterioninstance.func_192244_a());
/*     */           
/* 292 */           if (icriteriontrigger != null)
/*     */           {
/* 294 */             icriteriontrigger.func_192164_b(this, new ICriterionTrigger.Listener<>(icriterioninstance, p_193765_1_, entry.getKey()));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192741_b(EntityPlayerMP p_192741_1_) {
/* 303 */     if (!this.field_192760_h.isEmpty() || !this.field_192761_i.isEmpty()) {
/*     */       
/* 305 */       Map<ResourceLocation, AdvancementProgress> map = Maps.newHashMap();
/* 306 */       Set<Advancement> set = Sets.newLinkedHashSet();
/* 307 */       Set<ResourceLocation> set1 = Sets.newLinkedHashSet();
/*     */       
/* 309 */       for (Advancement advancement : this.field_192761_i) {
/*     */         
/* 311 */         if (this.field_192759_g.contains(advancement))
/*     */         {
/* 313 */           map.put(advancement.func_192067_g(), this.field_192758_f.get(advancement));
/*     */         }
/*     */       } 
/*     */       
/* 317 */       for (Advancement advancement1 : this.field_192760_h) {
/*     */         
/* 319 */         if (this.field_192759_g.contains(advancement1)) {
/*     */           
/* 321 */           set.add(advancement1);
/*     */           
/*     */           continue;
/*     */         } 
/* 325 */         set1.add(advancement1.func_192067_g());
/*     */       } 
/*     */ 
/*     */       
/* 329 */       if (!map.isEmpty() || !set.isEmpty() || !set1.isEmpty()) {
/*     */         
/* 331 */         p_192741_1_.connection.sendPacket((Packet)new SPacketAdvancementInfo(this.field_192763_k, set, set1, map));
/* 332 */         this.field_192760_h.clear();
/* 333 */         this.field_192761_i.clear();
/*     */       } 
/*     */     } 
/*     */     
/* 337 */     this.field_192763_k = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194220_a(@Nullable Advancement p_194220_1_) {
/* 342 */     Advancement advancement = this.field_194221_k;
/*     */     
/* 344 */     if (p_194220_1_ != null && p_194220_1_.func_192070_b() == null && p_194220_1_.func_192068_c() != null) {
/*     */       
/* 346 */       this.field_194221_k = p_194220_1_;
/*     */     }
/*     */     else {
/*     */       
/* 350 */       this.field_194221_k = null;
/*     */     } 
/*     */     
/* 353 */     if (advancement != this.field_194221_k)
/*     */     {
/* 355 */       this.field_192762_j.connection.sendPacket((Packet)new SPacketSelectAdvancementsTab((this.field_194221_k == null) ? null : this.field_194221_k.func_192067_g()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public AdvancementProgress func_192747_a(Advancement p_192747_1_) {
/* 361 */     AdvancementProgress advancementprogress = this.field_192758_f.get(p_192747_1_);
/*     */     
/* 363 */     if (advancementprogress == null) {
/*     */       
/* 365 */       advancementprogress = new AdvancementProgress();
/* 366 */       func_192743_a(p_192747_1_, advancementprogress);
/*     */     } 
/*     */     
/* 369 */     return advancementprogress;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_192743_a(Advancement p_192743_1_, AdvancementProgress p_192743_2_) {
/* 374 */     p_192743_2_.func_192099_a(p_192743_1_.func_192073_f(), p_192743_1_.func_192074_h());
/* 375 */     this.field_192758_f.put(p_192743_1_, p_192743_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_192742_b(Advancement p_192742_1_) {
/* 380 */     boolean flag = func_192738_c(p_192742_1_);
/* 381 */     boolean flag1 = this.field_192759_g.contains(p_192742_1_);
/*     */     
/* 383 */     if (flag && !flag1) {
/*     */       
/* 385 */       this.field_192759_g.add(p_192742_1_);
/* 386 */       this.field_192760_h.add(p_192742_1_);
/*     */       
/* 388 */       if (this.field_192758_f.containsKey(p_192742_1_))
/*     */       {
/* 390 */         this.field_192761_i.add(p_192742_1_);
/*     */       }
/*     */     }
/* 393 */     else if (!flag && flag1) {
/*     */       
/* 395 */       this.field_192759_g.remove(p_192742_1_);
/* 396 */       this.field_192760_h.add(p_192742_1_);
/*     */     } 
/*     */     
/* 399 */     if (flag != flag1 && p_192742_1_.func_192070_b() != null)
/*     */     {
/* 401 */       func_192742_b(p_192742_1_.func_192070_b());
/*     */     }
/*     */     
/* 404 */     for (Advancement advancement : p_192742_1_.func_192069_e())
/*     */     {
/* 406 */       func_192742_b(advancement);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_192738_c(Advancement p_192738_1_) {
/* 412 */     for (int i = 0; p_192738_1_ != null && i <= 2; i++) {
/*     */       
/* 414 */       if (i == 0 && func_192746_d(p_192738_1_))
/*     */       {
/* 416 */         return true;
/*     */       }
/*     */       
/* 419 */       if (p_192738_1_.func_192068_c() == null)
/*     */       {
/* 421 */         return false;
/*     */       }
/*     */       
/* 424 */       AdvancementProgress advancementprogress = func_192747_a(p_192738_1_);
/*     */       
/* 426 */       if (advancementprogress.func_192105_a())
/*     */       {
/* 428 */         return true;
/*     */       }
/*     */       
/* 431 */       if (p_192738_1_.func_192068_c().func_193224_j())
/*     */       {
/* 433 */         return false;
/*     */       }
/*     */       
/* 436 */       p_192738_1_ = p_192738_1_.func_192070_b();
/*     */     } 
/*     */     
/* 439 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_192746_d(Advancement p_192746_1_) {
/* 444 */     AdvancementProgress advancementprogress = func_192747_a(p_192746_1_);
/*     */     
/* 446 */     if (advancementprogress.func_192105_a())
/*     */     {
/* 448 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 452 */     for (Advancement advancement : p_192746_1_.func_192069_e()) {
/*     */       
/* 454 */       if (func_192746_d(advancement))
/*     */       {
/* 456 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 460 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\PlayerAdvancements.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */