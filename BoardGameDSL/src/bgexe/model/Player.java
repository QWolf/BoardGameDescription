package bgexe.model;

import bgexe.util.VariableManager;

public class Player {
	private String name;
	private boolean isHuman = true;
	private boolean isPlaying = false;
	
	private VariableManager varManager;
	
	
	public Player(String name, boolean isHuman){
		this.name = name;
		this.isHuman = isHuman;
	}
	
	
	public VariableManager getVarManager(){
		return varManager;
	}	
	
	public String getName(){
		return name;
	}

	public boolean isHuman(){
		return isHuman;
	}
	
	public boolean isPlaying(){
		return isPlaying;
	}
	
	public void setIsPlaying(boolean isPlaying){
		this.isPlaying = isPlaying;
	}
}
