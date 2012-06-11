package ex6;

import java.util.LinkedList;
import java.util.Random;

import ex6.models.ISphericalObstacle;
import ex6.models.Asteroid;

/**
 * Simple game where spaceship can be turned right or left (Model in the MVC paradigm)
 *
 */
public class GameLogic {
	
	private double angle; //Angle in which the spaceship is headed
	private long score; //Game score
	private boolean isGameOver; //Has the spaceship collided with an asteroid?
	private boolean isPaused; //Has the user paused the game?
	private boolean isGhostMode; //Has the user activated indestructible mode?
	
	private boolean isTurnLeft; //Is left arrow key pressed?
	private boolean isTurnRight; //Is right arrow key pressed?
		
	private Random rand = new Random(); //Random number generator
	//TIP you can use rand.nextDouble() to get a random number between 0.0-1.0
	
	private LinkedList<Asteroid> asteroids; //A list containing all astroids in the game, in order of creation.
	//TIP Take a look at the LinkedList methods descendingIterator() and removeAll() 
	
	
	public GameLogic() {
		//TIP you might want to get a reference to your spaceship here (because it's an ISphericalObstacle)
		restart();
	}

	/**
	 * Update game model for one turn; advance spaceship (by moving the asteroids).
	 */
	public synchronized void update() {
		if (!isGameOver && !isPaused) {
			updateAngle();
			updateAsteroids();
			if (!isGhostMode) {
				checkCollision();
				score++;
			}
		}
	}
	
	private void updateAngle() {
		//TODO use isTrunRight and isTurnLeft to change the spaceship's angle
	}
	
	
	private void updateAsteroids() {
		//TODO Add some new asteroids (using addAsteroid)

		//TODO update position of all asteroids
		
		//TODO Remove asteroids behind the spaceship
	}
	
	private void checkCollision() {
		//TODO check if any of the asteroids has collided with the spaceship
	}

	private void addAsteroid() {
		//TODO add a new asteroid to the game
		//TIP you don't have to check that two asteroids don't intersect, it's ok if they do.
	}
	
	private double dist(ISphericalObstacle a, ISphericalObstacle b) {
		//TODO calculate the distance between these two obstacles
		return 0.0;
	}

	public void restart() {
		//TODO this function should set the game logic to its initial values at startup and when 'r' is pressed.
	}
	
	public void setTurnLeft(boolean isTurnLeft) {
		this.isTurnLeft = isTurnLeft;
	}

	public void setTurnRight(boolean isTurnRight) {
		this.isTurnRight = isTurnRight;
	}

	public double getAngle() {
		return angle;
	}

	public LinkedList<Asteroid> getAsteroids() {
		return asteroids;
	}
	
	public void togglePause() {
		isPaused = !isPaused;
	}
	
	public boolean isPaused() {
		return isPaused;
	}

	public long getScore() {
		return score;
	}
	
	public int getAsteroidCount() {
		return asteroids.size();
	}
	
	public boolean isGameOver() {
		return isGameOver;
	}
	
	public boolean isGhostMode() {
		return isGhostMode;
	}

	public void toggleGhostMode() {
		if (!isGameOver)
			isGhostMode = !isGhostMode;
	}
	
}
