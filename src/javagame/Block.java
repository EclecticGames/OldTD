package javagame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

public class Block extends Rectangle { //Stefan

	public Tower tower;

	public static boolean rect = false;
	public boolean drawRange = false;

	public int groundID;
	public int airID;
	
	public Circle range;
//	public Rectangle range;

	public Block(float x, float y, float width, float height, int groundID,
			int airID) throws SlickException {
		super(x, y, width, height);
		this.groundID = groundID;
	}

	public void draw(Graphics g) {
		g.drawImage(Play.pic.resGround[groundID], x, y);
//		Play.pic.resGround[groundID].draw(x,y,new Float(2));//to scale pic

		if (airID != -1) {
			g.drawImage(Play.pic.resAir[airID], x, y);
		}

		if (tower != null) {
			g.drawImage(Play.pic.resAir[tower.towerID], x, y);
		}

		if (rect) {
			g.drawRect(x, y, height, height);
		}
		
	}
	
	public void drawOval(Graphics g){
		if (tower != null && drawRange) {
			range = new Circle(x + Map.blockSize / 2, y + Map.blockSize / 2, tower.range);
			g.draw(range);
			if (tower.isShooting) {
				Color oldColor = g.getColor();
				g.setColor(new Color(200,0,0));
				g.drawLine(tower.centerX, tower.centerY, Play.mobs[tower.shotMob].getCenterX(), Play.mobs[tower.shotMob].getCenterY());//laserTower shooting!
				g.setColor(oldColor);
			}
	} else if(tower != null){
			if (tower.isShooting) {
				Color oldColor = g.getColor();
				g.setColor(new Color(200,0,0));
				g.drawLine(tower.centerX, tower.centerY, Play.mobs[tower.shotMob].getCenterX(), Play.mobs[tower.shotMob].getCenterY());//laserTower shooting!
				g.setColor(oldColor);
			}
		}
	}
}
