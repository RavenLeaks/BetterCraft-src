/*     */ package net.minecraft.item;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.network.play.server.SPacketSetSlot;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentUtils;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemWrittenBook extends Item {
/*     */   public ItemWrittenBook() {
/*  29 */     setMaxStackSize(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean validBookTagContents(NBTTagCompound nbt) {
/*  34 */     if (!ItemWritableBook.isNBTValid(nbt))
/*     */     {
/*  36 */       return false;
/*     */     }
/*  38 */     if (!nbt.hasKey("title", 8))
/*     */     {
/*  40 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  44 */     String s = nbt.getString("title");
/*  45 */     return (s != null && s.length() <= 32) ? nbt.hasKey("author", 8) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getGeneration(ItemStack book) {
/*  54 */     return book.getTagCompound().getInteger("generation");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/*  59 */     if (stack.hasTagCompound()) {
/*     */       
/*  61 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*  62 */       String s = nbttagcompound.getString("title");
/*     */       
/*  64 */       if (!StringUtils.isNullOrEmpty(s))
/*     */       {
/*  66 */         return s;
/*     */       }
/*     */     } 
/*     */     
/*  70 */     return super.getItemStackDisplayName(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
/*  78 */     if (stack.hasTagCompound()) {
/*     */       
/*  80 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*  81 */       String s = nbttagcompound.getString("author");
/*     */       
/*  83 */       if (!StringUtils.isNullOrEmpty(s))
/*     */       {
/*  85 */         tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("book.byAuthor", new Object[] { s }));
/*     */       }
/*     */       
/*  88 */       tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("book.generation." + nbttagcompound.getInteger("generation")));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/*  94 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/*     */     
/*  96 */     if (!itemStackIn.isRemote)
/*     */     {
/*  98 */       resolveContents(itemstack, worldIn);
/*     */     }
/*     */     
/* 101 */     worldIn.openBook(itemstack, playerIn);
/* 102 */     worldIn.addStat(StatList.getObjectUseStats(this));
/* 103 */     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ 
/*     */   
/*     */   private void resolveContents(ItemStack stack, EntityPlayer player) {
/* 108 */     if (stack.getTagCompound() != null) {
/*     */       
/* 110 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */       
/* 112 */       if (!nbttagcompound.getBoolean("resolved")) {
/*     */         
/* 114 */         nbttagcompound.setBoolean("resolved", true);
/*     */         
/* 116 */         if (validBookTagContents(nbttagcompound)) {
/*     */           
/* 118 */           NBTTagList nbttaglist = nbttagcompound.getTagList("pages", 8);
/*     */           
/* 120 */           for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */             TextComponentString textComponentString;
/* 122 */             String s = nbttaglist.getStringTagAt(i);
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 127 */               ITextComponent itextcomponent = ITextComponent.Serializer.fromJsonLenient(s);
/* 128 */               itextcomponent = TextComponentUtils.processComponent((ICommandSender)player, itextcomponent, (Entity)player);
/*     */             }
/* 130 */             catch (Exception var9) {
/*     */               
/* 132 */               textComponentString = new TextComponentString(s);
/*     */             } 
/*     */             
/* 135 */             nbttaglist.set(i, (NBTBase)new NBTTagString(ITextComponent.Serializer.componentToJson((ITextComponent)textComponentString)));
/*     */           } 
/*     */           
/* 138 */           nbttagcompound.setTag("pages", (NBTBase)nbttaglist);
/*     */           
/* 140 */           if (player instanceof EntityPlayerMP && player.getHeldItemMainhand() == stack) {
/*     */             
/* 142 */             Slot slot = player.openContainer.getSlotFromInventory((IInventory)player.inventory, player.inventory.currentItem);
/* 143 */             ((EntityPlayerMP)player).connection.sendPacket((Packet)new SPacketSetSlot(0, slot.slotNumber, stack));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasEffect(ItemStack stack) {
/* 152 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemWrittenBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */