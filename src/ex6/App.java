package ex6;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCanvas;


/**
 * Entry point of the game, and also user IO handler (Controller in the MVC paradigm)
 */

public class App {
	
	private static Frame frame;
	private static GLCanvas canvas;
	private static GameLogic game;
	private static Viewer viewer;
	
	/**
	 * Create frame, canvas and viewer, and load the first model.
	 * 
	 * @param args
	 *            No arguments
	 */
	public static void main(String[] args) {

		frame = new Frame("ex6: AsteroidBelt");
		
		// Create game logic and viewer
		//TIP you might want to create a spaceship here and pass it to the game and viewer
		game = new GameLogic();
		viewer = new Viewer(game);
		
		canvas = new GLCanvas();
		
		frame.setSize(500, 500);
		frame.setLayout(new BorderLayout());		
		frame.add(canvas, BorderLayout.CENTER);
		
		// Add event listeners
		canvas.addGLEventListener(viewer);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
				super.windowClosing(e);
			}
		});
		canvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

				if (e.getKeyChar() == 'r')
					game.restart();
				if (e.getKeyChar() == 'p')
					game.togglePause();
				if (e.getKeyChar() == 's')
					viewer.toggleShip();
				if (e.getKeyChar() == 'j')
					viewer.toggleProjection();
				if (e.getKeyChar() == 'm')
					viewer.toggleShipMark();
				if (e.getKeyChar() == 'g')
					game.toggleGhostMode();

				super.keyTyped(e);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:					
					game.setTurnLeft(true);
					break;
				case KeyEvent.VK_RIGHT:
					game.setTurnRight(true);
					break;
				}
				super.keyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:					
					game.setTurnLeft(false);
					break;
				case KeyEvent.VK_RIGHT:
					game.setTurnRight(false);
					break;
				}
				super.keyReleased(e);
			}
			
		});

		// Show frame
		frame.setVisible(true);
		canvas.requestFocus();
		canvas.requestFocusInWindow();
	}

}
