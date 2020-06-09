/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.Mirror;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.Rotation;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.ChunkPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*    */ import net.minecraft.world.gen.structure.template.PlacementSettings;
/*    */ import net.minecraft.world.gen.structure.template.Template;
/*    */ import net.minecraft.world.gen.structure.template.TemplateManager;
/*    */ 
/*    */ public class WorldGenFossils
/*    */   extends WorldGenerator {
/* 18 */   private static final ResourceLocation STRUCTURE_SPINE_01 = new ResourceLocation("fossils/fossil_spine_01");
/* 19 */   private static final ResourceLocation STRUCTURE_SPINE_02 = new ResourceLocation("fossils/fossil_spine_02");
/* 20 */   private static final ResourceLocation STRUCTURE_SPINE_03 = new ResourceLocation("fossils/fossil_spine_03");
/* 21 */   private static final ResourceLocation STRUCTURE_SPINE_04 = new ResourceLocation("fossils/fossil_spine_04");
/* 22 */   private static final ResourceLocation STRUCTURE_SPINE_01_COAL = new ResourceLocation("fossils/fossil_spine_01_coal");
/* 23 */   private static final ResourceLocation STRUCTURE_SPINE_02_COAL = new ResourceLocation("fossils/fossil_spine_02_coal");
/* 24 */   private static final ResourceLocation STRUCTURE_SPINE_03_COAL = new ResourceLocation("fossils/fossil_spine_03_coal");
/* 25 */   private static final ResourceLocation STRUCTURE_SPINE_04_COAL = new ResourceLocation("fossils/fossil_spine_04_coal");
/* 26 */   private static final ResourceLocation STRUCTURE_SKULL_01 = new ResourceLocation("fossils/fossil_skull_01");
/* 27 */   private static final ResourceLocation STRUCTURE_SKULL_02 = new ResourceLocation("fossils/fossil_skull_02");
/* 28 */   private static final ResourceLocation STRUCTURE_SKULL_03 = new ResourceLocation("fossils/fossil_skull_03");
/* 29 */   private static final ResourceLocation STRUCTURE_SKULL_04 = new ResourceLocation("fossils/fossil_skull_04");
/* 30 */   private static final ResourceLocation STRUCTURE_SKULL_01_COAL = new ResourceLocation("fossils/fossil_skull_01_coal");
/* 31 */   private static final ResourceLocation STRUCTURE_SKULL_02_COAL = new ResourceLocation("fossils/fossil_skull_02_coal");
/* 32 */   private static final ResourceLocation STRUCTURE_SKULL_03_COAL = new ResourceLocation("fossils/fossil_skull_03_coal");
/* 33 */   private static final ResourceLocation STRUCTURE_SKULL_04_COAL = new ResourceLocation("fossils/fossil_skull_04_coal");
/* 34 */   private static final ResourceLocation[] FOSSILS = new ResourceLocation[] { STRUCTURE_SPINE_01, STRUCTURE_SPINE_02, STRUCTURE_SPINE_03, STRUCTURE_SPINE_04, STRUCTURE_SKULL_01, STRUCTURE_SKULL_02, STRUCTURE_SKULL_03, STRUCTURE_SKULL_04 };
/* 35 */   private static final ResourceLocation[] FOSSILS_COAL = new ResourceLocation[] { STRUCTURE_SPINE_01_COAL, STRUCTURE_SPINE_02_COAL, STRUCTURE_SPINE_03_COAL, STRUCTURE_SPINE_04_COAL, STRUCTURE_SKULL_01_COAL, STRUCTURE_SKULL_02_COAL, STRUCTURE_SKULL_03_COAL, STRUCTURE_SKULL_04_COAL };
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 39 */     Random random = worldIn.getChunkFromBlockCoords(position).getRandomWithSeed(987234911L);
/* 40 */     MinecraftServer minecraftserver = worldIn.getMinecraftServer();
/* 41 */     Rotation[] arotation = Rotation.values();
/* 42 */     Rotation rotation = arotation[random.nextInt(arotation.length)];
/* 43 */     int i = random.nextInt(FOSSILS.length);
/* 44 */     TemplateManager templatemanager = worldIn.getSaveHandler().getStructureTemplateManager();
/* 45 */     Template template = templatemanager.getTemplate(minecraftserver, FOSSILS[i]);
/* 46 */     Template template1 = templatemanager.getTemplate(minecraftserver, FOSSILS_COAL[i]);
/* 47 */     ChunkPos chunkpos = new ChunkPos(position);
/* 48 */     StructureBoundingBox structureboundingbox = new StructureBoundingBox(chunkpos.getXStart(), 0, chunkpos.getZStart(), chunkpos.getXEnd(), 256, chunkpos.getZEnd());
/* 49 */     PlacementSettings placementsettings = (new PlacementSettings()).setRotation(rotation).setBoundingBox(structureboundingbox).setRandom(random);
/* 50 */     BlockPos blockpos = template.transformedSize(rotation);
/* 51 */     int j = random.nextInt(16 - blockpos.getX());
/* 52 */     int k = random.nextInt(16 - blockpos.getZ());
/* 53 */     int l = 256;
/*    */     
/* 55 */     for (int i1 = 0; i1 < blockpos.getX(); i1++) {
/*    */       
/* 57 */       for (int j1 = 0; j1 < blockpos.getX(); j1++)
/*    */       {
/* 59 */         l = Math.min(l, worldIn.getHeight(position.getX() + i1 + j, position.getZ() + j1 + k));
/*    */       }
/*    */     } 
/*    */     
/* 63 */     int k1 = Math.max(l - 15 - random.nextInt(10), 10);
/* 64 */     BlockPos blockpos1 = template.getZeroPositionWithTransform(position.add(j, k1, k), Mirror.NONE, rotation);
/* 65 */     placementsettings.setIntegrity(0.9F);
/* 66 */     template.addBlocksToWorld(worldIn, blockpos1, placementsettings, 20);
/* 67 */     placementsettings.setIntegrity(0.1F);
/* 68 */     template1.addBlocksToWorld(worldIn, blockpos1, placementsettings, 20);
/* 69 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenFossils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */