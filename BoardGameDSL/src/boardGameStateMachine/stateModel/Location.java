package boardGameStateMachine.stateModel;

import java.util.ArrayList;
import java.util.HashSet;

import boardGameStateMachine.Variable.SingleScopeVariableManager;
import boardGameStateMachine.Variable.VarGameObject;
import boardGameStateMachine.Variable.VarList;
import boardGameStateMachine.Variable.VarLocation;
import boardGameStateMachine.Variable.VarOwner;
import boardGameStateMachine.Variable.Variable;


public class Location {
	private String name;
	private boolean isSupply = false;
	public GameObjectTemplate supplying = null;
	private SingleScopeVariableManager varMan;
	private Game g;
	
	private boolean existVisibleEverybody = true;
	private HashSet<String> existVisiblePlayers = new HashSet<String>();

	private boolean valueVisibleEverybody = false;
	private HashSet<String> valueVisiblePlayers = new HashSet<String>();
	
	
	public ArrayList<GameObjectInstance> inventory = new ArrayList<GameObjectInstance>();


	private ArrayList<Location> connections = new ArrayList<Location>();
	
	
	public Location(String name, String objectType) {
		this.name = name;
		this.varMan = new SingleScopeVariableManager();
		varMan.addVariable("Owner", new VarOwner());
		g.addLocation(this);
	}

	/*
	 * Inventory of location, what objects are here
	 * converts to an array of Variables
	 */
	public Variable getInventory() {
		Variable[] inv = new Variable[inventory.size()];
		int i = 0;
		for(GameObjectInstance goi : inventory){
			inv[i] = new VarGameObject(goi);
			i++;
		}
		
		return new VarList(inv);
	}
	
	public void removeObject(GameObjectInstance id){
		inventory.remove(id);
	}
	
	public void addObject(GameObjectInstance id){
		inventory.add(id);
	}
	
	
	/*
	 * Existance Visibility, what players may see the objects that are present in this location
	 */
	public void setPublicExistanceVisibility(boolean publiclyVisible){
		existVisibleEverybody = publiclyVisible;
	}
	
	public boolean getExistanceVisibility(String player){
		if (existVisibleEverybody){
			return true;
		} else {
			return existVisiblePlayers.contains(player);
		}
	}
	
	public void addExistanceVisiblePlayer(String player){
		existVisiblePlayers.add(player);
	}
	
	public void removeExistanceVisiblePlayer(String player){
		if (existVisiblePlayers.contains(player)){
			existVisiblePlayers.remove(player);
		}
	}

	/*
	 * Value Visibility: What players may see hidden values of objects present here
	 */
	public void setPublicValueVisibility(boolean publiclyVisible){
		valueVisibleEverybody = publiclyVisible;
	}
	
	public boolean getValueVisibility(String player){
		if (valueVisibleEverybody){
			return true;
		} else {
			return valueVisiblePlayers.contains(player);
		}
	}
	
	public void addValueVisiblePlayer(String player){
		valueVisiblePlayers.add(player);
	}
	
	public void removeValueVisiblePlayer(String player){
		if (valueVisiblePlayers.contains(player)){
			valueVisiblePlayers.remove(player);
		}
	}
	
	/*
	 * Is this location an infinite supply of some object
	 */
	public void setSupply(GameObjectTemplate o) {
		isSupply = true;
		supplying = o;
	}

	public GameObjectTemplate getSupplyTemplate() {
		return supplying;
	}
	
	
	public Variable getConnections(){
		Variable[] connectedLocs = new Variable[connections.size()];
		int i = 0;
		for(Location loc : connections){
			connectedLocs[i] = new VarLocation(loc);
			i++;
		}
		
		return new VarList(connectedLocs);
	}
	
	public void addConnections(Location l){
		connections.add(l);
	}
	
	public boolean isConnectedTo(Location l){
		return connections.contains(l);
	}

	
	/*
	 * Generic getters and setters
	 */
	public SingleScopeVariableManager getVarManager() {
		return varMan;
	}

	public String getName() {
		return name;
	}

	public boolean isSupply() {
		return isSupply;
	}

}
