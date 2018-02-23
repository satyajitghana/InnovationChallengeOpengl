package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_SHADER = "src/shaders/vertexShader.vert";
	private static final String FRAGMENT_SHADER = "src/shaders/fragmentShader.frag";
	
	private int location_tranform;
	private int location_projection;
	
	public StaticShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}

	@Override
	public void bindAttributes() {
		bindAttribute(0, "position");
		bindAttribute(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations() {
		location_tranform = super.getUniformLocation("transform");
		location_projection = super.getUniformLocation("projection");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_tranform, matrix);
		
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projection, projection);
	}
}
