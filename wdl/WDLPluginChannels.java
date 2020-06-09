/*     */ package wdl;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import com.google.common.io.ByteArrayDataOutput;
/*     */ import com.google.common.io.ByteStreams;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WDLPluginChannels
/*     */ {
/*  66 */   private static Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static HashSet<Integer> receivedPackets = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canUseFunctionsUnknownToServer = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canDownloadInGeneral = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   private static int saveRadius = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canCacheChunks = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canSaveEntities = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canSaveTileEntities = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canSaveContainers = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   private static Map<String, Integer> entityRanges = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canRequestPermissions = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   private static String requestMessage = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   private static Map<String, Multimap<String, ChunkRange>> chunkOverrides = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   private static Map<String, String> requests = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public static final List<String> BOOLEAN_REQUEST_FIELDS = Arrays.asList(new String[] {
/* 148 */         "downloadInGeneral", "cacheChunks", "saveEntities", 
/* 149 */         "saveTileEntities", "saveContainers", "getEntityRanges"
/*     */       });
/*     */ 
/*     */   
/* 153 */   public static final List<String> INTEGER_REQUEST_FIELDS = Arrays.asList(new String[] {
/* 154 */         "saveRadius"
/*     */       });
/*     */ 
/*     */ 
/*     */   
/* 159 */   private static List<ChunkRange> chunkOverrideRequests = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canUseFunctionsUnknownToServer() {
/* 165 */     if (receivedPackets.contains(Integer.valueOf(0))) {
/* 166 */       return canUseFunctionsUnknownToServer;
/*     */     }
/* 168 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canDownloadAtAll() {
/* 178 */     if (hasChunkOverrides()) {
/* 179 */       return true;
/*     */     }
/* 181 */     return canDownloadInGeneral();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canDownloadInGeneral() {
/* 190 */     if (receivedPackets.contains(Integer.valueOf(1))) {
/* 191 */       return canDownloadInGeneral;
/*     */     }
/* 193 */     return canUseFunctionsUnknownToServer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canSaveChunk(Chunk chunk) {
/* 202 */     if (isChunkOverridden(chunk)) {
/* 203 */       return true;
/*     */     }
/*     */     
/* 206 */     if (!canDownloadInGeneral()) {
/* 207 */       return false;
/*     */     }
/*     */     
/* 210 */     if (receivedPackets.contains(Integer.valueOf(1))) {
/* 211 */       if (!canCacheChunks && saveRadius >= 0) {
/* 212 */         int distanceX = chunk.xPosition - WDL.thePlayer.chunkCoordX;
/* 213 */         int distanceZ = chunk.zPosition - WDL.thePlayer.chunkCoordZ;
/*     */         
/* 215 */         if (Math.abs(distanceX) > saveRadius || 
/* 216 */           Math.abs(distanceZ) > saveRadius) {
/* 217 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 221 */       return true;
/*     */     } 
/* 223 */     return canUseFunctionsUnknownToServer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canSaveEntities() {
/* 231 */     if (!canDownloadInGeneral()) {
/* 232 */       return false;
/*     */     }
/*     */     
/* 235 */     if (receivedPackets.contains(Integer.valueOf(1))) {
/* 236 */       return canSaveEntities;
/*     */     }
/* 238 */     return canUseFunctionsUnknownToServer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canSaveEntities(Chunk chunk) {
/* 246 */     if (isChunkOverridden(chunk)) {
/* 247 */       return true;
/*     */     }
/*     */     
/* 250 */     return canSaveEntities();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canSaveEntities(int chunkX, int chunkZ) {
/* 257 */     if (isChunkOverridden(chunkX, chunkZ)) {
/* 258 */       return true;
/*     */     }
/*     */     
/* 261 */     return canSaveEntities();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canSaveTileEntities() {
/* 268 */     if (!canDownloadInGeneral()) {
/* 269 */       return false;
/*     */     }
/*     */     
/* 272 */     if (receivedPackets.contains(Integer.valueOf(1))) {
/* 273 */       return canSaveTileEntities;
/*     */     }
/* 275 */     return canUseFunctionsUnknownToServer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canSaveTileEntities(Chunk chunk) {
/* 283 */     if (isChunkOverridden(chunk)) {
/* 284 */       return true;
/*     */     }
/*     */     
/* 287 */     return canSaveTileEntities();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canSaveTileEntities(int chunkX, int chunkZ) {
/* 294 */     if (isChunkOverridden(chunkX, chunkZ)) {
/* 295 */       return true;
/*     */     }
/*     */     
/* 298 */     return canSaveTileEntities();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canSaveContainers() {
/* 305 */     if (!canDownloadInGeneral()) {
/* 306 */       return false;
/*     */     }
/* 308 */     if (!canSaveTileEntities()) {
/* 309 */       return false;
/*     */     }
/* 311 */     if (receivedPackets.contains(Integer.valueOf(1))) {
/* 312 */       return canSaveContainers;
/*     */     }
/* 314 */     return canUseFunctionsUnknownToServer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canSaveContainers(Chunk chunk) {
/* 322 */     if (isChunkOverridden(chunk)) {
/* 323 */       return true;
/*     */     }
/*     */     
/* 326 */     return canSaveContainers();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canSaveContainers(int chunkX, int chunkZ) {
/* 333 */     if (isChunkOverridden(chunkX, chunkZ)) {
/* 334 */       return true;
/*     */     }
/*     */     
/* 337 */     return canSaveContainers();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canSaveMaps() {
/* 344 */     if (!canDownloadInGeneral()) {
/* 345 */       return false;
/*     */     }
/*     */     
/* 348 */     if (receivedPackets.contains(Integer.valueOf(1))) {
/* 349 */       return canSaveTileEntities;
/*     */     }
/* 351 */     return canUseFunctionsUnknownToServer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEntityRange(String entity) {
/* 362 */     if (!canSaveEntities(null)) {
/* 363 */       return -1;
/*     */     }
/* 365 */     if (receivedPackets.contains(Integer.valueOf(2))) {
/* 366 */       if (entityRanges.containsKey(entity)) {
/* 367 */         return ((Integer)entityRanges.get(entity)).intValue();
/*     */       }
/* 369 */       return -1;
/*     */     } 
/*     */     
/* 372 */     return -1;
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
/*     */   public static int getSaveRadius() {
/* 385 */     return saveRadius;
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
/*     */   public static boolean canCacheChunks() {
/* 397 */     return canCacheChunks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasServerEntityRange() {
/* 404 */     return (receivedPackets.contains(Integer.valueOf(2)) && entityRanges.size() > 0);
/*     */   }
/*     */   
/*     */   public static Map<String, Integer> getEntityRanges() {
/* 408 */     return new HashMap<>(entityRanges);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasPermissions() {
/* 415 */     return (receivedPackets != null && !receivedPackets.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canRequestPermissions() {
/* 422 */     return (receivedPackets.contains(Integer.valueOf(3)) && canRequestPermissions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getRequestMessage() {
/* 430 */     if (receivedPackets.contains(Integer.valueOf(3))) {
/* 431 */       return requestMessage;
/*     */     }
/* 433 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isChunkOverridden(Chunk chunk) {
/* 441 */     if (chunk == null) {
/* 442 */       return false;
/*     */     }
/*     */     
/* 445 */     return isChunkOverridden(chunk.xPosition, chunk.zPosition);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isChunkOverridden(int x, int z) {
/* 451 */     for (Multimap<String, ChunkRange> map : chunkOverrides.values()) {
/* 452 */       for (ChunkRange range : map.values()) {
/* 453 */         if (x >= range.x1 && 
/* 454 */           x <= range.x2 && 
/* 455 */           z >= range.z1 && 
/* 456 */           z <= range.z2) {
/* 457 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 462 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasChunkOverrides() {
/* 469 */     if (!receivedPackets.contains(Integer.valueOf(4)))
/*     */     {
/*     */ 
/*     */       
/* 473 */       return false;
/*     */     }
/* 475 */     if (chunkOverrides == null || chunkOverrides.isEmpty()) {
/* 476 */       return false;
/*     */     }
/* 478 */     for (Multimap<String, ChunkRange> m : chunkOverrides.values()) {
/* 479 */       if (!m.isEmpty()) {
/* 480 */         return true;
/*     */       }
/*     */     } 
/* 483 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, Multimap<String, ChunkRange>> getChunkOverrides() {
/* 490 */     Map<String, Multimap<String, ChunkRange>> returned = 
/* 491 */       new HashMap<>();
/*     */ 
/*     */     
/* 494 */     Iterator<Map.Entry<String, Multimap<String, ChunkRange>>> iterator = chunkOverrides.entrySet().iterator(); while (iterator.hasNext()) { Map.Entry<String, Multimap<String, ChunkRange>> e = iterator.next();
/*     */       
/* 496 */       ImmutableMultimap immutableMultimap = ImmutableMultimap.copyOf(e.getValue());
/*     */       
/* 498 */       returned.put(e.getKey(), immutableMultimap); }
/*     */ 
/*     */     
/* 501 */     return (Map<String, Multimap<String, ChunkRange>>)ImmutableMap.copyOf(returned);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addRequest(String key, String value) {
/* 510 */     if (!isValidRequest(key, value)) {
/*     */       return;
/*     */     }
/*     */     
/* 514 */     requests.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, String> getRequests() {
/* 521 */     return (Map<String, String>)ImmutableMap.copyOf(requests);
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
/*     */   public static boolean isValidRequest(String key, String value) {
/* 533 */     if (key == null || value == null) {
/* 534 */       return false;
/*     */     }
/*     */     
/* 537 */     if (BOOLEAN_REQUEST_FIELDS.contains(key))
/* 538 */       return !(!value.equals("true") && !value.equals("false")); 
/* 539 */     if (INTEGER_REQUEST_FIELDS.contains(key)) {
/*     */       try {
/* 541 */         Integer.parseInt(value);
/* 542 */         return true;
/* 543 */       } catch (NumberFormatException e) {
/* 544 */         return false;
/*     */       } 
/*     */     }
/*     */     
/* 548 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ChunkRange> getChunkOverrideRequests() {
/* 555 */     return (List<ChunkRange>)ImmutableList.copyOf(chunkOverrideRequests);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addChunkOverrideRequest(ChunkRange range) {
/* 561 */     chunkOverrideRequests.add(range);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendRequests() {
/* 568 */     if (requests.isEmpty() && chunkOverrideRequests.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 572 */     ByteArrayDataOutput output = ByteStreams.newDataOutput();
/*     */     
/* 574 */     output.writeUTF("REQUEST REASON WILL GO HERE");
/* 575 */     output.writeInt(requests.size());
/* 576 */     for (Map.Entry<String, String> request : requests.entrySet()) {
/* 577 */       output.writeUTF(request.getKey());
/* 578 */       output.writeUTF(request.getValue());
/*     */     } 
/*     */     
/* 581 */     output.writeInt(chunkOverrideRequests.size());
/* 582 */     for (ChunkRange range : chunkOverrideRequests) {
/* 583 */       range.writeToOutput(output);
/*     */     }
/*     */     
/* 586 */     PacketBuffer requestBuffer = new PacketBuffer(Unpooled.buffer());
/* 587 */     requestBuffer.writeBytes(output.toByteArray());
/* 588 */     CPacketCustomPayload requestPacket = new CPacketCustomPayload(
/* 589 */         "WDL|REQUEST", requestBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void onWorldLoad() {
/* 598 */     Minecraft minecraft = Minecraft.getMinecraft();
/*     */     
/* 600 */     receivedPackets = new HashSet<>();
/* 601 */     requests = new HashMap<>();
/* 602 */     chunkOverrideRequests = new ArrayList<>();
/*     */     
/* 604 */     canUseFunctionsUnknownToServer = true;
/*     */     
/* 606 */     WDLMessages.chatMessageTranslated(
/* 607 */         WDLMessageTypes.PLUGIN_CHANNEL_MESSAGE, 
/* 608 */         "wdl.messages.permissions.init", new Object[0]);
/*     */ 
/*     */     
/* 611 */     PacketBuffer registerPacketBuffer = new PacketBuffer(Unpooled.buffer());
/*     */ 
/*     */     
/* 614 */     registerPacketBuffer.writeBytes(new byte[] { 
/* 615 */           87, 68, 76, 124, 73, 78, 73, 84, 
/* 616 */           87, 68, 76, 124, 67, 79, 78, 84, 82, 79, 76, 
/* 617 */           87, 68, 76, 124, 82, 69, 81, 85, 69, 83, 84 });
/* 618 */     CPacketCustomPayload registerPacket = new CPacketCustomPayload(
/* 619 */         "REGISTER", registerPacketBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 625 */       CPacketCustomPayload initPacket = new CPacketCustomPayload("WDL|INIT", 
/* 626 */           new PacketBuffer(Unpooled.copiedBuffer("1.11a-beta1"
/* 627 */               .getBytes("UTF-8"))));
/* 628 */     } catch (UnsupportedEncodingException e) {
/* 629 */       WDLMessages.chatMessageTranslated(WDLMessageTypes.ERROR, 
/* 630 */           "wdl.messages.generalError.noUTF8", new Object[] { e });
/*     */       
/* 632 */       CPacketCustomPayload initPacket = new CPacketCustomPayload("WDL|INIT", 
/* 633 */           new PacketBuffer(Unpooled.buffer()));
/*     */     } 
/*     */   }
/*     */   
/*     */   static void onPluginChannelPacket(String channel, byte[] bytes) {}
/*     */   
/*     */   public static class ChunkRange { public final String tag;
/*     */     public final int x1;
/*     */     public final int z1;
/*     */     public final int x2;
/*     */     public final int z2;
/*     */     
/*     */     public ChunkRange(String tag, int x1, int z1, int x2, int z2) {
/* 646 */       this.tag = tag;
/*     */ 
/*     */       
/* 649 */       if (x1 > x2) {
/* 650 */         this.x1 = x2;
/* 651 */         this.x2 = x1;
/*     */       } else {
/* 653 */         this.x1 = x1;
/* 654 */         this.x2 = x2;
/*     */       } 
/* 656 */       if (z1 > z2) {
/* 657 */         this.z1 = z2;
/* 658 */         this.z2 = z1;
/*     */       } else {
/* 660 */         this.z1 = z1;
/* 661 */         this.z2 = z2;
/*     */       } 
/*     */     }
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
/*     */     public static ChunkRange readFromInput(ByteArrayDataInput input) {
/* 680 */       String tag = input.readUTF();
/* 681 */       int x1 = input.readInt();
/* 682 */       int z1 = input.readInt();
/* 683 */       int x2 = input.readInt();
/* 684 */       int z2 = input.readInt();
/*     */       
/* 686 */       return new ChunkRange(tag, x1, z1, x2, z2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeToOutput(ByteArrayDataOutput output) {
/* 697 */       output.writeUTF(this.tag);
/*     */       
/* 699 */       output.writeInt(this.x1);
/* 700 */       output.writeInt(this.z1);
/* 701 */       output.writeInt(this.x2);
/* 702 */       output.writeInt(this.z2);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 707 */       return "ChunkRange [tag=" + this.tag + ", x1=" + this.x1 + ", z1=" + this.z1 + 
/* 708 */         ", x2=" + this.x2 + ", z2=" + this.z2 + "]";
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\WDLPluginChannels.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */