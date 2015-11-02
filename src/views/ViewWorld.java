package views;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ViewWorld {
	
	private ArrayList<ViewRoad> viewroads;
	private ArrayList<ViewNode> viewnodes;
	private int height; // in pixels
	private int width;  // in pixels
	private Color background_color = new Color(224,224,224);
	private static float pixelsToMeters = 2;  // ratio of pixels to Meters
	
	public ViewWorld(int height, int width) {
		this.viewroads = new ArrayList<ViewRoad>();
		this.viewnodes = new ArrayList<ViewNode>();
		this.height = height;
		this.width = width;
	}

	public void draw(Graphics g) {
		g.setColor(background_color);
		g.fillRect(0, 0, width, height);
		for (ViewNode vn : this.viewnodes) vn.drawNode(g);
		for (ViewRoad road : viewroads)    road.drawRoad(g);
		for (ViewNode vn : this.viewnodes) vn.drawVehicles(g);
		for (ViewRoad road : viewroads)    road.drawVehicles(g);
	}
	
	public void update(double dt) {
		for (ViewNode vn : this.viewnodes) {
			vn.update(dt);
		}
		for (ViewRoad vr : this.viewroads) {
			vr.update(dt);
		}
		
	}
	
	public void addViewNode(ViewNode vn) {
		this.viewnodes.add(vn);
	}
	
	public void addViewRoad(ViewRoad vr) {
		this.viewroads.add(vr);
	}
	
	public ViewRoad getViewRoad(int index) {
		return this.viewroads.get(index);
	}
	
	public float convertMetersToPixels(double meters) {
		return (float) meters * pixelsToMeters;
	}
	
	public float convertPixelsToMeters(float pixels) {
		return pixels / pixelsToMeters;
	}
	
	// return Height in Pixels
	public int getHeight() {
		return this.height;
	}
	
	// return Width in Pixels
	public int getWidth() {
		return this.width;
	}
}
