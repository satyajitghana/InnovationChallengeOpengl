package entity;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import components.CollisionComponent;
import components.HealthComponent;
import components.MaterialComponent;
import components.MiscComponent;
import components.StateMachineComponent;
import components.TransformComponent;
import components.WindowExitTriggerComponent;
import engine.Loader;
import engine.Main;
import fsm.EnemyAttackState;
import fsm.EnemyIdleState;
import fsm.StateMachine;
import gui.GUI;

public class EntityCreator {
	public static final int player_size = 80;
	public static final float player_speed = 4;
	
	private Entity player;
	private Light light;
	private Loader loader;
	
	public EntityCreator(Loader loader) {
		this.player = new Entity(EntityID.player);
		this.loader = loader;
		
	}
	
	public Entity createPlayer(float x, float y, float rot) {
		Vector2f playerPos = new Vector2f(x, y);
		GUI healthbar = new GUI(new Vector2f(-0.84f,0.95f), new Vector2f(0.15f,0.02f), player);
		
		player.addComponent(new TransformComponent(player, playerPos, rot, 1, player_speed));
		player.addComponent(new MaterialComponent(player, loader.loadToVAO(player_size, player_size, "player"), player_size, player_size));
		player.addComponent(new HealthComponent(player, 100, healthbar));
		player.addComponent(new MiscComponent(player, Main.c.followRotation(), player, "Mouse"));
		player.addComponent(new WindowExitTriggerComponent(player, 0, Main.c.inWindow(), player));
		player.addComponent(new CollisionComponent(player, player_size/2, player_size/2, Main.c.playerCollision(), player));
		
		this.light = new Light(playerPos, new Vector3f(1,0.8f,0.6f), new Vector3f(1,1,1), 150f);
		
		return player;
		
	}
	
	public void createBullet(Vector2f pos, Vector2f dir, float rot) {
		Entity bullet = new Entity(EntityID.bullet);
		
		bullet.addComponent(new TransformComponent(bullet, pos, rot, 1, 10));
		bullet.addComponent(new MaterialComponent(bullet, loader.loadToVAO(8, 8, "bullet"), 8, 8));
		bullet.addComponent(new MiscComponent(bullet, Main.c.propel(), bullet, dir));
		bullet.addComponent(new WindowExitTriggerComponent(bullet, 50, Main.c.windowExitRemove(), bullet));
		bullet.addComponent(new CollisionComponent(bullet, 8, 8, Main.c.bulletCollision(), bullet));
	}
	
	public Entity createEnemy(float x, float y, float rot, float sx, float sy) {
		Entity enemy = new Entity(EntityID.enemy);
		
		StateMachine enemyStateMachine = new StateMachine();
		EnemyAttackState attack = new EnemyAttackState(enemyStateMachine, enemy, player);
		EnemyIdleState idle = new EnemyIdleState(enemyStateMachine, enemy, attack);
		enemyStateMachine.setCurrentState(idle);
		enemyStateMachine.setDefaultState(idle);
		
		enemy.addComponent(new TransformComponent(enemy, new Vector2f(x, y), rot, 1, 4));
		enemy.addComponent(new MaterialComponent(enemy, loader.loadToVAO(sx, sy, "enemy"), sx, sy));
		enemy.addComponent(new WindowExitTriggerComponent(enemy, 0, Main.c.inWindow(), enemy));
		enemy.addComponent(new CollisionComponent(enemy, sx/2, sy/2, Main.c.enemyCollision(), enemy));
		enemy.addComponent(new HealthComponent(enemy, 100));
		enemy.addComponent(new StateMachineComponent(enemy, enemyStateMachine));
//		enemy.addComponent(new PathfinderComponent(enemy, player));
		
		return enemy;
		
	}
	
	public Entity createWall(float x, float y, float rot, float sx, float sy) {
		Entity wall = new Entity(EntityID.wall);
		
		wall.addComponent(new TransformComponent(wall, new Vector2f(x, y), rot, 1, 0));
		wall.addComponent(new MaterialComponent(wall, loader.loadToVAO(sx, sy, "wall"), sx, sy));
		wall.addComponent(new CollisionComponent(wall, sx, sy, null));
		
		return wall;
	}
	public Entity createGate(float x, float y, float rot, float sx, float sy, int targetRoom) {
		Entity gate = new Entity(EntityID.gate);
		
		gate.addComponent(new TransformComponent(gate, new Vector2f(x, y), rot, 1, 0));
		gate.addComponent(new MaterialComponent(gate, loader.loadToVAO(sx, sy, "gateDisabled"), sx, sy));
		gate.addComponent(new CollisionComponent(gate, sx, sy, Main.c.gateCollision(), gate, targetRoom));
		
		return gate;
	}
		
	public Entity getPlayer() {
		return this.player;
	}
	
	public Light getLight() {
		return light;
	}
}
