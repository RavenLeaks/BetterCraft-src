/*      */ package net.minecraft.network;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.primitives.Doubles;
/*      */ import com.google.common.primitives.Floats;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import io.netty.util.concurrent.GenericFutureListener;
/*      */ import java.io.IOException;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.Future;
/*      */ import net.minecraft.advancements.Advancement;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.block.BlockCommandBlock;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.crash.ICrashReportDetail;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.IJumpingMount;
/*      */ import net.minecraft.entity.MoverType;
/*      */ import net.minecraft.entity.item.EntityBoat;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.item.EntityMinecartCommandBlock;
/*      */ import net.minecraft.entity.passive.AbstractHorse;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.init.MobEffects;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.ContainerBeacon;
/*      */ import net.minecraft.inventory.ContainerMerchant;
/*      */ import net.minecraft.inventory.ContainerRepair;
/*      */ import net.minecraft.inventory.EntityEquipmentSlot;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.Slot;
/*      */ import net.minecraft.item.ItemElytra;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.ItemWritableBook;
/*      */ import net.minecraft.item.ItemWrittenBook;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.nbt.NBTTagString;
/*      */ import net.minecraft.network.play.INetHandlerPlayServer;
/*      */ import net.minecraft.network.play.client.CPacketAnimation;
/*      */ import net.minecraft.network.play.client.CPacketChatMessage;
/*      */ import net.minecraft.network.play.client.CPacketClickWindow;
/*      */ import net.minecraft.network.play.client.CPacketClientSettings;
/*      */ import net.minecraft.network.play.client.CPacketClientStatus;
/*      */ import net.minecraft.network.play.client.CPacketCloseWindow;
/*      */ import net.minecraft.network.play.client.CPacketConfirmTeleport;
/*      */ import net.minecraft.network.play.client.CPacketConfirmTransaction;
/*      */ import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
/*      */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*      */ import net.minecraft.network.play.client.CPacketEnchantItem;
/*      */ import net.minecraft.network.play.client.CPacketEntityAction;
/*      */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*      */ import net.minecraft.network.play.client.CPacketInput;
/*      */ import net.minecraft.network.play.client.CPacketKeepAlive;
/*      */ import net.minecraft.network.play.client.CPacketPlaceRecipe;
/*      */ import net.minecraft.network.play.client.CPacketPlayer;
/*      */ import net.minecraft.network.play.client.CPacketPlayerAbilities;
/*      */ import net.minecraft.network.play.client.CPacketPlayerBlockPlacement;
/*      */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*      */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*      */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*      */ import net.minecraft.network.play.client.CPacketRecipeInfo;
/*      */ import net.minecraft.network.play.client.CPacketRecipePlacement;
/*      */ import net.minecraft.network.play.client.CPacketSeenAdvancements;
/*      */ import net.minecraft.network.play.client.CPacketSpectate;
/*      */ import net.minecraft.network.play.client.CPacketSteerBoat;
/*      */ import net.minecraft.network.play.client.CPacketTabComplete;
/*      */ import net.minecraft.network.play.client.CPacketUpdateSign;
/*      */ import net.minecraft.network.play.client.CPacketUseEntity;
/*      */ import net.minecraft.network.play.client.CPacketVehicleMove;
/*      */ import net.minecraft.network.play.server.SPacketBlockChange;
/*      */ import net.minecraft.network.play.server.SPacketChat;
/*      */ import net.minecraft.network.play.server.SPacketConfirmTransaction;
/*      */ import net.minecraft.network.play.server.SPacketHeldItemChange;
/*      */ import net.minecraft.network.play.server.SPacketKeepAlive;
/*      */ import net.minecraft.network.play.server.SPacketMoveVehicle;
/*      */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*      */ import net.minecraft.network.play.server.SPacketRespawn;
/*      */ import net.minecraft.network.play.server.SPacketSetSlot;
/*      */ import net.minecraft.network.play.server.SPacketTabComplete;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.tileentity.CommandBlockBaseLogic;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.tileentity.TileEntityStructure;
/*      */ import net.minecraft.util.ChatAllowedCharacters;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.ITickable;
/*      */ import net.minecraft.util.IntHashMap;
/*      */ import net.minecraft.util.Mirror;
/*      */ import net.minecraft.util.NonNullList;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Rotation;
/*      */ import net.minecraft.util.ServerRecipeBookHelper;
/*      */ import net.minecraft.util.StringUtils;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.util.text.ChatType;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.TextComponentString;
/*      */ import net.minecraft.util.text.TextComponentTranslation;
/*      */ import net.minecraft.util.text.TextFormatting;
/*      */ import net.minecraft.world.DimensionType;
/*      */ import net.minecraft.world.GameType;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class NetHandlerPlayServer implements INetHandlerPlayServer, ITickable {
/*  128 */   private static final Logger LOGGER = LogManager.getLogger();
/*      */   
/*      */   public final NetworkManager netManager;
/*      */   
/*      */   private final MinecraftServer serverController;
/*      */   
/*      */   public EntityPlayerMP playerEntity;
/*      */   
/*      */   private int networkTickCount;
/*      */   
/*      */   private long field_194402_f;
/*      */   private boolean field_194403_g;
/*      */   private long field_194404_h;
/*      */   private int chatSpamThresholdCount;
/*      */   private int itemDropThreshold;
/*  143 */   private final IntHashMap<Short> pendingTransactions = new IntHashMap();
/*      */   
/*      */   private double firstGoodX;
/*      */   
/*      */   private double firstGoodY;
/*      */   
/*      */   private double firstGoodZ;
/*      */   
/*      */   private double lastGoodX;
/*      */   
/*      */   private double lastGoodY;
/*      */   
/*      */   private double lastGoodZ;
/*      */   
/*      */   private Entity lowestRiddenEnt;
/*      */   
/*      */   private double lowestRiddenX;
/*      */   
/*      */   private double lowestRiddenY;
/*      */   private double lowestRiddenZ;
/*      */   private double lowestRiddenX1;
/*      */   private double lowestRiddenY1;
/*      */   private double lowestRiddenZ1;
/*      */   private Vec3d targetPos;
/*      */   private int teleportId;
/*      */   private int lastPositionUpdate;
/*      */   private boolean floating;
/*      */   private int floatingTickCount;
/*      */   private boolean vehicleFloating;
/*      */   private int vehicleFloatingTickCount;
/*      */   private int movePacketCounter;
/*      */   private int lastMovePacketCounter;
/*  175 */   private ServerRecipeBookHelper field_194309_H = new ServerRecipeBookHelper();
/*      */ 
/*      */   
/*      */   public NetHandlerPlayServer(MinecraftServer server, NetworkManager networkManagerIn, EntityPlayerMP playerIn) {
/*  179 */     this.serverController = server;
/*  180 */     this.netManager = networkManagerIn;
/*  181 */     networkManagerIn.setNetHandler((INetHandler)this);
/*  182 */     this.playerEntity = playerIn;
/*  183 */     playerIn.connection = this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update() {
/*  191 */     captureCurrentPosition();
/*  192 */     this.playerEntity.onUpdateEntity();
/*  193 */     this.playerEntity.setPositionAndRotation(this.firstGoodX, this.firstGoodY, this.firstGoodZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*  194 */     this.networkTickCount++;
/*  195 */     this.lastMovePacketCounter = this.movePacketCounter;
/*      */     
/*  197 */     if (this.floating) {
/*      */       
/*  199 */       if (++this.floatingTickCount > 80) {
/*      */         
/*  201 */         LOGGER.warn("{} was kicked for floating too long!", this.playerEntity.getName());
/*  202 */         func_194028_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.flying", new Object[0]));
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } else {
/*  208 */       this.floating = false;
/*  209 */       this.floatingTickCount = 0;
/*      */     } 
/*      */     
/*  212 */     this.lowestRiddenEnt = this.playerEntity.getLowestRidingEntity();
/*      */     
/*  214 */     if (this.lowestRiddenEnt != this.playerEntity && this.lowestRiddenEnt.getControllingPassenger() == this.playerEntity) {
/*      */       
/*  216 */       this.lowestRiddenX = this.lowestRiddenEnt.posX;
/*  217 */       this.lowestRiddenY = this.lowestRiddenEnt.posY;
/*  218 */       this.lowestRiddenZ = this.lowestRiddenEnt.posZ;
/*  219 */       this.lowestRiddenX1 = this.lowestRiddenEnt.posX;
/*  220 */       this.lowestRiddenY1 = this.lowestRiddenEnt.posY;
/*  221 */       this.lowestRiddenZ1 = this.lowestRiddenEnt.posZ;
/*      */       
/*  223 */       if (this.vehicleFloating && this.playerEntity.getLowestRidingEntity().getControllingPassenger() == this.playerEntity) {
/*      */         
/*  225 */         if (++this.vehicleFloatingTickCount > 80) {
/*      */           
/*  227 */           LOGGER.warn("{} was kicked for floating a vehicle too long!", this.playerEntity.getName());
/*  228 */           func_194028_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.flying", new Object[0]));
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */       } else {
/*  234 */         this.vehicleFloating = false;
/*  235 */         this.vehicleFloatingTickCount = 0;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  240 */       this.lowestRiddenEnt = null;
/*  241 */       this.vehicleFloating = false;
/*  242 */       this.vehicleFloatingTickCount = 0;
/*      */     } 
/*      */     
/*  245 */     this.serverController.theProfiler.startSection("keepAlive");
/*  246 */     long i = currentTimeMillis();
/*      */     
/*  248 */     if (i - this.field_194402_f >= 15000L)
/*      */     {
/*  250 */       if (this.field_194403_g) {
/*      */         
/*  252 */         func_194028_b((ITextComponent)new TextComponentTranslation("disconnect.timeout", new Object[0]));
/*      */       }
/*      */       else {
/*      */         
/*  256 */         this.field_194403_g = true;
/*  257 */         this.field_194402_f = i;
/*  258 */         this.field_194404_h = i;
/*  259 */         sendPacket((Packet<?>)new SPacketKeepAlive(this.field_194404_h));
/*      */       } 
/*      */     }
/*      */     
/*  263 */     this.serverController.theProfiler.endSection();
/*      */     
/*  265 */     if (this.chatSpamThresholdCount > 0)
/*      */     {
/*  267 */       this.chatSpamThresholdCount--;
/*      */     }
/*      */     
/*  270 */     if (this.itemDropThreshold > 0)
/*      */     {
/*  272 */       this.itemDropThreshold--;
/*      */     }
/*      */     
/*  275 */     if (this.playerEntity.getLastActiveTime() > 0L && this.serverController.getMaxPlayerIdleMinutes() > 0 && MinecraftServer.getCurrentTimeMillis() - this.playerEntity.getLastActiveTime() > (this.serverController.getMaxPlayerIdleMinutes() * 1000 * 60))
/*      */     {
/*  277 */       func_194028_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.idling", new Object[0]));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void captureCurrentPosition() {
/*  283 */     this.firstGoodX = this.playerEntity.posX;
/*  284 */     this.firstGoodY = this.playerEntity.posY;
/*  285 */     this.firstGoodZ = this.playerEntity.posZ;
/*  286 */     this.lastGoodX = this.playerEntity.posX;
/*  287 */     this.lastGoodY = this.playerEntity.posY;
/*  288 */     this.lastGoodZ = this.playerEntity.posZ;
/*      */   }
/*      */ 
/*      */   
/*      */   public NetworkManager getNetworkManager() {
/*  293 */     return this.netManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_194028_b(final ITextComponent p_194028_1_) {
/*  298 */     this.netManager.sendPacket((Packet<?>)new SPacketDisconnect(p_194028_1_), new GenericFutureListener<Future<? super Void>>()
/*      */         {
/*      */           public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception
/*      */           {
/*  302 */             NetHandlerPlayServer.this.netManager.closeChannel(p_194028_1_);
/*      */           }
/*      */         },  (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener[0]);
/*  305 */     this.netManager.disableAutoRead();
/*  306 */     Futures.getUnchecked((Future)this.serverController.addScheduledTask(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/*  310 */               NetHandlerPlayServer.this.netManager.checkDisconnected();
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processInput(CPacketInput packetIn) {
/*  321 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/*  322 */     this.playerEntity.setEntityActionState(packetIn.getStrafeSpeed(), packetIn.func_192620_b(), packetIn.isJumping(), packetIn.isSneaking());
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isMovePlayerPacketInvalid(CPacketPlayer packetIn) {
/*  327 */     if (Doubles.isFinite(packetIn.getX(0.0D)) && Doubles.isFinite(packetIn.getY(0.0D)) && Doubles.isFinite(packetIn.getZ(0.0D)) && Floats.isFinite(packetIn.getPitch(0.0F)) && Floats.isFinite(packetIn.getYaw(0.0F)))
/*      */     {
/*  329 */       return !(Math.abs(packetIn.getX(0.0D)) <= 3.0E7D && Math.abs(packetIn.getY(0.0D)) <= 3.0E7D && Math.abs(packetIn.getZ(0.0D)) <= 3.0E7D);
/*      */     }
/*      */ 
/*      */     
/*  333 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isMoveVehiclePacketInvalid(CPacketVehicleMove packetIn) {
/*  339 */     return !(Doubles.isFinite(packetIn.getX()) && Doubles.isFinite(packetIn.getY()) && Doubles.isFinite(packetIn.getZ()) && Floats.isFinite(packetIn.getPitch()) && Floats.isFinite(packetIn.getYaw()));
/*      */   }
/*      */ 
/*      */   
/*      */   public void processVehicleMove(CPacketVehicleMove packetIn) {
/*  344 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/*      */     
/*  346 */     if (isMoveVehiclePacketInvalid(packetIn)) {
/*      */       
/*  348 */       func_194028_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.invalid_vehicle_movement", new Object[0]));
/*      */     }
/*      */     else {
/*      */       
/*  352 */       Entity entity = this.playerEntity.getLowestRidingEntity();
/*      */       
/*  354 */       if (entity != this.playerEntity && entity.getControllingPassenger() == this.playerEntity && entity == this.lowestRiddenEnt) {
/*      */         
/*  356 */         WorldServer worldserver = this.playerEntity.getServerWorld();
/*  357 */         double d0 = entity.posX;
/*  358 */         double d1 = entity.posY;
/*  359 */         double d2 = entity.posZ;
/*  360 */         double d3 = packetIn.getX();
/*  361 */         double d4 = packetIn.getY();
/*  362 */         double d5 = packetIn.getZ();
/*  363 */         float f = packetIn.getYaw();
/*  364 */         float f1 = packetIn.getPitch();
/*  365 */         double d6 = d3 - this.lowestRiddenX;
/*  366 */         double d7 = d4 - this.lowestRiddenY;
/*  367 */         double d8 = d5 - this.lowestRiddenZ;
/*  368 */         double d9 = entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ;
/*  369 */         double d10 = d6 * d6 + d7 * d7 + d8 * d8;
/*      */         
/*  371 */         if (d10 - d9 > 100.0D && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(entity.getName()))) {
/*      */           
/*  373 */           LOGGER.warn("{} (vehicle of {}) moved too quickly! {},{},{}", entity.getName(), this.playerEntity.getName(), Double.valueOf(d6), Double.valueOf(d7), Double.valueOf(d8));
/*  374 */           this.netManager.sendPacket((Packet<?>)new SPacketMoveVehicle(entity));
/*      */           
/*      */           return;
/*      */         } 
/*  378 */         boolean flag = worldserver.getCollisionBoxes(entity, entity.getEntityBoundingBox().contract(0.0625D)).isEmpty();
/*  379 */         d6 = d3 - this.lowestRiddenX1;
/*  380 */         d7 = d4 - this.lowestRiddenY1 - 1.0E-6D;
/*  381 */         d8 = d5 - this.lowestRiddenZ1;
/*  382 */         entity.moveEntity(MoverType.PLAYER, d6, d7, d8);
/*  383 */         double d11 = d7;
/*  384 */         d6 = d3 - entity.posX;
/*  385 */         d7 = d4 - entity.posY;
/*      */         
/*  387 */         if (d7 > -0.5D || d7 < 0.5D)
/*      */         {
/*  389 */           d7 = 0.0D;
/*      */         }
/*      */         
/*  392 */         d8 = d5 - entity.posZ;
/*  393 */         d10 = d6 * d6 + d7 * d7 + d8 * d8;
/*  394 */         boolean flag1 = false;
/*      */         
/*  396 */         if (d10 > 0.0625D) {
/*      */           
/*  398 */           flag1 = true;
/*  399 */           LOGGER.warn("{} moved wrongly!", entity.getName());
/*      */         } 
/*      */         
/*  402 */         entity.setPositionAndRotation(d3, d4, d5, f, f1);
/*  403 */         boolean flag2 = worldserver.getCollisionBoxes(entity, entity.getEntityBoundingBox().contract(0.0625D)).isEmpty();
/*      */         
/*  405 */         if (flag && (flag1 || !flag2)) {
/*      */           
/*  407 */           entity.setPositionAndRotation(d0, d1, d2, f, f1);
/*  408 */           this.netManager.sendPacket((Packet<?>)new SPacketMoveVehicle(entity));
/*      */           
/*      */           return;
/*      */         } 
/*  412 */         this.serverController.getPlayerList().serverUpdateMovingPlayer(this.playerEntity);
/*  413 */         this.playerEntity.addMovementStat(this.playerEntity.posX - d0, this.playerEntity.posY - d1, this.playerEntity.posZ - d2);
/*  414 */         this.vehicleFloating = (d11 >= -0.03125D && !this.serverController.isFlightAllowed() && !worldserver.checkBlockCollision(entity.getEntityBoundingBox().expandXyz(0.0625D).addCoord(0.0D, -0.55D, 0.0D)));
/*  415 */         this.lowestRiddenX1 = entity.posX;
/*  416 */         this.lowestRiddenY1 = entity.posY;
/*  417 */         this.lowestRiddenZ1 = entity.posZ;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void processConfirmTeleport(CPacketConfirmTeleport packetIn) {
/*  424 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/*      */     
/*  426 */     if (packetIn.getTeleportId() == this.teleportId) {
/*      */       
/*  428 */       this.playerEntity.setPositionAndRotation(this.targetPos.xCoord, this.targetPos.yCoord, this.targetPos.zCoord, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */       
/*  430 */       if (this.playerEntity.isInvulnerableDimensionChange()) {
/*      */         
/*  432 */         this.lastGoodX = this.targetPos.xCoord;
/*  433 */         this.lastGoodY = this.targetPos.yCoord;
/*  434 */         this.lastGoodZ = this.targetPos.zCoord;
/*  435 */         this.playerEntity.clearInvulnerableDimensionChange();
/*      */       } 
/*      */       
/*  438 */       this.targetPos = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_191984_a(CPacketRecipeInfo p_191984_1_) {
/*  444 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)p_191984_1_, this, (IThreadListener)this.playerEntity.getServerWorld());
/*      */     
/*  446 */     if (p_191984_1_.func_194156_a() == CPacketRecipeInfo.Purpose.SHOWN) {
/*      */       
/*  448 */       this.playerEntity.func_192037_E().func_194074_f(p_191984_1_.func_193648_b());
/*      */     }
/*  450 */     else if (p_191984_1_.func_194156_a() == CPacketRecipeInfo.Purpose.SETTINGS) {
/*      */       
/*  452 */       this.playerEntity.func_192037_E().func_192813_a(p_191984_1_.func_192624_c());
/*  453 */       this.playerEntity.func_192037_E().func_192810_b(p_191984_1_.func_192625_d());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_194027_a(CPacketSeenAdvancements p_194027_1_) {
/*  459 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)p_194027_1_, this, (IThreadListener)this.playerEntity.getServerWorld());
/*      */     
/*  461 */     if (p_194027_1_.func_194162_b() == CPacketSeenAdvancements.Action.OPENED_TAB) {
/*      */       
/*  463 */       ResourceLocation resourcelocation = p_194027_1_.func_194165_c();
/*  464 */       Advancement advancement = this.serverController.func_191949_aK().func_192778_a(resourcelocation);
/*      */       
/*  466 */       if (advancement != null)
/*      */       {
/*  468 */         this.playerEntity.func_192039_O().func_194220_a(advancement);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayer(CPacketPlayer packetIn) {
/*  478 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/*      */     
/*  480 */     if (isMovePlayerPacketInvalid(packetIn)) {
/*      */       
/*  482 */       func_194028_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.invalid_player_movement", new Object[0]));
/*      */     }
/*      */     else {
/*      */       
/*  486 */       WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*      */       
/*  488 */       if (!this.playerEntity.playerConqueredTheEnd) {
/*      */         
/*  490 */         if (this.networkTickCount == 0)
/*      */         {
/*  492 */           captureCurrentPosition();
/*      */         }
/*      */         
/*  495 */         if (this.targetPos != null) {
/*      */           
/*  497 */           if (this.networkTickCount - this.lastPositionUpdate > 20)
/*      */           {
/*  499 */             this.lastPositionUpdate = this.networkTickCount;
/*  500 */             setPlayerLocation(this.targetPos.xCoord, this.targetPos.yCoord, this.targetPos.zCoord, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  505 */           this.lastPositionUpdate = this.networkTickCount;
/*      */           
/*  507 */           if (this.playerEntity.isRiding()) {
/*      */             
/*  509 */             this.playerEntity.setPositionAndRotation(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ, packetIn.getYaw(this.playerEntity.rotationYaw), packetIn.getPitch(this.playerEntity.rotationPitch));
/*  510 */             this.serverController.getPlayerList().serverUpdateMovingPlayer(this.playerEntity);
/*      */           }
/*      */           else {
/*      */             
/*  514 */             double d0 = this.playerEntity.posX;
/*  515 */             double d1 = this.playerEntity.posY;
/*  516 */             double d2 = this.playerEntity.posZ;
/*  517 */             double d3 = this.playerEntity.posY;
/*  518 */             double d4 = packetIn.getX(this.playerEntity.posX);
/*  519 */             double d5 = packetIn.getY(this.playerEntity.posY);
/*  520 */             double d6 = packetIn.getZ(this.playerEntity.posZ);
/*  521 */             float f = packetIn.getYaw(this.playerEntity.rotationYaw);
/*  522 */             float f1 = packetIn.getPitch(this.playerEntity.rotationPitch);
/*  523 */             double d7 = d4 - this.firstGoodX;
/*  524 */             double d8 = d5 - this.firstGoodY;
/*  525 */             double d9 = d6 - this.firstGoodZ;
/*  526 */             double d10 = this.playerEntity.motionX * this.playerEntity.motionX + this.playerEntity.motionY * this.playerEntity.motionY + this.playerEntity.motionZ * this.playerEntity.motionZ;
/*  527 */             double d11 = d7 * d7 + d8 * d8 + d9 * d9;
/*      */             
/*  529 */             if (this.playerEntity.isPlayerSleeping()) {
/*      */               
/*  531 */               if (d11 > 1.0D)
/*      */               {
/*  533 */                 setPlayerLocation(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ, packetIn.getYaw(this.playerEntity.rotationYaw), packetIn.getPitch(this.playerEntity.rotationPitch));
/*      */               }
/*      */             }
/*      */             else {
/*      */               
/*  538 */               this.movePacketCounter++;
/*  539 */               int i = this.movePacketCounter - this.lastMovePacketCounter;
/*      */               
/*  541 */               if (i > 5) {
/*      */                 
/*  543 */                 LOGGER.debug("{} is sending move packets too frequently ({} packets since last tick)", this.playerEntity.getName(), Integer.valueOf(i));
/*  544 */                 i = 1;
/*      */               } 
/*      */               
/*  547 */               if (!this.playerEntity.isInvulnerableDimensionChange() && (!this.playerEntity.getServerWorld().getGameRules().getBoolean("disableElytraMovementCheck") || !this.playerEntity.isElytraFlying())) {
/*      */                 
/*  549 */                 float f2 = this.playerEntity.isElytraFlying() ? 300.0F : 100.0F;
/*      */                 
/*  551 */                 if (d11 - d10 > (f2 * i) && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(this.playerEntity.getName()))) {
/*      */                   
/*  553 */                   LOGGER.warn("{} moved too quickly! {},{},{}", this.playerEntity.getName(), Double.valueOf(d7), Double.valueOf(d8), Double.valueOf(d9));
/*  554 */                   setPlayerLocation(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */                   
/*      */                   return;
/*      */                 } 
/*      */               } 
/*  559 */               boolean flag2 = worldserver.getCollisionBoxes((Entity)this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(0.0625D)).isEmpty();
/*  560 */               d7 = d4 - this.lastGoodX;
/*  561 */               d8 = d5 - this.lastGoodY;
/*  562 */               d9 = d6 - this.lastGoodZ;
/*      */               
/*  564 */               if (this.playerEntity.onGround && !packetIn.isOnGround() && d8 > 0.0D)
/*      */               {
/*  566 */                 this.playerEntity.jump();
/*      */               }
/*      */               
/*  569 */               this.playerEntity.moveEntity(MoverType.PLAYER, d7, d8, d9);
/*  570 */               this.playerEntity.onGround = packetIn.isOnGround();
/*  571 */               double d12 = d8;
/*  572 */               d7 = d4 - this.playerEntity.posX;
/*  573 */               d8 = d5 - this.playerEntity.posY;
/*      */               
/*  575 */               if (d8 > -0.5D || d8 < 0.5D)
/*      */               {
/*  577 */                 d8 = 0.0D;
/*      */               }
/*      */               
/*  580 */               d9 = d6 - this.playerEntity.posZ;
/*  581 */               d11 = d7 * d7 + d8 * d8 + d9 * d9;
/*  582 */               boolean flag = false;
/*      */               
/*  584 */               if (!this.playerEntity.isInvulnerableDimensionChange() && d11 > 0.0625D && !this.playerEntity.isPlayerSleeping() && !this.playerEntity.interactionManager.isCreative() && this.playerEntity.interactionManager.getGameType() != GameType.SPECTATOR) {
/*      */                 
/*  586 */                 flag = true;
/*  587 */                 LOGGER.warn("{} moved wrongly!", this.playerEntity.getName());
/*      */               } 
/*      */               
/*  590 */               this.playerEntity.setPositionAndRotation(d4, d5, d6, f, f1);
/*  591 */               this.playerEntity.addMovementStat(this.playerEntity.posX - d0, this.playerEntity.posY - d1, this.playerEntity.posZ - d2);
/*      */               
/*  593 */               if (!this.playerEntity.noClip && !this.playerEntity.isPlayerSleeping()) {
/*      */                 
/*  595 */                 boolean flag1 = worldserver.getCollisionBoxes((Entity)this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(0.0625D)).isEmpty();
/*      */                 
/*  597 */                 if (flag2 && (flag || !flag1)) {
/*      */                   
/*  599 */                   setPlayerLocation(d0, d1, d2, f, f1);
/*      */                   
/*      */                   return;
/*      */                 } 
/*      */               } 
/*  604 */               this.floating = (d12 >= -0.03125D);
/*  605 */               this.floating &= (!this.serverController.isFlightAllowed() && !this.playerEntity.capabilities.allowFlying) ? 1 : 0;
/*  606 */               this.floating &= (!this.playerEntity.isPotionActive(MobEffects.LEVITATION) && !this.playerEntity.isElytraFlying() && !worldserver.checkBlockCollision(this.playerEntity.getEntityBoundingBox().expandXyz(0.0625D).addCoord(0.0D, -0.55D, 0.0D))) ? 1 : 0;
/*  607 */               this.playerEntity.onGround = packetIn.isOnGround();
/*  608 */               this.serverController.getPlayerList().serverUpdateMovingPlayer(this.playerEntity);
/*  609 */               this.playerEntity.handleFalling(this.playerEntity.posY - d3, packetIn.isOnGround());
/*  610 */               this.lastGoodX = this.playerEntity.posX;
/*  611 */               this.lastGoodY = this.playerEntity.posY;
/*  612 */               this.lastGoodZ = this.playerEntity.posZ;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlayerLocation(double x, double y, double z, float yaw, float pitch) {
/*  622 */     setPlayerLocation(x, y, z, yaw, pitch, Collections.emptySet());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlayerLocation(double x, double y, double z, float yaw, float pitch, Set<SPacketPlayerPosLook.EnumFlags> relativeSet) {
/*  627 */     double d0 = relativeSet.contains(SPacketPlayerPosLook.EnumFlags.X) ? this.playerEntity.posX : 0.0D;
/*  628 */     double d1 = relativeSet.contains(SPacketPlayerPosLook.EnumFlags.Y) ? this.playerEntity.posY : 0.0D;
/*  629 */     double d2 = relativeSet.contains(SPacketPlayerPosLook.EnumFlags.Z) ? this.playerEntity.posZ : 0.0D;
/*  630 */     this.targetPos = new Vec3d(x + d0, y + d1, z + d2);
/*  631 */     float f = yaw;
/*  632 */     float f1 = pitch;
/*      */     
/*  634 */     if (relativeSet.contains(SPacketPlayerPosLook.EnumFlags.Y_ROT))
/*      */     {
/*  636 */       f = yaw + this.playerEntity.rotationYaw;
/*      */     }
/*      */     
/*  639 */     if (relativeSet.contains(SPacketPlayerPosLook.EnumFlags.X_ROT))
/*      */     {
/*  641 */       f1 = pitch + this.playerEntity.rotationPitch;
/*      */     }
/*      */     
/*  644 */     if (++this.teleportId == Integer.MAX_VALUE)
/*      */     {
/*  646 */       this.teleportId = 0;
/*      */     }
/*      */     
/*  649 */     this.lastPositionUpdate = this.networkTickCount;
/*  650 */     this.playerEntity.setPositionAndRotation(this.targetPos.xCoord, this.targetPos.yCoord, this.targetPos.zCoord, f, f1);
/*  651 */     this.playerEntity.connection.sendPacket((Packet<?>)new SPacketPlayerPosLook(x, y, z, yaw, pitch, relativeSet, this.teleportId));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerDigging(CPacketPlayerDigging packetIn) {
/*      */     double d0, d1, d2, d3;
/*  661 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/*  662 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  663 */     BlockPos blockpos = packetIn.getPosition();
/*  664 */     this.playerEntity.markPlayerActive();
/*      */     
/*  666 */     switch (packetIn.getAction()) {
/*      */       
/*      */       case SWAP_HELD_ITEMS:
/*  669 */         if (!this.playerEntity.isSpectator()) {
/*      */           
/*  671 */           ItemStack itemstack = this.playerEntity.getHeldItem(EnumHand.OFF_HAND);
/*  672 */           this.playerEntity.setHeldItem(EnumHand.OFF_HAND, this.playerEntity.getHeldItem(EnumHand.MAIN_HAND));
/*  673 */           this.playerEntity.setHeldItem(EnumHand.MAIN_HAND, itemstack);
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case DROP_ITEM:
/*  679 */         if (!this.playerEntity.isSpectator())
/*      */         {
/*  681 */           this.playerEntity.dropItem(false);
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case DROP_ALL_ITEMS:
/*  687 */         if (!this.playerEntity.isSpectator())
/*      */         {
/*  689 */           this.playerEntity.dropItem(true);
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case RELEASE_USE_ITEM:
/*  695 */         this.playerEntity.stopActiveHand();
/*      */         return;
/*      */       
/*      */       case START_DESTROY_BLOCK:
/*      */       case null:
/*      */       case STOP_DESTROY_BLOCK:
/*  701 */         d0 = this.playerEntity.posX - blockpos.getX() + 0.5D;
/*  702 */         d1 = this.playerEntity.posY - blockpos.getY() + 0.5D + 1.5D;
/*  703 */         d2 = this.playerEntity.posZ - blockpos.getZ() + 0.5D;
/*  704 */         d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */         
/*  706 */         if (d3 > 36.0D) {
/*      */           return;
/*      */         }
/*      */         
/*  710 */         if (blockpos.getY() >= this.serverController.getBuildLimit()) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  716 */         if (packetIn.getAction() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK) {
/*      */           
/*  718 */           if (!this.serverController.isBlockProtected((World)worldserver, blockpos, (EntityPlayer)this.playerEntity) && worldserver.getWorldBorder().contains(blockpos))
/*      */           {
/*  720 */             this.playerEntity.interactionManager.onBlockClicked(blockpos, packetIn.getFacing());
/*      */           }
/*      */           else
/*      */           {
/*  724 */             this.playerEntity.connection.sendPacket((Packet<?>)new SPacketBlockChange((World)worldserver, blockpos));
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  729 */           if (packetIn.getAction() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
/*      */             
/*  731 */             this.playerEntity.interactionManager.blockRemoving(blockpos);
/*      */           }
/*  733 */           else if (packetIn.getAction() == CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
/*      */             
/*  735 */             this.playerEntity.interactionManager.cancelDestroyingBlock();
/*      */           } 
/*      */           
/*  738 */           if (worldserver.getBlockState(blockpos).getMaterial() != Material.AIR)
/*      */           {
/*  740 */             this.playerEntity.connection.sendPacket((Packet<?>)new SPacketBlockChange((World)worldserver, blockpos));
/*      */           }
/*      */         } 
/*      */         return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  748 */     throw new IllegalArgumentException("Invalid player action");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void processRightClickBlock(CPacketPlayerTryUseItemOnBlock packetIn) {
/*  754 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/*  755 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  756 */     EnumHand enumhand = packetIn.getHand();
/*  757 */     ItemStack itemstack = this.playerEntity.getHeldItem(enumhand);
/*  758 */     BlockPos blockpos = packetIn.getPos();
/*  759 */     EnumFacing enumfacing = packetIn.getDirection();
/*  760 */     this.playerEntity.markPlayerActive();
/*      */     
/*  762 */     if (blockpos.getY() < this.serverController.getBuildLimit() - 1 || (enumfacing != EnumFacing.UP && blockpos.getY() < this.serverController.getBuildLimit())) {
/*      */       
/*  764 */       if (this.targetPos == null && this.playerEntity.getDistanceSq(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D) < 64.0D && !this.serverController.isBlockProtected((World)worldserver, blockpos, (EntityPlayer)this.playerEntity) && worldserver.getWorldBorder().contains(blockpos))
/*      */       {
/*  766 */         this.playerEntity.interactionManager.processRightClickBlock((EntityPlayer)this.playerEntity, (World)worldserver, itemstack, enumhand, blockpos, enumfacing, packetIn.getFacingX(), packetIn.getFacingY(), packetIn.getFacingZ());
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  771 */       TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("build.tooHigh", new Object[] { Integer.valueOf(this.serverController.getBuildLimit()) });
/*  772 */       textcomponenttranslation.getStyle().setColor(TextFormatting.RED);
/*  773 */       this.playerEntity.connection.sendPacket((Packet<?>)new SPacketChat((ITextComponent)textcomponenttranslation, ChatType.GAME_INFO));
/*      */     } 
/*      */     
/*  776 */     this.playerEntity.connection.sendPacket((Packet<?>)new SPacketBlockChange((World)worldserver, blockpos));
/*  777 */     this.playerEntity.connection.sendPacket((Packet<?>)new SPacketBlockChange((World)worldserver, blockpos.offset(enumfacing)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerBlockPlacement(CPacketPlayerTryUseItem packetIn) {
/*  785 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/*  786 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  787 */     EnumHand enumhand = packetIn.getHand();
/*  788 */     ItemStack itemstack = this.playerEntity.getHeldItem(enumhand);
/*  789 */     this.playerEntity.markPlayerActive();
/*      */     
/*  791 */     if (!itemstack.func_190926_b())
/*      */     {
/*  793 */       this.playerEntity.interactionManager.processRightClick((EntityPlayer)this.playerEntity, (World)worldserver, itemstack, enumhand);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSpectate(CPacketSpectate packetIn) {
/*  799 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/*      */     
/*  801 */     if (this.playerEntity.isSpectator()) {
/*      */       
/*  803 */       Entity entity = null; byte b; int i;
/*      */       WorldServer[] arrayOfWorldServer;
/*  805 */       for (i = (arrayOfWorldServer = this.serverController.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/*      */         
/*  807 */         if (worldserver != null) {
/*      */           
/*  809 */           entity = packetIn.getEntity(worldserver);
/*      */           
/*  811 */           if (entity != null) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */         
/*      */         b++; }
/*      */       
/*  818 */       if (entity != null) {
/*      */         
/*  820 */         this.playerEntity.setSpectatingEntity((Entity)this.playerEntity);
/*  821 */         this.playerEntity.dismountRidingEntity();
/*      */         
/*  823 */         if (entity.world == this.playerEntity.world) {
/*      */           
/*  825 */           this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
/*      */         }
/*      */         else {
/*      */           
/*  829 */           WorldServer worldserver1 = this.playerEntity.getServerWorld();
/*  830 */           WorldServer worldserver2 = (WorldServer)entity.world;
/*  831 */           this.playerEntity.dimension = entity.dimension;
/*  832 */           sendPacket((Packet<?>)new SPacketRespawn(this.playerEntity.dimension, worldserver1.getDifficulty(), worldserver1.getWorldInfo().getTerrainType(), this.playerEntity.interactionManager.getGameType()));
/*  833 */           this.serverController.getPlayerList().updatePermissionLevel(this.playerEntity);
/*  834 */           worldserver1.removeEntityDangerously((Entity)this.playerEntity);
/*  835 */           this.playerEntity.isDead = false;
/*  836 */           this.playerEntity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
/*      */           
/*  838 */           if (this.playerEntity.isEntityAlive()) {
/*      */             
/*  840 */             worldserver1.updateEntityWithOptionalForce((Entity)this.playerEntity, false);
/*  841 */             worldserver2.spawnEntityInWorld((Entity)this.playerEntity);
/*  842 */             worldserver2.updateEntityWithOptionalForce((Entity)this.playerEntity, false);
/*      */           } 
/*      */           
/*  845 */           this.playerEntity.setWorld((World)worldserver2);
/*  846 */           this.serverController.getPlayerList().preparePlayer(this.playerEntity, worldserver1);
/*  847 */           this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
/*  848 */           this.playerEntity.interactionManager.setWorld(worldserver2);
/*  849 */           this.serverController.getPlayerList().updateTimeAndWeatherForPlayer(this.playerEntity, worldserver2);
/*  850 */           this.serverController.getPlayerList().syncPlayerInventory(this.playerEntity);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleResourcePackStatus(CPacketResourcePackStatus packetIn) {}
/*      */ 
/*      */   
/*      */   public void processSteerBoat(CPacketSteerBoat packetIn) {
/*  862 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/*  863 */     Entity entity = this.playerEntity.getRidingEntity();
/*      */     
/*  865 */     if (entity instanceof EntityBoat)
/*      */     {
/*  867 */       ((EntityBoat)entity).setPaddleState(packetIn.getLeft(), packetIn.getRight());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDisconnect(ITextComponent reason) {
/*  876 */     LOGGER.info("{} lost connection: {}", this.playerEntity.getName(), reason.getUnformattedText());
/*  877 */     this.serverController.refreshStatusNextTick();
/*  878 */     TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("multiplayer.player.left", new Object[] { this.playerEntity.getDisplayName() });
/*  879 */     textcomponenttranslation.getStyle().setColor(TextFormatting.YELLOW);
/*  880 */     this.serverController.getPlayerList().sendChatMsg((ITextComponent)textcomponenttranslation);
/*  881 */     this.playerEntity.mountEntityAndWakeUp();
/*  882 */     this.serverController.getPlayerList().playerLoggedOut(this.playerEntity);
/*      */     
/*  884 */     if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
/*      */       
/*  886 */       LOGGER.info("Stopping singleplayer server as player logged out");
/*  887 */       this.serverController.initiateShutdown();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPacket(final Packet<?> packetIn) {
/*  893 */     if (packetIn instanceof SPacketChat) {
/*      */       
/*  895 */       SPacketChat spacketchat = (SPacketChat)packetIn;
/*  896 */       EntityPlayer.EnumChatVisibility entityplayer$enumchatvisibility = this.playerEntity.getChatVisibility();
/*      */       
/*  898 */       if (entityplayer$enumchatvisibility == EntityPlayer.EnumChatVisibility.HIDDEN && spacketchat.func_192590_c() != ChatType.GAME_INFO) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  903 */       if (entityplayer$enumchatvisibility == EntityPlayer.EnumChatVisibility.SYSTEM && !spacketchat.isSystem()) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  911 */       this.netManager.sendPacket(packetIn);
/*      */     }
/*  913 */     catch (Throwable throwable) {
/*      */       
/*  915 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Sending packet");
/*  916 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Packet being sent");
/*  917 */       crashreportcategory.setDetail("Packet class", new ICrashReportDetail<String>()
/*      */           {
/*      */             public String call() throws Exception
/*      */             {
/*  921 */               return packetIn.getClass().getCanonicalName();
/*      */             }
/*      */           });
/*  924 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processHeldItemChange(CPacketHeldItemChange packetIn) {
/*  933 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/*      */     
/*  935 */     if (packetIn.getSlotId() >= 0 && packetIn.getSlotId() < InventoryPlayer.getHotbarSize()) {
/*      */       
/*  937 */       this.playerEntity.inventory.currentItem = packetIn.getSlotId();
/*  938 */       this.playerEntity.markPlayerActive();
/*      */     }
/*      */     else {
/*      */       
/*  942 */       LOGGER.warn("{} tried to set an invalid carried item", this.playerEntity.getName());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processChatMessage(CPacketChatMessage packetIn) {
/*  951 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/*      */     
/*  953 */     if (this.playerEntity.getChatVisibility() == EntityPlayer.EnumChatVisibility.HIDDEN) {
/*      */       
/*  955 */       TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("chat.cannotSend", new Object[0]);
/*  956 */       textcomponenttranslation.getStyle().setColor(TextFormatting.RED);
/*  957 */       sendPacket((Packet<?>)new SPacketChat((ITextComponent)textcomponenttranslation));
/*      */     }
/*      */     else {
/*      */       
/*  961 */       this.playerEntity.markPlayerActive();
/*  962 */       String s = packetIn.getMessage();
/*  963 */       s = StringUtils.normalizeSpace(s);
/*      */       
/*  965 */       for (int i = 0; i < s.length(); i++) {
/*      */         
/*  967 */         if (!ChatAllowedCharacters.isAllowedCharacter(s.charAt(i))) {
/*      */           
/*  969 */           func_194028_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.illegal_characters", new Object[0]));
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*  974 */       if (s.startsWith("/")) {
/*      */         
/*  976 */         handleSlashCommand(s);
/*      */       }
/*      */       else {
/*      */         
/*  980 */         TextComponentTranslation textComponentTranslation = new TextComponentTranslation("chat.type.text", new Object[] { this.playerEntity.getDisplayName(), s });
/*  981 */         this.serverController.getPlayerList().sendChatMsgImpl((ITextComponent)textComponentTranslation, false);
/*      */       } 
/*      */       
/*  984 */       this.chatSpamThresholdCount += 20;
/*      */       
/*  986 */       if (this.chatSpamThresholdCount > 200 && !this.serverController.getPlayerList().canSendCommands(this.playerEntity.getGameProfile()))
/*      */       {
/*  988 */         func_194028_b((ITextComponent)new TextComponentTranslation("disconnect.spam", new Object[0]));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleSlashCommand(String command) {
/*  998 */     this.serverController.getCommandManager().executeCommand((ICommandSender)this.playerEntity, command);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleAnimation(CPacketAnimation packetIn) {
/* 1003 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/* 1004 */     this.playerEntity.markPlayerActive();
/* 1005 */     this.playerEntity.swingArm(packetIn.getHand());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processEntityAction(CPacketEntityAction packetIn) {
/* 1014 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/* 1015 */     this.playerEntity.markPlayerActive();
/*      */     
/* 1017 */     switch (packetIn.getAction()) {
/*      */       
/*      */       case START_SNEAKING:
/* 1020 */         this.playerEntity.setSneaking(true);
/*      */         return;
/*      */       
/*      */       case STOP_SNEAKING:
/* 1024 */         this.playerEntity.setSneaking(false);
/*      */         return;
/*      */       
/*      */       case START_SPRINTING:
/* 1028 */         this.playerEntity.setSprinting(true);
/*      */         return;
/*      */       
/*      */       case STOP_SPRINTING:
/* 1032 */         this.playerEntity.setSprinting(false);
/*      */         return;
/*      */       
/*      */       case STOP_SLEEPING:
/* 1036 */         if (this.playerEntity.isPlayerSleeping()) {
/*      */           
/* 1038 */           this.playerEntity.wakeUpPlayer(false, true, true);
/* 1039 */           this.targetPos = new Vec3d(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ);
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case START_RIDING_JUMP:
/* 1045 */         if (this.playerEntity.getRidingEntity() instanceof IJumpingMount) {
/*      */           
/* 1047 */           IJumpingMount ijumpingmount1 = (IJumpingMount)this.playerEntity.getRidingEntity();
/* 1048 */           int i = packetIn.getAuxData();
/*      */           
/* 1050 */           if (ijumpingmount1.canJump() && i > 0)
/*      */           {
/* 1052 */             ijumpingmount1.handleStartJump(i);
/*      */           }
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case STOP_RIDING_JUMP:
/* 1059 */         if (this.playerEntity.getRidingEntity() instanceof IJumpingMount) {
/*      */           
/* 1061 */           IJumpingMount ijumpingmount = (IJumpingMount)this.playerEntity.getRidingEntity();
/* 1062 */           ijumpingmount.handleStopJump();
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case null:
/* 1068 */         if (this.playerEntity.getRidingEntity() instanceof AbstractHorse)
/*      */         {
/* 1070 */           ((AbstractHorse)this.playerEntity.getRidingEntity()).openGUI((EntityPlayer)this.playerEntity);
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case START_FALL_FLYING:
/* 1076 */         if (!this.playerEntity.onGround && this.playerEntity.motionY < 0.0D && !this.playerEntity.isElytraFlying() && !this.playerEntity.isInWater()) {
/*      */           
/* 1078 */           ItemStack itemstack = this.playerEntity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
/*      */           
/* 1080 */           if (itemstack.getItem() == Items.ELYTRA && ItemElytra.isBroken(itemstack))
/*      */           {
/* 1082 */             this.playerEntity.setElytraFlying();
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/* 1087 */           this.playerEntity.clearElytraFlying();
/*      */         } 
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/* 1093 */     throw new IllegalArgumentException("Invalid client command!");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processUseEntity(CPacketUseEntity packetIn) {
/* 1103 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/* 1104 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/* 1105 */     Entity entity = packetIn.getEntityFromWorld((World)worldserver);
/* 1106 */     this.playerEntity.markPlayerActive();
/*      */     
/* 1108 */     if (entity != null) {
/*      */       
/* 1110 */       boolean flag = this.playerEntity.canEntityBeSeen(entity);
/* 1111 */       double d0 = 36.0D;
/*      */       
/* 1113 */       if (!flag)
/*      */       {
/* 1115 */         d0 = 9.0D;
/*      */       }
/*      */       
/* 1118 */       if (this.playerEntity.getDistanceSqToEntity(entity) < d0)
/*      */       {
/* 1120 */         if (packetIn.getAction() == CPacketUseEntity.Action.INTERACT) {
/*      */           
/* 1122 */           EnumHand enumhand = packetIn.getHand();
/* 1123 */           this.playerEntity.func_190775_a(entity, enumhand);
/*      */         }
/* 1125 */         else if (packetIn.getAction() == CPacketUseEntity.Action.INTERACT_AT) {
/*      */           
/* 1127 */           EnumHand enumhand1 = packetIn.getHand();
/* 1128 */           entity.applyPlayerInteraction((EntityPlayer)this.playerEntity, packetIn.getHitVec(), enumhand1);
/*      */         }
/* 1130 */         else if (packetIn.getAction() == CPacketUseEntity.Action.ATTACK) {
/*      */           
/* 1132 */           if (entity instanceof EntityItem || entity instanceof net.minecraft.entity.item.EntityXPOrb || entity instanceof net.minecraft.entity.projectile.EntityArrow || entity == this.playerEntity) {
/*      */             
/* 1134 */             func_194028_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.invalid_entity_attacked", new Object[0]));
/* 1135 */             this.serverController.logWarning("Player " + this.playerEntity.getName() + " tried to attack an invalid entity");
/*      */             
/*      */             return;
/*      */           } 
/* 1139 */           this.playerEntity.attackTargetEntityWithCurrentItem(entity);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processClientStatus(CPacketClientStatus packetIn) {
/* 1151 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/* 1152 */     this.playerEntity.markPlayerActive();
/* 1153 */     CPacketClientStatus.State cpacketclientstatus$state = packetIn.getStatus();
/*      */     
/* 1155 */     switch (cpacketclientstatus$state) {
/*      */       
/*      */       case null:
/* 1158 */         if (this.playerEntity.playerConqueredTheEnd) {
/*      */           
/* 1160 */           this.playerEntity.playerConqueredTheEnd = false;
/* 1161 */           this.playerEntity = this.serverController.getPlayerList().recreatePlayerEntity(this.playerEntity, 0, true);
/* 1162 */           CriteriaTriggers.field_193134_u.func_193143_a(this.playerEntity, DimensionType.THE_END, DimensionType.OVERWORLD);
/*      */           
/*      */           break;
/*      */         } 
/* 1166 */         if (this.playerEntity.getHealth() > 0.0F) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1171 */         this.playerEntity = this.serverController.getPlayerList().recreatePlayerEntity(this.playerEntity, 0, false);
/*      */         
/* 1173 */         if (this.serverController.isHardcore()) {
/*      */           
/* 1175 */           this.playerEntity.setGameType(GameType.SPECTATOR);
/* 1176 */           this.playerEntity.getServerWorld().getGameRules().setOrCreateGameRule("spectatorsGenerateChunks", "false");
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case REQUEST_STATS:
/* 1183 */         this.playerEntity.getStatFile().sendStats(this.playerEntity);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processCloseWindow(CPacketCloseWindow packetIn) {
/* 1192 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/* 1193 */     this.playerEntity.closeContainer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processClickWindow(CPacketClickWindow packetIn) {
/* 1203 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/* 1204 */     this.playerEntity.markPlayerActive();
/*      */     
/* 1206 */     if (this.playerEntity.openContainer.windowId == packetIn.getWindowId() && this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity))
/*      */     {
/* 1208 */       if (this.playerEntity.isSpectator()) {
/*      */         
/* 1210 */         NonNullList<ItemStack> nonnulllist = NonNullList.func_191196_a();
/*      */         
/* 1212 */         for (int i = 0; i < this.playerEntity.openContainer.inventorySlots.size(); i++)
/*      */         {
/* 1214 */           nonnulllist.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(i)).getStack());
/*      */         }
/*      */         
/* 1217 */         this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, nonnulllist);
/*      */       }
/*      */       else {
/*      */         
/* 1221 */         ItemStack itemstack2 = this.playerEntity.openContainer.slotClick(packetIn.getSlotId(), packetIn.getUsedButton(), packetIn.getClickType(), (EntityPlayer)this.playerEntity);
/*      */         
/* 1223 */         if (ItemStack.areItemStacksEqual(packetIn.getClickedItem(), itemstack2)) {
/*      */           
/* 1225 */           this.playerEntity.connection.sendPacket((Packet<?>)new SPacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), true));
/* 1226 */           this.playerEntity.isChangingQuantityOnly = true;
/* 1227 */           this.playerEntity.openContainer.detectAndSendChanges();
/* 1228 */           this.playerEntity.updateHeldItem();
/* 1229 */           this.playerEntity.isChangingQuantityOnly = false;
/*      */         }
/*      */         else {
/*      */           
/* 1233 */           this.pendingTransactions.addKey(this.playerEntity.openContainer.windowId, Short.valueOf(packetIn.getActionNumber()));
/* 1234 */           this.playerEntity.connection.sendPacket((Packet<?>)new SPacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), false));
/* 1235 */           this.playerEntity.openContainer.setCanCraft((EntityPlayer)this.playerEntity, false);
/* 1236 */           NonNullList<ItemStack> nonnulllist1 = NonNullList.func_191196_a();
/*      */           
/* 1238 */           for (int j = 0; j < this.playerEntity.openContainer.inventorySlots.size(); j++) {
/*      */             
/* 1240 */             ItemStack itemstack = ((Slot)this.playerEntity.openContainer.inventorySlots.get(j)).getStack();
/* 1241 */             ItemStack itemstack1 = itemstack.func_190926_b() ? ItemStack.field_190927_a : itemstack;
/* 1242 */             nonnulllist1.add(itemstack1);
/*      */           } 
/*      */           
/* 1245 */           this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, nonnulllist1);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_194308_a(CPacketPlaceRecipe p_194308_1_) {
/* 1253 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)p_194308_1_, this, (IThreadListener)this.playerEntity.getServerWorld());
/* 1254 */     this.playerEntity.markPlayerActive();
/*      */     
/* 1256 */     if (!this.playerEntity.isSpectator() && this.playerEntity.openContainer.windowId == p_194308_1_.func_194318_a() && this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity))
/*      */     {
/* 1258 */       this.field_194309_H.func_194327_a(this.playerEntity, p_194308_1_.func_194317_b(), p_194308_1_.func_194319_c());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processEnchantItem(CPacketEnchantItem packetIn) {
/* 1268 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/* 1269 */     this.playerEntity.markPlayerActive();
/*      */     
/* 1271 */     if (this.playerEntity.openContainer.windowId == packetIn.getWindowId() && this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity) && !this.playerEntity.isSpectator()) {
/*      */       
/* 1273 */       this.playerEntity.openContainer.enchantItem((EntityPlayer)this.playerEntity, packetIn.getButton());
/* 1274 */       this.playerEntity.openContainer.detectAndSendChanges();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processCreativeInventoryAction(CPacketCreativeInventoryAction packetIn) {
/* 1283 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/*      */     
/* 1285 */     if (this.playerEntity.interactionManager.isCreative()) {
/*      */       
/* 1287 */       boolean flag = (packetIn.getSlotId() < 0);
/* 1288 */       ItemStack itemstack = packetIn.getStack();
/*      */       
/* 1290 */       if (!itemstack.func_190926_b() && itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("BlockEntityTag", 10)) {
/*      */         
/* 1292 */         NBTTagCompound nbttagcompound = itemstack.getTagCompound().getCompoundTag("BlockEntityTag");
/*      */         
/* 1294 */         if (nbttagcompound.hasKey("x") && nbttagcompound.hasKey("y") && nbttagcompound.hasKey("z")) {
/*      */           
/* 1296 */           BlockPos blockpos = new BlockPos(nbttagcompound.getInteger("x"), nbttagcompound.getInteger("y"), nbttagcompound.getInteger("z"));
/* 1297 */           TileEntity tileentity = this.playerEntity.world.getTileEntity(blockpos);
/*      */           
/* 1299 */           if (tileentity != null) {
/*      */             
/* 1301 */             NBTTagCompound nbttagcompound1 = tileentity.writeToNBT(new NBTTagCompound());
/* 1302 */             nbttagcompound1.removeTag("x");
/* 1303 */             nbttagcompound1.removeTag("y");
/* 1304 */             nbttagcompound1.removeTag("z");
/* 1305 */             itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1310 */       boolean flag1 = (packetIn.getSlotId() >= 1 && packetIn.getSlotId() <= 45);
/* 1311 */       boolean flag2 = !(!itemstack.func_190926_b() && (itemstack.getMetadata() < 0 || itemstack.func_190916_E() > 64 || itemstack.func_190926_b()));
/*      */       
/* 1313 */       if (flag1 && flag2) {
/*      */         
/* 1315 */         if (itemstack.func_190926_b()) {
/*      */           
/* 1317 */           this.playerEntity.inventoryContainer.putStackInSlot(packetIn.getSlotId(), ItemStack.field_190927_a);
/*      */         }
/*      */         else {
/*      */           
/* 1321 */           this.playerEntity.inventoryContainer.putStackInSlot(packetIn.getSlotId(), itemstack);
/*      */         } 
/*      */         
/* 1324 */         this.playerEntity.inventoryContainer.setCanCraft((EntityPlayer)this.playerEntity, true);
/*      */       }
/* 1326 */       else if (flag && flag2 && this.itemDropThreshold < 200) {
/*      */         
/* 1328 */         this.itemDropThreshold += 20;
/* 1329 */         EntityItem entityitem = this.playerEntity.dropItem(itemstack, true);
/*      */         
/* 1331 */         if (entityitem != null)
/*      */         {
/* 1333 */           entityitem.setAgeToCreativeDespawnTime();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processConfirmTransaction(CPacketConfirmTransaction packetIn) {
/* 1346 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/* 1347 */     Short oshort = (Short)this.pendingTransactions.lookup(this.playerEntity.openContainer.windowId);
/*      */     
/* 1349 */     if (oshort != null && packetIn.getUid() == oshort.shortValue() && this.playerEntity.openContainer.windowId == packetIn.getWindowId() && !this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity) && !this.playerEntity.isSpectator())
/*      */     {
/* 1351 */       this.playerEntity.openContainer.setCanCraft((EntityPlayer)this.playerEntity, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void processUpdateSign(CPacketUpdateSign packetIn) {
/* 1357 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/* 1358 */     this.playerEntity.markPlayerActive();
/* 1359 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/* 1360 */     BlockPos blockpos = packetIn.getPosition();
/*      */     
/* 1362 */     if (worldserver.isBlockLoaded(blockpos)) {
/*      */       
/* 1364 */       IBlockState iblockstate = worldserver.getBlockState(blockpos);
/* 1365 */       TileEntity tileentity = worldserver.getTileEntity(blockpos);
/*      */       
/* 1367 */       if (!(tileentity instanceof TileEntitySign)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1372 */       TileEntitySign tileentitysign = (TileEntitySign)tileentity;
/*      */       
/* 1374 */       if (!tileentitysign.getIsEditable() || tileentitysign.getPlayer() != this.playerEntity) {
/*      */         
/* 1376 */         this.serverController.logWarning("Player " + this.playerEntity.getName() + " just tried to change non-editable sign");
/*      */         
/*      */         return;
/*      */       } 
/* 1380 */       String[] astring = packetIn.getLines();
/*      */       
/* 1382 */       for (int i = 0; i < astring.length; i++)
/*      */       {
/* 1384 */         tileentitysign.signText[i] = (ITextComponent)new TextComponentString(TextFormatting.getTextWithoutFormattingCodes(astring[i]));
/*      */       }
/*      */       
/* 1387 */       tileentitysign.markDirty();
/* 1388 */       worldserver.notifyBlockUpdate(blockpos, iblockstate, iblockstate, 3);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processKeepAlive(CPacketKeepAlive packetIn) {
/* 1397 */     if (this.field_194403_g && packetIn.getKey() == this.field_194404_h) {
/*      */       
/* 1399 */       int i = (int)(currentTimeMillis() - this.field_194402_f);
/* 1400 */       this.playerEntity.ping = (this.playerEntity.ping * 3 + i) / 4;
/* 1401 */       this.field_194403_g = false;
/*      */     }
/* 1403 */     else if (!this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
/*      */       
/* 1405 */       func_194028_b((ITextComponent)new TextComponentTranslation("disconnect.timeout", new Object[0]));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private long currentTimeMillis() {
/* 1411 */     return System.nanoTime() / 1000000L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerAbilities(CPacketPlayerAbilities packetIn) {
/* 1419 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/* 1420 */     this.playerEntity.capabilities.isFlying = (packetIn.isFlying() && this.playerEntity.capabilities.allowFlying);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processTabComplete(CPacketTabComplete packetIn) {
/* 1428 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/* 1429 */     List<String> list = Lists.newArrayList();
/*      */     
/* 1431 */     for (String s : this.serverController.getTabCompletions((ICommandSender)this.playerEntity, packetIn.getMessage(), packetIn.getTargetBlock(), packetIn.hasTargetBlock()))
/*      */     {
/* 1433 */       list.add(s);
/*      */     }
/*      */     
/* 1436 */     this.playerEntity.connection.sendPacket((Packet<?>)new SPacketTabComplete(list.<String>toArray(new String[list.size()])));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processClientSettings(CPacketClientSettings packetIn) {
/* 1445 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/* 1446 */     this.playerEntity.handleClientSettings(packetIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processCustomPayload(CPacketCustomPayload packetIn) {
/* 1454 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerWorld());
/* 1455 */     String s = packetIn.getChannelName();
/*      */     
/* 1457 */     if ("MC|BEdit".equals(s)) {
/*      */       
/* 1459 */       PacketBuffer packetbuffer = packetIn.getBufferData();
/*      */ 
/*      */       
/*      */       try {
/* 1463 */         ItemStack itemstack = packetbuffer.readItemStackFromBuffer();
/*      */         
/* 1465 */         if (itemstack.func_190926_b()) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1470 */         if (!ItemWritableBook.isNBTValid(itemstack.getTagCompound()))
/*      */         {
/* 1472 */           throw new IOException("Invalid book tag!");
/*      */         }
/*      */         
/* 1475 */         ItemStack itemstack1 = this.playerEntity.getHeldItemMainhand();
/*      */         
/* 1477 */         if (itemstack1.func_190926_b()) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1482 */         if (itemstack.getItem() == Items.WRITABLE_BOOK && itemstack.getItem() == itemstack1.getItem())
/*      */         {
/* 1484 */           itemstack1.setTagInfo("pages", (NBTBase)itemstack.getTagCompound().getTagList("pages", 8));
/*      */         }
/*      */       }
/* 1487 */       catch (Exception exception6) {
/*      */         
/* 1489 */         LOGGER.error("Couldn't handle book info", exception6);
/*      */       }
/*      */     
/* 1492 */     } else if ("MC|BSign".equals(s)) {
/*      */       
/* 1494 */       PacketBuffer packetbuffer1 = packetIn.getBufferData();
/*      */ 
/*      */       
/*      */       try {
/* 1498 */         ItemStack itemstack3 = packetbuffer1.readItemStackFromBuffer();
/*      */         
/* 1500 */         if (itemstack3.func_190926_b()) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1505 */         if (!ItemWrittenBook.validBookTagContents(itemstack3.getTagCompound()))
/*      */         {
/* 1507 */           throw new IOException("Invalid book tag!");
/*      */         }
/*      */         
/* 1510 */         ItemStack itemstack4 = this.playerEntity.getHeldItemMainhand();
/*      */         
/* 1512 */         if (itemstack4.func_190926_b()) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1517 */         if (itemstack3.getItem() == Items.WRITABLE_BOOK && itemstack4.getItem() == Items.WRITABLE_BOOK)
/*      */         {
/* 1519 */           ItemStack itemstack2 = new ItemStack(Items.WRITTEN_BOOK);
/* 1520 */           itemstack2.setTagInfo("author", (NBTBase)new NBTTagString(this.playerEntity.getName()));
/* 1521 */           itemstack2.setTagInfo("title", (NBTBase)new NBTTagString(itemstack3.getTagCompound().getString("title")));
/* 1522 */           NBTTagList nbttaglist = itemstack3.getTagCompound().getTagList("pages", 8);
/*      */           
/* 1524 */           for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */             
/* 1526 */             String s1 = nbttaglist.getStringTagAt(i);
/* 1527 */             TextComponentString textComponentString = new TextComponentString(s1);
/* 1528 */             s1 = ITextComponent.Serializer.componentToJson((ITextComponent)textComponentString);
/* 1529 */             nbttaglist.set(i, (NBTBase)new NBTTagString(s1));
/*      */           } 
/*      */           
/* 1532 */           itemstack2.setTagInfo("pages", (NBTBase)nbttaglist);
/* 1533 */           this.playerEntity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, itemstack2);
/*      */         }
/*      */       
/* 1536 */       } catch (Exception exception7) {
/*      */         
/* 1538 */         LOGGER.error("Couldn't sign book", exception7);
/*      */       }
/*      */     
/* 1541 */     } else if ("MC|TrSel".equals(s)) {
/*      */       
/*      */       try
/*      */       {
/* 1545 */         int k = packetIn.getBufferData().readInt();
/* 1546 */         Container container = this.playerEntity.openContainer;
/*      */         
/* 1548 */         if (container instanceof ContainerMerchant)
/*      */         {
/* 1550 */           ((ContainerMerchant)container).setCurrentRecipeIndex(k);
/*      */         }
/*      */       }
/* 1553 */       catch (Exception exception5)
/*      */       {
/* 1555 */         LOGGER.error("Couldn't select trade", exception5);
/*      */       }
/*      */     
/* 1558 */     } else if ("MC|AdvCmd".equals(s)) {
/*      */       
/* 1560 */       if (!this.serverController.isCommandBlockEnabled()) {
/*      */         
/* 1562 */         this.playerEntity.addChatMessage((ITextComponent)new TextComponentTranslation("advMode.notEnabled", new Object[0]));
/*      */         
/*      */         return;
/*      */       } 
/* 1566 */       if (!this.playerEntity.canUseCommandBlock()) {
/*      */         
/* 1568 */         this.playerEntity.addChatMessage((ITextComponent)new TextComponentTranslation("advMode.notAllowed", new Object[0]));
/*      */         
/*      */         return;
/*      */       } 
/* 1572 */       PacketBuffer packetbuffer2 = packetIn.getBufferData();
/*      */ 
/*      */       
/*      */       try {
/* 1576 */         int l = packetbuffer2.readByte();
/* 1577 */         CommandBlockBaseLogic commandblockbaselogic1 = null;
/*      */         
/* 1579 */         if (l == 0) {
/*      */           
/* 1581 */           TileEntity tileentity = this.playerEntity.world.getTileEntity(new BlockPos(packetbuffer2.readInt(), packetbuffer2.readInt(), packetbuffer2.readInt()));
/*      */           
/* 1583 */           if (tileentity instanceof TileEntityCommandBlock)
/*      */           {
/* 1585 */             commandblockbaselogic1 = ((TileEntityCommandBlock)tileentity).getCommandBlockLogic();
/*      */           }
/*      */         }
/* 1588 */         else if (l == 1) {
/*      */           
/* 1590 */           Entity entity = this.playerEntity.world.getEntityByID(packetbuffer2.readInt());
/*      */           
/* 1592 */           if (entity instanceof EntityMinecartCommandBlock)
/*      */           {
/* 1594 */             commandblockbaselogic1 = ((EntityMinecartCommandBlock)entity).getCommandBlockLogic();
/*      */           }
/*      */         } 
/*      */         
/* 1598 */         String s6 = packetbuffer2.readStringFromBuffer(packetbuffer2.readableBytes());
/* 1599 */         boolean flag2 = packetbuffer2.readBoolean();
/*      */         
/* 1601 */         if (commandblockbaselogic1 != null)
/*      */         {
/* 1603 */           commandblockbaselogic1.setCommand(s6);
/* 1604 */           commandblockbaselogic1.setTrackOutput(flag2);
/*      */           
/* 1606 */           if (!flag2)
/*      */           {
/* 1608 */             commandblockbaselogic1.setLastOutput(null);
/*      */           }
/*      */           
/* 1611 */           commandblockbaselogic1.updateCommand();
/* 1612 */           this.playerEntity.addChatMessage((ITextComponent)new TextComponentTranslation("advMode.setCommand.success", new Object[] { s6 }));
/*      */         }
/*      */       
/* 1615 */       } catch (Exception exception4) {
/*      */         
/* 1617 */         LOGGER.error("Couldn't set command block", exception4);
/*      */       }
/*      */     
/* 1620 */     } else if ("MC|AutoCmd".equals(s)) {
/*      */       
/* 1622 */       if (!this.serverController.isCommandBlockEnabled()) {
/*      */         
/* 1624 */         this.playerEntity.addChatMessage((ITextComponent)new TextComponentTranslation("advMode.notEnabled", new Object[0]));
/*      */         
/*      */         return;
/*      */       } 
/* 1628 */       if (!this.playerEntity.canUseCommandBlock()) {
/*      */         
/* 1630 */         this.playerEntity.addChatMessage((ITextComponent)new TextComponentTranslation("advMode.notAllowed", new Object[0]));
/*      */         
/*      */         return;
/*      */       } 
/* 1634 */       PacketBuffer packetbuffer3 = packetIn.getBufferData();
/*      */ 
/*      */       
/*      */       try {
/* 1638 */         CommandBlockBaseLogic commandblockbaselogic = null;
/* 1639 */         TileEntityCommandBlock tileentitycommandblock = null;
/* 1640 */         BlockPos blockpos1 = new BlockPos(packetbuffer3.readInt(), packetbuffer3.readInt(), packetbuffer3.readInt());
/* 1641 */         TileEntity tileentity2 = this.playerEntity.world.getTileEntity(blockpos1);
/*      */         
/* 1643 */         if (tileentity2 instanceof TileEntityCommandBlock) {
/*      */           
/* 1645 */           tileentitycommandblock = (TileEntityCommandBlock)tileentity2;
/* 1646 */           commandblockbaselogic = tileentitycommandblock.getCommandBlockLogic();
/*      */         } 
/*      */         
/* 1649 */         String s7 = packetbuffer3.readStringFromBuffer(packetbuffer3.readableBytes());
/* 1650 */         boolean flag3 = packetbuffer3.readBoolean();
/* 1651 */         TileEntityCommandBlock.Mode tileentitycommandblock$mode = TileEntityCommandBlock.Mode.valueOf(packetbuffer3.readStringFromBuffer(16));
/* 1652 */         boolean flag = packetbuffer3.readBoolean();
/* 1653 */         boolean flag1 = packetbuffer3.readBoolean();
/*      */         
/* 1655 */         if (commandblockbaselogic != null) {
/*      */           IBlockState iblockstate3, iblockstate2, iblockstate;
/* 1657 */           EnumFacing enumfacing = (EnumFacing)this.playerEntity.world.getBlockState(blockpos1).getValue((IProperty)BlockCommandBlock.FACING);
/*      */           
/* 1659 */           switch (tileentitycommandblock$mode) {
/*      */             
/*      */             case SEQUENCE:
/* 1662 */               iblockstate3 = Blocks.CHAIN_COMMAND_BLOCK.getDefaultState();
/* 1663 */               this.playerEntity.world.setBlockState(blockpos1, iblockstate3.withProperty((IProperty)BlockCommandBlock.FACING, (Comparable)enumfacing).withProperty((IProperty)BlockCommandBlock.CONDITIONAL, Boolean.valueOf(flag)), 2);
/*      */               break;
/*      */             
/*      */             case null:
/* 1667 */               iblockstate2 = Blocks.REPEATING_COMMAND_BLOCK.getDefaultState();
/* 1668 */               this.playerEntity.world.setBlockState(blockpos1, iblockstate2.withProperty((IProperty)BlockCommandBlock.FACING, (Comparable)enumfacing).withProperty((IProperty)BlockCommandBlock.CONDITIONAL, Boolean.valueOf(flag)), 2);
/*      */               break;
/*      */             
/*      */             case REDSTONE:
/* 1672 */               iblockstate = Blocks.COMMAND_BLOCK.getDefaultState();
/* 1673 */               this.playerEntity.world.setBlockState(blockpos1, iblockstate.withProperty((IProperty)BlockCommandBlock.FACING, (Comparable)enumfacing).withProperty((IProperty)BlockCommandBlock.CONDITIONAL, Boolean.valueOf(flag)), 2);
/*      */               break;
/*      */           } 
/* 1676 */           tileentity2.validate();
/* 1677 */           this.playerEntity.world.setTileEntity(blockpos1, tileentity2);
/* 1678 */           commandblockbaselogic.setCommand(s7);
/* 1679 */           commandblockbaselogic.setTrackOutput(flag3);
/*      */           
/* 1681 */           if (!flag3)
/*      */           {
/* 1683 */             commandblockbaselogic.setLastOutput(null);
/*      */           }
/*      */           
/* 1686 */           tileentitycommandblock.setAuto(flag1);
/* 1687 */           commandblockbaselogic.updateCommand();
/*      */           
/* 1689 */           if (!StringUtils.isNullOrEmpty(s7))
/*      */           {
/* 1691 */             this.playerEntity.addChatMessage((ITextComponent)new TextComponentTranslation("advMode.setCommand.success", new Object[] { s7 }));
/*      */           }
/*      */         }
/*      */       
/* 1695 */       } catch (Exception exception3) {
/*      */         
/* 1697 */         LOGGER.error("Couldn't set command block", exception3);
/*      */       }
/*      */     
/* 1700 */     } else if ("MC|Beacon".equals(s)) {
/*      */       
/* 1702 */       if (this.playerEntity.openContainer instanceof ContainerBeacon) {
/*      */         
/*      */         try {
/*      */           
/* 1706 */           PacketBuffer packetbuffer4 = packetIn.getBufferData();
/* 1707 */           int i1 = packetbuffer4.readInt();
/* 1708 */           int k1 = packetbuffer4.readInt();
/* 1709 */           ContainerBeacon containerbeacon = (ContainerBeacon)this.playerEntity.openContainer;
/* 1710 */           Slot slot = containerbeacon.getSlot(0);
/*      */           
/* 1712 */           if (slot.getHasStack())
/*      */           {
/* 1714 */             slot.decrStackSize(1);
/* 1715 */             IInventory iinventory = containerbeacon.getTileEntity();
/* 1716 */             iinventory.setField(1, i1);
/* 1717 */             iinventory.setField(2, k1);
/* 1718 */             iinventory.markDirty();
/*      */           }
/*      */         
/* 1721 */         } catch (Exception exception2) {
/*      */           
/* 1723 */           LOGGER.error("Couldn't set beacon", exception2);
/*      */         }
/*      */       
/*      */       }
/* 1727 */     } else if ("MC|ItemName".equals(s)) {
/*      */       
/* 1729 */       if (this.playerEntity.openContainer instanceof ContainerRepair) {
/*      */         
/* 1731 */         ContainerRepair containerrepair = (ContainerRepair)this.playerEntity.openContainer;
/*      */         
/* 1733 */         if (packetIn.getBufferData() != null && packetIn.getBufferData().readableBytes() >= 1)
/*      */         {
/* 1735 */           String s5 = ChatAllowedCharacters.filterAllowedCharacters(packetIn.getBufferData().readStringFromBuffer(32767));
/*      */           
/* 1737 */           if (s5.length() <= 35)
/*      */           {
/* 1739 */             containerrepair.updateItemName(s5);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1744 */           containerrepair.updateItemName("");
/*      */         }
/*      */       
/*      */       } 
/* 1748 */     } else if ("MC|Struct".equals(s)) {
/*      */       
/* 1750 */       if (!this.playerEntity.canUseCommandBlock()) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1755 */       PacketBuffer packetbuffer5 = packetIn.getBufferData();
/*      */ 
/*      */       
/*      */       try {
/* 1759 */         BlockPos blockpos = new BlockPos(packetbuffer5.readInt(), packetbuffer5.readInt(), packetbuffer5.readInt());
/* 1760 */         IBlockState iblockstate1 = this.playerEntity.world.getBlockState(blockpos);
/* 1761 */         TileEntity tileentity1 = this.playerEntity.world.getTileEntity(blockpos);
/*      */         
/* 1763 */         if (tileentity1 instanceof TileEntityStructure)
/*      */         {
/* 1765 */           TileEntityStructure tileentitystructure = (TileEntityStructure)tileentity1;
/* 1766 */           int l1 = packetbuffer5.readByte();
/* 1767 */           String s8 = packetbuffer5.readStringFromBuffer(32);
/* 1768 */           tileentitystructure.setMode(TileEntityStructure.Mode.valueOf(s8));
/* 1769 */           tileentitystructure.setName(packetbuffer5.readStringFromBuffer(64));
/* 1770 */           int i2 = MathHelper.clamp(packetbuffer5.readInt(), -32, 32);
/* 1771 */           int j2 = MathHelper.clamp(packetbuffer5.readInt(), -32, 32);
/* 1772 */           int k2 = MathHelper.clamp(packetbuffer5.readInt(), -32, 32);
/* 1773 */           tileentitystructure.setPosition(new BlockPos(i2, j2, k2));
/* 1774 */           int l2 = MathHelper.clamp(packetbuffer5.readInt(), 0, 32);
/* 1775 */           int i3 = MathHelper.clamp(packetbuffer5.readInt(), 0, 32);
/* 1776 */           int j = MathHelper.clamp(packetbuffer5.readInt(), 0, 32);
/* 1777 */           tileentitystructure.setSize(new BlockPos(l2, i3, j));
/* 1778 */           String s2 = packetbuffer5.readStringFromBuffer(32);
/* 1779 */           tileentitystructure.setMirror(Mirror.valueOf(s2));
/* 1780 */           String s3 = packetbuffer5.readStringFromBuffer(32);
/* 1781 */           tileentitystructure.setRotation(Rotation.valueOf(s3));
/* 1782 */           tileentitystructure.setMetadata(packetbuffer5.readStringFromBuffer(128));
/* 1783 */           tileentitystructure.setIgnoresEntities(packetbuffer5.readBoolean());
/* 1784 */           tileentitystructure.setShowAir(packetbuffer5.readBoolean());
/* 1785 */           tileentitystructure.setShowBoundingBox(packetbuffer5.readBoolean());
/* 1786 */           tileentitystructure.setIntegrity(MathHelper.clamp(packetbuffer5.readFloat(), 0.0F, 1.0F));
/* 1787 */           tileentitystructure.setSeed(packetbuffer5.readVarLong());
/* 1788 */           String s4 = tileentitystructure.getName();
/*      */           
/* 1790 */           if (l1 == 2) {
/*      */             
/* 1792 */             if (tileentitystructure.save())
/*      */             {
/* 1794 */               this.playerEntity.addChatComponentMessage((ITextComponent)new TextComponentTranslation("structure_block.save_success", new Object[] { s4 }), false);
/*      */             }
/*      */             else
/*      */             {
/* 1798 */               this.playerEntity.addChatComponentMessage((ITextComponent)new TextComponentTranslation("structure_block.save_failure", new Object[] { s4 }), false);
/*      */             }
/*      */           
/* 1801 */           } else if (l1 == 3) {
/*      */             
/* 1803 */             if (!tileentitystructure.isStructureLoadable())
/*      */             {
/* 1805 */               this.playerEntity.addChatComponentMessage((ITextComponent)new TextComponentTranslation("structure_block.load_not_found", new Object[] { s4 }), false);
/*      */             }
/* 1807 */             else if (tileentitystructure.load())
/*      */             {
/* 1809 */               this.playerEntity.addChatComponentMessage((ITextComponent)new TextComponentTranslation("structure_block.load_success", new Object[] { s4 }), false);
/*      */             }
/*      */             else
/*      */             {
/* 1813 */               this.playerEntity.addChatComponentMessage((ITextComponent)new TextComponentTranslation("structure_block.load_prepare", new Object[] { s4 }), false);
/*      */             }
/*      */           
/* 1816 */           } else if (l1 == 4) {
/*      */             
/* 1818 */             if (tileentitystructure.detectSize()) {
/*      */               
/* 1820 */               this.playerEntity.addChatComponentMessage((ITextComponent)new TextComponentTranslation("structure_block.size_success", new Object[] { s4 }), false);
/*      */             }
/*      */             else {
/*      */               
/* 1824 */               this.playerEntity.addChatComponentMessage((ITextComponent)new TextComponentTranslation("structure_block.size_failure", new Object[0]), false);
/*      */             } 
/*      */           } 
/*      */           
/* 1828 */           tileentitystructure.markDirty();
/* 1829 */           this.playerEntity.world.notifyBlockUpdate(blockpos, iblockstate1, iblockstate1, 3);
/*      */         }
/*      */       
/* 1832 */       } catch (Exception exception1) {
/*      */         
/* 1834 */         LOGGER.error("Couldn't set structure block", exception1);
/*      */       }
/*      */     
/* 1837 */     } else if ("MC|PickItem".equals(s)) {
/*      */       
/* 1839 */       PacketBuffer packetbuffer6 = packetIn.getBufferData();
/*      */ 
/*      */       
/*      */       try {
/* 1843 */         int j1 = packetbuffer6.readVarIntFromBuffer();
/* 1844 */         this.playerEntity.inventory.pickItem(j1);
/* 1845 */         this.playerEntity.connection.sendPacket((Packet<?>)new SPacketSetSlot(-2, this.playerEntity.inventory.currentItem, this.playerEntity.inventory.getStackInSlot(this.playerEntity.inventory.currentItem)));
/* 1846 */         this.playerEntity.connection.sendPacket((Packet<?>)new SPacketSetSlot(-2, j1, this.playerEntity.inventory.getStackInSlot(j1)));
/* 1847 */         this.playerEntity.connection.sendPacket((Packet<?>)new SPacketHeldItemChange(this.playerEntity.inventory.currentItem));
/*      */       }
/* 1849 */       catch (Exception exception) {
/*      */         
/* 1851 */         LOGGER.error("Couldn't pick item", exception);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void func_191985_a(CPacketRecipePlacement cPacketRecipePlacement) {}
/*      */   
/*      */   public void processPlayerBlockPlacement(CPacketPlayerBlockPlacement packetIn) {}
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\NetHandlerPlayServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */