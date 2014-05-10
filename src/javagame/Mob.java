package javagame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class Mob extends Circle{//Stefan
	
	public double x, y;
	public double walkSpeed = 0.02;//0.001=1 pps (pixel per second)
	public double mobWalk = 0;
	public double health, healthPercent = 1.0, maxHealth;
	public int healthSpace = 3, healthHeight = 6;
	
	public int xC, yC;
	public int upward = 0, downward = 1, right = 2, left = 3;
	public int direction = right;
	public int mobID;
	
	public boolean inGame = false;
	public boolean hasUpward = false;
	public boolean hasDownward = false;
	public boolean hasRight = false;
	public boolean hasLeft = false;

	public Mob(float centerPointX, float centerPointY, float radius, double health, int mobID) {
		super(centerPointX, centerPointY, radius);
		this.x = centerPointX+168;
		this.y = centerPointY;
		this.setX((float)x);
		this.setY((float)y);
		this.health = health;
		this.mobID = mobID;
		maxHealth = health;
		xC=0;
		yC = Play.map.getFirstRoad();
	}
	
	public void spawnMob(){
		inGame = true;
	}

	public void draw(Graphics g) throws SlickException{
		g.drawImage(Play.pic.res[mobID], (int)x, (int)y, null);
//		g.draw(this); //for testing the size of the mobs
		
		Color oldColor = g.getColor();
		
		if(healthPercent != 1){//draw healthbar
			g.setColor(new Color(180,50,50)); //red part underneith green part
			g.fillRect((float)x,(float)y-(healthSpace+healthHeight),(float)Map.blockSize,(float)healthHeight);
		
			g.setColor(new Color(50,180,50));//green part
			g.fillRect((float)x,(float)y-(healthSpace+healthHeight),(float)(Map.blockSize*healthPercent),healthHeight);
			
			g.setColor(new Color(0,0,0));//rectangle around healthbar, green part
			g.drawRect((float)x,(float)y- (healthSpace + healthHeight), (float)(Map.blockSize*healthPercent) - 1, healthHeight - 1);
		
//			g.setColor(new Color(0,0,0));//rectangle around healthbar, red part
//			g.drawRect((float)x,(float)y- (healthSpace + healthHeight), (float)Map.blockSize - 1, healthHeight - 1);
		}
		
		g.setColor(oldColor);
	}

	public void walk(int delta) {
		if(inGame){
			if (direction == right) {
				x += delta * walkSpeed;
				this.setX((float)x);
			} else if (direction == upward) {
				y -= delta * walkSpeed;
				this.setY((float)y);
			} else if (direction == downward) {
				y += delta * walkSpeed;
				this.setY((float)y);
			}else if (direction == left) {
				x -= delta * walkSpeed;
				this.setX((float)x);
			}
			
			mobWalk += delta * walkSpeed;

			if ((int)mobWalk >= Map.blockSize) {
				if (direction == right) {
					xC += 1;
					hasRight = true;
				} else if (direction == upward) {
					yC -= 1;
					hasUpward = true;
				} else if (direction == downward) {
					yC += 1;
					hasDownward = true;
				}else if (direction == left) {
					xC -= 1;
					hasLeft = true;
				}

				if(!hasUpward){
				try {
					if (Play.map.data[xC][yC + 1].groundID == Value.GROUND_ROAD) {
						direction = downward;
						y = yC*52;
						x = xC*52+168;
					}
				} catch (Exception e) {}
				}
				
				if (!hasDownward) {
					try {
						if (Play.map.data[xC][yC - 1].groundID == Value.GROUND_ROAD) {
							direction = upward;
							y = yC*52;
							x = xC*52+168;
						}
					} catch (Exception e) {}
				}

				if (!hasLeft) {
					try {
						if (Play.map.data[xC+1][yC].groundID == Value.GROUND_ROAD) {
							direction = right;
							y = yC*52;
							x = xC*52+168;
						}
					} catch (Exception e) {}
				}
				
				if (!hasRight) {
					try {
						if (Play.map.data[xC-1][yC].groundID == Value.GROUND_ROAD) {
							direction = left;
							y = yC*52;
							x = xC*52+168;
						}
					} catch (Exception e) {}
				}
				
				if(Play.map.data[xC][yC].airID == Value.AIR_CAVE){//if at the end, mobs disappear
					inGame = false;
					Play.lifes -= 1;
					GameLogic.killCount += 1;
				}

				hasUpward = false;
				hasDownward = false;
				hasLeft = false;
				hasRight = false;
				mobWalk = 0;
			}
		} 
	}

	public void loseHealth(double damage) {
		health -= damage;
		healthPercent = health/maxHealth;
		if(health<=0){
			inGame = false;
			Play.coins += 10;
			GameLogic.kills += 1;
			GameLogic.killCount += 1;
			Play.points += maxHealth/10;
		}
	}
}
	

