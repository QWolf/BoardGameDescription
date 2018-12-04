package boardGameSimulator.model.boardGameStateMachine.stateModel;

import java.util.Random;

import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class Randomizer {
	
	
	Variable[] possibleOptions;
	Variable currentValue;
	
	/**
	 * Randomizer, produces a random object out of the possible options provided
	 * @param values possible outcomes of the Randomizer. each object has an equal chance of appearing
	 */
	public Randomizer(Variable[] values){
		this.possibleOptions = values;
		this.currentValue = possibleOptions[0];
		
	}
	
	public Variable getValue(){
		return currentValue;
	}
	
	public void Randomize(){
		Random r = new Random();
		int randInt = r.nextInt(possibleOptions.length);
		currentValue = possibleOptions[randInt];
		
		
	}
	
	public void setValue(Variable v){
		currentValue = v;
	}
	
	public Variable getVariableList(){
		VarList list = new VarList(possibleOptions);
		return list;
	}

}
