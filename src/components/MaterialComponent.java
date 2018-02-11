package components;
import static org.lwjgl.opengl.GL11.*;

import engine.Game;
import engine.Sprite;
import entity.Entity;

public class MaterialComponent extends Component{

	private Sprite sprite;
	public MaterialComponent(Entity attachedTo, Sprite sprite) {
		super(ComponentID.Material, attachedTo);
		
		this.sprite = sprite;
		
		Game.renderComponents.add(this);
	}
	
	public void render() {
		glPushMatrix();
		{
			attachedTo.transform.glTranslate();
			attachedTo.transform.glRotate();
			attachedTo.transform.glTranslate(-sprite.sx/2, -sprite.sy/2);
			sprite.render();
		}
		glPopMatrix();
	}

}
