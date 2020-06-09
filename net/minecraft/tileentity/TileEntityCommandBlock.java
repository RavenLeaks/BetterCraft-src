/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockCommandBlock;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TileEntityCommandBlock extends TileEntity {
/*     */   private boolean powered;
/*     */   private boolean auto;
/*     */   
/*     */   public TileEntityCommandBlock() {
/*  24 */     this.commandBlockLogic = new CommandBlockBaseLogic()
/*     */       {
/*     */         public BlockPos getPosition()
/*     */         {
/*  28 */           return TileEntityCommandBlock.this.pos;
/*     */         }
/*     */         
/*     */         public Vec3d getPositionVector() {
/*  32 */           return new Vec3d(TileEntityCommandBlock.this.pos.getX() + 0.5D, TileEntityCommandBlock.this.pos.getY() + 0.5D, TileEntityCommandBlock.this.pos.getZ() + 0.5D);
/*     */         }
/*     */         
/*     */         public World getEntityWorld() {
/*  36 */           return TileEntityCommandBlock.this.getWorld();
/*     */         }
/*     */         
/*     */         public void setCommand(String command) {
/*  40 */           super.setCommand(command);
/*  41 */           TileEntityCommandBlock.this.markDirty();
/*     */         }
/*     */         
/*     */         public void updateCommand() {
/*  45 */           IBlockState iblockstate = TileEntityCommandBlock.this.world.getBlockState(TileEntityCommandBlock.this.pos);
/*  46 */           TileEntityCommandBlock.this.getWorld().notifyBlockUpdate(TileEntityCommandBlock.this.pos, iblockstate, iblockstate, 3);
/*     */         }
/*     */         
/*     */         public int getCommandBlockType() {
/*  50 */           return 0;
/*     */         }
/*     */         
/*     */         public void fillInInfo(ByteBuf buf) {
/*  54 */           buf.writeInt(TileEntityCommandBlock.this.pos.getX());
/*  55 */           buf.writeInt(TileEntityCommandBlock.this.pos.getY());
/*  56 */           buf.writeInt(TileEntityCommandBlock.this.pos.getZ());
/*     */         }
/*     */         
/*     */         public MinecraftServer getServer() {
/*  60 */           return TileEntityCommandBlock.this.world.getMinecraftServer();
/*     */         }
/*     */       };
/*     */   }
/*     */   private boolean conditionMet; private boolean sendToClient; private final CommandBlockBaseLogic commandBlockLogic;
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/*  66 */     super.writeToNBT(compound);
/*  67 */     this.commandBlockLogic.writeToNBT(compound);
/*  68 */     compound.setBoolean("powered", isPowered());
/*  69 */     compound.setBoolean("conditionMet", isConditionMet());
/*  70 */     compound.setBoolean("auto", isAuto());
/*  71 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  76 */     super.readFromNBT(compound);
/*  77 */     this.commandBlockLogic.readDataFromNBT(compound);
/*  78 */     this.powered = compound.getBoolean("powered");
/*  79 */     this.conditionMet = compound.getBoolean("conditionMet");
/*  80 */     setAuto(compound.getBoolean("auto"));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SPacketUpdateTileEntity getUpdatePacket() {
/*  86 */     if (isSendToClient()) {
/*     */       
/*  88 */       setSendToClient(false);
/*  89 */       NBTTagCompound nbttagcompound = writeToNBT(new NBTTagCompound());
/*  90 */       return new SPacketUpdateTileEntity(this.pos, 2, nbttagcompound);
/*     */     } 
/*     */ 
/*     */     
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onlyOpsCanSetNbt() {
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandBlockBaseLogic getCommandBlockLogic() {
/* 105 */     return this.commandBlockLogic;
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandResultStats getCommandResultStats() {
/* 110 */     return this.commandBlockLogic.getCommandResultStats();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPowered(boolean poweredIn) {
/* 115 */     this.powered = poweredIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPowered() {
/* 120 */     return this.powered;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAuto() {
/* 125 */     return this.auto;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAuto(boolean autoIn) {
/* 130 */     boolean flag = this.auto;
/* 131 */     this.auto = autoIn;
/*     */     
/* 133 */     if (!flag && autoIn && !this.powered && this.world != null && getMode() != Mode.SEQUENCE) {
/*     */       
/* 135 */       Block block = getBlockType();
/*     */       
/* 137 */       if (block instanceof BlockCommandBlock) {
/*     */         
/* 139 */         setConditionMet();
/* 140 */         this.world.scheduleUpdate(this.pos, block, block.tickRate(this.world));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConditionMet() {
/* 147 */     return this.conditionMet;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setConditionMet() {
/* 152 */     this.conditionMet = true;
/*     */     
/* 154 */     if (isConditional()) {
/*     */       
/* 156 */       BlockPos blockpos = this.pos.offset(((EnumFacing)this.world.getBlockState(this.pos).getValue((IProperty)BlockCommandBlock.FACING)).getOpposite());
/*     */       
/* 158 */       if (this.world.getBlockState(blockpos).getBlock() instanceof BlockCommandBlock) {
/*     */         
/* 160 */         TileEntity tileentity = this.world.getTileEntity(blockpos);
/* 161 */         this.conditionMet = (tileentity instanceof TileEntityCommandBlock && ((TileEntityCommandBlock)tileentity).getCommandBlockLogic().getSuccessCount() > 0);
/*     */       }
/*     */       else {
/*     */         
/* 165 */         this.conditionMet = false;
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     return this.conditionMet;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSendToClient() {
/* 174 */     return this.sendToClient;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSendToClient(boolean p_184252_1_) {
/* 179 */     this.sendToClient = p_184252_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mode getMode() {
/* 184 */     Block block = getBlockType();
/*     */     
/* 186 */     if (block == Blocks.COMMAND_BLOCK)
/*     */     {
/* 188 */       return Mode.REDSTONE;
/*     */     }
/* 190 */     if (block == Blocks.REPEATING_COMMAND_BLOCK)
/*     */     {
/* 192 */       return Mode.AUTO;
/*     */     }
/*     */ 
/*     */     
/* 196 */     return (block == Blocks.CHAIN_COMMAND_BLOCK) ? Mode.SEQUENCE : Mode.REDSTONE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConditional() {
/* 202 */     IBlockState iblockstate = this.world.getBlockState(getPos());
/* 203 */     return (iblockstate.getBlock() instanceof BlockCommandBlock) ? ((Boolean)iblockstate.getValue((IProperty)BlockCommandBlock.CONDITIONAL)).booleanValue() : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {
/* 211 */     this.blockType = null;
/* 212 */     super.validate();
/*     */   }
/*     */   
/*     */   public enum Mode
/*     */   {
/* 217 */     SEQUENCE,
/* 218 */     AUTO,
/* 219 */     REDSTONE;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityCommandBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */