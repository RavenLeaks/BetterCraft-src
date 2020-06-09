/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerEnchantment;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.IInteractionObject;
/*     */ 
/*     */ public class TileEntityEnchantmentTable
/*     */   extends TileEntity implements ITickable, IInteractionObject {
/*     */   public int tickCount;
/*     */   public float pageFlip;
/*     */   public float pageFlipPrev;
/*     */   public float flipT;
/*     */   public float flipA;
/*     */   public float bookSpread;
/*     */   public float bookSpreadPrev;
/*     */   public float bookRotation;
/*     */   public float bookRotationPrev;
/*     */   public float tRot;
/*  28 */   private static final Random rand = new Random();
/*     */   
/*     */   private String customName;
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/*  33 */     super.writeToNBT(compound);
/*     */     
/*  35 */     if (hasCustomName())
/*     */     {
/*  37 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */     
/*  40 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  45 */     super.readFromNBT(compound);
/*     */     
/*  47 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/*  49 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  58 */     this.bookSpreadPrev = this.bookSpread;
/*  59 */     this.bookRotationPrev = this.bookRotation;
/*  60 */     EntityPlayer entityplayer = this.world.getClosestPlayer((this.pos.getX() + 0.5F), (this.pos.getY() + 0.5F), (this.pos.getZ() + 0.5F), 3.0D, false);
/*     */     
/*  62 */     if (entityplayer != null) {
/*     */       
/*  64 */       double d0 = entityplayer.posX - (this.pos.getX() + 0.5F);
/*  65 */       double d1 = entityplayer.posZ - (this.pos.getZ() + 0.5F);
/*  66 */       this.tRot = (float)MathHelper.atan2(d1, d0);
/*  67 */       this.bookSpread += 0.1F;
/*     */       
/*  69 */       if (this.bookSpread < 0.5F || rand.nextInt(40) == 0)
/*     */       {
/*  71 */         float f1 = this.flipT;
/*     */ 
/*     */         
/*     */         do {
/*  75 */           this.flipT += (rand.nextInt(4) - rand.nextInt(4));
/*     */         }
/*  77 */         while (f1 == this.flipT);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  86 */       this.tRot += 0.02F;
/*  87 */       this.bookSpread -= 0.1F;
/*     */     } 
/*     */     
/*  90 */     while (this.bookRotation >= 3.1415927F)
/*     */     {
/*  92 */       this.bookRotation -= 6.2831855F;
/*     */     }
/*     */     
/*  95 */     while (this.bookRotation < -3.1415927F)
/*     */     {
/*  97 */       this.bookRotation += 6.2831855F;
/*     */     }
/*     */     
/* 100 */     while (this.tRot >= 3.1415927F)
/*     */     {
/* 102 */       this.tRot -= 6.2831855F;
/*     */     }
/*     */     
/* 105 */     while (this.tRot < -3.1415927F)
/*     */     {
/* 107 */       this.tRot += 6.2831855F;
/*     */     }
/*     */     
/*     */     float f2;
/*     */     
/* 112 */     for (f2 = this.tRot - this.bookRotation; f2 >= 3.1415927F; f2 -= 6.2831855F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     while (f2 < -3.1415927F)
/*     */     {
/* 119 */       f2 += 6.2831855F;
/*     */     }
/*     */     
/* 122 */     this.bookRotation += f2 * 0.4F;
/* 123 */     this.bookSpread = MathHelper.clamp(this.bookSpread, 0.0F, 1.0F);
/* 124 */     this.tickCount++;
/* 125 */     this.pageFlipPrev = this.pageFlip;
/* 126 */     float f = (this.flipT - this.pageFlip) * 0.4F;
/* 127 */     float f3 = 0.2F;
/* 128 */     f = MathHelper.clamp(f, -0.2F, 0.2F);
/* 129 */     this.flipA += (f - this.flipA) * 0.9F;
/* 130 */     this.pageFlip += this.flipA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 138 */     return hasCustomName() ? this.customName : "container.enchant";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 146 */     return (this.customName != null && !this.customName.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomName(String customNameIn) {
/* 151 */     this.customName = customNameIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent getDisplayName() {
/* 159 */     return hasCustomName() ? (ITextComponent)new TextComponentString(getName()) : (ITextComponent)new TextComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 164 */     return (Container)new ContainerEnchantment(playerInventory, this.world, this.pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 169 */     return "minecraft:enchanting_table";
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityEnchantmentTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */