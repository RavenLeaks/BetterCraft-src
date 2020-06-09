/*    */ package net.minecraft.entity.item;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityMinecartEmpty
/*    */   extends EntityMinecart
/*    */ {
/*    */   public EntityMinecartEmpty(World worldIn) {
/* 12 */     super(worldIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityMinecartEmpty(World worldIn, double x, double y, double z) {
/* 17 */     super(worldIn, x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerFixesMinecartEmpty(DataFixer fixer) {
/* 22 */     EntityMinecart.registerFixesMinecart(fixer, EntityMinecartEmpty.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processInitialInteract(EntityPlayer player, EnumHand stack) {
/* 27 */     if (player.isSneaking())
/*    */     {
/* 29 */       return false;
/*    */     }
/* 31 */     if (isBeingRidden())
/*    */     {
/* 33 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 37 */     if (!this.world.isRemote)
/*    */     {
/* 39 */       player.startRiding(this);
/*    */     }
/*    */     
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
/* 51 */     if (receivingPower) {
/*    */       
/* 53 */       if (isBeingRidden())
/*    */       {
/* 55 */         removePassengers();
/*    */       }
/*    */       
/* 58 */       if (getRollingAmplitude() == 0) {
/*    */         
/* 60 */         setRollingDirection(-getRollingDirection());
/* 61 */         setRollingAmplitude(10);
/* 62 */         setDamage(50.0F);
/* 63 */         setBeenAttacked();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityMinecart.Type getType() {
/* 70 */     return EntityMinecart.Type.RIDEABLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityMinecartEmpty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */