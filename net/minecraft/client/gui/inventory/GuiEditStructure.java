/*     */ package net.minecraft.client.gui.inventory;
/*     */ import com.google.common.collect.Lists;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*     */ import net.minecraft.tileentity.TileEntityStructure;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiEditStructure extends GuiScreen {
/*  27 */   private static final Logger LOGGER = LogManager.getLogger();
/*  28 */   public static final int[] LEGAL_KEY_CODES = new int[] { 203, 205, 14, 211, 199, 207 };
/*     */   private final TileEntityStructure tileStructure;
/*  30 */   private Mirror mirror = Mirror.NONE;
/*  31 */   private Rotation rotation = Rotation.NONE;
/*  32 */   private TileEntityStructure.Mode mode = TileEntityStructure.Mode.DATA;
/*     */   private boolean ignoreEntities;
/*     */   private boolean showAir;
/*     */   private boolean showBoundingBox;
/*     */   private GuiTextField nameEdit;
/*     */   private GuiTextField posXEdit;
/*     */   private GuiTextField posYEdit;
/*     */   private GuiTextField posZEdit;
/*     */   private GuiTextField sizeXEdit;
/*     */   private GuiTextField sizeYEdit;
/*     */   private GuiTextField sizeZEdit;
/*     */   private GuiTextField integrityEdit;
/*     */   private GuiTextField seedEdit;
/*     */   private GuiTextField dataEdit;
/*     */   private GuiButton doneButton;
/*     */   private GuiButton cancelButton;
/*     */   private GuiButton saveButton;
/*     */   private GuiButton loadButton;
/*     */   private GuiButton rotateZeroDegreesButton;
/*     */   private GuiButton rotateNinetyDegreesButton;
/*     */   private GuiButton rotate180DegreesButton;
/*     */   private GuiButton rotate270DegressButton;
/*     */   private GuiButton modeButton;
/*     */   private GuiButton detectSizeButton;
/*     */   private GuiButton showEntitiesButton;
/*     */   private GuiButton mirrorButton;
/*     */   private GuiButton showAirButton;
/*     */   private GuiButton showBoundingBoxButton;
/*  60 */   private final List<GuiTextField> tabOrder = Lists.newArrayList();
/*  61 */   private final DecimalFormat decimalFormat = new DecimalFormat("0.0###");
/*     */ 
/*     */   
/*     */   public GuiEditStructure(TileEntityStructure p_i47142_1_) {
/*  65 */     this.tileStructure = p_i47142_1_;
/*  66 */     this.decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  74 */     this.nameEdit.updateCursorCounter();
/*  75 */     this.posXEdit.updateCursorCounter();
/*  76 */     this.posYEdit.updateCursorCounter();
/*  77 */     this.posZEdit.updateCursorCounter();
/*  78 */     this.sizeXEdit.updateCursorCounter();
/*  79 */     this.sizeYEdit.updateCursorCounter();
/*  80 */     this.sizeZEdit.updateCursorCounter();
/*  81 */     this.integrityEdit.updateCursorCounter();
/*  82 */     this.seedEdit.updateCursorCounter();
/*  83 */     this.dataEdit.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  92 */     Keyboard.enableRepeatEvents(true);
/*  93 */     this.buttonList.clear();
/*  94 */     this.doneButton = addButton(new GuiButton(0, this.width / 2 - 4 - 150, 210, 150, 20, I18n.format("gui.done", new Object[0])));
/*  95 */     this.cancelButton = addButton(new GuiButton(1, this.width / 2 + 4, 210, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  96 */     this.saveButton = addButton(new GuiButton(9, this.width / 2 + 4 + 100, 185, 50, 20, I18n.format("structure_block.button.save", new Object[0])));
/*  97 */     this.loadButton = addButton(new GuiButton(10, this.width / 2 + 4 + 100, 185, 50, 20, I18n.format("structure_block.button.load", new Object[0])));
/*  98 */     this.modeButton = addButton(new GuiButton(18, this.width / 2 - 4 - 150, 185, 50, 20, "MODE"));
/*  99 */     this.detectSizeButton = addButton(new GuiButton(19, this.width / 2 + 4 + 100, 120, 50, 20, I18n.format("structure_block.button.detect_size", new Object[0])));
/* 100 */     this.showEntitiesButton = addButton(new GuiButton(20, this.width / 2 + 4 + 100, 160, 50, 20, "ENTITIES"));
/* 101 */     this.mirrorButton = addButton(new GuiButton(21, this.width / 2 - 20, 185, 40, 20, "MIRROR"));
/* 102 */     this.showAirButton = addButton(new GuiButton(22, this.width / 2 + 4 + 100, 80, 50, 20, "SHOWAIR"));
/* 103 */     this.showBoundingBoxButton = addButton(new GuiButton(23, this.width / 2 + 4 + 100, 80, 50, 20, "SHOWBB"));
/* 104 */     this.rotateZeroDegreesButton = addButton(new GuiButton(11, this.width / 2 - 1 - 40 - 1 - 40 - 20, 185, 40, 20, "0"));
/* 105 */     this.rotateNinetyDegreesButton = addButton(new GuiButton(12, this.width / 2 - 1 - 40 - 20, 185, 40, 20, "90"));
/* 106 */     this.rotate180DegreesButton = addButton(new GuiButton(13, this.width / 2 + 1 + 20, 185, 40, 20, "180"));
/* 107 */     this.rotate270DegressButton = addButton(new GuiButton(14, this.width / 2 + 1 + 40 + 1 + 20, 185, 40, 20, "270"));
/* 108 */     this.nameEdit = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 152, 40, 300, 20);
/* 109 */     this.nameEdit.setMaxStringLength(64);
/* 110 */     this.nameEdit.setText(this.tileStructure.getName());
/* 111 */     this.tabOrder.add(this.nameEdit);
/* 112 */     BlockPos blockpos = this.tileStructure.getPosition();
/* 113 */     this.posXEdit = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 152, 80, 80, 20);
/* 114 */     this.posXEdit.setMaxStringLength(15);
/* 115 */     this.posXEdit.setText(Integer.toString(blockpos.getX()));
/* 116 */     this.tabOrder.add(this.posXEdit);
/* 117 */     this.posYEdit = new GuiTextField(4, this.fontRendererObj, this.width / 2 - 72, 80, 80, 20);
/* 118 */     this.posYEdit.setMaxStringLength(15);
/* 119 */     this.posYEdit.setText(Integer.toString(blockpos.getY()));
/* 120 */     this.tabOrder.add(this.posYEdit);
/* 121 */     this.posZEdit = new GuiTextField(5, this.fontRendererObj, this.width / 2 + 8, 80, 80, 20);
/* 122 */     this.posZEdit.setMaxStringLength(15);
/* 123 */     this.posZEdit.setText(Integer.toString(blockpos.getZ()));
/* 124 */     this.tabOrder.add(this.posZEdit);
/* 125 */     BlockPos blockpos1 = this.tileStructure.getStructureSize();
/* 126 */     this.sizeXEdit = new GuiTextField(6, this.fontRendererObj, this.width / 2 - 152, 120, 80, 20);
/* 127 */     this.sizeXEdit.setMaxStringLength(15);
/* 128 */     this.sizeXEdit.setText(Integer.toString(blockpos1.getX()));
/* 129 */     this.tabOrder.add(this.sizeXEdit);
/* 130 */     this.sizeYEdit = new GuiTextField(7, this.fontRendererObj, this.width / 2 - 72, 120, 80, 20);
/* 131 */     this.sizeYEdit.setMaxStringLength(15);
/* 132 */     this.sizeYEdit.setText(Integer.toString(blockpos1.getY()));
/* 133 */     this.tabOrder.add(this.sizeYEdit);
/* 134 */     this.sizeZEdit = new GuiTextField(8, this.fontRendererObj, this.width / 2 + 8, 120, 80, 20);
/* 135 */     this.sizeZEdit.setMaxStringLength(15);
/* 136 */     this.sizeZEdit.setText(Integer.toString(blockpos1.getZ()));
/* 137 */     this.tabOrder.add(this.sizeZEdit);
/* 138 */     this.integrityEdit = new GuiTextField(15, this.fontRendererObj, this.width / 2 - 152, 120, 80, 20);
/* 139 */     this.integrityEdit.setMaxStringLength(15);
/* 140 */     this.integrityEdit.setText(this.decimalFormat.format(this.tileStructure.getIntegrity()));
/* 141 */     this.tabOrder.add(this.integrityEdit);
/* 142 */     this.seedEdit = new GuiTextField(16, this.fontRendererObj, this.width / 2 - 72, 120, 80, 20);
/* 143 */     this.seedEdit.setMaxStringLength(31);
/* 144 */     this.seedEdit.setText(Long.toString(this.tileStructure.getSeed()));
/* 145 */     this.tabOrder.add(this.seedEdit);
/* 146 */     this.dataEdit = new GuiTextField(17, this.fontRendererObj, this.width / 2 - 152, 120, 240, 20);
/* 147 */     this.dataEdit.setMaxStringLength(128);
/* 148 */     this.dataEdit.setText(this.tileStructure.getMetadata());
/* 149 */     this.tabOrder.add(this.dataEdit);
/* 150 */     this.mirror = this.tileStructure.getMirror();
/* 151 */     updateMirrorButton();
/* 152 */     this.rotation = this.tileStructure.getRotation();
/* 153 */     updateDirectionButtons();
/* 154 */     this.mode = this.tileStructure.getMode();
/* 155 */     updateMode();
/* 156 */     this.ignoreEntities = this.tileStructure.ignoresEntities();
/* 157 */     updateEntitiesButton();
/* 158 */     this.showAir = this.tileStructure.showsAir();
/* 159 */     updateToggleAirButton();
/* 160 */     this.showBoundingBox = this.tileStructure.showsBoundingBox();
/* 161 */     updateToggleBoundingBox();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 169 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 177 */     if (button.enabled)
/*     */     {
/* 179 */       if (button.id == 1) {
/*     */         
/* 181 */         this.tileStructure.setMirror(this.mirror);
/* 182 */         this.tileStructure.setRotation(this.rotation);
/* 183 */         this.tileStructure.setMode(this.mode);
/* 184 */         this.tileStructure.setIgnoresEntities(this.ignoreEntities);
/* 185 */         this.tileStructure.setShowAir(this.showAir);
/* 186 */         this.tileStructure.setShowBoundingBox(this.showBoundingBox);
/* 187 */         this.mc.displayGuiScreen(null);
/*     */       }
/* 189 */       else if (button.id == 0) {
/*     */         
/* 191 */         if (sendToServer(1))
/*     */         {
/* 193 */           this.mc.displayGuiScreen(null);
/*     */         }
/*     */       }
/* 196 */       else if (button.id == 9) {
/*     */         
/* 198 */         if (this.tileStructure.getMode() == TileEntityStructure.Mode.SAVE)
/*     */         {
/* 200 */           sendToServer(2);
/* 201 */           this.mc.displayGuiScreen(null);
/*     */         }
/*     */       
/* 204 */       } else if (button.id == 10) {
/*     */         
/* 206 */         if (this.tileStructure.getMode() == TileEntityStructure.Mode.LOAD)
/*     */         {
/* 208 */           sendToServer(3);
/* 209 */           this.mc.displayGuiScreen(null);
/*     */         }
/*     */       
/* 212 */       } else if (button.id == 11) {
/*     */         
/* 214 */         this.tileStructure.setRotation(Rotation.NONE);
/* 215 */         updateDirectionButtons();
/*     */       }
/* 217 */       else if (button.id == 12) {
/*     */         
/* 219 */         this.tileStructure.setRotation(Rotation.CLOCKWISE_90);
/* 220 */         updateDirectionButtons();
/*     */       }
/* 222 */       else if (button.id == 13) {
/*     */         
/* 224 */         this.tileStructure.setRotation(Rotation.CLOCKWISE_180);
/* 225 */         updateDirectionButtons();
/*     */       }
/* 227 */       else if (button.id == 14) {
/*     */         
/* 229 */         this.tileStructure.setRotation(Rotation.COUNTERCLOCKWISE_90);
/* 230 */         updateDirectionButtons();
/*     */       }
/* 232 */       else if (button.id == 18) {
/*     */         
/* 234 */         this.tileStructure.nextMode();
/* 235 */         updateMode();
/*     */       }
/* 237 */       else if (button.id == 19) {
/*     */         
/* 239 */         if (this.tileStructure.getMode() == TileEntityStructure.Mode.SAVE)
/*     */         {
/* 241 */           sendToServer(4);
/* 242 */           this.mc.displayGuiScreen(null);
/*     */         }
/*     */       
/* 245 */       } else if (button.id == 20) {
/*     */         
/* 247 */         this.tileStructure.setIgnoresEntities(!this.tileStructure.ignoresEntities());
/* 248 */         updateEntitiesButton();
/*     */       }
/* 250 */       else if (button.id == 22) {
/*     */         
/* 252 */         this.tileStructure.setShowAir(!this.tileStructure.showsAir());
/* 253 */         updateToggleAirButton();
/*     */       }
/* 255 */       else if (button.id == 23) {
/*     */         
/* 257 */         this.tileStructure.setShowBoundingBox(!this.tileStructure.showsBoundingBox());
/* 258 */         updateToggleBoundingBox();
/*     */       }
/* 260 */       else if (button.id == 21) {
/*     */         
/* 262 */         switch (this.tileStructure.getMirror()) {
/*     */           
/*     */           case NONE:
/* 265 */             this.tileStructure.setMirror(Mirror.LEFT_RIGHT);
/*     */             break;
/*     */           
/*     */           case LEFT_RIGHT:
/* 269 */             this.tileStructure.setMirror(Mirror.FRONT_BACK);
/*     */             break;
/*     */           
/*     */           case null:
/* 273 */             this.tileStructure.setMirror(Mirror.NONE);
/*     */             break;
/*     */         } 
/* 276 */         updateMirrorButton();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateEntitiesButton() {
/* 283 */     boolean flag = !this.tileStructure.ignoresEntities();
/*     */     
/* 285 */     if (flag) {
/*     */       
/* 287 */       this.showEntitiesButton.displayString = I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 291 */       this.showEntitiesButton.displayString = I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateToggleAirButton() {
/* 297 */     boolean flag = this.tileStructure.showsAir();
/*     */     
/* 299 */     if (flag) {
/*     */       
/* 301 */       this.showAirButton.displayString = I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 305 */       this.showAirButton.displayString = I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateToggleBoundingBox() {
/* 311 */     boolean flag = this.tileStructure.showsBoundingBox();
/*     */     
/* 313 */     if (flag) {
/*     */       
/* 315 */       this.showBoundingBoxButton.displayString = I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 319 */       this.showBoundingBoxButton.displayString = I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateMirrorButton() {
/* 325 */     Mirror mirror = this.tileStructure.getMirror();
/*     */     
/* 327 */     switch (mirror) {
/*     */       
/*     */       case NONE:
/* 330 */         this.mirrorButton.displayString = "|";
/*     */         break;
/*     */       
/*     */       case LEFT_RIGHT:
/* 334 */         this.mirrorButton.displayString = "< >";
/*     */         break;
/*     */       
/*     */       case null:
/* 338 */         this.mirrorButton.displayString = "^ v";
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateDirectionButtons() {
/* 344 */     this.rotateZeroDegreesButton.enabled = true;
/* 345 */     this.rotateNinetyDegreesButton.enabled = true;
/* 346 */     this.rotate180DegreesButton.enabled = true;
/* 347 */     this.rotate270DegressButton.enabled = true;
/*     */     
/* 349 */     switch (this.tileStructure.getRotation()) {
/*     */       
/*     */       case NONE:
/* 352 */         this.rotateZeroDegreesButton.enabled = false;
/*     */         break;
/*     */       
/*     */       case null:
/* 356 */         this.rotate180DegreesButton.enabled = false;
/*     */         break;
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 360 */         this.rotate270DegressButton.enabled = false;
/*     */         break;
/*     */       
/*     */       case CLOCKWISE_90:
/* 364 */         this.rotateNinetyDegreesButton.enabled = false;
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateMode() {
/* 370 */     this.nameEdit.setFocused(false);
/* 371 */     this.posXEdit.setFocused(false);
/* 372 */     this.posYEdit.setFocused(false);
/* 373 */     this.posZEdit.setFocused(false);
/* 374 */     this.sizeXEdit.setFocused(false);
/* 375 */     this.sizeYEdit.setFocused(false);
/* 376 */     this.sizeZEdit.setFocused(false);
/* 377 */     this.integrityEdit.setFocused(false);
/* 378 */     this.seedEdit.setFocused(false);
/* 379 */     this.dataEdit.setFocused(false);
/* 380 */     this.nameEdit.setVisible(false);
/* 381 */     this.nameEdit.setFocused(false);
/* 382 */     this.posXEdit.setVisible(false);
/* 383 */     this.posYEdit.setVisible(false);
/* 384 */     this.posZEdit.setVisible(false);
/* 385 */     this.sizeXEdit.setVisible(false);
/* 386 */     this.sizeYEdit.setVisible(false);
/* 387 */     this.sizeZEdit.setVisible(false);
/* 388 */     this.integrityEdit.setVisible(false);
/* 389 */     this.seedEdit.setVisible(false);
/* 390 */     this.dataEdit.setVisible(false);
/* 391 */     this.saveButton.visible = false;
/* 392 */     this.loadButton.visible = false;
/* 393 */     this.detectSizeButton.visible = false;
/* 394 */     this.showEntitiesButton.visible = false;
/* 395 */     this.mirrorButton.visible = false;
/* 396 */     this.rotateZeroDegreesButton.visible = false;
/* 397 */     this.rotateNinetyDegreesButton.visible = false;
/* 398 */     this.rotate180DegreesButton.visible = false;
/* 399 */     this.rotate270DegressButton.visible = false;
/* 400 */     this.showAirButton.visible = false;
/* 401 */     this.showBoundingBoxButton.visible = false;
/*     */     
/* 403 */     switch (this.tileStructure.getMode()) {
/*     */       
/*     */       case SAVE:
/* 406 */         this.nameEdit.setVisible(true);
/* 407 */         this.nameEdit.setFocused(true);
/* 408 */         this.posXEdit.setVisible(true);
/* 409 */         this.posYEdit.setVisible(true);
/* 410 */         this.posZEdit.setVisible(true);
/* 411 */         this.sizeXEdit.setVisible(true);
/* 412 */         this.sizeYEdit.setVisible(true);
/* 413 */         this.sizeZEdit.setVisible(true);
/* 414 */         this.saveButton.visible = true;
/* 415 */         this.detectSizeButton.visible = true;
/* 416 */         this.showEntitiesButton.visible = true;
/* 417 */         this.showAirButton.visible = true;
/*     */         break;
/*     */       
/*     */       case LOAD:
/* 421 */         this.nameEdit.setVisible(true);
/* 422 */         this.nameEdit.setFocused(true);
/* 423 */         this.posXEdit.setVisible(true);
/* 424 */         this.posYEdit.setVisible(true);
/* 425 */         this.posZEdit.setVisible(true);
/* 426 */         this.integrityEdit.setVisible(true);
/* 427 */         this.seedEdit.setVisible(true);
/* 428 */         this.loadButton.visible = true;
/* 429 */         this.showEntitiesButton.visible = true;
/* 430 */         this.mirrorButton.visible = true;
/* 431 */         this.rotateZeroDegreesButton.visible = true;
/* 432 */         this.rotateNinetyDegreesButton.visible = true;
/* 433 */         this.rotate180DegreesButton.visible = true;
/* 434 */         this.rotate270DegressButton.visible = true;
/* 435 */         this.showBoundingBoxButton.visible = true;
/* 436 */         updateDirectionButtons();
/*     */         break;
/*     */       
/*     */       case null:
/* 440 */         this.nameEdit.setVisible(true);
/* 441 */         this.nameEdit.setFocused(true);
/*     */         break;
/*     */       
/*     */       case DATA:
/* 445 */         this.dataEdit.setVisible(true);
/* 446 */         this.dataEdit.setFocused(true);
/*     */         break;
/*     */     } 
/* 449 */     this.modeButton.displayString = I18n.format("structure_block.mode." + this.tileStructure.getMode().getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean sendToServer(int p_189820_1_) {
/*     */     try {
/* 456 */       PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/* 457 */       this.tileStructure.writeCoordinates((ByteBuf)packetbuffer);
/* 458 */       packetbuffer.writeByte(p_189820_1_);
/* 459 */       packetbuffer.writeString(this.tileStructure.getMode().toString());
/* 460 */       packetbuffer.writeString(this.nameEdit.getText());
/* 461 */       packetbuffer.writeInt(parseCoordinate(this.posXEdit.getText()));
/* 462 */       packetbuffer.writeInt(parseCoordinate(this.posYEdit.getText()));
/* 463 */       packetbuffer.writeInt(parseCoordinate(this.posZEdit.getText()));
/* 464 */       packetbuffer.writeInt(parseCoordinate(this.sizeXEdit.getText()));
/* 465 */       packetbuffer.writeInt(parseCoordinate(this.sizeYEdit.getText()));
/* 466 */       packetbuffer.writeInt(parseCoordinate(this.sizeZEdit.getText()));
/* 467 */       packetbuffer.writeString(this.tileStructure.getMirror().toString());
/* 468 */       packetbuffer.writeString(this.tileStructure.getRotation().toString());
/* 469 */       packetbuffer.writeString(this.dataEdit.getText());
/* 470 */       packetbuffer.writeBoolean(this.tileStructure.ignoresEntities());
/* 471 */       packetbuffer.writeBoolean(this.tileStructure.showsAir());
/* 472 */       packetbuffer.writeBoolean(this.tileStructure.showsBoundingBox());
/* 473 */       packetbuffer.writeFloat(parseIntegrity(this.integrityEdit.getText()));
/* 474 */       packetbuffer.writeVarLong(parseSeed(this.seedEdit.getText()));
/* 475 */       this.mc.getConnection().sendPacket((Packet)new CPacketCustomPayload("MC|Struct", packetbuffer));
/* 476 */       return true;
/*     */     }
/* 478 */     catch (Exception exception) {
/*     */       
/* 480 */       LOGGER.warn("Could not send structure block info", exception);
/* 481 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private long parseSeed(String p_189821_1_) {
/*     */     try {
/* 489 */       return Long.valueOf(p_189821_1_).longValue();
/*     */     }
/* 491 */     catch (NumberFormatException var3) {
/*     */       
/* 493 */       return 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float parseIntegrity(String p_189819_1_) {
/*     */     try {
/* 501 */       return Float.valueOf(p_189819_1_).floatValue();
/*     */     }
/* 503 */     catch (NumberFormatException var3) {
/*     */       
/* 505 */       return 1.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int parseCoordinate(String p_189817_1_) {
/*     */     try {
/* 513 */       return Integer.parseInt(p_189817_1_);
/*     */     }
/* 515 */     catch (NumberFormatException var3) {
/*     */       
/* 517 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 527 */     if (this.nameEdit.getVisible() && isValidCharacterForName(typedChar, keyCode))
/*     */     {
/* 529 */       this.nameEdit.textboxKeyTyped(typedChar, keyCode);
/*     */     }
/*     */     
/* 532 */     if (this.posXEdit.getVisible())
/*     */     {
/* 534 */       this.posXEdit.textboxKeyTyped(typedChar, keyCode);
/*     */     }
/*     */     
/* 537 */     if (this.posYEdit.getVisible())
/*     */     {
/* 539 */       this.posYEdit.textboxKeyTyped(typedChar, keyCode);
/*     */     }
/*     */     
/* 542 */     if (this.posZEdit.getVisible())
/*     */     {
/* 544 */       this.posZEdit.textboxKeyTyped(typedChar, keyCode);
/*     */     }
/*     */     
/* 547 */     if (this.sizeXEdit.getVisible())
/*     */     {
/* 549 */       this.sizeXEdit.textboxKeyTyped(typedChar, keyCode);
/*     */     }
/*     */     
/* 552 */     if (this.sizeYEdit.getVisible())
/*     */     {
/* 554 */       this.sizeYEdit.textboxKeyTyped(typedChar, keyCode);
/*     */     }
/*     */     
/* 557 */     if (this.sizeZEdit.getVisible())
/*     */     {
/* 559 */       this.sizeZEdit.textboxKeyTyped(typedChar, keyCode);
/*     */     }
/*     */     
/* 562 */     if (this.integrityEdit.getVisible())
/*     */     {
/* 564 */       this.integrityEdit.textboxKeyTyped(typedChar, keyCode);
/*     */     }
/*     */     
/* 567 */     if (this.seedEdit.getVisible())
/*     */     {
/* 569 */       this.seedEdit.textboxKeyTyped(typedChar, keyCode);
/*     */     }
/*     */     
/* 572 */     if (this.dataEdit.getVisible())
/*     */     {
/* 574 */       this.dataEdit.textboxKeyTyped(typedChar, keyCode);
/*     */     }
/*     */     
/* 577 */     if (keyCode == 15) {
/*     */       
/* 579 */       GuiTextField guitextfield = null;
/* 580 */       GuiTextField guitextfield1 = null;
/*     */       
/* 582 */       for (GuiTextField guitextfield2 : this.tabOrder) {
/*     */         
/* 584 */         if (guitextfield != null && guitextfield2.getVisible()) {
/*     */           
/* 586 */           guitextfield1 = guitextfield2;
/*     */           
/*     */           break;
/*     */         } 
/* 590 */         if (guitextfield2.isFocused() && guitextfield2.getVisible())
/*     */         {
/* 592 */           guitextfield = guitextfield2;
/*     */         }
/*     */       } 
/*     */       
/* 596 */       if (guitextfield != null && guitextfield1 == null)
/*     */       {
/* 598 */         for (GuiTextField guitextfield3 : this.tabOrder) {
/*     */           
/* 600 */           if (guitextfield3.getVisible() && guitextfield3 != guitextfield) {
/*     */             
/* 602 */             guitextfield1 = guitextfield3;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 608 */       if (guitextfield1 != null && guitextfield1 != guitextfield) {
/*     */         
/* 610 */         guitextfield.setFocused(false);
/* 611 */         guitextfield1.setFocused(true);
/*     */       } 
/*     */     } 
/*     */     
/* 615 */     if (keyCode != 28 && keyCode != 156) {
/*     */       
/* 617 */       if (keyCode == 1)
/*     */       {
/* 619 */         actionPerformed(this.cancelButton);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 624 */       actionPerformed(this.doneButton);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidCharacterForName(char p_190301_0_, int p_190301_1_) {
/* 630 */     boolean flag = true; byte b;
/*     */     int i, arrayOfInt[];
/* 632 */     for (i = (arrayOfInt = LEGAL_KEY_CODES).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/*     */       
/* 634 */       if (j == p_190301_1_)
/*     */       {
/* 636 */         return true; } 
/*     */       b++; }
/*     */     
/*     */     char[] arrayOfChar;
/* 640 */     for (i = (arrayOfChar = ChatAllowedCharacters.ILLEGAL_STRUCTURE_CHARACTERS).length, b = 0; b < i; ) { char c0 = arrayOfChar[b];
/*     */       
/* 642 */       if (c0 == p_190301_0_) {
/*     */         
/* 644 */         flag = false;
/*     */         break;
/*     */       } 
/*     */       b++; }
/*     */     
/* 649 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 657 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 659 */     if (this.nameEdit.getVisible())
/*     */     {
/* 661 */       this.nameEdit.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     
/* 664 */     if (this.posXEdit.getVisible())
/*     */     {
/* 666 */       this.posXEdit.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     
/* 669 */     if (this.posYEdit.getVisible())
/*     */     {
/* 671 */       this.posYEdit.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     
/* 674 */     if (this.posZEdit.getVisible())
/*     */     {
/* 676 */       this.posZEdit.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     
/* 679 */     if (this.sizeXEdit.getVisible())
/*     */     {
/* 681 */       this.sizeXEdit.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     
/* 684 */     if (this.sizeYEdit.getVisible())
/*     */     {
/* 686 */       this.sizeYEdit.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     
/* 689 */     if (this.sizeZEdit.getVisible())
/*     */     {
/* 691 */       this.sizeZEdit.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     
/* 694 */     if (this.integrityEdit.getVisible())
/*     */     {
/* 696 */       this.integrityEdit.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     
/* 699 */     if (this.seedEdit.getVisible())
/*     */     {
/* 701 */       this.seedEdit.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     
/* 704 */     if (this.dataEdit.getVisible())
/*     */     {
/* 706 */       this.dataEdit.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 715 */     drawDefaultBackground();
/* 716 */     TileEntityStructure.Mode tileentitystructure$mode = this.tileStructure.getMode();
/* 717 */     drawCenteredString(this.fontRendererObj, I18n.format("tile.structureBlock.name", new Object[0]), this.width / 2, 10, 16777215);
/*     */     
/* 719 */     if (tileentitystructure$mode != TileEntityStructure.Mode.DATA) {
/*     */       
/* 721 */       drawString(this.fontRendererObj, I18n.format("structure_block.structure_name", new Object[0]), this.width / 2 - 153, 30, 10526880);
/* 722 */       this.nameEdit.drawTextBox();
/*     */     } 
/*     */     
/* 725 */     if (tileentitystructure$mode == TileEntityStructure.Mode.LOAD || tileentitystructure$mode == TileEntityStructure.Mode.SAVE) {
/*     */       
/* 727 */       drawString(this.fontRendererObj, I18n.format("structure_block.position", new Object[0]), this.width / 2 - 153, 70, 10526880);
/* 728 */       this.posXEdit.drawTextBox();
/* 729 */       this.posYEdit.drawTextBox();
/* 730 */       this.posZEdit.drawTextBox();
/* 731 */       String s = I18n.format("structure_block.include_entities", new Object[0]);
/* 732 */       int i = this.fontRendererObj.getStringWidth(s);
/* 733 */       drawString(this.fontRendererObj, s, this.width / 2 + 154 - i, 150, 10526880);
/*     */     } 
/*     */     
/* 736 */     if (tileentitystructure$mode == TileEntityStructure.Mode.SAVE) {
/*     */       
/* 738 */       drawString(this.fontRendererObj, I18n.format("structure_block.size", new Object[0]), this.width / 2 - 153, 110, 10526880);
/* 739 */       this.sizeXEdit.drawTextBox();
/* 740 */       this.sizeYEdit.drawTextBox();
/* 741 */       this.sizeZEdit.drawTextBox();
/* 742 */       String s2 = I18n.format("structure_block.detect_size", new Object[0]);
/* 743 */       int k = this.fontRendererObj.getStringWidth(s2);
/* 744 */       drawString(this.fontRendererObj, s2, this.width / 2 + 154 - k, 110, 10526880);
/* 745 */       String s1 = I18n.format("structure_block.show_air", new Object[0]);
/* 746 */       int j = this.fontRendererObj.getStringWidth(s1);
/* 747 */       drawString(this.fontRendererObj, s1, this.width / 2 + 154 - j, 70, 10526880);
/*     */     } 
/*     */     
/* 750 */     if (tileentitystructure$mode == TileEntityStructure.Mode.LOAD) {
/*     */       
/* 752 */       drawString(this.fontRendererObj, I18n.format("structure_block.integrity", new Object[0]), this.width / 2 - 153, 110, 10526880);
/* 753 */       this.integrityEdit.drawTextBox();
/* 754 */       this.seedEdit.drawTextBox();
/* 755 */       String s3 = I18n.format("structure_block.show_boundingbox", new Object[0]);
/* 756 */       int l = this.fontRendererObj.getStringWidth(s3);
/* 757 */       drawString(this.fontRendererObj, s3, this.width / 2 + 154 - l, 70, 10526880);
/*     */     } 
/*     */     
/* 760 */     if (tileentitystructure$mode == TileEntityStructure.Mode.DATA) {
/*     */       
/* 762 */       drawString(this.fontRendererObj, I18n.format("structure_block.custom_data", new Object[0]), this.width / 2 - 153, 110, 10526880);
/* 763 */       this.dataEdit.drawTextBox();
/*     */     } 
/*     */     
/* 766 */     String s4 = "structure_block.mode_info." + tileentitystructure$mode.getName();
/* 767 */     drawString(this.fontRendererObj, I18n.format(s4, new Object[0]), this.width / 2 - 153, 174, 10526880);
/* 768 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 776 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\inventory\GuiEditStructure.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */