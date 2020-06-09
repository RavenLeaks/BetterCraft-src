/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.crafting.CraftingManager;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class ItemKnowledgeBook
/*    */   extends Item {
/* 21 */   private static final Logger field_194126_a = LogManager.getLogger();
/*    */ 
/*    */   
/*    */   public ItemKnowledgeBook() {
/* 25 */     setMaxStackSize(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 30 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/* 31 */     NBTTagCompound nbttagcompound = itemstack.getTagCompound();
/*    */     
/* 33 */     if (!worldIn.capabilities.isCreativeMode)
/*    */     {
/* 35 */       worldIn.setHeldItem(playerIn, ItemStack.field_190927_a);
/*    */     }
/*    */     
/* 38 */     if (nbttagcompound != null && nbttagcompound.hasKey("Recipes", 9)) {
/*    */       
/* 40 */       if (!itemStackIn.isRemote) {
/*    */         
/* 42 */         NBTTagList nbttaglist = nbttagcompound.getTagList("Recipes", 8);
/* 43 */         List<IRecipe> list = Lists.newArrayList();
/*    */         
/* 45 */         for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*    */           
/* 47 */           String s = nbttaglist.getStringTagAt(i);
/* 48 */           IRecipe irecipe = CraftingManager.func_193373_a(new ResourceLocation(s));
/*    */           
/* 50 */           if (irecipe == null) {
/*    */             
/* 52 */             field_194126_a.error("Invalid recipe: " + s);
/* 53 */             return new ActionResult(EnumActionResult.FAIL, itemstack);
/*    */           } 
/*    */           
/* 56 */           list.add(irecipe);
/*    */         } 
/*    */         
/* 59 */         worldIn.func_192021_a(list);
/* 60 */         worldIn.addStat(StatList.getObjectUseStats(this));
/*    */       } 
/*    */       
/* 63 */       return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*    */     } 
/*    */ 
/*    */     
/* 67 */     field_194126_a.error("Tag not valid: " + nbttagcompound);
/* 68 */     return new ActionResult(EnumActionResult.FAIL, itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemKnowledgeBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */