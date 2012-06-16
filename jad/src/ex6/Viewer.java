/*     */ package ex6;
/*     */ 
/*     */ import com.sun.opengl.util.FPSAnimator;
/*     */ import com.sun.opengl.util.GLUT;
/*     */ import com.sun.opengl.util.texture.Texture;
/*     */ import ex6.models.Meteorite;
/*     */ import ex6.models.Spaceship;
/*     */ import ex6.models.Vec;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import javax.media.opengl.DebugGL;
/*     */ import javax.media.opengl.GL;
/*     */ import javax.media.opengl.GLAutoDrawable;
/*     */ import javax.media.opengl.GLEventListener;
/*     */ import javax.media.opengl.glu.GLU;
/*     */ import javax.media.opengl.glu.GLUquadric;
/*     */ 
/*     */ public class Viewer
/*     */   implements GLEventListener
/*     */ {
/*     */   private GameLogic game;
/*     */   private static final double ORTHO_PROJECTION_SIZE = 20.0D;
/*     */   private static final double NEAR = 1.0D;
/*     */   private static final double FAR = 20000.0D;
/*     */   private static final double ROTATION_COEF_SKY = 1.333333333333333D;
/*     */   private static final double ROTATION_COEF_SHIP = 1.0D;
/*     */   private static final double ROTATION_COEF_BELT = 0.5D;
/*     */   private Meteorite testMeteor;
/*     */   private Spaceship spaceship;
/*     */   private FPSAnimator ani;
/*  40 */   private boolean isShowShip = true;
/*  41 */   private boolean isShipMark = false;
/*  42 */   private boolean isOrthographic = false;
/*  43 */   private boolean isReshape = false;
/*     */ 
/*     */   public Viewer(GameLogic game, Spaceship spaceship)
/*     */   {
/*  47 */     this.game = game;
/*  48 */     this.spaceship = spaceship;
/*  49 */     this.testMeteor = new Meteorite(spaceship.center(), spaceship.radius());
/*  50 */     this.testMeteor.setCheckerTexture();
/*     */   }
/*     */ 
/*     */   public void display(GLAutoDrawable drawable)
/*     */   {
/*  55 */     if (this.isReshape) {
/*  56 */       reshape(drawable, 0, 0, drawable.getWidth(), drawable.getHeight());
/*  57 */       this.isReshape = false;
/*     */     }
/*     */ 
/*  60 */     GL gl = drawable.getGL();
/*  61 */     this.game.update();
/*     */ 
/*  64 */     gl.glClear(16640);
/*  65 */     gl.glMatrixMode(5888);
/*  66 */     gl.glLoadIdentity();
/*     */ 
/*  68 */     GLU glu = new GLU();
/*  69 */     GLUquadric quad = glu.gluNewQuadric();
/*  70 */     glu.gluQuadricTexture(quad, true);
/*     */ 
/*  73 */     gl.glLightfv(16384, 4609, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, 0);
/*  74 */     gl.glLightfv(16384, 4608, new float[] { 0.0F, 0.0F, 0.0F, 0.0F }, 0);
/*  75 */     gl.glLightfv(16384, 4610, new float[] { 0.0F, 0.0F, 0.0F, 0.0F }, 0);
/*  76 */     gl.glLightfv(16384, 4611, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
/*     */ 
/*  78 */     gl.glLightfv(16385, 4609, new float[] { 0.7F, 0.7F, 0.7F, 1.0F }, 0);
/*  79 */     gl.glLightfv(16385, 4608, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, 0);
/*  80 */     gl.glLightfv(16385, 4610, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, 0);
/*  81 */     gl.glLightfv(16385, 4611, new float[] { 3.0F, 10.0F, -10.0F, 1.0F }, 0);
/*     */ 
/*  83 */     gl.glLightfv(16386, 4609, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, 0);
/*  84 */     gl.glLightfv(16386, 4608, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, 0);
/*  85 */     gl.glLightfv(16386, 4610, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
/*  86 */     gl.glLightfv(16386, 4611, new float[] { 5.0F, 10.0F, -10.0F, 1.0F }, 0);
/*     */ 
/*  89 */     gl.glEnable(16384);
/*  90 */     gl.glDepthMask(false);
/*  91 */     gl.glPushMatrix();
/*  92 */     Textures.texStars.bind();
/*  93 */     Textures.texStars.enable();
/*  94 */     gl.glMaterialfv(1032, 4609, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, 0);
/*  95 */     gl.glMaterialfv(1032, 4608, new float[] { 0.0F, 0.0F, 0.0F, 0.2F }, 0);
/*  96 */     gl.glRotated(1.333333333333333D * this.game.getAngle(), 0.0D, 0.0D, 1.0D);
/*  97 */     gl.glRotated(this.game.getAngle(), 0.0D, 1.0D, 0.0D);
/*  98 */     gl.glRotated(90.0D, 1.0D, 0.0D, 0.0D);
/*  99 */     glu.gluSphere(quad, 100.0D, 32, 32);
/* 100 */     Textures.texStars.disable();
/* 101 */     gl.glPopMatrix();
/* 102 */     gl.glDepthMask(true);
/* 103 */     gl.glDisable(16384);
/*     */ 
/* 106 */     gl.glTranslated(0.0D, -1.3D, -8.0D);
/*     */ 
/* 109 */     if (this.isShowShip) {
/* 110 */       gl.glPushMatrix();
/* 111 */       gl.glEnable(16385);
/* 112 */       gl.glRotated(-1.0D * this.game.getAngle(), 0.0D, 0.0D, 1.0D);
/* 113 */       gl.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
/* 114 */       gl.glRotated(-90.0D, 1.0D, 0.0D, 0.0D);
/* 115 */       this.spaceship.render(gl);
/* 116 */       gl.glDisable(16385);
/* 117 */       gl.glPopMatrix();
/*     */     }
/*     */ 
/* 122 */     gl.glEnable(16386);
/* 123 */     gl.glDepthMask(false);
/* 124 */     gl.glEnable(2884);
/* 125 */     gl.glPushMatrix();
/* 126 */     gl.glRotated(0.5D * this.game.getAngle(), 0.0D, 0.0D, 1.0D);
/* 127 */     gl.glRotated(this.game.getAngle(), 0.0D, 1.0D, 0.0D);
/* 128 */     LinkedList meteors = this.game.getMeteors();
/* 129 */     for (Iterator iter = meteors.descendingIterator(); iter.hasNext(); ) {
/* 130 */       Meteorite meteor = (Meteorite)iter.next();
/* 131 */       meteor.render(gl);
/*     */     }
/* 133 */     gl.glMaterialfv(1032, 4608, new float[] { 0.0F, 0.0F, 0.0F, 0.2F }, 0);
/* 134 */     gl.glPopMatrix();
/* 135 */     gl.glDisable(2884);
/* 136 */     gl.glDepthMask(true);
/*     */ 
/* 140 */     if (this.isShipMark) {
/* 141 */       gl.glPushMatrix();
/* 142 */       gl.glRotated(-1.0D * this.game.getAngle(), 0.0D, 0.0D, 1.0D);
/* 143 */       gl.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
/* 144 */       gl.glRotated(-90.0D, 1.0D, 0.0D, 0.0D);
/* 145 */       this.testMeteor.render(gl);
/* 146 */       gl.glPopMatrix();
/*     */     }
/*     */ 
/* 149 */     if (this.game.collisionPoint != null) {
/* 150 */       gl.glPushMatrix();
/* 151 */       gl.glPointSize(3.0F);
/* 152 */       gl.glRotated(0.5D * this.game.getAngle(), 0.0D, 0.0D, 1.0D);
/* 153 */       gl.glRotated(this.game.getAngle(), 0.0D, 1.0D, 0.0D);
/* 154 */       gl.glDisable(2929);
/* 155 */       gl.glDisable(2896);
/* 156 */       gl.glBegin(1);
/* 157 */       gl.glColor3d(1.0D, 1.0D, 0.0D);
/* 158 */       gl.glVertex3d(this.game.collisionMeteor.center().x, this.game.collisionMeteor.center().y, this.game.collisionMeteor.center().z);
/* 159 */       gl.glVertex3d(this.spaceship.center().x, this.spaceship.center().y, this.spaceship.center().z);
/* 160 */       gl.glEnd();
/* 161 */       gl.glBegin(0);
/* 162 */       gl.glColor3d(1.0D, 0.0D, 0.0D);
/* 163 */       gl.glVertex3d(this.game.collisionPoint.x, this.game.collisionPoint.y, this.game.collisionPoint.z);
/* 164 */       gl.glEnd();
/* 165 */       gl.glEnable(2929);
/* 166 */       gl.glEnable(2896);
/* 167 */       gl.glPopMatrix();
/*     */     }
/*     */ 
/* 171 */     gl.glDisable(16386);
/*     */ 
/* 174 */     gl.glDisable(2929);
/* 175 */     gl.glDisable(2896);
/* 176 */     GLUT glut = new GLUT();
/* 177 */     int lh = glut.glutBitmapWidth(7, 'H') + 5;
/* 178 */     gl.glColor3d(0.0D, 0.5D, 0.0D);
/* 179 */     gl.glWindowPos2d(5.0D, 5 + 2 * lh);
/* 180 */     glut.glutBitmapString(7, "Score: " + this.game.getScore());
/* 181 */     gl.glWindowPos2d(5.0D, 5 + lh);
/* 182 */     glut.glutBitmapString(7, "Asteroids: " + this.game.getMeteorCount());
/* 183 */     gl.glColor3d(0.3D, 0.3D, 0.1D);
/* 184 */     gl.glWindowPos2d(5.0D, 5.0D);
/* 185 */     glut.glutBitmapString(7, "Controls: s,m,g,p,j,r,->,<-");
/*     */ 
/* 187 */     if ((this.game.isGodMode()) && (!this.game.isPaused())) {
/* 188 */       gl.glColor3d(0.0D, 1.0D, 0.0D);
/* 189 */       gl.glWindowPos2d(5.0D, 5 + 3 * lh);
/* 190 */       glut.glutBitmapString(8, "GHOST MODE");
/*     */     }
/* 192 */     if (this.game.isGameOver()) {
/* 193 */       gl.glColor3d(1.0D, 0.0D, 0.0D);
/* 194 */       gl.glWindowPos2d(5.0D, 5 + 3 * lh);
/* 195 */       glut.glutBitmapString(8, "GAME OVER");
/*     */     }
/* 197 */     if ((this.game.isPaused()) && (!this.game.isGameOver())) {
/* 198 */       gl.glColor3d(1.0D, 1.0D, 0.0D);
/* 199 */       gl.glWindowPos2d(5.0D, 5 + 3 * lh);
/* 200 */       glut.glutBitmapString(8, "PAUSED");
/*     */     }
/* 202 */     gl.glEnable(2896);
/* 203 */     gl.glEnable(2929);
/*     */ 
/* 205 */     glu.gluDeleteQuadric(quad);
/*     */   }
/*     */ 
/*     */   public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void init(GLAutoDrawable drawable)
/*     */   {
/* 215 */     drawable.setGL(new DebugGL(drawable.getGL()));
/* 216 */     GL gl = drawable.getGL();
/*     */ 
/* 218 */     gl.glEnable(3042);
/* 219 */     gl.glBlendFunc(770, 771);
/*     */ 
/* 221 */     gl.glLightModeli(2898, 1);
/* 222 */     gl.glEnable(2977);
/* 223 */     gl.glEnable(2929);
/* 224 */     gl.glDepthFunc(515);
/*     */ 
/* 227 */     gl.glEnable(2896);
/*     */ 
/* 229 */     Textures.load();
/*     */ 
/* 232 */     gl.glTexParameteri(3553, 10240, 9728);
/* 233 */     gl.glTexParameteri(3553, 10241, 9984);
/* 234 */     gl.glTexEnvf(8960, 8704, 8448.0F);
/*     */ 
/* 237 */     this.ani = new FPSAnimator(30, true);
/* 238 */     this.ani.add(drawable);
/* 239 */     startAnimation();
/*     */   }
/*     */ 
/*     */   public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
/*     */   {
/* 247 */     GL gl = drawable.getGL();
/*     */     double newScreenHeight;
/*     */     double aspectRatio;
/*     */     double newScreenWidth;
/*     */     double newScreenHeight;
/* 250 */     if (height < width) {
/* 251 */       double aspectRatio = height / width;
/* 252 */       double newScreenWidth = 20.0D;
/* 253 */       newScreenHeight = 20.0D * aspectRatio;
/*     */     } else {
/* 255 */       aspectRatio = width / height;
/* 256 */       newScreenWidth = 20.0D * aspectRatio;
/* 257 */       newScreenHeight = 20.0D;
/*     */     }
/*     */ 
/* 261 */     gl.glMatrixMode(5889);
/* 262 */     gl.glLoadIdentity();
/*     */ 
/* 265 */     if (this.isOrthographic) {
/* 266 */       gl.glOrtho(-newScreenWidth, newScreenWidth, -newScreenHeight, newScreenHeight, -20000.0D, 20000.0D);
/* 267 */       gl.glRotated(90.0D, 1.0D, 0.0D, 0.0D);
/* 268 */       gl.glTranslated(0.0D, -1.0D, Math.sqrt(aspectRatio) * 20.0D);
/*     */     } else {
/* 270 */       GLU glu = new GLU();
/* 271 */       glu.gluPerspective(60.0D, width / height, 1.0D, 20000.0D);
/*     */     }
/* 273 */     gl.glMatrixMode(5888);
/*     */   }
/*     */ 
/*     */   public void startAnimation() {
/* 277 */     this.ani.start();
/*     */   }
/*     */ 
/*     */   public void stopAnimation() {
/* 281 */     this.ani.stop();
/*     */   }
/*     */ 
/*     */   public void toggleShip() {
/* 285 */     this.isShowShip = (!this.isShowShip);
/*     */   }
/*     */ 
/*     */   public void toggleShipMark() {
/* 289 */     this.isShipMark = (!this.isShipMark);
/*     */   }
/*     */ 
/*     */   public void toggleProjection() {
/* 293 */     this.isOrthographic = (!this.isOrthographic);
/* 294 */     this.isReshape = true;
/*     */   }
/*     */ }

/* Location:           /Volumes/DataHD/Projects/Java/Graphics/last-graphics-exercise/jad/
 * Qualified Name:     ex6.Viewer
 * JD-Core Version:    0.6.0
 */