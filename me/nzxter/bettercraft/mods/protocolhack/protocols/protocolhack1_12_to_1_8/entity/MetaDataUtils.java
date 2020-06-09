/*     */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.entity;
/*     */ 
/*     */ import com.google.common.base.Optional;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.passive.AbstractHorse;
/*     */ import net.minecraft.entity.passive.EntityBat;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.entity.passive.EntityTameable;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Rotations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetaDataUtils
/*     */ {
/*     */   public static void handleMetadata_47(AbstractClientPlayer player, Entity entity, List<EntityDataManager.DataEntry<?>> entries, List<EntityDataManager.DataEntry<?>> output) {
/*     */     try {
/*  39 */       Entity e = entity;
/*  40 */       if (e instanceof Entity) {
/*  41 */         for (EntityDataManager.DataEntry<?> entry2 : entries) {
/*     */           
/*  43 */           int id = entry2.getKey().getId();
/*  44 */           if (id == 0) {
/*  45 */             int i = ((Byte)entry2.getValue()).byteValue();
/*  46 */             output.add(new EntityDataManager.DataEntry(Entity.FLAGS, Byte.valueOf((byte)i)));
/*     */             continue;
/*     */           } 
/*  49 */           if (id == 1) {
/*  50 */             int i = 0;
/*  51 */             if (entry2.getValue() instanceof Byte) {
/*  52 */               i = ((Byte)entry2.getValue()).byteValue();
/*  53 */             } else if (entry2.getValue() instanceof Short) {
/*  54 */               i = ((Short)entry2.getValue()).shortValue();
/*  55 */             } else if (entry2.getValue() instanceof Integer) {
/*  56 */               i = ((Integer)entry2.getValue()).intValue();
/*     */             } 
/*  58 */             output.add(new EntityDataManager.DataEntry(Entity.AIR, Integer.valueOf(i)));
/*     */             continue;
/*     */           } 
/*  61 */           if (id == 2) {
/*  62 */             output.add(new EntityDataManager.DataEntry(Entity.CUSTOM_NAME, entry2.getValue()));
/*     */             continue;
/*     */           } 
/*  65 */           if (id == 3) {
/*  66 */             int i = ((Byte)entry2.getValue()).byteValue();
/*  67 */             output.add(new EntityDataManager.DataEntry(Entity.CUSTOM_NAME_VISIBLE, Boolean.valueOf((i == 1))));
/*     */             continue;
/*     */           } 
/*  70 */           if (id != 4)
/*  71 */             continue;  int value3 = ((Byte)entry2.getValue()).byteValue();
/*  72 */           output.add(new EntityDataManager.DataEntry(Entity.SILENT, Boolean.valueOf((value3 == 1))));
/*     */         } 
/*     */       }
/*  75 */       if (e instanceof EntityLivingBase) {
/*  76 */         for (EntityDataManager.DataEntry<?> entry2 : entries) {
/*  77 */           int id = entry2.getKey().getId();
/*  78 */           if (id == 6) {
/*  79 */             float value4 = ((Float)entry2.getValue()).floatValue();
/*  80 */             output.add(new EntityDataManager.DataEntry(EntityLivingBase.HEALTH, Float.valueOf(value4)));
/*     */             continue;
/*     */           } 
/*  83 */           if (id == 7) {
/*  84 */             byte b = (byte)((Integer)entry2.getValue()).intValue();
/*  85 */             output.add(new EntityDataManager.DataEntry(EntityLivingBase.POTION_EFFECTS, Integer.valueOf(b)));
/*     */             continue;
/*     */           } 
/*  88 */           if (id == 8) {
/*  89 */             byte b = ((Byte)entry2.getValue()).byteValue();
/*  90 */             output.add(new EntityDataManager.DataEntry(EntityLivingBase.HIDE_PARTICLES, Boolean.valueOf((b == 1))));
/*     */             continue;
/*     */           } 
/*  93 */           if (id != 9)
/*  94 */             continue;  byte value2 = ((Byte)entry2.getValue()).byteValue();
/*  95 */           output.add(new EntityDataManager.DataEntry(EntityLivingBase.ARROW_COUNT_IN_ENTITY, Integer.valueOf(value2)));
/*     */         } 
/*     */       }
/*  98 */       if (e instanceof EntityLiving) {
/*  99 */         for (EntityDataManager.DataEntry<?> entry2 : entries) {
/* 100 */           int id = entry2.getKey().getId();
/* 101 */           if (id != 15)
/* 102 */             continue;  byte value2 = ((Byte)entry2.getValue()).byteValue();
/* 103 */           output.add(new EntityDataManager.DataEntry(EntityLiving.AI_FLAGS, Byte.valueOf(value2)));
/*     */         } 
/*     */       }
/* 106 */       if (e instanceof EntityAgeable) {
/* 107 */         for (EntityDataManager.DataEntry<?> entry2 : entries) {
/* 108 */           int id = entry2.getKey().getId();
/* 109 */           if (id != 12)
/* 110 */             continue;  byte value2 = ((Byte)entry2.getValue()).byteValue();
/* 111 */           output.add(new EntityDataManager.DataEntry(EntityAgeable.BABY, Boolean.valueOf((value2 == 1))));
/*     */         } 
/*     */       }
/* 114 */       if (e instanceof EntityTameable) {
/* 115 */         for (EntityDataManager.DataEntry<?> entry2 : entries) {
/* 116 */           int id = entry2.getKey().getId();
/* 117 */           if (id == 16) {
/* 118 */             byte value2 = ((Byte)entry2.getValue()).byteValue();
/* 119 */             output.add(new EntityDataManager.DataEntry(EntityTameable.TAMED, Byte.valueOf(value2)));
/*     */             continue;
/*     */           } 
/* 122 */           if (id != 17)
/*     */             continue;  try {
/* 124 */             String value5 = (String)entry2.getValue();
/* 125 */             output.add(new EntityDataManager.DataEntry(EntityTameable.OWNER_UNIQUE_ID, Optional.of(UUID.fromString(value5))));
/*     */           }
/* 127 */           catch (Throwable throwable) {}
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 132 */       if (e instanceof EntityOcelot) {
/* 133 */         for (EntityDataManager.DataEntry<?> entry2 : entries) {
/* 134 */           int id = entry2.getKey().getId();
/* 135 */           if (id != 18)
/* 136 */             continue;  byte value2 = ((Byte)entry2.getValue()).byteValue();
/* 137 */           output.add(new EntityDataManager.DataEntry(EntityOcelot.OCELOT_VARIANT, Integer.valueOf(value2)));
/*     */         } 
/*     */         return;
/*     */       } 
/* 141 */       if (e instanceof EntityWolf) {
/* 142 */         for (EntityDataManager.DataEntry<?> entry2 : entries) {
/* 143 */           int id = entry2.getKey().getId();
/* 144 */           if (id == 18) {
/* 145 */             float value6 = ((Float)entry2.getValue()).floatValue();
/* 146 */             output.add(new EntityDataManager.DataEntry(EntityWolf.DATA_HEALTH_ID, Float.valueOf(value6)));
/*     */             continue;
/*     */           } 
/* 149 */           if (id == 19) {
/* 150 */             byte b = ((Byte)entry2.getValue()).byteValue();
/* 151 */             output.add(new EntityDataManager.DataEntry(EntityWolf.BEGGING, Boolean.valueOf((b == 1))));
/*     */             continue;
/*     */           } 
/* 154 */           if (id != 20)
/* 155 */             continue;  byte value2 = ((Byte)entry2.getValue()).byteValue();
/* 156 */           output.add(new EntityDataManager.DataEntry(EntityWolf.COLLAR_COLOR, Integer.valueOf(value2)));
/*     */         } 
/*     */         return;
/*     */       } 
/* 160 */       if (e instanceof EntityPig) {
/* 161 */         for (EntityDataManager.DataEntry<?> entry2 : entries) {
/* 162 */           int id = entry2.getKey().getId();
/* 163 */           if (id != 16)
/* 164 */             continue;  byte value2 = ((Byte)entry2.getValue()).byteValue();
/* 165 */           output.add(new EntityDataManager.DataEntry(EntityPig.SADDLED, Boolean.valueOf((value2 == 1))));
/*     */         } 
/*     */         return;
/*     */       } 
/* 169 */       if (e instanceof EntityArmorStand) {
/* 170 */         for (EntityDataManager.DataEntry<?> entry2 : entries) {
/* 171 */           int id = entry2.getKey().getId();
/* 172 */           if (id == 10) {
/* 173 */             byte value2 = ((Byte)entry2.getValue()).byteValue();
/* 174 */             output.add(new EntityDataManager.DataEntry(EntityArmorStand.STATUS, Byte.valueOf(value2)));
/*     */             continue;
/*     */           } 
/* 177 */           if (id == 11) {
/* 178 */             Rotations value7 = (Rotations)entry2.getValue();
/* 179 */             output.add(new EntityDataManager.DataEntry(EntityArmorStand.HEAD_ROTATION, value7));
/*     */             continue;
/*     */           } 
/* 182 */           if (id == 12) {
/* 183 */             Rotations value8 = (Rotations)entry2.getValue();
/* 184 */             output.add(new EntityDataManager.DataEntry(EntityArmorStand.BODY_ROTATION, value8));
/*     */             continue;
/*     */           } 
/* 187 */           if (id == 13) {
/* 188 */             Rotations value9 = (Rotations)entry2.getValue();
/* 189 */             output.add(new EntityDataManager.DataEntry(EntityArmorStand.LEFT_ARM_ROTATION, value9));
/*     */             continue;
/*     */           } 
/* 192 */           if (id == 14) {
/* 193 */             Rotations value10 = (Rotations)entry2.getValue();
/* 194 */             output.add(new EntityDataManager.DataEntry(EntityArmorStand.RIGHT_ARM_ROTATION, value10));
/*     */             continue;
/*     */           } 
/* 197 */           if (id == 15) {
/* 198 */             Rotations value11 = (Rotations)entry2.getValue();
/* 199 */             output.add(new EntityDataManager.DataEntry(EntityArmorStand.LEFT_LEG_ROTATION, value11));
/*     */             continue;
/*     */           } 
/* 202 */           if (id != 16)
/* 203 */             continue;  Rotations value12 = (Rotations)entry2.getValue();
/* 204 */           output.add(new EntityDataManager.DataEntry(EntityArmorStand.RIGHT_LEG_ROTATION, value12));
/*     */         } 
/*     */         return;
/*     */       } 
/* 208 */       if (e instanceof EntityPlayer) {
/* 209 */         for (EntityDataManager.DataEntry<?> entry2 : entries) {
/* 210 */           int id = entry2.getKey().getId();
/* 211 */           if (id == 17) {
/* 212 */             float value13 = ((Float)entry2.getValue()).floatValue();
/* 213 */             output.add(new EntityDataManager.DataEntry(EntityPlayer.ABSORPTION, Float.valueOf(value13)));
/*     */             continue;
/*     */           } 
/* 216 */           if (id == 18) {
/* 217 */             byte value2 = (byte)((Integer)entry2.getValue()).intValue();
/* 218 */             output.add(new EntityDataManager.DataEntry(EntityPlayer.PLAYER_SCORE, Integer.valueOf(value2)));
/*     */             continue;
/*     */           } 
/* 221 */           if (id != 10)
/*     */             continue;  try {
/* 223 */             byte value2 = ((Byte)entry2.getValue()).byteValue();
/* 224 */             output.add(new EntityDataManager.DataEntry(EntityPlayer.PLAYER_MODEL_FLAG, Byte.valueOf(value2)));
/*     */           }
/* 226 */           catch (Exception e2) {
/* 227 */             byte value2 = 0;
/* 228 */             output.add(new EntityDataManager.DataEntry(EntityPlayer.PLAYER_MODEL_FLAG, Byte.valueOf(value2)));
/*     */           } 
/*     */         } 
/*     */         return;
/*     */       } 
/* 233 */       if (e instanceof AbstractHorse) {
/* 234 */         for (EntityDataManager.DataEntry<?> entry2 : entries) {
/* 235 */           int id = entry2.getKey().getId();
/* 236 */           if (id == 16) {
/* 237 */             byte b = (byte)((Integer)entry2.getValue()).intValue();
/* 238 */             output.add(new EntityDataManager.DataEntry(AbstractHorse.STATUS, Byte.valueOf(b)));
/* 239 */           } else if (id == 21) {
/* 240 */             String value14 = (String)entry2.getValue();
/* 241 */             output.add(new EntityDataManager.DataEntry(AbstractHorse.OWNER_UNIQUE_ID, Optional.of(UUID.fromString(value14))));
/*     */           } 
/* 243 */           if (!(e instanceof EntityHorse))
/* 244 */             continue;  if (id == 22) {
/* 245 */             byte b = (byte)((Integer)entry2.getValue()).intValue();
/* 246 */             output.add(new EntityDataManager.DataEntry(EntityHorse.HORSE_ARMOR, Integer.valueOf(b)));
/*     */             continue;
/*     */           } 
/* 249 */           if (id != 19)
/* 250 */             continue;  byte value2 = ((Byte)entry2.getValue()).byteValue();
/* 251 */           output.add(new EntityDataManager.DataEntry(EntityHorse.HORSE_VARIANT, Integer.valueOf(value2)));
/*     */         } 
/*     */         return;
/*     */       } 
/* 255 */       if (e instanceof EntityBat) {
/* 256 */         for (EntityDataManager.DataEntry<?> entry2 : entries) {
/* 257 */           int id = entry2.getKey().getId();
/* 258 */           if (id != 16)
/* 259 */             continue;  byte value2 = ((Byte)entry2.getValue()).byteValue();
/* 260 */           output.add(new EntityDataManager.DataEntry(EntityBat.HANGING, Byte.valueOf(value2)));
/*     */         } 
/*     */         return;
/*     */       } 
/* 264 */       if (e instanceof EntityItem) {
/* 265 */         for (EntityDataManager.DataEntry<?> entry2 : entries) {
/* 266 */           int id = entry2.getKey().getId();
/* 267 */           if (id != 10)
/* 268 */             continue;  ItemStack itm = (ItemStack)entry2.getValue();
/* 269 */           output.add(new EntityDataManager.DataEntry(EntityItem.ITEM, itm));
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/* 274 */     } catch (Throwable t) {
/* 275 */       t.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<EntityDataManager.DataEntry<?>> readEntries_47(PacketBuffer buf) {
/* 280 */     ArrayList<EntityDataManager.DataEntry<?>> list = null;
/* 281 */     for (byte i = buf.readByte(); i != Byte.MAX_VALUE; i = buf.readByte()) {
/* 282 */       int posX; float var9; int posY; float var10; int posZ; float var11; if (list == null) {
/* 283 */         list = Lists.newArrayList();
/*     */       }
/* 285 */       int type = (i & 0xE0) >> 5;
/* 286 */       int id = i & 0x1F;
/* 287 */       EntityDataManager.DataEntry<?> obj = null;
/* 288 */       switch (type) {
/*     */         case 0:
/* 290 */           obj = new EntityDataManager.DataEntry(new DataParameter(id, DataSerializers.BYTE), Byte.valueOf(buf.readByte()));
/*     */           break;
/*     */         
/*     */         case 1:
/* 294 */           obj = new EntityDataManager.DataEntry(new DataParameter(id, DataSerializers.VARINT), Integer.valueOf(buf.readShort()));
/*     */           break;
/*     */         
/*     */         case 2:
/* 298 */           obj = new EntityDataManager.DataEntry(new DataParameter(id, DataSerializers.VARINT), Integer.valueOf(buf.readInt()));
/*     */           break;
/*     */         
/*     */         case 3:
/* 302 */           obj = new EntityDataManager.DataEntry(new DataParameter(id, DataSerializers.FLOAT), Float.valueOf(buf.readFloat()));
/*     */           break;
/*     */         
/*     */         case 4:
/* 306 */           obj = new EntityDataManager.DataEntry(new DataParameter(id, DataSerializers.STRING), buf.readStringFromBuffer(32767));
/*     */           break;
/*     */         
/*     */         case 5:
/*     */           try {
/* 311 */             obj = new EntityDataManager.DataEntry(new DataParameter(id, DataSerializers.OPTIONAL_ITEM_STACK), buf.readItemStackFromBuffer());
/*     */           }
/* 313 */           catch (IOException e) {
/* 314 */             e.printStackTrace();
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 6:
/* 319 */           posX = buf.readInt();
/* 320 */           posY = buf.readInt();
/* 321 */           posZ = buf.readInt();
/* 322 */           obj = new EntityDataManager.DataEntry(new DataParameter(id, DataSerializers.BLOCK_POS), new BlockPos(posX, posY, posZ));
/*     */           break;
/*     */         
/*     */         case 7:
/* 326 */           var9 = buf.readFloat();
/* 327 */           var10 = buf.readFloat();
/* 328 */           var11 = buf.readFloat();
/* 329 */           obj = new EntityDataManager.DataEntry(new DataParameter(id, DataSerializers.ROTATIONS), new Rotations(var9, var10, var11));
/*     */           break;
/*     */       } 
/* 332 */       list.add(obj);
/*     */     } 
/* 334 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_8\entity\MetaDataUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */