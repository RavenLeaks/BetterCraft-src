/*     */ package wdl;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.item.EntityMinecartChest;
/*     */ import net.minecraft.entity.item.EntityMinecartHopper;
/*     */ import net.minecraft.entity.passive.AbstractHorse;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.ContainerBeacon;
/*     */ import net.minecraft.inventory.ContainerHorseChest;
/*     */ import net.minecraft.inventory.ContainerHorseInventory;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryEnderChest;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityNote;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.village.MerchantRecipeList;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import wdl.api.IWorldLoadListener;
/*     */ import wdl.api.WDLApi;
/*     */ import wdl.update.WDLUpdateChecker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WDLEvents
/*     */ {
/*  57 */   private static final Profiler profiler = (Minecraft.getMinecraft()).mcProfiler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onWorldLoad(WorldClient world) {
/*  64 */     profiler.startSection("Core");
/*     */     
/*  66 */     if (WDL.minecraft.isIntegratedServerRunning()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  71 */     if (WDL.downloading) {
/*     */ 
/*     */       
/*  74 */       if (!WDL.saving) {
/*  75 */         WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, 
/*  76 */             "wdl.messages.generalInfo.worldChanged", new Object[0]);
/*  77 */         WDL.worldLoadingDeferred = true;
/*  78 */         WDL.startSaveThread();
/*     */       } 
/*     */       
/*  81 */       profiler.endSection();
/*     */       
/*     */       return;
/*     */     } 
/*  85 */     boolean sameServer = WDL.loadWorld();
/*     */     
/*  87 */     WDLUpdateChecker.startIfNeeded();
/*     */     
/*  89 */     profiler.endSection();
/*     */ 
/*     */     
/*  92 */     Iterator<WDLApi.ModInfo<IWorldLoadListener>> iterator = WDLApi.getImplementingExtensions(IWorldLoadListener.class).iterator(); while (iterator.hasNext()) { WDLApi.ModInfo<IWorldLoadListener> info = iterator.next();
/*  93 */       profiler.startSection(info.id);
/*  94 */       ((IWorldLoadListener)info.mod).onWorldLoad(world, sameServer);
/*  95 */       profiler.endSection(); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onChunkNoLongerNeeded(Chunk unneededChunk) {
/* 103 */     if (!WDL.downloading)
/*     */       return; 
/* 105 */     if (unneededChunk == null) {
/*     */       return;
/*     */     }
/*     */     
/* 109 */     if (WDLPluginChannels.canSaveChunk(unneededChunk)) {
/* 110 */       WDLMessages.chatMessageTranslated(
/* 111 */           WDLMessageTypes.ON_CHUNK_NO_LONGER_NEEDED, 
/* 112 */           "wdl.messages.onChunkNoLongerNeeded.saved", new Object[] {
/* 113 */             Integer.valueOf(unneededChunk.xPosition), Integer.valueOf(unneededChunk.zPosition) });
/* 114 */       WDL.saveChunk(unneededChunk);
/*     */     } else {
/* 116 */       WDLMessages.chatMessageTranslated(
/* 117 */           WDLMessageTypes.ON_CHUNK_NO_LONGER_NEEDED, 
/* 118 */           "wdl.messages.onChunkNoLongerNeeded.didNotSave", new Object[] {
/* 119 */             Integer.valueOf(unneededChunk.xPosition), Integer.valueOf(unneededChunk.zPosition)
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onItemGuiOpened() {
/* 128 */     if (!WDL.downloading)
/*     */       return; 
/* 130 */     if (WDL.minecraft.objectMouseOver == null) {
/*     */       return;
/*     */     }
/*     */     
/* 134 */     if (WDL.minecraft.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY) {
/* 135 */       WDL.lastEntity = WDL.minecraft.objectMouseOver.entityHit;
/*     */     } else {
/* 137 */       WDL.lastEntity = null;
/*     */ 
/*     */       
/* 140 */       WDL.lastClickedBlock = WDL.minecraft.objectMouseOver.getBlockPos();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean onItemGuiClosed() {
/* 149 */     if (!WDL.downloading) return true;
/*     */     
/* 151 */     String saveName = "";
/*     */     
/* 153 */     if (WDL.thePlayer.getRidingEntity() instanceof AbstractHorse)
/*     */     {
/*     */ 
/*     */       
/* 157 */       if (WDL.windowContainer instanceof ContainerHorseInventory) {
/* 158 */         AbstractHorse horseInContainer = 
/* 159 */           ReflectionUtils.<AbstractHorse>stealAndGetField(WDL.windowContainer, 
/* 160 */             AbstractHorse.class);
/*     */ 
/*     */         
/* 163 */         if (horseInContainer == WDL.thePlayer.getRidingEntity()) {
/* 164 */           if (!WDLPluginChannels.canSaveEntities(
/* 165 */               horseInContainer.chunkCoordX, 
/* 166 */               horseInContainer.chunkCoordZ)) {
/*     */ 
/*     */             
/* 169 */             WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_INFO, 
/* 170 */                 "wdl.messages.onGuiClosedInfo.cannotSaveEntities", new Object[0]);
/* 171 */             return true;
/*     */           } 
/*     */           
/* 174 */           AbstractHorse entityHorse = 
/* 175 */             (AbstractHorse)WDL.thePlayer.getRidingEntity();
/* 176 */           saveHorse((ContainerHorseInventory)WDL.windowContainer, entityHorse);
/*     */           
/* 178 */           WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_INFO, 
/* 179 */               "wdl.messages.onGuiClosedInfo.savedRiddenHorse", new Object[0]);
/* 180 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 186 */     if (WDL.lastEntity != null) {
/* 187 */       if (!WDLPluginChannels.canSaveEntities(WDL.lastEntity.chunkCoordX, 
/* 188 */           WDL.lastEntity.chunkCoordZ)) {
/* 189 */         WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_INFO, 
/* 190 */             "wdl.messages.onGuiClosedInfo.cannotSaveEntities", new Object[0]);
/* 191 */         return true;
/*     */       } 
/*     */       
/* 194 */       if (WDL.lastEntity instanceof EntityMinecartChest && 
/* 195 */         WDL.windowContainer instanceof net.minecraft.inventory.ContainerChest) {
/* 196 */         EntityMinecartChest emcc = (EntityMinecartChest)WDL.lastEntity;
/*     */         
/* 198 */         for (int i = 0; i < emcc.getSizeInventory(); i++) {
/* 199 */           Slot slot = WDL.windowContainer.getSlot(i);
/* 200 */           if (slot.getHasStack()) {
/* 201 */             emcc.setInventorySlotContents(i, slot.getStack());
/*     */           }
/*     */         } 
/*     */         
/* 205 */         saveName = "storageMinecart";
/* 206 */       } else if (WDL.lastEntity instanceof EntityMinecartHopper && 
/* 207 */         WDL.windowContainer instanceof net.minecraft.inventory.ContainerHopper) {
/* 208 */         EntityMinecartHopper emch = (EntityMinecartHopper)WDL.lastEntity;
/*     */         
/* 210 */         for (int i = 0; i < emch.getSizeInventory(); i++) {
/* 211 */           Slot slot = WDL.windowContainer.getSlot(i);
/* 212 */           if (slot.getHasStack()) {
/* 213 */             emch.setInventorySlotContents(i, slot.getStack());
/*     */           }
/*     */         } 
/*     */         
/* 217 */         saveName = "hopperMinecart";
/* 218 */       } else if (WDL.lastEntity instanceof EntityVillager && 
/* 219 */         WDL.windowContainer instanceof net.minecraft.inventory.ContainerMerchant) {
/* 220 */         EntityVillager ev = (EntityVillager)WDL.lastEntity;
/* 221 */         MerchantRecipeList list = ((IMerchant)ReflectionUtils.<IMerchant>stealAndGetField(
/* 222 */             WDL.windowContainer, IMerchant.class)).getRecipes(
/* 223 */             (EntityPlayer)WDL.thePlayer);
/* 224 */         ReflectionUtils.stealAndSetField(ev, MerchantRecipeList.class, list);
/*     */         
/* 226 */         saveName = "villager";
/* 227 */       } else if (WDL.lastEntity instanceof AbstractHorse && 
/* 228 */         WDL.windowContainer instanceof ContainerHorseInventory) {
/* 229 */         saveHorse((ContainerHorseInventory)WDL.windowContainer, 
/* 230 */             (AbstractHorse)WDL.lastEntity);
/*     */         
/* 232 */         saveName = "horse";
/*     */       } else {
/* 234 */         return false;
/*     */       } 
/*     */       
/* 237 */       WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_INFO, 
/* 238 */           "wdl.messages.onGuiClosedInfo.savedEntity." + saveName, new Object[0]);
/* 239 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 245 */     TileEntity te = WDL.worldClient.getTileEntity(WDL.lastClickedBlock);
/*     */     
/* 247 */     if (te == null) {
/*     */       
/* 249 */       WDLMessages.chatMessageTranslated(
/* 250 */           WDLMessageTypes.ON_GUI_CLOSED_WARNING, 
/* 251 */           "wdl.messages.onGuiClosedWarning.couldNotGetTE", new Object[] {
/* 252 */             WDL.lastClickedBlock });
/* 253 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 257 */     if (!WDLPluginChannels.canSaveContainers(te.getPos().getX() << 4, te
/* 258 */         .getPos().getZ() << 4)) {
/* 259 */       WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_INFO, 
/* 260 */           "wdl.messages.onGuiClosedInfo.cannotSaveTileEntities", new Object[0]);
/* 261 */       return true;
/*     */     } 
/*     */     
/* 264 */     if (WDL.windowContainer instanceof net.minecraft.inventory.ContainerChest && 
/* 265 */       te instanceof TileEntityChest) {
/* 266 */       if (WDL.windowContainer.inventorySlots.size() > 63) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 273 */         BlockPos pos1 = WDL.lastClickedBlock;
/* 274 */         TileEntity te1 = te;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 279 */         BlockPos chestPos1 = null, chestPos2 = null;
/* 280 */         TileEntityChest chest1 = null, chest2 = null;
/*     */         
/* 282 */         BlockPos pos2 = pos1.add(0, 0, 1);
/* 283 */         TileEntity te2 = WDL.worldClient.getTileEntity(pos2);
/* 284 */         if (te2 instanceof TileEntityChest && (
/* 285 */           (TileEntityChest)te2).getChestType() == (
/* 286 */           (TileEntityChest)te1).getChestType()) {
/*     */           
/* 288 */           chest1 = (TileEntityChest)te1;
/* 289 */           chest2 = (TileEntityChest)te2;
/*     */           
/* 291 */           chestPos1 = pos1;
/* 292 */           chestPos2 = pos2;
/*     */         } 
/*     */         
/* 295 */         pos2 = pos1.add(0, 0, -1);
/* 296 */         te2 = WDL.worldClient.getTileEntity(pos2);
/* 297 */         if (te2 instanceof TileEntityChest && (
/* 298 */           (TileEntityChest)te2).getChestType() == (
/* 299 */           (TileEntityChest)te1).getChestType()) {
/*     */           
/* 301 */           chest1 = (TileEntityChest)te2;
/* 302 */           chest2 = (TileEntityChest)te1;
/*     */           
/* 304 */           chestPos1 = pos2;
/* 305 */           chestPos2 = pos1;
/*     */         } 
/*     */         
/* 308 */         pos2 = pos1.add(1, 0, 0);
/* 309 */         te2 = WDL.worldClient.getTileEntity(pos2);
/* 310 */         if (te2 instanceof TileEntityChest && (
/* 311 */           (TileEntityChest)te2).getChestType() == (
/* 312 */           (TileEntityChest)te1).getChestType()) {
/* 313 */           chest1 = (TileEntityChest)te1;
/* 314 */           chest2 = (TileEntityChest)te2;
/*     */           
/* 316 */           chestPos1 = pos1;
/* 317 */           chestPos2 = pos2;
/*     */         } 
/*     */         
/* 320 */         pos2 = pos1.add(-1, 0, 0);
/* 321 */         te2 = WDL.worldClient.getTileEntity(pos2);
/* 322 */         if (te2 instanceof TileEntityChest && (
/* 323 */           (TileEntityChest)te2).getChestType() == (
/* 324 */           (TileEntityChest)te1).getChestType()) {
/* 325 */           chest1 = (TileEntityChest)te2;
/* 326 */           chest2 = (TileEntityChest)te1;
/*     */           
/* 328 */           chestPos1 = pos2;
/* 329 */           chestPos2 = pos1;
/*     */         } 
/*     */         
/* 332 */         if (chest1 == null || chest2 == null || 
/* 333 */           chestPos1 == null || chestPos2 == null) {
/* 334 */           WDLMessages.chatMessageTranslated(WDLMessageTypes.ERROR, 
/* 335 */               "wdl.messages.onGuiClosedWarning.failedToFindDoubleChest", new Object[0]);
/* 336 */           return true;
/*     */         } 
/*     */         
/* 339 */         WDL.saveContainerItems(WDL.windowContainer, (IInventory)chest1, 0);
/* 340 */         WDL.saveContainerItems(WDL.windowContainer, (IInventory)chest2, 27);
/* 341 */         WDL.saveTileEntity(chestPos1, (TileEntity)chest1);
/* 342 */         WDL.saveTileEntity(chestPos2, (TileEntity)chest2);
/*     */         
/* 344 */         saveName = "doubleChest";
/*     */       }
/*     */       else {
/*     */         
/* 348 */         WDL.saveContainerItems(WDL.windowContainer, (IInventory)te, 0);
/* 349 */         WDL.saveTileEntity(WDL.lastClickedBlock, te);
/* 350 */         saveName = "singleChest";
/*     */       } 
/* 352 */     } else if (WDL.windowContainer instanceof net.minecraft.inventory.ContainerChest && 
/* 353 */       te instanceof net.minecraft.tileentity.TileEntityEnderChest) {
/* 354 */       InventoryEnderChest inventoryEnderChest = WDL.thePlayer
/* 355 */         .getInventoryEnderChest();
/* 356 */       int inventorySize = inventoryEnderChest.getSizeInventory();
/* 357 */       int containerSize = WDL.windowContainer.inventorySlots.size();
/*     */       
/* 359 */       for (int i = 0; i < containerSize && i < inventorySize; i++) {
/* 360 */         Slot slot = WDL.windowContainer.getSlot(i);
/* 361 */         if (slot.getHasStack()) {
/* 362 */           inventoryEnderChest.setInventorySlotContents(i, slot.getStack());
/*     */         }
/*     */       } 
/*     */       
/* 366 */       saveName = "enderChest";
/* 367 */     } else if (WDL.windowContainer instanceof net.minecraft.inventory.ContainerBrewingStand && 
/* 368 */       te instanceof net.minecraft.tileentity.TileEntityBrewingStand) {
/* 369 */       IInventory brewingInventory = ReflectionUtils.<IInventory>stealAndGetField(
/* 370 */           WDL.windowContainer, IInventory.class);
/* 371 */       WDL.saveContainerItems(WDL.windowContainer, (IInventory)te, 0);
/* 372 */       WDL.saveInventoryFields(brewingInventory, (IInventory)te);
/* 373 */       WDL.saveTileEntity(WDL.lastClickedBlock, te);
/* 374 */       saveName = "brewingStand";
/* 375 */     } else if (WDL.windowContainer instanceof net.minecraft.inventory.ContainerDispenser && 
/* 376 */       te instanceof net.minecraft.tileentity.TileEntityDispenser) {
/* 377 */       WDL.saveContainerItems(WDL.windowContainer, (IInventory)te, 0);
/* 378 */       WDL.saveTileEntity(WDL.lastClickedBlock, te);
/* 379 */       saveName = "dispenser";
/* 380 */     } else if (WDL.windowContainer instanceof net.minecraft.inventory.ContainerFurnace && 
/* 381 */       te instanceof net.minecraft.tileentity.TileEntityFurnace) {
/* 382 */       IInventory furnaceInventory = ReflectionUtils.<IInventory>stealAndGetField(
/* 383 */           WDL.windowContainer, IInventory.class);
/* 384 */       WDL.saveContainerItems(WDL.windowContainer, (IInventory)te, 0);
/* 385 */       WDL.saveInventoryFields(furnaceInventory, (IInventory)te);
/* 386 */       WDL.saveTileEntity(WDL.lastClickedBlock, te);
/* 387 */       saveName = "furnace";
/* 388 */     } else if (WDL.windowContainer instanceof net.minecraft.inventory.ContainerHopper && 
/* 389 */       te instanceof net.minecraft.tileentity.TileEntityHopper) {
/* 390 */       WDL.saveContainerItems(WDL.windowContainer, (IInventory)te, 0);
/* 391 */       WDL.saveTileEntity(WDL.lastClickedBlock, te);
/* 392 */       saveName = "hopper";
/* 393 */     } else if (WDL.windowContainer instanceof ContainerBeacon && 
/* 394 */       te instanceof TileEntityBeacon) {
/* 395 */       IInventory beaconInventory = (
/* 396 */         (ContainerBeacon)WDL.windowContainer).getTileEntity();
/* 397 */       TileEntityBeacon savedBeacon = (TileEntityBeacon)te;
/* 398 */       WDL.saveContainerItems(WDL.windowContainer, (IInventory)savedBeacon, 0);
/* 399 */       WDL.saveInventoryFields(beaconInventory, (IInventory)savedBeacon);
/* 400 */       WDL.saveTileEntity(WDL.lastClickedBlock, te);
/* 401 */       saveName = "beacon";
/*     */     } else {
/* 403 */       return false;
/*     */     } 
/*     */     
/* 406 */     WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_INFO, 
/* 407 */         "wdl.messages.onGuiClosedInfo.savedTileEntity." + saveName, new Object[0]);
/* 408 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onBlockEvent(BlockPos pos, Block block, int event, int param) {
/* 417 */     if (!WDL.downloading)
/*     */       return; 
/* 419 */     if (!WDLPluginChannels.canSaveTileEntities(pos.getX() << 4, 
/* 420 */         pos.getZ() << 4)) {
/*     */       return;
/*     */     }
/* 423 */     if (block == Blocks.NOTEBLOCK) {
/* 424 */       TileEntityNote newTE = new TileEntityNote();
/* 425 */       newTE.note = (byte)(param % 25);
/* 426 */       WDL.worldClient.setTileEntity(pos, (TileEntity)newTE);
/* 427 */       WDL.saveTileEntity(pos, (TileEntity)newTE);
/* 428 */       WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_BLOCK_EVENT, 
/* 429 */           "wdl.messages.onBlockEvent.noteblock", new Object[] { pos, Integer.valueOf(param), newTE });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onMapDataLoaded(int mapID, MapData mapData) {
/* 439 */     if (!WDL.downloading)
/*     */       return; 
/* 441 */     if (!WDLPluginChannels.canSaveMaps()) {
/*     */       return;
/*     */     }
/*     */     
/* 445 */     WDL.newMapDatas.put(Integer.valueOf(mapID), mapData);
/*     */     
/* 447 */     WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_MAP_SAVED, 
/* 448 */         "wdl.messages.onMapSaved", new Object[] { Integer.valueOf(mapID) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onPluginChannelPacket(String channel, byte[] bytes) {
/* 457 */     WDLPluginChannels.onPluginChannelPacket(channel, bytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onRemoveEntityFromWorld(Entity entity) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onChatMessage(String msg) {
/* 521 */     if (WDL.downloading && msg.startsWith("Seed: ")) {
/* 522 */       String seed = msg.substring(6);
/* 523 */       WDL.worldProps.setProperty("RandomSeed", seed);
/*     */       
/* 525 */       if (WDL.worldProps.getProperty("MapGenerator", "void").equals("void")) {
/*     */         
/* 527 */         WDL.worldProps.setProperty("MapGenerator", "default");
/* 528 */         WDL.worldProps.setProperty("GeneratorName", "default");
/* 529 */         WDL.worldProps.setProperty("GeneratorVersion", "1");
/* 530 */         WDL.worldProps.setProperty("GeneratorOptions", "");
/*     */         
/* 532 */         WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, 
/* 533 */             "wdl.messages.generalInfo.seedAndGenSet", new Object[] { seed });
/*     */       } else {
/* 535 */         WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, 
/* 536 */             "wdl.messages.generalInfo.seedSet", new Object[] { seed });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void saveHorse(ContainerHorseInventory container, AbstractHorse horse) {
/* 548 */     int PLAYER_INVENTORY_SLOTS = 36;
/* 549 */     ContainerHorseChest horseInventory = new ContainerHorseChest(
/* 550 */         "HorseChest", container.inventorySlots.size() - 
/* 551 */         36);
/* 552 */     for (int i = 0; i < horseInventory.getSizeInventory(); i++) {
/* 553 */       Slot slot = container.getSlot(i);
/* 554 */       if (slot.getHasStack()) {
/* 555 */         horseInventory.setInventorySlotContents(i, slot.getStack());
/*     */       }
/*     */     } 
/*     */     
/* 559 */     ReflectionUtils.stealAndSetField(horse, ContainerHorseChest.class, horseInventory);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\WDLEvents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */