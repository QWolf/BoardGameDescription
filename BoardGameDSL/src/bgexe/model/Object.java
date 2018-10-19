package bgexe.model;

import bgexe.util.VariableManager;

public class Object {
	
	private String name;
	private String objectType;
	
	private VariableManager varManager;
	
	
	public Object(String name, String objectType){
		this.name = name;
		this.objectType = objectType;
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
