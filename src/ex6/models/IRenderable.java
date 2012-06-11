package ex6.models;

import javax.media.opengl.GL;


/**
 * A renderable model
 * 
 */
public interface IRenderable {
	public final static int TOGGLE_LIGHT_SPHERES = 0;
	public final static int SUBDIVIDE = 1;
	

	//TIP this interface can be used for both spaceship and asteroid
	
	/**
	 * Render the model
	 * 
	 * @param gl
	 *            GL context
	 */
	public void render(GL gl);

}
