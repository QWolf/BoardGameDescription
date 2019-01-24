package boardGameSimulator.model.boardGameStateMachine.stateModel;

import java.util.Random;

import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class Randomizer {
	
	
	Variable[] possibleOptions;
	Variable currentValue;
	int currentIndex = 0;
	
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
		currentIndex = randInt;
	}
	
	//Set value of Randomizer to value v
	public void setValue(Variable v, int i){
		currentValue = v;
		currentIndex = i;
		
		
	}
	
	public Variable getVariableList(){
		VarList list = new VarList(possibleOptions);
		return list;
	}
	
	public int getCurrentIndex(){
		return currentIndex;
	}

	//Set value of Randomizer to index i, else dont do anything
	public void setValue(int index) {
		if(index >= 0 && index < possibleOptions.length){
			setValue(possibleOptions[index], index);
		} else{
			String possibleOptionsToString = new VarList(possibleOptions).toString();
			
			System.err.println("index " + index + "not available for Randomizer " + possibleOptionsToString);
			
		}
		
	}

}
