package ex6;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import com.sun.opengl.util.FPSAnimator;



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
	
	
	
	public Viewer(GameLogic game) {
		//TIP you might want to get the spaceship here from outside
		this.game = game;
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
	}

	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		drawable.setGL(new javax.media.opengl.DebugGL(drawable.getGL()));
		//TODO initialization: lights, blending, textures, animation, general parameters etc.
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		setProjection(drawable, width, height);
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
