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
	private LinkedList<Asteroid> remAsteroids;
	private Spaceship spaceship;
	//TIP Take a look at the LinkedList methods descendingIterator() and removeAll() 
	private int addMeteorCount;
	private int addMeteorEvery;
	private double meteorSpeed;
	private double meteorZLimit;
	public Asteroid collisionMeteor; 
	public Vec collisionPoint;	
	private int meteorsAddedEachTime;	
	private double meteorMinDistance;	
	private int meteorsCountLimit;	
	private double initialViewerDist;	
	private boolean isGodMode;	

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
	    this.addMeteorCount -= 1;
	    if (this.addMeteorCount == 0) {
	        this.addMeteorCount = this.addMeteorEvery;
	        addAsteroid();
	    }

	    double angleRad = this.angle / 180.0D * Math.PI;
	    for (Asteroid asteroid : this.asteroids) {
	        Vec direction = new Vec(-this.meteorSpeed * Math.sin(angleRad), 0.0D, this.meteorSpeed * Math.cos(angleRad));

	        asteroid.move(direction);
	        if (asteroid.center().z + asteroid.radius() > this.meteorZLimit) {
	            this.remAsteroids.add(asteroid);
	        }
	    }

	    this.asteroids.removeAll(this.remAsteroids);
	    this.remAsteroids.clear(); 
	    
		//TODO Add some new asteroids (using addAsteroid)

		//TODO update position of all asteroids
		
		//TODO Remove asteroids behind the spaceship
	}
	
	private void checkCollision() {
	    //TODO check if any of the asteroids has collided with the spaceship
	    for (Asteroid asteroid : this.asteroids) {
	        double dist = dist(asteroid, this.spaceship);
	        if (dist <= 0.0D) {
	            this.collisionMeteor = asteroid;
	            this.collisionPoint = Vec.scale(0.5D, Vec.add(asteroid.center(), this.spaceship.center()));
	            this.isGameOver = true;
	        }
	    }
	}
	
	private void addAsteroid() {
		//TODO add a new asteroid to the game
		//TIP you don't have to check that two asteroids don't intersect, it's ok if they do.
	    
	    if (this.asteroids.size() >= this.meteorsCountLimit)
	        return;
	    int maxAttempts = 20;

	    for (int k = 0; k < this.meteorsAddedEachTime; k++)
	    {
	        int i = 0; if (i < 20) {
	            double r = 0.5D + this.rand.nextDouble();
	            double angleRad = 3.0D * (this.rand.nextDouble() - 0.5D) * 60.0D / 180.0D * Math.PI;
	            double x = this.initialViewerDist * Math.sin(angleRad);
	            double y = this.rand.nextDouble() - 0.5D;
	            double z = -this.initialViewerDist * Math.cos(angleRad);
	            Asteroid newMeteor = new Asteroid(new Vec(x, y, z), r);

	            this.asteroids.add(newMeteor);
	        }
	    } 
	}
	
	private double dist(ISphericalObstacle a, ISphericalObstacle b) {
		//TODO calculate the distance between these two obstacles
	    return Vec.sub(a.center(), b.center()).length() - a.radius() - b.radius();
	}

	public void restart() {
		//TODO this function should set the game logic to its initial values at startup and when 'r' is pressed.
	    this.asteroids = new LinkedList();
	    this.remAsteroids = new LinkedList();
	    this.angle = 0.0D;
	    this.addMeteorEvery = 1;
	    this.addMeteorCount = this.addMeteorEvery;
	    this.meteorsAddedEachTime = 5;
	    this.meteorSpeed = 0.1D;
	    this.meteorZLimit = 50.0D;
	    this.meteorMinDistance = 20.0D;
	    this.meteorsCountLimit = 1000;
	    this.initialViewerDist = 300.0D;
	    this.collisionPoint = null;
	    this.collisionMeteor = null;
	    this.isPaused = false;
	    this.score = 0L;
	    this.isGodMode = false;
	    this.isGameOver = false;
 
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
