package boardGameStateMachine.util;

import java.util.HashMap;

import boardGameStateMachine.actionModel.CodeValue.CodeValue;
import boardGameStateMachine.stateModel.Action;
import boardGameStateMachine.stateModel.GameObjectInstance;
import boardGameStateMachine.stateModel.GameObjectTemplate;
import boardGameStateMachine.stateModel.Location;
import boardGameStateMachine.stateModel.Player;
import boardGameStateMachine.stateModel.Round;

//ID manager, tracks what type an Id is. Is connected to a specific Object, either an ID or the global Game itself
public class IDManager {

	// List of types of the ID's
	private HashMap<String, IDType> typeList = new HashMap<String, IDType>();
	private HashMap<String, CodeValue> referenceList = new HashMap<String, CodeValue>();

	public boolean addID(String name, IDType type, CodeValue o) {
		if (!typeList.containsKey(name)) {
			typeList.put(name, type);
			referenceList.put(name, o);
			return true;
		} else {
			return false;
		}
	}

	public IDType getType(String name) {
		return typeList.get(name);
	}

	public CodeValue getObject(String name) {
		return  referenceList.get(name);
	}

	public Player getPlayer(String name) {
		return (Player) referenceList.get(name);
	}

	public Location getLocation(String name) {
		return (Location) referenceList.get(name);
	}

	public GameObjectInstance getObjectInstance(String name) {
		return (GameObjectInstance) referenceList.get(name);
	}

	public GameObjectTemplate getObjectTemplate(String name) {
		return (GameObjectTemplate) referenceList.get(name);
	}

	public Randomizer getRandomizer(String name) {
		return (Randomizer) referenceList.get(name);
	}

	public Round getRound(String name) {
		return (Round) referenceList.get(name);
	}

	public Action getAction(String name) {
		return (Action) referenceList.get(name);
	}

	public int getInt(String name) {
		return (int) referenceList.get(name);
	}

	public Double getDouble(String name) {
		return (double) referenceList.get(name);
	}

	public boolean getBoolean(String name) {
		return (boolean) referenceList.get(name);
	}
	
	public IDManager getCopy(){
		IDManager newManager = new IDManager();
		for(String key : typeList.keySet()){
			newManager.addID(key, typeList.get(key), referenceList.get(key));
		}
		return newManager;
	}
	
	

}