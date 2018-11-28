package boardGameStateMachine.stateModel;

import boardGameStateMachine.Variable.SingleScopeVariableManager;
import boardGameStateMachine.Variable.VarOwner;
import boardGameStateMachine.Variable.Variable;


public class GameObjectTemplate {

	private String objectTypeName;

	private SingleScopeVariableManager varMan;
	private Game game;
	private String generatorName;
	private int generatorcounter = 1;

	/**
	 * Object Template
	 * @param objectType TypeName, as well as Generator name
	 * @param g Game state space
	 */
	public GameObjectTemplate(String objectType, Game g) {
		this(objectType, g, objectType);
	}
	
	public GameObjectTemplate(String objectType, Game g, String generatorName){
		this.generatorName = generatorName;
		this.objectTypeName = objectType;
		this.varMan = new SingleScopeVariableManager();
		this.game = g;
		
		//Public owner
		varMan.addVariable("Owner", new VarOwner(VarOwner.OwnerType.Public));
	}

	public Variable getVariable(String name) {
		return varMan.getVariable(name);
	}
	
	public boolean hasVariable(String name){
		return varMan.containsKey(name);
	}
	
	public void setVariable(String name, Variable v){
		varMan.addVariable(name, v);
	}

	public String getObjectType() {
		return objectTypeName;
	}

	public GameObjectInstance getNewInstance(String name, Location loc) {
		GameObjectInstance instance = new GameObjectInstance(name, objectTypeName, varMan.getCopy(),game,loc);

		return instance;
	}
	
	public GameObjectInstance getNewInstance(Location loc){
		String name = generatorName + String.valueOf(generatorcounter);
		generatorcounter++;
		return getNewInstance(name, loc);
		
	}
}
