/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.ResourcePackListEntry;
/*     */ import net.minecraft.client.resources.ResourcePackListEntryDefault;
/*     */ import net.minecraft.client.resources.ResourcePackListEntryFound;
/*     */ import net.minecraft.client.resources.ResourcePackListEntryServer;
/*     */ import net.minecraft.client.resources.ResourcePackRepository;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiScreenResourcePacks
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen parentScreen;
/*     */   private List<ResourcePackListEntry> availableResourcePacks;
/*     */   private List<ResourcePackListEntry> selectedResourcePacks;
/*     */   private GuiResourcePackAvailable availableResourcePacksList;
/*     */   private GuiResourcePackSelected selectedResourcePacksList;
/*     */   private boolean changed;
/*     */   
/*     */   public GuiScreenResourcePacks(GuiScreen parentScreenIn) {
/*  31 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  40 */     this.buttonList.add(new GuiOptionButton(2, this.width / 2 - 154, this.height - 48, I18n.format("resourcePack.openFolder", new Object[0])));
/*  41 */     this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 4, this.height - 48, I18n.format("gui.done", new Object[0])));
/*     */     
/*  43 */     if (!this.changed) {
/*     */       
/*  45 */       this.availableResourcePacks = Lists.newArrayList();
/*  46 */       this.selectedResourcePacks = Lists.newArrayList();
/*  47 */       ResourcePackRepository resourcepackrepository = this.mc.getResourcePackRepository();
/*  48 */       resourcepackrepository.updateRepositoryEntriesAll();
/*  49 */       List<ResourcePackRepository.Entry> list = Lists.newArrayList(resourcepackrepository.getRepositoryEntriesAll());
/*  50 */       list.removeAll(resourcepackrepository.getRepositoryEntries());
/*     */       
/*  52 */       for (ResourcePackRepository.Entry resourcepackrepository$entry : list)
/*     */       {
/*  54 */         this.availableResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry));
/*     */       }
/*     */       
/*  57 */       ResourcePackRepository.Entry resourcepackrepository$entry2 = resourcepackrepository.getResourcePackEntry();
/*     */       
/*  59 */       if (resourcepackrepository$entry2 != null)
/*     */       {
/*  61 */         this.selectedResourcePacks.add(new ResourcePackListEntryServer(this, resourcepackrepository.getResourcePackInstance()));
/*     */       }
/*     */       
/*  64 */       for (ResourcePackRepository.Entry resourcepackrepository$entry1 : Lists.reverse(resourcepackrepository.getRepositoryEntries()))
/*     */       {
/*  66 */         this.selectedResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry1));
/*     */       }
/*     */       
/*  69 */       this.selectedResourcePacks.add(new ResourcePackListEntryDefault(this));
/*     */     } 
/*     */     
/*  72 */     this.availableResourcePacksList = new GuiResourcePackAvailable(this.mc, 200, this.height, this.availableResourcePacks);
/*  73 */     this.availableResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 - 4 - 200);
/*  74 */     this.availableResourcePacksList.registerScrollButtons(7, 8);
/*  75 */     this.selectedResourcePacksList = new GuiResourcePackSelected(this.mc, 200, this.height, this.selectedResourcePacks);
/*  76 */     this.selectedResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 + 4);
/*  77 */     this.selectedResourcePacksList.registerScrollButtons(7, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  85 */     super.handleMouseInput();
/*  86 */     this.selectedResourcePacksList.handleMouseInput();
/*  87 */     this.availableResourcePacksList.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasResourcePackEntry(ResourcePackListEntry p_146961_1_) {
/*  92 */     return this.selectedResourcePacks.contains(p_146961_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ResourcePackListEntry> getListContaining(ResourcePackListEntry p_146962_1_) {
/*  97 */     return hasResourcePackEntry(p_146962_1_) ? this.selectedResourcePacks : this.availableResourcePacks;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ResourcePackListEntry> getAvailableResourcePacks() {
/* 102 */     return this.availableResourcePacks;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ResourcePackListEntry> getSelectedResourcePacks() {
/* 107 */     return this.selectedResourcePacks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 115 */     if (button.enabled)
/*     */     {
/* 117 */       if (button.id == 2) {
/*     */         
/* 119 */         File file1 = this.mc.getResourcePackRepository().getDirResourcepacks();
/* 120 */         OpenGlHelper.openFile(file1);
/*     */       }
/* 122 */       else if (button.id == 1) {
/*     */         
/* 124 */         if (this.changed) {
/*     */           
/* 126 */           List<ResourcePackRepository.Entry> list = Lists.newArrayList();
/*     */           
/* 128 */           for (ResourcePackListEntry resourcepacklistentry : this.selectedResourcePacks) {
/*     */             
/* 130 */             if (resourcepacklistentry instanceof ResourcePackListEntryFound)
/*     */             {
/* 132 */               list.add(((ResourcePackListEntryFound)resourcepacklistentry).getResourcePackEntry());
/*     */             }
/*     */           } 
/*     */           
/* 136 */           Collections.reverse(list);
/* 137 */           this.mc.getResourcePackRepository().setRepositories(list);
/* 138 */           this.mc.gameSettings.resourcePacks.clear();
/* 139 */           this.mc.gameSettings.incompatibleResourcePacks.clear();
/*     */           
/* 141 */           for (ResourcePackRepository.Entry resourcepackrepository$entry : list) {
/*     */             
/* 143 */             this.mc.gameSettings.resourcePacks.add(resourcepackrepository$entry.getResourcePackName());
/*     */             
/* 145 */             if (resourcepackrepository$entry.getPackFormat() != 3)
/*     */             {
/* 147 */               this.mc.gameSettings.incompatibleResourcePacks.add(resourcepackrepository$entry.getResourcePackName());
/*     */             }
/*     */           } 
/*     */           
/* 151 */           this.mc.gameSettings.saveOptions();
/* 152 */           this.mc.refreshResources();
/*     */         } 
/*     */         
/* 155 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 165 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 166 */     this.availableResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
/* 167 */     this.selectedResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 175 */     super.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 183 */     drawBackground(0);
/* 184 */     this.availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
/* 185 */     this.selectedResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
/* 186 */     drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), this.width / 2, 16, 16777215);
/* 187 */     drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), this.width / 2 - 77, this.height - 26, 8421504);
/* 188 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markChanged() {
/* 196 */     this.changed = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiScreenResourcePacks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */