package boardGameSimulator.model.boardGameStateMachine.stateModel;

public class PlayerRanking {
	
	/**
	 * Object which holds the ranking of a player
	 */
	private Player player;
	private boolean isWinner = false;
	private int score = 0;
	
	public PlayerRanking (Player p){
		this.player = p;
	}
	public boolean isWinner() {
		return isWinner;
	}
	public void setWinner() {
		this.isWinner = true;
	}
	public boolean isLoser() {
		return !isWinner;
	}
	public void setLoser() {
		this.isWinner = false;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	
	public String getPlayerRankingAsString(boolean considerScore){
		String ranking = player.getName();
		
		if(considerScore){
			ranking += ": " + getScore() + ", ";
		} else{
			ranking += ": ";
		}
		
		if(isWinner){
			ranking += "WINNER!";
		}else{
			ranking += "Loser!";
		}
		
		return ranking;
	}

}
