/*      */ package net.minecraft.client.entity;
/*      */ 
/*      */ import com.darkmagician6.eventapi.EventManager;
/*      */ import com.darkmagician6.eventapi.events.Event;
/*      */ import java.util.List;
/*      */ import javax.annotation.Nullable;
/*      */ import me.nzxter.bettercraft.commands.Command;
/*      */ import me.nzxter.bettercraft.commands.CommandManager;
/*      */ import me.nzxter.bettercraft.events.ChatMessageSendEvent;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.audio.ElytraSound;
/*      */ import net.minecraft.client.audio.ISound;
/*      */ import net.minecraft.client.audio.MovingSoundMinecartRiding;
/*      */ import net.minecraft.client.audio.PositionedSoundRecord;
/*      */ import net.minecraft.client.gui.GuiCommandBlock;
/*      */ import net.minecraft.client.gui.GuiEnchantment;
/*      */ import net.minecraft.client.gui.GuiHopper;
/*      */ import net.minecraft.client.gui.GuiMerchant;
/*      */ import net.minecraft.client.gui.GuiRepair;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.GuiScreenBook;
/*      */ import net.minecraft.client.gui.inventory.GuiBeacon;
/*      */ import net.minecraft.client.gui.inventory.GuiBrewingStand;
/*      */ import net.minecraft.client.gui.inventory.GuiChest;
/*      */ import net.minecraft.client.gui.inventory.GuiCrafting;
/*      */ import net.minecraft.client.gui.inventory.GuiDispenser;
/*      */ import net.minecraft.client.gui.inventory.GuiEditCommandBlockMinecart;
/*      */ import net.minecraft.client.gui.inventory.GuiEditSign;
/*      */ import net.minecraft.client.gui.inventory.GuiEditStructure;
/*      */ import net.minecraft.client.gui.inventory.GuiFurnace;
/*      */ import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
/*      */ import net.minecraft.client.gui.inventory.GuiShulkerBox;
/*      */ import net.minecraft.client.network.NetHandlerPlayClient;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.IJumpingMount;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.MoverType;
/*      */ import net.minecraft.entity.item.EntityBoat;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.passive.AbstractHorse;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.init.MobEffects;
/*      */ import net.minecraft.init.SoundEvents;
/*      */ import net.minecraft.inventory.EntityEquipmentSlot;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemElytra;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.crafting.IRecipe;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.play.client.CPacketAnimation;
/*      */ import net.minecraft.network.play.client.CPacketChatMessage;
/*      */ import net.minecraft.network.play.client.CPacketClientStatus;
/*      */ import net.minecraft.network.play.client.CPacketCloseWindow;
/*      */ import net.minecraft.network.play.client.CPacketEntityAction;
/*      */ import net.minecraft.network.play.client.CPacketInput;
/*      */ import net.minecraft.network.play.client.CPacketPlayer;
/*      */ import net.minecraft.network.play.client.CPacketPlayerAbilities;
/*      */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*      */ import net.minecraft.network.play.client.CPacketRecipeInfo;
/*      */ import net.minecraft.network.play.client.CPacketVehicleMove;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.stats.RecipeBook;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.stats.StatisticsManager;
/*      */ import net.minecraft.tileentity.CommandBlockBaseLogic;
/*      */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.tileentity.TileEntityStructure;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.MovementInput;
/*      */ import net.minecraft.util.SoundEvent;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec2f;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.IInteractionObject;
/*      */ import net.minecraft.world.IWorldNameable;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EntityPlayerSP
/*      */   extends AbstractClientPlayer
/*      */ {
/*      */   public final NetHandlerPlayClient connection;
/*      */   private final StatisticsManager statWriter;
/*      */   private final RecipeBook field_192036_cb;
/*  106 */   private int permissionLevel = 0;
/*      */ 
/*      */   
/*      */   private double lastReportedPosX;
/*      */ 
/*      */   
/*      */   private double lastReportedPosY;
/*      */ 
/*      */   
/*      */   private double lastReportedPosZ;
/*      */ 
/*      */   
/*      */   private float lastReportedYaw;
/*      */ 
/*      */   
/*      */   private float lastReportedPitch;
/*      */ 
/*      */   
/*      */   private boolean prevOnGround;
/*      */ 
/*      */   
/*      */   private boolean serverSneakState;
/*      */ 
/*      */   
/*      */   private boolean serverSprintState;
/*      */ 
/*      */   
/*      */   private int positionUpdateTicks;
/*      */ 
/*      */   
/*      */   private boolean hasValidHealth;
/*      */ 
/*      */   
/*      */   private String serverBrand;
/*      */ 
/*      */   
/*      */   public MovementInput movementInput;
/*      */ 
/*      */   
/*      */   protected Minecraft mc;
/*      */ 
/*      */   
/*      */   protected int sprintToggleTimer;
/*      */ 
/*      */   
/*      */   public int sprintingTicksLeft;
/*      */ 
/*      */   
/*      */   public float renderArmYaw;
/*      */ 
/*      */   
/*      */   public float renderArmPitch;
/*      */   
/*      */   public float prevRenderArmYaw;
/*      */   
/*      */   public float prevRenderArmPitch;
/*      */   
/*      */   private int horseJumpPowerCounter;
/*      */   
/*      */   private float horseJumpPower;
/*      */   
/*      */   public float timeInPortal;
/*      */   
/*      */   public float prevTimeInPortal;
/*      */   
/*      */   private boolean handActive;
/*      */   
/*      */   private EnumHand activeHand;
/*      */   
/*      */   private boolean rowingBoat;
/*      */   
/*      */   private boolean autoJumpEnabled = true;
/*      */   
/*      */   private int autoJumpTime;
/*      */   
/*      */   private boolean wasFallFlying;
/*      */ 
/*      */   
/*      */   public EntityPlayerSP(Minecraft p_i47378_1_, World p_i47378_2_, NetHandlerPlayClient p_i47378_3_, StatisticsManager p_i47378_4_, RecipeBook p_i47378_5_) {
/*  185 */     super(p_i47378_2_, p_i47378_3_.getGameProfile());
/*  186 */     this.connection = p_i47378_3_;
/*  187 */     this.statWriter = p_i47378_4_;
/*  188 */     this.field_192036_cb = p_i47378_5_;
/*  189 */     this.mc = p_i47378_1_;
/*  190 */     this.dimension = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  198 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void heal(float healAmount) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean startRiding(Entity entityIn, boolean force) {
/*  210 */     if (!super.startRiding(entityIn, force))
/*      */     {
/*  212 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  216 */     if (entityIn instanceof EntityMinecart)
/*      */     {
/*  218 */       this.mc.getSoundHandler().playSound((ISound)new MovingSoundMinecartRiding(this, (EntityMinecart)entityIn));
/*      */     }
/*      */     
/*  221 */     if (entityIn instanceof EntityBoat) {
/*      */       
/*  223 */       this.prevRotationYaw = entityIn.rotationYaw;
/*  224 */       this.rotationYaw = entityIn.rotationYaw;
/*  225 */       setRotationYawHead(entityIn.rotationYaw);
/*      */     } 
/*      */     
/*  228 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void dismountRidingEntity() {
/*  234 */     super.dismountRidingEntity();
/*  235 */     this.rowingBoat = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3d getLook(float partialTicks) {
/*  243 */     return getVectorForRotation(this.rotationPitch, this.rotationYaw);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  252 */     if (this.world.isBlockLoaded(new BlockPos(this.posX, 0.0D, this.posZ))) {
/*      */       
/*  254 */       super.onUpdate();
/*      */       
/*  256 */       if (isRiding()) {
/*      */         
/*  258 */         this.connection.sendPacket((Packet)new CPacketPlayer.Rotation(this.rotationYaw, this.rotationPitch, this.onGround));
/*  259 */         this.connection.sendPacket((Packet)new CPacketInput(this.moveStrafing, this.field_191988_bg, this.movementInput.jump, this.movementInput.sneak));
/*  260 */         Entity entity = getLowestRidingEntity();
/*      */         
/*  262 */         if (entity != this && entity.canPassengerSteer())
/*      */         {
/*  264 */           this.connection.sendPacket((Packet)new CPacketVehicleMove(entity));
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  269 */         onUpdateWalkingPlayer();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void onUpdateWalkingPlayer() {
/*  280 */     boolean flag = isSprinting();
/*      */     
/*  282 */     if (flag != this.serverSprintState) {
/*      */       
/*  284 */       if (flag) {
/*      */         
/*  286 */         this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.START_SPRINTING));
/*      */       }
/*      */       else {
/*      */         
/*  290 */         this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.STOP_SPRINTING));
/*      */       } 
/*      */       
/*  293 */       this.serverSprintState = flag;
/*      */     } 
/*      */     
/*  296 */     boolean flag1 = isSneaking();
/*      */     
/*  298 */     if (flag1 != this.serverSneakState) {
/*      */       
/*  300 */       if (flag1) {
/*      */         
/*  302 */         this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.START_SNEAKING));
/*      */       }
/*      */       else {
/*      */         
/*  306 */         this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.STOP_SNEAKING));
/*      */       } 
/*      */       
/*  309 */       this.serverSneakState = flag1;
/*      */     } 
/*      */     
/*  312 */     if (isCurrentViewEntity()) {
/*      */       
/*  314 */       AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/*  315 */       double d0 = this.posX - this.lastReportedPosX;
/*  316 */       double d1 = axisalignedbb.minY - this.lastReportedPosY;
/*  317 */       double d2 = this.posZ - this.lastReportedPosZ;
/*  318 */       double d3 = (this.rotationYaw - this.lastReportedYaw);
/*  319 */       double d4 = (this.rotationPitch - this.lastReportedPitch);
/*  320 */       this.positionUpdateTicks++;
/*  321 */       boolean flag2 = !(d0 * d0 + d1 * d1 + d2 * d2 <= 9.0E-4D && this.positionUpdateTicks < 20);
/*  322 */       boolean flag3 = !(d3 == 0.0D && d4 == 0.0D);
/*      */       
/*  324 */       if (isRiding()) {
/*      */         
/*  326 */         this.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.motionX, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
/*  327 */         flag2 = false;
/*      */       }
/*  329 */       else if (flag2 && flag3) {
/*      */         
/*  331 */         this.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.posX, axisalignedbb.minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
/*      */       }
/*  333 */       else if (flag2) {
/*      */         
/*  335 */         this.connection.sendPacket((Packet)new CPacketPlayer.Position(this.posX, axisalignedbb.minY, this.posZ, this.onGround));
/*      */       }
/*  337 */       else if (flag3) {
/*      */         
/*  339 */         this.connection.sendPacket((Packet)new CPacketPlayer.Rotation(this.rotationYaw, this.rotationPitch, this.onGround));
/*      */       }
/*  341 */       else if (this.prevOnGround != this.onGround) {
/*      */         
/*  343 */         this.connection.sendPacket((Packet)new CPacketPlayer(this.onGround));
/*      */       } 
/*      */       
/*  346 */       if (flag2) {
/*      */         
/*  348 */         this.lastReportedPosX = this.posX;
/*  349 */         this.lastReportedPosY = axisalignedbb.minY;
/*  350 */         this.lastReportedPosZ = this.posZ;
/*  351 */         this.positionUpdateTicks = 0;
/*      */       } 
/*      */       
/*  354 */       if (flag3) {
/*      */         
/*  356 */         this.lastReportedYaw = this.rotationYaw;
/*  357 */         this.lastReportedPitch = this.rotationPitch;
/*      */       } 
/*      */       
/*  360 */       this.prevOnGround = this.onGround;
/*  361 */       this.autoJumpEnabled = this.mc.gameSettings.autoJump;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityItem dropItem(boolean dropAll) {
/*  373 */     CPacketPlayerDigging.Action cpacketplayerdigging$action = dropAll ? CPacketPlayerDigging.Action.DROP_ALL_ITEMS : CPacketPlayerDigging.Action.DROP_ITEM;
/*  374 */     this.connection.sendPacket((Packet)new CPacketPlayerDigging(cpacketplayerdigging$action, BlockPos.ORIGIN, EnumFacing.DOWN));
/*  375 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected ItemStack dropItemAndGetStack(EntityItem p_184816_1_) {
/*  380 */     return ItemStack.field_190927_a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendChatMessage(String message) {
/*  388 */     ChatMessageSendEvent ev = new ChatMessageSendEvent(message);
/*  389 */     EventManager.call((Event)ev);
/*  390 */     if (ev.isCancelled()) {
/*      */       return;
/*      */     }
/*  393 */     message = ev.getMessage();
/*  394 */     if (CommandManager.execute(message)) {
/*      */       return;
/*      */     }
/*  397 */     if (message.startsWith(CommandManager.syntax)) {
/*  398 */       Command.msg("§7Type §e" + CommandManager.syntax + "help 1-2 §7to list all commands", true);
/*      */       return;
/*      */     } 
/*  401 */     this.connection.sendPacket((Packet)new CPacketChatMessage(message));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void swingArm(EnumHand hand) {
/*  409 */     super.swingArm(hand);
/*  410 */     this.connection.sendPacket((Packet)new CPacketAnimation(hand));
/*      */   }
/*      */ 
/*      */   
/*      */   public void respawnPlayer() {
/*  415 */     this.connection.sendPacket((Packet)new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageEntity(DamageSource damageSrc, float damageAmount) {
/*  424 */     if (!isEntityInvulnerable(damageSrc))
/*      */     {
/*  426 */       setHealth(getHealth() - damageAmount);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeScreen() {
/*  435 */     this.connection.sendPacket((Packet)new CPacketCloseWindow(this.openContainer.windowId));
/*  436 */     closeScreenAndDropStack();
/*      */   }
/*      */ 
/*      */   
/*      */   public void closeScreenAndDropStack() {
/*  441 */     this.inventory.setItemStack(ItemStack.field_190927_a);
/*  442 */     super.closeScreen();
/*  443 */     this.mc.displayGuiScreen(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerSPHealth(float health) {
/*  451 */     if (this.hasValidHealth) {
/*      */       
/*  453 */       float f = getHealth() - health;
/*      */       
/*  455 */       if (f <= 0.0F)
/*      */       {
/*  457 */         setHealth(health);
/*      */         
/*  459 */         if (f < 0.0F)
/*      */         {
/*  461 */           this.hurtResistantTime = this.maxHurtResistantTime / 2;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  466 */         this.lastDamage = f;
/*  467 */         setHealth(getHealth());
/*  468 */         this.hurtResistantTime = this.maxHurtResistantTime;
/*  469 */         damageEntity(DamageSource.generic, f);
/*  470 */         this.maxHurtTime = 10;
/*  471 */         this.hurtTime = this.maxHurtTime;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  476 */       setHealth(health);
/*  477 */       this.hasValidHealth = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addStat(StatBase stat, int amount) {
/*  486 */     if (stat != null)
/*      */     {
/*  488 */       if (stat.isIndependent)
/*      */       {
/*  490 */         super.addStat(stat, amount);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendPlayerAbilities() {
/*  500 */     this.connection.sendPacket((Packet)new CPacketPlayerAbilities(this.capabilities));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUser() {
/*  508 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void sendHorseJump() {
/*  513 */     this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.START_RIDING_JUMP, MathHelper.floor(getHorseJumpPower() * 100.0F)));
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendHorseInventory() {
/*  518 */     this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.OPEN_INVENTORY));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerBrand(String brand) {
/*  527 */     this.serverBrand = brand;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getServerBrand() {
/*  537 */     return this.serverBrand;
/*      */   }
/*      */ 
/*      */   
/*      */   public StatisticsManager getStatFileWriter() {
/*  542 */     return this.statWriter;
/*      */   }
/*      */ 
/*      */   
/*      */   public RecipeBook func_192035_E() {
/*  547 */     return this.field_192036_cb;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_193103_a(IRecipe p_193103_1_) {
/*  552 */     if (this.field_192036_cb.func_194076_e(p_193103_1_)) {
/*      */       
/*  554 */       this.field_192036_cb.func_194074_f(p_193103_1_);
/*  555 */       this.connection.sendPacket((Packet)new CPacketRecipeInfo(p_193103_1_));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getPermissionLevel() {
/*  561 */     return this.permissionLevel;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPermissionLevel(int p_184839_1_) {
/*  566 */     this.permissionLevel = p_184839_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addChatComponentMessage(ITextComponent chatComponent, boolean p_146105_2_) {
/*  571 */     if (p_146105_2_) {
/*      */       
/*  573 */       this.mc.ingameGUI.setRecordPlaying(chatComponent, false);
/*      */     }
/*      */     else {
/*      */       
/*  577 */       this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean pushOutOfBlocks(double x, double y, double z) {
/*  583 */     if (this.noClip)
/*      */     {
/*  585 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  589 */     BlockPos blockpos = new BlockPos(x, y, z);
/*  590 */     double d0 = x - blockpos.getX();
/*  591 */     double d1 = z - blockpos.getZ();
/*      */     
/*  593 */     if (!isOpenBlockSpace(blockpos)) {
/*      */       
/*  595 */       int i = -1;
/*  596 */       double d2 = 9999.0D;
/*      */       
/*  598 */       if (isOpenBlockSpace(blockpos.west()) && d0 < d2) {
/*      */         
/*  600 */         d2 = d0;
/*  601 */         i = 0;
/*      */       } 
/*      */       
/*  604 */       if (isOpenBlockSpace(blockpos.east()) && 1.0D - d0 < d2) {
/*      */         
/*  606 */         d2 = 1.0D - d0;
/*  607 */         i = 1;
/*      */       } 
/*      */       
/*  610 */       if (isOpenBlockSpace(blockpos.north()) && d1 < d2) {
/*      */         
/*  612 */         d2 = d1;
/*  613 */         i = 4;
/*      */       } 
/*      */       
/*  616 */       if (isOpenBlockSpace(blockpos.south()) && 1.0D - d1 < d2) {
/*      */         
/*  618 */         d2 = 1.0D - d1;
/*  619 */         i = 5;
/*      */       } 
/*      */       
/*  622 */       float f = 0.1F;
/*      */       
/*  624 */       if (i == 0)
/*      */       {
/*  626 */         this.motionX = -0.10000000149011612D;
/*      */       }
/*      */       
/*  629 */       if (i == 1)
/*      */       {
/*  631 */         this.motionX = 0.10000000149011612D;
/*      */       }
/*      */       
/*  634 */       if (i == 4)
/*      */       {
/*  636 */         this.motionZ = -0.10000000149011612D;
/*      */       }
/*      */       
/*  639 */       if (i == 5)
/*      */       {
/*  641 */         this.motionZ = 0.10000000149011612D;
/*      */       }
/*      */     } 
/*      */     
/*  645 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isOpenBlockSpace(BlockPos pos) {
/*  654 */     return (!this.world.getBlockState(pos).isNormalCube() && !this.world.getBlockState(pos.up()).isNormalCube());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSprinting(boolean sprinting) {
/*  662 */     super.setSprinting(sprinting);
/*  663 */     this.sprintingTicksLeft = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setXPStats(float currentXP, int maxXP, int level) {
/*  671 */     this.experience = currentXP;
/*  672 */     this.experienceTotal = maxXP;
/*  673 */     this.experienceLevel = level;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(ITextComponent component) {
/*  681 */     this.mc.ingameGUI.getChatGUI().printChatMessage(component);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  689 */     return (permLevel <= getPermissionLevel());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/*  694 */     if (id >= 24 && id <= 28) {
/*      */       
/*  696 */       setPermissionLevel(id - 24);
/*      */     }
/*      */     else {
/*      */       
/*  700 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/*  710 */     return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSound(SoundEvent soundIn, float volume, float pitch) {
/*  715 */     this.world.playSound(this.posX, this.posY, this.posZ, soundIn, getSoundCategory(), volume, pitch, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServerWorld() {
/*  723 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setActiveHand(EnumHand hand) {
/*  728 */     ItemStack itemstack = getHeldItem(hand);
/*      */     
/*  730 */     if (!itemstack.func_190926_b() && !isHandActive()) {
/*      */       
/*  732 */       super.setActiveHand(hand);
/*  733 */       this.handActive = true;
/*  734 */       this.activeHand = hand;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isHandActive() {
/*  740 */     return this.handActive;
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetActiveHand() {
/*  745 */     super.resetActiveHand();
/*  746 */     this.handActive = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumHand getActiveHand() {
/*  751 */     return this.activeHand;
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyDataManagerChange(DataParameter<?> key) {
/*  756 */     super.notifyDataManagerChange(key);
/*      */     
/*  758 */     if (HAND_STATES.equals(key)) {
/*      */       
/*  760 */       boolean flag = ((((Byte)this.dataManager.get(HAND_STATES)).byteValue() & 0x1) > 0);
/*  761 */       EnumHand enumhand = ((((Byte)this.dataManager.get(HAND_STATES)).byteValue() & 0x2) > 0) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
/*      */       
/*  763 */       if (flag && !this.handActive) {
/*      */         
/*  765 */         setActiveHand(enumhand);
/*      */       }
/*  767 */       else if (!flag && this.handActive) {
/*      */         
/*  769 */         resetActiveHand();
/*      */       } 
/*      */     } 
/*      */     
/*  773 */     if (FLAGS.equals(key) && isElytraFlying() && !this.wasFallFlying)
/*      */     {
/*  775 */       this.mc.getSoundHandler().playSound((ISound)new ElytraSound(this));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRidingHorse() {
/*  781 */     Entity entity = getRidingEntity();
/*  782 */     return (isRiding() && entity instanceof IJumpingMount && ((IJumpingMount)entity).canJump());
/*      */   }
/*      */ 
/*      */   
/*      */   public float getHorseJumpPower() {
/*  787 */     return this.horseJumpPower;
/*      */   }
/*      */ 
/*      */   
/*      */   public void openEditSign(TileEntitySign signTile) {
/*  792 */     this.mc.displayGuiScreen((GuiScreen)new GuiEditSign(signTile));
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayGuiEditCommandCart(CommandBlockBaseLogic commandBlock) {
/*  797 */     this.mc.displayGuiScreen((GuiScreen)new GuiEditCommandBlockMinecart(commandBlock));
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayGuiCommandBlock(TileEntityCommandBlock commandBlock) {
/*  802 */     this.mc.displayGuiScreen((GuiScreen)new GuiCommandBlock(commandBlock));
/*      */   }
/*      */ 
/*      */   
/*      */   public void openEditStructure(TileEntityStructure structure) {
/*  807 */     this.mc.displayGuiScreen((GuiScreen)new GuiEditStructure(structure));
/*      */   }
/*      */ 
/*      */   
/*      */   public void openBook(ItemStack stack, EnumHand hand) {
/*  812 */     Item item = stack.getItem();
/*      */     
/*  814 */     if (item == Items.WRITABLE_BOOK)
/*      */     {
/*  816 */       this.mc.displayGuiScreen((GuiScreen)new GuiScreenBook(this, stack, true));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIChest(IInventory chestInventory) {
/*  825 */     String s = (chestInventory instanceof IInteractionObject) ? ((IInteractionObject)chestInventory).getGuiID() : "minecraft:container";
/*      */     
/*  827 */     if ("minecraft:chest".equals(s)) {
/*      */       
/*  829 */       this.mc.displayGuiScreen((GuiScreen)new GuiChest((IInventory)this.inventory, chestInventory));
/*      */     }
/*  831 */     else if ("minecraft:hopper".equals(s)) {
/*      */       
/*  833 */       this.mc.displayGuiScreen((GuiScreen)new GuiHopper(this.inventory, chestInventory));
/*      */     }
/*  835 */     else if ("minecraft:furnace".equals(s)) {
/*      */       
/*  837 */       this.mc.displayGuiScreen((GuiScreen)new GuiFurnace(this.inventory, chestInventory));
/*      */     }
/*  839 */     else if ("minecraft:brewing_stand".equals(s)) {
/*      */       
/*  841 */       this.mc.displayGuiScreen((GuiScreen)new GuiBrewingStand(this.inventory, chestInventory));
/*      */     }
/*  843 */     else if ("minecraft:beacon".equals(s)) {
/*      */       
/*  845 */       this.mc.displayGuiScreen((GuiScreen)new GuiBeacon(this.inventory, chestInventory));
/*      */     }
/*  847 */     else if (!"minecraft:dispenser".equals(s) && !"minecraft:dropper".equals(s)) {
/*      */       
/*  849 */       if ("minecraft:shulker_box".equals(s))
/*      */       {
/*  851 */         this.mc.displayGuiScreen((GuiScreen)new GuiShulkerBox(this.inventory, chestInventory));
/*      */       }
/*      */       else
/*      */       {
/*  855 */         this.mc.displayGuiScreen((GuiScreen)new GuiChest((IInventory)this.inventory, chestInventory));
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  860 */       this.mc.displayGuiScreen((GuiScreen)new GuiDispenser(this.inventory, chestInventory));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void openGuiHorseInventory(AbstractHorse horse, IInventory inventoryIn) {
/*  866 */     this.mc.displayGuiScreen((GuiScreen)new GuiScreenHorseInventory((IInventory)this.inventory, inventoryIn, horse));
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayGui(IInteractionObject guiOwner) {
/*  871 */     String s = guiOwner.getGuiID();
/*      */     
/*  873 */     if ("minecraft:crafting_table".equals(s)) {
/*      */       
/*  875 */       this.mc.displayGuiScreen((GuiScreen)new GuiCrafting(this.inventory, this.world));
/*      */     }
/*  877 */     else if ("minecraft:enchanting_table".equals(s)) {
/*      */       
/*  879 */       this.mc.displayGuiScreen((GuiScreen)new GuiEnchantment(this.inventory, this.world, (IWorldNameable)guiOwner));
/*      */     }
/*  881 */     else if ("minecraft:anvil".equals(s)) {
/*      */       
/*  883 */       this.mc.displayGuiScreen((GuiScreen)new GuiRepair(this.inventory, this.world));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayVillagerTradeGui(IMerchant villager) {
/*  889 */     this.mc.displayGuiScreen((GuiScreen)new GuiMerchant(this.inventory, villager, this.world));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCriticalHit(Entity entityHit) {
/*  897 */     this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onEnchantmentCritical(Entity entityHit) {
/*  902 */     this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT_MAGIC);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSneaking() {
/*  910 */     boolean flag = (this.movementInput != null && this.movementInput.sneak);
/*  911 */     return (flag && !this.sleeping);
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateEntityActionState() {
/*  916 */     super.updateEntityActionState();
/*      */     
/*  918 */     if (isCurrentViewEntity()) {
/*      */       
/*  920 */       this.moveStrafing = this.movementInput.moveStrafe;
/*  921 */       this.field_191988_bg = this.movementInput.field_192832_b;
/*  922 */       this.isJumping = this.movementInput.jump;
/*  923 */       this.prevRenderArmYaw = this.renderArmYaw;
/*  924 */       this.prevRenderArmPitch = this.renderArmPitch;
/*  925 */       this.renderArmPitch = (float)(this.renderArmPitch + (this.rotationPitch - this.renderArmPitch) * 0.5D);
/*  926 */       this.renderArmYaw = (float)(this.renderArmYaw + (this.rotationYaw - this.renderArmYaw) * 0.5D);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isCurrentViewEntity() {
/*  932 */     return (this.mc.getRenderViewEntity() == this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/*  941 */     this.sprintingTicksLeft++;
/*      */     
/*  943 */     if (this.sprintToggleTimer > 0)
/*      */     {
/*  945 */       this.sprintToggleTimer--;
/*      */     }
/*      */     
/*  948 */     this.prevTimeInPortal = this.timeInPortal;
/*      */     
/*  950 */     if (this.inPortal) {
/*      */       
/*  952 */       if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame()) {
/*      */         
/*  954 */         if (this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer)
/*      */         {
/*  956 */           closeScreen();
/*      */         }
/*      */         
/*  959 */         this.mc.displayGuiScreen(null);
/*      */       } 
/*      */       
/*  962 */       if (this.timeInPortal == 0.0F)
/*      */       {
/*  964 */         this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.BLOCK_PORTAL_TRIGGER, this.rand.nextFloat() * 0.4F + 0.8F));
/*      */       }
/*      */       
/*  967 */       this.timeInPortal += 0.0125F;
/*      */       
/*  969 */       if (this.timeInPortal >= 1.0F)
/*      */       {
/*  971 */         this.timeInPortal = 1.0F;
/*      */       }
/*      */       
/*  974 */       this.inPortal = false;
/*      */     }
/*  976 */     else if (isPotionActive(MobEffects.NAUSEA) && getActivePotionEffect(MobEffects.NAUSEA).getDuration() > 60) {
/*      */       
/*  978 */       this.timeInPortal += 0.006666667F;
/*      */       
/*  980 */       if (this.timeInPortal > 1.0F)
/*      */       {
/*  982 */         this.timeInPortal = 1.0F;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  987 */       if (this.timeInPortal > 0.0F)
/*      */       {
/*  989 */         this.timeInPortal -= 0.05F;
/*      */       }
/*      */       
/*  992 */       if (this.timeInPortal < 0.0F)
/*      */       {
/*  994 */         this.timeInPortal = 0.0F;
/*      */       }
/*      */     } 
/*      */     
/*  998 */     if (this.timeUntilPortal > 0)
/*      */     {
/* 1000 */       this.timeUntilPortal--;
/*      */     }
/*      */     
/* 1003 */     boolean flag = this.movementInput.jump;
/* 1004 */     boolean flag1 = this.movementInput.sneak;
/* 1005 */     float f = 0.8F;
/* 1006 */     boolean flag2 = (this.movementInput.field_192832_b >= 0.8F);
/* 1007 */     this.movementInput.updatePlayerMoveState();
/* 1008 */     this.mc.func_193032_ao().func_193293_a(this.movementInput);
/*      */     
/* 1010 */     if (isHandActive() && !isRiding()) {
/*      */       
/* 1012 */       this.movementInput.moveStrafe *= 0.2F;
/* 1013 */       this.movementInput.field_192832_b *= 0.2F;
/* 1014 */       this.sprintToggleTimer = 0;
/*      */     } 
/*      */     
/* 1017 */     boolean flag3 = false;
/*      */     
/* 1019 */     if (this.autoJumpTime > 0) {
/*      */       
/* 1021 */       this.autoJumpTime--;
/* 1022 */       flag3 = true;
/* 1023 */       this.movementInput.jump = true;
/*      */     } 
/*      */     
/* 1026 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/* 1027 */     pushOutOfBlocks(this.posX - this.width * 0.35D, axisalignedbb.minY + 0.5D, this.posZ + this.width * 0.35D);
/* 1028 */     pushOutOfBlocks(this.posX - this.width * 0.35D, axisalignedbb.minY + 0.5D, this.posZ - this.width * 0.35D);
/* 1029 */     pushOutOfBlocks(this.posX + this.width * 0.35D, axisalignedbb.minY + 0.5D, this.posZ - this.width * 0.35D);
/* 1030 */     pushOutOfBlocks(this.posX + this.width * 0.35D, axisalignedbb.minY + 0.5D, this.posZ + this.width * 0.35D);
/* 1031 */     boolean flag4 = !(getFoodStats().getFoodLevel() <= 6.0F && !this.capabilities.allowFlying);
/*      */     
/* 1033 */     if (this.onGround && !flag1 && !flag2 && this.movementInput.field_192832_b >= 0.8F && !isSprinting() && flag4 && !isHandActive() && !isPotionActive(MobEffects.BLINDNESS))
/*      */     {
/* 1035 */       if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
/*      */         
/* 1037 */         this.sprintToggleTimer = 7;
/*      */       }
/*      */       else {
/*      */         
/* 1041 */         setSprinting(true);
/*      */       } 
/*      */     }
/*      */     
/* 1045 */     if (!isSprinting() && this.movementInput.field_192832_b >= 0.8F && flag4 && !isHandActive() && !isPotionActive(MobEffects.BLINDNESS) && this.mc.gameSettings.keyBindSprint.isKeyDown())
/*      */     {
/* 1047 */       setSprinting(true);
/*      */     }
/*      */     
/* 1050 */     if (isSprinting() && (this.movementInput.field_192832_b < 0.8F || this.isCollidedHorizontally || !flag4))
/*      */     {
/* 1052 */       setSprinting(false);
/*      */     }
/*      */     
/* 1055 */     if (this.capabilities.allowFlying)
/*      */     {
/* 1057 */       if (this.mc.playerController.isSpectatorMode()) {
/*      */         
/* 1059 */         if (!this.capabilities.isFlying)
/*      */         {
/* 1061 */           this.capabilities.isFlying = true;
/* 1062 */           sendPlayerAbilities();
/*      */         }
/*      */       
/* 1065 */       } else if (!flag && this.movementInput.jump && !flag3) {
/*      */         
/* 1067 */         if (this.flyToggleTimer == 0) {
/*      */           
/* 1069 */           this.flyToggleTimer = 7;
/*      */         }
/*      */         else {
/*      */           
/* 1073 */           this.capabilities.isFlying = !this.capabilities.isFlying;
/* 1074 */           sendPlayerAbilities();
/* 1075 */           this.flyToggleTimer = 0;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1080 */     if (this.movementInput.jump && !flag && !this.onGround && this.motionY < 0.0D && !isElytraFlying() && !this.capabilities.isFlying) {
/*      */       
/* 1082 */       ItemStack itemstack = getItemStackFromSlot(EntityEquipmentSlot.CHEST);
/*      */       
/* 1084 */       if (itemstack.getItem() == Items.ELYTRA && ItemElytra.isBroken(itemstack))
/*      */       {
/* 1086 */         this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.START_FALL_FLYING));
/*      */       }
/*      */     } 
/*      */     
/* 1090 */     this.wasFallFlying = isElytraFlying();
/*      */     
/* 1092 */     if (this.capabilities.isFlying && isCurrentViewEntity()) {
/*      */       
/* 1094 */       if (this.movementInput.sneak) {
/*      */         
/* 1096 */         this.movementInput.moveStrafe = (float)(this.movementInput.moveStrafe / 0.3D);
/* 1097 */         this.movementInput.field_192832_b = (float)(this.movementInput.field_192832_b / 0.3D);
/* 1098 */         this.motionY -= (this.capabilities.getFlySpeed() * 3.0F);
/*      */       } 
/*      */       
/* 1101 */       if (this.movementInput.jump)
/*      */       {
/* 1103 */         this.motionY += (this.capabilities.getFlySpeed() * 3.0F);
/*      */       }
/*      */     } 
/*      */     
/* 1107 */     if (isRidingHorse()) {
/*      */       
/* 1109 */       IJumpingMount ijumpingmount = (IJumpingMount)getRidingEntity();
/*      */       
/* 1111 */       if (this.horseJumpPowerCounter < 0) {
/*      */         
/* 1113 */         this.horseJumpPowerCounter++;
/*      */         
/* 1115 */         if (this.horseJumpPowerCounter == 0)
/*      */         {
/* 1117 */           this.horseJumpPower = 0.0F;
/*      */         }
/*      */       } 
/*      */       
/* 1121 */       if (flag && !this.movementInput.jump) {
/*      */         
/* 1123 */         this.horseJumpPowerCounter = -10;
/* 1124 */         ijumpingmount.setJumpPower(MathHelper.floor(getHorseJumpPower() * 100.0F));
/* 1125 */         sendHorseJump();
/*      */       }
/* 1127 */       else if (!flag && this.movementInput.jump) {
/*      */         
/* 1129 */         this.horseJumpPowerCounter = 0;
/* 1130 */         this.horseJumpPower = 0.0F;
/*      */       }
/* 1132 */       else if (flag) {
/*      */         
/* 1134 */         this.horseJumpPowerCounter++;
/*      */         
/* 1136 */         if (this.horseJumpPowerCounter < 10)
/*      */         {
/* 1138 */           this.horseJumpPower = this.horseJumpPowerCounter * 0.1F;
/*      */         }
/*      */         else
/*      */         {
/* 1142 */           this.horseJumpPower = 0.8F + 2.0F / (this.horseJumpPowerCounter - 9) * 0.1F;
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/* 1148 */       this.horseJumpPower = 0.0F;
/*      */     } 
/*      */     
/* 1151 */     super.onLivingUpdate();
/*      */     
/* 1153 */     if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
/*      */       
/* 1155 */       this.capabilities.isFlying = false;
/* 1156 */       sendPlayerAbilities();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRidden() {
/* 1165 */     super.updateRidden();
/* 1166 */     this.rowingBoat = false;
/*      */     
/* 1168 */     if (getRidingEntity() instanceof EntityBoat) {
/*      */       
/* 1170 */       EntityBoat entityboat = (EntityBoat)getRidingEntity();
/* 1171 */       entityboat.updateInputs(this.movementInput.leftKeyDown, this.movementInput.rightKeyDown, this.movementInput.forwardKeyDown, this.movementInput.backKeyDown);
/* 1172 */       this.rowingBoat |= (!this.movementInput.leftKeyDown && !this.movementInput.rightKeyDown && !this.movementInput.forwardKeyDown && !this.movementInput.backKeyDown) ? 0 : 1;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRowingBoat() {
/* 1178 */     return this.rowingBoat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public PotionEffect removeActivePotionEffect(@Nullable Potion potioneffectin) {
/* 1189 */     if (potioneffectin == MobEffects.NAUSEA) {
/*      */       
/* 1191 */       this.prevTimeInPortal = 0.0F;
/* 1192 */       this.timeInPortal = 0.0F;
/*      */     } 
/*      */     
/* 1195 */     return super.removeActivePotionEffect(potioneffectin);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveEntity(MoverType x, double p_70091_2_, double p_70091_4_, double p_70091_6_) {
/* 1203 */     double d0 = this.posX;
/* 1204 */     double d1 = this.posZ;
/* 1205 */     super.moveEntity(x, p_70091_2_, p_70091_4_, p_70091_6_);
/* 1206 */     updateAutoJump((float)(this.posX - d0), (float)(this.posZ - d1));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAutoJumpEnabled() {
/* 1211 */     return this.autoJumpEnabled;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateAutoJump(float p_189810_1_, float p_189810_2_) {
/* 1216 */     if (isAutoJumpEnabled())
/*      */     {
/* 1218 */       if (this.autoJumpTime <= 0 && this.onGround && !isSneaking() && !isRiding()) {
/*      */         
/* 1220 */         Vec2f vec2f = this.movementInput.getMoveVector();
/*      */         
/* 1222 */         if (vec2f.x != 0.0F || vec2f.y != 0.0F) {
/*      */           
/* 1224 */           Vec3d vec3d = new Vec3d(this.posX, (getEntityBoundingBox()).minY, this.posZ);
/* 1225 */           double d0 = this.posX + p_189810_1_;
/* 1226 */           double d1 = this.posZ + p_189810_2_;
/* 1227 */           Vec3d vec3d1 = new Vec3d(d0, (getEntityBoundingBox()).minY, d1);
/* 1228 */           Vec3d vec3d2 = new Vec3d(p_189810_1_, 0.0D, p_189810_2_);
/* 1229 */           float f = getAIMoveSpeed();
/* 1230 */           float f1 = (float)vec3d2.lengthSquared();
/*      */           
/* 1232 */           if (f1 <= 0.001F) {
/*      */             
/* 1234 */             float f2 = f * vec2f.x;
/* 1235 */             float f3 = f * vec2f.y;
/* 1236 */             float f4 = MathHelper.sin(this.rotationYaw * 0.017453292F);
/* 1237 */             float f5 = MathHelper.cos(this.rotationYaw * 0.017453292F);
/* 1238 */             vec3d2 = new Vec3d((f2 * f5 - f3 * f4), vec3d2.yCoord, (f3 * f5 + f2 * f4));
/* 1239 */             f1 = (float)vec3d2.lengthSquared();
/*      */             
/* 1241 */             if (f1 <= 0.001F) {
/*      */               return;
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/* 1247 */           float f12 = (float)MathHelper.fastInvSqrt(f1);
/* 1248 */           Vec3d vec3d12 = vec3d2.scale(f12);
/* 1249 */           Vec3d vec3d13 = getForward();
/* 1250 */           float f13 = (float)(vec3d13.xCoord * vec3d12.xCoord + vec3d13.zCoord * vec3d12.zCoord);
/*      */           
/* 1252 */           if (f13 >= -0.15F) {
/*      */             
/* 1254 */             BlockPos blockpos = new BlockPos(this.posX, (getEntityBoundingBox()).maxY, this.posZ);
/* 1255 */             IBlockState iblockstate = this.world.getBlockState(blockpos);
/*      */             
/* 1257 */             if (iblockstate.getCollisionBoundingBox((IBlockAccess)this.world, blockpos) == null) {
/*      */               
/* 1259 */               blockpos = blockpos.up();
/* 1260 */               IBlockState iblockstate1 = this.world.getBlockState(blockpos);
/*      */               
/* 1262 */               if (iblockstate1.getCollisionBoundingBox((IBlockAccess)this.world, blockpos) == null) {
/*      */                 
/* 1264 */                 float f6 = 7.0F;
/* 1265 */                 float f7 = 1.2F;
/*      */                 
/* 1267 */                 if (isPotionActive(MobEffects.JUMP_BOOST))
/*      */                 {
/* 1269 */                   f7 += (getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.75F;
/*      */                 }
/*      */                 
/* 1272 */                 float f8 = Math.max(f * 7.0F, 1.0F / f12);
/* 1273 */                 Vec3d vec3d4 = vec3d1.add(vec3d12.scale(f8));
/* 1274 */                 float f9 = this.width;
/* 1275 */                 float f10 = this.height;
/* 1276 */                 AxisAlignedBB axisalignedbb = (new AxisAlignedBB(vec3d, vec3d4.addVector(0.0D, f10, 0.0D))).expand(f9, 0.0D, f9);
/* 1277 */                 Vec3d lvt_19_1_ = vec3d.addVector(0.0D, 0.5099999904632568D, 0.0D);
/* 1278 */                 vec3d4 = vec3d4.addVector(0.0D, 0.5099999904632568D, 0.0D);
/* 1279 */                 Vec3d vec3d5 = vec3d12.crossProduct(new Vec3d(0.0D, 1.0D, 0.0D));
/* 1280 */                 Vec3d vec3d6 = vec3d5.scale((f9 * 0.5F));
/* 1281 */                 Vec3d vec3d7 = lvt_19_1_.subtract(vec3d6);
/* 1282 */                 Vec3d vec3d8 = vec3d4.subtract(vec3d6);
/* 1283 */                 Vec3d vec3d9 = lvt_19_1_.add(vec3d6);
/* 1284 */                 Vec3d vec3d10 = vec3d4.add(vec3d6);
/* 1285 */                 List<AxisAlignedBB> list = this.world.getCollisionBoxes((Entity)this, axisalignedbb);
/*      */                 
/* 1287 */                 if (!list.isEmpty());
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1292 */                 float f11 = Float.MIN_VALUE;
/*      */ 
/*      */                 
/* 1295 */                 for (AxisAlignedBB axisalignedbb2 : list) {
/*      */                   
/* 1297 */                   if (axisalignedbb2.intersects(vec3d7, vec3d8) || axisalignedbb2.intersects(vec3d9, vec3d10)) {
/*      */                     
/* 1299 */                     f11 = (float)axisalignedbb2.maxY;
/* 1300 */                     Vec3d vec3d11 = axisalignedbb2.getCenter();
/* 1301 */                     BlockPos blockpos1 = new BlockPos(vec3d11);
/* 1302 */                     int i = 1;
/*      */ 
/*      */ 
/*      */                     
/* 1306 */                     while (i < f7) {
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/* 1311 */                       BlockPos blockpos2 = blockpos1.up(i);
/* 1312 */                       IBlockState iblockstate2 = this.world.getBlockState(blockpos2);
/*      */                       
/*      */                       AxisAlignedBB axisalignedbb1;
/* 1315 */                       if ((axisalignedbb1 = iblockstate2.getCollisionBoundingBox((IBlockAccess)this.world, blockpos2)) != null) {
/*      */                         
/* 1317 */                         f11 = (float)axisalignedbb1.maxY + blockpos2.getY();
/*      */                         
/* 1319 */                         if (f11 - (getEntityBoundingBox()).minY > f7) {
/*      */                           return;
/*      */                         }
/*      */                       } 
/*      */ 
/*      */                       
/* 1325 */                       if (i > 1) {
/*      */                         
/* 1327 */                         blockpos = blockpos.up();
/* 1328 */                         IBlockState iblockstate3 = this.world.getBlockState(blockpos);
/*      */                         
/* 1330 */                         if (iblockstate3.getCollisionBoundingBox((IBlockAccess)this.world, blockpos) != null) {
/*      */                           return;
/*      */                         }
/*      */                       } 
/*      */ 
/*      */                       
/* 1336 */                       i++;
/*      */                     } 
/*      */                     break;
/*      */                   } 
/*      */                 } 
/* 1341 */                 if (f11 != Float.MIN_VALUE) {
/*      */                   
/* 1343 */                   float f14 = (float)(f11 - (getEntityBoundingBox()).minY);
/*      */                   
/* 1345 */                   if (f14 > 0.5F && f14 <= f7)
/*      */                   {
/* 1347 */                     this.autoJumpTime = 1;
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\entity\EntityPlayerSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */