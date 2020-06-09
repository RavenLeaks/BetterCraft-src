/*    */ package net.minecraft.client.tutorial;
/*    */ 
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.gui.toasts.IToast;
/*    */ import net.minecraft.client.gui.toasts.TutorialToast;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.stats.StatBase;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ import net.minecraft.world.GameType;
/*    */ 
/*    */ public class CraftPlanksStep implements ITutorialStep {
/* 16 */   private static final ITextComponent field_193286_a = (ITextComponent)new TextComponentTranslation("tutorial.craft_planks.title", new Object[0]);
/* 17 */   private static final ITextComponent field_193287_b = (ITextComponent)new TextComponentTranslation("tutorial.craft_planks.description", new Object[0]);
/*    */   
/*    */   private final Tutorial field_193288_c;
/*    */   private TutorialToast field_193289_d;
/*    */   private int field_193290_e;
/*    */   
/*    */   public CraftPlanksStep(Tutorial p_i47583_1_) {
/* 24 */     this.field_193288_c = p_i47583_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_193245_a() {
/* 29 */     this.field_193290_e++;
/*    */     
/* 31 */     if (this.field_193288_c.func_194072_f() != GameType.SURVIVAL) {
/*    */       
/* 33 */       this.field_193288_c.func_193292_a(TutorialSteps.NONE);
/*    */     }
/*    */     else {
/*    */       
/* 37 */       if (this.field_193290_e == 1) {
/*    */         
/* 39 */         EntityPlayerSP entityplayersp = (this.field_193288_c.func_193295_e()).player;
/*    */         
/* 41 */         if (entityplayersp != null) {
/*    */           
/* 43 */           if (entityplayersp.inventory.hasItemStack(new ItemStack(Blocks.PLANKS))) {
/*    */             
/* 45 */             this.field_193288_c.func_193292_a(TutorialSteps.NONE);
/*    */             
/*    */             return;
/*    */           } 
/* 49 */           if (func_194071_a(entityplayersp)) {
/*    */             
/* 51 */             this.field_193288_c.func_193292_a(TutorialSteps.NONE);
/*    */             
/*    */             return;
/*    */           } 
/*    */         } 
/*    */       } 
/* 57 */       if (this.field_193290_e >= 1200 && this.field_193289_d == null) {
/*    */         
/* 59 */         this.field_193289_d = new TutorialToast(TutorialToast.Icons.WOODEN_PLANKS, field_193286_a, field_193287_b, false);
/* 60 */         this.field_193288_c.func_193295_e().func_193033_an().func_192988_a((IToast)this.field_193289_d);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_193248_b() {
/* 67 */     if (this.field_193289_d != null) {
/*    */       
/* 69 */       this.field_193289_d.func_193670_a();
/* 70 */       this.field_193289_d = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_193252_a(ItemStack p_193252_1_) {
/* 76 */     if (p_193252_1_.getItem() == Item.getItemFromBlock(Blocks.PLANKS))
/*    */     {
/* 78 */       this.field_193288_c.func_193292_a(TutorialSteps.NONE);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean func_194071_a(EntityPlayerSP p_194071_0_) {
/* 84 */     StatBase statbase = StatList.getCraftStats(Item.getItemFromBlock(Blocks.PLANKS));
/* 85 */     return (statbase != null && p_194071_0_.getStatFileWriter().readStat(statbase) > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\tutorial\CraftPlanksStep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */