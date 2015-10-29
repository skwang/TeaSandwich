package models;

import java.util.*;

public class Node {
	
	// integers indicating direction
	public static int L2R = 0;
	public static int R2L = 1;
	public static int T2B = 2;
	public static int B2T = 3;
	
	// used to represent the car's location in the intersection
	// Note that these are meters, not pixels!
	// X, Y describe the front-center of the car 
	public class NodeVector {
		
		public double X; 
		public double Y;
		public int D;
		
		public NodeVector(double x, double y, int direction) {
			X = x;
			Y = y;
			D = direction;
		}
	}
	
	private double WIDTH = 11;  // up down
	private double LENGTH = 11; // left right
	
	// references to the road objects
	private Road top;
	private Road bot;
	
	private Road left;
	private Road right;
	
	
	// all vehicles at the intersection, and their direction
	private HashMap<Vehicle, NodeVector> allVehicles; 
	 
	public Node (Road top, Road bot, Road left, Road right) {
		this.top = top;
		this.bot = bot;
		this.left = left;
		this.right = right;
		this.allVehicles = new HashMap<Vehicle, NodeVector>();
	}
	
	// enter a Vehicle v into the intersection. src denotes the 
	// source Road, which is used to determine orientation
	public int enterNode(Vehicle v, Road src) {
		if (src.equals(left)) {
			double initX = 0;
			double initY = WIDTH*3/4; 
			v.setX(0);
			allVehicles.put(v, new NodeVector(initX, initY, L2R));
			return 1;
		}
		else if (src.equals(right)) {
			double initX = LENGTH;
			double initY = WIDTH * 1/4;
			v.setX(0);
			allVehicles.put(v, new NodeVector(initX, initY, R2L));
			return 1;
		}
		return -1;
	}
	
	public void update(double dt) {
		HashSet<Vehicle> toBeRemoved = new HashSet<Vehicle>();
		for (Vehicle v : allVehicles.keySet()) {
			v.updateFromSignal(dt, null, 1000); // TODO: figure out this update thing
			int direction = allVehicles.get(v).D;
			if (direction == L2R) {
				allVehicles.get(v).X = v.getFrontX();
				
				// leaving the intersection
				if (allVehicles.get(v).X > this.LENGTH) {
					toBeRemoved.add(v);
					if (this.right != null) {
						right.addVehicle(v, 'A');
						v.setX(0);
					}
				}
			}
			else if (direction == R2L) {
				allVehicles.get(v).X = LENGTH - v.getFrontX();
				// leaving the intersection
				if (v.getFrontX() > this.LENGTH) {
					System.out.println("remove me");
					toBeRemoved.add(v);
					if (this.left != null) {
						left.addVehicle(v, 'B');
						v.setX(0);
					}
				}
			}
		}
		for (Vehicle v : toBeRemoved) {
			allVehicles.remove(v);
		}
	}
	
	public HashMap<Vehicle, NodeVector> getAllVehicles() {
		return allVehicles;
	}
	
	// return length of intersection
	public double getLength() {
		return this.LENGTH;
	}
	
	// return width of intersection
	public double getWidth() {
		return this.WIDTH;
	}
	
	public ArrayList<Road> getAllRoads(){
		ArrayList<Road> allRoads = new ArrayList<Road>();
		allRoads.add(top);
		allRoads.add(bot);
		allRoads.add(left);
		allRoads.add(right);
		return allRoads;
	}
	
	public Road getTop() {
		return this.top;
	}
	
	public Road getBot() {
		return this.bot;
	}
	
	public Road getLeft() {
		return this.left;
	}
	
	public Road getRight() {
		return this.right;
	}
}
