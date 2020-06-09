/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockHugeMushroom
/*     */   extends Block {
/*  23 */   public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);
/*     */   
/*     */   private final Block smallBlock;
/*     */   
/*     */   public BlockHugeMushroom(Material materialIn, MapColor color, Block smallBlockIn) {
/*  28 */     super(materialIn, color);
/*  29 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, EnumType.ALL_OUTSIDE));
/*  30 */     this.smallBlock = smallBlockIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/*  38 */     return Math.max(0, random.nextInt(10) - 7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
/*  46 */     switch ((EnumType)state.getValue((IProperty)VARIANT)) {
/*     */       
/*     */       case ALL_STEM:
/*  49 */         return MapColor.CLOTH;
/*     */       
/*     */       case null:
/*  52 */         return MapColor.SAND;
/*     */       
/*     */       case STEM:
/*  55 */         return MapColor.SAND;
/*     */     } 
/*     */     
/*  58 */     return super.getMapColor(state, p_180659_2_, p_180659_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  67 */     return Item.getItemFromBlock(this.smallBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/*  72 */     return new ItemStack(this.smallBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  81 */     return getDefaultState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  89 */     return getDefaultState().withProperty((IProperty)VARIANT, EnumType.byMetadata(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*  97 */     return ((EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 106 */     switch (rot) {
/*     */       
/*     */       case null:
/* 109 */         switch ((EnumType)state.getValue((IProperty)VARIANT)) {
/*     */           case STEM:
/*     */             break;
/*     */ 
/*     */           
/*     */           case NORTH_WEST:
/* 115 */             return state.withProperty((IProperty)VARIANT, EnumType.SOUTH_EAST);
/*     */           
/*     */           case NORTH:
/* 118 */             return state.withProperty((IProperty)VARIANT, EnumType.SOUTH);
/*     */           
/*     */           case NORTH_EAST:
/* 121 */             return state.withProperty((IProperty)VARIANT, EnumType.SOUTH_WEST);
/*     */           
/*     */           case WEST:
/* 124 */             return state.withProperty((IProperty)VARIANT, EnumType.EAST);
/*     */           
/*     */           case EAST:
/* 127 */             return state.withProperty((IProperty)VARIANT, EnumType.WEST);
/*     */           
/*     */           case SOUTH_WEST:
/* 130 */             return state.withProperty((IProperty)VARIANT, EnumType.NORTH_EAST);
/*     */           
/*     */           case SOUTH:
/* 133 */             return state.withProperty((IProperty)VARIANT, EnumType.NORTH);
/*     */           
/*     */           case SOUTH_EAST:
/* 136 */             return state.withProperty((IProperty)VARIANT, EnumType.NORTH_WEST);
/*     */           
/*     */           default:
/* 139 */             return state;
/*     */         } 
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 143 */         switch ((EnumType)state.getValue((IProperty)VARIANT)) {
/*     */           case STEM:
/*     */             break;
/*     */ 
/*     */           
/*     */           case NORTH_WEST:
/* 149 */             return state.withProperty((IProperty)VARIANT, EnumType.SOUTH_WEST);
/*     */           
/*     */           case NORTH:
/* 152 */             return state.withProperty((IProperty)VARIANT, EnumType.WEST);
/*     */           
/*     */           case NORTH_EAST:
/* 155 */             return state.withProperty((IProperty)VARIANT, EnumType.NORTH_WEST);
/*     */           
/*     */           case WEST:
/* 158 */             return state.withProperty((IProperty)VARIANT, EnumType.SOUTH);
/*     */           
/*     */           case EAST:
/* 161 */             return state.withProperty((IProperty)VARIANT, EnumType.NORTH);
/*     */           
/*     */           case SOUTH_WEST:
/* 164 */             return state.withProperty((IProperty)VARIANT, EnumType.SOUTH_EAST);
/*     */           
/*     */           case SOUTH:
/* 167 */             return state.withProperty((IProperty)VARIANT, EnumType.EAST);
/*     */           
/*     */           case SOUTH_EAST:
/* 170 */             return state.withProperty((IProperty)VARIANT, EnumType.NORTH_EAST);
/*     */           
/*     */           default:
/* 173 */             return state;
/*     */         } 
/*     */       
/*     */       case CLOCKWISE_90:
/* 177 */         switch ((EnumType)state.getValue((IProperty)VARIANT)) {
/*     */           case STEM:
/*     */             break;
/*     */ 
/*     */           
/*     */           case NORTH_WEST:
/* 183 */             return state.withProperty((IProperty)VARIANT, EnumType.NORTH_EAST);
/*     */           
/*     */           case NORTH:
/* 186 */             return state.withProperty((IProperty)VARIANT, EnumType.EAST);
/*     */           
/*     */           case NORTH_EAST:
/* 189 */             return state.withProperty((IProperty)VARIANT, EnumType.SOUTH_EAST);
/*     */           
/*     */           case WEST:
/* 192 */             return state.withProperty((IProperty)VARIANT, EnumType.NORTH);
/*     */           
/*     */           case EAST:
/* 195 */             return state.withProperty((IProperty)VARIANT, EnumType.SOUTH);
/*     */           
/*     */           case SOUTH_WEST:
/* 198 */             return state.withProperty((IProperty)VARIANT, EnumType.NORTH_WEST);
/*     */           
/*     */           case SOUTH:
/* 201 */             return state.withProperty((IProperty)VARIANT, EnumType.WEST);
/*     */           
/*     */           case SOUTH_EAST:
/* 204 */             return state.withProperty((IProperty)VARIANT, EnumType.SOUTH_WEST);
/*     */         } 
/*     */         
/* 207 */         return state;
/*     */     } 
/*     */ 
/*     */     
/* 211 */     return state;
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
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 223 */     EnumType blockhugemushroom$enumtype = (EnumType)state.getValue((IProperty)VARIANT);
/*     */     
/* 225 */     switch (mirrorIn) {
/*     */       
/*     */       case LEFT_RIGHT:
/* 228 */         switch (blockhugemushroom$enumtype) {
/*     */           
/*     */           case NORTH_WEST:
/* 231 */             return state.withProperty((IProperty)VARIANT, EnumType.SOUTH_WEST);
/*     */           
/*     */           case NORTH:
/* 234 */             return state.withProperty((IProperty)VARIANT, EnumType.SOUTH);
/*     */           
/*     */           case NORTH_EAST:
/* 237 */             return state.withProperty((IProperty)VARIANT, EnumType.SOUTH_EAST);
/*     */ 
/*     */ 
/*     */           
/*     */           default:
/* 242 */             return super.withMirror(state, mirrorIn);
/*     */           
/*     */           case SOUTH_WEST:
/* 245 */             return state.withProperty((IProperty)VARIANT, EnumType.NORTH_WEST);
/*     */           
/*     */           case SOUTH:
/* 248 */             return state.withProperty((IProperty)VARIANT, EnumType.NORTH);
/*     */           case SOUTH_EAST:
/*     */             break;
/* 251 */         }  return state.withProperty((IProperty)VARIANT, EnumType.NORTH_EAST);
/*     */ 
/*     */       
/*     */       case null:
/* 255 */         switch (blockhugemushroom$enumtype) {
/*     */           
/*     */           case NORTH_WEST:
/* 258 */             return state.withProperty((IProperty)VARIANT, EnumType.NORTH_EAST);
/*     */ 
/*     */           
/*     */           default:
/*     */             break;
/*     */ 
/*     */           
/*     */           case NORTH_EAST:
/* 266 */             return state.withProperty((IProperty)VARIANT, EnumType.NORTH_WEST);
/*     */           
/*     */           case WEST:
/* 269 */             return state.withProperty((IProperty)VARIANT, EnumType.EAST);
/*     */           
/*     */           case EAST:
/* 272 */             return state.withProperty((IProperty)VARIANT, EnumType.WEST);
/*     */           
/*     */           case SOUTH_WEST:
/* 275 */             return state.withProperty((IProperty)VARIANT, EnumType.SOUTH_EAST);
/*     */           case SOUTH_EAST:
/*     */             break;
/* 278 */         }  return state.withProperty((IProperty)VARIANT, EnumType.SOUTH_WEST);
/*     */     } 
/*     */ 
/*     */     
/* 282 */     return super.withMirror(state, mirrorIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 287 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)VARIANT });
/*     */   }
/*     */   
/*     */   public enum EnumType
/*     */     implements IStringSerializable {
/* 292 */     NORTH_WEST(1, "north_west"),
/* 293 */     NORTH(2, "north"),
/* 294 */     NORTH_EAST(3, "north_east"),
/* 295 */     WEST(4, "west"),
/* 296 */     CENTER(5, "center"),
/* 297 */     EAST(6, "east"),
/* 298 */     SOUTH_WEST(7, "south_west"),
/* 299 */     SOUTH(8, "south"),
/* 300 */     SOUTH_EAST(9, "south_east"),
/* 301 */     STEM(10, "stem"),
/* 302 */     ALL_INSIDE(0, "all_inside"),
/* 303 */     ALL_OUTSIDE(14, "all_outside"),
/* 304 */     ALL_STEM(15, "all_stem");
/*     */     
/* 306 */     private static final EnumType[] META_LOOKUP = new EnumType[16];
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
/*     */     private final int meta;
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
/*     */     private final String name;
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
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumType[] arrayOfEnumType;
/* 343 */       for (i = (arrayOfEnumType = values()).length, b = 0; b < i; ) { EnumType blockhugemushroom$enumtype = arrayOfEnumType[b];
/*     */         
/* 345 */         META_LOOKUP[blockhugemushroom$enumtype.getMetadata()] = blockhugemushroom$enumtype;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     EnumType(int meta, String name) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static EnumType byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length)
/*     */         meta = 0; 
/*     */       EnumType blockhugemushroom$enumtype = META_LOOKUP[meta];
/*     */       return (blockhugemushroom$enumtype == null) ? META_LOOKUP[0] : blockhugemushroom$enumtype;
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockHugeMushroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */