/*    */ package net.minecraft.entity.monster;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.SharedMonsterAttributes;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.storage.loot.LootTableList;
/*    */ 
/*    */ public class EntityGiantZombie
/*    */   extends EntityMob
/*    */ {
/*    */   public EntityGiantZombie(World worldIn) {
/* 16 */     super(worldIn);
/* 17 */     setSize(this.width * 6.0F, this.height * 6.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerFixesGiantZombie(DataFixer fixer) {
/* 22 */     EntityLiving.registerFixesMob(fixer, EntityGiantZombie.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getEyeHeight() {
/* 27 */     return 10.440001F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void applyEntityAttributes() {
/* 32 */     super.applyEntityAttributes();
/* 33 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
/* 34 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
/* 35 */     getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(50.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getBlockPathWeight(BlockPos pos) {
/* 40 */     return this.world.getLightBrightness(pos) - 0.5F;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected ResourceLocation getLootTable() {
/* 46 */     return LootTableList.ENTITIES_GIANT;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityGiantZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */