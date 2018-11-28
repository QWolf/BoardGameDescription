package boardGameStateMachine.stateModel;

import boardGameStateMachine.Variable.SingleScopeVariableManager;
import boardGameStateMachine.Variable.VarOwner;
import boardGameStateMachine.Variable.VarType;


public class GameObjectInstance {
	
	private String name;
	private String objectType;
	private Location currentLocation;
	private Game g;
	
	private SingleScopeVariableManager varManager;
	
	
	public GameObjectInstance(String name, String objectType, SingleScopeVariableManager varman, Game g, Location l){
		this.name = name;
		this.objectType = objectType;
		this.varManager = varman;
		this.g = g;
		currentLocation = l;
		Owner locationOwner = ( (VarOwner) l.getVarManager().getVariable("Owner")).getValue();
		
		varman.addVariable("Owner", new VarOwner(locationOwner));
//		varManager.addID("Owner", VarType.Player, l.getVarManager().getObject("Owner"));
	}
	
	
	public SingleScopeVariableManager getVarManager(){
		return varManager;
	}	
	
	public String getName(){
		return name;
	}

	public String getObjectType(){
		return objectType;
	}
	public Location getLocation(){
		return currentLocation;
	}
}
