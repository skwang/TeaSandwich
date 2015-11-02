package views;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import models.Node;
import models.Vehicle;

public class ViewNode {
	private Node modelNode;
	private float x; // the x pixel coordinate of the upper left pixel
	private float y; // the y pixel coordinate of the upper left pixel
	private float pixelsToMeters;
	private float length; // in pixels length is X
	private float width;  // width is Y
	
	public ViewNode(Node modelNode, int x, int y, float pixelsToMeters) {
		this.modelNode = modelNode;
		this.x = x;
		this.y = y;
		this.pixelsToMeters = pixelsToMeters;
		this.length = pixelsToMeters * (float) modelNode.getLength();
		this.width = pixelsToMeters * (float) modelNode.getWidth();
	}
	
	public void drawVehicles(Graphics g) {
		for (Vehicle v : this.modelNode.getAllVehicles().keySet()) {
			int direction = this.modelNode.getAllVehicles().get(v).D;
			drawVehicle(g, v, direction); // TODO: fix direction
		}
	}
	
	public void update(double dt) {
		this.modelNode.update(dt);
	}
	
	public void drawNode(Graphics g) {
		// draw road
		g.setColor(Color.black);
		g.fillRect(x, y, length, width);
		
		// draw the white bars
		g.setColor(Color.white);
		g.fillRect(x, y - 1, length, 2); 		 // top across
		g.fillRect(x, y + width - 1, length, 2); // bot across
		g.fillRect(x - 1, y, 2, width); 		 // left down
		g.fillRect(x + length - 1, y, 2, width); // right down
		
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
		
		if (direction == 0) {
			float vehicle_x = this.x + pixelsToMeters* (float) v.getEndX();
			float vehicle_y = this.y + 0.85f * width - 0.5f * pixelsToMeters * (float) v_width;
			g.fillRect(vehicle_x, vehicle_y, v_length, v_width);
			// print speed
			double mpstomph = 2.23694;
			g.setColor(Color.black);
			g.drawString(String.format("%.1f",v.getCurrSpeed() * mpstomph), vehicle_x - 10, vehicle_y + 10);
		}
		else if (direction == 1) { 
			float vehicle_x = this.x + this.length - pixelsToMeters * (float) v.getFrontX();
			float vehicle_y = this.y + 0.35f * width - 0.5f * pixelsToMeters * (float) v_width;
			g.fillRect(vehicle_x, vehicle_y, v_length, v_width);
		}
		else if (direction == 2) {
			float vehicle_x = this.x + 0.35f * width - 0.5f * pixelsToMeters * (float) v_width;
			float vehicle_y = this.y + pixelsToMeters * (float) v.getEndX();
			g.fillRect(vehicle_x, vehicle_y, v_width, v_length);
		}
		else if (direction == 3) {
			float vehicle_x = this.x + 0.85f * width - 0.5f * pixelsToMeters * (float) v_width;
			float vehicle_y = this.y + this.length - pixelsToMeters* (float) v.getFrontX();
			g.fillRect(vehicle_x, vehicle_y, v_width, v_length);
		}
	}
	
	// returns the pixel coordinate at the end
	public float getRightX() {
		return this.x + this.length;
	}
	
	public float getLeftX() {
		return this.x;
	}
	
	public float getTopY() {
		return this.y;
	}
	
	public float getBotY() {
		return this.y + this.width;
	}
}
