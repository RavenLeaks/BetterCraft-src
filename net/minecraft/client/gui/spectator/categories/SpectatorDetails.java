/*    */ package net.minecraft.client.gui.spectator.categories;
/*    */ 
/*    */ import com.google.common.base.MoreObjects;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
/*    */ import net.minecraft.client.gui.spectator.ISpectatorMenuView;
/*    */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*    */ 
/*    */ 
/*    */ public class SpectatorDetails
/*    */ {
/*    */   private final ISpectatorMenuView category;
/*    */   private final List<ISpectatorMenuObject> items;
/*    */   private final int selectedSlot;
/*    */   
/*    */   public SpectatorDetails(ISpectatorMenuView p_i45494_1_, List<ISpectatorMenuObject> p_i45494_2_, int p_i45494_3_) {
/* 17 */     this.category = p_i45494_1_;
/* 18 */     this.items = p_i45494_2_;
/* 19 */     this.selectedSlot = p_i45494_3_;
/*    */   }
/*    */ 
/*    */   
/*    */   public ISpectatorMenuObject getObject(int p_178680_1_) {
/* 24 */     return (p_178680_1_ >= 0 && p_178680_1_ < this.items.size()) ? (ISpectatorMenuObject)MoreObjects.firstNonNull(this.items.get(p_178680_1_), SpectatorMenu.EMPTY_SLOT) : SpectatorMenu.EMPTY_SLOT;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSelectedSlot() {
/* 29 */     return this.selectedSlot;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\spectator\categories\SpectatorDetails.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */