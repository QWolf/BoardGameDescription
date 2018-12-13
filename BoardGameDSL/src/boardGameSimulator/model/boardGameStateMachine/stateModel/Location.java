package boardGameSimulator.model.boardGameStateMachine.stateModel;

import java.util.ArrayList;
import java.util.HashSet;

import boardGameSimulator.model.boardGameStateMachine.Variable.SingleScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarGameObject;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarOwner;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;


public class Location {
	private String name;
	private boolean isSupply = false;
	public GameObjectTemplate supplying = null;
	private SingleScopeVariableManager varMan;
	private Game g;
	
	private boolean existVisibleEverybody = true;
	private HashSet<Player> existVisiblePlayers = new HashSet<Player>();

	private boolean valueVisibleEverybody = false;
	private HashSet<Player> valueVisiblePlayers = new HashSet<Player>();
	
	
	private ArrayList<GameObjectInstance> inventory = new ArrayList<GameObjectInstance>();


	private ArrayList<Location> connections = new ArrayList<Location>();
	
	
	public Location(String name, GameObjectTemplate objectTemplate, Game g) {
		this.name = name;
		this.varMan = new SingleScopeVariableManager();
		this.g = g;
		varMan.addVariable("Owner", new VarOwner());
		g.addLocation(this);
		
		this.supplying = objectTemplate;
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
	
	public void removeObjectFromInventory(GameObjectInstance id){
		inventory.remove(id);
	}
	
	public void addObjectToInventory(GameObjectInstance id){
		inventory.add(id);
	}
	
	
	/*
	 * Existance Visibility, what players may see the objects that are present in this location
	 */
	public void setPublicExistanceVisibility(boolean publiclyVisible){
		existVisibleEverybody = publiclyVisible;
	}
	
	public boolean getExistanceVisibility(Player player){
		if (existVisibleEverybody){
			return true;
		} else {
			return existVisiblePlayers.contains(player);
		}
	}
	
	public void addExistanceVisiblePlayer(Player player){
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
	
	public boolean getValueVisibility(Player player){
		if (valueVisibleEverybody){
			return true;
		} else {
			return valueVisiblePlayers.contains(player);
		}
	}
	
	public void addValueVisiblePlayer(Player player){
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
