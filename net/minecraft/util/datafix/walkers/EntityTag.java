/*    */ package net.minecraft.util.datafix.walkers;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.FixTypes;
/*    */ import net.minecraft.util.datafix.IDataFixer;
/*    */ import net.minecraft.util.datafix.IDataWalker;
/*    */ import net.minecraft.util.datafix.IFixType;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class EntityTag implements IDataWalker {
/* 12 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */ 
/*    */   
/*    */   public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn) {
/* 16 */     NBTTagCompound nbttagcompound = compound.getCompoundTag("tag");
/*    */     
/* 18 */     if (nbttagcompound.hasKey("EntityTag", 10)) {
/*    */       String s1; boolean flag;
/* 20 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("EntityTag");
/* 21 */       String s = compound.getString("id");
/*    */ 
/*    */       
/* 24 */       if ("minecraft:armor_stand".equals(s)) {
/*    */         
/* 26 */         s1 = (versionIn < 515) ? "ArmorStand" : "minecraft:armor_stand";
/*    */       }
/*    */       else {
/*    */         
/* 30 */         if (!"minecraft:spawn_egg".equals(s))
/*    */         {
/* 32 */           return compound;
/*    */         }
/*    */         
/* 35 */         s1 = nbttagcompound1.getString("id");
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 40 */       if (s1 == null) {
/*    */         
/* 42 */         LOGGER.warn("Unable to resolve Entity for ItemInstance: {}", s);
/* 43 */         flag = false;
/*    */       }
/*    */       else {
/*    */         
/* 47 */         flag = !nbttagcompound1.hasKey("id", 8);
/* 48 */         nbttagcompound1.setString("id", s1);
/*    */       } 
/*    */       
/* 51 */       fixer.process((IFixType)FixTypes.ENTITY, nbttagcompound1, versionIn);
/*    */       
/* 53 */       if (flag)
/*    */       {
/* 55 */         nbttagcompound1.removeTag("id");
/*    */       }
/*    */     } 
/*    */     
/* 59 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\walkers\EntityTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */