package ex6.models;

/**
 * This interface describes an object that can be intersected with other objects, and can be approximated by a bounding sphere.
 */
public interface ISphericalObstacle {

	//TIP this interface can be used for both spaceship and asteroid
	//TIP if you want more accurate results, you can change this class to a class holding an array of
	//	  different spheres, and then bound each object not by a single sphere but by a few spheres.
	
	/**
	 * Returns the center point of the obstacle
	 */
	public Vec center();

	
	/**
	 * Returns the radius of the obstacle
	 */
	public double radius();
		
}
