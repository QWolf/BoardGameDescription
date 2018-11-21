package boardGameStateMachine.stateModel;

import boardGameStateMachine.util.IDManager;

public class Player {
	private String name;
	private boolean isHuman = true;
	//Neutral force if false. If true, the player wins/loses in the end
	private boolean isPlayer = false;
	
	private IDManager idManager = new IDManager();
	
	
	public Player(String name, boolean isHuman, boolean isPlayer){
		this.name = name;
		this.isHuman = isHuman;
		this.isPlayer = isPlayer;
	}
	
	
	public IDManager getVarManager(){
		return idManager;
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
