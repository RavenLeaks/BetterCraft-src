/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class ShulkerBoxItemColor
/*    */   implements IFixableData {
/*  8 */   public static final String[] field_191278_a = new String[] { "minecraft:white_shulker_box", "minecraft:orange_shulker_box", "minecraft:magenta_shulker_box", "minecraft:light_blue_shulker_box", "minecraft:yellow_shulker_box", "minecraft:lime_shulker_box", "minecraft:pink_shulker_box", "minecraft:gray_shulker_box", "minecraft:silver_shulker_box", "minecraft:cyan_shulker_box", "minecraft:purple_shulker_box", "minecraft:blue_shulker_box", "minecraft:brown_shulker_box", "minecraft:green_shulker_box", "minecraft:red_shulker_box", "minecraft:black_shulker_box" };
/*    */ 
/*    */   
/*    */   public int getFixVersion() {
/* 12 */     return 813;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 17 */     if ("minecraft:shulker_box".equals(compound.getString("id")) && compound.hasKey("tag", 10)) {
/*    */       
/* 19 */       NBTTagCompound nbttagcompound = compound.getCompoundTag("tag");
/*    */       
/* 21 */       if (nbttagcompound.hasKey("BlockEntityTag", 10)) {
/*    */         
/* 23 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("BlockEntityTag");
/*    */         
/* 25 */         if (nbttagcompound1.getTagList("Items", 10).hasNoTags())
/*    */         {
/* 27 */           nbttagcompound1.removeTag("Items");
/*    */         }
/*    */         
/* 30 */         int i = nbttagcompound1.getInteger("Color");
/* 31 */         nbttagcompound1.removeTag("Color");
/*    */         
/* 33 */         if (nbttagcompound1.hasNoTags())
/*    */         {
/* 35 */           nbttagcompound.removeTag("BlockEntityTag");
/*    */         }
/*    */         
/* 38 */         if (nbttagcompound.hasNoTags())
/*    */         {
/* 40 */           compound.removeTag("tag");
/*    */         }
/*    */         
/* 43 */         compound.setString("id", field_191278_a[i % 16]);
/*    */       } 
/*    */     } 
/*    */     
/* 47 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\ShulkerBoxItemColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */