package engine;

import java.awt.Rectangle;

import components.ComponentID;
import entity.Entity;
import entity.EntityID;

public class BrutePhysics {
	
	public void update() {
		detectCollisions();
	}
	
	private void detectCollisions() {
		for(int i = 0;i<Game.updateComponents.size();i++) {
			if(Game.updateComponents.get(i).getId() != ComponentID.collision) continue;
			
			Entity e1 = Game.updateComponents.get(i).getAttachedTo();
			for(int j=i;j<Game.updateComponents.size();j++) {
				if(Game.updateComponents.get(j).getId() != ComponentID.collision) continue;
				
				Entity e2 = Game.updateComponents.get(j).getAttachedTo();
				
				if(e1.id == EntityID.player) {
					if(e2.id == EntityID.wall) {
						e1.collisionComponent.getAABB().getCollision(e2.collisionComponent.getAABB());
					}
				}
			}
		}
	}
	
	public boolean isColliding(Rectangle b1, Rectangle b2){
		return b1.intersects(b2);
	}
}
