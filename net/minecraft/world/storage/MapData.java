/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketMaps;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class MapData
/*     */   extends WorldSavedData
/*     */ {
/*     */   public int xCenter;
/*     */   public int zCenter;
/*     */   public byte dimension;
/*     */   public boolean trackingPosition;
/*     */   public boolean field_191096_f;
/*     */   public byte scale;
/*  29 */   public byte[] colors = new byte[16384];
/*  30 */   public List<MapInfo> playersArrayList = Lists.newArrayList();
/*  31 */   private final Map<EntityPlayer, MapInfo> playersHashMap = Maps.newHashMap();
/*  32 */   public Map<String, MapDecoration> mapDecorations = Maps.newLinkedHashMap();
/*     */ 
/*     */   
/*     */   public MapData(String mapname) {
/*  36 */     super(mapname);
/*     */   }
/*     */ 
/*     */   
/*     */   public void calculateMapCenter(double x, double z, int mapScale) {
/*  41 */     int i = 128 * (1 << mapScale);
/*  42 */     int j = MathHelper.floor((x + 64.0D) / i);
/*  43 */     int k = MathHelper.floor((z + 64.0D) / i);
/*  44 */     this.xCenter = j * i + i / 2 - 64;
/*  45 */     this.zCenter = k * i + i / 2 - 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  53 */     this.dimension = nbt.getByte("dimension");
/*  54 */     this.xCenter = nbt.getInteger("xCenter");
/*  55 */     this.zCenter = nbt.getInteger("zCenter");
/*  56 */     this.scale = nbt.getByte("scale");
/*  57 */     this.scale = (byte)MathHelper.clamp(this.scale, 0, 4);
/*     */     
/*  59 */     if (nbt.hasKey("trackingPosition", 1)) {
/*     */       
/*  61 */       this.trackingPosition = nbt.getBoolean("trackingPosition");
/*     */     }
/*     */     else {
/*     */       
/*  65 */       this.trackingPosition = true;
/*     */     } 
/*     */     
/*  68 */     this.field_191096_f = nbt.getBoolean("unlimitedTracking");
/*  69 */     int i = nbt.getShort("width");
/*  70 */     int j = nbt.getShort("height");
/*     */     
/*  72 */     if (i == 128 && j == 128) {
/*     */       
/*  74 */       this.colors = nbt.getByteArray("colors");
/*     */     }
/*     */     else {
/*     */       
/*  78 */       byte[] abyte = nbt.getByteArray("colors");
/*  79 */       this.colors = new byte[16384];
/*  80 */       int k = (128 - i) / 2;
/*  81 */       int l = (128 - j) / 2;
/*     */       
/*  83 */       for (int i1 = 0; i1 < j; i1++) {
/*     */         
/*  85 */         int j1 = i1 + l;
/*     */         
/*  87 */         if (j1 >= 0 || j1 < 128)
/*     */         {
/*  89 */           for (int k1 = 0; k1 < i; k1++) {
/*     */             
/*  91 */             int l1 = k1 + k;
/*     */             
/*  93 */             if (l1 >= 0 || l1 < 128)
/*     */             {
/*  95 */               this.colors[l1 + j1 * 128] = abyte[k1 + i1 * i];
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 105 */     compound.setByte("dimension", this.dimension);
/* 106 */     compound.setInteger("xCenter", this.xCenter);
/* 107 */     compound.setInteger("zCenter", this.zCenter);
/* 108 */     compound.setByte("scale", this.scale);
/* 109 */     compound.setShort("width", (short)128);
/* 110 */     compound.setShort("height", (short)128);
/* 111 */     compound.setByteArray("colors", this.colors);
/* 112 */     compound.setBoolean("trackingPosition", this.trackingPosition);
/* 113 */     compound.setBoolean("unlimitedTracking", this.field_191096_f);
/* 114 */     return compound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVisiblePlayers(EntityPlayer player, ItemStack mapStack) {
/* 122 */     if (!this.playersHashMap.containsKey(player)) {
/*     */       
/* 124 */       MapInfo mapdata$mapinfo = new MapInfo(player);
/* 125 */       this.playersHashMap.put(player, mapdata$mapinfo);
/* 126 */       this.playersArrayList.add(mapdata$mapinfo);
/*     */     } 
/*     */     
/* 129 */     if (!player.inventory.hasItemStack(mapStack))
/*     */     {
/* 131 */       this.mapDecorations.remove(player.getName());
/*     */     }
/*     */     
/* 134 */     for (int i = 0; i < this.playersArrayList.size(); i++) {
/*     */       
/* 136 */       MapInfo mapdata$mapinfo1 = this.playersArrayList.get(i);
/*     */       
/* 138 */       if (!mapdata$mapinfo1.entityplayerObj.isDead && (mapdata$mapinfo1.entityplayerObj.inventory.hasItemStack(mapStack) || mapStack.isOnItemFrame())) {
/*     */         
/* 140 */         if (!mapStack.isOnItemFrame() && mapdata$mapinfo1.entityplayerObj.dimension == this.dimension && this.trackingPosition)
/*     */         {
/* 142 */           func_191095_a(MapDecoration.Type.PLAYER, mapdata$mapinfo1.entityplayerObj.world, mapdata$mapinfo1.entityplayerObj.getName(), mapdata$mapinfo1.entityplayerObj.posX, mapdata$mapinfo1.entityplayerObj.posZ, mapdata$mapinfo1.entityplayerObj.rotationYaw);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 147 */         this.playersHashMap.remove(mapdata$mapinfo1.entityplayerObj);
/* 148 */         this.playersArrayList.remove(mapdata$mapinfo1);
/*     */       } 
/*     */     } 
/*     */     
/* 152 */     if (mapStack.isOnItemFrame() && this.trackingPosition) {
/*     */       
/* 154 */       EntityItemFrame entityitemframe = mapStack.getItemFrame();
/* 155 */       BlockPos blockpos = entityitemframe.getHangingPosition();
/* 156 */       func_191095_a(MapDecoration.Type.FRAME, player.world, "frame-" + entityitemframe.getEntityId(), blockpos.getX(), blockpos.getZ(), (entityitemframe.facingDirection.getHorizontalIndex() * 90));
/*     */     } 
/*     */     
/* 159 */     if (mapStack.hasTagCompound() && mapStack.getTagCompound().hasKey("Decorations", 9)) {
/*     */       
/* 161 */       NBTTagList nbttaglist = mapStack.getTagCompound().getTagList("Decorations", 10);
/*     */       
/* 163 */       for (int j = 0; j < nbttaglist.tagCount(); j++) {
/*     */         
/* 165 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(j);
/*     */         
/* 167 */         if (!this.mapDecorations.containsKey(nbttagcompound.getString("id")))
/*     */         {
/* 169 */           func_191095_a(MapDecoration.Type.func_191159_a(nbttagcompound.getByte("type")), player.world, nbttagcompound.getString("id"), nbttagcompound.getDouble("x"), nbttagcompound.getDouble("z"), nbttagcompound.getDouble("rot"));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void func_191094_a(ItemStack p_191094_0_, BlockPos p_191094_1_, String p_191094_2_, MapDecoration.Type p_191094_3_) {
/*     */     NBTTagList nbttaglist;
/* 179 */     if (p_191094_0_.hasTagCompound() && p_191094_0_.getTagCompound().hasKey("Decorations", 9)) {
/*     */       
/* 181 */       nbttaglist = p_191094_0_.getTagCompound().getTagList("Decorations", 10);
/*     */     }
/*     */     else {
/*     */       
/* 185 */       nbttaglist = new NBTTagList();
/* 186 */       p_191094_0_.setTagInfo("Decorations", (NBTBase)nbttaglist);
/*     */     } 
/*     */     
/* 189 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 190 */     nbttagcompound.setByte("type", p_191094_3_.func_191163_a());
/* 191 */     nbttagcompound.setString("id", p_191094_2_);
/* 192 */     nbttagcompound.setDouble("x", p_191094_1_.getX());
/* 193 */     nbttagcompound.setDouble("z", p_191094_1_.getZ());
/* 194 */     nbttagcompound.setDouble("rot", 180.0D);
/* 195 */     nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */     
/* 197 */     if (p_191094_3_.func_191162_c()) {
/*     */       
/* 199 */       NBTTagCompound nbttagcompound1 = p_191094_0_.func_190925_c("display");
/* 200 */       nbttagcompound1.setInteger("MapColor", p_191094_3_.func_191161_d());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void func_191095_a(MapDecoration.Type p_191095_1_, World p_191095_2_, String p_191095_3_, double p_191095_4_, double p_191095_6_, double p_191095_8_) {
/*     */     byte b2;
/* 206 */     int i = 1 << this.scale;
/* 207 */     float f = (float)(p_191095_4_ - this.xCenter) / i;
/* 208 */     float f1 = (float)(p_191095_6_ - this.zCenter) / i;
/* 209 */     byte b0 = (byte)(int)((f * 2.0F) + 0.5D);
/* 210 */     byte b1 = (byte)(int)((f1 * 2.0F) + 0.5D);
/* 211 */     int j = 63;
/*     */ 
/*     */     
/* 214 */     if (f >= -63.0F && f1 >= -63.0F && f <= 63.0F && f1 <= 63.0F) {
/*     */       
/* 216 */       p_191095_8_ += (p_191095_8_ < 0.0D) ? -8.0D : 8.0D;
/* 217 */       b2 = (byte)(int)(p_191095_8_ * 16.0D / 360.0D);
/*     */       
/* 219 */       if (this.dimension < 0)
/*     */       {
/* 221 */         int l = (int)(p_191095_2_.getWorldInfo().getWorldTime() / 10L);
/* 222 */         b2 = (byte)(l * l * 34187121 + l * 121 >> 15 & 0xF);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 227 */       if (p_191095_1_ != MapDecoration.Type.PLAYER) {
/*     */         
/* 229 */         this.mapDecorations.remove(p_191095_3_);
/*     */         
/*     */         return;
/*     */       } 
/* 233 */       int k = 320;
/*     */       
/* 235 */       if (Math.abs(f) < 320.0F && Math.abs(f1) < 320.0F) {
/*     */         
/* 237 */         p_191095_1_ = MapDecoration.Type.PLAYER_OFF_MAP;
/*     */       }
/*     */       else {
/*     */         
/* 241 */         if (!this.field_191096_f) {
/*     */           
/* 243 */           this.mapDecorations.remove(p_191095_3_);
/*     */           
/*     */           return;
/*     */         } 
/* 247 */         p_191095_1_ = MapDecoration.Type.PLAYER_OFF_LIMITS;
/*     */       } 
/*     */       
/* 250 */       b2 = 0;
/*     */       
/* 252 */       if (f <= -63.0F)
/*     */       {
/* 254 */         b0 = Byte.MIN_VALUE;
/*     */       }
/*     */       
/* 257 */       if (f1 <= -63.0F)
/*     */       {
/* 259 */         b1 = Byte.MIN_VALUE;
/*     */       }
/*     */       
/* 262 */       if (f >= 63.0F)
/*     */       {
/* 264 */         b0 = Byte.MAX_VALUE;
/*     */       }
/*     */       
/* 267 */       if (f1 >= 63.0F)
/*     */       {
/* 269 */         b1 = Byte.MAX_VALUE;
/*     */       }
/*     */     } 
/*     */     
/* 273 */     this.mapDecorations.put(p_191095_3_, new MapDecoration(p_191095_1_, b0, b1, b2));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Packet<?> getMapPacket(ItemStack mapStack, World worldIn, EntityPlayer player) {
/* 279 */     MapInfo mapdata$mapinfo = this.playersHashMap.get(player);
/* 280 */     return (mapdata$mapinfo == null) ? null : mapdata$mapinfo.getPacket(mapStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateMapData(int x, int y) {
/* 285 */     markDirty();
/*     */     
/* 287 */     for (MapInfo mapdata$mapinfo : this.playersArrayList)
/*     */     {
/* 289 */       mapdata$mapinfo.update(x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public MapInfo getMapInfo(EntityPlayer player) {
/* 295 */     MapInfo mapdata$mapinfo = this.playersHashMap.get(player);
/*     */     
/* 297 */     if (mapdata$mapinfo == null) {
/*     */       
/* 299 */       mapdata$mapinfo = new MapInfo(player);
/* 300 */       this.playersHashMap.put(player, mapdata$mapinfo);
/* 301 */       this.playersArrayList.add(mapdata$mapinfo);
/*     */     } 
/*     */     
/* 304 */     return mapdata$mapinfo;
/*     */   }
/*     */   
/*     */   public class MapInfo
/*     */   {
/*     */     public final EntityPlayer entityplayerObj;
/*     */     private boolean isDirty = true;
/*     */     private int minX;
/*     */     private int minY;
/* 313 */     private int maxX = 127;
/* 314 */     private int maxY = 127;
/*     */     
/*     */     private int tick;
/*     */     public int step;
/*     */     
/*     */     public MapInfo(EntityPlayer player) {
/* 320 */       this.entityplayerObj = player;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Packet<?> getPacket(ItemStack stack) {
/* 326 */       if (this.isDirty) {
/*     */         
/* 328 */         this.isDirty = false;
/* 329 */         return (Packet<?>)new SPacketMaps(stack.getMetadata(), MapData.this.scale, MapData.this.trackingPosition, MapData.this.mapDecorations.values(), MapData.this.colors, this.minX, this.minY, this.maxX + 1 - this.minX, this.maxY + 1 - this.minY);
/*     */       } 
/*     */ 
/*     */       
/* 333 */       return (this.tick++ % 5 == 0) ? (Packet<?>)new SPacketMaps(stack.getMetadata(), MapData.this.scale, MapData.this.trackingPosition, MapData.this.mapDecorations.values(), MapData.this.colors, 0, 0, 0, 0) : null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void update(int x, int y) {
/* 339 */       if (this.isDirty) {
/*     */         
/* 341 */         this.minX = Math.min(this.minX, x);
/* 342 */         this.minY = Math.min(this.minY, y);
/* 343 */         this.maxX = Math.max(this.maxX, x);
/* 344 */         this.maxY = Math.max(this.maxY, y);
/*     */       }
/*     */       else {
/*     */         
/* 348 */         this.isDirty = true;
/* 349 */         this.minX = x;
/* 350 */         this.minY = y;
/* 351 */         this.maxX = x;
/* 352 */         this.maxY = y;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\MapData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */