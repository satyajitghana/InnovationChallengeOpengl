package components;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.util.vector.Vector2f;

import engine.Game;
import entity.Entity;
public class TransformComponent extends Component{
	
	public Vector2f pos;
	public float rot;
	
	private float speed;
	
	public TransformComponent(Entity attachedTo, Vector2f pos, float rot, float speed) {
		super(ComponentID.Transform, attachedTo);
		
		this.pos = pos;
		this.rot = rot;
		this.speed = speed;
		
		Game.updateComponents.add(this);
	}
	
	public void move(float magX, float magY) {
		translate(magX*speed, magY*speed);
	}

	public void glTranslate() {
		glTranslatef(pos.x, pos.y, 0);
	}
	
	public void glTranslate(float x, float y) {
		glTranslatef(x, y, 0);
		
	}
	public void glRotate() {
		glRotatef(rot, 0,0,1); //z axis
	}
	
	public void translate(float x, float y) {
		this.pos.x += x;
		this.pos.y += y;
	}
	
	public void rotate(float rot) {
		this.rot = rot;
		
	}
}
