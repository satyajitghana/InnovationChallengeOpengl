package components;

import java.util.Stack;

import org.lwjgl.util.vector.Vector2f;

import ai.ASearch;
import ai.Pair;
import engine.EntitiesMap;
import engine.Game;
import entity.Entity;

public class PathfinderComponent extends Component{
	
	private Stack <Pair <Integer, Integer>> path;
	private Entity target;
	
	public PathfinderComponent(Entity attachedTo, Entity target) {
		super(ComponentID.pathfinder, attachedTo);
		this.target = target;
		this.path = new Stack<Pair <Integer, Integer>>();
		
		Game.updateComponents.add(this);
		
	}
	
	@Override
	public void update() {
		Vector2f sourceVec = EntitiesMap.getCellVec(attachedTo.transform.pos.x, attachedTo.transform.pos.y);
		Vector2f destVec = EntitiesMap.getCellVec(target.transform.pos.x, target.transform.pos.y);
	
		path = ASearch.getPath(new Pair<Integer, Integer>((int)sourceVec.x, (int)sourceVec.y), new Pair<Integer, Integer>((int)destVec.x, (int)destVec.y));
		
		//System.out.println("target cell x: "+destVec.x + " target y: "+destVec.y);
		//System.out.println("source cell x: "+sourceVec.x+" source cell y: "+sourceVec.y);
		//System.out.println(path+"\n");
		
		/*if(path.isEmpty()){
	 		//start shooting
		}else{
	 		Pair coords<Integer, Integer> = path.pop();
		 	Vector2f dir = getDir(coords); //gets direction from current position to popped value
		 	this.attachedTo.transform.move(dir.x, dir.y);
		}
		*/
	}
}
