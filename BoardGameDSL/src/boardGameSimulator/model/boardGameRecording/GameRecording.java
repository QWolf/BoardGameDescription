package boardGameSimulator.model.boardGameRecording;

public class GameRecording {

	
	private ActionRecord[] ar;
	private int actionRecordCounter = 0;
	
	
	
	public GameRecording(){
		
	}
	
	
	
	public void executeNextAction(){
		ar[actionRecordCounter].executeAction();
		
	}
	
	
}
