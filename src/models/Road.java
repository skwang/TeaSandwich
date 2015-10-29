package models;

import java.util.*;

public class Road {
	
	public enum Direction {
		AtoB, BtoA
	}
	
	// in m or m/s 
	private final double SPEED_LIMIT;
	private final double LENGTH;
	private final double WIDTH = 11; // 11 meters
	
	private Node pointA; // The node on end A
	private Node pointB; // The node on end B
	
	private Signal signalA; // The signal (if any) associated with node A
	private Signal signalB; // The signal (if any) associated with node B
	
	// the vehicles traveling from node A to node B
	private ArrayList<Vehicle> vehiclesAB; 
	
	// the vehicles traveling from node B to node A
	private ArrayList<Vehicle> vehiclesBA; 
	
	public Road(double speed_limit, double length) {
		this.pointA = null;
		this.pointB = null;
		this.signalA = null;
		this.signalB = null;
		this.SPEED_LIMIT = speed_limit;
		this.LENGTH = length;
		this.vehiclesAB = new ArrayList<Vehicle>();
		this.vehiclesBA = new ArrayList<Vehicle>();
	}
	
	// Set this Road's pointA Node to pointA
	public void setpointA(Node pointA) {
		this.pointA = pointA;
		return;
	}
	
	// Set this Road's pointB Node to pointB
	public void setpointB(Node pointB) {
		this.pointB = pointB;
		return;
	}
	
	// Set this Road's signalA Signal to signalA
	public void setsignalA(Signal signalA) {
		this.signalA = signalA;
		return;
	}
	
	// Set this Road's signalB Signal to signalB
	public void setsignalB(Signal signalB) {
		this.signalB = signalB;
		return;
	}
	
	// Add a vehicle V to the road segment starting with startNode
	public void addVehicle(Vehicle v, char startNode) {
		if (startNode == 'A') 
			vehiclesAB.add(v);	
		else if (startNode == 'B') 
			vehiclesBA.add(v);
		v.setX(0);
		v.setMaxSpeed(SPEED_LIMIT);
	}
	
	// Update the vehicles 
	private void updateRoad(char startNode, double dt) {
		HashSet<Vehicle> toBeRemoved = new HashSet<Vehicle>();
		if (startNode == 'A') {
			Signal s = this.signalA;
			Vehicle prev = null;
			for (Vehicle v : vehiclesAB) {
				prev = updateWithSignal(v, prev, dt, s, toBeRemoved, Direction.AtoB);
			}
			for (Vehicle v : toBeRemoved) {
				vehiclesAB.remove(v);
			}
		}
		else if (startNode == 'B') {
			Signal s = this.signalB;
			Vehicle prev = null;
			for (Vehicle v : vehiclesBA) {
				prev = updateWithSignal(v, prev, dt, s, toBeRemoved, Direction.BtoA);
			}
			for (Vehicle v : toBeRemoved) {
				vehiclesBA.remove(v);
			}
		}
	}
	
	private Vehicle updateWithSignal(Vehicle v, Vehicle prev, double dt, 
								Signal s, HashSet<Vehicle> toBeRemoved, Direction d) 
	{
		double distTo = Double.MAX_VALUE;
		if (prev != null) {
			distTo = prev.getEndX() - v.getFrontX();
		}
		double pos = v.updateFromSignal(dt, s, distTo);
		if (pos > this.LENGTH) {
			if (d == Direction.AtoB && this.pointB != null) {
				int result = this.pointB.enterNode(v, this);
				System.out.println("Enter node result: " + result);
			}
			else if (d == Direction.BtoA && this.pointA != null) {
				int result = this.pointA.enterNode(v, this);
				System.out.println("Enter node result: " + result);
			}
			toBeRemoved.add(v);	
			return null;
		}
		else if (prev != null && v.getFrontX() >= prev.getEndX()) {
			v.setX(prev.getEndX());
			v.setCrashed(true);
			prev.setCrashed(true);
		}
		return v;
	}
	
	// Update Roads with time delta dt and its 
	public void update(double dt) {
		updateRoad('A', dt);
		updateRoad('B', dt);
	}
	
	// return length of road
	public double getLength() {
		return this.LENGTH;
	}
	
	// return width of road
	public double getWidth() {
		return this.WIDTH;
	}
	
	public ArrayList<Vehicle> getAB() {
		return this.vehiclesAB;
	}
	
	public ArrayList<Vehicle> getBA() {
		return this.vehiclesBA;
	}
	
	// get the Node marked by char node 
	public Node getNode(char node) {
		if (node == 'A') return this.pointA;
		else			 return this.pointB;
	}
	
	// for testing only TODO: remove this prototype, slow first car
	public void slowFirstCar(char node, double delta) {
		if (node == 'A') {
			if (!this.vehiclesAB.isEmpty()) {
				this.vehiclesAB.get(0).setNoAccel(true);
			}
				
		}
	}
	
	// for testing only TODO: remove this prototype, unslow first car
	public void stopSlowFirstCar(char node, double delta) {
		if (node == 'A') {
			if (!this.vehiclesAB.isEmpty()) {
				this.vehiclesAB.get(0).setNoAccel(false);
			}
				
		}
	}
}
