/*     */ package net.minecraft.util;public enum EnumFacing implements IStringSerializable { private final int index; private final int opposite; private final int horizontalIndex; private final String name; private final Axis axis; private final AxisDirection axisDirection; private final Vec3i directionVec; public static final EnumFacing[] VALUES; private static final EnumFacing[] HORIZONTALS;
/*     */   private static final Map<String, EnumFacing> NAME_LOOKUP;
/*     */   
/*     */   EnumFacing(int indexIn, int oppositeIn, int horizontalIndexIn, String nameIn, AxisDirection axisDirectionIn, Axis axisIn, Vec3i directionVecIn) {
/*     */     this.index = indexIn;
/*     */     this.horizontalIndex = horizontalIndexIn;
/*     */     this.opposite = oppositeIn;
/*     */     this.name = nameIn;
/*     */     this.axis = axisIn;
/*     */     this.axisDirection = axisDirectionIn;
/*     */     this.directionVec = directionVecIn;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/*     */     return this.index;
/*     */   }
/*     */   
/*  18 */   DOWN(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vec3i(0, -1, 0)),
/*  19 */   UP(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vec3i(0, 1, 0)),
/*  20 */   NORTH(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vec3i(0, 0, -1)),
/*  21 */   SOUTH(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vec3i(0, 0, 1)),
/*  22 */   WEST(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vec3i(-1, 0, 0)),
/*  23 */   EAST(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vec3i(1, 0, 0));
/*     */   public int getHorizontalIndex() { return this.horizontalIndex; }
/*     */   public AxisDirection getAxisDirection() { return this.axisDirection; }
/*     */   public EnumFacing getOpposite() { return VALUES[this.opposite]; }
/*     */   public EnumFacing rotateAround(Axis axis) { switch (axis) { case null: if (this != WEST && this != EAST)
/*     */           return rotateX();  return this;case Y: if (this != UP && this != DOWN)
/*     */           return rotateY();  return this;case Z: if (this != NORTH && this != SOUTH)
/*     */           return rotateZ();  return this; }  throw new IllegalStateException("Unable to get CW facing for axis " + axis); }
/*     */   public EnumFacing rotateY() { switch (this) { case NORTH: return EAST;case EAST: return SOUTH;case SOUTH: return WEST;case WEST: return NORTH; }  throw new IllegalStateException("Unable to get Y-rotated facing of " + this); }
/*     */   private EnumFacing rotateX() { switch (this) { case NORTH: return DOWN;default: throw new IllegalStateException("Unable to get X-rotated facing of " + this);case SOUTH: return UP;case UP: return NORTH;case null: break; }  return SOUTH; }
/*     */   private EnumFacing rotateZ() { switch (this) { case EAST: return DOWN;default: throw new IllegalStateException("Unable to get Z-rotated facing of " + this);case WEST: return UP;case UP: return EAST;case null: break; }  return WEST; }
/*     */   public EnumFacing rotateYCCW() { switch (this) { case NORTH: return WEST;
/*     */       case EAST: return NORTH;
/*     */       case SOUTH: return EAST;
/*     */       case WEST: return SOUTH; }  throw new IllegalStateException("Unable to get CCW facing of " + this); }
/*     */   public int getFrontOffsetX() { return (this.axis == Axis.X) ? this.axisDirection.getOffset() : 0; }
/*     */   public int getFrontOffsetY() { return (this.axis == Axis.Y) ? this.axisDirection.getOffset() : 0; }
/*     */   public int getFrontOffsetZ() { return (this.axis == Axis.Z) ? this.axisDirection.getOffset() : 0; }
/*  41 */   public String getName2() { return this.name; } static { VALUES = new EnumFacing[6];
/*     */ 
/*     */     
/*  44 */     HORIZONTALS = new EnumFacing[4];
/*  45 */     NAME_LOOKUP = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     byte b;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     EnumFacing[] arrayOfEnumFacing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     for (i = (arrayOfEnumFacing = values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/* 380 */       VALUES[enumfacing.index] = enumfacing;
/*     */       
/* 382 */       if (enumfacing.getAxis().isHorizontal())
/*     */       {
/* 384 */         HORIZONTALS[enumfacing.horizontalIndex] = enumfacing;
/*     */       }
/*     */       
/* 387 */       NAME_LOOKUP.put(enumfacing.getName2().toLowerCase(Locale.ROOT), enumfacing); b++; }  } public Axis getAxis() { return this.axis; } @Nullable public static EnumFacing byName(String name) { return (name == null) ? null : NAME_LOOKUP.get(name.toLowerCase(Locale.ROOT)); } public static EnumFacing getFront(int index) { return VALUES[MathHelper.abs(index % VALUES.length)]; } public static EnumFacing getHorizontal(int horizontalIndexIn) { return HORIZONTALS[MathHelper.abs(horizontalIndexIn % HORIZONTALS.length)]; } public static EnumFacing fromAngle(double angle) { return getHorizontal(MathHelper.floor(angle / 90.0D + 0.5D) & 0x3); } public float getHorizontalAngle() { return ((this.horizontalIndex & 0x3) * 90); } public static EnumFacing random(Random rand) { return values()[rand.nextInt((values()).length)]; } public static EnumFacing getFacingFromVector(float x, float y, float z) { EnumFacing enumfacing = NORTH; float f = Float.MIN_VALUE; byte b; int i; EnumFacing[] arrayOfEnumFacing; for (i = (arrayOfEnumFacing = values()).length, b = 0; b < i; ) { EnumFacing enumfacing1 = arrayOfEnumFacing[b]; float f1 = x * enumfacing1.directionVec.getX() + y * enumfacing1.directionVec.getY() + z * enumfacing1.directionVec.getZ(); if (f1 > f) { f = f1; enumfacing = enumfacing1; }  b++; }  return enumfacing; } public String toString() { return this.name; }
/*     */   public String getName() { return this.name; }
/*     */   public static EnumFacing getFacingFromAxis(AxisDirection axisDirectionIn, Axis axisIn) { byte b; int i; EnumFacing[] arrayOfEnumFacing; for (i = (arrayOfEnumFacing = values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b]; if (enumfacing.getAxisDirection() == axisDirectionIn && enumfacing.getAxis() == axisIn) return enumfacing;  b++; }  throw new IllegalArgumentException("No such direction: " + axisDirectionIn + " " + axisIn); }
/*     */   public static EnumFacing func_190914_a(BlockPos p_190914_0_, EntityLivingBase p_190914_1_) { if (Math.abs(p_190914_1_.posX - (p_190914_0_.getX() + 0.5F)) < 2.0D && Math.abs(p_190914_1_.posZ - (p_190914_0_.getZ() + 0.5F)) < 2.0D) { double d0 = p_190914_1_.posY + p_190914_1_.getEyeHeight(); if (d0 - p_190914_0_.getY() > 2.0D) return UP;  if (p_190914_0_.getY() - d0 > 0.0D) return DOWN;  }  return p_190914_1_.getHorizontalFacing().getOpposite(); }
/*     */   public Vec3i getDirectionVec() { return this.directionVec; }
/* 392 */   public enum Axis implements Predicate<EnumFacing>, IStringSerializable { X("x", EnumFacing.Plane.HORIZONTAL),
/* 393 */     Y("y", EnumFacing.Plane.VERTICAL),
/* 394 */     Z("z", EnumFacing.Plane.HORIZONTAL);
/*     */     
/* 396 */     private static final Map<String, Axis> NAME_LOOKUP = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final EnumFacing.Plane plane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       Axis[] arrayOfAxis;
/* 448 */       for (i = (arrayOfAxis = values()).length, b = 0; b < i; ) { Axis enumfacing$axis = arrayOfAxis[b];
/*     */         
/* 450 */         NAME_LOOKUP.put(enumfacing$axis.getName2().toLowerCase(Locale.ROOT), enumfacing$axis); b++; } 
/*     */     } Axis(String name, EnumFacing.Plane plane) { this.name = name; this.plane = plane; } @Nullable public static Axis byName(String name) { return (name == null) ? null : NAME_LOOKUP.get(name.toLowerCase(Locale.ROOT)); } public String getName2() { return this.name; } public boolean isVertical() { return (this.plane == EnumFacing.Plane.VERTICAL); } public boolean isHorizontal() { return (this.plane == EnumFacing.Plane.HORIZONTAL); }
/*     */     public String toString() { return this.name; }
/*     */     public boolean apply(@Nullable EnumFacing p_apply_1_) { return (p_apply_1_ != null && p_apply_1_.getAxis() == this); }
/*     */     public EnumFacing.Plane getPlane() { return this.plane; }
/*     */     public String getName() { return this.name; } }
/* 456 */   public enum AxisDirection { POSITIVE(1, "Towards positive"),
/* 457 */     NEGATIVE(-1, "Towards negative");
/*     */     
/*     */     private final int offset;
/*     */     
/*     */     private final String description;
/*     */     
/*     */     AxisDirection(int offset, String description) {
/* 464 */       this.offset = offset;
/* 465 */       this.description = description;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOffset() {
/* 470 */       return this.offset;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 475 */       return this.description;
/*     */     } }
/*     */ 
/*     */   
/*     */   public enum Plane implements Predicate<EnumFacing>, Iterable<EnumFacing> {
/* 480 */     HORIZONTAL,
/* 481 */     VERTICAL;
/*     */ 
/*     */     
/*     */     public EnumFacing[] facings() {
/* 485 */       switch (this) {
/*     */         
/*     */         case null:
/* 488 */           return new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST };
/*     */         case VERTICAL:
/* 490 */           return new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN };
/*     */       } 
/* 492 */       throw new Error("Someone's been tampering with the universe!");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public EnumFacing random(Random rand) {
/* 498 */       EnumFacing[] aenumfacing = facings();
/* 499 */       return aenumfacing[rand.nextInt(aenumfacing.length)];
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean apply(@Nullable EnumFacing p_apply_1_) {
/* 504 */       return (p_apply_1_ != null && p_apply_1_.getAxis().getPlane() == this);
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<EnumFacing> iterator() {
/* 509 */       return (Iterator<EnumFacing>)Iterators.forArray((Object[])facings());
/*     */     }
/*     */   } }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\EnumFacing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */