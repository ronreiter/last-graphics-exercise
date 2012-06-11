package ex6.models;

import javax.media.opengl.GL;

/**
 * This class implements an asteroid, both in terms of view and model of the MVC.
 */
public class Asteroid implements IRenderable, ISphericalObstacle {

	private Vec center;
	private double radius;
	private static boolean isDisplayListGenerated = false;

	public Asteroid(Vec center, double radius) {
		this.center = center;
		this.radius = radius;
		//TODO set random color
		//TIP Have a look at Color.HSBtoRGB() to randomize only the hue.
	}
	
	@Override
	public void render(GL gl) {
		//TODO render the asteroid
	}
	
	public void genDisplayList(GL gl) {
		//TIP it makes sense to call this function in the beginning of render()
		if (!isDisplayListGenerated) {
			//TODO create a display list
			isDisplayListGenerated = true;
		}
	}
	
	public void move(Vec direction) {
		//TODO move the asteroid in the given direction
		//TIP to make things more interesting, you can use a multiplier for the given vector using the formula:
		//			multiplier = a+d^2/b
		//	  where d is the asteroid's distance from the origin, and a and b are some parameters.
		//	  then you move by multiplier*direction.
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
