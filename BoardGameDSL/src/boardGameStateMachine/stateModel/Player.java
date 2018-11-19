package boardGameStateMachine.stateModel;

import boardGameStateMachine.util.VariableManager;

public class Player {
	private String name;
	private boolean isHuman = true;
	//Neutral force if false
	private boolean isPlayer = false;
	
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
	
	public boolean isPlayer(){
		return isPlayer;
	}
	
	public void setIsPlaying(boolean isPlayer){
		this.isPlayer = isPlayer;
	}
}
