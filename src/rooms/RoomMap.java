package rooms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import engine.Loader;

public class RoomMap {
	private static HashMap<Integer, String> roomMap;
	public static ArrayList<Integer> visted;
	public static Room currentRoom;
	private static Loader loader;
	
	public RoomMap(String path, Loader l) {
		roomMap = new HashMap<Integer, String>();
		visted = new ArrayList<Integer>();
		loader = l;
		loadRoomMap(path);
		visit(0, 0);
	}
	
	private void loadRoomMap(String path){
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			
			String s;
			while((s = br.readLine()) != null) {
				String[] stuff = s.split(",");
				roomMap.put(Integer.parseInt(stuff[0]), stuff[1]);
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void visit(int currentRoomID, int targetRoomID) {
		if(currentRoom!=null && !visted.contains(currentRoomID)) {
			visted.add(currentRoomID);
		}
		currentRoom = new Room(targetRoomID, roomMap.get(targetRoomID), loader);
		currentRoom.loadRoom();
	}

}
