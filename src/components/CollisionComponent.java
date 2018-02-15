package components;

import engine.AABB;
import engine.Game;
import entity.Entity;

public class CollisionComponent extends Component{

	private AABB bounds;
	
	public CollisionComponent(Entity attachedTo, float sx, float sy) {
		super(ComponentID.collision, attachedTo);
		Game.updateComponents.add(this);
		
		bounds = new AABB(this.attachedTo, sx, sy);
	}
	
	public AABB getAABB() {
		return this.bounds;
	}
}
