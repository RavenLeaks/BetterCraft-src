/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandTP
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  23 */     return "tp";
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
/*  39 */     return "commands.tp.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*     */     Entity entity;
/*  47 */     if (args.length < 1)
/*     */     {
/*  49 */       throw new WrongUsageException("commands.tp.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  53 */     int i = 0;
/*     */ 
/*     */     
/*  56 */     if (args.length != 2 && args.length != 4 && args.length != 6) {
/*     */       
/*  58 */       EntityPlayerMP entityPlayerMP = getCommandSenderAsPlayer(sender);
/*     */     }
/*     */     else {
/*     */       
/*  62 */       entity = getEntity(server, sender, args[0]);
/*  63 */       i = 1;
/*     */     } 
/*     */     
/*  66 */     if (args.length != 1 && args.length != 2) {
/*     */       
/*  68 */       if (args.length < i + 3)
/*     */       {
/*  70 */         throw new WrongUsageException("commands.tp.usage", new Object[0]);
/*     */       }
/*  72 */       if (entity.world != null)
/*     */       {
/*  74 */         int j = 4096;
/*  75 */         int k = i + 1;
/*  76 */         CommandBase.CoordinateArg commandbase$coordinatearg = parseCoordinate(entity.posX, args[i], true);
/*  77 */         CommandBase.CoordinateArg commandbase$coordinatearg1 = parseCoordinate(entity.posY, args[k++], -4096, 4096, false);
/*  78 */         CommandBase.CoordinateArg commandbase$coordinatearg2 = parseCoordinate(entity.posZ, args[k++], true);
/*  79 */         CommandBase.CoordinateArg commandbase$coordinatearg3 = parseCoordinate(entity.rotationYaw, (args.length > k) ? args[k++] : "~", false);
/*  80 */         CommandBase.CoordinateArg commandbase$coordinatearg4 = parseCoordinate(entity.rotationPitch, (args.length > k) ? args[k] : "~", false);
/*  81 */         teleportEntityToCoordinates(entity, commandbase$coordinatearg, commandbase$coordinatearg1, commandbase$coordinatearg2, commandbase$coordinatearg3, commandbase$coordinatearg4);
/*  82 */         notifyCommandListener(sender, this, "commands.tp.success.coordinates", new Object[] { entity.getName(), Double.valueOf(commandbase$coordinatearg.getResult()), Double.valueOf(commandbase$coordinatearg1.getResult()), Double.valueOf(commandbase$coordinatearg2.getResult()) });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  87 */       Entity entity1 = getEntity(server, sender, args[args.length - 1]);
/*     */       
/*  89 */       if (entity1.world != entity.world)
/*     */       {
/*  91 */         throw new CommandException("commands.tp.notSameDimension", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/*  95 */       entity.dismountRidingEntity();
/*     */       
/*  97 */       if (entity instanceof EntityPlayerMP) {
/*     */         
/*  99 */         ((EntityPlayerMP)entity).connection.setPlayerLocation(entity1.posX, entity1.posY, entity1.posZ, entity1.rotationYaw, entity1.rotationPitch);
/*     */       }
/*     */       else {
/*     */         
/* 103 */         entity.setLocationAndAngles(entity1.posX, entity1.posY, entity1.posZ, entity1.rotationYaw, entity1.rotationPitch);
/*     */       } 
/*     */       
/* 106 */       notifyCommandListener(sender, this, "commands.tp.success", new Object[] { entity.getName(), entity1.getName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void teleportEntityToCoordinates(Entity p_189863_0_, CommandBase.CoordinateArg p_189863_1_, CommandBase.CoordinateArg p_189863_2_, CommandBase.CoordinateArg p_189863_3_, CommandBase.CoordinateArg p_189863_4_, CommandBase.CoordinateArg p_189863_5_) {
/* 117 */     if (p_189863_0_ instanceof EntityPlayerMP) {
/*     */       
/* 119 */       Set<SPacketPlayerPosLook.EnumFlags> set = EnumSet.noneOf(SPacketPlayerPosLook.EnumFlags.class);
/*     */       
/* 121 */       if (p_189863_1_.isRelative())
/*     */       {
/* 123 */         set.add(SPacketPlayerPosLook.EnumFlags.X);
/*     */       }
/*     */       
/* 126 */       if (p_189863_2_.isRelative())
/*     */       {
/* 128 */         set.add(SPacketPlayerPosLook.EnumFlags.Y);
/*     */       }
/*     */       
/* 131 */       if (p_189863_3_.isRelative())
/*     */       {
/* 133 */         set.add(SPacketPlayerPosLook.EnumFlags.Z);
/*     */       }
/*     */       
/* 136 */       if (p_189863_5_.isRelative())
/*     */       {
/* 138 */         set.add(SPacketPlayerPosLook.EnumFlags.X_ROT);
/*     */       }
/*     */       
/* 141 */       if (p_189863_4_.isRelative())
/*     */       {
/* 143 */         set.add(SPacketPlayerPosLook.EnumFlags.Y_ROT);
/*     */       }
/*     */       
/* 146 */       float f = (float)p_189863_4_.getAmount();
/*     */       
/* 148 */       if (!p_189863_4_.isRelative())
/*     */       {
/* 150 */         f = MathHelper.wrapDegrees(f);
/*     */       }
/*     */       
/* 153 */       float f1 = (float)p_189863_5_.getAmount();
/*     */       
/* 155 */       if (!p_189863_5_.isRelative())
/*     */       {
/* 157 */         f1 = MathHelper.wrapDegrees(f1);
/*     */       }
/*     */       
/* 160 */       p_189863_0_.dismountRidingEntity();
/* 161 */       ((EntityPlayerMP)p_189863_0_).connection.setPlayerLocation(p_189863_1_.getAmount(), p_189863_2_.getAmount(), p_189863_3_.getAmount(), f, f1, set);
/* 162 */       p_189863_0_.setRotationYawHead(f);
/*     */     }
/*     */     else {
/*     */       
/* 166 */       float f2 = (float)MathHelper.wrapDegrees(p_189863_4_.getResult());
/* 167 */       float f3 = (float)MathHelper.wrapDegrees(p_189863_5_.getResult());
/* 168 */       f3 = MathHelper.clamp(f3, -90.0F, 90.0F);
/* 169 */       p_189863_0_.setLocationAndAngles(p_189863_1_.getResult(), p_189863_2_.getResult(), p_189863_3_.getResult(), f2, f3);
/* 170 */       p_189863_0_.setRotationYawHead(f2);
/*     */     } 
/*     */     
/* 173 */     if (!(p_189863_0_ instanceof EntityLivingBase) || !((EntityLivingBase)p_189863_0_).isElytraFlying()) {
/*     */       
/* 175 */       p_189863_0_.motionY = 0.0D;
/* 176 */       p_189863_0_.onGround = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 182 */     return (args.length != 1 && args.length != 2) ? Collections.<String>emptyList() : getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 190 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandTP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */