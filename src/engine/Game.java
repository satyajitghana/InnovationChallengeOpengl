package engine;

import java.util.ArrayList;

import components.Component;

public class Game {

	public static ArrayList<Component> updateComponents;
	public static ArrayList<Component> renderComponents;
	
	public Game() {
		updateComponents = new ArrayList<Component>();
		renderComponents = new ArrayList<Component>();
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
			
			c.render();
		}
		
	}
	
}
