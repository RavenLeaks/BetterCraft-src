/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.storage.AnvilChunkLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandSummon
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  32 */     return "summon";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  40 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  48 */     return "commands.summon.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  56 */     if (args.length < 1)
/*     */     {
/*  58 */       throw new WrongUsageException("commands.summon.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  62 */     String s = args[0];
/*  63 */     BlockPos blockpos = sender.getPosition();
/*  64 */     Vec3d vec3d = sender.getPositionVector();
/*  65 */     double d0 = vec3d.xCoord;
/*  66 */     double d1 = vec3d.yCoord;
/*  67 */     double d2 = vec3d.zCoord;
/*     */     
/*  69 */     if (args.length >= 4) {
/*     */       
/*  71 */       d0 = parseDouble(d0, args[1], true);
/*  72 */       d1 = parseDouble(d1, args[2], false);
/*  73 */       d2 = parseDouble(d2, args[3], true);
/*  74 */       blockpos = new BlockPos(d0, d1, d2);
/*     */     } 
/*     */     
/*  77 */     World world = sender.getEntityWorld();
/*     */     
/*  79 */     if (!world.isBlockLoaded(blockpos))
/*     */     {
/*  81 */       throw new CommandException("commands.summon.outOfWorld", new Object[0]);
/*     */     }
/*  83 */     if (EntityList.field_191307_a.equals(new ResourceLocation(s))) {
/*     */       
/*  85 */       world.addWeatherEffect((Entity)new EntityLightningBolt(world, d0, d1, d2, false));
/*  86 */       notifyCommandListener(sender, (ICommand)this, "commands.summon.success", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/*  90 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  91 */       boolean flag = false;
/*     */       
/*  93 */       if (args.length >= 5) {
/*     */         
/*  95 */         String s1 = buildString(args, 4);
/*     */ 
/*     */         
/*     */         try {
/*  99 */           nbttagcompound = JsonToNBT.getTagFromJson(s1);
/* 100 */           flag = true;
/*     */         }
/* 102 */         catch (NBTException nbtexception) {
/*     */           
/* 104 */           throw new CommandException("commands.summon.tagError", new Object[] { nbtexception.getMessage() });
/*     */         } 
/*     */       } 
/*     */       
/* 108 */       nbttagcompound.setString("id", s);
/* 109 */       Entity entity = AnvilChunkLoader.readWorldEntityPos(nbttagcompound, world, d0, d1, d2, true);
/*     */       
/* 111 */       if (entity == null)
/*     */       {
/* 113 */         throw new CommandException("commands.summon.failed", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 117 */       entity.setLocationAndAngles(d0, d1, d2, entity.rotationYaw, entity.rotationPitch);
/*     */       
/* 119 */       if (!flag && entity instanceof EntityLiving)
/*     */       {
/* 121 */         ((EntityLiving)entity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), null);
/*     */       }
/*     */       
/* 124 */       notifyCommandListener(sender, (ICommand)this, "commands.summon.success", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 132 */     if (args.length == 1)
/*     */     {
/* 134 */       return getListOfStringsMatchingLastWord(args, EntityList.getEntityNameList());
/*     */     }
/*     */ 
/*     */     
/* 138 */     return (args.length > 1 && args.length <= 4) ? getTabCompletionCoordinate(args, 1, pos) : Collections.<String>emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandSummon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */