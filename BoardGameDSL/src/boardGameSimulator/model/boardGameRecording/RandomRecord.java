package boardGameSimulator.model.boardGameRecording;

import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class RandomRecord implements Record{
	
	
	private int random;
	private Variable v;
	private String vAsString;

	public RandomRecord(int result, Variable v){
		this.random = result;
		this.v = v;
		
	}
	
	public RandomRecord(int result, String s){
		this.random = result;
		this.v = null;
		this.vAsString = s;
		
	}
	
	public int getRandom(){
		return random;
	}
	
	public String getVariableAsString(){
		if(v!=null){
			return v.toString();
		}else{
			return vAsString;
		}
	}

	public String recordToString() {
		String r ="R "+ random + ": " + v.toString();

		return r;
	}

	public String recordToShortString() {
		String r = random + ": " + v.toString();

		return r;
	}



}
