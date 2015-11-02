package views;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import models.Road;
import models.Vehicle;

public class ViewRoad {
	
	public enum Orientation {
		VERTICAL, HORIZONTAL
	}
	
	private Road modelRoad; // modelRoad associated with the ViewRoad 
	private float x; // the x pixel coordinate of the upper left pixel
	private float y; // the y pixel coordinate of the upper left pixel
	private float pixelsToMeters;
	private float length; // in pixels
	private float width;
	private Orientation orientation;
	
	
	public ViewRoad(Road modelRoad, int x, int y, float pixelsToMeters, Orientation orientation) {
		this.modelRoad = modelRoad;
		this.x = x;
		this.y = y;
		this.pixelsToMeters = pixelsToMeters;
		this.length = pixelsToMeters * (float) modelRoad.getLength();
		this.width = pixelsToMeters * (float) modelRoad.getWidth();
		this.orientation = orientation;
	}
	
	// returns the pixel coordinate at the end
	public float getEndX() {
		return this.x + this.length;
	}
	
	public float getFrontX() {
		return this.x;
	}

	// draw the associated vehicles on it
	public void drawVehicles(Graphics g) {
		for (Vehicle v : this.modelRoad.getAB()) {
			drawVehicle(g, v, 0);
		}
		for (Vehicle v : this.modelRoad.getBA()) {
			drawVehicle(g, v, 1);
		}
	}
	
	public Road getModelRoad()
	{
		return this.modelRoad;
	}
	
	// direction 0 = L2R, direction 1 = R2L
	private void drawVehicle(Graphics g, Vehicle v, int direction) {
		// vehicle length and width in pixels
		float v_length = pixelsToMeters * (float) v.getLength();
		float v_width = pixelsToMeters * (float) v.getWidth();
		
		// default color is green
		g.setColor(Color.white);
		
		if (v.getCrashed()) g.setColor(Color.red); 
		else if (v.getCurrSpeed() == 0) g.setColor(Color.orange);
		else if (v.getCurrAccel() < 0) g.setColor(Color.yellow);
		else if (v.getCurrAccel() > 0) g.setColor(Color.green);
		
		double mpstomph = 2.23694;
		
		if (this.orientation == Orientation.HORIZONTAL) 
		{
			if (direction == 0) 
			{
				float vehicle_x = this.x + pixelsToMeters* (float) v.getEndX();
				float vehicle_y = this.y + 0.85f * width - 0.5f * pixelsToMeters * (float) v_width;
				g.fillRect(vehicle_x, vehicle_y, v_length, v_width);
				// print speed
				g.setColor(Color.black);
				g.drawString(String.format("%.1f",v.getCurrSpeed() * mpstomph), vehicle_x - 10, vehicle_y + 10);
			}
			else { // direction  = 1
				float vehicle_x = this.x + this.length - pixelsToMeters * (float) v.getFrontX();
				float vehicle_y = this.y + 0.35f * width - 0.5f * pixelsToMeters * (float) v_width;
				g.fillRect(vehicle_x, vehicle_y, v_length, v_width);
				// print speed
				g.setColor(Color.black);
				g.drawString(String.format("%.1f",v.getCurrSpeed() * mpstomph), vehicle_x - 10, vehicle_y - 20);
			}
		}
		else if (this.orientation == Orientation.VERTICAL) {
			if (direction == 0) 
			{
				float vehicle_x = this.x + 0.35f * width - 0.5f * pixelsToMeters * (float) v_width;
				float vehicle_y = this.y + pixelsToMeters * (float) v.getEndX();
				g.fillRect(vehicle_x, vehicle_y, v_width, v_length);
				// print speed
				g.setColor(Color.black);
				g.drawString(String.format("%.1f",v.getCurrSpeed() * mpstomph), vehicle_x - 45, vehicle_y);
			}
			else {
				float vehicle_x = this.x + 0.85f * width - 0.5f * pixelsToMeters * (float) v_width;
				float vehicle_y = this.y + this.length - pixelsToMeters* (float) v.getFrontX();
				g.fillRect(vehicle_x, vehicle_y, v_width, v_length);
				// print speed
				g.setColor(Color.black);
				g.drawString(String.format("%.1f",v.getCurrSpeed() * mpstomph), vehicle_x + 10, vehicle_y);
			}
		}
	}
	
	public void drawRoad(Graphics g) {
		// draw road
		g.setColor(Color.black);
		if (this.orientation == Orientation.HORIZONTAL) {
			g.fillRect(x, y, length, width);
		}
		else if (this.orientation == Orientation.VERTICAL) {
			g.fillRect(x, y, width, length);
		}
		// draw lane divider
		g.setColor(Color.yellow);
		if (this.orientation == Orientation.HORIZONTAL) {
			g.fillRect(x, y + width/2, length, 1);
		}
		else if (this.orientation == Orientation.VERTICAL) {
			g.fillRect(x + width/2, y, 1, length);
		}
	}
	
	public void update(double dt) {
		this.modelRoad.update(dt);
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public float getLength() {
		return this.length;
	}
	
}
