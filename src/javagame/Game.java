package javagame;

import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.Log;

public class Game extends StateBasedGame{//Stefan

	public static AppGameContainer appgc;
	
	public static int screenWidth = 960;
	public static int screenHeight = 540;
	
//	public static int screenWidth = 1920;
//	public static int screenHeight = 1080;
	
	public static final String gameName = "Tower Defense";
	
	public Game(String name) {
		super(name);
		this.addState(new Menu(Value.MENU_ID));
		this.addState(new Play(Value.PLAY_ID));
		this.addState(new MapEditor(Value.EDITOR_ID));
		this.addState(new SwitchMapState(Value.SWITCH_ID));
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(Value.MENU_ID).init(gc, this);
//		this.getState(Value.PLAY_ID).init(gc, this);
		this.enterState(Value.MENU_ID);

//		gc.setMinimumLogicUpdateInterval(20);//bad for timer!
//		gc.setMaximumLogicUpdateInterval(20);
	}
	
	public void init(){//to start the game
		try{
			//window wich hold game
			appgc = new AppGameContainer(new Game(gameName));
			appgc.setDisplayMode(screenWidth, screenHeight, false);
			appgc.start();
		}catch(SlickException e){
			Log.error(e.toString());
		}
	
	}
	
}
