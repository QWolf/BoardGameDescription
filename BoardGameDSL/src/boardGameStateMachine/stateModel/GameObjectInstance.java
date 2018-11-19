package boardGameStateMachine.stateModel;

import boardGameStateMachine.util.VariableManager;

public class GameObjectInstance {
	
	private String name;
	private String objectType;
	
	private VariableManager varManager;
	
	
	public GameObjectInstance(String name, String objectType, VariableManager varman){
		this.name = name;
		this.objectType = objectType;
		this.varManager = varman;
		varManager.setIDVariable("Owner", "Public");
	}
	
	
	public VariableManager getVarManager(){
		return varManager;
	}	
	
	public String getName(){
		return name;
	}

	public String getObjectType(){
		return objectType;
	}
}
