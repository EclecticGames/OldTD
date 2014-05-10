package javagame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class Map implements TileBasedMap{//Stefan

	public static int mapHeight = 8;
	public static int mapWidth = 12;
	public static int blockSize = 52;
	public static int roadLength = -1;
	public static int Cx, Cy;
	
	public static Block[][] data;
	
//	public int[][] map = { //if not 0, the path is blocked
//			{1,1,1,1,1,1,1,1,0,1},
//			{1,1,1,1,1,1,1,1,0,1},
//			{1,1,1,1,1,1,1,1,0,1},
//			{1,1,1,1,1,1,1,1,0,1},
//			{1,1,1,1,1,1,1,1,0,1},
//			{1,1,1,1,1,1,1,1,0,1},
//			{1,1,1,1,1,1,1,1,0,1},
//			{1,1,1,1,1,1,1,1,0,1},
//			{1,1,1,1,1,1,1,1,0,1}
//	};
	
	public Map() throws SlickException{
		define();
	}
	
	public void define() throws SlickException{
		data = new Block[mapWidth][mapHeight];
		
		for(int x=0;x<data.length;x++){
			for(int y=0;y<data[0].length;y++){
				data[x][y] = new Block(x*blockSize+168, y*blockSize, blockSize, blockSize, 1, -1);
			}
		}
	}
	
	public int getWidthInTiles() {
		return mapWidth;
	}

	public int getHeightInTiles() {
		return mapHeight;
	}
	
	/**
	 * gives the y-coordinate of the first road in the map
	 * @return y-coordinate, if -1 no road available
	 */
	public int getFirstRoad(){
		int a = -1;
		for(int i=0;i<data[0].length;i++){
			if(data[0][i].groundID==1){
				a = i;
			}
		}
		return a;
	}

	public void pathFinderVisited(int x, int y) {}

	public boolean blocked(PathFindingContext context, int tx, int ty) {
		return data[tx][ty].groundID != 0;
	}

	public float getCost(PathFindingContext context, int tx, int ty) {
		return 0;
	}
	
	public void resetDrawRange(){
		for(int x=0;x<data.length;x++){
			for(int y=0;y<data[0].length;y++){
				data[x][y].drawRange = false;
			}
		}
	}
	
	public void physic(int delta, int mouseX, int mouseY){
		for(int x=0;x<Map.mapWidth;x++){
			for(int y=0;y<Map.mapHeight;y++){
				if(data[x][y].tower!=null){
					data[x][y].tower.shoot(delta);
				}
			}
		}
		Cx = (mouseX-168)/Map.blockSize;
		Cy = mouseY/Map.blockSize;
		
		//drawRange if mouse clicked on
		resetDrawRange();
		if(Play.selectedTowerX != -1 && Play.selectedTowerY != -1 && Play.towerInfo){
			data[Play.selectedTowerX][Play.selectedTowerY].drawRange = true;
		}

			//		if(Cy <= Map.mapHeight-1 && Cx <= Map.mapWidth-1 && Cx>=0 && mouseX >=168){
//			for(int x=0;x<Map.mapWidth;x++){
//				for(int y=0;y<Map.mapHeight;y++){
//					if(x==Cx && y==Cy){//maybe if(mouse button clicked)
//						if(map[x][y].tower!=null){
//							map[x][y].drawRange = true;
//							break;
//						}
//					}else {
//						map[x][y].drawRange = false;
//					}
//				}
//			}
//		}
	}
	
	public boolean mouseOverTower(int mouseX){
//		if(Cy <= Map.mapHeight-1 && Cx <= Map.mapWidth-1 && Cx>=0 && mouseX >=168){
//			if(map[Cx][Cy].drawRange){//if drawRange = true, than the mouse is over the tower
//				return true;
//			}
//		}
//		return false;
		
		//updated: not drawRange, but tower!=null
		if(Cy <= Map.mapHeight-1 && Cx <= Map.mapWidth-1 && Cx>=0 && mouseX >=168){
			if(data[Cx][Cy].tower != null){//if drawRange = true, than the mouse is over the tower
				return true;
			}
		}
		return false;
	}
	
	public void getStats(){//is just working when mouseovertower() because of mouseX...
		if(Cy <= Map.mapHeight-1 && Cx <= Map.mapWidth-1 && Cx>=0){
			Play.towerDamage = data[Cx][Cy].tower.damage;
			Play.towerName = data[Cx][Cy].tower.towerName;
			Play.towerRange = data[Cx][Cy].tower.range;
			Play.towerSpeed = data[Cx][Cy].tower.shootTime;
			Play.selectedTowerX = Cx;
			Play.selectedTowerY = Cy;
		} else{
			Play.towerDamage = data[Play.selectedTowerX][Play.selectedTowerY].tower.damage;
			Play.towerName = data[Play.selectedTowerX][Play.selectedTowerY].tower.towerName;
			Play.towerRange = data[Play.selectedTowerX][Play.selectedTowerY].tower.range;
			Play.towerSpeed = data[Play.selectedTowerX][Play.selectedTowerY].tower.shootTime;
		}
	}
	
	public void drawMap(Graphics g){
		for(int x=0;x<Map.mapWidth;x++){
			for(int y=0;y<Map.mapHeight;y++){
				data[x][y].draw(g);
			}
		}
		for(int x=0;x<Map.mapWidth;x++){//drawing the circles around the towers
			for(int y=0;y<Map.mapHeight;y++){
				data[x][y].drawOval(g);
			}
		}
	}

}
