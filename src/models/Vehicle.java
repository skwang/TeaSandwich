package models;

public class Vehicle {
	// Current Physical Characteristics (m or m/s or m/s^2)
	private double speed;  		 
	private double acceleration; 
	private double x; 			 // x-position on the vehicle along the road
	private double max_speed; 	 // how fast the driver is willing to go
	private boolean crashed;     // Whether the car has crashed
	
	// Constant physical characteristics (m or m/s or m/s^2)
	private final double LENGTH = 4.8514;     // 191"
	private final double WIDTH = 1.8288;      // 72"
	private final double TOP_ACCEL = 3.57632; // 0-60mph in 7.5s
//	private final double TOP_ACCEL = 4.5;
	private final double TOP_BRAKE = 4.33;
	private final double TOP_SPEED = 54.5389; // 122mph
	
	// How much the accel and deaccelerate changes every second 
	private final double ACCEL_RATE = TOP_ACCEL/3;
	private final double BRAKE_RATE = TOP_BRAKE/4;
	
	// How fast the driver is willing to go above the speed limit
	private final double SPEED_LIMIT_FACTOR = 4.4704; // 10 mph
	
	// How long (in s) it takes the driver to respond to an event
	private final double RXN_TIME = 0.8; 
	
	// How much distance (in m) the driver places between himself and 
	// the object in front when moving
	// 1 car length for every 10 mph
	private final double MOVING_MARGIN_PER_10 = 0.8 * this.LENGTH;
	
	// start stopping when you see cars in front stopping, or difference in velocities
	// accelerate at slower rather than car in front from stopping
	
	// Same as above, but when braked
	private final double STOPPED_MARGIN = 4;
	
	// whether the car should not speed up
	private boolean noAccel = false; // TODO: remove this prototype
	
	public Vehicle(double x, double speed, double acceleration) {
		this.x = x;
		this.speed = speed;
		this.acceleration = acceleration;
		this.crashed = false;
	}
	
	// Update the Vehicle over an interval dt (in secs), in response to
	// the signal s and distTo (distance in meters to car in front)
	public double updateFromSignal(double dt, Signal s, double distTo) {
		if (s == null && !this.crashed) {
			if (distTo < this.getSafeDist())  // react to car in front
				this.brake(dt);
			else if (speed < max_speed && distTo > this.getSafeDist() + 5 && !noAccel) // otherwise try up to speed limit
				this.accel(dt);
			else if (speed > max_speed)       // but don't go past speed limit
				this.brake(dt);
			else if (noAccel) { // TODO: remove this prototype 
				this.brake(dt);
			}
			else 							  // cruise at max speed limit
				this.acceleration = 0;
		}
		else if (this.crashed) {
			this.brake(dt);
		}
		double new_speed = speed + dt * acceleration;
		//double avg_speed = (speed + new_speed)/2; // TODO: Try without the avg_speed
		double avg_speed = new_speed;
		if (avg_speed <= 0) {
			avg_speed = 0;
			this.speed = 0;
			this.acceleration = 0;
		}
		else if (avg_speed > TOP_SPEED)
			avg_speed = TOP_SPEED;
		else if (avg_speed > max_speed)
			avg_speed = max_speed;
		x = x + dt * avg_speed;
		speed = avg_speed;
		return x;
	}
	
	// return how close the car is willing to move to the car in front
	private double getSafeDist() {
		double TENMPH = 4.4704;
		return Math.max(MOVING_MARGIN_PER_10 * this.speed / TENMPH, this.STOPPED_MARGIN);
	}
	
	// Brake the car over an interval dt. Returns True if vehicle has 
	// not yet reached TOP_BRAKE, False otherwise
	public boolean brake(double dt) {
		// dt in secs!!!
		double brake_amount = BRAKE_RATE; // m/s^2
		double new_accel = acceleration - brake_amount;
		if (new_accel > TOP_BRAKE) {
			this.acceleration = TOP_BRAKE;
			return false;
		}
		this.acceleration = new_accel;
		return true;
	}
	
	// Accelerate the car over an interval dt. Returns True if vehicle has 
	// not yet reached TOP_ACCEL, False otherwise
	public boolean accel(double dt) {
		double accel_amount = ACCEL_RATE;
		double new_accel = acceleration + accel_amount;
		if (new_accel > TOP_ACCEL) {
			this.acceleration = TOP_ACCEL;
			return false;
		}
		this.acceleration = new_accel;
		return true;
	}
	
	// Stop braking or accelerating 
	public void stopAccel()
	{
		this.acceleration = 0;
		return;
	}
	
	public double getCurrSpeed() {
		return this.speed;
	}
	
	public double getCurrAccel() {
		return this.acceleration;
	}
	
	public double getCurrX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public void setMaxSpeed(double speed_limit) {
		this.max_speed = speed_limit + SPEED_LIMIT_FACTOR;
	}

	public double getFrontX() {
		return this.x;
	}
	
	public double getEndX() {
		return this.x - this.LENGTH;
	}
	
	public void setCrashed(boolean crashed) {
		this.crashed = crashed;
	}
	
	public boolean getCrashed() {
		return this.crashed;
	}

	public double getRxnTime() {
		return this.RXN_TIME;
	}
	
	public double getWidth() {
		return this.WIDTH;
	}
	
	public double getLength() {
		return this.LENGTH;
	}
	
	// gradually slow the car TODO: remove this prototype
//	public void gradualSlow(double dt) {
//		this.noAccel = true;
//	}
	
	public void setNoAccel(boolean value) {
		this.noAccel = value;
	}
}
