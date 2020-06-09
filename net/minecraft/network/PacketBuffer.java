/*      */ package net.minecraft.network;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.buffer.ByteBufInputStream;
/*      */ import io.netty.buffer.ByteBufOutputStream;
/*      */ import io.netty.handler.codec.DecoderException;
/*      */ import io.netty.handler.codec.EncoderException;
/*      */ import io.netty.util.ByteProcessor;
/*      */ import io.netty.util.ReferenceCounted;
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.nio.channels.GatheringByteChannel;
/*      */ import java.nio.channels.ScatteringByteChannel;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.Date;
/*      */ import java.util.UUID;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.CompressedStreamTools;
/*      */ import net.minecraft.nbt.NBTSizeTracker;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ 
/*      */ public class PacketBuffer extends ByteBuf {
/*      */   private final ByteBuf buf;
/*      */   
/*      */   public PacketBuffer(ByteBuf wrapped) {
/*   38 */     this.buf = wrapped;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getVarIntSize(int input) {
/*   47 */     for (int i = 1; i < 5; i++) {
/*      */       
/*   49 */       if ((input & -1 << i * 7) == 0)
/*      */       {
/*   51 */         return i;
/*      */       }
/*      */     } 
/*      */     
/*   55 */     return 5;
/*      */   }
/*      */ 
/*      */   
/*      */   public PacketBuffer writeByteArray(byte[] array) {
/*   60 */     writeVarIntToBuffer(array.length);
/*   61 */     writeBytes(array);
/*   62 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] readByteArray() {
/*   67 */     return readByteArray(readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] readByteArray(int maxLength) {
/*   72 */     int i = readVarIntFromBuffer();
/*      */     
/*   74 */     if (i > maxLength)
/*      */     {
/*   76 */       throw new DecoderException("ByteArray with size " + i + " is bigger than allowed " + maxLength);
/*      */     }
/*      */ 
/*      */     
/*   80 */     byte[] abyte = new byte[i];
/*   81 */     readBytes(abyte);
/*   82 */     return abyte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PacketBuffer writeVarIntArray(int[] array) {
/*   91 */     writeVarIntToBuffer(array.length); byte b;
/*      */     int i, arrayOfInt[];
/*   93 */     for (i = (arrayOfInt = array).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/*      */       
/*   95 */       writeVarIntToBuffer(j);
/*      */       b++; }
/*      */     
/*   98 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int[] readVarIntArray() {
/*  103 */     return readVarIntArray(readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public int[] readVarIntArray(int maxLength) {
/*  108 */     int i = readVarIntFromBuffer();
/*      */     
/*  110 */     if (i > maxLength)
/*      */     {
/*  112 */       throw new DecoderException("VarIntArray with size " + i + " is bigger than allowed " + maxLength);
/*      */     }
/*      */ 
/*      */     
/*  116 */     int[] aint = new int[i];
/*      */     
/*  118 */     for (int j = 0; j < aint.length; j++)
/*      */     {
/*  120 */       aint[j] = readVarIntFromBuffer();
/*      */     }
/*      */     
/*  123 */     return aint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PacketBuffer writeLongArray(long[] array) {
/*  132 */     writeVarIntToBuffer(array.length); byte b; int i;
/*      */     long[] arrayOfLong;
/*  134 */     for (i = (arrayOfLong = array).length, b = 0; b < i; ) { long l = arrayOfLong[b];
/*      */       
/*  136 */       writeLong(l);
/*      */       b++; }
/*      */     
/*  139 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long[] readLongArray(@Nullable long[] array) {
/*  147 */     return readLongArray(array, readableBytes() / 8);
/*      */   }
/*      */ 
/*      */   
/*      */   public long[] readLongArray(@Nullable long[] p_189423_1_, int p_189423_2_) {
/*  152 */     int i = readVarIntFromBuffer();
/*      */     
/*  154 */     if (p_189423_1_ == null || p_189423_1_.length != i) {
/*      */       
/*  156 */       if (i > p_189423_2_)
/*      */       {
/*  158 */         throw new DecoderException("LongArray with size " + i + " is bigger than allowed " + p_189423_2_);
/*      */       }
/*      */       
/*  161 */       p_189423_1_ = new long[i];
/*      */     } 
/*      */     
/*  164 */     for (int j = 0; j < p_189423_1_.length; j++)
/*      */     {
/*  166 */       p_189423_1_[j] = readLong();
/*      */     }
/*      */     
/*  169 */     return p_189423_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockPos readBlockPos() {
/*  174 */     return BlockPos.fromLong(readLong());
/*      */   }
/*      */ 
/*      */   
/*      */   public PacketBuffer writeBlockPos(BlockPos pos) {
/*  179 */     writeLong(pos.toLong());
/*  180 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ITextComponent readTextComponent() throws IOException {
/*  185 */     return ITextComponent.Serializer.jsonToComponent(readStringFromBuffer(32767));
/*      */   }
/*      */ 
/*      */   
/*      */   public PacketBuffer writeTextComponent(ITextComponent component) {
/*  190 */     return writeString(ITextComponent.Serializer.componentToJson(component));
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Enum<T>> T readEnumValue(Class<T> enumClass) {
/*  195 */     return (T)((Enum[])enumClass.getEnumConstants())[readVarIntFromBuffer()];
/*      */   }
/*      */ 
/*      */   
/*      */   public PacketBuffer writeEnumValue(Enum<?> value) {
/*  200 */     return writeVarIntToBuffer(value.ordinal());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int readVarIntFromBuffer() {
/*      */     byte b0;
/*  209 */     int i = 0;
/*  210 */     int j = 0;
/*      */ 
/*      */     
/*      */     do {
/*  214 */       b0 = readByte();
/*  215 */       i |= (b0 & Byte.MAX_VALUE) << j++ * 7;
/*      */       
/*  217 */       if (j > 5)
/*      */       {
/*  219 */         throw new RuntimeException("VarInt too big");
/*      */       }
/*      */     }
/*  222 */     while ((b0 & 0x80) == 128);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  228 */     return i;
/*      */   }
/*      */   
/*      */   public long readVarLong() {
/*      */     byte b0;
/*  233 */     long i = 0L;
/*  234 */     int j = 0;
/*      */ 
/*      */     
/*      */     do {
/*  238 */       b0 = readByte();
/*  239 */       i |= (b0 & Byte.MAX_VALUE) << j++ * 7;
/*      */       
/*  241 */       if (j > 10)
/*      */       {
/*  243 */         throw new RuntimeException("VarLong too big");
/*      */       }
/*      */     }
/*  246 */     while ((b0 & 0x80) == 128);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  252 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public PacketBuffer writeUuid(UUID uuid) {
/*  257 */     writeLong(uuid.getMostSignificantBits());
/*  258 */     writeLong(uuid.getLeastSignificantBits());
/*  259 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public UUID readUuid() {
/*  264 */     return new UUID(readLong(), readLong());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PacketBuffer writeVarIntToBuffer(int input) {
/*  275 */     while ((input & 0xFFFFFF80) != 0) {
/*      */       
/*  277 */       writeByte(input & 0x7F | 0x80);
/*  278 */       input >>>= 7;
/*      */     } 
/*      */     
/*  281 */     writeByte(input);
/*  282 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public PacketBuffer writeVarLong(long value) {
/*  287 */     while ((value & 0xFFFFFFFFFFFFFF80L) != 0L) {
/*      */       
/*  289 */       writeByte((int)(value & 0x7FL) | 0x80);
/*  290 */       value >>>= 7L;
/*      */     } 
/*      */     
/*  293 */     writeByte((int)value);
/*  294 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PacketBuffer writeNBTTagCompoundToBuffer(@Nullable NBTTagCompound nbt) {
/*  302 */     if (nbt == null) {
/*      */       
/*  304 */       writeByte(0);
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  310 */         CompressedStreamTools.write(nbt, (DataOutput)new ByteBufOutputStream(this));
/*      */       }
/*  312 */       catch (IOException ioexception) {
/*      */         
/*  314 */         throw new EncoderException(ioexception);
/*      */       } 
/*      */     } 
/*      */     
/*  318 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException {
/*  328 */     int i = readerIndex();
/*  329 */     byte b0 = readByte();
/*      */     
/*  331 */     if (b0 == 0)
/*      */     {
/*  333 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  337 */     readerIndex(i);
/*      */ 
/*      */     
/*      */     try {
/*  341 */       return CompressedStreamTools.read((DataInput)new ByteBufInputStream(this), new NBTSizeTracker(2097152L));
/*      */     }
/*  343 */     catch (IOException ioexception) {
/*      */       
/*  345 */       throw new EncoderException(ioexception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PacketBuffer writeItemStackToBuffer(ItemStack stack) {
/*  355 */     if (stack.func_190926_b()) {
/*      */       
/*  357 */       writeShort(-1);
/*      */     }
/*      */     else {
/*      */       
/*  361 */       writeShort(Item.getIdFromItem(stack.getItem()));
/*  362 */       writeByte(stack.func_190916_E());
/*  363 */       writeShort(stack.getMetadata());
/*  364 */       NBTTagCompound nbttagcompound = null;
/*      */       
/*  366 */       if (stack.getItem().isDamageable() || stack.getItem().getShareTag())
/*      */       {
/*  368 */         nbttagcompound = stack.getTagCompound();
/*      */       }
/*      */       
/*  371 */       writeNBTTagCompoundToBuffer(nbttagcompound);
/*      */     } 
/*      */     
/*  374 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack readItemStackFromBuffer() throws IOException {
/*  382 */     int i = readShort();
/*      */     
/*  384 */     if (i < 0)
/*      */     {
/*  386 */       return ItemStack.field_190927_a;
/*      */     }
/*      */ 
/*      */     
/*  390 */     int j = readByte();
/*  391 */     int k = readShort();
/*  392 */     ItemStack itemstack = new ItemStack(Item.getItemById(i), j, k);
/*  393 */     itemstack.setTagCompound(readNBTTagCompoundFromBuffer());
/*  394 */     return itemstack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String readStringFromBuffer(int maxLength) {
/*  404 */     int i = readVarIntFromBuffer();
/*      */     
/*  406 */     if (i > maxLength * 4)
/*      */     {
/*  408 */       throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + i + " > " + (maxLength * 4) + ")");
/*      */     }
/*  410 */     if (i < 0)
/*      */     {
/*  412 */       throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
/*      */     }
/*      */ 
/*      */     
/*  416 */     String s = toString(readerIndex(), i, StandardCharsets.UTF_8);
/*  417 */     readerIndex(readerIndex() + i);
/*      */     
/*  419 */     if (s.length() > maxLength)
/*      */     {
/*  421 */       throw new DecoderException("The received string length is longer than maximum allowed (" + i + " > " + maxLength + ")");
/*      */     }
/*      */ 
/*      */     
/*  425 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PacketBuffer writeString(String string) {
/*  432 */     byte[] abyte = string.getBytes(StandardCharsets.UTF_8);
/*      */     
/*  434 */     if (abyte.length > 32767)
/*      */     {
/*  436 */       throw new EncoderException("String too big (was " + abyte.length + " bytes encoded, max " + '翿' + ")");
/*      */     }
/*      */ 
/*      */     
/*  440 */     writeVarIntToBuffer(abyte.length);
/*  441 */     writeBytes(abyte);
/*  442 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ResourceLocation func_192575_l() {
/*  448 */     return new ResourceLocation(readStringFromBuffer(32767));
/*      */   }
/*      */ 
/*      */   
/*      */   public PacketBuffer func_192572_a(ResourceLocation p_192572_1_) {
/*  453 */     writeString(p_192572_1_.toString());
/*  454 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public Date func_192573_m() {
/*  459 */     return new Date(readLong());
/*      */   }
/*      */ 
/*      */   
/*      */   public PacketBuffer func_192574_a(Date p_192574_1_) {
/*  464 */     writeLong(p_192574_1_.getTime());
/*  465 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int capacity() {
/*  470 */     return this.buf.capacity();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf capacity(int p_capacity_1_) {
/*  475 */     return this.buf.capacity(p_capacity_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxCapacity() {
/*  480 */     return this.buf.maxCapacity();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBufAllocator alloc() {
/*  485 */     return this.buf.alloc();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteOrder order() {
/*  490 */     return this.buf.order();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf order(ByteOrder p_order_1_) {
/*  495 */     return this.buf.order(p_order_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf unwrap() {
/*  500 */     return this.buf.unwrap();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDirect() {
/*  505 */     return this.buf.isDirect();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() {
/*  510 */     return this.buf.isReadOnly();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf asReadOnly() {
/*  515 */     return this.buf.asReadOnly();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readerIndex() {
/*  520 */     return this.buf.readerIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readerIndex(int p_readerIndex_1_) {
/*  525 */     return this.buf.readerIndex(p_readerIndex_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int writerIndex() {
/*  530 */     return this.buf.writerIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writerIndex(int p_writerIndex_1_) {
/*  535 */     return this.buf.writerIndex(p_writerIndex_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setIndex(int p_setIndex_1_, int p_setIndex_2_) {
/*  540 */     return this.buf.setIndex(p_setIndex_1_, p_setIndex_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int readableBytes() {
/*  545 */     return this.buf.readableBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public int writableBytes() {
/*  550 */     return this.buf.writableBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxWritableBytes() {
/*  555 */     return this.buf.maxWritableBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable() {
/*  560 */     return this.buf.isReadable();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable(int p_isReadable_1_) {
/*  565 */     return this.buf.isReadable(p_isReadable_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable() {
/*  570 */     return this.buf.isWritable();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable(int p_isWritable_1_) {
/*  575 */     return this.buf.isWritable(p_isWritable_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf clear() {
/*  580 */     return this.buf.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markReaderIndex() {
/*  585 */     return this.buf.markReaderIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetReaderIndex() {
/*  590 */     return this.buf.resetReaderIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markWriterIndex() {
/*  595 */     return this.buf.markWriterIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetWriterIndex() {
/*  600 */     return this.buf.resetWriterIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardReadBytes() {
/*  605 */     return this.buf.discardReadBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardSomeReadBytes() {
/*  610 */     return this.buf.discardSomeReadBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf ensureWritable(int p_ensureWritable_1_) {
/*  615 */     return this.buf.ensureWritable(p_ensureWritable_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ensureWritable(int p_ensureWritable_1_, boolean p_ensureWritable_2_) {
/*  620 */     return this.buf.ensureWritable(p_ensureWritable_1_, p_ensureWritable_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int p_getBoolean_1_) {
/*  625 */     return this.buf.getBoolean(p_getBoolean_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(int p_getByte_1_) {
/*  630 */     return this.buf.getByte(p_getByte_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getUnsignedByte(int p_getUnsignedByte_1_) {
/*  635 */     return this.buf.getUnsignedByte(p_getUnsignedByte_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShort(int p_getShort_1_) {
/*  640 */     return this.buf.getShort(p_getShort_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShortLE(int p_getShortLE_1_) {
/*  645 */     return this.buf.getShortLE(p_getShortLE_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedShort(int p_getUnsignedShort_1_) {
/*  650 */     return this.buf.getUnsignedShort(p_getUnsignedShort_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedShortLE(int p_getUnsignedShortLE_1_) {
/*  655 */     return this.buf.getUnsignedShortLE(p_getUnsignedShortLE_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMedium(int p_getMedium_1_) {
/*  660 */     return this.buf.getMedium(p_getMedium_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMediumLE(int p_getMediumLE_1_) {
/*  665 */     return this.buf.getMediumLE(p_getMediumLE_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMedium(int p_getUnsignedMedium_1_) {
/*  670 */     return this.buf.getUnsignedMedium(p_getUnsignedMedium_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMediumLE(int p_getUnsignedMediumLE_1_) {
/*  675 */     return this.buf.getUnsignedMediumLE(p_getUnsignedMediumLE_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(int p_getInt_1_) {
/*  680 */     return this.buf.getInt(p_getInt_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getIntLE(int p_getIntLE_1_) {
/*  685 */     return this.buf.getIntLE(p_getIntLE_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getUnsignedInt(int p_getUnsignedInt_1_) {
/*  690 */     return this.buf.getUnsignedInt(p_getUnsignedInt_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getUnsignedIntLE(int p_getUnsignedIntLE_1_) {
/*  695 */     return this.buf.getUnsignedIntLE(p_getUnsignedIntLE_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(int p_getLong_1_) {
/*  700 */     return this.buf.getLong(p_getLong_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLongLE(int p_getLongLE_1_) {
/*  705 */     return this.buf.getLongLE(p_getLongLE_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public char getChar(int p_getChar_1_) {
/*  710 */     return this.buf.getChar(p_getChar_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(int p_getFloat_1_) {
/*  715 */     return this.buf.getFloat(p_getFloat_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(int p_getDouble_1_) {
/*  720 */     return this.buf.getDouble(p_getDouble_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_) {
/*  725 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_) {
/*  730 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_) {
/*  735 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int p_getBytes_1_, byte[] p_getBytes_2_) {
/*  740 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int p_getBytes_1_, byte[] p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_) {
/*  745 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuffer p_getBytes_2_) {
/*  750 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int p_getBytes_1_, OutputStream p_getBytes_2_, int p_getBytes_3_) throws IOException {
/*  755 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBytes(int p_getBytes_1_, GatheringByteChannel p_getBytes_2_, int p_getBytes_3_) throws IOException {
/*  760 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBytes(int p_getBytes_1_, FileChannel p_getBytes_2_, long p_getBytes_3_, int p_getBytes_5_) throws IOException {
/*  765 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_5_);
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSequence getCharSequence(int p_getCharSequence_1_, int p_getCharSequence_2_, Charset p_getCharSequence_3_) {
/*  770 */     return this.buf.getCharSequence(p_getCharSequence_1_, p_getCharSequence_2_, p_getCharSequence_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBoolean(int p_setBoolean_1_, boolean p_setBoolean_2_) {
/*  775 */     return this.buf.setBoolean(p_setBoolean_1_, p_setBoolean_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setByte(int p_setByte_1_, int p_setByte_2_) {
/*  780 */     return this.buf.setByte(p_setByte_1_, p_setByte_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setShort(int p_setShort_1_, int p_setShort_2_) {
/*  785 */     return this.buf.setShort(p_setShort_1_, p_setShort_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setShortLE(int p_setShortLE_1_, int p_setShortLE_2_) {
/*  790 */     return this.buf.setShortLE(p_setShortLE_1_, p_setShortLE_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setMedium(int p_setMedium_1_, int p_setMedium_2_) {
/*  795 */     return this.buf.setMedium(p_setMedium_1_, p_setMedium_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setMediumLE(int p_setMediumLE_1_, int p_setMediumLE_2_) {
/*  800 */     return this.buf.setMediumLE(p_setMediumLE_1_, p_setMediumLE_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setInt(int p_setInt_1_, int p_setInt_2_) {
/*  805 */     return this.buf.setInt(p_setInt_1_, p_setInt_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setIntLE(int p_setIntLE_1_, int p_setIntLE_2_) {
/*  810 */     return this.buf.setIntLE(p_setIntLE_1_, p_setIntLE_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setLong(int p_setLong_1_, long p_setLong_2_) {
/*  815 */     return this.buf.setLong(p_setLong_1_, p_setLong_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setLongLE(int p_setLongLE_1_, long p_setLongLE_2_) {
/*  820 */     return this.buf.setLongLE(p_setLongLE_1_, p_setLongLE_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setChar(int p_setChar_1_, int p_setChar_2_) {
/*  825 */     return this.buf.setChar(p_setChar_1_, p_setChar_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setFloat(int p_setFloat_1_, float p_setFloat_2_) {
/*  830 */     return this.buf.setFloat(p_setFloat_1_, p_setFloat_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setDouble(int p_setDouble_1_, double p_setDouble_2_) {
/*  835 */     return this.buf.setDouble(p_setDouble_1_, p_setDouble_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_) {
/*  840 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_) {
/*  845 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_) {
/*  850 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int p_setBytes_1_, byte[] p_setBytes_2_) {
/*  855 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int p_setBytes_1_, byte[] p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_) {
/*  860 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuffer p_setBytes_2_) {
/*  865 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int p_setBytes_1_, InputStream p_setBytes_2_, int p_setBytes_3_) throws IOException {
/*  870 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int p_setBytes_1_, ScatteringByteChannel p_setBytes_2_, int p_setBytes_3_) throws IOException {
/*  875 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int p_setBytes_1_, FileChannel p_setBytes_2_, long p_setBytes_3_, int p_setBytes_5_) throws IOException {
/*  880 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_5_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setZero(int p_setZero_1_, int p_setZero_2_) {
/*  885 */     return this.buf.setZero(p_setZero_1_, p_setZero_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int setCharSequence(int p_setCharSequence_1_, CharSequence p_setCharSequence_2_, Charset p_setCharSequence_3_) {
/*  890 */     return this.buf.setCharSequence(p_setCharSequence_1_, p_setCharSequence_2_, p_setCharSequence_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean readBoolean() {
/*  895 */     return this.buf.readBoolean();
/*      */   }
/*      */ 
/*      */   
/*      */   public byte readByte() {
/*  900 */     return this.buf.readByte();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readUnsignedByte() {
/*  905 */     return this.buf.readUnsignedByte();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShort() {
/*  910 */     return this.buf.readShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShortLE() {
/*  915 */     return this.buf.readShortLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShort() {
/*  920 */     return this.buf.readUnsignedShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShortLE() {
/*  925 */     return this.buf.readUnsignedShortLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMedium() {
/*  930 */     return this.buf.readMedium();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMediumLE() {
/*  935 */     return this.buf.readMediumLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMedium() {
/*  940 */     return this.buf.readUnsignedMedium();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMediumLE() {
/*  945 */     return this.buf.readUnsignedMediumLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readInt() {
/*  950 */     return this.buf.readInt();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readIntLE() {
/*  955 */     return this.buf.readIntLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedInt() {
/*  960 */     return this.buf.readUnsignedInt();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedIntLE() {
/*  965 */     return this.buf.readUnsignedIntLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLong() {
/*  970 */     return this.buf.readLong();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLongLE() {
/*  975 */     return this.buf.readLongLE();
/*      */   }
/*      */ 
/*      */   
/*      */   public char readChar() {
/*  980 */     return this.buf.readChar();
/*      */   }
/*      */ 
/*      */   
/*      */   public float readFloat() {
/*  985 */     return this.buf.readFloat();
/*      */   }
/*      */ 
/*      */   
/*      */   public double readDouble() {
/*  990 */     return this.buf.readDouble();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(int p_readBytes_1_) {
/*  995 */     return this.buf.readBytes(p_readBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readSlice(int p_readSlice_1_) {
/* 1000 */     return this.buf.readSlice(p_readSlice_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readRetainedSlice(int p_readRetainedSlice_1_) {
/* 1005 */     return this.buf.readRetainedSlice(p_readRetainedSlice_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf p_readBytes_1_) {
/* 1010 */     return this.buf.readBytes(p_readBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf p_readBytes_1_, int p_readBytes_2_) {
/* 1015 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_) {
/* 1020 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] p_readBytes_1_) {
/* 1025 */     return this.buf.readBytes(p_readBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_) {
/* 1030 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuffer p_readBytes_1_) {
/* 1035 */     return this.buf.readBytes(p_readBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(OutputStream p_readBytes_1_, int p_readBytes_2_) throws IOException {
/* 1040 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int readBytes(GatheringByteChannel p_readBytes_1_, int p_readBytes_2_) throws IOException {
/* 1045 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public CharSequence readCharSequence(int p_readCharSequence_1_, Charset p_readCharSequence_2_) {
/* 1050 */     return this.buf.readCharSequence(p_readCharSequence_1_, p_readCharSequence_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int readBytes(FileChannel p_readBytes_1_, long p_readBytes_2_, int p_readBytes_4_) throws IOException {
/* 1055 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf skipBytes(int p_skipBytes_1_) {
/* 1060 */     return this.buf.skipBytes(p_skipBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBoolean(boolean p_writeBoolean_1_) {
/* 1065 */     return this.buf.writeBoolean(p_writeBoolean_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeByte(int p_writeByte_1_) {
/* 1070 */     return this.buf.writeByte(p_writeByte_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeShort(int p_writeShort_1_) {
/* 1075 */     return this.buf.writeShort(p_writeShort_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeShortLE(int p_writeShortLE_1_) {
/* 1080 */     return this.buf.writeShortLE(p_writeShortLE_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeMedium(int p_writeMedium_1_) {
/* 1085 */     return this.buf.writeMedium(p_writeMedium_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeMediumLE(int p_writeMediumLE_1_) {
/* 1090 */     return this.buf.writeMediumLE(p_writeMediumLE_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeInt(int p_writeInt_1_) {
/* 1095 */     return this.buf.writeInt(p_writeInt_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeIntLE(int p_writeIntLE_1_) {
/* 1100 */     return this.buf.writeIntLE(p_writeIntLE_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeLong(long p_writeLong_1_) {
/* 1105 */     return this.buf.writeLong(p_writeLong_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeLongLE(long p_writeLongLE_1_) {
/* 1110 */     return this.buf.writeLongLE(p_writeLongLE_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeChar(int p_writeChar_1_) {
/* 1115 */     return this.buf.writeChar(p_writeChar_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeFloat(float p_writeFloat_1_) {
/* 1120 */     return this.buf.writeFloat(p_writeFloat_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeDouble(double p_writeDouble_1_) {
/* 1125 */     return this.buf.writeDouble(p_writeDouble_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_) {
/* 1130 */     return this.buf.writeBytes(p_writeBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_, int p_writeBytes_2_) {
/* 1135 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_) {
/* 1140 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] p_writeBytes_1_) {
/* 1145 */     return this.buf.writeBytes(p_writeBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_) {
/* 1150 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuffer p_writeBytes_1_) {
/* 1155 */     return this.buf.writeBytes(p_writeBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(InputStream p_writeBytes_1_, int p_writeBytes_2_) throws IOException {
/* 1160 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(ScatteringByteChannel p_writeBytes_1_, int p_writeBytes_2_) throws IOException {
/* 1165 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(FileChannel p_writeBytes_1_, long p_writeBytes_2_, int p_writeBytes_4_) throws IOException {
/* 1170 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeZero(int p_writeZero_1_) {
/* 1175 */     return this.buf.writeZero(p_writeZero_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeCharSequence(CharSequence p_writeCharSequence_1_, Charset p_writeCharSequence_2_) {
/* 1180 */     return this.buf.writeCharSequence(p_writeCharSequence_1_, p_writeCharSequence_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int indexOf(int p_indexOf_1_, int p_indexOf_2_, byte p_indexOf_3_) {
/* 1185 */     return this.buf.indexOf(p_indexOf_1_, p_indexOf_2_, p_indexOf_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(byte p_bytesBefore_1_) {
/* 1190 */     return this.buf.bytesBefore(p_bytesBefore_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int p_bytesBefore_1_, byte p_bytesBefore_2_) {
/* 1195 */     return this.buf.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int p_bytesBefore_1_, int p_bytesBefore_2_, byte p_bytesBefore_3_) {
/* 1200 */     return this.buf.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_, p_bytesBefore_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(ByteProcessor p_forEachByte_1_) {
/* 1205 */     return this.buf.forEachByte(p_forEachByte_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(int p_forEachByte_1_, int p_forEachByte_2_, ByteProcessor p_forEachByte_3_) {
/* 1210 */     return this.buf.forEachByte(p_forEachByte_1_, p_forEachByte_2_, p_forEachByte_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(ByteProcessor p_forEachByteDesc_1_) {
/* 1215 */     return this.buf.forEachByteDesc(p_forEachByteDesc_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(int p_forEachByteDesc_1_, int p_forEachByteDesc_2_, ByteProcessor p_forEachByteDesc_3_) {
/* 1220 */     return this.buf.forEachByteDesc(p_forEachByteDesc_1_, p_forEachByteDesc_2_, p_forEachByteDesc_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy() {
/* 1225 */     return this.buf.copy();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy(int p_copy_1_, int p_copy_2_) {
/* 1230 */     return this.buf.copy(p_copy_1_, p_copy_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice() {
/* 1235 */     return this.buf.slice();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedSlice() {
/* 1240 */     return this.buf.retainedSlice();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice(int p_slice_1_, int p_slice_2_) {
/* 1245 */     return this.buf.slice(p_slice_1_, p_slice_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedSlice(int p_retainedSlice_1_, int p_retainedSlice_2_) {
/* 1250 */     return this.buf.retainedSlice(p_retainedSlice_1_, p_retainedSlice_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf duplicate() {
/* 1255 */     return this.buf.duplicate();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retainedDuplicate() {
/* 1260 */     return this.buf.retainedDuplicate();
/*      */   }
/*      */ 
/*      */   
/*      */   public int nioBufferCount() {
/* 1265 */     return this.buf.nioBufferCount();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer() {
/* 1270 */     return this.buf.nioBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer(int p_nioBuffer_1_, int p_nioBuffer_2_) {
/* 1275 */     return this.buf.nioBuffer(p_nioBuffer_1_, p_nioBuffer_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer internalNioBuffer(int p_internalNioBuffer_1_, int p_internalNioBuffer_2_) {
/* 1280 */     return this.buf.internalNioBuffer(p_internalNioBuffer_1_, p_internalNioBuffer_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers() {
/* 1285 */     return this.buf.nioBuffers();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers(int p_nioBuffers_1_, int p_nioBuffers_2_) {
/* 1290 */     return this.buf.nioBuffers(p_nioBuffers_1_, p_nioBuffers_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasArray() {
/* 1295 */     return this.buf.hasArray();
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] array() {
/* 1300 */     return this.buf.array();
/*      */   }
/*      */ 
/*      */   
/*      */   public int arrayOffset() {
/* 1305 */     return this.buf.arrayOffset();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasMemoryAddress() {
/* 1310 */     return this.buf.hasMemoryAddress();
/*      */   }
/*      */ 
/*      */   
/*      */   public long memoryAddress() {
/* 1315 */     return this.buf.memoryAddress();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(Charset p_toString_1_) {
/* 1320 */     return this.buf.toString(p_toString_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(int p_toString_1_, int p_toString_2_, Charset p_toString_3_) {
/* 1325 */     return this.buf.toString(p_toString_1_, p_toString_2_, p_toString_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1330 */     return this.buf.hashCode();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object p_equals_1_) {
/* 1335 */     return this.buf.equals(p_equals_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(ByteBuf p_compareTo_1_) {
/* 1340 */     return this.buf.compareTo(p_compareTo_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1345 */     return this.buf.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retain(int p_retain_1_) {
/* 1350 */     return this.buf.retain(p_retain_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retain() {
/* 1355 */     return this.buf.retain();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf touch() {
/* 1360 */     return this.buf.touch();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf touch(Object p_touch_1_) {
/* 1365 */     return this.buf.touch(p_touch_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int refCnt() {
/* 1370 */     return this.buf.refCnt();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean release() {
/* 1375 */     return this.buf.release();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean release(int p_release_1_) {
/* 1380 */     return this.buf.release(p_release_1_);
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\PacketBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */