/*     */ package net.minecraft.client.renderer.debug;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Ordering;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class DebugRendererNeighborsUpdate implements DebugRenderer.IDebugRenderer {
/*     */   private final Minecraft field_191554_a;
/*  21 */   private final Map<Long, Map<BlockPos, Integer>> field_191555_b = Maps.newTreeMap((Comparator)Ordering.natural().reverse());
/*     */ 
/*     */   
/*     */   DebugRendererNeighborsUpdate(Minecraft p_i47365_1_) {
/*  25 */     this.field_191554_a = p_i47365_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191553_a(long p_191553_1_, BlockPos p_191553_3_) {
/*  30 */     Map<BlockPos, Integer> map = this.field_191555_b.get(Long.valueOf(p_191553_1_));
/*     */     
/*  32 */     if (map == null) {
/*     */       
/*  34 */       map = Maps.newHashMap();
/*  35 */       this.field_191555_b.put(Long.valueOf(p_191553_1_), map);
/*     */     } 
/*     */     
/*  38 */     Integer integer = map.get(p_191553_3_);
/*     */     
/*  40 */     if (integer == null)
/*     */     {
/*  42 */       integer = Integer.valueOf(0);
/*     */     }
/*     */     
/*  45 */     map.put(p_191553_3_, Integer.valueOf(integer.intValue() + 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float p_190060_1_, long p_190060_2_) {
/*  50 */     long i = this.field_191554_a.world.getTotalWorldTime();
/*  51 */     EntityPlayerSP entityPlayerSP = this.field_191554_a.player;
/*  52 */     double d0 = ((EntityPlayer)entityPlayerSP).lastTickPosX + (((EntityPlayer)entityPlayerSP).posX - ((EntityPlayer)entityPlayerSP).lastTickPosX) * p_190060_1_;
/*  53 */     double d1 = ((EntityPlayer)entityPlayerSP).lastTickPosY + (((EntityPlayer)entityPlayerSP).posY - ((EntityPlayer)entityPlayerSP).lastTickPosY) * p_190060_1_;
/*  54 */     double d2 = ((EntityPlayer)entityPlayerSP).lastTickPosZ + (((EntityPlayer)entityPlayerSP).posZ - ((EntityPlayer)entityPlayerSP).lastTickPosZ) * p_190060_1_;
/*  55 */     World world = this.field_191554_a.player.world;
/*  56 */     GlStateManager.enableBlend();
/*  57 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  58 */     GlStateManager.glLineWidth(2.0F);
/*  59 */     GlStateManager.disableTexture2D();
/*  60 */     GlStateManager.depthMask(false);
/*  61 */     int j = 200;
/*  62 */     double d3 = 0.0025D;
/*  63 */     Set<BlockPos> set = Sets.newHashSet();
/*  64 */     Map<BlockPos, Integer> map = Maps.newHashMap();
/*  65 */     Iterator<Map.Entry<Long, Map<BlockPos, Integer>>> iterator = this.field_191555_b.entrySet().iterator();
/*     */     
/*  67 */     while (iterator.hasNext()) {
/*     */       
/*  69 */       Map.Entry<Long, Map<BlockPos, Integer>> entry = iterator.next();
/*  70 */       Long olong = entry.getKey();
/*  71 */       Map<BlockPos, Integer> map1 = entry.getValue();
/*  72 */       long k = i - olong.longValue();
/*     */       
/*  74 */       if (k > 200L) {
/*     */         
/*  76 */         iterator.remove();
/*     */         
/*     */         continue;
/*     */       } 
/*  80 */       for (Map.Entry<BlockPos, Integer> entry1 : map1.entrySet()) {
/*     */         
/*  82 */         BlockPos blockpos = entry1.getKey();
/*  83 */         Integer integer = entry1.getValue();
/*     */         
/*  85 */         if (set.add(blockpos)) {
/*     */           
/*  87 */           RenderGlobal.drawSelectionBoundingBox((new AxisAlignedBB(BlockPos.ORIGIN)).expandXyz(0.002D).contract(0.0025D * k).offset(blockpos.getX(), blockpos.getY(), blockpos.getZ()).offset(-d0, -d1, -d2), 1.0F, 1.0F, 1.0F, 1.0F);
/*  88 */           map.put(blockpos, integer);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  94 */     for (Map.Entry<BlockPos, Integer> entry2 : map.entrySet()) {
/*     */       
/*  96 */       BlockPos blockpos1 = entry2.getKey();
/*  97 */       Integer integer1 = entry2.getValue();
/*  98 */       DebugRenderer.func_191556_a(String.valueOf(integer1), blockpos1.getX(), blockpos1.getY(), blockpos1.getZ(), p_190060_1_, -1);
/*     */     } 
/*     */     
/* 101 */     GlStateManager.depthMask(true);
/* 102 */     GlStateManager.enableTexture2D();
/* 103 */     GlStateManager.disableBlend();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\debug\DebugRendererNeighborsUpdate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */