/*    */ package net.minecraft.client.settings;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.nbt.CompressedStreamTools;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class CreativeSettings {
/* 12 */   private static final Logger field_192566_b = LogManager.getLogger();
/*    */   protected Minecraft field_192565_a;
/*    */   private final File field_192567_c;
/* 15 */   private final HotbarSnapshot[] field_192568_d = new HotbarSnapshot[9];
/*    */ 
/*    */   
/*    */   public CreativeSettings(Minecraft p_i47395_1_, File p_i47395_2_) {
/* 19 */     this.field_192565_a = p_i47395_1_;
/* 20 */     this.field_192567_c = new File(p_i47395_2_, "hotbar.nbt");
/*    */     
/* 22 */     for (int i = 0; i < 9; i++)
/*    */     {
/* 24 */       this.field_192568_d[i] = new HotbarSnapshot();
/*    */     }
/*    */     
/* 27 */     func_192562_a();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_192562_a() {
/*    */     try {
/* 34 */       NBTTagCompound nbttagcompound = CompressedStreamTools.read(this.field_192567_c);
/*    */       
/* 36 */       if (nbttagcompound == null) {
/*    */         return;
/*    */       }
/*    */ 
/*    */       
/* 41 */       for (int i = 0; i < 9; i++)
/*    */       {
/* 43 */         this.field_192568_d[i].func_192833_a(nbttagcompound.getTagList(String.valueOf(i), 10));
/*    */       }
/*    */     }
/* 46 */     catch (Exception exception) {
/*    */       
/* 48 */       field_192566_b.error("Failed to load creative mode options", exception);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_192564_b() {
/*    */     try {
/* 56 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*    */       
/* 58 */       for (int i = 0; i < 9; i++)
/*    */       {
/* 60 */         nbttagcompound.setTag(String.valueOf(i), (NBTBase)this.field_192568_d[i].func_192834_a());
/*    */       }
/*    */       
/* 63 */       CompressedStreamTools.write(nbttagcompound, this.field_192567_c);
/*    */     }
/* 65 */     catch (Exception exception) {
/*    */       
/* 67 */       field_192566_b.error("Failed to save creative mode options", exception);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public HotbarSnapshot func_192563_a(int p_192563_1_) {
/* 73 */     return this.field_192568_d[p_192563_1_];
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\settings\CreativeSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */