/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonSyntaxException;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.JsonToNBT;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTUtil;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ 
/*    */ public class NBTPredicate
/*    */ {
/* 18 */   public static final NBTPredicate field_193479_a = new NBTPredicate(null);
/*    */   
/*    */   @Nullable
/*    */   private final NBTTagCompound field_193480_b;
/*    */   
/*    */   public NBTPredicate(@Nullable NBTTagCompound p_i47536_1_) {
/* 24 */     this.field_193480_b = p_i47536_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_193478_a(ItemStack p_193478_1_) {
/* 29 */     return (this == field_193479_a) ? true : func_193477_a((NBTBase)p_193478_1_.getTagCompound());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_193475_a(Entity p_193475_1_) {
/* 34 */     return (this == field_193479_a) ? true : func_193477_a((NBTBase)CommandBase.entityToNBT(p_193475_1_));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_193477_a(@Nullable NBTBase p_193477_1_) {
/* 39 */     if (p_193477_1_ == null)
/*    */     {
/* 41 */       return (this == field_193479_a);
/*    */     }
/*    */ 
/*    */     
/* 45 */     return !(this.field_193480_b != null && !NBTUtil.areNBTEquals((NBTBase)this.field_193480_b, p_193477_1_, true));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static NBTPredicate func_193476_a(@Nullable JsonElement p_193476_0_) {
/* 51 */     if (p_193476_0_ != null && !p_193476_0_.isJsonNull()) {
/*    */       NBTTagCompound nbttagcompound;
/*    */ 
/*    */ 
/*    */       
/*    */       try {
/* 57 */         nbttagcompound = JsonToNBT.getTagFromJson(JsonUtils.getString(p_193476_0_, "nbt"));
/*    */       }
/* 59 */       catch (NBTException nbtexception) {
/*    */         
/* 61 */         throw new JsonSyntaxException("Invalid nbt tag: " + nbtexception.getMessage());
/*    */       } 
/*    */       
/* 64 */       return new NBTPredicate(nbttagcompound);
/*    */     } 
/*    */ 
/*    */     
/* 68 */     return field_193479_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\NBTPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */