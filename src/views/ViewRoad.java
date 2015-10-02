package views;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import models.Road;
import models.Vehicle;

public class ViewRoad {
	
	private Road modelRoad; // modelRoad associated with the ViewRoad 
	private float x; // the x pixel coordinate of the upper left pixel
	private float y; // the y pixel coordinate of the upper left pixel
	private float pixelsToMeters;
	private float length; // in pixels
	private float width;
	
	
	public ViewRoad(Road modelRoad, int x, int y, float pixelsToMeters) {
		this.modelRoad = modelRoad;
		this.x = x;
		this.y = y;
		this.pixelsToMeters = pixelsToMeters;
		this.length = pixelsToMeters * (float) modelRoad.getLength();
		this.width = pixelsToMeters * (float) modelRoad.getWidth();
	}

	// draw the road, and associated vehicles on it
	public void draw(Graphics g) {
		drawRoad(g);
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
	
	// direction 0 = AB, direction 1 = BA
	private void drawVehicle(Graphics g, Vehicle v, int direction) {
		float v_length = pixelsToMeters * (float) v.getLength();
		//		float v_width = road_width / 2;
		float v_width = pixelsToMeters * (float) v.getWidth();
		g.setColor(Color.green);
		if (direction == 0) {
			float vehicle_x = this.x + pixelsToMeters* (float) v.getEndX();
			//			float vehicle_y = this.y + 0.6f * road_width;
			float vehicle_y = this.y + 0.75f * width - 0.5f * pixelsToMeters * (float) v_width;
			g.fillRect(vehicle_x, vehicle_y, v_length, v_width);
		}
		else { // direction  = 1
			float vehicle_x = this.x + pixelsToMeters * (float) v.getFrontX();
			float vehicle_y = this.y + 0.25f * width - 0.5f * pixelsToMeters * (float) v_width;
			g.fillRect(vehicle_x, vehicle_y, v_length, v_width);
		}
	}
	
	private void drawRoad(Graphics g) {
		// draw road
		g.setColor(Color.black);
		g.fillRect(x, y, length, width);
		// draw lane divider
		g.setColor(Color.yellow);
		g.fillRect(x, y + width/2, length, 1);
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
