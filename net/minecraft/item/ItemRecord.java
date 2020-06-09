/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.BlockJukebox;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.text.translation.I18n;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemRecord extends Item {
/* 24 */   private static final Map<SoundEvent, ItemRecord> RECORDS = Maps.newHashMap();
/*    */   
/*    */   private final SoundEvent sound;
/*    */   private final String displayName;
/*    */   
/*    */   protected ItemRecord(String p_i46742_1_, SoundEvent soundIn) {
/* 30 */     this.displayName = "item.record." + p_i46742_1_ + ".desc";
/* 31 */     this.sound = soundIn;
/* 32 */     this.maxStackSize = 1;
/* 33 */     setCreativeTab(CreativeTabs.MISC);
/* 34 */     RECORDS.put(this.sound, this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 42 */     IBlockState iblockstate = playerIn.getBlockState(worldIn);
/*    */     
/* 44 */     if (iblockstate.getBlock() == Blocks.JUKEBOX && !((Boolean)iblockstate.getValue((IProperty)BlockJukebox.HAS_RECORD)).booleanValue()) {
/*    */       
/* 46 */       if (!playerIn.isRemote) {
/*    */         
/* 48 */         ItemStack itemstack = stack.getHeldItem(pos);
/* 49 */         ((BlockJukebox)Blocks.JUKEBOX).insertRecord(playerIn, worldIn, iblockstate, itemstack);
/* 50 */         playerIn.playEvent(null, 1010, worldIn, Item.getIdFromItem(this));
/* 51 */         itemstack.func_190918_g(1);
/* 52 */         stack.addStat(StatList.RECORD_PLAYED);
/*    */       } 
/*    */       
/* 55 */       return EnumActionResult.SUCCESS;
/*    */     } 
/*    */ 
/*    */     
/* 59 */     return EnumActionResult.PASS;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
/* 68 */     tooltip.add(getRecordNameLocal());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRecordNameLocal() {
/* 73 */     return I18n.translateToLocal(this.displayName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumRarity getRarity(ItemStack stack) {
/* 81 */     return EnumRarity.RARE;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static ItemRecord getBySound(SoundEvent soundIn) {
/* 87 */     return RECORDS.get(soundIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public SoundEvent getSound() {
/* 92 */     return this.sound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */