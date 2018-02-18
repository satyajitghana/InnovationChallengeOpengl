package rooms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RoomMap {
	private static HashMap<Integer, String> roomMap;
	public static ArrayList<Integer> visted;
	public static Room currentRoom;
	
	public RoomMap(String path) {
		roomMap = new HashMap<Integer, String>();
		visted = new ArrayList<Integer>();
		loadRoomMap(path);
		visit(0);
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
	
	public static void visit(int roomID) {
		currentRoom = new Room(roomID, roomMap.get(roomID));
		currentRoom.loadRoom();
		if(!visted.contains(roomID))
			visted.add(roomID);
	}

}
