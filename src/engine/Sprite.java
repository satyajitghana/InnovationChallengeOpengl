	package engine;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sprite {	
	private float sx;
	private float sy;
	
	private int draw_count;
	private int v_id;
	private int t_id;
	private int i_id;
	
	private float[] vertices;
	private float[] tex_coords;
	private int[] indices;
	
	private Texture tex;
	
	public Sprite(String path, float sx, float sy) {
		
		try {
			this.tex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.sx = sx;
		this.sy = sy;
		
		this.vertices = new float[] {
				0, this.sy,  	  //top left
				this.sx, this.sy, //top right
				this.sx, 0, 	  //bottom right
				0,0,    		  //bottom left
		};
		this.tex_coords = new float[] {
				0,0,
				1,0,
				1,1,
				0,1,
		};
		this.indices = new int[] {
				0,1,2,
				2,3,0
		};
		
		this.draw_count = indices.length;
		
		this.v_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		{
			glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);
		}
		
		this.t_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, t_id);
		{
			glBufferData(GL_ARRAY_BUFFER, createBuffer(tex_coords), GL_STATIC_DRAW);
		}
		
		this.i_id = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
		{
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, createBuffer(indices), GL_STATIC_DRAW);
		}
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
	}
	
	public void render() {
		tex.bind();
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		{
			glBindBuffer(GL_ARRAY_BUFFER, this.v_id);
			{
				glVertexPointer(2, GL_FLOAT, 0, 0);
			}
			
			glBindBuffer(GL_ARRAY_BUFFER, this.t_id);
			{
				glTexCoordPointer(2, GL_FLOAT, 0, 0);
			}
			
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.i_id);
			{
				glDrawElements(GL_TRIANGLES, draw_count, GL_UNSIGNED_INT, 0);
			}
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
		}
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		
	}
	
	public FloatBuffer createBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public IntBuffer createBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}

