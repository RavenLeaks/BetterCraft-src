/*    */ package optifine;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class PlayerControllerOF
/*    */   extends PlayerControllerMP {
/*    */   private boolean acting = false;
/* 21 */   private BlockPos lastClickBlockPos = null;
/* 22 */   private Entity lastClickEntity = null;
/*    */ 
/*    */   
/*    */   public PlayerControllerOF(Minecraft p_i72_1_, NetHandlerPlayClient p_i72_2_) {
/* 26 */     super(p_i72_1_, p_i72_2_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean clickBlock(BlockPos loc, EnumFacing face) {
/* 34 */     this.acting = true;
/* 35 */     this.lastClickBlockPos = loc;
/* 36 */     boolean flag = super.clickBlock(loc, face);
/* 37 */     this.acting = false;
/* 38 */     return flag;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing) {
/* 43 */     this.acting = true;
/* 44 */     this.lastClickBlockPos = posBlock;
/* 45 */     boolean flag = super.onPlayerDamageBlock(posBlock, directionFacing);
/* 46 */     this.acting = false;
/* 47 */     return flag;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumActionResult processRightClick(EntityPlayer player, World worldIn, EnumHand stack) {
/* 52 */     this.acting = true;
/* 53 */     EnumActionResult enumactionresult = super.processRightClick(player, worldIn, stack);
/* 54 */     this.acting = false;
/* 55 */     return enumactionresult;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumActionResult processRightClickBlock(EntityPlayerSP player, WorldClient worldIn, BlockPos stack, EnumFacing pos, Vec3d facing, EnumHand vec) {
/* 60 */     this.acting = true;
/* 61 */     this.lastClickBlockPos = stack;
/* 62 */     EnumActionResult enumactionresult = super.processRightClickBlock(player, worldIn, stack, pos, facing, vec);
/* 63 */     this.acting = false;
/* 64 */     return enumactionresult;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult interactWithEntity(EntityPlayer player, Entity target, EnumHand heldItem) {
/* 72 */     this.lastClickEntity = target;
/* 73 */     return super.interactWithEntity(player, target, heldItem);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult interactWithEntity(EntityPlayer player, Entity target, RayTraceResult raytrace, EnumHand heldItem) {
/* 81 */     this.lastClickEntity = target;
/* 82 */     return super.interactWithEntity(player, target, raytrace, heldItem);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isActing() {
/* 87 */     return this.acting;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getLastClickBlockPos() {
/* 92 */     return this.lastClickBlockPos;
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getLastClickEntity() {
/* 97 */     return this.lastClickEntity;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\PlayerControllerOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */