/*    */ package net.minecraft.client.gui.chat;
/*    */ 
/*    */ import com.mojang.text2speech.Narrator;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.toasts.GuiToast;
/*    */ import net.minecraft.client.gui.toasts.SystemToast;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.util.text.ChatType;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ 
/*    */ public class NarratorChatListener
/*    */   implements IChatListener {
/* 14 */   public static final NarratorChatListener field_193643_a = new NarratorChatListener();
/* 15 */   private final Narrator field_192580_a = Narrator.getNarrator();
/*    */ 
/*    */   
/*    */   public void func_192576_a(ChatType p_192576_1_, ITextComponent p_192576_2_) {
/* 19 */     int i = (Minecraft.getMinecraft()).gameSettings.field_192571_R;
/*    */     
/* 21 */     if (i != 0 && this.field_192580_a.active())
/*    */     {
/* 23 */       if (i == 1 || (i == 2 && p_192576_1_ == ChatType.CHAT) || (i == 3 && p_192576_1_ == ChatType.SYSTEM))
/*    */       {
/* 25 */         if (p_192576_2_ instanceof TextComponentTranslation && "chat.type.text".equals(((TextComponentTranslation)p_192576_2_).getKey())) {
/*    */           
/* 27 */           this.field_192580_a.say((new TextComponentTranslation("chat.type.text.narrate", ((TextComponentTranslation)p_192576_2_).getFormatArgs())).getUnformattedText());
/*    */         }
/*    */         else {
/*    */           
/* 31 */           this.field_192580_a.say(p_192576_2_.getUnformattedText());
/*    */         } 
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_193641_a(int p_193641_1_) {
/* 39 */     this.field_192580_a.clear();
/* 40 */     this.field_192580_a.say(String.valueOf((new TextComponentTranslation("options.narrator", new Object[0])).getUnformattedText()) + " : " + (new TextComponentTranslation(GameSettings.field_193632_b[p_193641_1_], new Object[0])).getUnformattedText());
/* 41 */     GuiToast guitoast = Minecraft.getMinecraft().func_193033_an();
/*    */     
/* 43 */     if (this.field_192580_a.active()) {
/*    */       
/* 45 */       if (p_193641_1_ == 0)
/*    */       {
/* 47 */         SystemToast.func_193657_a(guitoast, SystemToast.Type.NARRATOR_TOGGLE, (ITextComponent)new TextComponentTranslation("narrator.toast.disabled", new Object[0]), null);
/*    */       }
/*    */       else
/*    */       {
/* 51 */         SystemToast.func_193657_a(guitoast, SystemToast.Type.NARRATOR_TOGGLE, (ITextComponent)new TextComponentTranslation("narrator.toast.enabled", new Object[0]), (ITextComponent)new TextComponentTranslation(GameSettings.field_193632_b[p_193641_1_], new Object[0]));
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 56 */       SystemToast.func_193657_a(guitoast, SystemToast.Type.NARRATOR_TOGGLE, (ITextComponent)new TextComponentTranslation("narrator.toast.disabled", new Object[0]), (ITextComponent)new TextComponentTranslation("options.narrator.notavailable", new Object[0]));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_193640_a() {
/* 62 */     return this.field_192580_a.active();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_193642_b() {
/* 67 */     this.field_192580_a.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\chat\NarratorChatListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */