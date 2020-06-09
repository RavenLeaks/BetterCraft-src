/*    */ package net.minecraft.world.gen.structure;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StructureMineshaftStart
/*    */   extends StructureStart
/*    */ {
/*    */   private MapGenMineshaft.Type mineShaftType;
/*    */   
/*    */   public StructureMineshaftStart() {}
/*    */   
/*    */   public StructureMineshaftStart(World p_i47149_1_, Random p_i47149_2_, int p_i47149_3_, int p_i47149_4_, MapGenMineshaft.Type p_i47149_5_) {
/* 16 */     super(p_i47149_3_, p_i47149_4_);
/* 17 */     this.mineShaftType = p_i47149_5_;
/* 18 */     StructureMineshaftPieces.Room structuremineshaftpieces$room = new StructureMineshaftPieces.Room(0, p_i47149_2_, (p_i47149_3_ << 4) + 2, (p_i47149_4_ << 4) + 2, this.mineShaftType);
/* 19 */     this.components.add(structuremineshaftpieces$room);
/* 20 */     structuremineshaftpieces$room.buildComponent(structuremineshaftpieces$room, this.components, p_i47149_2_);
/* 21 */     updateBoundingBox();
/*    */     
/* 23 */     if (p_i47149_5_ == MapGenMineshaft.Type.MESA) {
/*    */       
/* 25 */       int i = -5;
/* 26 */       int j = p_i47149_1_.getSeaLevel() - this.boundingBox.maxY + this.boundingBox.getYSize() / 2 - -5;
/* 27 */       this.boundingBox.offset(0, j, 0);
/*    */       
/* 29 */       for (StructureComponent structurecomponent : this.components)
/*    */       {
/* 31 */         structurecomponent.offset(0, j, 0);
/*    */       }
/*    */     }
/*    */     else {
/*    */       
/* 36 */       markAvailableHeight(p_i47149_1_, p_i47149_2_, 10);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\StructureMineshaftStart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */