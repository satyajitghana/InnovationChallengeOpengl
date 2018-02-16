package engine;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import callback.Callbacks;
import entity.EntityCreator;
public class Main {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private static Game game;
	private static Input keyIn;
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
		while(!Display.isCloseRequested()) {
			getInput();
			update();
			render();
		}
	}
	
	private static void initGame() {
		game = new Game();
		c = new Callbacks();
		creator = new EntityCreator();
		keyIn = new Input(creator);
		creator.createRoom(0, "res/data/room1.txt");
	}
	
	private static void getInput() {
		keyIn.getInput();
	}
	
	private static void update() {
		game.update();
	}
	
	private static void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();
		
		game.render();
		
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
		glMatrixMode(GL_PROJECTION);;
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_TEXTURE_2D);
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
