package entity;

import org.joml.Vector2f;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import components.CollisionComponent;
import components.HealthComponent;
import components.MaterialComponent;
import components.MiscComponent;
import components.TransformComponent;
import components.WindowExitTriggerComponent;
import engine.Game;
import engine.Main;
import engine.Sprite;
import rooms.Room;

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
		player.addComponent(new CollisionComponent(player, player_size/2, player_size/2));
		
	}
	
	public void createBullet() {
		Entity bullet = new Entity(EntityID.bullet);
		
		float x = (float)(player.transform.pos.x + (player_size/2)*Math.cos(Math.toRadians(player.transform.rot+90)));
		float y = (float)(player.transform.pos.y + (player_size/2)*Math.sin(Math.toRadians(player.transform.rot+90)));
		Vector2f pos = new Vector2f(x, y);
		Vector2f dir = new Vector2f(Mouse.getX() - player.transform.pos.x, Mouse.getY() - player.transform.pos.y);
		dir.normalize();
		
		bullet.addComponent(new TransformComponent(bullet, pos, player.transform.rot, 10));
		bullet.addComponent(new MaterialComponent(bullet, new Sprite("res/images/bullet.png", 8, 8)));
		bullet.addComponent(new MiscComponent(bullet, Main.c.propel(), bullet, dir));
		bullet.addComponent(new WindowExitTriggerComponent(bullet, 50, Main.c.windowExitRemove(), bullet));
		bullet.addComponent(new CollisionComponent(bullet, 8, 8));
	}
	
	public Entity createWall(float x, float y, float rot, float sx, float sy) {
		Entity wall = new Entity(EntityID.wall);
		
		wall.addComponent(new TransformComponent(wall, new Vector2f(x, y), rot, 0));
		wall.addComponent(new MaterialComponent(wall, new Sprite("/res/images/wall.png", sx, sy)));
		wall.addComponent(new CollisionComponent(wall, sx, sy));
		
		return wall;
	}
	
	public void createRoom(int id, String path) {
		Game.currentRoom = new Room(id, path);
		Game.currentRoom.loadRoom();
	}
	public Entity getPlayer() {
		return this.player;
	}
}
