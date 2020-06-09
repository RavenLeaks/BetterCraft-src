/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntitySelector
/*     */ {
/*  46 */   private static final Pattern TOKEN_PATTERN = Pattern.compile("^@([pares])(?:\\[([^ ]*)\\])?$");
/*  47 */   private static final Splitter field_190828_b = Splitter.on(',').omitEmptyStrings();
/*  48 */   private static final Splitter field_190829_c = Splitter.on('=').limit(2);
/*  49 */   private static final Set<String> field_190830_d = Sets.newHashSet();
/*  50 */   private static final String field_190831_e = func_190826_c("r");
/*  51 */   private static final String field_190832_f = func_190826_c("rm");
/*  52 */   private static final String field_190833_g = func_190826_c("l");
/*  53 */   private static final String field_190834_h = func_190826_c("lm");
/*  54 */   private static final String field_190835_i = func_190826_c("x");
/*  55 */   private static final String field_190836_j = func_190826_c("y");
/*  56 */   private static final String field_190837_k = func_190826_c("z");
/*  57 */   private static final String field_190838_l = func_190826_c("dx");
/*  58 */   private static final String field_190839_m = func_190826_c("dy");
/*  59 */   private static final String field_190840_n = func_190826_c("dz");
/*  60 */   private static final String field_190841_o = func_190826_c("rx");
/*  61 */   private static final String field_190842_p = func_190826_c("rxm");
/*  62 */   private static final String field_190843_q = func_190826_c("ry");
/*  63 */   private static final String field_190844_r = func_190826_c("rym");
/*  64 */   private static final String field_190845_s = func_190826_c("c");
/*  65 */   private static final String field_190846_t = func_190826_c("m");
/*  66 */   private static final String field_190847_u = func_190826_c("team");
/*  67 */   private static final String field_190848_v = func_190826_c("name");
/*  68 */   private static final String field_190849_w = func_190826_c("type");
/*  69 */   private static final String field_190850_x = func_190826_c("tag");
/*  70 */   private static final Predicate<String> field_190851_y = new Predicate<String>()
/*     */     {
/*     */       public boolean apply(@Nullable String p_apply_1_)
/*     */       {
/*  74 */         return (p_apply_1_ != null && (EntitySelector.field_190830_d.contains(p_apply_1_) || (p_apply_1_.length() > "score_".length() && p_apply_1_.startsWith("score_"))));
/*     */       }
/*     */     };
/*  77 */   private static final Set<String> WORLD_BINDING_ARGS = Sets.newHashSet((Object[])new String[] { field_190835_i, field_190836_j, field_190837_k, field_190838_l, field_190839_m, field_190840_n, field_190832_f, field_190831_e });
/*     */ 
/*     */   
/*     */   private static String func_190826_c(String p_190826_0_) {
/*  81 */     field_190830_d.add(p_190826_0_);
/*  82 */     return p_190826_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static EntityPlayerMP matchOnePlayer(ICommandSender sender, String token) throws CommandException {
/*  92 */     return matchOneEntity(sender, token, EntityPlayerMP.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<EntityPlayerMP> func_193531_b(ICommandSender p_193531_0_, String p_193531_1_) throws CommandException {
/*  97 */     return matchEntities(p_193531_0_, p_193531_1_, EntityPlayerMP.class);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <T extends Entity> T matchOneEntity(ICommandSender sender, String token, Class<? extends T> targetClass) throws CommandException {
/* 103 */     List<T> list = matchEntities(sender, token, targetClass);
/* 104 */     return (list.size() == 1) ? list.get(0) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static ITextComponent matchEntitiesToTextComponent(ICommandSender sender, String token) throws CommandException {
/* 110 */     List<Entity> list = matchEntities(sender, token, Entity.class);
/*     */     
/* 112 */     if (list.isEmpty())
/*     */     {
/* 114 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 118 */     List<ITextComponent> list1 = Lists.newArrayList();
/*     */     
/* 120 */     for (Entity entity : list)
/*     */     {
/* 122 */       list1.add(entity.getDisplayName());
/*     */     }
/*     */     
/* 125 */     return CommandBase.join(list1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Entity> List<T> matchEntities(ICommandSender sender, String token, Class<? extends T> targetClass) throws CommandException {
/* 131 */     Matcher matcher = TOKEN_PATTERN.matcher(token);
/*     */     
/* 133 */     if (matcher.matches() && sender.canCommandSenderUseCommand(1, "@")) {
/*     */       
/* 135 */       Map<String, String> map = getArgumentMap(matcher.group(2));
/*     */       
/* 137 */       if (!isEntityTypeValid(sender, map))
/*     */       {
/* 139 */         return Collections.emptyList();
/*     */       }
/*     */ 
/*     */       
/* 143 */       String s = matcher.group(1);
/* 144 */       BlockPos blockpos = getBlockPosFromArguments(map, sender.getPosition());
/* 145 */       Vec3d vec3d = getPosFromArguments(map, sender.getPositionVector());
/* 146 */       List<World> list = getWorlds(sender, map);
/* 147 */       List<T> list1 = Lists.newArrayList();
/*     */       
/* 149 */       for (World world : list) {
/*     */         
/* 151 */         if (world != null) {
/*     */           
/* 153 */           List<Predicate<Entity>> list2 = Lists.newArrayList();
/* 154 */           list2.addAll(getTypePredicates(map, s));
/* 155 */           list2.addAll(getXpLevelPredicates(map));
/* 156 */           list2.addAll(getGamemodePredicates(map));
/* 157 */           list2.addAll(getTeamPredicates(map));
/* 158 */           list2.addAll(getScorePredicates(sender, map));
/* 159 */           list2.addAll(getNamePredicates(map));
/* 160 */           list2.addAll(getTagPredicates(map));
/* 161 */           list2.addAll(getRadiusPredicates(map, vec3d));
/* 162 */           list2.addAll(getRotationsPredicates(map));
/*     */           
/* 164 */           if ("s".equalsIgnoreCase(s)) {
/*     */             
/* 166 */             Entity entity = sender.getCommandSenderEntity();
/*     */             
/* 168 */             if (entity != null && targetClass.isAssignableFrom(entity.getClass())) {
/*     */               
/* 170 */               if (map.containsKey(field_190838_l) || map.containsKey(field_190839_m) || map.containsKey(field_190840_n)) {
/*     */                 
/* 172 */                 int i = getInt(map, field_190838_l, 0);
/* 173 */                 int j = getInt(map, field_190839_m, 0);
/* 174 */                 int k = getInt(map, field_190840_n, 0);
/* 175 */                 AxisAlignedBB axisalignedbb = getAABB(blockpos, i, j, k);
/*     */                 
/* 177 */                 if (!axisalignedbb.intersectsWith(entity.getEntityBoundingBox()))
/*     */                 {
/* 179 */                   return Collections.emptyList();
/*     */                 }
/*     */               } 
/*     */               
/* 183 */               for (Predicate<Entity> predicate : list2) {
/*     */                 
/* 185 */                 if (!predicate.apply(entity))
/*     */                 {
/* 187 */                   return Collections.emptyList();
/*     */                 }
/*     */               } 
/*     */               
/* 191 */               return Lists.newArrayList((Object[])new Entity[] { entity });
/*     */             } 
/*     */             
/* 194 */             return Collections.emptyList();
/*     */           } 
/*     */           
/* 197 */           list1.addAll(filterResults(map, targetClass, list2, s, world, blockpos));
/*     */         } 
/*     */       } 
/*     */       
/* 201 */       return getEntitiesFromPredicates(list1, map, sender, targetClass, s, vec3d);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 206 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<World> getWorlds(ICommandSender sender, Map<String, String> argumentMap) {
/* 212 */     List<World> list = Lists.newArrayList();
/*     */     
/* 214 */     if (hasArgument(argumentMap)) {
/*     */       
/* 216 */       list.add(sender.getEntityWorld());
/*     */     }
/*     */     else {
/*     */       
/* 220 */       Collections.addAll(list, (Object[])(sender.getServer()).worldServers);
/*     */     } 
/*     */     
/* 223 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Entity> boolean isEntityTypeValid(ICommandSender commandSender, Map<String, String> params) {
/* 228 */     String s = getArgument(params, field_190849_w);
/*     */     
/* 230 */     if (s == null)
/*     */     {
/* 232 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 236 */     ResourceLocation resourcelocation = new ResourceLocation(s.startsWith("!") ? s.substring(1) : s);
/*     */     
/* 238 */     if (EntityList.isStringValidEntityName(resourcelocation))
/*     */     {
/* 240 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 244 */     TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("commands.generic.entity.invalidType", new Object[] { resourcelocation });
/* 245 */     textcomponenttranslation.getStyle().setColor(TextFormatting.RED);
/* 246 */     commandSender.addChatMessage((ITextComponent)textcomponenttranslation);
/* 247 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> getTypePredicates(Map<String, String> params, String type) {
/* 254 */     String s = getArgument(params, field_190849_w);
/*     */     
/* 256 */     if (s == null || (!type.equals("e") && !type.equals("r") && !type.equals("s")))
/*     */     {
/* 258 */       return (!type.equals("e") && !type.equals("s")) ? Collections.<Predicate<Entity>>singletonList(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(@Nullable Entity p_apply_1_)
/*     */             {
/* 262 */               return p_apply_1_ instanceof net.minecraft.entity.player.EntityPlayer;
/*     */             }
/* 264 */           }) : Collections.<Predicate<Entity>>emptyList();
/*     */     }
/*     */ 
/*     */     
/* 268 */     final boolean flag = s.startsWith("!");
/* 269 */     final ResourceLocation resourcelocation = new ResourceLocation(flag ? s.substring(1) : s);
/* 270 */     return Collections.singletonList(new Predicate<Entity>()
/*     */         {
/*     */           public boolean apply(@Nullable Entity p_apply_1_)
/*     */           {
/* 274 */             return EntityList.isStringEntityName(p_apply_1_, resourcelocation) ^ flag;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> getXpLevelPredicates(Map<String, String> params) {
/* 282 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 283 */     final int i = getInt(params, field_190834_h, -1);
/* 284 */     final int j = getInt(params, field_190833_g, -1);
/*     */     
/* 286 */     if (i > -1 || j > -1)
/*     */     {
/* 288 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(@Nullable Entity p_apply_1_)
/*     */             {
/* 292 */               if (!(p_apply_1_ instanceof EntityPlayerMP))
/*     */               {
/* 294 */                 return false;
/*     */               }
/*     */ 
/*     */               
/* 298 */               EntityPlayerMP entityplayermp = (EntityPlayerMP)p_apply_1_;
/* 299 */               return ((i <= -1 || entityplayermp.experienceLevel >= i) && (j <= -1 || entityplayermp.experienceLevel <= j));
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 305 */     return list;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> getGamemodePredicates(Map<String, String> params) {
/*     */     GameType gametype;
/* 310 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 311 */     String s = getArgument(params, field_190846_t);
/*     */     
/* 313 */     if (s == null)
/*     */     {
/* 315 */       return list;
/*     */     }
/*     */ 
/*     */     
/* 319 */     final boolean flag = s.startsWith("!");
/*     */     
/* 321 */     if (flag)
/*     */     {
/* 323 */       s = s.substring(1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 330 */       int i = Integer.parseInt(s);
/* 331 */       gametype = GameType.parseGameTypeWithDefault(i, GameType.NOT_SET);
/*     */     }
/* 333 */     catch (Throwable var6) {
/*     */       
/* 335 */       gametype = GameType.parseGameTypeWithDefault(s, GameType.NOT_SET);
/*     */     } 
/*     */     
/* 338 */     final GameType type = gametype;
/* 339 */     list.add(new Predicate<Entity>()
/*     */         {
/*     */           public boolean apply(@Nullable Entity p_apply_1_)
/*     */           {
/* 343 */             if (!(p_apply_1_ instanceof EntityPlayerMP))
/*     */             {
/* 345 */               return false;
/*     */             }
/*     */ 
/*     */             
/* 349 */             EntityPlayerMP entityplayermp = (EntityPlayerMP)p_apply_1_;
/* 350 */             GameType gametype1 = entityplayermp.interactionManager.getGameType();
/* 351 */             return flag ? ((gametype1 != type)) : ((gametype1 == type));
/*     */           }
/*     */         });
/*     */     
/* 355 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> getTeamPredicates(Map<String, String> params) {
/* 361 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 362 */     String s = getArgument(params, field_190847_u);
/* 363 */     final boolean flag = (s != null && s.startsWith("!"));
/*     */     
/* 365 */     if (flag)
/*     */     {
/* 367 */       s = s.substring(1);
/*     */     }
/*     */     
/* 370 */     if (s != null) {
/*     */       
/* 372 */       final String s_f_ = s;
/* 373 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(@Nullable Entity p_apply_1_)
/*     */             {
/* 377 */               if (!(p_apply_1_ instanceof EntityLivingBase))
/*     */               {
/* 379 */                 return false;
/*     */               }
/*     */ 
/*     */               
/* 383 */               EntityLivingBase entitylivingbase = (EntityLivingBase)p_apply_1_;
/* 384 */               Team team = entitylivingbase.getTeam();
/* 385 */               String s1 = (team == null) ? "" : team.getRegisteredName();
/* 386 */               return s1.equals(s_f_) ^ flag;
/*     */             }
/*     */           });
/*     */     } 
/*     */ 
/*     */     
/* 392 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> getScorePredicates(final ICommandSender sender, Map<String, String> params) {
/* 397 */     final Map<String, Integer> map = getScoreMap(params);
/* 398 */     return map.isEmpty() ? Collections.<Predicate<Entity>>emptyList() : Lists.newArrayList((Object[])new Predicate[] { new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(@Nullable Entity p_apply_1_)
/*     */             {
/* 402 */               if (p_apply_1_ == null)
/*     */               {
/* 404 */                 return false;
/*     */               }
/*     */ 
/*     */               
/* 408 */               Scoreboard scoreboard = sender.getServer().worldServerForDimension(0).getScoreboard();
/*     */               
/* 410 */               for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>)map.entrySet()) {
/*     */                 
/* 412 */                 String s = entry.getKey();
/* 413 */                 boolean flag = false;
/*     */                 
/* 415 */                 if (s.endsWith("_min") && s.length() > 4) {
/*     */                   
/* 417 */                   flag = true;
/* 418 */                   s = s.substring(0, s.length() - 4);
/*     */                 } 
/*     */                 
/* 421 */                 ScoreObjective scoreobjective = scoreboard.getObjective(s);
/*     */                 
/* 423 */                 if (scoreobjective == null)
/*     */                 {
/* 425 */                   return false;
/*     */                 }
/*     */                 
/* 428 */                 String s1 = (p_apply_1_ instanceof EntityPlayerMP) ? p_apply_1_.getName() : p_apply_1_.getCachedUniqueIdString();
/*     */                 
/* 430 */                 if (!scoreboard.entityHasObjective(s1, scoreobjective))
/*     */                 {
/* 432 */                   return false;
/*     */                 }
/*     */                 
/* 435 */                 Score score = scoreboard.getOrCreateScore(s1, scoreobjective);
/* 436 */                 int i = score.getScorePoints();
/*     */                 
/* 438 */                 if (i < ((Integer)entry.getValue()).intValue() && flag)
/*     */                 {
/* 440 */                   return false;
/*     */                 }
/*     */                 
/* 443 */                 if (i > ((Integer)entry.getValue()).intValue() && !flag)
/*     */                 {
/* 445 */                   return false;
/*     */                 }
/*     */               } 
/*     */               
/* 449 */               return true;
/*     */             }
/*     */           } });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> getNamePredicates(Map<String, String> params) {
/* 457 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 458 */     String s = getArgument(params, field_190848_v);
/* 459 */     final boolean flag = (s != null && s.startsWith("!"));
/*     */     
/* 461 */     if (flag)
/*     */     {
/* 463 */       s = s.substring(1);
/*     */     }
/*     */     
/* 466 */     if (s != null) {
/*     */       
/* 468 */       final String s_f_ = s;
/* 469 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(@Nullable Entity p_apply_1_)
/*     */             {
/* 473 */               return (p_apply_1_ != null && p_apply_1_.getName().equals(s_f_) != flag);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 478 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> getTagPredicates(Map<String, String> params) {
/* 483 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 484 */     String s = getArgument(params, field_190850_x);
/* 485 */     final boolean flag = (s != null && s.startsWith("!"));
/*     */     
/* 487 */     if (flag)
/*     */     {
/* 489 */       s = s.substring(1);
/*     */     }
/*     */     
/* 492 */     if (s != null) {
/*     */       
/* 494 */       final String s_f_ = s;
/* 495 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(@Nullable Entity p_apply_1_)
/*     */             {
/* 499 */               if (p_apply_1_ == null)
/*     */               {
/* 501 */                 return false;
/*     */               }
/* 503 */               if ("".equals(s_f_))
/*     */               {
/* 505 */                 return p_apply_1_.getTags().isEmpty() ^ flag;
/*     */               }
/*     */ 
/*     */               
/* 509 */               return p_apply_1_.getTags().contains(s_f_) ^ flag;
/*     */             }
/*     */           });
/*     */     } 
/*     */ 
/*     */     
/* 515 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> getRadiusPredicates(Map<String, String> params, final Vec3d pos) {
/* 520 */     double d0 = getInt(params, field_190832_f, -1);
/* 521 */     double d1 = getInt(params, field_190831_e, -1);
/* 522 */     final boolean flag = (d0 < -0.5D);
/* 523 */     final boolean flag1 = (d1 < -0.5D);
/*     */     
/* 525 */     if (flag && flag1)
/*     */     {
/* 527 */       return Collections.emptyList();
/*     */     }
/*     */ 
/*     */     
/* 531 */     double d2 = Math.max(d0, 1.0E-4D);
/* 532 */     final double d3 = d2 * d2;
/* 533 */     double d4 = Math.max(d1, 1.0E-4D);
/* 534 */     final double d5 = d4 * d4;
/* 535 */     return Lists.newArrayList((Object[])new Predicate[] { new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(@Nullable Entity p_apply_1_)
/*     */             {
/* 539 */               if (p_apply_1_ == null)
/*     */               {
/* 541 */                 return false;
/*     */               }
/*     */ 
/*     */               
/* 545 */               double d6 = pos.squareDistanceTo(p_apply_1_.posX, p_apply_1_.posY, p_apply_1_.posZ);
/* 546 */               return ((flag || d6 >= d3) && (flag1 || d6 <= d5));
/*     */             }
/*     */           } });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> getRotationsPredicates(Map<String, String> params) {
/* 555 */     List<Predicate<Entity>> list = Lists.newArrayList();
/*     */     
/* 557 */     if (params.containsKey(field_190844_r) || params.containsKey(field_190843_q)) {
/*     */       
/* 559 */       final int i = MathHelper.clampAngle(getInt(params, field_190844_r, 0));
/* 560 */       final int j = MathHelper.clampAngle(getInt(params, field_190843_q, 359));
/* 561 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(@Nullable Entity p_apply_1_)
/*     */             {
/* 565 */               if (p_apply_1_ == null)
/*     */               {
/* 567 */                 return false;
/*     */               }
/*     */ 
/*     */               
/* 571 */               int i1 = MathHelper.clampAngle(MathHelper.floor(p_apply_1_.rotationYaw));
/*     */               
/* 573 */               if (i > j)
/*     */               {
/* 575 */                 return !(i1 < i && i1 > j);
/*     */               }
/*     */ 
/*     */               
/* 579 */               return (i1 >= i && i1 <= j);
/*     */             }
/*     */           });
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 586 */     if (params.containsKey(field_190842_p) || params.containsKey(field_190841_o)) {
/*     */       
/* 588 */       final int k = MathHelper.clampAngle(getInt(params, field_190842_p, 0));
/* 589 */       final int l = MathHelper.clampAngle(getInt(params, field_190841_o, 359));
/* 590 */       list.add(new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(@Nullable Entity p_apply_1_)
/*     */             {
/* 594 */               if (p_apply_1_ == null)
/*     */               {
/* 596 */                 return false;
/*     */               }
/*     */ 
/*     */               
/* 600 */               int i1 = MathHelper.clampAngle(MathHelper.floor(p_apply_1_.rotationPitch));
/*     */               
/* 602 */               if (k > l)
/*     */               {
/* 604 */                 return !(i1 < k && i1 > l);
/*     */               }
/*     */ 
/*     */               
/* 608 */               return (i1 >= k && i1 <= l);
/*     */             }
/*     */           });
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 615 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Entity> List<T> filterResults(Map<String, String> params, Class<? extends T> entityClass, List<Predicate<Entity>> inputList, String type, World worldIn, BlockPos position) {
/* 620 */     List<T> list = Lists.newArrayList();
/* 621 */     String s = getArgument(params, field_190849_w);
/* 622 */     s = (s != null && s.startsWith("!")) ? s.substring(1) : s;
/* 623 */     boolean flag = !type.equals("e");
/* 624 */     boolean flag1 = (type.equals("r") && s != null);
/* 625 */     int i = getInt(params, field_190838_l, 0);
/* 626 */     int j = getInt(params, field_190839_m, 0);
/* 627 */     int k = getInt(params, field_190840_n, 0);
/* 628 */     int l = getInt(params, field_190831_e, -1);
/* 629 */     Predicate<Entity> predicate = Predicates.and(inputList);
/* 630 */     Predicate<Entity> predicate1 = Predicates.and(EntitySelectors.IS_ALIVE, predicate);
/*     */     
/* 632 */     if (!params.containsKey(field_190838_l) && !params.containsKey(field_190839_m) && !params.containsKey(field_190840_n)) {
/*     */       
/* 634 */       if (l >= 0) {
/*     */         
/* 636 */         AxisAlignedBB axisalignedbb1 = new AxisAlignedBB((position.getX() - l), (position.getY() - l), (position.getZ() - l), (position.getX() + l + 1), (position.getY() + l + 1), (position.getZ() + l + 1));
/*     */         
/* 638 */         if (flag && !flag1)
/*     */         {
/* 640 */           list.addAll(worldIn.getPlayers(entityClass, predicate1));
/*     */         }
/*     */         else
/*     */         {
/* 644 */           list.addAll(worldIn.getEntitiesWithinAABB(entityClass, axisalignedbb1, predicate1));
/*     */         }
/*     */       
/* 647 */       } else if (type.equals("a")) {
/*     */         
/* 649 */         list.addAll(worldIn.getPlayers(entityClass, predicate));
/*     */       }
/* 651 */       else if (!type.equals("p") && (!type.equals("r") || flag1)) {
/*     */         
/* 653 */         list.addAll(worldIn.getEntities(entityClass, predicate1));
/*     */       }
/*     */       else {
/*     */         
/* 657 */         list.addAll(worldIn.getPlayers(entityClass, predicate1));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 662 */       final AxisAlignedBB axisalignedbb = getAABB(position, i, j, k);
/*     */       
/* 664 */       if (flag && !flag1) {
/*     */         
/* 666 */         Predicate<Entity> predicate2 = new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(@Nullable Entity p_apply_1_)
/*     */             {
/* 670 */               return (p_apply_1_ != null && axisalignedbb.intersectsWith(p_apply_1_.getEntityBoundingBox()));
/*     */             }
/*     */           };
/* 673 */         list.addAll(worldIn.getPlayers(entityClass, Predicates.and(predicate1, predicate2)));
/*     */       }
/*     */       else {
/*     */         
/* 677 */         list.addAll(worldIn.getEntitiesWithinAABB(entityClass, axisalignedbb, predicate1));
/*     */       } 
/*     */     } 
/*     */     
/* 681 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Entity> List<T> getEntitiesFromPredicates(List<T> matchingEntities, Map<String, String> params, ICommandSender sender, Class<? extends T> targetClass, String type, final Vec3d pos) {
/* 686 */     int i = getInt(params, field_190845_s, (!type.equals("a") && !type.equals("e")) ? 1 : 0);
/*     */     
/* 688 */     if (!type.equals("p") && !type.equals("a") && !type.equals("e")) {
/*     */       
/* 690 */       if (type.equals("r"))
/*     */       {
/* 692 */         Collections.shuffle(matchingEntities);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 697 */       Collections.sort(matchingEntities, (Comparator)new Comparator<Entity>()
/*     */           {
/*     */             public int compare(Entity p_compare_1_, Entity p_compare_2_)
/*     */             {
/* 701 */               return ComparisonChain.start().compare(p_compare_1_.getDistanceSq(pos.xCoord, pos.yCoord, pos.zCoord), p_compare_2_.getDistanceSq(pos.xCoord, pos.yCoord, pos.zCoord)).result();
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 706 */     Entity entity = sender.getCommandSenderEntity();
/*     */     
/* 708 */     if (entity != null && targetClass.isAssignableFrom(entity.getClass()) && i == 1 && matchingEntities.contains(entity) && !"r".equals(type))
/*     */     {
/* 710 */       matchingEntities = Lists.newArrayList((Object[])new Entity[] { entity });
/*     */     }
/*     */     
/* 713 */     if (i != 0) {
/*     */       
/* 715 */       if (i < 0)
/*     */       {
/* 717 */         Collections.reverse(matchingEntities);
/*     */       }
/*     */       
/* 720 */       matchingEntities = matchingEntities.subList(0, Math.min(Math.abs(i), matchingEntities.size()));
/*     */     } 
/*     */     
/* 723 */     return matchingEntities;
/*     */   }
/*     */ 
/*     */   
/*     */   private static AxisAlignedBB getAABB(BlockPos pos, int x, int y, int z) {
/* 728 */     boolean flag = (x < 0);
/* 729 */     boolean flag1 = (y < 0);
/* 730 */     boolean flag2 = (z < 0);
/* 731 */     int i = pos.getX() + (flag ? x : 0);
/* 732 */     int j = pos.getY() + (flag1 ? y : 0);
/* 733 */     int k = pos.getZ() + (flag2 ? z : 0);
/* 734 */     int l = pos.getX() + (flag ? 0 : x) + 1;
/* 735 */     int i1 = pos.getY() + (flag1 ? 0 : y) + 1;
/* 736 */     int j1 = pos.getZ() + (flag2 ? 0 : z) + 1;
/* 737 */     return new AxisAlignedBB(i, j, k, l, i1, j1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BlockPos getBlockPosFromArguments(Map<String, String> params, BlockPos pos) {
/* 742 */     return new BlockPos(getInt(params, field_190835_i, pos.getX()), getInt(params, field_190836_j, pos.getY()), getInt(params, field_190837_k, pos.getZ()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static Vec3d getPosFromArguments(Map<String, String> params, Vec3d pos) {
/* 747 */     return new Vec3d(getCoordinate(params, field_190835_i, pos.xCoord, true), getCoordinate(params, field_190836_j, pos.yCoord, false), getCoordinate(params, field_190837_k, pos.zCoord, true));
/*     */   }
/*     */ 
/*     */   
/*     */   private static double getCoordinate(Map<String, String> params, String key, double defaultD, boolean offset) {
/* 752 */     return params.containsKey(key) ? (MathHelper.getInt(params.get(key), MathHelper.floor(defaultD)) + (offset ? 0.5D : 0.0D)) : defaultD;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasArgument(Map<String, String> params) {
/* 757 */     for (String s : WORLD_BINDING_ARGS) {
/*     */       
/* 759 */       if (params.containsKey(s))
/*     */       {
/* 761 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 765 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getInt(Map<String, String> params, String key, int defaultI) {
/* 770 */     return params.containsKey(key) ? MathHelper.getInt(params.get(key), defaultI) : defaultI;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static String getArgument(Map<String, String> params, String key) {
/* 776 */     return params.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<String, Integer> getScoreMap(Map<String, String> params) {
/* 781 */     Map<String, Integer> map = Maps.newHashMap();
/*     */     
/* 783 */     for (String s : params.keySet()) {
/*     */       
/* 785 */       if (s.startsWith("score_") && s.length() > "score_".length())
/*     */       {
/* 787 */         map.put(s.substring("score_".length()), Integer.valueOf(MathHelper.getInt(params.get(s), 1)));
/*     */       }
/*     */     } 
/*     */     
/* 791 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean matchesMultiplePlayers(String selectorStr) throws CommandException {
/* 799 */     Matcher matcher = TOKEN_PATTERN.matcher(selectorStr);
/*     */     
/* 801 */     if (!matcher.matches())
/*     */     {
/* 803 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 807 */     Map<String, String> map = getArgumentMap(matcher.group(2));
/* 808 */     String s = matcher.group(1);
/* 809 */     int i = (!"a".equals(s) && !"e".equals(s)) ? 1 : 0;
/* 810 */     return (getInt(map, field_190845_s, i) != 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasArguments(String selectorStr) {
/* 819 */     return TOKEN_PATTERN.matcher(selectorStr).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<String, String> getArgumentMap(@Nullable String argumentString) throws CommandException {
/* 824 */     Map<String, String> map = Maps.newHashMap();
/*     */     
/* 826 */     if (argumentString == null)
/*     */     {
/* 828 */       return map;
/*     */     }
/*     */ 
/*     */     
/* 832 */     for (String s : field_190828_b.split(argumentString)) {
/*     */       
/* 834 */       Iterator<String> iterator = field_190829_c.split(s).iterator();
/* 835 */       String s1 = iterator.next();
/*     */       
/* 837 */       if (!field_190851_y.apply(s1))
/*     */       {
/* 839 */         throw new CommandException("commands.generic.selector_argument", new Object[] { s });
/*     */       }
/*     */       
/* 842 */       map.put(s1, iterator.hasNext() ? iterator.next() : "");
/*     */     } 
/*     */     
/* 845 */     return map;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\EntitySelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */