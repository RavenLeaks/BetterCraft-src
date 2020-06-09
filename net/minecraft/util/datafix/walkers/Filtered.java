/*    */ package net.minecraft.util.datafix.walkers;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityList;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.datafix.IDataFixer;
/*    */ import net.minecraft.util.datafix.IDataWalker;
/*    */ 
/*    */ public abstract class Filtered
/*    */   implements IDataWalker
/*    */ {
/*    */   private final ResourceLocation key;
/*    */   
/*    */   public Filtered(Class<?> p_i47309_1_) {
/* 17 */     if (Entity.class.isAssignableFrom(p_i47309_1_)) {
/*    */       
/* 19 */       this.key = EntityList.func_191306_a(p_i47309_1_);
/*    */     }
/* 21 */     else if (TileEntity.class.isAssignableFrom(p_i47309_1_)) {
/*    */       
/* 23 */       this.key = TileEntity.func_190559_a(p_i47309_1_);
/*    */     }
/*    */     else {
/*    */       
/* 27 */       this.key = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn) {
/* 33 */     if ((new ResourceLocation(compound.getString("id"))).equals(this.key))
/*    */     {
/* 35 */       compound = filteredProcess(fixer, compound, versionIn);
/*    */     }
/*    */     
/* 38 */     return compound;
/*    */   }
/*    */   
/*    */   abstract NBTTagCompound filteredProcess(IDataFixer paramIDataFixer, NBTTagCompound paramNBTTagCompound, int paramInt);
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\walkers\Filtered.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */