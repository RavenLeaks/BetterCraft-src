/*     */ package net.minecraft.client.tutorial;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.toasts.IToast;
/*     */ import net.minecraft.client.gui.toasts.TutorialToast;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.GameType;
/*     */ 
/*     */ public class PunchTreeStep implements ITutorialStep {
/*  20 */   private static final Set<Block> field_193274_a = Sets.newHashSet((Object[])new Block[] { Blocks.LOG, Blocks.LOG2 });
/*  21 */   private static final ITextComponent field_193275_b = (ITextComponent)new TextComponentTranslation("tutorial.punch_tree.title", new Object[0]);
/*  22 */   private static final ITextComponent field_193276_c = (ITextComponent)new TextComponentTranslation("tutorial.punch_tree.description", new Object[] { Tutorial.func_193291_a("attack") });
/*     */   
/*     */   private final Tutorial field_193277_d;
/*     */   private TutorialToast field_193278_e;
/*     */   private int field_193279_f;
/*     */   private int field_193280_g;
/*     */   
/*     */   public PunchTreeStep(Tutorial p_i47579_1_) {
/*  30 */     this.field_193277_d = p_i47579_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193245_a() {
/*  35 */     this.field_193279_f++;
/*     */     
/*  37 */     if (this.field_193277_d.func_194072_f() != GameType.SURVIVAL) {
/*     */       
/*  39 */       this.field_193277_d.func_193292_a(TutorialSteps.NONE);
/*     */     }
/*     */     else {
/*     */       
/*  43 */       if (this.field_193279_f == 1) {
/*     */         
/*  45 */         EntityPlayerSP entityplayersp = (this.field_193277_d.func_193295_e()).player;
/*     */         
/*  47 */         if (entityplayersp != null) {
/*     */           
/*  49 */           for (Block block : field_193274_a) {
/*     */             
/*  51 */             if (entityplayersp.inventory.hasItemStack(new ItemStack(block))) {
/*     */               
/*  53 */               this.field_193277_d.func_193292_a(TutorialSteps.CRAFT_PLANKS);
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/*  58 */           if (FindTreeStep.func_194070_a(entityplayersp)) {
/*     */             
/*  60 */             this.field_193277_d.func_193292_a(TutorialSteps.CRAFT_PLANKS);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*  66 */       if ((this.field_193279_f >= 600 || this.field_193280_g > 3) && this.field_193278_e == null) {
/*     */         
/*  68 */         this.field_193278_e = new TutorialToast(TutorialToast.Icons.TREE, field_193275_b, field_193276_c, true);
/*  69 */         this.field_193277_d.func_193295_e().func_193033_an().func_192988_a((IToast)this.field_193278_e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193248_b() {
/*  76 */     if (this.field_193278_e != null) {
/*     */       
/*  78 */       this.field_193278_e.func_193670_a();
/*  79 */       this.field_193278_e = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193250_a(WorldClient p_193250_1_, BlockPos p_193250_2_, IBlockState p_193250_3_, float p_193250_4_) {
/*  85 */     boolean flag = field_193274_a.contains(p_193250_3_.getBlock());
/*     */     
/*  87 */     if (flag && p_193250_4_ > 0.0F) {
/*     */       
/*  89 */       if (this.field_193278_e != null)
/*     */       {
/*  91 */         this.field_193278_e.func_193669_a(p_193250_4_);
/*     */       }
/*     */       
/*  94 */       if (p_193250_4_ >= 1.0F)
/*     */       {
/*  96 */         this.field_193277_d.func_193292_a(TutorialSteps.OPEN_INVENTORY);
/*     */       }
/*     */     }
/*  99 */     else if (this.field_193278_e != null) {
/*     */       
/* 101 */       this.field_193278_e.func_193669_a(0.0F);
/*     */     }
/* 103 */     else if (flag) {
/*     */       
/* 105 */       this.field_193280_g++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193252_a(ItemStack p_193252_1_) {
/* 111 */     for (Block block : field_193274_a) {
/*     */       
/* 113 */       if (p_193252_1_.getItem() == Item.getItemFromBlock(block)) {
/*     */         
/* 115 */         this.field_193277_d.func_193292_a(TutorialSteps.CRAFT_PLANKS);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\tutorial\PunchTreeStep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */