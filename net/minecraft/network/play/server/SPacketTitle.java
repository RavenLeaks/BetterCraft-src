/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Locale;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ 
/*     */ 
/*     */ public class SPacketTitle
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private Type type;
/*     */   private ITextComponent message;
/*     */   private int fadeInTime;
/*     */   private int displayTime;
/*     */   private int fadeOutTime;
/*     */   
/*     */   public SPacketTitle() {}
/*     */   
/*     */   public SPacketTitle(Type typeIn, ITextComponent messageIn) {
/*  25 */     this(typeIn, messageIn, -1, -1, -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public SPacketTitle(int fadeInTimeIn, int displayTimeIn, int fadeOutTimeIn) {
/*  30 */     this(Type.TIMES, null, fadeInTimeIn, displayTimeIn, fadeOutTimeIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public SPacketTitle(Type typeIn, @Nullable ITextComponent messageIn, int fadeInTimeIn, int displayTimeIn, int fadeOutTimeIn) {
/*  35 */     this.type = typeIn;
/*  36 */     this.message = messageIn;
/*  37 */     this.fadeInTime = fadeInTimeIn;
/*  38 */     this.displayTime = displayTimeIn;
/*  39 */     this.fadeOutTime = fadeOutTimeIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  47 */     this.type = (Type)buf.readEnumValue(Type.class);
/*     */     
/*  49 */     if (this.type == Type.TITLE || this.type == Type.SUBTITLE || this.type == Type.ACTIONBAR)
/*     */     {
/*  51 */       this.message = buf.readTextComponent();
/*     */     }
/*     */     
/*  54 */     if (this.type == Type.TIMES) {
/*     */       
/*  56 */       this.fadeInTime = buf.readInt();
/*  57 */       this.displayTime = buf.readInt();
/*  58 */       this.fadeOutTime = buf.readInt();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  67 */     buf.writeEnumValue(this.type);
/*     */     
/*  69 */     if (this.type == Type.TITLE || this.type == Type.SUBTITLE || this.type == Type.ACTIONBAR)
/*     */     {
/*  71 */       buf.writeTextComponent(this.message);
/*     */     }
/*     */     
/*  74 */     if (this.type == Type.TIMES) {
/*     */       
/*  76 */       buf.writeInt(this.fadeInTime);
/*  77 */       buf.writeInt(this.displayTime);
/*  78 */       buf.writeInt(this.fadeOutTime);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  87 */     handler.handleTitle(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Type getType() {
/*  92 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent getMessage() {
/*  97 */     return this.message;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFadeInTime() {
/* 102 */     return this.fadeInTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDisplayTime() {
/* 107 */     return this.displayTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFadeOutTime() {
/* 112 */     return this.fadeOutTime;
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/* 117 */     TITLE,
/* 118 */     SUBTITLE,
/* 119 */     ACTIONBAR,
/* 120 */     TIMES,
/* 121 */     CLEAR,
/* 122 */     RESET; public static Type byName(String name) {
/*     */       byte b;
/*     */       int i;
/*     */       Type[] arrayOfType;
/* 126 */       for (i = (arrayOfType = values()).length, b = 0; b < i; ) { Type spackettitle$type = arrayOfType[b];
/*     */         
/* 128 */         if (spackettitle$type.name().equalsIgnoreCase(name))
/*     */         {
/* 130 */           return spackettitle$type;
/*     */         }
/*     */         b++; }
/*     */       
/* 134 */       return TITLE;
/*     */     }
/*     */ 
/*     */     
/*     */     public static String[] getNames() {
/* 139 */       String[] astring = new String[(values()).length];
/* 140 */       int i = 0; byte b; int j;
/*     */       Type[] arrayOfType;
/* 142 */       for (j = (arrayOfType = values()).length, b = 0; b < j; ) { Type spackettitle$type = arrayOfType[b];
/*     */         
/* 144 */         astring[i++] = spackettitle$type.name().toLowerCase(Locale.ROOT);
/*     */         b++; }
/*     */       
/* 147 */       return astring;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketTitle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */