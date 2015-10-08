package simulator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public class Button {

	private String text;
	private Rectangle buttonShape;
	public Button(float x, float y, float width, float height, String text) {
		buttonShape = new Rectangle(x, y, width, height);
		this.text = text;
	}
	
	public boolean checkClicked(Input input) {
		if (buttonShape.contains(input.getMouseX(), input.getAbsoluteMouseY()) 
				&& input.isMousePressed(0)) 
			return true; 
		return false;
	}
	
	public boolean checkClickandHold(Input input) {
		if (buttonShape.contains(input.getMouseX(), input.getAbsoluteMouseY()) 
				&& input.isMouseButtonDown(0)) 
			return true;
		return false;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.orange);
		g.fill(buttonShape);
		g.setColor(Color.black);
		g.drawString(text, buttonShape.getX(), buttonShape.getY());
	}
}
