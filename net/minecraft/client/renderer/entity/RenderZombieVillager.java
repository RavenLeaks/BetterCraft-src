/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelZombieVillager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerVillagerArmor;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityZombieVillager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderZombieVillager extends RenderBiped<EntityZombieVillager> {
/* 10 */   private static final ResourceLocation ZOMBIE_VILLAGER_TEXTURES = new ResourceLocation("textures/entity/zombie_villager/zombie_villager.png");
/* 11 */   private static final ResourceLocation ZOMBIE_VILLAGER_FARMER_LOCATION = new ResourceLocation("textures/entity/zombie_villager/zombie_farmer.png");
/* 12 */   private static final ResourceLocation ZOMBIE_VILLAGER_LIBRARIAN_LOC = new ResourceLocation("textures/entity/zombie_villager/zombie_librarian.png");
/* 13 */   private static final ResourceLocation ZOMBIE_VILLAGER_PRIEST_LOCATION = new ResourceLocation("textures/entity/zombie_villager/zombie_priest.png");
/* 14 */   private static final ResourceLocation ZOMBIE_VILLAGER_SMITH_LOCATION = new ResourceLocation("textures/entity/zombie_villager/zombie_smith.png");
/* 15 */   private static final ResourceLocation ZOMBIE_VILLAGER_BUTCHER_LOCATION = new ResourceLocation("textures/entity/zombie_villager/zombie_butcher.png");
/*    */ 
/*    */   
/*    */   public RenderZombieVillager(RenderManager p_i47186_1_) {
/* 19 */     super(p_i47186_1_, (ModelBiped)new ModelZombieVillager(), 0.5F);
/* 20 */     addLayer(new LayerVillagerArmor(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityZombieVillager entity) {
/* 28 */     switch (entity.func_190736_dl()) {
/*    */       
/*    */       case 0:
/* 31 */         return ZOMBIE_VILLAGER_FARMER_LOCATION;
/*    */       
/*    */       case 1:
/* 34 */         return ZOMBIE_VILLAGER_LIBRARIAN_LOC;
/*    */       
/*    */       case 2:
/* 37 */         return ZOMBIE_VILLAGER_PRIEST_LOCATION;
/*    */       
/*    */       case 3:
/* 40 */         return ZOMBIE_VILLAGER_SMITH_LOCATION;
/*    */       
/*    */       case 4:
/* 43 */         return ZOMBIE_VILLAGER_BUTCHER_LOCATION;
/*    */     } 
/*    */ 
/*    */     
/* 47 */     return ZOMBIE_VILLAGER_TEXTURES;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void rotateCorpse(EntityZombieVillager entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 53 */     if (entityLiving.isConverting())
/*    */     {
/* 55 */       p_77043_3_ += (float)(Math.cos(entityLiving.ticksExisted * 3.25D) * Math.PI * 0.25D);
/*    */     }
/*    */     
/* 58 */     super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderZombieVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */