/*     */ package ex6.models;
/*     */ 
/*     */ public class Vec
/*     */ {
/*     */   protected static final int MINUS_ONE = -1;
/*     */   protected static final int ZERO = 0;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */ 
/*     */   public Vec()
/*     */   {
/*  33 */     this.x = 0.0D;
/*  34 */     this.y = 0.0D;
/*  35 */     this.z = 0.0D;
/*     */   }
/*     */ 
/*     */   public Vec(double x, double y, double z)
/*     */   {
/*  49 */     this.x = x;
/*  50 */     this.y = y;
/*  51 */     this.z = z;
/*     */   }
/*     */ 
/*     */   public Vec(Vec v)
/*     */   {
/*  61 */     this.x = v.x;
/*  62 */     this.y = v.y;
/*  63 */     this.z = v.z;
/*     */   }
/*     */ 
/*     */   public Vec clone()
/*     */   {
/*  70 */     return new Vec(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */   public void add(Vec v)
/*     */   {
/*  81 */     this.x += v.x;
/*  82 */     this.y += v.y;
/*  83 */     this.z += v.z;
/*     */   }
/*     */ 
/*     */   public void sub(Vec v)
/*     */   {
/*  93 */     this.x -= v.x;
/*  94 */     this.y -= v.y;
/*  95 */     this.z -= v.z;
/*     */   }
/*     */ 
/*     */   public void mac(double s, Vec v)
/*     */   {
/* 107 */     this.x += v.x * s;
/* 108 */     this.y += v.y * s;
/* 109 */     this.z += v.z * s;
/*     */   }
/*     */ 
/*     */   public void mult(double s)
/*     */   {
/* 119 */     this.x *= s;
/* 120 */     this.y *= s;
/* 121 */     this.z *= s;
/*     */   }
/*     */ 
/*     */   public void mult(Vec v)
/*     */   {
/* 131 */     this.x *= v.x;
/* 132 */     this.y *= v.y;
/* 133 */     this.z *= v.z;
/*     */   }
/*     */ 
/*     */   public void negate()
/*     */   {
/* 141 */     this.x = (-this.x);
/* 142 */     this.y = (-this.y);
/* 143 */     this.z = (-this.z);
/*     */   }
/*     */ 
/*     */   public double length()
/*     */   {
/* 152 */     return Math.sqrt(lengthSquared());
/*     */   }
/*     */ 
/*     */   public double lengthSquared()
/*     */   {
/* 161 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */ 
/*     */   public double dotProd(Vec v)
/*     */   {
/* 172 */     return this.x * v.x + this.y * v.y + this.z * v.z;
/*     */   }
/*     */ 
/*     */   public void normalize()
/*     */     throws ArithmeticException
/*     */   {
/* 182 */     if ((this.x == 0.0D) && (this.y == 0.0D) && (this.z == 0.0D)) {
/* 183 */       throw new ArithmeticException();
/*     */     }
/* 185 */     double currentLength = length();
/* 186 */     this.x /= currentLength;
/* 187 */     this.y /= currentLength;
/* 188 */     this.z /= currentLength;
/*     */   }
/*     */ 
/*     */   public boolean equals(Vec v)
/*     */   {
/* 199 */     return (this.x == v.x) && (this.y == v.y) && (this.z == v.z);
/*     */   }
/*     */ 
/*     */   public final double angle(Vec v)
/*     */   {
/* 211 */     double dotProduct = dotProd(this, v);
/* 212 */     double angle = dotProduct / (length() * v.length());
/* 213 */     return Math.acos(angle);
/*     */   }
/*     */ 
/*     */   public static double angle(Vec v1, Vec v2) {
/* 217 */     double dotProduct = dotProd(v1, v2);
/* 218 */     double angle = dotProduct / (v1.length() * v2.length());
/* 219 */     return Math.acos(angle);
/*     */   }
/*     */ 
/*     */   public static Vec crossProd(Vec a, Vec b)
/*     */   {
/* 241 */     double valX = a.y * b.z - b.y * a.z;
/* 242 */     double valY = a.z * b.x - a.x * b.z;
/* 243 */     double valZ = a.x * b.y - a.y * b.x;
/* 244 */     return new Vec(valX, valY, valZ);
/*     */   }
/*     */ 
/*     */   public Vec reflect(Vec normal)
/*     */   {
/* 255 */     Vec tempVec = mult(2.0D, mult(dotProd(normal), normal));
/* 256 */     return sub(this, tempVec);
/*     */   }
/*     */ 
/*     */   public static Vec add(Vec a, Vec b)
/*     */   {
/* 270 */     double valX = a.x + b.x;
/* 271 */     double valY = a.y + b.y;
/* 272 */     double valZ = a.z + b.z;
/* 273 */     return new Vec(valX, valY, valZ);
/*     */   }
/*     */ 
/*     */   public static Vec sub(Vec a, Vec b)
/*     */   {
/* 287 */     double valX = a.x - b.x;
/* 288 */     double valY = a.y - b.y;
/* 289 */     double valZ = a.z - b.z;
/* 290 */     return new Vec(valX, valY, valZ);
/*     */   }
/*     */ 
/*     */   public static Vec negate(Vec a)
/*     */   {
/* 301 */     return new Vec(-a.x, -a.y, -a.z);
/*     */   }
/*     */ 
/*     */   public static Vec mult(double s, Vec a)
/*     */   {
/* 314 */     return new Vec(a.x * s, a.y * s, a.z * s);
/*     */   }
/*     */ 
/*     */   public static Vec mult(Vec a, Vec b)
/*     */   {
/* 327 */     return new Vec(a.x * b.x, a.y * b.y, a.z * b.z);
/*     */   }
/*     */ 
/*     */   public static boolean equals(Vec a, Vec b)
/*     */   {
/* 340 */     return (a.x == b.x) && (a.y == b.y) && (a.z == b.z);
/*     */   }
/*     */ 
/*     */   public static double dotProd(Vec a, Vec b)
/*     */   {
/* 353 */     return a.x * b.x + a.y * b.y + a.z * b.z;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 362 */     StringBuilder newString = new StringBuilder("(");
/* 363 */     newString.append(this.x).append(',').append(this.y).append(',').append(this.z).append(')');
/* 364 */     return newString.toString();
/*     */   }
/*     */ }

/* Location:           /Volumes/DataHD/Projects/Java/Graphics/last-graphics-exercise/jad/
 * Qualified Name:     ex6.models.Vec
 * JD-Core Version:    0.6.0
 */