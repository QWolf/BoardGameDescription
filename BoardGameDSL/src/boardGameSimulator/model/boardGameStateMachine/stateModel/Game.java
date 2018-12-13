package boardGameSimulator.model.boardGameStateMachine.stateModel;

import java.util.HashMap;

import boardGameSimulator.controller.StateMachineController;
import boardGameSimulator.model.boardGameStateMachine.Variable.SingleScopeVariableManager;

public class Game {

	private String gameName;
	private HashMap<String, Player> players = new HashMap<String, Player>();
	private HashMap<String, Player> entities = new HashMap<String, Player>();
	private Player[] turnOrder;
	private int currentTurnIndex = 0;
	
	
//	private Player publicPlayer = new Player("Public", false, false, this);
	private int minPlayers;
	private int maxPlayers;
	private SingleScopeVariableManager varMan = new SingleScopeVariableManager();
	private HashMap<String, Location> locations = new HashMap<String, Location>();
	private HashMap<String, GameObjectTemplate> objectTypeList = new HashMap<String, GameObjectTemplate>();
	private HashMap<String, GameObjectInstance> objectInstanceList = new HashMap<String, GameObjectInstance>();
	private HashMap<String, ActionRound> actionRoundList = new HashMap<String, ActionRound>();
	private StateMachineController controller;
	
	/**
	 * A game object
	 * @param name	name of the game
	 * @param minPlayers minimal number of players
	 * @param maxPlayers maximum number of players
	 * @param smc A state Machine Controller
	 */
	public Game(String name, int minPlayers, int maxPlayers) {
		this.gameName = name;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
//		this.controller = smc;
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
	
	public Location getLocation(String locName){
		return locations.get(locName);
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
	
	public GameObjectInstance getGameObjectInstance(String name){
		return objectInstanceList.get(name);
	}

	public void addGameObjectTemplate(GameObjectTemplate template) {
		objectTypeList.put(template.getName(), template);
		
	}

	public void addLocation(Location location) {
		locations.put(location.getName(), location);		
	}
	
	public StateMachineController getStateMachineController(){
		return controller;
	}
	
	public void addActionRound(ActionRound ar){
		actionRoundList.put(ar.getName(), ar);
	}
	public ActionRound getActionRound(String name){
		return actionRoundList.get(name);
	}
	
	public void setStateMachineController(StateMachineController smc){
		this.controller = smc;
	}

	public GameObjectTemplate getGameObjectTemplate(String name) {
		return objectTypeList.get(name);
	}

}
