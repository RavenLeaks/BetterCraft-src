/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandTeleport
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  28 */     return "teleport";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  36 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  44 */     return "commands.teleport.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  52 */     if (args.length < 4)
/*     */     {
/*  54 */       throw new WrongUsageException("commands.teleport.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  58 */     Entity entity = getEntity(server, sender, args[0]);
/*     */     
/*  60 */     if (entity.world != null) {
/*     */       
/*  62 */       int i = 4096;
/*  63 */       Vec3d vec3d = sender.getPositionVector();
/*  64 */       int j = 1;
/*  65 */       CommandBase.CoordinateArg commandbase$coordinatearg = parseCoordinate(vec3d.xCoord, args[j++], true);
/*  66 */       CommandBase.CoordinateArg commandbase$coordinatearg1 = parseCoordinate(vec3d.yCoord, args[j++], -4096, 4096, false);
/*  67 */       CommandBase.CoordinateArg commandbase$coordinatearg2 = parseCoordinate(vec3d.zCoord, args[j++], true);
/*  68 */       Entity entity1 = (sender.getCommandSenderEntity() == null) ? entity : sender.getCommandSenderEntity();
/*  69 */       CommandBase.CoordinateArg commandbase$coordinatearg3 = parseCoordinate((args.length > j) ? entity1.rotationYaw : entity.rotationYaw, (args.length > j) ? args[j] : "~", false);
/*  70 */       j++;
/*  71 */       CommandBase.CoordinateArg commandbase$coordinatearg4 = parseCoordinate((args.length > j) ? entity1.rotationPitch : entity.rotationPitch, (args.length > j) ? args[j] : "~", false);
/*  72 */       doTeleport(entity, commandbase$coordinatearg, commandbase$coordinatearg1, commandbase$coordinatearg2, commandbase$coordinatearg3, commandbase$coordinatearg4);
/*  73 */       notifyCommandListener(sender, (ICommand)this, "commands.teleport.success.coordinates", new Object[] { entity.getName(), Double.valueOf(commandbase$coordinatearg.getResult()), Double.valueOf(commandbase$coordinatearg1.getResult()), Double.valueOf(commandbase$coordinatearg2.getResult()) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void doTeleport(Entity p_189862_0_, CommandBase.CoordinateArg p_189862_1_, CommandBase.CoordinateArg p_189862_2_, CommandBase.CoordinateArg p_189862_3_, CommandBase.CoordinateArg p_189862_4_, CommandBase.CoordinateArg p_189862_5_) {
/*  83 */     if (p_189862_0_ instanceof EntityPlayerMP) {
/*     */       
/*  85 */       Set<SPacketPlayerPosLook.EnumFlags> set = EnumSet.noneOf(SPacketPlayerPosLook.EnumFlags.class);
/*  86 */       float f = (float)p_189862_4_.getAmount();
/*     */       
/*  88 */       if (p_189862_4_.isRelative()) {
/*     */         
/*  90 */         set.add(SPacketPlayerPosLook.EnumFlags.Y_ROT);
/*     */       }
/*     */       else {
/*     */         
/*  94 */         f = MathHelper.wrapDegrees(f);
/*     */       } 
/*     */       
/*  97 */       float f1 = (float)p_189862_5_.getAmount();
/*     */       
/*  99 */       if (p_189862_5_.isRelative()) {
/*     */         
/* 101 */         set.add(SPacketPlayerPosLook.EnumFlags.X_ROT);
/*     */       }
/*     */       else {
/*     */         
/* 105 */         f1 = MathHelper.wrapDegrees(f1);
/*     */       } 
/*     */       
/* 108 */       p_189862_0_.dismountRidingEntity();
/* 109 */       ((EntityPlayerMP)p_189862_0_).connection.setPlayerLocation(p_189862_1_.getResult(), p_189862_2_.getResult(), p_189862_3_.getResult(), f, f1, set);
/* 110 */       p_189862_0_.setRotationYawHead(f);
/*     */     }
/*     */     else {
/*     */       
/* 114 */       float f2 = (float)MathHelper.wrapDegrees(p_189862_4_.getResult());
/* 115 */       float f3 = (float)MathHelper.wrapDegrees(p_189862_5_.getResult());
/* 116 */       f3 = MathHelper.clamp(f3, -90.0F, 90.0F);
/* 117 */       p_189862_0_.setLocationAndAngles(p_189862_1_.getResult(), p_189862_2_.getResult(), p_189862_3_.getResult(), f2, f3);
/* 118 */       p_189862_0_.setRotationYawHead(f2);
/*     */     } 
/*     */     
/* 121 */     if (!(p_189862_0_ instanceof EntityLivingBase) || !((EntityLivingBase)p_189862_0_).isElytraFlying()) {
/*     */       
/* 123 */       p_189862_0_.motionY = 0.0D;
/* 124 */       p_189862_0_.onGround = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 130 */     if (args.length == 1)
/*     */     {
/* 132 */       return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */     }
/*     */ 
/*     */     
/* 136 */     return (args.length > 1 && args.length <= 4) ? getTabCompletionCoordinate(args, 1, pos) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 145 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandTeleport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */