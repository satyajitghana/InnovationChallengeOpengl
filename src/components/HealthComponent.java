package components;

import engine.Game;
import entity.Entity;

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
		}
	}
	
	public void reduceHealth(float damage) {
		this.currentHealth-=damage;
	}
	
	public void increaseHealth(float increase) {
		this.currentHealth+=increase;
	}

}
