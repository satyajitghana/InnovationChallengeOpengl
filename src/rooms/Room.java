package rooms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import components.Component;
import engine.Main;
import entity.Entity;

public class Room {
	public float room_id;
	
	private String path;
	private ArrayList<Entity> entities;
	
	public Room(float room_id, String path) {
		this.entities = new ArrayList<Entity>();
		this.path = path;
	}
	
	public void loadRoom() {
		try {
			readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readFile() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		
		String s;
		while((s = br.readLine()) != null) {
			String[] stuff = s.split(",");
			if(stuff[0].equals("wall")) {
				entities.add(Main.creator.createWall(Integer.parseInt(stuff[1]), Integer.parseInt(stuff[2]), Integer.parseInt(stuff[3]), Integer.parseInt(stuff[4]), Integer.parseInt(stuff[5])));
			}
		}
		br.close();
	}
	
	public void destory() {
		for(int i = 0;i<entities.size();i++) {
			for(Component c: entities.get(i).getComponents())
				c.toRemove = true;
		}
	}
}
