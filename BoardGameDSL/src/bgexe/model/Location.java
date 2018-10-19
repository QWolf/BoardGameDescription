package bgexe.model;

import bgexe.util.VariableManager;

public class Location {
	private String name;
	private boolean isSupply = false;
	
	public GameObjectTemplate supplying = null;
	
	private VariableManager varManager;
	
	
	public Location(String name, String objectType){
		this.name = name;
		this.varManager = new VariableManager();
		varManager.setIDVariable("Owner", "Public");
	}
	
	
	
	public void setSupply(GameObjectTemplate o){
		isSupply = true;
		supplying = o;
		
		
	}
	
	public VariableManager getVarManager(){
		return varManager;
	}	
	
	public String getName(){
		return name;
	}
	
	public boolean isSupply(){
		return isSupply;
	}



}
