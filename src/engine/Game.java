package engine;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import components.Component;
import components.MaterialComponent;
import entity.EntityID;
import gui.GUI;
import gui.GUIRenderer;
import shaders.StaticShader;

public class Game {

	public static ArrayList<Component> updateComponents = new ArrayList<Component>();
	public static ArrayList<Component> renderComponents = new ArrayList<Component>();
	public static ArrayList<GUI> guis = new ArrayList<GUI>();
	public static float timeFreezeBarDepletionRate = 1f;
	public static float timeFreezeBarRegenRate = 1/30f;
	public static float timeFreezeGuiScaleX = 0.15f;
	public static float timeFreezeAmount = 100;
	
	private Renderer renderer;
	private GUIRenderer guiRenderer;
	private StaticShader shader;
	
	private static boolean game_freeze = false;
	private static float maxTimeFreezeAmount = 100;
	private static float scaleFactor = timeFreezeGuiScaleX/maxTimeFreezeAmount;
	
	
	public Game(Renderer renderer, GUIRenderer guiRenderer, StaticShader shader) {
		this.renderer = renderer;
		this.guiRenderer = guiRenderer;
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
			if(!game_freeze || c.getAttachedTo().id == EntityID.player || c.getAttachedTo().id == EntityID.bullet) {
				c.update();
			}
		}
		
		try {
			if(game_freeze && timeFreezeAmount>0) {
				decreaseTimeFreezeAmount(timeFreezeBarDepletionRate);
			}
			else if(!game_freeze) {
				increaseTimeFreezeAmount(timeFreezeBarRegenRate);
			}
		}catch(NullPointerException e) {
			return;
		}
		
		if(timeFreezeAmount <= 0) {
			game_freeze = false;
		}
	}
	
	public void renderEntities() {
		shader.start();
		
		if(Main.creator.getLight() != null)
			shader.loadLight(Main.creator.getLight());
		
		shader.isRenderingBg(true);
		renderer.render(Main.creator.getBg());
		shader.isRenderingBg(false);
		
		for(int i=0;i<renderComponents.size();i++) {
			Component c = renderComponents.get(i);
			if(c.toRemove) {
				renderComponents.remove(c);
				continue;
			}
			renderer.render((MaterialComponent) c, shader);
		}
		shader.stop();
	}
	
	public void renderGUIs() {
		guiRenderer.render(guis);
	}
	
	private void increaseTimeFreezeAmount(float amount) {
		if(timeFreezeAmount < maxTimeFreezeAmount) {
			timeFreezeAmount+=amount;
			GUI bar = Main.creator.getTimeFreezeBar();
			Vector2f scale = bar.getScale();
			
			if(timeFreezeAmount<maxTimeFreezeAmount) {
				bar.setScale(scale.x + (scaleFactor*amount), scale.y);
			}
		}
	}
	
	private void decreaseTimeFreezeAmount(float amount) {
		
		if(timeFreezeAmount>0) {
			timeFreezeAmount -= amount;
			GUI bar = Main.creator.getTimeFreezeBar();
			Vector2f scale = bar.getScale();
			bar.setScale(scale.x - (scaleFactor*amount), scale.y);
		}
	}
	
	public static void setTimeFreeze(boolean value) {
		game_freeze = value;
	}
	
	public static boolean isTimeFreeze() {
		return game_freeze;
	}
	
	public static void setTimeFreezeAmount(float amount) {
		timeFreezeAmount+=amount;
	}
	
	public static float getTimeFreezeAmount() {
		return timeFreezeAmount;
	}
	
	public static float getMaxTimeFreezeAmount() {
		return maxTimeFreezeAmount;
	}
}
