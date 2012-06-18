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
	private final static double VIEW_DISTANCE = 20000.0;
	private final static double PADDING = 5.0;

	private GameLogic game; //The game's state
	private FPSAnimator ani; //An animator object to redraw the scene every fraction of a second

	private boolean isShowShip = true; //Should the ship be rendered? (user presses 's')
	private boolean isShipMark = false; //Should the ship's surrounding sphere be rendered? (user presses 'm')
	private boolean isOrthographic = false; //Should an orthographic projection be used instead of spaceship viewpoint perspective? (user presses 'j')
	private boolean isReshape = false; //Should the OpenGL projection matrix be updated?

	private Spaceship spaceship;
	private Asteroid spaceshipMark;
	
	public Viewer(GameLogic game, Spaceship spaceship) {
		//TIP you might want to get the spaceship here from outside
		this.game = game;
		this.spaceship = spaceship;
		this.spaceshipMark = new Asteroid(spaceship.center(), spaceship.radius(), false);
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		if (this.isReshape) {
			reshape(drawable, 0, 0, drawable.getWidth(), drawable.getHeight());
			this.isReshape = false;
		}

		// move the game
		game.update();

		// start drawing
		GL gl = drawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		GLU glu = new GLU();
		GLUquadric quad = glu.gluNewQuadric();
		glu.gluQuadricTexture(quad, true);

		// create a light for the stars
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, new float[] { 50.0F, 50.0F, 50.0F, 50.0F }, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, new float[] { 0.0F, 0.0F, 10.0F, 0.0F }, 0);
		gl.glEnable(GL.GL_LIGHT0);

		gl.glDepthMask(false);
		gl.glPushMatrix();

		// draw the stars using a sphere
		Textures.starsTexture.bind();
		Textures.starsTexture.enable();

		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, new float[] { 0.0F, 0.0F, 0.0F, 0.2F }, 0);

		// rotate the stars according to the ship angle
		gl.glRotated(this.game.getAngle(), 0.0, 1.0, 0.0);

		// rotate the sphere a bit so we won't see the top of it (which looks bad)
		gl.glRotated(90.0, 1.0, 0.0, 0.0);
		glu.gluSphere(quad, 100.0, 15, 15);

		Textures.starsTexture.disable();
		gl.glPopMatrix();
		gl.glDepthMask(true);

		gl.glDisable(GL.GL_LIGHT0);

		// move the spaceship and the rest of the objects (collision line and collision sphere)
		gl.glTranslated(0.0, -1.5, -5.0);

		// draw the ship
		if (this.isShowShip) {
			gl.glPushMatrix();

			gl.glRotated(-1.0 * this.game.getAngle(), 0.0, 0.0, 1.0);
			gl.glRotated(90.0, 0.0, 1.0, 0.0);

			this.spaceship.render(gl);

			gl.glPopMatrix();
		}

		gl.glDepthMask(false);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glPushMatrix();

		gl.glRotated(0.5 * this.game.getAngle(), 0.0, 0.0, 1.0);
		gl.glRotated(this.game.getAngle(), 0.0, 1.0, 0.0);

		// render the asteroids
		for (Asteroid asteroid : this.game.getAsteroids())  {
			asteroid.render(gl);
		}

		gl.glPopMatrix();

		gl.glDisable(GL.GL_CULL_FACE);
		gl.glDepthMask(true);

		if (this.isShipMark) {
			gl.glPushMatrix();
			gl.glRotated(-1.0 * this.game.getAngle(), 0.0, 0.0, 1.0);
			this.spaceshipMark.render(gl);
			gl.glPopMatrix();
		}

		gl.glDisable(GL.GL_DEPTH_TEST);
		gl.glDisable(GL.GL_LIGHTING);

		// draw the collision line
		if (this.game.collisionPoint != null) {
			gl.glPushMatrix();

			gl.glPointSize(5.0F);
			gl.glRotated(0.5F * this.game.getAngle(), 0.0, 0.0, 1.0);
			gl.glRotated(this.game.getAngle(), 0.0, 1.0, 0.0);

			// draw the collision line
			gl.glBegin(GL.GL_LINES);
			gl.glColor3d(1.0, 1.0, 0.0);
			gl.glVertex3d(this.game.asteroidCollidedWith.center().x, this.game.asteroidCollidedWith.center().y, this.game.asteroidCollidedWith.center().z);
			gl.glVertex3d(this.spaceship.center().x, this.spaceship.center().y, this.spaceship.center().z);
			gl.glEnd();

			// draw the collision points
			gl.glBegin(GL.GL_POINTS);
			gl.glColor3d(1.0, 0.0, 0.0);
			gl.glVertex3d(this.game.collisionPoint.x, this.game.collisionPoint.y, this.game.collisionPoint.z);
			gl.glVertex3d(this.spaceship.center().x, this.spaceship.center().y, this.spaceship.center().z);
			gl.glVertex3d(this.game.asteroidCollidedWith.center().x, this.game.asteroidCollidedWith.center().y, this.game.asteroidCollidedWith.center().z);
			gl.glEnd();

			gl.glPopMatrix();
		}

		GLUT glut = new GLUT();
		int lineHeight = glut.glutBitmapWidth(7, 'H') + 5;

		// green color (dashboard)
		gl.glColor3d(0.0, 1.0, 0.0);

		gl.glWindowPos2d(PADDING, PADDING + lineHeight * 3);
		glut.glutBitmapString(7, "Score: " + this.game.getScore());

		gl.glWindowPos2d(PADDING, PADDING + lineHeight * 2);
		glut.glutBitmapString(7, "Asteroids: " + this.game.getAsteroidCount());


		// tutorial
		gl.glColor3d(1.0, 1.0, 1.0);

		gl.glWindowPos2d(PADDING, PADDING + lineHeight);
		glut.glutBitmapString(7, "Use arrows to move. Controls:");

		gl.glWindowPos2d(PADDING, PADDING);
		glut.glutBitmapString(7, "(S)how Ship, Show (M)ark, (G)host Mode, (P)ause Game, Pro(j)ection Mode, (R)estart");

		// notifications
		if ((this.game.isGhostMode()) && (!this.game.isPaused())) {
			gl.glColor3d(0.0, 1.0, 0.0);
			gl.glWindowPos2d(PADDING, PADDING + lineHeight * 4);
			glut.glutBitmapString(8, "Ghost mode on");
		}
		if (this.game.isGameOver()) {
			gl.glColor3d(1.0, 0.0, 0.0);
			gl.glWindowPos2d(PADDING, PADDING + lineHeight * 4);
			glut.glutBitmapString(8, "GAME OVER!");
		}
		if ((this.game.isPaused()) && (!this.game.isGameOver())) {
			gl.glColor3d(1.0, 1.0, 0.0);
			gl.glWindowPos2d(PADDING, PADDING + lineHeight * 4);
			glut.glutBitmapString(8, "Game Paused");
		}

		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_DEPTH_TEST);

		glu.gluDeleteQuadric(quad);
	}

	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		drawable.setGL(new javax.media.opengl.DebugGL(drawable.getGL()));

		drawable.setGL(new DebugGL(drawable.getGL()));
		GL gl = drawable.getGL();

		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

		gl.glLightModeli(GL.GL_LIGHT_MODEL_TWO_SIDE, GL.GL_TRUE);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL.GL_NORMALIZE);

		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glEnable(GL.GL_LIGHTING);

		Textures.load();

		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST_MIPMAP_NEAREST);
		gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);

		this.ani = new FPSAnimator(30, true);
		this.ani.add(drawable);
		this.ani.start();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		double aspectRatio;
		double newScreenWidth;
		double newScreenHeight;

		// calculate the aspect ratio, the width and the height
		if (height < width) {
			aspectRatio = Double.valueOf(height) / Double.valueOf(width);
			newScreenWidth = 20.0;
			newScreenHeight = 20.0 * aspectRatio;
		} else {
			aspectRatio = Double.valueOf(width) / Double.valueOf(height);
			newScreenWidth = 20.0 * aspectRatio;
			newScreenHeight = 20.0;
		}

		GL gl = drawable.getGL();
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();

		if (this.isOrthographic) {
			gl.glOrtho(-newScreenWidth, newScreenWidth, -newScreenHeight, newScreenHeight, -VIEW_DISTANCE, VIEW_DISTANCE);
			gl.glRotated(90.0, 1.0, 0.0, 0.0);
			gl.glTranslated(0.0, -1.0, Math.sqrt(aspectRatio) * 20.0);
		} else {
			GLU glu = new GLU();
			glu.gluPerspective(60.0, width / height, 1.0, VIEW_DISTANCE);
		}
		gl.glMatrixMode(GL.GL_MODELVIEW);
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
