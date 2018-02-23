package toolbox;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Maths {
	private static final float RIGHT_PLANE = (float)Display.getWidth();
	private static final float LEFT_PLANE = 0f;
	private static final float NEAR_PLANE = -1f;
	private static final float FAR_PLANE = 1f;
	private static final float TOP_PLANE = (float)Display.getHeight();
	private static final float BOTTOM_PLANE = 0f;
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(new Vector3f(translation.x, translation.y, 0), matrix, matrix);
		Matrix4f.rotate(0, new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate(0, new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		
		return matrix;
	}
	
	public static Matrix4f createProjectionMatrix() {
		
		Matrix4f projectionMatrix = new Matrix4f();
		projectionMatrix.setIdentity();
		
		projectionMatrix.m00 = 2f / (RIGHT_PLANE - LEFT_PLANE);
		projectionMatrix.m11 = 2f / (TOP_PLANE - BOTTOM_PLANE);
		projectionMatrix.m22 = -2f/ (FAR_PLANE - NEAR_PLANE);
		projectionMatrix.m33 = 1;
		projectionMatrix.m30 = -((RIGHT_PLANE + LEFT_PLANE) / (RIGHT_PLANE - LEFT_PLANE));
		projectionMatrix.m31 = -((TOP_PLANE + BOTTOM_PLANE)/(TOP_PLANE - BOTTOM_PLANE));
		projectionMatrix.m32 = -((FAR_PLANE + NEAR_PLANE)/(FAR_PLANE - NEAR_PLANE));
		
		return projectionMatrix;
	}
	
	public static Vector2f getNormalizedDeviceSpace(float x, float y) {
		Vector2f pos = new Vector2f();
		pos.x = (2f * x) / Display.getWidth() - 1f;
		pos.y = (2f * y) / Display.getHeight() - 1f;
		return pos;
	}
	
	public static Vector2f getViewportSpace(float x, float y) {
		Vector2f p = new Vector2f();
		p.x = ((x+1)*Display.getWidth()/2f);
		p.y = ((y+1)*Display.getHeight()/2f);
		return p;
	}
}
