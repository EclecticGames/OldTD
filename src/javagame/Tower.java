package javagame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.util.Log;

import java.math.*;
import java.awt.Point;
import java.awt.geom.*;

public class Tower {//Stefan und Jan-Niklas

	public int blockX, blockY;
	public int towerID;
	public int range = 100;
	public int centerX, centerY;
	public int shotMob = -1;
	public int towerLevel = 1;
	public int maxLevel = 3;
	public double shootDelay, shootTime = 1.5;//änderbar
	public double damage, damageCost = 20;
	
	public String towerName = "";
	private String originalName = "";
	
	public Circle shootRange;
	
	public boolean isShooting = false;
	public boolean fullBuild = false;
	
	Point towerPoint;
	
	public Tower(int towerID, int blockX, int blockY, double damage, String towerName){
		this.towerID = towerID;
		this.towerName = towerName;
		this.originalName = towerName;
		this.blockX = blockX;
		this.blockY = blockY;
		shootRange = new Circle(blockX+Map.blockSize/2, blockY+Map.blockSize/2, range);
		centerX = blockX*Map.blockSize+168+Map.blockSize/2;
		centerY = blockY*Map.blockSize+Map.blockSize/2;
		towerPoint = new Point(centerX, centerY);
		this.damage = damage;
	}

	public void shoot(int delta) {//shootDelay eingebaut!!!!
		shootDelay += delta;
		shotMob = findShootMob();
		if(shotMob!=-1 && shootDelay/1000 >= shootTime){
			Play.mobs[shotMob].loseHealth(damage);
			shootDelay =0;
			isShooting = true;
		}else{
			isShooting = false;
		}
	}

	private int findShootMob() {
		int mobShooting = -1;
		for(int i=0;i<Play.mobs.length;i++){
			Point mobPoint = new Point((int)Play.mobs[i].getCenterX(), (int)Play.mobs[i].getCenterY());
			if(Play.mobs[i].inGame){
				if(mobPoint.distance(towerPoint)-shootRange.radius-Play.mobs[i].radius<=0){
					mobShooting = i;
					return mobShooting;
				}
			}
		}
		return mobShooting;
	}
	
	public void updateDamage(){//rename to update stats
		if(Play.coins >= damageCost && maxLevel > towerLevel) {
			damage = damage * 2;
			Play.coins -= damageCost;
			Log.info(" tower damage updated");
			damageCost = damageCost * 2;
			range += 5;
			shootRange = new Circle(blockX+Map.blockSize/2, blockY+Map.blockSize/2, range);
			towerLevel += 1;
			shootTime -= shootTime*0.1;
			towerName = originalName+" Lv: "+towerLevel;
			if(towerLevel == maxLevel){
				Log.info("MaxLevele reached");
				fullBuild = true;
			}
		} else{
			Log.info(" not enough money. "+(damageCost-Play.coins)+" needed");
		}
	}

}
