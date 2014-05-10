package javagame;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class SwitchMapState extends BasicGameState{//Stefan und Jan-Niklas
	
	public SwitchMapState(int state){
		
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		g.drawString("Load default map", 60, 70);
		g.drawString("Load custom map", 60, 100);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		if(mouseX>=60 && mouseX<=180 && mouseY>=70 &&mouseY<=90){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				Play.playMusic.loop();
				game.enterState(Value.PLAY_ID);
			}
		}
		
		if(mouseX>=60 && mouseX<=180 && mouseY>=100 && mouseY<=120){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				GameLogic.loadMap("res/map2.map");
				Play.playMusic.loop();
				game.enterState(Value.PLAY_ID);
			}
		}
	}

	public int getID() {
		return Value.SWITCH_ID;
	}

}
