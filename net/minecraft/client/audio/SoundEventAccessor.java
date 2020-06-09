/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ 
/*    */ public class SoundEventAccessor
/*    */   implements ISoundEventAccessor<Sound> {
/* 13 */   private final List<ISoundEventAccessor<Sound>> accessorList = Lists.newArrayList();
/* 14 */   private final Random rnd = new Random();
/*    */   
/*    */   private final ResourceLocation location;
/*    */   private final ITextComponent subtitle;
/*    */   
/*    */   public SoundEventAccessor(ResourceLocation locationIn, @Nullable String subtitleIn) {
/* 20 */     this.location = locationIn;
/* 21 */     this.subtitle = (subtitleIn == null) ? null : (ITextComponent)new TextComponentTranslation(subtitleIn, new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWeight() {
/* 26 */     int i = 0;
/*    */     
/* 28 */     for (ISoundEventAccessor<Sound> isoundeventaccessor : this.accessorList)
/*    */     {
/* 30 */       i += isoundeventaccessor.getWeight();
/*    */     }
/*    */     
/* 33 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public Sound cloneEntry() {
/* 38 */     int i = getWeight();
/*    */     
/* 40 */     if (!this.accessorList.isEmpty() && i != 0) {
/*    */       
/* 42 */       int j = this.rnd.nextInt(i);
/*    */       
/* 44 */       for (ISoundEventAccessor<Sound> isoundeventaccessor : this.accessorList) {
/*    */         
/* 46 */         j -= isoundeventaccessor.getWeight();
/*    */         
/* 48 */         if (j < 0)
/*    */         {
/* 50 */           return isoundeventaccessor.cloneEntry();
/*    */         }
/*    */       } 
/*    */       
/* 54 */       return SoundHandler.MISSING_SOUND;
/*    */     } 
/*    */ 
/*    */     
/* 58 */     return SoundHandler.MISSING_SOUND;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSound(ISoundEventAccessor<Sound> p_188715_1_) {
/* 64 */     this.accessorList.add(p_188715_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getLocation() {
/* 69 */     return this.location;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ITextComponent getSubtitle() {
/* 75 */     return this.subtitle;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\SoundEventAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */