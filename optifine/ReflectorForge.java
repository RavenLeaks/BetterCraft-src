/*     */ package optifine;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemMap;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ 
/*     */ 
/*     */ public class ReflectorForge
/*     */ {
/*     */   public static void FMLClientHandler_trackBrokenTexture(ResourceLocation p_FMLClientHandler_trackBrokenTexture_0_, String p_FMLClientHandler_trackBrokenTexture_1_) {
/*  22 */     if (!Reflector.FMLClientHandler_trackBrokenTexture.exists()) {
/*     */       
/*  24 */       Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*  25 */       Reflector.call(object, Reflector.FMLClientHandler_trackBrokenTexture, new Object[] { p_FMLClientHandler_trackBrokenTexture_0_, p_FMLClientHandler_trackBrokenTexture_1_ });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void FMLClientHandler_trackMissingTexture(ResourceLocation p_FMLClientHandler_trackMissingTexture_0_) {
/*  31 */     if (!Reflector.FMLClientHandler_trackMissingTexture.exists()) {
/*     */       
/*  33 */       Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*  34 */       Reflector.call(object, Reflector.FMLClientHandler_trackMissingTexture, new Object[] { p_FMLClientHandler_trackMissingTexture_0_ });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void putLaunchBlackboard(String p_putLaunchBlackboard_0_, Object p_putLaunchBlackboard_1_) {
/*  40 */     Map<String, Object> map = (Map)Reflector.getFieldValue(Reflector.Launch_blackboard);
/*     */     
/*  42 */     if (map != null)
/*     */     {
/*  44 */       map.put(p_putLaunchBlackboard_0_, p_putLaunchBlackboard_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean renderFirstPersonHand(RenderGlobal p_renderFirstPersonHand_0_, float p_renderFirstPersonHand_1_, int p_renderFirstPersonHand_2_) {
/*  50 */     return !Reflector.ForgeHooksClient_renderFirstPersonHand.exists() ? false : Reflector.callBoolean(Reflector.ForgeHooksClient_renderFirstPersonHand, new Object[] { p_renderFirstPersonHand_0_, Float.valueOf(p_renderFirstPersonHand_1_), Integer.valueOf(p_renderFirstPersonHand_2_) });
/*     */   }
/*     */ 
/*     */   
/*     */   public static InputStream getOptiFineResourceStream(String p_getOptiFineResourceStream_0_) {
/*  55 */     if (!Reflector.OptiFineClassTransformer_instance.exists())
/*     */     {
/*  57 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  61 */     Object object = Reflector.getFieldValue(Reflector.OptiFineClassTransformer_instance);
/*     */     
/*  63 */     if (object == null)
/*     */     {
/*  65 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  69 */     if (p_getOptiFineResourceStream_0_.startsWith("/"))
/*     */     {
/*  71 */       p_getOptiFineResourceStream_0_ = p_getOptiFineResourceStream_0_.substring(1);
/*     */     }
/*     */     
/*  74 */     byte[] abyte = (byte[])Reflector.call(object, Reflector.OptiFineClassTransformer_getOptiFineResource, new Object[] { p_getOptiFineResourceStream_0_ });
/*     */     
/*  76 */     if (abyte == null)
/*     */     {
/*  78 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  82 */     InputStream inputstream = new ByteArrayInputStream(abyte);
/*  83 */     return inputstream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean blockHasTileEntity(IBlockState p_blockHasTileEntity_0_) {
/*  91 */     Block block = p_blockHasTileEntity_0_.getBlock();
/*  92 */     return !Reflector.ForgeBlock_hasTileEntity.exists() ? block.hasTileEntity() : Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, new Object[] { p_blockHasTileEntity_0_ });
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isItemDamaged(ItemStack p_isItemDamaged_0_) {
/*  97 */     return !Reflector.ForgeItem_showDurabilityBar.exists() ? p_isItemDamaged_0_.isItemDamaged() : Reflector.callBoolean(p_isItemDamaged_0_.getItem(), Reflector.ForgeItem_showDurabilityBar, new Object[] { p_isItemDamaged_0_ });
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean armorHasOverlay(ItemArmor p_armorHasOverlay_0_, ItemStack p_armorHasOverlay_1_) {
/* 102 */     if (Reflector.ForgeItemArmor_hasOverlay.exists())
/*     */     {
/* 104 */       return Reflector.callBoolean(p_armorHasOverlay_0_, Reflector.ForgeItemArmor_hasOverlay, new Object[] { p_armorHasOverlay_1_ });
/*     */     }
/*     */ 
/*     */     
/* 108 */     int i = p_armorHasOverlay_0_.getColor(p_armorHasOverlay_1_);
/* 109 */     return (i != 16777215);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLightValue(IBlockState p_getLightValue_0_, IBlockAccess p_getLightValue_1_, BlockPos p_getLightValue_2_) {
/* 115 */     return Reflector.ForgeIBlockProperties_getLightValue2.exists() ? Reflector.callInt(p_getLightValue_0_, Reflector.ForgeIBlockProperties_getLightValue2, new Object[] { p_getLightValue_1_, p_getLightValue_2_ }) : p_getLightValue_0_.getLightValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public static MapData getMapData(ItemMap p_getMapData_0_, ItemStack p_getMapData_1_, World p_getMapData_2_) {
/* 120 */     return Reflector.ForgeHooksClient.exists() ? ((ItemMap)p_getMapData_1_.getItem()).getMapData(p_getMapData_1_, p_getMapData_2_) : p_getMapData_0_.getMapData(p_getMapData_1_, p_getMapData_2_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ReflectorForge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */