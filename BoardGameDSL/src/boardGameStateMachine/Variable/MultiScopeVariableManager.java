package boardGameStateMachine.Variable;
/**
 *  A class that manages the global scope and the Round/Action scope, and decides which scope to call
 * @author Peter Schroten
 *
 */

public class MultiScopeVariableManager {
	
	private SingleScopeVariableManager global;
	private SingleScopeVariableManager roundAction;
	
	public MultiScopeVariableManager(SingleScopeVariableManager globalScope){
		this.global = globalScope;
	}
	
	public SingleScopeVariableManager getGlobalScope(){
		return global;
	}
	
	public SingleScopeVariableManager getRoundActionScope(){
		return roundAction;
	}
	
	public void setRoundActionScope(SingleScopeVariableManager round){
		this.roundAction = round;
	}
	
	
	
	public Variable getVariable(String name){
		//Find variable in scope of round/action
		if(roundAction != null){
			if(roundAction.containsKey(name)){
				return roundAction.getVariable(name);
			}
		}
		
		//If not found, find in global scope
		return global.getVariable(name);
		
		
		
	}
	

}
