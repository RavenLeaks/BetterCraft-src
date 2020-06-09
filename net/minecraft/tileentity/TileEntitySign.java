/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.Style;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentUtils;
/*     */ import net.minecraft.util.text.event.ClickEvent;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TileEntitySign
/*     */   extends TileEntity {
/*  23 */   public final ITextComponent[] signText = new ITextComponent[] { (ITextComponent)new TextComponentString(""), (ITextComponent)new TextComponentString(""), (ITextComponent)new TextComponentString(""), (ITextComponent)new TextComponentString("") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   public int lineBeingEdited = -1;
/*     */   private boolean isEditable = true;
/*     */   private EntityPlayer player;
/*  32 */   private final CommandResultStats stats = new CommandResultStats();
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/*  36 */     super.writeToNBT(compound);
/*     */     
/*  38 */     for (int i = 0; i < 4; i++) {
/*     */       
/*  40 */       String s = ITextComponent.Serializer.componentToJson(this.signText[i]);
/*  41 */       compound.setString("Text" + (i + 1), s);
/*     */     } 
/*     */     
/*  44 */     this.stats.writeStatsToNBT(compound);
/*  45 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setWorldCreate(World worldIn) {
/*  50 */     setWorldObj(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  55 */     this.isEditable = false;
/*  56 */     super.readFromNBT(compound);
/*  57 */     ICommandSender icommandsender = new ICommandSender()
/*     */       {
/*     */         public String getName()
/*     */         {
/*  61 */           return "Sign";
/*     */         }
/*     */         
/*     */         public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  65 */           return true;
/*     */         }
/*     */         
/*     */         public BlockPos getPosition() {
/*  69 */           return TileEntitySign.this.pos;
/*     */         }
/*     */         
/*     */         public Vec3d getPositionVector() {
/*  73 */           return new Vec3d(TileEntitySign.this.pos.getX() + 0.5D, TileEntitySign.this.pos.getY() + 0.5D, TileEntitySign.this.pos.getZ() + 0.5D);
/*     */         }
/*     */         
/*     */         public World getEntityWorld() {
/*  77 */           return TileEntitySign.this.world;
/*     */         }
/*     */         
/*     */         public MinecraftServer getServer() {
/*  81 */           return TileEntitySign.this.world.getMinecraftServer();
/*     */         }
/*     */       };
/*     */     
/*  85 */     for (int i = 0; i < 4; i++) {
/*     */       
/*  87 */       String s = compound.getString("Text" + (i + 1));
/*  88 */       ITextComponent itextcomponent = ITextComponent.Serializer.jsonToComponent(s);
/*     */ 
/*     */       
/*     */       try {
/*  92 */         this.signText[i] = TextComponentUtils.processComponent(icommandsender, itextcomponent, null);
/*     */       }
/*  94 */       catch (CommandException var7) {
/*     */         
/*  96 */         this.signText[i] = itextcomponent;
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     this.stats.readStatsFromNBT(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SPacketUpdateTileEntity getUpdatePacket() {
/* 106 */     return new SPacketUpdateTileEntity(this.pos, 9, getUpdateTag());
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getUpdateTag() {
/* 111 */     return writeToNBT(new NBTTagCompound());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onlyOpsCanSetNbt() {
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getIsEditable() {
/* 121 */     return this.isEditable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEditable(boolean isEditableIn) {
/* 129 */     this.isEditable = isEditableIn;
/*     */     
/* 131 */     if (!isEditableIn)
/*     */     {
/* 133 */       this.player = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayer(EntityPlayer playerIn) {
/* 139 */     this.player = playerIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPlayer getPlayer() {
/* 144 */     return this.player;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean executeCommand(final EntityPlayer playerIn) {
/* 149 */     ICommandSender icommandsender = new ICommandSender()
/*     */       {
/*     */         public String getName()
/*     */         {
/* 153 */           return playerIn.getName();
/*     */         }
/*     */         
/*     */         public ITextComponent getDisplayName() {
/* 157 */           return playerIn.getDisplayName();
/*     */         }
/*     */ 
/*     */         
/*     */         public void addChatMessage(ITextComponent component) {}
/*     */         
/*     */         public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 164 */           return (permLevel <= 2);
/*     */         }
/*     */         
/*     */         public BlockPos getPosition() {
/* 168 */           return TileEntitySign.this.pos;
/*     */         }
/*     */         
/*     */         public Vec3d getPositionVector() {
/* 172 */           return new Vec3d(TileEntitySign.this.pos.getX() + 0.5D, TileEntitySign.this.pos.getY() + 0.5D, TileEntitySign.this.pos.getZ() + 0.5D);
/*     */         }
/*     */         
/*     */         public World getEntityWorld() {
/* 176 */           return playerIn.getEntityWorld();
/*     */         }
/*     */         
/*     */         public Entity getCommandSenderEntity() {
/* 180 */           return (Entity)playerIn;
/*     */         }
/*     */         
/*     */         public boolean sendCommandFeedback() {
/* 184 */           return false;
/*     */         }
/*     */         
/*     */         public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 188 */           if (TileEntitySign.this.world != null && !TileEntitySign.this.world.isRemote)
/*     */           {
/* 190 */             TileEntitySign.this.stats.setCommandStatForSender(TileEntitySign.this.world.getMinecraftServer(), this, type, amount);
/*     */           }
/*     */         }
/*     */         
/*     */         public MinecraftServer getServer() {
/* 195 */           return playerIn.getServer(); } };
/*     */     byte b;
/*     */     int i;
/*     */     ITextComponent[] arrayOfITextComponent;
/* 199 */     for (i = (arrayOfITextComponent = this.signText).length, b = 0; b < i; ) { ITextComponent itextcomponent = arrayOfITextComponent[b];
/*     */       
/* 201 */       Style style = (itextcomponent == null) ? null : itextcomponent.getStyle();
/*     */       
/* 203 */       if (style != null && style.getClickEvent() != null) {
/*     */         
/* 205 */         ClickEvent clickevent = style.getClickEvent();
/*     */         
/* 207 */         if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND)
/*     */         {
/* 209 */           playerIn.getServer().getCommandManager().executeCommand(icommandsender, clickevent.getValue());
/*     */         }
/*     */       } 
/*     */       b++; }
/*     */     
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandResultStats getStats() {
/* 219 */     return this.stats;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntitySign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */