package cars;

public class Vehicle {
	private int speedAcceleration = 0;
	private int speedTurning = 0;
	private int maxSpeed = 0;
	private int carXPos = 0;
	private int carYPos = 0;
	
	public void Vehicle() {
		
	}
	
	public void Vehicle(int speed, int turning, int acceleration) {
		this.maxSpeed = speed;
		this.speedTurning = turning;
		this.speedAcceleration = acceleration;
		carXPos = 200;
		carYPos = 200;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public void setMaxSpeed(int speed) {
		this.maxSpeed = speed;
	}
	
	public int getSpeedAcceleration() {
		return speedAcceleration;
	}
	
	public void setSpeedAcceleration(int acceleration) {
		this.speedAcceleration = acceleration;
	}
	
	public int getSpeedTurning() {
		return speedAcceleration;
	}
	
	public void setSpeedTurning(int turningSpeed) {
		this.speedTurning = turningSpeed;
	}
	
	public int getCarXPos() {
		return carXPos;
	}
	
	public void setCarXPos(int x) {
		this.carXPos = x;
	}
	
	public int getCarYPos() {
		return carYPos;
	}
	
	public void setCarYPos(int y) {
		this.carYPos = y;
	}
	

	public void moveForward() {
		this.carXPos-=10;
	}
	
	public void rotateDirection() {
		//TODO: agile 2
	}
	
	public boolean collisionDetection() {
		return false;
	}
	
	/*******************************
	 * Agile 1 testing stuff below
	 * should be depreciated when 
	 * we get acceleration working
	 *******************************/
	public void gridMovement() {
		
	}
}
