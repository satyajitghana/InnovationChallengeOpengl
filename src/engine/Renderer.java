package engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import components.MaterialComponent;
import shaders.StaticShader;
import toolbox.Maths;

public class Renderer {	
	public Renderer(StaticShader shader) {
		shader.start();
		shader.loadProjectionMatrix(Maths.createProjectionMatrix());
		shader.stop();
	}
	
	public void prepare() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0,0,0,1);
	}
	
	public void render(MaterialComponent material, StaticShader shader) {
		GL30.glBindVertexArray(material.getMaterial().getVaoID());
		{
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			shader.loadTransformationMatrix(material.getAttachedTo().transform.getTransformationMatrix());
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, material.getMaterial().getTextureID());
			{
				GL11.glDrawElements(GL11.GL_TRIANGLES, material.getMaterial().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
		}
		GL30.glBindVertexArray(0);
	}
	
	
	
}
