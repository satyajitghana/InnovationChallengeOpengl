package engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import entity.Entity;
import entity.EntityCreator;

public class Input {
	
	private Entity player;
	
	public Input (EntityCreator creator) {
		this.player = creator.getPlayer();
	}
	
	public void getInput() {
		if(player.health.getCurrentHealth()>0) {
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
			
			while(Mouse.next()) {
				if(Mouse.getEventButtonState()) {
					if(Mouse.getEventButton() == 0) {
						float x = (float)(player.transform.pos.x + (EntityCreator.player_size/2)*Math.cos(Math.toRadians(player.transform.rot+90)));
						float y = (float)(player.transform.pos.y + (EntityCreator.player_size/2)*Math.sin(Math.toRadians(player.transform.rot+90)));
						Vector2f pos = new Vector2f(x, y);
						Vector2f dir = new Vector2f(Mouse.getX() - player.transform.pos.x, Mouse.getY() - player.transform.pos.y);
						dir.normalise();
						Main.creator.createBullet(pos, dir, player.transform.rot);
					}
				}
			}		
		}
	}
}
