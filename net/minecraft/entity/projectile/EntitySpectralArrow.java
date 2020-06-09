/*    */ package net.minecraft.entity.projectile;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntitySpectralArrow
/*    */   extends EntityArrow {
/* 15 */   private int duration = 200;
/*    */ 
/*    */   
/*    */   public EntitySpectralArrow(World worldIn) {
/* 19 */     super(worldIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySpectralArrow(World worldIn, EntityLivingBase shooter) {
/* 24 */     super(worldIn, shooter);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySpectralArrow(World worldIn, double x, double y, double z) {
/* 29 */     super(worldIn, x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 37 */     super.onUpdate();
/*    */     
/* 39 */     if (this.world.isRemote && !this.inGround)
/*    */     {
/* 41 */       this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack getArrowStack() {
/* 47 */     return new ItemStack(Items.SPECTRAL_ARROW);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void arrowHit(EntityLivingBase living) {
/* 52 */     super.arrowHit(living);
/* 53 */     PotionEffect potioneffect = new PotionEffect(MobEffects.GLOWING, this.duration, 0);
/* 54 */     living.addPotionEffect(potioneffect);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerFixesSpectralArrow(DataFixer fixer) {
/* 59 */     EntityArrow.registerFixesArrow(fixer, "SpectralArrow");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 67 */     super.readEntityFromNBT(compound);
/*    */     
/* 69 */     if (compound.hasKey("Duration"))
/*    */     {
/* 71 */       this.duration = compound.getInteger("Duration");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 80 */     super.writeEntityToNBT(compound);
/* 81 */     compound.setInteger("Duration", this.duration);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntitySpectralArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */