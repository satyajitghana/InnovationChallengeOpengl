package entity;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import components.HealthComponent;
import components.MaterialComponent;
import components.MiscComponent;
import components.TransformComponent;
import engine.Sprite;

public class EntityCreator {
	private Entity player;
	
	public EntityCreator() {
		this.player = new Entity(EntityID.player);
		createPlayer();
	}
	
	public void createPlayer() {
		int size = 80;
		player.addComponent(new TransformComponent(player, new Vector2f(Display.getWidth()/2 - size/2, Display.getHeight()/2 - size/2), 0));
		player.addComponent(new MaterialComponent(player, new Sprite("/res/images/player.png",size, size)));
		player.addComponent(new HealthComponent(player, 100));
		player.addComponent(new MiscComponent(player, (Object... data)-> {
			
			Entity player = (Entity)data[0];
			
			Vector2f pos = player.transform.local_pos;
			Vector2f mouse_pos = new Vector2f(Mouse.getX(), Mouse.getY());
		
			//bad rotation code. works but glitches sometimes
			float alpha = (float) Math.toDegrees(Math.atan((mouse_pos.y - pos.y)/(mouse_pos.x - pos.x)));
			
			player.transform.rotate((mouse_pos.x>pos.x) ? alpha-90 : alpha + 90);			
			
		}, player));
	}
	public Entity getPlayer() {
		return this.player;
	}

}
