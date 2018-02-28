package engine;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;

import ai.Pair;
import components.Component;
import components.ComponentID;

public class EntitiesMap {
	
	private static final int CELL_SIZE = 20;
	
	private static HashMap<String, ArrayList<Component>> map;
	
	private static int rows;
	private static int cols;
	
	public EntitiesMap() {
		map = new HashMap<String, ArrayList<Component>>();
		rows = Main.HEIGHT/CELL_SIZE;
		cols = Main.WIDTH/CELL_SIZE;
		update();
	}
	
	public void update() {
		initMap();
		populateMap();
	}
	
	private void populateMap() {
		// maps entities to cell number 
			ArrayList<Component> components = Game.updateComponents;
			for(int i=0;i<components.size();i++) {
				Component c = components.get(i);
				if(c.getId() != ComponentID.collision) continue;
				
				for(String j: getCellsForObj(c)) {
					ArrayList<Component> cs = map.get(j);
					if(cs != null) {
						cs.add(c);
						map.put(j, cs);
					}	
				}
			}
	}
	
	private ArrayList<String> getCellsForObj(Component c) {
		ArrayList<String> cells = new ArrayList<String>();
		
		float pos_x = c.getAttachedTo().transform.pos.x;
		float pos_y = c.getAttachedTo().transform.pos.y;
		float sx = c.getAttachedTo().material.getSx();
		float sy = c.getAttachedTo().material.getSy();
		
		Vector2f topRight = getCellVec(pos_x+(sx/2), pos_y+(sy/2));
		Vector2f bottomLeft = getCellVec(pos_x-(sx/2), pos_y-(sy/2));
		
		for(float i = bottomLeft.x; i<=topRight.x;i++) {
			for(float j = bottomLeft.y; j<=topRight.y;j++) {
				cells.add(coordToString(i, j));
			}
		}
		
		return cells;
	}
	
	//AI FUNCTIONS
	////////////////////////////////////////////////////////////////////////
	public static boolean validateCell(int x, int y) {
		return (x>=0 && x<=rows) && (y>=0 && y<=cols);
	}
	
	public static boolean cellIsUnblocked(int x, int y) {
		return map.get(coordToString(x, y)).isEmpty();
	}
	
	public static Pair<Integer, Integer> getRowsAndCols(){
		return new Pair<Integer, Integer>(rows, cols);
	}
	////////////////////////////////////////////////////////////////////////
	
	public static ArrayList<Component> getEntitiesInCell(Vector2f pos){
		return map.get(getCellS(pos.x, pos.y));
	}
	
	public static String getCellS(float x, float y) {
		return Integer.toString((int)(x/CELL_SIZE))+","+Integer.toString((int)(y/CELL_SIZE));
	}
	
	public Vector2f getCellVec(float x, float y) {
		return new Vector2f((int)(x/CELL_SIZE), (int)(y/CELL_SIZE));
	}
	
	public static String coordToString(float x, float y) {
		return Integer.toString((int) x)+","+Integer.toString((int) y);
	}
	
	private void initMap() {
		//clears the hashMap 
		for(int i=0;i<=rows;i++) {
			for(int j=0;j<=cols;j++) {
				String s = Integer.toString(i)+","+Integer.toString(j);
				map.put(s, new ArrayList<Component>());
			}
		}
	}

}
