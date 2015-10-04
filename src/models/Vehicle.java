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
	private final double TOP_BRAKE = 4.33;
	private final double TOP_SPEED = 54.5389; // 122mph
	
	// How much the accel and deaccelerate changes every second 
	private final double ACCEL_RATE = TOP_ACCEL/3;
	private final double BRAKE_RATE = TOP_BRAKE/3;
	
	// How fast the driver is willing to go above the speed limit
	private final double SPEED_LIMIT_FACTOR = 4.4704; // 10 mph
	
	// How long (in s) it takes the driver to respond to an event
	private final double RXN_TIME = 0.8; 
	
	// How much distance (in m) the driver places between himself and 
	// the object in front when moving
	private final double MOVING_MARGIN = 10;
	
	// Same as above, but when braked
	private final double STOPPED_MARGIN = 4;
	
	public Vehicle(double x, double speed, double acceleration) {
		this.x = x;
		this.speed = speed;
		this.acceleration = acceleration;
		this.crashed = false;
	}
	
	// Update the Vehicle over an interval dt (in ms), in response to
	// the signal s
	public double updateFromSignal(double dt, Signal s) {
		double secs = dt;
		double new_speed = speed + secs * acceleration;
		double avg_speed = (speed + new_speed)/2; // TODO: Try without the avg_speed
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
		System.out.println(avg_speed);
		return x;
	}
	
	// Brake the car over an interval dt. Returns True if vehicle has 
	// not yet reached TOP_BRAKE, False otherwise
	public boolean brake(double dt) {
		//System.out.println(dt);
		// dt in secs!!!
		double secs = dt;
		double brake_amount = BRAKE_RATE; // m/s^2
		double new_accel = acceleration - brake_amount;
		if (new_accel > TOP_BRAKE) {
			this.acceleration = TOP_BRAKE;
			return false;
		}
		this.acceleration = new_accel;
		//System.out.println(this.acceleration);
		return true;
	}
	
	// Accelerate the car over an interval dt. Returns True if vehicle has 
	// not yet reached TOP_ACCEL, False otherwise
	public boolean accel(double dt) {
		double secs = dt/1000;
		double accel_amount = ACCEL_RATE * secs;
		double new_accel = acceleration + dt*accel_amount;
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
}
