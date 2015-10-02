package simulator;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class TeaSandwich extends StateBasedGame{
	public static final String gamename = "Tea Sandwich";
	public static final int mainpageid = 0;
	
	public TeaSandwich() {
		super(gamename);
		this.addState(new MainPage(mainpageid));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(mainpageid).init(gc, this);
		this.enterState(mainpageid);
	}
	
	public static void main(String[] args){
		try {
			AppGameContainer agc = new AppGameContainer(new TeaSandwich());
			agc.setTargetFrameRate(60);
//			agc.setShowFPS(false);
			agc.setDisplayMode(1000, 600, false);
			agc.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
}
