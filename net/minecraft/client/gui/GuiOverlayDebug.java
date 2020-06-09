/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.ClientBrandRetriever;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import optifine.Reflector;
/*     */ import org.lwjgl.opengl.Display;
/*     */ 
/*     */ public class GuiOverlayDebug
/*     */   extends Gui
/*     */ {
/*     */   private final Minecraft mc;
/*     */   private final FontRenderer fontRenderer;
/*     */   
/*     */   public GuiOverlayDebug(Minecraft mc) {
/*  38 */     this.mc = mc;
/*  39 */     this.fontRenderer = mc.fontRendererObj;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderDebugInfo(ScaledResolution scaledResolutionIn) {
/*  44 */     this.mc.mcProfiler.startSection("debug");
/*  45 */     GlStateManager.pushMatrix();
/*  46 */     renderDebugInfoLeft();
/*  47 */     renderDebugInfoRight(scaledResolutionIn);
/*  48 */     GlStateManager.popMatrix();
/*     */     
/*  50 */     if (this.mc.gameSettings.showLagometer)
/*     */     {
/*  52 */       renderLagometer();
/*     */     }
/*     */     
/*  55 */     this.mc.mcProfiler.endSection();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderDebugInfoLeft() {
/*  60 */     List<String> list = call();
/*  61 */     list.add("");
/*  62 */     list.add("Debug: Pie [shift]: " + (this.mc.gameSettings.showDebugProfilerChart ? "visible" : "hidden") + " FPS [alt]: " + (this.mc.gameSettings.showLagometer ? "visible" : "hidden"));
/*  63 */     list.add("For help: press F3 + Q");
/*     */     
/*  65 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/*  67 */       String s = list.get(i);
/*     */       
/*  69 */       if (!Strings.isNullOrEmpty(s)) {
/*     */         
/*  71 */         int j = this.fontRenderer.FONT_HEIGHT;
/*  72 */         int k = this.fontRenderer.getStringWidth(s);
/*  73 */         int l = 2;
/*  74 */         int i1 = 2 + j * i;
/*  75 */         drawRect(1, i1 - 1, 2 + k + 1, i1 + j - 1, -1873784752);
/*  76 */         this.fontRenderer.drawString(s, 2, i1, 14737632);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderDebugInfoRight(ScaledResolution scaledRes) {
/*  83 */     List<String> list = getDebugInfoRight();
/*     */     
/*  85 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/*  87 */       String s = list.get(i);
/*     */       
/*  89 */       if (!Strings.isNullOrEmpty(s)) {
/*     */         
/*  91 */         int j = this.fontRenderer.FONT_HEIGHT;
/*  92 */         int k = this.fontRenderer.getStringWidth(s);
/*  93 */         int l = ScaledResolution.getScaledWidth() - 2 - k;
/*  94 */         int i1 = 2 + j * i;
/*  95 */         drawRect(l - 1, i1 - 1, l + k + 1, i1 + j - 1, -1873784752);
/*  96 */         this.fontRenderer.drawString(s, l, i1, 14737632);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> call() {
/* 104 */     BlockPos blockpos = new BlockPos((this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity().getEntityBoundingBox()).minY, (this.mc.getRenderViewEntity()).posZ);
/*     */     
/* 106 */     if (this.mc.isReducedDebug())
/*     */     {
/* 108 */       return Lists.newArrayList((Object[])new String[] { "Minecraft 1.12.2 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.world.getDebugLoadedEntities(), this.mc.world.getProviderName(), "", String.format("Chunk-relative: %d %d %d", new Object[] { Integer.valueOf(blockpos.getX() & 0xF), Integer.valueOf(blockpos.getY() & 0xF), Integer.valueOf(blockpos.getZ() & 0xF) }) });
/*     */     }
/*     */ 
/*     */     
/* 112 */     Entity entity = this.mc.getRenderViewEntity();
/* 113 */     EnumFacing enumfacing = entity.getHorizontalFacing();
/* 114 */     String s = "Invalid";
/*     */     
/* 116 */     switch (enumfacing) {
/*     */       
/*     */       case NORTH:
/* 119 */         s = "Towards negative Z";
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 123 */         s = "Towards positive Z";
/*     */         break;
/*     */       
/*     */       case WEST:
/* 127 */         s = "Towards negative X";
/*     */         break;
/*     */       
/*     */       case EAST:
/* 131 */         s = "Towards positive X";
/*     */         break;
/*     */     } 
/* 134 */     List<String> list = Lists.newArrayList((Object[])new String[] { "Minecraft 1.12.2 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ("release".equalsIgnoreCase(this.mc.getVersionType()) ? "" : ("/" + this.mc.getVersionType())) + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.world.getDebugLoadedEntities(), this.mc.world.getProviderName(), "", String.format("XYZ: %.3f / %.5f / %.3f", new Object[] { Double.valueOf((this.mc.getRenderViewEntity()).posX), Double.valueOf((this.mc.getRenderViewEntity().getEntityBoundingBox()).minY), Double.valueOf((this.mc.getRenderViewEntity()).posZ) }), String.format("Block: %d %d %d", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) }), String.format("Chunk: %d %d %d in %d %d %d", new Object[] { Integer.valueOf(blockpos.getX() & 0xF), Integer.valueOf(blockpos.getY() & 0xF), Integer.valueOf(blockpos.getZ() & 0xF), Integer.valueOf(blockpos.getX() >> 4), Integer.valueOf(blockpos.getY() >> 4), Integer.valueOf(blockpos.getZ() >> 4) }), String.format("Facing: %s (%s) (%.1f / %.1f)", new Object[] { enumfacing, s, Float.valueOf(MathHelper.wrapDegrees(entity.rotationYaw)), Float.valueOf(MathHelper.wrapDegrees(entity.rotationPitch)) }) });
/*     */     
/* 136 */     if (this.mc.world != null) {
/*     */       
/* 138 */       Chunk chunk = this.mc.world.getChunkFromBlockCoords(blockpos);
/*     */       
/* 140 */       if (this.mc.world.isBlockLoaded(blockpos) && blockpos.getY() >= 0 && blockpos.getY() < 256) {
/*     */         
/* 142 */         if (!chunk.isEmpty())
/*     */         {
/* 144 */           list.add("Biome: " + chunk.getBiome(blockpos, this.mc.world.getBiomeProvider()).getBiomeName());
/* 145 */           list.add("Light: " + chunk.getLightSubtracted(blockpos, 0) + " (" + chunk.getLightFor(EnumSkyBlock.SKY, blockpos) + " sky, " + chunk.getLightFor(EnumSkyBlock.BLOCK, blockpos) + " block)");
/* 146 */           DifficultyInstance difficultyinstance = this.mc.world.getDifficultyForLocation(blockpos);
/*     */           
/* 148 */           if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
/*     */             
/* 150 */             EntityPlayerMP entityplayermp = this.mc.getIntegratedServer().getPlayerList().getPlayerByUUID(this.mc.player.getUniqueID());
/*     */             
/* 152 */             if (entityplayermp != null)
/*     */             {
/* 154 */               difficultyinstance = entityplayermp.world.getDifficultyForLocation(new BlockPos((Entity)entityplayermp));
/*     */             }
/*     */           } 
/*     */           
/* 158 */           list.add(String.format("Local Difficulty: %.2f // %.2f (Day %d)", new Object[] { Float.valueOf(difficultyinstance.getAdditionalDifficulty()), Float.valueOf(difficultyinstance.getClampedAdditionalDifficulty()), Long.valueOf(this.mc.world.getWorldTime() / 24000L) }));
/*     */         }
/*     */         else
/*     */         {
/* 162 */           list.add("Waiting for chunk...");
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 167 */         list.add("Outside of world...");
/*     */       } 
/*     */     } 
/*     */     
/* 171 */     if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive())
/*     */     {
/* 173 */       list.add("Shader: " + this.mc.entityRenderer.getShaderGroup().getShaderGroupName());
/*     */     }
/*     */     
/* 176 */     if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
/*     */       
/* 178 */       BlockPos blockpos1 = this.mc.objectMouseOver.getBlockPos();
/* 179 */       list.add(String.format("Looking at: %d %d %d", new Object[] { Integer.valueOf(blockpos1.getX()), Integer.valueOf(blockpos1.getY()), Integer.valueOf(blockpos1.getZ()) }));
/*     */     } 
/*     */     
/* 182 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T extends Comparable<T>> List<String> getDebugInfoRight() {
/* 188 */     long i = Runtime.getRuntime().maxMemory();
/* 189 */     long j = Runtime.getRuntime().totalMemory();
/* 190 */     long k = Runtime.getRuntime().freeMemory();
/* 191 */     long l = j - k;
/* 192 */     List<String> list = Lists.newArrayList((Object[])new String[] { String.format("Java: %s %dbit", new Object[] { System.getProperty("java.version"), Integer.valueOf(this.mc.isJava64bit() ? 64 : 32) }), String.format("Mem: % 2d%% %03d/%03dMB", new Object[] { Long.valueOf(l * 100L / i), Long.valueOf(bytesToMb(l)), Long.valueOf(bytesToMb(i)) }), String.format("Allocated: % 2d%% %03dMB", new Object[] { Long.valueOf(j * 100L / i), Long.valueOf(bytesToMb(j)) }), "", String.format("CPU: %s", new Object[] { OpenGlHelper.getCpu() }), "", String.format("Display: %dx%d (%s)", new Object[] { Integer.valueOf(Display.getWidth()), Integer.valueOf(Display.getHeight()), GlStateManager.glGetString(7936) }), GlStateManager.glGetString(7937), GlStateManager.glGetString(7938) });
/*     */     
/* 194 */     if (Reflector.FMLCommonHandler_getBrandings.exists()) {
/*     */       
/* 196 */       Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/* 197 */       list.add("");
/* 198 */       list.addAll((Collection<? extends String>)Reflector.call(object, Reflector.FMLCommonHandler_getBrandings, new Object[] { Boolean.valueOf(false) }));
/*     */     } 
/*     */     
/* 201 */     if (this.mc.isReducedDebug())
/*     */     {
/* 203 */       return list;
/*     */     }
/*     */ 
/*     */     
/* 207 */     if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
/*     */       
/* 209 */       BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
/* 210 */       IBlockState iblockstate = this.mc.world.getBlockState(blockpos);
/*     */       
/* 212 */       if (this.mc.world.getWorldType() != WorldType.DEBUG_WORLD)
/*     */       {
/* 214 */         iblockstate = iblockstate.getActualState((IBlockAccess)this.mc.world, blockpos);
/*     */       }
/*     */       
/* 217 */       list.add("");
/* 218 */       list.add(String.valueOf(Block.REGISTRY.getNameForObject(iblockstate.getBlock())));
/*     */ 
/*     */ 
/*     */       
/* 222 */       for (UnmodifiableIterator unmodifiableiterator = iblockstate.getProperties().entrySet().iterator(); unmodifiableiterator.hasNext(); list.add(String.valueOf(iproperty.getName()) + ": " + s)) {
/*     */         
/* 224 */         Map.Entry<IProperty<?>, Comparable<?>> entry = (Map.Entry<IProperty<?>, Comparable<?>>)unmodifiableiterator.next();
/* 225 */         IProperty<T> iproperty = (IProperty<T>)entry.getKey();
/* 226 */         Comparable comparable = entry.getValue();
/* 227 */         String s = iproperty.getName(comparable);
/*     */         
/* 229 */         if (Boolean.TRUE.equals(comparable)) {
/*     */           
/* 231 */           s = TextFormatting.GREEN + s;
/*     */         }
/* 233 */         else if (Boolean.FALSE.equals(comparable)) {
/*     */           
/* 235 */           s = TextFormatting.RED + s;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 240 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderLagometer() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private int getFrameColor(int p_181552_1_, int p_181552_2_, int p_181552_3_, int p_181552_4_) {
/* 250 */     return (p_181552_1_ < p_181552_3_) ? blendColors(-16711936, -256, p_181552_1_ / p_181552_3_) : blendColors(-256, -65536, (p_181552_1_ - p_181552_3_) / (p_181552_4_ - p_181552_3_));
/*     */   }
/*     */ 
/*     */   
/*     */   private int blendColors(int p_181553_1_, int p_181553_2_, float p_181553_3_) {
/* 255 */     int i = p_181553_1_ >> 24 & 0xFF;
/* 256 */     int j = p_181553_1_ >> 16 & 0xFF;
/* 257 */     int k = p_181553_1_ >> 8 & 0xFF;
/* 258 */     int l = p_181553_1_ & 0xFF;
/* 259 */     int i1 = p_181553_2_ >> 24 & 0xFF;
/* 260 */     int j1 = p_181553_2_ >> 16 & 0xFF;
/* 261 */     int k1 = p_181553_2_ >> 8 & 0xFF;
/* 262 */     int l1 = p_181553_2_ & 0xFF;
/* 263 */     int i2 = MathHelper.clamp((int)(i + (i1 - i) * p_181553_3_), 0, 255);
/* 264 */     int j2 = MathHelper.clamp((int)(j + (j1 - j) * p_181553_3_), 0, 255);
/* 265 */     int k2 = MathHelper.clamp((int)(k + (k1 - k) * p_181553_3_), 0, 255);
/* 266 */     int l2 = MathHelper.clamp((int)(l + (l1 - l) * p_181553_3_), 0, 255);
/* 267 */     return i2 << 24 | j2 << 16 | k2 << 8 | l2;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long bytesToMb(long bytes) {
/* 272 */     return bytes / 1024L / 1024L;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiOverlayDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */