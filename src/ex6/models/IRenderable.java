package ex6.models;

import javax.media.opengl.GL;


/**
 * A renderable model
 * 
 */
public interface IRenderable {

	//TIP this interface can be used for both spaceship and asteroid
	
	/**
	 * Render the model
	 * 
	 * @param gl
	 *            GL context
	 */
	public void render(GL gl);

}
