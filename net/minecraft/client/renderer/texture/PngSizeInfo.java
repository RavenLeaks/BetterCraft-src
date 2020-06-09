/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import java.io.Closeable;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import net.minecraft.client.resources.IResource;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ 
/*    */ 
/*    */ public class PngSizeInfo
/*    */ {
/*    */   public final int pngWidth;
/*    */   public final int pngHeight;
/*    */   
/*    */   public PngSizeInfo(InputStream stream) throws IOException {
/* 17 */     DataInputStream datainputstream = new DataInputStream(stream);
/*    */     
/* 19 */     if (datainputstream.readLong() != -8552249625308161526L)
/*    */     {
/* 21 */       throw new IOException("Bad PNG Signature");
/*    */     }
/* 23 */     if (datainputstream.readInt() != 13)
/*    */     {
/* 25 */       throw new IOException("Bad length for IHDR chunk!");
/*    */     }
/* 27 */     if (datainputstream.readInt() != 1229472850)
/*    */     {
/* 29 */       throw new IOException("Bad type for IHDR chunk!");
/*    */     }
/*    */ 
/*    */     
/* 33 */     this.pngWidth = datainputstream.readInt();
/* 34 */     this.pngHeight = datainputstream.readInt();
/* 35 */     IOUtils.closeQuietly(datainputstream);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PngSizeInfo makeFromResource(IResource resource) throws IOException {
/*    */     PngSizeInfo pngsizeinfo;
/*    */     try {
/* 45 */       pngsizeinfo = new PngSizeInfo(resource.getInputStream());
/*    */     }
/*    */     finally {
/*    */       
/* 49 */       IOUtils.closeQuietly((Closeable)resource);
/*    */     } 
/*    */     
/* 52 */     return pngsizeinfo;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\texture\PngSizeInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */