package ex6;

import java.util.LinkedList;
import java.util.Random;

import ex6.models.ISphericalObstacle;
import ex6.models.Asteroid;
import ex6.models.Spaceship;
import ex6.models.Vec;

/**
 * Simple game where spaceship can be turned right or left (Model in the MVC paradigm)
 *
 */
public class GameLogic {
	
	private static final double LEFT_ANGLE = -4;
	private static final double RIGHT_ANGLE = 4;
	private static final double MIN_ANGLE = -60;
	private static final double MAX_ANGLE = 60;
    private static final int ASTEROIDS_ADDED_EACH_ROUND = 5;
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
	private LinkedList<Asteroid> asteroidsToRemove;
	
	private Spaceship spaceship;
	
	private int asteroidCounter;
	private int asteroidInterval;
	private double asteroidSpeed;
	private double limitAsteroidZ;
	public Asteroid asteroidCollidedWith; 
	public Vec collisionPoint;	
	private int asteroidLimit;	
	private double viewerDistanceInitialValue;	

	public GameLogic(Spaceship spaceship) {
		this.spaceship = spaceship;
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
		// Turn left
		if ((this.isTurnLeft) && (!this.isTurnRight)) 
			this.refineAngle(LEFT_ANGLE);
		
		// Turn Right
		if ((!this.isTurnLeft) && (this.isTurnRight))
			this.refineAngle(RIGHT_ANGLE);
		
		// Get back to origin
		if ((!this.isTurnLeft) && (!this.isTurnRight)) 
			this.alignForward();
	}
	
	// Align the spaceship to go forward
	private void alignForward(){	
		if(this.angle > 1) {
			this.angle -= 1;
		} else if(this.angle < -1) {
			this.angle += 1;
		} else {
			this.angle = 0;
		}

	}
	

	private void refineAngle(double newAngle){
		this.angle += newAngle;
		
		if(this.angle > MAX_ANGLE)
			this.angle = MAX_ANGLE;
		else if(this.angle < MIN_ANGLE)
			this.angle = MIN_ANGLE;
	}
	
	private void updateAsteroids() {
	    this.asteroidCounter--;
	    
	    if (this.asteroidCounter == 0) {
	        // Time to add a new asteroid
	        this.asteroidCounter = this.asteroidInterval;
	        addAsteroid();
	    }

	    // Calculate the new position of each asteroid
	    double angleRad = this.angle / 180.0 * Math.PI;
	    for (Asteroid asteroid : this.asteroids) {
	        Vec direction = new Vec(
	                -this.asteroidSpeed * Math.sin(angleRad),
	                0.0,
	                this.asteroidSpeed * Math.cos(angleRad)
	                );

	        asteroid.move(direction);
	        if (asteroid.center().z + asteroid.radius() > this.limitAsteroidZ) {
	            // Asteroid needs to be removed (behind spaceship)
	            this.asteroidsToRemove.add(asteroid);
	        }
	    }

	    this.asteroids.removeAll(this.asteroidsToRemove);
	    this.asteroidsToRemove.clear(); 
	}
	
	private void checkCollision() {
	    // Check if any of the asteroids has collided with the spaceship
	    for (Asteroid asteroid : this.asteroids) {
	        double distBetweenAsteroidAndSpaceship = dist(asteroid, this.spaceship);
	        if (distBetweenAsteroidAndSpaceship <= 0.0) {
	            // Collided with asteroid
	            this.asteroidCollidedWith = asteroid;

				// calculate the collision point by using an average of the centers
	            this.collisionPoint = Vec.scale(0.5, Vec.add(asteroid.center(), this.spaceship.center()));

	            this.isGameOver = true;
	        }
	    }
	}
	
	private void addAsteroid() {
		// Add a new asteroid to the game
	    
	    // Too many asteroids
	    if (this.asteroids.size() >= this.asteroidLimit)
	        return;
	    
	    for (int k = 0; k < ASTEROIDS_ADDED_EACH_ROUND; k++)
	    {
	        // Choose a random radius
            double radius = 0.3 + this.rand.nextDouble();
            double angleRadius = 3.0 * (this.rand.nextDouble() - 0.5) * 60.0 / 180.0 * Math.PI;
            
            // Choose a random location (X/Y/Z)
            double x = this.viewerDistanceInitialValue * Math.sin(angleRadius);
            double y = this.rand.nextDouble() - 0.3;
            double z = -this.viewerDistanceInitialValue * Math.cos(angleRadius);
            
            // Add the asteroid itself
            Asteroid asteroidToAdd = new Asteroid(new Vec(x, y, z), radius, true);
            this.asteroids.add(asteroidToAdd);
	    } 
	}
	
	private double dist(ISphericalObstacle a, ISphericalObstacle b) {
		// Calculate the distance between these two obstacles
	    return Vec.sub(a.center(), b.center()).length() - a.radius() - b.radius();
	}

	public void restart() {
	    // Initialize game logic
	    
	    this.asteroids = new LinkedList<Asteroid>();
	    this.asteroidsToRemove = new LinkedList<Asteroid>();
	    
	    this.angle = 0.0;
	    
	    // Controls how new asteroids appear
	    this.asteroidInterval = 1;
	    this.asteroidCounter = this.asteroidInterval;
	    this.asteroidLimit = 100;
	    
	    this.asteroidSpeed = 0.2;
	    this.limitAsteroidZ = 50;
	    
	    this.viewerDistanceInitialValue = 350;
	    
	    this.collisionPoint = null;
	    this.asteroidCollidedWith = null;
	    
	    this.score = 0;
	    
	    this.isGhostMode = false;
	    this.isGameOver = false;
	    this.isPaused = false;
 
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
