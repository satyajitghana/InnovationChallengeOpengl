package entity;

import java.util.ArrayList;

import components.Component;
import components.ComponentID;
import components.HealthComponent;
import components.TransformComponent;

public class Entity {
	
	private ArrayList<Component> components;
	protected EntityID id;
	
	
	public TransformComponent transform;
	public HealthComponent health;
	
	public Entity(EntityID id) {
		components = new ArrayList<Component>();
		this.id = id;
		
	}
	
	public void addComponent(Component c) {
		components.add(c);
		if(c.getId() == ComponentID.Transform)
			this.transform = (TransformComponent) c;
		
		if(c.getId() == ComponentID.Health)
			this.health = (HealthComponent) c;

	}
	
	public void removeComponent(Component c) {
		c.toRemove = true;
		components.remove(c);
		if(c.getId() == ComponentID.Transform)
			this.transform = null;
		if(c.getId() == ComponentID.Health);
			this.health = null;
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
