/*     */ package net.minecraft.world.gen.structure.template;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IFixType;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class TemplateManager {
/*  22 */   private final Map<String, Template> templates = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   private final String baseFolder;
/*     */ 
/*     */   
/*     */   private final DataFixer field_191154_c;
/*     */ 
/*     */   
/*     */   public TemplateManager(String p_i47239_1_, DataFixer p_i47239_2_) {
/*  32 */     this.baseFolder = p_i47239_1_;
/*  33 */     this.field_191154_c = p_i47239_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public Template getTemplate(@Nullable MinecraftServer server, ResourceLocation id) {
/*  38 */     Template template = get(server, id);
/*     */     
/*  40 */     if (template == null) {
/*     */       
/*  42 */       template = new Template();
/*  43 */       this.templates.put(id.getResourcePath(), template);
/*     */     } 
/*     */     
/*  46 */     return template;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Template get(@Nullable MinecraftServer p_189942_1_, ResourceLocation p_189942_2_) {
/*  52 */     String s = p_189942_2_.getResourcePath();
/*     */     
/*  54 */     if (this.templates.containsKey(s))
/*     */     {
/*  56 */       return this.templates.get(s);
/*     */     }
/*     */ 
/*     */     
/*  60 */     if (p_189942_1_ == null) {
/*     */       
/*  62 */       readTemplateFromJar(p_189942_2_);
/*     */     }
/*     */     else {
/*     */       
/*  66 */       readTemplate(p_189942_2_);
/*     */     } 
/*     */     
/*  69 */     return this.templates.containsKey(s) ? this.templates.get(s) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean readTemplate(ResourceLocation server) {
/*     */     boolean flag;
/*  80 */     String s = server.getResourcePath();
/*  81 */     File file1 = new File(this.baseFolder, String.valueOf(s) + ".nbt");
/*     */     
/*  83 */     if (!file1.exists())
/*     */     {
/*  85 */       return readTemplateFromJar(server);
/*     */     }
/*     */ 
/*     */     
/*  89 */     InputStream inputstream = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  94 */       inputstream = new FileInputStream(file1);
/*  95 */       readTemplateFromStream(s, inputstream);
/*  96 */       return true;
/*     */     }
/*  98 */     catch (Throwable var10) {
/*     */       
/* 100 */       flag = false;
/*     */     }
/*     */     finally {
/*     */       
/* 104 */       IOUtils.closeQuietly(inputstream);
/*     */     } 
/*     */     
/* 107 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean readTemplateFromJar(ResourceLocation id) {
/*     */     boolean flag;
/* 116 */     String s = id.getResourceDomain();
/* 117 */     String s1 = id.getResourcePath();
/* 118 */     InputStream inputstream = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 123 */       inputstream = MinecraftServer.class.getResourceAsStream("/assets/" + s + "/structures/" + s1 + ".nbt");
/* 124 */       readTemplateFromStream(s1, inputstream);
/* 125 */       return true;
/*     */     }
/* 127 */     catch (Throwable var10) {
/*     */       
/* 129 */       flag = false;
/*     */     }
/*     */     finally {
/*     */       
/* 133 */       IOUtils.closeQuietly(inputstream);
/*     */     } 
/*     */     
/* 136 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readTemplateFromStream(String id, InputStream stream) throws IOException {
/* 144 */     NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(stream);
/*     */     
/* 146 */     if (!nbttagcompound.hasKey("DataVersion", 99))
/*     */     {
/* 148 */       nbttagcompound.setInteger("DataVersion", 500);
/*     */     }
/*     */     
/* 151 */     Template template = new Template();
/* 152 */     template.read(this.field_191154_c.process((IFixType)FixTypes.STRUCTURE, nbttagcompound));
/* 153 */     this.templates.put(id, template);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean writeTemplate(@Nullable MinecraftServer server, ResourceLocation id) {
/* 161 */     String s = id.getResourcePath();
/*     */     
/* 163 */     if (server != null && this.templates.containsKey(s)) {
/*     */       boolean flag;
/* 165 */       File file1 = new File(this.baseFolder);
/*     */       
/* 167 */       if (!file1.exists()) {
/*     */         
/* 169 */         if (!file1.mkdirs())
/*     */         {
/* 171 */           return false;
/*     */         }
/*     */       }
/* 174 */       else if (!file1.isDirectory()) {
/*     */         
/* 176 */         return false;
/*     */       } 
/*     */       
/* 179 */       File file2 = new File(file1, String.valueOf(s) + ".nbt");
/* 180 */       Template template = this.templates.get(s);
/* 181 */       OutputStream outputstream = null;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 186 */         NBTTagCompound nbttagcompound = template.writeToNBT(new NBTTagCompound());
/* 187 */         outputstream = new FileOutputStream(file2);
/* 188 */         CompressedStreamTools.writeCompressed(nbttagcompound, outputstream);
/* 189 */         return true;
/*     */       }
/* 191 */       catch (Throwable var13) {
/*     */         
/* 193 */         flag = false;
/*     */       }
/*     */       finally {
/*     */         
/* 197 */         IOUtils.closeQuietly(outputstream);
/*     */       } 
/*     */       
/* 200 */       return flag;
/*     */     } 
/*     */ 
/*     */     
/* 204 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(ResourceLocation p_189941_1_) {
/* 210 */     this.templates.remove(p_189941_1_.getResourcePath());
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\template\TemplateManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */