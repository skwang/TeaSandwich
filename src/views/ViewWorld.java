package views;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ViewWorld {
	
	private ArrayList<ViewRoad> viewroads;
	private int height; // in pixels
	private int width;  // in pixels
	private Color background_color = new Color(224,224,224);
	private static float pixelsToMeters = 2;  // ratio of pixels to Meters
	
	public ViewWorld(int height, int width) {
		this.viewroads = new ArrayList<ViewRoad>();
		this.height = height;
		this.width = width;
	}

	public void draw(Graphics g) {
		g.setColor(background_color);
		g.fillRect(0, 0, width, height);
		for (ViewRoad road : viewroads) road.draw(g);
	}
	
	public void update(double dt) {
		for (ViewRoad vr : this.viewroads) {
			vr.update(dt);
		}
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
