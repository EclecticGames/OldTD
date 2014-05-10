package javagame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

public class MapEditor extends BasicGameState{//Stefan und Jan-Niklas
	
	public Image saveButton;
	
	public BufferedWriter writer = null;
	public Writer out;
	
	public int drawID = -1;
	private int right = 0, left = 1, up = 2, down = 3;
	private int walkDir = -1;
	private int time = 0;
	private int oldTime = 0;
	
	String tobewritten = "a ";
	
	private boolean drawGround = false;
	public boolean pause = false;
	private boolean informUserAboutSave = false;
	
	public MapEditor(int state){
		
	}

	public void init(GameContainer container, StateBasedGame game) throws SlickException {
//		try {
//			writer = new BufferedWriter(new FileWriter(new File("res/map2.map")));
////			for(int x=0;x<newMap.map.length;x++){
////				for(int y=0;y<newMap.map[0].length;y++){
//					writer.newLine();
//					writer.write(tobewritten);
////				}
////			}
//			
//		} catch (IOException e) {
//			Log.error("FileWriter: "+e.toString());
//		}
//		
		saveButton = new Image("res/saveButton.png");
	}
	
	public void createNewFile(String title){
		try {
			writer = new BufferedWriter(new FileWriter(new File("res/"+title+".map")));
				for(int y=0;y<Map.data[0].length;y++){
					writer.write("0  0  0  0  0  0  0  0  0  0  0  0");
					writer.newLine();
					writer.flush();
				}
				
				writer.newLine();
				
				for(int y=0;y<Map.data[0].length;y++){
					writer.write("-1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1");
					writer.newLine();
					writer.flush();
				}
			
		} catch (IOException e) {
			Log.error("FileWriter: "+e.toString());
		}
		
	}
	

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		Play.map.drawMap(g);
		
		Picture.resGround[Value.GROUND_GRASS].draw(Game.screenWidth/2+(Map.mapWidth*Map.blockSize)/2 + Map.blockSize , 20);
		Picture.resGround[Value.GROUND_ROAD].draw(Game.screenWidth/2+(Map.mapWidth*Map.blockSize)/2 + Map.blockSize, 20+Map.blockSize+Play.space);
		
		Color oldColor = g.getColor();
		g.setColor(new Color(179,179,179));
		g.fillRect(Game.screenWidth/2+(Map.mapWidth*Map.blockSize)/2 + Map.blockSize, 20+Map.blockSize*2+Play.space*2, Map.blockSize, Map.blockSize);
		g.setColor(oldColor);
		Picture.resAir[Value.AIR_CAVE].draw(Game.screenWidth/2+(Map.mapWidth*Map.blockSize)/2 + Map.blockSize, 20+Map.blockSize*2+Play.space*2);
		
		saveButton.draw(Game.screenWidth/2+(Map.mapWidth*Map.blockSize)/2 + Map.blockSize ,  20+Map.blockSize*3+Play.space*3, new Float(0.5));
//		saveButton.draw(Game.screenWidth/2+(Map.mapWidth*Map.blockSize)/2 + Map.blockSize ,  20+Map.blockSize*3+Play.space*3);
		
		if(pause){
			g.setColor(new Color(105,105,105,110));
			g.fillRect(-1, -1, Game.screenWidth+1, Game.screenHeight+1);
			drawPauseMenu(g);
			g.setColor(oldColor);
		}
		if(informUserAboutSave){
			if(!(time/1000 - oldTime/1000 >= 2)){
				g.drawString("The map has been saved. If you did correctly, you can play it through the main menu.", 70, 200);
			}
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		
		time = time + delta;
		
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		if(input.isKeyPressed(Input.KEY_ESCAPE)){//pause or unpause
			if(pause){
				pause = false;
			} else{
				pause = true;
			}
		}
		
		//on map with mouse
		if(mouseX>=168 && mouseX<=168+Map.mapWidth*Map.blockSize && mouseY<=Map.blockSize*Map.mapHeight){
			
			int Cx = (mouseX-168)/Map.blockSize;
			int Cy = mouseY/Map.blockSize;
			
			if(drawGround){
				if(drawID != -1 && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					Play.map.data[Cx][Cy].groundID = drawID;
				}
			}else{
				if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
					if (Play.map.data[Cx][Cy].airID == drawID) {
						Play.map.data[Cx][Cy].airID = -1;
					} else {
						Play.map.data[Cx][Cy].airID = drawID;
					}
				}
			}
		}
		
		//grass
		if(mouseX>=(Game.screenWidth/2+(Map.mapWidth*Map.blockSize)/2 + Map.blockSize) && mouseX<=(Game.screenWidth/2+(Map.mapWidth*Map.blockSize)/2 + Map.blockSize )+Map.blockSize && mouseY>=20 && mouseY<=20+Map.blockSize){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				drawID = Value.GROUND_GRASS;
				drawGround = true;
			}
		}
		
		//road
		if(mouseX>=(Game.screenWidth/2+(Map.mapWidth*Map.blockSize)/2 + Map.blockSize) && mouseX<=(Game.screenWidth/2+(Map.mapWidth*Map.blockSize)/2 + Map.blockSize )+Map.blockSize && mouseY>=20+Map.blockSize+Play.space && mouseY<=20+Map.blockSize*2+Play.space){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				drawID = Value.GROUND_ROAD;
				drawGround = true;
			}
		}
		
		//save map
		if(mouseX>=(Game.screenWidth/2+(Map.mapWidth*Map.blockSize)/2 + Map.blockSize) && mouseX<=(Game.screenWidth/2+(Map.mapWidth*Map.blockSize)/2 + Map.blockSize )+Map.blockSize && mouseY>=20+Map.blockSize*2+Play.space*2 && mouseY<=20+Map.blockSize*3+Play.space*2){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				drawID = Value.AIR_CAVE;
				drawGround = false;
			}
		}
		
		if(mouseX>=844 && mouseX<=867 && mouseY>=237 && mouseY<=260){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				saveCustomMap();
				Log.info("saving map");
				informUserAboutSave = true;
				oldTime = time;
			}
		}
		
		if(pause){
			if (mouseX >= 320 && mouseX <= 375 && mouseY >= 218
					&& mouseY <= 230) {
				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					pause = false;
				}
			}
			if (mouseX >= 320 && mouseX <= 420 && mouseY >= 247
					&& mouseY <= 262) {
				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					pause = false;
					game.enterState(Value.MENU_ID);
				}
			}
		}
	}

	public int getID() {
		return Value.EDITOR_ID;
	}
	
	public void saveCustomMap(){
		try{
			writer = new BufferedWriter(new FileWriter(new File("res/map2.map")));
			
			for(int y=0;y<Play.map.data[0].length;y++){
				for(int x=0;x<Play.map.data.length;x++){
					writer.write(""+Play.map.data[x][y].groundID+"  ");
					writer.flush();
				}
			writer.newLine();
			writer.flush();
			}
			
			writer.newLine();
			
			for(int y=0;y<Play.map.data[0].length;y++){
				for(int x=0;x<Play.map.data.length;x++){
					writer.write(""+Play.map.data[x][y].airID+" ");
					writer.flush();
				}
			writer.newLine();
			writer.flush();
			}
			
		} catch(Exception e){
			Log.error("Saving Map: "+e.toString());
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
	
//	public void checkPath(int xCoordinate, int yCoordinate){
//		
//		int counter = 0;
//		//check first row	
//		if(xCoordinate==Map.mapWidth-1){
//			Log.info("everything ok");
//		}
//		if(xCoordinate==0){
//			for(int i=0;i<Play.map.data[xCoordinate].length;i++){
//				if(Play.map.data[xCoordinate][i].groundID==1){
//					counter +=1;
//					yCoordinate = i;
//				}
//			}
//			if(counter==1){
//				walkDir = right;
//				checkPath(xCoordinate+1,yCoordinate);
//			} else{
//				Log.info("first row");
//			}
//		} else if(Play.map.data[xCoordinate][yCoordinate].groundID!=1){
//			Log.error("Fault in row x: "+xCoordinate+" ; y: "+yCoordinate);
//		} else if(xCoordinate<Map.mapWidth && !stop){
//			if(walkDir==right){//if the last road was on the left
//				if(Play.map.data[xCoordinate][yCoordinate+1].groundID==1){//up
//					counter +=1;
//					walkDir = up;
//				}
//				if(Play.map.data[xCoordinate][yCoordinate-1].groundID==1){//down
//					counter +=1;
//					walkDir = down;
//				}
//				if (Play.map.data[xCoordinate + 1][yCoordinate].groundID == 1) {// right
//					counter += 1;
//				}
//	
//				if(counter==1){
//					if(walkDir==up){
//						checkPath(xCoordinate,yCoordinate+1);
//					}
//					if(walkDir==right){
//						checkPath(xCoordinate+1,yCoordinate);
//					}
//					if(walkDir==down){
//						checkPath(xCoordinate,yCoordinate-1);
//					}
//				} else{
//					Log.info("Fault in row x: "+xCoordinate+" ; y: "+yCoordinate);
//				}
//			} else if(walkDir==up){//if the last road was below
//				if(Play.map.data[xCoordinate-1][yCoordinate].groundID==1){//left
//					counter +=1;
//					walkDir = left;
//				}
//				if(Play.map.data[xCoordinate][yCoordinate+1].groundID==1){//up
//					counter +=1;
//				}
//				if(Play.map.data[xCoordinate+1][yCoordinate].groundID==1){//right
//					counter +=1;
//					walkDir = right;
//				}
//				if(counter==1){
//					if(walkDir==up){
//						checkPath(xCoordinate,yCoordinate+1);
//					}
//					if(walkDir==right){
//						checkPath(xCoordinate+1,yCoordinate);
//					}
//					if(walkDir==left){
//						checkPath(xCoordinate-1,yCoordinate);
//					}
//				} else{
//					Log.info("Fault in row x: "+xCoordinate+" ; y: "+yCoordinate);
//				}
//			} else if(walkDir==down){//if the last was upon
//				if(Play.map.data[xCoordinate-1][yCoordinate].groundID==1){//left
//					counter +=1;
//					walkDir = left;
//				}
//				if(Play.map.data[xCoordinate][yCoordinate-1].groundID==1){//down
//					counter +=1;
//				}
//				if(Play.map.data[xCoordinate+1][yCoordinate].groundID==1){//right
//					counter +=1;
//					walkDir = right;
//				}
//				if(counter==1){
//					if(walkDir==left){
//						checkPath(xCoordinate-1,yCoordinate);
//					}
//					if(walkDir==right){
//						checkPath(xCoordinate+1,yCoordinate);
//					}
//					if(walkDir==down){
//						checkPath(xCoordinate,yCoordinate-1);
//					}
//				} else{
//					Log.info("Fault in row x: "+xCoordinate+" ; y: "+yCoordinate);
//				}
//			} else if(walkDir==left){//if the last was right
//				if(Play.map.data[xCoordinate-1][yCoordinate].groundID==1){//left
//					counter +=1;
//				}
//				if(Play.map.data[xCoordinate][yCoordinate-1].groundID==1){//down
//					counter +=1;
//					walkDir = down;
//				}
//				if(Play.map.data[xCoordinate][yCoordinate+1].groundID==1){//up
//					counter +=1;
//					walkDir = up;
//				}
//				if(counter==1){
//					if(walkDir==up){
//						checkPath(xCoordinate,yCoordinate+1);
//					}
//					if(walkDir==left){
//						checkPath(xCoordinate-1,yCoordinate);
//					}
//					if(walkDir==down){
//						checkPath(xCoordinate,yCoordinate-1);
//					}
//				} else{
//					Log.info("Fault in row x: "+xCoordinate+" ; y: "+yCoordinate);
//				}
//			}
//		}
//	}
//
//	//evt weiterarbeiten
//	public void checkPath2(int xCoordinate, int yCoordinate){
//		
//		int counter = 0;
//		
//		if(xCoordinate==0){
//			for(int i=0;i<Play.map.data[xCoordinate].length;i++){
//				if(Play.map.data[xCoordinate][i].groundID==1){
//					counter +=1;
//					yCoordinate = i;
//				}
//			}
//			if(counter==1){
//				walkDir = right;
//				checkPath(xCoordinate+1,yCoordinate);
//			} else{
//				Log.info("first row");
//			}
//		} else{
//			for(int i=0;i<Map.mapHeight;i++){
//				if(Play.map.data[xCoordinate][i].groundID==1){
//					counter +=1;
//				}
//				if(counter==1){
//					checkPath(xCoordinate, yCoordinate);
//				}
//			}
//		}
//	}
}
