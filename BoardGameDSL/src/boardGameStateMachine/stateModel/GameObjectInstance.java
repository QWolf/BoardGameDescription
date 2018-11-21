package boardGameStateMachine.stateModel;

import boardGameStateMachine.util.IDManager;
import boardGameStateMachine.util.IDType;

public class GameObjectInstance {
	
	private String name;
	private String objectType;
	private Location currentLocation;
	private Game g;
	
	private IDManager idManager;
	
	
	public GameObjectInstance(String name, String objectType, IDManager idman, Game g, Location l){
		this.name = name;
		this.objectType = objectType;
		this.idManager = idman;
		this.g = g;
		currentLocation = l;
		idManager.addID("Owner", IDType.Player, l.getIDManager().getObject("Owner"));
	}
	
	
	public IDManager getVarManager(){
		return idManager;
	}	
	
	public String getName(){
		return name;
	}

	public String getObjectType(){
		return objectType;
	}
}
