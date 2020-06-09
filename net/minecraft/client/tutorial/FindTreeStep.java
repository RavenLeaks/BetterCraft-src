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
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.GameType;
/*     */ 
/*     */ public class FindTreeStep implements ITutorialStep {
/*  22 */   private static final Set<Block> field_193268_a = Sets.newHashSet((Object[])new Block[] { Blocks.LOG, Blocks.LOG2, (Block)Blocks.LEAVES, (Block)Blocks.LEAVES2 });
/*  23 */   private static final ITextComponent field_193269_b = (ITextComponent)new TextComponentTranslation("tutorial.find_tree.title", new Object[0]);
/*  24 */   private static final ITextComponent field_193270_c = (ITextComponent)new TextComponentTranslation("tutorial.find_tree.description", new Object[0]);
/*     */   
/*     */   private final Tutorial field_193271_d;
/*     */   private TutorialToast field_193272_e;
/*     */   private int field_193273_f;
/*     */   
/*     */   public FindTreeStep(Tutorial p_i47582_1_) {
/*  31 */     this.field_193271_d = p_i47582_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193245_a() {
/*  36 */     this.field_193273_f++;
/*     */     
/*  38 */     if (this.field_193271_d.func_194072_f() != GameType.SURVIVAL) {
/*     */       
/*  40 */       this.field_193271_d.func_193292_a(TutorialSteps.NONE);
/*     */     }
/*     */     else {
/*     */       
/*  44 */       if (this.field_193273_f == 1) {
/*     */         
/*  46 */         EntityPlayerSP entityplayersp = (this.field_193271_d.func_193295_e()).player;
/*     */         
/*  48 */         if (entityplayersp != null) {
/*     */           
/*  50 */           for (Block block : field_193268_a) {
/*     */             
/*  52 */             if (entityplayersp.inventory.hasItemStack(new ItemStack(block))) {
/*     */               
/*  54 */               this.field_193271_d.func_193292_a(TutorialSteps.CRAFT_PLANKS);
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/*  59 */           if (func_194070_a(entityplayersp)) {
/*     */             
/*  61 */             this.field_193271_d.func_193292_a(TutorialSteps.CRAFT_PLANKS);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*  67 */       if (this.field_193273_f >= 6000 && this.field_193272_e == null) {
/*     */         
/*  69 */         this.field_193272_e = new TutorialToast(TutorialToast.Icons.TREE, field_193269_b, field_193270_c, false);
/*  70 */         this.field_193271_d.func_193295_e().func_193033_an().func_192988_a((IToast)this.field_193272_e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193248_b() {
/*  77 */     if (this.field_193272_e != null) {
/*     */       
/*  79 */       this.field_193272_e.func_193670_a();
/*  80 */       this.field_193272_e = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193246_a(WorldClient p_193246_1_, RayTraceResult p_193246_2_) {
/*  86 */     if (p_193246_2_.typeOfHit == RayTraceResult.Type.BLOCK && p_193246_2_.getBlockPos() != null) {
/*     */       
/*  88 */       IBlockState iblockstate = p_193246_1_.getBlockState(p_193246_2_.getBlockPos());
/*     */       
/*  90 */       if (field_193268_a.contains(iblockstate.getBlock()))
/*     */       {
/*  92 */         this.field_193271_d.func_193292_a(TutorialSteps.PUNCH_TREE);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193252_a(ItemStack p_193252_1_) {
/*  99 */     for (Block block : field_193268_a) {
/*     */       
/* 101 */       if (p_193252_1_.getItem() == Item.getItemFromBlock(block)) {
/*     */         
/* 103 */         this.field_193271_d.func_193292_a(TutorialSteps.CRAFT_PLANKS);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean func_194070_a(EntityPlayerSP p_194070_0_) {
/* 111 */     for (Block block : field_193268_a) {
/*     */       
/* 113 */       StatBase statbase = StatList.getBlockStats(block);
/*     */       
/* 115 */       if (statbase != null && p_194070_0_.getStatFileWriter().readStat(statbase) > 0)
/*     */       {
/* 117 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 121 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\tutorial\FindTreeStep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */