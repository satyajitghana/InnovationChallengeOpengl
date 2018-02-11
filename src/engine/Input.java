package engine;

import org.lwjgl.input.Keyboard;

import entity.Entity;
import entity.EntityCreator;

public class Input {
	
	private Entity player;
	
	public Input (EntityCreator creator) {
		this.player = creator.getPlayer();
	}
	
	public void getInput() {
		if(player.health.currentHealth>0) {
			if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
				player.transform.move(0, 1);
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
				player.transform.move(0, -1);
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
				player.transform.move(-1, 0);
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
				player.transform.move(1, 0);
			}
		}
		
		
	}
}
