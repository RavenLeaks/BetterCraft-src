/*     */ package net.minecraft.server.management;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketChangeGameState;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class DemoPlayerInteractionManager extends PlayerInteractionManager {
/*     */   private boolean displayedIntro;
/*     */   private boolean demoTimeExpired;
/*     */   private int demoEndedReminder;
/*     */   private int gameModeTicks;
/*     */   
/*     */   public DemoPlayerInteractionManager(World worldIn) {
/*  22 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBlockRemoving() {
/*  27 */     super.updateBlockRemoving();
/*  28 */     this.gameModeTicks++;
/*  29 */     long i = this.theWorld.getTotalWorldTime();
/*  30 */     long j = i / 24000L + 1L;
/*     */     
/*  32 */     if (!this.displayedIntro && this.gameModeTicks > 20) {
/*     */       
/*  34 */       this.displayedIntro = true;
/*  35 */       this.thisPlayerMP.connection.sendPacket((Packet)new SPacketChangeGameState(5, 0.0F));
/*     */     } 
/*     */     
/*  38 */     this.demoTimeExpired = (i > 120500L);
/*     */     
/*  40 */     if (this.demoTimeExpired)
/*     */     {
/*  42 */       this.demoEndedReminder++;
/*     */     }
/*     */     
/*  45 */     if (i % 24000L == 500L) {
/*     */       
/*  47 */       if (j <= 6L)
/*     */       {
/*  49 */         this.thisPlayerMP.addChatMessage((ITextComponent)new TextComponentTranslation("demo.day." + j, new Object[0]));
/*     */       }
/*     */     }
/*  52 */     else if (j == 1L) {
/*     */       
/*  54 */       if (i == 100L)
/*     */       {
/*  56 */         this.thisPlayerMP.connection.sendPacket((Packet)new SPacketChangeGameState(5, 101.0F));
/*     */       }
/*  58 */       else if (i == 175L)
/*     */       {
/*  60 */         this.thisPlayerMP.connection.sendPacket((Packet)new SPacketChangeGameState(5, 102.0F));
/*     */       }
/*  62 */       else if (i == 250L)
/*     */       {
/*  64 */         this.thisPlayerMP.connection.sendPacket((Packet)new SPacketChangeGameState(5, 103.0F));
/*     */       }
/*     */     
/*  67 */     } else if (j == 5L && i % 24000L == 22000L) {
/*     */       
/*  69 */       this.thisPlayerMP.addChatMessage((ITextComponent)new TextComponentTranslation("demo.day.warning", new Object[0]));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendDemoReminder() {
/*  78 */     if (this.demoEndedReminder > 100) {
/*     */       
/*  80 */       this.thisPlayerMP.addChatMessage((ITextComponent)new TextComponentTranslation("demo.reminder", new Object[0]));
/*  81 */       this.demoEndedReminder = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockClicked(BlockPos pos, EnumFacing side) {
/*  91 */     if (this.demoTimeExpired) {
/*     */       
/*  93 */       sendDemoReminder();
/*     */     }
/*     */     else {
/*     */       
/*  97 */       super.onBlockClicked(pos, side);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void blockRemoving(BlockPos pos) {
/* 103 */     if (!this.demoTimeExpired)
/*     */     {
/* 105 */       super.blockRemoving(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryHarvestBlock(BlockPos pos) {
/* 114 */     return this.demoTimeExpired ? false : super.tryHarvestBlock(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumActionResult processRightClick(EntityPlayer player, World worldIn, ItemStack stack, EnumHand hand) {
/* 119 */     if (this.demoTimeExpired) {
/*     */       
/* 121 */       sendDemoReminder();
/* 122 */       return EnumActionResult.PASS;
/*     */     } 
/*     */ 
/*     */     
/* 126 */     return super.processRightClick(player, worldIn, stack, hand);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult processRightClickBlock(EntityPlayer player, World worldIn, ItemStack stack, EnumHand hand, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ) {
/* 132 */     if (this.demoTimeExpired) {
/*     */       
/* 134 */       sendDemoReminder();
/* 135 */       return EnumActionResult.PASS;
/*     */     } 
/*     */ 
/*     */     
/* 139 */     return super.processRightClickBlock(player, worldIn, stack, hand, pos, facing, hitX, hitY, hitZ);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\management\DemoPlayerInteractionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */