package engine;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import components.Component;
import components.ComponentID;
import entity.Entity;
import entity.EntityID;

public class Physics {
	
	private static final int CELL_SIZE = 80;
	
	private HashMap<Integer, ArrayList<Component>> collisionMap;
	
	private int rows;
	private int cols;
	
	public Physics() {
		this.collisionMap = new HashMap<Integer, ArrayList<Component>>();
		this.rows = Main.HEIGHT/CELL_SIZE;
		this.cols = Main.WIDTH/CELL_SIZE;
	}
	
	public void update() {
		//detectCollisions();
	}
	
	/*public void detectCollisions() {
		initMap();
		populateCollisionMap();
		
		for(int i=0;i<collisionMap.size();i++) {
			ArrayList<Component> cs = collisionMap.get(i);
			for(int j = 0;j<cs.size();j++) { //goes through all keys of hash map
				for(int k = j;k<cs.size();k++) {
					Entity e1 = cs.get(j).getAttachedTo();
					Entity e2 = cs.get(k).getAttachedTo();
					
					if(e1.id == EntityID.player) {
						Collision data = e1.collisionComponent.getAABB().getCollision(e2.collisionComponent.getAABB());
						if(e2.id == EntityID.wall && data.isIntersecting) {
							e1.collisionComponent.getAABB().correctPosition(e1, data);
						}
					}
				}
			}
		}
	}*/
	
	private void populateCollisionMap() {
		// maps entities to cell number 
		ArrayList<Component> components = Game.updateComponents;
		for(int i=0;i<components.size();i++) {
			Component c = components.get(i);
			if(c.getId() != ComponentID.collision) continue;
			
			for(int j: getCellsForObj(c)) {
				ArrayList<Component> cs = collisionMap.get(j);
				cs.add(c);
				collisionMap.put(j, cs);
			}
		}
	}
	
	private ArrayList<Integer> getCellsForObj(Component c) {
		// finds which all cells the object is in
		ArrayList<Integer> cells = new ArrayList<Integer>();
		
		float pos_x = c.getAttachedTo().transform.pos.x;
		float pos_y = c.getAttachedTo().transform.pos.y;
		float sx = c.getAttachedTo().material.sprite.sx;
		float sy = c.getAttachedTo().material.sprite.sy;
		
		//int topRight = getCell(pos_x+(sx/2), pos_y+(sy/2));
		int topLeft = getCell(pos_x-(sx/2), pos_y+(sy/2));
		int bottomRight = getCell(pos_x+(sx/2), pos_y-(sy/2));
		int bottomLeft = getCell(pos_x-(sx/2), pos_y-(sy/2));
		
		for(int i=0;i<=topLeft-bottomLeft;i+=cols) {
			for(int j=bottomLeft+i;j<=bottomRight+i;j++)
				cells.add(j);
		}
		
		return cells;
	}
	
	private int getCell(float x, float y) {
		// converts coordinates to cell number
		return (int) (Math.floor(x/CELL_SIZE) + Math.floor(y/CELL_SIZE) + (Math.floor(x/CELL_SIZE) * 2));
	}

	private void initMap() {
		//clears the hashMap 
		for(int i=0;i<rows*cols;i++)
			collisionMap.put(i, new ArrayList<Component>());
	}
	
	public boolean isColliding(Rectangle b1, Rectangle b2){
		return b1.intersects(b2);
	}
	
	
}
