package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class GUIShader extends ShaderProgram{

	private static final String VERTEX_SHADER = "/shaders/guiShader.vert";
	private static final String FRAGMENT_SHADER = "/shaders/guiShader.frag";
	
	private int location_transformation;
	private int location_texture;
	private int location_renderinghHealthbar;
	private int location_healthBarColor;
	
	public GUIShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
		
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformation = super.getUniformLocation("transform");		
		location_texture = super.getUniformLocation("textureSampler");
		location_renderinghHealthbar = super.getUniformLocation("renderingHealthBar");
		location_healthBarColor = super.getUniformLocation("healthBarColor");
	}

	@Override
	protected void connectTextureUnits() {
		super.loadInt(location_texture, 0);
		
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformation, matrix);
	}
	
	public void renderingHealthBar(boolean value) {
		super.loadBoolean(location_renderinghHealthbar, value);
	}
	
	public void loadHealthBarColor(Vector3f color) {
		super.loadVector3f(location_healthBarColor, color);
	}

}
