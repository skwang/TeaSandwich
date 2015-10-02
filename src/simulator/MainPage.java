package simulator;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import models.Road;
import models.Vehicle;
import views.ViewRoad;
import views.ViewWorld;
 
public class MainPage extends BasicGameState {
	
	private int stateId;		// Identifies the game state
	private double avgdelta; 	// Helper to keep track of FPS/delta
	private int n;				// Helper to keep track of FPS/delta
	private ViewWorld world; // World to be displayed, contains the logic for the game
	private Button oneAB;
	
	public MainPage(int id) {
		this.stateId = id;
	}
	
	// Required for BGS 
	public int getID() {
		return this.stateId;
	}
	
	// Inherited: Initialize the state, load any resources it needs at this stage
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		n = 0;
		avgdelta = 0;
		world = new ViewWorld(gc.getHeight(), gc.getWidth());
		this.populateWorld();
		
	}
	
	// Inherited: Update the state's logic based on the amount of time thats passed
	// Delta is the ms/frame, determined by setting the fps
	public void update(GameContainer gc, StateBasedGame sbg, int delta)  throws SlickException {
		Input input = gc.getInput();
		avgdelta = (avgdelta * n + delta)/(n+1);
		n++;
		this.world.update(delta/1000.);
		if (n > 16) {
			
			n = 0;
		}
		if (input.isKeyPressed(Input.KEY_ENTER)) 
			sbg.enterState(1);
		if (oneAB != null && oneAB.checkClicked(input)) {
			int speed = (int) (Math.random() * 15) + 5;
			Vehicle car = new Vehicle(0, speed, 0);
			this.world.getViewRoad(0).getModelRoad().addVehicle(car, 'A');
		}
		
	}
	
	// Inherited: Render this state to the game's graphics context
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Input input = gc.getInput();
		world.draw(g);
		g.setColor(Color.white);
		g.drawString("mouseX: " + input.getMouseX() + " mouseY: " + input.getMouseY(), 
						10, gc.getHeight() - 20);
		g.drawString("Traffic Simulator!", 100, 100);
		g.drawString("Height " + gc.getHeight(), 100, 120);
		g.drawString("Width " + gc.getWidth(), 100, 140);
		g.drawString("Delta " + this.avgdelta, 100, 160);
		if (oneAB != null) {
			oneAB.draw(g);
		}
	}
	
	// prototype method to make the world
	private void populateWorld() {
		float world_length = this.world.convertPixelsToMeters((float) this.world.getWidth());
		Vehicle car = new Vehicle(0, 40, 0);
		Road center_road = new Road(20, world_length);
		center_road.addVehicle(car, 'A');
		ViewRoad v_center_road = new ViewRoad(center_road, 0, this.world.getHeight()/2, 
									this.world.convertMetersToPixels(1));
		this.world.addViewRoad(v_center_road);
		oneAB = new Button(v_center_road.getX(), v_center_road.getY() 
							+ v_center_road.getWidth(), 12f, 15f, "+");
	}
}
