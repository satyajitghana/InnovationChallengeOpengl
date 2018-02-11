package entity;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import components.HealthComponent;
import components.MaterialComponent;
import components.MiscComponent;
import components.TransformComponent;
import components.WindowExitTriggerComponent;
import engine.Main;
import engine.Sprite;

public class EntityCreator {
	public static final int player_size = 80;
	public static final float player_speed = 4;
	
	private Entity player;
	
	public EntityCreator() {
		this.player = new Entity(EntityID.player);
		createPlayer();
	}
	
	public void createPlayer() {
		player.addComponent(new TransformComponent(player, new Vector2f(Display.getWidth()/2 - player_size/2, Display.getHeight()/2 - player_size/2), 0, player_speed));
		player.addComponent(new MaterialComponent(player, new Sprite("/res/images/player.png",player_size, player_size)));
		player.addComponent(new HealthComponent(player, 100));
		player.addComponent(new MiscComponent(player, Main.c.mouseFollowRotation(), player));
		player.addComponent(new WindowExitTriggerComponent(player, 0, Main.c.inWindow(), player));
		
	}
	
	public void createBullet() {
		Entity bullet = new Entity(EntityID.bullet);
		
		float x = (float)(player.transform.pos.x + (player_size/2)*Math.cos(Math.toRadians(player.transform.rot+90)));
		float y = (float)(player.transform.pos.y + (player_size/2)*Math.sin(Math.toRadians(player.transform.rot+90)));
		Vector2f pos = new Vector2f(x, y);
		Vector2f dir = new Vector2f(Mouse.getX() - player.transform.pos.x, Mouse.getY() - player.transform.pos.y);
		dir.normalise();
		
		bullet.addComponent(new TransformComponent(bullet, pos, player.transform.rot, 10));
		bullet.addComponent(new MaterialComponent(bullet, new Sprite("res/images/bullet.png", 8, 8)));
		bullet.addComponent(new MiscComponent(bullet, Main.c.propel(), bullet, dir));
		bullet.addComponent(new WindowExitTriggerComponent(bullet, 50, Main.c.windowExitRemove(), bullet));
	}
	public Entity getPlayer() {
		return this.player;
	}

}
