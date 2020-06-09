/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.CommandBlockBaseLogic;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataFixer;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.IFixType;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityMinecartCommandBlock extends EntityMinecart {
/*  30 */   private static final DataParameter<String> COMMAND = EntityDataManager.createKey(EntityMinecartCommandBlock.class, DataSerializers.STRING);
/*  31 */   private static final DataParameter<ITextComponent> LAST_OUTPUT = EntityDataManager.createKey(EntityMinecartCommandBlock.class, DataSerializers.TEXT_COMPONENT);
/*  32 */   private final CommandBlockBaseLogic commandBlockLogic = new CommandBlockBaseLogic()
/*     */     {
/*     */       public void updateCommand()
/*     */       {
/*  36 */         EntityMinecartCommandBlock.this.getDataManager().set(EntityMinecartCommandBlock.COMMAND, getCommand());
/*  37 */         EntityMinecartCommandBlock.this.getDataManager().set(EntityMinecartCommandBlock.LAST_OUTPUT, getLastOutput());
/*     */       }
/*     */       
/*     */       public int getCommandBlockType() {
/*  41 */         return 1;
/*     */       }
/*     */       
/*     */       public void fillInInfo(ByteBuf buf) {
/*  45 */         buf.writeInt(EntityMinecartCommandBlock.this.getEntityId());
/*     */       }
/*     */       
/*     */       public BlockPos getPosition() {
/*  49 */         return new BlockPos(EntityMinecartCommandBlock.this.posX, EntityMinecartCommandBlock.this.posY + 0.5D, EntityMinecartCommandBlock.this.posZ);
/*     */       }
/*     */       
/*     */       public Vec3d getPositionVector() {
/*  53 */         return new Vec3d(EntityMinecartCommandBlock.this.posX, EntityMinecartCommandBlock.this.posY, EntityMinecartCommandBlock.this.posZ);
/*     */       }
/*     */       
/*     */       public World getEntityWorld() {
/*  57 */         return EntityMinecartCommandBlock.this.world;
/*     */       }
/*     */       
/*     */       public Entity getCommandSenderEntity() {
/*  61 */         return EntityMinecartCommandBlock.this;
/*     */       }
/*     */       
/*     */       public MinecraftServer getServer() {
/*  65 */         return EntityMinecartCommandBlock.this.world.getMinecraftServer();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private int activatorRailCooldown;
/*     */ 
/*     */   
/*     */   public EntityMinecartCommandBlock(World worldIn) {
/*  74 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecartCommandBlock(World worldIn, double x, double y, double z) {
/*  79 */     super(worldIn, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesMinecartCommand(DataFixer fixer) {
/*  84 */     EntityMinecart.registerFixesMinecart(fixer, EntityMinecartCommandBlock.class);
/*  85 */     fixer.registerWalker(FixTypes.ENTITY, new IDataWalker()
/*     */         {
/*     */           public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn)
/*     */           {
/*  89 */             if (TileEntity.func_190559_a(TileEntityCommandBlock.class).equals(new ResourceLocation(compound.getString("id")))) {
/*     */               
/*  91 */               compound.setString("id", "Control");
/*  92 */               fixer.process((IFixType)FixTypes.BLOCK_ENTITY, compound, versionIn);
/*  93 */               compound.setString("id", "MinecartCommandBlock");
/*     */             } 
/*     */             
/*  96 */             return compound;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 103 */     super.entityInit();
/* 104 */     getDataManager().register(COMMAND, "");
/* 105 */     getDataManager().register(LAST_OUTPUT, new TextComponentString(""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound compound) {
/* 113 */     super.readEntityFromNBT(compound);
/* 114 */     this.commandBlockLogic.readDataFromNBT(compound);
/* 115 */     getDataManager().set(COMMAND, getCommandBlockLogic().getCommand());
/* 116 */     getDataManager().set(LAST_OUTPUT, getCommandBlockLogic().getLastOutput());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound compound) {
/* 124 */     super.writeEntityToNBT(compound);
/* 125 */     this.commandBlockLogic.writeToNBT(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecart.Type getType() {
/* 130 */     return EntityMinecart.Type.COMMAND_BLOCK;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getDefaultDisplayTile() {
/* 135 */     return Blocks.COMMAND_BLOCK.getDefaultState();
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandBlockBaseLogic getCommandBlockLogic() {
/* 140 */     return this.commandBlockLogic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
/* 148 */     if (receivingPower && this.ticksExisted - this.activatorRailCooldown >= 4) {
/*     */       
/* 150 */       getCommandBlockLogic().trigger(this.world);
/* 151 */       this.activatorRailCooldown = this.ticksExisted;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInitialInteract(EntityPlayer player, EnumHand stack) {
/* 157 */     this.commandBlockLogic.tryOpenEditCommandBlock(player);
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 163 */     super.notifyDataManagerChange(key);
/*     */     
/* 165 */     if (LAST_OUTPUT.equals(key)) {
/*     */ 
/*     */       
/*     */       try {
/* 169 */         this.commandBlockLogic.setLastOutput((ITextComponent)getDataManager().get(LAST_OUTPUT));
/*     */       }
/* 171 */       catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 176 */     else if (COMMAND.equals(key)) {
/*     */       
/* 178 */       this.commandBlockLogic.setCommand((String)getDataManager().get(COMMAND));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean ignoreItemEntityData() {
/* 184 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityMinecartCommandBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */