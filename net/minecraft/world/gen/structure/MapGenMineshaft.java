/*    */ package net.minecraft.world.gen.structure;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.biome.Biome;
/*    */ 
/*    */ 
/*    */ public class MapGenMineshaft
/*    */   extends MapGenStructure
/*    */ {
/* 13 */   private double chance = 0.004D;
/*    */ 
/*    */ 
/*    */   
/*    */   public MapGenMineshaft() {}
/*    */ 
/*    */   
/*    */   public String getStructureName() {
/* 21 */     return "Mineshaft";
/*    */   }
/*    */ 
/*    */   
/*    */   public MapGenMineshaft(Map<String, String> p_i2034_1_) {
/* 26 */     for (Map.Entry<String, String> entry : p_i2034_1_.entrySet()) {
/*    */       
/* 28 */       if (((String)entry.getKey()).equals("chance"))
/*    */       {
/* 30 */         this.chance = MathHelper.getDouble(entry.getValue(), this.chance);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/* 37 */     return (this.rand.nextDouble() < this.chance && this.rand.nextInt(80) < Math.max(Math.abs(chunkX), Math.abs(chunkZ)));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos, boolean p_180706_3_) {
/* 42 */     int i = 1000;
/* 43 */     int j = pos.getX() >> 4;
/* 44 */     int k = pos.getZ() >> 4;
/*    */     
/* 46 */     for (int l = 0; l <= 1000; l++) {
/*    */       
/* 48 */       for (int i1 = -l; i1 <= l; i1++) {
/*    */         
/* 50 */         boolean flag = !(i1 != -l && i1 != l);
/*    */         
/* 52 */         for (int j1 = -l; j1 <= l; j1++) {
/*    */           
/* 54 */           boolean flag1 = !(j1 != -l && j1 != l);
/*    */           
/* 56 */           if (flag || flag1) {
/*    */             
/* 58 */             int k1 = j + i1;
/* 59 */             int l1 = k + j1;
/* 60 */             this.rand.setSeed((k1 ^ l1) ^ worldIn.getSeed());
/* 61 */             this.rand.nextInt();
/*    */             
/* 63 */             if (canSpawnStructureAtCoords(k1, l1) && (!p_180706_3_ || !worldIn.func_190526_b(k1, l1)))
/*    */             {
/* 65 */               return new BlockPos((k1 << 4) + 8, 64, (l1 << 4) + 8);
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 72 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/* 77 */     Biome biome = this.worldObj.getBiome(new BlockPos((chunkX << 4) + 8, 64, (chunkZ << 4) + 8));
/* 78 */     Type mapgenmineshaft$type = (biome instanceof net.minecraft.world.biome.BiomeMesa) ? Type.MESA : Type.NORMAL;
/* 79 */     return new StructureMineshaftStart(this.worldObj, this.rand, chunkX, chunkZ, mapgenmineshaft$type);
/*    */   }
/*    */   
/*    */   public enum Type
/*    */   {
/* 84 */     NORMAL,
/* 85 */     MESA;
/*    */ 
/*    */     
/*    */     public static Type byId(int id) {
/* 89 */       return (id >= 0 && id < (values()).length) ? values()[id] : NORMAL;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\MapGenMineshaft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */