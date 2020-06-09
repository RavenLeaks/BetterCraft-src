/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandSpreadPlayers
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  29 */     return "spreadplayers";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  37 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  45 */     return "commands.spreadplayers.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  53 */     if (args.length < 6)
/*     */     {
/*  55 */       throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  59 */     int i = 0;
/*  60 */     BlockPos blockpos = sender.getPosition();
/*  61 */     double d0 = parseDouble(blockpos.getX(), args[i++], true);
/*  62 */     double d1 = parseDouble(blockpos.getZ(), args[i++], true);
/*  63 */     double d2 = parseDouble(args[i++], 0.0D);
/*  64 */     double d3 = parseDouble(args[i++], d2 + 1.0D);
/*  65 */     boolean flag = parseBoolean(args[i++]);
/*  66 */     List<Entity> list = Lists.newArrayList();
/*     */     
/*  68 */     while (i < args.length) {
/*     */       
/*  70 */       String s = args[i++];
/*     */       
/*  72 */       if (EntitySelector.hasArguments(s)) {
/*     */         
/*  74 */         List<Entity> list1 = EntitySelector.matchEntities(sender, s, Entity.class);
/*     */         
/*  76 */         if (list1.isEmpty())
/*     */         {
/*  78 */           throw new EntityNotFoundException("commands.generic.selector.notFound", new Object[] { s });
/*     */         }
/*     */         
/*  81 */         list.addAll(list1);
/*     */         
/*     */         continue;
/*     */       } 
/*  85 */       EntityPlayerMP entityPlayerMP = server.getPlayerList().getPlayerByUsername(s);
/*     */       
/*  87 */       if (entityPlayerMP == null)
/*     */       {
/*  89 */         throw new PlayerNotFoundException("commands.generic.player.notFound", new Object[] { s });
/*     */       }
/*     */       
/*  92 */       list.add(entityPlayerMP);
/*     */     } 
/*     */ 
/*     */     
/*  96 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
/*     */     
/*  98 */     if (list.isEmpty())
/*     */     {
/* 100 */       throw new EntityNotFoundException("commands.spreadplayers.noop");
/*     */     }
/*     */ 
/*     */     
/* 104 */     sender.addChatMessage((ITextComponent)new TextComponentTranslation("commands.spreadplayers.spreading." + (flag ? "teams" : "players"), new Object[] { Integer.valueOf(list.size()), Double.valueOf(d3), Double.valueOf(d0), Double.valueOf(d1), Double.valueOf(d2) }));
/* 105 */     spread(sender, list, new Position(d0, d1), d2, d3, ((Entity)list.get(0)).world, flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void spread(ICommandSender sender, List<Entity> p_110669_2_, Position pos, double spreadDistance, double maxRange, World worldIn, boolean respectTeams) throws CommandException {
/* 112 */     Random random = new Random();
/* 113 */     double d0 = pos.x - maxRange;
/* 114 */     double d1 = pos.z - maxRange;
/* 115 */     double d2 = pos.x + maxRange;
/* 116 */     double d3 = pos.z + maxRange;
/* 117 */     Position[] acommandspreadplayers$position = createInitialPositions(random, respectTeams ? getNumberOfTeams(p_110669_2_) : p_110669_2_.size(), d0, d1, d2, d3);
/* 118 */     int i = spreadPositions(pos, spreadDistance, worldIn, random, d0, d1, d2, d3, acommandspreadplayers$position, respectTeams);
/* 119 */     double d4 = setPlayerPositions(p_110669_2_, worldIn, acommandspreadplayers$position, respectTeams);
/* 120 */     notifyCommandListener(sender, this, "commands.spreadplayers.success." + (respectTeams ? "teams" : "players"), new Object[] { Integer.valueOf(acommandspreadplayers$position.length), Double.valueOf(pos.x), Double.valueOf(pos.z) });
/*     */     
/* 122 */     if (acommandspreadplayers$position.length > 1)
/*     */     {
/* 124 */       sender.addChatMessage((ITextComponent)new TextComponentTranslation("commands.spreadplayers.info." + (respectTeams ? "teams" : "players"), new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d4) }), Integer.valueOf(i) }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private int getNumberOfTeams(List<Entity> p_110667_1_) {
/* 130 */     Set<Team> set = Sets.newHashSet();
/*     */     
/* 132 */     for (Entity entity : p_110667_1_) {
/*     */       
/* 134 */       if (entity instanceof net.minecraft.entity.player.EntityPlayer) {
/*     */         
/* 136 */         set.add(entity.getTeam());
/*     */         
/*     */         continue;
/*     */       } 
/* 140 */       set.add(null);
/*     */     } 
/*     */ 
/*     */     
/* 144 */     return set.size();
/*     */   }
/*     */ 
/*     */   
/*     */   private int spreadPositions(Position p_110668_1_, double p_110668_2_, World worldIn, Random random, double minX, double minZ, double maxX, double maxZ, Position[] p_110668_14_, boolean respectTeams) throws CommandException {
/* 149 */     boolean flag = true;
/* 150 */     double d0 = 3.4028234663852886E38D;
/*     */     
/*     */     int i;
/* 153 */     for (i = 0; i < 10000 && flag; i++) {
/*     */       
/* 155 */       flag = false;
/* 156 */       d0 = 3.4028234663852886E38D;
/*     */       
/* 158 */       for (int j = 0; j < p_110668_14_.length; j++) {
/*     */         
/* 160 */         Position commandspreadplayers$position = p_110668_14_[j];
/* 161 */         int k = 0;
/* 162 */         Position commandspreadplayers$position1 = new Position();
/*     */         
/* 164 */         for (int l = 0; l < p_110668_14_.length; l++) {
/*     */           
/* 166 */           if (j != l) {
/*     */             
/* 168 */             Position commandspreadplayers$position2 = p_110668_14_[l];
/* 169 */             double d1 = commandspreadplayers$position.dist(commandspreadplayers$position2);
/* 170 */             d0 = Math.min(d1, d0);
/*     */             
/* 172 */             if (d1 < p_110668_2_) {
/*     */               
/* 174 */               k++;
/* 175 */               commandspreadplayers$position1.x += commandspreadplayers$position2.x - commandspreadplayers$position.x;
/* 176 */               commandspreadplayers$position1.z += commandspreadplayers$position2.z - commandspreadplayers$position.z;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 181 */         if (k > 0) {
/*     */           
/* 183 */           commandspreadplayers$position1.x /= k;
/* 184 */           commandspreadplayers$position1.z /= k;
/* 185 */           double d2 = commandspreadplayers$position1.getLength();
/*     */           
/* 187 */           if (d2 > 0.0D) {
/*     */             
/* 189 */             commandspreadplayers$position1.normalize();
/* 190 */             commandspreadplayers$position.moveAway(commandspreadplayers$position1);
/*     */           }
/*     */           else {
/*     */             
/* 194 */             commandspreadplayers$position.randomize(random, minX, minZ, maxX, maxZ);
/*     */           } 
/*     */           
/* 197 */           flag = true;
/*     */         } 
/*     */         
/* 200 */         if (commandspreadplayers$position.clamp(minX, minZ, maxX, maxZ))
/*     */         {
/* 202 */           flag = true;
/*     */         }
/*     */       } 
/*     */       
/* 206 */       if (!flag) {
/*     */         byte b; int k; Position[] arrayOfPosition;
/* 208 */         for (k = (arrayOfPosition = p_110668_14_).length, b = 0; b < k; ) { Position commandspreadplayers$position3 = arrayOfPosition[b];
/*     */           
/* 210 */           if (!commandspreadplayers$position3.isSafe(worldIn)) {
/*     */             
/* 212 */             commandspreadplayers$position3.randomize(random, minX, minZ, maxX, maxZ);
/* 213 */             flag = true;
/*     */           } 
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 219 */     if (i >= 10000)
/*     */     {
/* 221 */       throw new CommandException("commands.spreadplayers.failure." + (respectTeams ? "teams" : "players"), new Object[] { Integer.valueOf(p_110668_14_.length), Double.valueOf(p_110668_1_.x), Double.valueOf(p_110668_1_.z), String.format("%.2f", new Object[] { Double.valueOf(d0) }) });
/*     */     }
/*     */ 
/*     */     
/* 225 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double setPlayerPositions(List<Entity> p_110671_1_, World worldIn, Position[] p_110671_3_, boolean p_110671_4_) {
/* 231 */     double d0 = 0.0D;
/* 232 */     int i = 0;
/* 233 */     Map<Team, Position> map = Maps.newHashMap();
/*     */     
/* 235 */     for (int j = 0; j < p_110671_1_.size(); j++) {
/*     */       Position commandspreadplayers$position;
/* 237 */       Entity entity = p_110671_1_.get(j);
/*     */ 
/*     */       
/* 240 */       if (p_110671_4_) {
/*     */         
/* 242 */         Team team = (entity instanceof net.minecraft.entity.player.EntityPlayer) ? entity.getTeam() : null;
/*     */         
/* 244 */         if (!map.containsKey(team))
/*     */         {
/* 246 */           map.put(team, p_110671_3_[i++]);
/*     */         }
/*     */         
/* 249 */         commandspreadplayers$position = map.get(team);
/*     */       }
/*     */       else {
/*     */         
/* 253 */         commandspreadplayers$position = p_110671_3_[i++];
/*     */       } 
/*     */       
/* 256 */       entity.setPositionAndUpdate((MathHelper.floor(commandspreadplayers$position.x) + 0.5F), commandspreadplayers$position.getSpawnY(worldIn), MathHelper.floor(commandspreadplayers$position.z) + 0.5D);
/* 257 */       double d2 = Double.MAX_VALUE; byte b; int k;
/*     */       Position[] arrayOfPosition;
/* 259 */       for (k = (arrayOfPosition = p_110671_3_).length, b = 0; b < k; ) { Position commandspreadplayers$position1 = arrayOfPosition[b];
/*     */         
/* 261 */         if (commandspreadplayers$position != commandspreadplayers$position1) {
/*     */           
/* 263 */           double d1 = commandspreadplayers$position.dist(commandspreadplayers$position1);
/* 264 */           d2 = Math.min(d1, d2);
/*     */         } 
/*     */         b++; }
/*     */       
/* 268 */       d0 += d2;
/*     */     } 
/*     */     
/* 271 */     d0 /= p_110671_1_.size();
/* 272 */     return d0;
/*     */   }
/*     */ 
/*     */   
/*     */   private Position[] createInitialPositions(Random p_110670_1_, int p_110670_2_, double p_110670_3_, double p_110670_5_, double p_110670_7_, double p_110670_9_) {
/* 277 */     Position[] acommandspreadplayers$position = new Position[p_110670_2_];
/*     */     
/* 279 */     for (int i = 0; i < acommandspreadplayers$position.length; i++) {
/*     */       
/* 281 */       Position commandspreadplayers$position = new Position();
/* 282 */       commandspreadplayers$position.randomize(p_110670_1_, p_110670_3_, p_110670_5_, p_110670_7_, p_110670_9_);
/* 283 */       acommandspreadplayers$position[i] = commandspreadplayers$position;
/*     */     } 
/*     */     
/* 286 */     return acommandspreadplayers$position;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 291 */     return (args.length >= 1 && args.length <= 2) ? getTabCompletionCoordinateXZ(args, 0, pos) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   static class Position
/*     */   {
/*     */     double x;
/*     */     
/*     */     double z;
/*     */ 
/*     */     
/*     */     Position() {}
/*     */     
/*     */     Position(double xIn, double zIn) {
/* 305 */       this.x = xIn;
/* 306 */       this.z = zIn;
/*     */     }
/*     */ 
/*     */     
/*     */     double dist(Position pos) {
/* 311 */       double d0 = this.x - pos.x;
/* 312 */       double d1 = this.z - pos.z;
/* 313 */       return Math.sqrt(d0 * d0 + d1 * d1);
/*     */     }
/*     */ 
/*     */     
/*     */     void normalize() {
/* 318 */       double d0 = getLength();
/* 319 */       this.x /= d0;
/* 320 */       this.z /= d0;
/*     */     }
/*     */ 
/*     */     
/*     */     float getLength() {
/* 325 */       return MathHelper.sqrt(this.x * this.x + this.z * this.z);
/*     */     }
/*     */ 
/*     */     
/*     */     public void moveAway(Position pos) {
/* 330 */       this.x -= pos.x;
/* 331 */       this.z -= pos.z;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean clamp(double p_111093_1_, double p_111093_3_, double p_111093_5_, double p_111093_7_) {
/* 336 */       boolean flag = false;
/*     */       
/* 338 */       if (this.x < p_111093_1_) {
/*     */         
/* 340 */         this.x = p_111093_1_;
/* 341 */         flag = true;
/*     */       }
/* 343 */       else if (this.x > p_111093_5_) {
/*     */         
/* 345 */         this.x = p_111093_5_;
/* 346 */         flag = true;
/*     */       } 
/*     */       
/* 349 */       if (this.z < p_111093_3_) {
/*     */         
/* 351 */         this.z = p_111093_3_;
/* 352 */         flag = true;
/*     */       }
/* 354 */       else if (this.z > p_111093_7_) {
/*     */         
/* 356 */         this.z = p_111093_7_;
/* 357 */         flag = true;
/*     */       } 
/*     */       
/* 360 */       return flag;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSpawnY(World worldIn) {
/* 365 */       BlockPos blockpos = new BlockPos(this.x, 256.0D, this.z);
/*     */       
/* 367 */       while (blockpos.getY() > 0) {
/*     */         
/* 369 */         blockpos = blockpos.down();
/*     */         
/* 371 */         if (worldIn.getBlockState(blockpos).getMaterial() != Material.AIR)
/*     */         {
/* 373 */           return blockpos.getY() + 1;
/*     */         }
/*     */       } 
/*     */       
/* 377 */       return 257;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSafe(World worldIn) {
/* 382 */       BlockPos blockpos = new BlockPos(this.x, 256.0D, this.z);
/*     */       
/* 384 */       while (blockpos.getY() > 0) {
/*     */         
/* 386 */         blockpos = blockpos.down();
/* 387 */         Material material = worldIn.getBlockState(blockpos).getMaterial();
/*     */         
/* 389 */         if (material != Material.AIR)
/*     */         {
/* 391 */           return (!material.isLiquid() && material != Material.FIRE);
/*     */         }
/*     */       } 
/*     */       
/* 395 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void randomize(Random rand, double p_111097_2_, double p_111097_4_, double p_111097_6_, double p_111097_8_) {
/* 400 */       this.x = MathHelper.nextDouble(rand, p_111097_2_, p_111097_6_);
/* 401 */       this.z = MathHelper.nextDouble(rand, p_111097_4_, p_111097_8_);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandSpreadPlayers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */