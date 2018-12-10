package boardGameSimulator.model.boardGameStateMachine.stateModel;

import boardGameSimulator.model.boardGameStateMachine.Variable.SingleScopeVariableManager;

public class Player {
	private String name;
	private boolean isHuman = true;
	//Neutral force if false. If true, the player wins/loses in the end
	private boolean isPlayer = false;
	
	private SingleScopeVariableManager varManager = new SingleScopeVariableManager();
	private Game game;
	
	private PlayerRanking ranking= new PlayerRanking();
	
	
	public Player(String name, boolean isHuman, boolean isContender, Game g){
		this.name = name;
		this.isHuman = isHuman;
		this.isPlayer = isContender;
		this.game = g;
		
		game.addPlayer(this);
		
	}
	
	
	public SingleScopeVariableManager getVarManager(){
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


	public PlayerRanking getRanking() {
		return ranking;
	}
}
