/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.gson.JsonParseException;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.nbt.NBTTagString;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.StringUtils;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ 
/*    */ public class BookPagesStrictJSON
/*    */   implements IFixableData {
/*    */   public int getFixVersion() {
/* 17 */     return 165;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 22 */     if ("minecraft:written_book".equals(compound.getString("id"))) {
/*    */       
/* 24 */       NBTTagCompound nbttagcompound = compound.getCompoundTag("tag");
/*    */       
/* 26 */       if (nbttagcompound.hasKey("pages", 9)) {
/*    */         
/* 28 */         NBTTagList nbttaglist = nbttagcompound.getTagList("pages", 8);
/*    */         
/* 30 */         for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*    */           TextComponentString textComponentString;
/* 32 */           String s = nbttaglist.getStringTagAt(i);
/* 33 */           ITextComponent itextcomponent = null;
/*    */           
/* 35 */           if (!"null".equals(s) && !StringUtils.isNullOrEmpty(s)) {
/*    */             
/* 37 */             if ((s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') || (s.charAt(0) == '{' && s.charAt(s.length() - 1) == '}')) {
/*    */               TextComponentString textComponentString1;
/*    */               ITextComponent iTextComponent;
/*    */               try {
/* 41 */                 itextcomponent = (ITextComponent)JsonUtils.gsonDeserialize(SignStrictJSON.GSON_INSTANCE, s, ITextComponent.class, true);
/*    */                 
/* 43 */                 if (itextcomponent == null)
/*    */                 {
/* 45 */                   textComponentString1 = new TextComponentString("");
/*    */                 }
/*    */               }
/* 48 */               catch (JsonParseException jsonParseException) {}
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 53 */               if (textComponentString1 == null) {
/*    */                 
/*    */                 try {
/*    */                   
/* 57 */                   iTextComponent = ITextComponent.Serializer.jsonToComponent(s);
/*    */                 }
/* 59 */                 catch (JsonParseException jsonParseException) {}
/*    */               }
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 65 */               if (iTextComponent == null) {
/*    */                 
/*    */                 try {
/*    */                   
/* 69 */                   iTextComponent = ITextComponent.Serializer.fromJsonLenient(s);
/*    */                 }
/* 71 */                 catch (JsonParseException jsonParseException) {}
/*    */               }
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 77 */               if (iTextComponent == null)
/*    */               {
/* 79 */                 textComponentString = new TextComponentString(s);
/*    */               }
/*    */             }
/*    */             else {
/*    */               
/* 84 */               textComponentString = new TextComponentString(s);
/*    */             }
/*    */           
/*    */           } else {
/*    */             
/* 89 */             textComponentString = new TextComponentString("");
/*    */           } 
/*    */           
/* 92 */           nbttaglist.set(i, (NBTBase)new NBTTagString(ITextComponent.Serializer.componentToJson((ITextComponent)textComponentString)));
/*    */         } 
/*    */         
/* 95 */         nbttagcompound.setTag("pages", (NBTBase)nbttaglist);
/*    */       } 
/*    */     } 
/*    */     
/* 99 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\BookPagesStrictJSON.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */