/*    */ package net.minecraft.world.storage.loot.functions;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.google.gson.JsonSyntaxException;
/*    */ import java.util.Random;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.JsonToNBT;
/*    */ import net.minecraft.nbt.NBTException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.storage.loot.LootContext;
/*    */ import net.minecraft.world.storage.loot.conditions.LootCondition;
/*    */ 
/*    */ public class SetNBT
/*    */   extends LootFunction
/*    */ {
/*    */   private final NBTTagCompound tag;
/*    */   
/*    */   public SetNBT(LootCondition[] conditionsIn, NBTTagCompound tagIn) {
/* 23 */     super(conditionsIn);
/* 24 */     this.tag = tagIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
/* 29 */     NBTTagCompound nbttagcompound = stack.getTagCompound();
/*    */     
/* 31 */     if (nbttagcompound == null) {
/*    */       
/* 33 */       nbttagcompound = this.tag.copy();
/*    */     }
/*    */     else {
/*    */       
/* 37 */       nbttagcompound.merge(this.tag);
/*    */     } 
/*    */     
/* 40 */     stack.setTagCompound(nbttagcompound);
/* 41 */     return stack;
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     extends LootFunction.Serializer<SetNBT>
/*    */   {
/*    */     public Serializer() {
/* 48 */       super(new ResourceLocation("set_nbt"), SetNBT.class);
/*    */     }
/*    */ 
/*    */     
/*    */     public void serialize(JsonObject object, SetNBT functionClazz, JsonSerializationContext serializationContext) {
/* 53 */       object.addProperty("tag", functionClazz.tag.toString());
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public SetNBT deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
/*    */       try {
/* 60 */         NBTTagCompound nbttagcompound = JsonToNBT.getTagFromJson(JsonUtils.getString(object, "tag"));
/* 61 */         return new SetNBT(conditionsIn, nbttagcompound);
/*    */       }
/* 63 */       catch (NBTException nbtexception) {
/*    */         
/* 65 */         throw new JsonSyntaxException(nbtexception);
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\functions\SetNBT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */