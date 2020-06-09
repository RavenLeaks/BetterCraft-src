/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.AdvancementProgress;
/*     */ import net.minecraft.advancements.CriterionProgress;
/*     */ import net.minecraft.advancements.PlayerAdvancements;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdvancementCommand
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  23 */     return "advancement";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  31 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  39 */     return "commands.advancement.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  47 */     if (args.length < 1)
/*     */     {
/*  49 */       throw new WrongUsageException("commands.advancement.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  53 */     ActionType advancementcommand$actiontype = ActionType.func_193536_a(args[0]);
/*     */     
/*  55 */     if (advancementcommand$actiontype != null) {
/*     */       
/*  57 */       if (args.length < 3)
/*     */       {
/*  59 */         throw advancementcommand$actiontype.func_193534_a();
/*     */       }
/*     */       
/*  62 */       EntityPlayerMP entityplayermp = getPlayer(server, sender, args[1]);
/*  63 */       Mode advancementcommand$mode = Mode.func_193547_a(args[2]);
/*     */       
/*  65 */       if (advancementcommand$mode == null)
/*     */       {
/*  67 */         throw advancementcommand$actiontype.func_193534_a();
/*     */       }
/*     */       
/*  70 */       func_193516_a(server, sender, args, entityplayermp, advancementcommand$actiontype, advancementcommand$mode);
/*     */     }
/*     */     else {
/*     */       
/*  74 */       if (!"test".equals(args[0]))
/*     */       {
/*  76 */         throw new WrongUsageException("commands.advancement.usage", new Object[0]);
/*     */       }
/*     */       
/*  79 */       if (args.length == 3) {
/*     */         
/*  81 */         func_192552_c(sender, getPlayer(server, sender, args[1]), func_192551_a(server, args[2]));
/*     */       }
/*     */       else {
/*     */         
/*  85 */         if (args.length != 4)
/*     */         {
/*  87 */           throw new WrongUsageException("commands.advancement.test.usage", new Object[0]);
/*     */         }
/*     */         
/*  90 */         func_192554_c(sender, getPlayer(server, sender, args[1]), func_192551_a(server, args[2]), args[3]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_193516_a(MinecraftServer p_193516_1_, ICommandSender p_193516_2_, String[] p_193516_3_, EntityPlayerMP p_193516_4_, ActionType p_193516_5_, Mode p_193516_6_) throws CommandException {
/*  98 */     if (p_193516_6_ == Mode.EVERYTHING) {
/*     */       
/* 100 */       if (p_193516_3_.length == 3) {
/*     */         
/* 102 */         int j = p_193516_5_.func_193532_a(p_193516_4_, p_193516_1_.func_191949_aK().func_192780_b());
/*     */         
/* 104 */         if (j == 0)
/*     */         {
/* 106 */           throw p_193516_6_.func_193543_a(p_193516_5_, new Object[] { p_193516_4_.getName() });
/*     */         }
/*     */ 
/*     */         
/* 110 */         p_193516_6_.func_193546_a(p_193516_2_, this, p_193516_5_, new Object[] { p_193516_4_.getName(), Integer.valueOf(j) });
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 115 */         throw p_193516_6_.func_193544_a(p_193516_5_);
/*     */       } 
/*     */     } else {
/* 118 */       if (p_193516_3_.length < 4)
/*     */       {
/* 120 */         throw p_193516_6_.func_193544_a(p_193516_5_);
/*     */       }
/*     */ 
/*     */       
/* 124 */       Advancement advancement = func_192551_a(p_193516_1_, p_193516_3_[3]);
/*     */       
/* 126 */       if (p_193516_6_ == Mode.ONLY && p_193516_3_.length == 5) {
/*     */         
/* 128 */         String s = p_193516_3_[4];
/*     */         
/* 130 */         if (!advancement.func_192073_f().keySet().contains(s))
/*     */         {
/* 132 */           throw new CommandException("commands.advancement.criterionNotFound", new Object[] { advancement.func_192067_g(), p_193516_3_[4] });
/*     */         }
/*     */         
/* 135 */         if (!p_193516_5_.func_193535_a(p_193516_4_, advancement, s))
/*     */         {
/* 137 */           throw new CommandException(String.valueOf(p_193516_5_.field_193541_d) + ".criterion.failed", new Object[] { advancement.func_192067_g(), p_193516_4_.getName(), s });
/*     */         }
/*     */         
/* 140 */         notifyCommandListener(p_193516_2_, this, String.valueOf(p_193516_5_.field_193541_d) + ".criterion.success", new Object[] { advancement.func_192067_g(), p_193516_4_.getName(), s });
/*     */       }
/*     */       else {
/*     */         
/* 144 */         if (p_193516_3_.length != 4)
/*     */         {
/* 146 */           throw p_193516_6_.func_193544_a(p_193516_5_);
/*     */         }
/*     */         
/* 149 */         List<Advancement> list = func_193514_a(advancement, p_193516_6_);
/* 150 */         int i = p_193516_5_.func_193532_a(p_193516_4_, list);
/*     */         
/* 152 */         if (i == 0)
/*     */         {
/* 154 */           throw p_193516_6_.func_193543_a(p_193516_5_, new Object[] { advancement.func_192067_g(), p_193516_4_.getName() });
/*     */         }
/*     */         
/* 157 */         p_193516_6_.func_193546_a(p_193516_2_, this, p_193516_5_, new Object[] { advancement.func_192067_g(), p_193516_4_.getName(), Integer.valueOf(i) });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_193515_a(Advancement p_193515_1_, List<Advancement> p_193515_2_) {
/* 164 */     for (Advancement advancement : p_193515_1_.func_192069_e()) {
/*     */       
/* 166 */       p_193515_2_.add(advancement);
/* 167 */       func_193515_a(advancement, p_193515_2_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Advancement> func_193514_a(Advancement p_193514_1_, Mode p_193514_2_) {
/* 173 */     List<Advancement> list = Lists.newArrayList();
/*     */     
/* 175 */     if (p_193514_2_.field_193555_h)
/*     */     {
/* 177 */       for (Advancement advancement = p_193514_1_.func_192070_b(); advancement != null; advancement = advancement.func_192070_b())
/*     */       {
/* 179 */         list.add(advancement);
/*     */       }
/*     */     }
/*     */     
/* 183 */     list.add(p_193514_1_);
/*     */     
/* 185 */     if (p_193514_2_.field_193556_i)
/*     */     {
/* 187 */       func_193515_a(p_193514_1_, list);
/*     */     }
/*     */     
/* 190 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_192554_c(ICommandSender p_192554_1_, EntityPlayerMP p_192554_2_, Advancement p_192554_3_, String p_192554_4_) throws CommandException {
/* 195 */     PlayerAdvancements playeradvancements = p_192554_2_.func_192039_O();
/* 196 */     CriterionProgress criterionprogress = playeradvancements.func_192747_a(p_192554_3_).func_192106_c(p_192554_4_);
/*     */     
/* 198 */     if (criterionprogress == null)
/*     */     {
/* 200 */       throw new CommandException("commands.advancement.criterionNotFound", new Object[] { p_192554_3_.func_192067_g(), p_192554_4_ });
/*     */     }
/* 202 */     if (!criterionprogress.func_192151_a())
/*     */     {
/* 204 */       throw new CommandException("commands.advancement.test.criterion.notDone", new Object[] { p_192554_2_.getName(), p_192554_3_.func_192067_g(), p_192554_4_ });
/*     */     }
/*     */ 
/*     */     
/* 208 */     notifyCommandListener(p_192554_1_, this, "commands.advancement.test.criterion.success", new Object[] { p_192554_2_.getName(), p_192554_3_.func_192067_g(), p_192554_4_ });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_192552_c(ICommandSender p_192552_1_, EntityPlayerMP p_192552_2_, Advancement p_192552_3_) throws CommandException {
/* 214 */     AdvancementProgress advancementprogress = p_192552_2_.func_192039_O().func_192747_a(p_192552_3_);
/*     */     
/* 216 */     if (!advancementprogress.func_192105_a())
/*     */     {
/* 218 */       throw new CommandException("commands.advancement.test.advancement.notDone", new Object[] { p_192552_2_.getName(), p_192552_3_.func_192067_g() });
/*     */     }
/*     */ 
/*     */     
/* 222 */     notifyCommandListener(p_192552_1_, this, "commands.advancement.test.advancement.success", new Object[] { p_192552_2_.getName(), p_192552_3_.func_192067_g() });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 228 */     if (args.length == 1)
/*     */     {
/* 230 */       return getListOfStringsMatchingLastWord(args, new String[] { "grant", "revoke", "test" });
/*     */     }
/*     */ 
/*     */     
/* 234 */     ActionType advancementcommand$actiontype = ActionType.func_193536_a(args[0]);
/*     */     
/* 236 */     if (advancementcommand$actiontype != null) {
/*     */       
/* 238 */       if (args.length == 2)
/*     */       {
/* 240 */         return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */       }
/*     */       
/* 243 */       if (args.length == 3)
/*     */       {
/* 245 */         return getListOfStringsMatchingLastWord(args, Mode.field_193553_f);
/*     */       }
/*     */       
/* 248 */       Mode advancementcommand$mode = Mode.func_193547_a(args[2]);
/*     */       
/* 250 */       if (advancementcommand$mode != null && advancementcommand$mode != Mode.EVERYTHING) {
/*     */         
/* 252 */         if (args.length == 4)
/*     */         {
/* 254 */           return getListOfStringsMatchingLastWord(args, func_193517_a(server));
/*     */         }
/*     */         
/* 257 */         if (args.length == 5 && advancementcommand$mode == Mode.ONLY) {
/*     */           
/* 259 */           Advancement advancement = server.func_191949_aK().func_192778_a(new ResourceLocation(args[3]));
/*     */           
/* 261 */           if (advancement != null)
/*     */           {
/* 263 */             return getListOfStringsMatchingLastWord(args, advancement.func_192073_f().keySet());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 269 */     if ("test".equals(args[0])) {
/*     */       
/* 271 */       if (args.length == 2)
/*     */       {
/* 273 */         return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */       }
/*     */       
/* 276 */       if (args.length == 3)
/*     */       {
/* 278 */         return getListOfStringsMatchingLastWord(args, func_193517_a(server));
/*     */       }
/*     */       
/* 281 */       if (args.length == 4) {
/*     */         
/* 283 */         Advancement advancement1 = server.func_191949_aK().func_192778_a(new ResourceLocation(args[2]));
/*     */         
/* 285 */         if (advancement1 != null)
/*     */         {
/* 287 */           return getListOfStringsMatchingLastWord(args, advancement1.func_192073_f().keySet());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 292 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<ResourceLocation> func_193517_a(MinecraftServer p_193517_1_) {
/* 298 */     List<ResourceLocation> list = Lists.newArrayList();
/*     */     
/* 300 */     for (Advancement advancement : p_193517_1_.func_191949_aK().func_192780_b())
/*     */     {
/* 302 */       list.add(advancement.func_192067_g());
/*     */     }
/*     */     
/* 305 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 313 */     return (args.length > 1 && ("grant".equals(args[0]) || "revoke".equals(args[0]) || "test".equals(args[0])) && index == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Advancement func_192551_a(MinecraftServer p_192551_0_, String p_192551_1_) throws CommandException {
/* 318 */     Advancement advancement = p_192551_0_.func_191949_aK().func_192778_a(new ResourceLocation(p_192551_1_));
/*     */     
/* 320 */     if (advancement == null)
/*     */     {
/* 322 */       throw new CommandException("commands.advancement.advancementNotFound", new Object[] { p_192551_1_ });
/*     */     }
/*     */ 
/*     */     
/* 326 */     return advancement;
/*     */   }
/*     */ 
/*     */   
/*     */   enum ActionType
/*     */   {
/* 332 */     GRANT("grant")
/*     */     {
/*     */       protected boolean func_193537_a(EntityPlayerMP p_193537_1_, Advancement p_193537_2_)
/*     */       {
/* 336 */         AdvancementProgress advancementprogress = p_193537_1_.func_192039_O().func_192747_a(p_193537_2_);
/*     */         
/* 338 */         if (advancementprogress.func_192105_a())
/*     */         {
/* 340 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 344 */         for (String s : advancementprogress.func_192107_d())
/*     */         {
/* 346 */           p_193537_1_.func_192039_O().func_192750_a(p_193537_2_, s);
/*     */         }
/*     */         
/* 349 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       protected boolean func_193535_a(EntityPlayerMP p_193535_1_, Advancement p_193535_2_, String p_193535_3_) {
/* 354 */         return p_193535_1_.func_192039_O().func_192750_a(p_193535_2_, p_193535_3_);
/*     */       }
/*     */     },
/* 357 */     REVOKE("revoke")
/*     */     {
/*     */       protected boolean func_193537_a(EntityPlayerMP p_193537_1_, Advancement p_193537_2_)
/*     */       {
/* 361 */         AdvancementProgress advancementprogress = p_193537_1_.func_192039_O().func_192747_a(p_193537_2_);
/*     */         
/* 363 */         if (!advancementprogress.func_192108_b())
/*     */         {
/* 365 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 369 */         for (String s : advancementprogress.func_192102_e())
/*     */         {
/* 371 */           p_193537_1_.func_192039_O().func_192744_b(p_193537_2_, s);
/*     */         }
/*     */         
/* 374 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       protected boolean func_193535_a(EntityPlayerMP p_193535_1_, Advancement p_193535_2_, String p_193535_3_) {
/* 379 */         return p_193535_1_.func_192039_O().func_192744_b(p_193535_2_, p_193535_3_);
/*     */       }
/*     */     };
/*     */ 
/*     */     
/*     */     final String field_193540_c;
/*     */     final String field_193541_d;
/*     */     
/*     */     ActionType(String p_i47557_3_) {
/* 388 */       this.field_193540_c = p_i47557_3_;
/* 389 */       this.field_193541_d = "commands.advancement." + p_i47557_3_;
/*     */     } @Nullable
/*     */     static ActionType func_193536_a(String p_193536_0_) {
/*     */       byte b;
/*     */       int i;
/*     */       ActionType[] arrayOfActionType;
/* 395 */       for (i = (arrayOfActionType = values()).length, b = 0; b < i; ) { ActionType advancementcommand$actiontype = arrayOfActionType[b];
/*     */         
/* 397 */         if (advancementcommand$actiontype.field_193540_c.equals(p_193536_0_))
/*     */         {
/* 399 */           return advancementcommand$actiontype;
/*     */         }
/*     */         b++; }
/*     */       
/* 403 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     CommandException func_193534_a() {
/* 408 */       return new CommandException(String.valueOf(this.field_193541_d) + ".usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_193532_a(EntityPlayerMP p_193532_1_, Iterable<Advancement> p_193532_2_) {
/* 413 */       int i = 0;
/*     */       
/* 415 */       for (Advancement advancement : p_193532_2_) {
/*     */         
/* 417 */         if (func_193537_a(p_193532_1_, advancement))
/*     */         {
/* 419 */           i++;
/*     */         }
/*     */       } 
/*     */       
/* 423 */       return i;
/*     */     }
/*     */     
/*     */     protected abstract boolean func_193537_a(EntityPlayerMP param1EntityPlayerMP, Advancement param1Advancement);
/*     */     
/*     */     protected abstract boolean func_193535_a(EntityPlayerMP param1EntityPlayerMP, Advancement param1Advancement, String param1String);
/*     */   }
/*     */   
/*     */   enum Mode
/*     */   {
/* 433 */     ONLY("only", false, false),
/* 434 */     THROUGH("through", true, true),
/* 435 */     FROM("from", false, true),
/* 436 */     UNTIL("until", true, false),
/* 437 */     EVERYTHING("everything", true, true);
/*     */     
/* 439 */     static final String[] field_193553_f = new String[(values()).length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final String field_193554_g;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final boolean field_193555_h;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final boolean field_193556_i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 481 */       for (int i = 0; i < (values()).length; i++)
/*     */       {
/* 483 */         field_193553_f[i] = (values()[i]).field_193554_g;
/*     */       }
/*     */     }
/*     */     
/*     */     Mode(String p_i47556_3_, boolean p_i47556_4_, boolean p_i47556_5_) {
/*     */       this.field_193554_g = p_i47556_3_;
/*     */       this.field_193555_h = p_i47556_4_;
/*     */       this.field_193556_i = p_i47556_5_;
/*     */     }
/*     */     
/*     */     CommandException func_193543_a(AdvancementCommand.ActionType p_193543_1_, Object... p_193543_2_) {
/*     */       return new CommandException(String.valueOf(p_193543_1_.field_193541_d) + "." + this.field_193554_g + ".failed", p_193543_2_);
/*     */     }
/*     */     
/*     */     CommandException func_193544_a(AdvancementCommand.ActionType p_193544_1_) {
/*     */       return new CommandException(String.valueOf(p_193544_1_.field_193541_d) + "." + this.field_193554_g + ".usage", new Object[0]);
/*     */     }
/*     */     
/*     */     void func_193546_a(ICommandSender p_193546_1_, AdvancementCommand p_193546_2_, AdvancementCommand.ActionType p_193546_3_, Object... p_193546_4_) {
/*     */       CommandBase.notifyCommandListener(p_193546_1_, p_193546_2_, String.valueOf(p_193546_3_.field_193541_d) + "." + this.field_193554_g + ".success", p_193546_4_);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     static Mode func_193547_a(String p_193547_0_) {
/*     */       byte b;
/*     */       int i;
/*     */       Mode[] arrayOfMode;
/*     */       for (i = (arrayOfMode = values()).length, b = 0; b < i; ) {
/*     */         Mode advancementcommand$mode = arrayOfMode[b];
/*     */         if (advancementcommand$mode.field_193554_g.equals(p_193547_0_))
/*     */           return advancementcommand$mode; 
/*     */         b++;
/*     */       } 
/*     */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\AdvancementCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */