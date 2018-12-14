package boardGameSimulator.model.boardGameStateMachine.stateModel;

import boardGameSimulator.model.boardGameStateMachine.Variable.SingleScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarOwner;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;

public class GameObjectInstance {

	private String name;
	private String objectType;
	private Location currentLocation;
	private Game g;
	private Randomizer randomizer;

	private SingleScopeVariableManager varManager;

	public GameObjectInstance(String name, String objectType, SingleScopeVariableManager varman, Game g, Location l) {
		this.name = name;
		this.objectType = objectType;
		this.varManager = varman;
		this.g = g;
		currentLocation = l;
		if (!varman.containsKey("Owner")) {
			Owner locationOwner = ((VarOwner) l.getVarManager().getVariable("Owner")).getValue();

			varman.addVariable("Owner", new VarOwner(locationOwner));
		}
		g.addObjectInstance(this);
		l.addObjectToInventory(this);
	}

	public Randomizer getRandomizer() {
		return randomizer;
	}

	public void setRandomizer(Randomizer r) {
		this.randomizer = r;
	}

	public SingleScopeVariableManager getVarManager() {
		return varManager;
	}

	public String getName() {
		return name;
	}

	public String getObjectType() {
		return objectType;
	}

	public Location getLocation() {
		return currentLocation;
	}

	public void moveTo(Location newLoc) {
		currentLocation.removeObjectFromInventory(this);
		newLoc.addObjectToInventory(this);
		this.currentLocation = newLoc;
	}
	
	public String toString(){
		String returnString = objectType + " " + name;
		
		if(varManager.variableCount() != 0){
			for(String key : varManager.keySet()){
				returnString += "\r\n\t\t" + key + ": " +varManager.getVariable(key).toString();
			}
		}
		
		if(randomizer != null){
			returnString += "\r\n\t\t Randomizer Value: "+ randomizer.getValue();
		}
		
		return returnString;
	}
}
