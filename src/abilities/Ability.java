package abilities;

public abstract class Ability {
	
	private AbilityID id;
	protected boolean active;
	
	public Ability(AbilityID id) {
		this.id = id;
		this.active = false;
	}
	
	public abstract void execute();
	public abstract void regenerate();
	
	public void activate() {
		if(active) return;
		this.active = true;
		execute();
		
	}
	
	public void deactivate() {
		if (!active) return;
		this.active = false;
	}
	
	public AbilityID getId() {
		return this.id;
		
	}
	
	public boolean isActive() {
		return this.active;
	}

}
