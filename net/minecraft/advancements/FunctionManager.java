/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Files;
/*     */ import java.io.File;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.command.FunctionObject;
/*     */ import net.minecraft.command.ICommandManager;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class FunctionManager
/*     */   implements ITickable {
/*  24 */   private static final Logger field_193067_a = LogManager.getLogger();
/*     */   private final File field_193068_b;
/*     */   private final MinecraftServer field_193069_c;
/*  27 */   private final Map<ResourceLocation, FunctionObject> field_193070_d = Maps.newHashMap();
/*  28 */   private String field_193071_e = "-";
/*     */   private FunctionObject field_193072_f;
/*  30 */   private final ArrayDeque<QueuedCommand> field_194020_g = new ArrayDeque<>(); private boolean field_194021_h = false;
/*     */   
/*  32 */   private final ICommandSender field_193073_g = new ICommandSender()
/*     */     {
/*     */       public String getName()
/*     */       {
/*  36 */         return FunctionManager.this.field_193071_e;
/*     */       }
/*     */       
/*     */       public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  40 */         return (permLevel <= 2);
/*     */       }
/*     */       
/*     */       public World getEntityWorld() {
/*  44 */         return (World)FunctionManager.this.field_193069_c.worldServers[0];
/*     */       }
/*     */       
/*     */       public MinecraftServer getServer() {
/*  48 */         return FunctionManager.this.field_193069_c;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public FunctionManager(@Nullable File p_i47517_1_, MinecraftServer p_i47517_2_) {
/*  54 */     this.field_193068_b = p_i47517_1_;
/*  55 */     this.field_193069_c = p_i47517_2_;
/*  56 */     func_193059_f();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public FunctionObject func_193058_a(ResourceLocation p_193058_1_) {
/*  62 */     return this.field_193070_d.get(p_193058_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public ICommandManager func_193062_a() {
/*  67 */     return this.field_193069_c.getCommandManager();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_193065_c() {
/*  72 */     return this.field_193069_c.worldServers[0].getGameRules().getInt("maxCommandChainLength");
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ResourceLocation, FunctionObject> func_193066_d() {
/*  77 */     return this.field_193070_d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  85 */     String s = this.field_193069_c.worldServers[0].getGameRules().getString("gameLoopFunction");
/*     */     
/*  87 */     if (!s.equals(this.field_193071_e)) {
/*     */       
/*  89 */       this.field_193071_e = s;
/*  90 */       this.field_193072_f = func_193058_a(new ResourceLocation(s));
/*     */     } 
/*     */     
/*  93 */     if (this.field_193072_f != null)
/*     */     {
/*  95 */       func_194019_a(this.field_193072_f, this.field_193073_g);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_194019_a(FunctionObject p_194019_1_, ICommandSender p_194019_2_) {
/* 101 */     int l, i = func_193065_c();
/*     */     
/* 103 */     if (this.field_194021_h) {
/*     */       
/* 105 */       if (this.field_194020_g.size() < i)
/*     */       {
/* 107 */         this.field_194020_g.addFirst(new QueuedCommand(this, p_194019_2_, (FunctionObject.Entry)new FunctionObject.FunctionEntry(p_194019_1_)));
/*     */       }
/*     */       
/* 110 */       return 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     try { this.field_194021_h = true;
/* 119 */       int j = 0;
/* 120 */       FunctionObject.Entry[] afunctionobject$entry = p_194019_1_.func_193528_a();
/*     */       
/* 122 */       for (int k = afunctionobject$entry.length - 1; k >= 0; k--)
/*     */       {
/* 124 */         this.field_194020_g.push(new QueuedCommand(this, p_194019_2_, afunctionobject$entry[k]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */        }
/*     */     
/*     */     finally
/*     */     
/*     */     { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       this.field_194020_g.clear();
/* 149 */       this.field_194021_h = false; }  this.field_194020_g.clear(); this.field_194021_h = false;
/*     */ 
/*     */     
/* 152 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_193059_f() {
/* 158 */     this.field_193070_d.clear();
/* 159 */     this.field_193072_f = null;
/* 160 */     this.field_193071_e = "-";
/* 161 */     func_193061_h();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_193061_h() {
/* 166 */     if (this.field_193068_b != null) {
/*     */       
/* 168 */       this.field_193068_b.mkdirs();
/*     */       
/* 170 */       for (File file1 : FileUtils.listFiles(this.field_193068_b, new String[] { "mcfunction" }, true)) {
/*     */         
/* 172 */         String s = FilenameUtils.removeExtension(this.field_193068_b.toURI().relativize(file1.toURI()).toString());
/* 173 */         String[] astring = s.split("/", 2);
/*     */         
/* 175 */         if (astring.length == 2) {
/*     */           
/* 177 */           ResourceLocation resourcelocation = new ResourceLocation(astring[0], astring[1]);
/*     */ 
/*     */           
/*     */           try {
/* 181 */             this.field_193070_d.put(resourcelocation, FunctionObject.func_193527_a(this, Files.readLines(file1, StandardCharsets.UTF_8)));
/*     */           }
/* 183 */           catch (Throwable throwable) {
/*     */             
/* 185 */             field_193067_a.error("Couldn't read custom function " + resourcelocation + " from " + file1, throwable);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 190 */       if (!this.field_193070_d.isEmpty())
/*     */       {
/* 192 */         field_193067_a.info("Loaded " + this.field_193070_d.size() + " custom command functions");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static class QueuedCommand
/*     */   {
/*     */     private final FunctionManager field_194223_a;
/*     */     private final ICommandSender field_194224_b;
/*     */     private final FunctionObject.Entry field_194225_c;
/*     */     
/*     */     public QueuedCommand(FunctionManager p_i47603_1_, ICommandSender p_i47603_2_, FunctionObject.Entry p_i47603_3_) {
/* 205 */       this.field_194223_a = p_i47603_1_;
/* 206 */       this.field_194224_b = p_i47603_2_;
/* 207 */       this.field_194225_c = p_i47603_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_194222_a(ArrayDeque<QueuedCommand> p_194222_1_, int p_194222_2_) {
/* 212 */       this.field_194225_c.func_194145_a(this.field_194223_a, this.field_194224_b, p_194222_1_, p_194222_2_);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 217 */       return this.field_194225_c.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\FunctionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */