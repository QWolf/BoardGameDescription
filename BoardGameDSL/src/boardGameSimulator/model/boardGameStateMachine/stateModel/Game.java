package boardGameSimulator.model.boardGameStateMachine.stateModel;

import java.util.HashMap;

import boardGameSimulator.model.boardGameStateMachine.Variable.SingleScopeVariableManager;

public class Game {

	private String gameName;
	private HashMap<String, Player> players = new HashMap<String, Player>();
	private HashMap<String, Player> entities = new HashMap<String, Player>();
	private Player[] turnOrder;
	private int currentTurnIndex = 0;
	
	
	private Player publicPlayer = new Player("Public", false, false, this);
	private int minPlayers;
	private int maxPlayers;
	private SingleScopeVariableManager varMan = new SingleScopeVariableManager();
	private HashMap<String, Location> locations = new HashMap<String, Location>();
	private HashMap<String, GameObjectTemplate> objectTypeList = new HashMap<String, GameObjectTemplate>();
	private HashMap<String, GameObjectInstance> objectInstanceList = new HashMap<String, GameObjectInstance>();

	public Game(String name, int minPlayers, int maxPlayers) {
		gameName = name;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
	}

	public String getName() {
		return gameName;
	}
	
	public void setTurnOrder(Player[] to){
		turnOrder = to;
		currentTurnIndex = 0;
	}
	
	public Player[] getTurnOrder(){
		return turnOrder;
	}
	
	public Player getCurrentTurn(){
		return turnOrder[currentTurnIndex];
	}
	
	public void advanceTurn(int i){
		currentTurnIndex = (currentTurnIndex+i) % turnOrder.length;
		
	}

	public Player[] getPlayers() {
		return (Player[]) players.values().toArray();
	}
	
	public Player getPlayer(String name){
		return players.get(name);
	}

	public boolean addPlayer(Player p) {
		if (players.size() >= maxPlayers){
			return false;
		}
		if (!entities.containsKey(p.getName())) {
			players.put(p.getName(), p);
			entities.put(p.getName(), p);
			return true;
		} else {
			return false;
		}
	}
	
	public int getPlayerNumber(){
		return players.size();
	}
	
	public Player[] getEntity() {
		return (Player[]) entities.values().toArray();
	}
	
	public Player getEntity(String name){
		return entities.get(name);
	}
	
	public boolean addEntity(Player p){
		return entities.put(p.getName(), p) == null;
	}
	
	public int getEntityNumber(){
		return entities.size();
	}
	
	public int getLocationNumber(){
		return locations.size();
	}
	
	public int getObjectInstanceNumber(){
		return objectInstanceList.size();
	}
	
	public SingleScopeVariableManager getVarMan(){
		return varMan;
	}

	public void addObjectInstance(GameObjectInstance gameObjectInstance) {
		objectInstanceList.put(gameObjectInstance.getName(), gameObjectInstance);
	}

	public void addGameObjectTemplate(GameObjectTemplate template) {
		objectTypeList.put(template.getName(), template);
		
	}

	public void addLocation(Location location) {
		locations.put(location.getName(), location);		
	}

}