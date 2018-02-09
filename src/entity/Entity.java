package entity;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import components.Component;
import components.ComponentID;
import components.HealthComponent;
import components.TransformComponent;

public class Entity {
	
	private ArrayList<Component> components;
	private ArrayList<Entity> children;
	protected EntityID id;
	
	
	public TransformComponent transform;
	public HealthComponent health;
	
	public Entity parent;
	
	public Entity(EntityID id) {
		components = new ArrayList<Component>();
		children = new ArrayList<Entity>();
		this.parent = null;
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
	
	public void addChild(Entity entity) {
		this.children.add(entity);
		entity.parent = this;
		
		//check below
		entity.transform.local_pos = new Vector2f(entity.transform.local_pos.x-this.transform.local_pos.x, entity.transform.local_pos.y-this.transform.local_pos.y);
	}
	
	public void removeChild(Entity entity) {
		this.children.remove(entity);
		entity.transform.local_pos = entity.transform.getWorldPos();
		entity.parent = null;
	}
	
	public void destroy() {
		for(int i=0;i<components.size();i++) {
			components.get(i).toRemove = true;
		}
		
		for(int i=0;i<children.size();i++) {
			ArrayList<Component> c = children.get(i).components;
			for(int j=0;j<c.size();j++) {
				c.get(j).toRemove = true;
			}
		}
	}
	
	public ArrayList<Entity> getChildren() {
		return this.children;
	}
	
	public ArrayList<Component> getComponents(){
		return this.components;
	}

}
