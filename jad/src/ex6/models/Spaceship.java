/*     */ package ex6.models;
/*     */ 
/*     */ import com.sun.opengl.util.texture.Texture;
/*     */ import ex6.Textures;
/*     */
/*     */ import java.util.Random;
/*     */ import javax.media.opengl.GL;
/*     */ import javax.media.opengl.glu.GLU;
/*     */ import javax.media.opengl.glu.GLUquadric;
/*     */ 
/*     */ public class Spaceship
/*     */   implements IRenderable, ISphericalObstacle
/*     */ {
/*     */   private Texture texEngine;
/*     */   private GLU glu;
/*     */   private GLUquadric quad;
/*     */   private Mesh body;
/*  24 */   private final int res = 2;
/*     */ 
/*  26 */   private boolean isStarted = false;
/*     */ 
/* 209 */   private static final Vec origin = new Vec(0.0D, 0.0D, 0.0D);
/*     */ 
/*     */   public Spaceship()
/*     */   {
/*  29 */     this.body = new Mesh();
/*  30 */     this.body.vertices.add(new Vec(0.0D, -0.3D, -4.0D));
/*  31 */     this.body.vertices.add(new Vec(0.0D, 1.1D, 0.5D));
/*  32 */     this.body.vertices.add(new Vec(-1.2D, -0.2D, 0.5D));
/*  33 */     this.body.vertices.add(new Vec(1.2D, -0.2D, 0.5D));
/*  34 */     this.body.faces.add(new int[] { 2, 3, 1 });
/*  35 */     this.body.faces.add(new int[] { 1, 3 });
/*  36 */     this.body.faces.add(new int[] { 2, 1 });
/*  37 */     this.body.faces.add(new int[] { 3, 2 });
/*  38 */     this.body.calculateNormals();
/*  39 */     this.body.subdivide(4);
/*     */   }
/*     */ 
/*     */   public void render(GL gl) {
/*  43 */     this.texEngine = Textures.engineText;
/*     */ 
/*  45 */     gl.glMatrixMode(GL.GL_MODELVIEW);
/*     */ 
/*  47 */     this.glu = new GLU();
/*  48 */     this.quad = this.glu.gluNewQuadric();
/*  49 */     this.glu.gluQuadricOrientation(this.quad, 100020);
/*     */ 
/*  53 */     drawSpaceship(gl);
/*     */   }
/*     */ 
/*     */   private void drawSpaceship(GL gl) {
/*  57 */     gl.glPushMatrix();
/*     */ 
/*  59 */     gl.glRotated(90.0D, 1.0D, 0.0D, 0.0D);
/*  60 */     gl.glTranslated(-0.75D, 0.0D, 0.0D);
/*  61 */     gl.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
/*     */ 
/*  63 */     gl.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
/*  64 */     gl.glTranslated(0.0D, -0.3D, 0.0D);
/*     */ 
/*  66 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, new float[] { 2.0F, 1.3F, 0.7F, 1.0F }, 0);
/*  67 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, 5633, new float[] { 100.0F, 200.0F, 300.0F, 1.0F }, 0);
/*  68 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, new float[] { 0.05F, 0.05F, 0.05F, 1.0F }, 0);
/*     */ 
/*  70 */     drawBody(gl);
/*     */ 
/*  72 */     gl.glPushMatrix();
/*  73 */     gl.glTranslated(0.1D, 0.1D, -1.7D);
/*  74 */     drawGun(gl);
/*  75 */     gl.glPopMatrix();
/*  76 */     gl.glPushMatrix();
/*  77 */     gl.glTranslated(-0.1D, 0.1D, -1.7D);
/*  78 */     drawGun(gl);
/*  79 */     gl.glPopMatrix();
/*     */ 
/*  81 */     gl.glPushMatrix();
/*  82 */     gl.glTranslated(0.4D, 0.4D, -0.6D);
/*  83 */     gl.glRotated(125.0D, 0.0D, 0.0D, 1.0D);
/*  84 */     drawWing(gl);
/*  85 */     gl.glPopMatrix();
/*  86 */     gl.glPushMatrix();
/*  87 */     gl.glTranslated(-0.4D, 0.4D, -0.6D);
/*  88 */     gl.glRotated(-125.0D, 0.0D, 0.0D, 1.0D);
/*  89 */     drawWing(gl);
/*  90 */     gl.glPopMatrix();
/*     */ 
/*  92 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
/*     */ 
/*  94 */     gl.glPopMatrix();
/*     */   }
/*     */ 
/*     */   private void drawBody(GL gl) {
/*  98 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, 5632, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
/*  99 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, new float[] { 0.8F, 0.8F, 1.0F, 1.0F }, 0);
/*     */ 
/* 101 */     this.body.draw(gl);
/*     */ 
/* 103 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, new float[] { 1.0F, 1.0F, 0.4F, 1.0F }, 0);
/*     */ 
/* 106 */     gl.glPushMatrix();
/* 107 */     gl.glTranslated(0.0D, 0.3D, -0.7D);
/* 108 */     gl.glScaled(1.0D, 1.0D, 2.0D);
/* 109 */     this.glu.gluSphere(this.quad, 0.2D, 20, 10);
/* 110 */     gl.glPopMatrix();
/*     */   }
/*     */ 
/*     */   private void drawWing(GL gl) {
/* 114 */     gl.glPushMatrix();
/* 115 */     gl.glTranslated(0.2D, 0.0D, 0.0D);
/* 116 */     gl.glRotated(90.0D, 0.0D, 0.0D, 1.0D);
/*     */ 
/* 118 */     gl.glPushMatrix();
/* 119 */     gl.glTranslated(0.0D, 0.4D, 0.0D);
/* 120 */     drawEngine(gl);
/* 121 */     gl.glPopMatrix();
/* 122 */     drawEngine(gl);
/* 123 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, 5632, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
/* 124 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, new float[] { 0.3F, 0.3F, 0.3F, 1.0F }, 0);
/*     */ 
/* 126 */     gl.glPushMatrix();
/* 127 */     gl.glTranslated(0.0D, 0.4D, 0.3D);
/* 128 */     gl.glRotated(90.0D, 1.0D, 0.0D, 0.0D);
/* 129 */     this.glu.gluCylinder(this.quad, 0.05D, 0.05D, 0.4D, 10, 10);
/* 130 */     gl.glTranslated(0.0D, 0.0D, 0.2D);
/* 131 */     gl.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
/* 132 */     this.glu.gluCylinder(this.quad, 0.05D, 0.05D, 0.4D, 10, 10);
/* 133 */     gl.glPopMatrix();
/*     */ 
/* 135 */     gl.glPopMatrix();
/*     */   }
/*     */ 
/*     */   private void drawEngine(GL gl) {
/* 139 */     float front = 0.1F;
/* 140 */     float back = 0.15F;
/* 141 */     float length = 0.7F;
/* 142 */     int slices = 20;
/* 143 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, 5632, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
/* 144 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, new float[] { 0.3F, 0.3F, 0.3F, 1.0F }, 0);
/* 145 */     this.glu.gluCylinder(this.quad, 0.1000000014901161D, 0.1500000059604645D, 0.699999988079071D, 20, 10);
/*     */ 
/* 147 */     float[] specular = new float[4];
/* 148 */     gl.glGetMaterialfv(1028, GL.GL_SPECULAR, specular, 0);
/* 149 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
/* 150 */     gl.glPushMatrix();
/* 151 */     gl.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 152 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, new float[] { 0.1F, 0.3F, 0.3F, 1.0F }, 0);
/* 153 */     this.glu.gluDisk(this.quad, 0.0D, 0.1000000014901161D, 20, 4);
/* 154 */     gl.glPopMatrix();
/* 155 */     gl.glPushMatrix();
/* 156 */     gl.glTranslatef(0.0F, 0.0F, 0.7F);
/* 157 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
/* 158 */     this.glu.gluQuadricTexture(this.quad, true);
/*     */ 
/* 160 */     this.texEngine.bind();
/* 161 */     if (this.isStarted) {
/* 162 */       gl.glMaterialfv(GL.GL_FRONT_AND_BACK, 5632, new float[] { 0.5F, 0.5F, 0.5F, 1.0F }, 0);
/* 163 */       this.texEngine.enable();
/*     */     } else {
/* 165 */       gl.glMaterialfv(GL.GL_FRONT_AND_BACK, 5632, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
/*     */     }
/* 167 */     gl.glPushMatrix();
/* 168 */     Random rand = new Random();
/* 169 */     double rot = 360.0D * rand.nextDouble();
/* 170 */     gl.glRotated(rot, 0.0D, 0.0D, 1.0D);
/* 171 */     this.glu.gluDisk(this.quad, 0.0D, 0.1500000059604645D, 20, 4);
/* 172 */     gl.glPopMatrix();
/* 173 */     this.texEngine.disable();
/* 174 */     this.glu.gluQuadricTexture(this.quad, false);
/* 175 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, 5632, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
/* 176 */     gl.glPopMatrix();
/* 177 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, specular, 0);
/*     */   }
/*     */ 
/*     */   private void drawGun(GL gl) {
/* 181 */     float radius = 0.03F;
/* 182 */     float length = 0.7F;
/* 183 */     int slices = 10;
/* 184 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, 5632, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
/* 185 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, new float[] { 0.7F, 0.7F, 0.0F, 1.0F }, 0);
/* 186 */     this.glu.gluCylinder(this.quad, 0.02999999932944775D, 0.02999999932944775D, 0.699999988079071D, 10, 6);
/*     */ 
/* 188 */     float[] specular = new float[4];
/* 189 */     gl.glGetMaterialfv(1028, GL.GL_SPECULAR, specular, 0);
/* 190 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
/* 191 */     gl.glPushMatrix();
/* 192 */     gl.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 193 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
/* 194 */     this.glu.gluDisk(this.quad, 0.0D, 0.02999999932944775D, 10, 10);
/* 195 */     gl.glPopMatrix();
/* 196 */     gl.glPushMatrix();
/* 197 */     gl.glTranslatef(0.0F, 0.0F, 0.7F);
/* 198 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
/* 199 */     this.glu.gluDisk(this.quad, 0.0D, 0.02999999932944775D, 10, 10);
/* 200 */     gl.glPopMatrix();
/* 201 */     gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, specular, 0);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 206 */     return "Spaceship";
/*     */   }
/*     */ 
/*     */   public Vec center()
/*     */   {
/* 213 */     return origin;
/*     */   }
/*     */ 
/*     */   public double radius()
/*     */   {
/* 218 */     return 0.7D;
/*     */   }
/*     */ 
/*     */   public boolean isStarted() {
/* 222 */     return this.isStarted;
/*     */   }
/*     */ 
/*     */   public void setStarted(boolean isStarted) {
/* 226 */     this.isStarted = isStarted;
/*     */   }
/*     */ }

/* Location:           /Volumes/DataHD/Projects/Java/Graphics/last-graphics-exercise/jad/
 * Qualified Name:     ex6.models.Spaceship
 * JD-Core Version:    0.6.0
 */