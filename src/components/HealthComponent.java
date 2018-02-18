package components;

import engine.Game;
import entity.Entity;
import entity.EntityID;
import rooms.RoomMap;

public class HealthComponent extends Component{

	public int maxHealth;
	public float currentHealth;
	
	public HealthComponent(Entity attachedTo, int maxHealth) {
		super(ComponentID.health, attachedTo);
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		
		Game.updateComponents.add(this);
	}
	
	@Override
	public void update() {
		if(currentHealth<=0){
			attachedTo.destroy();
			if(attachedTo.id == EntityID.enemy)
				RoomMap.currentRoom.enemyCount-=1;
		}
	}
	
	public void reduceHealth(float damage) {
		this.currentHealth-=damage;
	}
	
	public void increaseHealth(float increase) {
		this.currentHealth+=increase;
	}

}
