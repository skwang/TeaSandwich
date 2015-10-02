package models;

public class Signal {
	
	// G is go, Y is slow, R is stop
	private char TYPE;
	// radius (in m) that cars can observe it -- 50 m?
	private double RANGE;
	
	public Signal(char type, double range) {
		this.TYPE = type;
		this.RANGE = range;
	}
	
	public char getType() {
		return this.TYPE;
	}
	
	public double getRange() {
		return this.RANGE;
	}

}
