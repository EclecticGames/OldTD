package javagame;

import java.io.File;
import java.util.Scanner;

import org.newdawn.slick.util.Log;

public class Save {//Stefan
	
	public boolean first = true;
	
	int a =0;
	
	public void loadSave(String loadPath) {
	Map.roadLength = 0;
	
		try {
//			File file = new File(loadPath);
//			Log.info(file.getCanonicalPath());
			Log.info("map loading");
			Scanner loadScanner = new Scanner(new File(loadPath));
			
			while(loadScanner.hasNext()){		//&&first
				
				for (int y = 0; y < Map.mapHeight; y++) {//groundID
					for (int x = 0; x < Map.mapWidth; x++) {
						a = loadScanner.nextInt();
						Map.data[x][y].groundID = a;
						if(a == Value.GROUND_ROAD){
							Map.roadLength +=1;
						}
					}
				}
				
				for (int y = 0; y < Map.mapHeight; y++) {//airID
					for (int x = 0; x < Map.mapWidth; x++) {
						Map.data[x][y].airID = loadScanner.nextInt();
					}
				}
				first=false;
			}
			
			loadScanner.close();
			Log.info("map loaded");
		} catch (Exception e) {
			Log.error("error while loading map:  "+e.toString());
		}
	}
}
