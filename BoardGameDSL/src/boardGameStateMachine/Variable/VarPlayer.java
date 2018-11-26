package boardGameStateMachine.Variable;

import boardGameStateMachine.stateModel.Player;

public class VarPlayer extends Variable{
	Player value = null;

	public VarPlayer() {
		super(VarType.Player);
	}
	public VarPlayer(Player p){
		super(VarType.Player);
		value = p;
	}
	
	public Player getValue(){
		return value;
	}
	
	public void setValue(Player p){
		value = p;
	}
	
	@Override
	public SingleScopeVariableManager getGameObjectVariableManager() {
		return value.getVarManager();
	}
}
