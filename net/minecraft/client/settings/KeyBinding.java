/*     */ package net.minecraft.client.settings;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.IntHashMap;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class KeyBinding
/*     */   implements Comparable<KeyBinding>
/*     */ {
/*  16 */   private static final Map<String, KeyBinding> KEYBIND_ARRAY = Maps.newHashMap();
/*  17 */   private static final IntHashMap<KeyBinding> HASH = new IntHashMap();
/*  18 */   private static final Set<String> KEYBIND_SET = Sets.newHashSet();
/*  19 */   private static final Map<String, Integer> field_193627_d = Maps.newHashMap();
/*     */   
/*     */   private final String keyDescription;
/*     */   
/*     */   private final int keyCodeDefault;
/*     */   
/*     */   private final String keyCategory;
/*     */   private int keyCode;
/*     */   private boolean pressed;
/*     */   private int pressTime;
/*     */   
/*     */   public static void onTick(int keyCode) {
/*  31 */     if (keyCode != 0) {
/*     */       
/*  33 */       KeyBinding keybinding = (KeyBinding)HASH.lookup(keyCode);
/*     */       
/*  35 */       if (keybinding != null)
/*     */       {
/*  37 */         keybinding.pressTime++;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setKeyBindState(int keyCode, boolean pressed) {
/*  44 */     if (keyCode != 0) {
/*     */       
/*  46 */       KeyBinding keybinding = (KeyBinding)HASH.lookup(keyCode);
/*     */       
/*  48 */       if (keybinding != null)
/*     */       {
/*  50 */         keybinding.pressed = pressed;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateKeyBindState() {
/*  57 */     for (KeyBinding keybinding : KEYBIND_ARRAY.values()) {
/*     */ 
/*     */       
/*     */       try {
/*  61 */         setKeyBindState(keybinding.keyCode, (keybinding.keyCode < 256 && Keyboard.isKeyDown(keybinding.keyCode)));
/*     */       }
/*  63 */       catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unPressAllKeys() {
/*  72 */     for (KeyBinding keybinding : KEYBIND_ARRAY.values())
/*     */     {
/*  74 */       keybinding.unpressKey();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void resetKeyBindingArrayAndHash() {
/*  80 */     HASH.clearMap();
/*     */     
/*  82 */     for (KeyBinding keybinding : KEYBIND_ARRAY.values())
/*     */     {
/*  84 */       HASH.addKey(keybinding.keyCode, keybinding);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Set<String> getKeybinds() {
/*  90 */     return KEYBIND_SET;
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyBinding(String description, int keyCode, String category) {
/*  95 */     this.keyDescription = description;
/*  96 */     this.keyCode = keyCode;
/*  97 */     this.keyCodeDefault = keyCode;
/*  98 */     this.keyCategory = category;
/*  99 */     KEYBIND_ARRAY.put(description, this);
/* 100 */     HASH.addKey(keyCode, this);
/* 101 */     KEYBIND_SET.add(category);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKeyDown() {
/* 109 */     if (this.keyCode == (Minecraft.getMinecraft()).gameSettings.keyBindForward.getKeyCode() && 
/* 110 */       (Minecraft.getMinecraft()).player.movementInput.forwardKeyDown) {
/* 111 */       (Minecraft.getMinecraft()).player.setSprinting(true);
/*     */     }
/*     */     
/* 114 */     return this.pressed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKeyCategory() {
/* 120 */     return this.keyCategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPressed() {
/* 129 */     if (this.pressTime == 0)
/*     */     {
/* 131 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 135 */     this.pressTime--;
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void unpressKey() {
/* 142 */     this.pressTime = 0;
/* 143 */     this.pressed = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKeyDescription() {
/* 148 */     return this.keyDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getKeyCodeDefault() {
/* 153 */     return this.keyCodeDefault;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getKeyCode() {
/* 158 */     return this.keyCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKeyCode(int keyCode) {
/* 163 */     this.keyCode = keyCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(KeyBinding p_compareTo_1_) {
/* 168 */     return this.keyCategory.equals(p_compareTo_1_.keyCategory) ? I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyDescription, new Object[0])) : ((Integer)field_193627_d.get(this.keyCategory)).compareTo(field_193627_d.get(p_compareTo_1_.keyCategory));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Supplier<String> func_193626_b(String p_193626_0_) {
/* 173 */     KeyBinding keybinding = KEYBIND_ARRAY.get(p_193626_0_);
/* 174 */     return (keybinding == null) ? (() -> paramString) : (() -> GameSettings.getKeyDisplayString(paramKeyBinding.getKeyCode()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 185 */     field_193627_d.put("key.categories.movement", Integer.valueOf(1));
/* 186 */     field_193627_d.put("key.categories.gameplay", Integer.valueOf(2));
/* 187 */     field_193627_d.put("key.categories.inventory", Integer.valueOf(3));
/* 188 */     field_193627_d.put("key.categories.creative", Integer.valueOf(4));
/* 189 */     field_193627_d.put("key.categories.multiplayer", Integer.valueOf(5));
/* 190 */     field_193627_d.put("key.categories.ui", Integer.valueOf(6));
/* 191 */     field_193627_d.put("key.categories.misc", Integer.valueOf(7));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\settings\KeyBinding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */