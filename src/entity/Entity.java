package entity;

import java.util.ArrayList;

import components.CollisionComponent;
import components.Component;
import components.ComponentID;
import components.HealthComponent;
import components.MaterialComponent;
import components.TransformComponent;

public class Entity {
	
	private ArrayList<Component> components;
	public EntityID id;
	
	
	public TransformComponent transform;
	public HealthComponent health;
	public MaterialComponent material;
	public CollisionComponent collisionComponent;
	
	public Entity(EntityID id) {
		components = new ArrayList<Component>();
		this.id = id;
		
	}
	
	public void addComponent(Component c) {
		components.add(c);
		
		if(c.getId() == ComponentID.transform)
			this.transform = (TransformComponent) c;
		else if(c.getId() == ComponentID.health)
			this.health = (HealthComponent) c;
		else if(c.getId() == ComponentID.material)
			this.material = (MaterialComponent) c;
		else if(c.getId() == ComponentID.collision)
			this.collisionComponent = (CollisionComponent)c;

	}
	
	public void removeComponent(Component c) {
		c.toRemove = true;
		components.remove(c);
		
		if(c.getId() == ComponentID.transform)
			this.transform = null;
		else if(c.getId() == ComponentID.health)
			this.health = null;
		else if(c.getId() == ComponentID.material)
			this.material = null;
		else if(c.getId() == ComponentID.collision)
			this.collisionComponent = null;
	}
	
	public void destroy() {
		for(int i=0;i<components.size();i++) {
			components.get(i).toRemove = true;
		}
	}
	
	public ArrayList<Component> getComponents(){
		return this.components;
	}

}
