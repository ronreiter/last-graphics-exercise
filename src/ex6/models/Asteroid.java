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
	private static boolean isDisplayListGenerated = false;

	private float[] diffuseColor;
	private float[] ambientColor;

	public Asteroid(Vec center, double radius) {
		this.center = center;
		this.radius = radius;

		Random rand = new Random();


		float hue = (float)rand.nextDouble();
		float saturation = (float)rand.nextDouble();
		float brightness = 0.8F;
		Color color = new Color(Color.HSBtoRGB(hue, saturation, brightness));

		float R = color.getRed() / 256;
		float G = color.getGreen() / 256;
		float B = color.getBlue() / 256;

		this.diffuseColor = new float[] { R, G, B, 0.5F };
		this.ambientColor = new float[] { 0.5F, 0.5F, 0,5F, 0.5F };

		//TIP Have a look at Color.HSBtoRGB() to randomize only the hue.
	}
	
	@Override
	public void render(GL gl) {
		Texture texture = Textures.asteroidTexture;

		gl.glMaterialfv(1032, 4609, new float[] { 0.5F, 0.5F, 0.5F, 0.2F }, 0);
		gl.glMaterialfv(1032, 4608, new float[] { 0.5F, 0.5F, 0.5F, 0.2F }, 0);

		GLU glu = new GLU();
		GLUquadric quad = glu.gluNewQuadric();
		glu.gluQuadricTexture(quad, true);

		texture.bind();
		texture.enable();

		gl.glPushMatrix();
		gl.glTranslated(center.x, center.y, center.z);
		gl.glRotated(90.0D, 1.0D, 0.0D, 0.0D);
		glu.gluSphere(quad, radius, 16, 16);
		gl.glPopMatrix();

		texture.disable();

		glu.gluDeleteQuadric(quad);

	}
	
	public void genDisplayList(GL gl) {
		//TIP it makes sense to call this function in the beginning of render()
		if (!isDisplayListGenerated) {
			//TODO create a display list
			isDisplayListGenerated = true;
		}
	}
	
	public void move(Vec direction) {
		center.add(Vec.scale(5.0D + center.lengthSquared() / 100.0D, direction));

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
