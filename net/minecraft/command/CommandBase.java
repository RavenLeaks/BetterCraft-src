/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.base.Functions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.primitives.Doubles;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*     */ 
/*     */ public abstract class CommandBase
/*     */   implements ICommand {
/*     */   private static ICommandListener commandListener;
/*  41 */   private static final Splitter field_190796_b = Splitter.on(',');
/*  42 */   private static final Splitter field_190797_c = Splitter.on('=').limit(2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static SyntaxErrorException toSyntaxException(JsonParseException e) {
/*  49 */     Throwable throwable = ExceptionUtils.getRootCause((Throwable)e);
/*  50 */     String s = "";
/*     */     
/*  52 */     if (throwable != null) {
/*     */       
/*  54 */       s = throwable.getMessage();
/*     */       
/*  56 */       if (s.contains("setLenient"))
/*     */       {
/*  58 */         s = s.substring(s.indexOf("to accept ") + 10);
/*     */       }
/*     */     } 
/*     */     
/*  62 */     return new SyntaxErrorException("commands.tellraw.jsonException", new Object[] { s });
/*     */   }
/*     */ 
/*     */   
/*     */   public static NBTTagCompound entityToNBT(Entity theEntity) {
/*  67 */     NBTTagCompound nbttagcompound = theEntity.writeToNBT(new NBTTagCompound());
/*     */     
/*  69 */     if (theEntity instanceof EntityPlayer) {
/*     */       
/*  71 */       ItemStack itemstack = ((EntityPlayer)theEntity).inventory.getCurrentItem();
/*     */       
/*  73 */       if (!itemstack.func_190926_b())
/*     */       {
/*  75 */         nbttagcompound.setTag("SelectedItem", (NBTBase)itemstack.writeToNBT(new NBTTagCompound()));
/*     */       }
/*     */     } 
/*     */     
/*  79 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  87 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getCommandAliases() {
/*  92 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
/* 100 */     return sender.canCommandSenderUseCommand(getRequiredPermissionLevel(), getCommandName());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 105 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseInt(String input) throws NumberInvalidException {
/*     */     try {
/* 112 */       return Integer.parseInt(input);
/*     */     }
/* 114 */     catch (NumberFormatException var2) {
/*     */       
/* 116 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int parseInt(String input, int min) throws NumberInvalidException {
/* 122 */     return parseInt(input, min, 2147483647);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int parseInt(String input, int min, int max) throws NumberInvalidException {
/* 127 */     int i = parseInt(input);
/*     */     
/* 129 */     if (i < min)
/*     */     {
/* 131 */       throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { Integer.valueOf(i), Integer.valueOf(min) });
/*     */     }
/* 133 */     if (i > max)
/*     */     {
/* 135 */       throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { Integer.valueOf(i), Integer.valueOf(max) });
/*     */     }
/*     */ 
/*     */     
/* 139 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long parseLong(String input) throws NumberInvalidException {
/*     */     try {
/* 147 */       return Long.parseLong(input);
/*     */     }
/* 149 */     catch (NumberFormatException var2) {
/*     */       
/* 151 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static long parseLong(String input, long min, long max) throws NumberInvalidException {
/* 157 */     long i = parseLong(input);
/*     */     
/* 159 */     if (i < min)
/*     */     {
/* 161 */       throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { Long.valueOf(i), Long.valueOf(min) });
/*     */     }
/* 163 */     if (i > max)
/*     */     {
/* 165 */       throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { Long.valueOf(i), Long.valueOf(max) });
/*     */     }
/*     */ 
/*     */     
/* 169 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockPos parseBlockPos(ICommandSender sender, String[] args, int startIndex, boolean centerBlock) throws NumberInvalidException {
/* 175 */     BlockPos blockpos = sender.getPosition();
/* 176 */     return new BlockPos(parseDouble(blockpos.getX(), args[startIndex], -30000000, 30000000, centerBlock), parseDouble(blockpos.getY(), args[startIndex + 1], 0, 256, false), parseDouble(blockpos.getZ(), args[startIndex + 2], -30000000, 30000000, centerBlock));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double parseDouble(String input) throws NumberInvalidException {
/*     */     try {
/* 183 */       double d0 = Double.parseDouble(input);
/*     */       
/* 185 */       if (!Doubles.isFinite(d0))
/*     */       {
/* 187 */         throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */       }
/*     */ 
/*     */       
/* 191 */       return d0;
/*     */     
/*     */     }
/* 194 */     catch (NumberFormatException var3) {
/*     */       
/* 196 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static double parseDouble(String input, double min) throws NumberInvalidException {
/* 202 */     return parseDouble(input, min, Double.MAX_VALUE);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double parseDouble(String input, double min, double max) throws NumberInvalidException {
/* 207 */     double d0 = parseDouble(input);
/*     */     
/* 209 */     if (d0 < min)
/*     */     {
/* 211 */       throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d0) }), String.format("%.2f", new Object[] { Double.valueOf(min) }) });
/*     */     }
/* 213 */     if (d0 > max)
/*     */     {
/* 215 */       throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d0) }), String.format("%.2f", new Object[] { Double.valueOf(max) }) });
/*     */     }
/*     */ 
/*     */     
/* 219 */     return d0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean parseBoolean(String input) throws CommandException {
/* 225 */     if (!"true".equals(input) && !"1".equals(input)) {
/*     */       
/* 227 */       if (!"false".equals(input) && !"0".equals(input))
/*     */       {
/* 229 */         throw new CommandException("commands.generic.boolean.invalid", new Object[] { input });
/*     */       }
/*     */ 
/*     */       
/* 233 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 238 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayerMP getCommandSenderAsPlayer(ICommandSender sender) throws PlayerNotFoundException {
/* 247 */     if (sender instanceof EntityPlayerMP)
/*     */     {
/* 249 */       return (EntityPlayerMP)sender;
/*     */     }
/*     */ 
/*     */     
/* 253 */     throw new PlayerNotFoundException("commands.generic.player.unspecified");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<EntityPlayerMP> func_193513_a(MinecraftServer p_193513_0_, ICommandSender p_193513_1_, String p_193513_2_) throws CommandException {
/* 259 */     List<EntityPlayerMP> list = EntitySelector.func_193531_b(p_193513_1_, p_193513_2_);
/* 260 */     return list.isEmpty() ? Lists.newArrayList((Object[])new EntityPlayerMP[] { func_193512_a(p_193513_0_, null, p_193513_2_) }) : list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EntityPlayerMP getPlayer(MinecraftServer server, ICommandSender sender, String target) throws PlayerNotFoundException, CommandException {
/* 265 */     return func_193512_a(server, EntitySelector.matchOnePlayer(sender, target), target);
/*     */   }
/*     */ 
/*     */   
/*     */   private static EntityPlayerMP func_193512_a(MinecraftServer p_193512_0_, @Nullable EntityPlayerMP p_193512_1_, String p_193512_2_) throws CommandException {
/* 270 */     if (p_193512_1_ == null) {
/*     */       
/*     */       try {
/*     */         
/* 274 */         p_193512_1_ = p_193512_0_.getPlayerList().getPlayerByUUID(UUID.fromString(p_193512_2_));
/*     */       }
/* 276 */       catch (IllegalArgumentException illegalArgumentException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 282 */     if (p_193512_1_ == null)
/*     */     {
/* 284 */       p_193512_1_ = p_193512_0_.getPlayerList().getPlayerByUsername(p_193512_2_);
/*     */     }
/*     */     
/* 287 */     if (p_193512_1_ == null)
/*     */     {
/* 289 */       throw new PlayerNotFoundException("commands.generic.player.notFound", new Object[] { p_193512_2_ });
/*     */     }
/*     */ 
/*     */     
/* 293 */     return p_193512_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity getEntity(MinecraftServer server, ICommandSender sender, String target) throws EntityNotFoundException, CommandException {
/* 299 */     return getEntity(server, sender, target, Entity.class);
/*     */   }
/*     */   
/*     */   public static <T extends Entity> T getEntity(MinecraftServer server, ICommandSender sender, String target, Class<? extends T> targetClass) throws EntityNotFoundException, CommandException {
/*     */     EntityPlayerMP entityPlayerMP;
/* 304 */     Entity entity = EntitySelector.matchOneEntity(sender, target, targetClass);
/*     */     
/* 306 */     if (entity == null)
/*     */     {
/* 308 */       entityPlayerMP = server.getPlayerList().getPlayerByUsername(target);
/*     */     }
/*     */     
/* 311 */     if (entityPlayerMP == null) {
/*     */       
/*     */       try {
/*     */         
/* 315 */         UUID uuid = UUID.fromString(target);
/* 316 */         Entity entity1 = server.getEntityFromUuid(uuid);
/*     */         
/* 318 */         if (entity1 == null)
/*     */         {
/* 320 */           entityPlayerMP = server.getPlayerList().getPlayerByUUID(uuid);
/*     */         }
/*     */       }
/* 323 */       catch (IllegalArgumentException var6) {
/*     */         
/* 325 */         if ((target.split("-")).length == 5)
/*     */         {
/* 327 */           throw new EntityNotFoundException("commands.generic.entity.invalidUuid", new Object[] { target });
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 332 */     if (entityPlayerMP != null && targetClass.isAssignableFrom(entityPlayerMP.getClass()))
/*     */     {
/* 334 */       return (T)entityPlayerMP;
/*     */     }
/*     */ 
/*     */     
/* 338 */     throw new EntityNotFoundException(target);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Entity> getEntityList(MinecraftServer server, ICommandSender sender, String target) throws EntityNotFoundException, CommandException {
/* 344 */     return EntitySelector.hasArguments(target) ? EntitySelector.<Entity>matchEntities(sender, target, Entity.class) : Lists.newArrayList((Object[])new Entity[] { getEntity(server, sender, target) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPlayerName(MinecraftServer server, ICommandSender sender, String target) throws PlayerNotFoundException, CommandException {
/*     */     try {
/* 351 */       return getPlayer(server, sender, target).getName();
/*     */     }
/* 353 */     catch (CommandException commandexception) {
/*     */       
/* 355 */       if (EntitySelector.hasArguments(target))
/*     */       {
/* 357 */         throw commandexception;
/*     */       }
/*     */ 
/*     */       
/* 361 */       return target;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getEntityName(MinecraftServer server, ICommandSender sender, String target) throws EntityNotFoundException, CommandException {
/*     */     try {
/* 370 */       return getPlayer(server, sender, target).getName();
/*     */     }
/* 372 */     catch (PlayerNotFoundException var6) {
/*     */ 
/*     */       
/*     */       try {
/* 376 */         return getEntity(server, sender, target).getCachedUniqueIdString();
/*     */       }
/* 378 */       catch (EntityNotFoundException entitynotfoundexception) {
/*     */         
/* 380 */         if (EntitySelector.hasArguments(target))
/*     */         {
/* 382 */           throw entitynotfoundexception;
/*     */         }
/*     */ 
/*     */         
/* 386 */         return target;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ITextComponent getChatComponentFromNthArg(ICommandSender sender, String[] args, int index) throws CommandException, PlayerNotFoundException {
/* 394 */     return getChatComponentFromNthArg(sender, args, index, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ITextComponent getChatComponentFromNthArg(ICommandSender sender, String[] args, int index, boolean p_147176_3_) throws PlayerNotFoundException, CommandException {
/* 399 */     TextComponentString textComponentString = new TextComponentString("");
/*     */     
/* 401 */     for (int i = index; i < args.length; i++) {
/*     */       ITextComponent iTextComponent;
/* 403 */       if (i > index)
/*     */       {
/* 405 */         textComponentString.appendText(" ");
/*     */       }
/*     */       
/* 408 */       TextComponentString textComponentString1 = new TextComponentString(args[i]);
/*     */       
/* 410 */       if (p_147176_3_) {
/*     */         
/* 412 */         ITextComponent itextcomponent2 = EntitySelector.matchEntitiesToTextComponent(sender, args[i]);
/*     */         
/* 414 */         if (itextcomponent2 == null) {
/*     */           
/* 416 */           if (EntitySelector.hasArguments(args[i]))
/*     */           {
/* 418 */             throw new PlayerNotFoundException("commands.generic.selector.notFound", new Object[] { args[i] });
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 423 */           iTextComponent = itextcomponent2;
/*     */         } 
/*     */       } 
/*     */       
/* 427 */       textComponentString.appendSibling(iTextComponent);
/*     */     } 
/*     */     
/* 430 */     return (ITextComponent)textComponentString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String buildString(String[] args, int startPos) {
/* 438 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 440 */     for (int i = startPos; i < args.length; i++) {
/*     */       
/* 442 */       if (i > startPos)
/*     */       {
/* 444 */         stringbuilder.append(" ");
/*     */       }
/*     */       
/* 447 */       String s = args[i];
/* 448 */       stringbuilder.append(s);
/*     */     } 
/*     */     
/* 451 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static CoordinateArg parseCoordinate(double base, String selectorArg, boolean centerBlock) throws NumberInvalidException {
/* 456 */     return parseCoordinate(base, selectorArg, -30000000, 30000000, centerBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public static CoordinateArg parseCoordinate(double base, String selectorArg, int min, int max, boolean centerBlock) throws NumberInvalidException {
/* 461 */     boolean flag = selectorArg.startsWith("~");
/*     */     
/* 463 */     if (flag && Double.isNaN(base))
/*     */     {
/* 465 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { Double.valueOf(base) });
/*     */     }
/*     */ 
/*     */     
/* 469 */     double d0 = 0.0D;
/*     */     
/* 471 */     if (!flag || selectorArg.length() > 1) {
/*     */       
/* 473 */       boolean flag1 = selectorArg.contains(".");
/*     */       
/* 475 */       if (flag)
/*     */       {
/* 477 */         selectorArg = selectorArg.substring(1);
/*     */       }
/*     */       
/* 480 */       d0 += parseDouble(selectorArg);
/*     */       
/* 482 */       if (!flag1 && !flag && centerBlock)
/*     */       {
/* 484 */         d0 += 0.5D;
/*     */       }
/*     */     } 
/*     */     
/* 488 */     double d1 = d0 + (flag ? base : 0.0D);
/*     */     
/* 490 */     if (min != 0 || max != 0) {
/*     */       
/* 492 */       if (d1 < min)
/*     */       {
/* 494 */         throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d1) }), Integer.valueOf(min) });
/*     */       }
/*     */       
/* 497 */       if (d1 > max)
/*     */       {
/* 499 */         throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d1) }), Integer.valueOf(max) });
/*     */       }
/*     */     } 
/*     */     
/* 503 */     return new CoordinateArg(d1, d0, flag);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double parseDouble(double base, String input, boolean centerBlock) throws NumberInvalidException {
/* 509 */     return parseDouble(base, input, -30000000, 30000000, centerBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double parseDouble(double base, String input, int min, int max, boolean centerBlock) throws NumberInvalidException {
/* 514 */     boolean flag = input.startsWith("~");
/*     */     
/* 516 */     if (flag && Double.isNaN(base))
/*     */     {
/* 518 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { Double.valueOf(base) });
/*     */     }
/*     */ 
/*     */     
/* 522 */     double d0 = flag ? base : 0.0D;
/*     */     
/* 524 */     if (!flag || input.length() > 1) {
/*     */       
/* 526 */       boolean flag1 = input.contains(".");
/*     */       
/* 528 */       if (flag)
/*     */       {
/* 530 */         input = input.substring(1);
/*     */       }
/*     */       
/* 533 */       d0 += parseDouble(input);
/*     */       
/* 535 */       if (!flag1 && !flag && centerBlock)
/*     */       {
/* 537 */         d0 += 0.5D;
/*     */       }
/*     */     } 
/*     */     
/* 541 */     if (min != 0 || max != 0) {
/*     */       
/* 543 */       if (d0 < min)
/*     */       {
/* 545 */         throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d0) }), Integer.valueOf(min) });
/*     */       }
/*     */       
/* 548 */       if (d0 > max)
/*     */       {
/* 550 */         throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d0) }), Integer.valueOf(max) });
/*     */       }
/*     */     } 
/*     */     
/* 554 */     return d0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Item getItemByText(ICommandSender sender, String id) throws NumberInvalidException {
/* 565 */     ResourceLocation resourcelocation = new ResourceLocation(id);
/* 566 */     Item item = (Item)Item.REGISTRY.getObject(resourcelocation);
/*     */     
/* 568 */     if (item == null)
/*     */     {
/* 570 */       throw new NumberInvalidException("commands.give.item.notFound", new Object[] { resourcelocation });
/*     */     }
/*     */ 
/*     */     
/* 574 */     return item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Block getBlockByText(ICommandSender sender, String id) throws NumberInvalidException {
/* 585 */     ResourceLocation resourcelocation = new ResourceLocation(id);
/*     */     
/* 587 */     if (!Block.REGISTRY.containsKey(resourcelocation))
/*     */     {
/* 589 */       throw new NumberInvalidException("commands.give.block.notFound", new Object[] { resourcelocation });
/*     */     }
/*     */ 
/*     */     
/* 593 */     return (Block)Block.REGISTRY.getObject(resourcelocation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IBlockState func_190794_a(Block p_190794_0_, String p_190794_1_) throws NumberInvalidException, InvalidBlockStateException {
/*     */     try {
/* 601 */       int i = Integer.parseInt(p_190794_1_);
/*     */       
/* 603 */       if (i < 0)
/*     */       {
/* 605 */         throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { Integer.valueOf(i), Integer.valueOf(0) });
/*     */       }
/* 607 */       if (i > 15)
/*     */       {
/* 609 */         throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { Integer.valueOf(i), Integer.valueOf(15) });
/*     */       }
/*     */ 
/*     */       
/* 613 */       return p_190794_0_.getStateFromMeta(Integer.parseInt(p_190794_1_));
/*     */     
/*     */     }
/* 616 */     catch (RuntimeException var7) {
/*     */ 
/*     */       
/*     */       try {
/* 620 */         Map<IProperty<?>, Comparable<?>> map = func_190795_c(p_190794_0_, p_190794_1_);
/* 621 */         IBlockState iblockstate = p_190794_0_.getDefaultState();
/*     */         
/* 623 */         for (Map.Entry<IProperty<?>, Comparable<?>> entry : map.entrySet())
/*     */         {
/* 625 */           iblockstate = func_190793_a(iblockstate, (IProperty<Comparable>)entry.getKey(), entry.getValue());
/*     */         }
/*     */         
/* 628 */         return iblockstate;
/*     */       }
/* 630 */       catch (RuntimeException var6) {
/*     */         
/* 632 */         throw new InvalidBlockStateException("commands.generic.blockstate.invalid", new Object[] { p_190794_1_, Block.REGISTRY.getNameForObject(p_190794_0_) });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Comparable<T>> IBlockState func_190793_a(IBlockState p_190793_0_, IProperty<T> p_190793_1_, Comparable<?> p_190793_2_) {
/* 639 */     return p_190793_0_.withProperty(p_190793_1_, p_190793_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Predicate<IBlockState> func_190791_b(final Block p_190791_0_, String p_190791_1_) throws InvalidBlockStateException {
/* 644 */     if (!"*".equals(p_190791_1_) && !"-1".equals(p_190791_1_)) {
/*     */       
/*     */       try {
/*     */         
/* 648 */         final int i = Integer.parseInt(p_190791_1_);
/* 649 */         return new Predicate<IBlockState>()
/*     */           {
/*     */             public boolean apply(@Nullable IBlockState p_apply_1_)
/*     */             {
/* 653 */               return (i == p_apply_1_.getBlock().getMetaFromState(p_apply_1_));
/*     */             }
/*     */           };
/*     */       }
/* 657 */       catch (RuntimeException var3) {
/*     */         
/* 659 */         final Map<IProperty<?>, Comparable<?>> map = func_190795_c(p_190791_0_, p_190791_1_);
/* 660 */         return new Predicate<IBlockState>()
/*     */           {
/*     */             public boolean apply(@Nullable IBlockState p_apply_1_)
/*     */             {
/* 664 */               if (p_apply_1_ != null && p_190791_0_ == p_apply_1_.getBlock()) {
/*     */                 
/* 666 */                 for (Map.Entry<IProperty<?>, Comparable<?>> entry : (Iterable<Map.Entry<IProperty<?>, Comparable<?>>>)map.entrySet()) {
/*     */                   
/* 668 */                   if (!p_apply_1_.getValue(entry.getKey()).equals(entry.getValue()))
/*     */                   {
/* 670 */                     return false;
/*     */                   }
/*     */                 } 
/*     */                 
/* 674 */                 return true;
/*     */               } 
/*     */ 
/*     */               
/* 678 */               return false;
/*     */             }
/*     */           };
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 686 */     return Predicates.alwaysTrue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<IProperty<?>, Comparable<?>> func_190795_c(Block p_190795_0_, String p_190795_1_) throws InvalidBlockStateException {
/* 692 */     Map<IProperty<?>, Comparable<?>> map = Maps.newHashMap();
/*     */     
/* 694 */     if ("default".equals(p_190795_1_))
/*     */     {
/* 696 */       return (Map<IProperty<?>, Comparable<?>>)p_190795_0_.getDefaultState().getProperties();
/*     */     }
/*     */ 
/*     */     
/* 700 */     BlockStateContainer blockstatecontainer = p_190795_0_.getBlockState();
/* 701 */     Iterator<String> iterator = field_190796_b.split(p_190795_1_).iterator();
/*     */ 
/*     */     
/*     */     while (true) {
/* 705 */       if (!iterator.hasNext())
/*     */       {
/* 707 */         return map;
/*     */       }
/*     */       
/* 710 */       String s = iterator.next();
/* 711 */       Iterator<String> iterator1 = field_190797_c.split(s).iterator();
/*     */       
/* 713 */       if (!iterator1.hasNext()) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 718 */       IProperty<?> iproperty = blockstatecontainer.getProperty(iterator1.next());
/*     */       
/* 720 */       if (iproperty == null || !iterator1.hasNext()) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 725 */       Comparable<?> comparable = (Comparable<?>)func_190792_a(iproperty, iterator1.next());
/*     */       
/* 727 */       if (comparable == null) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 732 */       map.put(iproperty, comparable);
/*     */     } 
/*     */     
/* 735 */     throw new InvalidBlockStateException("commands.generic.blockstate.invalid", new Object[] { p_190795_1_, Block.REGISTRY.getNameForObject(p_190795_0_) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static <T extends Comparable<T>> T func_190792_a(IProperty<T> p_190792_0_, String p_190792_1_) {
/* 742 */     return (T)p_190792_0_.parseValue(p_190792_1_).orNull();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String joinNiceString(Object[] elements) {
/* 751 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 753 */     for (int i = 0; i < elements.length; i++) {
/*     */       
/* 755 */       String s = elements[i].toString();
/*     */       
/* 757 */       if (i > 0)
/*     */       {
/* 759 */         if (i == elements.length - 1) {
/*     */           
/* 761 */           stringbuilder.append(" and ");
/*     */         }
/*     */         else {
/*     */           
/* 765 */           stringbuilder.append(", ");
/*     */         } 
/*     */       }
/*     */       
/* 769 */       stringbuilder.append(s);
/*     */     } 
/*     */     
/* 772 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ITextComponent join(List<ITextComponent> components) {
/* 777 */     TextComponentString textComponentString = new TextComponentString("");
/*     */     
/* 779 */     for (int i = 0; i < components.size(); i++) {
/*     */       
/* 781 */       if (i > 0)
/*     */       {
/* 783 */         if (i == components.size() - 1) {
/*     */           
/* 785 */           textComponentString.appendText(" and ");
/*     */         }
/* 787 */         else if (i > 0) {
/*     */           
/* 789 */           textComponentString.appendText(", ");
/*     */         } 
/*     */       }
/*     */       
/* 793 */       textComponentString.appendSibling(components.get(i));
/*     */     } 
/*     */     
/* 796 */     return (ITextComponent)textComponentString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String joinNiceStringFromCollection(Collection<String> strings) {
/* 806 */     return joinNiceString(strings.toArray((Object[])new String[strings.size()]));
/*     */   }
/*     */   
/*     */   public static List<String> getTabCompletionCoordinate(String[] inputArgs, int index, @Nullable BlockPos pos) {
/*     */     String s;
/* 811 */     if (pos == null)
/*     */     {
/* 813 */       return Lists.newArrayList((Object[])new String[] { "~" });
/*     */     }
/*     */ 
/*     */     
/* 817 */     int i = inputArgs.length - 1;
/*     */ 
/*     */     
/* 820 */     if (i == index) {
/*     */       
/* 822 */       s = Integer.toString(pos.getX());
/*     */     }
/* 824 */     else if (i == index + 1) {
/*     */       
/* 826 */       s = Integer.toString(pos.getY());
/*     */     }
/*     */     else {
/*     */       
/* 830 */       if (i != index + 2)
/*     */       {
/* 832 */         return Collections.emptyList();
/*     */       }
/*     */       
/* 835 */       s = Integer.toString(pos.getZ());
/*     */     } 
/*     */     
/* 838 */     return Lists.newArrayList((Object[])new String[] { s });
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<String> getTabCompletionCoordinateXZ(String[] inputArgs, int index, @Nullable BlockPos lookedPos) {
/*     */     String s;
/* 844 */     if (lookedPos == null)
/*     */     {
/* 846 */       return Lists.newArrayList((Object[])new String[] { "~" });
/*     */     }
/*     */ 
/*     */     
/* 850 */     int i = inputArgs.length - 1;
/*     */ 
/*     */     
/* 853 */     if (i == index) {
/*     */       
/* 855 */       s = Integer.toString(lookedPos.getX());
/*     */     }
/*     */     else {
/*     */       
/* 859 */       if (i != index + 1)
/*     */       {
/* 861 */         return Collections.emptyList();
/*     */       }
/*     */       
/* 864 */       s = Integer.toString(lookedPos.getZ());
/*     */     } 
/*     */     
/* 867 */     return Lists.newArrayList((Object[])new String[] { s });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean doesStringStartWith(String original, String region) {
/* 876 */     return region.regionMatches(true, 0, original, 0, original.length());
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<String> getListOfStringsMatchingLastWord(String[] args, String... possibilities) {
/* 881 */     return getListOfStringsMatchingLastWord(args, Arrays.asList((Object[])possibilities));
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<String> getListOfStringsMatchingLastWord(String[] inputArgs, Collection<?> possibleCompletions) {
/* 886 */     String s = inputArgs[inputArgs.length - 1];
/* 887 */     List<String> list = Lists.newArrayList();
/*     */     
/* 889 */     if (!possibleCompletions.isEmpty()) {
/*     */       
/* 891 */       for (String s1 : Iterables.transform(possibleCompletions, Functions.toStringFunction())) {
/*     */         
/* 893 */         if (doesStringStartWith(s, s1))
/*     */         {
/* 895 */           list.add(s1);
/*     */         }
/*     */       } 
/*     */       
/* 899 */       if (list.isEmpty())
/*     */       {
/* 901 */         for (Object object : possibleCompletions) {
/*     */           
/* 903 */           if (object instanceof ResourceLocation && doesStringStartWith(s, ((ResourceLocation)object).getResourcePath()))
/*     */           {
/* 905 */             list.add(String.valueOf(object));
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 911 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 919 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void notifyCommandListener(ICommandSender sender, ICommand command, String translationKey, Object... translationArgs) {
/* 924 */     notifyCommandListener(sender, command, 0, translationKey, translationArgs);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void notifyCommandListener(ICommandSender sender, ICommand command, int flags, String translationKey, Object... translationArgs) {
/* 929 */     if (commandListener != null)
/*     */     {
/* 931 */       commandListener.notifyListener(sender, command, flags, translationKey, translationArgs);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setCommandListener(ICommandListener listener) {
/* 940 */     commandListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ICommand p_compareTo_1_) {
/* 945 */     return getCommandName().compareTo(p_compareTo_1_.getCommandName());
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CoordinateArg
/*     */   {
/*     */     private final double result;
/*     */     private final double amount;
/*     */     private final boolean isRelative;
/*     */     
/*     */     protected CoordinateArg(double resultIn, double amountIn, boolean relative) {
/* 956 */       this.result = resultIn;
/* 957 */       this.amount = amountIn;
/* 958 */       this.isRelative = relative;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getResult() {
/* 963 */       return this.result;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getAmount() {
/* 968 */       return this.amount;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isRelative() {
/* 973 */       return this.isRelative;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */