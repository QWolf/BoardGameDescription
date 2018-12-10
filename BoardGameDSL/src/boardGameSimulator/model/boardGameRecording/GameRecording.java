package boardGameSimulator.model.boardGameRecording;

import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class GameRecording {

	
	private ActionRecord[] ar;
	private int nextAction = 0;
	
	
	
	public GameRecording(){
		
	}
	
	
	
	public String getNextActionName(){
		return ar[nextAction].getActionName();
	}



	public void writeRandom(Variable value) {
		// TODO Auto-generated method stub
		
	}



	public void advanceRandom() {
		// TODO Auto-generated method stub
		
	}



	public void writeAction(String actionround, String player, Variable[] parameters) {
		// TODO Auto-generated method stub
		
	}



	public void advanceActions() {
		// TODO Auto-generated method stub
		
	}



	public Variable getNextRandomVariable() {
		// TODO Auto-generated method stub
		return null;
	}



	public Variable[] getNextActionParameters() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
