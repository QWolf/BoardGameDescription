package boardGameSimulator.model.boardGameStateMachine.stateModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import boardGameSimulator.controller.StateMachineController;
import boardGameSimulator.model.boardGameStateMachine.Variable.SingleScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarGameObject;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarPlayer;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class Game {

	private String gameName;

	// Players: Actually contending for the win
	private HashMap<String, Player> players = new HashMap<String, Player>();
	// Entities: Both players contending for the win, as well as neutral forces
	// which cannot win
	private HashMap<String, Player> entities = new HashMap<String, Player>();
	private Player[] turnOrder = new Player[0];
	private int currentTurnIndex = 0;

	// private Player publicPlayer = new Player("Public", false, false, this);
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
	 * 
	 * @param name
	 *            name of the game
	 * @param minPlayers
	 *            minimal number of players
	 * @param maxPlayers
	 *            maximum number of players
	 * @param smc
	 *            A state Machine Controller
	 */
	public Game(String name, int minPlayers, int maxPlayers) {
		this.gameName = name;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
		// this.controller = smc;
	}

	public String getName() {
		return gameName;
	}

	public void setTurnOrder(Player[] to) {
		turnOrder = to;
		currentTurnIndex = 0;
	}

	public Player[] getTurnOrder() {
		return turnOrder;
	}

	public Player getCurrentTurn() {
		if(turnOrder.length == 0){
			return null;
		}
		return turnOrder[currentTurnIndex];
	}

	public void advanceTurn(int i) {
		currentTurnIndex = (currentTurnIndex + i) % turnOrder.length;

	}

	public VarList getPlayers() {
		Variable[] plrs = new Variable[players.size()];
		int i = 0;
		for (String key : players.keySet()) {
			plrs[i] = new VarPlayer(players.get(key));
			i++;
		}

		return new VarList(plrs);
	}

	public Player getPlayer(String name) {
		return players.get(name);
	}

	public boolean addPlayer(Player p) {
		if (players.size() >= maxPlayers) {
			return false;
		}
		if (!entities.containsKey(p.getName())) {
			players.put(p.getName(), p);
			entities.put(p.getName(), p);
			varMan.addVariable(p.getName(), new VarPlayer(p));
			return true;
		} else {
			return false;
		}
	}

	public int getPlayerNumber() {
		return players.size();
	}

	public Player[] getEntity() {
		return (Player[]) entities.values().toArray();
	}

	public Player getEntity(String name) {
		return entities.get(name);
	}

	public boolean addEntity(Player p) {
		if (entities.put(p.getName(), p) == null) {
			varMan.addVariable(p.getName(), new VarPlayer(p));
			return true;
		}

		return false;
	}

	public int getEntityNumber() {
		return entities.size();
	}

	public int getLocationNumber() {
		return locations.size();
	}

	public Location getLocation(String locName) {
		return locations.get(locName);
	}

	public int getObjectInstanceNumber() {
		return objectInstanceList.size();
	}

	public SingleScopeVariableManager getVarMan() {
		return varMan;
	}

	public void addObjectInstance(GameObjectInstance gameObjectInstance) {
		objectInstanceList.put(gameObjectInstance.getName(), gameObjectInstance);
		varMan.addVariable(gameObjectInstance.getName(), new VarGameObject(gameObjectInstance));
	}

	public GameObjectInstance getGameObjectInstance(String name) {
		return objectInstanceList.get(name);
	}

	public void addGameObjectTemplate(GameObjectTemplate template) {
		objectTypeList.put(template.getName(), template);

	}

	public void addLocation(Location location) {
		locations.put(location.getName(), location);
		varMan.addVariable(location.getName(), new VarLocation(location));
	}

	public StateMachineController getStateMachineController() {
		return controller;
	}

	public void addActionRound(ActionRound ar) {
		actionRoundList.put(ar.getName(), ar);
	}

	public ActionRound getActionRound(String name) {
		return actionRoundList.get(name);
	}

	public void setStateMachineController(StateMachineController smc) {
		this.controller = smc;
	}

	public GameObjectTemplate getGameObjectTemplate(String name) {
		return objectTypeList.get(name);
	}

	public void printExtended() {
		print("Printing Game:");
		printGlobalVars();
		printPlayers();
		printLocations();
		printActions();
	}

	public void printGlobalVars() {
		print("Global Variables");
		if (varMan.variableCount() != 0) {
			for (String key : varMan.keySet()) {
				System.out.println(key + ": " + varMan.getVariable(key).toString());
			}
		}
		print("CurrentTurn: " + getCurrentTurn());

	}

	public void printPlayers() {
		print("Players:");
		for (Player p : players.values()) {
			print(p.toString());
		}
	}

	public void printLocations() {
		print("Locations");
		List<String> sortedKeys = new ArrayList<String>(locations.keySet());
		Collections.sort(sortedKeys);
		for (String key : sortedKeys) {
			print(locations.get(key).toString());
		}

	}

	public void printActions() {
		print("Actions");
		for (String arName : actionRoundList.keySet()) {
			ActionRound ar = actionRoundList.get(arName);
			if (ar.isAction()) {
				System.out.println(ar.printNamePars());
			}
		}
	}

	public void print(String s) {
		System.out.println(s);
	}

	public void printWinnersAndLosers() {

		ArrayList<Player> winners = new ArrayList<Player>();
		ArrayList<Player> losers = new ArrayList<Player>();

		for (Player p : players.values()) {
			if (p.getRanking().isWinner()) {
				winners.add(p);
			} else {
				losers.add(p);
			}
		}
		System.out.println("-----Results------");
		for (Player p : winners) {
			System.out.println(p.getRanking().getPlayerRankingAsString(true));
		}
		for (Player p : losers) {
			System.out.println(p.getRanking().getPlayerRankingAsString(true));
		}
	}

	public int getMinPlayers() {
		return minPlayers;
	}

}
