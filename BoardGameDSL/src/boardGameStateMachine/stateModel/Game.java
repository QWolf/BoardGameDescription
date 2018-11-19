package boardGameStateMachine.stateModel;

public class Game {

	private String gameName;
	private Player[] players = new Player[0];
	private int minPlayers;
	private int maxPlayers;

	public Game(String name, int minPlayers, int maxPlayers) {
		gameName = name;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
	}

	public String getName() {
		return gameName;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public void addPlayer(Player p) {
		Player[] newList = new Player[players.length + 1];
		System.arraycopy(players, 0, newList, 0, players.length);
		players = newList;
		players[players.length - 1] = p;

	}

}
