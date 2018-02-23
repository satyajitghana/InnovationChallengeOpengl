package engine;

import java.util.ArrayList;

import components.Component;
import components.MaterialComponent;
import shaders.StaticShader;

public class Game {

	public static ArrayList<Component> updateComponents;
	public static ArrayList<Component> renderComponents;
	
	private Renderer renderer;
	private StaticShader shader;
	
	public Game(Renderer renderer, StaticShader shader) {
		updateComponents = new ArrayList<Component>();
		renderComponents = new ArrayList<Component>();
		this.renderer = renderer;
		this.shader = shader;
	}
	public void update() {
		///System.out.println("number of components: "+(updateComponents.size()+renderComponents.size()));
		for(int i=0;i<updateComponents.size();i++) {
			Component c = updateComponents.get(i);
			if(c.toRemove) {
				updateComponents.remove(c);
				continue;
			}
			
			c.update();
		}
	}
	
	public void render() {
		for(int i=0;i<renderComponents.size();i++) {
			Component c = renderComponents.get(i);
			if(c.toRemove) {
				renderComponents.remove(c);
				continue;
			}
			
			renderer.render((MaterialComponent)c, shader);
		}
		
	}
	
}
