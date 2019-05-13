package boardGameSimulator.model.boardGameRecording;

import java.util.ArrayList;

import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class GameRecording {
	
	//TODO: Incorporate Setup
	
	private ArrayList<Record> recordList = new ArrayList<Record>();

	private ArrayList<ActionRecord> ar = new ArrayList<ActionRecord>();
	private int nextAction = 0;

	private ArrayList<RandomRecord> rr = new ArrayList<RandomRecord>();
	private int nextRandom = 0;
	
	

	public String getNextActionName() {
		return ar.get(nextAction).getActionName();
	}

	public void writeRandom(int i, Variable value) {
		RandomRecord record = new RandomRecord(i, value);
		rr.add(record);
		recordList.add(record);
		
	}
	
	public void writeRandom(int i, String value) {
		RandomRecord record = new RandomRecord(i, value);
		rr.add(record);
		recordList.add(record);
		
	}

	public void advanceRandom() {
		nextRandom++;
	}

	public void writeAction(String actionround, String player, String[] parameters) {
		ActionRecord record = new ActionRecord(player, actionround, parameters);
		ar.add(record);
		recordList.add(record);
	}

	public void advanceActions() {
		nextAction++;
	}

	public int getNextRandomVariable() {
		System.out.println(nextRandom);
		return rr.get(nextRandom).getRandom();
	}

	public String[] getNextActionParameters() {
		return ar.get(nextAction).getArguments();
	}

	
	public String printGameRecordSorted(){
		
		String result = "";
		result += "Actions: \r\n";
		for(ActionRecord a : ar){
			result += a.recordToShortString() + "\r\n";
		}

		result += "Random: \r\n";
		for(RandomRecord r : rr){
			result += r.recordToShortString() + "\r\n";
		}
		
		return result;
	}
	public String printGameRecordUnsorted(){
		
		String result = "";
		for(Record r : recordList){
			result += r.recordToString() + "\r\n";
		}
		
		return result;
	}	
	
}
