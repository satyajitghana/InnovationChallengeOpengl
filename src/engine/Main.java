package engine;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import callback.Callbacks;
import entity.EntityCreator;
import gui.GUIRenderer;
import rooms.RoomMap;
import shaders.GUIShader;
import shaders.StaticShader;
public class Main {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	private static Game game;
	private static Input keyIn;
	private static EntitiesMap emap;
	private static Renderer renderer;
	private static GUIRenderer guiRenderer;
	private static StaticShader shader;
	private static GUIShader guiShader;
	private static Loader loader;
	
	public static EntityCreator creator;
	public static Callbacks c;
	
	public static void main (String args[]) {
		initDisplay();
		initGL();
		initGame();
		
		gameLoop();
		cleanUp();
	}
	
	private static void gameLoop() {
		long lastTime = System.nanoTime();
		long now;
		long timer = System.currentTimeMillis();
		double delta = 0;
		int frames = 0;
		int updates = 0;
		int ups = 80; //runs at 60 updates per second. difference due to Display.sync(60)?
		
		while(!Display.isCloseRequested()) {
			now = System.nanoTime();
			delta += (now - lastTime)/(1000000000/ups);
			lastTime = now;
			while (delta >= 1) {
				getInput();
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
	            timer += 1000;
	            System.out.println(updates + " ups, " + frames + " fps");
	            updates = 0;
	            frames = 0;
	         }
		}
		
		shader.cleanUp();
		guiShader.cleanUp();
		loader.cleanUp();
	}
	
	private static void initGame() {
		loader = new Loader();
		shader = new StaticShader();
		guiShader = new GUIShader();
		renderer = new Renderer(shader);
		guiRenderer = new GUIRenderer(loader, guiShader);
		game = new Game(renderer, guiRenderer, shader);
		c = new Callbacks();
		creator = new EntityCreator(loader);
		creator.createPlayer(WIDTH/2, HEIGHT/2, 0);
		keyIn = new Input(creator);
		emap = new EntitiesMap();
		new RoomMap("res/data/roomMap.data", loader);
		
		creator.getPlayer().transform.pos.x = 400;
		creator.getPlayer().transform.pos.y = 300;
		
	}
	
	private static void getInput() {
		keyIn.getInput();
	}
	
	private static void update() {
		game.update();
		emap.update();
		RoomMap.currentRoom.update();
	}
	
	private static void render() {
		renderer.prepare();
		
		game.renderEntities();
		game.renderGUIs();
				
		Display.update();
		Display.sync(60);
	}
	
	private static void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
			Display.setVSyncEnabled(true);
			
			Keyboard.create();
		}catch(LWJGLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void initGL() {
		glViewport(0, 0, WIDTH, HEIGHT);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_DEPTH_TEST);
		glClearColor(0,0,0,0);
		
	}
	
	private static void cleanUp() {
		Display.destroy();
		Keyboard.destroy();
	}
}
