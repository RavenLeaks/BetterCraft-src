/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.item.EntityFireworkRocket;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.text.translation.I18n;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class ItemFirework
/*    */   extends Item
/*    */ {
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 26 */     if (!playerIn.isRemote) {
/*    */       
/* 28 */       ItemStack itemstack = stack.getHeldItem(pos);
/* 29 */       EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(playerIn, (worldIn.getX() + facing), (worldIn.getY() + hitX), (worldIn.getZ() + hitY), itemstack);
/* 30 */       playerIn.spawnEntityInWorld((Entity)entityfireworkrocket);
/*    */       
/* 32 */       if (!stack.capabilities.isCreativeMode)
/*    */       {
/* 34 */         itemstack.func_190918_g(1);
/*    */       }
/*    */     } 
/*    */     
/* 38 */     return EnumActionResult.SUCCESS;
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 43 */     if (worldIn.isElytraFlying()) {
/*    */       
/* 45 */       ItemStack itemstack = worldIn.getHeldItem(playerIn);
/*    */       
/* 47 */       if (!itemStackIn.isRemote) {
/*    */         
/* 49 */         EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(itemStackIn, itemstack, (EntityLivingBase)worldIn);
/* 50 */         itemStackIn.spawnEntityInWorld((Entity)entityfireworkrocket);
/*    */         
/* 52 */         if (!worldIn.capabilities.isCreativeMode)
/*    */         {
/* 54 */           itemstack.func_190918_g(1);
/*    */         }
/*    */       } 
/*    */       
/* 58 */       return new ActionResult(EnumActionResult.SUCCESS, worldIn.getHeldItem(playerIn));
/*    */     } 
/*    */ 
/*    */     
/* 62 */     return new ActionResult(EnumActionResult.PASS, worldIn.getHeldItem(playerIn));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
/* 71 */     NBTTagCompound nbttagcompound = stack.getSubCompound("Fireworks");
/*    */     
/* 73 */     if (nbttagcompound != null) {
/*    */       
/* 75 */       if (nbttagcompound.hasKey("Flight", 99))
/*    */       {
/* 77 */         tooltip.add(String.valueOf(I18n.translateToLocal("item.fireworks.flight")) + " " + nbttagcompound.getByte("Flight"));
/*    */       }
/*    */       
/* 80 */       NBTTagList nbttaglist = nbttagcompound.getTagList("Explosions", 10);
/*    */       
/* 82 */       if (!nbttaglist.hasNoTags())
/*    */       {
/* 84 */         for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*    */           
/* 86 */           NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 87 */           List<String> list = Lists.newArrayList();
/* 88 */           ItemFireworkCharge.addExplosionInfo(nbttagcompound1, list);
/*    */           
/* 90 */           if (!list.isEmpty()) {
/*    */             
/* 92 */             for (int j = 1; j < list.size(); j++)
/*    */             {
/* 94 */               list.set(j, "  " + (String)list.get(j));
/*    */             }
/*    */             
/* 97 */             tooltip.addAll(list);
/*    */           } 
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemFirework.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */