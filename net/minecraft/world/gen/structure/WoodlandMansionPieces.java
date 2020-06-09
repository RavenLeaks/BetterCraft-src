/*      */ package net.minecraft.world.gen.structure;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.BlockChest;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.monster.EntityEvoker;
/*      */ import net.minecraft.entity.monster.EntityVindicator;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.Mirror;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Rotation;
/*      */ import net.minecraft.util.Tuple;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.gen.structure.template.PlacementSettings;
/*      */ import net.minecraft.world.gen.structure.template.Template;
/*      */ import net.minecraft.world.gen.structure.template.TemplateManager;
/*      */ import net.minecraft.world.storage.loot.LootTableList;
/*      */ 
/*      */ 
/*      */ public class WoodlandMansionPieces
/*      */ {
/*      */   public static void func_191153_a() {
/*   32 */     MapGenStructureIO.registerStructureComponent((Class)MansionTemplate.class, "WMP");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void func_191152_a(TemplateManager p_191152_0_, BlockPos p_191152_1_, Rotation p_191152_2_, List<MansionTemplate> p_191152_3_, Random p_191152_4_) {
/*   37 */     Grid woodlandmansionpieces$grid = new Grid(p_191152_4_);
/*   38 */     Placer woodlandmansionpieces$placer = new Placer(p_191152_0_, p_191152_4_);
/*   39 */     woodlandmansionpieces$placer.func_191125_a(p_191152_1_, p_191152_2_, p_191152_3_, woodlandmansionpieces$grid);
/*      */   }
/*      */   
/*      */   static class FirstFloor extends RoomCollection {
/*      */     private FirstFloor() {
/*   44 */       super(null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String func_191104_a(Random p_191104_1_) {
/*   50 */       return "1x1_a" + (p_191104_1_.nextInt(5) + 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public String func_191099_b(Random p_191099_1_) {
/*   55 */       return "1x1_as" + (p_191099_1_.nextInt(4) + 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public String func_191100_a(Random p_191100_1_, boolean p_191100_2_) {
/*   60 */       return "1x2_a" + (p_191100_1_.nextInt(9) + 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public String func_191098_b(Random p_191098_1_, boolean p_191098_2_) {
/*   65 */       return "1x2_b" + (p_191098_1_.nextInt(5) + 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public String func_191102_c(Random p_191102_1_) {
/*   70 */       return "1x2_s" + (p_191102_1_.nextInt(2) + 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public String func_191101_d(Random p_191101_1_) {
/*   75 */       return "2x2_a" + (p_191101_1_.nextInt(4) + 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public String func_191103_e(Random p_191103_1_) {
/*   80 */       return "2x2_s1";
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class Grid
/*      */   {
/*      */     private final Random field_191117_a;
/*      */     private final WoodlandMansionPieces.SimpleGrid field_191118_b;
/*      */     private final WoodlandMansionPieces.SimpleGrid field_191119_c;
/*      */     private final WoodlandMansionPieces.SimpleGrid[] field_191120_d;
/*      */     private final int field_191121_e;
/*      */     private final int field_191122_f;
/*      */     
/*      */     public Grid(Random p_i47362_1_) {
/*   95 */       this.field_191117_a = p_i47362_1_;
/*   96 */       int i = 11;
/*   97 */       this.field_191121_e = 7;
/*   98 */       this.field_191122_f = 4;
/*   99 */       this.field_191118_b = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
/*  100 */       this.field_191118_b.func_191142_a(this.field_191121_e, this.field_191122_f, this.field_191121_e + 1, this.field_191122_f + 1, 3);
/*  101 */       this.field_191118_b.func_191142_a(this.field_191121_e - 1, this.field_191122_f, this.field_191121_e - 1, this.field_191122_f + 1, 2);
/*  102 */       this.field_191118_b.func_191142_a(this.field_191121_e + 2, this.field_191122_f - 2, this.field_191121_e + 3, this.field_191122_f + 3, 5);
/*  103 */       this.field_191118_b.func_191142_a(this.field_191121_e + 1, this.field_191122_f - 2, this.field_191121_e + 1, this.field_191122_f - 1, 1);
/*  104 */       this.field_191118_b.func_191142_a(this.field_191121_e + 1, this.field_191122_f + 2, this.field_191121_e + 1, this.field_191122_f + 3, 1);
/*  105 */       this.field_191118_b.func_191144_a(this.field_191121_e - 1, this.field_191122_f - 1, 1);
/*  106 */       this.field_191118_b.func_191144_a(this.field_191121_e - 1, this.field_191122_f + 2, 1);
/*  107 */       this.field_191118_b.func_191142_a(0, 0, 11, 1, 5);
/*  108 */       this.field_191118_b.func_191142_a(0, 9, 11, 11, 5);
/*  109 */       func_191110_a(this.field_191118_b, this.field_191121_e, this.field_191122_f - 2, EnumFacing.WEST, 6);
/*  110 */       func_191110_a(this.field_191118_b, this.field_191121_e, this.field_191122_f + 3, EnumFacing.WEST, 6);
/*  111 */       func_191110_a(this.field_191118_b, this.field_191121_e - 2, this.field_191122_f - 1, EnumFacing.WEST, 3);
/*  112 */       func_191110_a(this.field_191118_b, this.field_191121_e - 2, this.field_191122_f + 2, EnumFacing.WEST, 3); do {
/*      */       
/*  114 */       } while (func_191111_a(this.field_191118_b));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  119 */       this.field_191120_d = new WoodlandMansionPieces.SimpleGrid[3];
/*  120 */       this.field_191120_d[0] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
/*  121 */       this.field_191120_d[1] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
/*  122 */       this.field_191120_d[2] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
/*  123 */       func_191116_a(this.field_191118_b, this.field_191120_d[0]);
/*  124 */       func_191116_a(this.field_191118_b, this.field_191120_d[1]);
/*  125 */       this.field_191120_d[0].func_191142_a(this.field_191121_e + 1, this.field_191122_f, this.field_191121_e + 1, this.field_191122_f + 1, 8388608);
/*  126 */       this.field_191120_d[1].func_191142_a(this.field_191121_e + 1, this.field_191122_f, this.field_191121_e + 1, this.field_191122_f + 1, 8388608);
/*  127 */       this.field_191119_c = new WoodlandMansionPieces.SimpleGrid(this.field_191118_b.field_191149_b, this.field_191118_b.field_191150_c, 5);
/*  128 */       func_191115_b();
/*  129 */       func_191116_a(this.field_191119_c, this.field_191120_d[2]);
/*      */     }
/*      */ 
/*      */     
/*      */     public static boolean func_191109_a(WoodlandMansionPieces.SimpleGrid p_191109_0_, int p_191109_1_, int p_191109_2_) {
/*  134 */       int i = p_191109_0_.func_191145_a(p_191109_1_, p_191109_2_);
/*  135 */       return !(i != 1 && i != 2 && i != 3 && i != 4);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean func_191114_a(WoodlandMansionPieces.SimpleGrid p_191114_1_, int p_191114_2_, int p_191114_3_, int p_191114_4_, int p_191114_5_) {
/*  140 */       return ((this.field_191120_d[p_191114_4_].func_191145_a(p_191114_2_, p_191114_3_) & 0xFFFF) == p_191114_5_);
/*      */     } @Nullable
/*      */     public EnumFacing func_191113_b(WoodlandMansionPieces.SimpleGrid p_191113_1_, int p_191113_2_, int p_191113_3_, int p_191113_4_, int p_191113_5_) {
/*      */       byte b;
/*      */       int i;
/*      */       EnumFacing[] arrayOfEnumFacing;
/*  146 */       for (i = (arrayOfEnumFacing = EnumFacing.Plane.HORIZONTAL.facings()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*      */         
/*  148 */         if (func_191114_a(p_191113_1_, p_191113_2_ + enumfacing.getFrontOffsetX(), p_191113_3_ + enumfacing.getFrontOffsetZ(), p_191113_4_, p_191113_5_))
/*      */         {
/*  150 */           return enumfacing;
/*      */         }
/*      */         b++; }
/*      */       
/*  154 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     private void func_191110_a(WoodlandMansionPieces.SimpleGrid p_191110_1_, int p_191110_2_, int p_191110_3_, EnumFacing p_191110_4_, int p_191110_5_) {
/*  159 */       if (p_191110_5_ > 0) {
/*      */         
/*  161 */         p_191110_1_.func_191144_a(p_191110_2_, p_191110_3_, 1);
/*  162 */         p_191110_1_.func_191141_a(p_191110_2_ + p_191110_4_.getFrontOffsetX(), p_191110_3_ + p_191110_4_.getFrontOffsetZ(), 0, 1);
/*      */         
/*  164 */         for (int i = 0; i < 8; i++) {
/*      */           
/*  166 */           EnumFacing enumfacing = EnumFacing.getHorizontal(this.field_191117_a.nextInt(4));
/*      */           
/*  168 */           if (enumfacing != p_191110_4_.getOpposite() && (enumfacing != EnumFacing.EAST || !this.field_191117_a.nextBoolean())) {
/*      */             
/*  170 */             int j = p_191110_2_ + p_191110_4_.getFrontOffsetX();
/*  171 */             int k = p_191110_3_ + p_191110_4_.getFrontOffsetZ();
/*      */             
/*  173 */             if (p_191110_1_.func_191145_a(j + enumfacing.getFrontOffsetX(), k + enumfacing.getFrontOffsetZ()) == 0 && p_191110_1_.func_191145_a(j + enumfacing.getFrontOffsetX() * 2, k + enumfacing.getFrontOffsetZ() * 2) == 0) {
/*      */               
/*  175 */               func_191110_a(p_191110_1_, p_191110_2_ + p_191110_4_.getFrontOffsetX() + enumfacing.getFrontOffsetX(), p_191110_3_ + p_191110_4_.getFrontOffsetZ() + enumfacing.getFrontOffsetZ(), enumfacing, p_191110_5_ - 1);
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*  181 */         EnumFacing enumfacing1 = p_191110_4_.rotateY();
/*  182 */         EnumFacing enumfacing2 = p_191110_4_.rotateYCCW();
/*  183 */         p_191110_1_.func_191141_a(p_191110_2_ + enumfacing1.getFrontOffsetX(), p_191110_3_ + enumfacing1.getFrontOffsetZ(), 0, 2);
/*  184 */         p_191110_1_.func_191141_a(p_191110_2_ + enumfacing2.getFrontOffsetX(), p_191110_3_ + enumfacing2.getFrontOffsetZ(), 0, 2);
/*  185 */         p_191110_1_.func_191141_a(p_191110_2_ + p_191110_4_.getFrontOffsetX() + enumfacing1.getFrontOffsetX(), p_191110_3_ + p_191110_4_.getFrontOffsetZ() + enumfacing1.getFrontOffsetZ(), 0, 2);
/*  186 */         p_191110_1_.func_191141_a(p_191110_2_ + p_191110_4_.getFrontOffsetX() + enumfacing2.getFrontOffsetX(), p_191110_3_ + p_191110_4_.getFrontOffsetZ() + enumfacing2.getFrontOffsetZ(), 0, 2);
/*  187 */         p_191110_1_.func_191141_a(p_191110_2_ + p_191110_4_.getFrontOffsetX() * 2, p_191110_3_ + p_191110_4_.getFrontOffsetZ() * 2, 0, 2);
/*  188 */         p_191110_1_.func_191141_a(p_191110_2_ + enumfacing1.getFrontOffsetX() * 2, p_191110_3_ + enumfacing1.getFrontOffsetZ() * 2, 0, 2);
/*  189 */         p_191110_1_.func_191141_a(p_191110_2_ + enumfacing2.getFrontOffsetX() * 2, p_191110_3_ + enumfacing2.getFrontOffsetZ() * 2, 0, 2);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private boolean func_191111_a(WoodlandMansionPieces.SimpleGrid p_191111_1_) {
/*  195 */       boolean flag = false;
/*      */       
/*  197 */       for (int i = 0; i < p_191111_1_.field_191150_c; i++) {
/*      */         
/*  199 */         for (int j = 0; j < p_191111_1_.field_191149_b; j++) {
/*      */           
/*  201 */           if (p_191111_1_.func_191145_a(j, i) == 0) {
/*      */             
/*  203 */             int k = 0;
/*  204 */             k += func_191109_a(p_191111_1_, j + 1, i) ? 1 : 0;
/*  205 */             k += func_191109_a(p_191111_1_, j - 1, i) ? 1 : 0;
/*  206 */             k += func_191109_a(p_191111_1_, j, i + 1) ? 1 : 0;
/*  207 */             k += func_191109_a(p_191111_1_, j, i - 1) ? 1 : 0;
/*      */             
/*  209 */             if (k >= 3) {
/*      */               
/*  211 */               p_191111_1_.func_191144_a(j, i, 2);
/*  212 */               flag = true;
/*      */             }
/*  214 */             else if (k == 2) {
/*      */               
/*  216 */               int l = 0;
/*  217 */               l += func_191109_a(p_191111_1_, j + 1, i + 1) ? 1 : 0;
/*  218 */               l += func_191109_a(p_191111_1_, j - 1, i + 1) ? 1 : 0;
/*  219 */               l += func_191109_a(p_191111_1_, j + 1, i - 1) ? 1 : 0;
/*  220 */               l += func_191109_a(p_191111_1_, j - 1, i - 1) ? 1 : 0;
/*      */               
/*  222 */               if (l <= 1) {
/*      */                 
/*  224 */                 p_191111_1_.func_191144_a(j, i, 2);
/*  225 */                 flag = true;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  232 */       return flag;
/*      */     }
/*      */ 
/*      */     
/*      */     private void func_191115_b() {
/*  237 */       List<Tuple<Integer, Integer>> list = Lists.newArrayList();
/*  238 */       WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid = this.field_191120_d[1];
/*      */       
/*  240 */       for (int i = 0; i < this.field_191119_c.field_191150_c; i++) {
/*      */         
/*  242 */         for (int j = 0; j < this.field_191119_c.field_191149_b; j++) {
/*      */           
/*  244 */           int k = woodlandmansionpieces$simplegrid.func_191145_a(j, i);
/*  245 */           int l = k & 0xF0000;
/*      */           
/*  247 */           if (l == 131072 && (k & 0x200000) == 2097152)
/*      */           {
/*  249 */             list.add(new Tuple(Integer.valueOf(j), Integer.valueOf(i)));
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  254 */       if (list.isEmpty()) {
/*      */         
/*  256 */         this.field_191119_c.func_191142_a(0, 0, this.field_191119_c.field_191149_b, this.field_191119_c.field_191150_c, 5);
/*      */       }
/*      */       else {
/*      */         
/*  260 */         Tuple<Integer, Integer> tuple = list.get(this.field_191117_a.nextInt(list.size()));
/*  261 */         int l1 = woodlandmansionpieces$simplegrid.func_191145_a(((Integer)tuple.getFirst()).intValue(), ((Integer)tuple.getSecond()).intValue());
/*  262 */         woodlandmansionpieces$simplegrid.func_191144_a(((Integer)tuple.getFirst()).intValue(), ((Integer)tuple.getSecond()).intValue(), l1 | 0x400000);
/*  263 */         EnumFacing enumfacing1 = func_191113_b(this.field_191118_b, ((Integer)tuple.getFirst()).intValue(), ((Integer)tuple.getSecond()).intValue(), 1, l1 & 0xFFFF);
/*  264 */         int i2 = ((Integer)tuple.getFirst()).intValue() + enumfacing1.getFrontOffsetX();
/*  265 */         int i1 = ((Integer)tuple.getSecond()).intValue() + enumfacing1.getFrontOffsetZ();
/*      */         
/*  267 */         for (int j1 = 0; j1 < this.field_191119_c.field_191150_c; j1++) {
/*      */           
/*  269 */           for (int k1 = 0; k1 < this.field_191119_c.field_191149_b; k1++) {
/*      */             
/*  271 */             if (!func_191109_a(this.field_191118_b, k1, j1)) {
/*      */               
/*  273 */               this.field_191119_c.func_191144_a(k1, j1, 5);
/*      */             }
/*  275 */             else if (k1 == ((Integer)tuple.getFirst()).intValue() && j1 == ((Integer)tuple.getSecond()).intValue()) {
/*      */               
/*  277 */               this.field_191119_c.func_191144_a(k1, j1, 3);
/*      */             }
/*  279 */             else if (k1 == i2 && j1 == i1) {
/*      */               
/*  281 */               this.field_191119_c.func_191144_a(k1, j1, 3);
/*  282 */               this.field_191120_d[2].func_191144_a(k1, j1, 8388608);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  287 */         List<EnumFacing> list1 = Lists.newArrayList(); byte b; int j;
/*      */         EnumFacing[] arrayOfEnumFacing;
/*  289 */         for (j = (arrayOfEnumFacing = EnumFacing.Plane.HORIZONTAL.facings()).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*      */           
/*  291 */           if (this.field_191119_c.func_191145_a(i2 + enumfacing.getFrontOffsetX(), i1 + enumfacing.getFrontOffsetZ()) == 0)
/*      */           {
/*  293 */             list1.add(enumfacing);
/*      */           }
/*      */           b++; }
/*      */         
/*  297 */         if (list1.isEmpty()) {
/*      */           
/*  299 */           this.field_191119_c.func_191142_a(0, 0, this.field_191119_c.field_191149_b, this.field_191119_c.field_191150_c, 5);
/*  300 */           woodlandmansionpieces$simplegrid.func_191144_a(((Integer)tuple.getFirst()).intValue(), ((Integer)tuple.getSecond()).intValue(), l1);
/*      */         }
/*      */         else {
/*      */           
/*  304 */           EnumFacing enumfacing2 = list1.get(this.field_191117_a.nextInt(list1.size()));
/*  305 */           func_191110_a(this.field_191119_c, i2 + enumfacing2.getFrontOffsetX(), i1 + enumfacing2.getFrontOffsetZ(), enumfacing2, 4); do {
/*      */           
/*  307 */           } while (func_191111_a(this.field_191119_c));
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void func_191116_a(WoodlandMansionPieces.SimpleGrid p_191116_1_, WoodlandMansionPieces.SimpleGrid p_191116_2_) {
/*  317 */       List<Tuple<Integer, Integer>> list = Lists.newArrayList();
/*      */       
/*  319 */       for (int i = 0; i < p_191116_1_.field_191150_c; i++) {
/*      */         
/*  321 */         for (int j = 0; j < p_191116_1_.field_191149_b; j++) {
/*      */           
/*  323 */           if (p_191116_1_.func_191145_a(j, i) == 2)
/*      */           {
/*  325 */             list.add(new Tuple(Integer.valueOf(j), Integer.valueOf(i)));
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  330 */       Collections.shuffle(list, this.field_191117_a);
/*  331 */       int k3 = 10;
/*      */       
/*  333 */       for (Tuple<Integer, Integer> tuple : list) {
/*      */         
/*  335 */         int k = ((Integer)tuple.getFirst()).intValue();
/*  336 */         int l = ((Integer)tuple.getSecond()).intValue();
/*      */         
/*  338 */         if (p_191116_2_.func_191145_a(k, l) == 0) {
/*      */           
/*  340 */           int i1 = k;
/*  341 */           int j1 = k;
/*  342 */           int k1 = l;
/*  343 */           int l1 = l;
/*  344 */           int i2 = 65536;
/*      */           
/*  346 */           if (p_191116_2_.func_191145_a(k + 1, l) == 0 && p_191116_2_.func_191145_a(k, l + 1) == 0 && p_191116_2_.func_191145_a(k + 1, l + 1) == 0 && p_191116_1_.func_191145_a(k + 1, l) == 2 && p_191116_1_.func_191145_a(k, l + 1) == 2 && p_191116_1_.func_191145_a(k + 1, l + 1) == 2) {
/*      */             
/*  348 */             j1 = k + 1;
/*  349 */             l1 = l + 1;
/*  350 */             i2 = 262144;
/*      */           }
/*  352 */           else if (p_191116_2_.func_191145_a(k - 1, l) == 0 && p_191116_2_.func_191145_a(k, l + 1) == 0 && p_191116_2_.func_191145_a(k - 1, l + 1) == 0 && p_191116_1_.func_191145_a(k - 1, l) == 2 && p_191116_1_.func_191145_a(k, l + 1) == 2 && p_191116_1_.func_191145_a(k - 1, l + 1) == 2) {
/*      */             
/*  354 */             i1 = k - 1;
/*  355 */             l1 = l + 1;
/*  356 */             i2 = 262144;
/*      */           }
/*  358 */           else if (p_191116_2_.func_191145_a(k - 1, l) == 0 && p_191116_2_.func_191145_a(k, l - 1) == 0 && p_191116_2_.func_191145_a(k - 1, l - 1) == 0 && p_191116_1_.func_191145_a(k - 1, l) == 2 && p_191116_1_.func_191145_a(k, l - 1) == 2 && p_191116_1_.func_191145_a(k - 1, l - 1) == 2) {
/*      */             
/*  360 */             i1 = k - 1;
/*  361 */             k1 = l - 1;
/*  362 */             i2 = 262144;
/*      */           }
/*  364 */           else if (p_191116_2_.func_191145_a(k + 1, l) == 0 && p_191116_1_.func_191145_a(k + 1, l) == 2) {
/*      */             
/*  366 */             j1 = k + 1;
/*  367 */             i2 = 131072;
/*      */           }
/*  369 */           else if (p_191116_2_.func_191145_a(k, l + 1) == 0 && p_191116_1_.func_191145_a(k, l + 1) == 2) {
/*      */             
/*  371 */             l1 = l + 1;
/*  372 */             i2 = 131072;
/*      */           }
/*  374 */           else if (p_191116_2_.func_191145_a(k - 1, l) == 0 && p_191116_1_.func_191145_a(k - 1, l) == 2) {
/*      */             
/*  376 */             i1 = k - 1;
/*  377 */             i2 = 131072;
/*      */           }
/*  379 */           else if (p_191116_2_.func_191145_a(k, l - 1) == 0 && p_191116_1_.func_191145_a(k, l - 1) == 2) {
/*      */             
/*  381 */             k1 = l - 1;
/*  382 */             i2 = 131072;
/*      */           } 
/*      */           
/*  385 */           int j2 = this.field_191117_a.nextBoolean() ? i1 : j1;
/*  386 */           int k2 = this.field_191117_a.nextBoolean() ? k1 : l1;
/*  387 */           int l2 = 2097152;
/*      */           
/*  389 */           if (!p_191116_1_.func_191147_b(j2, k2, 1)) {
/*      */             
/*  391 */             j2 = (j2 == i1) ? j1 : i1;
/*  392 */             k2 = (k2 == k1) ? l1 : k1;
/*      */             
/*  394 */             if (!p_191116_1_.func_191147_b(j2, k2, 1)) {
/*      */               
/*  396 */               k2 = (k2 == k1) ? l1 : k1;
/*      */               
/*  398 */               if (!p_191116_1_.func_191147_b(j2, k2, 1)) {
/*      */                 
/*  400 */                 j2 = (j2 == i1) ? j1 : i1;
/*  401 */                 k2 = (k2 == k1) ? l1 : k1;
/*      */                 
/*  403 */                 if (!p_191116_1_.func_191147_b(j2, k2, 1)) {
/*      */                   
/*  405 */                   l2 = 0;
/*  406 */                   j2 = i1;
/*  407 */                   k2 = k1;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/*  413 */           for (int i3 = k1; i3 <= l1; i3++) {
/*      */             
/*  415 */             for (int j3 = i1; j3 <= j1; j3++) {
/*      */               
/*  417 */               if (j3 == j2 && i3 == k2) {
/*      */                 
/*  419 */                 p_191116_2_.func_191144_a(j3, i3, 0x100000 | l2 | i2 | k3);
/*      */               }
/*      */               else {
/*      */                 
/*  423 */                 p_191116_2_.func_191144_a(j3, i3, i2 | k3);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/*  428 */           k3++;
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class MansionTemplate
/*      */     extends StructureComponentTemplate
/*      */   {
/*      */     private String field_191082_d;
/*      */     
/*      */     private Rotation field_191083_e;
/*      */     private Mirror field_191084_f;
/*      */     
/*      */     public MansionTemplate() {}
/*      */     
/*      */     public MansionTemplate(TemplateManager p_i47355_1_, String p_i47355_2_, BlockPos p_i47355_3_, Rotation p_i47355_4_) {
/*  446 */       this(p_i47355_1_, p_i47355_2_, p_i47355_3_, p_i47355_4_, Mirror.NONE);
/*      */     }
/*      */ 
/*      */     
/*      */     public MansionTemplate(TemplateManager p_i47356_1_, String p_i47356_2_, BlockPos p_i47356_3_, Rotation p_i47356_4_, Mirror p_i47356_5_) {
/*  451 */       super(0);
/*  452 */       this.field_191082_d = p_i47356_2_;
/*  453 */       this.templatePosition = p_i47356_3_;
/*  454 */       this.field_191083_e = p_i47356_4_;
/*  455 */       this.field_191084_f = p_i47356_5_;
/*  456 */       func_191081_a(p_i47356_1_);
/*      */     }
/*      */ 
/*      */     
/*      */     private void func_191081_a(TemplateManager p_191081_1_) {
/*  461 */       Template template = p_191081_1_.getTemplate(null, new ResourceLocation("mansion/" + this.field_191082_d));
/*  462 */       PlacementSettings placementsettings = (new PlacementSettings()).setIgnoreEntities(true).setRotation(this.field_191083_e).setMirror(this.field_191084_f);
/*  463 */       setup(template, this.templatePosition, placementsettings);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  468 */       super.writeStructureToNBT(tagCompound);
/*  469 */       tagCompound.setString("Template", this.field_191082_d);
/*  470 */       tagCompound.setString("Rot", this.placeSettings.getRotation().name());
/*  471 */       tagCompound.setString("Mi", this.placeSettings.getMirror().name());
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/*  476 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/*  477 */       this.field_191082_d = tagCompound.getString("Template");
/*  478 */       this.field_191083_e = Rotation.valueOf(tagCompound.getString("Rot"));
/*  479 */       this.field_191084_f = Mirror.valueOf(tagCompound.getString("Mi"));
/*  480 */       func_191081_a(p_143011_2_);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void handleDataMarker(String p_186175_1_, BlockPos p_186175_2_, World p_186175_3_, Random p_186175_4_, StructureBoundingBox p_186175_5_) {
/*  485 */       if (p_186175_1_.startsWith("Chest")) {
/*      */         
/*  487 */         Rotation rotation = this.placeSettings.getRotation();
/*  488 */         IBlockState iblockstate = Blocks.CHEST.getDefaultState();
/*      */         
/*  490 */         if ("ChestWest".equals(p_186175_1_)) {
/*      */           
/*  492 */           iblockstate = iblockstate.withProperty((IProperty)BlockChest.FACING, (Comparable)rotation.rotate(EnumFacing.WEST));
/*      */         }
/*  494 */         else if ("ChestEast".equals(p_186175_1_)) {
/*      */           
/*  496 */           iblockstate = iblockstate.withProperty((IProperty)BlockChest.FACING, (Comparable)rotation.rotate(EnumFacing.EAST));
/*      */         }
/*  498 */         else if ("ChestSouth".equals(p_186175_1_)) {
/*      */           
/*  500 */           iblockstate = iblockstate.withProperty((IProperty)BlockChest.FACING, (Comparable)rotation.rotate(EnumFacing.SOUTH));
/*      */         }
/*  502 */         else if ("ChestNorth".equals(p_186175_1_)) {
/*      */           
/*  504 */           iblockstate = iblockstate.withProperty((IProperty)BlockChest.FACING, (Comparable)rotation.rotate(EnumFacing.NORTH));
/*      */         } 
/*      */         
/*  507 */         func_191080_a(p_186175_3_, p_186175_5_, p_186175_4_, p_186175_2_, LootTableList.field_191192_o, iblockstate);
/*      */       }
/*  509 */       else if ("Mage".equals(p_186175_1_)) {
/*      */         
/*  511 */         EntityEvoker entityevoker = new EntityEvoker(p_186175_3_);
/*  512 */         entityevoker.enablePersistence();
/*  513 */         entityevoker.moveToBlockPosAndAngles(p_186175_2_, 0.0F, 0.0F);
/*  514 */         p_186175_3_.spawnEntityInWorld((Entity)entityevoker);
/*  515 */         p_186175_3_.setBlockState(p_186175_2_, Blocks.AIR.getDefaultState(), 2);
/*      */       }
/*  517 */       else if ("Warrior".equals(p_186175_1_)) {
/*      */         
/*  519 */         EntityVindicator entityvindicator = new EntityVindicator(p_186175_3_);
/*  520 */         entityvindicator.enablePersistence();
/*  521 */         entityvindicator.moveToBlockPosAndAngles(p_186175_2_, 0.0F, 0.0F);
/*  522 */         entityvindicator.onInitialSpawn(p_186175_3_.getDifficultyForLocation(new BlockPos((Entity)entityvindicator)), null);
/*  523 */         p_186175_3_.spawnEntityInWorld((Entity)entityvindicator);
/*  524 */         p_186175_3_.setBlockState(p_186175_2_, Blocks.AIR.getDefaultState(), 2);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class PlacementData
/*      */   {
/*      */     public Rotation field_191138_a;
/*      */     
/*      */     public BlockPos field_191139_b;
/*      */     
/*      */     public String field_191140_c;
/*      */     
/*      */     private PlacementData() {}
/*      */   }
/*      */   
/*      */   static class Placer
/*      */   {
/*      */     private final TemplateManager field_191134_a;
/*      */     private final Random field_191135_b;
/*      */     private int field_191136_c;
/*      */     private int field_191137_d;
/*      */     
/*      */     public Placer(TemplateManager p_i47361_1_, Random p_i47361_2_) {
/*  549 */       this.field_191134_a = p_i47361_1_;
/*  550 */       this.field_191135_b = p_i47361_2_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void func_191125_a(BlockPos p_191125_1_, Rotation p_191125_2_, List<WoodlandMansionPieces.MansionTemplate> p_191125_3_, WoodlandMansionPieces.Grid p_191125_4_) {
/*  555 */       WoodlandMansionPieces.PlacementData woodlandmansionpieces$placementdata = new WoodlandMansionPieces.PlacementData(null);
/*  556 */       woodlandmansionpieces$placementdata.field_191139_b = p_191125_1_;
/*  557 */       woodlandmansionpieces$placementdata.field_191138_a = p_191125_2_;
/*  558 */       woodlandmansionpieces$placementdata.field_191140_c = "wall_flat";
/*  559 */       WoodlandMansionPieces.PlacementData woodlandmansionpieces$placementdata1 = new WoodlandMansionPieces.PlacementData(null);
/*  560 */       func_191133_a(p_191125_3_, woodlandmansionpieces$placementdata);
/*  561 */       woodlandmansionpieces$placementdata1.field_191139_b = woodlandmansionpieces$placementdata.field_191139_b.up(8);
/*  562 */       woodlandmansionpieces$placementdata1.field_191138_a = woodlandmansionpieces$placementdata.field_191138_a;
/*  563 */       woodlandmansionpieces$placementdata1.field_191140_c = "wall_window";
/*      */       
/*  565 */       if (!p_191125_3_.isEmpty());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  570 */       WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid = p_191125_4_.field_191118_b;
/*  571 */       WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid1 = p_191125_4_.field_191119_c;
/*  572 */       this.field_191136_c = p_191125_4_.field_191121_e + 1;
/*  573 */       this.field_191137_d = p_191125_4_.field_191122_f + 1;
/*  574 */       int i = p_191125_4_.field_191121_e + 1;
/*  575 */       int j = p_191125_4_.field_191122_f;
/*  576 */       func_191130_a(p_191125_3_, woodlandmansionpieces$placementdata, woodlandmansionpieces$simplegrid, EnumFacing.SOUTH, this.field_191136_c, this.field_191137_d, i, j);
/*  577 */       func_191130_a(p_191125_3_, woodlandmansionpieces$placementdata1, woodlandmansionpieces$simplegrid, EnumFacing.SOUTH, this.field_191136_c, this.field_191137_d, i, j);
/*  578 */       WoodlandMansionPieces.PlacementData woodlandmansionpieces$placementdata2 = new WoodlandMansionPieces.PlacementData(null);
/*  579 */       woodlandmansionpieces$placementdata2.field_191139_b = woodlandmansionpieces$placementdata.field_191139_b.up(19);
/*  580 */       woodlandmansionpieces$placementdata2.field_191138_a = woodlandmansionpieces$placementdata.field_191138_a;
/*  581 */       woodlandmansionpieces$placementdata2.field_191140_c = "wall_window";
/*  582 */       boolean flag = false;
/*      */       
/*  584 */       for (int k = 0; k < woodlandmansionpieces$simplegrid1.field_191150_c && !flag; k++) {
/*      */         
/*  586 */         for (int l = woodlandmansionpieces$simplegrid1.field_191149_b - 1; l >= 0 && !flag; l--) {
/*      */           
/*  588 */           if (WoodlandMansionPieces.Grid.func_191109_a(woodlandmansionpieces$simplegrid1, l, k)) {
/*      */             
/*  590 */             woodlandmansionpieces$placementdata2.field_191139_b = woodlandmansionpieces$placementdata2.field_191139_b.offset(p_191125_2_.rotate(EnumFacing.SOUTH), 8 + (k - this.field_191137_d) * 8);
/*  591 */             woodlandmansionpieces$placementdata2.field_191139_b = woodlandmansionpieces$placementdata2.field_191139_b.offset(p_191125_2_.rotate(EnumFacing.EAST), (l - this.field_191136_c) * 8);
/*  592 */             func_191131_b(p_191125_3_, woodlandmansionpieces$placementdata2);
/*  593 */             func_191130_a(p_191125_3_, woodlandmansionpieces$placementdata2, woodlandmansionpieces$simplegrid1, EnumFacing.SOUTH, l, k, l, k);
/*  594 */             flag = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  599 */       func_191123_a(p_191125_3_, p_191125_1_.up(16), p_191125_2_, woodlandmansionpieces$simplegrid, woodlandmansionpieces$simplegrid1);
/*  600 */       func_191123_a(p_191125_3_, p_191125_1_.up(27), p_191125_2_, woodlandmansionpieces$simplegrid1, null);
/*      */       
/*  602 */       if (!p_191125_3_.isEmpty());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  607 */       WoodlandMansionPieces.RoomCollection[] awoodlandmansionpieces$roomcollection = new WoodlandMansionPieces.RoomCollection[3];
/*  608 */       awoodlandmansionpieces$roomcollection[0] = new WoodlandMansionPieces.FirstFloor(null);
/*  609 */       awoodlandmansionpieces$roomcollection[1] = new WoodlandMansionPieces.SecondFloor(null);
/*  610 */       awoodlandmansionpieces$roomcollection[2] = new WoodlandMansionPieces.ThirdFloor(null);
/*      */       
/*  612 */       for (int l2 = 0; l2 < 3; l2++) {
/*      */         
/*  614 */         BlockPos blockpos = p_191125_1_.up(8 * l2 + ((l2 == 2) ? 3 : 0));
/*  615 */         WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid2 = p_191125_4_.field_191120_d[l2];
/*  616 */         WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid3 = (l2 == 2) ? woodlandmansionpieces$simplegrid1 : woodlandmansionpieces$simplegrid;
/*  617 */         String s = (l2 == 0) ? "carpet_south" : "carpet_south_2";
/*  618 */         String s1 = (l2 == 0) ? "carpet_west" : "carpet_west_2";
/*      */         
/*  620 */         for (int i1 = 0; i1 < woodlandmansionpieces$simplegrid3.field_191150_c; i1++) {
/*      */           
/*  622 */           for (int j1 = 0; j1 < woodlandmansionpieces$simplegrid3.field_191149_b; j1++) {
/*      */             
/*  624 */             if (woodlandmansionpieces$simplegrid3.func_191145_a(j1, i1) == 1) {
/*      */               
/*  626 */               BlockPos blockpos1 = blockpos.offset(p_191125_2_.rotate(EnumFacing.SOUTH), 8 + (i1 - this.field_191137_d) * 8);
/*  627 */               blockpos1 = blockpos1.offset(p_191125_2_.rotate(EnumFacing.EAST), (j1 - this.field_191136_c) * 8);
/*  628 */               p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "corridor_floor", blockpos1, p_191125_2_));
/*      */               
/*  630 */               if (woodlandmansionpieces$simplegrid3.func_191145_a(j1, i1 - 1) == 1 || (woodlandmansionpieces$simplegrid2.func_191145_a(j1, i1 - 1) & 0x800000) == 8388608)
/*      */               {
/*  632 */                 p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "carpet_north", blockpos1.offset(p_191125_2_.rotate(EnumFacing.EAST), 1).up(), p_191125_2_));
/*      */               }
/*      */               
/*  635 */               if (woodlandmansionpieces$simplegrid3.func_191145_a(j1 + 1, i1) == 1 || (woodlandmansionpieces$simplegrid2.func_191145_a(j1 + 1, i1) & 0x800000) == 8388608)
/*      */               {
/*  637 */                 p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "carpet_east", blockpos1.offset(p_191125_2_.rotate(EnumFacing.SOUTH), 1).offset(p_191125_2_.rotate(EnumFacing.EAST), 5).up(), p_191125_2_));
/*      */               }
/*      */               
/*  640 */               if (woodlandmansionpieces$simplegrid3.func_191145_a(j1, i1 + 1) == 1 || (woodlandmansionpieces$simplegrid2.func_191145_a(j1, i1 + 1) & 0x800000) == 8388608)
/*      */               {
/*  642 */                 p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, s, blockpos1.offset(p_191125_2_.rotate(EnumFacing.SOUTH), 5).offset(p_191125_2_.rotate(EnumFacing.WEST), 1), p_191125_2_));
/*      */               }
/*      */               
/*  645 */               if (woodlandmansionpieces$simplegrid3.func_191145_a(j1 - 1, i1) == 1 || (woodlandmansionpieces$simplegrid2.func_191145_a(j1 - 1, i1) & 0x800000) == 8388608)
/*      */               {
/*  647 */                 p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, s1, blockpos1.offset(p_191125_2_.rotate(EnumFacing.WEST), 1).offset(p_191125_2_.rotate(EnumFacing.NORTH), 1), p_191125_2_));
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  653 */         String s2 = (l2 == 0) ? "indoors_wall" : "indoors_wall_2";
/*  654 */         String s3 = (l2 == 0) ? "indoors_door" : "indoors_door_2";
/*  655 */         List<EnumFacing> list = Lists.newArrayList();
/*      */         
/*  657 */         for (int k1 = 0; k1 < woodlandmansionpieces$simplegrid3.field_191150_c; k1++) {
/*      */           
/*  659 */           for (int l1 = 0; l1 < woodlandmansionpieces$simplegrid3.field_191149_b; l1++) {
/*      */             
/*  661 */             boolean flag1 = (l2 == 2 && woodlandmansionpieces$simplegrid3.func_191145_a(l1, k1) == 3);
/*      */             
/*  663 */             if (woodlandmansionpieces$simplegrid3.func_191145_a(l1, k1) == 2 || flag1) {
/*      */               
/*  665 */               int i2 = woodlandmansionpieces$simplegrid2.func_191145_a(l1, k1);
/*  666 */               int j2 = i2 & 0xF0000;
/*  667 */               int k2 = i2 & 0xFFFF;
/*  668 */               flag1 = (flag1 && (i2 & 0x800000) == 8388608);
/*  669 */               list.clear();
/*      */               
/*  671 */               if ((i2 & 0x200000) == 2097152) {
/*      */                 byte b; int m; EnumFacing[] arrayOfEnumFacing;
/*  673 */                 for (m = (arrayOfEnumFacing = EnumFacing.Plane.HORIZONTAL.facings()).length, b = 0; b < m; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*      */                   
/*  675 */                   if (woodlandmansionpieces$simplegrid3.func_191145_a(l1 + enumfacing.getFrontOffsetX(), k1 + enumfacing.getFrontOffsetZ()) == 1)
/*      */                   {
/*  677 */                     list.add(enumfacing);
/*      */                   }
/*      */                   b++; }
/*      */               
/*      */               } 
/*  682 */               EnumFacing enumfacing1 = null;
/*      */               
/*  684 */               if (!list.isEmpty()) {
/*      */                 
/*  686 */                 enumfacing1 = list.get(this.field_191135_b.nextInt(list.size()));
/*      */               }
/*  688 */               else if ((i2 & 0x100000) == 1048576) {
/*      */                 
/*  690 */                 enumfacing1 = EnumFacing.UP;
/*      */               } 
/*      */               
/*  693 */               BlockPos blockpos2 = blockpos.offset(p_191125_2_.rotate(EnumFacing.SOUTH), 8 + (k1 - this.field_191137_d) * 8);
/*  694 */               blockpos2 = blockpos2.offset(p_191125_2_.rotate(EnumFacing.EAST), -1 + (l1 - this.field_191136_c) * 8);
/*      */               
/*  696 */               if (WoodlandMansionPieces.Grid.func_191109_a(woodlandmansionpieces$simplegrid3, l1 - 1, k1) && !p_191125_4_.func_191114_a(woodlandmansionpieces$simplegrid3, l1 - 1, k1, l2, k2))
/*      */               {
/*  698 */                 p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, (enumfacing1 == EnumFacing.WEST) ? s3 : s2, blockpos2, p_191125_2_));
/*      */               }
/*      */               
/*  701 */               if (woodlandmansionpieces$simplegrid3.func_191145_a(l1 + 1, k1) == 1 && !flag1) {
/*      */                 
/*  703 */                 BlockPos blockpos3 = blockpos2.offset(p_191125_2_.rotate(EnumFacing.EAST), 8);
/*  704 */                 p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, (enumfacing1 == EnumFacing.EAST) ? s3 : s2, blockpos3, p_191125_2_));
/*      */               } 
/*      */               
/*  707 */               if (WoodlandMansionPieces.Grid.func_191109_a(woodlandmansionpieces$simplegrid3, l1, k1 + 1) && !p_191125_4_.func_191114_a(woodlandmansionpieces$simplegrid3, l1, k1 + 1, l2, k2)) {
/*      */                 
/*  709 */                 BlockPos blockpos4 = blockpos2.offset(p_191125_2_.rotate(EnumFacing.SOUTH), 7);
/*  710 */                 blockpos4 = blockpos4.offset(p_191125_2_.rotate(EnumFacing.EAST), 7);
/*  711 */                 p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, (enumfacing1 == EnumFacing.SOUTH) ? s3 : s2, blockpos4, p_191125_2_.add(Rotation.CLOCKWISE_90)));
/*      */               } 
/*      */               
/*  714 */               if (woodlandmansionpieces$simplegrid3.func_191145_a(l1, k1 - 1) == 1 && !flag1) {
/*      */                 
/*  716 */                 BlockPos blockpos5 = blockpos2.offset(p_191125_2_.rotate(EnumFacing.NORTH), 1);
/*  717 */                 blockpos5 = blockpos5.offset(p_191125_2_.rotate(EnumFacing.EAST), 7);
/*  718 */                 p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, (enumfacing1 == EnumFacing.NORTH) ? s3 : s2, blockpos5, p_191125_2_.add(Rotation.CLOCKWISE_90)));
/*      */               } 
/*      */               
/*  721 */               if (j2 == 65536) {
/*      */                 
/*  723 */                 func_191129_a(p_191125_3_, blockpos2, p_191125_2_, enumfacing1, awoodlandmansionpieces$roomcollection[l2]);
/*      */               }
/*  725 */               else if (j2 == 131072 && enumfacing1 != null) {
/*      */                 
/*  727 */                 EnumFacing enumfacing3 = p_191125_4_.func_191113_b(woodlandmansionpieces$simplegrid3, l1, k1, l2, k2);
/*  728 */                 boolean flag2 = ((i2 & 0x400000) == 4194304);
/*  729 */                 func_191132_a(p_191125_3_, blockpos2, p_191125_2_, enumfacing3, enumfacing1, awoodlandmansionpieces$roomcollection[l2], flag2);
/*      */               }
/*  731 */               else if (j2 == 262144 && enumfacing1 != null && enumfacing1 != EnumFacing.UP) {
/*      */                 
/*  733 */                 EnumFacing enumfacing2 = enumfacing1.rotateY();
/*      */                 
/*  735 */                 if (!p_191125_4_.func_191114_a(woodlandmansionpieces$simplegrid3, l1 + enumfacing2.getFrontOffsetX(), k1 + enumfacing2.getFrontOffsetZ(), l2, k2))
/*      */                 {
/*  737 */                   enumfacing2 = enumfacing2.getOpposite();
/*      */                 }
/*      */                 
/*  740 */                 func_191127_a(p_191125_3_, blockpos2, p_191125_2_, enumfacing2, enumfacing1, awoodlandmansionpieces$roomcollection[l2]);
/*      */               }
/*  742 */               else if (j2 == 262144 && enumfacing1 == EnumFacing.UP) {
/*      */                 
/*  744 */                 func_191128_a(p_191125_3_, blockpos2, p_191125_2_, awoodlandmansionpieces$roomcollection[l2]);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void func_191130_a(List<WoodlandMansionPieces.MansionTemplate> p_191130_1_, WoodlandMansionPieces.PlacementData p_191130_2_, WoodlandMansionPieces.SimpleGrid p_191130_3_, EnumFacing p_191130_4_, int p_191130_5_, int p_191130_6_, int p_191130_7_, int p_191130_8_) {
/*  754 */       int i = p_191130_5_;
/*  755 */       int j = p_191130_6_;
/*  756 */       EnumFacing enumfacing = p_191130_4_;
/*      */ 
/*      */       
/*      */       do {
/*  760 */         if (!WoodlandMansionPieces.Grid.func_191109_a(p_191130_3_, i + p_191130_4_.getFrontOffsetX(), j + p_191130_4_.getFrontOffsetZ()))
/*      */         {
/*  762 */           func_191124_c(p_191130_1_, p_191130_2_);
/*  763 */           p_191130_4_ = p_191130_4_.rotateY();
/*      */           
/*  765 */           if (i != p_191130_7_ || j != p_191130_8_ || enumfacing != p_191130_4_)
/*      */           {
/*  767 */             func_191131_b(p_191130_1_, p_191130_2_);
/*      */           }
/*      */         }
/*  770 */         else if (WoodlandMansionPieces.Grid.func_191109_a(p_191130_3_, i + p_191130_4_.getFrontOffsetX(), j + p_191130_4_.getFrontOffsetZ()) && WoodlandMansionPieces.Grid.func_191109_a(p_191130_3_, i + p_191130_4_.getFrontOffsetX() + p_191130_4_.rotateYCCW().getFrontOffsetX(), j + p_191130_4_.getFrontOffsetZ() + p_191130_4_.rotateYCCW().getFrontOffsetZ()))
/*      */         {
/*  772 */           func_191126_d(p_191130_1_, p_191130_2_);
/*  773 */           i += p_191130_4_.getFrontOffsetX();
/*  774 */           j += p_191130_4_.getFrontOffsetZ();
/*  775 */           p_191130_4_ = p_191130_4_.rotateYCCW();
/*      */         }
/*      */         else
/*      */         {
/*  779 */           i += p_191130_4_.getFrontOffsetX();
/*  780 */           j += p_191130_4_.getFrontOffsetZ();
/*      */           
/*  782 */           if (i != p_191130_7_ || j != p_191130_8_ || enumfacing != p_191130_4_)
/*      */           {
/*  784 */             func_191131_b(p_191130_1_, p_191130_2_);
/*      */           }
/*      */         }
/*      */       
/*  788 */       } while (i != p_191130_7_ || j != p_191130_8_ || enumfacing != p_191130_4_);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void func_191123_a(List<WoodlandMansionPieces.MansionTemplate> p_191123_1_, BlockPos p_191123_2_, Rotation p_191123_3_, WoodlandMansionPieces.SimpleGrid p_191123_4_, @Nullable WoodlandMansionPieces.SimpleGrid p_191123_5_) {
/*  797 */       for (int i = 0; i < p_191123_4_.field_191150_c; i++) {
/*      */         
/*  799 */         for (int j = 0; j < p_191123_4_.field_191149_b; j++) {
/*      */           
/*  801 */           BlockPos lvt_8_3_ = p_191123_2_.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 8 + (i - this.field_191137_d) * 8);
/*  802 */           lvt_8_3_ = lvt_8_3_.offset(p_191123_3_.rotate(EnumFacing.EAST), (j - this.field_191136_c) * 8);
/*  803 */           boolean flag = (p_191123_5_ != null && WoodlandMansionPieces.Grid.func_191109_a(p_191123_5_, j, i));
/*      */           
/*  805 */           if (WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j, i) && !flag) {
/*      */             
/*  807 */             p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof", lvt_8_3_.up(3), p_191123_3_));
/*      */             
/*  809 */             if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j + 1, i)) {
/*      */               
/*  811 */               BlockPos blockpos1 = lvt_8_3_.offset(p_191123_3_.rotate(EnumFacing.EAST), 6);
/*  812 */               p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_front", blockpos1, p_191123_3_));
/*      */             } 
/*      */             
/*  815 */             if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j - 1, i)) {
/*      */               
/*  817 */               BlockPos blockpos5 = lvt_8_3_.offset(p_191123_3_.rotate(EnumFacing.EAST), 0);
/*  818 */               blockpos5 = blockpos5.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 7);
/*  819 */               p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_front", blockpos5, p_191123_3_.add(Rotation.CLOCKWISE_180)));
/*      */             } 
/*      */             
/*  822 */             if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j, i - 1)) {
/*      */               
/*  824 */               BlockPos blockpos6 = lvt_8_3_.offset(p_191123_3_.rotate(EnumFacing.WEST), 1);
/*  825 */               p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_front", blockpos6, p_191123_3_.add(Rotation.COUNTERCLOCKWISE_90)));
/*      */             } 
/*      */             
/*  828 */             if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j, i + 1)) {
/*      */               
/*  830 */               BlockPos blockpos7 = lvt_8_3_.offset(p_191123_3_.rotate(EnumFacing.EAST), 6);
/*  831 */               blockpos7 = blockpos7.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 6);
/*  832 */               p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_front", blockpos7, p_191123_3_.add(Rotation.CLOCKWISE_90)));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  838 */       if (p_191123_5_ != null)
/*      */       {
/*  840 */         for (int k = 0; k < p_191123_4_.field_191150_c; k++) {
/*      */           
/*  842 */           for (int i1 = 0; i1 < p_191123_4_.field_191149_b; i1++) {
/*      */             
/*  844 */             BlockPos blockpos3 = p_191123_2_.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 8 + (k - this.field_191137_d) * 8);
/*  845 */             blockpos3 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.EAST), (i1 - this.field_191136_c) * 8);
/*  846 */             boolean flag1 = WoodlandMansionPieces.Grid.func_191109_a(p_191123_5_, i1, k);
/*      */             
/*  848 */             if (WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1, k) && flag1) {
/*      */               
/*  850 */               if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1 + 1, k)) {
/*      */                 
/*  852 */                 BlockPos blockpos8 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.EAST), 7);
/*  853 */                 p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall", blockpos8, p_191123_3_));
/*      */               } 
/*      */               
/*  856 */               if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1 - 1, k)) {
/*      */                 
/*  858 */                 BlockPos blockpos9 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.WEST), 1);
/*  859 */                 blockpos9 = blockpos9.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 6);
/*  860 */                 p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall", blockpos9, p_191123_3_.add(Rotation.CLOCKWISE_180)));
/*      */               } 
/*      */               
/*  863 */               if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1, k - 1)) {
/*      */                 
/*  865 */                 BlockPos blockpos10 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.WEST), 0);
/*  866 */                 blockpos10 = blockpos10.offset(p_191123_3_.rotate(EnumFacing.NORTH), 1);
/*  867 */                 p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall", blockpos10, p_191123_3_.add(Rotation.COUNTERCLOCKWISE_90)));
/*      */               } 
/*      */               
/*  870 */               if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1, k + 1)) {
/*      */                 
/*  872 */                 BlockPos blockpos11 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.EAST), 6);
/*  873 */                 blockpos11 = blockpos11.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 7);
/*  874 */                 p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall", blockpos11, p_191123_3_.add(Rotation.CLOCKWISE_90)));
/*      */               } 
/*      */               
/*  877 */               if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1 + 1, k)) {
/*      */                 
/*  879 */                 if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1, k - 1)) {
/*      */                   
/*  881 */                   BlockPos blockpos12 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.EAST), 7);
/*  882 */                   blockpos12 = blockpos12.offset(p_191123_3_.rotate(EnumFacing.NORTH), 2);
/*  883 */                   p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall_corner", blockpos12, p_191123_3_));
/*      */                 } 
/*      */                 
/*  886 */                 if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1, k + 1)) {
/*      */                   
/*  888 */                   BlockPos blockpos13 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.EAST), 8);
/*  889 */                   blockpos13 = blockpos13.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 7);
/*  890 */                   p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall_corner", blockpos13, p_191123_3_.add(Rotation.CLOCKWISE_90)));
/*      */                 } 
/*      */               } 
/*      */               
/*  894 */               if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1 - 1, k)) {
/*      */                 
/*  896 */                 if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1, k - 1)) {
/*      */                   
/*  898 */                   BlockPos blockpos14 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.WEST), 2);
/*  899 */                   blockpos14 = blockpos14.offset(p_191123_3_.rotate(EnumFacing.NORTH), 1);
/*  900 */                   p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall_corner", blockpos14, p_191123_3_.add(Rotation.COUNTERCLOCKWISE_90)));
/*      */                 } 
/*      */                 
/*  903 */                 if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, i1, k + 1)) {
/*      */                   
/*  905 */                   BlockPos blockpos15 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.WEST), 1);
/*  906 */                   blockpos15 = blockpos15.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 8);
/*  907 */                   p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "small_wall_corner", blockpos15, p_191123_3_.add(Rotation.CLOCKWISE_180)));
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*  915 */       for (int l = 0; l < p_191123_4_.field_191150_c; l++) {
/*      */         
/*  917 */         for (int j1 = 0; j1 < p_191123_4_.field_191149_b; j1++) {
/*      */           
/*  919 */           BlockPos blockpos4 = p_191123_2_.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 8 + (l - this.field_191137_d) * 8);
/*  920 */           blockpos4 = blockpos4.offset(p_191123_3_.rotate(EnumFacing.EAST), (j1 - this.field_191136_c) * 8);
/*  921 */           boolean flag2 = (p_191123_5_ != null && WoodlandMansionPieces.Grid.func_191109_a(p_191123_5_, j1, l));
/*      */           
/*  923 */           if (WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1, l) && !flag2) {
/*      */             
/*  925 */             if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1 + 1, l)) {
/*      */               
/*  927 */               BlockPos blockpos16 = blockpos4.offset(p_191123_3_.rotate(EnumFacing.EAST), 6);
/*      */               
/*  929 */               if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1, l + 1)) {
/*      */                 
/*  931 */                 BlockPos blockpos2 = blockpos16.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 6);
/*  932 */                 p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_corner", blockpos2, p_191123_3_));
/*      */               }
/*  934 */               else if (WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1 + 1, l + 1)) {
/*      */                 
/*  936 */                 BlockPos blockpos18 = blockpos16.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 5);
/*  937 */                 p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_inner_corner", blockpos18, p_191123_3_));
/*      */               } 
/*      */               
/*  940 */               if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1, l - 1)) {
/*      */                 
/*  942 */                 p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_corner", blockpos16, p_191123_3_.add(Rotation.COUNTERCLOCKWISE_90)));
/*      */               }
/*  944 */               else if (WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1 + 1, l - 1)) {
/*      */                 
/*  946 */                 BlockPos blockpos19 = blockpos4.offset(p_191123_3_.rotate(EnumFacing.EAST), 9);
/*  947 */                 blockpos19 = blockpos19.offset(p_191123_3_.rotate(EnumFacing.NORTH), 2);
/*  948 */                 p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_inner_corner", blockpos19, p_191123_3_.add(Rotation.CLOCKWISE_90)));
/*      */               } 
/*      */             } 
/*      */             
/*  952 */             if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1 - 1, l)) {
/*      */               
/*  954 */               BlockPos blockpos17 = blockpos4.offset(p_191123_3_.rotate(EnumFacing.EAST), 0);
/*  955 */               blockpos17 = blockpos17.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 0);
/*      */               
/*  957 */               if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1, l + 1)) {
/*      */                 
/*  959 */                 BlockPos blockpos20 = blockpos17.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 6);
/*  960 */                 p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_corner", blockpos20, p_191123_3_.add(Rotation.CLOCKWISE_90)));
/*      */               }
/*  962 */               else if (WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1 - 1, l + 1)) {
/*      */                 
/*  964 */                 BlockPos blockpos21 = blockpos17.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 8);
/*  965 */                 blockpos21 = blockpos21.offset(p_191123_3_.rotate(EnumFacing.WEST), 3);
/*  966 */                 p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_inner_corner", blockpos21, p_191123_3_.add(Rotation.COUNTERCLOCKWISE_90)));
/*      */               } 
/*      */               
/*  969 */               if (!WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1, l - 1)) {
/*      */                 
/*  971 */                 p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_corner", blockpos17, p_191123_3_.add(Rotation.CLOCKWISE_180)));
/*      */               }
/*  973 */               else if (WoodlandMansionPieces.Grid.func_191109_a(p_191123_4_, j1 - 1, l - 1)) {
/*      */                 
/*  975 */                 BlockPos blockpos22 = blockpos17.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 1);
/*  976 */                 p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "roof_inner_corner", blockpos22, p_191123_3_.add(Rotation.CLOCKWISE_180)));
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void func_191133_a(List<WoodlandMansionPieces.MansionTemplate> p_191133_1_, WoodlandMansionPieces.PlacementData p_191133_2_) {
/*  986 */       EnumFacing enumfacing = p_191133_2_.field_191138_a.rotate(EnumFacing.WEST);
/*  987 */       p_191133_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "entrance", p_191133_2_.field_191139_b.offset(enumfacing, 9), p_191133_2_.field_191138_a));
/*  988 */       p_191133_2_.field_191139_b = p_191133_2_.field_191139_b.offset(p_191133_2_.field_191138_a.rotate(EnumFacing.SOUTH), 16);
/*      */     }
/*      */ 
/*      */     
/*      */     private void func_191131_b(List<WoodlandMansionPieces.MansionTemplate> p_191131_1_, WoodlandMansionPieces.PlacementData p_191131_2_) {
/*  993 */       p_191131_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191131_2_.field_191140_c, p_191131_2_.field_191139_b.offset(p_191131_2_.field_191138_a.rotate(EnumFacing.EAST), 7), p_191131_2_.field_191138_a));
/*  994 */       p_191131_2_.field_191139_b = p_191131_2_.field_191139_b.offset(p_191131_2_.field_191138_a.rotate(EnumFacing.SOUTH), 8);
/*      */     }
/*      */ 
/*      */     
/*      */     private void func_191124_c(List<WoodlandMansionPieces.MansionTemplate> p_191124_1_, WoodlandMansionPieces.PlacementData p_191124_2_) {
/*  999 */       p_191124_2_.field_191139_b = p_191124_2_.field_191139_b.offset(p_191124_2_.field_191138_a.rotate(EnumFacing.SOUTH), -1);
/* 1000 */       p_191124_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, "wall_corner", p_191124_2_.field_191139_b, p_191124_2_.field_191138_a));
/* 1001 */       p_191124_2_.field_191139_b = p_191124_2_.field_191139_b.offset(p_191124_2_.field_191138_a.rotate(EnumFacing.SOUTH), -7);
/* 1002 */       p_191124_2_.field_191139_b = p_191124_2_.field_191139_b.offset(p_191124_2_.field_191138_a.rotate(EnumFacing.WEST), -6);
/* 1003 */       p_191124_2_.field_191138_a = p_191124_2_.field_191138_a.add(Rotation.CLOCKWISE_90);
/*      */     }
/*      */ 
/*      */     
/*      */     private void func_191126_d(List<WoodlandMansionPieces.MansionTemplate> p_191126_1_, WoodlandMansionPieces.PlacementData p_191126_2_) {
/* 1008 */       p_191126_2_.field_191139_b = p_191126_2_.field_191139_b.offset(p_191126_2_.field_191138_a.rotate(EnumFacing.SOUTH), 6);
/* 1009 */       p_191126_2_.field_191139_b = p_191126_2_.field_191139_b.offset(p_191126_2_.field_191138_a.rotate(EnumFacing.EAST), 8);
/* 1010 */       p_191126_2_.field_191138_a = p_191126_2_.field_191138_a.add(Rotation.COUNTERCLOCKWISE_90);
/*      */     }
/*      */ 
/*      */     
/*      */     private void func_191129_a(List<WoodlandMansionPieces.MansionTemplate> p_191129_1_, BlockPos p_191129_2_, Rotation p_191129_3_, EnumFacing p_191129_4_, WoodlandMansionPieces.RoomCollection p_191129_5_) {
/* 1015 */       Rotation rotation = Rotation.NONE;
/* 1016 */       String s = p_191129_5_.func_191104_a(this.field_191135_b);
/*      */       
/* 1018 */       if (p_191129_4_ != EnumFacing.EAST)
/*      */       {
/* 1020 */         if (p_191129_4_ == EnumFacing.NORTH) {
/*      */           
/* 1022 */           rotation = rotation.add(Rotation.COUNTERCLOCKWISE_90);
/*      */         }
/* 1024 */         else if (p_191129_4_ == EnumFacing.WEST) {
/*      */           
/* 1026 */           rotation = rotation.add(Rotation.CLOCKWISE_180);
/*      */         }
/* 1028 */         else if (p_191129_4_ == EnumFacing.SOUTH) {
/*      */           
/* 1030 */           rotation = rotation.add(Rotation.CLOCKWISE_90);
/*      */         }
/*      */         else {
/*      */           
/* 1034 */           s = p_191129_5_.func_191099_b(this.field_191135_b);
/*      */         } 
/*      */       }
/*      */       
/* 1038 */       BlockPos blockpos = Template.func_191157_a(new BlockPos(1, 0, 0), Mirror.NONE, rotation, 7, 7);
/* 1039 */       rotation = rotation.add(p_191129_3_);
/* 1040 */       blockpos = blockpos.func_190942_a(p_191129_3_);
/* 1041 */       BlockPos blockpos1 = p_191129_2_.add(blockpos.getX(), 0, blockpos.getZ());
/* 1042 */       p_191129_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, s, blockpos1, rotation));
/*      */     }
/*      */ 
/*      */     
/*      */     private void func_191132_a(List<WoodlandMansionPieces.MansionTemplate> p_191132_1_, BlockPos p_191132_2_, Rotation p_191132_3_, EnumFacing p_191132_4_, EnumFacing p_191132_5_, WoodlandMansionPieces.RoomCollection p_191132_6_, boolean p_191132_7_) {
/* 1047 */       if (p_191132_5_ == EnumFacing.EAST && p_191132_4_ == EnumFacing.SOUTH) {
/*      */         
/* 1049 */         BlockPos blockpos13 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 1);
/* 1050 */         p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos13, p_191132_3_));
/*      */       }
/* 1052 */       else if (p_191132_5_ == EnumFacing.EAST && p_191132_4_ == EnumFacing.NORTH) {
/*      */         
/* 1054 */         BlockPos blockpos12 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 1);
/* 1055 */         blockpos12 = blockpos12.offset(p_191132_3_.rotate(EnumFacing.SOUTH), 6);
/* 1056 */         p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos12, p_191132_3_, Mirror.LEFT_RIGHT));
/*      */       }
/* 1058 */       else if (p_191132_5_ == EnumFacing.WEST && p_191132_4_ == EnumFacing.NORTH) {
/*      */         
/* 1060 */         BlockPos blockpos11 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 7);
/* 1061 */         blockpos11 = blockpos11.offset(p_191132_3_.rotate(EnumFacing.SOUTH), 6);
/* 1062 */         p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos11, p_191132_3_.add(Rotation.CLOCKWISE_180)));
/*      */       }
/* 1064 */       else if (p_191132_5_ == EnumFacing.WEST && p_191132_4_ == EnumFacing.SOUTH) {
/*      */         
/* 1066 */         BlockPos blockpos10 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 7);
/* 1067 */         p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos10, p_191132_3_, Mirror.FRONT_BACK));
/*      */       }
/* 1069 */       else if (p_191132_5_ == EnumFacing.SOUTH && p_191132_4_ == EnumFacing.EAST) {
/*      */         
/* 1071 */         BlockPos blockpos9 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 1);
/* 1072 */         p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos9, p_191132_3_.add(Rotation.CLOCKWISE_90), Mirror.LEFT_RIGHT));
/*      */       }
/* 1074 */       else if (p_191132_5_ == EnumFacing.SOUTH && p_191132_4_ == EnumFacing.WEST) {
/*      */         
/* 1076 */         BlockPos blockpos8 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 7);
/* 1077 */         p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos8, p_191132_3_.add(Rotation.CLOCKWISE_90)));
/*      */       }
/* 1079 */       else if (p_191132_5_ == EnumFacing.NORTH && p_191132_4_ == EnumFacing.WEST) {
/*      */         
/* 1081 */         BlockPos blockpos7 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 7);
/* 1082 */         blockpos7 = blockpos7.offset(p_191132_3_.rotate(EnumFacing.SOUTH), 6);
/* 1083 */         p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos7, p_191132_3_.add(Rotation.CLOCKWISE_90), Mirror.FRONT_BACK));
/*      */       }
/* 1085 */       else if (p_191132_5_ == EnumFacing.NORTH && p_191132_4_ == EnumFacing.EAST) {
/*      */         
/* 1087 */         BlockPos blockpos6 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 1);
/* 1088 */         blockpos6 = blockpos6.offset(p_191132_3_.rotate(EnumFacing.SOUTH), 6);
/* 1089 */         p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191100_a(this.field_191135_b, p_191132_7_), blockpos6, p_191132_3_.add(Rotation.COUNTERCLOCKWISE_90)));
/*      */       }
/* 1091 */       else if (p_191132_5_ == EnumFacing.SOUTH && p_191132_4_ == EnumFacing.NORTH) {
/*      */         
/* 1093 */         BlockPos blockpos5 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 1);
/* 1094 */         blockpos5 = blockpos5.offset(p_191132_3_.rotate(EnumFacing.NORTH), 8);
/* 1095 */         p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191098_b(this.field_191135_b, p_191132_7_), blockpos5, p_191132_3_));
/*      */       }
/* 1097 */       else if (p_191132_5_ == EnumFacing.NORTH && p_191132_4_ == EnumFacing.SOUTH) {
/*      */         
/* 1099 */         BlockPos blockpos4 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 7);
/* 1100 */         blockpos4 = blockpos4.offset(p_191132_3_.rotate(EnumFacing.SOUTH), 14);
/* 1101 */         p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191098_b(this.field_191135_b, p_191132_7_), blockpos4, p_191132_3_.add(Rotation.CLOCKWISE_180)));
/*      */       }
/* 1103 */       else if (p_191132_5_ == EnumFacing.WEST && p_191132_4_ == EnumFacing.EAST) {
/*      */         
/* 1105 */         BlockPos blockpos3 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 15);
/* 1106 */         p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191098_b(this.field_191135_b, p_191132_7_), blockpos3, p_191132_3_.add(Rotation.CLOCKWISE_90)));
/*      */       }
/* 1108 */       else if (p_191132_5_ == EnumFacing.EAST && p_191132_4_ == EnumFacing.WEST) {
/*      */         
/* 1110 */         BlockPos blockpos2 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.WEST), 7);
/* 1111 */         blockpos2 = blockpos2.offset(p_191132_3_.rotate(EnumFacing.SOUTH), 6);
/* 1112 */         p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191098_b(this.field_191135_b, p_191132_7_), blockpos2, p_191132_3_.add(Rotation.COUNTERCLOCKWISE_90)));
/*      */       }
/* 1114 */       else if (p_191132_5_ == EnumFacing.UP && p_191132_4_ == EnumFacing.EAST) {
/*      */         
/* 1116 */         BlockPos blockpos1 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 15);
/* 1117 */         p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191102_c(this.field_191135_b), blockpos1, p_191132_3_.add(Rotation.CLOCKWISE_90)));
/*      */       }
/* 1119 */       else if (p_191132_5_ == EnumFacing.UP && p_191132_4_ == EnumFacing.SOUTH) {
/*      */         
/* 1121 */         BlockPos blockpos = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 1);
/* 1122 */         blockpos = blockpos.offset(p_191132_3_.rotate(EnumFacing.NORTH), 0);
/* 1123 */         p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191132_6_.func_191102_c(this.field_191135_b), blockpos, p_191132_3_));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void func_191127_a(List<WoodlandMansionPieces.MansionTemplate> p_191127_1_, BlockPos p_191127_2_, Rotation p_191127_3_, EnumFacing p_191127_4_, EnumFacing p_191127_5_, WoodlandMansionPieces.RoomCollection p_191127_6_) {
/* 1129 */       int i = 0;
/* 1130 */       int j = 0;
/* 1131 */       Rotation rotation = p_191127_3_;
/* 1132 */       Mirror mirror = Mirror.NONE;
/*      */       
/* 1134 */       if (p_191127_5_ == EnumFacing.EAST && p_191127_4_ == EnumFacing.SOUTH) {
/*      */         
/* 1136 */         i = -7;
/*      */       }
/* 1138 */       else if (p_191127_5_ == EnumFacing.EAST && p_191127_4_ == EnumFacing.NORTH) {
/*      */         
/* 1140 */         i = -7;
/* 1141 */         j = 6;
/* 1142 */         mirror = Mirror.LEFT_RIGHT;
/*      */       }
/* 1144 */       else if (p_191127_5_ == EnumFacing.NORTH && p_191127_4_ == EnumFacing.EAST) {
/*      */         
/* 1146 */         i = 1;
/* 1147 */         j = 14;
/* 1148 */         rotation = p_191127_3_.add(Rotation.COUNTERCLOCKWISE_90);
/*      */       }
/* 1150 */       else if (p_191127_5_ == EnumFacing.NORTH && p_191127_4_ == EnumFacing.WEST) {
/*      */         
/* 1152 */         i = 7;
/* 1153 */         j = 14;
/* 1154 */         rotation = p_191127_3_.add(Rotation.COUNTERCLOCKWISE_90);
/* 1155 */         mirror = Mirror.LEFT_RIGHT;
/*      */       }
/* 1157 */       else if (p_191127_5_ == EnumFacing.SOUTH && p_191127_4_ == EnumFacing.WEST) {
/*      */         
/* 1159 */         i = 7;
/* 1160 */         j = -8;
/* 1161 */         rotation = p_191127_3_.add(Rotation.CLOCKWISE_90);
/*      */       }
/* 1163 */       else if (p_191127_5_ == EnumFacing.SOUTH && p_191127_4_ == EnumFacing.EAST) {
/*      */         
/* 1165 */         i = 1;
/* 1166 */         j = -8;
/* 1167 */         rotation = p_191127_3_.add(Rotation.CLOCKWISE_90);
/* 1168 */         mirror = Mirror.LEFT_RIGHT;
/*      */       }
/* 1170 */       else if (p_191127_5_ == EnumFacing.WEST && p_191127_4_ == EnumFacing.NORTH) {
/*      */         
/* 1172 */         i = 15;
/* 1173 */         j = 6;
/* 1174 */         rotation = p_191127_3_.add(Rotation.CLOCKWISE_180);
/*      */       }
/* 1176 */       else if (p_191127_5_ == EnumFacing.WEST && p_191127_4_ == EnumFacing.SOUTH) {
/*      */         
/* 1178 */         i = 15;
/* 1179 */         mirror = Mirror.FRONT_BACK;
/*      */       } 
/*      */       
/* 1182 */       BlockPos blockpos = p_191127_2_.offset(p_191127_3_.rotate(EnumFacing.EAST), i);
/* 1183 */       blockpos = blockpos.offset(p_191127_3_.rotate(EnumFacing.SOUTH), j);
/* 1184 */       p_191127_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191127_6_.func_191101_d(this.field_191135_b), blockpos, rotation, mirror));
/*      */     }
/*      */ 
/*      */     
/*      */     private void func_191128_a(List<WoodlandMansionPieces.MansionTemplate> p_191128_1_, BlockPos p_191128_2_, Rotation p_191128_3_, WoodlandMansionPieces.RoomCollection p_191128_4_) {
/* 1189 */       BlockPos blockpos = p_191128_2_.offset(p_191128_3_.rotate(EnumFacing.EAST), 1);
/* 1190 */       p_191128_1_.add(new WoodlandMansionPieces.MansionTemplate(this.field_191134_a, p_191128_4_.func_191103_e(this.field_191135_b), blockpos, p_191128_3_, Mirror.NONE));
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class RoomCollection
/*      */   {
/*      */     private RoomCollection() {}
/*      */     
/*      */     public abstract String func_191104_a(Random param1Random);
/*      */     
/*      */     public abstract String func_191099_b(Random param1Random);
/*      */     
/*      */     public abstract String func_191100_a(Random param1Random, boolean param1Boolean);
/*      */     
/*      */     public abstract String func_191098_b(Random param1Random, boolean param1Boolean);
/*      */     
/*      */     public abstract String func_191102_c(Random param1Random);
/*      */     
/*      */     public abstract String func_191101_d(Random param1Random);
/*      */     
/*      */     public abstract String func_191103_e(Random param1Random);
/*      */   }
/*      */   
/*      */   static class SecondFloor
/*      */     extends RoomCollection
/*      */   {
/*      */     private SecondFloor() {
/* 1217 */       super(null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String func_191104_a(Random p_191104_1_) {
/* 1223 */       return "1x1_b" + (p_191104_1_.nextInt(4) + 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public String func_191099_b(Random p_191099_1_) {
/* 1228 */       return "1x1_as" + (p_191099_1_.nextInt(4) + 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public String func_191100_a(Random p_191100_1_, boolean p_191100_2_) {
/* 1233 */       return p_191100_2_ ? "1x2_c_stairs" : ("1x2_c" + (p_191100_1_.nextInt(4) + 1));
/*      */     }
/*      */ 
/*      */     
/*      */     public String func_191098_b(Random p_191098_1_, boolean p_191098_2_) {
/* 1238 */       return p_191098_2_ ? "1x2_d_stairs" : ("1x2_d" + (p_191098_1_.nextInt(5) + 1));
/*      */     }
/*      */ 
/*      */     
/*      */     public String func_191102_c(Random p_191102_1_) {
/* 1243 */       return "1x2_se" + (p_191102_1_.nextInt(1) + 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public String func_191101_d(Random p_191101_1_) {
/* 1248 */       return "2x2_b" + (p_191101_1_.nextInt(5) + 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public String func_191103_e(Random p_191103_1_) {
/* 1253 */       return "2x2_s1";
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class SimpleGrid
/*      */   {
/*      */     private final int[][] field_191148_a;
/*      */     private final int field_191149_b;
/*      */     private final int field_191150_c;
/*      */     private final int field_191151_d;
/*      */     
/*      */     public SimpleGrid(int p_i47358_1_, int p_i47358_2_, int p_i47358_3_) {
/* 1266 */       this.field_191149_b = p_i47358_1_;
/* 1267 */       this.field_191150_c = p_i47358_2_;
/* 1268 */       this.field_191151_d = p_i47358_3_;
/* 1269 */       this.field_191148_a = new int[p_i47358_1_][p_i47358_2_];
/*      */     }
/*      */ 
/*      */     
/*      */     public void func_191144_a(int p_191144_1_, int p_191144_2_, int p_191144_3_) {
/* 1274 */       if (p_191144_1_ >= 0 && p_191144_1_ < this.field_191149_b && p_191144_2_ >= 0 && p_191144_2_ < this.field_191150_c)
/*      */       {
/* 1276 */         this.field_191148_a[p_191144_1_][p_191144_2_] = p_191144_3_;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void func_191142_a(int p_191142_1_, int p_191142_2_, int p_191142_3_, int p_191142_4_, int p_191142_5_) {
/* 1282 */       for (int i = p_191142_2_; i <= p_191142_4_; i++) {
/*      */         
/* 1284 */         for (int j = p_191142_1_; j <= p_191142_3_; j++)
/*      */         {
/* 1286 */           func_191144_a(j, i, p_191142_5_);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int func_191145_a(int p_191145_1_, int p_191145_2_) {
/* 1293 */       return (p_191145_1_ >= 0 && p_191145_1_ < this.field_191149_b && p_191145_2_ >= 0 && p_191145_2_ < this.field_191150_c) ? this.field_191148_a[p_191145_1_][p_191145_2_] : this.field_191151_d;
/*      */     }
/*      */ 
/*      */     
/*      */     public void func_191141_a(int p_191141_1_, int p_191141_2_, int p_191141_3_, int p_191141_4_) {
/* 1298 */       if (func_191145_a(p_191141_1_, p_191141_2_) == p_191141_3_)
/*      */       {
/* 1300 */         func_191144_a(p_191141_1_, p_191141_2_, p_191141_4_);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean func_191147_b(int p_191147_1_, int p_191147_2_, int p_191147_3_) {
/* 1306 */       return !(func_191145_a(p_191147_1_ - 1, p_191147_2_) != p_191147_3_ && func_191145_a(p_191147_1_ + 1, p_191147_2_) != p_191147_3_ && func_191145_a(p_191147_1_, p_191147_2_ + 1) != p_191147_3_ && func_191145_a(p_191147_1_, p_191147_2_ - 1) != p_191147_3_);
/*      */     }
/*      */   }
/*      */   
/*      */   static class ThirdFloor extends SecondFloor {
/*      */     private ThirdFloor() {
/* 1312 */       super(null, null);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\WoodlandMansionPieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */