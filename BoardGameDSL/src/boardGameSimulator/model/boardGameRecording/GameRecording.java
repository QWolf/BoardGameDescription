package boardGameSimulator.model.boardGameRecording;

public class GameRecording {

	
	private ActionRecord[] ar;
	private int nextAction = 0;
	
	
	
	public GameRecording(){
		
	}
	
	
	
	public String getNextActionName(){
		return ar[nextAction].getActionName();
	}
	
	
}
