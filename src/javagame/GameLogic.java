package javagame;


import java.io.File;

import org.lwjgl.util.Timer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

public class GameLogic {// Stefan und Jan-Niklas
	
	public static Save save;
	
	public boolean first = true;
	public static boolean loadCustom = false;
	
	public int time;
	int targetWave =0;
	public int spawnedMobs = 0;
	public static int killCount = 0;
	public static int kills = 0;
	private int mobCount = 10; //anzahl of mobs in one wave
	public static int wave = 1;
	
	public GameLogic() throws SlickException{
		save = new Save();
		
		save.loadSave("res/map1.map");//für export muss map1 in selbem pfad liegen wie jar datei und im unterordner res!!!
		
	}
	
	public static void loadMap(String mapPath){
		save.loadSave(mapPath);
	}
	
	public void logic(int delta){
		if (wave >= 0) {
			mobSpawner(delta);
			if (Play.lifes <= 0) {
				//lose
			}

			int id = -1;
			if (killCount >= mobCount) {
				if (wave % 2 == 0) {
					id = Value.LOW_MOB;
				} else {
					id = Value.MOB;
				}
				for (int i = 0; i < Play.mobs.length; i++) {
					Play.mobs[i] = new Mob(0, Play.map.data[0][Play.map.getFirstRoad()].getY(), Map.blockSize / 2, 100 * wave, id);
					time = 0;
					killCount = 0;
					first = false;
					spawnedMobs = 0;
				}
				wave += 1;
			}
		} //else if(targetWave == 0){//if maxWave is reached, save points in db
//			System.out.println("gewonnen");
//			System.out.println("jdbc anbindnung noch machen");
//			Menu.jdbc.verbindungsaufbau();
//			Menu.jdbc.auslesen("login", 6);
//			targetWave++;
//		}
	}
	
	public void mobSpawner(int delta){
		this.time += delta;
		if(spawnedMobs <= mobCount){
			if (first) {
				if (time / 1000 >= 5) {
					time = 0;
					Play.mobs[spawnedMobs].inGame = true;
					spawnedMobs += 1;
					first = false;
				}
			} else {
				if ((time / 1000) >= 3 && spawnedMobs < Play.mobs.length) {
					time = 0;
					Play.mobs[spawnedMobs].inGame = true;
					spawnedMobs += 1;
				}
			}
		}
	}
}
