/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.AnvilConverterException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.WorldSummary;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiListWorldSelection
/*     */   extends GuiListExtended {
/*  17 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private final GuiWorldSelection worldSelectionObj;
/*  19 */   private final List<GuiListWorldSelectionEntry> entries = Lists.newArrayList();
/*     */ 
/*     */   
/*  22 */   private int selectedIdx = -1;
/*     */ 
/*     */   
/*     */   public GuiListWorldSelection(GuiWorldSelection p_i46590_1_, Minecraft clientIn, int p_i46590_3_, int p_i46590_4_, int p_i46590_5_, int p_i46590_6_, int p_i46590_7_) {
/*  26 */     super(clientIn, p_i46590_3_, p_i46590_4_, p_i46590_5_, p_i46590_6_, p_i46590_7_);
/*  27 */     this.worldSelectionObj = p_i46590_1_;
/*  28 */     refreshList();
/*     */   }
/*     */   
/*     */   public void refreshList() {
/*     */     List<WorldSummary> list;
/*  33 */     ISaveFormat isaveformat = this.mc.getSaveLoader();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  38 */       list = isaveformat.getSaveList();
/*     */     }
/*  40 */     catch (AnvilConverterException anvilconverterexception) {
/*     */       
/*  42 */       LOGGER.error("Couldn't load level list", (Throwable)anvilconverterexception);
/*  43 */       this.mc.displayGuiScreen(new GuiErrorScreen(I18n.format("selectWorld.unable_to_load", new Object[0]), anvilconverterexception.getMessage()));
/*     */       
/*     */       return;
/*     */     } 
/*  47 */     Collections.sort(list);
/*     */     
/*  49 */     for (WorldSummary worldsummary : list)
/*     */     {
/*  51 */       this.entries.add(new GuiListWorldSelectionEntry(this, worldsummary, this.mc.getSaveLoader()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiListWorldSelectionEntry getListEntry(int index) {
/*  60 */     return this.entries.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getSize() {
/*  65 */     return this.entries.size();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/*  70 */     return super.getScrollBarX() + 20;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/*  78 */     return super.getListWidth() + 50;
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectWorld(int idx) {
/*  83 */     this.selectedIdx = idx;
/*  84 */     this.worldSelectionObj.selectWorld(getSelectedWorld());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSelected(int slotIndex) {
/*  92 */     return (slotIndex == this.selectedIdx);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public GuiListWorldSelectionEntry getSelectedWorld() {
/*  98 */     return (this.selectedIdx >= 0 && this.selectedIdx < getSize()) ? getListEntry(this.selectedIdx) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiWorldSelection getGuiWorldSelection() {
/* 103 */     return this.worldSelectionObj;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiListWorldSelection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */