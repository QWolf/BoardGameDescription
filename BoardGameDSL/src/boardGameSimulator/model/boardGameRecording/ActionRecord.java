package boardGameSimulator.model.boardGameRecording;

import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class ActionRecord {

	private String actionName;
	private Variable[] arguments;

	/**
	 * A record of a single action
	 * @param ar		The name of the Action that is to be performed, as action
	 * @param arguments	The arguments that go with the action, as Variables
	 */
	public ActionRecord(String ar, Variable[] arguments) {
		this.actionName = ar;
		this.arguments = arguments;

	}
	
	public String getActionName(){
		return actionName;
	}
	
	public Variable[] getArguments(){
		return arguments;
	}



}
