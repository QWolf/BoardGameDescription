package boardGameStateMachine.Variable;

import java.util.HashMap;

/**
 * A class that manages the variables (ID's) of a single GameLocation, that is of an Object, Location, Player, Round or Action, or the global variables.
 * 
 * @author Peter Schroten
 *
 */

public class SingleScopeVariableManager {
	
	private HashMap<String,Variable> varList = new HashMap<String,Variable>();
	
	public SingleScopeVariableManager(){
		
	}
	
	public boolean addVariable(String name, Variable v){
		boolean error = !varList.containsKey(name);
		varList.put(name, v);
		return error;
	}
	
	public Variable getVariable(String name){
		return varList.get(name);
	}
	
	public boolean containsKey(String name){
		return varList.containsKey(name);
	}
	
	/**
	 * Creates a deepcopy of the variablemanager, useful for ObjectTemplates
	 * @return A deepcopy of this variableManager
	 */
	public SingleScopeVariableManager getCopy(){
		SingleScopeVariableManager copy = new SingleScopeVariableManager();
			for(String key : varList.keySet()){
				copy.addVariable(key, varList.get(key));
			}
		
		return copy;
		
	}
		

}
