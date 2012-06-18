/*    */ package ex6.models;
/*    */ 
/*    */ import com.sun.opengl.util.texture.Texture;
/*    */ import ex6.Textures;
/*    */ import java.awt.Color;
/*    */ import java.util.Random;
/*    */ import javax.media.opengl.GL;
/*    */ import javax.media.opengl.glu.GLU;
/*    */ import javax.media.opengl.glu.GLUquadric;
/*    */ 
/*    */ public class Meteorite
/*    */   implements IRenderable, ISphericalObstacle
/*    */ {
/*    */   private Texture tex;
/*    */   private Vec ctr;
/*    */   private double r;
/*    */   private float[] colorAmbient;
/*    */   private float[] colorDiffuse;
/* 20 */   private boolean isChecker = false;
/*    */ 
/*    */   public Meteorite(Vec ctr, double r) {
/* 23 */     this.ctr = ctr;
/* 24 */     this.r = r;
/*    */ 
/* 26 */     Random rand = new Random();
/*    */ 
/* 28 */     float H = (float)rand.nextDouble();
/* 29 */     Color color = new Color(Color.HSBtoRGB(H, 0.5F, 1.0F));
/*    */ 
/* 35 */     float R = color.getRed() / 256.0F;
/* 36 */     float G = color.getGreen() / 256.0F;
/* 37 */     float B = color.getBlue() / 256.0F;
/* 38 */     this.colorDiffuse = new float[] { R, G, B, 0.6F };
/* 39 */     this.colorAmbient = new float[] { 0.2F * R, 0.2F * G, 0.2F * B, 1.0F };
/*    */   }
/*    */ 
/*    */   public void render(GL gl)
/*    */   {
/* 44 */     if (!this.isChecker) {
/* 45 */       gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, this.colorDiffuse, 0);
/* 46 */       gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, this.colorAmbient, 0);
/* 47 */       this.tex = Textures.asteroidTexture;
/*    */     } else {
/* 49 */       gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, new float[] { 0.5F, 0.5F, 0.5F, 0.2F }, 0);
/* 50 */       gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, new float[] { 0.5F, 0.5F, 0.5F, 0.2F }, 0);
/* 51 */       this.tex = Textures.texChecker;
/*    */     }
/* 53 */     GLU glu = new GLU();
/* 54 */     GLUquadric quad = glu.gluNewQuadric();
/* 55 */     glu.gluQuadricTexture(quad, true);
/*    */ 
/* 57 */     this.tex.bind();
/* 58 */     this.tex.enable();
/*    */ 
/* 60 */     gl.glPushMatrix();
/* 61 */     gl.glTranslated(this.ctr.x, this.ctr.y, this.ctr.z);
/* 62 */     gl.glRotated(90.0D, 1.0D, 0.0D, 0.0D);
/* 63 */     glu.gluSphere(quad, this.r, 16, 16);
/* 64 */     gl.glPopMatrix();
/*    */ 
/* 66 */     this.tex.disable();
/*    */ 
/* 68 */     glu.gluDeleteQuadric(quad);
/*    */   }
/*    */ 
/*    */   public void setCheckerTexture() {
/* 72 */     this.isChecker = true;
/*    */   }
/*    */ 
/*    */   public void move(Vec direction) {
/* 76 */     this.ctr.add(Vec.mult(5.0D + this.ctr.lengthSquared() / 100.0D, direction));
/*    */   }
/*    */ 
/*    */   public Vec center()
/*    */   {
/* 81 */     return this.ctr;
/*    */   }
/*    */ 
/*    */   public double radius()
/*    */   {
/* 86 */     return this.r;
/*    */   }
/*    */ }

/* Location:           /Volumes/DataHD/Projects/Java/Graphics/last-graphics-exercise/jad/
 * Qualified Name:     ex6.models.Meteorite
 * JD-Core Version:    0.6.0
 */