/*     */ package ex6;
/*     */ 
/*     */ import ex6.models.ISphericalObstacle;
/*     */ import ex6.models.Meteorite;
/*     */ import ex6.models.Spaceship;
/*     */ import ex6.models.Vec;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class GameLogic
/*     */ {
/*     */   private double angle;
/*     */   private int addMeteorCount;
/*     */   private int addMeteorEvery;
/*     */   private int meteorsAddedEachTime;
/*     */   private int meteorsCountLimit;
/*     */   private double meteorSpeed;
/*     */   private double meteorZLimit;
/*     */   private double meteorMinDistance;
/*     */   private double initialViewerDist;
/*     */   private boolean isGameOver;
/*     */   private boolean isPaused;
/*     */   private long score;
/*     */   private boolean isGodMode;
/*     */   private boolean isTurnLeft;
/*     */   private boolean isTurnRight;
/*     */   private static final double ANGLE_STEP = 4.0D;
/*     */   public static final double ANGLE_MAX = 60.0D;
/*     */   private static final double EPS = 1.0E-10D;
/*  40 */   private Random rand = new Random();
/*     */   private LinkedList<Meteorite> meteors;
/*     */   private LinkedList<Meteorite> remMeteors;
/*     */   private Spaceship spaceship;
/*     */   public Vec collisionPoint;
/*     */   public Meteorite collisionMeteor;
/*     */ 
/*     */   public GameLogic(Spaceship spaceship)
/*     */   {
/*  51 */     this.spaceship = spaceship;
/*  52 */     restart();
/*     */   }
/*     */ 
/*     */   public synchronized void update()
/*     */   {
/*  59 */     if ((!this.isGameOver) && (!this.isPaused)) {
/*  60 */       updateAngle();
/*  61 */       updateMeteors();
/*  62 */       if (!this.isGodMode) {
/*  63 */         checkCollision();
/*  64 */         this.score += 1L;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateAngle()
/*     */   {
/*  74 */     if ((this.isTurnLeft) && (!this.isTurnRight)) {
/*  75 */       this.angle -= 4.0D;
/*  76 */       if (this.angle < -60.0D)
/*  77 */         this.angle = -60.0D;
/*     */     }
/*  79 */     if ((!this.isTurnLeft) && (this.isTurnRight)) {
/*  80 */       this.angle += 4.0D;
/*  81 */       if (this.angle > 60.0D) {
/*  82 */         this.angle = 60.0D;
/*     */       }
/*     */     }
/*  85 */     if ((!this.isTurnLeft) && (!this.isTurnRight)) {
/*  86 */       if (this.angle > 1.0E-10D) {
/*  87 */         this.angle -= 1.0D;
/*  88 */         if (this.angle < -1.0E-10D)
/*  89 */           this.angle = 0.0D;
/*     */       }
/*  91 */       if (this.angle < -1.0E-10D) {
/*  92 */         this.angle += 1.0D;
/*  93 */         if (this.angle > 1.0E-10D)
/*  94 */           this.angle = 0.0D;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateMeteors()
/*     */   {
/* 103 */     this.addMeteorCount -= 1;
/* 104 */     if (this.addMeteorCount == 0) {
/* 105 */       this.addMeteorCount = this.addMeteorEvery;
/* 106 */       addMeteor();
/*     */     }
/*     */ 
/* 110 */     double angleRad = this.angle / 180.0D * Math.PI;
/* 111 */     for (Meteorite meteor : this.meteors) {
/* 112 */       Vec direction = new Vec(-this.meteorSpeed * Math.sin(angleRad), 0.0D, this.meteorSpeed * Math.cos(angleRad));
/*     */ 
/* 114 */       meteor.move(direction);
/* 115 */       if (meteor.center().z + meteor.radius() > this.meteorZLimit) {
/* 116 */         this.remMeteors.add(meteor);
/*     */       }
/*     */     }
/*     */ 
/* 120 */     this.meteors.removeAll(this.remMeteors);
/* 121 */     this.remMeteors.clear();
/*     */   }
/*     */ 
/*     */   private void checkCollision() {
/* 125 */     for (Meteorite meteor : this.meteors) {
/* 126 */       double dist = dist(meteor, this.spaceship);
/* 127 */       if (dist <= 0.0D) {
/* 128 */         this.collisionMeteor = meteor;
/* 129 */         this.collisionPoint = Vec.mult(0.5D, Vec.add(meteor.center(), this.spaceship.center()));
/* 130 */         this.spaceship.setStarted(false);
/* 131 */         this.isGameOver = true;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addMeteor() {
/* 137 */     if (this.meteors.size() >= this.meteorsCountLimit)
/* 138 */       return;
/* 139 */     int maxAttempts = 20;
/*     */ 
/* 141 */     for (int k = 0; k < this.meteorsAddedEachTime; k++)
/*     */     {
/* 143 */       int i = 0; if (i < 20) {
/* 144 */         double r = 0.5D + this.rand.nextDouble();
/* 145 */         double angleRad = 3.0D * (this.rand.nextDouble() - 0.5D) * 60.0D / 180.0D * Math.PI;
/* 146 */         double x = this.initialViewerDist * Math.sin(angleRad);
/* 147 */         double y = this.rand.nextDouble() - 0.5D;
/* 148 */         double z = -this.initialViewerDist * Math.cos(angleRad);
/* 149 */         Meteorite newMeteor = new Meteorite(new Vec(x, y, z), r);
/*     */ 
/* 160 */         this.meteors.add(newMeteor);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTurnLeft(boolean isTurnLeft)
/*     */   {
/* 167 */     this.isTurnLeft = isTurnLeft;
/*     */   }
/*     */ 
/*     */   public void setTurnRight(boolean isTurnRight) {
/* 171 */     this.isTurnRight = isTurnRight;
/*     */   }
/*     */ 
/*     */   public double getAngle() {
/* 175 */     return this.angle;
/*     */   }
/*     */ 
/*     */   public void setAngle(double angle) {
/* 179 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */   public LinkedList<Meteorite> getMeteors() {
/* 183 */     return this.meteors;
/*     */   }
/*     */ 
/*     */   private double dist(ISphericalObstacle a, ISphericalObstacle b) {
/* 187 */     return Vec.sub(a.center(), b.center()).length() - a.radius() - b.radius();
/*     */   }
/*     */ 
/*     */   public void restart() {
/* 191 */     this.meteors = new LinkedList();
/* 192 */     this.remMeteors = new LinkedList();
/* 193 */     this.angle = 0.0D;
/* 194 */     this.addMeteorEvery = 1;
/* 195 */     this.addMeteorCount = this.addMeteorEvery;
/* 196 */     this.meteorsAddedEachTime = 5;
/* 197 */     this.meteorSpeed = 0.1D;
/* 198 */     this.meteorZLimit = 50.0D;
/* 199 */     this.meteorMinDistance = 20.0D;
/* 200 */     this.meteorsCountLimit = 1000;
/* 201 */     this.initialViewerDist = 300.0D;
/* 202 */     this.collisionPoint = null;
/* 203 */     this.collisionMeteor = null;
/* 204 */     this.isPaused = false;
/* 205 */     this.spaceship.setStarted(true);
/* 206 */     this.score = 0L;
/* 207 */     this.isGodMode = false;
/* 208 */     this.isGameOver = false;
/*     */   }
/*     */ 
/*     */   public void togglePause() {
/* 212 */     this.isPaused = (!this.isPaused);
/*     */   }
/*     */ 
/*     */   public boolean isPaused() {
/* 216 */     return this.isPaused;
/*     */   }
/*     */ 
/*     */   public long getScore() {
/* 220 */     return this.score;
/*     */   }
/*     */ 
/*     */   public int getMeteorCount() {
/* 224 */     return this.meteors.size();
/*     */   }
/*     */ 
/*     */   public boolean isGameOver() {
/* 228 */     return this.isGameOver;
/*     */   }
/*     */ 
/*     */   public boolean isGodMode() {
/* 232 */     return this.isGodMode;
/*     */   }
/*     */ 
/*     */   public void toggleGodMode() {
/* 236 */     if (!this.isGameOver)
/* 237 */       this.isGodMode = (!this.isGodMode);
/*     */   }
/*     */ }

/* Location:           /Volumes/DataHD/Projects/Java/Graphics/last-graphics-exercise/jad/
 * Qualified Name:     ex6.GameLogic
 * JD-Core Version:    0.6.0
 */