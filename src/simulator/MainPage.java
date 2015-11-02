package simulator;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import models.Node;
import models.Road;
import models.Vehicle;
import views.ViewNode;
import views.ViewRoad;
import views.ViewWorld;
 
public class MainPage extends BasicGameState {
	
	private int stateId;		// Identifies the game state
	private double avgdelta; 	// Helper to keep track of FPS/delta
	private int n;				// Helper to keep track of FPS/delta
	private ViewWorld world; // World to be displayed, contains the logic for the game
	private Button oneAB;
	private Button oneBA;
	private Button topAB;
	private Button botBA;
	private Button slowFirst; 
	
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
//		if (n > 120) {
//			n = 0;
//			int speed = (int) (Math.random() * 15) + 5;
//			Vehicle car = new Vehicle(0, speed, 0);
//			this.world.getViewRoad(0).getModelRoad().addVehicle(car, 'A');
//		}
		this.world.update(delta/1000.); // dt = delta/1000. is secs
//		if (input.isKeyPressed(Input.KEY_ENTER)) 
//			sbg.enterState(1);
		if (oneAB != null && oneAB.checkClicked(input)) 
		{
			int speed = (int) (Math.random() * 15) + 5;
			Vehicle car = new Vehicle(0, speed, 0);
			this.world.getViewRoad(0).getModelRoad().addVehicle(car, 'A');
		}
		if (oneBA != null && oneBA.checkClicked(input)) 
		{
			int speed = (int) (Math.random() * 15) + 5;
			Vehicle car = new Vehicle(0, speed, 0);
			this.world.getViewRoad(1).getModelRoad().addVehicle(car, 'B');
		}
		if (topAB != null && topAB.checkClicked(input)) 
		{
			int speed = (int) (Math.random() * 15) + 5;
			Vehicle car = new Vehicle(0, speed, 0);
			this.world.getViewRoad(2).getModelRoad().addVehicle(car, 'A');
		}
		if (botBA != null && botBA.checkClicked(input)) 
		{
			int speed = (int) (Math.random() * 15) + 5;
			Vehicle car = new Vehicle(0, speed, 0);
			this.world.getViewRoad(3).getModelRoad().addVehicle(car, 'B');
		}
		// TODO: remove this prototype
		if (slowFirst != null && slowFirst.checkClickandHold(input)) {
			this.world.getViewRoad(0).getModelRoad().slowFirstCar('A', delta/1000.);
		}
		else {
			this.world.getViewRoad(0).getModelRoad().stopSlowFirstCar('A', delta/1000.);
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
		if (oneBA != null) {
			oneBA.draw(g);
		}
		if (topAB != null) {
			topAB.draw(g);
		}
		if (botBA != null) {
			botBA.draw(g);
		}
		if (slowFirst != null) {
			slowFirst.draw(g);
		}
	}
	
	// prototype method to make the world
	private void populateWorld() {
		float world_length = this.world.convertPixelsToMeters((float) this.world.getWidth());
		float world_height = this.world.convertPixelsToMeters((float) this.world.getHeight());
		Vehicle car = new Vehicle(0, 40, 0);
		Vehicle car2 = new Vehicle(0, 40, 0);
		
		Road center_road = new Road(15.64, world_length/2 - 5.5);
		Road next_road = new Road(15.64*1.25, world_length/2 - 5.5);
		Road top_road = new Road(15.64, world_height/2);
		Road bot_road = new Road(15.64, world_height/2 - 11);
		Node intersection = new Node(top_road, bot_road, center_road, next_road);
		
		
		top_road.addVehicle(car, 'A');
		bot_road.addVehicle(car2, 'B');
		center_road.setpointB(intersection);
		next_road.setpointA(intersection);
		top_road.setpointB(intersection);
		bot_road.setpointA(intersection);
		
		int level = this.world.getHeight()/2;
		ViewRoad v_center_road = new ViewRoad(center_road, 0, level, 
												this.world.convertMetersToPixels(1), 
												ViewRoad.Orientation.HORIZONTAL);
		ViewNode v_intersection = new ViewNode(intersection, (int) v_center_road.getEndX(), level, 
												this.world.convertMetersToPixels(1));
		ViewRoad v_next_road = new ViewRoad(next_road, (int) v_intersection.getRightX(), level,
											this.world.convertMetersToPixels(1), 
											ViewRoad.Orientation.HORIZONTAL);
		ViewRoad v_top_road = new ViewRoad(top_road,(int) v_intersection.getLeftX(), 0, 
											this.world.convertMetersToPixels(1),
											ViewRoad.Orientation.VERTICAL);
		ViewRoad v_bot_road = new ViewRoad(bot_road,(int) v_intersection.getLeftX(), (int) v_intersection.getBotY(), 
											this.world.convertMetersToPixels(1),
											ViewRoad.Orientation.VERTICAL);
		
		this.world.addViewRoad(v_center_road);
		this.world.addViewRoad(v_next_road);
		this.world.addViewNode(v_intersection);
		this.world.addViewRoad(v_top_road);
		this.world.addViewRoad(v_bot_road);
		
		oneAB = new Button(v_center_road.getX(), v_center_road.getY()
							+ v_center_road.getWidth() + 20f, 90f, 15f, "+ Add car");
		
		oneBA = new Button(v_next_road.getX() + v_next_road.getLength() - 90f, 
							v_next_road.getY() - v_next_road.getWidth() - 20f, 90f, 15f, "+ Add car");
		
		topAB = new Button(v_top_road.getX() - 90, 0, 90f, 15f, "+ Add car");

		botBA = new Button(v_bot_road.getX() + v_bot_road.getWidth(), 
							this.world.getHeight() - 15f, 90f, 15f, "+ Add car");
		
		slowFirst = new Button(v_center_road.getX(), v_center_road.getY() 
							+ v_center_road.getWidth() + 40f, 55f, 15f, "Slow 1");
	}
}
