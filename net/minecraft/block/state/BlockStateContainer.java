/*     */ package net.minecraft.block.state;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.collect.HashBasedTable;
/*     */ import com.google.common.collect.ImmutableCollection;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableSortedMap;
/*     */ import com.google.common.collect.ImmutableTable;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Table;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MapPopulator;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Cartesian;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.property.IUnlistedProperty;
/*     */ import optifine.BlockModelUtils;
/*     */ import optifine.Reflector;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockStateContainer
/*     */ {
/*  50 */   private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9_]+$");
/*  51 */   private static final Function<IProperty<?>, String> GET_NAME_FUNC = new Function<IProperty<?>, String>()
/*     */     {
/*     */       @Nullable
/*     */       public String apply(@Nullable IProperty<?> p_apply_1_)
/*     */       {
/*  56 */         return (p_apply_1_ == null) ? "<NULL>" : p_apply_1_.getName();
/*     */       }
/*     */     };
/*     */   
/*     */   private final Block block;
/*     */   private final ImmutableSortedMap<String, IProperty<?>> properties;
/*     */   private final ImmutableList<IBlockState> validStates;
/*     */   
/*     */   public BlockStateContainer(Block blockIn, IProperty... properties) {
/*  65 */     this(blockIn, (IProperty<?>[])properties, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected StateImplementation createState(Block p_createState_1_, ImmutableMap<IProperty<?>, Comparable<?>> p_createState_2_, @Nullable ImmutableMap<IUnlistedProperty<?>, Optional<?>> p_createState_3_) {
/*  70 */     return new StateImplementation(p_createState_1_, p_createState_2_, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer(Block p_i9_1_, IProperty[] p_i9_2_, ImmutableMap<IUnlistedProperty<?>, Optional<?>> p_i9_3_) {
/*  75 */     this.block = p_i9_1_;
/*  76 */     Map<String, IProperty<?>> map = Maps.newHashMap(); byte b; int i;
/*     */     IProperty[] arrayOfIProperty;
/*  78 */     for (i = (arrayOfIProperty = p_i9_2_).length, b = 0; b < i; ) { IProperty<?> iproperty = arrayOfIProperty[b];
/*     */       
/*  80 */       validateProperty(p_i9_1_, iproperty);
/*  81 */       map.put(iproperty.getName(), iproperty);
/*     */       b++; }
/*     */     
/*  84 */     this.properties = ImmutableSortedMap.copyOf(map);
/*  85 */     Map<Map<IProperty<?>, Comparable<?>>, StateImplementation> map2 = Maps.newLinkedHashMap();
/*  86 */     List<StateImplementation> list = Lists.newArrayList();
/*     */     
/*  88 */     for (List<Comparable<?>> list1 : (Iterable<List<Comparable<?>>>)Cartesian.cartesianProduct(getAllowedValues())) {
/*     */       
/*  90 */       Map<IProperty<?>, Comparable<?>> map1 = MapPopulator.createMap((Iterable)this.properties.values(), list1);
/*  91 */       StateImplementation blockstatecontainer$stateimplementation = createState(p_i9_1_, ImmutableMap.copyOf(map1), p_i9_3_);
/*  92 */       map2.put(map1, blockstatecontainer$stateimplementation);
/*  93 */       list.add(blockstatecontainer$stateimplementation);
/*     */     } 
/*     */     
/*  96 */     for (StateImplementation blockstatecontainer$stateimplementation1 : list)
/*     */     {
/*  98 */       blockstatecontainer$stateimplementation1.buildPropertyValueTable(map2);
/*     */     }
/*     */     
/* 101 */     this.validStates = ImmutableList.copyOf(list);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T extends Comparable<T>> String validateProperty(Block block, IProperty<T> property) {
/* 106 */     String s = property.getName();
/*     */     
/* 108 */     if (!NAME_PATTERN.matcher(s).matches())
/*     */     {
/* 110 */       throw new IllegalArgumentException("Block: " + block.getClass() + " has invalidly named property: " + s);
/*     */     }
/*     */ 
/*     */     
/* 114 */     for (Comparable comparable : property.getAllowedValues()) {
/*     */       
/* 116 */       String s1 = property.getName(comparable);
/*     */       
/* 118 */       if (!NAME_PATTERN.matcher(s1).matches())
/*     */       {
/* 120 */         throw new IllegalArgumentException("Block: " + block.getClass() + " has property: " + s + " with invalidly named value: " + s1);
/*     */       }
/*     */     } 
/*     */     
/* 124 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableList<IBlockState> getValidStates() {
/* 130 */     return this.validStates;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Iterable<Comparable<?>>> getAllowedValues() {
/* 135 */     List<Iterable<Comparable<?>>> list = Lists.newArrayList();
/* 136 */     ImmutableCollection<IProperty<?>> immutablecollection = this.properties.values();
/* 137 */     UnmodifiableIterator unmodifiableiterator = immutablecollection.iterator();
/*     */     
/* 139 */     while (unmodifiableiterator.hasNext()) {
/*     */       
/* 141 */       IProperty<?> iproperty = (IProperty)unmodifiableiterator.next();
/* 142 */       list.add(iproperty.getAllowedValues());
/*     */     } 
/*     */     
/* 145 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getBaseState() {
/* 150 */     return (IBlockState)this.validStates.get(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Block getBlock() {
/* 155 */     return this.block;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<IProperty<?>> getProperties() {
/* 160 */     return (Collection<IProperty<?>>)this.properties.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 165 */     return MoreObjects.toStringHelper(this).add("block", Block.REGISTRY.getNameForObject(this.block)).add("properties", Iterables.transform((Iterable)this.properties.values(), GET_NAME_FUNC)).toString();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IProperty<?> getProperty(String propertyName) {
/* 171 */     return (IProperty)this.properties.get(propertyName);
/*     */   }
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private final Block block;
/* 177 */     private final List<IProperty<?>> listed = Lists.newArrayList();
/* 178 */     private final List<IUnlistedProperty<?>> unlisted = Lists.newArrayList();
/*     */ 
/*     */     
/*     */     public Builder(Block p_i11_1_) {
/* 182 */       this.block = p_i11_1_;
/*     */     } public Builder add(IProperty... p_add_1_) {
/*     */       byte b;
/*     */       int i;
/*     */       IProperty[] arrayOfIProperty;
/* 187 */       for (i = (arrayOfIProperty = p_add_1_).length, b = 0; b < i; ) { IProperty<?> iproperty = arrayOfIProperty[b];
/*     */         
/* 189 */         this.listed.add(iproperty);
/*     */         b++; }
/*     */       
/* 192 */       return this;
/*     */     } public Builder add(IUnlistedProperty... p_add_1_) {
/*     */       byte b;
/*     */       int i;
/*     */       IUnlistedProperty[] arrayOfIUnlistedProperty;
/* 197 */       for (i = (arrayOfIUnlistedProperty = p_add_1_).length, b = 0; b < i; ) { IUnlistedProperty<?> iunlistedproperty = arrayOfIUnlistedProperty[b];
/*     */         
/* 199 */         this.unlisted.add(iunlistedproperty);
/*     */         b++; }
/*     */       
/* 202 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockStateContainer build() {
/* 207 */       IProperty[] iproperty = new IProperty[this.listed.size()];
/* 208 */       iproperty = this.listed.<IProperty>toArray(iproperty);
/*     */       
/* 210 */       if (this.unlisted.size() == 0)
/*     */       {
/* 212 */         return new BlockStateContainer(this.block, (IProperty<?>[])iproperty);
/*     */       }
/*     */ 
/*     */       
/* 216 */       IUnlistedProperty[] iunlistedproperty = new IUnlistedProperty[this.unlisted.size()];
/* 217 */       iunlistedproperty = this.unlisted.<IUnlistedProperty>toArray(iunlistedproperty);
/* 218 */       return (BlockStateContainer)Reflector.newInstance(Reflector.ExtendedBlockState_Constructor, new Object[] { this.block, iproperty, iunlistedproperty });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class StateImplementation
/*     */     extends BlockStateBase
/*     */   {
/*     */     private final Block block;
/*     */     private final ImmutableMap<IProperty<?>, Comparable<?>> properties;
/*     */     private ImmutableTable<IProperty<?>, Comparable<?>, IBlockState> propertyValueTable;
/*     */     
/*     */     private StateImplementation(Block blockIn, ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn) {
/* 231 */       this.block = blockIn;
/* 232 */       this.properties = propertiesIn;
/*     */     }
/*     */ 
/*     */     
/*     */     protected StateImplementation(Block p_i8_1_, ImmutableMap<IProperty<?>, Comparable<?>> p_i8_2_, ImmutableTable<IProperty<?>, Comparable<?>, IBlockState> p_i8_3_) {
/* 237 */       this.block = p_i8_1_;
/* 238 */       this.properties = p_i8_2_;
/* 239 */       this.propertyValueTable = p_i8_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<IProperty<?>> getPropertyNames() {
/* 244 */       return Collections.unmodifiableCollection((Collection<? extends IProperty<?>>)this.properties.keySet());
/*     */     }
/*     */ 
/*     */     
/*     */     public <T extends Comparable<T>> T getValue(IProperty<T> property) {
/* 249 */       Comparable<?> comparable = (Comparable)this.properties.get(property);
/*     */       
/* 251 */       if (comparable == null)
/*     */       {
/* 253 */         throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.block.getBlockState());
/*     */       }
/*     */ 
/*     */       
/* 257 */       return (T)property.getValueClass().cast(comparable);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value) {
/* 263 */       Comparable<?> comparable = (Comparable)this.properties.get(property);
/*     */       
/* 265 */       if (comparable == null)
/*     */       {
/* 267 */         throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.block.getBlockState());
/*     */       }
/* 269 */       if (comparable == value)
/*     */       {
/* 271 */         return this;
/*     */       }
/*     */ 
/*     */       
/* 275 */       IBlockState iblockstate = (IBlockState)this.propertyValueTable.get(property, value);
/*     */       
/* 277 */       if (iblockstate == null)
/*     */       {
/* 279 */         throw new IllegalArgumentException("Cannot set property " + property + " to " + value + " on block " + Block.REGISTRY.getNameForObject(this.block) + ", it is not an allowed value");
/*     */       }
/*     */ 
/*     */       
/* 283 */       return iblockstate;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableMap<IProperty<?>, Comparable<?>> getProperties() {
/* 290 */       return this.properties;
/*     */     }
/*     */ 
/*     */     
/*     */     public Block getBlock() {
/* 295 */       return this.block;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 300 */       return (this == p_equals_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 305 */       return this.properties.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public void buildPropertyValueTable(Map<Map<IProperty<?>, Comparable<?>>, StateImplementation> map) {
/* 310 */       if (this.propertyValueTable != null)
/*     */       {
/* 312 */         throw new IllegalStateException();
/*     */       }
/*     */ 
/*     */       
/* 316 */       HashBasedTable hashBasedTable = HashBasedTable.create();
/* 317 */       UnmodifiableIterator unmodifiableiterator = this.properties.entrySet().iterator();
/*     */       
/* 319 */       while (unmodifiableiterator.hasNext()) {
/*     */         
/* 321 */         Map.Entry<IProperty<?>, Comparable<?>> entry = (Map.Entry<IProperty<?>, Comparable<?>>)unmodifiableiterator.next();
/* 322 */         IProperty<?> iproperty = entry.getKey();
/*     */         
/* 324 */         for (Comparable<?> comparable : (Iterable<Comparable<?>>)iproperty.getAllowedValues()) {
/*     */           
/* 326 */           if (comparable != entry.getValue())
/*     */           {
/* 328 */             hashBasedTable.put(iproperty, comparable, map.get(getPropertiesWithValue(iproperty, comparable)));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 333 */       this.propertyValueTable = ImmutableTable.copyOf((Table)hashBasedTable);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Map<IProperty<?>, Comparable<?>> getPropertiesWithValue(IProperty<?> property, Comparable<?> value) {
/* 339 */       Map<IProperty<?>, Comparable<?>> map = Maps.newHashMap((Map)this.properties);
/* 340 */       map.put(property, value);
/* 341 */       return map;
/*     */     }
/*     */ 
/*     */     
/*     */     public Material getMaterial() {
/* 346 */       return this.block.getMaterial(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isFullBlock() {
/* 351 */       return this.block.isFullBlock(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canEntitySpawn(Entity entityIn) {
/* 356 */       return this.block.canEntitySpawn(this, entityIn);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getLightOpacity() {
/* 361 */       return this.block.getLightOpacity(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getLightValue() {
/* 366 */       return this.block.getLightValue(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isTranslucent() {
/* 371 */       return this.block.isTranslucent(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean useNeighborBrightness() {
/* 376 */       return this.block.getUseNeighborBrightness(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public MapColor getMapColor(IBlockAccess p_185909_1_, BlockPos p_185909_2_) {
/* 381 */       return this.block.getMapColor(this, p_185909_1_, p_185909_2_);
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState withRotation(Rotation rot) {
/* 386 */       return this.block.withRotation(this, rot);
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState withMirror(Mirror mirrorIn) {
/* 391 */       return this.block.withMirror(this, mirrorIn);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isFullCube() {
/* 396 */       return this.block.isFullCube(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_191057_i() {
/* 401 */       return this.block.func_190946_v(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public EnumBlockRenderType getRenderType() {
/* 406 */       return this.block.getRenderType(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPackedLightmapCoords(IBlockAccess source, BlockPos pos) {
/* 411 */       return this.block.getPackedLightmapCoords(this, source, pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public float getAmbientOcclusionLightValue() {
/* 416 */       return this.block.getAmbientOcclusionLightValue(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isBlockNormalCube() {
/* 421 */       return this.block.isBlockNormalCube(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isNormalCube() {
/* 426 */       return this.block.isNormalCube(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canProvidePower() {
/* 431 */       return this.block.canProvidePower(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getWeakPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 436 */       return this.block.getWeakPower(this, blockAccess, pos, side);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasComparatorInputOverride() {
/* 441 */       return this.block.hasComparatorInputOverride(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 446 */       return this.block.getComparatorInputOverride(this, worldIn, pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public float getBlockHardness(World worldIn, BlockPos pos) {
/* 451 */       return this.block.getBlockHardness(this, worldIn, pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public float getPlayerRelativeBlockHardness(EntityPlayer player, World worldIn, BlockPos pos) {
/* 456 */       return this.block.getPlayerRelativeBlockHardness(this, player, worldIn, pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getStrongPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 461 */       return this.block.getStrongPower(this, blockAccess, pos, side);
/*     */     }
/*     */ 
/*     */     
/*     */     public EnumPushReaction getMobilityFlag() {
/* 466 */       return this.block.getMobilityFlag(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState getActualState(IBlockAccess blockAccess, BlockPos pos) {
/* 471 */       return this.block.getActualState(this, blockAccess, pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/* 476 */       return this.block.getSelectedBoundingBox(this, worldIn, pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldSideBeRendered(IBlockAccess blockAccess, BlockPos pos, EnumFacing facing) {
/* 481 */       return this.block.shouldSideBeRendered(this, blockAccess, pos, facing);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isOpaqueCube() {
/* 486 */       return this.block.isOpaqueCube(this);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public AxisAlignedBB getCollisionBoundingBox(IBlockAccess worldIn, BlockPos pos) {
/* 492 */       return this.block.getCollisionBoundingBox(this, worldIn, pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public void addCollisionBoxToList(World worldIn, BlockPos pos, AxisAlignedBB p_185908_3_, List<AxisAlignedBB> p_185908_4_, @Nullable Entity p_185908_5_, boolean p_185908_6_) {
/* 497 */       this.block.addCollisionBoxToList(this, worldIn, pos, p_185908_3_, p_185908_4_, p_185908_5_, p_185908_6_);
/*     */     }
/*     */ 
/*     */     
/*     */     public AxisAlignedBB getBoundingBox(IBlockAccess blockAccess, BlockPos pos) {
/* 502 */       Block.EnumOffsetType block$enumoffsettype = this.block.getOffsetType();
/*     */       
/* 504 */       if (block$enumoffsettype != Block.EnumOffsetType.NONE && !(this.block instanceof net.minecraft.block.BlockFlower)) {
/*     */         
/* 506 */         AxisAlignedBB axisalignedbb = this.block.getBoundingBox(this, blockAccess, pos);
/* 507 */         axisalignedbb = BlockModelUtils.getOffsetBoundingBox(axisalignedbb, block$enumoffsettype, pos);
/* 508 */         return axisalignedbb;
/*     */       } 
/*     */ 
/*     */       
/* 512 */       return this.block.getBoundingBox(this, blockAccess, pos);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public RayTraceResult collisionRayTrace(World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
/* 518 */       return this.block.collisionRayTrace(this, worldIn, pos, start, end);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isFullyOpaque() {
/* 523 */       return this.block.isFullyOpaque(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public Vec3d func_191059_e(IBlockAccess p_191059_1_, BlockPos p_191059_2_) {
/* 528 */       return this.block.func_190949_e(this, p_191059_1_, p_191059_2_);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean onBlockEventReceived(World worldIn, BlockPos pos, int id, int param) {
/* 533 */       return this.block.eventReceived(this, worldIn, pos, id, param);
/*     */     }
/*     */ 
/*     */     
/*     */     public void neighborChanged(World worldIn, BlockPos pos, Block blockIn, BlockPos p_189546_4_) {
/* 538 */       this.block.neighborChanged(this, worldIn, pos, blockIn, p_189546_4_);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_191058_s() {
/* 543 */       return this.block.causesSuffocation(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public ImmutableTable<IProperty<?>, Comparable<?>, IBlockState> getPropertyValueTable() {
/* 548 */       return this.propertyValueTable;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getLightOpacity(IBlockAccess p_getLightOpacity_1_, BlockPos p_getLightOpacity_2_) {
/* 553 */       return Reflector.callInt(this.block, Reflector.ForgeBlock_getLightOpacity, new Object[] { this, p_getLightOpacity_1_, p_getLightOpacity_2_ });
/*     */     }
/*     */ 
/*     */     
/*     */     public int getLightValue(IBlockAccess p_getLightValue_1_, BlockPos p_getLightValue_2_) {
/* 558 */       return Reflector.callInt(this.block, Reflector.ForgeBlock_getLightValue, new Object[] { this, p_getLightValue_1_, p_getLightValue_2_ });
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSideSolid(IBlockAccess p_isSideSolid_1_, BlockPos p_isSideSolid_2_, EnumFacing p_isSideSolid_3_) {
/* 563 */       return Reflector.callBoolean(this.block, Reflector.ForgeBlock_isSideSolid, new Object[] { this, p_isSideSolid_1_, p_isSideSolid_2_, p_isSideSolid_3_ });
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean doesSideBlockRendering(IBlockAccess p_doesSideBlockRendering_1_, BlockPos p_doesSideBlockRendering_2_, EnumFacing p_doesSideBlockRendering_3_) {
/* 568 */       return Reflector.callBoolean(this.block, Reflector.ForgeBlock_doesSideBlockRendering, new Object[] { this, p_doesSideBlockRendering_1_, p_doesSideBlockRendering_2_, p_doesSideBlockRendering_3_ });
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockFaceShape func_193401_d(IBlockAccess p_193401_1_, BlockPos p_193401_2_, EnumFacing p_193401_3_) {
/* 573 */       return this.block.func_193383_a(p_193401_1_, this, p_193401_2_, p_193401_3_);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\state\BlockStateContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */