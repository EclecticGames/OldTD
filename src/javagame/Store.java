package javagame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.util.Log;

public class Store {//Stefan
	
	public static int[] buttonID = {Value.TOWER_LASER, Value.TOWER_GREEN, -1, -1, -1, -1, -1, Value.AIR_TRASH};
	public static int[]	buttonPrice = {10,20,0,0,0,0,0,0};
	public static double[] towerStats = {Value.LASER_DMG, Value.GREEN_DMG, 0, 0, 0, 0, 0, -1};
	public static String[] towerNames ={"Greeny", "Other", "", "", "", "", "", null};
	public int mouseX;
	public int mouseY;
	public int newPicX;
	public int newPicY;
	public int oldPicX = 3;
	public int oldPicY = 7;
	public int heldID = -1;
	public int itemCount = -1;
	
	public static boolean holdsItem = false;
	public static boolean multipleBuild = false;
	
	public void draw(Graphics g) throws SlickException{
		for(int i=0; i<Play.pic.towerButton.length; i++){//drawing the buttons
			Play.pic.towerButton[i].draw((Play.pic.towerButton[0].getWidth()+10)*i+Play.map.data[0][0].getX()+ Play.space*3, Play.map.data[0][Map.mapHeight-1].getY()+Play.map.blockSize+Play.space);
		}
		
		//coins, life
		g.drawImage(Play.pic.res[Value.HEART], Play.map.data[0][0].getX()-Play.space, Play.map.data[0][Map.mapHeight-1].getY()+Play.map.blockSize+Play.space-5);
		g.drawImage(Play.pic.res[Value.COINS],Play.map.data[0][0].getX()-Play.space, Play.map.data[0][Map.mapHeight-1].getY()+Play.map.blockSize+Play.space+Play.pic.res[Value.HEART].getHeight()+Play.space-5);
		g.drawString(" "+Play.lifes, Play.map.data[0][0].getX()+Play.pic.res[Value.HEART].getWidth()-Play.space,  Play.map.data[0][Map.mapHeight-1].getY()+Play.map.blockSize+Play.space-5);
		g.drawString(" "+Play.coins, Play.map.data[0][0].getX()+Play.pic.res[Value.COINS].getWidth()-Play.space, Play.map.data[0][Map.mapHeight-1].getY()+Play.map.blockSize+Play.space+Play.pic.res[Value.COINS].getHeight()+Play.space-5);
		
		for(int i=0; i<Play.pic.towerButton.length; i++){//drawing the tower on the buttons and the price
			if(buttonID[i] != -1){
				Picture.resAir[buttonID[i]].draw(((Picture.towerButton[0].getWidth()+10)*i+Play.map.data[0][0].getX()+ Play.space*3)+Play.space/4+2 , Play.map.data[0][Map.mapHeight-1].getY()+Play.map.blockSize+Play.space+(Play.space/3)*new Float(1.5), new Float(0.7));
			}
			if(buttonPrice[i] != 0){
				g.drawString("$"+buttonPrice[i],((Picture.towerButton[0].getWidth()+10)*i+Play.map.data[0][0].getX()+ Play.space*3)+Play.space/4+2 , Play.map.data[0][Map.mapHeight-1].getY()+Play.map.blockSize+Play.space+Play.space/5);
			}
			
		}
		
		if(holdsItem){
			newPicX = (mouseX-168)/52;
			newPicY = mouseY/52;
			if(newPicY <= Play.map.mapHeight-1 && newPicX <= Play.map.mapWidth-1 && newPicX>=0 && mouseX >=168){
				if(Play.map.data[newPicX][newPicY].groundID != Value.GROUND_ROAD){
					Play.pic.resAir[heldID].draw(Play.map.data[newPicX][newPicY].getX(), Play.map.data[newPicX][newPicY].getY());
					oldPicY = newPicY;
					oldPicX = newPicX;
				}else{
					Play.pic.resAir[heldID].draw(Play.map.data[oldPicX][oldPicY].getX(), Play.map.data[oldPicX][oldPicY].getY());//draw at the old position, not on the road
				}
			}else{
				Play.pic.resAir[heldID].draw(mouseX-Play.pic.resAir[heldID].getWidth()/2, mouseY-Play.pic.resAir[heldID].getHeight()/2);
			}
		}
	}
	
	public void physic(int mouseX, int mouseY, Input input){
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		
		for(int i=0; i<Play.pic.towerButton.length;i++){
			if((mouseX>=((Play.pic.towerButton[0].getWidth()+10)*i+Play.map.data[0][0].getX()+ Play.space*3) && mouseX<=(((Play.pic.towerButton[0].getWidth()+10)*i+Play.map.data[0][0].getX()+ Play.space*3)+Play.pic.towerButton[i].getWidth()) ) && mouseY>=( Play.map.data[0][Map.mapHeight-1].getY()+Play.map.blockSize+Play.space) && mouseY<=(( Play.map.data[0][Map.mapHeight-1].getY()+Play.map.blockSize+Play.space)+Play.pic.towerButton[i].getHeight())){
				if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
					if(i != Play.pic.towerButton.length-1 && buttonID[i] != -1){
						itemCount = i;
						if (Play.coins - buttonPrice[itemCount] >= 0) {//to check if price is bigger than money
							holdsItem = true;
							heldID = buttonID[i];
						}else{
							holdsItem = false;
							heldID = -1;
						}
					}
					else{
						holdsItem = false;
						heldID = -1;
					}
				}
			}
		}
		if(holdsItem ){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				if(newPicY <= Play.map.mapHeight-1 && newPicX <= Play.map.mapWidth-1 && newPicX>=0){
					if(Play.map.data[newPicX][newPicY].groundID != Value.GROUND_ROAD){
							if(Play.coins >= buttonPrice[itemCount] && Play.map.data[newPicX][newPicY].tower==null){
								Play.map.data[newPicX][newPicY].tower = new Tower(heldID, newPicX, newPicY, towerStats[itemCount], towerNames[itemCount]);//placing tower
								Play.coins -= buttonPrice[itemCount];
							}
							if(!multipleBuild){
								holdsItem = false;								
							}
					}else{
						Log.info("something wrong while placing a tower");
					}
				}
			} else if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
				holdsItem = false;
			}
		}
	}
}
