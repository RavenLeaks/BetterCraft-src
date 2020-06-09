/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.client.renderer.block.model.ModelManager;
/*    */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class ItemModelMesher
/*    */ {
/* 16 */   private final Map<Integer, ModelResourceLocation> simpleShapes = Maps.newHashMap();
/* 17 */   private final Map<Integer, IBakedModel> simpleShapesCache = Maps.newHashMap();
/* 18 */   private final Map<Item, ItemMeshDefinition> shapers = Maps.newHashMap();
/*    */   
/*    */   private final ModelManager modelManager;
/*    */   
/*    */   public ItemModelMesher(ModelManager modelManager) {
/* 23 */     this.modelManager = modelManager;
/*    */   }
/*    */ 
/*    */   
/*    */   public TextureAtlasSprite getParticleIcon(Item item) {
/* 28 */     return getParticleIcon(item, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public TextureAtlasSprite getParticleIcon(Item item, int meta) {
/* 33 */     return getItemModel(new ItemStack(item, 1, meta)).getParticleTexture();
/*    */   }
/*    */ 
/*    */   
/*    */   public IBakedModel getItemModel(ItemStack stack) {
/* 38 */     Item item = stack.getItem();
/* 39 */     IBakedModel ibakedmodel = getItemModel(item, getMetadata(stack));
/*    */     
/* 41 */     if (ibakedmodel == null) {
/*    */       
/* 43 */       ItemMeshDefinition itemmeshdefinition = this.shapers.get(item);
/*    */       
/* 45 */       if (itemmeshdefinition != null)
/*    */       {
/* 47 */         ibakedmodel = this.modelManager.getModel(itemmeshdefinition.getModelLocation(stack));
/*    */       }
/*    */     } 
/*    */     
/* 51 */     if (ibakedmodel == null)
/*    */     {
/* 53 */       ibakedmodel = this.modelManager.getMissingModel();
/*    */     }
/*    */     
/* 56 */     return ibakedmodel;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getMetadata(ItemStack stack) {
/* 61 */     return (stack.getMaxDamage() > 0) ? 0 : stack.getMetadata();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected IBakedModel getItemModel(Item item, int meta) {
/* 67 */     return this.simpleShapesCache.get(Integer.valueOf(getIndex(item, meta)));
/*    */   }
/*    */ 
/*    */   
/*    */   private int getIndex(Item item, int meta) {
/* 72 */     return Item.getIdFromItem(item) << 16 | meta;
/*    */   }
/*    */ 
/*    */   
/*    */   public void register(Item item, int meta, ModelResourceLocation location) {
/* 77 */     this.simpleShapes.put(Integer.valueOf(getIndex(item, meta)), location);
/* 78 */     this.simpleShapesCache.put(Integer.valueOf(getIndex(item, meta)), this.modelManager.getModel(location));
/*    */   }
/*    */ 
/*    */   
/*    */   public void register(Item item, ItemMeshDefinition definition) {
/* 83 */     this.shapers.put(item, definition);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelManager getModelManager() {
/* 88 */     return this.modelManager;
/*    */   }
/*    */ 
/*    */   
/*    */   public void rebuildCache() {
/* 93 */     this.simpleShapesCache.clear();
/*    */     
/* 95 */     for (Map.Entry<Integer, ModelResourceLocation> entry : this.simpleShapes.entrySet())
/*    */     {
/* 97 */       this.simpleShapesCache.put(entry.getKey(), this.modelManager.getModel(entry.getValue()));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\ItemModelMesher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */