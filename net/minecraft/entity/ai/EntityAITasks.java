/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class EntityAITasks
/*     */ {
/*  13 */   private static final Logger LOGGER = LogManager.getLogger();
/*  14 */   private final Set<EntityAITaskEntry> taskEntries = Sets.newLinkedHashSet();
/*  15 */   private final Set<EntityAITaskEntry> executingTaskEntries = Sets.newLinkedHashSet();
/*     */   
/*     */   private final Profiler theProfiler;
/*     */   
/*     */   private int tickCount;
/*  20 */   private int tickRate = 3;
/*     */   
/*     */   private int disabledControlFlags;
/*     */   
/*     */   public EntityAITasks(Profiler profilerIn) {
/*  25 */     this.theProfiler = profilerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTask(int priority, EntityAIBase task) {
/*  33 */     this.taskEntries.add(new EntityAITaskEntry(priority, task));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTask(EntityAIBase task) {
/*  41 */     Iterator<EntityAITaskEntry> iterator = this.taskEntries.iterator();
/*     */     
/*  43 */     while (iterator.hasNext()) {
/*     */       
/*  45 */       EntityAITaskEntry entityaitasks$entityaitaskentry = iterator.next();
/*  46 */       EntityAIBase entityaibase = entityaitasks$entityaitaskentry.action;
/*     */       
/*  48 */       if (entityaibase == task) {
/*     */         
/*  50 */         if (entityaitasks$entityaitaskentry.using) {
/*     */           
/*  52 */           entityaitasks$entityaitaskentry.using = false;
/*  53 */           entityaitasks$entityaitaskentry.action.resetTask();
/*  54 */           this.executingTaskEntries.remove(entityaitasks$entityaitaskentry);
/*     */         } 
/*     */         
/*  57 */         iterator.remove();
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateTasks() {
/*  65 */     this.theProfiler.startSection("goalSetup");
/*     */     
/*  67 */     if (this.tickCount++ % this.tickRate == 0) {
/*     */       
/*  69 */       for (EntityAITaskEntry entityaitasks$entityaitaskentry : this.taskEntries) {
/*     */         
/*  71 */         if (entityaitasks$entityaitaskentry.using) {
/*     */           
/*  73 */           if (!canUse(entityaitasks$entityaitaskentry) || !canContinue(entityaitasks$entityaitaskentry)) {
/*     */             
/*  75 */             entityaitasks$entityaitaskentry.using = false;
/*  76 */             entityaitasks$entityaitaskentry.action.resetTask();
/*  77 */             this.executingTaskEntries.remove(entityaitasks$entityaitaskentry);
/*     */           }  continue;
/*     */         } 
/*  80 */         if (canUse(entityaitasks$entityaitaskentry) && entityaitasks$entityaitaskentry.action.shouldExecute())
/*     */         {
/*  82 */           entityaitasks$entityaitaskentry.using = true;
/*  83 */           entityaitasks$entityaitaskentry.action.startExecuting();
/*  84 */           this.executingTaskEntries.add(entityaitasks$entityaitaskentry);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/*  90 */       Iterator<EntityAITaskEntry> iterator = this.executingTaskEntries.iterator();
/*     */       
/*  92 */       while (iterator.hasNext()) {
/*     */         
/*  94 */         EntityAITaskEntry entityaitasks$entityaitaskentry1 = iterator.next();
/*     */         
/*  96 */         if (!canContinue(entityaitasks$entityaitaskentry1)) {
/*     */           
/*  98 */           entityaitasks$entityaitaskentry1.using = false;
/*  99 */           entityaitasks$entityaitaskentry1.action.resetTask();
/* 100 */           iterator.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     this.theProfiler.endSection();
/*     */     
/* 107 */     if (!this.executingTaskEntries.isEmpty()) {
/*     */       
/* 109 */       this.theProfiler.startSection("goalTick");
/*     */       
/* 111 */       for (EntityAITaskEntry entityaitasks$entityaitaskentry2 : this.executingTaskEntries)
/*     */       {
/* 113 */         entityaitasks$entityaitaskentry2.action.updateTask();
/*     */       }
/*     */       
/* 116 */       this.theProfiler.endSection();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canContinue(EntityAITaskEntry taskEntry) {
/* 125 */     return taskEntry.action.continueExecuting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canUse(EntityAITaskEntry taskEntry) {
/* 134 */     if (this.executingTaskEntries.isEmpty())
/*     */     {
/* 136 */       return true;
/*     */     }
/* 138 */     if (isControlFlagDisabled(taskEntry.action.getMutexBits()))
/*     */     {
/* 140 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 144 */     for (EntityAITaskEntry entityaitasks$entityaitaskentry : this.executingTaskEntries) {
/*     */       
/* 146 */       if (entityaitasks$entityaitaskentry != taskEntry) {
/*     */         
/* 148 */         if (taskEntry.priority >= entityaitasks$entityaitaskentry.priority) {
/*     */           
/* 150 */           if (!areTasksCompatible(taskEntry, entityaitasks$entityaitaskentry))
/*     */           {
/* 152 */             return false; } 
/*     */           continue;
/*     */         } 
/* 155 */         if (!entityaitasks$entityaitaskentry.action.isInterruptible())
/*     */         {
/* 157 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 162 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean areTasksCompatible(EntityAITaskEntry taskEntry1, EntityAITaskEntry taskEntry2) {
/* 171 */     return ((taskEntry1.action.getMutexBits() & taskEntry2.action.getMutexBits()) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isControlFlagDisabled(int p_188528_1_) {
/* 176 */     return ((this.disabledControlFlags & p_188528_1_) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disableControlFlag(int p_188526_1_) {
/* 181 */     this.disabledControlFlags |= p_188526_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void enableControlFlag(int p_188525_1_) {
/* 186 */     this.disabledControlFlags &= p_188525_1_ ^ 0xFFFFFFFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setControlFlag(int p_188527_1_, boolean p_188527_2_) {
/* 191 */     if (p_188527_2_) {
/*     */       
/* 193 */       enableControlFlag(p_188527_1_);
/*     */     }
/*     */     else {
/*     */       
/* 197 */       disableControlFlag(p_188527_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   class EntityAITaskEntry
/*     */   {
/*     */     public final EntityAIBase action;
/*     */     public final int priority;
/*     */     public boolean using;
/*     */     
/*     */     public EntityAITaskEntry(int priorityIn, EntityAIBase task) {
/* 209 */       this.priority = priorityIn;
/* 210 */       this.action = task;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object p_equals_1_) {
/* 215 */       if (this == p_equals_1_)
/*     */       {
/* 217 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 221 */       return (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) ? this.action.equals(((EntityAITaskEntry)p_equals_1_).action) : false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 227 */       return this.action.hashCode();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAITasks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */