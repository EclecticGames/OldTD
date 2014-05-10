package javagame;

public class LowMob extends Mob {//Stefan

	private LowMob(float centerPointX, float centerPointY, float radius, double health, int mobID) {
		super(centerPointX, centerPointY, radius, health, mobID);
		
	}

	public LowMob(float centerPointX, float centerPointY){
		this(centerPointX, centerPointY, Map.blockSize/2, 50, Value.LOW_MOB);
	}
	
}
