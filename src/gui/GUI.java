package gui;

import org.lwjgl.util.vector.Vector2f;

import entity.Entity;

public class GUI {
	
	private int texID;
	private Vector2f pos;
	private Vector2f scale;
	private Entity attachedTo;
	
	public GUI(Vector2f pos, Vector2f scale, int texID) {
		this.pos = pos;
		this.scale = scale;
		this.texID = texID;

	}
	
	public GUI(Vector2f pos, Vector2f scale, Entity attachedTo) {
		this(pos, scale);
		this.attachedTo = attachedTo;
	}
	
	public GUI(Vector2f pos, Vector2f scale) {
		this.pos = pos;
		this.scale = scale;
		this.texID = 0;
	}

	public int getTexID() {
		return texID;
	}

	public Vector2f getPos() {
		return pos;
	}

	public Vector2f getScale() {
		return scale;
	}
	
	public Entity getAttachedTo() {
		return this.attachedTo;
	}
	
	public void setScale(float sx, float sy) {
		this.scale.x = sx;
		this.scale.y = sy;
	}
	
	public boolean hasTexture() {
		return (texID == 0) ? false : true;
	}
}
