/*    */ package net.minecraft.client.player.inventory;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.InventoryBasic;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.world.ILockableContainer;
/*    */ import net.minecraft.world.LockCode;
/*    */ 
/*    */ public class ContainerLocalMenu
/*    */   extends InventoryBasic implements ILockableContainer {
/*    */   private final String guiID;
/* 16 */   private final Map<Integer, Integer> dataValues = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   public ContainerLocalMenu(String id, ITextComponent title, int slotCount) {
/* 20 */     super(title, slotCount);
/* 21 */     this.guiID = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getField(int id) {
/* 26 */     return this.dataValues.containsKey(Integer.valueOf(id)) ? ((Integer)this.dataValues.get(Integer.valueOf(id))).intValue() : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setField(int id, int value) {
/* 31 */     this.dataValues.put(Integer.valueOf(id), Integer.valueOf(value));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFieldCount() {
/* 36 */     return this.dataValues.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLocked() {
/* 41 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLockCode(LockCode code) {}
/*    */ 
/*    */   
/*    */   public LockCode getLockCode() {
/* 50 */     return LockCode.EMPTY_CODE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getGuiID() {
/* 55 */     return this.guiID;
/*    */   }
/*    */ 
/*    */   
/*    */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 60 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\player\inventory\ContainerLocalMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */