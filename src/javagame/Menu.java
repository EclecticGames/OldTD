package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState{//Stefan

	public static JDBC jdbc;
	
	public Image playNow;
	public Image exitGame;
	public Image mapEditor;
	
	public static Music menuMusic;
	
	public String newGame;
	
	public int x = 0;//coordinates for the mouse
	public int y = 0;
	
	public int time = 0;
	
	public boolean debug = false;	
	
	public Menu(int state){
		
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		newGame = "Neues Spiel starten";
		playNow = new Image("res/playNow.png");
		exitGame = new Image("res/exitGame.png");
		mapEditor = new Image("res/mapEditor.png");
		jdbc = new JDBC();
		
		menuMusic = new Music("res/info.ogg");
		menuMusic.loop();
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {	
		if(debug){//coordinates of the mouse
			g.drawString("X : "+x+", Y : "+y, 750, 5);
		}
		
		playNow.draw(Game.screenWidth/2 - playNow.getWidth()/2 , Game.screenHeight/2 - playNow.getHeight() * 2);
		exitGame.draw(Game.screenWidth/2 - exitGame.getWidth()/2 , Game.screenHeight/2 - exitGame.getHeight() +10 );
		mapEditor.draw(Game.screenWidth/2 - mapEditor.getWidth()/2 , Game.screenHeight/2 - mapEditor.getHeight() +20 + mapEditor.getHeight() );
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		x = mouseX;
		y = mouseY;
		
		if(input.isKeyPressed(Input.KEY_D)){ //turn debug mode on/off
			if(debug){
				debug = false;
			} else{
				debug = true;
			}
		}
		
		if(input.isKeyPressed(Input.KEY_F)){
			container.setFullscreen(true);
		}
		
		//if mouse is over playNow button
		if((mouseX >= (Game.screenWidth/2 - playNow.getWidth() / 2) && mouseX <= ((Game.screenWidth/2 - playNow.getWidth() / 2) + playNow.getWidth())) && (mouseY >= (Game.screenHeight/2 - playNow.getHeight() * 2)) && mouseY <= (Game.screenHeight/2 - playNow.getHeight() - 10)){
			if(input.isMousePressed(0)){
				game.getState(Value.PLAY_ID).init(container, game);
//				game.enterState(Value.PLAY_ID);
				game.enterState(Value.SWITCH_ID);
			}
		}
		//if mouse is over exitGame button
		if((mouseX >= (Game.screenWidth/2 - exitGame.getWidth()/2) && mouseX <= (Game.screenWidth/2 - exitGame.getWidth()/2 + exitGame.getWidth())) && mouseY >= Game.screenHeight/2 - exitGame.getHeight() +10 && mouseY <= (Game.screenHeight/2)){
			if(input.isMousePressed(0)){
				System.exit(0);
			}
		}
		if(mouseX >= 375 && mouseX<= 585 && mouseY >= 290 && mouseY <= 340){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				game.enterState(Value.EDITOR_ID);
			}
		}
		
	}

	public int getID() {
		return Value.MENU_ID;
	}
	
}
