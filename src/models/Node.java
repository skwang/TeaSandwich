package models;

import java.util.*;

public class Node {
	
	private Road up;
	private Road down;
	
	private Road left;
	private Road right;

	public Node (Road up, Road down, Road left, Road right) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
	}
	
	public ArrayList<Road> getAllRoads(){
		ArrayList<Road> allRoads = new ArrayList<Road>();
		allRoads.add(up);
		allRoads.add(down);
		allRoads.add(left);
		allRoads.add(right);
		return allRoads;
	}
	
	public Road getUp() {
		return this.up;
	}
	
	public Road getDown() {
		return this.down;
	}
	
	public Road getLeft() {
		return this.left;
	}
	
	public Road getRight() {
		return this.right;
	}
}
