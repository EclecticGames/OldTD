package javagame;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.Log;

public class Play extends BasicGameState{//Stefan

	public static Map map;
	public static Mob[] mobs = new Mob[10];
	public static GameLogic logic;
	public static Picture pic;
	public static Store store;
	
	public Image upgradeButton;
	public Image unselect;
	public Image maxTowerLevel;
	
	public static Music playMusic;
	
	public boolean debug;
	public boolean pause;
	public static boolean towerInfo;
	public boolean tooExpensive = false;
	
	public static int space = 20;
	public static int lifes;
	public static int coins;
	public static int points = 0;
	public static int mouseTowerX, mouseTowerY;
	public int time, oldTime;
	public int x = 0;//coordinates for the mouse
	public int y = 0;
	
	public static double towerSpeed, towerRange, towerDamage;
	public static String towerName;
	public static int selectedTowerX = -1, selectedTowerY = -1;//for upgrade system
	
	
	public Play(int state){
		
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {	
		Log.info("height: "+container.getScreenHeight()+ " width: "+container.getScreenWidth());
		
		upgradeButton = new Image("res/upgradeButton.png");
		unselect = new Image("res/unselect.png");
		maxTowerLevel = new Image("res/maxTowerLevel.png");
		
		debug = false;
		pause = false;
		towerInfo = false;
		
		lifes = 20;
		coins = 10;
		time = 0;
		
		map = new Map();
		logic = new GameLogic();
		pic = new Picture();
		store = new Store();
		
		for(int i=0;i<mobs.length;i++){
//			mobs[i] = new Mob(0,map.map[0][map.getFirstRoad()].getY(), Map.blockSize/2, 100, Value.LOW_MOB);
			mobs[i] = new LowMob(0,Map.data[0][map.getFirstRoad()].getY());
		}
		
		playMusic = new Music("res/duringgame.ogg");
//		playMusic.loop();
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		if(debug){//coordinates of the mouse
			g.drawString("X : "+x+", Y : "+y, 800, 5);
		}
		
		map.drawMap(g);
		store.draw(g);
		
		for(int i=0;i<mobs.length;i++){
			if(mobs[i].inGame){
				mobs[i].draw(g);
			}
		}
		
		g.drawString("Time : " + time/1000, 10, 50);//drawing the time?!
		g.drawString("Points: "+points, 10, 80);
		g.drawString("Wave: "+GameLogic.wave, 10, 110);
		g.drawString("Kills: "+GameLogic.kills, 10, 140);
		
		if(towerInfo){//if tower selected, this is the info about the tower
			g.drawString("Name: "+towerName, Game.screenWidth -165, 30);
			g.drawString("Damage: "+towerDamage, Game.screenWidth -165, 50);
			g.drawString("Range: "+towerRange, Game.screenWidth -165, 70);
			g.drawString("AttackSpeed: "+towerSpeed, Game.screenWidth -165, 90);
			g.drawImage(unselect,  Game.screenWidth -155 + upgradeButton.getWidth(), 110);
			if(!map.data[selectedTowerX][selectedTowerY].tower.fullBuild){
				g.drawImage(upgradeButton, Game.screenWidth -165, 110);
			}else{
				g.drawImage(maxTowerLevel, Game.screenWidth -165, 110);
			}//maxtowerlevel bild einfügen, mapeditor bugs behebn
			
		}
		
		if(pause){
			Color oldColor = g.getColor();
			g.setColor(new Color(105,105,105,110));
			g.fillRect(-1, -1, Game.screenWidth+1, Game.screenHeight+1);
			drawPauseMenu(g);
			g.setColor(oldColor);
		}
		
		if(tooExpensive){
			if (!(time / 1000 - oldTime / 1000 >= 3)) {
				Color oldColor = g.getColor();
				g.setColor(new Color(200,0,0));
				g.drawString("You don´t have enough money: "+ (Map.data[selectedTowerX][selectedTowerY].tower.damageCost - coins)+ " more needed!", 160, 500);
				g.setColor(oldColor);
			} else {
				tooExpensive = false;
			}
		}

	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		time += delta;
		Input input = container.getInput();
		
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		x = mouseX;
		y = mouseY;
		
		if(input.isKeyPressed(Input.KEY_ESCAPE)){//pause or unpause
			if(pause){
				pause = false;
			} else{
				pause = true;
			}
		}
		
		if(!pause){
		
			store.physic(mouseX, mouseY, input);
			map.physic(delta, mouseX, mouseY);
		
			for(int i=0;i<mobs.length;i++){
				mobs[i].walk(delta);
			}

			logic.logic(delta);
			
			if(map.mouseOverTower(mouseX)){//to get states of the tower/ to upgrade the tower
				if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
					map.getStats();//to get the stats of the tower
					towerInfo = true;
				}
			}
			
			if(towerInfo){
				if(!map.data[selectedTowerX][selectedTowerY].tower.fullBuild){
					if(mouseX>=795 && mouseX<=895 && mouseY>=110 && mouseY<=140){//if mouse is over update button
						if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
							Map.data[selectedTowerX][selectedTowerY].tower.updateDamage();
							map.getStats();
							if(Map.data[selectedTowerX][selectedTowerY].tower.damageCost>Play.coins){
								oldTime = time;
								tooExpensive = true;
							}
						}
					} 
				} 	
				if(mouseX>=905 && mouseX<=935 && mouseY>=110 && mouseY<=140){
					if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
						towerInfo = false;
					}
				}
			}
		
			if(input.isKeyPressed(Input.KEY_D)){ //turn debug mode on/off
				if(debug){
					debug = false;
				} else{
					debug = true;
				}
			}
		
			if(input.isKeyPressed(Input.KEY_R)){//draw rects around blocks
				if(Block.rect){
					Block.rect = false;
				} else{
					Block.rect = true;
				}
			}
			
			if(input.isKeyDown(Input.KEY_LSHIFT)){
				Store.multipleBuild = true;
			} else{
				Store.multipleBuild = false;
			}
		} else{//same as if pause
			if(mouseX>=320 && mouseX<=375 && mouseY>=218 && mouseY<=230){
				if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					pause = false;
				}
			} 
			if(mouseX>= 320 && mouseX<=420 && mouseY>=247 && mouseY<=262){
				if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
//					playMusic.stop();
					Menu.menuMusic.loop();
					game.enterState(Value.MENU_ID);
				}
			}
		}
	}
	
	
	public void drawPauseMenu(Graphics g){
		g.setColor(new Color(0,0,0));
		g.fillRect(Game.screenWidth/2 - Game.screenWidth/4, Game.screenHeight/2 - Game.screenHeight/4, Game.screenWidth/2, Game.screenHeight/2);
		g.setColor(new Color(256,256,256));
		g.drawString("Pause Menu", Game.screenWidth/4 + Game.screenWidth/5, Game.screenHeight/4 + 30);
		g.drawString("Resume", Game.screenWidth/4 + Game.screenWidth/12, Game.screenHeight/4 + 80);
		g.drawString("Quit to menu", Game.screenWidth/4 + Game.screenWidth/12, Game.screenHeight/4 + 110);
	}

	public int getID() {
		return Value.PLAY_ID;
	}
	
}
