/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ public class CooldownTracker
/*    */ {
/* 12 */   private final Map<Item, Cooldown> cooldowns = Maps.newHashMap();
/*    */   
/*    */   private int ticks;
/*    */   
/*    */   public boolean hasCooldown(Item itemIn) {
/* 17 */     return (getCooldown(itemIn, 0.0F) > 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getCooldown(Item itemIn, float partialTicks) {
/* 22 */     Cooldown cooldowntracker$cooldown = this.cooldowns.get(itemIn);
/*    */     
/* 24 */     if (cooldowntracker$cooldown != null) {
/*    */       
/* 26 */       float f = (cooldowntracker$cooldown.expireTicks - cooldowntracker$cooldown.createTicks);
/* 27 */       float f1 = cooldowntracker$cooldown.expireTicks - this.ticks + partialTicks;
/* 28 */       return MathHelper.clamp(f1 / f, 0.0F, 1.0F);
/*    */     } 
/*    */ 
/*    */     
/* 32 */     return 0.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void tick() {
/* 38 */     this.ticks++;
/*    */     
/* 40 */     if (!this.cooldowns.isEmpty()) {
/*    */       
/* 42 */       Iterator<Map.Entry<Item, Cooldown>> iterator = this.cooldowns.entrySet().iterator();
/*    */       
/* 44 */       while (iterator.hasNext()) {
/*    */         
/* 46 */         Map.Entry<Item, Cooldown> entry = iterator.next();
/*    */         
/* 48 */         if (((Cooldown)entry.getValue()).expireTicks <= this.ticks) {
/*    */           
/* 50 */           iterator.remove();
/* 51 */           notifyOnRemove(entry.getKey());
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCooldown(Item itemIn, int ticksIn) {
/* 59 */     this.cooldowns.put(itemIn, new Cooldown(this.ticks, this.ticks + ticksIn, null));
/* 60 */     notifyOnSet(itemIn, ticksIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeCooldown(Item itemIn) {
/* 65 */     this.cooldowns.remove(itemIn);
/* 66 */     notifyOnRemove(itemIn);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void notifyOnSet(Item itemIn, int ticksIn) {}
/*    */ 
/*    */   
/*    */   protected void notifyOnRemove(Item itemIn) {}
/*    */ 
/*    */   
/*    */   class Cooldown
/*    */   {
/*    */     final int createTicks;
/*    */     
/*    */     final int expireTicks;
/*    */ 
/*    */     
/*    */     private Cooldown(int createTicksIn, int expireTicksIn) {
/* 84 */       this.createTicks = createTicksIn;
/* 85 */       this.expireTicks = expireTicksIn;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\CooldownTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */