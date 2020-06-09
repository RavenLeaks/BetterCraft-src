/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.util.ReportedException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompressedStreamTools
/*     */ {
/*     */   public static NBTTagCompound readCompressed(InputStream is) throws IOException {
/*     */     NBTTagCompound nbttagcompound;
/*  29 */     DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(is)));
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  34 */       nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
/*     */     }
/*     */     finally {
/*     */       
/*  38 */       datainputstream.close();
/*     */     } 
/*     */     
/*  41 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeCompressed(NBTTagCompound compound, OutputStream outputStream) throws IOException {
/*  49 */     DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)));
/*     */ 
/*     */     
/*     */     try {
/*  53 */       write(compound, dataoutputstream);
/*     */     }
/*     */     finally {
/*     */       
/*  57 */       dataoutputstream.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void safeWrite(NBTTagCompound compound, File fileIn) throws IOException {
/*  63 */     File file1 = new File(String.valueOf(fileIn.getAbsolutePath()) + "_tmp");
/*     */     
/*  65 */     if (file1.exists())
/*     */     {
/*  67 */       file1.delete();
/*     */     }
/*     */     
/*  70 */     write(compound, file1);
/*     */     
/*  72 */     if (fileIn.exists())
/*     */     {
/*  74 */       fileIn.delete();
/*     */     }
/*     */     
/*  77 */     if (fileIn.exists())
/*     */     {
/*  79 */       throw new IOException("Failed to delete " + fileIn);
/*     */     }
/*     */ 
/*     */     
/*  83 */     file1.renameTo(fileIn);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(NBTTagCompound compound, File fileIn) throws IOException {
/*  89 */     DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(fileIn));
/*     */ 
/*     */     
/*     */     try {
/*  93 */       write(compound, dataoutputstream);
/*     */     }
/*     */     finally {
/*     */       
/*  97 */       dataoutputstream.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static NBTTagCompound read(File fileIn) throws IOException {
/*     */     NBTTagCompound nbttagcompound;
/* 104 */     if (!fileIn.exists())
/*     */     {
/* 106 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 110 */     DataInputStream datainputstream = new DataInputStream(new FileInputStream(fileIn));
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 115 */       nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
/*     */     }
/*     */     finally {
/*     */       
/* 119 */       datainputstream.close();
/*     */     } 
/*     */     
/* 122 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound read(DataInputStream inputStream) throws IOException {
/* 131 */     return read(inputStream, NBTSizeTracker.INFINITE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound read(DataInput input, NBTSizeTracker accounter) throws IOException {
/* 139 */     NBTBase nbtbase = read(input, 0, accounter);
/*     */     
/* 141 */     if (nbtbase instanceof NBTTagCompound)
/*     */     {
/* 143 */       return (NBTTagCompound)nbtbase;
/*     */     }
/*     */ 
/*     */     
/* 147 */     throw new IOException("Root tag must be a named compound tag");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(NBTTagCompound compound, DataOutput output) throws IOException {
/* 153 */     writeTag(compound, output);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void writeTag(NBTBase tag, DataOutput output) throws IOException {
/* 158 */     output.writeByte(tag.getId());
/*     */     
/* 160 */     if (tag.getId() != 0) {
/*     */       
/* 162 */       output.writeUTF("");
/* 163 */       tag.write(output);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static NBTBase read(DataInput input, int depth, NBTSizeTracker accounter) throws IOException {
/* 169 */     byte b0 = input.readByte();
/*     */     
/* 171 */     if (b0 == 0)
/*     */     {
/* 173 */       return new NBTTagEnd();
/*     */     }
/*     */ 
/*     */     
/* 177 */     input.readUTF();
/* 178 */     NBTBase nbtbase = NBTBase.createNewByType(b0);
/*     */ 
/*     */     
/*     */     try {
/* 182 */       nbtbase.read(input, depth, accounter);
/* 183 */       return nbtbase;
/*     */     }
/* 185 */     catch (IOException ioexception) {
/*     */       
/* 187 */       CrashReport crashreport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
/* 188 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
/* 189 */       crashreportcategory.addCrashSection("Tag type", Byte.valueOf(b0));
/* 190 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\nbt\CompressedStreamTools.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */