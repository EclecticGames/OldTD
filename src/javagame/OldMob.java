package javagame;
//not used
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class OldMob extends Rectangle{//Stefan
	
	public double x, y;
	public double walkSpeed = 0.02;//0.001=1 pps (pixel per second)
	public double mobWalk = 0;
	
	public int xC, yC;
	public int upward = 0, downward = 1, right = 2, left = 3;
	public int direction = right;
	
	
	public boolean inGame = false;
	public boolean hasUpward = false;
	public boolean hasDownward = false;
	public boolean hasRight = false;
	public boolean hasLeft = false;

	public OldMob(float x, float y, float width, float height) {
		super(x+168, y, width, height);
		this.x = x+168;
		this.y = y;
		xC=0;
		yC = Play.map.getFirstRoad();
	}
	 
	public void spawnMob(){
		inGame = true;
	}

	public void draw(Graphics g) throws SlickException{
		g.drawImage(Play.pic.res[Value.MOB], (int)x, (int)y, null);
	}

	public void walk(int delta) {
		if(inGame){
			if (direction == right) {
				x += delta * walkSpeed;
			} else if (direction == upward) {
				y -= delta * walkSpeed;
			} else if (direction == downward) {
				y += delta * walkSpeed;
			}else if (direction == left) {
				x -= delta * walkSpeed;
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
				}

				hasUpward = false;
				hasDownward = false;
				hasLeft = false;
				hasRight = false;
				mobWalk = 0;
			}

			
		} 
	}
}
	

