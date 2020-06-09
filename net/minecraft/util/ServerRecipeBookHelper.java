/*     */ package net.minecraft.util;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntListIterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.util.RecipeItemHelper;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerPlayer;
/*     */ import net.minecraft.inventory.ContainerWorkbench;
/*     */ import net.minecraft.inventory.InventoryCraftResult;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.item.crafting.ShapedRecipes;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketPlaceGhostRecipe;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ServerRecipeBookHelper
/*     */ {
/*  27 */   private final Logger field_194330_a = LogManager.getLogger();
/*  28 */   private final RecipeItemHelper field_194331_b = new RecipeItemHelper();
/*     */   
/*     */   private EntityPlayerMP field_194332_c;
/*     */   private IRecipe field_194333_d;
/*     */   private boolean field_194334_e;
/*     */   private InventoryCraftResult field_194335_f;
/*     */   private InventoryCrafting field_194336_g;
/*     */   private List<Slot> field_194337_h;
/*     */   
/*     */   public void func_194327_a(EntityPlayerMP p_194327_1_, @Nullable IRecipe p_194327_2_, boolean p_194327_3_) {
/*  38 */     if (p_194327_2_ != null && p_194327_1_.func_192037_E().func_193830_f(p_194327_2_)) {
/*     */       
/*  40 */       this.field_194332_c = p_194327_1_;
/*  41 */       this.field_194333_d = p_194327_2_;
/*  42 */       this.field_194334_e = p_194327_3_;
/*  43 */       this.field_194337_h = p_194327_1_.openContainer.inventorySlots;
/*  44 */       Container container = p_194327_1_.openContainer;
/*  45 */       this.field_194335_f = null;
/*  46 */       this.field_194336_g = null;
/*     */       
/*  48 */       if (container instanceof ContainerWorkbench) {
/*     */         
/*  50 */         this.field_194335_f = ((ContainerWorkbench)container).craftResult;
/*  51 */         this.field_194336_g = ((ContainerWorkbench)container).craftMatrix;
/*     */       }
/*  53 */       else if (container instanceof ContainerPlayer) {
/*     */         
/*  55 */         this.field_194335_f = ((ContainerPlayer)container).craftResult;
/*  56 */         this.field_194336_g = ((ContainerPlayer)container).craftMatrix;
/*     */       } 
/*     */       
/*  59 */       if (this.field_194335_f != null && this.field_194336_g != null)
/*     */       {
/*  61 */         if (func_194328_c() || p_194327_1_.isCreative()) {
/*     */           
/*  63 */           this.field_194331_b.func_194119_a();
/*  64 */           p_194327_1_.inventory.func_194016_a(this.field_194331_b, false);
/*  65 */           this.field_194336_g.func_194018_a(this.field_194331_b);
/*     */           
/*  67 */           if (this.field_194331_b.func_194116_a(p_194327_2_, null)) {
/*     */             
/*  69 */             func_194329_b();
/*     */           }
/*     */           else {
/*     */             
/*  73 */             func_194326_a();
/*  74 */             p_194327_1_.connection.sendPacket((Packet)new SPacketPlaceGhostRecipe(p_194327_1_.openContainer.windowId, p_194327_2_));
/*     */           } 
/*     */           
/*  77 */           p_194327_1_.inventory.markDirty();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_194326_a() {
/*  85 */     InventoryPlayer inventoryplayer = this.field_194332_c.inventory;
/*     */     
/*  87 */     for (int i = 0; i < this.field_194336_g.getSizeInventory(); i++) {
/*     */       
/*  89 */       ItemStack itemstack = this.field_194336_g.getStackInSlot(i);
/*     */       
/*  91 */       if (!itemstack.func_190926_b())
/*     */       {
/*  93 */         while (itemstack.func_190916_E() > 0) {
/*     */           
/*  95 */           int j = inventoryplayer.storeItemStack(itemstack);
/*     */           
/*  97 */           if (j == -1)
/*     */           {
/*  99 */             j = inventoryplayer.getFirstEmptyStack();
/*     */           }
/*     */           
/* 102 */           ItemStack itemstack1 = itemstack.copy();
/* 103 */           itemstack1.func_190920_e(1);
/* 104 */           inventoryplayer.func_191971_c(j, itemstack1);
/* 105 */           this.field_194336_g.decrStackSize(i, 1);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 110 */     this.field_194336_g.clear();
/* 111 */     this.field_194335_f.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_194329_b() {
/* 116 */     boolean flag = this.field_194333_d.matches(this.field_194336_g, this.field_194332_c.world);
/* 117 */     int i = this.field_194331_b.func_194114_b(this.field_194333_d, null);
/*     */     
/* 119 */     if (flag) {
/*     */       
/* 121 */       boolean flag1 = true;
/*     */       
/* 123 */       for (int j = 0; j < this.field_194336_g.getSizeInventory(); j++) {
/*     */         
/* 125 */         ItemStack itemstack = this.field_194336_g.getStackInSlot(j);
/*     */         
/* 127 */         if (!itemstack.func_190926_b() && Math.min(i, itemstack.getMaxStackSize()) > itemstack.func_190916_E())
/*     */         {
/* 129 */           flag1 = false;
/*     */         }
/*     */       } 
/*     */       
/* 133 */       if (flag1) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 139 */     int i1 = func_194324_a(i, flag);
/* 140 */     IntArrayList intArrayList = new IntArrayList();
/*     */     
/* 142 */     if (this.field_194331_b.func_194118_a(this.field_194333_d, (IntList)intArrayList, i1)) {
/*     */       
/* 144 */       int j1 = i1;
/* 145 */       IntListIterator intlistiterator = intArrayList.iterator();
/*     */       
/* 147 */       while (intlistiterator.hasNext()) {
/*     */         
/* 149 */         int k = ((Integer)intlistiterator.next()).intValue();
/* 150 */         int l = RecipeItemHelper.func_194115_b(k).getMaxStackSize();
/*     */         
/* 152 */         if (l < j1)
/*     */         {
/* 154 */           j1 = l;
/*     */         }
/*     */       } 
/*     */       
/* 158 */       if (this.field_194331_b.func_194118_a(this.field_194333_d, (IntList)intArrayList, j1)) {
/*     */         
/* 160 */         func_194326_a();
/* 161 */         func_194323_a(j1, (IntList)intArrayList);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_194324_a(int p_194324_1_, boolean p_194324_2_) {
/* 168 */     int i = 1;
/*     */     
/* 170 */     if (this.field_194334_e) {
/*     */       
/* 172 */       i = p_194324_1_;
/*     */     }
/* 174 */     else if (p_194324_2_) {
/*     */       
/* 176 */       i = 64;
/*     */       
/* 178 */       for (int j = 0; j < this.field_194336_g.getSizeInventory(); j++) {
/*     */         
/* 180 */         ItemStack itemstack = this.field_194336_g.getStackInSlot(j);
/*     */         
/* 182 */         if (!itemstack.func_190926_b() && i > itemstack.func_190916_E())
/*     */         {
/* 184 */           i = itemstack.func_190916_E();
/*     */         }
/*     */       } 
/*     */       
/* 188 */       if (i < 64)
/*     */       {
/* 190 */         i++;
/*     */       }
/*     */     } 
/*     */     
/* 194 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_194323_a(int p_194323_1_, IntList p_194323_2_) {
/* 199 */     int i = this.field_194336_g.getWidth();
/* 200 */     int j = this.field_194336_g.getHeight();
/*     */     
/* 202 */     if (this.field_194333_d instanceof ShapedRecipes) {
/*     */       
/* 204 */       ShapedRecipes shapedrecipes = (ShapedRecipes)this.field_194333_d;
/* 205 */       i = shapedrecipes.func_192403_f();
/* 206 */       j = shapedrecipes.func_192404_g();
/*     */     } 
/*     */     
/* 209 */     int j1 = 1;
/* 210 */     IntListIterator<Integer> intListIterator = p_194323_2_.iterator();
/*     */     
/* 212 */     for (int k = 0; k < this.field_194336_g.getWidth() && j != k; k++) {
/*     */       
/* 214 */       for (int l = 0; l < this.field_194336_g.getHeight(); l++) {
/*     */         
/* 216 */         if (i == l || !intListIterator.hasNext()) {
/*     */           
/* 218 */           j1 += this.field_194336_g.getWidth() - l;
/*     */           
/*     */           break;
/*     */         } 
/* 222 */         Slot slot = this.field_194337_h.get(j1);
/* 223 */         ItemStack itemstack = RecipeItemHelper.func_194115_b(((Integer)intListIterator.next()).intValue());
/*     */         
/* 225 */         if (itemstack.func_190926_b()) {
/*     */           
/* 227 */           j1++;
/*     */         }
/*     */         else {
/*     */           
/* 231 */           for (int i1 = 0; i1 < p_194323_1_; i1++)
/*     */           {
/* 233 */             func_194325_a(slot, itemstack);
/*     */           }
/*     */           
/* 236 */           j1++;
/*     */         } 
/*     */       } 
/*     */       
/* 240 */       if (!intListIterator.hasNext()) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_194325_a(Slot p_194325_1_, ItemStack p_194325_2_) {
/* 249 */     InventoryPlayer inventoryplayer = this.field_194332_c.inventory;
/* 250 */     int i = inventoryplayer.func_194014_c(p_194325_2_);
/*     */     
/* 252 */     if (i != -1) {
/*     */       
/* 254 */       ItemStack itemstack = inventoryplayer.getStackInSlot(i).copy();
/*     */       
/* 256 */       if (!itemstack.func_190926_b()) {
/*     */         
/* 258 */         if (itemstack.func_190916_E() > 1) {
/*     */           
/* 260 */           inventoryplayer.decrStackSize(i, 1);
/*     */         }
/*     */         else {
/*     */           
/* 264 */           inventoryplayer.removeStackFromSlot(i);
/*     */         } 
/*     */         
/* 267 */         itemstack.func_190920_e(1);
/*     */         
/* 269 */         if (p_194325_1_.getStack().func_190926_b()) {
/*     */           
/* 271 */           p_194325_1_.putStack(itemstack);
/*     */         }
/*     */         else {
/*     */           
/* 275 */           p_194325_1_.getStack().func_190917_f(1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_194328_c() {
/* 283 */     InventoryPlayer inventoryplayer = this.field_194332_c.inventory;
/*     */     
/* 285 */     for (int i = 0; i < this.field_194336_g.getSizeInventory(); i++) {
/*     */       
/* 287 */       ItemStack itemstack = this.field_194336_g.getStackInSlot(i);
/*     */       
/* 289 */       if (!itemstack.func_190926_b()) {
/*     */         
/* 291 */         int j = inventoryplayer.storeItemStack(itemstack);
/*     */         
/* 293 */         if (j == -1)
/*     */         {
/* 295 */           j = inventoryplayer.getFirstEmptyStack();
/*     */         }
/*     */         
/* 298 */         if (j == -1)
/*     */         {
/* 300 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 305 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\ServerRecipeBookHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */