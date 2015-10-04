package models;

import java.util.*;

public class Road {
	
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
		else
			vehiclesBA.add(v);
		v.setX(0);
		v.setMaxSpeed(SPEED_LIMIT);
	}
	
	// Update the vehicles 
	private void updateRoad(char startNode, double dt) {
		ArrayList<Vehicle> toBeRemoved = new ArrayList<Vehicle>();
		if (startNode == 'A') {
			Signal s = this.signalA;
			Vehicle prev = null;
			for (Vehicle v : vehiclesAB) {
				double pos = v.updateFromSignal(dt, s);
				if (pos > this.LENGTH) {
					if (this.pointB == null)
						toBeRemoved.add(v);
				}
				else if (prev != null && v.getFrontX() >= prev.getEndX()) {
					v.setX(prev.getEndX());
					v.setCrashed(true);
					prev.setCrashed(true);
					v.brake(dt);
					prev.brake(dt);
				}
				prev = v;
			}
			for (Vehicle v : toBeRemoved) {
				vehiclesAB.remove(v);
			}
		}
		else if (startNode == 'B') {
			// TODO: fix this method
			Signal s = this.signalB;
			Vehicle prev = null;
			for (Vehicle v : vehiclesBA) {
				double pos = v.updateFromSignal(dt, s);
			}
		}
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
}
