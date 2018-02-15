package engine;

import org.joml.Vector2f;

import entity.Entity;

public class AABB {
	private Vector2f center;
	private Vector2f top_left;
	private Vector2f half_extent;
	private Vector2f offsets;
	
	public AABB(Entity entity, float sx, float sy, float offset_x, float offset_y) {
		this.center = entity.transform.pos;
		this.half_extent = new Vector2f(sx/2, sy/2);
		this.top_left = new Vector2f((center.x - half_extent.x) + offset_x, (center.y + half_extent.y) + offset_y);
		this.offsets = new Vector2f(offset_x, offset_y);
	}

	public void getCollision(AABB bounds) {
		updateAABB();
		
		if(rangeIntersect(top_left.x, top_left.x + (half_extent.x*2), bounds.top_left.x, bounds.top_left.x + (bounds.half_extent.x*2)) &&
				rangeIntersect(top_left.y - (half_extent.y*2), top_left.y, bounds.top_left.y - (bounds.half_extent.y*2), bounds.top_left.y)) {
			System.out.println("Collision");
		}
	}
	
	/*
	public Collision getCollision(AABB bounds) {
		Vector2f distance = bounds.center.sub(center, new Vector2f());
		distance.x = (float) Math.abs(distance.x);
		distance.y = (float) Math.abs(distance.y);
		
		distance.sub(half_extent.add(bounds.half_extent, new Vector2f()));
		
		return new Collision(distance, (distance.x < 0 && distance.y < 0));
	}
	
	public void correctPosition(Entity entity, Collision data) {
		AABB bounds = entity.collisionComponent.getAABB();
		Vector2f correction = bounds.center.sub(center, new Vector2f());
		
		if(data.distance.x > data.distance.y) {
			if(correction.x > 0) {
				center.add(data.distance.x, 0);
				entity.transform.pos.add(data.distance.x, 0);
			}else {
				center.add(-data.distance.x, 0);
				entity.transform.pos.add(-data.distance.x, 0);
			}
		}else {
			if(correction.y > 0) {
				center.add(0, data.distance.y);
				entity.transform.pos.add(0, data.distance.y);
			}else {
				center.add(0, -data.distance.y);
				entity.transform.pos.add(0, -data.distance.y);
			}
		}
	}*/
	
	private boolean rangeIntersect(float min0, float max0, float min1, float max1) {
		return Math.min(min0, min1) <= Math.max(min0, min1) && Math.max(min0, min1) <= Math.min(max0, max1);
		
	}
	
	private void updateAABB() {
		this.top_left.x = (center.x - half_extent.x) + offsets.x;
		this.top_left.y = (center.y + half_extent.y) + offsets.y;
		
	}
	
	public Vector2f getCenter() {
		return center;
	}

	public Vector2f getHalf_extent() {
		return half_extent;
	}
	
	
}
