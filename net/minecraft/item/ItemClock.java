/*    */ package net.minecraft.item;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemClock
/*    */   extends Item
/*    */ {
/*    */   public ItemClock() {
/* 14 */     addPropertyOverride(new ResourceLocation("time"), new IItemPropertyGetter()
/*    */         {
/*    */           double rotation;
/*    */           double rota;
/*    */           long lastUpdateTick;
/*    */           
/*    */           public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
/* 21 */             boolean flag = (entityIn != null);
/* 22 */             Entity entity = flag ? (Entity)entityIn : (Entity)stack.getItemFrame();
/*    */             
/* 24 */             if (worldIn == null && entity != null)
/*    */             {
/* 26 */               worldIn = entity.world;
/*    */             }
/*    */             
/* 29 */             if (worldIn == null)
/*    */             {
/* 31 */               return 0.0F;
/*    */             }
/*    */ 
/*    */ 
/*    */ 
/*    */             
/* 37 */             if (worldIn.provider.isSurfaceWorld()) {
/*    */               
/* 39 */               d0 = worldIn.getCelestialAngle(1.0F);
/*    */             }
/*    */             else {
/*    */               
/* 43 */               d0 = Math.random();
/*    */             } 
/*    */             
/* 46 */             double d0 = wobble(worldIn, d0);
/* 47 */             return (float)d0;
/*    */           }
/*    */ 
/*    */           
/*    */           private double wobble(World p_185087_1_, double p_185087_2_) {
/* 52 */             if (p_185087_1_.getTotalWorldTime() != this.lastUpdateTick) {
/*    */               
/* 54 */               this.lastUpdateTick = p_185087_1_.getTotalWorldTime();
/* 55 */               double d0 = p_185087_2_ - this.rotation;
/* 56 */               d0 = MathHelper.func_191273_b(d0 + 0.5D, 1.0D) - 0.5D;
/* 57 */               this.rota += d0 * 0.1D;
/* 58 */               this.rota *= 0.9D;
/* 59 */               this.rotation = MathHelper.func_191273_b(this.rotation + this.rota, 1.0D);
/*    */             } 
/*    */             
/* 62 */             return this.rotation;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemClock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */