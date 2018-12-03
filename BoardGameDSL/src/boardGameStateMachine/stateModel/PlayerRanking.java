package boardGameStateMachine.stateModel;

public class PlayerRanking {
	
	/**
	 * Object which holds the ranking of a player
	 */
	
	private boolean isWinner = false;
	private int score = 0;
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

}
