package entity;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Light {
	public Vector2f position;
	public Vector3f color;
	
	public Light(Vector2f position, Vector3f color) {
		this.position = position;
		this.color = color;
	}
}
