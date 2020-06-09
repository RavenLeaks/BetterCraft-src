/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketTabComplete;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public abstract class TabCompleter {
/*     */   protected final GuiTextField textField;
/*     */   protected final boolean hasTargetBlock;
/*     */   protected boolean didComplete;
/*     */   protected boolean requestedCompletions;
/*     */   protected int completionIdx;
/*  19 */   protected List<String> completions = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public TabCompleter(GuiTextField textFieldIn, boolean hasTargetBlockIn) {
/*  23 */     this.textField = textFieldIn;
/*  24 */     this.hasTargetBlock = hasTargetBlockIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void complete() {
/*  33 */     if (this.didComplete) {
/*     */       
/*  35 */       this.textField.deleteFromCursor(0);
/*  36 */       this.textField.deleteFromCursor(this.textField.getNthWordFromPosWS(-1, this.textField.getCursorPosition(), false) - this.textField.getCursorPosition());
/*     */       
/*  38 */       if (this.completionIdx >= this.completions.size())
/*     */       {
/*  40 */         this.completionIdx = 0;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  45 */       int i = this.textField.getNthWordFromPosWS(-1, this.textField.getCursorPosition(), false);
/*  46 */       this.completions.clear();
/*  47 */       this.completionIdx = 0;
/*  48 */       String s = this.textField.getText().substring(0, this.textField.getCursorPosition());
/*  49 */       requestCompletions(s);
/*     */       
/*  51 */       if (this.completions.isEmpty()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  56 */       this.didComplete = true;
/*  57 */       this.textField.deleteFromCursor(i - this.textField.getCursorPosition());
/*     */     } 
/*     */     
/*  60 */     this.textField.writeText(this.completions.get(this.completionIdx++));
/*     */   }
/*     */ 
/*     */   
/*     */   private void requestCompletions(String prefix) {
/*  65 */     if (prefix.length() >= 1) {
/*     */       
/*  67 */       (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketTabComplete(prefix, getTargetBlockPos(), this.hasTargetBlock));
/*  68 */       this.requestedCompletions = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public abstract BlockPos getTargetBlockPos();
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCompletions(String... newCompl) {
/*  80 */     if (this.requestedCompletions) {
/*     */       
/*  82 */       this.didComplete = false;
/*  83 */       this.completions.clear(); byte b; int i;
/*     */       String[] arrayOfString;
/*  85 */       for (i = (arrayOfString = newCompl).length, b = 0; b < i; ) { String s = arrayOfString[b];
/*     */         
/*  87 */         if (!s.isEmpty())
/*     */         {
/*  89 */           this.completions.add(s);
/*     */         }
/*     */         b++; }
/*     */       
/*  93 */       String s1 = this.textField.getText().substring(this.textField.getNthWordFromPosWS(-1, this.textField.getCursorPosition(), false));
/*  94 */       String s2 = StringUtils.getCommonPrefix(newCompl);
/*     */       
/*  96 */       if (!s2.isEmpty() && !s1.equalsIgnoreCase(s2)) {
/*     */         
/*  98 */         this.textField.deleteFromCursor(0);
/*  99 */         this.textField.deleteFromCursor(this.textField.getNthWordFromPosWS(-1, this.textField.getCursorPosition(), false) - this.textField.getCursorPosition());
/* 100 */         this.textField.writeText(s2);
/*     */       }
/* 102 */       else if (!this.completions.isEmpty()) {
/*     */         
/* 104 */         this.didComplete = true;
/* 105 */         complete();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetDidComplete() {
/* 115 */     this.didComplete = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetRequested() {
/* 120 */     this.requestedCompletions = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\TabCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */