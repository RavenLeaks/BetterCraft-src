/*     */ package net.minecraft.stats;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketRecipeBook;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class RecipeBookServer extends RecipeBook {
/*  20 */   private static final Logger field_192828_d = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   public void func_193835_a(List<IRecipe> p_193835_1_, EntityPlayerMP p_193835_2_) {
/*  24 */     List<IRecipe> list = Lists.newArrayList();
/*     */     
/*  26 */     for (IRecipe irecipe : p_193835_1_) {
/*     */       
/*  28 */       if (!this.field_194077_a.get(func_194075_d(irecipe)) && !irecipe.func_192399_d()) {
/*     */         
/*  30 */         func_194073_a(irecipe);
/*  31 */         func_193825_e(irecipe);
/*  32 */         list.add(irecipe);
/*  33 */         CriteriaTriggers.field_192126_f.func_192225_a(p_193835_2_, irecipe);
/*     */       } 
/*     */     } 
/*     */     
/*  37 */     func_194081_a(SPacketRecipeBook.State.ADD, p_193835_2_, list);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193834_b(List<IRecipe> p_193834_1_, EntityPlayerMP p_193834_2_) {
/*  42 */     List<IRecipe> list = Lists.newArrayList();
/*     */     
/*  44 */     for (IRecipe irecipe : p_193834_1_) {
/*     */       
/*  46 */       if (this.field_194077_a.get(func_194075_d(irecipe))) {
/*     */         
/*  48 */         func_193831_b(irecipe);
/*  49 */         list.add(irecipe);
/*     */       } 
/*     */     } 
/*     */     
/*  53 */     func_194081_a(SPacketRecipeBook.State.REMOVE, p_193834_2_, list);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_194081_a(SPacketRecipeBook.State p_194081_1_, EntityPlayerMP p_194081_2_, List<IRecipe> p_194081_3_) {
/*  58 */     p_194081_2_.connection.sendPacket((Packet)new SPacketRecipeBook(p_194081_1_, p_194081_3_, Collections.emptyList(), this.field_192818_b, this.field_192819_c));
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_192824_e() {
/*  63 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  64 */     nbttagcompound.setBoolean("isGuiOpen", this.field_192818_b);
/*  65 */     nbttagcompound.setBoolean("isFilteringCraftable", this.field_192819_c);
/*  66 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  68 */     for (IRecipe irecipe : func_194079_d())
/*     */     {
/*  70 */       nbttaglist.appendTag((NBTBase)new NBTTagString(((ResourceLocation)CraftingManager.field_193380_a.getNameForObject(irecipe)).toString()));
/*     */     }
/*     */     
/*  73 */     nbttagcompound.setTag("recipes", (NBTBase)nbttaglist);
/*  74 */     NBTTagList nbttaglist1 = new NBTTagList();
/*     */     
/*  76 */     for (IRecipe irecipe1 : func_194080_e())
/*     */     {
/*  78 */       nbttaglist1.appendTag((NBTBase)new NBTTagString(((ResourceLocation)CraftingManager.field_193380_a.getNameForObject(irecipe1)).toString()));
/*     */     }
/*     */     
/*  81 */     nbttagcompound.setTag("toBeDisplayed", (NBTBase)nbttaglist1);
/*  82 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192825_a(NBTTagCompound p_192825_1_) {
/*  87 */     this.field_192818_b = p_192825_1_.getBoolean("isGuiOpen");
/*  88 */     this.field_192819_c = p_192825_1_.getBoolean("isFilteringCraftable");
/*  89 */     NBTTagList nbttaglist = p_192825_1_.getTagList("recipes", 8);
/*     */     
/*  91 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/*  93 */       ResourceLocation resourcelocation = new ResourceLocation(nbttaglist.getStringTagAt(i));
/*  94 */       IRecipe irecipe = CraftingManager.func_193373_a(resourcelocation);
/*     */       
/*  96 */       if (irecipe == null) {
/*     */         
/*  98 */         field_192828_d.info("Tried to load unrecognized recipe: {} removed now.", resourcelocation);
/*     */       }
/*     */       else {
/*     */         
/* 102 */         func_194073_a(irecipe);
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     NBTTagList nbttaglist1 = p_192825_1_.getTagList("toBeDisplayed", 8);
/*     */     
/* 108 */     for (int j = 0; j < nbttaglist1.tagCount(); j++) {
/*     */       
/* 110 */       ResourceLocation resourcelocation1 = new ResourceLocation(nbttaglist1.getStringTagAt(j));
/* 111 */       IRecipe irecipe1 = CraftingManager.func_193373_a(resourcelocation1);
/*     */       
/* 113 */       if (irecipe1 == null) {
/*     */         
/* 115 */         field_192828_d.info("Tried to load unrecognized recipe: {} removed now.", resourcelocation1);
/*     */       }
/*     */       else {
/*     */         
/* 119 */         func_193825_e(irecipe1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<IRecipe> func_194079_d() {
/* 126 */     List<IRecipe> list = Lists.newArrayList();
/*     */     
/* 128 */     for (int i = this.field_194077_a.nextSetBit(0); i >= 0; i = this.field_194077_a.nextSetBit(i + 1))
/*     */     {
/* 130 */       list.add((IRecipe)CraftingManager.field_193380_a.getObjectById(i));
/*     */     }
/*     */     
/* 133 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<IRecipe> func_194080_e() {
/* 138 */     List<IRecipe> list = Lists.newArrayList();
/*     */     
/* 140 */     for (int i = this.field_194078_b.nextSetBit(0); i >= 0; i = this.field_194078_b.nextSetBit(i + 1))
/*     */     {
/* 142 */       list.add((IRecipe)CraftingManager.field_193380_a.getObjectById(i));
/*     */     }
/*     */     
/* 145 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192826_c(EntityPlayerMP p_192826_1_) {
/* 150 */     p_192826_1_.connection.sendPacket((Packet)new SPacketRecipeBook(SPacketRecipeBook.State.INIT, func_194079_d(), func_194080_e(), this.field_192818_b, this.field_192819_c));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\stats\RecipeBookServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */