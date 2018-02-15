package engine;

import org.joml.Vector2f;

import entity.Entity;

public class AABB {
	private Vector2f center;
	private Vector2f half_extent;
	
	public AABB(Entity entity, float sx, float sy) {
		this.center = entity.transform.pos;
		this.half_extent = new Vector2f(sx/2, sy/2);
	}

	public Collision getCollision(AABB bounds) {		
		Vector2f distance = center.sub(bounds.center, new Vector2f());
		distance.x = Math.abs(distance.x);
		distance.y = Math.abs(distance.y);
		
		distance.sub(half_extent.add(bounds.half_extent, new Vector2f()));
		
		return new Collision(distance, distance.x<=0 && distance.y<=0);
	}
	
	public void correctPosition(AABB bounds, Collision data) {
		Vector2f correction = bounds.center.sub(center, new Vector2f());
		
		if(data.distance.x > data.distance.y) {
			if(correction.x<0) {
				center.add(-data.distance.x, 0);
			}else if(correction.x>0) {
				center.add(data.distance.x, 0);
			}
		}else {
			if(correction.y<0) {
				center.add(0, -data.distance.y);
			}else if(correction.y>0) {
				center.add(0, data.distance.y);
			}
		}
	}
	
	public Vector2f getCenter() {
		return center;
	}

	public Vector2f getHalf_extent() {
		return half_extent;
	}
	
	
}
