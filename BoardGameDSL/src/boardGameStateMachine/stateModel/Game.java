package boardGameStateMachine.stateModel;

import java.util.HashMap;

import boardGameStateMachine.util.IDManager;

public class Game {

	private String gameName;
	private HashMap<String, Player> players = new HashMap<String, Player>();
	private Player publicPlayer = new Player("Public", false, false);
	private int minPlayers;
	private int maxPlayers;
	public IDManager idManager = new IDManager();
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
		if (!players.containsKey(p.getName())) {
			players.put(p.getName(), p);
			return true;
		} else {
			return false;
		}
	}
	
	public int getPlayerNumber(){
		return players.size();
	}
	
	public int getLocationNumber(){
		return locations.size();
	}
	
	public int getObjectInstanceNumber(){
		return objectInstanceList.size();
	}
	
	public IDManager getIDManager(){
		return idManager;
	}

}
