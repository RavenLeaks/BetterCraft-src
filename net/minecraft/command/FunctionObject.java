/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.FunctionManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class FunctionObject
/*     */ {
/*     */   private final Entry[] field_193530_b;
/*     */   
/*     */   public FunctionObject(Entry[] p_i47600_1_) {
/*  16 */     this.field_193530_b = p_i47600_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public Entry[] func_193528_a() {
/*  21 */     return this.field_193530_b;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FunctionObject func_193527_a(FunctionManager p_193527_0_, List<String> p_193527_1_) {
/*  26 */     List<Entry> list = Lists.newArrayListWithCapacity(p_193527_1_.size());
/*     */     
/*  28 */     for (String s : p_193527_1_) {
/*     */       
/*  30 */       s = s.trim();
/*     */       
/*  32 */       if (!s.startsWith("#") && !s.isEmpty()) {
/*     */         
/*  34 */         String[] astring = s.split(" ", 2);
/*  35 */         String s1 = astring[0];
/*     */         
/*  37 */         if (!p_193527_0_.func_193062_a().getCommands().containsKey(s1)) {
/*     */           
/*  39 */           if (s1.startsWith("//"))
/*     */           {
/*  41 */             throw new IllegalArgumentException("Unknown or invalid command '" + s1 + "' (if you intended to make a comment, use '#' not '//')");
/*     */           }
/*     */           
/*  44 */           if (s1.startsWith("/") && s1.length() > 1)
/*     */           {
/*  46 */             throw new IllegalArgumentException("Unknown or invalid command '" + s1 + "' (did you mean '" + s1.substring(1) + "'? Do not use a preceding forwards slash.)");
/*     */           }
/*     */           
/*  49 */           throw new IllegalArgumentException("Unknown or invalid command '" + s1 + "'");
/*     */         } 
/*     */         
/*  52 */         list.add(new CommandEntry(s));
/*     */       } 
/*     */     } 
/*     */     
/*  56 */     return new FunctionObject(list.<Entry>toArray(new Entry[list.size()]));
/*     */   }
/*     */   
/*     */   public static class CacheableFunction
/*     */   {
/*  61 */     public static final CacheableFunction field_193519_a = new CacheableFunction(null);
/*     */     
/*     */     @Nullable
/*     */     private final ResourceLocation field_193520_b;
/*     */     private boolean field_193521_c;
/*     */     private FunctionObject field_193522_d;
/*     */     
/*     */     public CacheableFunction(@Nullable ResourceLocation p_i47537_1_) {
/*  69 */       this.field_193520_b = p_i47537_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public CacheableFunction(FunctionObject p_i47602_1_) {
/*  74 */       this.field_193520_b = null;
/*  75 */       this.field_193522_d = p_i47602_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public FunctionObject func_193518_a(FunctionManager p_193518_1_) {
/*  81 */       if (!this.field_193521_c) {
/*     */         
/*  83 */         if (this.field_193520_b != null)
/*     */         {
/*  85 */           this.field_193522_d = p_193518_1_.func_193058_a(this.field_193520_b);
/*     */         }
/*     */         
/*  88 */         this.field_193521_c = true;
/*     */       } 
/*     */       
/*  91 */       return this.field_193522_d;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  96 */       return String.valueOf(this.field_193520_b);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CommandEntry
/*     */     implements Entry
/*     */   {
/*     */     private final String field_193525_a;
/*     */     
/*     */     public CommandEntry(String p_i47534_1_) {
/* 106 */       this.field_193525_a = p_i47534_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_194145_a(FunctionManager p_194145_1_, ICommandSender p_194145_2_, ArrayDeque<FunctionManager.QueuedCommand> p_194145_3_, int p_194145_4_) {
/* 111 */       p_194145_1_.func_193062_a().executeCommand(p_194145_2_, this.field_193525_a);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 116 */       return "/" + this.field_193525_a;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Entry
/*     */   {
/*     */     void func_194145_a(FunctionManager param1FunctionManager, ICommandSender param1ICommandSender, ArrayDeque<FunctionManager.QueuedCommand> param1ArrayDeque, int param1Int);
/*     */   }
/*     */   
/*     */   public static class FunctionEntry
/*     */     implements Entry
/*     */   {
/*     */     private final FunctionObject.CacheableFunction field_193524_a;
/*     */     
/*     */     public FunctionEntry(FunctionObject p_i47601_1_) {
/* 131 */       this.field_193524_a = new FunctionObject.CacheableFunction(p_i47601_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_194145_a(FunctionManager p_194145_1_, ICommandSender p_194145_2_, ArrayDeque<FunctionManager.QueuedCommand> p_194145_3_, int p_194145_4_) {
/* 136 */       FunctionObject functionobject = this.field_193524_a.func_193518_a(p_194145_1_);
/*     */       
/* 138 */       if (functionobject != null) {
/*     */         
/* 140 */         FunctionObject.Entry[] afunctionobject$entry = functionobject.func_193528_a();
/* 141 */         int i = p_194145_4_ - p_194145_3_.size();
/* 142 */         int j = Math.min(afunctionobject$entry.length, i);
/*     */         
/* 144 */         for (int k = j - 1; k >= 0; k--)
/*     */         {
/* 146 */           p_194145_3_.addFirst(new FunctionManager.QueuedCommand(p_194145_1_, p_194145_2_, afunctionobject$entry[k]));
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 153 */       return "/function " + this.field_193524_a;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\FunctionObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */