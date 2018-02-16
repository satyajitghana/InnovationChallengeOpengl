package callback;

import org.joml.Vector2f;
import org.lwjgl.input.Mouse;

import components.Component;
import components.ComponentID;
import engine.Collision;
import engine.Game;
import engine.Main;
import entity.Entity;
import entity.EntityID;

public class Callbacks {
	
	
	public Callback inWindow() {
		 return (Object[] data, Object... extra)-> {
			
			int dir = (int)extra[0];
			int offset = (int) extra[1];
				
			Entity player = (Entity)data[0];
			
			switch(dir) {
			case 0:
				player.transform.pos.x = offset;
				break;
			case 1:
				player.transform.pos.x = Main.WIDTH-offset;
				break;
			case 2:
				player.transform.pos.y = offset;
				break;
			case 3:
				player.transform.pos.y = Main.HEIGHT-offset;
			}
			
		};
	}
	
	public Callback windowExitRemove() {
		return (Object[] data, Object... extra)->{
			((Entity)data[0]).destroy();
		};
	}
	
	public Callback mouseFollowRotation() {
		return (Object[] data, Object... extra)-> {
			
			Entity player = (Entity)data[0];
			
			Vector2f pos = player.transform.pos;
			Vector2f mouse_pos = new Vector2f(Mouse.getX(), Mouse.getY());	
			
			float alpha = (float) Math.toDegrees(Math.atan2(mouse_pos.y - pos.y, mouse_pos.x - pos.x));
			player.transform.rotate(alpha - 90);
		};
	}
	
	public Callback propel() {
		return (Object[] data, Object... extra)->{
			Entity bullet = (Entity) data[0];
			Vector2f dir = (Vector2f) data[1];
			
			bullet.transform.move(dir.x, dir.y);
		};
	}
	
	public Callback playerCollision() {
		return (Object[] data, Object... extra)-> {
			Entity player = (Entity) data[0];
			for(int i=0;i<Game.updateComponents.size();i++) {
				Entity e = Game.updateComponents.get(i).getAttachedTo();
				if(e.collisionComponent.getId() == null) continue;
				
				if(e.id == EntityID.wall) {
					Collision c = player.collisionComponent.getAABB().getCollision(e.collisionComponent.getAABB());
					if(c.isIntersecting) {
						player.collisionComponent.getAABB().correctPosition(e.collisionComponent.getAABB(), c);
					}
				}
				
			}
		};
	}
	
	public Callback bulletCollision() {
		return (Object[] data, Object... extra)-> {
			Entity bullet = (Entity) data[0];
			for(int i=0;i<Game.updateComponents.size();i++) {
				Entity e = Game.updateComponents.get(i).getAttachedTo();
				if(e.collisionComponent.getId() == null) continue;
				
				if(e.id == EntityID.wall) {
					Collision c = bullet.collisionComponent.getAABB().getCollision(e.collisionComponent.getAABB());
					if(c.isIntersecting) {
						bullet.destroy();
					}
				}
				
			}
		};
	}
}
