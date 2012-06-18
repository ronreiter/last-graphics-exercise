package ex6.models;

import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 * A simple axes dummy 
 *
 */
public class Spaceship implements ISphericalObstacle {

	private boolean lightSpheresVisible = false;
	private Mesh body;
	private GLU glu;
	private GLUquadric gluQuad;
	private ArrayList<float[]> lightPositions = null;

	// constants
	private static final int NUM_LIGHTS = 2;
	private static final double LIGHT_SIZE = 0.1;
	private static final int NUM_SLICES = 20;

	// colors
	private static final float[] RED_COLOR = new float[] {1f, 0f, 0f, 1f};
	private static final float[] FIRE_COLOR = new float[] {1f, 0.5f, 0f, 1f};
	private static final float[] GRAY_COLOR = new float[] {0.4f, 0.4f, 0.4f, 1f};
	private static final float[] WHITE_COLOR = new float[] {1f, 1f, 1f, 1f};
	private static final float[] BLACK_COLOR = new float[] {0f, 0f, 0f, 1f};
	private static final float[] COCKPIT_BLUE_COLOR = new float[] {0.4f, 0.5f, 0.9f, 0};
	private static final float[] LIGHT_SPHERE_COLOR = new float[] {1f, 0f, 0f, 1f};

	private static final float[] LIGHT_DIFFUSE_COLOR = new float[] {20f, 20f, 20f, 1f};
	private static final float[] LIGHT_SPECULAR_COLOR = new float[] {5f, 5f, 5f, 1f};
	private static final float LIGHT_ATTENUATION = 1.0f;

	public static final int SPACESHIP_RADIUS = 100;

	// spherical properties
	private Vec center = new Vec();
	private double radius = 0;


	public Spaceship() {
		ArrayList<int[]> faces = new ArrayList<int[]>();
		ArrayList<Vec> vertices = new ArrayList<Vec>();

		// create the basic body
		faces.add(new int[]{1, 3, 0});
		faces.add(new int[]{2, 1, 0});
		faces.add(new int[]{3, 2, 0});
		faces.add(new int[]{2, 3, 1});

		vertices.add(new Vec(0.0 ,-1.0, -10.0));
		vertices.add(new Vec(0.0, 1.1, 0.0));
		vertices.add(new Vec(-2.0, -1.0, 0.0));
		vertices.add(new Vec(2.0, -1.0, 0.0));

		body = new Mesh(faces, vertices);

		// create lights
		this.lightPositions = new ArrayList<float[]>(NUM_LIGHTS);
		lightPositions.add(new float[] {5, 2, 0, 1});
		lightPositions.add(new float[] {-5, 2, 0, 1});

	}

	public Vec center() {
		return center;
	}

	public void setCenter(Vec center) {
		this.center = center;
	}

	public double radius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void render(GL gl) {
		this.glu = new GLU();
		gluQuad = glu.gluNewQuadric();
		glu.gluQuadricOrientation(gluQuad, GLU.GLU_OUTSIDE);

		createLights(gl);
		drawSpaceship(gl);
		
		glu.gluDeleteQuadric(gluQuad);
	}

	private void createLights(GL gl) {
		gl.glLightModeli(GL.GL_LIGHT_MODEL_TWO_SIDE, GL.GL_TRUE);
		gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, WHITE_COLOR, 0);
		
		gl.glPushMatrix();
		gl.glLoadIdentity();

		for (int i = 0; i < lightPositions.size(); i++) {
			int lightIndex = GL.GL_LIGHT0 + i;

			gl.glLightfv(lightIndex, GL.GL_POSITION, lightPositions.get(i), 0);
			gl.glLightfv(lightIndex, GL.GL_DIFFUSE, LIGHT_DIFFUSE_COLOR , 0);
			gl.glLightfv(lightIndex, GL.GL_SPECULAR, LIGHT_SPECULAR_COLOR , 0);
			gl.glLightf(lightIndex, GL.GL_QUADRATIC_ATTENUATION, LIGHT_ATTENUATION);
			gl.glEnable(lightIndex);

		}

		gl.glPopMatrix();
		gl.glEnable(GL.GL_LIGHTING);

	}

	private void drawSpaceship(GL gl) {
		// draw the main hull of the ship
		drawHull(gl);

		// draw the cockpit
		drawCockpit(gl);

		// draw the engines
		drawLeftEngine(gl);
		drawRightEngine(gl);

		// draw the cannons
		drawCannons(gl);

		// draw light spheres
		if (lightSpheresVisible) {
			drawLightSpheres(gl);
		}


	}

	private void drawLightSpheres(GL gl) {
		for (float[] lightPosition : lightPositions) {
			drawLightSphere(gl, lightPosition);
		}
	}

	private void drawLightSphere(GL gl, float[] lightPos) {
		gl.glPushMatrix();

		// move to the light position
		gl.glTranslatef(lightPos[0], lightPos[1], lightPos[2]);

		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, LIGHT_SPHERE_COLOR, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, WHITE_COLOR, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, WHITE_COLOR, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, BLACK_COLOR, 0);

		glu.gluSphere(gluQuad, LIGHT_SIZE, NUM_SLICES, NUM_SLICES);

		gl.glPopMatrix();

	}


	
	private void drawCannons(GL gl) {
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, RED_COLOR , 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, WHITE_COLOR, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, WHITE_COLOR, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, BLACK_COLOR, 0);

		gl.glPushMatrix();
		gl.glRotated(90, 0, 1, 0);
		gl.glTranslated(-0.25, -0.25, 0.5);
		glu.gluCylinder(gluQuad, 0.1, 0.05, 2, NUM_SLICES, NUM_SLICES);
		gl.glTranslated(0, 0, 2);
		glu.gluDisk(gluQuad, 0, 0.05, NUM_SLICES, 1);

		gl.glTranslated(0.5, 0, -2);
		glu.gluCylinder(gluQuad, 0.1, 0.05, 2, NUM_SLICES, NUM_SLICES);
		gl.glTranslated(0, 0, 2);
		glu.gluDisk(gluQuad, 0, 0.05, NUM_SLICES, 1);
		gl.glPopMatrix();

	}

	private void drawRightEngine(GL gl) {
		gl.glPushMatrix();

		// rotate right
		gl.glTranslated(-0.7, 0, -0.7);
		gl.glRotated(-45, 1, 0, 0);

		// draw the engine
		drawEngineSection(gl);
		gl.glPopMatrix();		
	}

	private void drawLeftEngine(GL gl) {
		gl.glPushMatrix();

		// rotate left
		gl.glTranslated(-0.7, 0.35, 0.35);
		gl.glRotated(45, 1, 0, 0);

		// draw the engine
		drawEngineSection(gl);

		gl.glPopMatrix();

	}

	private void drawHull(GL gl) {
		gl.glPushMatrix();

		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, WHITE_COLOR , 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, WHITE_COLOR, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, WHITE_COLOR, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, BLACK_COLOR, 0);


		gl.glTranslated(-1.8, 0.2, 0);
		gl.glRotated(-90, 0, 1, 0);
		gl.glRotated(0, 0, 0, 1);
		body.draw(gl);

		gl.glPopMatrix();
	}

	private void drawCockpit(GL gl) {
		gl.glPushMatrix();

		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, COCKPIT_BLUE_COLOR , 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, WHITE_COLOR, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, WHITE_COLOR, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, BLACK_COLOR, 0);

		gl.glRotated(-5, 0, 0, 1);
		gl.glTranslated(1, 0.1, 0);
		gl.glScaled(2.5, 1, 1);
		glu.gluSphere(gluQuad, 0.3, NUM_SLICES, NUM_SLICES);

		gl.glPopMatrix();
	}

	private void drawEngineSection(GL gl) {
		gl.glPushMatrix();

		// create the body of the engines
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, GRAY_COLOR, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, BLACK_COLOR, 0);

		// build the small engine holders
		glu.gluCylinder(gluQuad, 0.05, 0.05, 0.5, NUM_SLICES, NUM_SLICES);
		gl.glRotated(90, 1, 0, 0);
		gl.glTranslated(0, 0.25, 0);
		glu.gluCylinder(gluQuad, 0.05, 0.05, 0.5, NUM_SLICES, NUM_SLICES);

		// build the engine
		gl.glRotated(90, 0, 1, 0);
		gl.glTranslated(0, 0.3, -0.5);
		glu.gluCylinder(gluQuad, 0.2, 0.1, 1.0, NUM_SLICES, NUM_SLICES);

		// create the engine fire effect
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, FIRE_COLOR, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, FIRE_COLOR, 0);

		// backfire
		gl.glRotated(180, 0, 1, 0);
		glu.gluDisk(gluQuad, 0, 0.2, NUM_SLICES, 1);

		// front fire
		gl.glRotated(180, 0, 1, 0);
		gl.glTranslated(0, 0, 1);
		glu.gluDisk(gluQuad, 0, 0.1, NUM_SLICES, 1);

		// build the second engine
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, GRAY_COLOR , 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, BLACK_COLOR, 0);

		gl.glTranslated(0, -0.6, -1);
		glu.gluCylinder(gluQuad, 0.2, 0.1, 1.0, NUM_SLICES, NUM_SLICES);

		// create the engine fire effect
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, FIRE_COLOR, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, FIRE_COLOR, 0);

		// back fire
		gl.glRotated(180, 0, 1, 0);
		glu.gluDisk(gluQuad, 0, 0.2, NUM_SLICES, 1);

		// front fire
		gl.glRotated(180, 0, 1, 0);
		gl.glTranslated(0, 0, 1);
		glu.gluDisk(gluQuad, 0, 0.1, NUM_SLICES, 1);

		gl.glPopMatrix();

		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, BLACK_COLOR, 0);

	}

	//If your scene requires more control (like keyboard events), you can define it here.
	public void control(int type, Object params) {
		switch (type) {
			case IRenderable.TOGGLE_LIGHT_SPHERES:
				this.lightSpheresVisible = !this.lightSpheresVisible;
				break;
			case IRenderable.SUBDIVIDE:
				this.body.subdivide((Integer)params);
				break;
			default:
				System.out.println("Control type not supported: " + toString() + ", " + type);
		}
	}

}
