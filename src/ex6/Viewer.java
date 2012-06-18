package ex6;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;
import ex6.models.Asteroid;
import ex6.models.Spaceship;

import java.util.Iterator;
import java.util.LinkedList;


/**
 * An OpenGL model viewer (View in the MVC paradigm)
 */
public class Viewer implements GLEventListener {
	
	private GameLogic game; //The game's state
	private FPSAnimator ani; //An animator object to redraw the scene every fraction of a second

	private boolean isShowShip = true; //Should the ship be rendered? (user presses 's')
	private boolean isShipMark = false; //Should the ship's surrounding sphere be rendered? (user presses 'm')
	private boolean isOrthographic = false; //Should an orthographic projection be used instead of spaceship viewpoint perspective? (user presses 'j')
	private boolean isReshape = false; //Should the OpenGL projection matrix be updated?

	private Spaceship spaceship;
	private Asteroid testAsteroid;
	
	public Viewer(GameLogic game, Spaceship spaceship) {
		//TIP you might want to get the spaceship here from outside
		this.game = game;
		this.spaceship = spaceship;
		this.testAsteroid = new Asteroid(spaceship.center(), spaceship.radius());
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		if (isReshape) {
			setProjection(drawable, drawable.getWidth(), drawable.getHeight());
			isReshape = false;
		}

		game.update();

		//TODO render everything: sky, spaceship (maybe with marker), asteroids, collision line, info text.
		//TIP don't forget to setup the camera, so everything you need would be drawn inside the viewing volume.
		//TIP Always keep track (on a piece of paper) where are your model objects are positioned, and where is their corresponding world view position.
		//TIP If 3D spheres look weird with blending, try activating back face culling. Then the back face won't be blended.
		//TIP Keep the sky far enough, so it doesn't obscure objects. You can even disable writing to depth buffer while rendering the sky.
		//TIP The sky's, ship's and astroid belt's rotation coefficients don't have to be exactly the same.

		if (this.isReshape) {
			reshape(drawable, 0, 0, drawable.getWidth(), drawable.getHeight());
			this.isReshape = false;
		}

		GL gl = drawable.getGL();
		this.game.update();

		gl.glClear(16640);
		gl.glMatrixMode(5888);
		gl.glLoadIdentity();

		GLU glu = new GLU();
		GLUquadric quad = glu.gluNewQuadric();
		glu.gluQuadricTexture(quad, true);

		gl.glLightfv(16384, 4609, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, 0);
		gl.glLightfv(16384, 4608, new float[] { 0.0F, 0.0F, 0.0F, 0.0F }, 0);
		gl.glLightfv(16384, 4610, new float[] { 0.0F, 0.0F, 0.0F, 0.0F }, 0);
		gl.glLightfv(16384, 4611, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);

		gl.glLightfv(16385, 4609, new float[] { 0.7F, 0.7F, 0.7F, 1.0F }, 0);
		gl.glLightfv(16385, 4608, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, 0);
		gl.glLightfv(16385, 4610, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, 0);
		gl.glLightfv(16385, 4611, new float[] { 3.0F, 10.0F, -10.0F, 1.0F }, 0);

		gl.glLightfv(16386, 4609, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, 0);
		gl.glLightfv(16386, 4608, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, 0);
		gl.glLightfv(16386, 4610, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
		gl.glLightfv(16386, 4611, new float[] { 5.0F, 10.0F, -10.0F, 1.0F }, 0);

		gl.glEnable(16384);
		gl.glDepthMask(false);
		gl.glPushMatrix();
		Textures.starsTexture.bind();
		Textures.starsTexture.enable();
		gl.glMaterialfv(1032, 4609, new float[]{1.0F, 1.0F, 1.0F, 1.0F}, 0);
		gl.glMaterialfv(1032, 4608, new float[]{0.0F, 0.0F, 0.0F, 0.2F}, 0);
		gl.glRotated(1.333333333333333D * this.game.getAngle(), 0.0D, 0.0D, 1.0D);
		gl.glRotated(this.game.getAngle(), 0.0D, 1.0D, 0.0D);
		gl.glRotated(90.0D, 1.0D, 0.0D, 0.0D);
		glu.gluSphere(quad, 100.0D, 32, 32);
		Textures.starsTexture.disable();
		gl.glPopMatrix();
		gl.glDepthMask(true);
		gl.glDisable(16384);

		gl.glTranslated(0.0D, -1.3D, -8.0D);

		if (this.isShowShip) {
			gl.glPushMatrix();
			gl.glEnable(16385);
			gl.glRotated(-1.0D * this.game.getAngle(), 0.0D, 0.0D, 1.0D);
			gl.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
			gl.glRotated(-90.0D, 1.0D, 0.0D, 0.0D);
			this.spaceship.render(gl);
			gl.glDisable(16385);
			gl.glPopMatrix();
		}

		gl.glEnable(16386);
		gl.glDepthMask(false);
		gl.glEnable(2884);
		gl.glPushMatrix();
		gl.glRotated(0.5D * this.game.getAngle(), 0.0D, 0.0D, 1.0D);
		gl.glRotated(this.game.getAngle(), 0.0D, 1.0D, 0.0D);
		LinkedList meteors = this.game.getAsteroids();
		for (Iterator iter = meteors.descendingIterator(); iter.hasNext(); ) {
			Asteroid asteroid = (Asteroid)iter.next();
			asteroid.render(gl);
		}
		gl.glMaterialfv(1032, 4608, new float[] { 0.0F, 0.0F, 0.0F, 0.2F }, 0);
		gl.glPopMatrix();
		gl.glDisable(2884);
		gl.glDepthMask(true);

		if (this.isShipMark) {
			gl.glPushMatrix();
			gl.glRotated(-1.0D * this.game.getAngle(), 0.0D, 0.0D, 1.0D);
			gl.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
			gl.glRotated(-90.0D, 1.0D, 0.0D, 0.0D);
			this.testAsteroid.render(gl);
			gl.glPopMatrix();
		}

		if (this.game.collisionPoint != null) {
			gl.glPushMatrix();
			gl.glPointSize(3.0F);
			gl.glRotated(0.5D * this.game.getAngle(), 0.0D, 0.0D, 1.0D);
			gl.glRotated(this.game.getAngle(), 0.0D, 1.0D, 0.0D);
			gl.glDisable(2929);
			gl.glDisable(2896);
			gl.glBegin(1);
			gl.glColor3d(1.0D, 1.0D, 0.0D);
			gl.glVertex3d(this.game.collisionMeteor.center().x, this.game.collisionMeteor.center().y, this.game.collisionMeteor.center().z);
			gl.glVertex3d(this.spaceship.center().x, this.spaceship.center().y, this.spaceship.center().z);
			gl.glEnd();
			gl.glBegin(0);
			gl.glColor3d(1.0D, 0.0D, 0.0D);
			gl.glVertex3d(this.game.collisionPoint.x, this.game.collisionPoint.y, this.game.collisionPoint.z);
			gl.glEnd();
			gl.glEnable(2929);
			gl.glEnable(2896);
			gl.glPopMatrix();
		}

		gl.glDisable(16386);

		gl.glDisable(2929);
		gl.glDisable(2896);
		GLUT glut = new GLUT();
		int lh = glut.glutBitmapWidth(7, 'H') + 5;
		gl.glColor3d(0.0D, 0.5D, 0.0D);
		gl.glWindowPos2d(5.0D, 5 + 2 * lh);
		glut.glutBitmapString(7, "Score: " + this.game.getScore());
		gl.glWindowPos2d(5.0D, 5 + lh);
		glut.glutBitmapString(7, "Asteroids: " + this.game.getAsteroidCount());
		gl.glColor3d(0.3D, 0.3D, 0.1D);
		gl.glWindowPos2d(5.0D, 5.0D);
		glut.glutBitmapString(7, "Controls: s,m,g,p,j,r,->,<-");

		if ((this.game.isGhostMode()) && (!this.game.isPaused())) {
			gl.glColor3d(0.0D, 1.0D, 0.0D);
			gl.glWindowPos2d(5.0D, 5 + 3 * lh);
			glut.glutBitmapString(8, "GHOST MODE");
		}
		if (this.game.isGameOver()) {
			gl.glColor3d(1.0D, 0.0D, 0.0D);
			gl.glWindowPos2d(5.0D, 5 + 3 * lh);
			glut.glutBitmapString(8, "GAME OVER");
		}
		if ((this.game.isPaused()) && (!this.game.isGameOver())) {
			gl.glColor3d(1.0D, 1.0D, 0.0D);
			gl.glWindowPos2d(5.0D, 5 + 3 * lh);
			glut.glutBitmapString(8, "PAUSED");
		}
		gl.glEnable(2896);
		gl.glEnable(2929);

		glu.gluDeleteQuadric(quad);
	}

	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		drawable.setGL(new javax.media.opengl.DebugGL(drawable.getGL()));
		//TODO initialization: lights, blending, textures, animation, general parameters etc.

		drawable.setGL(new DebugGL(drawable.getGL()));
		GL gl = drawable.getGL();

		gl.glEnable(3042);
		gl.glBlendFunc(770, 771);

		gl.glLightModeli(2898, 1);
		gl.glEnable(2977);
		gl.glEnable(2929);
		gl.glDepthFunc(515);

		gl.glEnable(2896);

		Textures.load();

		gl.glTexParameteri(3553, 10240, 9728);
		gl.glTexParameteri(3553, 10241, 9984);
		gl.glTexEnvf(8960, 8704, 8448.0F);

		this.ani = new FPSAnimator(30, true);
		this.ani.add(drawable);
		startAnimation();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		setProjection(drawable, width, height);

		GL gl = drawable.getGL();
		double aspectRatio;
		double newScreenWidth;
		double newScreenHeight;

		if (height < width) {
			aspectRatio = height / width;
			newScreenWidth = 20.0D;
			newScreenHeight = 20.0D * aspectRatio;
		} else {
			aspectRatio = width / height;
			newScreenWidth = 20.0D * aspectRatio;
			newScreenHeight = 20.0D;
		}

		gl.glMatrixMode(5889);
		gl.glLoadIdentity();

		if (this.isOrthographic) {
			gl.glOrtho(-newScreenWidth, newScreenWidth, -newScreenHeight, newScreenHeight, -20000.0D, 20000.0D);
			gl.glRotated(90.0D, 1.0D, 0.0D, 0.0D);
			gl.glTranslated(0.0D, -1.0D, Math.sqrt(aspectRatio) * 20.0D);
		} else {
			GLU glu = new GLU();
			glu.gluPerspective(60.0D, width / height, 1.0D, 20000.0D);
		}
		gl.glMatrixMode(5888);
	}
	
	private void setProjection(GLAutoDrawable drawable, int width, int height) {
		//TODO set the appropriate projection
	}

	public void startAnimation() {
		ani.start();
	}

	public void stopAnimation() {
		ani.stop();
	}
	
	public void toggleShip() {
		isShowShip = ! isShowShip;
	}
	
	public void toggleShipMark() {
		isShipMark = ! isShipMark;
	}
	
	public void toggleProjection() {
		isOrthographic = !isOrthographic;
		isReshape = true;
	}

}
