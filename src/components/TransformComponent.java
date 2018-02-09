package components;

import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glRotatef;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import engine.Game;
import entity.Entity;
public class TransformComponent extends Component{
	
	public Vector2f local_pos; // acts as world position if there is no parent
	public float local_rot;
	
	private float speed = 4;
	
	public TransformComponent(Entity attachedTo, Vector2f pos, int rot) {
		super(ComponentID.Transform, attachedTo);
		
		this.local_pos = pos;
		this.local_rot = rot;
		
		Game.updateComponents.add(this);
	}
	
	public void move(float magX, float magY) {
		translate(magX*speed, magY*speed);
		
		ArrayList<Entity> children = attachedTo.getChildren();
		for(int i=0;i<children.size();i++) {
			Entity child = children.get(i);
			for(int j=0;j<child.getComponents().size();j++) {
				child.transform.translate(local_pos.x, local_pos.y);
			}
		}
		//System.out.println("x: +"+local_pos.x+"\ny: "+local_pos.y+"\n\n");
	}

	public void glTranslate() {
		if(attachedTo.parent!=null) {
			Vector2f pos = getWorldPos();
			glTranslatef(pos.x, pos.y, 0);
		}
		else{
			glTranslatef(local_pos.x, local_pos.y, 0);
		}
		
	}
	
	public void glRotate() {
		if(attachedTo.parent!=null) {
			float rot = getWorldRot();
			glRotatef(rot, 0,0,1);
		}
		else{
			glRotatef(local_rot, 0,0,1); //z axis
		}
	}
	
	public void translate(float x, float y) {
		this.local_pos.x += x;
		this.local_pos.y += y;
	}
	
	public void rotate(float rot) {
		this.local_rot = rot;
		
	}
	
	public Vector2f getWorldPos() {
		Vector2f parentWorldPos = attachedTo.parent.transform.local_pos;
		return new Vector2f(parentWorldPos.x + this.local_pos.x, parentWorldPos.y + this.local_pos.y);
	}
	
	public float getWorldRot() {
		float parentWorldRot = attachedTo.transform.local_rot;
		return parentWorldRot + local_rot;
	}

}
