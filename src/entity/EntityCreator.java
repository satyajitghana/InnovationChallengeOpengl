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
	private Entity pointer;
	
	public EntityCreator() {
		this.player = new Entity(EntityID.player);
		this.pointer = new Entity(EntityID.pointer);
		createPointer();
		createPlayer();
	}
	
	public void createPlayer() {
		player.addComponent(new TransformComponent(player, new Vector2f(Display.getWidth()/2 - 32, Display.getHeight()/2 - 32), 0));
		player.addComponent(new MaterialComponent(player, new Sprite("/res/images/player.png",64,64)));
		player.addComponent(new HealthComponent(player, 100));
		//player.addChild(pointer);
	}
	
	public void createPointer() {
		pointer.addComponent(new TransformComponent(pointer, new Vector2f(Display.getWidth()/2 - 50, Display.getHeight()/2 - 50), 0));
		pointer.addComponent(new MaterialComponent(pointer, new Sprite("/res/images/pointer.png", 100, 100)));
		pointer.addComponent(new MiscComponent(pointer, (Object... data)-> {
			
			Entity pointer = (Entity)data[0];
			
			Vector2f pos = pointer.transform.local_pos;
			Vector2f mouse_pos = new Vector2f(Mouse.getX(), Mouse.getY());
		
			//bad rotation code. works but glitches sometimes
			float alpha = (float) Math.toDegrees(Math.atan((mouse_pos.y - pos.y)/(mouse_pos.x - pos.x)));
			
			pointer.transform.rotate((mouse_pos.x>pos.x) ? alpha-90 : alpha + 90);			
			
		}, pointer));
	}
	
	public Entity getPlayer() {
		return this.player;
	}

}
