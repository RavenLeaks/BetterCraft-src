/*     */ package net.minecraft.network.datasync;
/*     */ 
/*     */ import com.google.common.base.Optional;
/*     */ import java.io.IOException;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IntIdentityHashBiMap;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Rotations;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ 
/*     */ public class DataSerializers
/*     */ {
/*  20 */   private static final IntIdentityHashBiMap<DataSerializer<?>> REGISTRY = new IntIdentityHashBiMap(16);
/*  21 */   public static final DataSerializer<Byte> BYTE = new DataSerializer<Byte>()
/*     */     {
/*     */       public void write(PacketBuffer buf, Byte value)
/*     */       {
/*  25 */         buf.writeByte(value.byteValue());
/*     */       }
/*     */       
/*     */       public Byte read(PacketBuffer buf) throws IOException {
/*  29 */         return Byte.valueOf(buf.readByte());
/*     */       }
/*     */       
/*     */       public DataParameter<Byte> createKey(int id) {
/*  33 */         return new DataParameter<>(id, this);
/*     */       }
/*     */       
/*     */       public Byte func_192717_a(Byte p_192717_1_) {
/*  37 */         return p_192717_1_;
/*     */       }
/*     */     };
/*  40 */   public static final DataSerializer<Integer> VARINT = new DataSerializer<Integer>()
/*     */     {
/*     */       public void write(PacketBuffer buf, Integer value)
/*     */       {
/*  44 */         buf.writeVarIntToBuffer(value.intValue());
/*     */       }
/*     */       
/*     */       public Integer read(PacketBuffer buf) throws IOException {
/*  48 */         return Integer.valueOf(buf.readVarIntFromBuffer());
/*     */       }
/*     */       
/*     */       public DataParameter<Integer> createKey(int id) {
/*  52 */         return new DataParameter<>(id, this);
/*     */       }
/*     */       
/*     */       public Integer func_192717_a(Integer p_192717_1_) {
/*  56 */         return p_192717_1_;
/*     */       }
/*     */     };
/*  59 */   public static final DataSerializer<Float> FLOAT = new DataSerializer<Float>()
/*     */     {
/*     */       public void write(PacketBuffer buf, Float value)
/*     */       {
/*  63 */         buf.writeFloat(value.floatValue());
/*     */       }
/*     */       
/*     */       public Float read(PacketBuffer buf) throws IOException {
/*  67 */         return Float.valueOf(buf.readFloat());
/*     */       }
/*     */       
/*     */       public DataParameter<Float> createKey(int id) {
/*  71 */         return new DataParameter<>(id, this);
/*     */       }
/*     */       
/*     */       public Float func_192717_a(Float p_192717_1_) {
/*  75 */         return p_192717_1_;
/*     */       }
/*     */     };
/*  78 */   public static final DataSerializer<String> STRING = new DataSerializer<String>()
/*     */     {
/*     */       public void write(PacketBuffer buf, String value)
/*     */       {
/*  82 */         buf.writeString(value);
/*     */       }
/*     */       
/*     */       public String read(PacketBuffer buf) throws IOException {
/*  86 */         return buf.readStringFromBuffer(32767);
/*     */       }
/*     */       
/*     */       public DataParameter<String> createKey(int id) {
/*  90 */         return new DataParameter<>(id, this);
/*     */       }
/*     */       
/*     */       public String func_192717_a(String p_192717_1_) {
/*  94 */         return p_192717_1_;
/*     */       }
/*     */     };
/*  97 */   public static final DataSerializer<ITextComponent> TEXT_COMPONENT = new DataSerializer<ITextComponent>()
/*     */     {
/*     */       public void write(PacketBuffer buf, ITextComponent value)
/*     */       {
/* 101 */         buf.writeTextComponent(value);
/*     */       }
/*     */       
/*     */       public ITextComponent read(PacketBuffer buf) throws IOException {
/* 105 */         return buf.readTextComponent();
/*     */       }
/*     */       
/*     */       public DataParameter<ITextComponent> createKey(int id) {
/* 109 */         return new DataParameter<>(id, this);
/*     */       }
/*     */       
/*     */       public ITextComponent func_192717_a(ITextComponent p_192717_1_) {
/* 113 */         return p_192717_1_.createCopy();
/*     */       }
/*     */     };
/* 116 */   public static final DataSerializer<ItemStack> OPTIONAL_ITEM_STACK = new DataSerializer<ItemStack>()
/*     */     {
/*     */       public void write(PacketBuffer buf, ItemStack value)
/*     */       {
/* 120 */         buf.writeItemStackToBuffer(value);
/*     */       }
/*     */       
/*     */       public ItemStack read(PacketBuffer buf) throws IOException {
/* 124 */         return buf.readItemStackFromBuffer();
/*     */       }
/*     */       
/*     */       public DataParameter<ItemStack> createKey(int id) {
/* 128 */         return new DataParameter<>(id, this);
/*     */       }
/*     */       
/*     */       public ItemStack func_192717_a(ItemStack p_192717_1_) {
/* 132 */         return p_192717_1_.copy();
/*     */       }
/*     */     };
/* 135 */   public static final DataSerializer<Optional<IBlockState>> OPTIONAL_BLOCK_STATE = new DataSerializer<Optional<IBlockState>>()
/*     */     {
/*     */       public void write(PacketBuffer buf, Optional<IBlockState> value)
/*     */       {
/* 139 */         if (value.isPresent()) {
/*     */           
/* 141 */           buf.writeVarIntToBuffer(Block.getStateId((IBlockState)value.get()));
/*     */         }
/*     */         else {
/*     */           
/* 145 */           buf.writeVarIntToBuffer(0);
/*     */         } 
/*     */       }
/*     */       
/*     */       public Optional<IBlockState> read(PacketBuffer buf) throws IOException {
/* 150 */         int i = buf.readVarIntFromBuffer();
/* 151 */         return (i == 0) ? Optional.absent() : Optional.of(Block.getStateById(i));
/*     */       }
/*     */       
/*     */       public DataParameter<Optional<IBlockState>> createKey(int id) {
/* 155 */         return new DataParameter<>(id, this);
/*     */       }
/*     */       
/*     */       public Optional<IBlockState> func_192717_a(Optional<IBlockState> p_192717_1_) {
/* 159 */         return p_192717_1_;
/*     */       }
/*     */     };
/* 162 */   public static final DataSerializer<Boolean> BOOLEAN = new DataSerializer<Boolean>()
/*     */     {
/*     */       public void write(PacketBuffer buf, Boolean value)
/*     */       {
/* 166 */         buf.writeBoolean(value.booleanValue());
/*     */       }
/*     */       
/*     */       public Boolean read(PacketBuffer buf) throws IOException {
/* 170 */         return Boolean.valueOf(buf.readBoolean());
/*     */       }
/*     */       
/*     */       public DataParameter<Boolean> createKey(int id) {
/* 174 */         return new DataParameter<>(id, this);
/*     */       }
/*     */       
/*     */       public Boolean func_192717_a(Boolean p_192717_1_) {
/* 178 */         return p_192717_1_;
/*     */       }
/*     */     };
/* 181 */   public static final DataSerializer<Rotations> ROTATIONS = new DataSerializer<Rotations>()
/*     */     {
/*     */       public void write(PacketBuffer buf, Rotations value)
/*     */       {
/* 185 */         buf.writeFloat(value.getX());
/* 186 */         buf.writeFloat(value.getY());
/* 187 */         buf.writeFloat(value.getZ());
/*     */       }
/*     */       
/*     */       public Rotations read(PacketBuffer buf) throws IOException {
/* 191 */         return new Rotations(buf.readFloat(), buf.readFloat(), buf.readFloat());
/*     */       }
/*     */       
/*     */       public DataParameter<Rotations> createKey(int id) {
/* 195 */         return new DataParameter<>(id, this);
/*     */       }
/*     */       
/*     */       public Rotations func_192717_a(Rotations p_192717_1_) {
/* 199 */         return p_192717_1_;
/*     */       }
/*     */     };
/* 202 */   public static final DataSerializer<BlockPos> BLOCK_POS = new DataSerializer<BlockPos>()
/*     */     {
/*     */       public void write(PacketBuffer buf, BlockPos value)
/*     */       {
/* 206 */         buf.writeBlockPos(value);
/*     */       }
/*     */       
/*     */       public BlockPos read(PacketBuffer buf) throws IOException {
/* 210 */         return buf.readBlockPos();
/*     */       }
/*     */       
/*     */       public DataParameter<BlockPos> createKey(int id) {
/* 214 */         return new DataParameter<>(id, this);
/*     */       }
/*     */       
/*     */       public BlockPos func_192717_a(BlockPos p_192717_1_) {
/* 218 */         return p_192717_1_;
/*     */       }
/*     */     };
/* 221 */   public static final DataSerializer<Optional<BlockPos>> OPTIONAL_BLOCK_POS = new DataSerializer<Optional<BlockPos>>()
/*     */     {
/*     */       public void write(PacketBuffer buf, Optional<BlockPos> value)
/*     */       {
/* 225 */         buf.writeBoolean(value.isPresent());
/*     */         
/* 227 */         if (value.isPresent())
/*     */         {
/* 229 */           buf.writeBlockPos((BlockPos)value.get());
/*     */         }
/*     */       }
/*     */       
/*     */       public Optional<BlockPos> read(PacketBuffer buf) throws IOException {
/* 234 */         return !buf.readBoolean() ? Optional.absent() : Optional.of(buf.readBlockPos());
/*     */       }
/*     */       
/*     */       public DataParameter<Optional<BlockPos>> createKey(int id) {
/* 238 */         return new DataParameter<>(id, this);
/*     */       }
/*     */       
/*     */       public Optional<BlockPos> func_192717_a(Optional<BlockPos> p_192717_1_) {
/* 242 */         return p_192717_1_;
/*     */       }
/*     */     };
/* 245 */   public static final DataSerializer<EnumFacing> FACING = new DataSerializer<EnumFacing>()
/*     */     {
/*     */       public void write(PacketBuffer buf, EnumFacing value)
/*     */       {
/* 249 */         buf.writeEnumValue((Enum)value);
/*     */       }
/*     */       
/*     */       public EnumFacing read(PacketBuffer buf) throws IOException {
/* 253 */         return (EnumFacing)buf.readEnumValue(EnumFacing.class);
/*     */       }
/*     */       
/*     */       public DataParameter<EnumFacing> createKey(int id) {
/* 257 */         return new DataParameter<>(id, this);
/*     */       }
/*     */       
/*     */       public EnumFacing func_192717_a(EnumFacing p_192717_1_) {
/* 261 */         return p_192717_1_;
/*     */       }
/*     */     };
/* 264 */   public static final DataSerializer<Optional<UUID>> OPTIONAL_UNIQUE_ID = new DataSerializer<Optional<UUID>>()
/*     */     {
/*     */       public void write(PacketBuffer buf, Optional<UUID> value)
/*     */       {
/* 268 */         buf.writeBoolean(value.isPresent());
/*     */         
/* 270 */         if (value.isPresent())
/*     */         {
/* 272 */           buf.writeUuid((UUID)value.get());
/*     */         }
/*     */       }
/*     */       
/*     */       public Optional<UUID> read(PacketBuffer buf) throws IOException {
/* 277 */         return !buf.readBoolean() ? Optional.absent() : Optional.of(buf.readUuid());
/*     */       }
/*     */       
/*     */       public DataParameter<Optional<UUID>> createKey(int id) {
/* 281 */         return new DataParameter<>(id, this);
/*     */       }
/*     */       
/*     */       public Optional<UUID> func_192717_a(Optional<UUID> p_192717_1_) {
/* 285 */         return p_192717_1_;
/*     */       }
/*     */     };
/* 288 */   public static final DataSerializer<NBTTagCompound> field_192734_n = new DataSerializer<NBTTagCompound>()
/*     */     {
/*     */       public void write(PacketBuffer buf, NBTTagCompound value)
/*     */       {
/* 292 */         buf.writeNBTTagCompoundToBuffer(value);
/*     */       }
/*     */       
/*     */       public NBTTagCompound read(PacketBuffer buf) throws IOException {
/* 296 */         return buf.readNBTTagCompoundFromBuffer();
/*     */       }
/*     */       
/*     */       public DataParameter<NBTTagCompound> createKey(int id) {
/* 300 */         return new DataParameter<>(id, this);
/*     */       }
/*     */       
/*     */       public NBTTagCompound func_192717_a(NBTTagCompound p_192717_1_) {
/* 304 */         return p_192717_1_.copy();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public static void registerSerializer(DataSerializer<?> serializer) {
/* 310 */     REGISTRY.add(serializer);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static DataSerializer<?> getSerializer(int id) {
/* 316 */     return (DataSerializer)REGISTRY.get(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getSerializerId(DataSerializer<?> serializer) {
/* 321 */     return REGISTRY.getId(serializer);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 326 */     registerSerializer(BYTE);
/* 327 */     registerSerializer(VARINT);
/* 328 */     registerSerializer(FLOAT);
/* 329 */     registerSerializer(STRING);
/* 330 */     registerSerializer(TEXT_COMPONENT);
/* 331 */     registerSerializer(OPTIONAL_ITEM_STACK);
/* 332 */     registerSerializer(BOOLEAN);
/* 333 */     registerSerializer(ROTATIONS);
/* 334 */     registerSerializer(BLOCK_POS);
/* 335 */     registerSerializer(OPTIONAL_BLOCK_POS);
/* 336 */     registerSerializer(FACING);
/* 337 */     registerSerializer(OPTIONAL_UNIQUE_ID);
/* 338 */     registerSerializer(OPTIONAL_BLOCK_STATE);
/* 339 */     registerSerializer(field_192734_n);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\datasync\DataSerializers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */