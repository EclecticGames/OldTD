package javagame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Picture {//Stefan 

	public static Image[] towerButton;
	public static Image[] resGround;
	public static Image[] resAir;
	public static Image[] res;
	
	
	public Picture() throws SlickException{
		towerButton = new Image[8];
		resGround = new Image[15];
		resAir = new Image[15];
		res = new Image[15];
		
		for (int i = 0; i < towerButton.length; i++) {
			towerButton[i] = new Image("res/cell.png");
		}
		
		resGround[0] = new Image("res/grass.png");
		resGround[1] = new Image("res/road.png");
		
		resAir[0] = new Image("res/cave.png");
		resAir[1] = new Image("res/laserTower.png");
		resAir[2] = new Image("res/trash.png");
		resAir[3] = new Image("res/greenTower.png");
		
		res[0] = new Image("res/heart.png");
		res[1] = new Image("res/coin.png");
		res[2] = new Image("res/mob.png");
		res[3] = new Image("res/low_mob.png");
	}
}
