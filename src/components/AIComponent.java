package components;

import java.util.Stack;

import ai.ASearch;
import entity.Entity;
import ai.Pair;
import engine.Game;

public class AIComponent extends Component{
	
	private ASearch pathfinder;
	private Stack <Pair <Integer, Integer>> path;
	private Entity target;
	
	public AIComponent(Entity attachedTo, Entity target, ASearch pathfinder) {
		super(ComponentID.AI, attachedTo);
		this.target = target;
		this.pathfinder = pathfinder;
		this.path = new Stack<Pair <Integer, Integer>>();
		
		Game.updateComponents.add(this);
		
	}
	
	@Override
	public void update() {
		/*
		 path = pathfinder.getPath(int sourceX, int sourceY, int destX, int destY);
		 if(path.isEmpty()){
		 	//start shooting
		 }else{
		 	Pair coords<Integer, Integer> = path.pop();
		 	Vector2f dir = getDir(coords); //gets direction from current position to popped value
		 	this.attachedTo.transform.move(dir.x, dir.y);
		 }
		 */
	}
}
