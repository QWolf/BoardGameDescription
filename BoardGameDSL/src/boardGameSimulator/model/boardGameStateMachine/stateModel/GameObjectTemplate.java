package boardGameSimulator.model.boardGameStateMachine.stateModel;

import boardGameSimulator.model.boardGameStateMachine.Variable.SingleScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarOwner;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;


public class GameObjectTemplate {

	private String objectTypeName;

	private SingleScopeVariableManager varMan;
	private Game game;
	private String generatorName;
	private int generatorcounter = 1;
	private Randomizer randomizer;
	private boolean ownerIsLocation = false;

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
		varMan.addVariable("Owner", new VarOwner());
		g.addGameObjectTemplate(this);
	}
	
	public void setOwnerIsLocation(boolean ownerequalsLocation){
		this.ownerIsLocation = ownerequalsLocation;
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
	
	public Randomizer getRandomizer(){
		return randomizer;
	}
	
	public void setRandomizer(Randomizer r){
		this.randomizer = r;
	}

	public GameObjectInstance getNewInstance(String name, Location loc) {
		//Copy the object
		GameObjectInstance instance = new GameObjectInstance(name, objectTypeName, varMan.getCopy(),game,loc);

		//copy the randomizer, if any
		if(randomizer != null){
			instance.setRandomizer(new Randomizer(((VarList)randomizer.getVariableList()).getValue()));
			instance.getRandomizer().setValue(randomizer.getValue());
		}
		
		if(ownerIsLocation){
			instance.getVarManager().addVariable("Owner", loc.getVarManager().getVariable("Owner"));
		}
		return instance;
	}
	
	public GameObjectInstance getNewInstance(Location loc){
		String name = generatorName + String.valueOf(generatorcounter);
		generatorcounter++;
		return getNewInstance(name, loc);
		
	}

	public String getName() {
		return objectTypeName;
	}
}
