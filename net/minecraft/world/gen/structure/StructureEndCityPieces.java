/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.monster.EntityShulker;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.template.PlacementSettings;
/*     */ import net.minecraft.world.gen.structure.template.Template;
/*     */ import net.minecraft.world.gen.structure.template.TemplateManager;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class StructureEndCityPieces {
/*  28 */   private static final PlacementSettings OVERWRITE = (new PlacementSettings()).setIgnoreEntities(true);
/*  29 */   private static final PlacementSettings INSERT = (new PlacementSettings()).setIgnoreEntities(true).setReplacedBlock(Blocks.AIR);
/*  30 */   private static final IGenerator HOUSE_TOWER_GENERATOR = new IGenerator()
/*     */     {
/*     */       public void init() {}
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean func_191086_a(TemplateManager p_191086_1_, int p_191086_2_, StructureEndCityPieces.CityTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_, Random p_191086_6_) {
/*  37 */         if (p_191086_2_ > 8)
/*     */         {
/*  39 */           return false;
/*     */         }
/*     */ 
/*     */         
/*  43 */         Rotation rotation = p_191086_3_.placeSettings.getRotation();
/*  44 */         StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, p_191086_3_, p_191086_4_, "base_floor", rotation, true));
/*  45 */         int i = p_191086_6_.nextInt(3);
/*     */         
/*  47 */         if (i == 0) {
/*     */           
/*  49 */           StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 4, -1), "base_roof", rotation, true));
/*     */         }
/*  51 */         else if (i == 1) {
/*     */           
/*  53 */           structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
/*  54 */           structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 8, -1), "second_roof", rotation, false));
/*  55 */           StructureEndCityPieces.func_191088_b(p_191086_1_, StructureEndCityPieces.TOWER_GENERATOR, p_191086_2_ + 1, structureendcitypieces$citytemplate, null, p_191086_5_, p_191086_6_);
/*     */         }
/*  57 */         else if (i == 2) {
/*     */           
/*  59 */           structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
/*  60 */           structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 4, -1), "third_floor_c", rotation, false));
/*  61 */           structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 8, -1), "third_roof", rotation, true));
/*  62 */           StructureEndCityPieces.func_191088_b(p_191086_1_, StructureEndCityPieces.TOWER_GENERATOR, p_191086_2_ + 1, structureendcitypieces$citytemplate, null, p_191086_5_, p_191086_6_);
/*     */         } 
/*     */         
/*  65 */         return true;
/*     */       }
/*     */     };
/*     */   
/*  69 */   private static final List<Tuple<Rotation, BlockPos>> TOWER_BRIDGES = Lists.newArrayList((Object[])new Tuple[] { new Tuple(Rotation.NONE, new BlockPos(1, -1, 0)), new Tuple(Rotation.CLOCKWISE_90, new BlockPos(6, -1, 1)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 5)), new Tuple(Rotation.CLOCKWISE_180, new BlockPos(5, -1, 6)) });
/*  70 */   private static final IGenerator TOWER_GENERATOR = new IGenerator()
/*     */     {
/*     */       public void init() {}
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean func_191086_a(TemplateManager p_191086_1_, int p_191086_2_, StructureEndCityPieces.CityTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_, Random p_191086_6_) {
/*  77 */         Rotation rotation = p_191086_3_.placeSettings.getRotation();
/*  78 */         StructureEndCityPieces.CityTemplate lvt_8_1_ = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, p_191086_3_, new BlockPos(3 + p_191086_6_.nextInt(2), -3, 3 + p_191086_6_.nextInt(2)), "tower_base", rotation, true));
/*  79 */         lvt_8_1_ = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, lvt_8_1_, new BlockPos(0, 7, 0), "tower_piece", rotation, true));
/*  80 */         StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate1 = (p_191086_6_.nextInt(3) == 0) ? lvt_8_1_ : null;
/*  81 */         int i = 1 + p_191086_6_.nextInt(3);
/*     */         
/*  83 */         for (int j = 0; j < i; j++) {
/*     */           
/*  85 */           lvt_8_1_ = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, lvt_8_1_, new BlockPos(0, 4, 0), "tower_piece", rotation, true));
/*     */           
/*  87 */           if (j < i - 1 && p_191086_6_.nextBoolean())
/*     */           {
/*  89 */             structureendcitypieces$citytemplate1 = lvt_8_1_;
/*     */           }
/*     */         } 
/*     */         
/*  93 */         if (structureendcitypieces$citytemplate1 != null) {
/*     */           
/*  95 */           for (Tuple<Rotation, BlockPos> tuple : (Iterable<Tuple<Rotation, BlockPos>>)StructureEndCityPieces.TOWER_BRIDGES) {
/*     */             
/*  97 */             if (p_191086_6_.nextBoolean()) {
/*     */               
/*  99 */               StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate2 = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate1, (BlockPos)tuple.getSecond(), "bridge_end", rotation.add((Rotation)tuple.getFirst()), true));
/* 100 */               StructureEndCityPieces.func_191088_b(p_191086_1_, StructureEndCityPieces.TOWER_BRIDGE_GENERATOR, p_191086_2_ + 1, structureendcitypieces$citytemplate2, null, p_191086_5_, p_191086_6_);
/*     */             } 
/*     */           } 
/*     */           
/* 104 */           StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, lvt_8_1_, new BlockPos(-1, 4, -1), "tower_top", rotation, true));
/*     */         }
/*     */         else {
/*     */           
/* 108 */           if (p_191086_2_ != 7)
/*     */           {
/* 110 */             return StructureEndCityPieces.func_191088_b(p_191086_1_, StructureEndCityPieces.FAT_TOWER_GENERATOR, p_191086_2_ + 1, lvt_8_1_, null, p_191086_5_, p_191086_6_);
/*     */           }
/*     */           
/* 113 */           StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, lvt_8_1_, new BlockPos(-1, 4, -1), "tower_top", rotation, true));
/*     */         } 
/*     */         
/* 116 */         return true;
/*     */       }
/*     */     };
/* 119 */   private static final IGenerator TOWER_BRIDGE_GENERATOR = new IGenerator()
/*     */     {
/*     */       public boolean shipCreated;
/*     */       
/*     */       public void init() {
/* 124 */         this.shipCreated = false;
/*     */       }
/*     */       
/*     */       public boolean func_191086_a(TemplateManager p_191086_1_, int p_191086_2_, StructureEndCityPieces.CityTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_, Random p_191086_6_) {
/* 128 */         Rotation rotation = p_191086_3_.placeSettings.getRotation();
/* 129 */         int i = p_191086_6_.nextInt(4) + 1;
/* 130 */         StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, p_191086_3_, new BlockPos(0, 0, -4), "bridge_piece", rotation, true));
/* 131 */         structureendcitypieces$citytemplate.componentType = -1;
/* 132 */         int j = 0;
/*     */         
/* 134 */         for (int k = 0; k < i; k++) {
/*     */           
/* 136 */           if (p_191086_6_.nextBoolean()) {
/*     */             
/* 138 */             structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, j, -4), "bridge_piece", rotation, true));
/* 139 */             j = 0;
/*     */           }
/*     */           else {
/*     */             
/* 143 */             if (p_191086_6_.nextBoolean()) {
/*     */               
/* 145 */               structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, j, -4), "bridge_steep_stairs", rotation, true));
/*     */             }
/*     */             else {
/*     */               
/* 149 */               structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, j, -8), "bridge_gentle_stairs", rotation, true));
/*     */             } 
/*     */             
/* 152 */             j = 4;
/*     */           } 
/*     */         } 
/*     */         
/* 156 */         if (!this.shipCreated && p_191086_6_.nextInt(10 - p_191086_2_) == 0) {
/*     */           
/* 158 */           StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-8 + p_191086_6_.nextInt(8), j, -70 + p_191086_6_.nextInt(10)), "ship", rotation, true));
/* 159 */           this.shipCreated = true;
/*     */         }
/* 161 */         else if (!StructureEndCityPieces.func_191088_b(p_191086_1_, StructureEndCityPieces.HOUSE_TOWER_GENERATOR, p_191086_2_ + 1, structureendcitypieces$citytemplate, new BlockPos(-3, j + 1, -11), p_191086_5_, p_191086_6_)) {
/*     */           
/* 163 */           return false;
/*     */         } 
/*     */         
/* 166 */         structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(4, j, 0), "bridge_end", rotation.add(Rotation.CLOCKWISE_180), true));
/* 167 */         structureendcitypieces$citytemplate.componentType = -1;
/* 168 */         return true;
/*     */       }
/*     */     };
/* 171 */   private static final List<Tuple<Rotation, BlockPos>> FAT_TOWER_BRIDGES = Lists.newArrayList((Object[])new Tuple[] { new Tuple(Rotation.NONE, new BlockPos(4, -1, 0)), new Tuple(Rotation.CLOCKWISE_90, new BlockPos(12, -1, 4)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 8)), new Tuple(Rotation.CLOCKWISE_180, new BlockPos(8, -1, 12)) });
/* 172 */   private static final IGenerator FAT_TOWER_GENERATOR = new IGenerator()
/*     */     {
/*     */       public void init() {}
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean func_191086_a(TemplateManager p_191086_1_, int p_191086_2_, StructureEndCityPieces.CityTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_, Random p_191086_6_) {
/* 179 */         Rotation rotation = p_191086_3_.placeSettings.getRotation();
/* 180 */         StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, p_191086_3_, new BlockPos(-3, 4, -3), "fat_tower_base", rotation, true));
/* 181 */         structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, 4, 0), "fat_tower_middle", rotation, true));
/*     */         
/* 183 */         for (int i = 0; i < 2 && p_191086_6_.nextInt(3) != 0; i++) {
/*     */           
/* 185 */           structureendcitypieces$citytemplate = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, 8, 0), "fat_tower_middle", rotation, true));
/*     */           
/* 187 */           for (Tuple<Rotation, BlockPos> tuple : (Iterable<Tuple<Rotation, BlockPos>>)StructureEndCityPieces.FAT_TOWER_BRIDGES) {
/*     */             
/* 189 */             if (p_191086_6_.nextBoolean()) {
/*     */               
/* 191 */               StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate1 = StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, (BlockPos)tuple.getSecond(), "bridge_end", rotation.add((Rotation)tuple.getFirst()), true));
/* 192 */               StructureEndCityPieces.func_191088_b(p_191086_1_, StructureEndCityPieces.TOWER_BRIDGE_GENERATOR, p_191086_2_ + 1, structureendcitypieces$citytemplate1, null, p_191086_5_, p_191086_6_);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 197 */         StructureEndCityPieces.func_189935_b(p_191086_5_, StructureEndCityPieces.func_191090_b(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-2, 8, -2), "fat_tower_top", rotation, true));
/* 198 */         return true;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public static void registerPieces() {
/* 204 */     MapGenStructureIO.registerStructureComponent((Class)CityTemplate.class, "ECP");
/*     */   }
/*     */ 
/*     */   
/*     */   private static CityTemplate func_191090_b(TemplateManager p_191090_0_, CityTemplate p_191090_1_, BlockPos p_191090_2_, String p_191090_3_, Rotation p_191090_4_, boolean p_191090_5_) {
/* 209 */     CityTemplate structureendcitypieces$citytemplate = new CityTemplate(p_191090_0_, p_191090_3_, p_191090_1_.templatePosition, p_191090_4_, p_191090_5_);
/* 210 */     BlockPos blockpos = p_191090_1_.template.calculateConnectedPos(p_191090_1_.placeSettings, p_191090_2_, structureendcitypieces$citytemplate.placeSettings, BlockPos.ORIGIN);
/* 211 */     structureendcitypieces$citytemplate.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/* 212 */     return structureendcitypieces$citytemplate;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_191087_a(TemplateManager p_191087_0_, BlockPos p_191087_1_, Rotation p_191087_2_, List<StructureComponent> p_191087_3_, Random p_191087_4_) {
/* 217 */     FAT_TOWER_GENERATOR.init();
/* 218 */     HOUSE_TOWER_GENERATOR.init();
/* 219 */     TOWER_BRIDGE_GENERATOR.init();
/* 220 */     TOWER_GENERATOR.init();
/* 221 */     CityTemplate structureendcitypieces$citytemplate = func_189935_b(p_191087_3_, new CityTemplate(p_191087_0_, "base_floor", p_191087_1_, p_191087_2_, true));
/* 222 */     structureendcitypieces$citytemplate = func_189935_b(p_191087_3_, func_191090_b(p_191087_0_, structureendcitypieces$citytemplate, new BlockPos(-1, 0, -1), "second_floor", p_191087_2_, false));
/* 223 */     structureendcitypieces$citytemplate = func_189935_b(p_191087_3_, func_191090_b(p_191087_0_, structureendcitypieces$citytemplate, new BlockPos(-1, 4, -1), "third_floor", p_191087_2_, false));
/* 224 */     structureendcitypieces$citytemplate = func_189935_b(p_191087_3_, func_191090_b(p_191087_0_, structureendcitypieces$citytemplate, new BlockPos(-1, 8, -1), "third_roof", p_191087_2_, true));
/* 225 */     func_191088_b(p_191087_0_, TOWER_GENERATOR, 1, structureendcitypieces$citytemplate, null, p_191087_3_, p_191087_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static CityTemplate func_189935_b(List<StructureComponent> p_189935_0_, CityTemplate p_189935_1_) {
/* 230 */     p_189935_0_.add(p_189935_1_);
/* 231 */     return p_189935_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean func_191088_b(TemplateManager p_191088_0_, IGenerator p_191088_1_, int p_191088_2_, CityTemplate p_191088_3_, BlockPos p_191088_4_, List<StructureComponent> p_191088_5_, Random p_191088_6_) {
/* 236 */     if (p_191088_2_ > 8)
/*     */     {
/* 238 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 242 */     List<StructureComponent> list = Lists.newArrayList();
/*     */     
/* 244 */     if (p_191088_1_.func_191086_a(p_191088_0_, p_191088_2_, p_191088_3_, p_191088_4_, list, p_191088_6_)) {
/*     */       
/* 246 */       boolean flag = false;
/* 247 */       int i = p_191088_6_.nextInt();
/*     */       
/* 249 */       for (StructureComponent structurecomponent : list) {
/*     */         
/* 251 */         structurecomponent.componentType = i;
/* 252 */         StructureComponent structurecomponent1 = StructureComponent.findIntersecting(p_191088_5_, structurecomponent.getBoundingBox());
/*     */         
/* 254 */         if (structurecomponent1 != null && structurecomponent1.componentType != p_191088_3_.componentType) {
/*     */           
/* 256 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 261 */       if (!flag) {
/*     */         
/* 263 */         p_191088_5_.addAll(list);
/* 264 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CityTemplate
/*     */     extends StructureComponentTemplate
/*     */   {
/*     */     private String pieceName;
/*     */     
/*     */     private Rotation rotation;
/*     */     
/*     */     private boolean overwrite;
/*     */     
/*     */     public CityTemplate() {}
/*     */     
/*     */     public CityTemplate(TemplateManager p_i47214_1_, String p_i47214_2_, BlockPos p_i47214_3_, Rotation p_i47214_4_, boolean p_i47214_5_) {
/* 284 */       super(0);
/* 285 */       this.pieceName = p_i47214_2_;
/* 286 */       this.templatePosition = p_i47214_3_;
/* 287 */       this.rotation = p_i47214_4_;
/* 288 */       this.overwrite = p_i47214_5_;
/* 289 */       func_191085_a(p_i47214_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_191085_a(TemplateManager p_191085_1_) {
/* 294 */       Template template = p_191085_1_.getTemplate(null, new ResourceLocation("endcity/" + this.pieceName));
/* 295 */       PlacementSettings placementsettings = (this.overwrite ? StructureEndCityPieces.OVERWRITE : StructureEndCityPieces.INSERT).copy().setRotation(this.rotation);
/* 296 */       setup(template, this.templatePosition, placementsettings);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 301 */       super.writeStructureToNBT(tagCompound);
/* 302 */       tagCompound.setString("Template", this.pieceName);
/* 303 */       tagCompound.setString("Rot", this.rotation.name());
/* 304 */       tagCompound.setBoolean("OW", this.overwrite);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 309 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/* 310 */       this.pieceName = tagCompound.getString("Template");
/* 311 */       this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
/* 312 */       this.overwrite = tagCompound.getBoolean("OW");
/* 313 */       func_191085_a(p_143011_2_);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void handleDataMarker(String p_186175_1_, BlockPos p_186175_2_, World p_186175_3_, Random p_186175_4_, StructureBoundingBox p_186175_5_) {
/* 318 */       if (p_186175_1_.startsWith("Chest")) {
/*     */         
/* 320 */         BlockPos blockpos = p_186175_2_.down();
/*     */         
/* 322 */         if (p_186175_5_.isVecInside((Vec3i)blockpos))
/*     */         {
/* 324 */           TileEntity tileentity = p_186175_3_.getTileEntity(blockpos);
/*     */           
/* 326 */           if (tileentity instanceof TileEntityChest)
/*     */           {
/* 328 */             ((TileEntityChest)tileentity).setLootTable(LootTableList.CHESTS_END_CITY_TREASURE, p_186175_4_.nextLong());
/*     */           }
/*     */         }
/*     */       
/* 332 */       } else if (p_186175_1_.startsWith("Sentry")) {
/*     */         
/* 334 */         EntityShulker entityshulker = new EntityShulker(p_186175_3_);
/* 335 */         entityshulker.setPosition(p_186175_2_.getX() + 0.5D, p_186175_2_.getY() + 0.5D, p_186175_2_.getZ() + 0.5D);
/* 336 */         entityshulker.setAttachmentPos(p_186175_2_);
/* 337 */         p_186175_3_.spawnEntityInWorld((Entity)entityshulker);
/*     */       }
/* 339 */       else if (p_186175_1_.startsWith("Elytra")) {
/*     */         
/* 341 */         EntityItemFrame entityitemframe = new EntityItemFrame(p_186175_3_, p_186175_2_, this.rotation.rotate(EnumFacing.SOUTH));
/* 342 */         entityitemframe.setDisplayedItem(new ItemStack(Items.ELYTRA));
/* 343 */         p_186175_3_.spawnEntityInWorld((Entity)entityitemframe);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static interface IGenerator {
/*     */     void init();
/*     */     
/*     */     boolean func_191086_a(TemplateManager param1TemplateManager, int param1Int, StructureEndCityPieces.CityTemplate param1CityTemplate, BlockPos param1BlockPos, List<StructureComponent> param1List, Random param1Random);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\StructureEndCityPieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */