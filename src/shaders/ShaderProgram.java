package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class ShaderProgram {
	
	private int program;
	private int vs;
	private int fs;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public ShaderProgram(String vertexShader, String fragmentShader) {
		this.vs = loadShader(vertexShader, GL20.GL_VERTEX_SHADER);
		this.fs = loadShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);
		
		this.program = GL20.glCreateProgram();
		GL20.glAttachShader(program, vs);
		GL20.glAttachShader(program, fs);
		bindAttributes();
		GL20.glLinkProgram(program);
		GL20.glValidateProgram(program);
		getAllUniformLocations();
	}
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	public void start() {
		GL20.glUseProgram(program);
	}
	
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	public void cleanUp() {
		stop();
		GL20.glDetachShader(program, vs);
		GL20.glDetachShader(program, fs);
		GL20.glDeleteProgram(program);
		GL20.glDeleteShader(vs);
		GL20.glDeleteShader(fs);
	}
	
	public abstract void bindAttributes();
	
	protected void bindAttribute(int attributeNumber, String variable) {
		GL20.glBindAttribLocation(program, attributeNumber, variable);
	}
	
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	
	protected void loadVector(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	protected void loadBoolean(int location, boolean value) {
		GL20.glUniform1f(location, (value) ? 1 : 0);
	}
	
	protected void loadMatrix(int location, Matrix4f matrix) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
	
	private static int loadShader(String file, int type) {
		StringBuilder source = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null)
				source.append(line).append("\n");
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, source);
		GL20.glCompileShader(shaderID);
		
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader");
			System.exit(-1);
		}
		
		return shaderID;
	}
}
