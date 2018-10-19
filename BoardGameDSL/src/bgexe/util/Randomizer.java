package bgexe.util;

import java.util.Random;

public class Randomizer {
	
	
	
	private Object[] variables;
	private Object currentValue;
	
	
	public Randomizer(Object[] varList){
		variables = varList;
		currentValue = variables[0];
	}
	
	
	public Object getValue(){
		return currentValue;
	}
	
	public Object randomize(){
		int rnd = new Random().nextInt(variables.length);
		currentValue = variables[rnd];
		
		return currentValue;
	}

}
