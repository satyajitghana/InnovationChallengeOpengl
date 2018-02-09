package engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import entity.EntityCreator;

import static org.lwjgl.opengl.GL11.*;
public class Main {
	
	private static Game game;
	private static Input keyIn;
	private static EntityCreator creator;
	
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
		creator = new EntityCreator();
		keyIn = new Input(creator.getPlayer());
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
			Display.setDisplayMode(new DisplayMode(800, 600));
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
		glDisable(GL_DEPTH_TEST);
		glClearColor(0,0,0,0);
		
	}
	
	private static void cleanUp() {
		Display.destroy();
		Keyboard.destroy();
	}

}
