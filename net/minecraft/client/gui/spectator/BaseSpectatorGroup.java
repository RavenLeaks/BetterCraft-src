/*    */ package net.minecraft.client.gui.spectator;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.spectator.categories.TeleportToPlayer;
/*    */ import net.minecraft.client.gui.spectator.categories.TeleportToTeam;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ 
/*    */ public class BaseSpectatorGroup
/*    */   implements ISpectatorMenuView {
/* 12 */   private final List<ISpectatorMenuObject> items = Lists.newArrayList();
/*    */ 
/*    */   
/*    */   public BaseSpectatorGroup() {
/* 16 */     this.items.add(new TeleportToPlayer());
/* 17 */     this.items.add(new TeleportToTeam());
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ISpectatorMenuObject> getItems() {
/* 22 */     return this.items;
/*    */   }
/*    */ 
/*    */   
/*    */   public ITextComponent getPrompt() {
/* 27 */     return (ITextComponent)new TextComponentTranslation("spectatorMenu.root.prompt", new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\spectator\BaseSpectatorGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */