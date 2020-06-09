/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class CommandBlockBaseLogic
/*     */   implements ICommandSender
/*     */ {
/*     */   public CommandBlockBaseLogic() {
/*  24 */     this.field_193041_b = -1L;
/*  25 */     this.field_193042_c = true;
/*     */ 
/*     */ 
/*     */     
/*  29 */     this.trackOutput = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  35 */     this.commandStored = "";
/*     */ 
/*     */     
/*  38 */     this.customName = "@";
/*  39 */     this.resultStats = new CommandResultStats();
/*     */   }
/*     */   private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("HH:mm:ss"); private long field_193041_b;
/*     */   private boolean field_193042_c;
/*     */   private int successCount;
/*     */   
/*     */   public int getSuccessCount() {
/*  46 */     return this.successCount;
/*     */   }
/*     */   private boolean trackOutput; private ITextComponent lastOutput; private String commandStored; private String customName; private final CommandResultStats resultStats;
/*     */   
/*     */   public void setSuccessCount(int successCountIn) {
/*  51 */     this.successCount = successCountIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent getLastOutput() {
/*  59 */     return (this.lastOutput == null) ? (ITextComponent)new TextComponentString("") : this.lastOutput;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound p_189510_1_) {
/*  64 */     p_189510_1_.setString("Command", this.commandStored);
/*  65 */     p_189510_1_.setInteger("SuccessCount", this.successCount);
/*  66 */     p_189510_1_.setString("CustomName", this.customName);
/*  67 */     p_189510_1_.setBoolean("TrackOutput", this.trackOutput);
/*     */     
/*  69 */     if (this.lastOutput != null && this.trackOutput)
/*     */     {
/*  71 */       p_189510_1_.setString("LastOutput", ITextComponent.Serializer.componentToJson(this.lastOutput));
/*     */     }
/*     */     
/*  74 */     p_189510_1_.setBoolean("UpdateLastExecution", this.field_193042_c);
/*     */     
/*  76 */     if (this.field_193042_c && this.field_193041_b > 0L)
/*     */     {
/*  78 */       p_189510_1_.setLong("LastExecution", this.field_193041_b);
/*     */     }
/*     */     
/*  81 */     this.resultStats.writeStatsToNBT(p_189510_1_);
/*  82 */     return p_189510_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDataFromNBT(NBTTagCompound nbt) {
/*  90 */     this.commandStored = nbt.getString("Command");
/*  91 */     this.successCount = nbt.getInteger("SuccessCount");
/*     */     
/*  93 */     if (nbt.hasKey("CustomName", 8))
/*     */     {
/*  95 */       this.customName = nbt.getString("CustomName");
/*     */     }
/*     */     
/*  98 */     if (nbt.hasKey("TrackOutput", 1))
/*     */     {
/* 100 */       this.trackOutput = nbt.getBoolean("TrackOutput");
/*     */     }
/*     */     
/* 103 */     if (nbt.hasKey("LastOutput", 8) && this.trackOutput) {
/*     */       
/*     */       try
/*     */       {
/* 107 */         this.lastOutput = ITextComponent.Serializer.jsonToComponent(nbt.getString("LastOutput"));
/*     */       }
/* 109 */       catch (Throwable throwable)
/*     */       {
/* 111 */         this.lastOutput = (ITextComponent)new TextComponentString(throwable.getMessage());
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 116 */       this.lastOutput = null;
/*     */     } 
/*     */     
/* 119 */     if (nbt.hasKey("UpdateLastExecution"))
/*     */     {
/* 121 */       this.field_193042_c = nbt.getBoolean("UpdateLastExecution");
/*     */     }
/*     */     
/* 124 */     if (this.field_193042_c && nbt.hasKey("LastExecution")) {
/*     */       
/* 126 */       this.field_193041_b = nbt.getLong("LastExecution");
/*     */     }
/*     */     else {
/*     */       
/* 130 */       this.field_193041_b = -1L;
/*     */     } 
/*     */     
/* 133 */     this.resultStats.readStatsFromNBT(nbt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 141 */     return (permLevel <= 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCommand(String command) {
/* 149 */     this.commandStored = command;
/* 150 */     this.successCount = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommand() {
/* 158 */     return this.commandStored;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean trigger(World worldIn) {
/* 163 */     if (!worldIn.isRemote && worldIn.getTotalWorldTime() != this.field_193041_b) {
/*     */       
/* 165 */       if ("Searge".equalsIgnoreCase(this.commandStored)) {
/*     */         
/* 167 */         this.lastOutput = (ITextComponent)new TextComponentString("#itzlipofutzli");
/* 168 */         this.successCount = 1;
/* 169 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 173 */       MinecraftServer minecraftserver = getServer();
/*     */       
/* 175 */       if (minecraftserver != null && minecraftserver.isAnvilFileSet() && minecraftserver.isCommandBlockEnabled()) {
/*     */         
/*     */         try
/*     */         {
/* 179 */           this.lastOutput = null;
/* 180 */           this.successCount = minecraftserver.getCommandManager().executeCommand(this, this.commandStored);
/*     */         }
/* 182 */         catch (Throwable throwable)
/*     */         {
/* 184 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Executing command block");
/* 185 */           CrashReportCategory crashreportcategory = crashreport.makeCategory("Command to be executed");
/* 186 */           crashreportcategory.setDetail("Command", new ICrashReportDetail<String>()
/*     */               {
/*     */                 public String call() throws Exception
/*     */                 {
/* 190 */                   return CommandBlockBaseLogic.this.getCommand();
/*     */                 }
/*     */               });
/* 193 */           crashreportcategory.setDetail("Name", new ICrashReportDetail<String>()
/*     */               {
/*     */                 public String call() throws Exception
/*     */                 {
/* 197 */                   return CommandBlockBaseLogic.this.getName();
/*     */                 }
/*     */               });
/* 200 */           throw new ReportedException(crashreport);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 205 */         this.successCount = 0;
/*     */       } 
/*     */       
/* 208 */       if (this.field_193042_c) {
/*     */         
/* 210 */         this.field_193041_b = worldIn.getTotalWorldTime();
/*     */       }
/*     */       else {
/*     */         
/* 214 */         this.field_193041_b = -1L;
/*     */       } 
/*     */       
/* 217 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 222 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 231 */     return this.customName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 236 */     this.customName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChatMessage(ITextComponent component) {
/* 244 */     if (this.trackOutput && getEntityWorld() != null && !(getEntityWorld()).isRemote) {
/*     */       
/* 246 */       this.lastOutput = (new TextComponentString("[" + TIMESTAMP_FORMAT.format(new Date()) + "] ")).appendSibling(component);
/* 247 */       updateCommand();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendCommandFeedback() {
/* 256 */     MinecraftServer minecraftserver = getServer();
/* 257 */     return !(minecraftserver != null && minecraftserver.isAnvilFileSet() && !minecraftserver.worldServers[0].getGameRules().getBoolean("commandBlockOutput"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 262 */     this.resultStats.setCommandStatForSender(getServer(), this, type, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void updateCommand();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getCommandBlockType();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void fillInInfo(ByteBuf paramByteBuf);
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastOutput(@Nullable ITextComponent lastOutputMessage) {
/* 280 */     this.lastOutput = lastOutputMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTrackOutput(boolean shouldTrackOutput) {
/* 285 */     this.trackOutput = shouldTrackOutput;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldTrackOutput() {
/* 290 */     return this.trackOutput;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tryOpenEditCommandBlock(EntityPlayer playerIn) {
/* 295 */     if (!playerIn.canUseCommandBlock())
/*     */     {
/* 297 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 301 */     if ((playerIn.getEntityWorld()).isRemote)
/*     */     {
/* 303 */       playerIn.displayGuiEditCommandCart(this);
/*     */     }
/*     */     
/* 306 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CommandResultStats getCommandResultStats() {
/* 312 */     return this.resultStats;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\CommandBlockBaseLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */