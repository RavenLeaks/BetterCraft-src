/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CommandSenderWrapper
/*     */   implements ICommandSender
/*     */ {
/*     */   private final ICommandSender field_193043_a;
/*     */   @Nullable
/*     */   private final Vec3d field_194002_b;
/*     */   @Nullable
/*     */   private final BlockPos field_194003_c;
/*     */   @Nullable
/*     */   private final Integer field_194004_d;
/*     */   @Nullable
/*     */   private final Entity field_194005_e;
/*     */   @Nullable
/*     */   private final Boolean field_194006_f;
/*     */   
/*     */   public CommandSenderWrapper(ICommandSender p_i47599_1_, @Nullable Vec3d p_i47599_2_, @Nullable BlockPos p_i47599_3_, @Nullable Integer p_i47599_4_, @Nullable Entity p_i47599_5_, @Nullable Boolean p_i47599_6_) {
/*  28 */     this.field_193043_a = p_i47599_1_;
/*  29 */     this.field_194002_b = p_i47599_2_;
/*  30 */     this.field_194003_c = p_i47599_3_;
/*  31 */     this.field_194004_d = p_i47599_4_;
/*  32 */     this.field_194005_e = p_i47599_5_;
/*  33 */     this.field_194006_f = p_i47599_6_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static CommandSenderWrapper func_193998_a(ICommandSender p_193998_0_) {
/*  38 */     return (p_193998_0_ instanceof CommandSenderWrapper) ? (CommandSenderWrapper)p_193998_0_ : new CommandSenderWrapper(p_193998_0_, null, null, null, null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandSenderWrapper func_193997_a(Entity p_193997_1_, Vec3d p_193997_2_) {
/*  43 */     return (this.field_194005_e == p_193997_1_ && Objects.equals(this.field_194002_b, p_193997_2_)) ? this : new CommandSenderWrapper(this.field_193043_a, p_193997_2_, new BlockPos(p_193997_2_), this.field_194004_d, p_193997_1_, this.field_194006_f);
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandSenderWrapper func_193999_a(int p_193999_1_) {
/*  48 */     return (this.field_194004_d != null && this.field_194004_d.intValue() <= p_193999_1_) ? this : new CommandSenderWrapper(this.field_193043_a, this.field_194002_b, this.field_194003_c, Integer.valueOf(p_193999_1_), this.field_194005_e, this.field_194006_f);
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandSenderWrapper func_194001_a(boolean p_194001_1_) {
/*  53 */     return (this.field_194006_f == null || (this.field_194006_f.booleanValue() && !p_194001_1_)) ? new CommandSenderWrapper(this.field_193043_a, this.field_194002_b, this.field_194003_c, this.field_194004_d, this.field_194005_e, Boolean.valueOf(p_194001_1_)) : this;
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandSenderWrapper func_194000_i() {
/*  58 */     return (this.field_194002_b != null) ? this : new CommandSenderWrapper(this.field_193043_a, getPositionVector(), getPosition(), this.field_194004_d, this.field_194005_e, this.field_194006_f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  66 */     return (this.field_194005_e != null) ? this.field_194005_e.getName() : this.field_193043_a.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent getDisplayName() {
/*  74 */     return (this.field_194005_e != null) ? this.field_194005_e.getDisplayName() : this.field_193043_a.getDisplayName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChatMessage(ITextComponent component) {
/*  82 */     if (this.field_194006_f == null || this.field_194006_f.booleanValue())
/*     */     {
/*  84 */       this.field_193043_a.addChatMessage(component);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  93 */     return (this.field_194004_d != null && this.field_194004_d.intValue() < permLevel) ? false : this.field_193043_a.canCommandSenderUseCommand(permLevel, commandName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/* 102 */     if (this.field_194003_c != null)
/*     */     {
/* 104 */       return this.field_194003_c;
/*     */     }
/*     */ 
/*     */     
/* 108 */     return (this.field_194005_e != null) ? this.field_194005_e.getPosition() : this.field_193043_a.getPosition();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3d getPositionVector() {
/* 118 */     if (this.field_194002_b != null)
/*     */     {
/* 120 */       return this.field_194002_b;
/*     */     }
/*     */ 
/*     */     
/* 124 */     return (this.field_194005_e != null) ? this.field_194005_e.getPositionVector() : this.field_193043_a.getPositionVector();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getEntityWorld() {
/* 134 */     return (this.field_194005_e != null) ? this.field_194005_e.getEntityWorld() : this.field_193043_a.getEntityWorld();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getCommandSenderEntity() {
/* 144 */     return (this.field_194005_e != null) ? this.field_194005_e.getCommandSenderEntity() : this.field_193043_a.getCommandSenderEntity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendCommandFeedback() {
/* 152 */     return (this.field_194006_f != null) ? this.field_194006_f.booleanValue() : this.field_193043_a.sendCommandFeedback();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 157 */     if (this.field_194005_e != null) {
/*     */       
/* 159 */       this.field_194005_e.setCommandStat(type, amount);
/*     */     }
/*     */     else {
/*     */       
/* 163 */       this.field_193043_a.setCommandStat(type, amount);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MinecraftServer getServer() {
/* 174 */     return this.field_193043_a.getServer();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandSenderWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */