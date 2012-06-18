package ex6.models;

import com.sun.opengl.util.texture.Texture;
import ex6.Textures;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import java.awt.*;
import java.util.Random;

/**
 * This class implements an asteroid, both in terms of view and model of the MVC.
 */
public class Asteroid implements IRenderable, ISphericalObstacle {

	private Vec center;
	private double radius;
	private boolean showTexture;

	public Asteroid(Vec center, double radius, boolean showTexture) {
		this.center = center;
		this.radius = radius;
		this.showTexture = showTexture;
	}
	
	@Override
	public void render(GL gl) {
		Texture texture = Textures.asteroidTexture;

		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, new float[] { 1F, 1F, 1F, 1F }, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, new float[] { 0.2F, 0.2F, 0.2F, 0.2F }, 0);

		GLU glu = new GLU();
		GLUquadric quad = glu.gluNewQuadric();
		glu.gluQuadricTexture(quad, true);

		if (showTexture) {
			texture.bind();
			texture.enable();
		}

		gl.glPushMatrix();
		gl.glTranslated(center.x, center.y, center.z);
		gl.glRotated(90.0, 1.0, 0.0, 0.0);
		glu.gluSphere(quad, radius, 10, 10);
		gl.glPopMatrix();

		if (showTexture) {
			texture.disable();
		}

		glu.gluDeleteQuadric(quad);

	}
	
	public void move(Vec direction) {
		center.add(Vec.scale(2.0 + center.lengthSquared() / 100.0, direction));

	}
	
	@Override
	public Vec center() {
		return center;
	}

	@Override
	public double radius() {
		return radius;
	}

}
