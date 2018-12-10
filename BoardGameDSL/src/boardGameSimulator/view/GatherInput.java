package boardGameSimulator.view;

import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class GatherInput implements ViewInterface {
	
	String actionname = null;
	String parameters = null;
	
	
	
	
	public void waitForInput(){
		//TODO stream
		String input = null;
		
		
		//TODO output
		String output = parseInput(input);
		
		
	}
	
	public String parseInput(String str){
		String sanitizedString = str.replaceAll(" ", "");
		String[] splitString = sanitizedString.split(":");
		if(splitString.length != 2){
				return "Could not parse input. Please input in the format: <Actionname>: [par1] (, [par2]) etc.";
		}
		
		actionname = splitString[0];
		actionname = splitString[1];
		return "Action registered";
	}

	@Override
	public String getActionName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Variable[] getParameters() {
		// TODO Auto-generated method stub
		return null;
	}
	


	@Override
	public Variable[] getActionParameters() {
		// TODO Auto-generated method stub
		return null;
	}

}
