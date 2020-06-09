/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPressurePlate
/*     */   extends BlockBasePressurePlate
/*     */ {
/*  20 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*     */   
/*     */   private final Sensitivity sensitivity;
/*     */   
/*     */   protected BlockPressurePlate(Material materialIn, Sensitivity sensitivityIn) {
/*  25 */     super(materialIn);
/*  26 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/*  27 */     this.sensitivity = sensitivityIn;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getRedstoneStrength(IBlockState state) {
/*  32 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBlockState setRedstoneStrength(IBlockState state, int strength) {
/*  37 */     return state.withProperty((IProperty)POWERED, Boolean.valueOf((strength > 0)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playClickOnSound(World worldIn, BlockPos color) {
/*  42 */     if (this.blockMaterial == Material.WOOD) {
/*     */       
/*  44 */       worldIn.playSound(null, color, SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.8F);
/*     */     }
/*     */     else {
/*     */       
/*  48 */       worldIn.playSound(null, color, SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playClickOffSound(World worldIn, BlockPos pos) {
/*  54 */     if (this.blockMaterial == Material.WOOD) {
/*     */       
/*  56 */       worldIn.playSound(null, pos, SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.7F);
/*     */     }
/*     */     else {
/*     */       
/*  60 */       worldIn.playSound(null, pos, SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
/*     */     List<? extends Entity> list;
/*  66 */     AxisAlignedBB axisalignedbb = PRESSURE_AABB.offset(pos);
/*     */ 
/*     */     
/*  69 */     switch (this.sensitivity) {
/*     */       
/*     */       case null:
/*  72 */         list = worldIn.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb);
/*     */         break;
/*     */       
/*     */       case MOBS:
/*  76 */         list = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
/*     */         break;
/*     */       
/*     */       default:
/*  80 */         return 0;
/*     */     } 
/*     */     
/*  83 */     if (!list.isEmpty())
/*     */     {
/*  85 */       for (Entity entity : list) {
/*     */         
/*  87 */         if (!entity.doesEntityNotTriggerPressurePlate())
/*     */         {
/*  89 */           return 15;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  94 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 102 */     return getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf((meta == 1)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 110 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 115 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)POWERED });
/*     */   }
/*     */   
/*     */   public enum Sensitivity
/*     */   {
/* 120 */     EVERYTHING,
/* 121 */     MOBS;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockPressurePlate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */